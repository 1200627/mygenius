package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;
import bargainingchips.Stack;

/**
 * If 75 percent of the stacks be satisfied regarding the quantity as long as 
 * the total price would be less than the total price of the wishlist, 
 * bundle b would be evaluated as 1.0, otherwise as 0.0.
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public class UF_75pQtyOrNothing implements UtilityFunction {

	Bundle wishlist;
	String role;


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public UF_75pQtyOrNothing(Bundle w)
	{
		this.wishlist=w;
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
					if (s.getColor()==t.getColor() && s.getQuantity() >= t.getQuantity())
						// if bundle b has any additional stack
						// if the quantity is less than what is required, so this stack could not be satisfied
						num--;							
			if (b.getTotalPrice() <= wishlist.getTotalPrice() && num < (double)wishlist.size()*0.25) // i.e. less than 25 percent of the stacks could not be satisfied
				if (isBuyer(role)) 
					return 1.0;
				else 
					return 0.0;
			else 
				if (isBuyer(role)) 
					return 0.0;
				else 
					return 1.0;
		}
		if (isBuyer(role)) 
			return 0.0;
		else 
			return 1.0;
	}


	@Override
	public String toString()
	{
		return this.getClass().getSimpleName();
	}
	
	
	public Boolean isBuyer(String role)
	{
		switch (role.toLowerCase())
		{
		case "buyer": return Boolean.TRUE;
		case "seller": return Boolean.FALSE;
		default:
			System.out.println("Undefined role!");
			return null;
		}		
	}

}
