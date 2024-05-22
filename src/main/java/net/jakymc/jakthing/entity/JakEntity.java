package net.jakymc.jakthing.entity;

import net.jakymc.jakthing.JakThing;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import static net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES;

public class JakEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ENTITY_TYPES, JakThing.MODID);

    public static final RegistryObject<EntityType<LivingPotato>> LivingPotato = ENTITIES.register("living_potato", () ->
            EntityType.Builder.of(LivingPotato::new, MobCategory.CREATURE).sized(0.25f, 0.5f).build("living_potato"));

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
