package de.marcely.manticheat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.marcely.manticheat.detection.*;
import de.marcely.manticheat.events.BaseEventHandler;
import de.marcely.manticheat.events.PlayerJoin;
import de.marcely.manticheat.events.PlayerQuit;
import de.marcely.manticheat.nms.Version;
import de.marcely.manticheat.util.Util;
import lombok.Getter;

public class MAntiCheat extends JavaPlugin {
	
	public static Plugin plugin;
	@Deprecated public static PluginState state;
	private static List<Detection> registredDetections = new ArrayList<Detection>();
	
	@Override
	public void onEnable(){
		plugin = this;
		
		// register eventhandler
		Util.registerEventListener(new BaseEventHandler());
		
		// load everything
		Version.onEnable();
		
		// add players (if reload)
		for(Player p:Bukkit.getOnlinePlayers())
			PlayerJoin.onPlayerJoinEvent(p);
		
		// load detections
		registredDetections.add(new MovementDetection());
		registredDetections.add(new TooManyPacketsDetection());
		
		for(Detection det:registredDetections)
			det.onLoad();
	}
	
	@Override
	public void onDisable(){
		for(Player p:Bukkit.getOnlinePlayers())
			PlayerQuit.onPlayerQuitEvent(p);
	}
	
	public static PluginState getState(){
		return state;
	}
	
	public static void registerDetection(Detection det){
		registredDetections.add(det);
		det.onLoad();
	}
	
	public static void unregisterDetection(Detection det){
		registredDetections.remove(det);
		det.onUnload();
	}
    
    
    
    public static enum PluginState {
    	Enabling(true),
    	Running(true),
    	StartFailed(false),
    	Disabling(false),
    	Disabled(false);
    	
    	@Getter private boolean taskable;
    	
    	private PluginState(boolean taskable){
    		this.taskable = taskable;
    	}
    }
}
