package bargainingchips.players;

import java.util.List;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import bargainingchips.NegotiationContext;
import bargainingchips.actions.Offer;
import bargainingchips.actions.OfferBy;
import bargainingchips.outcomes.Outcome;
import bargainingchips.utilityfunctions.UtilityFunction;
/**
 * An agent typically has a buyer or seller role and can send an receive offers.
 * An agent has a utility function for evaluating any proposal in a given negotiation context. 
 */
public abstract class Agent implements Runnable
{
	protected String name;
	protected UtilityFunction u;
	protected NegotiationContext negotiationContext; 
	
	int k;
	
	public Agent(String name, UtilityFunction u, NegotiationContext negotiationContext,
			BlockingQueue<Offer> in, BlockingQueue<OfferBy> out,
			BlockingQueue<CoordinationMessage> cin, BlockingQueue<StatusMessage> cout) 
	{
		this.name = name;
		this.u = u;
		this.negotiationContext = negotiationContext;
		this.in = in;
		this.out = out;
		this.cin  = cin;
		this.cout = cout;
		k = 0;
	}
	
	// Messaging from and to the opponent
	protected BlockingQueue<Offer> in;
	protected BlockingQueue<OfferBy> out;
	// Messaging from and to the coordinator
	protected BlockingQueue<CoordinationMessage> cin;
	protected BlockingQueue<StatusMessage> cout;
	
	protected abstract void  receiveOffer(Offer bundle);
	
	protected abstract void  receiveOutcome(Outcome outcome);
	
	protected abstract Offer sendOffer();
	
	protected abstract Offer sendOpeningOffer();
	
	protected abstract void  receiveCoordinationMessage(CoordinationMessage cpoll); 
	
	protected abstract StatusMessage sendStatusMessage();
	
	@Override
	public void run() 
	{
		// A buyer may send an opening offer
		Offer opening = do_sendOpeningOffer();
		if (opening != null)
			try {
				out.put(new OfferBy(name, opening));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		while (true)
		{
			try 
			{
				/* Wait for the messages from coordinator or an incoming offer from seller.
				 * There may be multiple messages waiting. The agent should process them all.
				 * The agent can choose itself how to handle multiple messages (e.g. only
				 * use the last one).
				 */
				while (true)
				{
					List<CoordinationMessage> coordinationMessages = new ArrayList<CoordinationMessage>();
					int messageNo = cin.drainTo(coordinationMessages);
					
					if (messageNo > 0)
					{
						for (CoordinationMessage cm: coordinationMessages)
							do_receiveCoordinationMessage(cm);
						break;
					}
					
					List<Offer> offers = new ArrayList<Offer>();
					int offersNo = in.drainTo(offers);
					
					if (offersNo > 0)
					{
						for (Offer o: offers)
							if (o.isEnd())
							{
								do_receiveOutcome((Outcome) o);		// the offer codes that the negotiation ended in an outcome
								return;
							}
							else
								do_receiveOffer(o);
						break;
					}
				}
				
				// A sync happened, so we can send out a new offer, given the new information
				Offer sendOffer = do_sendOffer();
				out.put(new OfferBy(name, sendOffer));
				
				// Pause
//				Thread.sleep(500);
				Thread.sleep(500 + (int)(Math.random() * 1000));
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void do_receiveOutcome(Outcome o) 
	{
		System.out.println(this + " received a message that an outcome was reached: " + o);
		receiveOutcome(o);		
	}

	private void do_receiveCoordinationMessage(CoordinationMessage cpoll) 
	{
		System.out.println(this + " received coordination msg " + cpoll);
		receiveCoordinationMessage(cpoll);
	}

	protected void do_receiveOffer(Offer o) 
	{
		System.out.println(this + " received " + o 
				+ (o.isBid() ? (", with util = " + u.getUtility(o.getBundle())) : "") 
				);
		receiveOffer(o);		
	}

	protected Offer do_sendOffer()
	{
		Offer o = sendOffer();
		System.out.println(this + " sends " + o);
		k++;		
		return o;
	}
	
	protected Offer do_sendOpeningOffer() 
	{
		Offer o = sendOpeningOffer();
		if (o != null)
		{
			System.out.println(this + " sends opening offer " + o);
			k++;
		}
		return o;
	}

	@Override
	public String toString() 
	{
		return "#" + k + ": " + name;
	}
	
	public String toDescription() 
	{
		return name + " with u = " + u;
	}
}
