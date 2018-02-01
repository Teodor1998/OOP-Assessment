import java.util.Vector;

public class Factura {
	//Clasa Factura din scheletul dar in cerinta: (clasa concreta)

	String denumire;
	Vector<ProdusComandat> produse;
	
	public Factura ()
	{
		denumire = new String();
		produse = new Vector<ProdusComandat>();
	}
	
	public double getTotalFaraTaxe()
	{
		double total = 0;
		for (int i = 0; i < produse.size(); i ++)
		{
			//Adun la total: produsul dintre pret si cantitate al fiecarui
			//produs comandat.
			int cantitate = produse.get(i).getCantitate();
			total += cantitate * produse.get(i).getProdus().getPret();
		}
		//Rotunjire la 4 zecimale.
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalCuTaxe()
	{
		double total = 0;
		//Totalul se calculeaza ca suma dintre totalul fara taxe si taxe.
		total += getTotalFaraTaxe();
		total += getTaxe();
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTaxe()
	{
		double total = 0;
		for (int i = 0; i < produse.size(); i ++)
		{
			double taxa = produse.get(i).getTaxa();
			int cantitate = produse.get(i).getCantitate();
			
			//Se aplica taxa (procent) la pretul initial al produsului
			taxa = produse.get(i).getProdus().getPret() * taxa / 100;
			//Iar totalul se calculeaza prin suma taxelor aplicate fiecarui produs
			total += taxa * cantitate;
		}
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalTaraFaraTaxe(String tara)
	{
		double total = 0;
		for (int i = 0; i < produse.size(); i ++)
		{
			//Pentru fiecare produs din tara primita ca parametru
			//se va calcula pretul fara taxe si se va adauga la total.
			if (tara.equals(produse.get(i).getProdus().getTaraOrigine()))
			{
				double pret = produse.get(i).getProdus().getPret();
				int cantitate = produse.get(i).getCantitate();
				total += cantitate * pret;
			}
		}
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTotalTaraCuTaxe(String tara)
	{
		double total = 0;
		//Functia calculeaza totalul fara taxe pentru tara
		//cu taxele totale ale tarii
		total += getTotalTaraFaraTaxe(tara);
		total += getTaxeTara(tara);
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public double getTaxeTara(String tara)
	{
		double total = 0;
		for (int i = 0; i < produse.size(); i ++)
		{
			//Calculam taxele aplicate in total pentru toate produsele ce
			//provin din tara primita ca parametru
			if (tara.equals(produse.get(i).getProdus().getTaraOrigine()))
			{
				double taxa = produse.get(i).getTaxa();
				int cantitate = produse.get(i).getCantitate();
				taxa = produse.get(i).getProdus().getPret() * taxa / 100;
				total += taxa * cantitate;
			}
		}
		total = Math.round(total*10000.0)/10000.0;
		return total;
	}
	
	public String toString()
	{
		String s = "";
		s += denumire;
		s += '\n';
		s += "Total Fara Taxe: " + getTotalFaraTaxe();
		s += '\n';
		s += "Total Cu Taxe: " + getTotalCuTaxe();
		for (int i = 0; i < produse.size(); i ++)
		{
			s += '\n';
			s += produse.get(i).toString();
		}
		return s;
	}
}
