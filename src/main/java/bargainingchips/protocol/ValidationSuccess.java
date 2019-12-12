package bargainingchips.protocol;
/**
 * The offer was valid and the negotiation is still ongoing
 */
public class ValidationSuccess extends ValidationResult 
{
	public ValidationSuccess() 
	{
		super(true, null);
	}
}
