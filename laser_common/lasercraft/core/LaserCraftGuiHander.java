package lasercraft.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class LaserCraftGuiHander implements IGuiHandler {
    // returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
            int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof LaserCommandBlockTileEntity) { return new LaserCommandBlockContainer(
                player.inventory, (LaserCommandBlockTileEntity) tileEntity); }
        return null;
    }

    // returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
            int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof LaserCommandBlockTileEntity) { return new LaserCommandBlockGui(
                player.inventory, (LaserCommandBlockTileEntity) tileEntity); }
        return null;

    }

}
