package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.settings.UserTracker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardQuickAffordanceProviderClientFactoryImpl implements KeyguardQuickAffordanceProviderClientFactory {
    public final CoroutineDispatcher backgroundDispatcher;
    public final UserTracker userTracker;

    public KeyguardQuickAffordanceProviderClientFactoryImpl(UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userTracker = userTracker;
        this.backgroundDispatcher = coroutineDispatcher;
    }
}
