import java.util.Vector;

public class MiniMarket extends Magazin{
	//Subclasa concreta pentru tipul de magazin MiniMarket
	
	public double calculScutiriTaxe()
	{
		Vector<String> tari = new Vector<String>();
		double totalVanzari = getTotalCuTaxe();
		
		//Construiesc vectorul cu toate tarile existente in facturi:
		for (int i = 0; i < facturi.size(); i ++)
		{
			for (int j = 0; j < facturi.get(i).produse.size(); j ++)
			{
				if (!tari.contains(facturi.get(i).produse.get(j).getProdus().getTaraOrigine()))
				{
					tari.add(facturi.get(i).produse.get(j).getProdus().getTaraOrigine());
				}
			}
		}
		
		//Verific daca exista o tara pentru care suma produselor comandate sa indeplineasca
		//conditia:
		for (int i = 0; i < tari.size(); i ++)
		{
			double total_tara = 0;
			//Parcurg facturile:
			for (int j = 0; j < facturi.size(); j ++)
			{
				total_tara += facturi.get(j).getTotalTaraCuTaxe(tari.get(i));
			}
			if (total_tara > 0.5 * totalVanzari)
			{
				return 0.1;	//Exista tara => scutire de 10%
			}
		}
		return 0;
	}
}
