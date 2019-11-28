/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;

/**
 * One custom widget to display the textual information
 * from one delivery. also called DDI
 * @author jerome
 */
public class DetailledDeliveryInformation extends JPanel implements MouseListener{
    
    private static final long serialVersionUID = -482625727213007669L;

    private DeliveryI delivery;
    protected DeliveryDrawer parent;
    private Set<MouseListener> partialListeners = new HashSet<>();
    
    private JLabel address;
    private JLabel duration;
    private JLabel timeframe;
    private JLabel estimation;
    private JLabel waitingTime;
    private ImageButton delete = new ImageButton(DELETE_BUTTON_NAME);
    private ImageButton modify = new ImageButton(MODIFY_BUTTON_NAME);
    private String lastButtonName = "";
    
    private boolean clicked = false;
    
    public static final String DELETE_BUTTON_NAME = "delete";
    public static final String MODIFY_BUTTON_NAME = "modify";
    
    private static final String ADDRESS_TXT = "<html><b>Address :</b> ";
    private static final String DURATION_TXT = "<html><b>Duration :</b> ";
    private static final String DURATION_UNIT_TXT = " min";
    private static final String TIMEFRAME_TXT = "<html><b>Timewindow :</b> ";
    private static final String DEFAULT_TIMEFRAME_TXT = "anytime";
    private static final String ESTIMATION_TXT = "<html><b>Arr./dep. times :</b> ";
    private static final String WAINTING_TXT = "<html><b>Waiting time :</b> ";
    private static final String UNKNOWN_TXT = "unknown";
    private static final String END_OF_LABEL = "</html>";
    
    private static final Color SELECTED_BACKGROUND_COLOR = new Color(230,230,230); // gray
    private static final Color CLICKED_BACKGROUND_COLOR = new Color(200,200,230); // blue gray
    private static final Color BACKGROUND_COLOR = Color.white;
    private static final Color BORDER_COLOR = Color.black;
    private static final Color WARNING_COLOR = Color.red;
    private static final Color SELECTED_BORDER_COLOR = new Color(255,237,15); //gold
    private static final int SELECTED_BORDER_WEIGHT = 1;
    private static final int BORDER_WEIGHT = 1;
    
    
    
    
    /**
     * Constructor
     * 
     * @param delivery The Delivery to be drawn
     * @param parent the deliveryDrawer supposed to draw this ddi
     */
    public DetailledDeliveryInformation(DeliveryI delivery, DeliveryDrawer parent) {
        this();
        this.delivery = delivery;
        this.parent = parent;
        this.setInformations(delivery);
    }
    
    /**
     * Constructor
     *
     */
    public DetailledDeliveryInformation() {
        super();
        this.setLayout(new GridBagLayout());
        this.setBackground(BACKGROUND_COLOR);
        this.setBorder(new LineBorder(BORDER_COLOR, BORDER_WEIGHT));
        this.address = new JLabel(ADDRESS_TXT + UNKNOWN_TXT + END_OF_LABEL);
        this.duration = new JLabel(DURATION_TXT + UNKNOWN_TXT + END_OF_LABEL);
        this.timeframe = new JLabel(TIMEFRAME_TXT + DEFAULT_TIMEFRAME_TXT + END_OF_LABEL);
        this.estimation = new JLabel(ESTIMATION_TXT + UNKNOWN_TXT + END_OF_LABEL);
        this.waitingTime = new JLabel(WAINTING_TXT + "00:00" + END_OF_LABEL);
        
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.gridx=0; c.gridy=0; c.weighty=1; c.weightx=1;
        c.fill = java.awt.GridBagConstraints.BOTH;
        this.add(this.address, c);
        
        java.awt.GridBagConstraints c2 = new java.awt.GridBagConstraints();
        c2.gridx=0; c2.gridy=1; c2.weighty=1; c2.weightx=3;
        c2.fill = java.awt.GridBagConstraints.BOTH;
        this.add(this.duration, c2);
        
        java.awt.GridBagConstraints c3 = new java.awt.GridBagConstraints();
        c3.gridx=0; c3.gridy=2; c3.weighty=1; c3.weightx=3;
        c3.fill = java.awt.GridBagConstraints.BOTH;
        this.add(this.timeframe, c3);
        
        java.awt.GridBagConstraints c4 = new java.awt.GridBagConstraints();
        c4.gridx=0; c4.gridy=3; c4.weighty=1; c4.weightx=3;
        c4.fill = java.awt.GridBagConstraints.BOTH;
        this.add(this.estimation, c4);
        
        java.awt.GridBagConstraints c5 = new java.awt.GridBagConstraints();
        c5.gridx=2; c5.gridy=0; c5.weighty=1; c5.weightx=1;
        c5.fill = java.awt.GridBagConstraints.BOTH;
        this.add(this.delete, c5);
        this.delete.addMouseListener(this);
        
        java.awt.GridBagConstraints c6 = new java.awt.GridBagConstraints();
        c6.gridx=1; c6.gridy=0; c6.weighty=1; c6.weightx=1;
        c5.fill = java.awt.GridBagConstraints.BOTH;
        this.add(this.modify, c6);
        this.modify.addMouseListener(this);
        
        java.awt.GridBagConstraints c7 = new java.awt.GridBagConstraints();
        c7.gridx=0; c7.gridy=4; c7.weighty=1; c7.weightx=3;
        c7.fill = java.awt.GridBagConstraints.BOTH;
        this.add(this.waitingTime, c7);
        
        this.repaint();
        
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        this.modify.setEnabled(enabled);
        this.delete.setEnabled(enabled);
    }
    
    /**
     * add a window as special mouseListener, to have specific operations executed
     * in the window
     * @param listener 
     */
    public void addListener(Window listener) {
    	this.partialListeners.add(listener);
        this.addMouseListener(listener);
    	
    }
    
    /**
     * This method sets the background and border color of an item to selected
     */
    public void hoovered() {
        if(this.clicked) {
            this.setBackground(CLICKED_BACKGROUND_COLOR);
            this.setBorder(new LineBorder(SELECTED_BORDER_COLOR, SELECTED_BORDER_WEIGHT));
            repaint();
            return;
        }
        this.setBackground(SELECTED_BACKGROUND_COLOR);
        this.setBorder(new LineBorder(SELECTED_BORDER_COLOR, SELECTED_BORDER_WEIGHT));
        repaint();
    }
    
    /**
     * This method sets the background and border color of an item back to standard
     */
    public void unHoovered() {
        if(this.clicked) {
            this.setBackground(CLICKED_BACKGROUND_COLOR);
            this.setBorder(new LineBorder(SELECTED_BORDER_COLOR, SELECTED_BORDER_WEIGHT));
            repaint();
            return;
        }
        this.setBackground(BACKGROUND_COLOR);
        this.setBorder(new LineBorder(BORDER_COLOR, BORDER_WEIGHT));
        repaint();
    }
    
    /**
     * (Un)Set clicked parameter, superior in priority to the selected parameter
     * @param clicked true : set to clicked, false : unset 
     */
    public void clicked(boolean clicked) {     
        this.clicked = clicked;
        if(this.clicked) {
            this.setBackground(CLICKED_BACKGROUND_COLOR);
            this.setBorder(new LineBorder(SELECTED_BORDER_COLOR, SELECTED_BORDER_WEIGHT));
            repaint();
        }
        
        this.parent.setSelected(clicked);
    }
    
    /**
     * getter for clicked parameter
     * @return clicked
     */
    public boolean isClicked() {
        return this.clicked;
    }
    
    /**
     * getter for the inner delivery
     * @return the delivery used to display the infos
     */
    public DeliveryI getDelivery() {
        return delivery;
    }
    
    /**
     * fill the widget with informations
     * 
     * @param delivery The delivery in which the informations are found
     */
    @SuppressWarnings("deprecation")
    public final void setInformations(DeliveryI delivery) {
        
        String tf = DEFAULT_TIMEFRAME_TXT;
        if(delivery.hasTimeframe()) {
            tf = (delivery.getMinTime().getHours() < 10 ? "0" + delivery.getMinTime().getHours() : delivery.getMinTime().getHours() )
                    + ":" + (delivery.getMinTime().getMinutes() < 10 ? "0" + delivery.getMinTime().getMinutes() : delivery.getMinTime().getMinutes() )
                    + " - " + (delivery.getMaxTime().getHours() < 10 ? "0" + delivery.getMaxTime().getHours() : delivery.getMaxTime().getHours() )
                    + ":" + (delivery.getMaxTime().getMinutes() < 10 ? "0" + delivery.getMaxTime().getMinutes() : delivery.getMaxTime().getMinutes() );
        }
        
        this.address.setText(ADDRESS_TXT + delivery.getAddress().getId() + END_OF_LABEL);
        this.duration.setText(DURATION_TXT + delivery.getDurationInMin() + DURATION_UNIT_TXT + END_OF_LABEL);
        this.timeframe.setText(TIMEFRAME_TXT + tf + END_OF_LABEL);
        this.estimation.setText(ESTIMATION_TXT + UNKNOWN_TXT + END_OF_LABEL);
        this.waitingTime.setText(WAINTING_TXT + "00:00" + END_OF_LABEL);
        this.repaint();
    }
    
    /**
     * fill the widget with informations, and complete with informations
     * relative to the computation
     * 
     * @param delivery The delivery in which the informations are found
     * @param dj the journey to find the informations computed
     */
    @SuppressWarnings("deprecation")
    public final void setInformations(DeliveryI delivery, DeliveryJourneyI dj) {
        
        this.setInformations(delivery);
        if(dj == null) {
            return;
        }
        this.estimation.setText(ESTIMATION_TXT + (dj.getArrivalTime(delivery).getHours() < 10 ? "0" + dj.getArrivalTime(delivery).getHours() : dj.getArrivalTime(delivery).getHours() )
                                                + ":" + (dj.getArrivalTime(delivery).getMinutes() < 10 ? "0" + dj.getArrivalTime(delivery).getMinutes() : dj.getArrivalTime(delivery).getMinutes() )
                                                + " - " + (dj.getDepartureTime(delivery).getHours() < 10 ? "0" + dj.getDepartureTime(delivery).getHours() : dj.getDepartureTime(delivery).getHours() )
                                                + ":" + (dj.getDepartureTime(delivery).getMinutes() < 10 ? "0" + dj.getDepartureTime(delivery).getMinutes() : dj.getDepartureTime(delivery).getMinutes() ) +END_OF_LABEL);
 
        if(delivery.hasTimeframe()) {
            int minutes = 0;
            int hour = 0;

            minutes = delivery.getMinTime().getMinutes() - dj.getArrivalTime(delivery).getMinutes();
            if(minutes < 0) {
                hour--;
                minutes += 60;
            }

            hour += delivery.getMinTime().getHours() - dj.getArrivalTime(delivery).getHours();
            if(hour >= 0 && minutes >= 0)
                this.waitingTime.setText(WAINTING_TXT + (hour < 10 ? "0" + hour : "" + hour) + ":" + (minutes < 10 ? "0" + minutes : "" + minutes) + END_OF_LABEL);
        }
        
        if(delivery.hasTimeframe() && (delivery.getMaxTime().getTime() < dj.getArrivalTime(delivery).getTime() || delivery.getMaxTime().getTime() < dj.getDepartureTime(delivery).getTime())) {
            this.address.setForeground(WARNING_COLOR);
            this.duration.setForeground(WARNING_COLOR);
            this.timeframe.setForeground(WARNING_COLOR);
            this.estimation.setForeground(WARNING_COLOR);
            this.waitingTime.setForeground(WARNING_COLOR);
        }
        this.repaint();
    }
    
    /**
     * @return the name of the last button called inside the ddi (delete, modify, ...)
     */
    public String getLastButtonCalledName() {
    	String tps = this.lastButtonName;
        this.lastButtonName = "";
        return tps;
    }

/*****************************MOUSELISTENER****************************************/
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
            this.lastButtonName = ((ImageButton) arg0.getSource()).getButtonName();
            arg0.setSource(this);
            Iterator<MouseListener> it = this.partialListeners.iterator();
            while(it.hasNext()) {
                    it.next().mouseClicked(arg0);
            }

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
            // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
            // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
            // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
            // TODO Auto-generated method stub

    }
    
}
