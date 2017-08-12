package de.marcely.manticheat.location;

import org.bukkit.Location;
import org.bukkit.World;

public class XYZYP extends XYZ {
	
	public float yaw, pitch;
	
	public XYZYP(double x, double y, double z, float yaw, float pitch){
		super(x, y, z);
		
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public static XYZYP fromLocation(Location loc){
		return new XYZYP(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
	public Location toLocation(World world){
		return new Location(world, x, y, z, yaw, pitch);
	}
}
