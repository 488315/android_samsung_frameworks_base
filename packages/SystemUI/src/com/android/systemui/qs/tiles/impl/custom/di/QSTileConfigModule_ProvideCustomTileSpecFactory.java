package com.android.systemui.qs.tiles.impl.custom.di;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Provider;

public final class QSTileConfigModule_ProvideCustomTileSpecFactory implements Provider {
    public final QSTileConfigModule module;

    public QSTileConfigModule_ProvideCustomTileSpecFactory(QSTileConfigModule qSTileConfigModule) {
        this.module = qSTileConfigModule;
    }

    public static TileSpec.CustomTileSpec provideCustomTileSpec(QSTileConfigModule qSTileConfigModule) {
        return (TileSpec.CustomTileSpec) qSTileConfigModule.config.tileSpec;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCustomTileSpec(this.module);
    }
}
