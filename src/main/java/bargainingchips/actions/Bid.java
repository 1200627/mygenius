package bargainingchips.actions;

import bargainingchips.Bundle;

public class Bid extends Offer
{
	/**
	 * Creates a bid. Bundle is immutable and therefore so is Offer.
	 */
	public Bid(Bundle b)
	{
		super(b, OfferType.BID);
	}
	
	@Override
	public String toString() 
	{
		return "Bid: " + getBundle();
	}
}
