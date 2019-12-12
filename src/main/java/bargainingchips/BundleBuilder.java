package bargainingchips;

import java.util.List;

import java.util.ArrayList;

/**
 * Can be used to build bundles easily.
 */
public class BundleBuilder
{
	private List<Stack> bundle;
	
	/**
	 * Makes sure bundle remains unmodifiable.
	 */
	public BundleBuilder()
	{
        this.bundle = new ArrayList<Stack>();
    }

	public BundleBuilder addStack(String c, double p, int q)
	{
		bundle.add(new Stack(c, p, q));
		return this;
	}
	
	/**
	 * @return Immutable bundle
	 */
	public Bundle build()
	{
		return new Bundle(bundle);
	}

}
