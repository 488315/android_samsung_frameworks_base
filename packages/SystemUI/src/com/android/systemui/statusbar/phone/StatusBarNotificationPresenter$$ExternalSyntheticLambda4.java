package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.BooleanSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarNotificationPresenter$$ExternalSyntheticLambda4 implements BooleanSupplier {
    public final /* synthetic */ KeyguardStateController f$0;

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        return ((KeyguardStateControllerImpl) this.f$0).mCanDismissLockScreen;
    }
}
