import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProduseOrdonate extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	public ProduseOrdonate() {
		setBounds(100, 100, 500, 250);
		contentPanel.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Afisam produsele sortate dupa nume
		JButton btnSortByName = new JButton("Sortare dupa nume");
		btnSortByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gestiune g = Gestiune.getInstance();
				//Functie pentru sortare
				g.sortProduse(1);
				String s = "";
				for (int i = 0; i < g.produse.size(); i ++)
				{
					s += g.produse.get(i).toString();
					s += '\n';
				}
				
				//Fereastra ce afiseaza stringul primit ca parametru
				Statistici p = new Statistici(s);
				p.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				p.setVisible(true);
				dispose();
			}
		});
		btnSortByName.setBounds(12, 35, 184, 36);
		getContentPane().add(btnSortByName);
		
		//Afisam produsele sortate dupa tara
		JButton btnSortByCountry = new JButton("Sortare dupa tara");
		btnSortByCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gestiune g = Gestiune.getInstance();
				g.sortProduse(2);
				String s = "";
				for (int i = 0; i < g.produse.size(); i ++)
				{
					s += g.produse.get(i).toString();
					s += '\n';
				}
				Statistici p = new Statistici(s);
				p.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				p.setVisible(true);
				dispose();
			}
		});
		btnSortByCountry.setBounds(12, 136, 184, 36);
		getContentPane().add(btnSortByCountry);
		
		//Imaginea de fundal
		JLabel label = new JLabel();
		label.setBounds(0, 0, 500, 250);
		Image img = new ImageIcon(this.getClass().getResource("produse_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		getContentPane().add(label);
		getContentPane().setLayout(null);
	}

}
