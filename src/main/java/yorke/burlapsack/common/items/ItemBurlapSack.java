package yorke.burlapsack.common.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import yorke.burlapsack.common.BurlapSack;

public class ItemBurlapSack extends Item {

    // Entity blacklist
    private static List<EntityType<?>> blacklist = new ArrayList<>();

    public ItemBurlapSack () {
        super(new Item.Properties().stacksTo(16).tab(BurlapSack.BURLAP_SACK_TAB));
    }

    @Override
    public ActionResultType interactLivingEntity (ItemStack item, PlayerEntity player, LivingEntity target, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.hasTag() && ! (target instanceof MonsterEntity) && !blacklist.contains(target.getType())) {
            CompoundNBT tag = new CompoundNBT();
            if (target.save(tag)) {
                if (stack.getCount() > 1) {
                    stack.shrink(1);
                    ItemStack stack2 = stack.copy();
                    stack2.setCount(1);
                    stack2.setTag(tag);
                    stack2.setHoverName(makeNewName(stack2, target));
                    if (!player.addItem(stack2)) player.drop(stack2, false);
                }
                else {
                    stack.setTag(tag);
                    stack.setHoverName(makeNewName(stack, target));
                }
                target.remove();
                return ActionResultType.SUCCESS;
            }
            else return ActionResultType.PASS;
        }
        else return ActionResultType.PASS;
    }

    private IFormattableTextComponent makeNewName (ItemStack stack, LivingEntity target) {
        return new TranslationTextComponent(stack.getHoverName().getString() + " ("
                + (target.hasCustomName() ? target.getDisplayName() : target.getName().getString()) + ")")
                        .setStyle(Style.EMPTY.withItalic(false));
    }

    @Override
    public ActionResultType useOn (ItemUseContext context) {
        if (context.getPlayer().level.isClientSide) return ActionResultType.PASS;

        int offsetX = context.getClickedFace().getStepX();
        int offsetY = context.getClickedFace().getStepY();
        int offsetZ = context.getClickedFace().getStepZ();

        ItemStack stack = context.getItemInHand();

        if (stack != null && stack.hasTag()) {
            CompoundNBT tag = stack.getTag();
            Optional<Entity> e = EntityType.create(tag, context.getPlayer().level);

            if (e.isPresent()) {
                AxisAlignedBB bb = e.get().getBoundingBox();
                BlockPos pos = context.getClickedPos();

                e.get().moveTo(pos.getX() + (bb.maxX - bb.minX) * 0.5 + offsetX, pos.getY() + offsetY, pos.getZ()
                        + (bb.maxZ - bb.minZ) * 0.5 + offsetZ, context.getPlayer().level.random.nextFloat() * 360.0F, 0);
                context.getPlayer().level.addFreshEntity(e.get());

                stack.setTag(null);
                stack.resetHoverName();

                if (e.get() instanceof MobEntity) ((MobEntity) e.get()).playAmbientSound();

                return ActionResultType.SUCCESS;
            }
            else return ActionResultType.PASS;
        }
        else return ActionResultType.PASS;
    }

    /**
     * Adds an entity to the Burlap Sack blacklist
     *
     * @param The name of the entity
     */
    public static void blacklistEntity (String entity) {
        EntityType<?> entityClass = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity));
        if (entityClass != null) blacklist.add(entityClass);
    }
}
