package lasercraft.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LaserCommandBlock extends BlockContainer {
    
    TileEntity TE = null;
    
    protected LaserCommandBlock(int par1, Material par2Material) {
        super(par1, par2Material);
        
        setHardness(2.0F);
        setStepSound(Block.soundGlassFootstep);
        setUnlocalizedName("LaserCommandBlock");
        setCreativeTab(CreativeTabs.tabBlock);
    }

    public TileEntity getBlockEntity() {
        // was: return new TestSpecialBlockTileEntity(); No difference
        if (TE != null) return TE;
        return createNewTileEntity(null);
    }
    
    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new LaserCommandBlockTileEntity();
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                    EntityPlayer player, int idk, float what, float these, float are) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if (tileEntity == null || player.isSneaking()) {
                    return false;
            }
            //code to open gui explained later
            player.openGui(LaserCraftCore.instance, 0, world, x, y, z);
            return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
            //TODO: drop contents dropItems(world, x, y, z);
            super.breakBlock(world, x, y, z, par5, par6);
    }
}
