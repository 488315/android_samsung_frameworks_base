package com.android.systemui.controls.dagger;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsModule_Companion_ProvideDeviceControlsTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public ControlsModule_Companion_ProvideDeviceControlsTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideDeviceControlsTileConfig(QsEventLogger qsEventLogger) {
        ControlsModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("controls"), new QSTileUIConfig.Resource(R.drawable.controls_icon, R.string.quick_controls_title), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDeviceControlsTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
