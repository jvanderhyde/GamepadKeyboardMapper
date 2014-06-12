//Main class for mapping gamepad to keyboard
//Created by James Vanderhyde, 10 June 2014
//  Copied from JInputJoystick.java

package us.vanderhyde.gamepad;

import javax.swing.JFrame;

/**
 *
 * @author james
 */
public class JoystickRobot
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        runGamepadKeyboardMapper();
    }

    private static void runGamepadKeyboardMapper()
    {
        //Start observing the controller and mapping to keyboard
        final EventThread t;
	try 
        {
	    t=new EventThread();
	    t.addGamepadButtonListener(new CHGamepadToMibibli());
	    t.start();
	}
	catch (GamepadException e)
        {
	    System.out.println(e);
	    return;
	}
        
        //Open a window for easy exit to the program
        final JFrame f=new JFrame("Gamepad to Keyboard Mapper");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	f.add(new KeyDefArrayPanel(3));
	f.pack();
        f.setVisible(true);
    }

}
