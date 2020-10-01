package yorke.burlapsack.common.registry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yorke.burlapsack.common.BurlapSack;
import yorke.burlapsack.common.items.ItemBurlapSack;

@Mod.EventBusSubscriber
public class ItemRegistry
{
	public static Item BURLAP_FABRIC = new Item().setRegistryName("burlap_fabric").setUnlocalizedName("burlap_fabric").setCreativeTab(BurlapSack.tabBurlapSack);
	public static ItemBurlapSack BURLAP_SACK = new ItemBurlapSack();

	@SubscribeEvent
	public static void registerItems (RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(BURLAP_FABRIC);
		event.getRegistry().register(BURLAP_SACK);
	}

	@SubscribeEvent
	public static void registerRenderers (ModelRegistryEvent event)
	{
		registerRenderer(BURLAP_FABRIC);
		registerRenderer(BURLAP_SACK);
	}

	private static void registerRenderer (Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
