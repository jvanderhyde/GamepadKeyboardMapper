//Main class for printing gamepad buttons
//Created by James Vanderhyde, 10 June 2014
//  Copied from JInputJoystick.java

package us.vanderhyde.gamepad;

import javax.swing.JFrame;

/**
 *
 * @author james
 */
public class JoystickButtons
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        testControllerEvents();
    }
    
    private static void testControllerEvents()
    {
        //Start observing controller attachment/detachment
        final EventThread t=new EventThread();
        t.addControllerListener(new ControllerListener()
        {
            public void controllerAdded(ControllerConnectionEvent evt)
            {
                System.out.println("Controller added: "+evt.getController());
            }

            public void controllerRemoved(ControllerConnectionEvent evt)
            {
                System.out.println("Controller removed: "+evt.getController());
            }
        });
        t.addGamepadButtonListener(new GamepadButtonListener()
        {
            public void buttonPressed(GamepadButtonEvent evt)
            {
                System.out.print("Button pressed: ");
                System.out.println(evt);
            }

            public void buttonReleased(GamepadButtonEvent evt)
            {
                System.out.print("Button released: ");
                System.out.println(evt);
            }
        });
        t.start();
        
        //Open a window for easy exit to the program
        final JFrame f=new JFrame("Event thread tester");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }
}
