package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.BooleanSupplier;

/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarNotificationPresenter$$ExternalSyntheticLambda4 implements BooleanSupplier {
    public final /* synthetic */ KeyguardStateController f$0;

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        return ((KeyguardStateControllerImpl) this.f$0).mCanDismissLockScreen;
    }
}
