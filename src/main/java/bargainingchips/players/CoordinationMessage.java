package bargainingchips.players;

import bargainingchips.utilityfunctions.UtilityFunction;

/**
 * A message from the Coordinator to the buying subnegotiators; specifying e.g. their utility function.
 * 
 * Should be immutable.
 */
public class CoordinationMessage 
{
	private final UtilityFunction f;
	
	public CoordinationMessage(UtilityFunction f) 
	{
		this.f = f;
	}
	
	@Override
	public String toString() 
	{
		return f.toString();
	}
	
	public UtilityFunction getUtilityFunction() 
	{
		return f;
	}
}
