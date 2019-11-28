/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

import javax.swing.JLabel;

import model.delivery.DeliveryJourneyI;

/**
 * Specific drawer for the Store
 * @author jerome
 */
public class StoreNodeDrawer extends PointDrawer{
    
    
    private static final Color STORE_NODE_COLOR = Color.magenta;
    private String departureTime = "";
    private String arrivalTime = "";
    
    /**
     * Constructor
     * @param id the address
     * @param address the label to display the address
     * @param times the label to display the arrival/departure time for the whole journey
     */
    public StoreNodeDrawer(int id , JLabel address, JLabel times) {
        this.color = STORE_NODE_COLOR;
        this.id = id;
        address.setText("Store Address : " + this.id);
        address.setFont(new Font("Arial", Font.BOLD, 13));
        times.setFont(new Font("Arial", Font.BOLD, 13));
    }
    
    /**
     * Constructor
     * @param id the address
     * @param dj the journey to find informations about the arrival/departure time
     * @param address the label to display the address
     * @param times the label to display the arrival/departure time for the whole journey
     */
    @SuppressWarnings("deprecation")
    public StoreNodeDrawer(int id, DeliveryJourneyI dj, JLabel address, JLabel times) {
        this(id, address, times);
        Date end = dj.getDeliveryJourneyArrivalTime();
        this.departureTime = "08:00";
        this.arrivalTime = "" + (end.getHours() < 10 ? "0" + end.getHours() : end.getHours());
        this.arrivalTime += ":" + (end.getMinutes() < 10 ? "0" + end.getMinutes() : end.getMinutes());
        times.setText("Arr/Dep : " + this.departureTime + " - " + this.arrivalTime);
    }
    
}