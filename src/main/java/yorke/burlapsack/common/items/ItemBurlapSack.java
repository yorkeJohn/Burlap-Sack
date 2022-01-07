package yorke.burlapsack.common.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import yorke.burlapsack.common.BurlapSack;

public class ItemBurlapSack extends Item {

    // Entity blacklist
    private static List<EntityType<?>> blacklist = new ArrayList<>();

    public ItemBurlapSack () {
        super(new Item.Properties().stacksTo(16).tab(BurlapSack.BURLAP_SACK_TAB));
    }

    @Override
    public InteractionResult interactLivingEntity (ItemStack item, Player player, LivingEntity target, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.hasTag() && ! (target instanceof Enemy) && !blacklist.contains(target.getType())) {
            CompoundTag tag = new CompoundTag();
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
                target.setRemoved(RemovalReason.KILLED);
                return InteractionResult.SUCCESS;
            }
            else return InteractionResult.PASS;
        }
        else return InteractionResult.PASS;
    }

    private MutableComponent makeNewName (ItemStack stack, LivingEntity target) {
        return new TextComponent(stack.getHoverName().getString() + " ("
                + (target.hasCustomName() ? target.getDisplayName() : target.getName().getString()) + ")")
                        .setStyle(Style.EMPTY.withItalic(false));
    }

    @Override
    public InteractionResult useOn (UseOnContext context) {
        if (context.getPlayer().level.isClientSide) return InteractionResult.PASS;

        int offsetX = context.getClickedFace().getStepX();
        int offsetY = context.getClickedFace().getStepY();
        int offsetZ = context.getClickedFace().getStepZ();

        ItemStack stack = context.getItemInHand();

        if (stack != null && stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            Optional<Entity> e = EntityType.create(tag, context.getPlayer().level);

            if (e.isPresent()) {
                AABB bb = e.get().getBoundingBox();
                BlockPos pos = context.getClickedPos();

                e.get().moveTo(pos.getX() + (bb.maxX - bb.minX) * 0.5 + offsetX, pos.getY() + offsetY, pos.getZ()
                        + (bb.maxZ - bb.minZ) * 0.5 + offsetZ, context.getPlayer().level.random.nextFloat() * 360.0F, 0);

                context.getPlayer().level.addFreshEntity(e.get());

                stack.setTag(null);
                stack.resetHoverName();

                if (e.get() instanceof Mob) ((Mob) e.get()).playAmbientSound();

                return InteractionResult.SUCCESS;
            }
            else return InteractionResult.PASS;
        }
        else return InteractionResult.PASS;
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
