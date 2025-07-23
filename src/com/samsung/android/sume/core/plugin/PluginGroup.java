package com.samsung.android.sume.core.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class PluginGroup implements Plugin<PluginFixture<?>> {
    protected List<Plugin<?>> plugins = new ArrayList();

    @Override // com.samsung.android.sume.core.plugin.Plugin
    public void bindToFixture(PluginFixture<?> pluginFixture) {
        throw new UnsupportedOperationException("use stream for PluginGroup");
    }

    @Override // com.samsung.android.sume.core.plugin.Plugin
    public Stream<Plugin<? extends PluginFixture<?>>> stream() {
        return this.plugins.stream();
    }

    public void addImgpPlugin(Consumer<ImgpPlugin> consumer) {
        this.plugins.add(new PluginAdapter(ImgpPlugin.class, consumer));
    }

    public void addNNPlugin(Consumer<NNPlugin> consumer) {
        this.plugins.add(new PluginAdapter(NNPlugin.class, consumer));
    }
}
