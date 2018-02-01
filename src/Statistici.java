import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Statistici extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public Statistici(String stats) {
		setBounds(100, 100, 500, 250);
		contentPanel.setLayout(new FlowLayout());
		setResizable(false);
		
		//Aici vom afisa Stringul stats
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(28, 12, 426, 169);
		textPane.setText(stats);
		
		//Pentru a adauga scrollBar la text
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(12, 12, 465, 169);
		scrollPane.setVisible(true);
		getContentPane().add(scrollPane);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(187, 183, 114, 25);
		getContentPane().add(btnOk);
		
		//Imaginea de fundal:
		JLabel label = new JLabel();
		label.setBounds(0, 0, 500, 250);
		Image img = new ImageIcon(this.getClass().getResource("produse_background.jpg")).getImage();
		getContentPane().setLayout(null);
		label.setIcon(new ImageIcon(img));
		getContentPane().add(label);
		getContentPane().setLayout(null);
	}
}
