

@Plugin(id = "content_determine",name = "ContentDetermine plugin", version = "0.1", description = "Plugin for our server")
public class Main {

	@Inject
	PluginContainer container;

	public void preInit(GamePreInitializationEvent e) {
		ContentDetermineKeys.dummy();

		DataRegistration.builder()
				.dataName("My Bool Data")
				.manipulatorId("bool_data")
				.dataClass(MyData.class)
				.immutableClass(MyImmutableData.class)
				.builder(new MyDataBuilder())
				.buildAndRegister(container);

	}

	@Listener
	public void onInteract(InteractBlockEvent.Secondary event) {
		Player player = event.getCause().first(Player.class).get();
		World world = Sponge.getGame().getServer().getWorld(AirshipModule.getInstance().getManager().getBase().getOptions().getVacuumWorld()).get();
		Vector3i pos = event.getTargetBlock().getPosition();
		Location<World> loc = new Location<World>(world, pos);

		if(player.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(ItemTypes.APPLE)) {

			BlockState bs = BlockState.builder()
					.blockType(BlockTypes.BONE_BLOCK)//add(new BoolDataImpl(true))
					.add(ContentDetermineKeys.BOOL_ENABLED, true)
					.build();

			loc.setBlock(bs);

		} else if(player.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(ItemTypes.BONE)) {

			Optional<Boolean> bs = event.getTargetBlock().getState().get(ContentDetermineKeys.BOOL_ENABLED);
			player.sendMessage(Text.of(bs.get()));
		}
	}


}
