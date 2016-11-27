import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;




public class Main_Panel extends JDialog{
	JButton ricerca= new JButton("Ricerca");
	JButton inserisci= new JButton("Inserisci");
	JButton istruzioni= new JButton("Istruzioni");
	JButton fonti=new JButton("Fonti");
	JButton cerca= new JButton("Cerca");
	JButton save=new JButton("Salva");
	JButton chiudi=new JButton("Chiudi");
	JDialog d;
	JScrollPane scroll;
	JTextArea a=new JTextArea(10,40);

	JPanel filtri=new JPanel();
	JPanel empty=new JPanel();
	JPanel empty2=new JPanel();

	String mio_cognome;
	Campo cognome;

	Lista_Religiosi l;

	public Main_Panel(){
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		fonti.setPreferredSize(new Dimension(110, 30));
		fonti.addActionListener(new FontiListner());
		inserisci.setPreferredSize(new Dimension(110, 30));
		ricerca.setPreferredSize(new Dimension(110, 30));
		chiudi.setPreferredSize(new Dimension(110, 30));

		gbc.gridx=1;
		gbc.gridy=0;
		gbc.ipadx=100;
		gbc.ipady=8;
		add(chiudi,gbc);

		gbc.gridx=1;
		gbc.gridy=1;
		gbc.ipadx=100;
		gbc.ipady=8;
		add(fonti,gbc);

		gbc.gridx=1;
		gbc.gridy=3;
		gbc.ipadx=100;
		gbc.ipady=8;
		add(ricerca,gbc);

		gbc.gridx=1;
		gbc.gridy=2;
		add(inserisci,gbc);

		filtri.setVisible(false);
		gbc.gridx=1;
		gbc.gridy=4;
		add(filtri,gbc);

		cognome= new Campo("","Cognome...........................................",30);
		filtri.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		filtri.setLayout(new GridLayout(3,0));
		filtri.setPreferredSize(new Dimension(250, 120));
		empty.add(cerca);
		ricerca.addActionListener(new RicercaListner());
		cerca.addActionListener(new CercaListner());
		inserisci.addActionListener(new InserisciListner());
		chiudi.addActionListener(new ChiudiListner());

		filtri.add(cognome);
		filtri.add(empty2);
		filtri.add(empty);

	}
	public void cerca(){
		JFrame frame = new JFrame("RELIGIOSI");
		mio_cognome=cognome.dato.getText();
		Lista_Religiosi	l= new Lista_Religiosi(frame, mio_cognome);
		JScrollPane	scroll = new JScrollPane (l);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scroll.setPreferredSize(new Dimension(920,600));
		scroll.setViewportView(l);

		frame.getContentPane().add(scroll);
		frame.pack();
		frame.setVisible(true);

		dispose();
	}
	private class RicercaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			filtri.setVisible(true);
		}
	}
	private class CercaListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			cerca();

		}
	}
	private class InserisciListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			JFrame frame = new JFrame("RELIGIOSI");
			Data_Interface d= new  Data_Interface(frame);
			d.setPreferredSize(new Dimension(800, 650));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(d);
			frame.pack();
			frame.setVisible(true);
			dispose();
		}
	}
	private class FontiListner implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			d=new JDialog();
			save.addActionListener(new SaveListner());
			GridBagConstraints gbc = new GridBagConstraints();
			d.setLayout(new GridBagLayout());
			scroll = new JScrollPane (a,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JPanel below=new JPanel();
			a.setLineWrap(true);
			a.setFont(new Font("Serif", Font.ITALIC, 16));
			a.setWrapStyleWord(true);;
			d.setSize(new Dimension(600, 500));       
			d.setVisible(true);
			d.add(scroll);
			below.add(save);

			gbc.gridx=1;
			gbc.gridy=0;
			d.add(scroll,gbc);

			gbc.gridx=1;
			gbc.gridy=1;
			d.add(below,gbc);
			File f = new File("D:/Utenti/User03/Desktop/fonti.doc");
			if(f.exists()){
				FileReader reader = null;
				try {
					reader = new FileReader(f);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					a.read(reader,"filename.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
				fileOut = new BufferedWriter(new FileWriter("D:/Utenti/User03/Desktop/fonti.doc") );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				a.write(fileOut);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			d.dispose();
		}
	}
	private class ChiudiListner implements ActionListener
	{

		public void actionPerformed (ActionEvent event)
		{
			int dialogResult = JOptionPane.showConfirmDialog (null,"Sicuro di voler chiudere?");
			if(dialogResult == JOptionPane.YES_OPTION){
				System.exit(0);
			}
		}
	}
}

