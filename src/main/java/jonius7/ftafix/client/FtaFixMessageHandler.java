package jonius7.ftafix.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class FtaFixMessageHandler {

    private boolean hasShown = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!hasShown && Minecraft.getMinecraft().thePlayer != null) {
            hasShown = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("FullThrottle Alchemist was patched successfully. Without it, a mod cycle error would occur.")
            );
        }
    }
}