package cookie.random;

import cookie.random.client.model.newentity.NewChickenRenderer;
import cookie.random.core.block.RSBlocks;
import cookie.random.core.newentity.NewMob;
import cookie.random.core.newentity.NewMobs;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.model.ModelChicken;
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
		NewMobs.initializeNewMobs();
		EntityHelper.createEntity(NewMobs.CHICKEN.getClass(), 100, NewMobs.CHICKEN.name, () -> new NewChickenRenderer(new ModelChicken()));
	}

	@Override
	public void afterGameStart() {

	}
}
