package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.fun.*;

public class View extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelEncryption, panelDecryption;
	private JButton encryptButton, decryptButton;
	private JTextArea inputTextArea, outputTextArea, resultTextField;
	private JTextField enKey, enFile, endFile, enTime;
	private JTextField deKey, deFile, dedFile, deTime;
	private JProgressBar enProgressBar, deProgressBar;

	private Color redButton = new Color(231, 76, 60);
	private Color greenButton = new Color(46, 204, 113);
	private Font font = new Font("Times New Roman", Font.PLAIN, 20);
	private Border redBorder = new LineBorder(Color.BLUE, 1);
	
//	private static AES AES = new AES();
//	private static HandleFile HF = new HandleFile();

	public View() {
		setTitle("AES Encryption");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 360);
		setLocationRelativeTo(null);

		enFile = new JTextField();
		endFile = new JTextField();
		enKey = new JTextField();
		enTime = new JTextField();

		deFile = new JTextField();
		dedFile = new JTextField();
		deKey = new JTextField();
		deTime = new JTextField();

		enProgressBar = new JProgressBar(0, 100);
		deProgressBar = new JProgressBar(0, 100);

		encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String inputFilePath = enFile.getText();
				String outputFilePath = endFile.getText();
				String key = enKey.getText();
				byte[] inputBytes;
				try {
					inputBytes = HandleFile.readFile(inputFilePath);
					byte[] keyBytes = key.getBytes();
					byte[] encryptedBytes = AES.encrypt(inputBytes, keyBytes);
					HandleFile.writeFile(outputFilePath, encryptedBytes);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Encryption completed.");
				enTime.setText("ok");
			}
		});
		decryptButton = new JButton("Decrypt");
		decryptButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//				String inputFilePath = deFile.getText();
//				String outputFilePath = dedFile.getText();
//				String key = deKey.getText();
//				byte[] inputBytes;
//				try {
//					inputBytes = HandleFile.readFile(inputFilePath);
//					byte[] keyBytes = key.getBytes();
//	    			  byte[] decryptedBytes = AES.decrypt(inputBytes, keyBytes);
//	    			  HandleFile.writeFile(outputFilePath, decryptedBytes);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				System.out.println("Encryption completed.");
				deTime.setText("ok");
			}
		});
		encryptButton.setBackground(redButton);
		decryptButton.setBackground(greenButton);

		// Initialize panels
		panelEncryption = new CryptionPanel(encryptButton, enFile, endFile, enKey, enProgressBar, enTime);
		panelDecryption = new CryptionPanel(decryptButton, deFile, dedFile, deKey, deProgressBar, deTime);

		// Create tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Encryption", panelEncryption);
		tabbedPane.addTab("Decryption", panelDecryption);

		// Add tabbed pane to frame
		getContentPane().add(tabbedPane);

		// Other configurations and event handling..
	}

	// Custom JPanel for Encryption/Decryption
	private class CryptionPanel extends JPanel {
		public CryptionPanel(JButton button, JTextField file, JTextField newFile, JTextField key,
				final JProgressBar progressBar, JTextField timeProcess) {
			setLayout(new BorderLayout());

			// Initialize components
			GridBagConstraints gbc = new GridBagConstraints();

			// Add components to panel
			JPanel panelMaterial = new JPanel(new GridBagLayout());
			panelMaterial.setBorder(new CompoundBorder(new TitledBorder("Input"), new EmptyBorder(4, 4, 4, 4)));
			gbc.anchor = GridBagConstraints.EAST;
			gbc.insets.set(10, 10, 10, 10);
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelMaterial.add(new JLabel("File path :"), gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			panelMaterial.add(new JLabel("New file :"), gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			panelMaterial.add(new JLabel("Enter key :"), gbc);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1.0;
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelMaterial.add(file, gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			panelMaterial.add(newFile, gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			panelMaterial.add(key, gbc);

			JPanel panelWrap = new JPanel(new GridBagLayout());
			JPanel panelProcess = new JPanel();
			panelProcess.setBorder(new CompoundBorder(new TitledBorder("Process"), new EmptyBorder(4, 4, 4, 4)));
			panelProcess.add(progressBar);
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelWrap.add(button, gbc);
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelWrap.add(panelProcess, gbc);

			JPanel panelResult = new JPanel(new GridBagLayout());
			panelResult.setBorder(new CompoundBorder(new TitledBorder("Result"), new EmptyBorder(4, 4, 4, 4)));
			gbc.fill = GridBagConstraints.NONE;
			gbc.weightx = 0;
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelResult.add(new JLabel("Time :"), gbc);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1;
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelResult.add(timeProcess, gbc);

			// Add components to the panel
			add(panelMaterial, BorderLayout.NORTH);
			add(panelWrap, BorderLayout.CENTER);
			add(panelResult, BorderLayout.SOUTH);

			button.setFont(font);
			button.setBorder(redBorder);
			button.setFocusable(false);
			timeProcess.setEditable(false);
			timeProcess.setBackground(null);
			timeProcess.setBorder(null);

//			button.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					Timer timer = new Timer(100, new ActionListener() {
//						int progressValue = 0;
//
//						public void actionPerformed(ActionEvent e) {
//							if (progressValue <= 100) {
//								progressBar.setValue(progressValue);
//								progressValue++;
//							} else {
//								((Timer) e.getSource()).stop();
//							}
//						}
//					});
//
//					timer.start();
//				};
//			});
		}
	}

	private void performEncryption() {
//        System.out.printf("click");
//        timeProcess.setText(fileTextField.getText());
	}

	// Other methods and configurations...

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
