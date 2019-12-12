package genius.core.uncertainty;

import genius.core.Bid;
import genius.core.BidIterator;
import genius.core.Domain;
import genius.core.DomainImpl;
import genius.core.bidding.BidDetails;
import genius.core.utility.AdditiveUtilitySpace;
import genius.core.utility.UncertainAdditiveUtilitySpace;
import genius.core.boaframework.SortedOutcomeSpace;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import agents.anac.y2019.harddealer.math3.stat.correlation.SpearmansCorrelation;
/**
 * This is the class in which we will run the simulations on the estimateUtilitySpace functions present in EstimateUtilityLibrary.
 * @author Adel Magra
 */

public class EstimateSimulations {
	
	static String PARTY = "src/test/resources/partydomain/";
	
	/**
	 * Computes the sum of squared error for two utility functions defined on the same domain.
	 * @param estimate: The utility space to compare.
	 * @param baseline: The true utility space be compared to (NOTE: the order of inputs has no importance).
	 * @return squared error of the the utility functions.
	 */
	public static double computeSquaredError(AdditiveUtilitySpace estimate, AdditiveUtilitySpace baseline) throws Exception{
		 //First make sure that estimate and baseline have the same domain.
		if(!estimate.getDomain().equals(baseline.getDomain()))
			 throw new IllegalArgumentException("The utility spaces are not defined on the same domain");
		
		Domain domain = baseline.getDomain();
		long nrOfBids = domain.getNumberOfPossibleBids();
		BidIterator bidIterator = new BidIterator(domain);
		double error = 0;
		while(bidIterator.hasNext()){
			Bid bid = bidIterator.next();
			error += Math.pow(estimate.getUtility(bid)-baseline.getUtility(bid),2);
		}
		return (error/nrOfBids); 
	}
	
	/**
	 * Orders the bids in an array according to their true utilities .
	 * @param utilSpace
	 * @return utilities of the bids of toRank in the order of the true ranking of bids.
	 * @throws Exception 
	 */
	public static double[] estimate_to_rank(AdditiveUtilitySpace baseline, AdditiveUtilitySpace estimate) throws Exception {
		Domain domain = baseline.getDomain();
		SortedOutcomeSpace sortedSpace = new SortedOutcomeSpace(baseline);
		List<BidDetails> sortedBids = sortedSpace.getOrderedList();
		Collections.reverse(sortedBids);
		double[] full_rank = new double[(int) domain.getNumberOfPossibleBids()];
		for(int i=0; i<full_rank.length; i++){
			full_rank[i]= estimate.getUtility(sortedBids.get(i).getBid());
		}
		return full_rank;
	}
	
	
	/**
	 * Converts utilities in a ranked array.
	 */
	public static double[] true_to_rank(AdditiveUtilitySpace baseline){
		Domain domain = baseline.getDomain();
		BidIterator bidIterator = new BidIterator(domain);
		double[] full_rank = new double[(int) domain.getNumberOfPossibleBids()];
		for(int i=0; i<full_rank.length; i++)
			full_rank[i]=baseline.getUtility(bidIterator.next());
		Arrays.sort(full_rank);
		return full_rank;
	}
	
	
	/**
	 * The main function were the simulations are ran.
	 * @param args
	 * @throws Exception 
	 */
	public static void main (String[] args) throws Exception {
		DomainImpl domain = new DomainImpl(PARTY + "party_domain.xml");
		UncertainAdditiveUtilitySpace utilSpace = new UncertainAdditiveUtilitySpace(domain,
				PARTY + "party1_utility.xml");
		UncertainPreferenceContainer u = new UncertainPreferenceContainer(utilSpace, UNCERTAINTYTYPE.PAIRWISECOMP, 4);
		UserModel userModel = u.getPairwiseCompUserModel();
		BidRanking bidRank = userModel.getBidRanking();
		System.out.println(bidRank.toString());
		System.out.println(bidRank.getTotalVarDistance());
		Bid maximizer = bidRank.getTVMaximizer();
		System.out.println(maximizer.toString());


		/**
		 * Un-comment to run tests based on squared error
		 */
		/*//Store the error on estimations of KS and SAGA in two arrays, where i^th index corresponds to an estimation from a bidRank of size i.
				/*double[] kS_Error = new double[47];
				double[] saga_Error = new double[47];
				for(int i=3; i<50; i++) {
					UncertainPreferenceContainer u = new UncertainPreferenceContainer(utilSpace, UNCERTAINTYTYPE.PAIRWISECOMP, i);
					UserModel userModel = u.getPairwiseCompUserModel();
					BidRanking bidRank = userModel.getBidRanking();
					EstimateUtilityLibrary library = new EstimateUtilityLibrary(domain, bidRank);
					AdditiveUtilitySpace estimate_ks = library.kS_Movement();
					AdditiveUtilitySpace estimate_saga = (AdditiveUtilitySpace) library.fitnessCross_SAGA();
					kS_Error[i-3] = computeSquaredError(estimate_ks,utilSpace);
					saga_Error[i-3] = computeSquaredError(estimate_saga,utilSpace);
				}
				
				System.out.println("KS:");
				for(int i = 0 ; i<47; i++) 
					System.out.println(kS_Error[i]);
				System.out.println("SAGA:");
				for(int i = 0 ; i<47; i++)
					System.out.println(saga_Error[i]);
		*/
		
		/**
		 * Uncomment to to run tests based on spearman coefficient.
		 */
		
		//Store the spearman coefficient on estimations of KS and SAGA in two arrays, where i^th index corresponds to an spearman from a bidRank of size i.
		/*double[] true_rank = true_to_rank(utilSpace);
		SpearmansCorrelation spearman_helper = new SpearmansCorrelation();
	 	double[] kS_Spearman = new double[50];
		double[] saga_Spearman = new double[50];
		for(int i=3; i<53; i++) {
			UncertainPreferenceContainer u = new UncertainPreferenceContainer(utilSpace, UNCERTAINTYTYPE.PAIRWISECOMP, i);
			UserModel userModel = u.getPairwiseCompUserModel();
			BidRanking bidRank = userModel.getBidRanking();
			EstimateUtilityLibrary library = new EstimateUtilityLibrary(domain, bidRank);
			AdditiveUtilitySpace estimate_ks = library.kS_Movement();
			AdditiveUtilitySpace estimate_saga = (AdditiveUtilitySpace) library.fitnessCross_SAGA();
			double[] kS_rank = estimate_to_rank(utilSpace,estimate_ks);
			double[] saga_rank = estimate_to_rank(utilSpace,estimate_saga);
			kS_Spearman[i-3] = spearman_helper.correlation(true_rank,kS_rank);
			saga_Spearman[i-3] = spearman_helper.correlation(true_rank,saga_rank);
		}
		
		System.out.println("KS:");
		for(int i = 0 ; i<50; i++) 
			System.out.println(kS_Spearman[i]);
		System.out.println("SAGA:");
		for(int i = 0 ; i<50; i++)
			System.out.println(saga_Spearman[i]);
		*/
		
		/**
		 * Basic comparaison based on squared Error.
		 */
		/*UncertainPreferenceContainer u = new UncertainPreferenceContainer(utilSpace, UNCERTAINTYTYPE.PAIRWISECOMP, 10);
		UserModel userModel = u.getPairwiseCompUserModel();
		BidRanking bidRank = userModel.getBidRanking();
		EstimateUtilityLibrary library = new EstimateUtilityLibrary(domain, bidRank);
		AdditiveUtilitySpace estimate_ks = library.kS_Movement();
		AdditiveUtilitySpace estimate_saga = (AdditiveUtilitySpace) library.fitnessCross_SAGA();
		for (int i=0; i<bidRank.getSize(); i++) {
			Bid bid = bidRank.getBidOrder().get(i);
			System.out.println("estimate_KS: " + estimate_ks.getUtility(bid) + " VS true: " + utilSpace.getUtility(bid) + " VS estimate_SAGA: " + estimate_saga.getUtility(bid));
		}
		System.out.println("Error_KS: " + computeSquaredError(estimate_ks,utilSpace));
		System.out.println("Error_SAGA: " + computeSquaredError(estimate_saga,utilSpace));
		*/
	
	}
		
}
