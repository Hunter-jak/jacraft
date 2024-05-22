package net.jakymc.jakthing;

import com.mojang.logging.LogUtils;
import net.jakymc.jakthing.block.JakBlock;
import net.jakymc.jakthing.client.model.LivingPotatoModel;
import net.jakymc.jakthing.client.renderer.RenderLivingPotato;
import net.jakymc.jakthing.effect.JakEffect;
import net.jakymc.jakthing.entity.JakEntity;
import net.jakymc.jakthing.entity.LivingPotato;
import net.jakymc.jakthing.item.JakItem;
import net.jakymc.jakthing.enchantment.JakEnchantment;
import net.jakymc.jakthing.tab.JakTab;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static net.jakymc.jakthing.item.JakItem.*;

@Mod(JakThing.MODID)
public class JakThing
{
    public static final String MODID = "jakthing";
    public static final Logger LOGGER = LogUtils.getLogger();

    public JakThing()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        JakItem.register(modEventBus);
        JakBlock.register(modEventBus);
        JakEnchantment.register(modEventBus);
        JakEffect.register(modEventBus);
        JakEntity.register(modEventBus);
        JakTab.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

//        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

//    private void addCreative(BuildCreativeModeTabContentsEvent event)
//    {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(Blocks.BARRIER);
//    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID,bus=Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(JakEntity.LivingPotato.get(),RenderLivingPotato::new);
        }
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(LivingPotatoModel.LIVINGPOTATO_LAYER, LivingPotatoModel::createBodyLayer);
        }
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(JakEntity.LivingPotato.get(), LivingPotato.createAttributes().build());
        }
    }
}
