package de.teamlapen.vampirism.advancements;

import com.google.gson.JsonObject;
import de.teamlapen.vampirism.REFERENCE;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * Collection of several hunter related triggers
 */
public class HunterActionTrigger extends SimpleCriterionTrigger<HunterActionTrigger.Instance> {

    public static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "hunter_action");
    private final static Logger LOGGER = LogManager.getLogger();

    public static Instance builder(Action action) {
        return new Instance(action);
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, Action action) {
        this.trigger(player, (instance) -> instance.test(action));
    }

    @Nonnull
    @Override
    protected Instance createInstance(JsonObject json, @Nonnull EntityPredicate.Composite entityPredicate, @Nonnull DeserializationContext conditionsParser) {
        Action action = Action.NONE;
        if (json.has("action")) {
            String name = json.get("action").getAsString();

            try {
                action = Action.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Action {} does not exist", name);
            }
        } else {
            LOGGER.warn("Action not specified");
        }
        return new Instance(action);
    }

    public enum Action {
        STAKE, NONE
    }

    static class Instance extends AbstractCriterionTriggerInstance {
        @Nonnull
        private final Action action;

        Instance(@Nonnull Action action) {
            super(ID, EntityPredicate.Composite.ANY);
            this.action = action;
        }

        @Nonnull
        @Override
        public JsonObject serializeToJson(@Nonnull SerializationContext serializer) {
            JsonObject json = super.serializeToJson(serializer);
            json.addProperty("action", action.name());
            return json;
        }

        boolean test(Action action) {
            return this.action == action;
        }
    }
}
