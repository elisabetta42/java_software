import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class Lista_Religiosi extends JPanel{

	String dateString;
	String dateString1;

	JPanel myList= new JPanel();

	ArrayList<JPanel> array= new ArrayList<JPanel>();
	JPanel empty= new JPanel();
	JLabel c= new JLabel("COGNOME");

	JLabel n= new JLabel("NOME");
	JLabel b= new JLabel("ESTREMO INFERIORE");
	JLabel a= new JLabel("ESTREMO SUPERIORE");
	JButton indietro= new JButton("Indietro");
	JFrame f;
	String mio_cognome;

	public Lista_Religiosi(JFrame f,String mio_cognome){
		this.mio_cognome=mio_cognome;
		this.f=f;
		//	setPreferredSize(new Dimension(800, 600));

		empty.setLayout(new GridLayout(0,5));

		indietro.addActionListener(new IndietroListener());
		JPanel m= new JPanel();
		c.setFont (c.getFont ().deriveFont (13.0f)); 
		empty.add(c);
		n.setFont (n.getFont ().deriveFont (13.0f)); 
		empty.add(n);
		b.setFont (b.getFont ().deriveFont (13.0f)); 
		empty.add(b);
		a.setFont (a.getFont ().deriveFont (13.0f)); 
		empty.add(a);
		empty.add(indietro);
		add(empty);
		ResultSet r;
		try {
			r = DBConnection.prendi_religioso("SELECT id_prete,cognome,nome,data_piu_bassa,data_piu_alta"
					+ " FROM religiosi,status WHERE cognome LIKE"+"'"+"%"+mio_cognome+"%"+"'"+"and id_prete=status_id_prete ORDER BY data_piu_bassa");


			while(r.next()){

				JPanel n= new JPanel();
				Indice i= new Indice(r.getInt("id_prete"),r.getString("cognome"),r.getString("nome"),
						r.getDate("data_piu_bassa"),r.getDate("data_piu_alta"),f,mio_cognome);
				array.add(i);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

		for(int i=0;i<array.size();i++){
			add(array.get(i));
		}
		setLayout(new GridLayout(20,0));
	}
	private class IndietroListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			Main_Panel s= new  Main_Panel();
			s.setSize(new Dimension(600, 500));
			s.setVisible(true);
			ImageIcon image = new ImageIcon("C:/Users/User03/Dropbox/preti/maschera.png");
			s.setIconImage(image.getImage());
			f.dispose();
		}
	}
}



