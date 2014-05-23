package us.vanderhyde.gamepad;

/**
 *
 * @author james
 */
public interface GamepadButtonListener
{ 
    public void buttonPressed(GamepadButtonEvent evt);
    public void buttonReleased(GamepadButtonEvent evt);
}
