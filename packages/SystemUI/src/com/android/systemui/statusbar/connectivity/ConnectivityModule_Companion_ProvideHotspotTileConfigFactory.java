package com.android.systemui.statusbar.connectivity;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConnectivityModule_Companion_ProvideHotspotTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public ConnectivityModule_Companion_ProvideHotspotTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideHotspotTileConfig(QsEventLogger qsEventLogger) {
        ConnectivityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("hotspot"), new QSTileUIConfig.Resource(R.drawable.ic_hotspot, R.string.quick_settings_hotspot_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.CONNECTIVITY, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideHotspotTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
