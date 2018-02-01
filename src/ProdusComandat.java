public class ProdusComandat {
	//Clasa ProdusComandat din scheletul dat in cerinta: (clasa concreta)

	private Produs produs;
	private double taxa;
	private int cantitate;
	
	public void setProdus(Produs produs)
	{
		this.produs = produs;
	}
	public Produs getProdus()
	{
		return produs;
	}
	public void setTaxa(double taxa)
	{
		this.taxa = taxa;
	}
	public double getTaxa()
	{
		return taxa;
	}
	public void setCantitate(int cantitate)
	{
		this.cantitate = cantitate;
	}
	public int getCantitate()
	{
		return cantitate;
	}
	public String toString()
	{
		String s = "";
		s += produs.toString();
		s += " - cantitate: " + getCantitate();
		return s;
	}
}