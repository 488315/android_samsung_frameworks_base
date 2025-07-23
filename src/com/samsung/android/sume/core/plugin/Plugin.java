package com.samsung.android.sume.core.plugin;

import com.samsung.android.sume.core.plugin.PluginFixture;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public interface Plugin<T extends PluginFixture<?>> {
    void bindToFixture(T t);

    default Stream<Plugin<? extends PluginFixture<?>>> stream() {
        return Stream.of(this);
    }
}
