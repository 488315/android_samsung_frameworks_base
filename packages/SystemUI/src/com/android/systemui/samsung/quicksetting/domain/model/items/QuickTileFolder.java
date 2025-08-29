package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.unit.IntSize;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class QuickTileFolder extends IdAssignedGridTileItem {
    public final long defaultSpanSize;
    public final List tiles;
    public final String type;

    public /* synthetic */ QuickTileFolder(long j, String str, List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str, list);
    }

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    /* renamed from: getDefaultSpanSize-YbymL2g */
    public final long mo2936getDefaultSpanSizeYbymL2g() {
        return this.defaultSpanSize;
    }

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    public final String getType() {
        return this.type;
    }

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    public final String getUniqueKey() {
        return this.id;
    }

    private QuickTileFolder(long j, String str, List<QuickTile> list) {
        this.defaultSpanSize = j;
        this.type = str;
        this.tiles = list;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public QuickTileFolder(long j, String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            long j2 = 1;
            j = (j2 & 4294967295L) | (j2 << 32);
            IntSize.Companion companion = IntSize.Companion;
        }
        this(j, (i & 2) != 0 ? "QuickTileFolder" : str, list, null);
    }
}
