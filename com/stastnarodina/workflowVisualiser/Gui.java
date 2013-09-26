package com.stastnarodina.workflowVisualiser;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField outputLocation = null;
	private JButton outputDirButton = null;
	private JFileChooser fc = new JFileChooser();
	
	/**
	 * This is the default constructor
	 */
	public Gui() {
		super();
		initialize();
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(408, 306);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getOutputLocation(), null);
			jContentPane.add(getOutputDirButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes outputLocation	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getOutputLocation() {
		if (outputLocation == null) {
			outputLocation = new JTextField();
			outputLocation.setBounds(new Rectangle(11, 71, 211, 23));
		}
		return outputLocation;
	}

	/**
	 * This method initializes outputDirButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOutputDirButton() {
		if (outputDirButton == null) {
			outputDirButton = new JButton();
			outputDirButton.setBounds(new Rectangle(227, 65, 124, 32));
			outputDirButton.setText("Locate");
			outputDirButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int retVal = fc.showOpenDialog(Gui.this);
					if(retVal == JFileChooser.APPROVE_OPTION) {
						System.out.println("Selected " + fc.getSelectedFile());
					}
				}
			});
		}
		return outputDirButton;
	}

}  //  @jve:decl-index=0:visual-constraint="300,37"
