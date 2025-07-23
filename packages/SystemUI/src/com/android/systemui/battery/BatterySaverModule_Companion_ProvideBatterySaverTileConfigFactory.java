package com.android.systemui.battery;

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
public final class BatterySaverModule_Companion_ProvideBatterySaverTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public BatterySaverModule_Companion_ProvideBatterySaverTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideBatterySaverTileConfig(QsEventLogger qsEventLogger) {
        BatterySaverModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("battery"), new QSTileUIConfig.Resource(R.drawable.qs_battery_saver_icon_off, R.string.battery_detail_switch_title), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBatterySaverTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
