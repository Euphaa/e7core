package io.github.euphaa.core;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.tools.nsc.doc.base.comment.Link;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * contains static methods to schedule tasks based on both ms and ClientTick delay
 */
public class Scheduler
{
    private static long currentTick = 0L;
    private static final HashMap<Long, List<Runnable>> TASKS = new HashMap<>();


    @SubscribeEvent
    public void _onClientTick(TickEvent.ClientTickEvent event)
    {
        currentTick++;

        List<Runnable> tasks = TASKS.get(currentTick);
        if (tasks == null) return;

        for (Runnable task : TASKS.get(currentTick))
        {
            task.run();
        }
        tasks.clear();
    }

    /**
     * used to run a task after a certain number of ClientTick events.
     * @param delay
     * @param task
     * @see RecurringRunnable
     */
    public static void newTask(long delay, Runnable task)
    {
        if (delay < 1) return;
        long time = currentTick + delay;
        TASKS.computeIfAbsent(time, k -> new LinkedList<>()).add(task);
    }

    /**
     * creates a new thread and executes a task after a delay. works just like in js, except you dont have to bind context.
     * @param task
     * @param delay
     */
    public static void setTimeout(Runnable task, int delay)
    {
        new Thread(() -> {
            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            task.run();
        }).start();
    }

    /**
     * performs on the current thread instead of making a new one.
     * @param task
     * @param delay
     */
    public static void setTimeoutSync(Runnable task, int delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        task.run();
    }

}
