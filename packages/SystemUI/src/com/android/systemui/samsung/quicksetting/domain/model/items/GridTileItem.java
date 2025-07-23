package com.android.systemui.samsung.quicksetting.domain.model.items;

import com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface GridTileItem {
    /* renamed from: getDefaultSpanSize-YbymL2g */
    long mo2921getDefaultSpanSizeYbymL2g();

    String getType();

    default String getUniqueKey() {
        return getType();
    }

    default void setOwnerGridItem(QSPanelItem qSPanelItem) {
    }
}
