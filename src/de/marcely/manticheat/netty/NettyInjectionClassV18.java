package de.marcely.manticheat.netty;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import de.marcely.manticheat.location.XYZYP;
import de.marcely.manticheat.nms.NMSClass;
import de.marcely.manticheat.nms.Version;

public class NettyInjectionClassV18 extends NettyInjectionClass {
	
	private io.netty.channel.Channel channel = null;
	private io.netty.channel.ChannelHandler handler = null;
	
	public NettyInjectionClassV18(NettyInjection nj){
		super(nj);
	}

	@Override
	public boolean isInjected(){
		return channel != null && channel.isActive() && channel.pipeline().names().contains(NettyInjection.NAME);
	}

	@Override
	public boolean inject(){
		if(isInjected())
			return false;
		
		try{
			final Object craftPlayer = NMSClass.CRAFTPLAYER.cast(nj.player);
			final Object entityPlayer = craftPlayer.getClass().getMethod("getHandle").invoke(craftPlayer);
			final Object playerConnection = entityPlayer.getClass().getDeclaredField("playerConnection").get(entityPlayer);
			final Object networkManager = playerConnection.getClass().getDeclaredField("networkManager").get(playerConnection);
			
			Field f = networkManager.getClass().getDeclaredField(Version.version.getVersionNumber() == 8 && Version.version.getVersionNumber2() <= 1 ? "i" : "channel");
			f.setAccessible(true);
			
			channel = (io.netty.channel.Channel) f.get(networkManager);
			
			handler = new io.netty.handler.codec.MessageToMessageDecoder<Object>(){
				@Override
				protected void decode(io.netty.channel.ChannelHandlerContext chc, Object packet, List<Object> out) throws Exception {
					boolean continu = true;
					
					nj.onPacketReceive();
					System.out.println(packet);
					
					try{
						if(NMSClass.PACKETPLAYINUSEENTITY.isInstance(packet)){
							Field field = NMSClass.PACKETPLAYINUSEENTITY.getDeclaredField("a");
							field.setAccessible(true);
							final int entityId = field.getInt(packet);
							
							field = NMSClass.PACKETPLAYINUSEENTITY.getDeclaredField("action");
							field.setAccessible(true);
							Object rawAction = field.get(packet);
							String action = (String) rawAction.getClass().getMethod("name").invoke(rawAction);
							
							if(action.equals("INTERACT"))
								continu = nj.onReceivePacket_InteractEntity(entityId);
							else if(action.equals("ATTACK"))
								continu = nj.onReceivePacket_AttackEntity(entityId);	
						
						}else if(NMSClass.PACKETPLAYINFLYING.isInstance(packet)){
							
							// moved
							Field field = NMSClass.PACKETPLAYINFLYING.getDeclaredField("hasPos");
							field.setAccessible(true);
							final boolean moved = field.getBoolean(packet);
							// rotated
							field = NMSClass.PACKETPLAYINFLYING.getDeclaredField("hasLook");
							field.setAccessible(true);
							final boolean rotated = field.getBoolean(packet);
							
							double x = 0, y = 0, z = 0;
							float yaw = 0, pitch = 0;
							
							if(Version.version.getVersionNumber() >= 9){
								// x
								Method m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("a", double.class);
								x = (double) m.invoke(packet, nj.player.getLocation().getX());
								// y
								m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("b", double.class);
								y = (double) m.invoke(packet, nj.player.getLocation().getY());
								// z
								m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("c", double.class);
								z = (double) m.invoke(packet, nj.player.getLocation().getZ());
								// yaw
								m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("a", float.class);
								yaw = (float) m.invoke(packet, nj.player.getLocation().getYaw());
								// pitch
								m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("b", float.class);
								pitch = (float) m.invoke(packet, nj.player.getLocation().getPitch());
							
							}else{
								if(moved){
									// x
									Method m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("a");
									x = (double) m.invoke(packet);
									// y
									NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("b");
									y = (double) m.invoke(packet);
									// z
									NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("c");
									z = (double) m.invoke(packet);
								}else{
									x = nj.player.getLocation().getX();
									y = nj.player.getLocation().getY();
									z = nj.player.getLocation().getZ();
								}if(rotated){
									// yaw
									Method m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("d");
									yaw = (float) m.invoke(packet);
									// pitch
									m = NMSClass.PACKETPLAYINFLYING.getDeclaredMethod("e");
									pitch = (float) m.invoke(packet);
								}else{
									yaw = nj.player.getLocation().getYaw();
									pitch = nj.player.getLocation().getPitch();
								}
							}
							
							continu = nj.onReceivePacket_PlayerMove(new XYZYP(x, y, z, yaw, pitch), moved, rotated);
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
					
					// continue packet
					if(continu)
						out.add(packet);
				}
			};
			
			try{
				channel.pipeline().addAfter("decoder", NettyInjection.NAME, handler);
			}catch(IllegalArgumentException e){
				channel.pipeline().remove(NettyInjection.NAME);
				channel.pipeline().addAfter("decoder", NettyInjection.NAME, handler);
			}
			
		}catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public boolean uninject(){
		if(!isInjected())
			return false;
		
		channel.pipeline().remove(NettyInjection.NAME);
		
		channel = null;
		handler = null;
		
		return true;
	}
}
