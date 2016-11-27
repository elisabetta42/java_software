import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Indice extends JPanel{
	Scheda my_scheda= new Scheda();
	String dateString;
	String dateString1;
	String cognome;
	String nome;
	String input;
	Date bassa;
	Date alta;
	ArrayList <Qualifica> q= new ArrayList<Qualifica>();
	ArrayList <Data_con_Registro> data= new ArrayList<Data_con_Registro>();
	JFrame f;
	long id;

	public Indice(int id,String cognome,String nome,Date bassa,Date alta,JFrame f,String input){
		this.input=input;
		this.id=id;
		this.cognome=cognome;
		this.nome=nome;
		this.bassa=bassa;
		this.alta=alta;
		this.f=f;
		draw();
	}
	public void draw(){

		JPanel m= new JPanel();
		JLabel cognome1= new JLabel();
		cognome1.setFont (cognome1.getFont ().deriveFont (14.0f)); 
		cognome1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel nome1= new JLabel();
		nome1.setFont (nome1.getFont ().deriveFont (14.0f)); 
		nome1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel basso1= new JLabel();
		basso1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		basso1.setFont (basso1.getFont ().deriveFont (14.0f)); 
		JLabel alto1= new JLabel();
		JButton modifica= new JButton("Visualizza");
		alto1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		alto1.setFont (alto1.getFont ().deriveFont (14.0f)); 
		setLayout(new GridLayout(0,5));
		cognome1.setText(cognome);
		nome1.setText(nome);
		modifica.addActionListener(new VisualizzaListner());

		SimpleDateFormat  sdfr = new SimpleDateFormat("dd-MMM-yyyy");

		try{
			dateString = sdfr.format(bassa);
		}catch (Exception ex ){
			System.out.println(ex);
		}
		basso1.setText(dateString);
		try{
			dateString1 = sdfr.format(alta);
		}catch (Exception ex ){
			System.out.println(ex);
		}
		alto1.setText(dateString1);
		add(cognome1);
		add(nome1);
		add(basso1);
		add(alto1);
		m.add(modifica);
		add(m);
	}
	public void modifica(){

		get_religioso_data();
		getDate();
		getEta_Luogo();
		getQualifiche();
		getStatus();

	}
	public void draw_GUI(){
		JFrame frame = new JFrame("RELIGIOSI");
		Data_Interface d= new  Data_Interface(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(d);
		frame.pack();
		frame.setVisible(true);
	}
	public void get_religioso_data(){
		try {

			ResultSet s=DBConnection.prendi_religioso("SELECT altri_dati, cognome,"
					+ "originale,nome,detto,soprannome,altri_nomi,nome_in_religione,padre,madre,"
					+ "titolo_di_studio,note,coniuge FROM religiosi WHERE id_prete="+"'"+id+"'");
			while(s.next()){
				my_scheda.set_ID(id);
				my_scheda.setAltro(s.getString("altri_dati"));
				my_scheda.setCognome(s.getString("cognome"));
				my_scheda.setOriginale(s.getString("originale"));
				my_scheda.setNome(s.getString("nome"));
				my_scheda.setDetto(s.getString("detto"));
				my_scheda.setSoprannome(s.getString("soprannome"));
				my_scheda.set_altri_Nomi(s.getString("altri_nomi"));
				my_scheda.setRNome(s.getString("nome_in_religione"));
				my_scheda.setPaternita(s.getString("padre"));
				my_scheda.setMaternita(s.getString("madre"));
				my_scheda.setTitolo(s.getString("titolo_di_studio"));
				my_scheda.setNote(s.getString("note"));
				my_scheda.set_Coniuge(s.getString("coniuge"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getDate(){
		try {
			ResultSet s=DBConnection.prendi_religioso("SELECT id,tipo_data,mydata,registro FROM pdata WHERE data_id_prete="+"'"+id+"'");
			while(s.next()){
				Registro r;
				if(!s.getString("registro").equals("///")){
					String [] parts= new String[3];
					parts = s.getString("registro").split("\\s*/\\s*");
					r= new Registro(parts[0],parts[1],parts[2],parts[3]);
				}
				else{
					r= new Registro("","","","");
				}
				Data_con_Registro d= new Data_con_Registro(s.getString("tipo_data"),s.getDate("mydata"),r);
				d.setID(s.getInt("id"));
				data.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		my_scheda.setRegistri(data);
	}
	public void getEta_Luogo(){
		try {
			ResultSet s= DBConnection.prendi_religioso("SELECT eta,tipo_eta ,luogo FROM eta_luogo WHERE eta_luogo_id_prete = "+"'"+id+"'");
			while(s.next()){
				my_scheda.setLuogo(s.getString("luogo"));
				my_scheda.setEta(s.getString("eta"), s.getString("tipo_eta"));
				System.out.println(s.getString("luogo")+" "+s.getString("eta")+" "+ s.getString("tipo_eta"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getQualifiche(){
		try {
			ResultSet s= DBConnection.prendi_religioso("SELECT id, qualifica,dal,al FROM qualifica  WHERE qualifica_id_prete="+"'"+id+"'"+"ORDER BY dal" );
			while(s.next()){
				Qualifica qualifica= new Qualifica();
				qualifica.setId(s.getInt("id"));
				qualifica.setStringQualifica(s.getString("qualifica"));
				qualifica.setDal(s.getDate("dal"));
				qualifica.setAl(s.getDate("al"));
				System.out.println(s.getInt("id")+s.getString("qualifica")+s.getDate("dal")+s.getDate("al"));
				q.add(qualifica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		my_scheda.setQualifica(q);
	} 

	public void getStatus(){
		try {
			ResultSet s= DBConnection.prendi_religioso("SELECT data_piu_bassa,data_piu_alta FROM status WHERE status_id_prete="+"'"+id+"'");
			while(s.next()){
				my_scheda.set_alto(s.getDate("data_piu_alta"));
				my_scheda.set_basso(s.getDate("data_piu_bassa"));
				System.out.println(""+s.getDate("data_piu_bassa")+s.getDate("data_piu_alta"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class VisualizzaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			modifica();
			JFrame frame = new JFrame("RELIGIOSI");
			Data_Modify_GUI d= new  Data_Modify_GUI(my_scheda,frame,input);


			JScrollPane scroll= new JScrollPane();
			scroll.add(d);

			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			scroll.setPreferredSize(new Dimension(820,600));
			scroll.setViewportView(d);
			scroll.getViewport().setMinimumSize(new Dimension(820,600));
			scroll.getViewport().setPreferredSize(new Dimension(820,600));

			frame.getContentPane().add(scroll);
			frame.pack();
			frame.setVisible(true);

			f.dispose();

		}
	}

}

