package net.jakymc.jakthing.effect;

import net.jakymc.jakthing.JakThing;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JakEffect {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, JakThing.MODID);

    public static RegistryObject<MobEffect> FLAME = EFFECT.register("flame",
            () ->new Flame(MobEffectCategory.HARMFUL,0xf8970d));
    public static void register(IEventBus eventBus){
        EFFECT.register(eventBus);
    }
}
