package jonius7.ftafix.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

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
            File originalJar = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar");
            File patchedJar = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a-patched.jar");
            File backupJar = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar.bak");

            if (originalJar.exists() && !patchedJar.exists()) {
                System.out.println("[FTA Fix] Found FullThrottle Alchemist jar, patching...");
                JarPatcher.stripForestry(originalJar, patchedJar);

                if (originalJar.renameTo(backupJar)) {
                    System.out.println("[FTA Fix] Original jar renamed to: " + backupJar.getName());
                } else {
                    System.out.println("[FTA Fix] WARNING: Could not rename original jar.");
                }

                System.out.println("[FTA Fix] Patched jar created: " + patchedJar.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public String[] getASMTransformerClass() { return null; }
    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }
    @Override public void injectData(Map<String, Object> data) {}
    @Override public String getAccessTransformerClass() { return null; }
}
