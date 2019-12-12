package bargainingchips;

/**
 * Describes the context of the negotiation, such as deadlines, possible offers, etc.
 */
public class NegotiationContext 
{
	public static final int DEADLINE = 20;
	OutcomeSpace outcomeSpace;
	
	public NegotiationContext() 
	{
		outcomeSpace = new OutcomeSpace();
	}
	
	public OutcomeSpace getOutcomeSpace() 
	{
		return outcomeSpace;
	}

}
