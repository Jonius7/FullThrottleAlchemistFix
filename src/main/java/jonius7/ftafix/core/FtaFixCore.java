package jonius7.ftafix.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

import java.io.*;
import java.util.Map;

@MCVersion("1.7.10")
public class FtaFixCore implements IFMLLoadingPlugin {

    private static final File MARKER_FILE = new File("config/FTA_PATCHED.marker");

    public FtaFixCore() {
        if (!MARKER_FILE.exists()) {
            boolean patched = patchFullThrottleAlchemist();
            if (patched) writeMarker();
        }
    }

    private boolean patchFullThrottleAlchemist() {
        try {
            File modsDir = new File("mods");
            File target = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar");
            File patched = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a-patched.jar");
            File backup = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar.bak");

            if (target.exists() && !patched.exists()) {
                System.out.println("[FTA Fix] Found FTA jar, patching...");
                JarPatcher.stripForestry(target, patched);

                if (target.renameTo(backup)) {
                    System.out.println("[FTA Fix] Original jar renamed to: " + backup.getName());
                } else {
                    System.out.println("[FTA Fix] WARNING: Could not rename original jar.");
                    return false;
                }

                System.out.println("[FTA Fix] Patched jar created: " + patched.getName());
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

    @Override public String[] getASMTransformerClass() { return null; }
    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }
    @Override public void injectData(Map<String, Object> data) {}
    @Override public String getAccessTransformerClass() { return null; }
}