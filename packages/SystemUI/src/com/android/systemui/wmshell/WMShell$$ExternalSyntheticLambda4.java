package com.android.systemui.wmshell;

import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.BooleanSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WMShell$$ExternalSyntheticLambda4 implements BooleanSupplier {
    public final /* synthetic */ WMShell f$0;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda4(WMShell wMShell) {
        this.f$0 = wMShell;
    }

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.f$0.mKeyguardStateController;
        return keyguardStateControllerImpl.mOccluded && keyguardStateControllerImpl.mShowing;
    }
}
