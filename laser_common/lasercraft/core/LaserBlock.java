package lasercraft.core;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LaserBlock extends BlockContainer {
	
	LaserBlockTileEntity TE = null;
	
	public LaserBlock(int par1, Material par3Material) {
		super(par1, par3Material);
		
		setHardness(2.0F);
		setStepSound(Block.soundGlassFootstep);
		setUnlocalizedName("LaserBlock");
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	public int damageDropped(int par1) {
	    return par1;
	}
	
	public int getRenderType() {
		return LaserCraftCore.LaserBlockModelID;
	}

	public boolean isOpaqueCube() {
		return false;
	} 
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public boolean blockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer) {

		int p = MathHelper
				.floor_double((double) ((par5EntityPlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;

		byte byte0 = 3;

		if (p == 0) {
			byte0 = 4;
		}
		if (p == 1) {
			byte0 = 3;
		}
		if (p == 2) {
			byte0 = 2;
		}
		if (p == 3) {
			byte0 = 1;
		}
		// x, y, z, metadata, flag. See setBlock for flag description
		par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0, 2);

		return true;
	}
	
	public TileEntity getBlockEntity() {
		// was: return new TestSpecialBlockTileEntity(); No difference
		if (TE != null) return TE;
		return createNewTileEntity(null);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new LaserBlockTileEntity();
	}

}
