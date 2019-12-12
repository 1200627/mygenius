package bargainingchips.actions;

import bargainingchips.Bundle;
import bargainingchips.BundleBuilder;

/**
 * An offer has two parts: (1) a bundle or null, (2) a message code: `bid', `accept', `breakoff', or `end'.
 * 
 * Immutable.
 */
public class Offer 
{
	/** 
	 * Message codes: 
	 * (1) `bid' [body contains a bundle], 
	 * (2) `accept' [agree with the deal (based on the rules); null body], 
	 * (3) `breakoff' [propose to quit the negotiation; null body]
	 * 
	 * Special:
	 * (4) `end' [poison message to signal the agents that the negotiation is over; 
	 *     null body for no agreement, bundle body for agreement]
	 */
	private final OfferType type; 
	private final Bundle bundle;
	
	/**
	 * Creates an offer. Bundle and OfferType are immutable and therefore so is Offer.
	 */
	public Offer(Bundle b, OfferType t)
	{
		bundle = b;
		type = t;
	}

	public Offer(Offer o)
	{
		this(o.bundle, o.type);
	}	

	/**
	 * @return the proposal
	 */
	public Bundle getBundle() 
	{
		return bundle;
	}
	
	@Override
	public String toString() 
	{
		if (bundle != null)
			return type.toString() + ": " + bundle.toString();
		else
			return type.toString();
	}
	
	public static Bid getSampleOffer(int qty)
	{
		return getSampleOffer("Red", qty);
	}
	
	/**
	 * Just a sample example of an offer
	 */
	public static Bid getSampleOffer(String color, int qty)
	{
		Bundle bundle = new BundleBuilder()
				.addStack(color, 7.0, qty)
				.build();
		return new Bid(bundle);
	}
	
	public boolean isBid() 
	{
		return type == OfferType.BID;
	}
	
	public boolean isBreakoff() 
	{
		return type == OfferType.BREAKOFF;
	}

	public boolean isAccept() 
	{
		return type == OfferType.ACCEPT;
	}
	
	public boolean isEnd() 
	{
		return type == OfferType.END;
	}
	
	public boolean isAgreement()
	{
	 return isEnd() && bundle != null;
	}
	
	public boolean isDisagreement()
	{
	 return isEnd() && bundle == null;
	}
}