/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * the container for the DDI. Also called DDIContainer
 * @author jerome
 */
public class DetailledDeliveryInformationContainer extends JPanel {
    
    private static final long serialVersionUID = 3263964035659890765L;
    private static final Color BACKGROUND_COLOR = Color.lightGray;

    private List<DetailledDeliveryInformation> ddiList = new LinkedList<>();

    /**
     * Constructor
     * 
     * @param minWidth This is an integer value which defines the minimal width of the container
     */
    public DetailledDeliveryInformationContainer(int minWidth) {
        this.setBackground(BACKGROUND_COLOR);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setMinimumSize(new Dimension(minWidth, 1)); 
    }
    
    /**
     * Transmit this method to every ddi it contains
     * @param w 
     */
    public void addListener(Window w) {
    	Iterator<DetailledDeliveryInformation> it = ddiList.iterator();
    	while(it.hasNext()) {
    		it.next().addListener(w);
    	}
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        Iterator<DetailledDeliveryInformation> it = this.ddiList.iterator();
        while(it.hasNext()) {
            it.next().setEnabled(enabled);
        }
    }
    
    @Override
    public Component add(Component component) {
        DetailledDeliveryInformation ddi = new DetailledDeliveryInformation();
        if(component instanceof DetailledDeliveryInformation) {
            ddi = (DetailledDeliveryInformation) component;
        }
        
        this.ddiList.add(ddi);
        super.add(component);
        this.repaint();
        return component;
    }
    
}
