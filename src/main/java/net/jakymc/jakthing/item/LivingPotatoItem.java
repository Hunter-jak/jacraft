package net.jakymc.jakthing.item;

import net.jakymc.jakthing.entity.JakEntity;
import net.jakymc.jakthing.entity.LivingPotato;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LivingPotatoItem extends Item {
    public LivingPotatoItem() {
        super(new Item.Properties()
                .stacksTo(1)
        );
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        LivingPotato potato = JakEntity.LivingPotato.get().create(level);
        if (potato != null) {
            potato.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                potato.setPotatoSize(tag.getInt("Size"));
                potato.setFertilize(tag.getInt("Fertilize"));
                potato.setNeedFertilize(tag.getInt("NeedFertilize"));
                potato.setMaxSize(tag.getInt("MaxSize"));
                if (tag.contains("Health"))
                    potato.setHealth(tag.getInt("Health"));
            }

            level.addFreshEntity(potato);

            // 消耗物品
            if (!player.isCreative()) {
                stack.shrink(1);
            }

            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!stack.hasTag()) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("Size", 1);
            tag.putInt("Fertilize", 1);
            tag.putInt("NeedFertilize", 5);
            tag.putInt("MaxSize", 20);
            stack.setTag(tag);
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            int size = tag.getInt("Size");
            int fertilize = tag.getInt("Fertilize");
            int needfertilize = tag.getInt("NeedFertilize");
            int maxsize = tag.getInt("MaxSize");
            float health = tag.getFloat("Health");
            double maxhealth = tag.getDouble("MaxHealth");
            if(Screen.hasShiftDown()){
                if (tag.contains("Health"))
                    tooltip.add(Component.translatable("livingpotato.info.health" , health,maxhealth).withStyle(ChatFormatting.RED));
                tooltip.add(Component.translatable("livingpotato.info.size",size,maxsize).withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("livingpotato.info.fertilize",fertilize,needfertilize).withStyle(ChatFormatting.GRAY));
            }
            else {
                tooltip.add(Component.translatable("livingpotato.info",size).withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
