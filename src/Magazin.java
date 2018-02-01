import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public abstract class Magazin implements IMagazin {
	//Contine functia abstracta calculScutiriTaxe()
	
	String nume;
	Vector<Factura> facturi;
	
	public Magazin ()
	{
		nume = new String();
		facturi = new Vector<Factura>();
	}
	
	public double getTotalFaraTaxe()
	{
		double total = 0;
		for (int i = 0; i < facturi.size(); i ++)
		{
			//Calculez suma totalurilor fara taxe pentru fiecare
			//factura
			total += facturi.get(i).getTotalFaraTaxe();
		}
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalCuTaxe()
	{
		double total = 0;
		for (int i = 0; i < facturi.size(); i ++)
		{
			//Pe acelasi principiu ca metoda de mai sus
			total += facturi.get(i).getTotalCuTaxe();
		}
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalCuTaxeScutite()
	{
		//Calculez totalul cu taxe din care scad scutirea
		//calculata pentru fiecare tip de magazin in parte
		double scutire = calculScutiriTaxe();
		double total_nescutit = getTotalCuTaxe();
		scutire = total_nescutit * scutire;
		double total =  total_nescutit - scutire;
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalTaraFaraTaxe(String tara)
	{
		double total = 0;
		for (int i = 0; i < facturi.size(); i ++)
		{
			//Pentru fiecare factura calculez totalul pentru fiecare
			//tara fara taxe
			total += facturi.get(i).getTotalTaraFaraTaxe(tara);
		}
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalTaraCuTaxe(String tara)
	{
		double total = 0;
		for (int i = 0; i < facturi.size(); i ++)
		{
			//Pe acelasi principiu ca metoda de mai sus
			total += facturi.get(i).getTotalTaraCuTaxe(tara);
		}
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalTaraCuTaxeScutite(String tara)
	{
		//Calculez totalul cu taxe pentru tara din care scad scutirea
		//calculata pentru fiecare tip de magazin in parte
		double scutire = calculScutiriTaxe();
		double total_nescutit = getTotalTaraCuTaxe(tara);
		scutire = total_nescutit * scutire;
		double total = total_nescutit - scutire;
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public String toString()
	{
		//Construiesc stringul pentru afisare (pentru Taskul 1)
		//Cu toate informatiile magazinului.
		
		//Folosesc DecimalFormat "#' pentru a transforma numerele
		//deja rotunjite din: 5.0 in 5 (de exemplu)
		DecimalFormat form = new DecimalFormat("#.####");
		
		String s = nume + '\n' + '\n';
		s += "Total " + form.format(getTotalFaraTaxe()) + " ";
		s += form.format(getTotalCuTaxe()) + " ";
		s += form.format(getTotalCuTaxeScutite());
		s += '\n';
		s += '\n';
		s += "Tara";
		s += '\n';
		
		//Tarile le obtin prelucrand prima linie a fisierului taxe.txt
		Gestiune g = Gestiune.getInstance();
		ArrayList<String> tari = g.tari_in_taxe();
		
		//Sortez tarile in ordine alfabetica (ca in exemplul de out.txt din arhiva)
		Collections.sort(tari, new Comparator<String>()
				{
			//Folosesc explicit comparatorul pentru stringuri
			public int compare(String s1, String s2)
			{
				return s1.compareToIgnoreCase(s2);
			}
				});
		
		
		for (int i = 0; i < tari.size(); i ++)
		{
			//Apoi afisez in ordinea obtinuta mai sus totalurile tarilor
			s += tari.get(i) + " ";
			if (getTotalTaraCuTaxe(tari.get(i)) == 0)
			{
				s += "0";
				s += '\n';
				continue;
			}
			s += form.format(getTotalTaraFaraTaxe(tari.get(i))) + " ";
			s += form.format(getTotalTaraCuTaxe(tari.get(i))) + " ";
			s += form.format(getTotalTaraCuTaxeScutite(tari.get(i)));
			s += '\n';
		}
		s += '\n';
		
		//Sortez facturile dupa criteriul dat in enunt
		Collections.sort(facturi, new Comparator<Factura>()
				{
			public int compare(Factura f1, Factura f2)
			{
				if (f1.getTotalCuTaxe() > f2.getTotalCuTaxe())
					return 1;
				if (f1.getTotalCuTaxe() < f2.getTotalCuTaxe())
					return -1;
				return 0;
			}
				});
		
		for (int i = 0; i < facturi.size(); i ++)
		{
			//Parcurg facturile si le afisez cum este specificat in enunt
			s += facturi.get(i).denumire + '\n' + '\n';
			s += "Total" + " ";
			s += form.format(facturi.get(i).getTotalFaraTaxe()) + " ";
			s += form.format(facturi.get(i).getTotalCuTaxe());
			s += '\n';
			s += '\n';
			s += "Tara";
			s += '\n';
			for (int j = 0; j < tari.size(); j ++)
			{		
				s += tari.get(j) + " ";
				if (facturi.get(i).getTotalTaraCuTaxe(tari.get(j)) == 0)
				{
					s += "0";
					s += '\n';
					continue;
				}
				s += form.format(facturi.get(i).getTotalTaraFaraTaxe(tari.get(j))) + " ";
				s += form.format(facturi.get(i).getTotalTaraCuTaxe(tari.get(j)));
				s += '\n';
			}
			s += '\n';
		}
		//La sfarsit elimin ultimul '\n' (NewLine)
		String s2 = s.substring(0, s.length()-1);
		return s2;
	}
	
	//Implementata in clasele concrete aferente tipurilor de magazine
	public abstract double calculScutiriTaxe();
}
