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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UnfoldSharedInternalModule {
    public static Optional createOptionalUnfoldTransitionProgressProvider(UnfoldTransitionConfig unfoldTransitionConfig, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1 anonymousClass1, ATraceLoggerTransitionProgressListener aTraceLoggerTransitionProgressListener, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3 anonymousClass3, Provider provider, FoldStateProvider foldStateProvider, Handler handler) {
        ResourceUnfoldTransitionConfig resourceUnfoldTransitionConfig = (ResourceUnfoldTransitionConfig) unfoldTransitionConfig;
        if (!((Boolean) resourceUnfoldTransitionConfig.isEnabled$delegate.getValue()).booleanValue()) {
            return Optional.empty();
        }
        Object create = ((Boolean) resourceUnfoldTransitionConfig.isHingeAngleEnabled$delegate.getValue()).booleanValue() ? anonymousClass3.create(foldStateProvider, handler) : provider.get();
        Intrinsics.checkNotNull(create);
        ScaleAwareTransitionProgressProvider wrap = anonymousClass1.wrap((UnfoldTransitionProgressProvider) create);
        wrap.scopedUnfoldTransitionProgressProvider.listeners.add(aTraceLoggerTransitionProgressListener);
        return Optional.of(wrap);
    }
}
