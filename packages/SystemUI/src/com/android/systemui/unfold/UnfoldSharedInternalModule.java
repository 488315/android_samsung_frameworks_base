package com.android.systemui.unfold;

import android.os.Handler;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class UnfoldSharedInternalModule {
    public static Optional createOptionalUnfoldTransitionProgressProvider(UnfoldTransitionConfig unfoldTransitionConfig, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1 anonymousClass1, ATraceLoggerTransitionProgressListener aTraceLoggerTransitionProgressListener, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3 anonymousClass3, Provider provider, FoldStateProvider foldStateProvider, Handler handler) {
        ResourceUnfoldTransitionConfig resourceUnfoldTransitionConfig = (ResourceUnfoldTransitionConfig) unfoldTransitionConfig;
        if (!((Boolean) resourceUnfoldTransitionConfig.isEnabled$delegate.getValue()).booleanValue()) {
            return Optional.empty();
        }
        ScaleAwareTransitionProgressProvider scaleAwareTransitionProgressProviderWrap = anonymousClass1.wrap((UnfoldTransitionProgressProvider) (((Boolean) resourceUnfoldTransitionConfig.isHingeAngleEnabled$delegate.getValue()).booleanValue() ? anonymousClass3.create(foldStateProvider, handler) : provider.get()));
        scaleAwareTransitionProgressProviderWrap.scopedUnfoldTransitionProgressProvider.listeners.add(aTraceLoggerTransitionProgressListener);
        return Optional.of(scaleAwareTransitionProgressProviderWrap);
    }
}
