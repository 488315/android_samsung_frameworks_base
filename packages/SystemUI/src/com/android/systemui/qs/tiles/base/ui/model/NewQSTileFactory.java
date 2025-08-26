package com.android.systemui.qs.tiles.base.ui.model;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProvider;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelFactory$Component;
import com.android.systemui.qs.tiles.base.ui.viewmodel.StubQSTileViewModel;
import java.util.Map;
import javax.inject.Provider;

/* loaded from: classes2.dex */
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
        throw new IllegalStateException("New code path not supported when com.android.systemui.qs_new_tiles is disabled.");
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public final QSTile createTile(String str) {
        QSTileViewModel qSTileViewModelCreate;
        TileSpec.Companion.getClass();
        TileSpec tileSpecCreate = TileSpec.Companion.create(str);
        if (tileSpecCreate instanceof TileSpec.CustomTileSpec) {
            qSTileViewModelCreate = this.customTileViewModelFactory.create((TileSpec.CustomTileSpec) tileSpecCreate);
        } else {
            if (tileSpecCreate instanceof TileSpec.PlatformTileSpec) {
                Provider provider = (Provider) this.tileMap.get(str);
                if (provider == null || (qSTileViewModelCreate = (QSTileViewModel) provider.get()) == null || (qSTileViewModelCreate instanceof StubQSTileViewModel)) {
                }
            } else {
                boolean z = tileSpecCreate instanceof TileSpec.Invalid;
            }
            qSTileViewModelCreate = null;
        }
        if (qSTileViewModelCreate == null) {
            return null;
        }
        return this.adapterFactory.create(qSTileViewModelCreate);
    }
}
