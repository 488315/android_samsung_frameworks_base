package com.android.systemui.qs.tiles.impl.custom.di;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
