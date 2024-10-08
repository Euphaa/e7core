package io.github.euphaa.core;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "e7core", useMetadata=true)
public class E7Core
{


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    public static void addChatComponent(String msg)
    {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(msg));
    }
}
