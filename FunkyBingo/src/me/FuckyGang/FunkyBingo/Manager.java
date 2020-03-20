package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
		this.bingotiles = new ArrayList<BingoTile>();
		this.advancements = new ArrayList<Advancement>();
		initAdvancements();
		AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.BEDROCK, "root", "boat", AdvancementFrame.TASK, false, false, AdvancementVisibility.ALWAYS);
		this.root = new Advancement(null, new NameKey("bingo", "root"), rootDisplay);
	}
	
	public void update(Player player)
	{
		advManager.addPlayer(player);
	}
	
	public boolean createCard(int difficulty, int size)
	{
		resetCard();
		BingoTile[] tiles = getSelection(difficulty, size);
		if (tiles == null || tiles.length < size*size)
		{
			Bukkit.getLogger().log(Level.SEVERE,"Not enough tiles");
			return false;
		}
		
		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				if (x == 0)
				{
					addAdvancement(tiles[y * size + x], root);
				}
				else
				{
					addAdvancement(tiles[y * size + x], advancements.get(advancements.size() - 1));
				}
				
			}
		}
		for (Advancement adv : advancements)
		{
			advManager.addAdvancement(adv);
		}
		return true;
	}
	
	private BingoTile[] getSelection(int difficulty, int size)
	{
		BingoTile[] result = new BingoTile[size * size];
		ArrayList<BingoTile> selection = getTilesDifficulty(difficulty);
		Collections.shuffle((ArrayList<BingoTile>)selection);
		
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
	
	private ArrayList<BingoTile> getTilesDifficulty(int difficulty)
	{
		ArrayList<BingoTile> tiles = new ArrayList<BingoTile>();
		for (BingoTile tile : this.bingotiles)
		{
			if (tile.getDifficluty() == difficulty || difficulty == 0)
			{
				tiles.add(tile);
			}
		}
		return tiles;
	}
	
	public void resetCard()
	{
		if (!advancements.isEmpty())
		{
			for (Advancement adv : advancements)
			{
				advManager.removeAdvancement(adv);
			}
			advancements.clear();
		}
	}
	
	private void addAdvancement(BingoTile tile, Advancement parent)
	{
		advancements.add(new Advancement(parent, tile.getId(), new AdvancementDisplay(tile.getIcon(), tile.getTitle(), tile.getDescription(), AdvancementFrame.TASK, false, true, AdvancementVisibility.ALWAYS)));
	}
	
	private void initAdvancements()
	{
		addBingoTile(0, "bingo", "diamondblock0", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock1", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock2", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock3", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock4", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock5", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock6", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock7", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
		addBingoTile(0, "bingo", "diamondblock8", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block");
	}
	
	private void addBingoTile(int difficulty, String namespace, String key, Material icon, String title, String description)
	{
		bingotiles.add(new BingoTile(0, new NameKey(namespace, key), icon, title, description));
	}
	
}
