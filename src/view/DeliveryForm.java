/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Date;

/**
 * Class to transmit informations to a delivery, has the shape of the fields in DeliveryFormWindow
 * @author jerome
 */
public class DeliveryForm {
    public Integer duration = 0;
    public Date minTime = new Date();
    public Date maxTime = new Date();
}
