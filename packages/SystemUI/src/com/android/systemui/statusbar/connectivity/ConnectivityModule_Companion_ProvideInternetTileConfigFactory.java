package com.android.systemui.statusbar.connectivity;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import com.sec.ims.settings.ImsProfile;
import dagger.internal.Provider;

public final class ConnectivityModule_Companion_ProvideInternetTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public ConnectivityModule_Companion_ProvideInternetTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideInternetTileConfig(QsEventLogger qsEventLogger) {
        ConnectivityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create(ImsProfile.PDN_INTERNET), new QSTileUIConfig.Resource(R.drawable.ic_qs_no_internet_available, R.string.quick_settings_internet_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideInternetTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
