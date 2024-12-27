package com.android.systemui.qs.tiles.impl.custom.di;

import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;

public final class QSTileConfigModule {
    public final QSTileConfig config;

    public QSTileConfigModule(QSTileConfig qSTileConfig) {
        this.config = qSTileConfig;
    }
}
