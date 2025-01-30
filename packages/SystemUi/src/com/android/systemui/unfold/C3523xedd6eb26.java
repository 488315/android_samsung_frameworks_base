package com.android.systemui.unfold;

import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import java.util.ArrayList;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.unfold.UnfoldSharedInternalModule_UnfoldTransitionProgressProviderFactory */
/* loaded from: classes2.dex */
public final class C3523xedd6eb26 implements Provider {
    public final Provider configProvider;
    public final Provider fixedTimingTransitionProgressProvider;
    public final UnfoldSharedInternalModule module;
    public final Provider physicsBasedUnfoldTransitionProgressProvider;
    public final Provider scaleAwareProviderFactoryProvider;
    public final Provider tracingListenerProvider;

    public C3523xedd6eb26(UnfoldSharedInternalModule unfoldSharedInternalModule, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.module = unfoldSharedInternalModule;
        this.configProvider = provider;
        this.scaleAwareProviderFactoryProvider = provider2;
        this.tracingListenerProvider = provider3;
        this.physicsBasedUnfoldTransitionProgressProvider = provider4;
        this.fixedTimingTransitionProgressProvider = provider5;
    }

    public static Optional unfoldTransitionProgressProvider(UnfoldSharedInternalModule unfoldSharedInternalModule, UnfoldTransitionConfig unfoldTransitionConfig, ScaleAwareTransitionProgressProvider.Factory factory, ATraceLoggerTransitionProgressListener aTraceLoggerTransitionProgressListener, Provider provider, Provider provider2) {
        unfoldSharedInternalModule.getClass();
        ResourceUnfoldTransitionConfig resourceUnfoldTransitionConfig = (ResourceUnfoldTransitionConfig) unfoldTransitionConfig;
        if (!((Boolean) resourceUnfoldTransitionConfig.isEnabled$delegate.getValue()).booleanValue()) {
            return Optional.empty();
        }
        ScaleAwareTransitionProgressProvider wrap = factory.wrap((UnfoldTransitionProgressProvider) (((Boolean) resourceUnfoldTransitionConfig.isHingeAngleEnabled$delegate.getValue()).booleanValue() ? provider.get() : provider2.get()));
        ((ArrayList) wrap.scopedUnfoldTransitionProgressProvider.listeners).add(aTraceLoggerTransitionProgressListener);
        return Optional.of(wrap);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return unfoldTransitionProgressProvider(this.module, (UnfoldTransitionConfig) this.configProvider.get(), (ScaleAwareTransitionProgressProvider.Factory) this.scaleAwareProviderFactoryProvider.get(), (ATraceLoggerTransitionProgressListener) this.tracingListenerProvider.get(), this.physicsBasedUnfoldTransitionProgressProvider, this.fixedTimingTransitionProgressProvider);
    }
}
