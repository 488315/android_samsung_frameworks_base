package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.WalletController;

public final class WalletAutoAddable implements AutoAddable {
    public final String description;
    public final TileSpec spec;
    public final WalletController walletController;

    public WalletAutoAddable(WalletController walletController) {
        TileSpec.Companion.getClass();
        this.spec = TileSpec.Companion.create("wallet");
        this.description = "WalletAutoAddable (" + new AutoAddTracking.IfNotAdded(this.spec) + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }
}
