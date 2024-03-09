package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelEncryption, panelDecryption;
    private JButton encryptButton, decryptButton;
    private JTextArea inputTextArea, outputTextArea, resultTextField;
    private JTextField keyTextField, fileTextField, newFileTextField, timeProcess;

    public View() {
        setTitle("AES Encryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 360);
        setLocationRelativeTo(null);

        // initial component
        panelEncryption = new JPanel();
        panelEncryption.setLayout(new BorderLayout());
        
        panelDecryption = new JPanel();
        panelDecryption.setLayout(new BorderLayout());
        
        JPanel panelMaterial = new JPanel(new GridBagLayout());
        panelMaterial.setBorder(new CompoundBorder(new TitledBorder("Input"), new EmptyBorder(4, 4, 4, 4)));
        
        JPanel panelProcess = new JPanel();
        panelProcess.setBorder(new CompoundBorder(new TitledBorder("Process"), new EmptyBorder(4, 4, 4, 4)));
        
        JPanel panelWrap = new JPanel(new GridBagLayout());
        
        JPanel panelResult = new JPanel(new GridBagLayout());
        panelResult.setBorder(new CompoundBorder(new TitledBorder("Result"), new EmptyBorder(4, 4, 4, 4)));
        
        fileTextField = new JTextField();
        newFileTextField = new JTextField();
        keyTextField = new JTextField();
        timeProcess = new JTextField();
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        
        JTabbedPane tabbedPane = new JTabbedPane();
        GridBagConstraints gbc = new GridBagConstraints();
        final JProgressBar progressBar = new JProgressBar(0, 100);
        
        // material        
        Color redButton = new Color(231, 76, 60);
        Color greenButton = new Color(46, 204, 113);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        Border redBorder = new LineBorder(Color.BLUE, 1);
        
        // draw input panel
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
        panelMaterial.add(fileTextField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelMaterial.add(newFileTextField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelMaterial.add(keyTextField, gbc);
        
        // draw process panel
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
        panelProcess.add(progressBar);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelWrap.add(encryptButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelWrap.add(panelProcess, gbc);
        
        // add component to encryption panel
        panelEncryption.add(panelMaterial, BorderLayout.NORTH);
        panelEncryption.add(panelWrap, BorderLayout.CENTER);
        panelEncryption.add(panelResult, BorderLayout.SOUTH);

        // Tạo textarea cho dữ liệu đầu ra
        outputTextArea = new JTextArea();
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        JScrollPane scrollPaneOutput = new JScrollPane(outputTextArea);
        

        // Thêm các thành phần vào panel giải mã
//        panelDecryption.add(new JLabel("Enter key:"), BorderLayout.WEST);
//        panelDecryption.add(keyTextField, BorderLayout.EAST);
//        panelDecryption.add(decryptButton, BorderLayout.SOUTH);
//        panelDecryption.add(new JLabel("Result:"), BorderLayout.SOUTH);
//        panelDecryption.add(resultTextField, BorderLayout.SOUTH);
        panelDecryption.add(scrollPaneOutput, BorderLayout.CENTER);
        panelDecryption.add(decryptButton, BorderLayout.SOUTH);

        // Tạo tabbed pane để chuyển đổi giữa mã hóa và giải mã
        
        tabbedPane.addTab("Encryption", panelEncryption);
        tabbedPane.addTab("Decryption", panelDecryption);

        // Thêm tabbed pane vào frame
        getContentPane().add(tabbedPane);

        // Cài đặt font và màu sắc
        
        
        
        // change in visible
//        inputTextArea.setFont(font);
//        outputTextArea.setFont(font);
        encryptButton.setFont(font);
        decryptButton.setFont(font);
        encryptButton.setBackground(redButton);
        decryptButton.setBackground(greenButton);
        encryptButton.setBorder(redBorder);
        decryptButton.setBorder(redBorder);
        encryptButton.setFocusable(false);
        decryptButton.setFocusable(false);
        timeProcess.setEditable(false);
        timeProcess.setBackground(null);
        timeProcess.setBorder(null);
        
        // handle data
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Gọi hàm mã hóa ở đây
                // Ví dụ: encryptData(inputTextArea.getText());
            	 Timer timer = new Timer(100, new ActionListener() {
                     int progressValue = 0;

                     public void actionPerformed(ActionEvent e) {
                         if (progressValue <= 100) {
                        	 progressBar.setValue(progressValue);
                             progressValue++;
                         } else {
                             ((Timer) e.getSource()).stop();
                         }
                     }
                 });

                 timer.start();
            }
        });
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Gọi hàm giải mã ở đây
                // Ví dụ: decryptData(outputTextArea.getText());
            }
        });
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

//Tạo textarea cho dữ liệu đầu vào
//inputTextArea = new JTextArea();
//inputTextArea.setLineWrap(true);
//inputTextArea.setWrapStyleWord(true);
//JScrollPane scrollPaneInput = new JScrollPane(inputTextArea);

// Tạo JTextField để hiển thị kết quả
//resultTextField = new JTextArea();
//resultTextField.setEditable(false);
//resultTextField.setLineWrap(true);  // Thêm dòng này
//resultTextField.setWrapStyleWord(true);  // Thêm dòng này
//resultTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
