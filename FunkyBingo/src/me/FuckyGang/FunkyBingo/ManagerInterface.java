package me.FuckyGang.FunkyBingo;

import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.manager.AdvancementManager;

public interface ManagerInterface {
	public boolean createCard(int difficulty, int size);
	public void resetCard();
	public void update(Player player);
	public AdvancementManager getManager();
}
