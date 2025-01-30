package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PhoneStatusBarPolicy.C30946 f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0(PhoneStatusBarPolicy.C30946 c30946, boolean z) {
        this.f$0 = c30946;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PhoneStatusBarPolicy.C30946 c30946 = this.f$0;
        boolean z = this.f$1;
        PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotSensorsOff, z);
    }
}
