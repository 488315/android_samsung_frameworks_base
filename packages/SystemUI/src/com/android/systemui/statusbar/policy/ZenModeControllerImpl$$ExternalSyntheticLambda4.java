package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ boolean f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((ZenModeController.Callback) obj).onZenAvailableChanged(this.f$0);
    }
}
