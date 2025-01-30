package com.android.keyguard.dagger;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.clocks.DefaultClockProvider;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClockRegistryModule_GetClockRegistryFactory implements Provider {
    public final Provider bgDispatcherProvider;
    public final Provider contextProvider;
    public final Provider featureFlagsProvider;
    public final Provider layoutInflaterProvider;
    public final Provider logBufferProvider;
    public final Provider mainDispatcherProvider;
    public final Provider pluginManagerProvider;
    public final Provider resourcesProvider;
    public final Provider scopeProvider;

    public ClockRegistryModule_GetClockRegistryFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.pluginManagerProvider = provider2;
        this.scopeProvider = provider3;
        this.mainDispatcherProvider = provider4;
        this.bgDispatcherProvider = provider5;
        this.featureFlagsProvider = provider6;
        this.resourcesProvider = provider7;
        this.layoutInflaterProvider = provider8;
        this.logBufferProvider = provider9;
    }

    public static ClockRegistry getClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, FeatureFlags featureFlags, Resources resources, LayoutInflater layoutInflater, LogBuffer logBuffer) {
        FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) featureFlags;
        ClockRegistry clockRegistry = new ClockRegistry(context, pluginManager, coroutineScope, coroutineDispatcher, coroutineDispatcher2, featureFlagsRelease.isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS), true, new DefaultClockProvider(context, layoutInflater, resources, featureFlagsRelease.isEnabled(Flags.STEP_CLOCK_ANIMATION)), context.getString(R.string.lockscreen_clock_id_fallback), logBuffer, false, "System");
        clockRegistry.registerListeners();
        return clockRegistry;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return getClockRegistry((Context) this.contextProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (CoroutineScope) this.scopeProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (Resources) this.resourcesProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get(), (LogBuffer) this.logBufferProvider.get());
    }
}
