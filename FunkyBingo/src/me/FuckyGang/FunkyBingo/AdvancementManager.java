package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import hu.trigary.advancementcreator.AdvancementFactory;
import hu.trigary.advancementcreator.Advancement;

public class AdvancementManager {

	
	private AdvancementFactory factory;
	private ArrayList<Pair> advancements;
	private Advancement root;
	private Advancement unInitialised;
	
	
	public AdvancementManager(Plugin plugin)
	{
		this.factory = new AdvancementFactory(plugin, true, false);
		this.root = factory.getRoot("bingo/root", "Getting Started", "Newbie Advancements", Material.PLAYER_HEAD, "block/dirt");
		this.unInitialised = factory.getImpossible("voided", root, "Not Initialised", "Root of advancements not in use", Material.BEDROCK);
		
		this.advancements = new ArrayList<Pair>();
	}
	
	
	public Advancement[] getSelection(int difficulty, int size)
	{
		Advancement[] selection = new Advancement[size*size]; 
		
		
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
	
	private void addAdvancement(int difficulty, Advancement a)
	{
		this.advancements.add(new Pair(difficulty, a));
	}
	
	private void initAdvancements()
	{
		addAdvancement(1,factory.getItem("newbie/wood", unInitialised, "Chopper", "Chop down a tree", Material.OAK_LOG));
	}
	
	private ArrayList<Advancement> getAdvancementsDifficulty(int difficulty)
	{
		ArrayList<Advancement> adv = new ArrayList<Advancement>();
		
		for (Pair p : advancements)
		{
			if (p.getDifficulty() == difficulty)
			{
				adv.add(p.getAdvancement());
			}
		}
		
		return adv;
	}
	
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
	}
	
}
