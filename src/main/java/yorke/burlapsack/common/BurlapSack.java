package yorke.burlapsack.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yorke.burlapsack.common.handler.ConfigHandler;
import yorke.burlapsack.common.items.ItemBurlapSack;
import yorke.burlapsack.common.lib.BurlapSackCreativeTab;
import yorke.burlapsack.common.lib.LibInfo;
import yorke.burlapsack.common.registry.ItemRegistry;

@Mod (modid = LibInfo.MODID, name = LibInfo.NAME, version = LibInfo.VERSION)
public class BurlapSack
{
	public static final CreativeTabs tabBurlapSack = new BurlapSackCreativeTab("burlapsack");

	@EventHandler
	public void preInit (FMLPreInitializationEvent event)
	{
		ConfigHandler.config = new Configuration(event.getSuggestedConfigurationFile());
		ConfigHandler.loadConfig();
	}

	@EventHandler
	public void init (FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(ItemRegistry.class);
	}

	@EventHandler
	public void loadComplete (FMLLoadCompleteEvent event)
	{
		blacklistEntities();
	}

	public void blacklistEntities ()
	{
		String[] blacklist = ConfigHandler.sackBlacklist.getStringList();
		for (String str : blacklist)
		{
			Class<?> entityClass = EntityList.getClass(new ResourceLocation(str));
			if (entityClass != null) ItemBurlapSack.blacklistEntity(entityClass);
		}
	}
}
