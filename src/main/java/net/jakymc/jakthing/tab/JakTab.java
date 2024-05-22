package net.jakymc.jakthing.tab;

import net.jakymc.jakthing.JakThing;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.jakymc.jakthing.item.JakItem.*;

public class JakTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JakThing.MODID);
    public static final RegistryObject<CreativeModeTab> JakThingTab = CREATIVE_MODE_TABS.register("jakthing_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.jakthing"))
            .icon(() -> SMG.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(SMG.get());
                output.accept(LIVINGPOTATO_SPAWN_EGG.get());
//                output.accept(STAVECHEST_ITEM.get());
            }).build());
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
