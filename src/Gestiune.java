import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

public final class Gestiune {
	private static Gestiune instanta;
	private static boolean exist = false;
	
	ArrayList<Produs> produse;
	ArrayList<Magazin> magazine;
	TreeMap<String, TreeMap<String, Double>> taxe;
	
	String produse_file = null;
	String taxe_file = null;
	String facturi_file = null;
	
	private Gestiune(String produse_file, String taxe_file, String facturi_file)
	{
		this.produse_file = produse_file;
		this.taxe_file = taxe_file;
		this.facturi_file = facturi_file;
		
		produse = new ArrayList<Produs>();
		magazine = new ArrayList<Magazin>();
		taxe = new TreeMap<String, TreeMap<String, Double>>();
	}
	
	private Gestiune()
	{
		produse = new ArrayList<Produs>();
		magazine = new ArrayList<Magazin>();
		taxe = new TreeMap<String, TreeMap<String, Double>>();
	}
	
	public static Gestiune getInstance()
	{
		//Daca exista deja o instanta o intoarcem:
		if (exist)
			return instanta;
		
		//Daca nu, vom crea o instanta si apoi o vom intoarce:
		exist = true;
		instanta = new Gestiune();
		return instanta;
	}
	
	public static Gestiune replaceInstance(String produse_file, String taxe_file, String facturi_file)
	{
		//Stergem vechea instanta si o inlocuim cu una noua
		//Ramane Singleton pentru ca avem o singura instanta in program.
		if (exist)
		{
			instanta = null;
		}
		exist = true;
		instanta = new Gestiune(produse_file, taxe_file, facturi_file);
		return instanta;
	}
	
	public ArrayList<String> getCategorii()
	{
		ArrayList<String> categorii = new ArrayList<String>();
		for (int i = 0; i < produse.size(); i ++)
		{
			if (!categorii.contains(produse.get(i).getCategorie()))
			{
				categorii.add(produse.get(i).getCategorie());
			}
		}
		return categorii;
	}
	public void sortProduse(int nume_sau_tara)
	{
		//1 = sort dupa nume
		//2 = sort dupa tara
		
		if (nume_sau_tara == 1)
		{
			Collections.sort(produse, new Comparator<Produs>()
			{
				public int compare(Produs p1, Produs p2)
				{
					return p1.getDenumire().compareTo(p2.getDenumire());
				}
			});
		}
		else
		{
			Collections.sort(produse, new Comparator<Produs>()
			{
				public int compare(Produs p1, Produs p2)
				{
					return p1.getTaraOrigine().compareTo(p2.getTaraOrigine());
				}
			});
		}
	}
	
	//Citire produse.txt:
	public void citire_produse()
	{
		File produse_txt = new File(produse_file);
		
		try {
			//Scanner-ul scan va scana linie cu linie fisierul
			Scanner scan = new Scanner(produse_txt);
			
			String linie = scan.nextLine();
			
			//Scanner-ul scan_str va scana fiecare element (separat prin " ")
			//de pe linie curenta salvata in stringul linie
			Scanner scan_str = new Scanner(linie);
			scan_str.useDelimiter("\\s");
			
			ArrayList<String> tari = new ArrayList<String>();
			
			scan_str.next(); //"Produs" - camp informativ
			scan_str.next(); //"Categorie" - camp informativ
			
			while (scan_str.hasNext())
			{
				//Adaug tarile in vector
				tari.add(scan_str.next());
			}
			scan_str.close();
			
			while (scan.hasNextLine())
			{
				//Citest datele produselor de pe fiecare linie
				linie = scan.nextLine();
				scan_str = new Scanner(linie);
				scan_str.useDelimiter("\\s");
				
				String prod = scan_str.next();	//nume produs
				String categorie = scan_str.next();	//categorie produs
				String taraOrigine;	//taraOrigine produs
				double pret;	//pret produs
				
				for (int i = 0; i < tari.size(); i ++)
				{
					//Pentru fiecare tara voi crea cate un produs cu datele:
					//produs - categorie - tara - pret (aferent tarii)
					taraOrigine = tari.get(i);
					pret = scan_str.nextDouble();
					produse.add(new Produs (prod, categorie, taraOrigine, pret));
				}
				scan_str.close();
			}
			scan.close();
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
	}
	
	//Citesc fisierul taxe.txt si construiesc dictionarul taxe:
	public void parsing_taxe()
	{
		//Voi salva toate datele din fisier in cele 3 ArrayList-uri:
		ArrayList<String> tari = tari_in_taxe();
		ArrayList<String> categorii = new ArrayList<String>();
		ArrayList<ArrayList<Double>> taxe = new ArrayList<ArrayList<Double>>();
		
		
		File taxe_txt = new File(taxe_file);
		try {
			//Scanner-ul scan va scana linie cu linie fisierul
			Scanner scan = new Scanner (taxe_txt);
			
			//Prima linie a fost deja prelucrata in tari_in_taxe()
			scan.nextLine();
			
			while (scan.hasNextLine())
			{
				//Scanner-ul scan_lin va scana fiecare element (separat prin " ")
				Scanner scan_lin = new Scanner(scan.nextLine());
				scan_lin.useDelimiter("\\s");
				
				categorii.add(scan_lin.next());
				
				ArrayList<Double> tax = new ArrayList<Double>();
				//tax este un tablou unidimensional ce va fi un element din taxe
				//(variabila locala), nu dictionarul
				for (int i = 0; i < tari.size(); i ++)
				{
					tax.add(scan_lin.nextDouble());
				}
				taxe.add(tax);
				scan_lin.close();
			}
			scan.close();
			for (int i = 0; i < tari.size(); i ++)
			{
				//Acum introduc in dictionarul taxe toate informatiile extrase din taxe.txt
				TreeMap<String, Double> map = new TreeMap<String, Double>();
				for (int j = 0; j < categorii.size(); j ++)
				{
					map.put(categorii.get(j), taxe.get(j).get(i));
				}
				this.taxe.put(tari.get(i), map);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> tari_in_taxe()
	{
		//Aici se citesc numai numele tarilor din fisierul taxe.
		File taxe_txt = new File(taxe_file);
		try {
			Scanner scan = new Scanner(taxe_txt);
			String linie = scan.nextLine();
			scan.close();
			
			scan = new Scanner(linie);
			Scanner scan_str = scan.useDelimiter("\\s");
			scan_str.next(); //Ignor "Caregorie/Tara si trec pe a doua coloana"
			
			ArrayList<String> tari = new ArrayList<String>();
			
			while (scan_str.hasNext())
			{
				tari.add(scan_str.next());
			}
			scan.close();
			scan_str.close();
			
			return tari;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void parsing_facturi()
	{
		File facturi_txt = new File (facturi_file);
		try {
			//Cele doua scannere pe acelasi principiu ca pana acum.
			Scanner scan = new Scanner(facturi_txt);
			Scanner scan_line;
			while (scan.hasNextLine())
			{
				String line = scan.nextLine();
				if (line.length() == 0) //Linie goala
				{
					//O ignoram
					continue;
				}
				
				if (line.contains("Magazin"))
				{
					//Inseamna ca vom construi un magazin nou
					
					scan_line = new Scanner(line);
					scan_line.useDelimiter(":");
					scan_line.next();
					
					Magazin x = MarketFactory.createMarket(scan_line.next());
					x.nume = scan_line.next();
					
					//Adaug noul obiect magazin la ArrayList-ul magazine:
					magazine.add(x);
					scan_line.close();
					continue;
				}
				
				//Daca nu e nici rand magazin, nici rand gol, inseamna ca vom
				//avea o succesiune de randuri pentru o factura:
				
				//Construiesc o factura noua:
				Factura f = new Factura();
				f.denumire = line;
				scan.nextLine(); //Linie informativa
				line = scan.nextLine();
				while (line.length() > 1)
				{
					//Adaug produsele in factura:
					scan_line = new Scanner(line);
					scan_line.useDelimiter("\\s");
					
					String nume = scan_line.next();
					String tara = scan_line.next();
					
					ProdusComandat p = new ProdusComandat();
					p.setCantitate(scan_line.nextInt());
					
					for (int i = 0; i < produse.size(); i ++)
					{
						if (nume.equals(produse.get(i).getDenumire()))
							if (tara.equals(produse.get(i).getTaraOrigine()))
							{
								//Iau informatiile despre produs din ArrayList-ul produse
								p.setProdus(produse.get(i));
								break;
							}
					}
					//Taxa o iau din dictionarul taxe:
					p.setTaxa(this.taxe.get(p.getProdus().getTaraOrigine()).get(p.getProdus().getCategorie()));
					scan_line.close();
					
					//Adaug produsul la vectorul de produse din factura.
					f.produse.add(p);
					
					if (scan.hasNextLine())
						line = scan.nextLine();
					else
						break;
				}
				//Dupa ce am introdus toate datele in factura o adaug la ultimul magazin adaugat:
				magazine.get(magazine.size()-1).facturi.add(f);
			}
			scan.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Factura getMaxFactura()
	{
		Factura max = magazine.get(0).facturi.get(0);
		for (int i = 0; i < magazine.size(); i ++)
		{
			for (int j = 0; j < magazine.get(i).facturi.size(); j ++)
			{
				if (max.getTotalFaraTaxe() < magazine.get(i).facturi.get(j).getTotalFaraTaxe())
					max = magazine.get(i).facturi.get(j);
			}
		}
		return max;
	}
}