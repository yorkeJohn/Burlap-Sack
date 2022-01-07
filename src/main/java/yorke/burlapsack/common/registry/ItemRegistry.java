package yorke.burlapsack.common.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yorke.burlapsack.common.BurlapSack;
import yorke.burlapsack.common.items.ItemBurlapSack;

@Mod.EventBusSubscriber
public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BurlapSack.MODID);

    public static final RegistryObject<Item> BURLAP_FABRIC = ITEMS
            .register("burlap_fabric", () -> new Item(new Item.Properties().tab(BurlapSack.BURLAP_SACK_TAB)));
    public static final RegistryObject<Item> BURLAP_SACK = ITEMS.register("burlap_sack", ItemBurlapSack::new);

}
