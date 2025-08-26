package com.android.systemui.rotationlock;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class RotationLockNewModule_Companion_ProvideRotationTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public RotationLockNewModule_Companion_ProvideRotationTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideRotationTileConfig(QsEventLogger qsEventLogger) {
        RotationLockNewModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("rotation"), new QSTileUIConfig.Resource(R.drawable.qs_auto_rotate_icon_off, R.string.quick_settings_rotation_unlocked_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.DISPLAY, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRotationTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
