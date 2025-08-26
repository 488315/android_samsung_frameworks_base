package com.android.systemui.qs.pipeline.data.restoreprocessors;

import android.util.SparseIntArray;
import com.android.systemui.qs.pipeline.data.model.RestoreProcessor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* loaded from: classes2.dex */
public final class WorkTileRestoreProcessor implements RestoreProcessor {
    public static final TileSpec TILE_SPEC;
    public final SparseIntArray lastRestorePosition = new SparseIntArray();
    public final SharedFlowImpl _removeTrackingForUser = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TileSpec.Companion.getClass();
        TILE_SPEC = TileSpec.Companion.create("WorkMode");
    }
}
