/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jinputjoystick;

/**
 *
 * @author james
 */
public interface EventGenerator<E>
{
    public E getNextEvent() throws InterruptedException;
}
