package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.DataSaverController;

public final class DataSaverAutoAddable extends CallbackControllerAutoAddable {
    public final String description;

    public DataSaverAutoAddable(DataSaverController dataSaverController) {
        super(dataSaverController);
        this.description = "DataSaverAutoAddable (" + getAutoAddTracking() + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.qs.pipeline.domain.autoaddable.CallbackControllerAutoAddable
    public final TileSpec getSpec() {
        TileSpec.Companion.getClass();
        return TileSpec.Companion.create("saver");
    }
}
