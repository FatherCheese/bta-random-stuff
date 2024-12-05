package cookie.random.extra.mixin;

import cookie.random.RandomStuff;
import net.minecraft.client.render.stitcher.AtlasStitcher;
import net.minecraft.client.render.stitcher.TextureRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.render.stitcher.TextureRegistry.*;

@Mixin(value = TextureRegistry.class, remap = false)
public abstract class TextureRegistryMixin {

	@Shadow
	public static AtlasStitcher artAtlas;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void random_staticInit(CallbackInfo ci) {
		try {
			for (AtlasStitcher stitcher : stitcherMap.values()) {
				initializeAllFiles("random", stitcher, stitcher != artAtlas);
			}
		} catch (Exception e) {
			RandomStuff.LOGGER.error(e.getMessage(), e);
		}
	}
}
