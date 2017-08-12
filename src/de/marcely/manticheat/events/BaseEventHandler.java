package de.marcely.manticheat.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BaseEventHandler implements Listener {
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		PlayerJoin.onPlayerJoinEvent(event);
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event){
		PlayerQuit.onPlayerQuitEvent(event);
	}
}
