package de.teamlapen.vampirism.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.vampirism.entity.minion.management.PlayerMinionController;
import de.teamlapen.vampirism.world.MinionWorldData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


public class SRequestMinionSelectPacket implements IMessage {

    /**
     * Create a minion selection request that can be send to the client (player).
     * It offers all available/callable minions up for selection.
     * If no minions are available, it prints a status message to the player and returns empty
     * @param action The action that should be executed for the selected minion
     * @return Empty if no minions are available
     */
    public static Optional<SRequestMinionSelectPacket> createRequestForPlayer(ServerPlayer player, Action action){
        return FactionPlayerHandler.getOpt(player).resolve().flatMap(fp -> {
            PlayerMinionController controller = MinionWorldData.getData(player.server).getOrCreateController(fp);
            Collection<Integer> ids = controller.getCallableMinions();
            if (ids.size() > 0) {
                List<Pair<Integer, Component>> minions = new ArrayList<>(ids.size());
                ids.forEach(id -> controller.contactMinionData(id, data -> data.getFormattedName().copy()).ifPresent(n -> minions.add(Pair.of(id, n))));
                return Optional.of(new SRequestMinionSelectPacket(action, minions));
            } else {
                CSelectMinionTaskPacket.printRecoveringMinions(player, controller.getRecoveringMinionNames());
            }
            return Optional.empty();
        });
    }

    public static void handle(final SRequestMinionSelectPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> VampirismMod.proxy.handleRequestMinionSelect(msg.action, msg.minions));
        ctx.setPacketHandled(true);
    }

    static void encode(SRequestMinionSelectPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.action.ordinal());
        buf.writeVarInt(msg.minions.size());
        for (Pair<Integer, Component> minion : msg.minions) {
            buf.writeVarInt(minion.getLeft());
            buf.writeComponent(minion.getRight());
        }

    }

    static SRequestMinionSelectPacket decode(FriendlyByteBuf buf) {
        Action a = Action.values()[buf.readVarInt()];
        int count = buf.readVarInt();
        List<Pair<Integer, Component>> minions = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            minions.add(Pair.of(buf.readVarInt(), buf.readComponent()));
        }
        return new SRequestMinionSelectPacket(a, minions);
    }

    public final List<Pair<Integer, Component>> minions;
    public final Action action;

    public SRequestMinionSelectPacket(Action action, List<Pair<Integer, Component>> minions) {
        this.action = action;
        this.minions = minions;
    }

    public enum Action {
        CALL
    }
}
