package de.marcely.manticheat.netty;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.marcely.manticheat.location.XYZYP;
import de.marcely.manticheat.util.MThread;
import de.marcely.manticheat.util.MThread.ThreadType;

public class NettyInjection {
	
	public static final String NAME = "MBedwars_NI";
	
	public final Player player;
	private final NettyInjectionClass nclass;
	
	public List<PacketListener> listeners = new ArrayList<PacketListener>();
	
	public NettyInjection(Player player){
		this.player = player;
		
		nclass = new NettyInjectionClassV18(this);
	}
	
	public boolean isInjected(){
		if(!player.isOnline())
			return false;
		
		return this.nclass.isInjected();
	}
	
	public boolean inject(){
		if(!player.isOnline())
			return false;
		
		return this.nclass.inject();
	}
	
	public boolean uninject(){
		if(!player.isOnline())
			return false;
		
		// TODO Improve
		new MThread(ThreadType.Netty_Uninject, player.getName()){
			public void run(){
				nclass.uninject();
			}
		}.start();
		
		return true;
	}
	
	public void onPacketReceive(){
		for(PacketListener listener:listeners)
			listener.onPacketReceive(player);
	}
	
	public boolean onReceivePacket_InteractEntity(int entityId){
		boolean continu = true;
		
		for(PacketListener listener:listeners){
			if(listener.onInteractEntity(player, entityId) == false)
				continu = false;
		}
			
		return continu;
	}
	
	public boolean onReceivePacket_AttackEntity(int entityId){
		boolean continu = true;
		
		for(PacketListener listener:listeners){
			if(listener.onAttackEntity(player, entityId) == false)
				continu = false;
		}
		
		return continu;
	}
	
	public boolean onReceivePacket_PlayerMove(XYZYP xyz, boolean moved, boolean rotated){
		boolean continu = true;
		
		for(PacketListener listener:listeners){
			if(listener.onPlayerMove(player, xyz, moved, rotated) == false)
				continu = false;
		}
		
		return continu;
	}
}