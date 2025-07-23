package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhoneStatusBarPolicy$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhoneStatusBarPolicy.AnonymousClass3 f$0;

    public /* synthetic */ PhoneStatusBarPolicy$3$$ExternalSyntheticLambda0(PhoneStatusBarPolicy.AnonymousClass3 anonymousClass3, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PhoneStatusBarPolicy.AnonymousClass3 anonymousClass3 = this.f$0;
        switch (i) {
            case 0:
                anonymousClass3.getClass();
                boolean z = PhoneStatusBarPolicy.DEBUG;
                PhoneStatusBarPolicy phoneStatusBarPolicy = anonymousClass3.this$0;
                phoneStatusBarPolicy.updateAlarm();
                phoneStatusBarPolicy.updateProfileIcon();
                phoneStatusBarPolicy.onUserSetupChanged();
                break;
            default:
                ((UserInfoControllerImpl) anonymousClass3.this$0.mUserInfoController).reloadUserInfo();
                break;
        }
    }
}
