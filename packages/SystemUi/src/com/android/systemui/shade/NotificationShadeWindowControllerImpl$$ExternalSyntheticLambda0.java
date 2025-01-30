package com.android.systemui.shade;

import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.keyguard.Log;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecNotificationShadeWindowControllerHelperImpl f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = secNotificationShadeWindowControllerHelperImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.f$0;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                if (secNotificationShadeWindowControllerHelperImpl.getCurrentState().keyguardFadingAway != booleanValue) {
                    Log.m138d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, AbstractC0866xb1ce8deb.m86m("keyguardFadingAway ", booleanValue));
                    break;
                }
                break;
            default:
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = this.f$0;
                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                if (secNotificationShadeWindowControllerHelperImpl2.getCurrentState().keyguardGoingAway != booleanValue2) {
                    Log.m138d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, AbstractC0866xb1ce8deb.m86m("keyguardGoingAway ", booleanValue2));
                    break;
                }
                break;
        }
    }
}
