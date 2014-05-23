/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputjoystick;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import net.java.games.input.Controller;

/**
 *
 * @author james
 */
public class JInputJoystick
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        runGamepadKeyboardMapper();
    }
    
    private static void robotTest()
    {
        Robot rob;
        try
        {
            rob=new Robot();
        }
        catch (AWTException e)
        {
            System.out.println(e);
            return;
        }
        rob.delay(1000);
        rob.keyPress(KeyEvent.VK_A);
        rob.delay(400);
        rob.keyRelease(KeyEvent.VK_A);
    }

    private static void joystickTest()
    {
        joystick.JInputJoystickTest test=new joystick.JInputJoystickTest();
        test.getAllControllersInfo();
        test.pollControllerAndItsComponents(Controller.Type.GAMEPAD);
    }

    private static void pollingDaemonTest()
    {
        PollingDaemon d=new PollingDaemon();
        d.addGamepadButtonListener(new GamepadButtonListener()
        {
            public void buttonPressed(GamepadButtonEvent evt)
            {
                System.out.println("Button "+evt.getButtonNumber()+" pressed.");
            }

            public void buttonReleased(GamepadButtonEvent evt)
            {
                System.out.println("Button "+evt.getButtonNumber()+" released.");
            }
        });
        d.start();
        JFrame f=new JFrame("Gamepad test");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }

    private static void runGamepadKeyboardMapper()
    {
        //Start observing the controller and mapping to keyboard
        final PollingDaemon d;
	try 
        {
	    d=new PollingDaemon();
	    d.addGamepadButtonListener(new WiiToStencyl());
	    d.start();
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
