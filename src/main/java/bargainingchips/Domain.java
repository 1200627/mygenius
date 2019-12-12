package bargainingchips;

import java.util.List;

import java.util.ArrayList;

/**
 * The chips that are under negotiation.
 */
public class Domain 
{
	List<Chip> chips;
	
	public Domain() 
	{
		chips = new ArrayList<Chip>();
	}
	
	public void add(String c)
	{
		chips.add(new Chip(c));
	}

}
