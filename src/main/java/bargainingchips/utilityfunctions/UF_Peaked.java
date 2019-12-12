package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;
import bargainingchips.Chip;
import bargainingchips.ChipMappingType;
import bargainingchips.Stack;
import bargainingchips.WishList;

/**
 * 
 * According to Equation (1) [1], the buyer with peaked utility function evaluates bundles with price 0, as 1.0, and any price greater than the total break even unit price of the chips in the bundle, as 0.0. 
 * For any other price between the two, she follows a linear downward function. However, regarding the quantity she checks per chip, so that if the quantity of the chip is equal to what she wished, it gets 1.0. 
 * She may tolerate a deviation around this desired quantity. Any quantity outside this range would be evaluated as 0.0. The quantities within this range would be evaluated via a linear downward function and linear 
 * upward function respectively for quantities greater than or less than the desired quantity, both with a peak at the desired quantity.     
 * 
 * UF_peaked ignores any color which is not introduced in wishlist (i.e., free disposal) and takes the following values as input: 
 * \phi_c, \kappa_c, \sigma_c, and \lambda_c, respectively the break even unit price, the exact quantity, the acceptable deviation around \kappa_c, 
 * the importance of the chip with color c, 
 * p_c, q_c, \lambda_q, and \lambda_p, respectively the unit price, quantity of the chip with color c and the importance of quantity and price in offer \omega
 * 
 * And outputs u(\omega), where \omega is the offer to evaluate.
 *  
 * 
 *  Equation (1):
 *  
 *  UF_Peacked(Bundle \omega) = \lambda_p [ ( \sum{\lambda_c * p_c * q_c} - \sum{\lambda_c * \phi_c * \kappa_c} ) / \sum{\lambda_c * \phi_c * \kappa_c} - 1 ]
 *  					 + \lambda_q [ (-1)^\psi_c * (q_c - \kappa_c) / \sigma_c - 1 ]
 *  
 *  where,
 *  \psi_c = 1, if q_c >= \kappa_c, otherwise, \psi_c = 0.
 *  \lambda_p + \lambda_q = 1
 *  \sum{\lambda_c} = 1 
 *  
 * 
 * 
 * Reference:
 * [1] T. Baarslag and F. Nassiri-Mofakham, `Bargaining Chips: A Test-bed for Multi-Deal One-to-Many Negotiations', AAMAS'20, May 2020, Auckland, New Zeland. 
 * 
 *  
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public class UF_Peaked implements UtilityFunction {

	private WishList wishlist; //q_c 
	private ChipMappingType<Integer> qtyDeviation; // sigma_c. Assuming a symmetric deviation. Of course, two different deviation could also be considered for quantities less or more than the exact qty per chip
	private ChipMappingType<Double> breakEvenPrice, lambdaC; // kappa_c and lambda_c
	private double lambdaP,lambdaQ; // lambda_p and lambda_q


	public UF_Peaked(WishList w, ChipMappingType<Integer> qtyDev, ChipMappingType<Double> bEP, ChipMappingType<Double> lc, double lp, double lq)
	{
		wishlist=w;
		qtyDeviation=qtyDev;
		breakEvenPrice=bEP;
		lambdaC=lc;
		lambdaP=lp;
		lambdaQ=lq;
	}


	@Override
	public Double getUtility(Bundle b) 
	{
		// TODO Auto-generated method stub

		double sumWeightedPriceOffered=0.0;
		double sumWeightedPriceDesired=0.0;
		double sumWeightedQty=0.0;

		if (b!=null)
		{			
			for (Chip c : wishlist)
			{
				int desiredQ = wishlist.getQuantity(c);
				Double desiredP= breakEvenPrice.getUnitValue(c);
				Double importanceC= lambdaC.getUnitValue(c);
				int devC= qtyDeviation.getUnitValue(c);


				for (Stack s : b)
				{
					Integer offeredQ = b.getQuantity(c);
					Double offeredP= b.getUnitPrice(c);

					int sign = ((offeredQ >= desiredQ) ? 1 : 0);

					if (s.getChip().equals(c) && offeredQ != null)
					{
						sumWeightedPriceOffered += importanceC * offeredP * offeredQ;
						sumWeightedPriceDesired += importanceC * desiredP * desiredQ;						
						sumWeightedQty += importanceC * (Math.pow(-1,sign)*(offeredQ - desiredQ)/devC - 1);
					}
				}
			}
			// return lambdaP * (1- (sumWeightedPriceOffered-sumWeightedPriceDesired)/sumWeightedPriceDesired) + lambdaQ * sumWeightedQty;  
			return lambdaP * ((sumWeightedPriceOffered-sumWeightedPriceDesired)/sumWeightedPriceDesired-1) + lambdaQ * sumWeightedQty;  	//after changing the signs of sums  
		}
		return 0.0;
	}


	@Override
	public String toString()
	{
		return this.getClass().getSimpleName();
	}
}