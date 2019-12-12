package bargainingchips;

import java.util.Random;

import java.awt.Color;

/**
 * Chip is the fundamental element in Bargaining Chips.
 * A chip could be assumed as any item at any scenario, e.g. Paracetamol in pharmaceutical market, 
 * a chair in furniture market, or a Red Dot. 
 * A chip is a color, where color is the id of the chip.
 * 
 * Immutable.
 */
public class Chip 
{	
	private final String color;
	private static final String [] colors = new String [] {"Black", "Blue", "Cyan", "darkGray", "Gray", "lightGray", "Green", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
	
	public Chip() 
	{
		int rnd = new Random().nextInt(colors.length);
	    color = colors[rnd];
	}	
	
	public Chip(String c) 
	{
		color = c;
	}
	
	/**
	 * @return the color
	 */
	public String getColor() 
	{
		return color;
	}

	@Override
	public String toString() 
	{
		return getColor();
	}

	public Color getRGBcolor()
	{
		return Color.decode(color); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chip other = (Chip) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}	
	
	
}
