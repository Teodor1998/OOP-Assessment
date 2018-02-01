import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PretTara extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	public PretTara(String nume, String categorie, int index, ArrayList<String> tari) {
		setBounds(100, 100, 500, 250);
		contentPanel.setLayout(new FlowLayout());
		setResizable(false);
		
		//Afisam tara curenta
		JLabel lblNewLabel = new JLabel(tari.get(index));
		lblNewLabel.setForeground(Color.white);
		lblNewLabel.setBounds(91, 45, 66, 15);
		getContentPane().add(lblNewLabel);
		
		//Input pentru pretul aferent tarii
		textField = new JTextField();
		textField.setBounds(33, 91, 124, 19);
		getContentPane().add(textField);
		
		JButton btnMaiDeparte = new JButton("Mai departe");
		btnMaiDeparte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().isEmpty())
				{
					double pret = Double.parseDouble(textField.getText());
					Gestiune g = Gestiune.getInstance();
					g.produse.add(new Produs(nume, categorie, tari.get(index), pret));
					//Daca nu e ultima tara, continuam cu inca un Dialog
					if (index < tari.size() - 1)
					{
						PretTara dialog = new PretTara(nume, categorie, index + 1, tari);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
						dispose();
					}
					else
					{
						try {
							//Daca am setat pentru fiecare tara, afisam in fisier produsul
							String new_line = "";
							new_line += '\n';
							
							FileWriter out = new FileWriter(g.produse_file);
							g.sortProduse(1); //Sortez produsele dupa nume
							out.write("Produs Categorie");
							for (int i = 0; i < tari.size(); i ++)
							{
								out.write(" " + tari.get(i));
							}
							out.write(new_line);
							
							for (int i = 0; i < g.produse.size(); i +=tari.size())
							{
								out.write(g.produse.get(i).getDenumire() + " ");
								out.write(g.produse.get(i).getCategorie());
								for (int j = 0; j < tari.size(); j ++)
								{
									Double pret1 = g.produse.get(i + j).getPret(); 
									out.write(" " + pret1.toString());
								}
								out.write(new_line);
							}
							out.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Produsul a fost adaugat cu succes!");
						dispose();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Trebuie sa adaugati un pret!");
				}
			}
		});
		btnMaiDeparte.setBounds(33, 148, 116, 25);
		getContentPane().add(btnMaiDeparte);

		//Imaginea de fundal
		JLabel label = new JLabel();
		label.setBounds(0, 0, 500, 250);
		Image img = new ImageIcon(this.getClass().getResource("tari_background.jpg")).getImage();
		getContentPane().setLayout(null);
		label.setIcon(new ImageIcon(img));
		getContentPane().add(label);
		getContentPane().setLayout(null);

	}

}
