package net.sheephaven.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TestBlockItem extends ItemBlock {

    public TestBlockItem(int par1) {
        super(par1);
    }
    
    public String getItemNameIS(ItemStack itemstack) {
        return "TestBlock Item";
    }
    
    public int getMetadata(int par1) {
        return par1;
    }

}
