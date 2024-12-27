package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.CallbackController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class CallbackControllerAutoAddable implements AutoAddable {
    public final CallbackController controller;

    public CallbackControllerAutoAddable(CallbackController callbackController) {
    }

    public AutoAddTracking getAutoAddTracking() {
        return new AutoAddTracking.IfNotAdded(getSpec());
    }

    public abstract TileSpec getSpec();
}
