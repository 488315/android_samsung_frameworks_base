package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;

/* loaded from: classes3.dex */
public final /* synthetic */ class PhoneStatusBarPolicy$7$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PhoneStatusBarPolicy.AnonymousClass7 f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PhoneStatusBarPolicy$7$$ExternalSyntheticLambda0(PhoneStatusBarPolicy.AnonymousClass7 anonymousClass7, boolean z) {
        this.f$0 = anonymousClass7;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PhoneStatusBarPolicy.AnonymousClass7 anonymousClass7 = this.f$0;
        boolean z = this.f$1;
        PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotSensorsOff, z);
    }
}
