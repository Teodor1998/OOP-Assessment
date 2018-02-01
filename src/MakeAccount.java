import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MakeAccount extends JPanel {


	private static final long serialVersionUID = 1L;
	
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	
	TreeMap<String, String> accounts;

	public MakeAccount(JPanel main, CardLayout cards) {
		setBounds(100, 100, 340, 460);
		setLayout(null);
		
		//Campul pentru utilizator:
		textField = new JTextField();
		textField.setBounds(155, 104, 173, 19);
		add(textField);
		textField.setColumns(10);
		
		//Prima introducere a parolei
		passwordField = new JPasswordField();
		passwordField.setBounds(155, 172, 173, 19);
		add(passwordField);
		
		//A doua introducere a parolei
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(155, 203, 173, 19);
		add(passwordField_1);
		
		JLabel lblUsername = new JLabel("Utilizator:");
		lblUsername.setForeground(Color.blue);
		lblUsername.setBounds(24, 106, 86, 15);
		add(lblUsername);
		
		JLabel lblGu = new JLabel("Parola:");
		lblGu.setForeground(Color.blue);
		lblGu.setBounds(24, 174, 86, 15);
		add(lblGu);
		
		JLabel lblNewLabel = new JLabel("Repetati parola:");
		lblNewLabel.setForeground(Color.blue);
		lblNewLabel.setBounds(24, 203, 127, 15);
		add(lblNewLabel);
		
		JButton btnLogin = new JButton("Creare Cont");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Va rugam introduceti un username!");
					return;
				}
				if (passwordField.getText().isEmpty() || passwordField_1.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Va rugam introduceti o parola");
					return;
				}
				//Verific daca parola a fost introdusa la fel in ambele campuri
				if (passwordField.getText().equals(passwordField_1.getText()))
				{
					accounts = new TreeMap<String, String>();
					
					//Adaug toate conturile curente la dictionar:
					File accounts_txt = new File("accounts.txt");
					try {
						Scanner scan = new Scanner(accounts_txt);
						while (scan.hasNextLine())
						{
							String user = scan.nextLine();
							String parola = scan.nextLine();
							accounts.put(user, parola);
						}
						scan.close();
						
						//Verific daca numele de utilizator este deja folosit
						if (accounts.containsKey(textField.getText()))
						{
							JOptionPane.showMessageDialog(null, "Utilizator deja existent!");
							return;
						}
						
						//Adaug noul cont la dictionarul de conturi:
						accounts.put(textField.getText(), passwordField.getText());
						
						//Scriu dictionarul in fisier:
						try {
							FileWriter out = new FileWriter("accounts.txt");
							
							String s = "";
							s += '\n';
							
							for (Map.Entry <String, String> entry : accounts.entrySet())
							{
								out.write(entry.getKey() + s);
								out.write(entry.getValue() + s);
							}
							out.close();
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						//Programul se intoarce la Autentificare:
						JOptionPane.showMessageDialog(null, "V-ati inregistrat cu succes!");
						cards.show(main, "Login");
						
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Nu ati introdus corect parola!");
				}
				
				
			}
		});
		btnLogin.setBounds(87, 319, 166, 25);
		add(btnLogin);
		
		JButton btnBackToLogin = new JButton("Inapoi");
		btnBackToLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Inapoi la Login
				cards.show(main, "Login");
			}
		});
		btnBackToLogin.setBounds(120, 356, 106, 25);
		add(btnBackToLogin);
		
		//Imaginea de fundal:
		JLabel label = new JLabel();
		Image img = new ImageIcon(this.getClass().getResource("register_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(0, 0, 340, 460);
		add(label);
	}

}
