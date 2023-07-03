package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

/** This is a class that builds a custom component, or row, for each file in our directories.
 * 
 * @author Luke Brown
 *
 */
class CustomComponent {
    private final String fileName;
    private final String filePath;


    public CustomComponent(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
    
    public String getFileName() {
    	return fileName;
    }
    
    public String getFilePath() {
    	return filePath;
    }

}

class CustomComponentField extends CustomField<CustomComponent> {
	
	TextField fileNameField = new TextField();
	TextField filePathField = new TextField();
	Button viewButton = new Button("View File");
	Button deleteButton = new Button("Delete");
	
	public CustomComponentField() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(false);
		layout.getThemeList().add("spacing-l");
		fileNameField.setReadOnly(true);
		fileNameField.setValue("File Name");
		fileNameField.setWidth("250px");
		filePathField.setReadOnly(true);
		filePathField.setValue("File Path");
		filePathField.setWidth("250px");
		deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,  ButtonVariant.LUMO_ERROR);
		layout.add(fileNameField, filePathField, viewButton, deleteButton);
		add( layout);
	}


	@Override
	protected CustomComponent generateModelValue() {
		return new CustomComponent(fileNameField.getValue(), filePathField.getValue());
	}

	@Override
	protected void setPresentationValue(CustomComponent newPresentationValue) {
		// TODO Auto-generated method stub
		fileNameField.setValue(newPresentationValue.getFileName());
		filePathField.setValue(newPresentationValue.getFilePath());
	}
	
}
