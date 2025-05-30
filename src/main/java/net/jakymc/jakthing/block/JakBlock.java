package net.jakymc.jakthing.block;

import net.jakymc.jakthing.JakThing;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JakBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, JakThing.MODID);
    public static final RegistryObject<Block> STEVE = BLOCKS.register("steve", () -> new Steve(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
