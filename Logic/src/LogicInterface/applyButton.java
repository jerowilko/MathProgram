package LogicInterface;

import javax.swing.JButton;

public class applyButton extends JButton {

	int index;
	
	public applyButton(String txt, int ind) {
		super(txt);
		this.index = ind;
		this.setEnabled(false);
	}
	
	public void enableButton() {
		this.setEnabled(true);
	}
	
	public void disableButton() {
		this.setEnabled(false);
	}
	
}
