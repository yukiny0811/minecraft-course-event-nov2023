package com.example.examplemod.mc_03_magicstick;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ItemMagicStick extends Item {
    public ItemMagicStick() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }
}
