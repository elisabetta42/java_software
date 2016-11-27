import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.xml.soap.Text;

//Questa classe è la grafica principale, il modulo che sarà riempito con i dati da archiviare.
public class Data_Interface extends JPanel{
	/**
	 * 
	 */
	//referenza al frame del panello per poterlo chiudere quando è necessario da dentro usando f.dispose();
	JFrame f;
	//la finestra legenda per memorizzare tutte le lettere che possono apparire accanto alla prima lettera del cognome.
	JButton l=new JButton("Legenda");
	//array contenente i campi che formano il modulo, non tutti sono presenti in quest'array.
	Campo [] campi= new Campo[20];
	//campo contenente la data più bassa e la data più alta presente all'interno della scheda.
	Campo basso;
	Campo alto;
	//arraylist contenente tutte le qualifiche, pronte per essere settate nell'oggetto scheda.
	ArrayList<Qualifica>qualifica= new ArrayList<Qualifica>();
	//arraylist contenente le date con registro per essere settate nell'oggetto scheda.
	ArrayList<Data_con_Registro> date= new ArrayList<Data_con_Registro>();
	//arraylist contenente i risultati provenienti dalla tabella qualifiche per creare una specie di autocomplete field per le qualifiche.
	ArrayList <String> array= new ArrayList<String>();

	//panelli contenenti le date con i rispettivi registri
	JPanel datan = new JPanel();
	JPanel datab = new JPanel();
	JPanel datam= new JPanel();
	JPanel datas= new JPanel();
	Date nascita;
	Date battesimo;
	Date morte;
	Date sepoltura;

	//panello di sinistra contenendo tutta la scheda tranne le note.
	JPanel left= new JPanel();
	//panello di destra contenendo i pulsanti.
	JPanel rigth= new JPanel();
	//area dove inserire le note.
	JTextArea commento= new JTextArea(10,10);
	JScrollPane scroll1;
	//panello in alto contenente gli estremi.
	JPanel above= new JPanel();
	Date estremo_basso;
	Date estremo_alto;
	//panello in basso contenente le note
	JPanel below= new JPanel();
	//panello con i tre pulsanti per manipolare le qualifiche.
	JPanel qualifiche= new JPanel();
	//operazioni per manipolare le qualifiche
	JButton aggiungi;
	//Stringa contenente la qualifica quando viene aggiunta premendo aggiungi.
	String q;
	JButton memorizza;
	//combobox per simulare autocompletion nel campo qualifiche.
	JComboBox combo_qua=new JComboBox();

	JButton coniuge;
	//area dove inserire i dati del coniuge.
	JTextArea area=new JTextArea(10,40);
	JScrollPane scroll;
	//settare il testo nel coniuge.
	JButton set_Text =new JButton("Inserisci testo");
	JPanel radiobutton= new JPanel();
	//panelli vuoti per far funzionare il gridlayout.
	JPanel empty= new JPanel();
	JPanel empty2= new JPanel();
	JPanel empty3= new JPanel();
	JPanel empty4= new JPanel();
	JPanel empty5= new JPanel();

	JPanel  ok= new JPanel();
	//panello che ti appare quando clicchi aggiungi qualifica per settare dal al...
	JPanel tempo= new JPanel();
	Campo dal= new Campo("dal","Dal...................................",7);
	Campo al= new Campo("al","Al.....................................",7);
	Date dal_date;
	Date al_date;
	//panello contenente i pulsanti per creare,salvare e stampare un nuovo record.
	JPanel center;
	//pulsanti per manipolare il record
	JButton modifica=new JButton("Modifica");
	JButton salva=new JButton("Salva");
	JButton stampa;
	JButton indietro=new JButton ("Indietro");
	JButton nuovo=new JButton("Nuovo record");

	JButton ok3= new JButton("OK");

	JLabel note1= new JLabel();
	//varie finestre di dialogo 
	JDialog d= new JDialog();
	JDialog d1= new JDialog();
	JDialog d2;
	JDialog d3;
	JDialog dialog;

	//campi con textfield contenenti i vari registri delle date 
	Campo nregistropc= new Campo("","R........",2);
	JTextField nregistroarco= new JTextField();
	Campo nregistrofn= new Campo("","/",2);
	JTextField nregistrofoglio= new JTextField();

	Campo bregistropc= new Campo("","R.......",2);
	JTextField bregistroarco= new JTextField();
	Campo bregistrofn= new Campo("","/",2);
	JTextField bregistrofoglio= new JTextField();

	Campo mregistropc= new Campo("","R.......",2);
	JTextField mregistroarco= new JTextField();
	Campo mregistrofn= new Campo("","/",2);
	JTextField mregistrofoglio= new JTextField();

	Campo sregistropc= new Campo("","R........",2);
	JTextField sregistroarco= new JTextField();
	Campo sregistrofn= new Campo("","/",2);
	JTextField sregistrofoglio= new JTextField();

	//pulsante per visualizzare i campi nascosti.
	Campo altro;
	//campi in aggiunta con i campi nascosti.
	Campo c= new Campo("soprannome"," Soprannome.............................................................",30);	
	Campo c1= new Campo("detto"," Detto.....................................................................",30);
	String soprannome;
	String detto;
	//campi del cognome originale, altri nomi...
	Campo originale2;
	Campo altri_nomi;
	//campo col luogo di sepoltura.
	Campo luogo_s;
	//campo con l'età della morte.
	JTextField e;
	String tipo_eta;
	JRadioButton dichiarate;
	JRadioButton effettiva;
	//oggetto dove saranno memorizzate tutte le informazioni per essere poi messe nel database.
	Scheda scheda= new Scheda();

	//legenda
	JScrollPane scrollPane;
	JTextArea a=new JTextArea(10,40);

	public Data_Interface(JFrame f){
		this.f=f;
		//layout del panello 
		setLayout(new BorderLayout());	
		left.setPreferredSize(new Dimension(535, 290));
		left.setLayout(new GridLayout(18,0));
		//left.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		add(left,BorderLayout.CENTER);
		add(rigth,BorderLayout.EAST);
		rigth.setPreferredSize(new Dimension(265, 600));
		rigth.setLayout(new GridLayout(3,1));
		//rigth.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		creare_campi();
	}
	//aggiungere addActionListners ai vari pulsanti e aggiungere i pulsanti per manipolare i record al center panel...
	public void creare_operazioni_pulsanti(){
		center= new JPanel();

		JButton altro= new JButton("...");
		altro.setToolTipText("Premere per inserire i campi nascosti soprannome e detto");
		altro.addActionListener(new AltroListner());

		empty.setLayout(new BorderLayout());
		empty4.setPreferredSize(new Dimension(165, 74));
		empty5.setPreferredSize(new Dimension(165, 76));
		empty.add(empty4,BorderLayout.NORTH);
		empty.add(altro,BorderLayout.WEST);
		empty.add(empty5,BorderLayout.SOUTH);

		l.setPreferredSize(new Dimension(120,30));
		l.addActionListener(new LegendaListner());

		empty4.add(l);
		empty2.add(indietro);

		rigth.add(empty);
		rigth.add(empty2);
		rigth.add(center);
		rigth.add(empty3);

		indietro.addActionListener(new IndietroListner());
		indietro.setPreferredSize(new Dimension(120,30));

		nuovo.setPreferredSize(new Dimension(120,30));
		nuovo.addActionListener(new NuovoListner());
		modifica.setVisible(false);
		center.add(nuovo);

		salva=new JButton("Salva");
		salva.addActionListener(new SalvaListner());
		salva.setPreferredSize(new Dimension(120,30));
		center.add(salva);

		stampa=new JButton("Stampa ");
		stampa.addActionListener(new StampaListner());
		stampa.setEnabled(false);
		stampa.setPreferredSize(new Dimension(120,30));
		center.add(stampa);


	}
	//nome sbagliato, creare i vari tooltip e aggiungere elementi nell'above panel quali i due estremi e la leggenda
	public void creare_jradiobutton(){

		altro= new Campo("","Legenda",7);
		altro.campo.setToolTipText("premere pulsante legenda.");

		basso= new Campo("","             inferiore",7);
		basso.setToolTipText("inserire data più bassa presente nel record");

		alto= new Campo("","           superiore",7);
		alto.setToolTipText("inserire data più alta presente nel record");

		above.add(altro);
		above.add(basso);
		above.add(alto);
	}
	//tutta la parte che si occupa della grafica per il funzionamento delle operazioni sulle qualifiche
	public void creare_qualifiche_pulsanti(){
		aggiungi= new JButton("Aggiungi Qualifica");
		aggiungi.addActionListener(new AggiungiListener());

		memorizza= new JButton("Memorizza Qualifica");
		memorizza.addActionListener(new MemorizzaListner());

		coniuge= new JButton("Coniuge");
		coniuge.addActionListener(new ConiugeListner());
		combo_qua.addActionListener(new JCombo_Listner());

		qualifiche.setLayout(new GridLayout(0,3));
		qualifiche.add(aggiungi);
		qualifiche.add(memorizza);
		qualifiche.add(coniuge);

		creare_operazioni_pulsanti();
	}
	//creare i principali campi presenti nel modulo
	void creare_campi(){
		//panelli contenenti i 4 fields dei registri
		JPanel nregistro= new JPanel();
		JPanel bregistro= new JPanel();
		JPanel mregistro= new JPanel();
		JPanel sregistro= new JPanel();
		//settare i layout dei 4 panel...
		nregistro.setLayout(new GridLayout(0,4));
		bregistro.setLayout(new GridLayout(0,4));
		mregistro.setLayout(new GridLayout(0,4));
		sregistro.setLayout(new GridLayout(0,4));

		//panelli per riempire il gridLayout
		JPanel empty1= new JPanel();
		empty1.setLayout(new GridLayout(0,2));
		JPanel empty2= new JPanel();
		JPanel empty_qua=new JPanel();
		//panello contenente il JComboBox per selezionare le qualifiche, più panello vuoto per riempire la destra...
		JPanel qua=new JPanel();
		qua.setLayout(new GridLayout(0,2));
		qua.add(empty_qua);
		qua.add(combo_qua);
		//panello con i radiobutton "dichiarata" "effettiva"
		JPanel de= new JPanel();
		de.setLayout(new GridLayout(0,4));
		//settare layout dei vari panel dichiarati a livello della classe...
		datan.setLayout(new GridLayout(0,2));
		datab.setLayout(new GridLayout(0,2));
		datas.setLayout(new GridLayout(0,2));
		datam.setLayout(new GridLayout(0,2));
		below.setLayout(new GridLayout(0,3));
		above.setLayout(new GridLayout(0,3));

		//aggiungere i vari field al panello di sinistra...
		left.add(above);
		campi[0]= new Campo("cognome","   Cognome..............................................................................",30);
		left.add(campi[0]);
		originale2= new Campo("","   Originale..........................................................................",30);
		left.add(originale2);
		campi[1]= new Campo("nome","   Nome...............................................................................",30);
		left.add(campi[1]);
		altri_nomi= new Campo("","   Altri Nomi....................................................................",30);
		left.add(altri_nomi);
		campi[2]= new Campo("nome_in_religione","   Nome in Religione................................................................",30);
		left.add(campi[2]);

		//tutto il pacchetto di campi, label, textfields per creare la data di nascita
		campi[3]= new Campo("nascita","   Data di nascita..........................",7);	
		datan.add(campi[3]);
		nregistro.add(nregistropc);
		nregistro.add(nregistroarco);
		nregistrofn.campo.setFont (nregistrofn.campo.getFont().deriveFont (20.0f));
		nregistro.add(nregistrofn);
		nregistro.add(nregistrofoglio);
		datan.add(nregistro);
		left.add(datan);

		//tutto il pacchetto di campi, label, textfields per creare la data di battesimo
		campi[5]= new Campo("battesimo","   Data di battesimo.........",7);	
		datab.add(campi[5]);
		bregistro.add(bregistropc);
		bregistro.add(bregistroarco);
		bregistrofn.campo.setFont (bregistrofn.campo.getFont().deriveFont (20.0f));
		bregistro.add(bregistrofn);
		bregistro.add(bregistrofoglio);
		datab.add(bregistro);
		left.add(datab);

		//tutto il pacchetto di campi, label, textfields per creare la data di morte
		campi[7]= new Campo("morte","   Data di morte...........................",7);	
		datam.add(campi[7]);
		datam.add(mregistro);
		mregistro.add(mregistropc);
		mregistro.add(mregistroarco);
		mregistrofn.campo.setFont (mregistrofn.campo.getFont().deriveFont (20.0f));
		mregistro.add(mregistrofn);
		mregistro.add(mregistrofoglio);
		left.add(datam);

		//parte di codice per sistemare i vari elementi dell'età della morte...
		JLabel eta= new JLabel("   Età alla morte.....................................................................");
		e= new JTextField(5);
		eta.setFont(eta.getFont().deriveFont (11.0f));
		dichiarate= new JRadioButton("dichiarata");
		effettiva= new JRadioButton("effettiva");
		ButtonGroup group=new ButtonGroup();
		empty1.add(empty2);
		empty1.add(e);
		de.add(eta);
		group.add(dichiarate);
		group.add(effettiva);
		de.add(dichiarate);
		de.add(effettiva);
		de.add(empty1);
		left.add(de);

		//elementi di grafica per la data di sepoltura ed il luogo...
		campi[9]= new Campo("sepoltura","   Data di sepoltura........",7);	
		datas.add(campi[9]);
		luogo_s= new Campo("", "   Luogo di sepoltura..........................................................",30);	
		datas.add(sregistro);
		sregistro.add(sregistropc);
		sregistro.add(sregistroarco);
		sregistrofn.campo.setFont (sregistrofn.campo.getFont().deriveFont (20.0f));
		sregistro.add(sregistrofn);
		sregistro.add(sregistrofoglio);
		left.add(datas);
		left.add(luogo_s);

		campi[11]= new Campo("padre","   Paternità ....................................................................",30);	
		left.add(campi[11]);
		campi[12]= new Campo("madre","   Maternità ....................................................................",30);	
		left.add(campi[12]);
		campi[14]= new Campo("titolo_di_studio","   Titolo di studio.............................................................",30);	
		left.add(campi[14]);

		//pacchetto codice qualifiche
		campi[13]= new Campo("qualifiche","   Qualifiche.............................................................................",30);	
		left.add(campi[13]);
		left.add(qua);
		left.add(qualifiche);

		//tutta la parte delle note
		note1.setText("   Note...................................................................................");
		below.add(note1);
		scroll = new JScrollPane (commento,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		commento.setLineWrap(true);
		below.add(scroll);
		below.setPreferredSize(new Dimension(600, 100));
		add(below,BorderLayout.SOUTH);
		creare_jradiobutton();
		creare_qualifiche_pulsanti();
		key();
	}
	//settare tutti i campi nelle rispettive variabili del campo scheda che verrà poi inserito nel database
	public void inserire_in_db(){
		//prendere i dati immessi dai JtextFields
		String altri_dati=altro.dato.getText();
		String inferiore=basso.dato.getText();
		String superiore=alto.dato.getText();
		String cognome=campi[0].dato.getText();
		String originale= originale2.dato.getText();
		String nome=campi[1].dato.getText();
		String nome2=altri_nomi.dato.getText();
		String nome_in_religione= campi[2].dato.getText();
		String soprannome= c.dato.getText();
		String detto= c1.dato.getText();
		String padre= campi[11].dato.getText();
		String madre= campi[12].dato.getText();
		String titolo= campi[14].dato.getText();
		String note= commento.getText();
		String ndata=campi[3].dato.getText();
		String bdata=campi[5].dato.getText();
		String mdata=campi[7].dato.getText();
		String sdata=campi[9].dato.getText();
		String eta=e.getText();
		String luogo=luogo_s.dato.getText();
		//creare oggetti di tipo registro contenenti i 4 dati provenienti dai 4 JTextFields
		Registro nregistro= new Registro(nregistropc.dato.getText(), nregistroarco.getText(), 
				nregistrofn.dato.getText(), nregistrofoglio.getText());
		Registro bregistro= new Registro(bregistropc.dato.getText(), bregistroarco.getText(), 
				bregistrofn.dato.getText(), bregistrofoglio.getText());
		Registro mregistro= new Registro(mregistropc.dato.getText(), mregistroarco.getText(), 
				mregistrofn.dato.getText(), mregistrofoglio.getText());
		Registro sregistro= new Registro(sregistropc.dato.getText(), sregistroarco.getText(), 
				sregistrofn.dato.getText(), sregistrofoglio.getText());
		//formatare le Stringhe in date...
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		//varie conversioni da data a Stringa
		scheda = new Scheda();
		if(!ndata.equals(""))
			try {
				nascita = (Date) formatter.parse(ndata);
				addDate(campi[3].nome_in_db,nascita,nregistro,date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		if(!bdata.equals(""))
			try {
				battesimo=(Date) formatter.parse(bdata);
				addDate(campi[5].nome_in_db,battesimo,bregistro,date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		if(!mdata.equals(""))
			try {
				morte=(Date) formatter.parse(mdata);
				addDate(campi[7].nome_in_db,morte,mregistro,date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		if(!sdata.equals(""))
			try {
				sepoltura=(Date) formatter.parse(sdata);
				addDate(campi[9].nome_in_db,sepoltura,sregistro,date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		//conversioni per inferiore e superiore...
		if(!inferiore.equals("")){
			try {
				estremo_basso=(Date) formatter.parse(inferiore);
				scheda.set_basso(estremo_basso);
				System.out.println(estremo_basso);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!superiore.equals("")){
			try {
				estremo_alto=(Date) formatter.parse(superiore);
				scheda.set_alto(estremo_alto);
				System.out.println(estremo_alto);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//prendere la data di inserimento dati
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date current_date = new Date();
		//settare stringe dell'oggetto
		scheda.setAltro(altri_dati);
		scheda.setCognome(cognome);
		scheda.setOriginale(originale);
		scheda.setNome(nome);
		scheda.set_altri_Nomi(nome2);
		scheda.setSoprannome(soprannome);
		scheda.setDetto(detto);
		if(effettiva.isSelected()){
			tipo_eta="EFFETTIVA";
		}
		if(dichiarate.isSelected()){
			tipo_eta="DICHIARATA";
		}
		scheda.setEta(eta,tipo_eta);
		scheda.setLuogo(luogo);
		scheda.setPaternita(padre);
		scheda.setMaternita(madre);
		scheda.setTitolo(titolo);
		scheda.setNote(note);
		scheda.setRNome(nome_in_religione);
		scheda.setQualifica(qualifica);
		scheda.setRegistri(date);
		scheda.setCurrentDate(current_date);
		scheda.setModifyDate(current_date);
		scheda.set_Coniuge(area.getText());
	}

	//pulire tutti i campi quado un nuovo record deve essere inserito...
	public void erase_JTextField(){

		alto.dato.setText("");
		altro.dato.setText("");
		campi[0].dato.setText("");
		originale2.dato.setText("");
		campi[1].dato.setText("");
		altri_nomi.dato.setText("");
		campi[2].dato.setText("");
		c.dato.setText("");
		c1.dato.setText("");
		campi[11].dato.setText("");
		campi[12].dato.setText("");
		campi[14].dato.setText("");
		e.setText("");
		luogo_s.dato.setText("");
		commento.setText("");
		campi[3].dato.setText("");
		basso.dato.setText("");
		alto.dato.setText("");
		nregistropc.dato.setText("");
		nregistroarco.setText("");;
		nregistrofn.dato.setText("");
		nregistrofoglio.setText("");
		campi[5].dato.setText("");

		bregistropc.dato.setText("");
		bregistroarco.setText("");;
		bregistrofn.dato.setText("");
		bregistrofoglio.setText("");

		campi[7].dato.setText("");

		mregistropc.dato.setText("");
		mregistroarco.setText("");;
		mregistrofn.dato.setText("");
		mregistrofoglio.setText("");

		campi[9].dato.setText("");

		sregistropc.dato.setText("");
		sregistroarco.setText("");;
		sregistrofn.dato.setText("");
		sregistrofoglio.setText("");
		area.setText("");
	}

	//creare un oggetto di tipo data con registro contenente le varie date(nascita,battesimo,morte, sepoltura) coi relativi registri
	public void addDate(String tipo_data,Date data, Registro r,ArrayList<Data_con_Registro>date){
		Data_con_Registro d= new Data_con_Registro(tipo_data, data, r);
		date.add(d);
	}

	//actionlistner per far apparire il JDialog col detto e soprannome
	private class AltroListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			//panel coi pulsante ok...
			JPanel salva2= new JPanel();
			JButton ok = new JButton("OK");
			ok.addActionListener(new okListner());
			salva2.add(ok);
			//dialog coi campi
			d2=new JDialog();	
			d2.setSize(500,141);		        
			d2.setVisible(true);
			d2.add(c);
			d2.add(c1);
			d2.add(salva2);
			d2.setLayout(new GridLayout(3,0));
		}
	}
	//listner del pulsante ok del soprannome e nome
	private class okListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			soprannome= c.dato.getText();
			detto= c1.dato.getText();
			d2.dispose();
		}
	}	
	//listner del tasto aggiungi delle qualifiche
	private class AggiungiListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{

			ok3.addActionListener(new okbisListner());
			ok.add(ok3);

			d3=new JDialog();	
			setVisible(true);
			d3.setSize(350,100);
			d3.setLayout(new GridLayout(2,0));
			d3.add(tempo);
			d3.add(ok);
			d3.setVisible(true);

			tempo.add(dal);
			tempo.add(al);
			tempo.setLayout(new GridLayout(0,2));

			q= campi[13].dato.getText();	
		}
	}
	//listner dell'ok delle qualifiche
	private class okbisListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			//creare un oggetto completo delle qualifiche prendendo nome e date di attestazione...
			int dialogResult = JOptionPane.showConfirmDialog (null,"Sicuro di voler aggiugere la qualifica?");
			if(dialogResult == JOptionPane.YES_OPTION){
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				String dal1=dal.dato.getText();
				String al1=al.dato.getText();
				if(!dal1.equals("")&&!al1.equals("")){
					try {

						dal_date = (Date) formatter.parse(dal1);
						al_date =  (Date) formatter.parse(al1);

					} catch (ParseException e) {
						e.printStackTrace();
					}
					Qualifica myqualifica= new Qualifica();

					myqualifica.setStringQualifica(q);
					myqualifica.setDal(dal_date);
					myqualifica.setAl(al_date);
					qualifica.add(myqualifica);
					d3.dispose();
				}
			}
			

			dal.dato.setText("");
			al.dato.setText("");
			campi[13].dato.setText("");

		}
	}
	//actionlistner del pulsante salva per rendere permanenti i campi...
	private class SalvaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null,"Sicuro di voler salvare?");
			if(dialogResult == JOptionPane.YES_OPTION){
				inserire_in_db();
				scheda.salva();
				stampa.setEnabled(true);
			}
		}
	}

	private class ConiugeListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			JButton save=new JButton("Salva");
			save.addActionListener(new  SListner());

			d=new JDialog();
			d.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();

			scroll1 = new JScrollPane (area,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JPanel below=new JPanel();


			set_Text.addActionListener(new SetListner());
			set_Text.setToolTipText("permette di inserire campi predefiniti");

			area.setSize(new Dimension(900,1000));
			area.setFont(new Font("Serif", Font.ITALIC, 16));
			area.setWrapStyleWord(true);
			area.setLineWrap(true);

			d.setSize(new Dimension(600, 500));       
			d.setVisible(true);
			d.add(scroll1);

			below.add(save);
			below.add(set_Text);

			gbc.gridx=1;
			gbc.gridy=0;
			d.add(scroll1,gbc);

			gbc.gridx=1;
			gbc.gridy=1;
			d.add(below,gbc);
		}
	}


	private class  SetListner implements ActionListener
	{

		public void actionPerformed (ActionEvent event)
		{
			area.setText("Coniuge:"
					+"\nPadre: "                                           
					+"\nMadre: "
					+"\nData nascita: "
					+"\nData battesimo: "
					+"\nData matrimonio: "
					+"\nData morte:\nData sepoltura: "
					+"\nAttestato: ");

		}
	}
	private class  SListner implements ActionListener
	{

		public void actionPerformed (ActionEvent event)
		{
			String coniuge=area.getText();
			System.out.println(coniuge);
			d.dispose();
		}
	}
	private class StampaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null,"Confermare la stampa?");
			if(dialogResult == JOptionPane.YES_OPTION){

				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(new Scheda_Stampata(scheda));
				boolean doPrint = job.printDialog();
				stampa.setEnabled(false);
				if (doPrint) {
					try {
						job.print();
					} catch (PrinterException e) {
					}
				}

			}
		}
	}
	private class NuovoListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null,"Sicuro di voler creare un nuovo record?");
			if(dialogResult == JOptionPane.YES_OPTION){
				qualifica.clear();
				date.clear();
				erase_JTextField();
			}
		}
	}
	private class IndietroListner implements ActionListener
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
	private class LegendaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			JButton save=new JButton("Salva");

			dialog=new JDialog();
			dialog.setLayout(new GridBagLayout());
			dialog.setSize(new Dimension(600, 500));       
			dialog.setVisible(true);

			save.addActionListener(new SaveListner());

			GridBagConstraints gbc = new GridBagConstraints();

			scrollPane = new JScrollPane (a,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JPanel below=new JPanel();

			a.setLineWrap(true);
			a.setFont(new Font("Serif", Font.BOLD, 16));
			a.setWrapStyleWord(true);
			dialog.add(scrollPane);

			below.add(save);

			gbc.gridx=1;
			gbc.gridy=0;
			dialog.add(scrollPane,gbc);

			gbc.gridx=1;
			gbc.gridy=1;
			dialog.add(below,gbc);
			File f = new File("D:/Utenti/User03/Desktop/legenda.doc");

			if(f.exists()){

				FileReader reader = null;
				try {
					reader = new FileReader(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					a.read(reader,"filename.txt");
				} catch (IOException e) {
					e.printStackTrace();

				}
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	private class SaveListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			BufferedWriter fileOut = null;
			try {
				fileOut = new BufferedWriter(new FileWriter("D:/Utenti/User03/Desktop/legenda.doc") );
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				a.write(fileOut);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dialog.dispose();
		}
	}
	private class MemorizzaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null,"Sicuro di voler memorizzare?");
			if(dialogResult == JOptionPane.YES_OPTION){
				scheda.store_qualifiche(campi[13].dato.getText());
			}
		}
	}
	public void key(){
		KeyListener keyListener = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {

				if(keyEvent.getKeyCode()==KeyEvent.VK_ENTER){
					ResultSet rs=DBConnection.cerca_qualifica(campi[13].dato.getText());
					try {
						while(rs.next()){
							if(!array.contains(rs.getString("qualifica"))){
								array.add(rs.getString("qualifica"));

							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					combo_qua.removeAllItems();

					for(int i=0;i<array.size();i++){
						combo_qua.addItem(array.get(i));	
					}  

				}
			}
			public void keyReleased(KeyEvent keyEvent) {
				array.clear();

			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		};
		campi[13].dato.addKeyListener(keyListener);
	}
	private class JCombo_Listner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			if(combo_qua.getSelectedItem()!=null){
				String result= combo_qua.getSelectedItem().toString();
				campi[13].dato.setText(result);
			}
		}

	}
}











