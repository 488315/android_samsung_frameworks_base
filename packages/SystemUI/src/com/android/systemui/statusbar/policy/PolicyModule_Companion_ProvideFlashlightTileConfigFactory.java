package com.android.systemui.statusbar.policy;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class PolicyModule_Companion_ProvideFlashlightTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public PolicyModule_Companion_ProvideFlashlightTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideFlashlightTileConfig(QsEventLogger qsEventLogger) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("flashlight"), new QSTileUIConfig.Resource(R.drawable.qs_flashlight_icon_off, R.string.quick_settings_flashlight_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideFlashlightTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
