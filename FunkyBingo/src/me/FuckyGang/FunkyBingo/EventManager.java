package me.FuckyGang.FunkyBingo;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

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
	public void onInventoryPickupItem(InventoryPickupItemEvent event)
	{
		
	}
}
