package com.android.systemui.samsung.quicksetting.domain.model.items;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ElementKey;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final long mo2921getDefaultSpanSizeYbymL2g() {
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public QuickTile(long r8, java.lang.String r10, java.lang.String r11, int r12, com.android.systemui.plugins.qs.QSTile r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r0 = 1
            r1 = r14 & 1
            if (r1 == 0) goto L14
            long r0 = (long) r0
            r2 = 32
            long r2 = r0 << r2
            r4 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r4
            long r0 = r0 | r2
            androidx.compose.ui.unit.IntSize$Companion r2 = androidx.compose.ui.unit.IntSize.Companion
            goto L15
        L14:
            r0 = r8
        L15:
            r2 = r14 & 2
            if (r2 == 0) goto L1c
            java.lang.String r2 = "QuickTile"
            goto L1d
        L1c:
            r2 = r10
        L1d:
            r3 = r14 & 4
            if (r3 == 0) goto L24
            java.lang.String r3 = ""
            goto L25
        L24:
            r3 = r11
        L25:
            r4 = r14 & 8
            if (r4 == 0) goto L2d
            r4 = 2131234530(0x7f080ee2, float:1.8085228E38)
            goto L2e
        L2d:
            r4 = r12
        L2e:
            r5 = r14 & 16
            if (r5 == 0) goto L38
            com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileKt$getDummyTile$1 r5 = new com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileKt$getDummyTile$1
            r5.<init>()
            goto L39
        L38:
            r5 = r13
        L39:
            r6 = 0
            r8 = r7
            r9 = r0
            r11 = r2
            r12 = r3
            r13 = r4
            r14 = r5
            r15 = r6
            r8.<init>(r9, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.domain.model.items.QuickTile.<init>(long, java.lang.String, java.lang.String, int, com.android.systemui.plugins.qs.QSTile, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
