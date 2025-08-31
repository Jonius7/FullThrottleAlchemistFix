package jonius7.ftafix;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FtaFixMod.MODID, name = FtaFixMod.NAME, version = FtaFixMod.VERSION)
public class FtaFixMod{
	
	public static final String MODID = "ftafix";
	public static final String NAME = "FtaFix";
    public static final String VERSION = "1.0";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	}
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("[FtaFix] Initialised");
	}
	  
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
}