package bargainingchips;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Offers are exchanged in Bundle formats.
 * A Bundle is a set of several stacks, {s_1, ..., s_n}, where no two stacks are of the same color.
 * A stack can be aggregated into a bundle, if at the same color of one of the Bundle's stacks. 
 * Two bundles can also be aggregated into a new bundle, where every two stacks of the same colors from both bundles are aggregated into one stack in the new bundle.  
 * Aggregation of a bundle with the empty stack or empty bundle is itself.  
 * 
 * Immutable.
 */
public class Bundle implements Iterable<Stack>
{
	private final List<Stack> bundle;
	
	/**
	 * Makes sure bundle remains unmodifiable.
	 */
	public Bundle(List<Stack> list)
	{
        List<Stack> tmpListOfHolding = new ArrayList<Stack>();
        tmpListOfHolding.addAll(list);
        this.bundle = Collections.unmodifiableList(tmpListOfHolding);
    }

	/**
	 * @return the bundle
	 */
	public List<Stack> getBundle() 
	{
		return bundle;
	}

	@Override
	public String toString() 
	{
		return bundle.toString();
	}

	public int size()
	{
		return getBundle().size();
	}

	public Iterator<Stack> iterator() 
	{
		return bundle.iterator();   
	}	


	public Double getTotalPrice()
	{
		double sum=0.0;
		for (Stack t: bundle)
			sum += t.getTotalPrice();
		return sum;
	}
	
	/**
	 * Gets the quantity of the right stack in this bundle 
	 */
	public Integer getQuantity(Chip c)
	{
		for (Stack s : this)
			if (s.getChip().equals(c))
				return s.getQuantity();
		return null;
	}

	/**
	 * Gets the unit price of the chip in the right stack in this bundle 
	 */
	public double getUnitPrice(Chip c)
	{
		for (Stack s : this)
			if (s.getChip().equals(c))
				return s.getUnitPrice();
		return 0.0;
	}

	/**
	 * 
	 * bundle "+" bundle 
	 * 
	 * @param b, this method adds bundle b to `this' bundle
	 * @return the aggregated bundle
	 * where, any two stacks of the same colors are aggregated as a new stack into the new bundle, otherwise, they are just added into the new bundle.
	 * 
	 * This method just calls stack '+' stack aggregation operation (has no need to bundle "+" stack aggregation operation. 
	 */
	public Bundle aggregateWith(Bundle b) 
	{
		// Start with this bundle
		Bundle agg = new Bundle(this.getBundle());
		// And add all stacks from b
		for (Stack t : b)
			agg = agg.aggregateWith(t);
			
		return agg;
	}


	

	/**
	 * 	bundle '+' stack
	 * 	
	 * @param s, this method adds stack s to `this' bundle
	 * @return the aggregated bundle
	 * where, stack s is aggregated with a stack of the same color in `this' bundle and the new stack is added into the new bundle;
	 * otherwise, stack s is just added into the new bundle.
	 */
	public Bundle aggregateWith(Stack s)
	{
		ArrayList<Stack> agg = new ArrayList<Stack>();
		boolean sameColorFound = false;
		for (Stack t : bundle)
			if (!t.hasSameColorAs(s)) 
				agg.add(t);
			else
			{
				agg.add(s.aggregateWith(t));
				sameColorFound = true;
			}
		if (!sameColorFound)
			agg.add(s);
		return new Bundle(agg);
	}

	public static void main(String[] args) 
	{
		Bundle b = new BundleBuilder()
							.addStack("Red", 7.0, 3)
							.addStack("Green", 3.0, 5)
							.addStack("Purple", 2.0, 7)
							.build();
		
		Bundle c = new BundleBuilder()
				.addStack("Red", 5.0, 3)
				.addStack("Yellow", 9.0, 1)
				.addStack("Green", 9.0, 1)
				.build();
		
		System.out.println(b);
		System.out.println(b.aggregateWith(new Stack("Green", 3.0, 2)));
		System.out.println(b.aggregateWith(c));
	}



}
