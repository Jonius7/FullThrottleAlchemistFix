package jonius7.ftafix.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;
import jonius7.ftafix.client.FtaFixMessageHandler;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class FtaFixCore implements IFMLLoadingPlugin {

    public FtaFixCore() {
        patchFullThrottleAlchemist();
    }

    private void patchFullThrottleAlchemist() {
        try {
            File modsDir = new File("mods");
            File targetJar = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar");
            File patchedJar = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a-patched.jar");

            if (targetJar.exists() && !patchedJar.exists()) {
                System.out.println("[FTA Fix] Patching FullThrottle Alchemist jar...");
                JarPatcher.stripForestry(targetJar, patchedJar);

                // Backup original
                File backup = new File(modsDir, targetJar.getName() + ".bak");
                if (targetJar.renameTo(backup)) {
                    System.out.println("[FTA Fix] Original jar backed up as: " + backup.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getASMTransformerClass() { return null; }

    @Override
    public String getModContainerClass() { return null; }

    @Override
    public String getSetupClass() { return null; }

    @Override
    public void injectData(Map<String, Object> data) {
        Object sideObj = data.get("side");
        if (sideObj == Side.CLIENT) {
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new FtaFixMessageHandler());
            System.out.println("[FTA Fix] Client tick handler registered to show patch message.");
        }
    }


    @Override
    public String getAccessTransformerClass() { return null; }
}
