package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class QuickButton extends IdAssignedGridTileItem {
    public final long defaultSpanSize;
    public final String spec;
    public final String title;
    public final String type;

    public /* synthetic */ QuickButton(long j, String str, String str2, int i, String str3, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str, str2, i, str3, function0);
    }

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    /* renamed from: getDefaultSpanSize-YbymL2g */
    public final long mo2938getDefaultSpanSizeYbymL2g() {
        return this.defaultSpanSize;
    }

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    public final String getType() {
        return this.type;
    }

    private QuickButton(long j, String str, String str2, int i, String str3, Function0 function0) {
        this.defaultSpanSize = j;
        this.type = str;
        this.spec = str2;
        this.title = str3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public QuickButton(long j, String str, String str2, int i, String str3, Function0 function0, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        long j2;
        if ((i2 & 1) != 0) {
            long j3 = 1;
            j2 = (j3 & 4294967295L) | (j3 << 32);
            IntSize.Companion companion = IntSize.Companion;
        } else {
            j2 = j;
        }
        this(j2, (i2 & 2) != 0 ? "QuickButton" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? 0 : i, (i2 & 16) == 0 ? str3 : "", (i2 & 32) != 0 ? new QuickButton$$ExternalSyntheticLambda0() : function0, null);
    }
}
