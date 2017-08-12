package de.marcely.manticheat.detection;

import org.bukkit.entity.Player;

import de.marcely.manticheat.location.XYZYP;
import de.marcely.manticheat.netty.PacketListener;
import de.marcely.manticheat.util.Util;

public class MovementDetection extends Detection {
	
	private PacketListener listener;
	
	public MovementDetection(){ }
	
	@Override
	public void onLoad(){
		listener = new PacketListener(){
			
			@Override
			public boolean onPlayerMove(Player player, XYZYP xyz, boolean moved, boolean rotated){
				return true;
			}
		};
		
		Util.registerPacketListener(listener);
	}

	@Override
	public void onUnload(){
		Util.unregisterPacketListener(listener);
	}
}
