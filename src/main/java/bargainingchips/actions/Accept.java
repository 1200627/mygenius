package bargainingchips.actions;

import bargainingchips.Bundle;

public class Accept extends Offer
{
	public Accept(Offer agreement)
	{
		super(agreement.getBundle(), OfferType.ACCEPT);
	}
	
	public Bundle getAgreement()
	{
		return getBundle();
	}
	
	@Override
	public String toString() 
	{
		return "Accept: " + getBundle();
	}
}
