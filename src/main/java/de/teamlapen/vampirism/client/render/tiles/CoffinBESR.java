package de.teamlapen.vampirism.client.render.tiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.blockentity.CoffinBlockEntity;
import de.teamlapen.vampirism.blocks.CoffinBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

/**
 * Render the coffin with its different colors and the lid opening animation
 */
@OnlyIn(Dist.CLIENT)
public class CoffinBESR extends VampirismBESR<CoffinBlockEntity> {
    private static final Marker COFFIN = new MarkerManager.Log4jMarker("COFFIN");
    private final Logger LOGGER = LogManager.getLogger();

    public CoffinBESR(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull CoffinBlockEntity tile, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource iRenderTypeBuffer, int i, int i1) {
        this.renderBlock(tile, partialTicks, matrixStack, iRenderTypeBuffer, i, i1);
    }

    public void renderBlock(@NotNull CoffinBlockEntity tile, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource iRenderTypeBuffer, int i, int i1) {
        assert tile.getLevel() != null;
        BlockState state = tile.getBlockState();
        Direction direction = state.getValue(HORIZONTAL_FACING);

        if (!isHeadSafe(tile.getLevel(), tile.getBlockPos())) return;

        matrixStack.pushPose();
        boolean vertical = state.getValue(CoffinBlock.VERTICAL);
        switch (direction) {
            case EAST:
                if (vertical) {
                    matrixStack.mulPose(new Quaternion(new Vector3f(0, 0, 1), 90, true));
                    matrixStack.translate(0, -1, 0);
                }
                matrixStack.mulPose(new Quaternion(new Vector3f(0, 1, 0), 90, true));
                matrixStack.translate(-1, 0, -1);
                break;
            case WEST:
                if (vertical) {
                    matrixStack.mulPose(new Quaternion(new Vector3f(0, 0, 1), -90, true));
                    matrixStack.translate(-1, 0, 0);
                }
                matrixStack.mulPose(new Quaternion(new Vector3f(0, 1, 0), -90, true));
                matrixStack.translate(0, 0, -2);
                break;
            case SOUTH:
                if (vertical) {
                    matrixStack.mulPose(new Quaternion(new Vector3f(1, 0, 0), -90, true));
                    matrixStack.translate(0, -1, 0);
                }
                matrixStack.translate(0, 0, -1);
                break;
            case NORTH:
                if (vertical) {
                    matrixStack.mulPose(new Quaternion(new Vector3f(1, 0, 0), 90, true));
                    matrixStack.translate(0, 0, -1);
                }
                matrixStack.mulPose(new Quaternion(new Vector3f(0, 1, 0), 180, true));
                matrixStack.translate(-1, 0, -2);
                break;
        }

        BakedModel baseModel = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(REFERENCE.MODID, "block/coffin/coffin_bottom_" + tile.color.getName()));
        ModelData modelData = baseModel.getModelData(tile.getLevel(), tile.getBlockPos(), state, ModelData.EMPTY);
        for (RenderType renderType : baseModel.getRenderTypes(state, RandomSource.create(42), modelData)) {
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(matrixStack.last(), iRenderTypeBuffer.getBuffer(net.minecraftforge.client.RenderTypeHelper.getEntityRenderType(renderType, false)), state, baseModel, 1, 1, 1, i, i1, modelData, renderType);
        }

        matrixStack.pushPose();
        if (vertical) {
            matrixStack.mulPose(new Quaternion(new Vector3f(0, 0, 1), 80 * tile.lidPos, true));
            matrixStack.translate(0, -0.5 * tile.lidPos, 0);
        } else {
            matrixStack.mulPose(new Quaternion(new Vector3f(0, 1, 0), -35 * tile.lidPos, true));
            matrixStack.translate(0, 0, -0.5 * tile.lidPos);
        }

        BakedModel lidModel = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(REFERENCE.MODID, "block/coffin/coffin_top_" + tile.color.getName()));
        modelData = lidModel.getModelData(tile.getLevel(), tile.getBlockPos(), state, ModelData.EMPTY);
        for (RenderType renderType : lidModel.getRenderTypes(state, RandomSource.create(42), modelData)) {
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(matrixStack.last(), iRenderTypeBuffer.getBuffer(net.minecraftforge.client.RenderTypeHelper.getEntityRenderType(renderType, false)), state, lidModel, 1, 1, 1, i, i1, modelData, renderType);
        }
        matrixStack.popPose();
        matrixStack.popPose();
    }

    /**
     * Checks if the coffin part at the given pos is the head of the coffin. Any exception is caught and false is returned
     */
    private boolean isHeadSafe(@NotNull Level world, @NotNull BlockPos pos) {
        try {
            return CoffinBlock.isHead(world, pos);
        } catch (IllegalArgumentException e) {
            LOGGER.error(COFFIN, "Failed to check coffin head at {} caused by wrong blockstate. Block at that pos: {}", pos, world.getBlockState(pos));
        } catch (Exception e) {
            LOGGER.error(COFFIN, "Failed to check coffin head at " + pos + ".", e);
        }
        return false;
    }
}
