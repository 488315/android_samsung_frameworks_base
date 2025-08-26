package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.unit.IntSize;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    public final long mo2938getDefaultSpanSizeYbymL2g() {
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
    public QuickTileDrawer(long j, String str, TileGridViewModel tileGridViewModel, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            j = (4 << 32) | (2 & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
        }
        this(j, (i & 2) != 0 ? "QuickTileDrawer" : str, tileGridViewModel, null);
    }
}
