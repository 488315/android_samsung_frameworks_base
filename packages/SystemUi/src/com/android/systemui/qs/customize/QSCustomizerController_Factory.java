package com.android.systemui.qs.customize;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.qs.QSHost;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSCustomizerController_Factory implements Provider {
    public final Provider configurationControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider lightBarControllerProvider;
    public final Provider qsHostProvider;
    public final Provider screenLifecycleProvider;
    public final Provider tileAdapterProvider;
    public final Provider tileQueryHelperProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider viewProvider;

    public QSCustomizerController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.viewProvider = provider;
        this.tileQueryHelperProvider = provider2;
        this.qsHostProvider = provider3;
        this.tileAdapterProvider = provider4;
        this.screenLifecycleProvider = provider5;
        this.keyguardStateControllerProvider = provider6;
        this.lightBarControllerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.uiEventLoggerProvider = provider9;
    }

    public static QSCustomizerController newInstance(QSCustomizer qSCustomizer, TileQueryHelper tileQueryHelper, QSHost qSHost, TileAdapter tileAdapter, ScreenLifecycle screenLifecycle, KeyguardStateController keyguardStateController, LightBarController lightBarController, ConfigurationController configurationController, UiEventLogger uiEventLogger) {
        return new QSCustomizerController(qSCustomizer, tileQueryHelper, qSHost, tileAdapter, screenLifecycle, keyguardStateController, lightBarController, configurationController, uiEventLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((QSCustomizer) this.viewProvider.get(), (TileQueryHelper) this.tileQueryHelperProvider.get(), (QSHost) this.qsHostProvider.get(), (TileAdapter) this.tileAdapterProvider.get(), (ScreenLifecycle) this.screenLifecycleProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (LightBarController) this.lightBarControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
    }
}
