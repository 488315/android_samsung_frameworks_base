package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VoipCallPopUpCoordinator_Factory implements Provider {
    private final Provider executorProvider;
    private final Provider headsUpManagerProvider;
    private final Provider interactorProvider;
    private final Provider shadeControllerProvider;

    public VoipCallPopUpCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.interactorProvider = provider;
        this.executorProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.shadeControllerProvider = provider4;
    }

    public static VoipCallPopUpCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new VoipCallPopUpCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static VoipCallPopUpCoordinator newInstance(SecQSExpansionStateInteractor secQSExpansionStateInteractor, DelayableExecutor delayableExecutor, HeadsUpManager headsUpManager, ShadeController shadeController) {
        return new VoipCallPopUpCoordinator(secQSExpansionStateInteractor, delayableExecutor, headsUpManager, shadeController);
    }

    public static VoipCallPopUpCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new VoipCallPopUpCoordinator_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public VoipCallPopUpCoordinator get() {
        return newInstance((SecQSExpansionStateInteractor) this.interactorProvider.get(), (DelayableExecutor) this.executorProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (ShadeController) this.shadeControllerProvider.get());
    }
}
