public class MarketFactory {
	//Clasa pentru a implementa Factory pattern
	
	public static String[] markets = {"MiniMarket", "MediumMarket", "HyperMarket"};
	
	public static Magazin createMarket(String tip)
	{
		//In functie de parametru creeaza o noua instanta a unei
		//sublcase concrete a clasei abstracte Magazin
		
		switch (tip)
		{
		case "MiniMarket":
			return new MiniMarket();
		case "MediumMarket":
			return new MediumMarket();
		case "HyperMarket":
			return new HyperMarket();
		}
		throw new IllegalArgumentException("Tipul de magazin: " + tip + "este invalid!");
	}
}
