package com.android.systemui.statusbar.connectivity;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ConnectivityModule_Companion_ProvideDataSaverTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public ConnectivityModule_Companion_ProvideDataSaverTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideDataSaverTileConfig(QsEventLogger qsEventLogger) {
        ConnectivityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("saver"), new QSTileUIConfig.Resource(R.drawable.qs_data_saver_icon_off, R.string.data_saver), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDataSaverTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
