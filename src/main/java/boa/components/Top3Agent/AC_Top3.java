package boa.components.Top3Agent;

import genius.core.Bid;
import genius.core.boaframework.AcceptanceStrategy;
import genius.core.boaframework.Actions;
import genius.core.uncertainty.User;
import genius.core.uncertainty.UserModel;
import java.util.List;

/**
 * Acceptance strategy of the Top 3 Agent which
 * UNDER UNCERTAINTY: accepts only if it receives an offer in the top 3 bids available OR Total bother > 0.2.
 * The agent also elicits the received bid of the opponent at every round.
 * OTHERWISE: accepts only if it receives the highest bid.
 * @author: Adel Magra
 */

public class AC_Top3 extends AcceptanceStrategy {
	
	/**
	 * Accepts: if the received bid is one of the top 3 in the current user model augmented with it.
	 * OR the total bother inflicted to the user is greater than 0.2
	 */
	
	public Actions determineAcceptability() {
		
		Bid receivedBid = negotiationSession.getOpponentBidHistory()
				.getLastBid();
		if (receivedBid == null) {
			return Actions.Reject;
		}
		UserModel userModel = negotiationSession.getUserModel();
		
		/**
		 * Uncertainty case
		 */
		if (userModel != null) {
			User user = negotiationSession.getUser();
			
			//Accepts if bother is too large
			if(user.getTotalBother() > 0.2)
				return Actions.Accept;
			
			List<Bid> bidOrder = userModel.getBidRanking().getBidOrder();
			
			//Elicits the received bid
			userModel = user.elicitRank(receivedBid,userModel);
			bidOrder = userModel.getBidRanking().getBidOrder();
			int rankOfBid = bidOrder.indexOf(receivedBid);
			
			//Accepts if receivedBid is one of the top 3 bids in the user model
			if (rankOfBid > bidOrder.size()-4){
				return Actions.Accept;
			}
		}
		
		/**
		 * Normal Utility Space
		 */
		else if(receivedBid.equals(negotiationSession.getMaxBidinDomain().getBid()))
				 return Actions.Accept;
		
		return Actions.Reject;
	}
	
	public String getName() {
		return "AC_Top3";
	}

	
	
}
