package yorke.burlapsac.common.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import yorke.burlapsac.common.registry.ItemRegistry;

public class BurlapSacCreativeTab extends CreativeTabs
{
	public BurlapSacCreativeTab (String label)
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem ()
	{
		return new ItemStack(ItemRegistry.BURLAP_SAC);
	}
}
