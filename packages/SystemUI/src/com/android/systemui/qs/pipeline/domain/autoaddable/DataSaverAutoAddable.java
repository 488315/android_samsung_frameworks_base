package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.DataSaverController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
