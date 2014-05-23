/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputjoystick;

/**
 *
 * @author james
 */
public interface GamepadButtonListener
{ 
    public void buttonPressed(GamepadButtonEvent evt);
    public void buttonReleased(GamepadButtonEvent evt);
}
