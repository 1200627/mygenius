package bargainingchips.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bargainingchips.Chip;
import bargainingchips.Stack;


public class StackTest 
{
	
	@Test
	public void testConstructor() 
	{
		Stack s = new Stack("Green", 7.0, 5);
				
		assertEquals(s.getColor(), "Green");
		assertEquals(s.getChip(), new Chip("Green"));
		assertEquals(s.getTotalPrice(), 35.0, 0.0);
		assertEquals(s.getUnitPrice(), 7.0, 0.0);
		
		Stack t = new Stack("Green", 4.0, 9);
		Stack u = new Stack("Blue",  2.0, 15);
		
		assertTrue(s.hasSameColorAs(t));
		assertFalse(s.hasSameColorAs(u));		
	}
	
	@Test
	public void testAggregationSymmetry() 
	{
		Stack s = new Stack("Red", 9.0, 3);
		Stack t = new Stack ("Red", 4.0, 2);
		
		Stack sPlust = s.aggregateWith(t);
		System.out.println(s + " + " + t + " = " + sPlust);
		
		Stack tPluss = t.aggregateWith(s);
		System.out.println(t + " + " + s + " = " + tPluss);		
		
		assertEquals(sPlust.getChip(), tPluss.getChip());
		assertEquals(sPlust.getQuantity(), tPluss.getQuantity());
	}

}
