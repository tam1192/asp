package com.outlook.tamkame123.asp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Commands implements TabExecutor {

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {
		List<String> completeList = new ArrayList<String>();
		// switch(args.length) {
		// case 1:
		completeList.add("true");
		completeList.add("false");
		// break;
		// }

		return completeList;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		switch (args.length) {
			case 0:
				if (PlayerChecker.isDisabled()) {
					sender.sendMessage("asp is disabled");
				} else {
					sender.sendMessage("asp is enabled");
				}
				return true;
			case 1:
				switch (args[0]) {
					case "true":
						sender.sendMessage("asp is enabled!");
						PlayerChecker.isDisabled(false);
						return true;
					case "false":
						sender.sendMessage("asp is disabled!");
						PlayerChecker.isDisabled(true);
						return true;
					default:
						return false;
				}
		}
		return false;
	}
}
