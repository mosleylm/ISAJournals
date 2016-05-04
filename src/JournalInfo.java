/**
 * 
 * Author: Liam Mosley (mosleylm@miamio.edu)
 * object for storing journal information for runtime
 * and writing out to an excel file sheet
 * 
 * @author Liam
 *
 */

public class JournalInfo implements Comparable<JournalInfo>{
	private String title;
	private String category;
	private int year;
	private float yr1JIF;
	private float yr2JIF;
	private float yr3JIF;
	private float yr4JIF;
	private float yr5JIF;
	private float fiveYrAvg;
	private String groupID;
	private int rank;
	
	
	public JournalInfo(String _title, String _category, int _year, float _yr1JIF, float _yr2JIF, float _yr3JIF, float _yr4JIF, float _yr5JIF, int numYears) {
		this.title = _title;
		this.category = _category;
		this.year = _year;
		this.yr1JIF = _yr1JIF;
		this.yr2JIF = _yr2JIF;
		this.yr3JIF = _yr3JIF;
		this.yr4JIF = _yr4JIF;
		this.yr5JIF = _yr5JIF;
		
		this.computeFiveYearAvg(numYears);
		System.out.println(this.getFiveYrAvg());
	}
	
	/**
	 * CALCULATES AVERAGE IF AT LEAST TWO JIF are known
	 */
	public void computeFiveYearAvg(int noYears) {
		float total=0;
		int numNonZero=0;
		if(this.getYr1JIF()>0) {
			total+=this.getYr1JIF();
			numNonZero++;
		}
		if(this.getYr2JIF()>0) {
			total+=this.getYr2JIF();
			numNonZero++;
		}
		if(this.getYr3JIF()>0) {
			total+=this.getYr3JIF();
			numNonZero++;
		}
		if(this.getYr4JIF()>0) {
			total+=this.getYr4JIF();
			numNonZero++;
		}
		if(this.getYr5JIF()>0) {
			total+=this.getYr5JIF();
			numNonZero++;
		}
		if(numNonZero >= noYears) {
			this.setFiveYrAvg(total/numNonZero);
		}
		else {
			this.setFiveYrAvg(0);
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public float getYr1JIF() {
		return yr1JIF;
	}
	public void setYr1JIF(float yr1jif) {
		yr1JIF = yr1jif;
	}
	public float getYr2JIF() {
		return yr2JIF;
	}
	public void setYr2JIF(float yr2jif) {
		yr2JIF = yr2jif;
	}
	public float getYr3JIF() {
		return yr3JIF;
	}
	public void setYr3JIF(float yr3jif) {
		yr3JIF = yr3jif;
	}
	public float getYr4JIF() {
		return yr4JIF;
	}
	public void setYr4JIF(float yr4jif) {
		yr4JIF = yr4jif;
	}
	public float getYr5JIF() {
		return yr5JIF;
	}
	public void setYr5JIF(float yr5jif) {
		yr5JIF = yr5jif;
	}
	public float getFiveYrAvg() {
		return fiveYrAvg;
	}
	public void setFiveYrAvg(float fiveYrAvg) {
		this.fiveYrAvg = fiveYrAvg;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(JournalInfo journal2) {
		return Float.compare(this.getFiveYrAvg(), journal2.getFiveYrAvg());
	}
}
