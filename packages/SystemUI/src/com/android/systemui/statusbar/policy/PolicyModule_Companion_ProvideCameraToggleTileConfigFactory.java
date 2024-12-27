package com.android.systemui.statusbar.policy;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTilePolicy;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;
import java.util.Collections;

public final class PolicyModule_Companion_ProvideCameraToggleTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public PolicyModule_Companion_ProvideCameraToggleTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideCameraToggleTileConfig(QsEventLogger qsEventLogger) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("cameratoggle"), new QSTileUIConfig.Resource(R.drawable.qs_camera_access_icon_off, R.string.quick_settings_camera_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, new QSTilePolicy.Restricted(Collections.singletonList("disallow_camera_toggle")), 8, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCameraToggleTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
