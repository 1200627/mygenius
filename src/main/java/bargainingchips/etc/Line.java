package bargainingchips.etc;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;


/**
 * @author Faria Nassiri-Mofakham
 *
 */
public class Line extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Color color;
	int length;

	public Line(Color c, int l)
	{
		color=c;
		length=l;
	}
	public void paint(Graphics g) 
	{
		g.setColor(color);
		g.drawLine(g.getClipBounds().x,g.getClipBounds().y, g.getClipBounds().x+length,g.getClipBounds().y);
	}
		
}
