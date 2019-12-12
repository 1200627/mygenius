package bargainingchips.etc;

public class Main 
{
	private Portfolio portfolio;
	private DemandPlan demandPlan;
	
	public static int numType = 7;  // this could later be gained from Domain
	
	public Main()
	{
		Product redProduct = new Product("Red");
		Product pinkProduct = new Product("Pink");
		Product blueProduct = new Product("Blue");
		//Product orangeProduct = new Product("Orange");		


		portfolio = new Portfolio();

		portfolio.addProduct(redProduct);
		portfolio.addProduct(pinkProduct);
		portfolio.addProduct(blueProduct);
		//portfolio.addProduct(orangeProduct);


		demandPlan = new DemandPlan();
//    	for (Product product : portfolio.getPortfolio())
//    		demandPlan.setQuantity(product, (int)(Math.random()*numType+1));
		
//    	System.out.println(demandPlan);
    	
    	

//        Agent sam = new Sam();
//        Agent sally = new Sally();
//
//        Agent bob = new Bob();
//
//        OneToManyProtocol p = new OneToManyProtocol(bob, sam, sally);   
//===> BilateralThread(bob, sam) and BilateralThread(bob, sally)`
//        p.start();



	
	}

	
	public Portfolio getPortfolio() 
	{
		return portfolio;
	}
	
	public DemandPlan getDemandPlan() 
	{
		return demandPlan;
	}

}

