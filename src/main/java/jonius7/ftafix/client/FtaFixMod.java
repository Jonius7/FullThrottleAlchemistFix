package jonius7.ftafix.client;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jonius7.ftafix.PatchedFtaGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@Mod(modid = "FtaFix", name = "FullThrottle Alchemist Fix", version = "1.0")
public class FtaFixMod {

    private boolean hasShownGui = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Only register the event if the patch marker exists
        if (new File("config/FTA_PATCHED.marker").exists()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (hasShownGui) return;

        // Only show after main menu opens
        if (event.gui == null || event.gui.getClass().getSimpleName().contains("GuiMainMenu")) {
            hasShownGui = true;
            PatchedFtaGui gui = new PatchedFtaGui(event.gui);
            Minecraft.getMinecraft().displayGuiScreen(gui);
        }
    }
}
