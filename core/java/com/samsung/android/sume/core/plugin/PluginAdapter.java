package com.samsung.android.sume.core.plugin;

import java.util.function.Consumer;

/* loaded from: classes6.dex */
public class PluginAdapter<T extends PluginFixture<?>> implements Plugin<T> {
    private final Consumer<T> function;
    private final Class<T> pluginType;

    public PluginAdapter(Class<T> pluginType, Consumer<T> function) {
        this.pluginType = pluginType;
        this.function = function;
    }

    public Class<T> getPluginType() {
        return this.pluginType;
    }

    @Override // com.samsung.android.sume.core.plugin.Plugin
    public void bindToFixture(T fixture) {
        this.function.accept(fixture);
    }
}
