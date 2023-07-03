package com.example.application.views.main;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;

import com.example.application.views.main.CSVHelper.Document;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 *  Our main view, this class builds our web components and houses most of the basic business logic for the program.
 */
@PageTitle("Mapsys Interview Project")
@Route(value = "")
public class MainView extends VerticalLayout {

    public MainView() {
    	
    	// Helper class to help us read the CSV file.
    	CSVHelper helper = new CSVHelper();
		helper.readCsv();
		
		// Set the layout of the page. We are using 2 to better manage each section.
        setMargin(true);
     
        VerticalLayout layoutSD = new VerticalLayout();
        VerticalLayout layoutSIG = new VerticalLayout();
      
        // Label for our supporting documents
        Label supportingDocumentsLabel = new Label("Supporting Documents");
        layoutSD.add(supportingDocumentsLabel);
		
		
		// Add Uploader for supporting documents
		MemoryBuffer buffer = new MemoryBuffer();
        Upload uploadSD = new Upload(buffer);

        uploadSD.addSucceededListener(event -> {
        	
        	InputStream fileData = buffer.getInputStream();
            String fileName = event.getFileName();
            
        	File target = new File("src/main/resources/Docs/SD/" + fileName);
        	
            try {
				FileUtils.copyInputStreamToFile(fileData, target);
				helper.supportingDocuments.add(new Document(fileName, "Docs\\SD\\" + fileName, "Supporting Documents"));
				helper.writeToCSV();
				
				if(fileData != null) {
					// Add a row
	 				CustomComponent component = new CustomComponent(fileName, "Docs\\SD\\" + fileName);
	 	            CustomComponentField field = new CustomComponentField();
	 	            field.setPresentationValue(component);
	 	            layoutSD.add(field);
				}
 	            
			} catch (IOException e) {
				e.printStackTrace();
			}
     
        });
        
        layoutSD.add(uploadSD);
        
        
        // Loop through each supporting Document
        
        for(Document doc : helper.supportingDocuments) {
        	CustomComponent component = new CustomComponent(doc.getName(), doc.getPath());
            CustomComponentField field = new CustomComponentField();
            field.setPresentationValue(component);
            
            // View File
            field.viewButton.addClickListener( event -> {
            	
            	try {
                    File file = new File("src/main/resources/" + doc.getPath());
                    
                    // Check if the file exists and is a PDF
                    if (file.exists() && file.isFile() && file.getName().endsWith(".pdf")) {
                    	// Open the PDF
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
                    } 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	
            });
            
            // Delete File
            field.deleteButton.addClickListener( event -> {
            	helper.supportingDocuments.remove(doc);
            	helper.writeToCSV();
            	// Remove the row
            	layoutSD.remove(field);
            	
            	try {
					FileUtils.delete(new File("src/main/resources/Docs/SD/" + doc.getName()));
					} catch (IOException e) {
					e.printStackTrace();
				}
            });
            layoutSD.add(field);
        }
        // Add our SD layout into the page
        add(layoutSD);
        
        Label signatureLabel = new Label("Signatures");
		layoutSIG.add(signatureLabel);
		
		// Add Uploader for signatures
        Upload uploadSIG = new Upload(buffer);

        uploadSIG.addSucceededListener(event -> {
        	InputStream fileData = buffer.getInputStream();
            String fileName = event.getFileName();
            
        	File target = new File("src/main/resources/Docs/SIG/" + fileName);
        	
        	 try {
 				FileUtils.copyInputStreamToFile(fileData, target);
 				helper.signatures.add(new Document(fileName, "Docs\\SIG\\" + fileName, "Signatures"));
 				helper.writeToCSV();
 				
 				if(fileData != null) {
					// Add a row
	 				CustomComponent component = new CustomComponent(fileName, "Docs\\SIG\\" + fileName);
	 	            CustomComponentField field = new CustomComponentField();
	 	            field.setPresentationValue(component);
	 	            layoutSIG.add(field);
				}
 				
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
        	 
        });
        
        layoutSIG.add(uploadSIG);
        
        // Loop through each signature
        for(Document doc : helper.signatures) {
        	CustomComponent component = new CustomComponent(doc.getName(), doc.getPath());
            CustomComponentField field = new CustomComponentField();
            field.setPresentationValue(component);
            
            // View File
            field.viewButton.addClickListener( event -> {
            	
            	try {
                    File file = new File("src/main/resources/" + doc.getPath());
                    
                    // Check if the file exists and is a PDF
                    if (file.exists() && file.isFile() && file.getName().endsWith(".pdf")) {
                    	// Open the PDF
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
                    } 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	
            });
            
            // Delete File
            field.deleteButton.addClickListener( event -> {
            	helper.signatures.remove(doc);
            	helper.writeToCSV();
            	layoutSIG.remove(field);
            	
            	try {
					FileUtils.delete(new File("src/main/resources/Docs/SIG/" + doc.getName()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });
            
            
            layoutSIG.add(field);
        }
        
        // Add our SIG layout into the page
            add(layoutSIG);
            
    }
    
}
