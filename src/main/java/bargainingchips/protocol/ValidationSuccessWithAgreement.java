package bargainingchips.protocol;

import bargainingchips.Bundle;
import bargainingchips.outcomes.Agreement;
/**
 * The offer was valid and the negotiation has ended with a deal
 */
public class ValidationSuccessWithAgreement extends ValidationResult 
{
	public ValidationSuccessWithAgreement(Bundle b) 
	{
		super(true, new Agreement(b));
	}
}
