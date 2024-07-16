package cookie.random;

import cookie.random.core.RSBlocks;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	}

	@Override
	public void afterGameStart() {

	}
}
