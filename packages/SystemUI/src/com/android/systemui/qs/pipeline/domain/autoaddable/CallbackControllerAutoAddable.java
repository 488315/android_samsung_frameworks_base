package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.statusbar.policy.CallbackController;

public abstract class CallbackControllerAutoAddable implements AutoAddable {
    public final CallbackController controller;

    public CallbackControllerAutoAddable(CallbackController callbackController) {
    }

    public AutoAddTracking getAutoAddTracking() {
        return new AutoAddTracking.IfNotAdded(getSpec());
    }

    public abstract TileSpec getSpec();
}
