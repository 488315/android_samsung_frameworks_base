package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.unit.IntSize;
import com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class VolumeBar extends IdAssignedGridTileItem {
    public final long defaultSpanSize;
    public final String type;
    public final SecVolumeBarViewModel viewModel;

    public /* synthetic */ VolumeBar(SecVolumeBarViewModel secVolumeBarViewModel, long j, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(secVolumeBarViewModel, j, str);
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

    private VolumeBar(SecVolumeBarViewModel secVolumeBarViewModel, long j, String str) {
        this.viewModel = secVolumeBarViewModel;
        this.defaultSpanSize = j;
        this.type = str;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public VolumeBar(SecVolumeBarViewModel secVolumeBarViewModel, long j, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            long j2 = 2;
            j = (j2 & 4294967295L) | (j2 << 32);
            IntSize.Companion companion = IntSize.Companion;
        }
        this(secVolumeBarViewModel, j, (i & 4) != 0 ? "VolumeBar" : str, null);
    }
}
