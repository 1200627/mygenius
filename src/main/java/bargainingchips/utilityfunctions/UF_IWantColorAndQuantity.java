package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;
import bargainingchips.Stack;

/**
 * This utility function simply implements a hardheaded behavior 
 * which evaluates as 1 any bundle which includes a stack with chips 
 * in specific color c and quantity q, and 0, otherwise 
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public class UF_IWantColorAndQuantity implements UtilityFunction {

	int quantity;
	String color;

	public UF_IWantColorAndQuantity(String c, int q)
	{
		this.color=c;
		this.quantity=q;
	}

	@Override
	public Double getUtility(Bundle b) 
	{
		// TODO Auto-generated method stub

		if (b!=null)
			for (Stack s : b)
				if (s.getColor()==this.color && s.getQuantity()==this.quantity)
					return 1.0;
		return 0.0;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + ": " + quantity + " x " + color;
	}	
}