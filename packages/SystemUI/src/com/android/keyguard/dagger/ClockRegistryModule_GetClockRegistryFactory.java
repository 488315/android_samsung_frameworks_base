package com.android.keyguard.dagger;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.clocks.DefaultClockProvider;
import com.android.systemui.util.ThreadAssert;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ClockRegistryModule_GetClockRegistryFactory implements Provider {
    public final javax.inject.Provider bgDispatcherProvider;
    public final javax.inject.Provider clockBuffersProvider;
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider featureFlagsProvider;
    public final javax.inject.Provider layoutInflaterProvider;
    public final javax.inject.Provider mainDispatcherProvider;
    public final javax.inject.Provider pluginManagerProvider;
    public final javax.inject.Provider resourcesProvider;
    public final javax.inject.Provider scopeProvider;

    public ClockRegistryModule_GetClockRegistryFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
        this.contextProvider = provider;
        this.pluginManagerProvider = provider2;
        this.scopeProvider = provider3;
        this.mainDispatcherProvider = provider4;
        this.bgDispatcherProvider = provider5;
        this.featureFlagsProvider = provider6;
        this.resourcesProvider = provider7;
        this.layoutInflaterProvider = provider8;
        this.clockBuffersProvider = provider9;
    }

    public static ClockRegistry getClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, FeatureFlags featureFlags, Resources resources, LayoutInflater layoutInflater, ClockMessageBuffers clockMessageBuffers) {
        FeatureFlagsClassicRelease featureFlagsClassicRelease = (FeatureFlagsClassicRelease) featureFlags;
        boolean isEnabled = featureFlagsClassicRelease.isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS);
        boolean isEnabled2 = featureFlagsClassicRelease.isEnabled(Flags.STEP_CLOCK_ANIMATION);
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        com.android.systemui.Flags.migrateClocksToBlueprint();
        ClockRegistry clockRegistry = new ClockRegistry(context, pluginManager, coroutineScope, coroutineDispatcher, coroutineDispatcher2, isEnabled, true, new DefaultClockProvider(context, layoutInflater, resources, isEnabled2, false), context.getString(R.string.lockscreen_clock_id_fallback), clockMessageBuffers, false, "System", featureFlagsClassicRelease.isEnabled(Flags.TRANSIT_CLOCK), new ThreadAssert());
        clockRegistry.registerListeners();
        return clockRegistry;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return getClockRegistry((Context) this.contextProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (CoroutineScope) this.scopeProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (Resources) this.resourcesProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get(), (ClockMessageBuffers) this.clockBuffersProvider.get());
    }
}
