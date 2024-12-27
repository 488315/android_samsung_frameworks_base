package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WorkTileAutoAddable implements AutoAddable {
    public final AutoAddTracking.Always autoAddTracking;
    public final String description;
    public final TileSpec spec;
    public final UserTracker userTracker;
    public final WorkTileRestoreProcessor workTileRestoreProcessor;

    public WorkTileAutoAddable(UserTracker userTracker, WorkTileRestoreProcessor workTileRestoreProcessor) {
        TileSpec.Companion.getClass();
        TileSpec.Companion.create("WorkMode");
        this.description = "WorkTileAutoAddable (" + AutoAddTracking.Always.INSTANCE + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }
}
