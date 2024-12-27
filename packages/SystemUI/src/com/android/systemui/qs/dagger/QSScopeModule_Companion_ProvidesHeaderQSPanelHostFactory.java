package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import dagger.internal.Provider;

public final class QSScopeModule_Companion_ProvidesHeaderQSPanelHostFactory implements Provider {
    public final javax.inject.Provider hostProvider;
    public final javax.inject.Provider metricsLoggerProvider;
    public final javax.inject.Provider resourcePickerProvider;
    public final javax.inject.Provider viewProvider;

    public QSScopeModule_Companion_ProvidesHeaderQSPanelHostFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.viewProvider = provider;
        this.hostProvider = provider2;
        this.metricsLoggerProvider = provider3;
        this.resourcePickerProvider = provider4;
    }

    public static QSPanelHost providesHeaderQSPanelHost(View view, QSTileHost qSTileHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        QSScopeModule.Companion.getClass();
        return new QSPanelHost(1, view.findViewById(R.id.quick_qs_panel), qSTileHost, metricsLogger, secQSPanelResourcePicker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesHeaderQSPanelHost((View) this.viewProvider.get(), (QSTileHost) this.hostProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (SecQSPanelResourcePicker) this.resourcePickerProvider.get());
    }
}
