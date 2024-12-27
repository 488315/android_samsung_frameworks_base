package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.WalletController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
