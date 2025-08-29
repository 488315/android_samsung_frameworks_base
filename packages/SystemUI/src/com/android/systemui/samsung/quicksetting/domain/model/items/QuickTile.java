package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.compose.ui.unit.IntSize;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ElementKey;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class QuickTile extends IdAssignedGridTileItem {
    public final long defaultSpanSize;
    public final ElementKey elementKey;
    public final ElementKey folderItemKey;
    public final String spec;
    public final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 state;
    public final QSTile tile;
    public final String type;

    public /* synthetic */ QuickTile(long j, String str, String str2, int i, QSTile qSTile, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str, str2, i, qSTile);
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

    @Override // com.android.systemui.samsung.quicksetting.domain.model.items.GridTileItem
    public final String getUniqueKey() {
        return this.spec;
    }

    private QuickTile(long j, String str, String str2, int i, QSTile qSTile) {
        this.defaultSpanSize = j;
        this.type = str;
        this.spec = str2;
        this.tile = qSTile;
        this.state = FlowKt.drop(FlowConflatedKt.conflatedCallbackFlow(new QuickTile$state$1(this, null)));
        this.elementKey = new ElementKey(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " : ", str2), null, null, false, 14, null);
        this.folderItemKey = new ElementKey(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " : ", str2), null, null, false, 14, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public QuickTile(long j, String str, String str2, int i, QSTile qSTile, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        long j2;
        if ((i2 & 1) != 0) {
            long j3 = 1;
            j2 = (j3 & 4294967295L) | (j3 << 32);
            IntSize.Companion companion = IntSize.Companion;
        } else {
            j2 = j;
        }
        this(j2, (i2 & 2) != 0 ? "QuickTile" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? R.drawable.qs_airplane_icon_off : i, (i2 & 16) != 0 ? new QuickTileKt$getDummyTile$1() : qSTile, null);
    }
}
