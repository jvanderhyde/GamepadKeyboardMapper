/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputjoystick;

import net.java.games.input.Component;
import net.java.games.input.Controller;

/**
 *
 * @author james
 */
public class GamepadController
{
    private Controller controller;
    private boolean[] buttonsOn1,buttonsOn2;
    private boolean[] buttonsOn,buttonsOnPrev;

    public GamepadController(Controller controller)
    {
        this.controller = controller;
        
        //Poll the controller for the first time
        boolean controllerValid = controller.poll();
        if(!controllerValid) 
        {
            //Lost the controller already
            throw new GamepadException("Controller disconnected.");
        }
        Component[] components = controller.getComponents();
        
        //Set up button buffers
        buttonsOn1 = new boolean[components.length];
        buttonsOn2 = new boolean[components.length];
        buttonsOn = buttonsOn1;
        for (int i=0; i<components.length; i++)
        {
            if (components[i].getPollData() > 0.5f)
                buttonsOn[i]=true;
            else
                buttonsOn[i]=false;
        }
    }
    
    public Controller getController()
    {
        return this.controller;
    }
    
    public void pollController()
    {
        //poll the controller
        boolean controllerValid = controller.poll();
        if (!controllerValid)
        {
            controller = null;
            System.out.println("Controller disconnected.");
            return;
        }
        
        Component[] components = controller.getComponents();
        int numComponents = components.length;
        if (buttonsOn.length < numComponents)
            numComponents = buttonsOn.length;
        
        //swap the button buffers
        buttonsOnPrev = buttonsOn;
        if (buttonsOn==buttonsOn1)
            buttonsOn = buttonsOn2;
        else
            buttonsOn = buttonsOn1;
        
        //fill in the button buffer
        for (int i=0; i<numComponents; i++)
        {
            if (components[i].getPollData() > 0.5f)
                buttonsOn[i]=true;
            else
                buttonsOn[i]=false;
        }
    }
    
    public boolean compareBuffers()
    {
        for (int i=0; i<buttonsOn.length; i++)
            if (buttonsOn[i] != buttonsOnPrev[i])
                return true;
        return false;
    }
    
    public int numButtons()
    {
        return buttonsOn.length;
    }
    
    public boolean buttonOn(int i)
    {
        return buttonsOn[i];
    }
    
    public boolean buttonOnPrev(int i)
    {
        return buttonsOnPrev[i];
    }
    
}
