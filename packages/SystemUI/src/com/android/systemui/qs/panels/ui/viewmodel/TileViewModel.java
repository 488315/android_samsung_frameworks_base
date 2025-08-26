package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
public final class TileViewModel {
    public final TileSpec spec;
    public final Flow state = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new TileViewModel$state$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new TileViewModel$state$1(this, null)))));
    public final QSTile tile;

    public TileViewModel(QSTile qSTile, TileSpec tileSpec) {
        this.tile = qSTile;
        this.spec = tileSpec;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileViewModel)) {
            return false;
        }
        TileViewModel tileViewModel = (TileViewModel) obj;
        return Intrinsics.areEqual(this.tile, tileViewModel.tile) && Intrinsics.areEqual(this.spec, tileViewModel.spec);
    }

    public final int hashCode() {
        return this.spec.hashCode() + (this.tile.hashCode() * 31);
    }

    public final String toString() {
        return "TileViewModel(tile=" + this.tile + ", spec=" + this.spec + ")";
    }
}
