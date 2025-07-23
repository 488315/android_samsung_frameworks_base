package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ boolean f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((ZenModeController.Callback) obj).onZenAvailableChanged(this.f$0);
    }
}
