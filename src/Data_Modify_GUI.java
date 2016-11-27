import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
//grafica per modificare un record che inerita dalla grafica principale...
public class Data_Modify_GUI extends Data_Interface{
	JFrame f;
	//scheda contenente i dati presi dal database
	Scheda scheda;
	//scheda contenente i dati modifiac da reimettere nel database...
	Scheda printScheda=new Scheda();
	//pulsante per modificare un record..
	JButton m;
	//pulsante per stampare un record...
	JButton modify_stampa;
	JButton indice=new JButton("Indice   ");
	//varie arraylist per le date anagrafiche	
	ArrayList<Data_con_Registro> a= new ArrayList<Data_con_Registro>();
	ArrayList<Data_con_Registro> new_a;
	ArrayList<Data_con_Registro> final_a= new ArrayList<Data_con_Registro>();
	//varie arraylist per le qualifiche...
	ArrayList<Qualifica> qua;
	ArrayList<Qualifica> modify_qua= new ArrayList<Qualifica>();
	ArrayList<Qualifica> complete=new ArrayList<Qualifica>();

	Campo ca[][];
	int qualifiche_id[];
	//integer per rimpicciolire man mano che le qualifiche sono aggiunte...
	int y;
	String input;

	public Data_Modify_GUI(Scheda s,JFrame f,String input){
		super(f);
		this.f=f;
		this.scheda=s;
		this.input=input;
		fill_module();
	}
	void creare_campi(){
		m= new JButton("Modifica");
		m.addActionListener(new MListner());
		JPanel nregistro= new JPanel();
		JPanel bregistro= new JPanel();
		JPanel mregistro= new JPanel();
		JPanel sregistro= new JPanel();
		JPanel empty1= new JPanel();
		JPanel empty2= new JPanel();
		JPanel de= new JPanel();

		JPanel empty_qua=new JPanel();
		JPanel qua=new JPanel();
		qua.setLayout(new GridLayout(0,2));
		qua.add(empty_qua);
		qua.add(combo_qua);

		datan.setLayout(new GridLayout(0,2));
		nregistro.setLayout(new GridLayout(0,4));
		bregistro.setLayout(new GridLayout(0,4));
		mregistro.setLayout(new GridLayout(0,4));
		sregistro.setLayout(new GridLayout(0,4));
		de.setLayout(new GridLayout(0,4));
		empty1.setLayout(new GridLayout(0,2));

		//nregistro.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		//nregistro.setPreferredSize(new Dimension(265,80));
		datab.setLayout(new GridLayout(0,2));
		datas.setLayout(new GridLayout(0,2));
		datam.setLayout(new GridLayout(0,2));
		below.setLayout(new GridLayout(0,3));
		above.setLayout(new GridLayout(0,3));

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
		campi[3]= new Campo("nascita","   Data di nascita..........................",7);	
		datan.add(campi[3]);
		//campi[4]= new Campo("registro","Registro...........................",7);	

		nregistro.add(nregistropc);
		nregistro.add(nregistroarco);
		nregistrofn.campo.setFont (nregistrofn.campo.getFont().deriveFont (20.0f));
		nregistro.add(nregistrofn);
		nregistro.add(nregistrofoglio);
		datan.add(nregistro);
		left.add(datan);

		campi[5]= new Campo("battesimo","   Data di battesimo.........",7);	
		datab.add(campi[5]);
		//campi[6]= new Campo("registro","Registro...........................",7);	
		bregistro.add(bregistropc);
		bregistro.add(bregistroarco);
		bregistrofn.campo.setFont (bregistrofn.campo.getFont().deriveFont (20.0f));
		bregistro.add(bregistrofn);
		bregistro.add(bregistrofoglio);
		datab.add(bregistro);
		left.add(datab);

		campi[7]= new Campo("morte","   Data di morte...........................",7);	
		datam.add(campi[7]);

		//campi[8]= new Campo("registro","Registro........................",7);
		datam.add(mregistro);
		//mregistro.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		mregistro.add(mregistropc);
		mregistro.add(mregistroarco);
		mregistrofn.campo.setFont (mregistrofn.campo.getFont().deriveFont (20.0f));
		mregistro.add(mregistrofn);
		mregistro.add(mregistrofoglio);
		JLabel eta= new JLabel("   Et‡ alla morte.....................................................................");
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

		//datam.add(et√†);
		left.add(datam);
		left.add(de);
		campi[9]= new Campo("sepoltura","   Data di sepoltura........",7);	
		datas.add(campi[9]);
		luogo_s= new Campo("", "   Luogo di sepoltura..........................................................",30);

		//campi[10]= new Campo("registro","Registro............................",7);	
		datas.add(sregistro);
		sregistro.add(sregistropc);
		sregistro.add(sregistroarco);
		sregistrofn.campo.setFont (sregistrofn.campo.getFont().deriveFont (20.0f));
		sregistro.add(sregistrofn);
		sregistro.add(sregistrofoglio);

		left.add(datas);
		left.add(luogo_s);
		campi[11]= new Campo("padre","   Paternit‡†....................................................................",30);	
		left.add(campi[11]);
		campi[12]= new Campo("madre","   Maternit‡†....................................................................",30);	
		left.add(campi[12]);
		campi[14]= new Campo("titolo_di_studio","   Titolo di studio.............................................................",30);	
		left.add(campi[14]);
		campi[13]= new Campo("qualifiche","   Qualifiche.............................................................................",30);	
		left.add(campi[13]);
		left.add(qua);
		left.add(qualifiche);
		key();
	}
	//metodo per comprimere la grafica quando vengono aggiunte un numero variabile di qualifiche...
	public void add_qualifiche(){
		SimpleDateFormat  si = new SimpleDateFormat("dd-MM-yyyy");
		y=18;
		qua= scheda.getQualifica();
		ca=new Campo[qua.size()][3];
		qualifiche_id=new int[qua.size()];
		for (int i=0;i<qua.size();i++){
			qualifiche_id[i]=qua.get(i).id;
			JPanel p= new JPanel();
			String dal= new String();
			String al= new String();
			p.setLayout(new GridLayout(0,3));
			Campo c= new Campo("","Qualifica...................................",30);
			c.dato.setText(scheda.getQualifica().get(i).qualifica);
			ca[i][0]=c;
			try{
				dal= si.format(scheda.getQualifica().get(i).dal);

			}catch (Exception ex ){
				System.out.println(ex);
			}
			try{
				al= si.format(qua.get(i).al);
			}catch (Exception ex ){
				System.out.println(ex);
			}
			Campo dal1= new Campo("","Dal..............................",7);
			ca[i][1]=dal1;
			dal1.dato.setText(dal);
			Campo al1= new Campo("","Al.............................",7);
			ca[i][2]=al1;
			al1.dato.setText(al);
			p.add(c);
			p.add(dal1);
			p.add(al1);
			left.add(p);
			y++;
		}

		left.setLayout(new GridLayout(y,0));


		note1.setText("   Note...................................................................................");
		below.add(note1);
		scroll = new JScrollPane (commento,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		commento.setLineWrap(true);
		below.add(scroll);
		modify_stampa=new JButton("Stampa");
		modify_stampa.setEnabled(false);
		below.setPreferredSize(new Dimension(600, 100));
		add(below,BorderLayout.SOUTH);
		creare_jradiobutton();
		creare_qualifiche_pulsanti();
		center.add(m);
		center.add(indice);
		indice.addActionListener(new indiceListner());
		center.add(modify_stampa);
		modify_stampa.addActionListener(new StampaListner());
		salva.setVisible(false);
		nuovo.setVisible(false);
		stampa.setVisible(false);
		indietro.setVisible(false);
		set_Text.setVisible(false);
		ok3.addActionListener(new okbListner());
	}
	//metodo per riempire i campi con i dati presi dal database...
	public void fill_module(){
		add_qualifiche();
		//prendere dati dalla scheda e settarli nei vari campi...
		area.setText(scheda.get_Coniuge());
		String alta= new String();
		String bassa= new String();
		altro.dato.setText(scheda.getAltro());
		campi[0].dato.setText(scheda.getCognome());
		originale2.dato.setText(scheda.getOriginale());
		campi[1].dato.setText(scheda.getNome());
		altri_nomi.dato.setText(scheda.get_altri_Nomi());
		campi[2].dato.setText(scheda.getRNome());
		c.dato.setText(scheda.getSoprannome());
		c1.dato.setText(scheda.getDetto());
		campi[11].dato.setText(scheda.getPaternita());
		campi[12].dato.setText(scheda.getMaternita());
		campi[14].dato.setText(scheda.getTitolo());
		String []s=new  String[2];
		s=scheda.getEta();
		e.setText(s[0]);
		if(s[1]!=null && s[1].equals("EFFETTIVA"))
			effettiva.setSelected(true);
		if(s[1]!=null && s[1].equals("DICHIARATA"))
			dichiarate.setSelected(true);
		luogo_s.dato.setText(scheda.getLuogo());
		commento.setText(scheda.getNote());
		SimpleDateFormat  sdfr = new SimpleDateFormat("dd-MM-yyyy");
		try{
			bassa= sdfr.format(scheda.get_basso());
		}catch (Exception ex ){
			System.out.println(ex);
		}

		try{
			alta= sdfr.format(scheda.get_alto());
		}catch (Exception ex ){
			System.out.println(ex);
		}

		basso.dato.setText(bassa);
		alto.dato.setText(alta);

		new_a= scheda.getRegistri();
		for (int i=0;i<new_a.size();i++){
			if(new_a.get(i).tipo_data.equals("nascita")){
				String nascita= new String();
				nregistropc.dato.setText(new_a.get(i).r.cp);
				nregistroarco.setText(new_a.get(i).r.arco);
				nregistrofn.dato.setText(new_a.get(i).r.fn);
				nregistrofoglio.setText(new_a.get(i).r.foglio);

				try{
					nascita= sdfr.format(new_a.get(i).data);
				}catch (Exception ex ){
					System.out.println(ex);
				}
				campi[3].dato.setText(nascita);
			}
			if(new_a.get(i).tipo_data.equals("battesimo")){
				String battesimo= new String();
				try{
					battesimo= sdfr.format(new_a.get(i).data);
					bregistropc.dato.setText(new_a.get(i).r.cp);
					bregistroarco.setText(new_a.get(i).r.arco);
					bregistrofn.dato.setText(new_a.get(i).r.fn);
					bregistrofoglio.setText(new_a.get(i).r.foglio);
				}catch (Exception ex ){
					System.out.println(ex);
				}
				campi[5].dato.setText(battesimo);
			}

			if(new_a.get(i).tipo_data.equals("morte")){
				String morte= new String();

				try{
					morte= sdfr.format(new_a.get(i).data);
					mregistropc.dato.setText(new_a.get(i).r.cp);
					mregistroarco.setText(new_a.get(i).r.arco);
					mregistrofn.dato.setText(new_a.get(i).r.fn);
					mregistrofoglio.setText(new_a.get(i).r.foglio);
				}catch (Exception ex ){
					System.out.println(ex);				

				}
				campi[7].dato.setText(morte);
			}


			if(new_a.get(i).tipo_data.equals("sepoltura")){
				String sepoltura= new String();

				try{
					sepoltura= sdfr.format(new_a.get(i).data);
					sregistropc.dato.setText(new_a.get(i).r.cp);
					sregistroarco.setText(new_a.get(i).r.arco);
					sregistrofn.dato.setText(new_a.get(i).r.fn);
					sregistrofoglio.setText(new_a.get(i).r.foglio);
				}catch (Exception ex ){
					System.out.println(ex);				
				}
				campi[9].dato.setText(sepoltura);
			}
		}
	}
	public void modifica_scheda(){
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
		String eta=e.getText();
		String luogo=luogo_s.dato.getText();
		scheda.set_Coniuge(area.getText());


		if(effettiva.isSelected()){
			tipo_eta="EFFETTIVA";
		}
		if(dichiarate.isSelected()){
			tipo_eta="DICHIARATA";
		}
		scheda.setEta(eta,tipo_eta);
		printScheda.setEta(eta,tipo_eta);
		printScheda.id=scheda.id;
		scheda.setLuogo(luogo);
		printScheda.setLuogo(luogo);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		if(!inferiore.equals("")){
			try {
				estremo_basso=(Date) formatter.parse(inferiore);
				scheda.set_basso(estremo_basso);
				printScheda.set_basso(estremo_basso);
				System.out.println(estremo_basso);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(!superiore.equals("")){
			try {
				estremo_alto=(Date) formatter.parse(superiore);
				scheda.set_alto(estremo_alto);
				printScheda.set_alto(estremo_alto);
				System.out.println(estremo_alto);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date modify_date = new Date();

		scheda.setModifyDate(modify_date);
		scheda.setAltro(altri_dati);
		scheda.setCognome(cognome);
		scheda.setOriginale(originale);
		scheda.setNome(nome);
		scheda.set_altri_Nomi(nome2);
		scheda.setPaternita(padre);
		scheda.setMaternita(madre);
		scheda.setTitolo(titolo);
		scheda.setNote(note);
		scheda.setRNome(nome_in_religione);
		scheda.setSoprannome(soprannome);
		scheda.setDetto(detto);
		scheda.setQualifica(qualifica);

		printScheda.setModifyDate(modify_date);
		printScheda.setAltro(altri_dati);
		printScheda.setCognome(cognome);
		printScheda.setOriginale(originale);
		printScheda.setNome(nome);
		printScheda.set_altri_Nomi(nome2);
		printScheda.setPaternita(padre);
		printScheda.setMaternita(madre);
		printScheda.setTitolo(titolo);
		printScheda.setNote(note);
		printScheda.setRNome(nome_in_religione);
		printScheda.setSoprannome(soprannome);
		printScheda.setDetto(detto);
		printScheda.set_Coniuge(area.getText());
		complete.addAll(qualifica);
		printScheda.setQualifica(complete);
		printScheda.setRegistri(a);
	}

	public void riprendi_date(){
		String ndata=campi[3].dato.getText();
		String bdata=campi[5].dato.getText();
		String mdata=campi[7].dato.getText();
		String sdata=campi[9].dato.getText();
		Registro nregistro= new Registro(nregistropc.dato.getText(), nregistroarco.getText(), 
				nregistrofn.dato.getText(), nregistrofoglio.getText());
		Registro bregistro= new Registro(bregistropc.dato.getText(), bregistroarco.getText(), 
				bregistrofn.dato.getText(), bregistrofoglio.getText());
		Registro mregistro= new Registro(mregistropc.dato.getText(), mregistroarco.getText(), 
				mregistrofn.dato.getText(), mregistrofoglio.getText());
		Registro sregistro= new Registro(sregistropc.dato.getText(), sregistroarco.getText(), 
				sregistrofn.dato.getText(), sregistrofoglio.getText());

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		if(!ndata.equals(""))
			try {
				nascita = (Date) formatter.parse(ndata);
				addDate(campi[3].nome_in_db,nascita,nregistro,a);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if(!bdata.equals(""))
			try {
				battesimo=(Date) formatter.parse(bdata);
				addDate(campi[5].nome_in_db,battesimo,bregistro,a);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if(!mdata.equals(""))
			try {
				morte=(Date) formatter.parse(mdata);
				addDate(campi[7].nome_in_db,morte,mregistro,a);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if(!sdata.equals(""))
			try {
				sepoltura=(Date) formatter.parse(sdata);
				addDate(campi[9].nome_in_db,sepoltura,sregistro,a);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void inserisci_mydate(){
		for(int i=0;i<new_a.size();i++){
			System.out.println("sn in new a"+new_a.get(i).tipo_data);
		}

		if(new_a.size()==0 && a.size()>0){
			for(int i=0;i<a.size();i++){
				System.out.println("sn in a"+a.get(i).tipo_data);
				scheda.aggiungi_datecon_registro(a.get(i));
			}
		}
		else
			if(a.size()==0 && new_a.size()>0){
				for(int i=0;i<new_a.size();i++){
					System.out.println("sn in a"+a.get(i).tipo_data);
					scheda.update_datecon_registro(new_a.get(i));
				}
			}
			else if(a.size()>0&&new_a.size()>0){
				for(int j=0;j<new_a.size();j++){
					for(int i=0;i<a.size();i++){
						if(new_a.get(j).tipo_data.equals(a.get(i).tipo_data)){
							System.out.println(new_a.get(j).tipo_data+"is equal "+a.get(i).tipo_data);
							new_a.get(j).setTag(true);
							a.get(i).setTag(true);
							a.get(i).setID(new_a.get(i).id);
						}

					}
				}
				for (int h=0;h<a.size();h++){
					if(a.get(h).tag==true)
						scheda.update_datecon_registro(a.get(h));
					else

						scheda.aggiungi_datecon_registro(a.get(h));
				}
			}
	}
	public void check_qua_equality(){
		for(int j=0;j<ca.length;j++){
			String dal2=new String();
			dal2=ca[j][1].dato.getText();
			String al2= new String();
			al2=ca[j][2].dato.getText();
			Date dal3=new Date();
			Date al3=new Date();

			SimpleDateFormat  sdfr = new SimpleDateFormat("dd-MM-yyyy");
			if(!dal2.equals("")){
				try{
					dal3=(Date) sdfr.parse(dal2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(!al2.equals("")){
				try{
					al3=(Date) sdfr.parse(al2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Qualifica q= new Qualifica();
			q.setStringQualifica(ca[j][0].dato.getText());
			q.setId(qualifiche_id[j]);
			q.setDal(dal3);
			q.setAl(al3);

			modify_qua.add(q);
			System.out.println(ca[j][0].dato.getText()+ " "+dal3+ " "+al3);
			complete.add(q);
		}
	}

	public void update1(){
		check_qua_equality();
		System.out.println("sn in update1");
		for(int i=0;i<modify_qua.size();i++){
			scheda.inserisci_qualifica(modify_qua.get(i));
		}
	}

	public void modifica_salva(){

		modifica_scheda();
		scheda.modifica_prete();
		scheda.inseriscieta_luogo();
		scheda.inserisci_qualifiche();
		scheda.insert_update_Date();
		scheda.update_estremi();
		riprendi_date();
		inserisci_mydate();
		update1();
	}

	private class MListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null,"Sicuro di voler modificare?");
			if(dialogResult == JOptionPane.YES_OPTION){
				modifica_salva();
				modify_stampa.setEnabled(true);

			}
		}
	}
	private class okbListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
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
				System.out.println(myqualifica.getStringQualifica());
				qualifica.add(myqualifica);
			}
		}
	}
	private class StampaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null,"Sicuro di voler stampare?");
			if(dialogResult == JOptionPane.YES_OPTION){
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(new Scheda_Stampata(printScheda));
				boolean doPrint = job.printDialog();
				modify_stampa.setEnabled(false);
				a.clear();
				new_a.clear();
				modify_qua.clear();
				qua.clear();
				if (doPrint) {
					try {
						job.print();
					} catch (PrinterException e) {
					}
				}
			}

			complete.clear();
		}
	}
	private class indiceListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			JFrame frame = new JFrame("RELIGIOSI");
			Lista_Religiosi l=new Lista_Religiosi(frame, input);
			JScrollPane	scroll = new JScrollPane (l);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			scroll.setPreferredSize(new Dimension(920,600));
			scroll.setViewportView(l);

			frame.getContentPane().add(scroll);
			frame.pack();
			frame.setVisible(true);


			f.dispose();
		}
	}
}

