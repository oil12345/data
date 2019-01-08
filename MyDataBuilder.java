package org.determine.content.data;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class MyDataBuilder extends AbstractDataBuilder<MyData> implements DataManipulatorBuilder<MyData, MyImmutableData> {

    public MyDataBuilder() {
        super(MyData.class, 1);
    }

    @Override
    public MyData create() {
        return new MyData(false);
    }

    @Override
    public Optional<MyData> createFrom(DataHolder dataHolder) {
        return create().fill(dataHolder);
    }

    @Override
    protected Optional<MyData> buildContent(DataView container) throws InvalidDataException {
        return create().from(container);
    }
}