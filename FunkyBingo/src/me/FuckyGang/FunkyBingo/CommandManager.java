package me.FuckyGang.FunkyBingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager
{
	private ManagerInterface manager;
	
	public CommandManager(ManagerInterface m)
	{
		this.manager = m;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("bc"))
		{
			
			/* /-1 0      1      2      3            4
			 * /bc card   add    [card] [difficulty] [size]
			 * /bc card   remove [card]
			 * /bc player add    [card] <[player]>
			 * /bc player remove [card] <[player]>
			 */
			switch(args[0])
			{
				case "card":
				{
					switch(args[1])
					{
						case "add":
						{
							if (manager.getManager(args[2]) == null)
							{
								if (areIntegers(args[3], args[4]))
								{
									int diff = Integer.parseInt(args[3]);
									int size = Integer.parseInt(args[4]);
									if (diff >= 0 && diff <= 2 && size >= 0 && size <= 5)
									{
										manager.createCard(args[2], diff, size);
										sender.sendMessage(ChatColor.GREEN + "Successfully created card '" + args[2] + "'!");
										return true;
									}
									else
									{
										sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: argument(s) out of range");
										return false;
									}
								}
								else
								{
									sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: argument(s) of '/bc create' must be integers");
									return false;
								}
							}
							else
							{
								sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[2] + "' already exists");
								return false;
							}
						}
						case "remove":
						{
							if (manager.getManager(args[2]) != null)
							{
								manager.resetCard(args[2]);
								sender.sendMessage(ChatColor.GREEN + "Successfully removed card '" + args[2] + "'!");
								return true;
							}
							else
							{
								sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[2] + "' does not exist");
								return false;
							}
						}
					}
				}
				case "player":
				{
					if (manager.getManager(args[2]) != null)
					{
						Player player;
						if (args[3] != null)
						{
							player = Bukkit.getPlayer(args[3]);
						}
						else
						{
							if (sender instanceof Player)
							{
								player = Bukkit.getPlayer(sender.getName());
							}
							else
							{
								sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: command sender is not a player (specify a player)");
								return false;
							}
						}
						switch (args[1])
						{
							case "add":
							{
								manager.addPlayer(args[1], player);
								sender.sendMessage(ChatColor.DARK_RED + "successfully added player '" + args[2] + "' to card'" + args[1] + "'!");
								return true;
							}
							case "remove":
							{
								manager.removePlayer(args[1], player);
								sender.sendMessage(ChatColor.DARK_RED + "successfully removed player '" + args[2] + "' from card'" + args[1] + "'!");
								return true;
							}
						}
					}
					else
					{
						sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[1] + "' does not exist");
					}
				}
			}
		}
		return false;
	}
	
	private boolean areIntegers(String... strings)
	{
		for (String s : strings)
		{
			if (s.isEmpty()) return false;
			for(int i = 0; i < s.length(); i++)
			{
				if(i == 0 && s.charAt(i) == '-')
				{
		            if(s.length() == 1) return false;
		            else continue;
		        }
				if(Character.digit(s.charAt(i),10) < 0 ) return false;
			}
		}
		return true;
	}
}
