package com.android.systemui.dreams.dagger;

import com.android.systemui.R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTilePolicy;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DreamModule_ProvideDreamTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public DreamModule_ProvideDreamTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideDreamTileConfig(QsEventLogger qsEventLogger) {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create(BcSmartspaceDataPlugin.UI_SURFACE_DREAM);
        return new QSTileConfig(create, new QSTileUIConfig.Resource(R.drawable.ic_qs_screen_saver, R.string.quick_settings_screensaver_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), create.getSpec(), QSTilePolicy.NoRestrictions.INSTANCE);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDreamTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
