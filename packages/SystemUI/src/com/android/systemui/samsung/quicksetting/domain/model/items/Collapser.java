package com.android.systemui.samsung.quicksetting.domain.model.items;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Collapser extends IdAssignedGridTileItem {
    public final long defaultSpanSize;
    public final String type;

    public /* synthetic */ Collapser(long j, String str, String str2, int i, String str3, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str, str2, i, str3, function0);
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

    private Collapser(long j, String str, String str2, int i, String str3, Function0 function0) {
        this.defaultSpanSize = j;
        this.type = str;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Collapser(long r9, java.lang.String r11, java.lang.String r12, int r13, java.lang.String r14, kotlin.jvm.functions.Function0 r15, int r16, kotlin.jvm.internal.DefaultConstructorMarker r17) {
        /*
            r8 = this;
            r0 = 1
            r1 = r16 & 1
            r2 = 32
            if (r1 == 0) goto L14
            long r0 = (long) r0
            long r3 = r0 << r2
            r5 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r5
            long r0 = r0 | r3
            androidx.compose.ui.unit.IntSize$Companion r3 = androidx.compose.ui.unit.IntSize.Companion
            goto L15
        L14:
            r0 = r9
        L15:
            r3 = r16 & 2
            if (r3 == 0) goto L1c
            java.lang.String r3 = "Collapser"
            goto L1d
        L1c:
            r3 = r11
        L1d:
            r4 = r16 & 4
            java.lang.String r5 = ""
            if (r4 == 0) goto L25
            r4 = r5
            goto L26
        L25:
            r4 = r12
        L26:
            r6 = r16 & 8
            if (r6 == 0) goto L2c
            r6 = 0
            goto L2d
        L2c:
            r6 = r13
        L2d:
            r7 = r16 & 16
            if (r7 == 0) goto L32
            goto L33
        L32:
            r5 = r14
        L33:
            r2 = r16 & 32
            if (r2 == 0) goto L3d
            com.android.systemui.samsung.quicksetting.domain.model.items.Collapser$$ExternalSyntheticLambda0 r2 = new com.android.systemui.samsung.quicksetting.domain.model.items.Collapser$$ExternalSyntheticLambda0
            r2.<init>()
            goto L3e
        L3d:
            r2 = r15
        L3e:
            r7 = 0
            r9 = r8
            r10 = r0
            r16 = r2
            r12 = r3
            r13 = r4
            r15 = r5
            r14 = r6
            r17 = r7
            r9.<init>(r10, r12, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.model.items.Collapser.<init>(long, java.lang.String, java.lang.String, int, java.lang.String, kotlin.jvm.functions.Function0, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
