package bargainingchips.etc;
//package negotiator.onetomany;
//
//import java.util.HashMap;
//import java.util.Set;
//
//
///**
// * * A demand plan is a specification of the demand of an agent for each product.
// * An example:
// * Red -> 4, Blue -> 4, Yellow -> 2
// * 
// * 
// * 
// * 
// * 
// * @author Faria Nassiri-Mofakham
// *
// */
//public class Bid extends DemandPlan
//{
//
//	private HashMap<Product, Integer> bid;
//
//
//	public Bid() 
//	{
//		this.bid = new HashMap<Product, Integer>();
//	}
//
//	public HashMap<Product, Integer> getBid() 
//	{
//		return bid;
//	}
//
//
//	@Override
//	public String toString() 
//	{
//		return bid.toString();
//	}
//
//	public void setQuantity(Product p, Integer n)
//	{
//		bid.put(p,n);
//	}
//
//	public Integer getQuantity(Product p)
//	{
//		if (this.bid.containsKey(p))
//			return bid.get(p);
//		else
//			return 0;
//	}
//
//
//
//	public Set<Product> getKeys()
//	{
//		return bid.keySet();
//	}
//
//
//	public Set<Integer> getValues()
//	{
//		return (Set<Integer>) bid.values(); 
//	}
//
//
//}
