/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Base class to draw points on the DisplayedMap
 * @author jerome
 */
public abstract class PointDrawer{
    
    protected enum Shape {
            ROUND,
            RECT
    }
    
    protected DisplayedMap container = null;
    protected boolean selected = false;
    protected int id;
    protected Color color;
    protected Shape shape = Shape.RECT;
    protected int size = NORMAL_SIZE;
    protected boolean stayHovered = false;
    
    protected static final int NORMAL_SIZE = 10;
    protected static final int HOVERED_SIZE = 15;
    protected static final Color STAY_HOVERED_BOUNDS_COLOR = Color.YELLOW;
    protected static final int STAY_HOVERED_SIZE = 17;
  
    public static final int DRAW_ROUND = 0x00000001;
    
    /**
     * Constructor
     * @param flags 
     */
    public PointDrawer(int flags) {
    	
    	if((flags & DRAW_ROUND) == DRAW_ROUND) {
    		this.shape = Shape.ROUND;
    	}
    	
    }
    
    /**
     * Constructor
     */
    public PointDrawer() {
    	
    }
    
    /**
     * Draw the point using g on the coordinates x, y, function of the options
     * saved in the object
     * @param g the graphics from DisplayedMap
     * @param x
     * @param y 
     */
    public void drawPoint(Graphics g, int x, int y) {
        
        g.setColor(color);
        
        switch(shape) {
        case RECT:
        	g.fillRect(x-this.size/2, y-this.size/2, this.size, this.size);
        	if(this.stayHovered) {
        		g.setColor(STAY_HOVERED_BOUNDS_COLOR);
        		g.drawRect(x-this.size/2-1, y-this.size/2-1, this.size+2, this.size+2);
        	}
        	break;
        case ROUND:
        	g.fillOval(x-this.size/2, y-this.size/2, this.size, this.size);
        	if(this.stayHovered) {
        		g.setColor(STAY_HOVERED_BOUNDS_COLOR);
        		g.drawOval(x-this.size/2-1, y-this.size/2-1, this.size+2, this.size+2);
        	}
        	break;
        }; 
        
    }
    
    /**
     * (Un)set the drawer to the selected state.
     * Options of drawing change in this state. If stayHovered, has no effects
     * @param selected 
     */
    public void setSelected(boolean selected) {
    	
    	if(this.selected && this.stayHovered)
    		return;
    	
        this.selected = selected;
        
        if(this.selected) {
            this.size = HOVERED_SIZE;
        }
        else {
            this.size = NORMAL_SIZE;
        }
    }
    
    /**
     * (Un)set the drawer to the stayHovered state. This is superior in priority
     * to the selected state
     * @param stayHovered 
     */
    public void setStayHovered(boolean stayHovered) {
    	this.stayHovered = stayHovered;
    	
    	if(this.stayHovered) {
            this.size = STAY_HOVERED_SIZE;
        }
    	
    }
    
    /**
     * @return the address of the point
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * set a reference to a displayedMap in the drawer
     * @param dm 
     */
    public void setContainer(DisplayedMap dm) {
        container = dm;
    }
    
}
