package mod.ryuichimonji.client.renderer;

import org.lwjgl.opengl.GL11;

import mod.ryuichimonji.entity.EntityDriveEievui;
import mod.ryuichimonji.entity.EntityDriveEievuiEX;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntityDriveEievuiEX extends Render{
	
	private static double[][] dVec ={ 
			{  0.00,  1.00, -0.50},
	        {  0.00,  0.75,  0.00},
	        {  0.10,  0.60, -0.15},
	        {  0.00,  0.50, -0.25},
	        { -0.10,  0.60, -0.15},
	        {  0.00,  0.00,  0.25},
	        {  0.25,  0.00,  0.00},
	        {  0.00,  0.00, -0.25},
	        { -0.25,  0.00,  0.00},
	        {  0.00, -0.75,  0.00},
	        {  0.10, -0.60, -0.15},
	        {  0.00, -0.50, -0.25},
	        { -0.10, -0.60, -0.15},
	        {  0.00, -1.00, -0.50},
		};

	private static int[] nVecPos = {
			0,  1,  2,  3,
	         0,  3,  4,  1,
	         1,  5,  6,  2,
	         3,  2,  6,  7,
	         3,  7,  8,  4,
	         1,  4,  8,  5,
	         6,  5,  9, 10,
	         6, 10, 11,  7,
	         8,  7, 11, 12,
	         8, 12,  9,  5,
	        10,  9, 13, 11,
	        12, 11, 13,  9
	};

	public RenderEntityDriveEievuiEX(RenderManager renderManager) {
		super(renderManager);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
		 if (entity instanceof EntityDriveEievuiEX) {
			 doDriveRender((EntityDriveEievuiEX) entity, d0, d1, d2, f, f1);
		 }
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void doDriveRender(EntityDriveEievuiEX entityDrive, double dX, double dY, double dZ, float f, float f1) {

		GL11.glPushAttrib(GL11.GL_CURRENT_BIT |
				  GL11.GL_ENABLE_BIT |
				  GL11.GL_LIGHTING_BIT |
				  GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        
        float lifetime = entityDrive.getLifeTime();
        float ticks = entityDrive.ticksExisted;
        float alpha = (float)Math.pow((lifetime - Math.min(lifetime,ticks)) / lifetime, 2.0);
		
		int color = entityDrive.getColor();
		GL11.glColor4f(((color >> 16) & 0xff)/255.0f,
					   ((color >>  8) & 0xff)/255.0f,
					   ((color      ) & 0xff)/255.0f,
					   alpha);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)dX, (float)dY+0.5f, (float)dZ);
		GL11.glRotatef(entityDrive.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-entityDrive.rotationPitch, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(entityDrive.getRoll(),0,0,1);
        GL11.glScalef(0.25f, 1, 1);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder wr = tessellator.getBuffer();

        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        
        for(int idx = 0; idx < nVecPos.length; idx++) {
        	double[] v = dVec[nVecPos[idx]];
            wr.pos(v[0], v[1], v[2]).endVertex();
        }
        
        tessellator.draw();

        GL11.glPopMatrix();
        GL11.glPopAttrib();

	}
	
}
