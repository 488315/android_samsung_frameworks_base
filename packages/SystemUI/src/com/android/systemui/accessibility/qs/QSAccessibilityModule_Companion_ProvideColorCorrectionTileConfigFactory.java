package com.android.systemui.accessibility.qs;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* loaded from: classes.dex */
public final class QSAccessibilityModule_Companion_ProvideColorCorrectionTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public QSAccessibilityModule_Companion_ProvideColorCorrectionTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideColorCorrectionTileConfig(QsEventLogger qsEventLogger) {
        QSAccessibilityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("color_correction"), new QSTileUIConfig.Resource(R.drawable.ic_qs_color_correction, R.string.quick_settings_color_correction_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.ACCESSIBILITY, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideColorCorrectionTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
