package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;
import bargainingchips.Stack;

/**
 * While the total price of the bundle b is less than that of the wishlist, 
 * it evaluates the bundle as 1.0 if quantity of all stacks in b is equal or more than
 * that of the stack of the same color in wishlist; since 
 * under free disposal it doesn't cost to receive extra quantities. 
 * If any less quantity or extra total price, it evaluates bundle b as 0.0.
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public class UF_FreeDisposal implements UtilityFunction {

	Bundle wishlist;

	public UF_FreeDisposal(Bundle w)
	{
		this.wishlist=w;
	}

	/**
	 * @return the wishlist
	 */
	public Bundle getWishlist() 
	{
		return wishlist;
	}

	/**
	 * @param wishlist the wishlist to set
	 */
	public void setWishlist(Bundle w) 
	{
		this.wishlist = w;
	}

	@Override
	public Double getUtility(Bundle b) 
	{
		// TODO Auto-generated method stub
		int num=0;
		if (b!=null)
		{
			for (Stack s : b)
			{
				for (Stack t: wishlist)
					if (s.getColor()==t.getColor() && s.getQuantity() != t.getQuantity())
						if (s.getQuantity() < t.getQuantity()) //bundle b doesn't have any stacks with extra quantities
							return 0.0;	
				num++; //bundle b has extra stacks
			}
			if (b.size() >= wishlist.size() && num >= 0 && b.getTotalPrice() <= wishlist.getTotalPrice())
				return 1.0;
			else 
				return 0.0;
		}
		return 0.0;
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName();
	}

}


