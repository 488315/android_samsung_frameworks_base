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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class PolicyModule_Companion_ProvideMicrophoneToggleTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public PolicyModule_Companion_ProvideMicrophoneToggleTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideMicrophoneToggleTileConfig(QsEventLogger qsEventLogger) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("mictoggle"), new QSTileUIConfig.Resource(R.drawable.qs_mic_access_off, R.string.quick_settings_mic_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, new QSTilePolicy.Restricted(Collections.singletonList("disallow_microphone_toggle")), 8, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideMicrophoneToggleTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
