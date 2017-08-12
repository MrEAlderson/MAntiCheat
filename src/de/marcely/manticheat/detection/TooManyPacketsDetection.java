package de.marcely.manticheat.detection;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.marcely.manticheat.MAntiCheat;
import de.marcely.manticheat.netty.PacketListener;
import de.marcely.manticheat.util.Util;

public class TooManyPacketsDetection extends Detection {
	
	private PacketListener listener;
	private BukkitTask task;
	
	private List<Long> updates = new ArrayList<Long>();
	
	@Override
	public void onLoad(){
		listener = new PacketListener(){
			long lastSend = System.currentTimeMillis();
			
			public void onPacketReceive(Player player){
				updates.add(System.currentTimeMillis() - lastSend);
				lastSend = System.currentTimeMillis();
				System.out.println("Received");
			}
		};
		Util.registerPacketListener(listener);
		
		task = new BukkitRunnable(){
			public void run(){
				// calculate average
				double average = 0;
				for(long u:updates)
					average += u;
				average /= (double) updates.size();
				
				System.out.println(average);
				
				updates.clear();
			}
		}.runTaskTimerAsynchronously(MAntiCheat.plugin, 20 * 10, 20 * 10);
	}

	@Override
	public void onUnload(){
		Util.unregisterPacketListener(listener);
		task.cancel();
	}
}
