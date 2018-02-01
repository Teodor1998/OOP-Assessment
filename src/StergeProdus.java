import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class StergeProdus extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JButton btnAdaugarePret;
	private JLabel lblProdus;
	private JButton btnInapoi;

	public StergeProdus(JPanel main, CardLayout cards) {
		setBounds(100, 100, 340, 460);
		
		textField = new JTextField();
		textField.setBounds(96, 132, 203, 19);
		add(textField);
		textField.setColumns(10);
		
		btnAdaugarePret = new JButton("Stergere Produs");
		btnAdaugarePret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().isEmpty())
				{

					Gestiune g = Gestiune.getInstance();

					ArrayList<String> tari = g.tari_in_taxe();
					g.sortProduse(1); //Le sortam dupa nume
					boolean nu_a_fost_gasit = false;
					for (int i = 0; i < g.produse.size(); i ++)
					{
						if (g.produse.get(i).getDenumire().equals(textField.getText()))
						{
							//Odata gasit, stergem produsul (cu toate preturile pentru tari)
							for (int j = 0; j < tari.size(); j ++)
							{
								g.produse.remove(i);
							}
							try {
								String new_line = "";
								new_line += '\n';
								
								//Afisam produsele (fara cel sters)
								FileWriter out = new FileWriter(g.produse_file);
								out.write("Produs Categorie");
								for (int j = 0; j < tari.size(); j ++)
								{
									out.write(" " + tari.get(j));
								}
								out.write(new_line);
								
								for (int j = 0; j < g.produse.size(); j +=tari.size())
								{
									out.write(g.produse.get(j).getDenumire() + " ");
									out.write(g.produse.get(j).getCategorie());
									for (int k = 0; k < tari.size(); k ++)
									{
										Double pret1 = g.produse.get(j + k).getPret(); 
										out.write(" " + pret1.toString());
									}
										out.write(new_line);
								}
								out.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Produsul a fost sters cu succes!");
							nu_a_fost_gasit = true;
							break;
						}
					}
					if (!nu_a_fost_gasit)
					{
						JOptionPane.showMessageDialog(null, "Produs inexistent!");
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
		lblProdus.setBounds(12, 134, 66, 15);
		add(lblProdus);
		
		//Intoarcerea la Gestiune Produse
		btnInapoi = new JButton("Inapoi");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
