package com.android.systemui.unfold;

import android.os.Handler;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.progress.MainThreadUnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener;
import dagger.internal.Provider;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UnfoldSharedInternalModule_UnfoldTransitionProgressProviderFactory implements Provider {
    public final javax.inject.Provider bgProvider;
    public final javax.inject.Provider configProvider;
    public final javax.inject.Provider fixedTimingTransitionProgressProvider;
    public final javax.inject.Provider foldStateProvider;
    public final javax.inject.Provider mainHandlerProvider;
    public final javax.inject.Provider mainThreadUnfoldTransitionProgressProviderFactoryProvider;
    public final UnfoldSharedInternalModule module;
    public final javax.inject.Provider physicsBasedUnfoldTransitionProgressProvider;
    public final javax.inject.Provider scaleAwareProviderFactoryProvider;
    public final javax.inject.Provider tracingListenerProvider;
    public final javax.inject.Provider unfoldBgProgressFlagProvider;

    public UnfoldSharedInternalModule_UnfoldTransitionProgressProviderFactory(UnfoldSharedInternalModule unfoldSharedInternalModule, javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        this.module = unfoldSharedInternalModule;
        this.configProvider = provider;
        this.scaleAwareProviderFactoryProvider = provider2;
        this.tracingListenerProvider = provider3;
        this.physicsBasedUnfoldTransitionProgressProvider = provider4;
        this.fixedTimingTransitionProgressProvider = provider5;
        this.foldStateProvider = provider6;
        this.mainHandlerProvider = provider7;
        this.mainThreadUnfoldTransitionProgressProviderFactoryProvider = provider8;
        this.bgProvider = provider9;
        this.unfoldBgProgressFlagProvider = provider10;
    }

    public static Optional unfoldTransitionProgressProvider(UnfoldSharedInternalModule unfoldSharedInternalModule, UnfoldTransitionConfig unfoldTransitionConfig, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1 anonymousClass1, final ATraceLoggerTransitionProgressListener.Factory factory, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3 anonymousClass3, javax.inject.Provider provider, FoldStateProvider foldStateProvider, Handler handler, final MainThreadUnfoldTransitionProgressProvider.Factory factory2, javax.inject.Provider provider2, Optional optional) {
        unfoldSharedInternalModule.getClass();
        Object obj = Boolean.FALSE;
        if (optional.isPresent()) {
            obj = optional.get();
        }
        if (!((Boolean) obj).booleanValue()) {
            return UnfoldSharedInternalModule.createOptionalUnfoldTransitionProgressProvider(unfoldTransitionConfig, anonymousClass1, factory.create("MainThread"), anonymousClass3, provider, foldStateProvider, handler);
        }
        Optional map = ((Optional) provider2.get()).map(new Function() { // from class: com.android.systemui.unfold.UnfoldSharedInternalModule$unfoldTransitionProgressProvider$mainThreadProvider$1
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) obj2;
                MainThreadUnfoldTransitionProgressProvider.Factory factory3 = MainThreadUnfoldTransitionProgressProvider.Factory.this;
                Intrinsics.checkNotNull(unfoldTransitionProgressProvider);
                return factory3.create(unfoldTransitionProgressProvider);
            }
        });
        map.ifPresent(new Consumer() { // from class: com.android.systemui.unfold.UnfoldSharedInternalModule$unfoldTransitionProgressProvider$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                ((UnfoldTransitionProgressProvider) obj2).addCallback(ATraceLoggerTransitionProgressListener.Factory.this.create("MainThreadFromBgProgress"));
            }
        });
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        UnfoldTransitionConfig unfoldTransitionConfig = (UnfoldTransitionConfig) this.configProvider.get();
        DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1 anonymousClass1 = (DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1) this.scaleAwareProviderFactoryProvider.get();
        ATraceLoggerTransitionProgressListener.Factory factory = (ATraceLoggerTransitionProgressListener.Factory) this.tracingListenerProvider.get();
        DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3 anonymousClass3 = (DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3) this.physicsBasedUnfoldTransitionProgressProvider.get();
        FoldStateProvider foldStateProvider = (FoldStateProvider) this.foldStateProvider.get();
        Handler handler = (Handler) this.mainHandlerProvider.get();
        MainThreadUnfoldTransitionProgressProvider.Factory factory2 = (MainThreadUnfoldTransitionProgressProvider.Factory) this.mainThreadUnfoldTransitionProgressProviderFactoryProvider.get();
        Optional optional = (Optional) this.unfoldBgProgressFlagProvider.get();
        return unfoldTransitionProgressProvider(this.module, unfoldTransitionConfig, anonymousClass1, factory, anonymousClass3, this.fixedTimingTransitionProgressProvider, foldStateProvider, handler, factory2, this.bgProvider, optional);
    }
}
