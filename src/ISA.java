
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;


public class ISA {

	public static String category = "", yearS = "", directory = "", fileName = "", numString = "";

	
	public static ArrayList<String> categoryList = new ArrayList<String>();
	public static ArrayList<String> yearList = new ArrayList<String>();
	public static Object[] yearsList;
	
	public static int numBack;
	
	final JFileChooser fc = new JFileChooser();


	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ISA window = new ISA();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ISA() {
		fillCategoryAndYears();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 240));
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 610, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Journal Ranking Application");
		
		final JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		comboBox_2.setBounds(334, 118, 175, 20);
		frame.getContentPane().add(comboBox_2);
		
		JButton btnNewButton = new JButton("Run");
		btnNewButton.setToolTipText("RUN!");
		btnNewButton.setBackground(SystemColor.controlHighlight);
		btnNewButton.setBounds(61, 315, 119, 22);
		frame.getContentPane().add(btnNewButton);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(10, 204, 89, 23);
		frame.getContentPane().add(btnBrowse);
		
		final JEditorPane editorPane = new JEditorPane();
		editorPane.setBackground(SystemColor.inactiveCaption);
		editorPane.setBounds(10, 284, 244, 20);
		frame.getContentPane().add(editorPane);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("Categorys");
		comboBox.setModel(new DefaultComboBoxModel(categoryList.toArray()));
		comboBox.setBounds(10, 90, 244, 20);
		frame.getContentPane().add(comboBox);
		
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setToolTipText("Years");
		comboBox_1.setModel(new DefaultComboBoxModel(yearsList));
		comboBox_1.setBounds(10, 146, 244, 20);
		frame.getContentPane().add(comboBox_1);
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 238, 244, 20);
		frame.getContentPane().add(textPane);
		
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		          category = (String) comboBox.getSelectedItem();
		  		  yearS = (String) comboBox_1.getSelectedItem();		  		  
		  		  fileName = (String) editorPane.getText();
		  		  numString = (String) comboBox_2.getSelectedItem();
		  		  numBack = Integer.parseInt(numString);
		  		  
		  		  String saveFile = directory + '\\' + fileName;
		  		  
		  		  int year = Integer.parseInt(yearS);
		  		  
		  		  JournalAlgorithm run = new JournalAlgorithm();
		  		  run.runAlg(category, year, saveFile, numBack);
		  		  
		        }
		      });
		
		btnBrowse.addActionListener(new java.awt.event.ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		          
		  		  JFileChooser chooser = new JFileChooser();
		  		  chooser.setCurrentDirectory(new java.io.File("."));
		  		  chooser.setDialogTitle("Browse the folder to process");
		  		  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		  		  chooser.setAcceptAllFileFilterUsed(false);

		  		  if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		  			  directory = chooser.getSelectedFile().toString();
		  			  textPane.setText(directory);
		  			  
		  		  } else {
		  			  System.out.println("No Selection Directory Selection.");
		  		  }
		  		  
		        }
		      });
		
		
		JLabel lblNewLabel = new JLabel("Choose the category:");
		lblNewLabel.setBounds(10, 65, 137, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblChooseTheYear = new JLabel("Choose the year:");
		lblChooseTheYear.setBounds(10, 121, 114, 14);
		frame.getContentPane().add(lblChooseTheYear);
		
		JLabel lblNewLabel_1 = new JLabel("NOTE: Only data from 2008+ loaded in database");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 23, 486, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Choose minimum years for average: ");
		lblNewLabel_2.setBounds(296, 226, 251, 35);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(296, 74, 189, 52);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(296, 173, 313, 54);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(296, 180, 313, 83);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(296, 142, 251, 73);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(296, 133, 313, 52);
		frame.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setBounds(296, 104, 313, 73);
		frame.getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setBounds(296, 90, 281, 60);
		frame.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_2.setBounds(324, 93, 260, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblInsertTheLocation = new JLabel("Select the directory to save the file to:");
		lblInsertTheLocation.setBounds(10, 177, 244, 28);
		frame.getContentPane().add(lblInsertTheLocation);
		
		JLabel lblTypeTheName = new JLabel("Type the name of the file (don't add .xls):");
		lblTypeTheName.setBounds(10, 255, 244, 28);
		frame.getContentPane().add(lblTypeTheName);		
	}
	
	public void fillCategoryAndYears() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st2 = null;
		ResultSet rs2 = null;
		
		try
	       {

	           String url = "jdbc:mysql://10.36.0.145:3306/isajournals?allowMultiQueries=true";
	           Class.forName ("com.mysql.jdbc.Driver");
	           conn = (Connection) DriverManager.getConnection (url,"root","kanmos");
	           System.out.println ("Database connection established");
	           
	           String query1 = "SELECT DISTINCT year FROM journal";
	           String query2 = "SELECT DISTINCT category FROM journal";
	           
	           st = (Statement) conn.createStatement();
			   rs = (ResultSet) st.executeQuery(query1);
			   
			   while(rs.next()) {
				   yearList.add(rs.getString(1));
			   }
	           
			   st2 = (Statement) conn.createStatement();
			   rs2 = (ResultSet) st2.executeQuery(query2);
			   
			   while(rs2.next()) {
				   categoryList.add(rs2.getString(1));
			   }
			   
			   /**
			    * Shorten year list to lose bottom three years
			    */
			   Object[] copyOfYears = new Object[yearList.size()];
			   
			   copyOfYears = yearList.toArray();
			   
			   yearsList = Arrays.copyOfRange(copyOfYears, 2, yearList.size());
			   
			   
			   
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();

	       }
	       finally
	       {
	           if (conn != null)
	           {
	               try
	               {
	                   conn.close ();
	                   System.out.println ("Database connection terminated");
	               }
	               catch (Exception e) { /* ignore close errors */ }
	           }
	       }
		initialize();
	}
}
