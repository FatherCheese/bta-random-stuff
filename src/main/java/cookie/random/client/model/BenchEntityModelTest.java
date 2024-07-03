package cookie.random.client.model;

import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.model.entity.BenchEntityModel;
import org.useless.dragonfly.model.entity.processor.BenchEntityBones;
import org.useless.dragonfly.utilities.vector.Vector3f;

public class BenchEntityModelTest extends BenchEntityModel {

	private void convertWithMoreParent(BenchEntityBones parentBone, float scale) {
		//don't forget some parent has more parent
		if (parentBone.getParent() != null) {
			convertWithMoreParent(getIndexBones().get(parentBone.getParent()), scale);
		}
		GL11.glTranslatef(convertPivot(parentBone, 0) * scale, convertPivot(parentBone, 1) * scale, convertPivot(parentBone, 2) * scale);


		if (parentBone.rotationPointX != 0.0f || parentBone.rotationPointY != 0.0f || parentBone.rotationPointZ != 0.0f) {
			GL11.glTranslatef(parentBone.rotationPointX * scale, parentBone.rotationPointY * scale, parentBone.rotationPointZ * scale);
		}
		if (parentBone.getRotation() != null) {
			GL11.glRotatef(parentBone.getRotation().x, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(parentBone.getRotation().y, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(parentBone.getRotation().z, 0.0f, 0.0f, 1.0f);
		}

		if (parentBone.rotateAngleZ != 0.0f) {
			GL11.glRotatef((float) Math.toDegrees(parentBone.rotateAngleZ), 0.0f, 0.0f, 1.0f);
		}
		if (parentBone.rotateAngleY != 0.0f) {
			GL11.glRotatef((float) Math.toDegrees(parentBone.rotateAngleY), 0.0f, 1.0f, 0.0f);
		}
		if (parentBone.rotateAngleX != 0.0f) {
			GL11.glRotatef((float) Math.toDegrees(parentBone.rotateAngleX), 1.0f, 0.0f, 0.0f);
		}

		if (parentBone.scaleX != 0.0f || parentBone.scaleY != 0.0f || parentBone.scaleZ != 0.0f) {
			GL11.glScalef(parentBone.scaleX, parentBone.scaleY, parentBone.scaleZ);
		}
	}

	@Override
	public void postRender(BenchEntityBones bones, float scale) {
		Vector3f rotation = bones.getRotation();
		//parent time before rotate itself
		if (bones.getParent() != null) {
			BenchEntityBones parentBone = this.getIndexBones().get(bones.getParent());

			convertWithMoreParent(parentBone, scale);
		}

		GL11.glTranslatef(convertPivot(bones, 0) * scale, convertPivot(bones, 1) * scale, convertPivot(bones, 2) * scale);

		if (bones.rotationPointX != 0.0f || bones.rotationPointY != 0.0f || bones.rotationPointZ != 0.0f) {
			GL11.glTranslatef(bones.rotationPointX * scale, bones.rotationPointY * scale, bones.rotationPointZ * scale);
		}
		if (rotation != null) {
			GL11.glRotatef(rotation.x, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(rotation.y, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(rotation.z, 0.0f, 0.0f, 1.0f);
		}
		if (bones.rotateAngleY != 0.0f) {
			GL11.glRotatef((float) Math.toDegrees(bones.rotateAngleY), 0.0f, 1.0f, 0.0f);
		}
		if (bones.rotateAngleX != 0.0f) {
			GL11.glRotatef((float) Math.toDegrees(bones.rotateAngleX) + 180.0F, 1.0f, 0.0f, 0.0f);
		}
		if (bones.rotateAngleZ != 0.0f) {
			GL11.glRotatef((float) Math.toDegrees(-bones.rotateAngleZ) + 170.0F, 0.0f, 0.0f, 1.0f);
		}

		if (bones.scaleX != 0.0f || bones.scaleY != 0.0f || bones.scaleZ != 0.0f) {
			GL11.glScalef(bones.scaleX, bones.scaleY, -bones.scaleZ);
		}
	}
}
