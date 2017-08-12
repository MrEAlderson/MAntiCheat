package de.marcely.manticheat.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import de.marcely.manticheat.MAntiCheat;
import de.marcely.manticheat.exception.UnexpectedException;
import lombok.Getter;

public class MThread extends java.lang.Thread {
	
	private static final List<MThread> runningThreads = new ArrayList<MThread>();
	@Getter private static final  ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	
	@Getter private final ThreadType type;
	@Getter private final String info;
	
	public MThread(ThreadType type){
		this(type, null);
	}
	
	public MThread(ThreadType type, String info){
		this.type = type;
		this.info = info;
		
		if(type.requiresInfo && info == null)
			new UnexpectedException(type.name() + " requires an information!").printStackTrace();
	}
	
	@Override
	public void start(){
		runningThreads.add(this);
		super.start();
		
		final MThread t = this;
		if(MAntiCheat.getState().isTaskable()){
			new BukkitRunnable(){
				public void run(){
					if(!t.isAlive())
						runningThreads.remove(t);
				}
			}.runTaskTimer(MAntiCheat.plugin, 20, 20);
		}
	}
	
	@Override
	public void interrupt(){
		super.interrupt();
		runningThreads.remove(this);
	}
	
	public static synchronized List<Thread> getRunningThreads(){
		List<Thread> list = new ArrayList<Thread>();
		list.add(Thread.currentThread());
		list.addAll(runningThreads);
		
		return list;
	}
	
	
	
	public static enum ThreadType {
		Netty_Uninject(true),
		Custom(false);
		
		@Getter private final boolean requiresInfo;
		
		private ThreadType(boolean requiresInfo){
			this.requiresInfo = requiresInfo;
		}
	}
}
