
package us.vanderhyde.gamepad;

import java.util.LinkedList;
import java.util.Queue;

public abstract class EventGenerationPoll<E> implements Runnable, EventGenerator<E>
{
    private long pollTime = 15;
    private EventQueue<E> queue = new EventQueue<E>();

    public EventGenerationPoll()
    {
    }

    public void setPollTime(long pollTime)
    {
        if (pollTime < 0)
            throw new IllegalArgumentException("Poll time must not be negative: "+pollTime);
        this.pollTime = pollTime;
    }

    public long getPollTime()
    {
        return pollTime;
    }
    
    public E getNextEvent() throws InterruptedException
    {
        return queue.remove();
    }
    
    @Override
    public void run()
    {
        while (canStillRun())
        {
            while (!Thread.interrupted() && canStillRun())
            {
                long time = System.currentTimeMillis();
                
                boolean changeHasOccurred = poll();
                if (changeHasOccurred)
                {
                    Iterable<E> events = generateEvents();
                    for (E evt:events)
                        queue.add(evt);
                }
                
                try 
                {
                    long sleepTime = pollTime - (System.currentTimeMillis() - time);
                    if (sleepTime > 0)
                        Thread.sleep(pollTime);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public abstract boolean canStillRun();

    public abstract boolean poll();

    public abstract Iterable<E> generateEvents();

    private static class EventQueue<E>
    {
        private Queue<E> q = new LinkedList<E>();

        public synchronized void add(E event)
        {
            q.add(event);
            this.notify();
        }

        public synchronized E remove() throws InterruptedException
        {
            while (q.isEmpty())
                this.wait();
            return q.remove();
        }
    }
}
