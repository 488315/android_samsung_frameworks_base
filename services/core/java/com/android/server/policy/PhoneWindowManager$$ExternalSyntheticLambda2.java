package com.android.server.policy;

import android.view.KeyEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhoneWindowManager$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ KeyEvent f$1;

    public /* synthetic */ PhoneWindowManager$$ExternalSyntheticLambda2(
            Object obj, KeyEvent keyEvent, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = keyEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((PhoneWindowManager) this.f$0).handleShortPressOnHome(this.f$1);
                break;
            case 1:
                PhoneWindowManager.DisplayHomeButtonHandler displayHomeButtonHandler =
                        (PhoneWindowManager.DisplayHomeButtonHandler) this.f$0;
                PhoneWindowManager.this.handleShortPressOnHome(this.f$1);
                break;
            case 2:
                PhoneWindowManager.DisplayHomeButtonHandler displayHomeButtonHandler2 =
                        (PhoneWindowManager.DisplayHomeButtonHandler) this.f$0;
                displayHomeButtonHandler2.handleDoubleTapOnHome(
                        this.f$1, displayHomeButtonHandler2.mDisplayId);
                break;
            case 3:
                ((PhoneWindowManager.DisplayHomeButtonHandler) this.f$0)
                        .handleDoubleTapOnHome(this.f$1, 0);
                break;
            default:
                PhoneWindowManager.DisplayHomeButtonHandler displayHomeButtonHandler3 =
                        (PhoneWindowManager.DisplayHomeButtonHandler) this.f$0;
                KeyEvent keyEvent = this.f$1;
                if (!displayHomeButtonHandler3.mHomeConsumed
                        && PhoneWindowManager.this.mExt.handleLongPressOnHomeWithPolicy(
                                keyEvent.getDeviceId(), keyEvent.getEventTime())) {
                    displayHomeButtonHandler3.mHomeConsumed = true;
                    break;
                }
                break;
        }
    }
}
