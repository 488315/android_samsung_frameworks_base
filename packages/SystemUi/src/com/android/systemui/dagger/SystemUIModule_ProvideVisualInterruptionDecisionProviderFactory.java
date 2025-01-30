package com.android.systemui.dagger;

import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIModule_ProvideVisualInterruptionDecisionProviderFactory implements Provider {
    public final Provider innerProvider;

    public SystemUIModule_ProvideVisualInterruptionDecisionProviderFactory(Provider provider) {
        this.innerProvider = provider;
    }

    public static NotificationInterruptStateProviderWrapper provideVisualInterruptionDecisionProvider(NotificationInterruptStateProvider notificationInterruptStateProvider) {
        return new NotificationInterruptStateProviderWrapper(notificationInterruptStateProvider);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotificationInterruptStateProviderWrapper((NotificationInterruptStateProvider) this.innerProvider.get());
    }
}
