package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.settings.UserTracker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceProviderClientFactoryImpl implements KeyguardQuickAffordanceProviderClientFactory {
    public final CoroutineDispatcher backgroundDispatcher;
    public final UserTracker userTracker;

    public KeyguardQuickAffordanceProviderClientFactoryImpl(UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userTracker = userTracker;
        this.backgroundDispatcher = coroutineDispatcher;
    }
}
