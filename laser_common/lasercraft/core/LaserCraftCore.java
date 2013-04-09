package lasercraft.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.command.CommandHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.sheephaven.core.CommandSheepHaven;
import net.sheephaven.core.TestBlock;
import net.sheephaven.core.TestBlockItem;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "LaserCraft", name = "LaserCraft", version = "0.1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)

public class LaserCraftCore extends BaseMod {
	
	public final static Block TestBlock = new TestBlock(501,Material.ground);
	public final static Block LaserBlock = new LaserBlock(503,Material.ground);
	
	public static int LaserBlockModelID;
	
	@SidedProxy(clientSide="net.sheephaven.core.client.ClientProxy",
			serverSide="net.sheephaven.core.SheephavenProxy")
	public static LaserCraftProxy proxy;

	// The instance of your mod that Forge uses.
	@Instance("SheepHavenCore")
	public static LaserCraftCore instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println("preInit Sheephaven");
		// Stub Method
	}

	@Init
	public void load(FMLInitializationEvent event) {
		System.out.println("load Sheephaven");
		
		//registerBlock(net.minecraft.block.Block block, Class<? extends ItemBlock> itemclass, String name)
		// TestBlock
		LanguageRegistry.addName(TestBlock,"TestBlock");
		MinecraftForge.setBlockHarvestLevel(TestBlock,"shovel",0);
		GameRegistry.registerBlock(TestBlock, TestBlockItem.class, "TestBlock");
		
		// Laser
		LaserBlockModelID = ModLoader.getUniqueBlockModelID(this, true);
		//LanguageRegistry.addName(LaserBlock,"LaserBlock");
		ModLoader.registerTileEntity(LaserBlockTileEntity.class, "LaserBlock",new LaserBlockTileEntityRenderer() );
		GameRegistry.registerBlock(LaserBlock,LaserBlockItem.class,"LaserBlock");
		//GameRegistry.registerBlock(LaserBlock);
		RenderingRegistry.registerBlockHandler(new LaserBlockItemRenderer());
		
		proxy.registerRenderers();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println("postInit Sheephaven");
		// Stub Method
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
		System.out.println("ServerStarting Sheephaven");
		CommandHandler commandManager = (CommandHandler) event.getServer()
				.getCommandManager();
		commandManager.registerCommand(new CommandSheepHaven());
	}

	public void renderInvBlock(RenderBlocks var1, Block var2, int var3, int var4){
    	super.renderInvBlock(var1, var2, var3, var4);
    
    	if (var2 == LaserBlock) {
    	    LaserBlockTileEntityRenderer.Instance.renderInventoryModelAt(new LaserBlockTileEntity(), 0.0D, 0.0D, 0.0D, 0.0F);
    	}
	}
	
	@Override
	public String getVersion() {
	    Mod A = LaserCraftCore.class.getAnnotation(Mod.class);
		return A.version();
	}

	@Override
	public void load() {
		// ?? Required by basemod class
	}
}
