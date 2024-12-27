package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSScopeModule_Companion_ProvidesPanelQSPanelHostFactory implements Provider {
    public final javax.inject.Provider hostProvider;
    public final javax.inject.Provider metricsLoggerProvider;
    public final javax.inject.Provider resourcePickerProvider;
    public final javax.inject.Provider viewProvider;

    public QSScopeModule_Companion_ProvidesPanelQSPanelHostFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.viewProvider = provider;
        this.hostProvider = provider2;
        this.metricsLoggerProvider = provider3;
        this.resourcePickerProvider = provider4;
    }

    public static QSPanelHost providesPanelQSPanelHost(View view, QSTileHost qSTileHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        QSScopeModule.Companion.getClass();
        return new QSPanelHost(0, view.findViewById(R.id.quick_settings_panel), qSTileHost, metricsLogger, secQSPanelResourcePicker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesPanelQSPanelHost((View) this.viewProvider.get(), (QSTileHost) this.hostProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (SecQSPanelResourcePicker) this.resourcePickerProvider.get());
    }
}
