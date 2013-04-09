package lasercraft.core;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;

public class LaserBlockItemRenderer implements ISimpleBlockRenderingHandler{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID,
            RenderBlocks renderer) {
        System.out.println(block.toString());
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        fontRenderer.drawStringWithShadow("Test", 1, 1, 0xFFFFFF);
        
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
            Block block, int modelId, RenderBlocks renderer) {
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return 0;
    }

}
