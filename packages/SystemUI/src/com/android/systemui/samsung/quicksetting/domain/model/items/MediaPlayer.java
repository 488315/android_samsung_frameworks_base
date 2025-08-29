package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaPlayer extends OwnerAwareGridTileItem {
    public final long defaultSpanSize;
    public final String type;

    public /* synthetic */ MediaPlayer(long j, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str);
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

    private MediaPlayer(long j, String str) {
        this.defaultSpanSize = j;
        this.type = str;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MediaPlayer(long j, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            j = (4 << 32) | (2 & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
        }
        this(j, (i & 2) != 0 ? "MediaPlayer" : str, null);
    }
}
