package com.android.systemui.biometrics;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
