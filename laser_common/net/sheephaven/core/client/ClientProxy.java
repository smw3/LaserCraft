package net.sheephaven.core.client;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.client.MinecraftForgeClient;
import net.sheephaven.core.SheephavenProxy;

public class ClientProxy extends SheephavenProxy {

	public ClientProxy() {
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public void registerRenderers() {
            MinecraftForgeClient.preloadTexture(BLOCK_PNG);
    }
}
