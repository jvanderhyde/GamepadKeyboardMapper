package us.vanderhyde.gamepad;

import java.util.ArrayList;

/**
 *
 * @author james
 */
public class GamepadButtonPoll extends EventGenerationPoll<GamepadButtonEvent>
     implements ControllerListener
{
    private ArrayList<GamepadController> controllers;
    private boolean controllerIsAttached;
    
    public GamepadButtonPoll()
    {
        controllers = new ArrayList<GamepadController>();
        this.setPollTime(15);

        //Assume this is true, until a controller is removed,
        // so that the thread will not quit before a controller is added.
        controllerIsAttached = true; 
    }

    @Override
    public boolean canStillRun()
    {
        return controllerIsAttached;
    }

    @Override
    public boolean poll()
    {
        GamepadController detachedController = null;
        boolean change = false;
        for (GamepadController g:controllers)
        {
            g.pollController();
            change |= g.compareBuffers();
            if (!g.isControllerValid())
                detachedController = g;
        }
        if (detachedController != null)
            this.controllerRemoved(new ControllerConnectionEvent(detachedController.getController(),ControllerConnectionEvent.CONTROLLER_REMOVED));
        return change;
    }

    @Override
    public Iterable<GamepadButtonEvent> generateEvents()
    {
        ArrayList<GamepadButtonEvent> events = new ArrayList<GamepadButtonEvent>();
        for (GamepadController g:controllers)
            for (int i=0; i<g.numButtons(); i++)
                if (g.buttonOn(i) != g.buttonOnPrev(i))
                {
                    if (g.buttonOn(i))
                        events.add(new GamepadButtonEvent(g.getController(),GamepadButtonEvent.BUTTON_PRESSED,i));
                    else
                        events.add(new GamepadButtonEvent(g.getController(),GamepadButtonEvent.BUTTON_RELEASED,i));
                }
        return events;
    }

    public void controllerAdded(ControllerConnectionEvent evt)
    {
        controllers.add(new GamepadController(evt.getController()));
    }

    public void controllerRemoved(ControllerConnectionEvent evt)
    {
        for (GamepadController g:controllers)
            if (g.getController()==evt.getController())
            {
                controllers.remove(g);
                break;
            }
        if (controllers.isEmpty())
            controllerIsAttached = false;
    }
    
}
