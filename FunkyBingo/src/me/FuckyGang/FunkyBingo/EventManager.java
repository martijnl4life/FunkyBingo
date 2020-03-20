package me.FuckyGang.FunkyBingo;

import org.bukkit.event.Listener;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import eu.endercentral.crazy_advancements.events.*;

public class EventManager implements Listener
{
	private ManagerInterface manager;
	
	public EventManager(ManagerInterface manager)
	{
		this.manager = manager;
	}
	
	@EventHandler
	public void onAdvancementGrantEvent(AdvancementGrantEvent event)
	{
		Bukkit.getLogger().log(Level.INFO, event.getEventName());
	}
	
	@EventHandler
	public void onAdvancementRevokeEvent(AdvancementRevokeEvent event)
	{
		Bukkit.getLogger().log(Level.INFO, event.getEventName());
	}
	
	@EventHandler
	public void onAdvancementScreenCloseEvent(AdvancementScreenCloseEvent event)
	{
		Bukkit.getLogger().log(Level.INFO, event.getEventName());
	}
	
	@EventHandler
	public void onAdvancementTabChangeEvent(AdvancementTabChangeEvent event)
	{
		Bukkit.getLogger().log(Level.INFO, event.getEventName());
	}
	
	@EventHandler
	public void onCriteriaGrantEvent(CriteriaGrantEvent event)
	{
		Bukkit.getLogger().log(Level.INFO, event.getEventName());
	}
	
	@EventHandler
	public void onCriteriaProgressChangeEvent(CriteriaProgressChangeEvent event)
	{
		Bukkit.getLogger().log(Level.INFO, event.getEventName());
	}
	
	@EventHandler
	public void onCriteriaRevokeEvent(CriteriaRevokeEvent event)
	{
		Bukkit.getLogger().log(Level.INFO, event.getEventName());
	}
}
