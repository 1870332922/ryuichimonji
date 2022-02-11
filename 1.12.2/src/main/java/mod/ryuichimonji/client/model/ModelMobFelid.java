package mod.ryuichimonji.client.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelMobFelid extends ModelBiped {
	private final ModelRenderer head;
	private final ModelRenderer ear;
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftLeg;
	private final ModelRenderer rightLeg;

	public ModelMobFelid() {
		textureWidth = 64;
		textureHeight = 32;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, true));
		head.cubeList.add(new ModelBox(head, 32, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.1F, false));
		
		this.bipedHead = head;

		ear = new ModelRenderer(this);
		ear.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(ear);
		ear.cubeList.add(new ModelBox(ear, 0, 0, -4.0F, -10.0F, -1.0F, 2, 2, 1, 0.0F, false));
		ear.cubeList.add(new ModelBox(ear, 0, 4, 2.0F, -10.0F, -1.0F, 2, 2, 1, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 16, 16, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F, true));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 0.0F, 0.0F);
		tail.rotateAngleX = -0.7854F;
		//setRotationAngle(tail, -0.7854F, 0.0F, 0.0F);
		body.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 56, 16, -1.0F, -3.0F, 8.0F, 2, 10, 2, 0.0F, false));

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		leftArm.cubeList.add(new ModelBox(leftArm, 40, 16, -3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, false));
		
		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		rightArm.cubeList.add(new ModelBox(rightArm, 40, 16, -1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F, true));

		this.bipedLeftArm = rightArm;
		this.bipedRightArm = leftArm;
		
		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 24, 0, -2.0F, 12.0F, -2.0F, 4, 0, 4, 0.0F, false));

		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 16, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 24, 0, -2.0F, 12.0F, -2.0F, 4, 0, 4, 0.0F, true));
		
		this.bipedLeftLeg = rightLeg;
		this.bipedRightLeg = leftLeg;
		
		this.bipedBody = body;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		head.render(f5);
		body.render(f5);
		leftArm.render(f5);
		rightArm.render(f5);
		leftLeg.render(f5);
		rightLeg.render(f5);
		tail.renderWithRotation(f5);
	}
	public void setRotationAngle(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		
		this.head.rotateAngleX = (float) (Math.PI / 180) * headPitch;
		this.head.rotateAngleY = (float) (Math.PI / 180) * netHeadYaw;
		
	}

}
