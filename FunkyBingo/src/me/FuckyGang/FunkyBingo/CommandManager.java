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
			switch(args[0])
			{
				// /bc create name difficulty size 
				case "create":
					if (manager.getManager(args[1]) == null)
					{
						if (areIntegers(args[2], args[3]))
						{
							int diff = Integer.parseInt(args[2]);
							int size = Integer.parseInt(args[3]);
							if (diff >= 0 && diff <= 2 && size >= 0 && size <= 5)
							{
								manager.createCard(args[1], diff, size);
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
						sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[1] + "' already exists");
						return false;
					}
				case "reset":
					break;
				case "addplayer":
					{
						if (manager.getManager(args[1]) != null)
						{
							Player player;
							if (args[2] != null)
							{
								player = Bukkit.getPlayer(args[2]);
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
							manager.addPlayer(args[1], player);
							break;
						}
						else
						{
							sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: card '" + args[1] + "' does not exist");
						}
					}
				default:
					sender.sendMessage(ChatColor.DARK_RED + "[FAILED]: not enough parameters");
					return false;
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
