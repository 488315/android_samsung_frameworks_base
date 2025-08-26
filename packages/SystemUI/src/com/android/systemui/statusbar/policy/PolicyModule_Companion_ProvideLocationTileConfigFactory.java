package com.android.systemui.statusbar.policy;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTilePolicy;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class PolicyModule_Companion_ProvideLocationTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public PolicyModule_Companion_ProvideLocationTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideLocationTileConfig(QsEventLogger qsEventLogger) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("location"), new QSTileUIConfig.Resource(R.drawable.qs_location_icon_off, R.string.quick_settings_location_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.PRIVACY, null, new QSTilePolicy.Restricted(Arrays.asList("no_share_location", "no_config_location")), false, 80, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideLocationTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
