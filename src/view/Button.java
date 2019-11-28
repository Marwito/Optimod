/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 * Personnalized button, only textButton used in the application
 * @author jerome
 */
public class Button extends JButton{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6437523312024233761L;

    private static final Dimension PREFERRED_SIZE = new Dimension(150,50);
    
    public Button(String text)
    {
        super(text);
        this.setMaximumSize(PREFERRED_SIZE);
        this.setMinimumSize(PREFERRED_SIZE);
    }
    
}
