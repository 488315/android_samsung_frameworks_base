package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ColorizedFgsCoordinator_Factory implements Provider {
    private final Provider mainScopeProvider;
    private final Provider promotedNotificationsInteractorProvider;

    public ColorizedFgsCoordinator_Factory(Provider provider, Provider provider2) {
        this.mainScopeProvider = provider;
        this.promotedNotificationsInteractorProvider = provider2;
    }

    public static ColorizedFgsCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new ColorizedFgsCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static ColorizedFgsCoordinator newInstance(CoroutineScope coroutineScope, PromotedNotificationsInteractor promotedNotificationsInteractor) {
        return new ColorizedFgsCoordinator(coroutineScope, promotedNotificationsInteractor);
    }

    public static ColorizedFgsCoordinator_Factory create(Provider provider, Provider provider2) {
        return new ColorizedFgsCoordinator_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ColorizedFgsCoordinator get() {
        return newInstance((CoroutineScope) this.mainScopeProvider.get(), (PromotedNotificationsInteractor) this.promotedNotificationsInteractorProvider.get());
    }
}
