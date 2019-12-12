package bargainingchips.etc;

import java.util.List;

//import java.awt.Color;
import java.util.ArrayList;

/**
 * A portfolio is a list of products that is relevant to an agent.
 * Every product in the portfolio is unique.
 *  
 * 
 * * 
 * @author Faria Nassiri-Mofakham
 */
public class Portfolio 
{
	private List<Product> portfolio;

	public Portfolio() 
	{
		this.portfolio = new ArrayList<Product>();
	}

	public List<Product> getPortfolio() 
	{
		return portfolio;
	}

	public void addProduct(Product p) 
	{
		portfolio.add(p);
	}

	@Override
	public String toString() 
	{
		return portfolio.toString();
	}

	public int size()
	{
		if (portfolio != null)
		{
			return getPortfolio().size();
		}
		else return 0;		
	}

	public Product getProduct(int i)
	{
		return getPortfolio().get(i);
	}

	public Product iterator()  // product or List<product> or Portfolio
	{
		Product s=null;
		if (this!=null)
			for (int i=0; i<this.size();i++)
				s = portfolio.get(i);
		return s;   // this just returns the last one, a product, not a list!
	}
}
