package us.vanderhyde.gamepad;

/**
 *
 * @author james
 */
public interface ControllerListener
{
    public void controllerAdded(ControllerConnectionEvent evt);
    public void controllerRemoved(ControllerConnectionEvent evt);
}
