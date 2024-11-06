package io.github.euphaa.core;

public abstract class EventManager
{
    /**
     * called every tick to determine if the event listeners in this class should be registered to the forge event bus.
     * return true to always have the feature enabled.
     * @return
     */
    public abstract boolean shouldBeActive();

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj.getClass() == this.getClass();
    }
}