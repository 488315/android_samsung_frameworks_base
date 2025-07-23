package com.android.keyguard.dagger;

import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.view.LayoutInflater;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.clocks.DefaultClockProvider;
import com.android.systemui.util.ThreadAssert;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ClockRegistryModule_GetClockRegistryFactory implements Provider {
    public final Provider bgDispatcherProvider;
    public final Provider clockBuffersProvider;
    public final Provider contextProvider;
    public final Provider featureFlagsProvider;
    public final Provider layoutInflaterProvider;
    public final Provider mainDispatcherProvider;
    public final Provider pluginManagerProvider;
    public final Provider resourcesProvider;
    public final Provider scopeProvider;
    public final Provider vibratorProvider;

    public ClockRegistryModule_GetClockRegistryFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.contextProvider = provider;
        this.pluginManagerProvider = provider2;
        this.scopeProvider = provider3;
        this.mainDispatcherProvider = provider4;
        this.bgDispatcherProvider = provider5;
        this.featureFlagsProvider = provider6;
        this.resourcesProvider = provider7;
        this.layoutInflaterProvider = provider8;
        this.clockBuffersProvider = provider9;
        this.vibratorProvider = provider10;
    }

    public static ClockRegistry getClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, FeatureFlags featureFlags, Resources resources, LayoutInflater layoutInflater, ClockMessageBuffers clockMessageBuffers, Vibrator vibrator) {
        ClockRegistry clockRegistry = new ClockRegistry(context, pluginManager, coroutineScope, coroutineDispatcher, coroutineDispatcher2, ((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS), true, new DefaultClockProvider(context, layoutInflater, resources, false, vibrator), context.getString(R.string.lockscreen_clock_id_fallback), clockMessageBuffers, false, "System", new ThreadAssert());
        clockRegistry.registerListeners();
        return clockRegistry;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return getClockRegistry((Context) this.contextProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (CoroutineScope) this.scopeProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (Resources) this.resourcesProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get(), (ClockMessageBuffers) this.clockBuffersProvider.get(), (Vibrator) this.vibratorProvider.get());
    }
}
