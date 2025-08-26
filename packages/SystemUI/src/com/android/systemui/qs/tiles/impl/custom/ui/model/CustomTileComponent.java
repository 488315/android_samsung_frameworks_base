package com.android.systemui.qs.tiles.impl.custom.ui.model;

import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;

/* loaded from: classes2.dex */
public interface CustomTileComponent {

    public interface Builder {
    }

    QSTileDataInteractor dataInteractor();

    QSTileDataToStateMapper dataToStateMapper();

    QSTileUserActionInteractor userActionInteractor();
}
