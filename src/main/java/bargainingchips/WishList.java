package bargainingchips;

import java.util.Iterator;
import java.util.Map;

/**
 * A wish list is a list of chips with their quantitites.
 */
public class WishList extends ChipMapping<Integer> implements Iterable<Chip>
{
	public WishList(Map<Chip, Integer> f) 
	{
		this.f = f;
	}
	
	public int getQuantity(Chip c)
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

	public int size() 
	{
		return f.size();
	}
}
