import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductManage extends JPanel {

	private static final long serialVersionUID = 1L;

	public ProductManage(JPanel main, CardLayout cards) {
		setBounds(100, 100, 340, 460);
		
		JButton btnAfisareProduse = new JButton("Afisare Produse");
		btnAfisareProduse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deschid Dialog-ul de afisare a produselor
				ProduseOrdonate p = new ProduseOrdonate();
				p.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				p.setVisible(true);
			}
		});
		btnAfisareProduse.setBounds(82, 44, 169, 37);
		add(btnAfisareProduse);
		
		JButton btnAdaugareProdus = new JButton("Adaugare Produs");
		btnAdaugareProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Afisam panelul de adaugare produs
				cards.show(main, "adaugareprodus");
			}
		});
		btnAdaugareProdus.setBounds(82, 106, 169, 37);
		add(btnAdaugareProdus);
		
		JButton btnInapoi = new JButton("Inapoi");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Ne intoarcem la pagina principala
				cards.show(main, "principala");
			}
		});
		btnInapoi.setBounds(112, 360, 114, 25);
		add(btnInapoi);
		
		JButton btnStergeProdus = new JButton("Sterge Produs");
		btnStergeProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deschidem pagina de stergere a unui produs
				cards.show(main, "stergeprodus");
			}
		});
		btnStergeProdus.setBounds(82, 166, 169, 37);
		add(btnStergeProdus);
		
		JButton btnEditeazaProdus = new JButton("Editeaza Produs");
		btnEditeazaProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deschidem pagina de editare a unui produs
				cards.show(main, "editareprodus");
			}
		});
		btnEditeazaProdus.setBounds(82, 231, 169, 37);
		add(btnEditeazaProdus);
		
		JButton btnCautaProdus = new JButton("Cauta Produs");
		btnCautaProdus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pagina de cautare produs
				cards.show(main, "cautaprodus");
			}
		});
		btnCautaProdus.setBounds(82, 295, 169, 34);
		add(btnCautaProdus);
		
		//Imaginea de fundal
		JLabel label = new JLabel();
		label.setBounds(0, 0, 340, 460);
		Image img = new ImageIcon(this.getClass().getResource("register_background.jpg")).getImage();
		label.setIcon(new ImageIcon(img));
		add(label);
		setLayout(null);
	}

}
