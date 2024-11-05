package io.github.euphaa.core;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.List;

public class Scheduler
{
    private static Long currentTick = 0L;
    private static final HashMap<Long, List<Runnable>> TASKS = new HashMap<>();


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
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

    public static void newTask(int delay, Runnable task)
    {
        if (delay < 1)
        {
            return;
        }

        TASKS.get(currentTick + delay).add(task);
    }

    /**
     * creates a anew thread and executes a task after a delay. works just like in js, except you dont have to bind context.
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
