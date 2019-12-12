package bargainingchips.protocol;
/**
 * The offer was NOT valid and the negotiation is still ongoing
 */
public class ValidationFailure extends ValidationResult 
{
	public ValidationFailure() 
	{
		super(false, null);
	}
}
