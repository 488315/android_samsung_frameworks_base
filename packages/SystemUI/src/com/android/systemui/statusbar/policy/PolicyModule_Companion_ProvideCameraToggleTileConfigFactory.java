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
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PolicyModule_Companion_ProvideCameraToggleTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public PolicyModule_Companion_ProvideCameraToggleTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideCameraToggleTileConfig(QsEventLogger qsEventLogger) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("cameratoggle"), new QSTileUIConfig.Resource(R.drawable.qs_camera_access_icon_off, R.string.quick_settings_camera_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.PRIVACY, null, new QSTilePolicy.Restricted(Collections.singletonList("disallow_camera_toggle")), false, 80, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCameraToggleTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
