package com.android.systemui.statusbar.notification.dagger;

import com.android.settingslib.notification.data.repository.ZenModeRepository;
import com.android.settingslib.notification.domain.interactor.NotificationsSoundPolicyInteractor;
import dagger.internal.Provider;

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
