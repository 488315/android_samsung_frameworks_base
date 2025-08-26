package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.unit.IntSize;
import com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    public final long mo2938getDefaultSpanSizeYbymL2g() {
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
    public BrightBar(SecBrightBarViewModel secBrightBarViewModel, long j, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            j = (2 << 32) | (1 & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
        }
        this(secBrightBarViewModel, j, (i & 4) != 0 ? "BrightBar" : str, null);
    }
}
