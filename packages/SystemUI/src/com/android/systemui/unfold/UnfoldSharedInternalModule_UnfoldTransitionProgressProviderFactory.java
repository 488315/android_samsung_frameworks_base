package com.android.systemui.unfold;

import android.os.Handler;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldStateProvider;
import dagger.internal.Provider;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UnfoldSharedInternalModule_UnfoldTransitionProgressProviderFactory implements Provider {
    public final Provider bgProvider;
    public final Provider configProvider;
    public final Provider fixedTimingTransitionProgressProvider;
    public final Provider foldStateProvider;
    public final Provider mainHandlerProvider;
    public final Provider mainThreadUnfoldTransitionProgressProviderFactoryProvider;
    public final UnfoldSharedInternalModule module;
    public final Provider physicsBasedUnfoldTransitionProgressProvider;
    public final Provider scaleAwareProviderFactoryProvider;
    public final Provider tracingListenerProvider;
    public final Provider unfoldBgProgressFlagProvider;

    public UnfoldSharedInternalModule_UnfoldTransitionProgressProviderFactory(UnfoldSharedInternalModule unfoldSharedInternalModule, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
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

    public static Optional unfoldTransitionProgressProvider(UnfoldSharedInternalModule unfoldSharedInternalModule, UnfoldTransitionConfig unfoldTransitionConfig, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1 anonymousClass1, final DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass2 anonymousClass2, DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3 anonymousClass3, javax.inject.Provider provider, FoldStateProvider foldStateProvider, Handler handler, final DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass8 anonymousClass8, javax.inject.Provider provider2, Optional optional) {
        Optional createOptionalUnfoldTransitionProgressProvider;
        unfoldSharedInternalModule.getClass();
        Object obj = Boolean.FALSE;
        if (optional.isPresent()) {
            obj = optional.get();
        }
        if (((Boolean) obj).booleanValue()) {
            Optional optional2 = (Optional) provider2.get();
            final int i = 0;
            final Function1 function1 = new Function1() { // from class: com.android.systemui.unfold.UnfoldSharedInternalModule$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) obj2;
                    switch (i) {
                        case 0:
                            unfoldTransitionProgressProvider.getClass();
                            return ((DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass8) anonymousClass8).create(unfoldTransitionProgressProvider);
                        default:
                            unfoldTransitionProgressProvider.addCallback(((DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass2) anonymousClass8).create("MainThreadFromBgProgress"));
                            return Unit.INSTANCE;
                    }
                }
            };
            createOptionalUnfoldTransitionProgressProvider = optional2.map(new Function() { // from class: com.android.systemui.unfold.UnfoldSharedInternalModule$sam$java_util_function_Function$0
                @Override // java.util.function.Function
                public final /* synthetic */ Object apply(Object obj2) {
                    return Function1.this.mo779invoke(obj2);
                }
            });
            final int i2 = 1;
            final Function1 function12 = new Function1() { // from class: com.android.systemui.unfold.UnfoldSharedInternalModule$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) obj2;
                    switch (i2) {
                        case 0:
                            unfoldTransitionProgressProvider.getClass();
                            return ((DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass8) anonymousClass2).create(unfoldTransitionProgressProvider);
                        default:
                            unfoldTransitionProgressProvider.addCallback(((DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass2) anonymousClass2).create("MainThreadFromBgProgress"));
                            return Unit.INSTANCE;
                    }
                }
            };
            createOptionalUnfoldTransitionProgressProvider.ifPresent(new Consumer() { // from class: com.android.systemui.unfold.UnfoldSharedInternalModule$sam$java_util_function_Consumer$0
                @Override // java.util.function.Consumer
                public final /* synthetic */ void accept(Object obj2) {
                    Function1.this.mo779invoke(obj2);
                }
            });
        } else {
            createOptionalUnfoldTransitionProgressProvider = UnfoldSharedInternalModule.createOptionalUnfoldTransitionProgressProvider(unfoldTransitionConfig, anonymousClass1, anonymousClass2.create("MainThread"), anonymousClass3, provider, foldStateProvider, handler);
        }
        createOptionalUnfoldTransitionProgressProvider.getClass();
        return createOptionalUnfoldTransitionProgressProvider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        UnfoldTransitionConfig unfoldTransitionConfig = (UnfoldTransitionConfig) this.configProvider.get();
        DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1 anonymousClass1 = (DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass1) this.scaleAwareProviderFactoryProvider.get();
        DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass2 anonymousClass2 = (DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass2) this.tracingListenerProvider.get();
        DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3 anonymousClass3 = (DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass3) this.physicsBasedUnfoldTransitionProgressProvider.get();
        FoldStateProvider foldStateProvider = (FoldStateProvider) this.foldStateProvider.get();
        Handler handler = (Handler) this.mainHandlerProvider.get();
        DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass8 anonymousClass8 = (DaggerReferenceGlobalRootComponent.ReferenceGlobalRootComponentImpl.SwitchingProvider.AnonymousClass8) this.mainThreadUnfoldTransitionProgressProviderFactoryProvider.get();
        Optional optional = (Optional) this.unfoldBgProgressFlagProvider.get();
        return unfoldTransitionProgressProvider(this.module, unfoldTransitionConfig, anonymousClass1, anonymousClass2, anonymousClass3, this.fixedTimingTransitionProgressProvider, foldStateProvider, handler, anonymousClass8, this.bgProvider, optional);
    }
}
