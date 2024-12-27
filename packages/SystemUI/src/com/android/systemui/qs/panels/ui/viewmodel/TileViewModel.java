package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TileViewModel {
    public final TileSpec spec;
    public final Flow state;
    public final QSTile tile;

    public TileViewModel(QSTile qSTile, TileSpec tileSpec) {
        this.tile = qSTile;
        FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new TileViewModel$state$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new TileViewModel$state$1(this, null))));
    }
}
