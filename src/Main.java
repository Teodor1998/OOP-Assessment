import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CardLayout layout;
	JPanel contentPane;
	CardLayout cards;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Aici incepe aplicatia!
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Main() {
		super("Store Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 460);
		//Din motive de design am preferat sa blochez dimensiunea aplicatiei:
		setResizable(false);
		//Panoul principal al frame-ului:
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		//Iconita aplicatiei
		Image img = new ImageIcon(this.getClass().getResource("app_icon.png")).getImage();
		setIconImage(img);
		
		//Construim toate Panel-urile
		//Utilizatorul va alege care este la suprafata (apasand pe butoane)
		layout = new CardLayout();
		contentPane.setLayout(layout);
		//Login e prima adaugata, deci aceasta va fi implicit la suprafata
		contentPane.add(new Login(contentPane, layout), "Login");
		contentPane.add(new MakeAccount(contentPane, layout), "register");
		contentPane.add(new Principala(contentPane, layout, this), "principala");
		contentPane.add(new LoadFiles(contentPane, layout), "loadfiles");
		contentPane.add(new ProductManage(contentPane, layout), "productmanage");
		contentPane.add(new AdaugareProdus(contentPane, layout), "adaugareprodus");
		contentPane.add(new StergeProdus(contentPane, layout), "stergeprodus");
		contentPane.add(new EditareProdus(contentPane, layout), "editareprodus");
		contentPane.add(new CautaProdus(contentPane, layout), "cautaprodus");
	}

}
