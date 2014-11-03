package us.vanderhyde.gamepad;

import java.awt.event.KeyEvent;

/**
 *
 * @author james
 */
public class CHGamepadToTransistor extends CHGamepad
{
    public CHGamepadToTransistor()
    {
        keys[B_UP]=KeyEvent.VK_UP;
        keys[B_DOWN]=KeyEvent.VK_DOWN;
        keys[B_LEFT]=KeyEvent.VK_LEFT;
        keys[B_RIGHT]=KeyEvent.VK_RIGHT;
        
        keys[B_A]=KeyEvent.VK_1;
        keys[B_B]=KeyEvent.VK_2;
        keys[B_C]=KeyEvent.VK_3;
        keys[B_D]=KeyEvent.VK_4;
        
        keys[B_L_ONE]=KeyEvent.VK_E;
        keys[B_L_TWO]=KeyEvent.VK_Q;
        keys[B_R_ONE]=KeyEvent.VK_R;
        keys[B_R_TWO]=KeyEvent.VK_TAB;
        
        keys[B_START]=KeyEvent.VK_ESCAPE;
        keys[B_SELECT]=KeyEvent.VK_SPACE;
    }
    
}
