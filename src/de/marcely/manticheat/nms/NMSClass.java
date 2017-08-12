package de.marcely.manticheat.nms;

import org.bukkit.Bukkit;

public class NMSClass {
	
	public static Class<?> CRAFTPLAYER, PACKETPLAYINUSEENTITY, PACKETPLAYINFLYING;
	
	static {
		final String PATH = Bukkit.getServer().getClass().getPackage().getName();
		final String VERSION = PATH.substring(PATH.lastIndexOf(".")+1, PATH.length());
        
		try{
			
			CRAFTPLAYER = Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftPlayer");
			PACKETPLAYINUSEENTITY = Class.forName("net.minecraft.server." + VERSION + ".PacketPlayInUseEntity");
			PACKETPLAYINFLYING = Class.forName("net.minecraft.server." + VERSION + ".PacketPlayInFlying");
			
		}catch(ClassNotFoundException | IllegalArgumentException | SecurityException e){
			e.printStackTrace();
		}
	}
}
