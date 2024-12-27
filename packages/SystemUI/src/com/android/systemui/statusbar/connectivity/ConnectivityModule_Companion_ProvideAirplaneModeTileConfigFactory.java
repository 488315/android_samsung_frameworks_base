package com.android.systemui.statusbar.connectivity;

import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTilePolicy;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;
import java.util.Collections;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ConnectivityModule_Companion_ProvideAirplaneModeTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public ConnectivityModule_Companion_ProvideAirplaneModeTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideAirplaneModeTileConfig(QsEventLogger qsEventLogger) {
        ConnectivityModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create(SubRoom.EXTRA_KEY_AIRPLANE_MODE), new QSTileUIConfig.Resource(R.drawable.qs_airplane_icon_off, R.string.airplane_mode), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, new QSTilePolicy.Restricted(Collections.singletonList("no_airplane_mode")), 8, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideAirplaneModeTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
