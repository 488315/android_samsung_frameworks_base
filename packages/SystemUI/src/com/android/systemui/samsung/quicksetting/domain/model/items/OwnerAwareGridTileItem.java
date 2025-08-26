package com.android.systemui.samsung.quicksetting.domain.model.items;

import com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem;

/* loaded from: classes2.dex */
public abstract class OwnerAwareGridTileItem extends IdAssignedGridTileItem {
    public QSPanelItem _ownerGridItem;

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    public final void setOwnerGridItem(QSPanelItem qSPanelItem) {
        this._ownerGridItem = qSPanelItem;
    }
}
