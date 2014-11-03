package us.vanderhyde.gamepad;

import java.awt.event.KeyEvent;

/**
 *
 * @author james
 */
public class CHGamepadToMibibli extends CHGamepad
{
    public CHGamepadToMibibli()
    {
        keys[B_UP]=KeyEvent.VK_UP;
        keys[B_DOWN]=KeyEvent.VK_DOWN;
        keys[B_LEFT]=KeyEvent.VK_LEFT;
        keys[B_RIGHT]=KeyEvent.VK_RIGHT;
        
        keys[B_B]=KeyEvent.VK_Z;
        keys[B_A]=KeyEvent.VK_X;
        keys[B_C]=KeyEvent.VK_C;
        
        keys[B_START]=KeyEvent.VK_1;
        keys[B_SELECT]=KeyEvent.VK_2;
    }
    
}
