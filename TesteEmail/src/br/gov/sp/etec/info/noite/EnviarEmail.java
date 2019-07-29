package br.gov.sp.etec.info.noite;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class EnviarEmail extends JFrame {

	private JPanel contentPane;
	private JTextField txtPara;
	private JTextField txtAssunto;
	private JTextArea txtTexto;
	private String caminho;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnviarEmail frame = new EnviarEmail(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EnviarEmail(Email email) {
		
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			System.err.println(ex);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 791, 560);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtPara = new JTextField();
		txtPara.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtPara.setBounds(139, 38, 516, 20);
		panel.add(txtPara);
		txtPara.setColumns(10);
		
		JLabel lblPara = new JLabel("DESTINATARIO:");
		lblPara.setBounds(30, 41, 89, 14);
		panel.add(lblPara);
		
		txtAssunto = new JTextField();
		txtAssunto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtAssunto.setColumns(10);
		txtAssunto.setBounds(139, 85, 516, 20);
		panel.add(txtAssunto);
		
		JLabel lblAssunto = new JLabel("ASSUNTO:");
		lblAssunto.setBounds(30, 88, 63, 14);
		panel.add(lblAssunto);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(30, 180, 736, 331);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 738, 371);
		panel_1.add(scrollPane);
		
		txtTexto = new JTextArea();
		txtTexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		scrollPane.setViewportView(txtTexto);
		
		JButton btnEnviar = new JButton("ENVIAR");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					enviarEmail(email, caminho);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnEnviar.setBounds(30, 526, 89, 23);
		panel.add(btnEnviar);
		
		JLabel lblTexto = new JLabel("TEXTO:");
		lblTexto.setBounds(31, 155, 46, 14);
		panel.add(lblTexto);
		
		JButton btnAnexar = new JButton("ANEXAR");
		btnAnexar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();  
			       int returnVal = chooser.showOpenDialog(null);  
			       if(returnVal == JFileChooser.APPROVE_OPTION) {  
			           File arq = chooser.getSelectedFile();  
			           caminho = arq.getPath().toString();  
			       }
			}
		});
		btnAnexar.setBounds(171, 526, 89, 23);
		panel.add(btnAnexar);
	}
	
	private void enviarEmail(Email email, String caminho) throws SQLException {
		email.setEmailDestinatario(txtPara.getText());
		email.setAssuntoEmail(txtAssunto.getText());
		email.setTextoEmail(txtTexto.getText());
		
		JavaMailApp javaMail = new JavaMailApp();
		
		javaMail.enviarEmail(email, caminho);
		
		
	}
}
