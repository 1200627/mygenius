package bargainingchips.protocol;

import bargainingchips.outcomes.Disagreement;

/**
 * The offer was valid but the negotiation has ended WITHOUT a deal
 */
public class ValidationSuccessWithDisagreement extends ValidationResult 
{
	public ValidationSuccessWithDisagreement() 
	{
		super(true, new Disagreement());
	}
}
