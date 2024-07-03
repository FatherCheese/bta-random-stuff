package cookie.random;

import cookie.random.client.model.PigmanModelVanilla;
import cookie.random.client.model.PigmanRenderer;
import cookie.random.core.block.RSBlocks;
import cookie.random.client.model.BuilderRenderer;
import cookie.random.core.entity.EntityPigman;
import cookie.random.core.entity.EntityWaypoint;
import cookie.random.core.entity.EntityBuilder;
import cookie.random.client.model.WaypointRenderer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class RandomStuff implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "random";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("Random Stuff initialized. Have fun experimenting!");
    }

	@Override
	public void beforeGameStart() {
		RSBlocks.initializeBlocks();
		EntityHelper.createEntity(EntityBuilder.class, 90, "Builder", BuilderRenderer::new);
		EntityHelper.createEntity(EntityWaypoint.class, 91, "Waypoint", WaypointRenderer::new);
		EntityHelper.createEntity(EntityPigman.class, 92, "pigman", () -> new PigmanRenderer(new PigmanModelVanilla(0.0F), 0.5F));
	}

	@Override
	public void afterGameStart() {

	}
}
