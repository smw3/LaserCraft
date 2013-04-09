package net.sheephaven.core;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TestBlock extends Block {
	public TestBlock(int par1, Material par3Material) {
		super(par1, par3Material);
		
		setHardness(2.0F);
		setStepSound(Block.soundGlassFootstep);
		setUnlocalizedName("TestBlock");
		setCreativeTab(CreativeTabs.tabBlock);
	}
}
