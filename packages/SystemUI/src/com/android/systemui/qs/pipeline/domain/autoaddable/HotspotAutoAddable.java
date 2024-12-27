package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.HotspotController;

public final class HotspotAutoAddable extends CallbackControllerAutoAddable {
    public final String description;

    public HotspotAutoAddable(HotspotController hotspotController) {
        super(hotspotController);
        this.description = "HotspotAutoAddable (" + getAutoAddTracking() + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.qs.pipeline.domain.autoaddable.CallbackControllerAutoAddable
    public final TileSpec getSpec() {
        TileSpec.Companion.getClass();
        return TileSpec.Companion.create("Hotspot");
    }
}
