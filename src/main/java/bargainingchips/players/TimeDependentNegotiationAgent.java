package bargainingchips.players;

import static java.lang.Math.pow;

import java.util.concurrent.BlockingQueue;

import bargainingchips.Bundle;
import bargainingchips.NegotiationContext;
import bargainingchips.OutcomeSpace;
import bargainingchips.actions.Accept;
import bargainingchips.actions.Bid;
import bargainingchips.actions.Offer;
import bargainingchips.actions.OfferBy;
import bargainingchips.outcomes.Outcome;
import bargainingchips.utilityfunctions.UtilityFunction;
import genius.core.protocol.MultilateralProtocol;

/**
 * Boulware/Conceder tactics, by Tim Baarslag, adapted from [1].
 *
 * [1] S. Shaheen Fatima Michael Wooldridge Nicholas R. Jennings Optimal
 * Negotiation Strategies for Agents with Incomplete Information
 * http://eprints.ecs.soton.ac.uk/6151/1/atal01.pdf
 */
public abstract class TimeDependentNegotiationAgent extends Agent 
{
	private Offer lastReceivedOffer = null;
	
	public TimeDependentNegotiationAgent(String name, UtilityFunction u, NegotiationContext nc,
			BlockingQueue<Offer> in,
			BlockingQueue<OfferBy> out,
			BlockingQueue<CoordinationMessage> cin,
			BlockingQueue<StatusMessage> cout) {
		super(name, u, nc, in, out, cin, cout);
	}

	/**
	 * When this class is called, it is expected that the Party chooses one of
	 * the actions from the possible action list and returns an instance of the
	 * chosen action. This class is only called if this
	 * {@link genius.core.parties.NegotiationParty} is in the
	 * {@link MultilateralProtocol#getRoundStructure(java.util.List, negotiator.session.Session)}
	 * .
	 *
	 * @param possibleActions
	 *            List of all actions possible.
	 * @return The chosen action
	 */
	@Override
	protected Offer sendOffer()
	{
		// Nothing received yet
		if (lastReceivedOffer == null)
			return new Bid(getNextBid());
		
		// Our last bid got accepted. We are also accepting (and we should notify the coordinator).
		if (lastReceivedOffer.isAccept())			
			return new Accept(lastReceivedOffer);
		
		Bundle lastReceivedBid = lastReceivedOffer.getBundle();
		Bundle nextBid = getNextBid();
		
		double lastUtil = lastReceivedBid != null ? u.getUtility(lastReceivedBid) : 0;
		double nextUtil = nextBid != null ? u.getUtility(nextBid) : 0;

		// Accept
		if (nextUtil <= lastUtil)
			return new Accept(lastReceivedOffer);
		// Counter offer based actions
		else 
			return new Bid(nextBid);
	}

	/**
	 * Get the next bid we should do
	 */
	protected Bundle getNextBid() 
	{
		OutcomeSpace outcomeSpace = negotiationContext.getOutcomeSpace();
		return outcomeSpace.getBidNearUtility(getTargetUtility(), u, this);
	}

	@Override
	protected void receiveOffer(Offer o) 
	{
		lastReceivedOffer = o;
	}
	
	@Override
	protected void receiveOutcome(Outcome outcome) 
	{
		System.out.println(this + " is done with outcome " + outcome + ". Should clean up now.");		
	}

	/**
	 * Gets the target utility for the next bid
	 *
	 * @return The target utility for the given time
	 */
	private double getTargetUtility() 
	{
		// timeline runs from 0.0 to 1.0
		int totalrounds = NegotiationContext.DEADLINE;
		double time = (double) k / totalrounds;
		double target = 1d - f(time);
//		System.out.println(this + ": t = " + time + ". Target util: " + target);
		return target;
	}

	/**
	 * From [1]:
	 *
	 * A wide range of time dependent functions can be defined by varying the
	 * way in which f(t) is computed. However, functions must ensure that 0 <=
	 * f(t) <= 1, f(0) = k, and f(1) = 1.
	 *
	 * That is, the offer will always be between the value range, at the
	 * beginning it will give the initial constant and when the deadline is
	 * reached, it will offer the reservation value.
	 *
	 * For e = 0 (special case), it will behave as a Hardliner.
	 */
	private double f(double t) {
		if (getE() == 0) {
			return 0;
		}
		return pow(t, 1 / getE());
	}

	/**
	 * Depending on the value of e, extreme sets show clearly different patterns
	 * of behaviour [1]:
	 *
	 * 1. Boulware: For this strategy e < 1 and the initial offer is maintained
	 * till time is almost exhausted, when the agent concedes up to its
	 * reservation value.
	 *
	 * 2. Conceder: For this strategy e > 1 and the agent goes to its
	 * reservation value very quickly.
	 *
	 * 3. When e = 1, the price is increased linearly.
	 *
	 * 4. When e = 0, the agent plays hardball.
	 */
	public abstract double getE();
	
	@Override
	protected Offer sendOpeningOffer() 
	{
		return sendOffer();
	}

	/**
	 * Messaging with the coordinator can happen below
	 */
	
	@Override
	protected void receiveCoordinationMessage(CoordinationMessage cpoll) 
	{
		// Update the utility function
		u = cpoll.getUtilityFunction();
	}
	
	@Override
	protected StatusMessage sendStatusMessage() 
	{
		return null;
	}
}
