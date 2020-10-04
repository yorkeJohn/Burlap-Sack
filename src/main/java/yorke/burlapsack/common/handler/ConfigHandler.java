package yorke.burlapsack.common.handler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler
{
	public static Configuration config;
	public static Property sackBlacklist;

	public static void loadConfig ()
	{
		config.load();

		sackBlacklist = config.get("blacklist", "burlapSackBlacklist", new String[0]);
		sackBlacklist.setComment("A list of entity IDs to blacklist from the Burlap Sack. eg Llama");

		config.save();
	}
}
