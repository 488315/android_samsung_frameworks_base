package com.android.systemui.statusbar.notification.dagger;

import com.android.settingslib.notification.data.repository.ZenModeRepository;
import com.android.settingslib.notification.domain.interactor.NotificationsSoundPolicyInteractor;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationsModule_ProvideNotificationsSoundPolicyInteractorFactory implements Provider {
    public final Provider repositoryProvider;

    public NotificationsModule_ProvideNotificationsSoundPolicyInteractorFactory(Provider provider) {
        this.repositoryProvider = provider;
    }

    public static NotificationsSoundPolicyInteractor provideNotificationsSoundPolicyInteractor(ZenModeRepository zenModeRepository) {
        return new NotificationsSoundPolicyInteractor(zenModeRepository);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotificationsSoundPolicyInteractor((ZenModeRepository) this.repositoryProvider.get());
    }
}
