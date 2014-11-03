package us.vanderhyde.gamepad;

import java.awt.event.KeyEvent;

/**
 *
 * @author james
 */
public class CHGamepad extends GamepadKeyboardMapper
{
    public static final int B_UP =      13;
    public static final int B_DOWN =    1;
    public static final int B_LEFT =    12;
    public static final int B_RIGHT =   0;
    public static final int B_A =       2;
    public static final int B_B =       3;
    public static final int B_C =       4;
    public static final int B_D =       5;
    public static final int B_START =    11;
    public static final int B_SELECT =   10;
    public static final int B_L_ONE =     8;
    public static final int B_L_TWO =     9;
    public static final int B_R_ONE =     6;
    public static final int B_R_TWO =     7;
    
    protected int[] keys =        new int[14];

    public static final int B_PS_SQUARE =  B_D;
    public static final int B_PS_TRIANGLE = B_C;
    public static final int B_PS_CIRCLE = B_A;
    public static final int B_PS_EX = B_B;

    public CHGamepad()
    {
        for (int i=0; i<keys.length; i++)
            keys[i]=KeyEvent.VK_UNDEFINED;
    }

    @Override
    public int keyFromButton(int button)
    {
        if (0<=button && button<keys.length)
            return keys[button];
        throw new IllegalArgumentException("Unknown button number: "+button);
    }
    
}
