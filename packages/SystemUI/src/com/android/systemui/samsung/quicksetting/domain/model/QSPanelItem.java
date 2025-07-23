package com.android.systemui.samsung.quicksetting.domain.model;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPanelItem {
    public final float expandedHeight;
    public final int expandedSpanHeight;
    public final float extraHeight;
    public final GridTileItem gridTileItem;
    public final boolean isTarget;
    public final IntRect resizeSpanRect;
    public final int spanHeight;
    public final long spanOffset;
    public final int spanWidth;
    public final IntOffset translationOffset;
    public final IntOffset translationSpanOffset;

    public /* synthetic */ QSPanelItem(GridTileItem gridTileItem, int i, int i2, long j, float f, float f2, int i3, IntOffset intOffset, IntOffset intOffset2, IntRect intRect, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(gridTileItem, i, i2, j, f, f2, i3, intOffset, intOffset2, intRect, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSPanelItem)) {
            return false;
        }
        QSPanelItem qSPanelItem = (QSPanelItem) obj;
        return Intrinsics.areEqual(this.gridTileItem, qSPanelItem.gridTileItem) && this.spanWidth == qSPanelItem.spanWidth && this.spanHeight == qSPanelItem.spanHeight && IntOffset.m849equalsimpl0(this.spanOffset, qSPanelItem.spanOffset) && Dp.m836equalsimpl0(this.extraHeight, qSPanelItem.extraHeight) && Dp.m836equalsimpl0(this.expandedHeight, qSPanelItem.expandedHeight) && this.expandedSpanHeight == qSPanelItem.expandedSpanHeight && Intrinsics.areEqual(this.translationSpanOffset, qSPanelItem.translationSpanOffset) && Intrinsics.areEqual(this.translationOffset, qSPanelItem.translationOffset) && Intrinsics.areEqual(this.resizeSpanRect, qSPanelItem.resizeSpanRect) && this.isTarget == qSPanelItem.isTarget;
    }

    public final int hashCode() {
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.spanHeight, ReorderTile$$ExternalSyntheticOutline0.m(this.spanWidth, this.gridTileItem.hashCode() * 31, 31), 31);
        IntOffset.Companion companion = IntOffset.Companion;
        int m2 = MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.spanOffset);
        Dp.Companion companion2 = Dp.Companion;
        int m3 = ReorderTile$$ExternalSyntheticOutline0.m(this.expandedSpanHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.expandedHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.extraHeight, m2, 31), 31), 31);
        IntOffset intOffset = this.translationSpanOffset;
        int hashCode = (m3 + (intOffset == null ? 0 : Long.hashCode(intOffset.packedValue))) * 31;
        IntOffset intOffset2 = this.translationOffset;
        int hashCode2 = (hashCode + (intOffset2 == null ? 0 : Long.hashCode(intOffset2.packedValue))) * 31;
        IntRect intRect = this.resizeSpanRect;
        return Boolean.hashCode(this.isTarget) + ((hashCode2 + (intRect != null ? intRect.hashCode() : 0)) * 31);
    }

    public final String toString() {
        String m852toStringimpl = IntOffset.m852toStringimpl(this.spanOffset);
        String m837toStringimpl = Dp.m837toStringimpl(this.extraHeight);
        String m837toStringimpl2 = Dp.m837toStringimpl(this.expandedHeight);
        StringBuilder sb = new StringBuilder("QSPanelItem(gridTileItem=");
        sb.append(this.gridTileItem);
        sb.append(", spanWidth=");
        sb.append(this.spanWidth);
        sb.append(", spanHeight=");
        sb.append(this.spanHeight);
        sb.append(", spanOffset=");
        sb.append(m852toStringimpl);
        sb.append(", extraHeight=");
        MoveResult$$ExternalSyntheticOutline0.m(sb, m837toStringimpl, ", expandedHeight=", m837toStringimpl2, ", expandedSpanHeight=");
        sb.append(this.expandedSpanHeight);
        sb.append(", translationSpanOffset=");
        sb.append(this.translationSpanOffset);
        sb.append(", translationOffset=");
        sb.append(this.translationOffset);
        sb.append(", resizeSpanRect=");
        sb.append(this.resizeSpanRect);
        sb.append(", isTarget=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isTarget, ")");
    }

    private QSPanelItem(GridTileItem gridTileItem, int i, int i2, long j, float f, float f2, int i3, IntOffset intOffset, IntOffset intOffset2, IntRect intRect, boolean z) {
        this.gridTileItem = gridTileItem;
        this.spanWidth = i;
        this.spanHeight = i2;
        this.spanOffset = j;
        this.extraHeight = f;
        this.expandedHeight = f2;
        this.expandedSpanHeight = i3;
        this.translationSpanOffset = intOffset;
        this.translationOffset = intOffset2;
        this.resizeSpanRect = intRect;
        this.isTarget = z;
        gridTileItem.setOwnerGridItem(this);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public QSPanelItem(com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem r18, int r19, int r20, long r21, float r23, float r24, int r25, androidx.compose.ui.unit.IntOffset r26, androidx.compose.ui.unit.IntOffset r27, androidx.compose.ui.unit.IntRect r28, boolean r29, int r30, kotlin.jvm.internal.DefaultConstructorMarker r31) {
        /*
            r17 = this;
            r0 = r30
            r1 = r0 & 2
            r2 = 32
            if (r1 == 0) goto L10
            long r3 = r18.mo2921getDefaultSpanSizeYbymL2g()
            long r3 = r3 >> r2
            int r1 = (int) r3
            r5 = r1
            goto L12
        L10:
            r5 = r19
        L12:
            r1 = r0 & 4
            r3 = 4294967295(0xffffffff, double:2.1219957905E-314)
            if (r1 == 0) goto L23
            long r6 = r18.mo2921getDefaultSpanSizeYbymL2g()
            long r6 = r6 & r3
            int r1 = (int) r6
            r6 = r1
            goto L25
        L23:
            r6 = r20
        L25:
            r1 = r0 & 8
            if (r1 == 0) goto L33
            r1 = -1
            long r7 = (long) r1
            long r9 = r7 << r2
            long r3 = r3 & r7
            long r3 = r3 | r9
            androidx.compose.ui.unit.IntOffset$Companion r1 = androidx.compose.ui.unit.IntOffset.Companion
            r7 = r3
            goto L35
        L33:
            r7 = r21
        L35:
            r1 = r0 & 16
            java.lang.String r3 = "QuickTileDrawer"
            r4 = 0
            if (r1 == 0) goto L51
            java.lang.String r1 = r18.getType()
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r1 == 0) goto L4c
            r1 = 10
            float r1 = (float) r1
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            goto L4f
        L4c:
            float r1 = (float) r4
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
        L4f:
            r9 = r1
            goto L53
        L51:
            r9 = r23
        L53:
            r1 = r0 & 32
            if (r1 == 0) goto L7c
            java.lang.String r1 = r18.getType()
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r2 == 0) goto L69
            androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
            r1.getClass()
            float r1 = androidx.compose.ui.unit.Dp.Infinity
            goto L7a
        L69:
            java.lang.String r2 = "MediaPlayer"
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r1 == 0) goto L77
            r1 = 300(0x12c, float:4.2E-43)
            float r1 = (float) r1
            androidx.compose.ui.unit.Dp$Companion r2 = androidx.compose.ui.unit.Dp.Companion
            goto L7a
        L77:
            float r1 = (float) r4
            androidx.compose.ui.unit.Dp$Companion r2 = androidx.compose.ui.unit.Dp.Companion
        L7a:
            r10 = r1
            goto L7e
        L7c:
            r10 = r24
        L7e:
            r1 = r0 & 64
            if (r1 == 0) goto L84
            r11 = r4
            goto L86
        L84:
            r11 = r25
        L86:
            r1 = r0 & 128(0x80, float:1.8E-43)
            r2 = 0
            if (r1 == 0) goto L8d
            r12 = r2
            goto L8f
        L8d:
            r12 = r26
        L8f:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L95
            r13 = r2
            goto L97
        L95:
            r13 = r27
        L97:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L9d
            r14 = r2
            goto L9f
        L9d:
            r14 = r28
        L9f:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto La5
            r15 = r4
            goto La7
        La5:
            r15 = r29
        La7:
            r16 = 0
            r3 = r17
            r4 = r18
            r3.<init>(r4, r5, r6, r7, r9, r10, r11, r12, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.model.QSPanelItem.<init>(com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem, int, int, long, float, float, int, androidx.compose.ui.unit.IntOffset, androidx.compose.ui.unit.IntOffset, androidx.compose.ui.unit.IntRect, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
