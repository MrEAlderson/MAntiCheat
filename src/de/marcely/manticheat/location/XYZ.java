package de.marcely.manticheat.location;

import org.bukkit.Location;
import org.bukkit.World;

public class XYZ {
	
	public double x, y, z;
	
	public XYZ(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static XYZ fromLocation(Location loc){
		return new XYZ(loc.getX(), loc.getY(), loc.getZ());
	}
	
	public Location toLocation(World world){
		return new Location(world, x, y, z);
	}
}
