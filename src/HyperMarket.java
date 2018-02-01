public class HyperMarket extends Magazin{
	//Subclasa concreta pentru tipul de magazin HyperMarket
	
	public double calculScutiriTaxe()
	{
		for (int i = 0; i < facturi.size(); i ++)
		{
			if (facturi.get(i).getTotalCuTaxe() > 0.1 * getTotalCuTaxe())
			{
				return 0.01;
			}
		}
		return 0;
	}
}
