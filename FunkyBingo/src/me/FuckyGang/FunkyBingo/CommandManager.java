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
								manager.removeManager(args[2]);
								sender.sendMessage(ChatColor.GREEN + "Successfully removed card '" + args[2] + "'!");
								return true;
							}
							else
							{
								sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[2] + "' does not exist");
								return false;
							}
							
						}
						default:
						{
							sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: does not recognise command");
						}
					}
				}
				case "player":
				{
					if (manager.getManager(args[2]) != null)
					{
						Player player;
						if (args.length == 4)
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
								manager.addPlayer(args[2], player);
								sender.sendMessage(ChatColor.GREEN + "successfully added player '" + player.getName() + "' to card '" + args[2] + "'!");
								return true;
							}
							case "remove":
							{
								manager.removePlayer(args[2], player);
								sender.sendMessage(ChatColor.GREEN + "successfully removed player '" + player.getName() + "' from card '" + args[2] + "'!");
								return true;
							}
							default:
							{
								sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: does not recognise command");
							}
						}
					}
					else
					{
						sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[2] + "' does not exist");
					}
				}
				case "team":
				{
					if (args.length == 4 && manager.getManager(args[3]) != null )
					{
						switch (args[1])
						{
							// bc team add [teamname] [card]
						
							case "add":
							{
								manager.getManager(args[3]).addTeam(args[2]);
								sender.sendMessage(ChatColor.GREEN + "successfully added team '" + args[2] + "'!");
								return true;
							}
							case "remove":
							{
								manager.getManager(args[3]).removeTeam(args[2]);
								sender.sendMessage(ChatColor.GREEN + "successfully removed team '" + args[2] + "'!");
								return true;
							}
							case "update":
							{
								manager.getManager(args[3]).updateTeams();
								sender.sendMessage(ChatColor.GREEN + "successfully updated teams " + "!");
								return true;
							}
							default:
							{
								sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: does not recognise command");
							}
						}
					}
					else
					{
						sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[2] + "' does not exist");
					}
				}
				default:
				{
					sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: does not recognise command");
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
