package net.jakymc.jakthing.item;

import net.jakymc.jakthing.entity.JakEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.ModList;

import static net.jakymc.jakthing.JakThing.MODID;

public class JakItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    //Items
    public static final RegistryObject<Item> SMG = ITEMS.register("snowball_machine_gun", () -> new SnowballMachineGun());
    public static final RegistryObject<Item> LivingPotatoItem = ITEMS.register("living_potato_item", () -> new LivingPotatoItem());
    //Blocks
//    public static final RegistryObject<Item> STAVECHEST_ITEM = ITEMS.register("stave_chest", () -> new BlockItem(STAVE_CHEST.get(), new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
