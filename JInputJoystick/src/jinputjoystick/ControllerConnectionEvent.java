package jinputjoystick;

import net.java.games.input.Controller;

/**
 *
 * @author james
 */
public class ControllerConnectionEvent extends ControllerEvent
{
    public static final int CONTROLLER_ADDED = 1;
    public static final int CONTROLLER_REMOVED = 2;

    public ControllerConnectionEvent(Controller c, int id)
    {
        super(c, id);
    }
    
}
