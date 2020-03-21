package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;

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
	private ArrayList<String> playerList;
	private Advancement root;
	
	public AdvancementManagerInstance(String id)
	{
		this.advManager = CrazyAdvancements.getNewAdvancementManager();
		this.playerList = new ArrayList<String>();
		this.id = id;
		makeRoot();
	}
	
	public void addPlayer(Player player)
	{
		this.advManager.addPlayer(player);
		this.playerList.add(player.getName());
	}
	
	public void addAdvancement(Advancement... adv)
	{
		this.advManager.addAdvancement(adv);
	}
	
	public void removePlayer(Player player)
	{
		this.advManager.removePlayer(player);
	}
	
	public void removeAdvancement(Advancement adv)
	{
		this.advManager.removeAdvancement(adv);
	}
	
	public Advancement getRoot()
	{
		return root;
	}
	
	public String getId()
	{
		return id;
	}
	
	private void makeRoot()
	{
		AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.BEDROCK, id, "Made possible by the Fucky Gang", AdvancementFrame.TASK, "block/gray_concrete", false, false, AdvancementVisibility.ALWAYS);
		this.root = new Advancement(null, new NameKey("bingo", "root"), rootDisplay);
	}
}
