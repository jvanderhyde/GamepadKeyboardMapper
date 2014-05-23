/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputjoystick;

import net.java.games.input.Controller;

/**
 *
 * @author james
 */
public class GamepadButtonEvent extends ControllerEvent
{
    public static final int BUTTON_PRESSED = 17;
    public static final int BUTTON_RELEASED = 18;

    private int buttonNumber;

    public GamepadButtonEvent(Controller c, int id, int buttonNumber)
    {
        super(c,id);
        this.buttonNumber = buttonNumber;
    }

    public int getButtonNumber()
    {
        return buttonNumber;
    }

    public boolean isPressed()
    {
        return this.getId()==BUTTON_PRESSED;
    }

    public boolean isReleased()
    {
        return this.getId()==BUTTON_RELEASED;
    }
    
    @Override
    public String toString()
    {
        return "GamepadButtonEvent: "+
               "[source = "+this.getSource()+"]"+
               "[button = "+this.getButtonNumber()+"]";
    }
    
}
