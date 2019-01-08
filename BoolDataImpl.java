package org.determine.content.data;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.DataContentUpdater;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class BoolDataImpl extends AbstractBooleanData<BoolData, BoolData.Immutable> implements BoolData {
    public BoolDataImpl(boolean enabled) {
        super(enabled, ContentDetermineKeys.BOOL_ENABLED, false);
    }

    @Override
    public Value<Boolean> enabled() {
        return getValueGetter();
    }

    @Override
    public Optional<BoolData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<BoolDataImpl> data_ = dataHolder.get(BoolDataImpl.class);
        if (data_.isPresent()) {
            BoolDataImpl data = data_.get();
            BoolDataImpl finalData = overlap.merge(this, data);
            setValue(finalData.getValue());
        }
        return Optional.of(this);
    }

    @Override
    public Optional<BoolData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<BoolData> from(DataView view) {
        if (view.contains(ContentDetermineKeys.BOOL_ENABLED.getQuery())) {
            setValue(view.getBoolean(ContentDetermineKeys.BOOL_ENABLED.getQuery()).get());
            return Optional.of(this);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public BoolDataImpl copy() {
        return new BoolDataImpl(getValue());
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(getValue());
    }

    @Override
    public int getContentVersion() {
        return 2;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(ContentDetermineKeys.BOOL_ENABLED.getQuery(), getValue());
    }

    public static class Immutable extends AbstractImmutableBooleanData<BoolData.Immutable, BoolData> implements BoolData.Immutable {
        Immutable(boolean enabled) {
            super(enabled, ContentDetermineKeys.BOOL_ENABLED, false);
        }

        @Override
        public BoolDataImpl asMutable() {
            return new BoolDataImpl(getValue());
        }

        @Override
        public int getContentVersion() {
            return 2;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer().set(ContentDetermineKeys.BOOL_ENABLED.getQuery(), getValue());
        }

        @Override
        public ImmutableValue<Boolean> enabled() {
            return getValueGetter();
        }
    }
    public static class Builder extends AbstractDataBuilder<BoolData> implements BoolData.Builder {
        public Builder() {
            super(BoolData.class, 2);
        }

        @Override
        public BoolDataImpl create() {
            return new BoolDataImpl(false);
        }

        @Override
        public Optional<BoolData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<BoolData> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }
    }
    public static class BoolEnabled1To2Updater implements DataContentUpdater {

        @Override
        public int getInputVersion() {
            return 1;
        }

        @Override
        public int getOutputVersion() {
            return 2;
        }

        @Override
        public DataView update(DataView content) {
            return content.set(DataQuery.of('.', "bool.enabled"), content.get(DataQuery.of('.', "bool.isEnabled"))).remove(DataQuery.of('.', "bool.isEnabled"));
        }
    }
}
