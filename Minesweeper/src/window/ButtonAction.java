package window;

import java.awt.event.*;

import javax.swing.JButton;

public class ButtonAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmdName = e.getActionCommand();
			if("level1".equals(cmdName)) {
				System.out.println("レベル１");
				
			}
			else if("level2".equals(cmdName)) {
				System.out.println("レベル2");
			}
			else if("level3".equals(cmdName)) {
				System.out.println("レベル3");
			}
		
	}

}
