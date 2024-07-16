package cookie.random.extra.mixin;

import cookie.random.client.renderer.block.RawQuartzTexture;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = RenderEngine.class, remap = false)
public abstract class RenderEngineMixin {

	@Shadow
	private List<DynamicTexture> dynamicTextures;

	@Inject(method = "initDynamicTextures", at = @At(value = "TAIL"))
	private void random_initDynamicTextures(List<Throwable> errors, CallbackInfo ci) {
		dynamicTextures.add(new RawQuartzTexture());
	}
}
