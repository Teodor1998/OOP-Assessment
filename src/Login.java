import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Aici se va citi utilizatorul:
	private JTextField textField;
	//Aici se va citi parola:
	private JPasswordField passwordField;
	
	//key = utilizator; value = parola
	TreeMap<String, String> accounts;

	public Login(JPanel main, CardLayout cards) {
		//Dimensiune sincronizata cu panoul principal din main(frame)
		setBounds(100, 100, 340, 460);
		setLayout(null);
		
		
		textField = new JTextField();
		textField.setBounds(114, 119, 173, 19);
		add(textField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(114, 174, 173, 19);
		add(passwordField);
		
		//Afisare Utilizator:
		JLabel lblUsername = new JLabel("Utilizator:");
		lblUsername.setForeground(Color.blue);
		lblUsername.setBounds(28, 121, 86, 15);
		add(lblUsername);
		
		//Afisare Parola:
		JLabel lblGu = new JLabel("Parola:");
		lblGu.setForeground(Color.blue);
		lblGu.setBounds(28, 176, 86, 15);
		add(lblGu);
		

		//Butonul de autentificare/login:
		JButton btnLogin = new JButton("Autentificare");
		btnLogin.setBounds(28, 288, 123, 25);
		add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accounts = new TreeMap<String, String>();
				
				//Citesc informatiile referitoare la conturile existente:
				File accounts_txt = new File("accounts.txt");
				try {
					Scanner scan = new Scanner(accounts_txt);
					while (scan.hasNextLine())
					{
						String user = scan.nextLine();
						String parola = scan.nextLine();
						
						//Adaug la dictionarul de conturi:
						accounts.put(user, parola);
					}
					scan.close();
					
					//Verific daca exista User-ul introdus:
					if (accounts.containsKey(textField.getText()))
					{
						//Verific daca parola introdusa este corecta:
						if (accounts.get(textField.getText()).equals(passwordField.getText()))
						{
					        cards.show(main, "principala");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Parola introdusa este gresita!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Nu exista acest utilizator!");
					}
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Butonul de inregistrare/register
		JButton btnRegister = new JButton("Inregistrare");
		btnRegister.setBounds(189, 288, 123, 25);
		add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deschide(aduce in fata) panelul de inregistrare
				cards.show(main, "register");
			}
		});
		
		//Adaug o imagine de fundal panoului (cu ajutorul unui label)
		JLabel label = new JLabel();
		Image img = new ImageIcon(this.getClass().getResource("start_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(0, 0, 340, 460);
		add(label);
	}
}
