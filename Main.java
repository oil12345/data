package org.determine.content;

import java.nio.file.Path;
import java.util.Optional;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.World;
import org.determine.content.airship.AirshipModule;
import org.determine.content.chat.ChatModule;
import org.determine.content.config.ConfigModule;
import org.determine.content.data.BoolData;
import org.determine.content.data.BoolDataImpl;
import org.determine.content.data.ContentDetermineKeys;
import org.determine.content.database.DataBaseModule;
import org.determine.content.fuel.FuelModule;
import org.determine.content.gui.InterfaceModule;
import org.determine.content.item.ItemModule;
import org.determine.content.permission.PermissionModule;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;


@Plugin(id = "content_determine",name = "ContentDetermine plugin", version = "0.1", description = "Plugin for our server")
public class Main {

	@Inject
	PluginContainer container;
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	private Path ConfigDir;
	
	@Inject
	@DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;

	@Inject
    private Game gmae;
	
	private static PluginContainer plugin;
	
	public static PluginContainer getPlugin() {
		return plugin;
	}

	public void preInit(GamePreInitializationEvent e) {
		DataRegistration.builder()
				.dataName("My Bool Data")
				.manipulatorId("bool_data")
				.dataClass(BoolData.class)
				.dataImplementation(BoolDataImpl.class)
				.immutableClass(BoolData.Immutable.class)
				.immutableImplementation(BoolDataImpl.Immutable.class)
				.builder(new BoolDataImpl.Builder())
				.buildAndRegister(container);
		Sponge.getDataManager().registerContentUpdater(BoolDataImpl.class, new BoolDataImpl.BoolEnabled1To2Updater());
	}
	
	@Listener
	public void onStart(GameStartedServerEvent e) {
		plugin=Sponge.getPluginManager().getPlugin("content_determine").get();
		ConfigModule.getInstance().init(configLoader, ConfigDir);
		DataBaseModule.getInstance().init();
		ChatModule.getInstance().init();
		PermissionModule.getInstance().init();
		FuelModule.getInstance().init();
		ItemModule.getInstance().init();
		InterfaceModule.getInstance().init();

		AirshipModule.getInstance().init();

	}

	@Listener
	public void onInteract(InteractBlockEvent.Secondary event) {
		Player player = event.getCause().first(Player.class).get();
		World world = Sponge.getGame().getServer().getWorld(AirshipModule.getInstance().getManager().getBase().getOptions().getVacuumWorld()).get();
		Vector3i pos = event.getTargetBlock().getPosition();
		Location<World> loc = new Location<World>(world, pos);

		if(player.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(ItemTypes.APPLE)) {

			BlockState bs = BlockState.builder()
					.blockType(BlockTypes.BONE_BLOCK).add(new BoolDataImpl(true))
					//.add(ContentDetermineKeys.BOOL_ENABLED, true)
					.build();

			loc.setBlock(bs);

		} else if(player.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(ItemTypes.BONE)) {

			Optional<Boolean> bs = event.getTargetBlock().getState().get(ContentDetermineKeys.BOOL_ENABLED);
			player.sendMessage(Text.of(bs.get()));
		}
	}
}
