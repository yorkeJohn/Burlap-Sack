package yorke.burlapsack.common.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import yorke.burlapsack.common.registry.ItemRegistry;

public class BurlapSackCreativeTab extends CreativeTabs
{
	public BurlapSackCreativeTab (String label)
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem ()
	{
		return new ItemStack(ItemRegistry.BURLAP_SACK);
	}
}
