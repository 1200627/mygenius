package bargainingchips.etc;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;


/**
 * @author Faria Nassiri-Mofakham
 *
 */
public class Paraboloid extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Color color;
	int length;
	double a,b,c;//,d;

	
	public Paraboloid(Color col, int l, double aa, double bb, double cc)//, double dd)
	{
		color=col;
		length=l;
		a=aa;
		b=bb;
		c=cc;
//		?d=dd;
	}
	
	public void paint(Graphics g) 
	{
		g.setColor(color);
		g.drawLine(g.getClipBounds().x,g.getClipBounds().y, g.getClipBounds().x+length,g.getClipBounds().y);
		
		g.setColor(Color.red);
        for (double x=-100;x<=100;x = x+0.1){
        double y = a * x * x + b * x + c;
        int X = (int)Math.round(200 + x*20);
        int Y = (int)Math.round(200 - y*20);
        g.fillOval(X-2,Y-2,4,4);
        
        y = - (x + 4) * (x + 4) - 7;
        X = (int)Math.round(200 + x*20);
        Y = (int)Math.round(200 - y*20);
        g.fillOval(X-2,Y-2,4,4);

		
//        for (double x=-100;x<=100;x = x+0.1){
//        double z = a * x * x * x + b * x * x + c;
//        	
//        double y = a * x * x + b * x + c;
//        int X = (int)Math.round(200 + x*20);
//        int Y = (int)Math.round(200 - y*20);
//        g.fillOval(X-2,Y-2,4,4);
               
        
        }   
		
	}
	
}
