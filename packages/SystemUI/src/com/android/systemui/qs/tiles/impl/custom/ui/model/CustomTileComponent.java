package com.android.systemui.qs.tiles.impl.custom.ui.model;

import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface CustomTileComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Builder {
    }

    QSTileDataInteractor dataInteractor();

    QSTileDataToStateMapper dataToStateMapper();

    QSTileUserActionInteractor userActionInteractor();
}
