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

public class Manager implements ManagerInterface
{
	private AdvancementManager advManager;
	private ArrayList<BingoTile> bingotiles;
	
	private Advancement root;
	
	public Manager()
	{
		this.advManager = CrazyAdvancements.getNewAdvancementManager();
		this.bingotiles = new ArrayList<BingoTile>();
		initAdvancements();
		AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.BEDROCK, "Bingo", "Made possible by the Fucky Gang", AdvancementFrame.TASK, "block/gray_concrete", false, false, AdvancementVisibility.ALWAYS);
		this.root = new Advancement(null, new NameKey("bingo", "root"), rootDisplay);
	}
	
	@Override
	public void update(Player player)
	{
		advManager.addPlayer(player);
	}
	
	@Override
	public boolean createCard(int difficulty, int size)
	{
		resetCard();
		root.getDisplay().setCoordinates(-1, 1);
		
		this.advManager.addAdvancement(root); 
		
		
//		BingoTile[] tiles = getSelection(difficulty, size);
//		if (tiles == null || tiles.length < size*size)
//		{
//			Bukkit.getLogger().log(Level.SEVERE,"Not enough tiles");
//			return false;
//		}
		
		Collections.shuffle(this.bingotiles);

		for (int i = 0; i < bingotiles.size(); i+=size )
		{
			for (int j = 0; j < size; j++)
			{
				if (j == 0)
				{
					bingotiles.get(i).makeAdvancement(root, j, i/size);
				}
				else
				{
					bingotiles.get(i + j).makeAdvancement(bingotiles.get(i).getAdvancement(), j, i/size);
				}
				
			}
			
		}
		for (BingoTile tile : bingotiles)
		{
			if (tile.getAdvancement() != null)
			{
				advManager.addAdvancement(tile.getAdvancement());
			}
		}
		return true;
	}
	

	@Override
	public void resetCard()
	{
		if (!bingotiles.isEmpty())
		{
			for (BingoTile tile : bingotiles)
			{
				if (tile.getAdvancement() != null)
				{
					advManager.removeAdvancement(tile.getAdvancement());
					tile.removeAdvancement();
				}
			}
		}
	}
	
	@Override
	public AdvancementManager getManager() {
		return advManager;
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
	
	private void initAdvancements()
	{
		addBingoTile(0, "bingo", "diamondblock0", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock1", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock2", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock3", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock4", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock5", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock6", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock7", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "bingo", "diamondblock8", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
	}
	
	private void addBingoTile(int difficulty, String namespace, String key, Material icon, String title, String description, int criteria)
	{
		bingotiles.add(new BingoTile(0, new NameKey(namespace, key), icon, title, description, criteria));
	}
}
