package de.teamlapen.vampirism.blockentity;

import de.teamlapen.vampirism.core.ModTiles;
import de.teamlapen.vampirism.items.PureBloodItem;
import de.teamlapen.vampirism.world.VampirismWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;


public class FogDiffuserBlockEntity extends BlockEntity {

    @NotNull
    private State state = State.IDLE;
    @NotNull
    private Strength strength = Strength.NONE;

    private float fuel = 0;
    private float bootProgress = 0;

    public FogDiffuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModTiles.FOG_DIFFUSER.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("state", this.state.name());
        pTag.putString("strength", this.strength.name());
        pTag.putFloat("fuel", this.fuel);
        pTag.putFloat("bootProgress", this.bootProgress);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        this.state = State.valueOf(pTag.getString("state"));
        this.strength = Strength.valueOf(pTag.getString("strength"));
        this.fuel = pTag.getFloat("fuel");
        this.bootProgress = pTag.getFloat("bootProgress");
    }

    public boolean addFuel(@NotNull Player pPlayer, ItemStack stack) {
        if (canAcceptFuelItem(stack) && isFuelingAllowedFor(stack)) {
            Strength strength = getStrengthFor(stack);
            if (strength.getLevel() > this.strength.getLevel()) {
                this.strength = strength;
            }
            float fuel = getFuelFor(stack);
            this.fuel = Math.min(1, this.fuel + fuel);
            if (!pPlayer.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }

    public boolean isFuelingAllowedFor(ItemStack stack) {
        float fuel = getFuelFor(stack);
        return this.fuel + fuel <= 1 + fuel * 0.6;
    }

    public boolean canAcceptFuelItem(ItemStack stack) {
        Strength strength = getStrengthFor(stack);
        return strength != Strength.NONE && strength.getLevel() >= this.strength.getLevel();
    }

    public Strength getStrengthFor(ItemStack stack) {
        if (stack.getItem() instanceof PureBloodItem pureBlood) {
            return switch (pureBlood.getLevel()) {
                default -> Strength.LOW;
                case 2, 3 -> Strength.MEDIUM;
                case 4 -> Strength.HIGH;
            };
        }
        return Strength.NONE;
    }

    public float getFuelFor(ItemStack stack) {
        if (stack.getItem() instanceof PureBloodItem pureBlood) {
            return switch (pureBlood.getLevel()) {
                default -> 0.1f;
                case 1 -> 0.2f;
                case 2 -> 0.3f;
                case 3 -> 0.4f;
                case 4 -> 0.5f;
            };
        }
        return 0;
    }

    protected int getRange() {
        return this.strength.getRadius();
    }

    public @NotNull Strength getStrength() {
        return strength;
    }

    public @NotNull State getState() {
        return state;
    }

    public float getFuel() {
        return fuel;
    }

    public float getBootProgress() {
        return bootProgress;
    }

    protected AABB getArea() {
        return getArea(this.getRange());
    }

    protected AABB getArea(int range) {
        return new AABB(this.worldPosition.offset(-range, -range, -range), this.worldPosition.offset(range, range, range));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FogDiffuserBlockEntity blockEntity) {
        switch (blockEntity.state) {
            case IDLE -> {
                if (blockEntity.fuel > 0) {
                    blockEntity.state = State.BOOTING;
                    blockEntity.bootProgress = 0;
                }
            }
            case BOOTING -> {
                if (level.getGameTime() % 128 == 0) {
                    blockEntity.bootProgress += 0.1;
                    if (blockEntity.bootProgress >= 1) {
                        blockEntity.state = State.ACTIVE;
                    }
                    blockEntity.updateFogArea(level);
                }
            }
            case ACTIVE -> {
                if (level.getGameTime() % 256 == 0) {
                    blockEntity.fuel -= blockEntity.strength.getFuelConsumption() * 256;
                    if (blockEntity.fuel <= 0) {
                        blockEntity.state = State.SHUTTING_DOWN;
                        blockEntity.strength = Strength.NONE;
                        blockEntity.bootProgress = 1;
                    }
                }
            }
            case SHUTTING_DOWN -> {
                if (level.getGameTime() % 128 == 0) {
                    blockEntity.bootProgress -= 0.1;
                    if (blockEntity.bootProgress <= 0) {
                        blockEntity.state = State.IDLE;
                    }
                    blockEntity.updateFogArea(level);
                }
            }
        }
    }

    public void updateFogArea(Level level) {
        VampirismWorld.getOpt(level).ifPresent(w -> w.updateTemporaryArtificialFog(this.worldPosition, switch (this.state) {
            case BOOTING, SHUTTING_DOWN -> getArea((int) (this.getRange() * this.bootProgress));
            case ACTIVE -> getArea();
            default -> null;
        }));
    }

    public enum State {
        IDLE,
        BOOTING,
        ACTIVE,
        SHUTTING_DOWN
    }

    public enum Strength {
        NONE(0, 0, 0),
        /**
         * lasts ~ 20 minutes
         */
        LOW(1, 5, 0.000040f),
        /**
         * lasts ~ 40 minutes
         */
        MEDIUM(2, 10, 0.000020f),
        /**
         * lasts ~ 1.5 hours
         */
        HIGH(3, 15, 0.000010f);

        private final int level;
        private final int radius;
        private final float fuelConsumption;

        Strength(int level, int radius, float fuelConsumption) {
            this.level = level;
            this.radius = radius;
            this.fuelConsumption = fuelConsumption;
        }

        public int getLevel() {
            return level;
        }

        public int getRadius() {
            return radius;
        }

        public float getFuelConsumption() {
            return fuelConsumption;
        }

    }
}
