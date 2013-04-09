package lasercraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LaserBlockItem extends ItemBlock {

	public LaserBlockItem(int par1) {
		super(par1);
	}
	
	public String getItemNameIS(ItemStack itemstack) {
		return "LaserBlock Item";
	}
	
	public int getMetadata(int par1) {
		return par1;
	}

}
