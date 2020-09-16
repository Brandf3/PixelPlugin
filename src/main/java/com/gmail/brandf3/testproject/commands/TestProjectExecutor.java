package com.gmail.brandf3.testproject.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class TestProjectExecutor implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if (src instanceof Player) {
			Player player = (Player) src;
			//src.sendMessage(Text.of("Hello, " + player.getName()));
			src.sendMessage(Text.of("Ruby smells :)"));
			return CommandResult.success();
		}
		else if (src instanceof ConsoleSource) {
			src.sendMessage(Text.of("Hey Console."));
			return CommandResult.success();
		}
		else {
			src.sendMessage(Text.of("We don't know what you are."));
		}
		
		return CommandResult.empty();
	}

}
