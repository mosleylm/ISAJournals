import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Liam
 *	Class is called from the ISAJournalRanking Application window.
 * Calls the MySQL Server Database on Ubuntu VM @ 10.36.0.145:3306 (user=root, pass=kanmos), queries
 * the Journal table in the ISAJournals Database.
 * 
 * Journal Table consists of a title, year, category, and 5-year JIF (journal impact 
 * factor). Algorithm takes in 5 years worth of JIF for a title and computes a 5-year average
 * of 5year JIF's. 
 * 		-Group I is the top 20% 
 * 		-Group II is the next 30%
 * 		-Group III is the bottom 50%
 * 
 * The toExcel method takes the resulting groupings and 5-year impact factors and generates
 * an excel file from the data.
 */


public class JournalAlgorithm {
	private JournalInfo[] journal;;
	private ArrayList<String> arrayOfTitles = new ArrayList<String>();
	private Connection conn;
	private Statement st;
	private ResultSet rs;
	private Statement st2;
	private ResultSet rs2;
	private int minNumYears;
	
	/**
	 * Connection to remote database @10.36.0.145 Port:3306
	 * Username: root
	 * Password: kanmos
	 * 
	 * @param category
	 * @param year
	 */
	public void runAlg(String category, int year, String directory, int numYears) {
		minNumYears = numYears;
		conn = null;
		st = null;
		rs = null;
		st2 = null;
		rs2 = null;

	       try
	       {

	           String url = "jdbc:mysql://10.36.0.145:3306/isajournals?allowMultiQueries=true";
	           Class.forName ("com.mysql.jdbc.Driver");
	           conn = (Connection) DriverManager.getConnection (url,"root","kanmos");
	           System.out.println ("Database connection established");
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();

	       }
	       finally
	       {
	           if (conn != null)
	           {
	        	   this.computeFiveYrAvg(category, year);
	        	   this.generateExcel(journal, directory);
	               try
	               {
	                   conn.close ();
	                   System.out.println ("Database connection terminated");
	               }
	               catch (Exception e) { /* ignore close errors */ }
	           }
	       }
	}
	
	/**
	 * Fills the array of JournalInfo objects with 
	 * information obtained through MySQL server queries
	 * based on the category and year selected through
	 * application interface options.
	 * 
	 * @param category
	 * @param year
	 */
	public void computeFiveYrAvg(String category, int year) {
		try {
			int lastYear = year - 5;
			int blwYear = year + 1;
			
			String query;
			
			query = "SELECT DISTINCT `Full JOURNAL TITLE` FROM journal WHERE category = '" + category + "';";
			
			st = (Statement) conn.createStatement();
			rs = (ResultSet) st.executeQuery(query);
			
			
			while (rs.next()) {
				
				arrayOfTitles.add(rs.getString("Full JOURNAL TITLE"));
				
			}
			
			journal = new JournalInfo[arrayOfTitles.size()];
			
			
			for (int i=0; i<arrayOfTitles.size(); i++) {
				float[] journalImp = new float[5];
				String jTitle = arrayOfTitles.get(i);
				
				String query2 = "SELECT `5-year impact factor` FROM journal WHERE category = '" + category + "' AND "
						+ "`FULL JOURNAL TITLE` = '" + jTitle + "' AND Year > " + lastYear + " AND Year < " + blwYear + ";";
				
				st2 = (Statement) conn.createStatement();
				rs2 = (ResultSet) st.executeQuery(query2);
				
				int impactInd = 0;
				while(rs2.next() && impactInd < 5) {
					System.out.println(rs2.getString(1));
					try {
						journalImp[impactInd] = Float.parseFloat(rs2.getString("5-year impact factor"));
					} catch (NumberFormatException e){
						journalImp[impactInd] = 0;
					}
					impactInd++;
				}
				
				this.journal[i] = new JournalInfo(jTitle, category, year, journalImp[0], journalImp[1], journalImp[2]
						, journalImp[3], journalImp[4], this.minNumYears);
				
				System.out.println(jTitle);
			}
			sortArray();
				
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (st != null) {
					st.close();
				}
				if (st2 != null) {
					st2.close();
				}
			} catch (SQLException ex){
				System.out.println("Exit error");
			}
		}
	}
	
	/**
	 * sorts the array based on the calculated 5 year average of 5yrJIF,
	 * and assigns a rank # based on it's new position.
	 * 
	 * Groupings will also be assigned here.
	 */
	public void sortArray() {
		Arrays.sort(journal);
		
		float group1CutOff = calcQuintile(journal);
		float group2CutOff = calcHalf(journal);
		
		
		/**
		 * sets journal rankings post-sorting
		 * 
		 * assigns groupings based on percentiles
		 * 
		 * GROUP I : TOP 20%
		 * GROUP II : TOP 50-20%
		 * GROUP III : REMAINING
		 */
		
		System.out.println(group1CutOff);
		System.out.println(group2CutOff);
		
		int rank=1;
		for(int i=journal.length - 1; i>0; i--) {
			journal[i].setRank(rank);
			
			if(journal[i].getFiveYrAvg() > group1CutOff) {
				journal[i].setGroupID("I");
			}
			else if(journal[i].getFiveYrAvg() > group2CutOff) {
				journal[i].setGroupID("II");
			}
			else {
				journal[i].setGroupID("III");
			}
			rank++;
		}
		
		
	}
	
	private float calcQuintile(JournalInfo[] journals) {
		int numZeroRows=0;
		
		for(int i=0; i<journals.length; i++) {
			if(journals[i].getFiveYrAvg() == 0){
				numZeroRows++;
			}
		}
		
		int actualLength = journals.length - numZeroRows;
		
		
		int quint = 4 * actualLength/5;
		
		return journals[numZeroRows + quint - 1].getFiveYrAvg();
	}
	
	private float calcHalf(JournalInfo[] journals) {
		int numZeroRows=0;
		
		for(int i=0; i<journals.length; i++) {
			if(journals[i].getFiveYrAvg() == 0){
				numZeroRows++;
			}
		}
		
		int actualLength = journals.length - numZeroRows;
		
		int median = actualLength/2;
		
		return journals[numZeroRows + median - 1].getFiveYrAvg();
	}
	
	/**
	 * produces an excel sheet with data production
	 * for the specified parameters from the 
	 * application interface.
	 * 
	 * @param journalList
	 */
	public void generateExcel(JournalInfo[] journalList, String directory) {
		ExcelGeneration newExcel = new ExcelGeneration();
		newExcel.writeToFile(journalList, directory);
	}
}
