package com.android.systemui.biometrics;

import android.content.Context;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricNotificationBroadcastReceiver_Factory implements Provider {
    public final Provider contextProvider;
    public final Provider notificationDialogFactoryProvider;

    public BiometricNotificationBroadcastReceiver_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.notificationDialogFactoryProvider = provider2;
    }

    public static BiometricNotificationBroadcastReceiver newInstance(Context context, BiometricNotificationDialogFactory biometricNotificationDialogFactory) {
        return new BiometricNotificationBroadcastReceiver(context, biometricNotificationDialogFactory);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BiometricNotificationBroadcastReceiver((Context) this.contextProvider.get(), (BiometricNotificationDialogFactory) this.notificationDialogFactoryProvider.get());
    }
}
