package bargainingchips.players;

import bargainingchips.Bundle;

/**
 * A message from the buying agents to the Coordinator
 * Should be immutable
 */
public class StatusMessage 
{
	private final StatusMessageType type;
	private final Bundle agreement;
	
	public StatusMessage(Bundle agreement, StatusMessageType type)
	{
		this.agreement = agreement;
		this.type = type;
	}
	
	public Bundle getAgreement() 
	{
		return agreement;
	}
	
	public StatusMessageType getType() 
	{
		return type;
	}

}
