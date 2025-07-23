package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSScopeModule_Companion_ProvidesHeaderQSPanelHostFactory implements Provider {
    public final Provider hostProvider;
    public final Provider metricsLoggerProvider;
    public final Provider resourcePickerProvider;
    public final Provider viewProvider;

    public QSScopeModule_Companion_ProvidesHeaderQSPanelHostFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.viewProvider = provider;
        this.hostProvider = provider2;
        this.metricsLoggerProvider = provider3;
        this.resourcePickerProvider = provider4;
    }

    public static QSPanelHost providesHeaderQSPanelHost(View view, QSHost qSHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        QSScopeModule.Companion.getClass();
        return new QSPanelHost(1, view.findViewById(R.id.quick_qs_panel), qSHost, metricsLogger, secQSPanelResourcePicker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesHeaderQSPanelHost((View) this.viewProvider.get(), (QSHost) this.hostProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (SecQSPanelResourcePicker) this.resourcePickerProvider.get());
    }
}
