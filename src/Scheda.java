

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JPanel;


public class Scheda {
	/**
	 * 
	 */

	long id;
	String altro,cognome, originale,nome,altri_nomi, eta_morte,tipo_eta,soprannome,detto,nome_in_religione, padre, madre,titolo_di_Studio,note,coniuge,luogo_di_sepoltuta;
	Date aggiornamento,inserimento, primo_estremo, ultimo_estremo;
	ArrayList<Qualifica> qualifiche=new ArrayList<Qualifica>();
	static ArrayList<String> a =new ArrayList();
	ArrayList<Data_con_Registro> registri= new ArrayList<Data_con_Registro>();


	public Scheda(){
	}

	public void set_ID(long id){
		this.id=id;
	}
	public long get_ID(){
		return id;
	}
	public void set_Coniuge(String coniuge ){
		System.out.println("SN IN SET CONIUGE"+coniuge);
		this.coniuge=coniuge;
		System.out.println("SN IN SET 1 CONIUGE"+this.coniuge);
	}
	public String get_Coniuge(){
		return coniuge;
	}
	public void set_basso(Date estremo_inferiore){
		primo_estremo=estremo_inferiore;
		System.out.println("sn in Scheada"+primo_estremo);
	}
	public Date get_basso(){
		return primo_estremo;
	}

	public void set_alto(Date estremo_superiore){
		ultimo_estremo=estremo_superiore;
		System.out.println("sn in Scheada"+ultimo_estremo);
	}

	public Date get_alto(){
		return ultimo_estremo;
	}
	public void setAltro(String altro){
		this.altro=altro;
	}
	public String getAltro(){
		return altro;
	}

	public void setOriginale(String originale){
		this.originale=originale;
	}
	public String getOriginale(){
		return originale;
	}

	public void setCognome(String cognome){
		this.cognome=cognome;
	}
	public String getCognome(){
		return cognome;
	}

	public void setNome(String nome){
		this.nome=nome;
	}
	public String getNome(){
		return nome;
	}

	public void set_altri_Nomi(String altri_nomi){
		this.altri_nomi=altri_nomi;
	}
	public String get_altri_Nomi(){
		return altri_nomi;
	}

	public void setSoprannome(String soprannome){
		this.soprannome=soprannome;
	}
	public String getSoprannome(){
		return soprannome;
	}

	public void setDetto(String detto){
		this.detto=detto;
	}
	public String getDetto(){
		return detto;
	}

	public void setRNome(String nome_in_religione){
		this.nome_in_religione=nome_in_religione;
	}
	public String getRNome(){
		return nome_in_religione;
	}

	public void setPaternita(String padre){
		this.padre=padre;
	}
	public String getPaternita(){
		return padre;
	}

	public void setMaternita(String madre){
		this.madre=madre;
	}
	public String getMaternita(){
		return madre;
	}

	public void setTitolo(String titolo){
		this.titolo_di_Studio=titolo;
	}
	public String getTitolo(){
		return titolo_di_Studio;
	}

	public void setNote(String note){
		this.note=note;
	}
	public String getNote(){
		return note;
	}

	public void setQualifica(ArrayList<Qualifica> qualifiche){

		this.qualifiche=qualifiche;
		System.out.println("sono in setQualifiche"+this.qualifiche.size());
	}

	public ArrayList<Qualifica> getQualifica(){
		return this.qualifiche;
	}

	public void setRegistri(ArrayList<Data_con_Registro> registri){
		this.registri=registri;
	}
	public ArrayList<Data_con_Registro> getRegistri(){
		return registri;
	}

	public void setCurrentDate(Date date){
		inserimento=date;
	}
	public Date getInserimento(){
		return aggiornamento;
	}
	public Date getAggiornamento(){
		return aggiornamento;
	}

	public void setModifyDate(Date modify){
		aggiornamento= modify;
	}

	public void setEta(String eta , String tipo){
		eta_morte=eta;
		tipo_eta=tipo;
	}
	public String[] getEta(){
		String[]s= new String[2];
		s[0]=eta_morte;
		s[1]=tipo_eta;
		return s;
	}
	public void setLuogo(String Luogo_di_sepoltura){
		this.luogo_di_sepoltuta=Luogo_di_sepoltura;
	}
	public String getLuogo(){
		return luogo_di_sepoltuta;
	}

	public String toString(){
		return altro+cognome+ " "+originale+ " "+nome+ " "
				+altri_nomi+ " "+ eta_morte+ " "+tipo_eta+ " "+soprannome+ " "+detto+ " "+nome_in_religione+  " "+padre+  " "+madre+ " "+titolo_di_Studio+ " "+note
				+ aggiornamento+inserimento+ primo_estremo+ultimo_estremo;
	}

	//aggiungi clear alle due liste
	public void salva(){
		add_religioso();
		add_qualifiche();
		add_date();
		addeta_luogo();
		add_Date();
		id();
	}

	public void add_religioso(){
		System.out.println(coniuge);
		String s="'";
		String sv="',";
		try {
			String query="insert into religiosi(altri_dati,cognome,originale,nome,soprannome,detto,altri_nomi,nome_in_religione," +
					"padre,madre ,titolo_di_studio,note,coniuge) "
					+ "values("+s+altro+sv+s+cognome+sv+s+originale+sv+s+nome+sv+s+soprannome+sv+s+detto+sv+s+altri_nomi+sv+s+nome_in_religione+sv+s+padre+sv+s+madre+sv+s+titolo_di_Studio+sv+s+note+sv+s+coniuge+s+")";
			DBConnection.inserisci_religioso(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void store_qualifiche(String qua){
		if(!a.contains(qua)){
			a.add(qua);
			String query2="insert into existing_qualifiche(qualifica) values("+"'"+qua+"'"+")";
			try {
				DBConnection.inserisci_religioso (query2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void add_qualifiche(){
		for(int i=0;i<qualifiche.size();i++){

			String query2="insert into qualifica(qualifica_id_prete,qualifica,dal,al) values("+"(SELECT max(id_prete) FROM religiosi),"+"'"+qualifiche.get(i).qualifica+"',"+"'"+qualifiche.get(i).dal+"',"+"'"+qualifiche.get(i).al+"'"+")";
			try {
				DBConnection.inserisci_religioso (query2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void id(){

		try {
			ResultSet s= DBConnection.prendi_religioso("SELECT max(id_prete) as max FROM religiosi");
			while(s.next()){

				id=s.getLong("max");
				System.out.println("sn in id_prete"+id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void add_date(){
		for(int i=0;i<registri.size();i++){

			String query2="insert into pdata(data_id_prete,tipo_data,mydata,registro) values("+"(SELECT max(id_prete) FROM religiosi),"+"'"+registri.get(i).tipo_data+"',"+"'"+registri.get(i).data+"',"+"'"+registri.get(i).r.toString()+"'"+")";
			try {
				DBConnection.inserisci_religioso (query2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void addeta_luogo(){
		String query4= "insert into eta_luogo(eta_luogo_id_prete,eta,tipo_eta,luogo)values ("+"(SELECT max(id_prete) FROM religiosi),"+"'"+eta_morte+"',"+"'"+tipo_eta+"',"+"'"+luogo_di_sepoltuta+"'"+")";
		try {
			System.out.println(primo_estremo);
			System.out.println(ultimo_estremo);
			DBConnection.inserisci_religioso (query4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void add_Date(){
		String query3= "insert into status(status_id_prete,data_inserimento,data_piu_bassa,data_piu_alta)values ("+"(SELECT max(id_prete) FROM religiosi),"+"'"+inserimento+"',"+"'"+primo_estremo+"',"+"'"+ultimo_estremo+"'"+")";
		try {
			System.out.println(primo_estremo);
			System.out.println(ultimo_estremo);
			DBConnection.inserisci_religioso (query3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void aggiungi_datecon_registro(Data_con_Registro d){
		System.out.println("id:"+id);
		String query2="insert into pdata(data_id_prete,tipo_data,mydata,registro) values("+"'"+id+"',"+"'"+d.tipo_data+"',"+"'"+d.data+"',"+"'"+d.r.toString()+"'"+" )";
		try {
			DBConnection.inserisci_religioso (query2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void update_datecon_registro(Data_con_Registro d){
		System.out.println("id:"+id);
		String query2="UPDATE  pdata set data_id_prete="+"'"+id+"',"+"tipo_data="+"'"+d.tipo_data+"',"+"mydata="+"'"+d.data+"',"+
				"registro="+"'"+d.r.toString()+"'"+"where data_id_prete="+"'"+id+"'"+"and id="+"'"+d.id+"'";

		try {
			DBConnection.inserisci_religioso (query2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void inserisci_qualifica(Qualifica q){
		System.out.println("qualifica_id:"+id);
		String query="update qualifica set qualifica_id_prete="+"'"+id+"',"+"qualifica="+"'"+q.qualifica+"',"+"dal="+"'"+q.dal+"',"+"al="+"'"+q.al+"'"+"where qualifica_id_prete="+"'"+id+"'"+"and id="+"'"+q.id+"'";
		try {
			DBConnection.inserisci_religioso (query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insert_update_Date(){
		String query="update status set data_aggiornamento="+"'"+aggiornamento+"'"+"WHERE status_id_prete="+"'"+id+"'";
		try {
			DBConnection.inserisci_religioso (query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void modifica_prete(){
		String s="'";
		String sv="',";
		String query="update religiosi set altri_dati= "+s+altro+sv+"cognome="+s+cognome+sv+"originale="+s+originale+sv
				+"nome="+ s+nome+sv+"soprannome="+s+soprannome+sv+"detto="+s+detto+sv+"altri_nomi="+s+altri_nomi+sv+"nome_in_religione="+s+nome_in_religione+sv
				+"padre="+s+padre+sv+"madre="+s+madre+sv+"titolo_di_studio="+s+titolo_di_Studio+sv+"note="+s+note+sv+"coniuge="+s+coniuge+s+"where id_prete="+s+id+s; 
		try {
			DBConnection.inserisci_religioso(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void inseriscieta_luogo(){
		String query4= "UPDATE eta_luogo SET eta_luogo_id_prete="+"'"+id+"',"+"eta="+"'"+eta_morte+"',"+"tipo_eta="+"'"+tipo_eta+"',"+"luogo="+"'"+luogo_di_sepoltuta+"'"+"where eta_luogo_id_prete="+"'"+id+"'";
		try {
			System.out.println(primo_estremo);
			System.out.println(ultimo_estremo);
			DBConnection.inserisci_religioso (query4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void inserisci_qualifiche(){
		System.out.println("sn in inserisci qualifiche");
		System.out.println(qualifiche.size());
		for(int i=0;i<qualifiche.size();i++)
			System.out.println("sn IN inserisci qUALIFICHEe"+qualifiche.get(i).qualifica);
		for(int i=0;i<qualifiche.size();i++){
			System.out.println(qualifiche.get(i).qualifica);
			String query2="insert into qualifica(qualifica_id_prete,qualifica,dal,al) values("+"'"+id+"',"+"'"+qualifiche.get(i).qualifica+"',"+"'"+qualifiche.get(i).dal+"',"+"'"+qualifiche.get(i).al+"'"+")";
			try {
				DBConnection.inserisci_religioso (query2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void update_estremi(){
		String query3= "update status set data_piu_bassa=" +"'"+primo_estremo+"',"+"data_piu_alta="+"'"+ultimo_estremo+"'"+"where status_id_prete="+"'"+id+"'";

		try {
			DBConnection.inserisci_religioso (query3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}






