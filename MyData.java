package org.determine.content.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class MyData extends AbstractData<MyData, MyImmutableData> {

    private boolean flag;

    MyData(boolean flag) {
        this.flag = flag;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(ContentDetermineKeys.BOOL_ENABLED, () -> this.flag);
        registerFieldSetter(ContentDetermineKeys.BOOL_ENABLED, flag -> this.flag = flag);
        registerKeyValue(ContentDetermineKeys.BOOL_ENABLED, this::flag);
    }

    public Value<Boolean> flag() {
        return Sponge.getRegistry().getValueFactory().createValue(ContentDetermineKeys.BOOL_ENABLED, flag);
    }

    @Override
    public Optional<MyData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<MyData> otherData_ = dataHolder.get(MyData.class);
        if (otherData_.isPresent()) {
            MyData otherData = otherData_.get();
            MyData finalData = overlap.merge(this, otherData);
            this.flag = finalData.flag;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<MyData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<MyData> from(DataView view) {
        if (view.contains(ContentDetermineKeys.BOOL_ENABLED.getQuery())) {
            this.flag = view.getObject(ContentDetermineKeys.BOOL_ENABLED.getQuery(), Boolean.class).get();
            return Optional.of(this);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public MyData copy() {
        return new MyData(this.flag);
    }

    @Override
    public MyImmutableData asImmutable() {
        return new MyImmutableData(this.flag);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(ContentDetermineKeys.BOOL_ENABLED.getQuery(), this.flag);
    }
}
