package com.android.systemui.samsung.quicksetting.domain.model.items;

import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickTileDrawer extends IdAssignedGridTileItem {
    public final long defaultSpanSize;
    public final TileGridViewModel tileGridViewModel;
    public final String type;

    public /* synthetic */ QuickTileDrawer(long j, String str, TileGridViewModel tileGridViewModel, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str, tileGridViewModel);
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

    private QuickTileDrawer(long j, String str, TileGridViewModel tileGridViewModel) {
        this.defaultSpanSize = j;
        this.type = str;
        this.tileGridViewModel = tileGridViewModel;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public QuickTileDrawer(long r8, java.lang.String r10, com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r7 = this;
            r13 = r12 & 1
            r0 = 2
            if (r13 == 0) goto L14
            r8 = 4
            long r8 = (long) r8
            r13 = 32
            long r8 = r8 << r13
            long r1 = (long) r0
            r3 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r1 = r1 & r3
            long r8 = r8 | r1
            androidx.compose.ui.unit.IntSize$Companion r13 = androidx.compose.ui.unit.IntSize.Companion
        L14:
            r2 = r8
            r8 = r12 & 2
            if (r8 == 0) goto L1b
            java.lang.String r10 = "QuickTileDrawer"
        L1b:
            r4 = r10
            r6 = 0
            r1 = r7
            r5 = r11
            r1.<init>(r2, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileDrawer.<init>(long, java.lang.String, com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
