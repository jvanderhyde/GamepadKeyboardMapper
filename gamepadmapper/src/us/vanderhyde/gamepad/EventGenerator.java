package us.vanderhyde.gamepad;

/**
 *
 * @author james
 */
public interface EventGenerator<E>
{
    public E getNextEvent() throws InterruptedException;
}
