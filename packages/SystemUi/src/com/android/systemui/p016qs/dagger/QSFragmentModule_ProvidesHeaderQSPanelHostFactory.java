package com.android.systemui.p016qs.dagger;

import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.p016qs.QSPanelHost;
import com.android.systemui.p016qs.QSTileHost;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSFragmentModule_ProvidesHeaderQSPanelHostFactory implements Provider {
    public final Provider hostProvider;
    public final Provider metricsLoggerProvider;
    public final Provider resourcePickerProvider;
    public final Provider viewProvider;

    public QSFragmentModule_ProvidesHeaderQSPanelHostFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.viewProvider = provider;
        this.hostProvider = provider2;
        this.metricsLoggerProvider = provider3;
        this.resourcePickerProvider = provider4;
    }

    public static QSPanelHost providesHeaderQSPanelHost(View view, QSTileHost qSTileHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        return new QSPanelHost(1, view.findViewById(R.id.quick_qs_panel), qSTileHost, metricsLogger, secQSPanelResourcePicker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesHeaderQSPanelHost((View) this.viewProvider.get(), (QSTileHost) this.hostProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (SecQSPanelResourcePicker) this.resourcePickerProvider.get());
    }
}
