package jonius7.ftafix.core;

import cpw.mods.fml.relauncher.FMLRelaunchLog;
import net.minecraft.launchwrapper.IClassTransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.net.URL;
import java.util.Map;

public class FtaFixCore implements IFMLLoadingPlugin, IClassTransformer {

    public FtaFixCore() {
        FMLRelaunchLog.info("[FtaFix] Coremod plugin loaded!");
    }

    // IFMLLoadingPlugin methods
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{getClass().getName()};
    }

    @Override
    public String getModContainerClass() {
        //return FtaFixCoreContainer.class.getName();
    	return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() { return null; }

    // IClassTransformer method
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        FMLRelaunchLog.info("[FtaFix] Checking class: %s", name);

        // Only intercept Forestry API classes
        if (name.startsWith("forestry.api")) {
            try {
                String resourcePath = name.replace('.', '/') + ".class";
                URL classUrl = getClass().getClassLoader().getResource(resourcePath);

                if (classUrl != null && classUrl.toString().toLowerCase().contains("project_alchemy")) {
                    FMLRelaunchLog.info("[FtaFix] Blocking Forestry API class from FullThrottle Alchemist: %s", name);
                    return null; // skip loading only for Project_Alchemy
                }
            } catch (Exception e) {
                FMLRelaunchLog.warning("[FtaFix] Error checking class origin: %s", e.getMessage());
            }
        }

        return basicClass; // leave other classes untouched
    }

    private boolean classIsFromProjectAlchemy(String className) {
        try {
            String resourcePath = className.replace('.', '/') + ".class";
            URL classUrl = ClassLoader.getSystemClassLoader().getResource(resourcePath);
            return classUrl != null && classUrl.toString().toLowerCase().contains("project_alchemy");
        } catch (Exception e) {
            FMLRelaunchLog.warning("[FtaFix] Error checking class origin: %s", e.getMessage());
            return false;
        }
    }
}
