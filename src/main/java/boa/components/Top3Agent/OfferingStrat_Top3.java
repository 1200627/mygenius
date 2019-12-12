package boa.components.Top3Agent;


import java.util.List;
import java.util.Random;

import genius.core.Bid; 
import genius.core.bidding.BidDetails;
import genius.core.boaframework.OfferingStrategy;
import genius.core.uncertainty.UserModel;

/**
 * Offering Strategy of Top 3 Agent which
 * UNDER UNCERTAINTY: Offers one of the 3 best bids in the user model at random.
 * OTHERWISE: Offers the best bid in the domain.
 * @author Adel Magra
*/

public class OfferingStrat_Top3 extends OfferingStrategy{
	
	public BidDetails determineOpeningBid(){
		return determineNextBid();
	}
	
	/**
	 * Finds the best three bid and offers one at random. If it doesn't find them, it offers a random bid.
	 * @return the next bid to offer
	 */
	public BidDetails determineNextBid() {	
		
		/**
		 * Uncertainty case
		 */
		UserModel userModel = negotiationSession.getUserModel();
		if (userModel != null) {
			List<Bid> bidOrder = userModel.getBidRanking().getBidOrder();
			
			//In case ranking <= 3, the min bid is in top 3 and we clearly don't want to offer it.
			//So we just offer the best bid.
			if(bidOrder.size()<=3)
				return new BidDetails(userModel.getBidRanking().getMaximalBid(),userModel.getBidRanking().getHighUtility());
			
			Bid[] best3 = {bidOrder.get(bidOrder.size()-1),bidOrder.get(bidOrder.size()-2),bidOrder.get(bidOrder.size()-3)};
			Random generator = new Random();
			int randomIndex = generator.nextInt(best3.length);
			double scaleFactor = (bidOrder.size()-randomIndex)/bidOrder.size();
			return new BidDetails(best3[randomIndex],userModel.getBidRanking().getHighUtility()*scaleFactor);
		}
		
		/**
		 * Normal utility case.
		 */
		return negotiationSession.getMaxBidinDomain();
	}
	
	public String getName() {
		return "O_S Top3";
	}
	
	
	
}
