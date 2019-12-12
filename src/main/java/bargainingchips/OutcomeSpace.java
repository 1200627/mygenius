package bargainingchips;

import java.util.List;

import java.util.ArrayList;

import bargainingchips.actions.Offer;
import bargainingchips.players.Agent;
import bargainingchips.utilityfunctions.UtilityFunction;

/**
 * This class represents the complete outcome space and is therefore useful if
 * someone wants to quickly implement an agent. Note that working with a sorted 
 * outcomespace class would be faster during the negotiation.
 */
public class OutcomeSpace 
{
	/** List of all possible bids in the domain */
	protected List<Bundle> allBids = new ArrayList<Bundle>();

	/**
	 * Creates a test outcome space.
	 */
	public OutcomeSpace() 
	{
		generateAllBids();
	}

	/**
	 * Generates all the possible bids in the domain
	 * 
	 * @param utilSpace
	 */
	private void generateAllBids() 
	{
		for (int g = 1; g < 10; g++)
			allBids.add(Offer.getSampleOffer("Green", g).getBundle());
	}
	
	/**
	 * gets a {@link Bundle} which is closest to the given utility
	 * 
	 * @param utility to which the found bid must be closest.
	 */
	public Bundle getBidNearUtility(double utility, UtilityFunction u, Agent a) 
	{
		int index = getIndexOfBidNearUtility(utility, u);
		Bundle bundle = allBids.get(index);
		System.out.println(a + ": the bid closest to target util " + utility + " is " + bundle + " (#" + index + ") with util " + u.getUtility(bundle));
		return bundle;
	}

	/**
	 * @return best bid in the domain.
	 */
	private Bundle getMaxBidPossible(UtilityFunction u) 
	{
		Bundle maxBid = allBids.get(0);
		for (Bundle bid : allBids) {
			if (u.getUtility(bid) > u.getUtility(maxBid)) {
				maxBid = bid;
			}
		}
		return maxBid;
	}

	/**
	 * @return worst bid in the domain.
	 */
	private Bundle getMinBidPossible(UtilityFunction u) {
		Bundle minBid = allBids.get(0);
		for (Bundle bid : allBids) {
			if (u.getUtility(bid) < u.getUtility(minBid)) {
				minBid = bid;
			}
		}
		return minBid;
	}

	/**
	 * @param utility
	 *            to which the found bid must be closest.
	 * @return index of the bid with the utility closest to the given utilty.
	 */
	private int getIndexOfBidNearUtility(double utility, UtilityFunction u) 
	{
		double closesDistance = 1;
		int best = 0;
		for (int i = 0; i < allBids.size(); i++) {
			if (Math.abs(u.getUtility(allBids.get(i)) - utility) < closesDistance) {
				closesDistance = Math.abs(u.getUtility(allBids.get(i)) - utility);
				best = i;
			}
		}
		return best;
	}

	@Override
	public String toString() {
		String all = "";
		for (Bundle b : allBids) {
			all += b.toString() + "\n,";
		}
		return "OutcomeSpace[" + all + "]";
	}
}