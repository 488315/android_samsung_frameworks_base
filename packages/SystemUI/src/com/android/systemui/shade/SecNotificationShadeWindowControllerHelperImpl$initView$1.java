package com.android.systemui.shade;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.Log;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.StatusBarState;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecNotificationShadeWindowControllerHelperImpl$initView$1 implements Consumer {
    public final /* synthetic */ SecNotificationShadeWindowControllerHelperImpl this$0;

    public SecNotificationShadeWindowControllerHelperImpl$initView$1(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl) {
        this.this$0 = secNotificationShadeWindowControllerHelperImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
        SecNotificationShadeWindowControllerHelperImpl.Provider provider = this.this$0.provider;
        if (provider == null) {
            provider = null;
        }
        boolean test = provider.isExpandedPredicate.test(Boolean.TRUE);
        String statusBarState = StatusBarState.toString(this.this$0.getCurrentState().statusBarState);
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("verifyVisibility needsExpand=", ", isExpanded=", ", state=", booleanValue, test);
        m.append(statusBarState);
        Log.d(str, m.toString());
    }
}
