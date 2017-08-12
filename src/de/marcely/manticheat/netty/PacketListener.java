package de.marcely.manticheat.netty;

import org.bukkit.entity.Player;

import de.marcely.manticheat.location.XYZYP;

/**
 * 
 * Simply just override one of these methods.
 * If one of the methods returns false, the packet won't receive the server
 */
public class PacketListener {
	
	public void onPacketReceive(Player player){ }
	
	public boolean onInteractEntity(Player player, int entityId){ return true; }
	
	public boolean onAttackEntity(Player player, int entityId){ return true; }
	
	public boolean onPlayerMove(Player player, XYZYP xyz, boolean moved, boolean rotated){ return true; }
}
