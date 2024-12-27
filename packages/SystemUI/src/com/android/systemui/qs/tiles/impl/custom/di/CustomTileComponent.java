package com.android.systemui.qs.tiles.impl.custom.di;

import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import kotlinx.coroutines.CoroutineScope;

public interface CustomTileComponent {

    public interface Builder {
    }

    CoroutineScope coroutineScope();

    QSTileDataInteractor dataInteractor();

    QSTileDataToStateMapper dataToStateMapper();

    QSTileUserActionInteractor userActionInteractor();
}
