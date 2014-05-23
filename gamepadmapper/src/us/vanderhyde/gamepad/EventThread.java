package us.vanderhyde.gamepad;

import java.util.ArrayList;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author james
 */
public class EventThread extends Thread
{
    private ArrayList<ControllerListener> controllerListeners;
    private ArrayList<GamepadButtonListener> buttonListeners;

    public EventThread()
    {
        super("Controller-event-thread");
        this.setDaemon(true);
        
        this.controllerListeners = new ArrayList<ControllerListener>();
        this.buttonListeners = new ArrayList<GamepadButtonListener>();
    }
    
    public static int numGamepadsAttached()
    {
        Controller[] conts = ControllerEnvironment.getDefaultEnvironment().getControllers();
        int num = 0;
        for (Controller c:conts)
            if (c.getType() == Controller.Type.GAMEPAD) 
                num++;
        return num;
    }
    
    @Override
    public void run()
    {
        System.out.println("Gamepad event thread started.");
        
        ControllerSearch search = new ControllerSearch();
        Thread searcherThread = new Thread(search);
        Thread responderThread = new WaitForEvents<ControllerConnectionEvent>(search,new ControllerListenerNotifier());
        
        GamepadButtonPoll buttonPoll = new GamepadButtonPoll();
        Thread gamepadThread = new Thread(buttonPoll);
        Thread gamepadResponderThread = new WaitForEvents<GamepadButtonEvent>(buttonPoll,new ButtonListenerNotifier());

        this.addControllerListener(buttonPoll);
        
        responderThread.start();
        searcherThread.start();
                
        gamepadResponderThread.start();
        gamepadThread.start();
                
        try
        {
            searcherThread.join();
            responderThread.interrupt();
            gamepadThread.join();
            gamepadResponderThread.interrupt();
        }
        catch (InterruptedException e)
        {
            System.out.println("Gamepad event thread interruped.");
        }
        System.out.println("Gamepad event thread terminated.");
    }
    
    private class ControllerListenerNotifier implements EventHandler<ControllerConnectionEvent>
    {
        public void handleEvent(ControllerConnectionEvent evt)
        {
            for (ControllerListener listener:controllerListeners)
            {
                if (evt.getId()==ControllerConnectionEvent.CONTROLLER_ADDED)
                    listener.controllerAdded(evt);
                else if (evt.getId()==ControllerConnectionEvent.CONTROLLER_REMOVED)
                    listener.controllerRemoved(evt);
            }
        }
    }
    
    private class ButtonListenerNotifier implements EventHandler<GamepadButtonEvent>
    {
        public void handleEvent(GamepadButtonEvent evt)
        {
            for (GamepadButtonListener listener:buttonListeners)
            {
                if (evt.getId()==GamepadButtonEvent.BUTTON_PRESSED)
                    listener.buttonPressed(evt);
                else if (evt.getId()==GamepadButtonEvent.BUTTON_RELEASED)
                    listener.buttonReleased(evt);
            }
        }
    }
    
    private static class WaitForEvents<E> extends Thread
    {
        private EventGenerator<E> gen;
        private EventHandler<E> han;

        public WaitForEvents(EventGenerator<E> gen, EventHandler<E> han)
        {
            this.gen = gen;
            this.han = han;
        }
        
        @Override
        public void run()
        {
            boolean done = false;
            while (!done)
            {
                try
                {
                    E evt = gen.getNextEvent();
                    han.handleEvent(evt);
                }
                catch (InterruptedException e)
                {
                    done = true;
                }
            }
        }
    }
    
    public void addGamepadButtonListener(GamepadButtonListener listener)
    {
        this.buttonListeners.add(listener);
    }

    public void removeGamepadButtonListener(GamepadButtonListener listener)
    {
        this.buttonListeners.remove(listener);
    }
    
    public void addControllerListener(ControllerListener listener)
    {
        this.controllerListeners.add(listener);
    }

    public void removeControllerListener(ControllerListener listener)
    {
        this.controllerListeners.remove(listener);
    }

}
