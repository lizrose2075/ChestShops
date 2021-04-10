package com.lizminecraft.shoppingplugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChestShopCommand implements CommandExecutor {

	private Main main;
	
	public ChestShopCommand(Main main) {
		//THE LIST OF VALID TYPES OF BLOCK, MORE CAN BE ADDED AS LONG AS A CHEST CAN BE PLACE ON IT
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//Material.getmaterial
		
		if (sender.isOp()) {
			if (args[0].equalsIgnoreCase("block")) {
				//CHANGING TO UPPER CASE TO BE CHECK AGAINST THE ENUM "MATERIAL"
				String attemptedMaterialAsString = args[1].toUpperCase();
				Material attemptedMaterial;
				if((Material.getMaterial(attemptedMaterialAsString) != null)) {
					//IF IT IS IN THE ENUM "MATERIAL"
					attemptedMaterial = Material.getMaterial(attemptedMaterialAsString);
					if (attemptedMaterial.isBlock() && attemptedMaterial.isSolid()) {
						main.setMaterial(attemptedMaterial);
						sender.sendMessage("The material required to create a shop has been set to " + attemptedMaterialAsString);
						return false;
					}
				}
			} else {
				sender.sendMessage("You must use the command /ChestShop block <Material>");
			} 
		}
		return false;
	}

}
