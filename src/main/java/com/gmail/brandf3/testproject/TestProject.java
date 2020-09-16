package com.gmail.brandf3.testproject;

import com.gmail.brandf3.testproject.commands.*;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

@Plugin(id = "testproject", name = "Test Project", version = "1.0", description = "Testing building")
public class TestProject {
	
	@Inject
	private Logger logger;
	
	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		logger.info("Loading...");
		
		CommandSpec testCommand = CommandSpec.builder()
				.description(Text.of("Testing first command."))
				.executor(new TestProjectExecutor())
				.build();
		
		Sponge.getCommandManager().register(this, testCommand, Lists.newArrayList("testing"));
		
		logger.info("Successfully running TestProject.");
	}
}