package com.android.systemui.qs.pipeline.data.restoreprocessors;

import android.util.SparseIntArray;
import com.android.systemui.qs.pipeline.data.model.RestoreProcessor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

public final class WorkTileRestoreProcessor implements RestoreProcessor {
    public static final TileSpec TILE_SPEC = null;
    public final SharedFlowImpl _removeTrackingForUser;
    public final SparseIntArray lastRestorePosition;

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
