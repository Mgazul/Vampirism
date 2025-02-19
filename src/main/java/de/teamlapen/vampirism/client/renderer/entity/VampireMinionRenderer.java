package de.teamlapen.vampirism.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.vampirism.client.core.ModEntitiesRender;
import de.teamlapen.vampirism.client.renderer.entity.layers.PlayerBodyOverlayLayer;
import de.teamlapen.vampirism.entity.minion.VampireMinionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class VampireMinionRenderer extends DualBipedRenderer<VampireMinionEntity, PlayerModel<VampireMinionEntity>> {

    private final Pair<ResourceLocation, Boolean> @NotNull [] textures;
    private final Pair<ResourceLocation, Boolean> @NotNull [] minionSpecificTextures;


    public VampireMinionRenderer(EntityRendererProvider.@NotNull Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModEntitiesRender.GENERIC_BIPED), false), new PlayerModel<>(context.bakeLayer(ModEntitiesRender.GENERIC_BIPED_SLIM), true), 0.5F);
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        textures = gatherTextures("textures/entity/vampire", true);
        minionSpecificTextures = gatherTextures("textures/entity/minion/vampire", false);

        this.addLayer(new PlayerBodyOverlayLayer<>(this));
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModEntitiesRender.GENERIC_BIPED_ARMOR_INNER)), new HumanoidModel<>(context.bakeLayer(ModEntitiesRender.GENERIC_BIPED_ARMOR_OUTER))));
        this.getModel().body.visible = this.getModel().jacket.visible = false;
        this.getModel().leftArm.visible = this.getModel().leftSleeve.visible = this.getModel().rightArm.visible = this.getModel().rightSleeve.visible = false;
        this.getModel().rightLeg.visible = this.getModel().rightPants.visible = this.getModel().leftLeg.visible = this.getModel().leftPants.visible = false;
    }

    public int getMinionSpecificTextureCount() {
        return this.minionSpecificTextures.length;
    }

    public int getVampireTextureCount() {
        return this.textures.length;
    }

    @Override
    protected Pair<ResourceLocation, Boolean> determineTextureAndModel(@NotNull VampireMinionEntity entity) {
        Pair<ResourceLocation, Boolean> p = (entity.hasMinionSpecificSkin() && this.minionSpecificTextures.length > 0) ? minionSpecificTextures[entity.getVampireType() % minionSpecificTextures.length] : textures[entity.getVampireType() % textures.length];
        if (entity.shouldRenderLordSkin()) {
            return entity.getOverlayPlayerProperties().map(Pair::getRight).map(b -> Pair.of(p.getLeft(), b)).orElse(p);
        }
        return p;
    }

    @Override
    protected void scale(@NotNull VampireMinionEntity entityIn, @NotNull PoseStack matrixStackIn, float partialTickTime) {
        float s = entityIn.getScale();
        //float off = (1 - s) * 1.95f;
        matrixStackIn.scale(s, s, s);
        //matrixStackIn.translate(0,off,0f);
    }

}