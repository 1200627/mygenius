package bargainingchips;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a function from Chips to Double, Integer, etc;
 * such as quantity, price bounds, etc.
 */
public class ChipMappingType<T> implements Iterable<Chip>
{
	protected Map<Chip, T> f;
	
	public ChipMappingType() 
	{
		f = new HashMap<Chip, T>();
	}
	
	public T getUnitValue(Chip c)
	{
		return f.get(c);
	}

	@Override
	public Iterator<Chip> iterator() 
	{
		return f.keySet().iterator();
	}

	@Override
	public String toString() 
	{
		return f.toString();
	}

}
