package com.android.systemui.dreams.dagger;

import com.android.systemui.R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTilePolicy;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamModule_ProvideDreamTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public DreamModule_ProvideDreamTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideDreamTileConfig(QsEventLogger qsEventLogger) {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create(BcSmartspaceDataPlugin.UI_SURFACE_DREAM);
        return new QSTileConfig(create, new QSTileUIConfig.Resource(R.drawable.ic_qs_screen_saver, R.string.quick_settings_screensaver_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, create.getSpec(), QSTilePolicy.NoRestrictions.INSTANCE);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDreamTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
