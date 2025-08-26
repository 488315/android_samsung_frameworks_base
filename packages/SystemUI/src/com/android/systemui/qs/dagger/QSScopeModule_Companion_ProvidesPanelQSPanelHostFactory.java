package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QSScopeModule_Companion_ProvidesPanelQSPanelHostFactory implements Provider {
    public final Provider hostProvider;
    public final Provider metricsLoggerProvider;
    public final Provider resourcePickerProvider;
    public final Provider viewProvider;

    public QSScopeModule_Companion_ProvidesPanelQSPanelHostFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.viewProvider = provider;
        this.hostProvider = provider2;
        this.metricsLoggerProvider = provider3;
        this.resourcePickerProvider = provider4;
    }

    public static QSPanelHost providesPanelQSPanelHost(View view, QSHost qSHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        QSScopeModule.Companion.getClass();
        return new QSPanelHost(0, view.findViewById(R.id.quick_settings_panel), qSHost, metricsLogger, secQSPanelResourcePicker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesPanelQSPanelHost((View) this.viewProvider.get(), (QSHost) this.hostProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (SecQSPanelResourcePicker) this.resourcePickerProvider.get());
    }
}
