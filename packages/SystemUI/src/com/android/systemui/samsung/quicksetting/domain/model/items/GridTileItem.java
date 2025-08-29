package com.android.systemui.samsung.quicksetting.domain.model.items;

import com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem;

/* loaded from: classes2.dex */
public interface GridTileItem {
    /* renamed from: getDefaultSpanSize-YbymL2g */
    long mo2936getDefaultSpanSizeYbymL2g();

    String getType();

    default String getUniqueKey() {
        return getType();
    }

    default void setOwnerGridItem(QSPanelItem qSPanelItem) {
    }
}
