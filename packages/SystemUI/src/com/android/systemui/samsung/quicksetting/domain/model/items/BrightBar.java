package com.android.systemui.samsung.quicksetting.domain.model.items;

import com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BrightBar extends IdAssignedGridTileItem {
    public final long defaultSpanSize;
    public final String type;
    public final SecBrightBarViewModel viewModel;

    public /* synthetic */ BrightBar(SecBrightBarViewModel secBrightBarViewModel, long j, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(secBrightBarViewModel, j, str);
    }

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    /* renamed from: getDefaultSpanSize-YbymL2g, reason: not valid java name */
    public final long mo2921getDefaultSpanSizeYbymL2g() {
        return this.defaultSpanSize;
    }

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    public final String getType() {
        return this.type;
    }

    private BrightBar(SecBrightBarViewModel secBrightBarViewModel, long j, String str) {
        this.viewModel = secBrightBarViewModel;
        this.defaultSpanSize = j;
        this.type = str;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BrightBar(com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel r7, long r8, java.lang.String r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r12 = 2
            r0 = r11 & 2
            if (r0 == 0) goto L14
            long r8 = (long) r12
            r12 = 32
            long r8 = r8 << r12
            r12 = 1
            long r0 = (long) r12
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r2
            long r8 = r8 | r0
            androidx.compose.ui.unit.IntSize$Companion r12 = androidx.compose.ui.unit.IntSize.Companion
        L14:
            r2 = r8
            r8 = r11 & 4
            if (r8 == 0) goto L1b
            java.lang.String r10 = "BrightBar"
        L1b:
            r4 = r10
            r5 = 0
            r0 = r6
            r1 = r7
            r0.<init>(r1, r2, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.model.items.BrightBar.<init>(com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel, long, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
