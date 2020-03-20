package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;

public class Manager 
{
	private AdvancementManager advManager;
	private ArrayList<BingoTile> bingotiles;
	private ArrayList<Advancement> advancements;
	
	private Advancement root;
	
	public Manager()
	{
		this.advManager = CrazyAdvancements.getNewAdvancementManager();
		initAdvancements();
		AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.BEDROCK, "", "", null, false, false, null);
		this.root = new Advancement(null, new NameKey("bingo", "root"), rootDisplay);
	}
	
	public void createCard(int difficulty, int size)
	{
		resetCard();
		getSelection(difficulty, size);
		
		// assign the right parents using card size etc
		
		advManager.addAdvancement((Advancement[]) advancements.toArray());
	}
	
	public BingoTile[] getSelection(int difficulty, int size)
	{
		BingoTile[] result = new BingoTile[size * size];
		ArrayList<BingoTile> selection = getTilesDifficulty(difficulty);
		Collections.shuffle((ArrayList<?>)selection);
		
		if (selection.size() >= size * size)
		{
			for (int i = 0; i < size * size; i++)
			{
				result[i] = selection.get(i);
			}
			return result;
		}
		else
		{
			Bukkit.getLogger().log(Level.SEVERE,"Not enough tiles in difficulty" + difficulty);
			return null;
		}
	}
	
	public ArrayList<BingoTile> getTilesDifficulty(int difficulty)
	{
		ArrayList<BingoTile> tiles = new ArrayList<BingoTile>();
		for (BingoTile tile : this.bingotiles)
		{
			if (tile.getDifficluty() == difficulty)
			{
				tiles.add(tile);
			}
		}
		return tiles;
	}
	
	public void resetCard()
	{
		
	}
	
	public void addAdvancement(BingoTile tile, Advancement parent)
	{
		advancements.add(new Advancement(parent, tile.getId(), new AdvancementDisplay(tile.getIcon(), tile.getTitle(), tile.getDescription(), AdvancementFrame.TASK, false, true, AdvancementVisibility.ALWAYS)));
	}
	
	public void initAdvancements()
	{
		bingotiles.add(new BingoTile(0, new NameKey("bingo", "diamondblock"), Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block"));
	}
	
}
