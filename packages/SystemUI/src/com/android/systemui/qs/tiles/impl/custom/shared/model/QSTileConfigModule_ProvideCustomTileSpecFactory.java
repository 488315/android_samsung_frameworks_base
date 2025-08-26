package com.android.systemui.qs.tiles.impl.custom.shared.model;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QSTileConfigModule_ProvideCustomTileSpecFactory implements Provider {
    public final QSTileConfigModule module;

    public QSTileConfigModule_ProvideCustomTileSpecFactory(QSTileConfigModule qSTileConfigModule) {
        this.module = qSTileConfigModule;
    }

    public static TileSpec.CustomTileSpec provideCustomTileSpec(QSTileConfigModule qSTileConfigModule) {
        TileSpec.CustomTileSpec customTileSpec = (TileSpec.CustomTileSpec) qSTileConfigModule.config.tileSpec;
        customTileSpec.getClass();
        return customTileSpec;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCustomTileSpec(this.module);
    }
}
