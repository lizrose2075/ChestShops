package com.lizminecraft.shoppingplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;


public class CreateShop implements Listener {
	
	private Main main;
	
	public CreateShop(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onCreate(BlockPlaceEvent e) {
		if(e.getBlockPlaced().getType().equals(Material.CHEST)) {
			Location location = e.getBlockPlaced().getLocation();
			BlockState bs = e.getPlayer().getWorld().getBlockAt(location).getState();
			Chest chest = (Chest)bs;
			Location blockUnderneath = location.subtract(0,1,0);
			
			if(blockUnderneath.getBlock().getType().equals(main.currentShopMakerBlock)) {
				//USED TO DECLARE THAT THIS IS A PLAYERS CHEST SHOP!!
				chest.setCustomName("Shop");
				//GIVING THE PLAYER THE PAYMENT ACCESS
				ItemStack shulker = new ItemStack(Material.SHULKER_BOX);
				ItemMeta shulkMeta = shulker.getItemMeta();
				shulkMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Payment Shulker");
				shulker.setItemMeta(shulkMeta);
			
				ItemStack glassFiller = new ItemStack(Material.CYAN_STAINED_GLASS_PANE);
				ItemMeta glassMeta = glassFiller.getItemMeta();
				glassMeta.setDisplayName("just filler...");
				glassFiller.setItemMeta(glassMeta);
				
				ItemStack shopTitle = new ItemStack(Material.ENDER_EYE);
				ItemMeta titleMeta = shopTitle.getItemMeta();
				titleMeta.setDisplayName(e.getPlayer().getName());
				shopTitle.setItemMeta(titleMeta);
				
				chest.getInventory().setItem(18, shopTitle);
				for(int i = 19; i < 26; i++) {
					chest.getInventory().setItem(i, glassFiller);
				}
				chest.getInventory().setItem(4, glassFiller);
				chest.getInventory().setItem(13, shopTitle);
				chest.getInventory().setItem(26, shulker);
				
			}else {
				chest.setCustomName(null);
			}
		}
	}
	
	
	
}
