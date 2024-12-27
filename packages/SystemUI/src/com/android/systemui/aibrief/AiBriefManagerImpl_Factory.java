package com.android.systemui.aibrief;

import com.android.systemui.aibrief.control.BriefNotificationController;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AiBriefManagerImpl_Factory implements Provider {
    private final javax.inject.Provider loggerProvider;
    private final javax.inject.Provider notificationControllerProvider;
    private final javax.inject.Provider nowBarControllerProvider;
    private final javax.inject.Provider viewControllerProvider;

    public AiBriefManagerImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.loggerProvider = provider;
        this.viewControllerProvider = provider2;
        this.nowBarControllerProvider = provider3;
        this.notificationControllerProvider = provider4;
    }

    public static AiBriefManagerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new AiBriefManagerImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static AiBriefManagerImpl newInstance(BriefLogger briefLogger, BriefViewController briefViewController, BriefNowBarController briefNowBarController, BriefNotificationController briefNotificationController) {
        return new AiBriefManagerImpl(briefLogger, briefViewController, briefNowBarController, briefNotificationController);
    }

    @Override // javax.inject.Provider
    public AiBriefManagerImpl get() {
        return newInstance((BriefLogger) this.loggerProvider.get(), (BriefViewController) this.viewControllerProvider.get(), (BriefNowBarController) this.nowBarControllerProvider.get(), (BriefNotificationController) this.notificationControllerProvider.get());
    }
}
