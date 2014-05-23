
package us.vanderhyde.gamepad;

import net.java.games.input.Controller;

public class ControllerEvent extends java.util.EventObject
{
    
    private int id;

    public ControllerEvent(Controller c, int id)
    {
        super(c);
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
    
    public Controller getController()
    {
        if (this.getSource() instanceof Controller)
            return (Controller)this.getSource();
        else
            return null;
    }
}
