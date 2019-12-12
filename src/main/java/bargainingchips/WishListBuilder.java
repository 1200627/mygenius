package bargainingchips;

import java.util.HashMap;
import java.util.Map;

/**
 * Can be used to build bundles easily.
 */
public class WishListBuilder
{
	private Map<Chip, Integer> f;
	
	/**
	 * Makes sure bundle remains unmodifiable.
	 */
	public WishListBuilder()
	{
		f = new HashMap<Chip, Integer>();
    }

	/**
	 * Add a wish to the wish list, such as 3 x Green 
	 */
	public WishListBuilder addWish(String c, int q)
	{
		f.put(new Chip(c), q);
		return this;
	}
	
	/**
	 * @return Immutable bundle
	 */
	public WishList build()
	{
		return new WishList(f);
	}

}
