package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;
import bargainingchips.Stack;


/**
 * If p1 percent of the stacks regarding quantity and p2 percent of total price of the bundle be satisfied
 * compared to the total price of the wishlist, 
 * bundle b would be evaluated as 1.0, otherwise as 0.0.
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public class UF_prcntQTYandprcntPrice implements UtilityFunction {

	Bundle wishlist;

	double p1;
	double p2;	

	public UF_prcntQTYandprcntPrice(Bundle w, double p1, double p2)
	{
		this.wishlist=w;
		this.p1=p1;  // tolerable percentage for extra price 
		this.p2=p2;  // tolerable percentage for extra stacks and/or stacks with less quantities 
	}

	/**
	 * @return the wishlist
	 */
	public Bundle getWishlist() {
		return wishlist;
	}

	/**
	 * @param wishlist the wishlist to set
	 */
	public void setWishlist(Bundle w) {
		this.wishlist = w;
	}

	@Override
	public Double getUtility(Bundle b) 
	{
		// TODO Auto-generated method stub
		int num=wishlist.size();

		if (b!=null)
		{
			for (Stack s : b)
				for (Stack t: wishlist)
					if (s.hasSameColorAs(t) && s.getQuantity() >= t.getQuantity())
						// if the quantity is less than what is required, so this stack could not be satisfied
						num--;							
			if (b.getTotalPrice() <= p2* wishlist.getTotalPrice()+wishlist.getTotalPrice() && num < (double)wishlist.size()*(1-p1) )   
				// i.e., the number of `dissatisfied' stacks are less than p1 percent of the stacks regarding the quantity
				// and, the bundle total price is less that or equal to p2 percent of the wishlist total price 
				return 1.0;
			else 
				return 0.0;
		}
		return 0.0;
	}


	/**
	 * @return the p1
	 */
	public double getP1() {
		return p1;
	}


	/**
	 * @param p1 the p1 to set
	 */
	public void setP1(double p1) {
		this.p1 = p1;
	}


	/**
	 * @return the p2
	 */
	public double getP2() {
		return p2;
	}


	/**
	 * @param p2 the p2 to set
	 */
	public void setP2(double p2) {
		this.p2 = p2;
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName()+":qty%"+p1+":price%"+p2;
	}

}
