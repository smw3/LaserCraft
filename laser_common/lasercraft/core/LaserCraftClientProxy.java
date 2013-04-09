package lasercraft.core;

import java.io.File;
import java.io.IOException;


import net.minecraftforge.client.MinecraftForgeClient;

public class LaserCraftClientProxy extends LaserCraftProxy {

	public LaserCraftClientProxy() {

	}
	
    @Override
    public void registerRenderers() {
            MinecraftForgeClient.preloadTexture(BLOCK_LASER_TEXTURE);
            MinecraftForgeClient.preloadTexture(EFFECT_LASER_RED_TEXTURE);
    }
}
