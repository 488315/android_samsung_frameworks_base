package com.android.systemui.screenrecord;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

public final class ScreenRecordModule_Companion_ProvideScreenRecordTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public ScreenRecordModule_Companion_ProvideScreenRecordTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideScreenRecordTileConfig(QsEventLogger qsEventLogger) {
        ScreenRecordModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("screenrecord"), new QSTileUIConfig.Resource(R.drawable.qs_screen_record_icon_off, R.string.quick_settings_screen_record_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideScreenRecordTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
