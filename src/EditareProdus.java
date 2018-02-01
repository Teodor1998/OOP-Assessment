import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditareProdus extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JButton btnAdaugarePret;
	private JLabel lblProdus;
	private JButton btnInapoi;
	private JTextField textField_3;
	private JLabel lblCategorieNoua;

	
	public EditareProdus(JPanel main, CardLayout cards) {
		setBounds(100, 100, 340, 460);
		
		//Produsul pe care il vom modifica
		textField = new JTextField();
		textField.setBounds(136, 145, 166, 19);
		add(textField);
		textField.setColumns(10);
		
		//Noua categorie pe care o va avea
		textField_3 = new JTextField();
		textField_3.setBounds(136, 198, 166, 19);
		add(textField_3);
		textField_3.setColumns(10);
		
		//Similar cu adaugare produs vom parcurge toate tarile pentru a edita pretul
		btnAdaugarePret = new JButton("Editare produs");
		btnAdaugarePret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gestiune g = Gestiune.getInstance();
				ArrayList<String> categ = g.getCategorii();
				
				if (!textField.getText().isEmpty())
				{
					if (!textField_3.getText().isEmpty())
					{
						if (categ.contains(textField_3.getText()))
						{
							boolean gasit = false;
							for (int i = 0; i < g.produse.size(); i ++)
							{
								if (g.produse.get(i).getDenumire().equals(textField.getText()))
								{
									//Pentru fiecare aparitie a produsului (pret tara) setez categoria
									g.produse.get(i).setCategorie(textField_3.getText());
									gasit = true;
								}
							}
							//Verific daca produsul este existent
							if (gasit)
							{
								ArrayList<String> tari = g.tari_in_taxe();
								EditTara dialog = new EditTara(textField.getText(), 0, tari);
								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
								dialog.setVisible(true);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Produs inexistent!");
							}
						}
						else
						{
							String s;
							s = "Categorie inexistenta!";
							s += '\n';
							s += "Categorii existente:";
							s += '\n';
							for(int i = 0; i < categ.size(); i ++)
							{
								s += categ.get(i);
								s += '\n';
							}
							JOptionPane.showMessageDialog(null, s);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Trebuie sa alegeti o categorie!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Trebuie sa dati un nume produsului!");
				}
			}
		});
		btnAdaugarePret.setBounds(54, 292, 233, 49);
		add(btnAdaugarePret);
		
		lblProdus = new JLabel("Produs");
		lblProdus.setForeground(Color.white);
		lblProdus.setBounds(12, 147, 66, 15);
		add(lblProdus);
		
		//Inapoi la Gestionare Produse
		btnInapoi = new JButton("Inapoi");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(main, "productmanage");
			}
		});
		btnInapoi.setBounds(109, 368, 114, 25);
		add(btnInapoi);
		
		lblCategorieNoua = new JLabel("Categorie Noua");
		lblCategorieNoua.setForeground(Color.white);
		lblCategorieNoua.setBounds(12, 190, 106, 34);
		add(lblCategorieNoua);
		

		//Imaginea de fundal
		JLabel label = new JLabel();
		label.setBounds(0, 0, 340, 460);
		Image img = new ImageIcon(this.getClass().getResource("register_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		add(label);
		setLayout(null);
	}
}
