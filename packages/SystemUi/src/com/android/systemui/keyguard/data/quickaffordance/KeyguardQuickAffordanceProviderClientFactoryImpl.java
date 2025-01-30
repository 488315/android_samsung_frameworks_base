package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.settings.UserTracker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceProviderClientFactoryImpl implements KeyguardQuickAffordanceProviderClientFactory {
    public final CoroutineDispatcher backgroundDispatcher;
    public final UserTracker userTracker;

    public KeyguardQuickAffordanceProviderClientFactoryImpl(UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userTracker = userTracker;
        this.backgroundDispatcher = coroutineDispatcher;
    }
}
