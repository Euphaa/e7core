package io.github.euphaa.core;

/**
 * used to make recurring events.
 */
public class RecurringRunnable implements Runnable
{
    private int repeats;
    private Runnable task;
    private Registerer registerer;

    /**
     *
     * @param repeats use -1 for effectively infinite repeats
     * @param registerer used to repeatedly register this RecurringRunnable to any executor service. return early inside or call RecurringRunnable::setCancelled on this to stop it.
     *                   ex: (self) -> Scheduler.setTimeout(self, 100)
     * @param task main task to be run
     */
    public RecurringRunnable(int repeats, Registerer registerer, Runnable task)
    {
        this.repeats = repeats;
        this.task = task;
        this.registerer = registerer;
        this.registerer.reschedule(this);
    }

    public void setCancelled()
    {
        this.registerer = (a) -> {};
        this.task = () -> {};
    }

    public void setOneMoreRepeat()
    {
        this.repeats = 0;
    }

    @Override
    public void run()
    {
        task.run();

        if (repeats == 0) return;

        registerer.reschedule(this);
        repeats--;
    }
}
