package com.android.systemui.samsung.quicksetting.domain.model.items;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final long mo2921getDefaultSpanSizeYbymL2g() {
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public QuickTileFolder(long r7, java.lang.String r9, java.util.List r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r12 = 1
            r0 = r11 & 1
            if (r0 == 0) goto L13
            long r7 = (long) r12
            r12 = 32
            long r0 = r7 << r12
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r7 = r7 & r2
            long r7 = r7 | r0
            androidx.compose.ui.unit.IntSize$Companion r12 = androidx.compose.ui.unit.IntSize.Companion
        L13:
            r1 = r7
            r7 = r11 & 2
            if (r7 == 0) goto L1a
            java.lang.String r9 = "QuickTileFolder"
        L1a:
            r3 = r9
            r5 = 0
            r0 = r6
            r4 = r10
            r0.<init>(r1, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileFolder.<init>(long, java.lang.String, java.util.List, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
