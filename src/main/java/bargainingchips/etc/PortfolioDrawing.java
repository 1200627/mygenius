package bargainingchips.etc;
import java.awt.Canvas;
import java.awt.Graphics;


/** Draws n circles, where n is the number of products in the portfolio.
 * The circles are spaced apart by....
 * 
 * 
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public class PortfolioDrawing extends Canvas 
{

	private static final long serialVersionUID = 1L;
	Portfolio p;

	public PortfolioDrawing(Portfolio p2)
	{
		p = p2;
	}

	/**
	 * Draws n circles, where n is the number of products in the portfolio.
	 * The circles are spaced apart by....
	 */
	public void paint(Graphics g) 
	{
		int z= p.size();
		int r=30/z;
		int yOffset=50;
		int d=(g.getClipBounds().width-z*2*r)/(z+5);
		int x = g.getClipBounds().x+ d;  // it is now aligned, so the result seem be shown at the center!! but replace it with a mathematical formula which aligns it dynamically at the center

		for (Product product : p.getPortfolio())
		{
			g.setColor(product.getColor());
			g.fillOval(x, g.getClipBounds().y+yOffset, 2*r, 2*r); // it also needs to became more dynamic
			x += d;
		}

	}
}

