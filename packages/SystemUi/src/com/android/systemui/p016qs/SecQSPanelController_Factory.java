package com.android.systemui.p016qs;

import android.os.Handler;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSPanelController_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider barControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider falsingManagerProvider;
    public final Provider mainHandlerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider panelHostProvider;
    public final Provider qsButtonGridControllerProvider;
    public final Provider qsHostProvider;
    public final Provider qsLoggerProvider;
    public final Provider resourcePickerProvider;
    public final Provider shadeHeaderControllerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;
    public final Provider statusBarWindowProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider viewProvider;

    public SecQSPanelController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.viewProvider = provider;
        this.qsHostProvider = provider2;
        this.metricsLoggerProvider = provider3;
        this.uiEventLoggerProvider = provider4;
        this.qsLoggerProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.panelHostProvider = provider7;
        this.barControllerProvider = provider8;
        this.resourcePickerProvider = provider9;
        this.falsingManagerProvider = provider10;
        this.statusBarKeyguardViewManagerProvider = provider11;
        this.activityStarterProvider = provider12;
        this.mainHandlerProvider = provider13;
        this.shadeHeaderControllerProvider = provider14;
        this.statusBarWindowProvider = provider15;
        this.qsButtonGridControllerProvider = provider16;
    }

    public static SecQSPanelController newInstance(SecQSPanel secQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, Provider provider, SecQSPanelResourcePicker secQSPanelResourcePicker, FalsingManager falsingManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ActivityStarter activityStarter, Handler handler, ShadeHeaderController shadeHeaderController, NotificationShadeWindowView notificationShadeWindowView, QSButtonGridController qSButtonGridController) {
        return new SecQSPanelController(secQSPanel, qSHost, metricsLogger, uiEventLogger, qSLogger, dumpManager, qSPanelHost, provider, secQSPanelResourcePicker, falsingManager, statusBarKeyguardViewManager, activityStarter, handler, shadeHeaderController, notificationShadeWindowView, qSButtonGridController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((SecQSPanel) this.viewProvider.get(), (QSHost) this.qsHostProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (QSPanelHost) this.panelHostProvider.get(), this.barControllerProvider, (SecQSPanelResourcePicker) this.resourcePickerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (Handler) this.mainHandlerProvider.get(), (ShadeHeaderController) this.shadeHeaderControllerProvider.get(), (NotificationShadeWindowView) this.statusBarWindowProvider.get(), (QSButtonGridController) this.qsButtonGridControllerProvider.get());
    }
}
