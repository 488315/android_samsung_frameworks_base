package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotifTimeSortCoordnator_Factory implements Provider {
    private final javax.inject.Provider mHeadsUpManagerProvider;
    private final javax.inject.Provider settingsHelperProvider;
    private final javax.inject.Provider systemClockProvider;

    public NotifTimeSortCoordnator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.settingsHelperProvider = provider;
        this.systemClockProvider = provider2;
        this.mHeadsUpManagerProvider = provider3;
    }

    public static NotifTimeSortCoordnator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new NotifTimeSortCoordnator_Factory(provider, provider2, provider3);
    }

    public static NotifTimeSortCoordnator newInstance(SettingsHelper settingsHelper, SystemClock systemClock, HeadsUpManager headsUpManager) {
        return new NotifTimeSortCoordnator(settingsHelper, systemClock, headsUpManager);
    }

    @Override // javax.inject.Provider
    public NotifTimeSortCoordnator get() {
        return newInstance((SettingsHelper) this.settingsHelperProvider.get(), (SystemClock) this.systemClockProvider.get(), (HeadsUpManager) this.mHeadsUpManagerProvider.get());
    }
}
