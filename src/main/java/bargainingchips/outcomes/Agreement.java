package bargainingchips.outcomes;

import bargainingchips.Bundle;

public class Agreement extends Outcome
{
	public Agreement(Bundle b)
	{
		super(b);
	}
	
	@Override
	public String toString() 
	{
		return "Agreement: " + getBundle();
	}
}
