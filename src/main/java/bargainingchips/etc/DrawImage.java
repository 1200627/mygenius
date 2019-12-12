package bargainingchips.etc;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;


/**
 * @author Faria Nassiri-Mofakham
 *
 */
public class DrawImage extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	Image image;

	public DrawImage(String filename)
	{
		loadImage(filename);
	}
	
	public void paint(Graphics g) 
	{
    	g.drawImage(image, g.getClipBounds().x,g.getClipBounds().y, this);
	}

	public void loadImage(String f)
	{
		MediaTracker tracker = new MediaTracker(this);
		image = Toolkit.getDefaultToolkit().getImage(f);
		tracker.addImage(image, 0);
		try
        {
            tracker.waitForID(0);
        }
        catch(InterruptedException ie)
        {
            System.out.println(ie.getMessage());
        }
	}
}




