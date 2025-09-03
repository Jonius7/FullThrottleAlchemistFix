package jonius7.ftafix.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

import java.io.File;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@MCVersion("1.7.10")
public class FtaFixCore implements IFMLLoadingPlugin {

    private static final Logger LOGGER = LogManager.getLogger("FtaFix");

    public FtaFixCore() {
        patchFullThrottleAlchemist();
    }

    private void patchFullThrottleAlchemist() {
        try {
            File modsDir = new File("mods");
            File targetJar = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a.jar");
            File patchedJar = new File(modsDir, "FullThrottle_Alchemist-1.7.10-1.0.18a-patched.jar");
            File backupJar = new File(modsDir, targetJar.getName() + ".bak");

            if (!targetJar.exists()) {
            	if (patchedJar.exists()) {
            		LOGGER.warn("FullThrottle Alchemist already patched: {}", patchedJar.getName());
            		return;
            	} else {
            		LOGGER.warn("FullThrottle Alchemist jar not found: {}", targetJar.getAbsolutePath());
                    return;
            	}
            }

            if (patchedJar.exists()) {
                LOGGER.info("Patched jar already exists: {}", patchedJar.getName());
                return;
            }

            LOGGER.info("Patching FullThrottle Alchemist jar...");
            JarPatcher.stripForestry(targetJar, patchedJar);

            if (targetJar.renameTo(backupJar)) {
                LOGGER.info("Original jar backed up as: {}", backupJar.getName());
            } else {
                LOGGER.warn("Could not backup original jar. It will remain alongside the patched jar.");
            }

            LOGGER.info("Patch complete. Loading will continue with the patched jar.");

        } catch (Exception e) {
            LOGGER.error("Error while patching FullThrottle Alchemist jar", e);
        }
    }

    @Override
    public String[] getASMTransformerClass() { return null; }

    @Override
    public String getModContainerClass() { return null; }

    @Override
    public String getSetupClass() { return null; }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getAccessTransformerClass() { return null; }
}
