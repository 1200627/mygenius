package bargainingchips;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a function from Chips to Double;
 * such as quantity, price bounds, etc.
 */
public class ChipMapping<T>
{
	protected Map<Chip, T> f;
	
	public ChipMapping() 
	{
		f = new HashMap<Chip, T>();
	}

}
