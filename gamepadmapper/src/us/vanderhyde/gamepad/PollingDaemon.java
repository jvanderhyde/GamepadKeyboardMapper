package us.vanderhyde.gamepad;

import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author james
 */
public class PollingDaemon extends Thread
{
    private static final long pollTime = 15;
    
    private Controller.Type controllerType;
    private Controller firstController;
    private boolean[] buttonsOn1,buttonsOn2;
    private boolean[] buttonsOn,buttonsOnPrev;
    
    private ArrayList<GamepadButtonListener> listeners;

    public PollingDaemon()
    {
        this(Controller.Type.GAMEPAD);
    }

    public PollingDaemon(Controller.Type controllerType)
    {
        this.controllerType = controllerType;
        this.firstController = null;
        
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for(int i=0; i < controllers.length && firstController == null; i++)
        {
            if(controllers[i].getType() == controllerType) 
            {
                //Found a controller
                firstController = controllers[i];
            }
        }
        
        if(firstController == null) 
        {
            //Couldn't find a controller
            throw new GamepadException("Could not find controller of type "+controllerType);
        }
        
        System.out.println("Found a controller: "+firstController.getName());
        this.setName(firstController.getName()+"_thread");
        this.setDaemon(true);
        
        //Poll the controller for the first time
        boolean controllerValid = firstController.poll();
        if(!controllerValid) 
        {
            //Lost the controller already
            throw new GamepadException("Controller disconnected.");
        }
        Component[] components = firstController.getComponents();
        
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
        
        //Set up listener list
        listeners = new ArrayList<GamepadButtonListener>();
    }
    
    private void pollController()
    {
        //poll the controller
        boolean controllerValid = firstController.poll();
        if (!controllerValid)
        {
            this.interrupt();
            firstController = null;
            System.out.println("Controller disconnected.");
            return;
        }
        
        Component[] components = firstController.getComponents();
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
    
    private boolean compareBuffers()
    {
        for (int i=0; i<buttonsOn.length; i++)
            if (buttonsOn[i] != buttonsOnPrev[i])
                return true;
        return false;
    }
    
    public void addGamepadButtonListener(GamepadButtonListener listener)
    {
        this.listeners.add(listener);
    }

    public void removeGamepadButtonListener(GamepadButtonListener listener)
    {
        this.listeners.remove(listener);
    }

    @Override
    public void run()
    {
        while (firstController != null)
        {
            while (!Thread.interrupted())
            {
                pollController();
                if (compareBuffers())
                    notifyListeners();
                
                try 
                {
                    Thread.sleep(pollTime);
                }
                catch (InterruptedException e)
                {
                    this.interrupt();
                }
            }
        }
    }

    private void notifyListeners()
    {
        for (GamepadButtonListener listener:this.listeners)
        {
            for (int i=0; i<buttonsOn.length; i++)
                if (buttonsOn[i] != buttonsOnPrev[i])
                {
                    if (buttonsOn[i])
                        listener.buttonPressed(new GamepadButtonEvent(firstController,GamepadButtonEvent.BUTTON_PRESSED,i));
                    else
                        listener.buttonReleased(new GamepadButtonEvent(firstController,GamepadButtonEvent.BUTTON_RELEASED,i));
                }
        }
    }
    
}
