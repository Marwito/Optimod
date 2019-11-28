/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;

/**
 * The variations a primary color make when scanning the specter
 * @author jerome
 */
public class ColorCurve {
    
    private int length;
    private int intervalLength;  
    private int actualPosition = 0;
    private int decal = 0;
    
    /**
     * 
     * @param primary the primary color relative to this curve
     * @param numberOfIntervals how many reporting to do (precision)
     */
    public ColorCurve(Color primary, int numberOfIntervals) {
        length = 6*255;
        intervalLength = length / numberOfIntervals;
        
        if(primary.equals(Color.red)) {
            decal = 2;
        }
        if(primary.equals(Color.blue)) {
            decal = -2;
        }
        
        
    }
    
    /**
     * Get the intensity of the curve for this reporting point
     * @return 
     */
    public int getIntensity() {
        
        if((this.actualPosition >= 255-decal*255 % length && this.actualPosition <= 255*3-decal*255 % length)) {
            return 255;
        }
        
        if((this.actualPosition >= 255*4-decal*255 % length && this.actualPosition <= 255*6-decal*255 % length)) {
            return 0;
        }
        
        if((this.actualPosition >= 0-decal*255 % length && this.actualPosition <= 255-decal*255 % length)) {
            return this.actualPosition % 255;
        }
        
        if((this.actualPosition >= 255*3-decal*255 % length && this.actualPosition <= 255*4-decal*255 % length)) {
            return 255 - (this.actualPosition % 255);
        }
        return 0;
    }
    
    /**
     * go to the next reporting point, loop if on this end
     * @return 
     */
    public ColorCurve next() {
        this.actualPosition += this.intervalLength;
        this.actualPosition %= length+255;
        return this;
    }
    
    
    
}