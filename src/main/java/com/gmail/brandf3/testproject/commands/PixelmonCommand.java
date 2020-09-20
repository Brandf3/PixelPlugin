package com.gmail.brandf3.testproject.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PixelmonCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Player player = (Player) src;
		World world = (World) ((Player) src).getWorld();
		
		EntityPlayer newOT = world.getPlayerEntityByName("RubyRevolution");
		
		PlayerStorage party = PixelmonStorage.pokeBallManager.getPlayerStorageFromUUID(player.getUniqueId()).get();
		
		PixelmonStorage.computerManager.getPlayerStorageFromUUID(world, player.getUniqueId()).getBoxList();
		int[] x = party.getIDFromPosition(0);
		EntityPixelmon pokemon = party.getPokemon(x, world);

		pokemon.originalTrainer = "RubyRevolution";
		pokemon.originalTrainerUUID = newOT.getUniqueID().toString();
		pokemon.updateStats();
		return CommandResult.success();
		
	}

}
