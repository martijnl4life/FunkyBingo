package me.FuckyGang.FunkyBingo;

import java.util.Set;

import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.manager.AdvancementManager;

public interface ManagerInterface {
	public boolean createCard(String id, int difficulty, int size);
	public void resetCard(String id);
	public void addPlayer(String id, Player player);
	public AdvancementManagerInstance getManager(String id);
	public Set<String> getNamespaces();
}
