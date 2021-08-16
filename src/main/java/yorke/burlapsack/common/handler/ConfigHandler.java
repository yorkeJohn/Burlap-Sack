package yorke.burlapsack.common.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ConfigHandler {

	public static ForgeConfigSpec CONFIG;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> sackBlacklist;
	private static final ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();

	static {
		buildConfig();
		CONFIG = CONFIG_BUILDER.build();
	}

	private static void buildConfig () {
		CONFIG_BUILDER.push("General");
		CONFIG_BUILDER.comment("A list of entity IDs to blacklist from the Burlap Sack. eg [\"llama\", \"chicken\", \"pig\"]");
		sackBlacklist = CONFIG_BUILDER.defineList("blacklist", new ArrayList<String>(), o -> o instanceof String);
		CONFIG_BUILDER.pop();
	}

}
