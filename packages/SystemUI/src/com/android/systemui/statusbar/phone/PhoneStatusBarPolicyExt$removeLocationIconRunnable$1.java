package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PhoneStatusBarPolicyExt$removeLocationIconRunnable$1 implements Runnable {
    public final /* synthetic */ PhoneStatusBarPolicyExt this$0;

    public PhoneStatusBarPolicyExt$removeLocationIconRunnable$1(PhoneStatusBarPolicyExt phoneStatusBarPolicyExt) {
        this.this$0 = phoneStatusBarPolicyExt;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PhoneStatusBarPolicyExt phoneStatusBarPolicyExt = this.this$0;
        ((StatusBarIconControllerImpl) phoneStatusBarPolicyExt.iconController).setIconVisibility(phoneStatusBarPolicyExt.SLOT_NAME_LOCATION, false);
    }
}
