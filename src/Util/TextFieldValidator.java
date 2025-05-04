package Util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TextFieldValidator {
    public static DocumentFilter getSoloTextoFilter() {
        return new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (string.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+") || text.isEmpty()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
    }
    
    public static DocumentFilter getSoloNumerosFilter() {
        return new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (text.matches("\\d+") || text.isEmpty()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
    }
    
    public static DocumentFilter getUsernameFilter() {
        return new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (string.matches("[a-zA-Z0-9]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (text.matches("[a-zA-Z0-9]+") || text.isEmpty()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
    }
    
    public static DocumentFilter getEmailFilter() {
        return new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (string.matches("[a-zA-Z0-9@._-]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (text.matches("[a-zA-Z0-9@._-]+") || text.isEmpty()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
    }
}
