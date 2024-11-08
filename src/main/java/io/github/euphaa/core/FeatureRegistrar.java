package io.github.euphaa.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;

/**
 * use FeatureRegistrar.newFeature() to register features.
 * @see EventManager
 * @see Scheduler
 */
public class FeatureRegistrar
{
    private static short currentTick = 0;
    private static final HashSet<EventManager> ALL_FEATURES = new HashSet<>();
    private static final HashSet<EventManager> RUNNING_FEATURES = new HashSet<>();

    /**
     * called every other client tick to change what EventManagers are listening to events.
     */
    public static void refreshFeatures()
    {
        for (EventManager feature : ALL_FEATURES)
        {
            boolean shouldBeActive = feature.shouldBeActive();
            if (shouldBeActive && !RUNNING_FEATURES.contains(feature))
            {
                register(feature);
            }
            else if (!shouldBeActive && RUNNING_FEATURES.contains(feature))
            {
                unRegister(feature);
            }
        }
    }

    /**
     * this is where you should register all features from within the mod. use on or after FMLInitializationEvent
     * @param feature - an event handler that can be enabled or disabled by registering
     *                or unregistering from the forge event bus
     * @param enabled - whether the feature should be enabled or disabled by default;
     */
    public static void newFeature(EventManager feature, boolean enabled)
    {
        ALL_FEATURES.add(feature);
        if (enabled)
        {
            register(feature);
        }
    }

    /**
     * registers to the MinecraftForge.EVENT_BUS
     * @param feature
     */
    private static void register(EventManager feature)
    {
        MinecraftForge.EVENT_BUS.register(feature);
        RUNNING_FEATURES.add(feature);
    }

    /**
     * unregisters from the MinecraftForge.EVENT_BUS
     * @param feature
     */
    private static void unRegister(EventManager feature)
    {
        MinecraftForge.EVENT_BUS.unregister(feature);
        RUNNING_FEATURES.remove(feature);
    }

    @SubscribeEvent
    public void _onClientTick(TickEvent.ClientTickEvent event)
    {
        if (++currentTick % 2 == 0)
        {
            refreshFeatures();
        }
    }
}




