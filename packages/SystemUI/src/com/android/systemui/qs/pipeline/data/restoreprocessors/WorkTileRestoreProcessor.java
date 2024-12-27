package com.android.systemui.qs.pipeline.data.restoreprocessors;

import android.util.SparseIntArray;
import com.android.systemui.qs.pipeline.data.model.RestoreProcessor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WorkTileRestoreProcessor implements RestoreProcessor {
    public static final TileSpec TILE_SPEC = null;
    public final SharedFlowImpl _removeTrackingForUser;
    public final SparseIntArray lastRestorePosition;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TileSpec.Companion.getClass();
        TileSpec.Companion.create("WorkMode");
    }

    public WorkTileRestoreProcessor() {
        new SparseIntArray();
        SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    }
}
