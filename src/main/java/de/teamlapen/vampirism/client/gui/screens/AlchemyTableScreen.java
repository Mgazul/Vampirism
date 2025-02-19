package de.teamlapen.vampirism.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.inventory.AlchemyTableMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {

    private static final ResourceLocation BREWING_STAND_LOCATION = new ResourceLocation(REFERENCE.MODID, "textures/gui/container/alchemy_table.png");
    private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public AlchemyTableScreen(@NotNull AlchemyTableMenu p_i51105_1_, @NotNull Inventory p_i51105_2_, @NotNull Component p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.imageHeight = 181;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    @Override
    protected void renderBg(PoseStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BREWING_STAND_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = this.menu.getFuel();
        int l = Mth.clamp((18 * k + 20 - 1) / 20, 0, 18);
        if (l > 0) {
            this.blit(stack, i + 33, j + 60, 176, 9, l, 4);
        }

        int i1 = this.menu.getBrewingTicks();
        if (i1 > 0) {
            float j1 = 1.0F - ((float) i1 / 600.0F);
            if (j1 > 0) {
                this.blit(stack, i + 73, j + 57, 176, 1, (int) (j1 * 28), 8);
                int color = this.menu.getColor();
                RenderSystem.setShaderColor(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f, 1F);
                this.blit(stack, i + 104, j + 36, 176, 13, (int) (j1 * 32), 32);
            }
        }

    }
}
