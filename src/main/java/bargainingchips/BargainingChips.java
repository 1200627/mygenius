package bargainingchips;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import bargainingchips.actions.Offer;
import bargainingchips.actions.OfferBy;
import bargainingchips.players.Agent;
import bargainingchips.players.BoulwareAgent;
import bargainingchips.players.CoordinationMessage;
import bargainingchips.players.Coordinator;
import bargainingchips.players.StatusMessage;
import bargainingchips.protocol.AsynchronousOffersProtocol;
import bargainingchips.utilityfunctions.UF_CloseToQuantity;
import bargainingchips.utilityfunctions.UtilityFunction;

/**
 * This main class showcases the fundamental concepts in the Bargaining Chips game.
 * 
 * Bargaining Chips is played by a buyer who seeks to acquire a number of chips for a good price. 
 * For example, the wish list of the buyer may consist of 2 red chips and 1 blue chip. 
 * Chips represents arbitrary indivisible items, such as products or tasks and are differentiated 
 * from others by a unique color.
 * 
 * Bargaining Chips is played using an asynchronous offer protocol for each bilateral negotiation thread. 
 * Multiple deals in simultaneous threads of one-to-many negotiation need to be coordinated; therefore, 
 * the buyer is equipped with two modules, one coordinator and multiple negotiators one per each thread.
 * 
 * Each of the individual negotiations is itself a bilateral negotiation over multiple items and multiple 
 * issues, i.e. a multi-issue multi-item thread. As each thread could reach a deal, the whole negotiation 
 * could reach multiple deals. 
 *  
 * Negotiating in this setting needs some coordination efforts to synchronize threads according to the 
 * progress of each individual negotiation as well as the multiple deals compared with the party's preference.
 *   
 * Bargaining Chips is a testbed for evaluating agents in such settings.
 *
 */
public class BargainingChips 
{
	public static void main(String[] args) throws InterruptedException 
	{
		// Set up the protocol
		BlockingQueue<OfferBy> from  = new LinkedBlockingQueue<OfferBy>();
		BlockingQueue<Offer> toBuyer = new LinkedBlockingQueue<Offer>();
		BlockingQueue<Offer> toSeller = new LinkedBlockingQueue<Offer>();
		
		String nameBuyer = "Bob 1";
		String nameSeller = "Sam";
		
		AsynchronousOffersProtocol aop = new AsynchronousOffersProtocol(from, nameBuyer, toBuyer, nameSeller, toSeller);



		
		BlockingQueue<CoordinationMessage>  cin  = new LinkedBlockingQueue<CoordinationMessage>();
		BlockingQueue<StatusMessage> 		cout = new LinkedBlockingQueue<StatusMessage>();
		
		NegotiationContext context = new NegotiationContext();
		
		// Bob
		WishList wishlist 		= new WishListBuilder().addWish("Green", 2).build();		// Bob wishes for 2 Green chips
		UtilityFunction u 		= new UF_CloseToQuantity(wishlist);
		Agent bob1 				= new BoulwareAgent(nameBuyer, u, context, toBuyer, from, cin, cout);
		
		// Coordinator
		Coordinator c		 	= new Coordinator(wishlist, cout, cin);
		
		// Sam
		WishList wishlistSam 	= new WishListBuilder().addWish("Green", 10).build(); 		// Sam wishes for 10 Green chips
		UtilityFunction uSam 	= new UF_CloseToQuantity(wishlistSam);
		Agent sam 				= new BoulwareAgent(nameSeller, uSam, context, toSeller, from);
		
		// Start threads
		System.out.println(bob1.toDescription());
		System.out.println("playing vs");
		System.out.println(sam.toDescription());
		
		Thread aopThread = new Thread(aop);
		aopThread.start();
		
		Thread threadBuyer = new Thread(bob1);
		threadBuyer.start();
		
		Thread threadSeller = new Thread(sam);
		threadSeller.start();
		
		Thread threadCoordinator = new Thread(c);
		threadCoordinator.start();
	}

}
