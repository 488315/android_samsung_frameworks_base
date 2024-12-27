package com.android.systemui.qs.tiles.impl.custom.di;

import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface CustomTileComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Builder {
    }

    CoroutineScope coroutineScope();

    QSTileDataInteractor dataInteractor();

    QSTileDataToStateMapper dataToStateMapper();

    QSTileUserActionInteractor userActionInteractor();
}
