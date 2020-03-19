package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Collections;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import hu.trigary.advancementcreator.AdvancementFactory;
import hu.trigary.advancementcreator.Advancement;

public class AdvancementManager {
	private class Pair
	{
		private int difficulty;
		private Advancement advancement;
		
		public Pair(int difficulty, Advancement advancement)
		{
			this.difficulty = difficulty;
			this.advancement = advancement;
		}
		
		public int getDifficulty()
		{
			return difficulty;
		}
		
		public Advancement getAdvancement()
		{
			return advancement;
		}
		
		public void setHidden(boolean hidden)
		{
			advancement.setHidden(hidden);
		}
	}
	
	private AdvancementFactory factory;
	private ArrayList<Pair> advancements;
	private Advancement root;
	private Advancement unInitialised;
	
	
	public AdvancementManager(Plugin plugin)
	{
		this.factory = new AdvancementFactory(plugin, false, false);
		this.root = factory.getRoot("bingo/root", "Getting Started", "Newbie Advancements", Material.DIRT, "block/dirt");
		this.unInitialised = factory.getImpossible("voided", root, "Not Initialised", "Root of advancements not in use", Material.BEDROCK);
		this.unInitialised.setHidden(true);
		
		this.advancements = new ArrayList<Pair>();
		this.initAdvancements();
	}
	
	public Advancement[] getSelection(int difficulty, int size)
	{
		Advancement[] selection = new Advancement[size*size]; 
		ArrayList<Advancement> tempDifficulty = getAdvancementsDifficulty(difficulty);
		Collections.shuffle(tempDifficulty);
		if (tempDifficulty.size() >= size*size)
		{
			for (int i = 0; i < size*size; i++)
			{
				selection[i] = tempDifficulty.get(i);
			}
		}
		return selection;
	}
	
	public Advancement getRoot()
	{
		return root;
	}
	
	public Advancement getUninitialised()
	{
		return unInitialised;
	}
	
	public int getAdvancementsAmount(int difficulty)
	{
		ArrayList<Advancement> tempDifficulty = getAdvancementsDifficulty(difficulty);
		return tempDifficulty.size();
	}
	
	private void addAdvancement(int difficulty, Advancement a)
	{
		this.advancements.add(new Pair(difficulty, a));
	}
	
	private ArrayList<Advancement> getAdvancementsDifficulty(int difficulty)
	{
		ArrayList<Advancement> adv = new ArrayList<Advancement>();
		for (Pair p : advancements)
		{
			if (p.getDifficulty() == difficulty || p.getDifficulty() == 0)
			{
				adv.add(p.getAdvancement());
			}
		}
		return adv;
	}
	
	private void initAdvancements()
	{	
		addAdvancement(0,factory.getItem("bingo/diamondblock", unInitialised, "9 Diamonds Pogu", "Obtain 1 Diamond Block", Material.DIAMOND_BLOCK));
        addAdvancement(0,factory.getItem("bingo/bookshelf", unInitialised, "Booked!", "Obtain 1 Bookshelf", Material.BOOKSHELF));
        addAdvancement(0,factory.getItem("bingo/enchantmenttable", unInitialised, "Time for some magic :O", "Obtain 1 Entchanting Table", Material.ENCHANTING_TABLE));
        addAdvancement(0,factory.getItem("bingo/endcrystal", unInitialised, "A teary eye", "Obtain 1 End Crystal", Material.END_CRYSTAL));
        addAdvancement(0,factory.getItem("bingo/emeraldblock", unInitialised, "For the Villagers :)", "Obtain 1 Emerald Block", Material.EMERALD_BLOCK));
        addAdvancement(0,factory.getItem("bingo/brick", unInitialised, "Just Like Legos", "Obtain 5 Bricks", Material.BRICK, 5));
        addAdvancement(0,factory.getItem("bingo/glisteringmelonslice", unInitialised, "Watermelone", "Obtain 1 Glistering Melon Slice", Material.GLISTERING_MELON_SLICE));
        addAdvancement(0,factory.getItem("bingo/seapickle", unInitialised, "I'M PICKLE RICK!!!", "Obtain 32 sea pickles", Material.SEA_PICKLE, 32));
        addAdvancement(0,factory.getItem("bingo/cookie", unInitialised, "Just get 1 Cookie :)", "Obtain 1 Cookie", Material.COOKIE));
	}
	
}











