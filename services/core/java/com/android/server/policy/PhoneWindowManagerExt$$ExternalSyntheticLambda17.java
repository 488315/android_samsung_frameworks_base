package com.android.server.policy;

import android.view.KeyEvent;
import com.android.server.policy.PhoneWindowManagerExt;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhoneWindowManagerExt$$ExternalSyntheticLambda17 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ KeyEvent f$1;

    public /* synthetic */ PhoneWindowManagerExt$$ExternalSyntheticLambda17(Object obj, KeyEvent keyEvent, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = keyEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PhoneWindowManagerExt phoneWindowManagerExt = (PhoneWindowManagerExt) this.f$0;
                phoneWindowManagerExt.mPolicy.postShortPressOnHome(this.f$1);
                break;
            default:
                PhoneWindowManagerExt.HomeKeyRule homeKeyRule = (PhoneWindowManagerExt.HomeKeyRule) this.f$0;
                KeyEvent keyEvent = this.f$1;
                homeKeyRule.getClass();
                homeKeyRule.this$0.handleLongPressOnHomeWithPolicy(keyEvent.getDeviceId(), keyEvent.getEventTime());
                break;
        }
    }
}
