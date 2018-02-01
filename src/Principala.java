import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.ActionEvent;

public class Principala extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Principala(JPanel main, CardLayout cards, JFrame frame_super) {
		setBounds(100, 100, 340, 460);
		
		//Butonul "Importare Date" ne duce pe pagina de unde alegem fisierele de input
		JButton btnManageFiles = new JButton("Importare Date");
		btnManageFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(main, "loadfiles");
				JOptionPane.showMessageDialog(null, "Selectati cele 3 fisiere: produse, facturi si taxe, apoi apasati butonul de Manage");
			}
		});
		btnManageFiles.setBounds(75, 85, 177, 37);
		add(btnManageFiles);
		
		//Butonul "Gestionare Produse" ne duce pe pagina creata pentru acest scop
		JButton btnManageProducts = new JButton("Gestionare Produse");
		btnManageProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Verific in primul rand daca au fost importate fisierele necesare
				Gestiune g = Gestiune.getInstance();
				if (g.produse_file != null)
				{
					cards.show(main, "productmanage");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Nu ati importat fisierele de input!");
				}
			}
		});
		btnManageProducts.setBounds(75, 165, 177, 37);
		add(btnManageProducts);
		
		//Butonul "Statistici" genereaza statisticile (cerute in enunt)
		//si apeleaza o fereastra JDialog in care le afiseaza
		JButton btnStatistici = new JButton("Statistici");
		btnStatistici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Gestiune g = Gestiune.getInstance();
				if (g.produse_file != null)
				{
					//In stringul stats vom avea salvata statisticile
					String stats = "";
					stats += "Magazinul cu cele mai mari vanzari:";
					stats += '\n';
					
					//Sortez magazinele dupa total pentru a-l selecta pe cel mai mare
					Collections.sort(g.magazine, new Comparator<Magazin>()
					{
						public int compare(Magazin m1, Magazin m2)
						{
							if (m1.getTotalCuTaxe() > m2.getTotalCuTaxe())
								return 1;
							if (m1.getTotalCuTaxe() < m2.getTotalCuTaxe())
								return -1;
							return 0;
						}
					});
					//Adaug magazinul la string:
					stats += g.magazine.get(g.magazine.size()-1).nume;
					stats += '\n';
					stats += "Total fara taxe: ";
					stats += g.magazine.get(g.magazine.size()-1).getTotalFaraTaxe();
					stats += '\n';
					stats += "Total cu taxe: ";
					stats += g.magazine.get(g.magazine.size()-1).getTotalCuTaxe();
					stats += '\n';
					stats += "Total cu taxe scutite: ";
					stats += g.magazine.get(g.magazine.size()-1).getTotalCuTaxeScutite();
					stats += '\n';
					stats += '\n';
					
					//Iau tarile
					ArrayList<String> tari = g.tari_in_taxe();
					
					stats += "Magazinul cu cele mai mari vanzari pentru fiecare tara:";
					for (int i = 0; i < tari.size(); i ++)
					{
						//Pentru fiecare tara voi selecta magazinul cu totalul mai mare
						String tara = tari.get(i);
						Collections.sort(g.magazine, new Comparator<Magazin>()
						{
							public int compare(Magazin m1, Magazin m2)
							{
								if (m1.getTotalTaraCuTaxe(tara) > m2.getTotalTaraCuTaxe(tara))
									return 1;
								if (m1.getTotalTaraCuTaxe(tara) < m2.getTotalTaraCuTaxe(tara))
									return -1;
								return 0;
							}
						});
						//Afisez magazinul pentru tara curenta:
						stats += '\n';
						stats += tara;
						stats += '\n';
						stats += g.magazine.get(g.magazine.size()-1).nume;
						stats += '\n';
						stats += "Total fara taxe: ";
						stats += g.magazine.get(g.magazine.size()-1).getTotalFaraTaxe();
						stats += '\n';
						stats += "Total cu taxe: ";
						stats += g.magazine.get(g.magazine.size()-1).getTotalCuTaxe();
						stats += '\n';
						stats += "Total cu taxe scutite: ";
						stats += g.magazine.get(g.magazine.size()-1).getTotalCuTaxeScutite();
						stats += '\n';
					}
					stats += '\n';
					stats += "Magazinul cu cele mai mari vanzari pentru fiecare categorie:";
					ArrayList<String> categorii = g.getCategorii();
					for (int i = 0; i < categorii.size(); i ++)
					{
						//Similar doar ca acum facem pentru categorii
						String categ = categorii.get(i);
						Collections.sort(g.magazine, new Comparator<Magazin>()
						{
							public int compare(Magazin m1, Magazin m2)
							{
								if (MediumMarket.totalCategorie(m1, categ) > MediumMarket.totalCategorie(m2, categ))
									return 1;
								if (MediumMarket.totalCategorie(m1, categ) < MediumMarket.totalCategorie(m2, categ))
									return -1;
								return 0;
							}
						});
						stats += '\n';
						stats += categ;
						stats += '\n';
						stats += g.magazine.get(g.magazine.size()-1).nume;
						stats += '\n';
						stats += "Total fara taxe: ";
						stats += g.magazine.get(g.magazine.size()-1).getTotalFaraTaxe();
						stats += '\n';
						stats += "Total cu taxe: ";
						stats += g.magazine.get(g.magazine.size()-1).getTotalCuTaxe();
						stats += '\n';
						stats += "Total cu taxe scutite: ";
						stats += g.magazine.get(g.magazine.size()-1).getTotalCuTaxeScutite();
						stats += '\n';
					}
					stats += '\n';
					
					//Adaug la stringul de afisare factura maxima:
					Factura fact_max = g.getMaxFactura();
					stats += fact_max.toString();
					
					Statistici dialog = new Statistici(stats);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Nu ati importat fisierele de input!");
				}
			}
		});
		btnStatistici.setBounds(75, 247, 177, 37);
		add(btnStatistici);
		
		//Butonul de "Inchidere" are ca scop inchiderea aplicatiei
		JButton btnInchidere = new JButton("Inchidere");
		btnInchidere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame_super.dispose();
			}
		});
		btnInchidere.setBounds(103, 334, 114, 25);
		add(btnInchidere);
		
		//Imaginea de fundal:
		JLabel label = new JLabel();
		label.setBounds(0, 0, 340, 460);
		Image img = new ImageIcon(this.getClass().getResource("register_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		add(label);
		setLayout(null);
	}
}
