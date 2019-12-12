package bargainingchips.etc;

import java.util.HashMap;

/**
 * 
 * 
 * 
 * 
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public class DemandPlan 
{
	private HashMap<Product, Integer> demandPlan;
	private HashMap<Product, Double> weight;
	


	public DemandPlan() 
	{
		this.demandPlan = new HashMap<Product, Integer>();
		this.weight = new HashMap<Product, Double>();
	}

	public HashMap<Product, Integer> getDemandPlan() 
	{
		return demandPlan;
	}

	public HashMap<Product, Double> getWeight() 
	{
		return weight;
	}


	@Override
	public String toString() 
	{
		return demandPlan.toString();
	}

	public void setQuantity(Product p, Integer n)
	{
		demandPlan.put(p,n);
	}


	public void setWeight(Product p, Double e)
	{
		weight.put(p,e);
	} 
	
	public Integer getQuantity(Product p)
	{
		if (this.demandPlan.containsKey(p))
			return demandPlan.get(p);
		else
			return 0;
	}

	public Double getWeight(Product p)
	{
		if (this.weight.containsKey(p))
			return weight.get(p);
		else
			return 0.0;
	}

//	public Set<Product> getKeys()
//	{
//		return demandPlan.keySet();
//	}
//
//
//	public Set<Integer> getValues()
//	{
//		return (Set<Integer>) demandPlan.values(); 
//	}
}
