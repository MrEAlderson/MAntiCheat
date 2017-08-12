package de.marcely.manticheat.nms;

import org.bukkit.Bukkit;

import de.marcely.manticheat.MAntiCheat;
import de.marcely.manticheat.util.Console;
import lombok.Getter;

public enum Version {
	
	R1_12(12, 1, 10);
	
	public static Version version = null;
	
	@Getter private int versionNumber, versionNumber2, id;
	
	private Version(int number, int number2, int id){
		this.versionNumber = number;
		this.versionNumber2 = number2;
		this.id = id;
	}
	
	public static boolean onEnable(){
		final String PATH = Bukkit.getServer().getClass().getPackage().getName();
		final String VERSION = PATH.substring(PATH.lastIndexOf(".")+1, PATH.length());
		String[] vs = VERSION.split("_");
		
		String bwVersionName = vs[2] + "_" + vs[1];
		
		try{
			version = valueOf(bwVersionName);
			return true;
		}catch(IllegalArgumentException e){
			Console.printWarn("IMPORTANT: Failed to work your version. (" + VERSION + ")! Unsupported?");
			Console.printWarn("Stopping the plugin...");
			Bukkit.getPluginManager().disablePlugin(MAntiCheat.plugin);
			return false;
		}
	}
}
