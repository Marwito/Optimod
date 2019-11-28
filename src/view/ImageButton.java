/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Personnalized label used as a button, only imageButton used in the application
 * @author jerome
 */
public class ImageButton extends JLabel implements MouseListener{
    
    /**
    * 
    */
    private static final long serialVersionUID = 294209744689877109L;

    private static final String DISABLED_SUFFIX = "Disabled";
    private static final String FORMAT = ".png";
    
    private int NORMAL_SIZE;
    private int HOOVERED_SIZE;
    private int CLICKED_SIZE;
    
    private int PREFFERED_SIZE;
    
    //private int actualSize = NORMAL_SIZE;
    
    private String name = "default";
    private ImageIcon normalDisplay;
    private ImageIcon disabledDisplay;
    
    /**
     * Constructor
     * @param filename the name of the image without the extention, if a specific disabled image is wished
     * save it under filename+"Disabled.png"
     */
    public ImageButton(String filename) {
        this.name = filename;
        this.addMouseListener(this);
        try {
            this.normalDisplay = new ImageIcon(ImageIO.read(new File("image/"+this.name+FORMAT))); 
            NORMAL_SIZE = this.normalDisplay.getIconWidth();
            HOOVERED_SIZE = (int) (NORMAL_SIZE*7f/6f);
            CLICKED_SIZE = (int) (NORMAL_SIZE*5f/6f);
            PREFFERED_SIZE = HOOVERED_SIZE + 2;
            try {
                this.disabledDisplay = new ImageIcon(ImageIO.read(new File("image/"+this.name+DISABLED_SUFFIX+FORMAT)));
                this.setDisabledIcon(this.resizeIcon(this.disabledDisplay, NORMAL_SIZE));
            }
            catch(Exception e) {
                this.disabledDisplay = null;
            }
            this.changeIcon(NORMAL_SIZE);
            
            this.setPreferredSize(new Dimension(PREFFERED_SIZE, PREFFERED_SIZE));
            
        } catch (IOException ex) {
            Logger.getLogger(ImageButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * @return the name of the image file without the extention or the "Disabled" part
     */
    public String getButtonName() {
        return this.name;
    }
    
    /**
     * Resize the given icon to the new size, and return it
     * @param icon
     * @param size
     * @return 
     */
    private ImageIcon resizeIcon(ImageIcon icon, int size) {
        // int offset = (this.actualSize - size)/2;
        // TODO translate on resize (move point to center)
        //this.actualSize = size;
        return new ImageIcon(icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
    }
    
    /**
     * Set another size of the icon in the label
     * @param size the new size for the icon
     */
    private void changeIcon(int size) {
        ImageIcon tps = this.resizeIcon(this.normalDisplay, size);
        this.setIcon(tps);
        this.repaint();
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        if(enabled) {
            this.changeIcon(NORMAL_SIZE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.isEnabled()) {
            this.changeIcon(CLICKED_SIZE);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.isEnabled()) {
            this.changeIcon(HOOVERED_SIZE);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(this.isEnabled()) {
            this.changeIcon(HOOVERED_SIZE);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(this.isEnabled()) {
            this.changeIcon(NORMAL_SIZE);
        }
    }
    
}
