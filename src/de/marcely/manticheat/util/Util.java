package de.marcely.manticheat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.marcely.manticheat.MAntiCheat;
import de.marcely.manticheat.location.XYZYP;
import de.marcely.manticheat.netty.NettyInjection;
import de.marcely.manticheat.netty.PacketListener;
import lombok.Getter;

public class Util {
	
	public static List<Player> watchingPlayers = new ArrayList<Player>();
	public static HashMap<Player, NettyInjection> netty = new HashMap<Player, NettyInjection>();
	@Getter private static List<PacketListener> packetListeners = new ArrayList<PacketListener>();
	
	public static void registerPacketListener(PacketListener listener){
		packetListeners.add(listener);
		
		for(NettyInjection nj:netty.values())
			nj.listeners.add(listener);
	}
	
	public static void unregisterPacketListener(PacketListener listener){
		packetListeners.remove(listener);
		
		for(NettyInjection nj:netty.values())
			nj.listeners.remove(listener);
	}
	
	public static void registerEventListener(Listener listener){
		Bukkit.getPluginManager().registerEvents(listener, MAntiCheat.plugin);
	}
	
	public static boolean wasMoving(XYZYP before, XYZYP after){
		return before.yaw == after.yaw && before.pitch == after.pitch;
	}
}
