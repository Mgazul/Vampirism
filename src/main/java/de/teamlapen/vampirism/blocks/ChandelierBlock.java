package de.teamlapen.vampirism.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import java.util.stream.Stream;


public class ChandelierBlock extends VampirismBlock {

    private final static VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(7, 10, 8, 8, 16, 9),
            Block.makeCuboidShape(8, 8, 9, 9, 10, 10),
            Block.makeCuboidShape(7, 10, 9, 8, 11, 10),
            Block.makeCuboidShape(6, 8, 7, 7, 10, 8),
            Block.makeCuboidShape(6, 10, 8, 7, 11, 9),
            Block.makeCuboidShape(6, 8, 9, 7, 10, 10),
            Block.makeCuboidShape(7, 10, 7, 8, 11, 8),
            Block.makeCuboidShape(8, 8, 7, 9, 10, 8),
            Block.makeCuboidShape(8, 10, 8, 9, 11, 9),
            Block.makeCuboidShape(11.25, 8, 2.25, 13.75, 9, 4.75),
            Block.makeCuboidShape(6.25, 8, 1.25, 8.75, 9, 3.75),
            Block.makeCuboidShape(1.25, 8, 2.25, 3.75, 9, 4.75),
            Block.makeCuboidShape(0.25, 8, 7.25, 2.75, 9, 9.75),
            Block.makeCuboidShape(1.25, 8, 12.25, 3.75, 9, 14.75),
            Block.makeCuboidShape(6.25, 8, 13.25, 8.75, 9, 15.75),
            Block.makeCuboidShape(11.25, 8, 12.25, 13.75, 9, 14.75),
            Block.makeCuboidShape(12.25, 8, 7.25, 14.75, 9, 9.75),
            Block.makeCuboidShape(7, 6, 13, 8, 7, 14),
            Block.makeCuboidShape(7, 7, 14, 8, 9, 15),
            Block.makeCuboidShape(7, 5, 12, 8, 6, 13),
            Block.makeCuboidShape(4, 6, 8, 5, 8, 9),
            Block.makeCuboidShape(3, 6, 12, 4, 7, 13),
            Block.makeCuboidShape(2, 6, 8, 3, 7, 9),
            Block.makeCuboidShape(2, 7, 13, 3, 9, 14),
            Block.makeCuboidShape(1, 7, 8, 2, 9, 9),
            Block.makeCuboidShape(3, 5, 8, 4, 6, 9),
            Block.makeCuboidShape(4, 5, 11, 5, 6, 12),
            Block.makeCuboidShape(5, 6, 10, 6, 8, 11),
            Block.makeCuboidShape(9, 6, 10, 10, 8, 11),
            Block.makeCuboidShape(10, 6, 8, 11, 8, 9),
            Block.makeCuboidShape(11, 6, 12, 12, 7, 13),
            Block.makeCuboidShape(10, 5, 11, 11, 6, 12),
            Block.makeCuboidShape(11, 5, 8, 12, 6, 9),
            Block.makeCuboidShape(12, 6, 8, 13, 7, 9),
            Block.makeCuboidShape(12, 7, 13, 13, 9, 14),
            Block.makeCuboidShape(13, 7, 8, 14, 9, 9),
            Block.makeCuboidShape(5, 8, 8, 6, 10, 9),
            Block.makeCuboidShape(9, 8, 8, 10, 10, 9),
            Block.makeCuboidShape(13, 9, 8, 14, 10, 9),
            Block.makeCuboidShape(12, 9, 13, 13, 10, 14),
            Block.makeCuboidShape(1, 9, 8, 2, 10, 9),
            Block.makeCuboidShape(2, 9, 13, 3, 10, 14),
            Block.makeCuboidShape(7, 6, 5, 8, 8, 6),
            Block.makeCuboidShape(9, 6, 6, 10, 8, 7),
            Block.makeCuboidShape(7, 8, 6, 8, 10, 7),
            Block.makeCuboidShape(7, 8, 10, 8, 10, 11),
            Block.makeCuboidShape(7, 6, 11, 8, 8, 12),
            Block.makeCuboidShape(10, 5, 5, 11, 6, 6),
            Block.makeCuboidShape(11, 6, 4, 12, 7, 5),
            Block.makeCuboidShape(7, 5, 4, 8, 6, 5),
            Block.makeCuboidShape(7, 6, 3, 8, 7, 4),
            Block.makeCuboidShape(12, 7, 3, 13, 9, 4),
            Block.makeCuboidShape(7, 7, 2, 8, 9, 3),
            Block.makeCuboidShape(12, 9, 3, 13, 10, 4),
            Block.makeCuboidShape(7, 9, 2, 8, 10, 3),
            Block.makeCuboidShape(7, 9, 14, 8, 10, 15)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).orElse(VoxelShapes.empty());

    public ChandelierBlock() {
        super("chandelier", AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2).setLightLevel(s -> 14).notSolid());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasEnoughSolidSide(worldIn, pos.up(), Direction.DOWN);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.UP && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
