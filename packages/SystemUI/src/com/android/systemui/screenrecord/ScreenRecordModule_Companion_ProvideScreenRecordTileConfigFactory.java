package com.android.systemui.screenrecord;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenRecordModule_Companion_ProvideScreenRecordTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public ScreenRecordModule_Companion_ProvideScreenRecordTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideScreenRecordTileConfig(QsEventLogger qsEventLogger) {
        ScreenRecordModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("screenrecord"), new QSTileUIConfig.Resource(R.drawable.qs_screen_record_icon_off, R.string.quick_settings_screen_record_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.DISPLAY, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideScreenRecordTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
