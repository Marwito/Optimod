package view;

import java.awt.Color;
import java.awt.Graphics;

import model.graph.NodeI;

public class NodeDrawer extends PointDrawer{

	private static final Color NODE_COLOR = Color.black;
	private static final int NODE_SIZE = 6;
	private static final int NODE_SELECTED_SIZE = 10;
	
	private int x, y;
	
    
    public NodeDrawer(NodeI node) {
    	super(PointDrawer.DRAW_ROUND);
        this.color = NODE_COLOR;
        this.id = node.getId();
        this.x = node.getX();
        this.y = node.getY();
        this.size = NODE_SIZE;
    }
    
    public void drawPoint(Graphics g) {
    	super.drawPoint(g, x, y);
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    @Override
    public void setSelected(boolean selected) {
    	super.setSelected(selected);
        if(this.stayHovered)
            return;
    	this.selected = selected;
        
        if(this.selected) {
            this.size = NODE_SELECTED_SIZE;
        }
        else {
            this.size = NODE_SIZE;
        }
    }
	
}
