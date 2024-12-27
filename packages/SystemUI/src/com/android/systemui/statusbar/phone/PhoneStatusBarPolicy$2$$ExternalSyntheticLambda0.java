package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhoneStatusBarPolicy$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhoneStatusBarPolicy.AnonymousClass2 f$0;

    public /* synthetic */ PhoneStatusBarPolicy$2$$ExternalSyntheticLambda0(PhoneStatusBarPolicy.AnonymousClass2 anonymousClass2, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PhoneStatusBarPolicy.AnonymousClass2 anonymousClass2 = this.f$0;
        switch (i) {
            case 0:
                ((UserInfoControllerImpl) anonymousClass2.this$0.mUserInfoController).reloadUserInfo();
                break;
            default:
                anonymousClass2.getClass();
                boolean z = PhoneStatusBarPolicy.DEBUG;
                PhoneStatusBarPolicy phoneStatusBarPolicy = anonymousClass2.this$0;
                phoneStatusBarPolicy.updateAlarm();
                phoneStatusBarPolicy.updateProfileIcon();
                phoneStatusBarPolicy.onUserSetupChanged();
                break;
        }
    }
}
