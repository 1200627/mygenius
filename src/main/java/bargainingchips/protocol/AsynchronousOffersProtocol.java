package bargainingchips.protocol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import bargainingchips.actions.Accept;
import bargainingchips.actions.Offer;
import bargainingchips.actions.OfferBy;


public class AsynchronousOffersProtocol extends BilateralProtocol 
{
	public AsynchronousOffersProtocol(BlockingQueue<OfferBy> from, String nameA,
			BlockingQueue<Offer> toA, String nameB, BlockingQueue<Offer> toB) {
		super(from, nameA, toA, nameB, toB);
	}

	/**
	 * An offer is valid iff:
	 * 1) An offer or walk-away is sent by anyone at any time: $o_{t+1} \in \Omega \cup \{End\}$; or
     * 2) A bid is accepted by the other: $o_{t+1} = Accept$ and $o_{t}\in\Omega$ and $g_t \neq g_{t+1}$; or
     * 3) An accept is acknowledged, concluding the thread: $o_{t+1} = Accept$ and $o_{t} = Accept$ and $o_{t-1} \in \Omega$ and $g_{t-1} = g_{t+1} \neq g_{t}$.
	 */	
	@Override
	protected ValidationResult validate(OfferBy curOfferBy) 
	{
		Offer curOffer = curOfferBy.getOffer();
		String curSender = curOfferBy.getSender();
		
		// 1)
		if (curOffer.isBid())
			return new ValidationSuccess();
		
		if (curOffer.isBreakoff())
			return new ValidationSuccessWithDisagreement();	// we are done
		
		// 2)
		if (log.size() < 2) 		// o_t should exist
			return new ValidationFailure();
		
		OfferBy prevOfferBy = getLastOffer();
		Offer prevOffer = prevOfferBy.getOffer();
		String prevSender = prevOfferBy.getSender();
		
		if (curOffer.isAccept() && prevOffer.isBid() && !curSender.equals(prevSender))
			return new ValidationSuccess();
		
		// 3)
		if (log.size() < 3) 		// o_{t-1} should exist
			return new ValidationFailure();
		
		OfferBy prevprevBy = log.get(log.size() - 2);
		Offer prevprev = prevprevBy.getOffer();
		String prevprevSender = prevprevBy.getSender();
		
		if (curOffer.isAccept() && prevOffer.isAccept() 
				&& prevprev.isBid() && prevprevSender.equals(curSender) 
				&& !curSender.equals(prevSender))
			return new ValidationSuccessWithAgreement(prevprev.getBundle());		// we are done
		
		return new ValidationFailure();
	}
	
	public static void main(String[] args) throws InterruptedException 
	{
		BlockingQueue<OfferBy> from  = new LinkedBlockingQueue<OfferBy>();
		String nameA = "Buyer";
		BlockingQueue<Offer> toA	= new LinkedBlockingQueue<Offer>();
		String nameB = "Seller";
		BlockingQueue<Offer> toB	= new LinkedBlockingQueue<Offer>();
		
		AsynchronousOffersProtocol aop = new AsynchronousOffersProtocol(from, nameA, toA, nameB, toB);
		
		Thread aopThread = new Thread(aop);
		aopThread.start();
		
		from.put(OfferBy.getSampleOffer("Buyer", "Red", 3));
//		Thread.sleep(1000);
		from.put(OfferBy.getSampleOffer("Buyer", "Red", 6));
//		Thread.sleep(1000);
		from.put(new OfferBy("Seller", new Accept(Offer.getSampleOffer(6))));
//		Thread.sleep(1000);
		from.put(new OfferBy("Buyer", new Accept(Offer.getSampleOffer(6))));
//		from.put(new OfferBy("Buyer", new Breakoff()));
		
		Thread.sleep(1000);
		System.out.println("Sent to A:" + toA);
		System.out.println("Sent to B:" + toB);
	}

}
