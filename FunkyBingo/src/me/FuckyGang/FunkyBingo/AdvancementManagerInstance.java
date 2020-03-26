package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;

public class AdvancementManagerInstance {
	private AdvancementManager advManager;
	private final String id;
	private ArrayList<UUID> playerList;
	private ArrayList<Team> teams;
	private Advancement root;
	private int size;
	
	
	public AdvancementManagerInstance(String id)
	{
		this.advManager = CrazyAdvancements.getNewAdvancementManager();
		this.playerList = new ArrayList<UUID>();
		this.teams = new ArrayList<Team>();
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
		advManager.addAdvancement(adv);
	}
	
	public void addBingoAdvancement(Advancement parent, String name, int x, int y)
	{
		AdvancementDisplay bingoDisplay = new AdvancementDisplay(Material.BEDROCK, id, "Made possible by the Fucky Gang", AdvancementFrame.GOAL, false, false, AdvancementVisibility.ALWAYS);
		Advancement bingo = new Advancement(parent, new NameKey(id, name), bingoDisplay);
		bingo.getDisplay().setCoordinates(x, y);
		advManager.addAdvancement(bingo);
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
	
	public void addTeam(String teamName)
	{
		teams.add(Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam(teamName));
	}
	
	public void removeTeam(String teamName)
	{
		teams.remove(Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam(teamName));
	}
	
	public void updateTeams()
	{
		for (Team t : teams)
		{
			Set<String> entries = t.getEntries();
			for (String s : entries)
			{
				if (!hasPlayer(s))
				{
					this.advManager.addPlayer(Bukkit.getPlayer(s));
				}
			}
		}
	}
	
	public ArrayList<Player> getTeamMembers(Player player)
	{
		ArrayList<Player> teamMembers = new ArrayList<Player>();
		teamMembers.add(player);
		if (teams.size() > 0)
		{
			for (Team team : teams)
			{
				if (team.hasEntry(player.getName()))
				{
					Set<String> entries = team.getEntries();
					for (String entry : entries)
					{
						if (!entry.equals(player.getName()))
						{
							teamMembers.add(Bukkit.getPlayer(entry));
						}
					}
				}
			}
		}
		return teamMembers;
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
		for (int i = 0; i < playerList.size(); i++)
		{
			removePlayer(Bukkit.getOfflinePlayer(playerList.get(i)).getPlayer());
		}
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public boolean hasPlayer(String playerName)
	{
		ArrayList<Player> players = advManager.getPlayers();
		for (Player p : players)
		{
			if (p.getName().equals(playerName))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPlayerInListOrTeam(UUID playerId)
	{
		for (UUID id : playerList)
		{
			if (id == playerId)
			{
				return true;
			}
		}
		for (Team t : teams)
		{
			if (t.hasEntry(Bukkit.getPlayer(playerId).getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	
	private void makeRoot()
	{
		AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.BEDROCK, id, "Made possible by the Fucky Gang", AdvancementFrame.TASK, false, false, AdvancementVisibility.ALWAYS);
		rootDisplay.setBackgroundTexture("textures/block/orange_concrete.png");
		this.root = new Advancement(null, new NameKey(id, "root"), rootDisplay);
		advManager.addAdvancement(root);
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
