package com.android.systemui.accessibility.qs;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

public final class QSAccessibilityModule_Companion_ProvideReduceBrightColorsTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public QSAccessibilityModule_Companion_ProvideReduceBrightColorsTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideReduceBrightColorsTileConfig(QsEventLogger qsEventLogger) {
        QSAccessibilityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("reduce_brightness"), new QSTileUIConfig.Resource(R.drawable.qs_extra_dim_icon_on, 17042536), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideReduceBrightColorsTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
