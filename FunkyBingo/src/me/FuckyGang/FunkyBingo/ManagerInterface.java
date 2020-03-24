package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.entity.Player;

public interface ManagerInterface {
	public boolean createCard(String id, int difficulty, int size);
	public void removeManager(String id);
	public void addPlayer(String id, Player player);
	public void removePlayer(String id, Player player);
	public AdvancementManagerInstance getManager(String id);
	public Set<String> getNamespaces();
	public ArrayList<AdvancementHolder> getHolders(EventType event);
}
