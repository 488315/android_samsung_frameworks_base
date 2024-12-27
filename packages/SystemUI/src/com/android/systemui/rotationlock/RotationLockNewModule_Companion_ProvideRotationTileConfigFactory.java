package com.android.systemui.rotationlock;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RotationLockNewModule_Companion_ProvideRotationTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public RotationLockNewModule_Companion_ProvideRotationTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideRotationTileConfig(QsEventLogger qsEventLogger) {
        RotationLockNewModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("rotation"), new QSTileUIConfig.Resource(R.drawable.qs_auto_rotate_icon_off, R.string.quick_settings_rotation_unlocked_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRotationTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
