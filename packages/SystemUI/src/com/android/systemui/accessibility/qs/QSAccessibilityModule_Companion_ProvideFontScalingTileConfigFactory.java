package com.android.systemui.accessibility.qs;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class QSAccessibilityModule_Companion_ProvideFontScalingTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public QSAccessibilityModule_Companion_ProvideFontScalingTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideFontScalingTileConfig(QsEventLogger qsEventLogger) {
        QSAccessibilityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("font_scaling"), new QSTileUIConfig.Resource(R.drawable.ic_qs_font_scaling, R.string.quick_settings_font_scaling_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.DISPLAY, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideFontScalingTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
