package com.example.examplemod;

import com.example.examplemod.mc_01_myblock.BlockMyBlock;
import com.example.examplemod.mc_03_magicstick.ItemMagicStick;
import com.example.examplemod.mc_15_tobisuke.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod {

    //MODID
    public static final String MODID = "examplemod";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final EntityType<EntityTobisuke> ENTITY_TOBISUKE =
            EntityType.Builder
                    .of(EntityTobisuke::new, MobCategory.CREATURE)
                    .sized(0.9f, 0.9f)
                    .setTrackingRange(32)
                    .setUpdateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("tobisuke");

    // Block
    public static final Block BLOCK_MYBLOCK =
            new BlockMyBlock().setRegistryName(MODID, "block_myblock");

    public static final Item ITEM_MAGIC_STICK =
            new ItemMagicStick().setRegistryName(MODID, "magic_stick");

    public static final Item TOBISUKE_SPAWN_EGG =
            new SpawnEggItem(ENTITY_TOBISUKE,
                    0xFF0000,
                    0x00FF00,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
            ).setRegistryName(MODID, "tobisuke_spawn_egg");

    public ExampleMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {});
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        EntityRenderers.register(ENTITY_TOBISUKE, RenderTobisuke::new);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        private static final RegisterBlockData[] registerBlocks = {
                // ここにBlockを書いてね！
                new RegisterBlockData(BLOCK_MYBLOCK),
        };

        private static final Item[] registerItems = {
                ITEM_MAGIC_STICK,
                TOBISUKE_SPAWN_EGG,
        };

        @SubscribeEvent
        public static void registerBiomes(RegistryEvent.Register<Biome> event) {
            IForgeRegistry<Biome> registry = event.getRegistry();
        }

        @SubscribeEvent
        public static void onAttributeCreation(final EntityAttributeCreationEvent event) {
            event.put(ENTITY_TOBISUKE, EntityTobisuke.registerAttributes().build());
        }

        @SubscribeEvent
        public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(ENTITY_TOBISUKE.setRegistryName(MODID, "tobisuke"));
            ForgeHooksClient.registerLayerDefinition(RenderTobisuke.modelLayerLocation, ModelTobisuke::createLayer);
        }


        // ======================================================================================================
        // ここから下はいじらないよ！
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            LOGGER.info("HELLO from Register Block");
            for (RegisterBlockData data : registerBlocks) {
                event.getRegistry().register(data.block);
            }
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            for (RegisterBlockData data : registerBlocks) {
                event.getRegistry().register(new BlockItem(data.block, new Item.Properties().tab(data.creativeModeTab)).setRegistryName(data.block.getRegistryName()));
            }

            for (Item item : registerItems) {
                event.getRegistry().register(item);
            }
        }


        static class RegisterBlockData {
            Block block;
            CreativeModeTab creativeModeTab;

            public RegisterBlockData(Block block) {
                this.block = block;
                creativeModeTab = CreativeModeTab.TAB_BUILDING_BLOCKS;
            }

            public RegisterBlockData(Block block, CreativeModeTab creativeModeTab) {
                this.block = block;
                this.creativeModeTab = creativeModeTab;
            }
        }
    }
}
