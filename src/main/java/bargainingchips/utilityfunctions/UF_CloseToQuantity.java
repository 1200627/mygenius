package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;
import bargainingchips.BundleBuilder;
import bargainingchips.Chip;
import bargainingchips.WishList;
import bargainingchips.WishListBuilder;

/**
 * Utility function for testing that acts on queantity alone.
 * Returns 1 when the wish list is exactly satisfied. Becomes lower with higher quantity deviations.
 */
public class UF_CloseToQuantity implements UtilityFunction 
{
	private WishList wishlist;

	public UF_CloseToQuantity(WishList w)
	{
		this.wishlist = w;
	}


	@Override
	public Double getUtility(Bundle b) 
	{
		int totalDeviation = 0;
		for (Chip c : wishlist)
		{
			int desired = wishlist.getQuantity(c);
			Integer offered = b.getQuantity(c);
			if (offered == null)
				offered = 0;

			int deviationOffered = Math.abs(desired - offered);
			totalDeviation += deviationOffered;
		}

		return 1.0 / (1 + totalDeviation);
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + ": " + wishlist.toString();
	}
	
	public static void main(String[] args) 
	{
		WishList wishlist = new WishListBuilder().addWish("Red", 7).addWish("Green", 5).build();
		UF_CloseToQuantity u = new UF_CloseToQuantity(wishlist);
		System.out.println(u);
		
		Bundle offer = new BundleBuilder()
				.addStack("Red", 1.0, 6)
				.addStack("Green", 3.0, 15)
				.addStack("Purple", 0.10, 10)
				.build();
		System.out.println(u.getUtility(offer));
	}

}

