package jonius7.ftafix.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jonius7.ftafix.PatchedFtaGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiOpenEvent;

@SideOnly(Side.CLIENT)
public class GuiOpenEventHandler {
	
	private boolean hasShownPatchedGui = false;
	
	@SubscribeEvent
	
	public void openGui(GuiOpenEvent event) {
		if (!hasShownPatchedGui) {
            hasShownPatchedGui = true;

            // Wrap the current GUI as the parent for the message
            PatchedFtaGui gui = new PatchedFtaGui(event.gui);
            
            // Set it as the current GUI
            Minecraft.getMinecraft().displayGuiScreen(gui);
        }
	}
}
