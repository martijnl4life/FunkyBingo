package me.FuckyGang.FunkyBingo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandManager 
{
	private AdvancementManager manager;
	private BingoCard card;
	
	public CommandManager(AdvancementManager manager)
	{
		this.manager = manager;
    	this.card = new BingoCard(manager.getRoot(), manager.getUninitialised());
	}
	
	public boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("bc"))
		{
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.RED + "" + "[FAILED]: no argument specified");
				return false;
			}
			if(args.length == 1)
			{
				
				// /bc reset  --> resets the bingo card
				if (args[0].equalsIgnoreCase("reset"))
				{
					card.resetCard();
					sender.sendMessage(ChatColor.GOLD + "" + "Bingo Card Reset!");
					return true;
				}
				
				sender.sendMessage(ChatColor.RED + "" + "[FAILED]: no argument specified");
				return false;
			}
			if(args.length == 3)
			{
				// /bc create <difficulty> <size>
				if (args[0].equalsIgnoreCase("create"))
				{
					if (isInteger(args[1]) && isInteger(args[2]))
					{
						int diff = Integer.parseInt(args[1]);
						int s = Integer.parseInt(args[2]);
						if (diff >= 0 && diff <= 2 && s > 0 && s <= 3)
						{
							if (card.isCreated())
							{
								sender.sendMessage(ChatColor.YELLOW + "" + "[FAILED]: there is already a card present");
							}
							else 
							{
								card.createCard(manager.getSelection(diff, s), s);
								sender.sendMessage(ChatColor.GOLD + "" + "Bingo Card Created!");
								return true;
							}
						}
					} 
				}
				sender.sendMessage(ChatColor.RED + "" + "[FAILED]: wrong arguments specified");
				return false;
			}
		}
		if (label.equalsIgnoreCase("log"))
		{
			if(args.length == 0)
			{
				card.logCard();
				return true;
			}
			sender.sendMessage(ChatColor.RED + "" + "[FAILED]: wrong argument specified");
			return false;
		}
		return false;
	}
	
	private boolean isInteger(String s)
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
		return true;
	}
}
