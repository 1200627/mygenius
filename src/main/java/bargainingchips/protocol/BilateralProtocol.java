package bargainingchips.protocol;

import java.util.List;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import bargainingchips.actions.Offer;
import bargainingchips.actions.OfferBy;

/**
 * A (possibly asynchronous) protocol between two agents A and B.
 * The protocol acts as the postman of delivering messages between the agents and validates their messages.
 * The protocol keeps the one official (synchronized) log of what has happened.
 */
public abstract class BilateralProtocol implements Runnable
{
	// Messaging to and from A and B
	protected final BlockingQueue<OfferBy> from;
	protected final String nameA;
	protected final BlockingQueue<Offer> toA;
	protected final String nameB;
	protected final BlockingQueue<Offer> toB;
		
	protected final List<OfferBy> log;
	
	public BilateralProtocol(BlockingQueue<OfferBy> from, String nameA,
			BlockingQueue<Offer> toA, String nameB, BlockingQueue<Offer> toB) {
		super();
		this.from = from;
		this.nameA = nameA;
		this.toA = toA;
		this.nameB = nameB;
		this.toB = toB;
		this.log = new ArrayList<OfferBy>();
	}

	protected abstract ValidationResult validate(OfferBy o);
	
	@Override
	public void run() 
	{
		// Wait for a message from A or B
		try {
			while (true)
			{
				OfferBy ob = from.poll();
				
				if (ob != null)
				{
					String sender = ob.getSender();
					Offer o = ob.getOffer();
					ValidationResult validationResult = validate(ob);
					
					if (validationResult.isValid())						// if the offer was valid
					{
						log(ob);										// make it official
						BlockingQueue<Offer> to = addressee(sender);	// put it in the right outbox
						to.put(o);
					}
					else // drop!
						System.err.println("Warning: " + ob + " was not a valid offer and was dropped. Log: " + log.toString());
					
					// The validation could result in a state where the negotiation ended. 
					// We have an outcome, we let both agents know and clean up
					if (validationResult.hasEnded())
					{
						Offer finalOutcome = validationResult.getOutcome();
						toA.put(finalOutcome);
						toB.put(finalOutcome);
						System.out.println("Protocol detected final outcome: " + finalOutcome + 
								". Notified agents and quitting.");
						return;
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gives the correct outbox for the sender
	 */
	private BlockingQueue<Offer> addressee(String sender) 
	{
		if (nameA.equals(sender))
			return toB;
		
		if (nameB.equals(sender))
			return toA;
		
		throw new IllegalStateException("Sender " + sender + " unknown! Should be either " + nameA + " or " + nameB);
	}

	private void log(OfferBy ob) 
	{
		log.add(ob);
		System.out.println("Logged and validated: " + ob);
	}
	
	protected OfferBy getLastOffer()
	{
		return log.get(log.size() - 1);
	}	

}
