package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;

public class BingoTile
{
	private int difficulty;
	private NameKey id;
	private Material icon;
	private String title;
	private String description;
	private int criteria;
	private Advancement advancement;
	private AdvancementVisibility visibility;
	
	public BingoTile(int difficulty, NameKey id, Material icon, String title, String description, int criteria)
	{
		this.difficulty = difficulty;
		this.id = id;
		this.icon = icon;
		this.title = title;
		this.description = description;
		this.criteria = criteria;
		this.advancement = null;
	}
	
	public Advancement makeAdvancement(Advancement parent, float x, float y)
	{
		this.advancement = new Advancement(parent, id, new AdvancementDisplay(icon, title, description, AdvancementFrame.TASK, true, true, visibility));
		this.advancement.getDisplay().setCoordinates(x, y);
		this.advancement.setCriteria(criteria);
		
		return this.advancement;
	}
	
	public int getDifficluty()
	{
		return this.difficulty;
	}
	
	public NameKey getId()
	{
		return this.id;
	}
	
	public Material getIcon()
	{
		return this.icon;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
}