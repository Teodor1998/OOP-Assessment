import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.ActionEvent;

public class LoadFiles extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String produse;
	private String taxe;
	private String facturi;
	
	int contor_manage = 0;
	
	public LoadFiles(JPanel main, CardLayout cards) {
		setBounds(100, 100, 340, 460);
		setLayout(null);
		
		//Butonul "Gestionare" - disponibil dupa ce au fost importate toate fisierele
		JButton btnManage = new JButton("Gestionare");
		JButton btnProduse = new JButton("Produse");
		JButton btnTaxe = new JButton("Taxe");
		JButton btnFacturi = new JButton("Facturi");
		
		btnManage.setEnabled(false);
		btnManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Vom crea instanta obiectului gestiune cu elemente din fisiere
				Gestiune g = Gestiune.replaceInstance(produse, taxe, facturi);
				g.citire_produse();
				g.parsing_taxe();
				g.parsing_facturi();
				
				Collections.sort(g.magazine, new Comparator<Magazin>()
				{
					public int compare(Magazin m1, Magazin m2)
					{
						//Ordine: MiniMarket, MediumMarket, HyperMarket
						//Iar intre cele de acelasi tip dupa totalul fara taxe
						if (m1 instanceof MiniMarket)
						{
							if (m2 instanceof MiniMarket)
							{
								if (m1.getTotalFaraTaxe() > m2.getTotalFaraTaxe())
									return 1;
								if (m1.getTotalFaraTaxe() < m2.getTotalFaraTaxe())
									return -1;
								return 0;
							}
							else return -1;
						}
						if (m2 instanceof MiniMarket)
							return 1;
						
						if (m1 instanceof MediumMarket)
						{
							if (m2 instanceof MediumMarket)
							{
								if (m1.getTotalFaraTaxe() > m2.getTotalFaraTaxe())
									return 1;
								if (m1.getTotalFaraTaxe() < m2.getTotalFaraTaxe())
									return -1;
								return 0;
							}
							else return -1;
						}
						if (m2 instanceof MediumMarket)
							return 1;

						if (m1.getTotalFaraTaxe() > m2.getTotalFaraTaxe())
							return 1;
						if (m1.getTotalFaraTaxe() < m2.getTotalFaraTaxe())
							return -1;
						return 0;

					}
				});
				
				//Pentru a nu afisa la fiecare magazin tipul lui, ci doar la primul:
				//Le initializez cu false = inca nu a fost afisat tipul de magazin
				boolean ok_mini = false;
				boolean ok_medium = false;
				boolean ok_hyper = false;
				
				try {
					FileWriter out = new FileWriter("out.txt");
					String s = "";
					s += '\n';
					int i;
					for (i = 0; i < g.magazine.size() - 1; i ++)
					{
						//Afisez tipul magazinului daca nu a mai fost afisat
						if ((g.magazine.get(i) instanceof MiniMarket) && !ok_mini)
						{
							ok_mini = true;
							out.write("MiniMarket" + s);
						}
						else if ((g.magazine.get(i) instanceof MediumMarket) && !ok_medium)
						{
							ok_medium = true;
							out.write("MediumMarket" + s);
						}
						else if ((g.magazine.get(i) instanceof HyperMarket) && !ok_hyper)
						{
							ok_hyper = true;
							out.write("HyperMarket" + s);
						}
						
						//Afisez magazinul propriu-zis
						out.write(g.magazine.get(i).toString());
						out.write(s);
					}
					
					//Afisez si ultimul magazin:
					if ((g.magazine.get(i) instanceof MiniMarket) && !ok_mini)
					{
						ok_mini = true;
						out.write("MiniMarket" + s);
					}
					else if ((g.magazine.get(i) instanceof MediumMarket) && !ok_medium)
					{
						ok_medium = true;
						out.write("MediumMarket" + s);
					}
					else if ((g.magazine.get(i) instanceof HyperMarket) && !ok_hyper)
					{
						ok_hyper = true;
						out.write("HyperMarket" + s);
					}
					out.write(g.magazine.get(i).toString());
					
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Fisierul out.txt a fost creat cu succes!");
				//Aduc butoanele la disponibilitatea initiala
				btnProduse.setEnabled(true);
				btnTaxe.setEnabled(true);
				btnFacturi.setEnabled(true);
				btnManage.setEnabled(false);
				contor_manage = 0;
				//Ma intorc la pagina principala
				cards.show(main, "principala");
			}
		});
		btnManage.setBounds(96, 319, 151, 39);
		add(btnManage);
		
		//Urmatoarele 3 butoane functioneaza pe acelasi principiu:
		btnProduse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Creez un FileChoser si adaug conditie ca fisierul sa fie .txt
				JFileChooser choose = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
				choose.setFileFilter(filter);
				int returnVal = choose.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					//Salvez calea absoluta catre fisier
					produse = choose.getSelectedFile().getAbsolutePath();
					contor_manage++;
					btnProduse.setEnabled(false);
					if (contor_manage == 3)
					{
						btnManage.setEnabled(true);
					}
				}
				
			}
		});
		btnProduse.setBounds(96, 61, 151, 39);
		add(btnProduse);
		
		btnFacturi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
				choose.setFileFilter(filter);
				int returnVal = choose.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					facturi = choose.getSelectedFile().getAbsolutePath();
					contor_manage++;
					btnFacturi.setEnabled(false);
					if (contor_manage == 3)
					{
						btnManage.setEnabled(true);
					}
				}
			}
		});
		btnFacturi.setBounds(96, 140, 151, 39);
		add(btnFacturi);
		
		btnTaxe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
				choose.setFileFilter(filter);
				int returnVal = choose.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					taxe = choose.getSelectedFile().getAbsolutePath();
					contor_manage++;
					btnTaxe.setEnabled(false);
					if (contor_manage == 3)
					{
						btnManage.setEnabled(true);
					}
				}
			}
		});
		btnTaxe.setBounds(96, 217, 151, 39);
		add(btnTaxe);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Aducem toti parametrii in forma initiala si ne intoarcem
				btnProduse.setEnabled(true);
				btnTaxe.setEnabled(true);
				btnFacturi.setEnabled(true);
				btnManage.setEnabled(false);
				cards.show(main, "principala");
			}
		});
		
		btnBack.setBounds(121, 377, 93, 25);
		add(btnBack);
		
		//Imaginea de fundal:
		JLabel label = new JLabel();
		Image img = new ImageIcon(this.getClass().getResource("register_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(0, 0, 340, 460);
		add(label);
	}
	public String getProduse()
	{
		//Functie pentru verificare (daca s-au selectat sau nu fisierele de input)
		return produse;
	}

}
