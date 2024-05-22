package net.jakymc.jakthing.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class StaveChest extends Block {
    public static VoxelShape SHAPE;

    static{
        VoxelShape base = Block.box(4, 23.5, 4, 12, 31.5, 12);
        VoxelShape column1 = Block.box(4, 11.5, 6, 12, 23.5, 10);
        VoxelShape column2 = Block.box(12, 11.5, 6, 16, 23.5, 10);
        VoxelShape column3 = Block.box(0, 11.5, 6, 4, 23.5, 10);
        VoxelShape column4 = Block.box(8, -0.5, 6, 12, 11.5, 10);
        SHAPE = Shapes.or(base, column1, column2, column3, column4);
    }

    protected StaveChest(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState p_60581_, BlockGetter p_60582_, BlockPos p_60583_) {
        return super.getShape(p_60581_, p_60582_, p_60583_ ,CollisionContext.empty());
    }
}
