package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.fun.*;

public class View extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelEncryption, panelDecryption;
	private JButton encryptButton, decryptButton;
	private JTextField enKey, enFile, endFile, enTime;
	private JTextField deKey, deFile, dedFile, deTime;

	private Color redButton = new Color(231, 76, 60);
	private Color greenButton = new Color(46, 204, 113);
	private Color grayButton = new Color(215, 215, 215);
	private Font font = new Font("Times New Roman", Font.PLAIN, 20);
	private Border redBorder = new LineBorder(Color.BLUE, 1);

	public View() {
		setTitle("AES Encryption");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 360);
		setLocationRelativeTo(null);

		enFile = new JTextField();
		endFile = new JTextField();
		enKey = new JTextField();
		enTime = new JTextField();
		enFile.setFont(enFile.getFont().deriveFont(16.0f));
		endFile.setFont(enFile.getFont().deriveFont(16.0f));
		enKey.setFont(enFile.getFont().deriveFont(16.0f));

		deFile = new JTextField();
		dedFile = new JTextField();
		deKey = new JTextField();
		deTime = new JTextField();
		deFile.setFont(enFile.getFont().deriveFont(16.0f));
		dedFile.setFont(enFile.getFont().deriveFont(16.0f));
		deKey.setFont(enFile.getFont().deriveFont(16.0f));

		encryptButton = new JButton("  Encrypt  ");
		encryptButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!validateString(enFile) || !validateString(endFile) || !validateString(enKey)) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields!!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (enKey.getText().length() != 16) {
					JOptionPane.showMessageDialog(null, "The length of the key field must be 16", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String inputFilePath = enFile.getText();
				String outputFilePath = endFile.getText();
				String key = enKey.getText();

				String time = (String) HandleFile.awaitEncrypt(inputFilePath, outputFilePath, key);
				enTime.setText(time);
			}
		});
		decryptButton = new JButton("  Decrypt  ");
		decryptButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!validateString(deFile) || !validateString(dedFile) || !validateString(deKey)) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields!!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String inputFilePath = deFile.getText();
				String outputFilePath = dedFile.getText();
				String key = deKey.getText();

				String time = (String) HandleFile.awaitDecrypt(inputFilePath, outputFilePath, key);
				deTime.setText(time);
			}
		});
		encryptButton.setBackground(redButton);
		decryptButton.setBackground(greenButton);

		// Initialize panels
		panelEncryption = new CryptionPanel(encryptButton, new JButton("  Refresh  "), enFile, endFile, enKey, enTime);
		panelDecryption = new CryptionPanel(decryptButton, new JButton("  Refresh  "),deFile, dedFile, deKey, deTime);

		// Create tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(" Encryption ", panelEncryption);
		tabbedPane.addTab(" Decryption ", panelDecryption);

		// Add tabbed pane to frame
		getContentPane().add(tabbedPane);

		// Other configurations and event handling..
	}

	// Custom JPanel for Encryption/Decryption
	private class CryptionPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CryptionPanel(JButton mainButton, JButton refreshButton, final JTextField file, 
				final JTextField newFile, final JTextField key, final JTextField timeProcess) {
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
			
			ButtonChooseFile chooseFile = new ButtonChooseFile(file);
			gbc.weightx = 0;
			gbc.gridx = 2;
			gbc.gridy = 0;
			panelMaterial.add(chooseFile, gbc);

			JPanel panelWrap = new JPanel(new GridBagLayout());
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelWrap.add(mainButton, gbc);
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelWrap.add(refreshButton, gbc);

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

			mainButton.setFont(font);
			mainButton.setBorder(redBorder);
			mainButton.setFocusable(false);
			refreshButton.setFont(font);
			refreshButton.setBorder(redBorder);
			refreshButton.setFocusable(false);
			refreshButton.setBackground(grayButton);
			timeProcess.setEditable(false);
			timeProcess.setBackground(null);
			timeProcess.setBorder(null);

			refreshButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					file.setText("");
					newFile.setText("");
					key.setText("");
					timeProcess.setText("");
				};
			});
		}
	}
	
	private class ButtonChooseFile extends JButton {
		public ButtonChooseFile(final JTextField text) {
			this.setText(" Choose ");
//			this.setFont(font);
			this.setBorder(redBorder);
			this.setFocusable(false);
			this.setBackground(grayButton);
	        final JFileChooser fileChooser = new JFileChooser();
	        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("(*.txt)", "txt");
	        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("(*.docx/doc/pdf)", "docx", "doc", "pdf");
	        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("(*.jpg/png/jpe)", "jpg", "png", "jpe");
	        FileNameExtensionFilter filter4 = new FileNameExtensionFilter("(*.aes/enc)", "aes", "enc");
	        // Thêm các bộ lọc vào fileChooser
	        fileChooser.addChoosableFileFilter(filter1);
	        fileChooser.addChoosableFileFilter(filter2);
	        fileChooser.addChoosableFileFilter(filter3);
	        fileChooser.addChoosableFileFilter(filter4);
	        
	        addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    int returnValue = fileChooser.showOpenDialog(null);
				    if (returnValue == JFileChooser.APPROVE_OPTION) {
				        // Người dùng đã chọn một tệp
				        java.io.File selectedFile = fileChooser.getSelectedFile();
				        text.setText(selectedFile.getAbsolutePath());;
				        return;
				    }
				    return;
				}
			});
	    }
	}
	
	private boolean validateString(JTextField text) {
		if(text.getText().isEmpty()) {
			return false;
		}
		return true;
	}

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
