package LogicInterface;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Logic.AxiomSystem;
import Logic.Statement;
import javax.swing.JTextArea;

public class MainWindow implements ActionListener, ListSelectionListener {

	private JFrame frame;
	private JButton btnImportDefinitions;
	private JButton btnImportAxioms;
	private JLabel lblTo;
	private JLabel lblApply;
	private JList applyList;
	private JList toList;
	private JSplitPane splitPane;

	public AxiomSystem as = new AxiomSystem();
	DefaultListModel<Statement> axiomListModel = new DefaultListModel<Statement>();
	public ArrayList<applyButton> applyButtons = new ArrayList<applyButton>();
	public Statement resultingStatement = null;
	
	private JPanel panel;
	private JTextArea resultArea;
	private JButton addTheorem;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
		this.importAxioms("AxiomSets/TestAxioms.txt");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1500, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setBounds(10, 56, 1474, 581);
		frame.getContentPane().add(splitPane);
		
		toList = new JList(axiomListModel);
		toList.addListSelectionListener(this);
		splitPane.setRightComponent(toList);
		
		applyList = new JList(axiomListModel);
		applyList.addListSelectionListener(this);
		splitPane.setLeftComponent(applyList);
		
		lblApply = new JLabel("Apply");
		lblApply.setBounds(360, 41, 35, 14);
		frame.getContentPane().add(lblApply);
		
		lblTo = new JLabel("To");
		lblTo.setBounds(1133, 41, 19, 14);
		frame.getContentPane().add(lblTo);
		
		btnImportAxioms = new JButton("Import Axioms");
		btnImportAxioms.addActionListener(this);
		btnImportAxioms.setBounds(10, 11, 124, 23);
		frame.getContentPane().add(btnImportAxioms);
		
		btnImportDefinitions = new JButton("Import Definitions");
		btnImportDefinitions.addActionListener(this);
		btnImportDefinitions.setBounds(1343, 11, 141, 23);
		frame.getContentPane().add(btnImportDefinitions);
		
		panel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		scrollPane.setBounds(10, 674, 1474, 60);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblAtPosition = new JLabel("At Position");
		lblAtPosition.setBounds(723, 648, 71, 14);
		frame.getContentPane().add(lblAtPosition);
		
		resultArea = new JTextArea();
		resultArea.setBounds(10, 777, 1474, 42);
		frame.getContentPane().add(resultArea);
		
		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(734, 745, 46, 14);
		frame.getContentPane().add(lblResult);
		
		addTheorem = new JButton("Add To List");
		addTheorem.addActionListener(this);
		addTheorem.setBounds(1343, 745, 141, 23);
		frame.getContentPane().add(addTheorem);
	}
	
	public void importAxioms(String filname) {
		as.importAxioms(filname);
		this.refreshStatements();
	}
	
	public void importDefinitions(String filname) {
		as.importDefinitions(filname);
		this.refreshStatements();
	}
	
	public void refreshStatements() {
		this.toList.clearSelection();
		this.applyList.clearSelection();
		
		this.axiomListModel.clear();
		
		ArrayList<Statement> allStatements = as.getAllStatements();
		for(int i=0;i<allStatements.size();i++) {
			this.axiomListModel.addElement(allStatements.get(i));
		}
	}

	public void breakDownToSelection() {
		Statement st = (Statement) this.toList.getSelectedValue();
		
		if(st==null) return;
		
		panel.removeAll();
		this.applyButtons.clear();
		
		for(int i=0;i<st.Sequence.size();i++) {
			applyButton btnNewButton = new applyButton(st.Sequence.get(i).toString(), i);
			btnNewButton.addActionListener(this);
			panel.add(btnNewButton);
			this.applyButtons.add(btnNewButton);
		}
		
		this.enableApplyButtons();
		
		panel.doLayout();
		panel.repaint();
	}
	
	public void enableApplyButtons() {
		Statement st1 = (Statement) this.applyList.getSelectedValue();
		Statement st2 = (Statement) this.toList.getSelectedValue();
		
		if(st1==null || st2==null) return;
		
		for(int i=0;i<this.applyButtons.size();i++) {
			this.applyButtons.get(i).disableButton();
		}
		
		ArrayList<Integer> indices = st1.getApplicableIndices(st2);
		
		if(indices!=null) {
			for(int i=0;i<indices.size();i++) {
				this.applyButtons.get(indices.get(i)).enableButton();
			}
		}
		
		panel.doLayout();
		panel.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnImportAxioms) {
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.importAxioms(fc.getSelectedFile().getAbsolutePath());
			}
		}
		
		if(e.getSource()==this.btnImportDefinitions) {
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.importDefinitions(fc.getSelectedFile().getAbsolutePath());
			}
		}
		
		if(e.getSource() instanceof applyButton) {
			applyButton bt = (applyButton) e.getSource();
			int index = bt.index;
			
			Statement st1 = (Statement) this.applyList.getSelectedValue();
			Statement st2 = (Statement) this.toList.getSelectedValue();
			
			this.resultingStatement = st1.applyTo(st2, index);
			
			this.resultArea.setText(this.resultingStatement.toString());
		}
		
		if(e.getSource() == this.addTheorem) {
			as.addTheorem(this.resultingStatement);
			this.refreshStatements();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource()==this.toList && !e.getValueIsAdjusting()) {
			this.breakDownToSelection();
		} else if(e.getSource()==this.applyList && !e.getValueIsAdjusting()) {
			this.enableApplyButtons();
		}
	}
}
