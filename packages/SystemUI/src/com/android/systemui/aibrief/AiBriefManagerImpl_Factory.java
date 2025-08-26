package com.android.systemui.aibrief;

import com.android.systemui.aibrief.control.BriefNotificationController;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class AiBriefManagerImpl_Factory implements Provider {
    private final Provider faceWidgetNotificationControllerWrapperProvider;
    private final Provider loggerProvider;
    private final Provider notificationControllerProvider;
    private final Provider nowBarControllerProvider;
    private final Provider viewControllerProvider;

    public AiBriefManagerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.loggerProvider = provider;
        this.viewControllerProvider = provider2;
        this.nowBarControllerProvider = provider3;
        this.notificationControllerProvider = provider4;
        this.faceWidgetNotificationControllerWrapperProvider = provider5;
    }

    public static AiBriefManagerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new AiBriefManagerImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static AiBriefManagerImpl newInstance(BriefLogger briefLogger, BriefViewController briefViewController, BriefNowBarController briefNowBarController, BriefNotificationController briefNotificationController, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
        return new AiBriefManagerImpl(briefLogger, briefViewController, briefNowBarController, briefNotificationController, faceWidgetNotificationControllerWrapper);
    }

    public static AiBriefManagerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new AiBriefManagerImpl_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public AiBriefManagerImpl get() {
        return newInstance((BriefLogger) this.loggerProvider.get(), (BriefViewController) this.viewControllerProvider.get(), (BriefNowBarController) this.nowBarControllerProvider.get(), (BriefNotificationController) this.notificationControllerProvider.get(), (FaceWidgetNotificationControllerWrapper) this.faceWidgetNotificationControllerWrapperProvider.get());
    }
}
