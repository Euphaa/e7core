package io.github.euphaa.core;

public abstract class RecurringRunnable implements Runnable
{
    private int interval;
    private int repeats;
    private Runnable task;

    /**
     *
     * @param interval minecraft ticks
     * @param repeats use -1 for effectively infinite repeats
     * @param task
     */
    public RecurringRunnable(int interval, int repeats, Runnable task)
    {
        this.interval = interval;
        this.repeats = repeats;
        this.task = task;
    }

    @Override
    public void run()
    {
        task.run();

        if (repeats == 0) return;

        Scheduler.newTask(interval, task);
        repeats--;
    }
}
