package jinputjoystick;

import java.awt.AWTException;
import java.awt.Robot;

/**
 *
 * @author james
 */
public abstract class GamepadKeyboardMapper implements GamepadButtonListener
{
    private Robot rob;
    
    public GamepadKeyboardMapper()
    {
        try
        {
            rob=new Robot();
        }
        catch (AWTException e)
        {
            throw new RuntimeException(e);
        }    
    }
    
    public abstract int keyFromButton(int button);

    public void buttonPressed(GamepadButtonEvent evt)
    {
        int keyCode = keyFromButton(evt.getButtonNumber());
        if (keyCode != java.awt.event.KeyEvent.VK_UNDEFINED)
            rob.keyPress(keyCode);
    }

    public void buttonReleased(GamepadButtonEvent evt)
    {
        int keyCode = keyFromButton(evt.getButtonNumber());
        if (keyCode != java.awt.event.KeyEvent.VK_UNDEFINED)
            rob.keyRelease(keyCode);
    }
    
}
