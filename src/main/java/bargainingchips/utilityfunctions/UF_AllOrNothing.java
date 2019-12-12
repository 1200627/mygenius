package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;
import bargainingchips.BundleBuilder;
import bargainingchips.Chip;
import bargainingchips.WishList;
import bargainingchips.WishListBuilder;

/**
 * If all requested quantities at the total requested price (or less) would be satisfied, 
 * then the bundle is evaluated as 1.0, otherwise as 0.0;
 */
public class UF_AllOrNothing implements UtilityFunction 
{
	private WishList wishlist;
	private double maxprice;


	public UF_AllOrNothing(WishList w, double max)
	{
		this.wishlist = w;
		this.maxprice = max;
	}


	@Override
	public Double getUtility(Bundle b) 
	{
		if (b.getTotalPrice() > maxprice)
			return 0.0;

		for (Chip c : wishlist)
		{
			int desired = wishlist.getQuantity(c);
			Integer offered = b.getQuantity(c);

			if (offered == null || desired != offered)
				return 0.0;
		}

		return 1.0;
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + ": " + wishlist.toString() + " for <= $" + maxprice;
	}
	
	public static void main(String[] args) 
	{
		WishList wishlist = new WishListBuilder().addWish("Red", 3).addWish("Green", 2).build();
		UF_AllOrNothing u = new UF_AllOrNothing(wishlist, 10.0);
		System.out.println(u);
		
		Bundle offer = new BundleBuilder()
				.addStack("Red", 1.0, 3)
				.addStack("Green", 3.0, 2)
				.addStack("Purple", 0.10, 10)
				.build();
		System.out.println(u.getUtility(offer));
	}

}

