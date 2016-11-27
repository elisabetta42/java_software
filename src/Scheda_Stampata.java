import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class Scheda_Stampata implements Printable {
	ArrayList<Printablefield>gra= new ArrayList<Printablefield>();
	Scheda scheda;
	ArrayList<Data_con_Registro>d= new ArrayList<Data_con_Registro>();
	ArrayList<Qualifica> qual=new ArrayList<Qualifica>();
	public Scheda_Stampata (Scheda scheda){
		this.scheda=scheda;
	}

	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {

		// We have only one page, and 'page'
		// is zero-based
		if (page > 0) {
			return NO_SUCH_PAGE;
		}

		// User (0,0) is typically outside the
		// imageable area, so we must translate
		// by the X and Y values in the PageFormat
		// to avoid clipping.
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		// Now we perform our rendering

		g.drawString("GRUPPO UMANESIMO DELLA PIETRA",160,50);
		g.drawString("MARTINA FRANCA",216,70);
		g.drawString("ARCHIVIO ECCLESIASTICI",190,92);
		String lettera=scheda.getCognome();
		g.setFont(new Font("TimesRoman", Font.BOLD, 36)); 
		if(!lettera.equals("")){
			g.drawString(lettera.substring(0, 1),445,115);
		}

		g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
		if(!scheda.getAltro().equals("")){
			g.drawString(scheda.getAltro(), 475, 115);
		}

		g.setFont(new Font("default", Font.PLAIN, 13));
		SimpleDateFormat  sdfr = new SimpleDateFormat("dd-MM-yyyy");
		if(scheda.get_alto()!=null && scheda.get_basso()!=null){
			String alta=sdfr.format(scheda.get_alto());
			String bassa=sdfr.format(scheda.get_basso());
			g.drawString(bassa+"   "+alta,405,132);
		}
		g.setFont(new Font("default", Font.PLAIN, 10));
		String inserimento=sdfr.format(scheda.getInserimento());
		String aggiornamento=sdfr.format(scheda.getAggiornamento());
		g.drawString("Data inserimento: "+inserimento,30,800);
		g.drawString("Data aggiornamento: "+aggiornamento,430,800);


		if(!scheda.getCognome().equals("")||!scheda.getOriginale().equals("")){
			Printablefield p= new Printablefield();

			if(!scheda.getCognome().equals("")){
				p.setName("Cognome:");
				p.setField(scheda.getCognome());
			}

			if(!scheda.getOriginale().equals("")){
				p.setcentro_field("Originale:");
				p.setcentro(scheda.getOriginale());}
			gra.add(p);
		}

		if(!scheda.getNome().equals("")){
			Printablefield p= new Printablefield();
			p.setName("Nome:");
			p.setField(scheda.getNome());
			gra.add(p);
		}

		if(!scheda.get_altri_Nomi().equals("")){
			Printablefield p= new Printablefield();
			p.setName("Altri Nomi:");
			p.setField(scheda.get_altri_Nomi());
			gra.add(p);
		}

		if(!scheda.getDetto().equals("")||!scheda.getSoprannome().equals("")){
			Printablefield p= new Printablefield();
			if(!scheda.getDetto().equals("")){
				p.setName("Detto:");
				p.setField(scheda.getDetto());
				if(!scheda.getSoprannome().equals("")){
					p.setcentro_field("Soprannome:");
					p.setcentro(scheda.getSoprannome());
				}
			}
			if(scheda.getDetto().equals("")&&!scheda.getSoprannome().equals("")){
				p.setName("Soprannome:");
				p.setField(scheda.getSoprannome());
			}
			gra.add(p);
		}
		if(!scheda.getRNome().equals("")){
			Printablefield p= new Printablefield();
			p.setName("Nome in Religione:");
			p.setField(scheda.getRNome());
			gra.add(p);		
		}
		if(!scheda.getPaternita().equals("")){
			Printablefield p= new Printablefield();
			p.setName("Paternità:");
			p.setField(scheda.getPaternita());
			gra.add(p);
		}

		if(!scheda.getMaternita().equals("")){
			Printablefield p= new Printablefield();
			p.setName("Maternità:");
			p.setField(scheda.getMaternita());
			gra.add(p);
		}
		d=scheda.getRegistri();
		for (int i=0;i<d.size();i++){
			SimpleDateFormat  sd= new SimpleDateFormat("dd-MM-yyyy");
			String data1=sd.format(d.get(i).data);
			Printablefield p= new Printablefield();
			p.setName("Data "+d.get(i).tipo_data+":");
			p.setField(data1);
			if(!d.get(i).r.toString().equals("///")){
				p.fieldSinistra="R: ";
				p.namesinistra=d.get(i).r.toString();
			}
			if(d.get(i).tipo_data.equals("morte")&& !scheda.eta_morte.equals("")){
				p.centro_field="Età morte:";
				String[]s=scheda.getEta();
				p.centro=s[0]+" "+s[1];
			}
			if(d.get(i).tipo_data.equals("sepoltura")&&!scheda.getLuogo().equals("")){
				p.centro_field="Luogo Sepoltura:";
				p.centro=scheda.getLuogo();
			}
			gra.add(p);
		}


		if(!scheda.getTitolo().equals("")){
			Printablefield p= new Printablefield();
			p.setName("Titolo di Studio:");
			p.setField(scheda.getTitolo());
			gra.add(p);
		}
		if(!scheda.get_Coniuge().equals("")){
			Printablefield p= new Printablefield();
			String[]tokens = scheda.get_Coniuge().split("\n");
			String first[]=tokens[0].split(":");
			p.setName(first[0]);
			p.setField(first[1]);
			gra.add(p);
			for(int i=1;i<tokens.length-2;i=i+2){
				Printablefield pa=new Printablefield();
				pa.setName("");
				pa.setField(tokens[i]);
				pa.setcentro(tokens[i+1]);
				gra.add(pa);
			}
			Printablefield pi=new Printablefield();
			pi.setField(tokens[tokens.length-1]);
			gra.add(pi);
		}
		getQualifiche();
		if(!scheda.getNote().equals("")){
			Printablefield p= new Printablefield();
			p.setName("Note:");
			String result=addLinebreaks(scheda.getNote(),65);
			String[]tokens = result.split("\n");
			p.setField(tokens[0]);
			gra.add(p);
			for(int i=1;i<tokens.length;i++){
				Printablefield pa=new Printablefield();
				pa.setName("");
				pa.setField(tokens[i]);
				gra.add(pa);
			}
		}
		int x=20;
		int y=155;
		int current=0;
		for(int i=0;i<gra.size();i++){
			g.setFont(new Font("default", Font.ITALIC, 11));
			g.drawString(gra.get(i).getName(),x,y+(i*20));
			g.setFont(new Font("default", Font.BOLD, 11));
			g.drawString(gra.get(i).getField(), 115, y+(i*20));
			g.setFont(new Font("default", Font.ITALIC, 11));
			g.drawString(gra.get(i).centro_field, 300, y+(i*20));
			g.setFont(new Font("default", Font.BOLD, 11));
			g.drawString(gra.get(i).centro, 400, y+(i*20));
			g.setFont(new Font("default", Font.ITALIC, 11));
			g.drawString(gra.get(i).fieldSinistra,450, y+(i*20));
			g.setFont(new Font("default", Font.BOLD, 11));
			g.drawString(gra.get(i).namesinistra,465 , y+(i*20));
		}
		gra.clear();
		return PAGE_EXISTS;
	}
	public String addLinebreaks(String input, int maxLineLength) {
		StringTokenizer tok = new StringTokenizer(input," ");
		StringBuilder output = new StringBuilder(input.length());
		int lineLen = 0;
		while (tok.hasMoreTokens()) {
			String word = tok.nextToken();

			if (lineLen + word.length() > maxLineLength) {
				output.append("\n");
				lineLen = 0;
			}
			output.append(word+" ");
			lineLen += word.length();
		}
		return output.toString();
	}

	public void getQualifiche(){
		try {
			ResultSet s= DBConnection.prendi_religioso("SELECT id, qualifica,dal,al FROM qualifica  WHERE qualifica_id_prete="+"'"+scheda.id+"'"+"ORDER BY dal" );
			while(s.next()){
				Qualifica qualifica= new Qualifica();
				qualifica.setId(s.getInt("id"));
				qualifica.setStringQualifica(s.getString("qualifica"));
				qualifica.setDal(s.getDate("dal"));
				qualifica.setAl(s.getDate("al"));
				System.out.println(s.getInt("id")+s.getString("qualifica")+s.getDate("dal")+s.getDate("al"));
				qual.add(qualifica);
				System.out.println(qualifica.qualifica);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(qual.size()>0){
			Printablefield p= new Printablefield();
			p.setName("Qualifica:");
			p.setField(qual.get(0).qualifica);
			SimpleDateFormat  sd= new SimpleDateFormat("dd-MM-yyyy");
			String dal=sd.format(qual.get(0).dal);
			String al=sd.format(qual.get(0).al);
			p.setcentro("dal "+dal+"   "+"al "+al);
			gra.add(p);
			for(int i=1;i<scheda.qualifiche.size();i++){
				Printablefield pa=new Printablefield();
				pa.setName("");
				pa.setField(qual.get(i).qualifica);
				String dal1=sd.format(qual.get(i).dal);
				String al1=sd.format(qual.get(i).al);
				pa.setcentro("dal "+dal1+"   "+"al "+al1);
				gra.add(pa);
			}
		}
	}
}