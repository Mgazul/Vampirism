package de.teamlapen.vampirism.inventory;

import de.teamlapen.lib.lib.inventory.InventoryContainerMenu;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.core.ModContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.network.IContainerFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PotionTableMenu extends InventoryContainerMenu {
    public static final SelectorInfo @NotNull [] SELECTOR_INFOS;
    public static final SelectorInfo @NotNull [] SELECTOR_INFOS_EXTENDED;

    static {
        SELECTOR_INFOS = new SelectorInfo[6];
        SELECTOR_INFOS_EXTENDED = new SelectorInfo[8];
        SELECTOR_INFOS[0] = SELECTOR_INFOS_EXTENDED[0] = new SelectorInfo(Ingredient.of(Items.BLAZE_POWDER), 23, 14);
        SELECTOR_INFOS[1] = SELECTOR_INFOS_EXTENDED[1] = new SelectorInfo(stack -> VampirismAPI.extendedBrewingRecipeRegistry().isValidExtraIngredient(stack), 101, 16);
        SELECTOR_INFOS[2] = SELECTOR_INFOS_EXTENDED[2] = new SelectorInfo(stack -> VampirismAPI.extendedBrewingRecipeRegistry().isValidIngredient(stack), 126, 8);
        SELECTOR_INFOS[3] = SELECTOR_INFOS_EXTENDED[3] = new SelectorInfo(stack -> VampirismAPI.extendedBrewingRecipeRegistry().isValidInput(stack), 148, 59);
        SELECTOR_INFOS[4] = SELECTOR_INFOS_EXTENDED[4] = new SelectorInfo(stack -> VampirismAPI.extendedBrewingRecipeRegistry().isValidInput(stack), 126, 59);
        SELECTOR_INFOS[5] = SELECTOR_INFOS_EXTENDED[5] = new SelectorInfo(stack -> VampirismAPI.extendedBrewingRecipeRegistry().isValidInput(stack), 104, 59);
        SELECTOR_INFOS_EXTENDED[6] = new SelectorInfo(stack -> VampirismAPI.extendedBrewingRecipeRegistry().isValidInput(stack), 82, 59);
        SELECTOR_INFOS_EXTENDED[7] = new SelectorInfo(stack -> VampirismAPI.extendedBrewingRecipeRegistry().isValidInput(stack), 60, 59);
    }

    private final boolean extended;
    private final @NotNull ContainerData syncedProperties;

    public PotionTableMenu(int id, @NotNull Inventory playerInventory, ContainerLevelAccess worldPos, @NotNull Container inventory, boolean extended, @Nullable ContainerData syncedProperties) {
        super(ModContainer.EXTENDED_POTION_TABLE.get(), id, playerInventory, worldPos, inventory, extended ? SELECTOR_INFOS_EXTENDED : SELECTOR_INFOS);
        assert inventory.getContainerSize() >= (extended ? 8 : 6);
        this.syncedProperties = syncedProperties == null ? new SimpleContainerData(2) : syncedProperties;
        addPlayerSlots(playerInventory);
        this.addDataSlots(this.syncedProperties);
        this.extended = extended;
    }

    public int getBrewTime() {
        return syncedProperties.get(0);
    }

    public int getFuelTime() {
        return syncedProperties.get(1);
    }

    public boolean isExtendedTable() {
        return this.extended;
    }

    @Override
    protected boolean isSlotEnabled(int id) {
        return id <= 5 || this.isExtendedTable();
    }

    public static class Factory implements IContainerFactory<PotionTableMenu> {

        @Nullable
        @Override
        public PotionTableMenu create(int windowId, @NotNull Inventory inv, @Nullable FriendlyByteBuf data) {
            if (data == null) {
                return new PotionTableMenu(windowId, inv, ContainerLevelAccess.NULL, new SimpleContainer(6), false, null);
            }
            boolean extraSlots = data.readBoolean(); //Anything read here has to be written to buffer in open method (in ExtendedPotionTableTileEntity)
            return new PotionTableMenu(windowId, inv, ContainerLevelAccess.NULL, new SimpleContainer(extraSlots ? 8 : 6), extraSlots, null);
        }
    }
}
