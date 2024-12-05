package cookie.random.client.renderer.entity;

import org.useless.dragonfly.model.entity.BenchEntityModel;

public class CogModel extends BenchEntityModel {

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		this.getIndexBones().forEach((s, benchEntityBones) -> benchEntityBones.resetPose());
	}
}
