package com.gmail.brandf3.testproject;

import com.gmail.brandf3.testproject.commands.*;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
//import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
//import org.spongepowered.api.event.entity.InteractEntityEvent;
//import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;
//import com.pixelmongenerations.common.entity.pixelmon.EntityPixelmon;
//import com.pixelmongenerations.common.entity.pixelmon.stats.Gender;

@Plugin(id = "testproject", name = "Test Project", version = "1.0", description = "Testing building")
public class TestProject {
	
	@Inject
	private Logger logger;
	
	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		logger.info("Loading...");
		
		CommandSpec testCommand = CommandSpec.builder()
				.description(Text.of("Reverse mount an entity."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("entity"))))
				// 			 GenericArguments.remainingJoinedStrings(Text.of("message")))
				.executor(new TestProjectExecutor())
				.build();
		
		CommandSpec otChange = CommandSpec.builder()
				.description(Text.of("Change the OT of the given pokemon."))
				.executor(new PixelmonCommand())
				.build();
		
		CommandSpec PCSee = CommandSpec.builder()
				.description(Text.of("View another player's pc."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("player"))))
				.executor(new PC())
				.build();
		
		Sponge.getCommandManager().register(this, testCommand, "reverseMount");
		Sponge.getCommandManager().register(this, otChange, "changeot");
		Sponge.getCommandManager().register(this, PCSee, "pcsee");
		
		logger.info("Successfully running TestProject.");
	}
	
//	@Listener
//	public void onUse (InteractEntityEvent.Secondary.MainHand event, @Root Player player) {
//		if (event.getTargetEntity() instanceof EntityPixelmon) {
//			EntityPixelmon pokemon = (EntityPixelmon) event.getTargetEntity();
//			pokemon.setGender(Gender.Female);
//			pokemon.updateStats();
//			
//		}
//		int x = 5 + 7;
//	}
}