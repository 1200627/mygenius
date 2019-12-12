package bargainingchips.actions;

/**
 * An offer by a certain entity (Agent) with unique id.
 * 
 * Immutable.
 */
public class OfferBy
{
	private final String sender;
	private final Offer o;

	public OfferBy(String id, Offer o)
	{
		this.o = o;
		this.sender = id;
	}
	
	/**
	 * Just a sample example of an offer by a sender
	 */
	public static OfferBy getSampleOffer(String sender, String color, int qty)
	{
		return new OfferBy(sender, Offer.getSampleOffer(color, qty));
	}
	
	public String getSender() 
	{
		return sender;
	}
	
	public Offer getOffer() 
	{
		return o;
	}
	
	@Override
	public String toString() 
	{
		return o.toString() + " by: " + sender + "";
	}
}
