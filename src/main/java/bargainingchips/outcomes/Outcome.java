package bargainingchips.outcomes;

import bargainingchips.Bundle;
import bargainingchips.actions.Offer;
import bargainingchips.actions.OfferType;
/**
 * An outcome is the final result of a negotiation.
 * It is an {@link Offer} for convenience, so that Agents can easily pass it around.
 * The offer type is always END.
 */
public class Outcome extends Offer 
{
	public Outcome(Bundle outcome)
	{
		super(outcome, OfferType.END);
	}
}
