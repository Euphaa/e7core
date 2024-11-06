package io.github.euphaa.core;

/**
 * used when creating a RecurringRunnable so it can register itself after running.
 * @see RecurringRunnable
 */
public interface Registerer
{
    void reschedule(Runnable self);
}
