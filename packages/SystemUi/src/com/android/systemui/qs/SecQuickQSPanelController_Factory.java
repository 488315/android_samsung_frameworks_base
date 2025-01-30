package com.android.systemui.qs;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.logging.QSLogger;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQuickQSPanelController_Factory implements Provider {
    public final Provider barControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider panelHostProvider;
    public final Provider qsHostProvider;
    public final Provider qsLoggerProvider;
    public final Provider resourcePickerProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider viewProvider;

    public SecQuickQSPanelController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.viewProvider = provider;
        this.qsHostProvider = provider2;
        this.metricsLoggerProvider = provider3;
        this.uiEventLoggerProvider = provider4;
        this.qsLoggerProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.panelHostProvider = provider7;
        this.barControllerProvider = provider8;
        this.resourcePickerProvider = provider9;
    }

    public static SecQuickQSPanelController newInstance(SecQuickQSPanel secQuickQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, Provider provider, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        return new SecQuickQSPanelController(secQuickQSPanel, qSHost, metricsLogger, uiEventLogger, qSLogger, dumpManager, qSPanelHost, provider, secQSPanelResourcePicker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((SecQuickQSPanel) this.viewProvider.get(), (QSHost) this.qsHostProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (QSPanelHost) this.panelHostProvider.get(), this.barControllerProvider, (SecQSPanelResourcePicker) this.resourcePickerProvider.get());
    }
}
