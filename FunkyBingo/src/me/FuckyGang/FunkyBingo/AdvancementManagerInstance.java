package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;

public class AdvancementManagerInstance {
	private AdvancementManager advManager;
	private final String id;
	private ArrayList<UUID> playerList;
	private Advancement root;
	
	public AdvancementManagerInstance(String id)
	{
		this.advManager = CrazyAdvancements.getNewAdvancementManager();
		this.playerList = new ArrayList<UUID>();
		this.id = id;
		makeRoot();
	}
	
	public void addPlayer(Player player)
	{
		this.advManager.addPlayer(player);
		this.playerList.add(player.getUniqueId());
	}
	
	public void addAdvancement(Advancement... adv)
	{
		this.advManager.addAdvancement(adv);
	}
	
	public void removePlayer(Player player)
	{
		this.advManager.removePlayer(player);
		this.playerList.remove(player.getUniqueId());
	}
	
	public void removeAdvancement(Advancement adv)
	{
		this.advManager.removeAdvancement(adv);
	}
	
	public AdvancementManager getAdvancementManager()
	{
		return this.advManager;
	}
	
	public Advancement getRoot()
	{
		return this.root;
	}
	
	public void removeRoot()
	{
		removeAdvancement(root);
	}
	
	public void removeAllPlayers()
	{
		for (UUID playerId : playerList)
		{
			removePlayer(Bukkit.getOfflinePlayer(playerId).getPlayer());
		}
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public boolean hasPlayerInList(UUID playerId)
	{
		for (UUID id : playerList)
		{
			if (id == playerId)
			{
				return true;
			}
		}
		return false;
	}
	
	
	private void makeRoot()
	{
		AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.BEDROCK, id, "Made possible by the Fucky Gang", AdvancementFrame.TASK, false, false, AdvancementVisibility.ALWAYS);
		rootDisplay.setBackgroundTexture("textures/block/concrete_orange.png");
		this.root = new Advancement(null, new NameKey(id, "root"), rootDisplay);
		advManager.addAdvancement(root);
	}
}
