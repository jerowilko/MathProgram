package LogicInterface;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logic.AxiomSystem;
import Logic.Statement;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TheoremsWindow extends JFrame {

	private JPanel contentPane;
	JList StatementsList;
	DefaultListModel<Statement> axiomListModel = new DefaultListModel<Statement>();

	MainWindow parent;
	public JButton btnRemoveStatement;
	
	/**
	 * Create the frame.
	 */
	public TheoremsWindow(MainWindow parent) {
		setAlwaysOnTop(true);
		setAutoRequestFocus(false);
		setTitle("Important Results");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 400, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		StatementsList = new JList(axiomListModel);
		StatementsList.addListSelectionListener(parent);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(StatementsList);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnRemoveStatement = new JButton("Remove Statement");
		btnRemoveStatement.addActionListener(parent);
		panel.add(btnRemoveStatement);
		
		this.setLocation(1514, 58);
		this.setFocusableWindowState(false);
		
		this.parent = parent;
	}
	
	public void refreshStatements() {
		AxiomSystem as = this.parent.as;
		
		this.axiomListModel.clear();
		
		for(int i=0;i<as.importantResults.size();i++) {
			this.axiomListModel.addElement(as.importantResults.get(i));
		}
	}

}
