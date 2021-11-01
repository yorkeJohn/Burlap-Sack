package yorke.burlapsack.common;

import java.util.List;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yorke.burlapsack.common.handler.ConfigHandler;
import yorke.burlapsack.common.items.ItemBurlapSack;
import yorke.burlapsack.common.registry.ItemRegistry;

@Mod ("burlapsack")
public class BurlapSack {

    public static final String MODID = "burlapsack";
    public static final CreativeModeTab BURLAP_SACK_TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon () {
            return new ItemStack(ItemRegistry.BURLAP_SACK.get());
        }
    };

    public BurlapSack () {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.CONFIG);
        ItemRegistry.ITEMS.register(event);
        event.addListener(this::loadComplete);
    }

    public void loadComplete (final FMLLoadCompleteEvent event) {
        blacklistEntities();
    }

    public void blacklistEntities () {
        List<? extends String> blacklist = ConfigHandler.sackBlacklist.get();
        blacklist.forEach(ItemBurlapSack::blacklistEntity);
    }

}
