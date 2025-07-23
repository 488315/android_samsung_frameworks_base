package com.android.systemui.samsung.quicksetting.domain.model.items;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaPlayer extends OwnerAwareGridTileItem {
    public final long defaultSpanSize;
    public final String type;

    public /* synthetic */ MediaPlayer(long j, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str);
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

    private MediaPlayer(long j, String str) {
        this.defaultSpanSize = j;
        this.type = str;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MediaPlayer(long r6, java.lang.String r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r5 = this;
            r10 = r9 & 1
            r0 = 2
            if (r10 == 0) goto L14
            r6 = 4
            long r6 = (long) r6
            r10 = 32
            long r6 = r6 << r10
            long r1 = (long) r0
            r3 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r1 = r1 & r3
            long r6 = r6 | r1
            androidx.compose.ui.unit.IntSize$Companion r10 = androidx.compose.ui.unit.IntSize.Companion
        L14:
            r9 = r9 & r0
            if (r9 == 0) goto L19
            java.lang.String r8 = "MediaPlayer"
        L19:
            r9 = 0
            r5.<init>(r6, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.model.items.MediaPlayer.<init>(long, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
