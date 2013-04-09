package lasercraft.core;


import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class LaserBlockTileEntityRenderer extends
		TileEntitySpecialRenderer {

	private LaserBlockModel model;

	public static LaserBlockTileEntityRenderer Instance = new LaserBlockTileEntityRenderer(); // For inventory rendering
	
	public LaserBlockTileEntityRenderer() {
		model = new LaserBlockModel();
	}
	/**
	 * Called by the inventory rendering
	 */
	public void renderInventoryModelAt(LaserBlockTileEntity tile, double d,
            double d1, double d2, float f) {
	    Minecraft.getMinecraft().renderEngine.bindTexture("/blocks/laser.png");
	    renderLaserModel(tile, d, d1, d2);
	    
	}
    /**
     * Ingame rendering
     */ 
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1,
            double d2, float f) {
        renderModelAt((LaserBlockTileEntity) tileentity, d, d1, d2, f); // where to
                                                                        // render
    }
    /**
     * Ingame rendering
     */ 
	public void renderModelAt(LaserBlockTileEntity tile, double x,
			double y, double z, float f) {
		
	    this.bindTextureByName("/blocks/laser.png");
		renderLaserModel(tile,x,y,z);
		
		renderLaser(x,y,z,tile.getLaserLength(),tile.getLaserNuzzle(),tile.getYaw(),tile.getPitch());

	}
	

    /**
     * Renders the (untextured) Laser Model. Used by both ingame and inventory rendering
     */
    private void renderLaserModel(LaserBlockTileEntity tile, double x, double y, double z) {
        GL11.glPushMatrix(); // start
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F,
                (float) z + 0.5F); // size
        GL11.glRotatef(0, 0.0F, 1.0F, 0.0F); // change the first 0 in like 90 to
                                                // make it rotate 90 degrees.
        GL11.glScalef(1.0F, -1F, -1F); // to make your block have a normal
                                        // positioning. comment out to see what
                                        // happens
        model.renderModel(tile,0.0625F); // renders and 0.0625F is exactly 1/16.
                                    // every block has 16 entitys/pixels. if you
                                    // make the number 1, every pixel will be as
                                    // big as a block. make it 0.03125 and your
                                    // block will be half as big as a normal
                                    // one.
        GL11.glPopMatrix(); // end  
    }
    /**
     * Generates geometry for the laser and renders it 
     */
    private void renderLaser(double x, double y, double z, double laserLength, Vec3 LaserNuzzle, double Yaw, double Pitch) {
        this.bindTextureByName("/effects/laser_red.png");
        
        Tessellator tessellator = Tessellator.instance;
        
        // I have no idea what I'm doing.
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        //GL11.glDisable(GL11.GL_BLEND);
        //GL11.glDepthMask(true);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        
        double laserWidth = 0.05;        
        
        double u1,v1,u2,v2;
        double x1,y1,z1,x2,y2,z2;
        
        double u3,v3,u4,v4;
        double x3,y3,z3,x4,y4,z4;
        
        u1 = 0.0;
        v1 = 1.0;
        
        x1 = x+LaserNuzzle.xCoord + Math.sin(Pitch)*Math.cos(Yaw)*laserWidth;
        y1 = y+LaserNuzzle.yCoord - Math.cos(Pitch)*laserWidth;
        z1 = z+LaserNuzzle.zCoord + Math.sin(Pitch)*Math.sin(Yaw)*laserWidth;
        
        u2 = 0.0;
        v2 = 0.0;
        
        x2 = x1 - 2*Math.sin(Pitch)*Math.cos(Yaw)*laserWidth;
        y2 = y1 + 2*Math.cos(Pitch)*laserWidth;
        z2 = z1 - 2*Math.sin(Pitch)*Math.sin(Yaw)*laserWidth;
        
        u3 = 1.0*laserLength;
        v3 = 1.0;
        
        x3 = x1 + Math.cos(Yaw)*laserLength;
        y3 = y1 + Math.sin(Pitch)*laserLength;
        z3 = z1 + Math.sin(Yaw)*laserLength;
        
        u4 = 1.0*laserLength;
        v4 = 0.0;
        
        x4 = x2 + Math.cos(Yaw)*laserLength;
        y4 = y2 + Math.sin(Pitch)*laserLength;
        z4 = z2 + Math.sin(Yaw)*laserLength;
        
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 32);
        
        tessellator.addVertexWithUV(x2, y2, z2, u2, v2);        
        tessellator.addVertexWithUV(x1, y1, z1, u1, v1);
        tessellator.addVertexWithUV(x3, y3, z3, u3, v3);
        tessellator.addVertexWithUV(x4, y4, z4, u4, v4);

        
        tessellator.draw();
        
        GL11.glEnable(GL11.GL_LIGHTING);
        //GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);        
    }
}
