package yorke.burlapsack.common.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import yorke.burlapsack.common.BurlapSack;

public class ItemBurlapSack extends Item
{
	private static List<Class<?>> blacklist = new ArrayList<>();

	public ItemBurlapSack ()
	{
		this.setRegistryName("burlap_sack");
		this.setUnlocalizedName("burlap_sack");
		this.setCreativeTab(BurlapSack.tabBurlapSack);
		this.setMaxStackSize(16);
	}

	@Override
	public boolean itemInteractionForEntity (ItemStack item, EntityPlayer player, EntityLivingBase target, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.hasTagCompound() && ! (target instanceof IMob) && target.isNonBoss() && !blacklist.contains(target.getClass()))
		{
			NBTTagCompound tag = new NBTTagCompound();
			if (!target.writeToNBTAtomically(tag)) return false;
			else
			{
				if (stack.getCount() > 1)
				{
					stack.shrink(1);
					ItemStack stack2 = stack.copy();
					stack2.setCount(1);
					stack2.setTagCompound(tag);
					stack2.setStackDisplayName(TextFormatting.RESET + stack.getDisplayName() + " (" + (target.hasCustomName() ? target.getDisplayName().getUnformattedText() : target.getName()) + ")");
					if (!player.addItemStackToInventory(stack2)) player.entityDropItem(stack2, (float) player.getEntityBoundingBox().maxY);
				}
				else
				{
					stack.setTagCompound(tag);
					stack.setStackDisplayName(TextFormatting.RESET + stack.getDisplayName() + " (" + (target.hasCustomName() ? target.getDisplayName().getUnformattedText() : target.getName()) + ")");
				}
				target.setDead();
				return true;
			}
		}
		else return false;
	}

	@Override
	public EnumActionResult onItemUse (EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset)
	{
		if (world.isRemote) return EnumActionResult.PASS;

		int offsetX = side.getFrontOffsetX();
		int offsetY = side.getFrontOffsetY();
		int offsetZ = side.getFrontOffsetZ();

		ItemStack stack = player.getHeldItem(hand);

		if (stack != null && stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			Entity e = EntityList.createEntityFromNBT(tag, world);

			if (e != null)
			{
				AxisAlignedBB bb = e.getEntityBoundingBox();

				e.setLocationAndAngles(pos.getX() + (bb.maxX - bb.minX) * 0.5 + offsetX, pos.getY() + offsetY, pos.getZ() + (bb.maxZ - bb.minZ) * 0.5 + offsetZ, world.rand.nextFloat() * 360.0F, 0.0F);
				world.spawnEntity(e);

				stack.setTagCompound(null);
				stack.clearCustomName();

				if (e instanceof EntityLiving) ((EntityLiving) e).playLivingSound();

				return EnumActionResult.SUCCESS;
			}
			else return EnumActionResult.PASS;
		}
		return EnumActionResult.PASS;
	}

	/**
	 * Adds an entity to the Burlap Sack blacklist
	 * 
	 * @param entityClass The entity to blacklist's class
	 */
	public static void blacklistEntity (Class<?> entityClass)
	{
		blacklist.add(entityClass);
	}
}
