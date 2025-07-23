package com.android.systemui.samsung.quicksetting.domain.model.items;

import com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class OwnerAwareGridTileItem extends IdAssignedGridTileItem {
    public QSPanelItem _ownerGridItem;

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    public final void setOwnerGridItem(QSPanelItem qSPanelItem) {
        this._ownerGridItem = qSPanelItem;
    }
}
