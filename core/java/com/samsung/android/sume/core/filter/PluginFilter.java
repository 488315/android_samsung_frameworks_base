package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.plugin.PluginFixture;

/* loaded from: classes6.dex */
public abstract class PluginFilter<T extends PluginFixture<?>> implements MediaFilter {
    protected T plugin;

    PluginFilter(T plugin) {
        this.plugin = plugin;
    }
}
