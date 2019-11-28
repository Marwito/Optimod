/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;

import controller.Controller;
import model.delivery.DeliveryI;

/**
 * Popup window to add or modify informations on a delivery.
 * As a type of Window, send message to the controller
 * @author jerome
 */
public class DeliveryFormWindow extends JFrame implements ActionListener {
    
    /**
    * 
    */
    private static final long serialVersionUID = 6820542278925976961L;

    private static final Dimension SIZE = new Dimension(300, 200);
    private final JButton valid = new JButton(VALID_TEXT);
    
    private JFormattedTextField duration;
    private JFormattedTextField beginTimeFrame;
    private JFormattedTextField endTimeFrame;
    
    public static final String VALID_TEXT = "Validate";
    public static final String DURATION = "duration (min) :";
    public static final String BEGIN_TIMEFRAME = "timewindow start :";
    public static final String END_TIMEFRAME = "timewindow end :";
    
    private Controller ctrl;
    private DeliveryI delivery = null;
    
    /**
     * Constructor to modify a delivery
     * @param ctrl reference to the controller
     * @param delivery the delivery to modify 
     */
    @SuppressWarnings("deprecation")
	public DeliveryFormWindow(Controller ctrl, DeliveryI delivery) {
    	this(ctrl);
        this.delivery = delivery;
        int deliveryDuration = this.delivery.getDurationInMin();
        String durationTx = "";
        if(deliveryDuration < 1) {
            durationTx = "000";
        }
        else if(deliveryDuration < 10) {
            durationTx = "00";
        }
        else if(deliveryDuration < 100) {
            durationTx = "0";
        }
        
        String tfB = "00:00";
        String tfE = "00:00";
        if(delivery.hasTimeframe()) {
            tfB = (delivery.getMinTime().getHours() < 10 ? "0" + delivery.getMinTime().getHours() : delivery.getMinTime().getHours() )
                    + ":" + (delivery.getMinTime().getMinutes() < 10 ? "0" + delivery.getMinTime().getMinutes() : delivery.getMinTime().getMinutes() );
        
            tfE = (delivery.getMaxTime().getHours() < 10 ? "0" + delivery.getMaxTime().getHours() : delivery.getMaxTime().getHours() )
                    + ":" + (delivery.getMaxTime().getMinutes() < 10 ? "0" + delivery.getMaxTime().getMinutes() : delivery.getMaxTime().getMinutes() );
            
        }
        
        this.beginTimeFrame.setText(tfB);
        this.endTimeFrame.setText(tfE);
        
        durationTx += deliveryDuration;
        durationTx += " min";
        this.duration.setText(durationTx);
        
        
    }
    
    /**
     * Constructor to add a delivery
     * @param ctrl reference to the controller
     */
    public DeliveryFormWindow(Controller ctrl) {
    	this.ctrl = ctrl;
        try {
            this.setResizable(false);
            this.setPreferredSize(SIZE);
            this.setSize(SIZE);
            this.setLayout(new GridLayout(4, 2));
            NumberFormat format = NumberFormat.getNumberInstance();
            format.setMaximumFractionDigits(0);
            this.duration = new JFormattedTextField(format);
            this.duration.setText("1");
            this.beginTimeFrame = new JFormattedTextField(new MaskFormatter("##:##"));
            this.beginTimeFrame.setText("00:00");
            this.endTimeFrame = new JFormattedTextField(new MaskFormatter("##:##"));
            this.endTimeFrame.setText("00:00");
            this.add(new JLabel(DURATION));
            this.add(this.duration);
            this.add(new JLabel(BEGIN_TIMEFRAME));
            this.add(this.beginTimeFrame);
            this.add(new JLabel(END_TIMEFRAME));
            this.add(this.endTimeFrame);
            this.add(new JLabel());
            this.add(this.valid);
            this.valid.addActionListener(this);
        }
        catch(Exception e) {
            
        }
             
    }
    
    @SuppressWarnings("deprecation")
    private DeliveryForm getForm() {
        DeliveryForm form = new DeliveryForm();
        try {
            form.duration = Integer.parseInt(this.duration.getText().replaceAll("[^0-9]", ""));
            if(form.duration == 0) {
                duration = null;
            }
            
            int hour = Integer.parseInt(this.beginTimeFrame.getText().split(":")[0].replaceAll("[^0-9]", ""));
            int minutes = Integer.parseInt(this.beginTimeFrame.getText().split(":")[1].replaceAll("[^0-9]", ""));
            if (hour == 0 && minutes == 0) {
                form.minTime = null;
            }
            else {
                form.minTime.setHours(hour);
                form.minTime.setMinutes(minutes);
            }
            
            hour = Integer.parseInt(this.endTimeFrame.getText().split(":")[0].replaceAll("[^0-9]", ""));
            minutes = Integer.parseInt(this.endTimeFrame.getText().split(":")[1].replaceAll("[^0-9]", ""));
            if (hour == 0 && minutes == 0) {
                form.maxTime = null;
            }
            else {
                form.maxTime.setHours(hour);
                form.maxTime.setMinutes(minutes);
            }
        }
        catch(Exception e) {

        }

        return form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.delivery == null) {
            this.ctrl.addNewDelivery(this.getForm());
        }
        else {
            this.ctrl.modifyDelivery(delivery, this.getForm());
        }
        this.setVisible(false);

    }    
}
