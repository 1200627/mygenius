package bargainingchips.players;

import java.util.concurrent.BlockingQueue;

import bargainingchips.WishList;

/**
 * The coordinator monitors and synchronizes all bilateral negotiation threads.
 */
public class Coordinator implements Runnable
{
	// Messages coming in and out of the coordinator
	protected BlockingQueue<StatusMessage> cin;
	protected BlockingQueue<CoordinationMessage> cout;
	/** The fixed, initial wish list that should be obtained overall */
	private WishList overallWishList;
	
	public Coordinator(WishList wishlist, BlockingQueue<StatusMessage> negotiationMsg,
			BlockingQueue<CoordinationMessage> coordinationMsg) 
	{
		this.overallWishList = wishlist;
		cin = negotiationMsg;
		cout = coordinationMsg;
	}

	@Override
	public void run() 
	{
		// Send a very simple coordination message to Bob_i after a while
		try {
			Thread.sleep(2000);
			
//			CoordinationMessage coordinationMessage2 = new CoordinationMessage();
//			coordinationMessage2.f = new UF_IWantColorAndQuantity("Green", 7);
//			cout.put(coordinationMessage2);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
