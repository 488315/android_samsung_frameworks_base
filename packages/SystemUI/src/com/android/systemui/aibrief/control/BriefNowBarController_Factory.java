package com.android.systemui.aibrief.control;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BriefNowBarController_Factory implements Provider {
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider faceWidgetNotificationControllerWrapperProvider;
    private final Provider loggerProvider;
    private final Provider mainHandlerProvider;
    private final Provider notificationShadeWindowControllerProvider;
    private final Provider powerInteractorProvider;
    private final Provider viewControllerProvider;

    public BriefNowBarController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.loggerProvider = provider2;
        this.viewControllerProvider = provider3;
        this.faceWidgetNotificationControllerWrapperProvider = provider4;
        this.notificationShadeWindowControllerProvider = provider5;
        this.powerInteractorProvider = provider6;
        this.configurationControllerProvider = provider7;
        this.mainHandlerProvider = provider8;
    }

    public static BriefNowBarController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
        return new BriefNowBarController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8));
    }

    public static BriefNowBarController newInstance(Context context, BriefLogger briefLogger, BriefViewController briefViewController, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, NotificationShadeWindowController notificationShadeWindowController, PowerInteractor powerInteractor, ConfigurationController configurationController, Handler handler) {
        return new BriefNowBarController(context, briefLogger, briefViewController, faceWidgetNotificationControllerWrapper, notificationShadeWindowController, powerInteractor, configurationController, handler);
    }

    public static BriefNowBarController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new BriefNowBarController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    @Override // javax.inject.Provider
    public BriefNowBarController get() {
        return newInstance((Context) this.contextProvider.get(), (BriefLogger) this.loggerProvider.get(), (BriefViewController) this.viewControllerProvider.get(), (FaceWidgetNotificationControllerWrapper) this.faceWidgetNotificationControllerWrapperProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (PowerInteractor) this.powerInteractorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}
