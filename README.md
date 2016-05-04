# ISAJournals
ISA Journal Ranking Application

Four classes:
  - ISA
  - JournalAlgorithm
  - JournalInformation
  - ExcelGeneration
  
External Libraries:
  - Swing/Windowbuilder: gui building
  - Apache POI: export to excel
  - mySQL JDBC: connect to mySQL server
  
ISA:
  - creates GUI 
  - passes GUI fields to JournalAlgorithm
  - live fills GUI with categories and years from mySQL server
  
JournalAlgorithm:
  - takes category, year, # of years to calculate average with, and file directory/name as arguments from ISA GUI
  - makes connection to mySQL server and queries for journal titles
  - creates journal objects using JournalInformation class based on arraylist of journal titles 
  - journal objects year fields defined through mysql query based on title names
  - detirmines top quintile and top half of journal rankings for grouping cutoffs
  
ExcelGeneration:
  - takes journal objects and creates an excel output file using apache poi
  - file directory/name passed along with array of journal objects
  - file directory/name detirmine excel save location and file name
  
