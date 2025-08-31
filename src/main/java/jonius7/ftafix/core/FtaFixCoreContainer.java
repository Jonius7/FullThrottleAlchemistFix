package jonius7.ftafix.core;

import java.util.Arrays;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class FtaFixCoreContainer extends DummyModContainer {
	public FtaFixCoreContainer() {
		super(new ModMetadata());
		ModMetadata modMeta = getMetadata();
		modMeta.authorList = Arrays.asList("Jonius7");
		modMeta.modId = "ftafix";
		modMeta.version = "1.0.4";
		modMeta.name = "FtaFix";
	}

	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
