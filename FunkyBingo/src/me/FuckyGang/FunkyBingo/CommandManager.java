package me.FuckyGang.FunkyBingo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandManager
{
	private Manager manager;
	
	public CommandManager()
	{
		this.manager = new Manager();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("bc"))
		{
			switch(args[0])
			{
				case "create":
					if (areIntegers(args[1], args[2]))
					{
						int diff = Integer.parseInt(args[1]);
						int size = Integer.parseInt(args[2]);
						if (diff >= 0 && diff <= 2 && size >= 0 && size <= 5)
						{
							manager.createCard(diff, size);
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
				case "reset":
					break;
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
