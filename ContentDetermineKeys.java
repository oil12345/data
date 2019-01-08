package org.determine.content.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class ContentDetermineKeys {
    private ContentDetermineKeys() {}
    public static void dummy() {} // invoke static constructor
    public static final Key<Value<Boolean>> BOOL_ENABLED;

    static {
        BOOL_ENABLED = Key.builder()
                .type(TypeTokens.BOOLEAN_VALUE_TOKEN)
                .id("content_determine:bool_enabled")
                .name("Bool Enabled")
                .query(DataQuery.of('.', "bool.enabled"))
                .build();
    }
}
