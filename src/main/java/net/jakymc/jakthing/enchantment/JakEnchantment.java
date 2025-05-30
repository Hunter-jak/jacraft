package net.jakymc.jakthing.enchantment;

import com.mojang.blaze3d.shaders.Effect;
import net.jakymc.jakthing.item.SnowballMachineGun;
import net.jakymc.jakthing.JakThing;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JakEnchantment {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, JakThing.MODID);
    public static final EnchantmentCategory SnowGun = EnchantmentCategory.create("snowball_machine_gun", (item -> item instanceof SnowballMachineGun));
    public static RegistryObject<Enchantment> MULTISNOW = ENCHANTMENTS.register("multisnow",
            ()->new MultiSnow(Enchantment.Rarity.RARE, SnowGun, EquipmentSlot.MAINHAND));
    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }
}
