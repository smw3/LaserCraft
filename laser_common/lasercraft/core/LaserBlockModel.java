package lasercraft.core;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LaserBlockModel extends ModelBase {
	// fields
    ModelRenderer Base;
    ModelRenderer Base2;
    ModelRenderer StrutLeft;
    ModelRenderer StrutRight;
    ModelRenderer Body;
    ModelRenderer BodyStrut;
    ModelRenderer Front;

	public LaserBlockModel() {
	    textureWidth = 128;
	    textureHeight = 128;
	    
	      Base = new ModelRenderer(this, 0, 0);
	      Base.addBox(-8F, 7F, -8F, 16, 1, 16);
	      Base.setRotationPoint(0F, 16F, 0F);
	      Base.setTextureSize(64, 32);
	      Base.mirror = true;
	      setRotation(Base, 0F, 0F, 0F);
	      Base2 = new ModelRenderer(this, 0, 0);
	      Base2.addBox(-6F, 6F, -6F, 12, 1, 12);
	      Base2.setRotationPoint(0F, 16F, 0F);
	      Base2.setTextureSize(64, 32);
	      Base2.mirror = true;
	      setRotation(Base2, 0F, 0F, 0F);
	      StrutLeft = new ModelRenderer(this, 0, 0);
	      StrutLeft.addBox(-5F, -4F, -3F, 1, 10, 6);
	      StrutLeft.setRotationPoint(0F, 16F, 0F);
	      StrutLeft.setTextureSize(64, 32);
	      StrutLeft.mirror = true;
	      setRotation(StrutLeft, 0F, 0F, 0F);
	      StrutRight = new ModelRenderer(this, 0, 0);
	      StrutRight.addBox(4F, -4F, -3F, 1, 10, 6);
	      StrutRight.setRotationPoint(0F, 16F, 0F);
	      StrutRight.setTextureSize(64, 32);
	      StrutRight.mirror = true;
	      setRotation(StrutRight, 0F, 0F, 0F);
	      Body = new ModelRenderer(this, 0, 0);
	      Body.addBox(-2F, -2F, -5F, 4, 4, 10);
	      Body.setRotationPoint(0F, 16F, 0F);
	      Body.setTextureSize(128, 128);
	      Body.mirror = true;
	      setRotation(Body, 0F, 0F, 0F);
	      BodyStrut = new ModelRenderer(this, 0, 0);
	      BodyStrut.addBox(-4F, -1F, -1F, 8, 2, 2);
	      BodyStrut.setRotationPoint(0F, 16F, 0F);
	      BodyStrut.setTextureSize(128, 128);
	      BodyStrut.mirror = true;
	      setRotation(BodyStrut, 0F, 0F, 0F);
	      Front = new ModelRenderer(this, 0, 0);
	      Front.addBox(-1F, -1F, 5F, 2, 2, 3);
	      Front.setRotationPoint(0F, 16F, 0F);
	      Front.setTextureSize(128, 128);
	      Front.mirror = true;
	      setRotation(Front, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(entity, f, f1, f2, f3, f4, f5);
	    Base.render(f5);
	    Base2.render(f5);
	    StrutLeft.render(f5);
	    StrutRight.render(f5);
	    Body.render(f5);
	    BodyStrut.render(f5);
	    Front.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(Entity entity, float f, float f1, float f2,
			float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	public void renderModel(LaserBlockTileEntity entity,float f) {
		setRotation(Base2,0f,entity.getYaw() + (float)Math.PI/2,0f);
		setRotation(StrutRight,0f,entity.getYaw() + (float)Math.PI/2,0f);
		setRotation(StrutLeft,0f,entity.getYaw() + (float)Math.PI/2,0f);
		
		setRotation(BodyStrut,entity.getPitch(),entity.getYaw() + (float)Math.PI/2,0f);
		setRotation(Body,entity.getPitch(),entity.getYaw() + (float)Math.PI/2,0f);
		setRotation(Front,entity.getPitch(),entity.getYaw() + (float)Math.PI/2,0f);
		//setRotation(Laser,entity.getPitch(),0f,0f);
	    Base.render(f);
	    Base2.render(f);
	    StrutLeft.render(f);
	    StrutRight.render(f);
	    Body.render(f);
	    BodyStrut.render(f);
	    Front.render(f);
	}

}
