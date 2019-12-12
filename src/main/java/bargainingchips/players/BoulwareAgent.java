package bargainingchips.players;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import bargainingchips.NegotiationContext;
import bargainingchips.actions.Offer;
import bargainingchips.actions.OfferBy;
import bargainingchips.utilityfunctions.UtilityFunction;

public class BoulwareAgent extends TimeDependentNegotiationAgent 
{
	public BoulwareAgent(String name, UtilityFunction u, NegotiationContext nc,
			BlockingQueue<Offer> in, BlockingQueue<OfferBy> out,
			BlockingQueue<CoordinationMessage> cin,
			BlockingQueue<StatusMessage> cout) 
	{
		super(name, u, nc, in, out, cin, cout);
	}
	
	/**
	 * For sellers dummy coordinator queues are created but are never linked up
	 */
	public BoulwareAgent(String name, UtilityFunction u, NegotiationContext nc, 
			BlockingQueue<Offer> in, BlockingQueue<OfferBy> out) 
	{
		super(name, u, nc, in, out, 
				new LinkedBlockingQueue<CoordinationMessage>(),
				new LinkedBlockingQueue<StatusMessage>());
	}

	@Override
	public double getE() {
		return 0.2;
	}
}
