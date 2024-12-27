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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BriefNowBarController_Factory implements Provider {
    private final javax.inject.Provider configurationControllerProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider faceWidgetNotificationControllerWrapperProvider;
    private final javax.inject.Provider loggerProvider;
    private final javax.inject.Provider mainHandlerProvider;
    private final javax.inject.Provider notificationShadeWindowControllerProvider;
    private final javax.inject.Provider powerInteractorProvider;
    private final javax.inject.Provider viewControllerProvider;

    public BriefNowBarController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
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
        return new BriefNowBarController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static BriefNowBarController newInstance(Context context, BriefLogger briefLogger, BriefViewController briefViewController, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, NotificationShadeWindowController notificationShadeWindowController, PowerInteractor powerInteractor, ConfigurationController configurationController, Handler handler) {
        return new BriefNowBarController(context, briefLogger, briefViewController, faceWidgetNotificationControllerWrapper, notificationShadeWindowController, powerInteractor, configurationController, handler);
    }

    @Override // javax.inject.Provider
    public BriefNowBarController get() {
        return newInstance((Context) this.contextProvider.get(), (BriefLogger) this.loggerProvider.get(), (BriefViewController) this.viewControllerProvider.get(), (FaceWidgetNotificationControllerWrapper) this.faceWidgetNotificationControllerWrapperProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (PowerInteractor) this.powerInteractorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}
