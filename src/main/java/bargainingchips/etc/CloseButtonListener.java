/**
 * 
 */
package bargainingchips.etc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * @author Faria Nassiri-Mofakham
 *
 */
public class CloseButtonListener implements ActionListener {

	private JFrame target;

	public CloseButtonListener(JFrame aFrame)
	{
		target=aFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub

		target.dispose();
	}

}
