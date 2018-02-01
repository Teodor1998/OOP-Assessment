import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AdaugareProdus extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JButton btnAdaugarePret;
	private JLabel lblProdus;
	private JLabel lblCategorie;
	private JButton btnInapoi;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public AdaugareProdus(JPanel main, CardLayout cards) {
		setBounds(100, 100, 340, 460);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//Nume produs
		textField = new JTextField();
		textField.setBounds(94, 108, 203, 19);
		add(textField);
		textField.setColumns(10);
		
		//Categorie
		textField_2 = new JTextField();
		textField_2.setBounds(99, 184, 198, 19);
		add(textField_2);
		textField_2.setColumns(10);
		
		
		btnAdaugarePret = new JButton("Adaugare pret aferent tarilor");
		btnAdaugarePret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gestiune g = Gestiune.getInstance();
				ArrayList<String> categ = g.getCategorii();
				//Verificare daca vreunul din campuri nu e completat
				if (!textField.getText().isEmpty())
				{
					if (!textField_2.getText().isEmpty())
					{
						//Verific daca exista categoriile, daca nu le afisez
						if (g.getCategorii().contains(textField_2.getText()))
						{
							for (int i = 0; i < g.produse.size(); i ++)
							{
								if (g.produse.get(i).getDenumire().equals(textField.getText()))
								{
									JOptionPane.showMessageDialog(null, "Produs deja existent!");
									return;
								}
							}
					
							//Creez un Dialog care se va reapela pentru fiecare tara
							//Pentru a introduce pretul respectiv
							ArrayList<String> tari = g.tari_in_taxe();
							PretTara dialog = new PretTara(textField.getText(), textField_2.getText(), 0, tari);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						}
						else
						{
							String s;
							s = "Categorie inexistenta!";
							s += '\n';
							s += "Alegeti o categorie existenta:";
							s += '\n';
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
		lblProdus.setBounds(12, 110, 66, 15);
		add(lblProdus);
		
		lblCategorie = new JLabel("Categorie");
		lblCategorie.setForeground(Color.white);
		lblCategorie.setBounds(10, 186, 66, 15);
		add(lblCategorie);
		
		btnInapoi = new JButton("Inapoi");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Inapoi la pagina de administrare produse
				cards.show(main, "productmanage");
			}
		});
		btnInapoi.setBounds(109, 368, 114, 25);
		add(btnInapoi);

		//Imaginea de fundal:
		JLabel label = new JLabel();
		label.setBounds(0, 0, 340, 460);
		Image img = new ImageIcon(this.getClass().getResource("register_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		add(label);
		setLayout(null);
	}
}
