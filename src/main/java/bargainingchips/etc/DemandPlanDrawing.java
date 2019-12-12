package bargainingchips.etc;
//package negotiator.onetomany.etc;
//
//import java.awt.Canvas;
//import java.awt.Graphics;
//import java.util.HashMap;
//
//import negotiator.onetomany.DemandPlan;
//import negotiator.onetomany.Portfolio;
//import negotiator.onetomany.Product;
//
//
///**
// * @author Faria Nassiri-Mofakham
// *
// */
//public class DemandPlanDrawing extends Canvas 
//{
//
//	private static final long serialVersionUID = 1L;
//	DemandPlan p;
//
//	public DemandPlanDrawing(DemandPlan p2)
//	{
//		p = p2;
//	}
//
//
//	public void paint(Graphics g) 
//	{
//		int z = p.getDemandPlan().size();
//		int yOffset=50;
//		int r=30/z;
//		int d=(g.getClipBounds().width-z*2*r)/(z+4); // it is enough to be divided by z+1, but for making the distance smaller, larger numbers are better
//		int x = g.getClipBounds().x;//+ d;
//		final int xx =x;
//		int offset=d/2;
//
//
//		for (Product product : p.getDemandPlan().keySet())
//		{
//			Integer q = p.getQuantity(product);
//
//			System.out.println("For product " + product + ", the quantity I want to draw is: " + q);
//
//			g.setColor(product.getColor());
//			for (int i=0; i<q; i++)
//			{
//				if (x+2*r+d < g.getClipBounds().width)
//					x += d/2;							
//				else
//				{
//					x=xx+d;
//					yOffset+=offset;
//				}				
//				g.fillOval(x, g.getClipBounds().y+yOffset, 2*r, 2*r); // it also need to became dynamic
//
//			}
//
//			x=xx;
//			yOffset+=offset; // no condition for the height ?!
//		}
//
//	}
//
//
//}
//
