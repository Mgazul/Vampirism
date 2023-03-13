package de.teamlapen.vampirism.blocks;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.blockentity.FogDiffuserBlockEntity;
import de.teamlapen.vampirism.core.ModTiles;
import de.teamlapen.vampirism.world.VampirismWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FogDiffuserBlock extends VampirismBlockContainer {

    public FogDiffuserBlock(@NotNull Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        getBlockEntity(pLevel, pPos).ifPresent(blockEntity -> {
            blockEntity.interact(itemInHand);
            VampirismMod.proxy.displayFogDiffuserScreen(blockEntity, getName());
        });
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @NotNull
    protected Optional<FogDiffuserBlockEntity> getBlockEntity(@NotNull Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FogDiffuserBlockEntity) {
            return Optional.of((FogDiffuserBlockEntity) blockEntity);
        }
        return Optional.empty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new FogDiffuserBlockEntity(pPos, pState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        super.onRemove(state, worldIn, pos, newState, isMoving);
        VampirismWorld.getOpt(worldIn).ifPresent(vampirismWorld -> vampirismWorld.updateTemporaryArtificialFog(pos, null));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModTiles.FOG_DIFFUSER.get(), FogDiffuserBlockEntity::tick);
    }
}
