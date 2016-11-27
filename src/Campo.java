import java.awt.*;

import javax.swing.*;

//classe che rapresenta un singolo elemento della scheda, formato da un label e da un Jtextfield
public class Campo extends JPanel {
	
	int dimension;	
	String nome_in_db;
	String field;
	JPanel left = new JPanel();
	JPanel rigth = new JPanel();
	JLabel campo;
	JTextField dato;
	
//nel costruttore è richiesto il nome del campo nel database (usato solo per le date di nascita/morte/battesimo etc, la string da inserire 
//nel label e la lunghezza del JTextField, per formattarli uno accanto all'altro un GridLayout è stato usato.
	public Campo (String nome_in_db,String field, int dimension){
		this.nome_in_db= nome_in_db;
		campo=new JLabel();
		campo.setText(field);
		campo.setHorizontalAlignment(SwingConstants.LEFT);
		
		setLayout(new GridLayout(0,2));
		
		dato= new JTextField(dimension);
		
		add(campo);
		add(dato);
		
		draw();
	}
	public void draw(){
		
		setPreferredSize(new Dimension(500, 80));
		campo.setFont (campo.getFont ().deriveFont (11.0f)); 
		campo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
	}
}
