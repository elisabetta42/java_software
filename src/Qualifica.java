import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;


public class Qualifica {

	String qualifica;
	Date dal;
	Date al;
	int id;
	int tag_dal;
	int tag_al;

	public Qualifica(){
	}

	public void setStringQualifica(String qualifica){
		this.qualifica=qualifica;
	}
	public String getStringQualifica(){
		return qualifica;
	}

	public void setDal(Date dal){      
		this.dal=dal;
	}

	public void setAl(Date al){
		this.al=al;
	}
	public void setId(int id){
		this.id=id;
	}

}