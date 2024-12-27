package com.android.systemui.statusbar.policy;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

public final class PolicyModule_Companion_ProvideAlarmTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public PolicyModule_Companion_ProvideAlarmTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideAlarmTileConfig(QsEventLogger qsEventLogger) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("alarm"), new QSTileUIConfig.Resource(R.drawable.ic_alarm, R.string.status_bar_alarm), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideAlarmTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
