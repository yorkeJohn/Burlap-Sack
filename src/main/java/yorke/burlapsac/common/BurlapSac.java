package yorke.burlapsac.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yorke.burlapsac.common.lib.BurlapSacCreativeTab;
import yorke.burlapsac.common.lib.LibInfo;
import yorke.burlapsac.common.registry.ItemRegistry;

@Mod (modid = LibInfo.MODID, name = LibInfo.NAME, version = LibInfo.VERSION)
public class BurlapSac
{
	public static final CreativeTabs tabBurlapSac = new BurlapSacCreativeTab("burlapsac");

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
