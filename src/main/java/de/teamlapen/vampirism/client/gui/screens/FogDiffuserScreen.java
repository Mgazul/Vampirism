package de.teamlapen.vampirism.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.client.gui.ProgressBar;
import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.blockentity.FogDiffuserBlockEntity;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FogDiffuserScreen extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(REFERENCE.MODID, "textures/gui/fog_diffuser.png");
    protected final int xSize = 220;
    protected final int ySize = 79;
    protected int guiLeft;
    protected int guiTop;

    private final FogDiffuserBlockEntity blockEntity;
    private ProgressBar fuelProgress;
    private ProgressBar startupProgress;

    public FogDiffuserScreen(FogDiffuserBlockEntity blockEntity, Component pTitle) {
        super(pTitle);
        this.blockEntity = blockEntity;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.fuelProgress = this.addRenderableWidget(new ProgressBar(this.guiLeft + 25, this.guiTop + 22, 170, Component.translatable("fuel")));
        this.fuelProgress.setColor(0xaaaaaa);
        this.startupProgress = this.addRenderableWidget(new ProgressBar(this.guiLeft + 25, this.guiTop + 45, 170, Component.translatable("startup")));
        this.startupProgress.setColor(0xaaaaaa);
        this.updateFuel();
    }

    @Override
    public void tick() {
        updateFuel();
        updateStartup();
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.renderGuiBackground(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTitle(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private void renderTitle(@NotNull PoseStack pPoseStack) {
        this.font.drawShadow(pPoseStack, title, this.guiLeft + 15, this.guiTop + 5, -1);
    }

    private void renderGuiBackground(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        blit(pPoseStack, this.guiLeft, this.guiTop, this.getBlitOffset(), 0, 0, this.xSize, this.ySize, 256, 256);
    }

    protected void updateFuel() {
        this.fuelProgress.setProgress(this.blockEntity.getFuel());
        this.fuelProgress.setColor(switch (this.blockEntity.getStrength()) {
            default -> 0x964c4c;
            case LOW -> 0x9f3b3b;
            case MEDIUM -> 0xae2828;
            case HIGH -> 0xaa0101;
        });
        if (this.blockEntity.getState() == FogDiffuserBlockEntity.State.IDLE) {
            this.fuelProgress.setColor(0x808080);
        }
        this.fuelProgress.setMessage(this.getFuelText());
    }

    protected void updateStartup() {
        this.startupProgress.visible = switch (this.blockEntity.getState()) {
            case IDLE, BOOTING, SHUTTING_DOWN -> true;
            default -> false;
        };
        this.startupProgress.setProgress(this.blockEntity.getBootProgress());
        this.startupProgress.setMessage(this.getStartupText());
    }

    protected Component getFuelText() {
        FogDiffuserBlockEntity.Strength strength = this.blockEntity.getStrength();
        if (strength == FogDiffuserBlockEntity.Strength.NONE) {
            return Component.translatable("text.vampirism.empty");
        } else {
            return Component.translatable("item.vampirism.pure_blood").append(" ").append(Component.translatable("text.vampirism.purity_" + this.blockEntity.getStrength().name().toLowerCase()));
        }
    }

    protected Component getStartupText() {
        return switch (this.blockEntity.getState()) {
            case SHUTTING_DOWN -> Component.translatable("text.vampirism.fog_diffuser.shutting_down");
            case BOOTING -> Component.translatable("text.vampirism.fog_diffuser.booting");
            case IDLE -> Component.translatable("text.vampirism.fog_diffuser.idle");
            case ACTIVE -> Component.translatable("text.vampirism.fog_diffuser.active");
        };
    }
}
