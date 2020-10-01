package yorke.burlapsack.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
	}

	@EventHandler
	public void init (FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(ItemRegistry.class);
	}
}
