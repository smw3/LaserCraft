package lasercraft.core;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LaserCommandBlockItem extends ItemBlock {

    public LaserCommandBlockItem(int par1) {
        super(par1);
    }
    
    public String getItemNameIS(ItemStack itemstack) {
        return "LaserCommandBlock Item";
    }
    
    public int getMetadata(int par1) {
        return par1;
    }

}