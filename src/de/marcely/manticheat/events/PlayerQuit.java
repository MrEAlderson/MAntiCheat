package de.marcely.manticheat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import de.marcely.manticheat.netty.NettyInjection;
import de.marcely.manticheat.util.Util;

public class PlayerQuit {
	
	public static void onPlayerQuitEvent(PlayerQuitEvent event){
		onPlayerQuitEvent(event);
	}
	
	public static void onPlayerQuitEvent(Player player){
    	// netty
    	final NettyInjection netty = Util.netty.get(player);
    	if(netty != null){
	    	netty.uninject();
	    	Util.netty.remove(player);
    	}
    	
    	// remove from watching list
    	Util.watchingPlayers.remove(player);
	}
}
