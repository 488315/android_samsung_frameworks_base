package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserTracker;

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
