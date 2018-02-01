import java.util.Vector;

public class MediumMarket extends Magazin{
	//Subclasa concreta pentru tipul de magazin MediumMarket
	
	public double calculScutiriTaxe()
	{
		Vector<String> categ = new Vector<String>();
		double totalVanzari = getTotalCuTaxe();
		
		//Construiesc vectorul cu toate tarile existente in facturi:
		for (int i = 0; i < facturi.size(); i ++)
		{
			for (int j = 0; j < facturi.get(i).produse.size(); j ++)
			{
				if (!categ.contains(facturi.get(i).produse.get(j).getProdus().getCategorie()))
				{
					categ.add(facturi.get(i).produse.get(j).getProdus().getCategorie());
				}
			}
		}
		
		//Verific daca exista o categorie pentru care suma produselor comandate sa 
		//indeplineasca conditia:
		for (int i = 0; i < categ.size(); i ++)
		{
			double total_categ = 0;
			//Parcurg facturile:
			for (int j = 0; j < facturi.size(); j ++)
			{
				for (int k = 0; k < facturi.get(j).produse.size(); k ++)
				{
					if (categ.get(i).equals(facturi.get(j).produse.get(k).getProdus().getCategorie()))
					{
						double pret = facturi.get(j).produse.get(k).getProdus().getPret();
						int cantitate = facturi.get(j).produse.get(k).getCantitate();
						double taxa = facturi.get(j).produse.get(k).getTaxa();
						pret = pret + (pret * taxa / 100);
						total_categ += pret * cantitate;
					}
				}
			}
			if (total_categ > 0.5 * totalVanzari)
			{
				return 0.05;	//Exista categorie => scutire de 5%
			}
		}
		return 0;
	}
	
	//Metoda statica este folosita pentru partea de statistici (interfata grafica)
	static public double totalCategorie(Magazin m, String categorie)
	{
		double total_categ = 0;
		//Parcurg facturile:
		for (int j = 0; j < m.facturi.size(); j ++)
		{
			for (int k = 0; k < m.facturi.get(j).produse.size(); k ++)
			{
				if (categorie.equals(m.facturi.get(j).produse.get(k).getProdus().getCategorie()))
				{
					double pret = m.facturi.get(j).produse.get(k).getProdus().getPret();
					int cantitate = m.facturi.get(j).produse.get(k).getCantitate();
					double taxa = m.facturi.get(j).produse.get(k).getTaxa();
					pret = pret + (pret * taxa / 100);
					total_categ += pret * cantitate;
				}
			}
		}
		return total_categ;
	}
}
