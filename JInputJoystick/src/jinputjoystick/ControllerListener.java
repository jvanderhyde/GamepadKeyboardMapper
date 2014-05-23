/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputjoystick;

/**
 *
 * @author james
 */
public interface ControllerListener
{
    public void controllerAdded(ControllerConnectionEvent evt);
    public void controllerRemoved(ControllerConnectionEvent evt);
}
