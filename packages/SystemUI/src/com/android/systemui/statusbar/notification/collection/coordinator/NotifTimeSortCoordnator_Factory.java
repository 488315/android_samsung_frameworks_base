package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class NotifTimeSortCoordnator_Factory implements Provider {
    private final Provider mHeadsUpManagerProvider;
    private final Provider settingsHelperProvider;
    private final Provider systemClockProvider;

    public NotifTimeSortCoordnator_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.settingsHelperProvider = provider;
        this.systemClockProvider = provider2;
        this.mHeadsUpManagerProvider = provider3;
    }

    public static NotifTimeSortCoordnator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new NotifTimeSortCoordnator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static NotifTimeSortCoordnator newInstance(SettingsHelper settingsHelper, SystemClock systemClock, HeadsUpManager headsUpManager) {
        return new NotifTimeSortCoordnator(settingsHelper, systemClock, headsUpManager);
    }

    public static NotifTimeSortCoordnator_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new NotifTimeSortCoordnator_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public NotifTimeSortCoordnator get() {
        return newInstance((SettingsHelper) this.settingsHelperProvider.get(), (SystemClock) this.systemClockProvider.get(), (HeadsUpManager) this.mHeadsUpManagerProvider.get());
    }
}
