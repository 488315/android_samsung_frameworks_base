package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.settings.UserTracker;
import kotlinx.coroutines.CoroutineDispatcher;

public final class KeyguardQuickAffordanceProviderClientFactoryImpl implements KeyguardQuickAffordanceProviderClientFactory {
    public final CoroutineDispatcher backgroundDispatcher;
    public final UserTracker userTracker;

    public KeyguardQuickAffordanceProviderClientFactoryImpl(UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userTracker = userTracker;
        this.backgroundDispatcher = coroutineDispatcher;
    }
}
