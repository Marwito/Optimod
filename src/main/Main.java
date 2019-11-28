/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import controller.Controller;

/**
 * Entry Point class.
 * Simply creates a controller instance and deleguates all the work to it.
 * @author jerome
 */
public class Main {

    /**
     * The main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        @SuppressWarnings("unused")
		Controller c = new Controller();
    }
    
}