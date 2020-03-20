package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;

import eu.endercentral.crazy_advancements.NameKey;

public class BingoTile
{
	private int difficulty;
	private NameKey id;
	private Material icon;
	private String title;
	private String description;
	private int criteria;
	
	public BingoTile(int difficulty, NameKey id, Material icon, String title, String description, int criteria)
	{
		this.difficulty = difficulty;
		this.id = id;
		this.icon = icon;
		this.title = title;
		this.description = description;
		this.criteria = criteria;
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