package com.lizminecraft.shoppingplugin;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;


public class ShopInventoryListener implements Listener {
	

	List<Integer> validPurchases = Arrays.asList(0,2,5,7,9,11,14,16 );
	
	@EventHandler
	public void inventoryInteract(InventoryClickEvent e) {
		//FIRES IF IT IS THE CREATOR OF THE INVENTORY
		if(e.getClickedInventory().getLocation() == null) {
			return;
		}
		if (e.getClickedInventory().getItem(26) != null && e.getClickedInventory().getItem(26).getType().equals(Material.SHULKER_BOX)) {
			Player player = (Player) e.getWhoClicked();
			String shopOwnerName = e.getClickedInventory().getItem(18).getItemMeta().getDisplayName();
			if (player.getName().equals(shopOwnerName)) {
				if (e.getSlot() < 18) {
					//THEY CAN CHANGE ANYTHING HERE
				} else if (e.getSlot() == 26) {
					player.sendMessage(ChatColor.RED + "Please be aware that the shop will not be accessible if you don't put this back after use");
				} else {
					e.setCancelled(true);
				}
			} else {
				InventoryAction act = e.getAction();
				if (e.getClick() == ClickType.RIGHT) {
					e.setCancelled(true);
					if (e.getSlot() < 18) {
						//THE EVEN SLOTS ARE THE ITEMS/THE ODD SLOTS ARE THE PAYMENTS
						if (validPurchases.contains(e.getSlot()) ) {
							if (e.getCurrentItem() != null) {
								e.setCancelled(true);
								int slot = e.getSlot();
								ItemStack item = e.getCurrentItem();
								ItemStack toGive = new ItemStack(item.getType(), 1);
								ItemStack putBack = new ItemStack(item.getType(), item.getAmount() - 1);

								int paymentIndex = slot + 1;
								ItemStack payment = e.getClickedInventory().getItem(paymentIndex);
								if (player.getInventory().contains(payment)) {
									player.getInventory().remove(payment);
									player.getInventory().addItem(toGive);
									e.getClickedInventory().setItem(slot, putBack);
									//ADDING TO SHULKER
									net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand());
					                NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
					                 //This is the list of Items inside our shulkerbox
					                NBTTagList payments = new NBTTagList();
					                NBTTagCompound paymentAsNMS = getTag(payment);
					                payments.add(paymentAsNMS);
					                NBTTagCompound shulkerContents = compound.getCompound("BlockEntityTag");
					                shulkerContents.set("Items", payments);
					                nmsStack.setTag(compound);
					                e.getInventory().setItem(26, CraftItemStack.asBukkitCopy(nmsStack));
								} else {
									player.sendMessage(ChatColor.RED
											+ "You must have the exact amount of the payment in order to buy this");
								}
							}
						} else {
							player.sendMessage(
									ChatColor.RED + "This is the payment for another item! It cannot be purchased!");
						}
					}
				}else if (act.equals(InventoryAction.PLACE_ALL) || act.equals(InventoryAction.PLACE_SOME) || act.equals(InventoryAction.PLACE_ONE)) {
					e.setCancelled(true);
				}else if(act.equals(InventoryAction.DROP_ALL_CURSOR) ||act.equals(InventoryAction.DROP_ONE_SLOT) ||act.equals(InventoryAction.COLLECT_TO_CURSOR)) {
					e.setCancelled(true);
				}else if(act.equals(InventoryAction.DROP_ALL_SLOT)) {
					e.setCancelled(true);
				}
			} 
		}
	}
	//CANCELLING OTHER EVENTS
	public void onDrag(InventoryDragEvent e) {
		if(e.getInventory().getItem(26).getType().equals(Material.SHULKER_BOX)) {
			e.setCancelled(true);
		}
	}
	public void onAddItem(InventoryMoveItemEvent e) {
		
		if (e.getSource().getHolder() instanceof Player) {
			Player player = (Player) e.getSource().getHolder();
			Inventory to = e.getDestination();
			if (to.getHolder() instanceof Chest) {
				if (to.getItem(26) != null) {
					if (to.getItem(26).getType().equals(Material.ENDER_EYE)) {
						if (!(to.getItem(18).getItemMeta().getDisplayName().equals(player.getName()))){
							e.setCancelled(true);
						}
					}
				}
			} 
		}
	}
	//GETTING THE NMS COMPOUND FOR THE PAYMENT
	private NBTTagCompound getTag(ItemStack stack) {
		net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		NBTTagCompound tag = null;
		if(!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if(tag == null) {tag = nmsStack.getTag();}
		return tag;
	}

}
