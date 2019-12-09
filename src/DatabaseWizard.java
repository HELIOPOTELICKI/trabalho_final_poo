/**
 * Trabalho Final - POO
 * Equipe: Hélio Potelicki & Luis Felipe Zaguini 
 */

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class DatabaseWizard extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	private JTextField textField_Usuario;
	private JTextField textField_Senha;
	private JTextField textField_Host;
	private JTextField textField_Database;

	public DatabaseWizard(Application application, String defaultHost, String defaultDbName, String defaultDbUser, String defaultDbPassword) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Conectar ao banco de dados");
		setBounds(100, 100, 520, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel connect_BD = new JPanel();
		connect_BD.setBounds(0, 0, 504, 263);
		contentPane.add(connect_BD);
		connect_BD.setLayout(null);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("User:");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewJgoodiesTitle.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));
		lblNewJgoodiesTitle.setBounds(10, 44, 178, 32);
		connect_BD.add(lblNewJgoodiesTitle);
		
		JLabel lblNewJgoodiesTitle_1 = DefaultComponentFactory.getInstance().createTitle("Password:");
		lblNewJgoodiesTitle_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewJgoodiesTitle_1.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));
		lblNewJgoodiesTitle_1.setBounds(4, 81, 184, 32);
		connect_BD.add(lblNewJgoodiesTitle_1);
		
		JLabel lblNewJgoodiesTitle_2 = DefaultComponentFactory.getInstance().createTitle("Host:");
		lblNewJgoodiesTitle_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewJgoodiesTitle_2.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));
		lblNewJgoodiesTitle_2.setBounds(10, 11, 178, 32);
		connect_BD.add(lblNewJgoodiesTitle_2);
		
		JLabel lblDatabase = DefaultComponentFactory.getInstance().createTitle("Database:");
		lblDatabase.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDatabase.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));
		lblDatabase.setBounds(3, 120, 183, 32);
		connect_BD.add(lblDatabase);
		
		textField_Usuario = new JTextField();
		textField_Usuario.setBounds(198, 52, 239, 20);
		connect_BD.add(textField_Usuario);
		textField_Usuario.setColumns(10);
		textField_Usuario.setText(defaultDbUser);
		
		textField_Senha = new JTextField();
		textField_Senha.setBounds(200, 89, 239, 20);
		connect_BD.add(textField_Senha);
		textField_Senha.setColumns(10);
		textField_Usuario.setText(defaultDbPassword);
		
		textField_Host = new JTextField();
		textField_Host.setBounds(200, 19, 239, 20);
		connect_BD.add(textField_Host);
		textField_Host.setColumns(10);
		textField_Usuario.setText(defaultHost);
		
		textField_Database = new JTextField();
		textField_Database.setBounds(198, 128, 239, 20);
		connect_BD.add(textField_Database);
		textField_Database.setColumns(10);
		textField_Usuario.setText(defaultDbName);
		
		JButton btnConectar = new JButton("CONECTAR");
		btnConectar.setBounds(200, 182, 113, 44);
		connect_BD.add(btnConectar);
		btnConectar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					application.loadDatabase(
						textField_Host.getText(),
						textField_Database.getText(),
						textField_Usuario.getText(),
						textField_Senha.getText()
					);
					
					application.onConnectionGranted(
						textField_Host.getText(),
						textField_Database.getText(),
						textField_Usuario.getText(),
						textField_Senha.getText()
					);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Falha ao conectar no banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		
		setVisible(true);
	}
}
