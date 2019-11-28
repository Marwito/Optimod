/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;

/**
 * Class responsible of the drawing of the deliveries in the graphic view 
 * AND the textual one and also the linking event between them
 * 
 * @author jerome
 */
public class DeliveryDrawer extends PointDrawer implements MouseListener{
    
    private DetailledDeliveryInformation informations = null;
    
    private static final Color DELIVERY_COLOR = Color.blue;
    private static final Color DELIVERY_WARNING_COLOR = Color.red;

    /**
     * Constructor
     * 
     * @param delivery The Delivery to be drawn
     * @param w unused, keeped for compatibility
     */
    public DeliveryDrawer(DeliveryI delivery, Window w) {
    
        this.color = DELIVERY_COLOR;
        this.informations = new DetailledDeliveryInformation(delivery, this);
        this.informations.addMouseListener(this);
        this.id = delivery.getAddress().getId();
    }
    
    /**
     * Constructor
     * @param delivery the delivery to be drawn
     * @param w unused, keeped for compatibility
     * @param dj the journey to add the informations relatives to the computing to the ddi
     */
    public DeliveryDrawer(DeliveryI delivery, Window w, DeliveryJourneyI dj) {
    
        this(delivery, w);
        long departure = dj.getDepartureTime(delivery).getTime();
        long arrival = dj.getArrivalTime(delivery).getTime();
        Date endTf = delivery.getMaxTime();
        if(endTf != null && (endTf.getTime() < departure || endTf.getTime() < arrival)) {
            this.color = DELIVERY_WARNING_COLOR;
        }
        this.informations.setInformations(delivery, dj);
    }
    
    /**
     * This method gets the detailed information of a delivery
     * 
     * @return DetailledDeliveryInformation The Information about the Delivery is returned
     */
    public DetailledDeliveryInformation getDetailledDeliveryInformation() {
        return this.informations;
    }
    
    /**
     * Set the DetailledDeliveryInformation
     * @param ddi the ddi to be set
     */
    public void setDetailledDeliveryInformation(DetailledDeliveryInformation ddi) {
        this.informations = ddi;
        ddi.parent = this;
    }
    
    /**
     * This method sets a delivery as true (selected) or false (not selected) 
     * if the delivery is clicked, this method has no impact
     * @param selected This is a boolean value which tells if a delivery is selected or not
     */
    public void setSelected(boolean selected) {
        if(this.informations.isClicked() || this.stayHovered) {
            selected = true;
        }
    	super.setSelected(selected);
        this.selected = selected;
        
        if(this.selected) {
            this.size = HOVERED_SIZE;
            this.informations.hoovered();
        }
        else {
            this.size = NORMAL_SIZE;
            this.informations.unHoovered();
        }
        
        if(this.container != null) {
            this.container.repaint();
        }
        
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
/**************************MOUSELISTENER*************************************/
    
    @Override
    public void mouseEntered(MouseEvent e) {
        this.setSelected(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setSelected(false);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    
}
