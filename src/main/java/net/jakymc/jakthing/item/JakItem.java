package net.jakymc.jakthing.item;

import net.jakymc.jakthing.entity.JakEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.jakymc.jakthing.JakThing.MODID;

public class JakItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    //Items
    public static final RegistryObject<Item> SMG = ITEMS.register("snowball_machine_gun", () -> new SnowballMachineGun());
    public static final RegistryObject<Item> LIVINGPOTATO_SPAWN_EGG = ITEMS.register("livingpotato_spawn_egg",() -> new ForgeSpawnEggItem(JakEntity.LivingPotato,0x234155,0x234155,new Item.Properties()));

    //Blocks
//    public static final RegistryObject<Item> STAVECHEST_ITEM = ITEMS.register("stave_chest", () -> new BlockItem(STAVE_CHEST.get(), new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
