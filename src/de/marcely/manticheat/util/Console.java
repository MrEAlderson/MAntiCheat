package de.marcely.manticheat.util;

import org.bukkit.Bukkit;

public class Console {
	public static void printWarn(String warn){
		Bukkit.getLogger().warning("[MAntiCheat] " + warn);
	}
	
	public static void printInfo(String info){
		Bukkit.getLogger().info("[MAntiCheat] " + info);
	}
}
