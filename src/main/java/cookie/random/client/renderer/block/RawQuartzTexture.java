package cookie.random.client.renderer.block;

import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.MathHelper;

import java.awt.*;

public class RawQuartzTexture extends DynamicTexture {
	private float[] arr1;
	private float[] arr2;
	private float[] arr3;
	private float[] arr4;

	@Override
	public void postInit() {
		setTexture(TextureRegistry.getTexture("random:block/raw_quartz"));
		this.arr1 = new float[this.texture.getArea()];
		this.arr2 = new float[this.texture.getArea()];
		this.arr3 = new float[this.texture.getArea()];
		this.arr4 = new float[this.texture.getArea()];
	}

	@Override
	public void update() {
		float hue = (System.currentTimeMillis() % 4000 / 4000F);
		int color = Color.HSBtoRGB(hue, 1.0f, 1.0f) | 0xFF000000;

		float[] var11 = this.arr2;
		this.arr2 = this.arr1;
		this.arr1 = var11;

		for (int i = 0; i < texture.getArea(); i++) {
			float bright = this.arr1[i];
			if (bright > 1.0F) bright = 1.0F;

			if (bright < 0.0F) bright = 0.0F;

			float var13 = bright * bright;
			int r = (int)(color * hue / 2);
			int g = (int)(color * hue / 4);
			int b = (int)(color * hue / 6);
			int a = (int)(146.0F + var13 * 50.0F);
			this.imageData[i * 4] = (byte)r;
			this.imageData[i * 4 + 1] = (byte)g;
			this.imageData[i * 4 + 2] = (byte)b;
			this.imageData[i * 4 + 3] = (byte) a;
		}
	}
}
