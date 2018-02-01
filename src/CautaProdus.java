import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class CautaProdus extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public CautaProdus(JPanel main, CardLayout cards) {
		setBounds(100, 100, 340, 460);
		
		//Cautam Produsul si afisam date despre acesta
		JButton btnCautareProdus = new JButton("Cautare Produs");
		btnCautareProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().isEmpty())
				{
					Gestiune g = Gestiune.getInstance();
					g.sortProduse(1); //Sortare dupa nume
					boolean gasit = false;
					
					String s = "";
					for (int i = 0; i < g.produse.size(); i ++)
					{
						if (g.produse.get(i).getDenumire().equals(textField.getText()))
						{
							gasit = true;
							s = "Produs gasit!";
							s += '\n';
							ArrayList<String> tari = g.tari_in_taxe();
							s += "Produsul poate proveni din " + tari.size() +" tari:";
							for (int j = 0; j < tari.size(); j ++)
							{
								s += '\n';
								s += g.produse.get(i + j).toString();
							}
							break;
						}
					}
					if (gasit)
					{
						//Daca a fost gasit afisam date despre acesta:
						JOptionPane.showMessageDialog(null, s);
					}
					if (!gasit)
					{
						JOptionPane.showMessageDialog(null, "Produs inexistent!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Scrieti un nume de produs!");
				}
			}
		});
		btnCautareProdus.setBounds(91, 240, 164, 37);
		add(btnCautareProdus);
		
		textField = new JTextField();
		textField.setBounds(72, 178, 203, 19);
		add(textField);
		
		JButton btnInapoi = new JButton("Inapoi");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(main, "productmanage");
			}
		});
		btnInapoi.setBounds(112, 298, 114, 25);
		add(btnInapoi);
		
		JLabel lblNumeProdus = new JLabel("Nume Produs");
		lblNumeProdus.setForeground(Color.white);
		lblNumeProdus.setBounds(72, 151, 102, 15);
		add(lblNumeProdus);
		
		//Imaginea de fundal:
		JLabel label = new JLabel();
		label.setBounds(0, 0, 340, 460);
		Image img = new ImageIcon(this.getClass().getResource("register_background.jpg")).getImage();
		setLayout(null);
		label.setIcon(new ImageIcon(img));
		add(label);
	}

}
