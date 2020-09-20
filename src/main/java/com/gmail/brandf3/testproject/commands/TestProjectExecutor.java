package com.gmail.brandf3.testproject.commands;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class TestProjectExecutor implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if (src instanceof Player) {
			Player player = (Player) src;
			
			//src.sendMessage(Text.of("Hello, " + player.getName()));
			String name = args.<String>getOne("entity").get();
			
			//System.out.println(Sponge.getRegistry().getAllOf(EntityType.class));
			
			Optional<EntityType> opt = Sponge.getRegistry().getType(EntityType.class, name);
			
			if (opt.isPresent()) {
				EntityType type = opt.get();
				
				Location<World> world = player.getLocation();
				
				//Entity passenger = world.createEntity(EntityTypes.COW);
				Entity passenger = world.createEntity(type);
				
				//passenger.setTransform(passenger.getTransform().addScale(new Vector3d(0.1, 0.1, 0.1)));
				player.addPassenger(passenger);
				
			    try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
			        frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);

			        world.spawnEntity(passenger);
			    }
			    
				//src.sendMessage(Text.of("Ruby smells :)"));
				//Sponge.getCommandManager().process(player, "msg Brandon55604 test");
				//Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "kill Notch");
				return CommandResult.success();
			}
			return CommandResult.empty();

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
