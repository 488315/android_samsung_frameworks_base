package com.android.systemui.accessibility.qs;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class QSAccessibilityModule_Companion_ProvideFontScalingTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public QSAccessibilityModule_Companion_ProvideFontScalingTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideFontScalingTileConfig(QsEventLogger qsEventLogger) {
        QSAccessibilityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("font_scaling"), new QSTileUIConfig.Resource(R.drawable.ic_qs_font_scaling, R.string.quick_settings_font_scaling_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideFontScalingTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
