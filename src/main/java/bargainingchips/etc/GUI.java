package bargainingchips.etc;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * @author Faria Nassiri-Mofakham
 *
 */
public class GUI extends JFrame 
{
	Main main;
	private static final long serialVersionUID = -5602032021645365870L;
	

	public GUI(Main m) //throws MalformedURLException, IOException
	{
		super("Bob gets ready for negotiation ...");
		main = m;


		// Show the GUI
		setLayout(new MigLayout("", "", ""));

		// Could not `resize' and show small CWI-ACUMEX QR-code
//		Canvas canvas00 = new PutImage("C:\\Users\\fnm\\Pictures\\qr_code.png"); 
//		canvas00.setBounds(0,0,50,50);
//		canvas00.setSize(50, 50);	
//		add(canvas00);				


			
		
		// how to change to url address of Bob ? "./NegotiatorGUI/src/main/java/negotiator/onetomany/etc/Bob.png"
		Canvas canvas0 = new DrawImage("C:\\Users\\fnm\\Pictures\\Bob.png");		
//		Canvas canvas0 = new Bob("NegotiatorGUI.src.main.java.negotiator.onetomany.etc.Bob.png");
		canvas0.setSize(400, 200);
		add(canvas0, "cell 0 0, span 1 4, growx, center"); //cannot make it center
	
		//defining a font	
		Font f1 = new Font("TimesRoman",Font.BOLD,20);
		Font f2 = new Font("TimesRoman",Font.ITALIC,20);
		Font f3 = new Font("TimesRoman",Font.PLAIN,16);

		
//		JLabel l1 = new JLabel("The `Agent' receives Bob's PORTFOLIO containing "+m.getPortfolio().size()+" different products as follows:");
		JLabel l1 = new JLabel("Bob's PORTFOLIO contains "+m.getPortfolio().size()+" different products as follows:");
		l1.setFont(f1);

		add(l1, "cell 1 1, center,  width 100:550:1000, wrap"); // no notice to min and max constraints for column width, just the preferred (the middle number)

		
		JPanel panel1 = new JPanel();
		panel1.setForeground(Color.WHITE);
		panel1.setFont(f3);
		
		panel1.setBorder(BorderFactory.createTitledBorder("Portfolio"));
		panel1.setBackground(Color.decode("#5C8585"));
		Canvas canvas1 = new PortfolioDrawing(m.getPortfolio());
		canvas1.setBackground(Color.decode("#5C8585"));
		canvas1.setSize(550, 200);
        panel1.add(canvas1);
		add(panel1, "cell 1 2, growx, center, wrap");
		
	
		
//		Canvas canvas1 = new PortfolioDrawing(m.getPortfolio());
//		canvas1.setBackground(Color.decode("#5C8585"));
//		canvas1.setSize(200, 200);
//		add(canvas1, "cell 1 2, growx, center, wrap");
		
		add(new JLabel(" "), "center, wrap");

	
	
		
		//a line
		int w = (canvas0.getWidth()+canvas1.getWidth())*3;  // doesn't calculate dynamically well!	
		
				
		
		Canvas canvas2 = new Line(Color.BLACK,w);
		canvas2.setSize(1, 1);
		add(canvas2, "span 2 0, growx, center, wrap");	

		add(new JLabel(" "), "center, wrap");

		
//		JLabel l2 = new JLabel("It also receives Bob's DEMAND PLAN as follows:");
		JLabel l2 = new JLabel("And his DEMAND has been planed as the following quantities:");
		l2.setFont(f1);
		
		add(l2, "span 2 0, center, wrap");




		add(new JLabel(" "), "center, wrap");
		
		JLabel n = new JLabel(m.getDemandPlan().toString());
		n.setFont(f2);
		add(n, "span 2 0, center, wrap");

      
		JPanel panel2 = new JPanel();
		//panel2.setFont(f1);
		panel2.setBackground(Color.WHITE);
        panel2.setBorder(BorderFactory.createTitledBorder("Demand Plan"));
  		
        
        //Canvas canvas3 = new DemandPlan(m.getDemandPlan());
		//canvas3.setBackground(Color.WHITE);
		//canvas3.setSize(800, 300);       
        
		
		//panel2.add(canvas3);
        //add(panel2, "span 2 0, growx, growy, center, wrap");

		
		
//		Canvas canvas3 = new DemandPlanDrawing(m.getDemandPlan());
//		canvas3.setBackground(Color.WHITE);
//		canvas3.setSize(200, 400);
//		add(canvas3, "span 2 0, growx, growy, center, wrap");

		
		
		
		
		add(new JLabel(" "), "span 2 0, center, wrap");
		
		//a line
//		Canvas canvas4 = new Line(Color.BLACK,w);
//		canvas4.setSize(1, 1);
//		add(canvas4, "span 2 0, growx, center, wrap"); 

		add(new JLabel(" "), "span 2 0, center, wrap");

		
				

		
		Canvas canvas222 = new Paraboloid(Color.BLACK,w, 0.4, 0.4, 0.4);
		canvas222.setSize(800, 800);
		add(canvas222, "span 2 0, growx, center, wrap");	
		
		
		
		//buttons
		
		JButton a = new JButton("Edit...");
		add(a, "cell 0 12, span 2 0, center, w 90");
		// at dynamic position ?  for the height ?! by canvases heights, or counting the rows and columns? 
		JButton b = new JButton("Delete...");
		add(b, "cell 0 12, span 2 0, center, w 90");
		
		add(new JButton("Next..."), "cell 0 12, span 2 0, center, w 90");
		
		JButton d = new JButton("Close");		
		d.addActionListener(new CloseButtonListener(this));   // <-- only this was added to this from examples closeButtonListenner and ClosableWindow
		add(d, "cell 0 12, span 2 0, center, wrap, w 90");


		//setting the background as the Bob's image
		getContentPane().setBackground(Color.decode("#AFD6D6"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
					
		setLocationRelativeTo(null);
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		setBounds(0,0,screenSize.width, screenSize.height);	
		
		setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Main main = new Main();
		GUI gui = new GUI(main);

	}

}


