package bargainingchips.utilityfunctions;

import bargainingchips.Bundle;

/**
 * 
 * This package is devoted to the Utility Function interface and all the classes for different definitions of utility functions.
 * Utility function is employed by each agent, either coordinator or negotiator, to evaluate an offer, the bundle.
 * A few of these functions evaluate a bundle as 1.0 or 0.0 according to satisfaction of one or few simple conditions.
 * A few others, more general, could represent more flexible conditions, e.g., weighted average, or so, which are not as simple as those in single item cases. 
 * 
 * A few simple 0-1 utility functions: 
 * UF_IWant3Red, UF_IWantColorAndQuantity, UF_Nice, UF_Crazy, UF_AllOrNothing, UF_75pQtyOrNothing, UF_prcntQTYandprcntPrice, UF_FreeDisposal, ...
 * 
 * A few middle complexity utility functions: UF_SimpleAdditive, UF_Peaked, ... 
 *  
 * A few complex utility functions:
 * 
 * 
 * 
 * @author Faria Nassiri-Mofakham
 *
 */
public interface UtilityFunction 
{
	public Double getUtility(Bundle b);
}

