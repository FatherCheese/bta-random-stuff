package cookie.random.client.model;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;
import org.lwjgl.opengl.GL11;

public class PigmanModelVanilla extends ModelBiped {
	public Cube snout;

	public PigmanModelVanilla(float f, float f1) {
		super(f, f1);
		snout = new Cube(24, 4);
		snout.addBox(-2.0F, -4.0F, -5.0F, 4, 3, 1);
		snout.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
	}

	public PigmanModelVanilla(float f) {
		this(f, 0.0f);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		GL11.glPushMatrix();
		snout.render(scale);
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		snout.rotateAngleY = headYaw / (float) (180.0 / Math.PI);
		snout.rotateAngleX = headPitch / (float) (180.0 / Math.PI);
	}
}
