package de.marcely.manticheat.netty;

import lombok.Getter;

public abstract class NettyInjectionClass {
	
	@Getter protected NettyInjection nj;
	
	public NettyInjectionClass(NettyInjection nj){
		this.nj = nj;
	}
	
	
	public abstract boolean isInjected();
	
	public abstract boolean inject();
	
	public abstract boolean uninject();
}