package bargainingchips.protocol;

import bargainingchips.actions.Offer;
import bargainingchips.outcomes.Outcome;

/**
 * An offer may be valid or invalid.
 * As a secondary result, the may also lead to an outcome with offer type END, namely:
 * 
 * 1. An agreement
 * 2. A disagreement
 * 
 * Otherwise, the negotiation is ongoing and the outcome is null.
 */
public class ValidationResult
{
	private final boolean valid;
	private final Outcome outcome;
	
	public ValidationResult() 
	{
		valid = false;
		outcome = null;
	}

	public ValidationResult(boolean valid, Outcome outcome) 
	{
		this.valid = valid;
		this.outcome = outcome;
	}	
	
	public boolean isValid()
	{
		return valid;
	}
	
	public boolean hasEnded()
	{
		return outcome != null;
	}
	
	public Offer getOutcome()
	{
		return outcome;
	}
}