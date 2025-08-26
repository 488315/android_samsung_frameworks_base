package com.android.systemui.statusbar.connectivity;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.sec.ims.settings.ImsProfile;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ConnectivityModule_Companion_ProvideInternetTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public ConnectivityModule_Companion_ProvideInternetTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideInternetTileConfig(QsEventLogger qsEventLogger) {
        ConnectivityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create(ImsProfile.PDN_INTERNET), new QSTileUIConfig.Resource(R.drawable.ic_qs_no_internet_available, R.string.quick_settings_internet_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.CONNECTIVITY, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideInternetTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
