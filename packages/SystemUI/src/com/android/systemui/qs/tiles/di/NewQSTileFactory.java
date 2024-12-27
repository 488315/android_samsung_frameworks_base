package com.android.systemui.qs.tiles.di;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelFactory$Component;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfigProvider;
import com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter;
import java.util.Map;
import javax.inject.Provider;

public final class NewQSTileFactory implements QSFactory {
    public final QSTileViewModelAdapter.Factory adapterFactory;
    public final QSTileViewModelFactory$Component customTileViewModelFactory;
    public final Map tileMap;

    public NewQSTileFactory(QSTileConfigProvider qSTileConfigProvider, QSTileViewModelAdapter.Factory factory, Map<String, Provider> map, QSTileViewModelFactory$Component qSTileViewModelFactory$Component) {
        this.adapterFactory = factory;
        this.tileMap = map;
        this.customTileViewModelFactory = qSTileViewModelFactory$Component;
        QSPipelineFlagsRepository.Utils.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.qsNewTiles();
        throw new IllegalStateException("New code path not supported when com.android.systemui.qs_new_tiles is disabled.".toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0031, code lost:
    
        if ((!(r4 instanceof com.android.systemui.qs.tiles.viewmodel.StubQSTileViewModel)) != false) goto L18;
     */
    @Override // com.android.systemui.plugins.qs.QSFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.plugins.qs.QSTile createTile(java.lang.String r4) {
        /*
            r3 = this;
            com.android.systemui.qs.pipeline.shared.TileSpec$Companion r0 = com.android.systemui.qs.pipeline.shared.TileSpec.Companion
            r0.getClass()
            com.android.systemui.qs.pipeline.shared.TileSpec r0 = com.android.systemui.qs.pipeline.shared.TileSpec.Companion.create(r4)
            boolean r1 = r0 instanceof com.android.systemui.qs.pipeline.shared.TileSpec.CustomTileSpec
            r2 = 0
            if (r1 == 0) goto L17
            com.android.systemui.qs.pipeline.shared.TileSpec$CustomTileSpec r0 = (com.android.systemui.qs.pipeline.shared.TileSpec.CustomTileSpec) r0
            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelFactory$Component r4 = r3.customTileViewModelFactory
            com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelImpl r4 = r4.create(r0)
            goto L3b
        L17:
            boolean r1 = r0 instanceof com.android.systemui.qs.pipeline.shared.TileSpec.PlatformTileSpec
            if (r1 == 0) goto L36
            java.util.Map r0 = r3.tileMap
            java.lang.Object r4 = r0.get(r4)
            javax.inject.Provider r4 = (javax.inject.Provider) r4
            if (r4 == 0) goto L34
            java.lang.Object r4 = r4.get()
            com.android.systemui.qs.tiles.viewmodel.QSTileViewModel r4 = (com.android.systemui.qs.tiles.viewmodel.QSTileViewModel) r4
            if (r4 == 0) goto L34
            boolean r0 = r4 instanceof com.android.systemui.qs.tiles.viewmodel.StubQSTileViewModel
            r0 = r0 ^ 1
            if (r0 == 0) goto L34
            goto L3b
        L34:
            r4 = r2
            goto L3b
        L36:
            boolean r4 = r0 instanceof com.android.systemui.qs.pipeline.shared.TileSpec.Invalid
            if (r4 == 0) goto L45
            goto L34
        L3b:
            if (r4 != 0) goto L3e
            return r2
        L3e:
            com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter$Factory r3 = r3.adapterFactory
            com.android.systemui.qs.tiles.viewmodel.QSTileViewModelAdapter r3 = r3.create(r4)
            return r3
        L45:
            kotlin.NoWhenBranchMatchedException r3 = new kotlin.NoWhenBranchMatchedException
            r3.<init>()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.di.NewQSTileFactory.createTile(java.lang.String):com.android.systemui.plugins.qs.QSTile");
    }
}
