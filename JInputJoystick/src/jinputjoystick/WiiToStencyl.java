/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputjoystick;

import java.awt.event.KeyEvent;

/**
 *
 * @author james
 */
public class WiiToStencyl extends GamepadKeyboardMapper
{
    public static final int B_UP =      2;
    public static final int B_DOWN =    3;
    public static final int B_LEFT =    0;
    public static final int B_RIGHT =   1;
    public static final int B_B =       5;
    public static final int B_A =       4;
    public static final int B_PLUS =    6;
    public static final int B_MINUS =   7;
    public static final int B_HOME =    8;
    public static final int B_ONE =     9;
    public static final int B_TWO =     10;
    
    private int[] keys =        new int[11];

    public static final int B_SW_UP =   B_RIGHT;
    public static final int B_SW_DOWN = B_LEFT;
    public static final int B_SW_LEFT = B_UP;
    public static final int B_SW_RIGHT =B_DOWN;

    public WiiToStencyl()
    {
        for (int i=0; i<keys.length; i++)
            keys[i]=KeyEvent.VK_UNDEFINED;
        
        keys[B_SW_UP]=KeyEvent.VK_UP;
        keys[B_SW_DOWN]=KeyEvent.VK_DOWN;
        keys[B_SW_LEFT]=KeyEvent.VK_LEFT;
        keys[B_SW_RIGHT]=KeyEvent.VK_RIGHT;
        
        keys[B_TWO]=KeyEvent.VK_UP;
        keys[B_ONE]=KeyEvent.VK_K;
        
        keys[B_A]=KeyEvent.VK_ESCAPE;
    }

    @Override
    public int keyFromButton(int button)
    {
        if (0<=button && button<keys.length)
            return keys[button];
        throw new IllegalArgumentException("Unknown button number: "+button);
    }
    
}
