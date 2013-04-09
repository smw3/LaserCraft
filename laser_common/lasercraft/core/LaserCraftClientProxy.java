package lasercraft.core;

import java.io.File;
import java.io.IOException;


import net.minecraftforge.client.MinecraftForgeClient;

public class LaserCraftClientProxy extends LaserCraftProxy {

	public LaserCraftClientProxy() {
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public void registerRenderers() {
            MinecraftForgeClient.preloadTexture(BLOCK_PNG);
    }
}
