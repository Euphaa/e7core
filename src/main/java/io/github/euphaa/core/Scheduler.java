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
    private static void onClientTick(TickEvent.ClientTickEvent event)
    {
        currentTick++;
        for (Runnable task : TASKS.get(currentTick))
        {
            task.run();
        }
    }

    public static void newTask(int delay, Runnable task)
    {
        if (delay < 1)
        {

            return;
        }

        TASKS.get(currentTick + delay).add(task);
    }

}
