package bargainingchips.etc;

import java.awt.Color;

/**
 * A product, such as Paracetamol, or a Red Dot
 */
public class Product 
{

	String id;

	public Product(String id) 
	{
		this.id = id;
	}	

	@Override
	public String toString() 
	{
		return id;
	}

	public Color getColor()   //anyway, gives the result in RGB ! :)
	
	{
		switch(id.toLowerCase())
		{
		case "red":			
			return Color.RED; 
		case "blue":
			return Color.BLUE; 
		case "pink":				
			return Color.PINK; 	
		case "orange":				
			return Color.ORANGE;			
		default:
			System.out.println("Undefined color!");
			return null;	
		}
	}
	
	public Color getRGBColor(String s) // to be able to pass colors in RGB hex strings
	{
		return Color.decode(s); 				
	}

}