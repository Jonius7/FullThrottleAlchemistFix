package jonius7.ftafix;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.GuiOpenEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@MCVersion("1.7.10")
public class FtaFixCore implements IFMLLoadingPlugin {

    private static final File MARKER_FILE = new File("config/FTA_PATCHED.marker");

    public FtaFixCore() {
        // Run patch immediately if not already done
        if (!MARKER_FILE.exists()) {
            boolean patched = patchFullThrottleAlchemist();
            if (patched) {
                writeMarker();
            }
        }
    }

    private boolean patchFullThrottleAlchemist() {
        System.out.println("[FTA Fix] Coremod initialized.");
        try {
            File modsDir = new File("mods");
            File target = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar");
            File patched = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a-patched.jar");
            File backup = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar.bak");

            if (target.exists() && !patched.exists()) {
                System.out.println("[FTA Fix] Found FTA jar, patching...");
                JarPatcher.stripForestry(target, patched);

                // Rename original to .bak
                if (target.renameTo(backup)) {
                    System.out.println("[FTA Fix] Original jar renamed to: " + backup.getName());
                } else {
                    System.out.println("[FTA Fix] WARNING: Could not rename original jar. It will remain alongside the patched one.");
                    return false;
                }

                System.out.println("[FTA Fix] Patched jar created: " + patched.getName());
                System.out.println("[FTA Fix] Loading will continue with the patched jar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void writeMarker() {
        try {
            File dir = MARKER_FILE.getParentFile();
            if (!dir.exists()) dir.mkdirs();

            try (FileWriter fw = new FileWriter(MARKER_FILE)) {
                fw.write("Patched FullThrottle Alchemist\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =====================
    // GUI popup registration
    // =====================
    @Override
    public void injectData(Map<String, Object> data) {
        Object sideObj = data.get("side");
        if (sideObj == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new Object() {
                private boolean shown = false;

                @SubscribeEvent
                @SideOnly(Side.CLIENT)
                public void onGuiOpen(GuiOpenEvent event) {
                    // Show only once after main menu opens
                    if (shown) return;
                    if (event.gui == null || event.gui.getClass().getSimpleName().contains("GuiMainMenu")) {
                        shown = true;
                        PatchedFtaGui gui = new PatchedFtaGui(event.gui);
                        Minecraft.getMinecraft().displayGuiScreen(gui);
                    }
                }
            });
            System.out.println("[FTA Fix] Patched GUI event registered on client side.");
        }
    }

    @Override public String[] getASMTransformerClass() { return null; }
    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }
    @Override public String getAccessTransformerClass() { return null; }
}
