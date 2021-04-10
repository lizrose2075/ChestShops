package com.lizminecraft.shoppingplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


public class InventoryClose implements Listener {
	
	@EventHandler
	public void onPaymentChestClose(InventoryCloseEvent e) {
		
		if (!e.getInventory().getHolder().equals(null)) {
			if (e.getInventory().getHolder() instanceof Chest) {
				if ((e.getInventory().getSize() == 27)) {
					if(e.getInventory().getItem(26).equals(null)) {
						return;
					}else {
						//NOTIFYING THE PLAYER ABOUT THE SHULKER BOX
						if(e.getInventory().getItem(26).getType().equals(Material.SHULKER_BOX)) {
							e.getPlayer().sendMessage(ChatColor.YELLOW + "Please make sure you have added only payment and item pairs");
						}
					}
				}
			} 
		}
	}
}
