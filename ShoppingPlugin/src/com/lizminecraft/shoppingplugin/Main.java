package com.lizminecraft.shoppingplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	//THE MATERIAL A CHEST MUST BE PLACED ON IN ORDER FOR IT TO BECOME A SHOP
	public Material currentShopMakerBlock;
	@Override
	public void onEnable() {
		
		//REGISTER EVENT LISTENERS
		Bukkit.getPluginManager().registerEvents(new CreateShop(this), this);
		Bukkit.getPluginManager().registerEvents(new ShopInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
		//REGISTER COMMAND
		getCommand("ChestShop").setExecutor(new ChestShopCommand(this));
		//CONFIG THINGS
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		//INITIALISE THE MATERIAL
		Material tryMaterial = getMaterial();
		if(tryMaterial == null) {
			this.currentShopMakerBlock = Material.QUARTZ_BLOCK;
		}else {
			this.currentShopMakerBlock = tryMaterial;
		}
	
	}
	
	@Override
	public void onDisable() {
		//SET THE MATERIAL IN THE CONFIG IN CASE IT HAS BEEN CHANGED
		writeMaterial();
	}
	
	public void setMaterial(Material material) {
		this.currentShopMakerBlock = material;
	}
	
	private Material getMaterial() {
		String material = getConfig().getString("material");
		return Material.getMaterial(material);
	}
	
	private void writeMaterial() {
		getConfig().set("material", currentShopMakerBlock.toString());
	}
}
