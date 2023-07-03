package com.example.application.views.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a helper class, it reads and writes to the provided Documents.csv file.
 */
public class CSVHelper {
	
	 static List<Document> supportingDocuments;
	 static List<Document> signatures ;
	
	CSVHelper(){
		
	}
	
    public static void readCsv (){
        String csvFile = "src/main/resources/Documents.csv";
        String line;
        String csvSplitBy = ",";
        
        Map<String, List<Document>> categoryMap = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header line
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                
                String name = data[0].trim();
                String path = data[1].trim();
                String category = data[2].trim();
                
                Document document = new Document(name, path, category);
                
                List<Document> documents = categoryMap.getOrDefault(category, new ArrayList<>());
                documents.add(document);
                categoryMap.put(category, documents);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Access the documents by category
         supportingDocuments = categoryMap.get("Supporting Documents");
         signatures = categoryMap.get("Signatures");
 
    }
    
    public static void writeToCSV() {
    	List<Document> documents = supportingDocuments;
    	documents.addAll(signatures);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Documents.csv"))) {
            // Write the header line
            writer.write("Name,Path,Category");
            writer.newLine();

            // Write the documents
            for (Document document : documents) {
                writer.write(document.getName() + "," + document.getPath() + "," + document.getCategory());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class Document {
        private String name;
        private String path;
        private String category;
        
        public Document(String name, String path, String category) {
            this.name = name;
            this.path = path;
            this.category = category;
        }
        
        public String getName() {
            return name;
        }
        
        public String getPath() {
            return path;
        }
        
        public String getCategory() {
            return category;
        }
    }
}