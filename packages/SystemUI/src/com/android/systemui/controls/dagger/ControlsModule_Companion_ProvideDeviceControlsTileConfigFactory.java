package com.android.systemui.controls.dagger;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsModule_Companion_ProvideDeviceControlsTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public ControlsModule_Companion_ProvideDeviceControlsTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideDeviceControlsTileConfig(QsEventLogger qsEventLogger) {
        ControlsModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("controls"), new QSTileUIConfig.Resource(R.drawable.controls_icon, R.string.quick_controls_title), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDeviceControlsTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
