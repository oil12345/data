package org.determine.content.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class MyImmutableData extends AbstractImmutableData<MyImmutableData, MyData> {

    private boolean flag;

    public MyImmutableData(boolean flag) {
        this.flag = flag;
        registerGetters();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(ContentDetermineKeys.BOOL_ENABLED, () -> this.flag);
        registerKeyValue(ContentDetermineKeys.BOOL_ENABLED, this::flag);
    }

    public ImmutableValue<Boolean> flag() {
        return Sponge.getRegistry().getValueFactory().createValue(ContentDetermineKeys.BOOL_ENABLED, flag).asImmutable();
    }

    @Override
    public MyData asMutable() {
        return new MyData(flag);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(ContentDetermineKeys.BOOL_ENABLED.getQuery(), this.flag)
                .set(ContentDetermineKeys.BOOL_ENABLED.getQuery(), this.flag);
    }
}
