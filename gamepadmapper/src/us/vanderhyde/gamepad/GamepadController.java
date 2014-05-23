package us.vanderhyde.gamepad;

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
    private boolean controllerValid;

    public GamepadController(Controller controller)
    {
        this.controller = controller;
        
        //Poll the controller for the first time
        controllerValid = controller.poll();
        if(!controllerValid) 
        {
            //Lost the controller already
            throw new GamepadException("Controller disconnected.");
        }
        Component[] components = controller.getComponents();
        
        //Check for axis components.
        //  We'll add the negative axis as a button on the end of the buffer.
        int numAxes = 0;
        for (int i=0; i<components.length; i++)
            if (components[i].isAnalog())
                numAxes++;
        
        //Set up button buffers
        buttonsOn1 = new boolean[components.length+numAxes];
        buttonsOn2 = new boolean[components.length+numAxes];
        buttonsOn = buttonsOn1;
        
        fillInButtonBuffer(components);
    }
    
    public Controller getController()
    {
        return this.controller;
    }
    
    public boolean isControllerValid()
    {
        return controllerValid;
    }
    
    public void pollController()
    {
        //make sure controller is present
        if (!controllerValid)
            return;
        
        //poll the controller
        controllerValid = controller.poll();
        if (!controllerValid)
        {
            System.out.println("Controller disconnected.");
            return;
        }
        Component[] components = controller.getComponents();
        
        //swap the button buffers
        buttonsOnPrev = buttonsOn;
        if (buttonsOn==buttonsOn1)
            buttonsOn = buttonsOn2;
        else
            buttonsOn = buttonsOn1;
        
        fillInButtonBuffer(components);
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

    private void fillInButtonBuffer(Component[] components)
    {
        int nextAxis=0;
        for (int i=0; i<components.length; i++)
        {
            final float pollData = components[i].getPollData();
            if (components[i].isAnalog())
            {
                if (pollData > 0.5f)
                    buttonsOn[i]=true;
                else if (pollData < -0.5f)
                    buttonsOn[components.length+nextAxis]=true;
                else
                {
                    buttonsOn[i]=false;
                    buttonsOn[components.length+nextAxis]=false;
                }
                nextAxis++;
            }
            else
            {
                if (pollData > 0.5f)
                    buttonsOn[i]=true;
                else
                    buttonsOn[i]=false;
            }
        }
    }
    
}
