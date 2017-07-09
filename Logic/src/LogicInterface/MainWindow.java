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
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Logic.AxiomSystem;
import Logic.GlobalConstants;
import Logic.PunctuationalContext;
import Logic.Statement;

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

	private JPanel positionSelectorPane;
	private JTextPane resultArea;
	private JButton addTheorem;
	private JButton toggleDebugBtn;
	private JButton btnLoadState;
	private JButton btnSaveState;
	private JTextPane manualStmt;
	private JLabel lblAddNewStatement;
	private JButton btnAsAxiom;
	private JButton btnAsDefinition;
	private JButton btnReset;
	private JButton btnImportPunctuation;
	private JButton btnPlayWithSelected;
	private JButton btnRemoveSelectedStatement;
	private JLabel lblNotReplacement = new JLabel("Statement selected to be applied is not a replacement statement.");
	private JScrollPane positionScrollPane;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;

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
	}

	public MainWindow(ArrayList<Statement> axioms, PunctuationalContext punct) {
		this();

		this.as = new AxiomSystem(axioms, punct);
		this.refreshStatements();
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
		frame.setLocation(5, 5);

		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setBounds(10, 56, 1474, 581);
		frame.getContentPane().add(splitPane);
		
		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
				applyList = new JList(axiomListModel);
				scrollPane.setViewportView(applyList);
				
				scrollPane_2 = new JScrollPane();
				splitPane.setRightComponent(scrollPane_2);
				
						toList = new JList(axiomListModel);
						scrollPane_2.setViewportView(toList);
						toList.addListSelectionListener(this);
				applyList.addListSelectionListener(this);

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

		positionSelectorPane = new JPanel();

		positionScrollPane = new JScrollPane(positionSelectorPane);
		positionSelectorPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		positionScrollPane.setBounds(10, 674, 1474, 60);
		frame.getContentPane().add(positionScrollPane);

		JLabel lblAtPosition = new JLabel("At Position");
		lblAtPosition.setBounds(712, 649, 71, 14);
		frame.getContentPane().add(lblAtPosition);

		resultArea = new JTextPane();
		resultArea.setEditable(false);
		resultArea.setBounds(10, 777, 1474, 42);
		frame.getContentPane().add(resultArea);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(attribs, 22);
		resultArea.setParagraphAttributes(attribs, true);

		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(723, 745, 46, 14);
		frame.getContentPane().add(lblResult);

		addTheorem = new JButton("Add To List");
		addTheorem.addActionListener(this);
		addTheorem.setBounds(1343, 745, 141, 23);
		frame.getContentPane().add(addTheorem);

		toggleDebugBtn = new JButton("Toggle Debug Mode");
		toggleDebugBtn.addActionListener(this);
		toggleDebugBtn.setBounds(10, 745, 150, 23);
		frame.getContentPane().add(toggleDebugBtn);

		btnLoadState = new JButton("Load State");
		btnLoadState.addActionListener(this);
		btnLoadState.setBounds(641, 11, 105, 23);
		frame.getContentPane().add(btnLoadState);

		btnSaveState = new JButton("Save State");
		btnSaveState.addActionListener(this);
		btnSaveState.setBounds(749, 11, 105, 23);
		frame.getContentPane().add(btnSaveState);

		lblAddNewStatement = new JLabel("Add New Statement");
		lblAddNewStatement.setBounds(686, 839, 124, 14);
		frame.getContentPane().add(lblAddNewStatement);

		btnAsAxiom = new JButton("As Axiom");
		btnAsAxiom.addActionListener(this);
		btnAsAxiom.setBounds(641, 917, 105, 23);
		frame.getContentPane().add(btnAsAxiom);

		btnAsDefinition = new JButton("As Definition");
		btnAsDefinition.addActionListener(this);
		btnAsDefinition.setBounds(749, 917, 116, 23);
		frame.getContentPane().add(btnAsDefinition);

		btnReset = new JButton("Reset");
		btnReset.addActionListener(this);
		btnReset.setBounds(328, 11, 89, 23);
		frame.getContentPane().add(btnReset);

		btnImportPunctuation = new JButton("Import Punctuation");
		btnImportPunctuation.addActionListener(this);
		btnImportPunctuation.setBounds(1070, 11, 150, 23);
		frame.getContentPane().add(btnImportPunctuation);

		btnPlayWithSelected = new JButton("Play With Selected Axioms");
		btnPlayWithSelected.addActionListener(this);
		btnPlayWithSelected.setBounds(112, 640, 206, 23);
		frame.getContentPane().add(btnPlayWithSelected);
		
		btnRemoveSelectedStatement = new JButton("Remove Selected Statement");
		btnRemoveSelectedStatement.addActionListener(this);
		btnRemoveSelectedStatement.setBounds(446, 640, 198, 23);
		frame.getContentPane().add(btnRemoveSelectedStatement);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 864, 1474, 44);
		frame.getContentPane().add(scrollPane_1);
		
				manualStmt = new JTextPane();
				scrollPane_1.setViewportView(manualStmt);
				manualStmt.setParagraphAttributes(attribs, true);
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
		for (int i = 0; i < allStatements.size(); i++) {
			this.axiomListModel.addElement(allStatements.get(i));
		}

		this.applyButtons.clear();
		this.positionSelectorPane.removeAll();

		positionSelectorPane.doLayout();
		positionSelectorPane.repaint();
	}

	public void breakDownToSelection() {
		Statement toStatement = (Statement) this.toList.getSelectedValue();
		Statement applyStatement = (Statement) this.applyList.getSelectedValue();

		if (toStatement == null)
			return;

		positionSelectorPane.removeAll();
		this.applyButtons.clear();

		if(applyStatement.isReplacementStatement()) {
			for (int i = 0; i < toStatement.Sequence.size(); i++) {
				applyButton btnNewButton = new applyButton(toStatement.Sequence.get(i).toString(), i);
				btnNewButton.addActionListener(this);
				positionSelectorPane.add(btnNewButton);
				this.applyButtons.add(btnNewButton);
			}
		} else {
			positionSelectorPane.add(lblNotReplacement);
		}

		this.enableApplyButtons();

		positionSelectorPane.doLayout();
		positionSelectorPane.repaint();
	}

	public void enableApplyButtons() {
		Statement st1 = (Statement) this.applyList.getSelectedValue();
		Statement st2 = (Statement) this.toList.getSelectedValue();

		if (st1 == null || st2 == null)
			return;

		for (int i = 0; i < this.applyButtons.size(); i++) {
			this.applyButtons.get(i).disableButton();
		}

		ArrayList<Integer> indices = st1.getApplicableIndices(st2);

		if (indices != null) {
			for (int i = 0; i < indices.size(); i++) {
				this.applyButtons.get(indices.get(i)).enableButton();
			}
		}

		positionSelectorPane.doLayout();
		positionSelectorPane.repaint();
	}

	public void showResult() {
		this.resultArea.setText("");

		if (this.resultingStatement != null)
			this.resultArea.setText(this.resultingStatement.toString());
	}

	public void importState(String filname) {
		this.as.importState(filname);

		this.refreshStatements();
	}

	public void exportState(String filname) {
		this.as.exportState(filname);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnImportAxioms) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.importAxioms(fc.getSelectedFile().getAbsolutePath());
			}
		}

		if (e.getSource() == this.btnImportDefinitions) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.importDefinitions(fc.getSelectedFile().getAbsolutePath());
			}
		}

		if (e.getSource() instanceof applyButton) {
			applyButton bt = (applyButton) e.getSource();
			int index = bt.index;

			Statement st1 = (Statement) this.applyList.getSelectedValue();
			Statement st2 = (Statement) this.toList.getSelectedValue();

			this.resultingStatement = st1.applyTo(st2, index);

			this.showResult();
		}

		if (e.getSource() == this.addTheorem) {
			as.addTheorem(this.resultingStatement);
			this.refreshStatements();
		}

		if (e.getSource() == this.toggleDebugBtn) {
			GlobalConstants.debugMode = !GlobalConstants.debugMode;
			this.refreshStatements();
			this.showResult();
		}

		if (e.getSource() == this.btnLoadState) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.as = new AxiomSystem();
				this.refreshStatements();
				this.showResult();
				
				this.importState(fc.getSelectedFile().getAbsolutePath());
			}
		}

		if (e.getSource() == this.btnSaveState) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.exportState(fc.getSelectedFile().getAbsolutePath());
			}
		}

		if (e.getSource() == this.btnAsAxiom) {
			this.as.addAxiom(this.manualStmt.getText());
			this.refreshStatements();
		}

		if (e.getSource() == this.btnAsDefinition) {
			this.as.addDefinition(this.manualStmt.getText());
			this.refreshStatements();
		}

		if (e.getSource() == this.btnReset) {
			this.as = new AxiomSystem();
			this.refreshStatements();
			this.resultingStatement = null;
			this.showResult();
		}

		if (e.getSource() == this.btnImportPunctuation) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.as = new AxiomSystem();
				this.as.importPunctuation(fc.getSelectedFile().getAbsolutePath());
			}
		}

		if (e.getSource() == this.btnPlayWithSelected) {
			ArrayList<Statement> stmts = new ArrayList<Statement>(this.applyList.getSelectedValuesList());
			

			EventQueue.invokeLater(new MainWindowRunHelper(stmts, this.as.punct));
		}
		
		if (e.getSource() == this.btnRemoveSelectedStatement) {
			Statement selectedStatement = (Statement) this.applyList.getSelectedValue();
			this.as.axioms.remove(selectedStatement);
			this.as.definitions.remove(selectedStatement);
			this.as.theorems.remove(selectedStatement);
			
			this.refreshStatements();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == this.toList && !e.getValueIsAdjusting()) {
			this.breakDownToSelection();
		} else if (e.getSource() == this.applyList && !e.getValueIsAdjusting()) {
			this.enableApplyButtons();
		}
		this.positionScrollPane.doLayout();
		this.positionScrollPane.repaint();
	}

	class MainWindowRunHelper implements Runnable {
		ArrayList<Statement> stmts;
		PunctuationalContext punct;
		
		public MainWindowRunHelper(ArrayList<Statement> stmts, PunctuationalContext punct) {
			this.stmts = stmts;
			this.punct = punct;
		}
		
		public void run() {
			try {
				MainWindow window = new MainWindow(stmts, punct);
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
