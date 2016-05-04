import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Author: Liam Mosley (mosleylm@miamioh.edu)
 * 
 * Class generates an excel file after being based a directory location
 * to save the file and an array of journal information objects.
 */

public class ExcelGeneration {
	public void writeToFile(JournalInfo[] journal, String directory) {
		HSSFWorkbook journalRankings = new HSSFWorkbook();
		HSSFSheet sheet = journalRankings.createSheet("Rankings");
		int FYear = journal[0].getYear();
		int rowNum=0;
		
		
		/** 
		 * Column names
		 */
		Row colTitles = sheet.createRow(rowNum);
		
		Cell groupTitle = colTitles.createCell(0);
		groupTitle.setCellValue("Grouping");
		
		Cell rankTitle = colTitles.createCell(1);
		rankTitle.setCellValue("Rank");
		
		Cell journalTitle = colTitles.createCell(2);
		journalTitle.setCellValue("Title");
		
		Cell yr1 = colTitles.createCell(3);
		yr1.setCellValue(FYear-4);
		
		Cell yr2 = colTitles.createCell(4);
		yr2.setCellValue(FYear-3);
		
		Cell yr3 = colTitles.createCell(5);
		yr3.setCellValue(FYear-2);
		
		Cell yr4 = colTitles.createCell(6);
		yr4.setCellValue(FYear-1);
		
		Cell yr5 = colTitles.createCell(7);
		yr5.setCellValue(FYear);
		
		Cell avg = colTitles.createCell(8);
		avg.setCellValue("5YRJIF Average");
		
		rowNum++;
		
		/** 
		 * Fill rows
		 */
		for (int i=journal.length - 1; i>0; i--) {
			Row row = sheet.createRow(rowNum);
			
			Cell jGroup = row.createCell(0);
			jGroup.setCellValue(journal[i].getGroupID());
			
			Cell jRank = row.createCell(1);
			jRank.setCellValue(journal[i].getRank());
			
			Cell jTitle = row.createCell(2);
			jTitle.setCellValue(journal[i].getTitle());
			
			if (journal[0].getYear() == 2010) {
				Cell jYr1 = row.createCell(3);
				jYr1.setCellValue(0);
				
				Cell jYr2 = row.createCell(4);
				jYr2.setCellValue(0);
				
				Cell jYr3 = row.createCell(5);
				jYr3.setCellValue(journal[i].getYr1JIF());
				
				Cell jYr4 = row.createCell(6);
				jYr4.setCellValue(journal[i].getYr2JIF());
				
				Cell jYr5 = row.createCell(7);
				jYr5.setCellValue(journal[i].getYr3JIF());
			}
			else if (journal[0].getYear() == 2011) {
				Cell jYr1 = row.createCell(3);
				jYr1.setCellValue(0);
				
				Cell jYr2 = row.createCell(4);
				jYr2.setCellValue(journal[i].getYr1JIF());
				
				Cell jYr3 = row.createCell(5);
				jYr3.setCellValue(journal[i].getYr2JIF());
				
				Cell jYr4 = row.createCell(6);
				jYr4.setCellValue(journal[i].getYr3JIF());
				
				Cell jYr5 = row.createCell(7);
				jYr5.setCellValue(journal[i].getYr4JIF());
			}
			else {
				
				Cell jYr1 = row.createCell(3);
				jYr1.setCellValue(journal[i].getYr1JIF());
				
				Cell jYr2 = row.createCell(4);
				jYr2.setCellValue(journal[i].getYr2JIF());
				
				Cell jYr3 = row.createCell(5);
				jYr3.setCellValue(journal[i].getYr3JIF());
				
				Cell jYr4 = row.createCell(6);
				jYr4.setCellValue(journal[i].getYr4JIF());
				
				Cell jYr5 = row.createCell(7);
				jYr5.setCellValue(journal[i].getYr5JIF());
			}

			Cell jAvg = row.createCell(8);
			jAvg.setCellValue(journal[i].getFiveYrAvg());
			rowNum++;
		}
		
		/**
		 * Create excel file and write data to file
		 */
		try {
			FileOutputStream out = new FileOutputStream(new File(directory + ".xls"));
			journalRankings.write(out);
			out.close();
			journalRankings.close();
			
			System.out.println("Excel successfully written");
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
}
