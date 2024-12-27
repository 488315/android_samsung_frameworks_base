package com.android.systemui.statusbar.policy;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTilePolicy;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;
import kotlin.collections.CollectionsKt__CollectionsKt;

public final class PolicyModule_Companion_ProvideLocationTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public PolicyModule_Companion_ProvideLocationTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideLocationTileConfig(QsEventLogger qsEventLogger) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("location"), new QSTileUIConfig.Resource(R.drawable.qs_location_icon_off, R.string.quick_settings_location_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, new QSTilePolicy.Restricted(CollectionsKt__CollectionsKt.listOf("no_share_location", "no_config_location")), 8, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideLocationTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
