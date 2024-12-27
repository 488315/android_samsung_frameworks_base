package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.CastController;

public final class CastAutoAddable extends CallbackControllerAutoAddable {
    public final CastController controller;
    public final String description;

    public CastAutoAddable(CastController castController) {
        super(castController);
        this.description = "CastAutoAddable (" + getAutoAddTracking() + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.qs.pipeline.domain.autoaddable.CallbackControllerAutoAddable
    public final TileSpec getSpec() {
        TileSpec.Companion.getClass();
        return TileSpec.Companion.create("cast");
    }
}
