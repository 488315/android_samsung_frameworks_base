package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import android.safetycenter.SafetyCenterManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CameraToggleTile_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider backgroundLooperProvider;
    public final Provider falsingManagerProvider;
    public final Provider hostProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider mainHandlerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider panelInteractorProvider;
    public final Provider qsLoggerProvider;
    public final Provider safetyCenterManagerProvider;
    public final Provider sensorPrivacyControllerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider uiEventLoggerProvider;

    public CameraToggleTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        this.hostProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.backgroundLooperProvider = provider3;
        this.mainHandlerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.falsingManagerProvider = provider6;
        this.statusBarStateControllerProvider = provider7;
        this.activityStarterProvider = provider8;
        this.qsLoggerProvider = provider9;
        this.sensorPrivacyControllerProvider = provider10;
        this.keyguardStateControllerProvider = provider11;
        this.safetyCenterManagerProvider = provider12;
        this.panelInteractorProvider = provider13;
    }

    public static CameraToggleTile newInstance(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, MetricsLogger metricsLogger, FalsingManager falsingManager, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController, SafetyCenterManager safetyCenterManager, PanelInteractor panelInteractor) {
        return new CameraToggleTile(qSHost, qsEventLogger, looper, handler, metricsLogger, falsingManager, statusBarStateController, activityStarter, qSLogger, individualSensorPrivacyController, keyguardStateController, safetyCenterManager, panelInteractor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new CameraToggleTile((QSHost) this.hostProvider.get(), (QsEventLogger) this.uiEventLoggerProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (IndividualSensorPrivacyController) this.sensorPrivacyControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (SafetyCenterManager) this.safetyCenterManagerProvider.get(), (PanelInteractor) this.panelInteractorProvider.get());
    }
}
