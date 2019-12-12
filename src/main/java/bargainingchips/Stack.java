package bargainingchips;

/**
 * Stack contains a number of {@link Chip} objects of the same color and price. 
 * A stack=(chip, quantity, price). 
 * Aggregation of a two stacks of the same colors, creates another stack with the total quantity but its chip in a new price equal to weighted average of the both unit prices.  
 * Aggregation of a stack with the empty stack is itself. 
 * 
 *  Immutable.
 */
public class Stack 
{
	private final Chip chip;
	private final double price;
	private final int quantity;

	public Stack(Chip c, double p, int q)
	{
		chip = c;
		price = p;
		quantity = q;
	}
	
	public Stack(String c, double p, int q)
	{
		this(new Chip(c), p, q);
	}
	
	/**
	 * @return the chip
	 */
	public Chip getChip() {
		return chip;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() 
	{
		return quantity;
	}

	public double getUnitPrice()
	{
		return price;
	}
	
	public double getTotalPrice()
	{
		return price * quantity;
	}

	public String getColor()
	{
		return chip.getColor();
	}
	
	public boolean hasSameColorAs(Stack s)
	{
		return getChip().equals(s.getChip());
	}
	
	public Stack aggregateWith(Stack s) 
	{
		if (s!=null && s.hasSameColorAs(this)) 
		{
			int q = quantity + s.getQuantity();
			double p = (getUnitPrice()*quantity+s.getUnitPrice()*s.getQuantity())/q;
			return new Stack(getChip(), p, q);
		} else 
			throw new IllegalStateException("\n\n[Warning] StackClass::aggregateWith(Stack). Different colors! Aggregating "+s+" into "+this+" is not possible.");
	}	

	@Override
	public String toString() 
	{
		return getQuantity()+"x"+getChip()+"($" + getUnitPrice() + ")";
	}
	
	public static void main(String[] args) 
	{
		Stack s = new Stack("Red", 9.0, 3);
		Stack t = new Stack ("Red", 4.0, 2);
		System.out.println(s + " + " + t + " = " + s.aggregateWith(t));
		System.out.println(t + " + " + s + " = " + t.aggregateWith(s));		
	}
}
