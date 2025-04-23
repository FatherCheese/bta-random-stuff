package cookie.random.extra;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class RandomStuffClient implements ClientModInitializer, ClientStartEntrypoint {
	public static final String MOD_ID = "random|client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Random Stuff initialized for the client.");
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {

	}
}
