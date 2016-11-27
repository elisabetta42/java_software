import java.text.SimpleDateFormat;
import java.util.*;

// Questa classe è usata per memorizzare le date con i rispettivi registri
public class Data_con_Registro {

	Registro r;
	Date data;
	String tipo_data;
	int id=0;
	//usato per distinguere una data modificata da una inserita durante la fase di modifica
	boolean tag;

	public Data_con_Registro(String tipo_data,Date data,Registro r){
		this.tipo_data=tipo_data;
		this.r=r;
		this.data=data;
	}
	public void setID(int id){
		this.id=id;
	}
	public void setTag(boolean tag){
		this.tag=tag;
	}
	
	public String toString(){
		SimpleDateFormat  sdfr = new SimpleDateFormat("dd-MM-yyyy");
		String data1=sdfr.format(data);
		return data1;
	}

}
