package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.IconAndNameCustomRepository;
import com.android.systemui.qs.panels.data.repository.StockTilesRepository;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfigProvider;

public final class EditTilesListInteractor {
    public final IconAndNameCustomRepository iconAndNameCustomRepository;
    public final QSTileConfigProvider qsTileConfigProvider;
    public final StockTilesRepository stockTilesRepository;

    public EditTilesListInteractor(StockTilesRepository stockTilesRepository, QSTileConfigProvider qSTileConfigProvider, IconAndNameCustomRepository iconAndNameCustomRepository) {
        this.stockTilesRepository = stockTilesRepository;
        this.qsTileConfigProvider = qSTileConfigProvider;
        this.iconAndNameCustomRepository = iconAndNameCustomRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getTilesToEdit(kotlin.coroutines.Continuation r13) {
        /*
            Method dump skipped, instructions count: 240
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.domain.interactor.EditTilesListInteractor.getTilesToEdit(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
