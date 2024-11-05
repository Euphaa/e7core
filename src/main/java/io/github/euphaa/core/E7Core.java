package io.github.euphaa.core;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "e7core", useMetadata=true)
public class E7Core
{
    public static final String MODID = "e7core";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Scheduler());
    }

    public static void addChatComponent(String msg)
    {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(msg));
    }
}
