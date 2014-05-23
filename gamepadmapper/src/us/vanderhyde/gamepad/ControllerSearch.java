//A polling thread that checks the status of controllers.
//It doesn't work with the current version of JInput.
//It does generate add events for controllers attached at launch time.
//Created by James Vanderhyde, 7 January 2014

package us.vanderhyde.gamepad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerSearch extends EventGenerationPoll<ControllerConnectionEvent>
{
    private Controller.Type controllerType;
    private ArrayList<Controller> activeControllers;
    private ArrayList<Controller> inactiveControllers;
    private ArrayList<Controller> newControllers;
    
    private boolean runOnce = false;

    public ControllerSearch()
    {
        this(Controller.Type.GAMEPAD);
    }

    public ControllerSearch(Controller.Type controllerType)
    {
        this.controllerType = controllerType;
        this.activeControllers = new ArrayList<Controller>();
        this.inactiveControllers = new ArrayList<Controller>();
        this.newControllers = new ArrayList<Controller>();
        this.setPollTime(100);
    }
    
    @Override
    public boolean canStillRun()
    {
        return (!runOnce); //run only once, since the controllers are cached anyway.
    }

    @Override
    public boolean poll()
    {
        //It turns out this doesn't work, because getControllers returns the
        // contents of a cached list, and the cache is never refreshed
        // in the DefaultControllerEnvironment.
        runOnce = true;
        Controller[] conts = ControllerEnvironment.getDefaultEnvironment().getControllers();
        List<Controller> environment=Arrays.asList(conts);
        
        newControllers.clear();
        for (Controller c:environment)
        {
            if (c.getType() == controllerType) 
                if (!activeControllers.contains(c))
                {
                    //Found a new controller
                    newControllers.add(c);
                }
        }
        
        inactiveControllers.clear();
        for (Controller active:activeControllers)
            if (!environment.contains(active))
            {
                //Lost a controller
                inactiveControllers.add(active);
            }
        
        activeControllers.removeAll(inactiveControllers);
        activeControllers.addAll(newControllers);
        
        return (!newControllers.isEmpty() || !inactiveControllers.isEmpty());
    }

    @Override
    public Iterable<ControllerConnectionEvent> generateEvents()
    {
        ArrayList<ControllerConnectionEvent> events = new ArrayList<ControllerConnectionEvent>();
        for (Controller c:newControllers)
            events.add(new ControllerConnectionEvent(c,ControllerConnectionEvent.CONTROLLER_ADDED));
        for (Controller c:inactiveControllers)
            events.add(new ControllerConnectionEvent(c,ControllerConnectionEvent.CONTROLLER_REMOVED));
        return events;
    }
    
}
