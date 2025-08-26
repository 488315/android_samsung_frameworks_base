package com.android.systemui.shade;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.Log;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.StatusBarState;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class SecNotificationShadeWindowControllerHelperImpl$initView$1 implements Consumer {
    public final /* synthetic */ SecNotificationShadeWindowControllerHelperImpl this$0;

    public SecNotificationShadeWindowControllerHelperImpl$initView$1(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl) {
        this.this$0 = secNotificationShadeWindowControllerHelperImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
        SecNotificationShadeWindowControllerHelperImpl.Provider provider = this.this$0.provider;
        if (provider == null) {
            provider = null;
        }
        boolean zTest = provider.isExpandedPredicate.test(Boolean.TRUE);
        String string = StatusBarState.toString(this.this$0.getCurrentState().statusBarState);
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("verifyVisibility needsExpand=", ", isExpanded=", ", state=", zBooleanValue, zTest);
        sbM.append(string);
        Log.d(str, sbM.toString());
    }
}
