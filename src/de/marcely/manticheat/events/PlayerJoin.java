package de.marcely.manticheat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import de.marcely.manticheat.netty.NettyInjection;
import de.marcely.manticheat.netty.PacketListener;
import de.marcely.manticheat.util.Util;

public class PlayerJoin {
	
	public static void onPlayerJoinEvent(PlayerJoinEvent event){
		onPlayerJoinEvent(event.getPlayer());
	}
	
	public static void onPlayerJoinEvent(Player player){
		// netty
		final NettyInjection netty = new NettyInjection(player);
		netty.inject();
		Util.netty.put(player, netty);
		
		// netty: add registred packet listeners
		for(PacketListener pl:Util.getPacketListeners())
			netty.listeners.add(pl);
		
		// add to watching players
		Util.watchingPlayers.add(player);
	}
}
