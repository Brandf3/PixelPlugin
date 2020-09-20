package com.gmail.brandf3.testproject.commands;

import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import com.pixelmongenerations.core.Pixelmon;
import com.pixelmongenerations.core.enums.EnumGui;
import com.pixelmongenerations.core.network.PixelmonData;
import com.pixelmongenerations.core.network.packetHandlers.pcClientStorage.PCAdd;
import com.pixelmongenerations.core.network.packetHandlers.pcClientStorage.PCClear;
import com.pixelmongenerations.core.network.packetHandlers.pcClientStorage.PCLastBox;
import com.pixelmongenerations.core.storage.ComputerBox;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerComputerStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PC implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Player player = (Player) src;
		World world = (World) ((Player) src).getWorld();
		
		String playername = args.<String>getOne("player").get();
		
		EntityPlayer newOT = world.getPlayerEntityByName(playername);
		
		//PlayerStorage party = PixelmonStorage.pokeBallManager.getPlayerStorageFromUUID(player.getUniqueId()).get();
		
		//PixelmonStorage.computerManager.getPlayerStorageOffline(arg0, newOT.get)
		
		//PixelmonStorage.computerManager.getPlayerStorageFromUUID(world, player.getUniqueId()).getBoxList();
		
		
		CompletableFuture.runAsync(() -> {
			Pixelmon.NETWORK.sendTo(new PCClear(), (EntityPlayerMP) player);
			PlayerComputerStorage storage = PixelmonStorage.computerManager.getPlayerStorage((EntityPlayerMP) newOT);
			Pixelmon.NETWORK.sendTo(new PCLastBox(storage.lastBoxOpen), (EntityPlayerMP) player);
			ComputerBox[] boxes = storage.getBoxList();
			
			for (int i = 0; i < boxes.length; ++i) {
				ComputerBox box = boxes[i];
				NBTTagCompound[] tags = box.getStoredPokemon();
				
				for (int j = 0; j < tags.length; ++j) {
					NBTTagCompound pokemonData = tags[j];
					if (pokemonData != null) {
						PixelmonData pokemon = new PixelmonData(pokemonData);
						Pixelmon.NETWORK.sendTo(new PCAdd(pokemon), (EntityPlayerMP) player);
					}
				}
			}
		}).thenAccept((v) -> {
			((EntityPlayerMP) player).openGui(Pixelmon.INSTANCE, EnumGui.PC.getIndex(), world, 0, 0, 0);
		});

		return CommandResult.success();
		
	}

}