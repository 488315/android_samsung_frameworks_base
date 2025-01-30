package com.android.systemui.pluginlock;

import com.android.systemui.shade.C2456x44c40078;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockMediatorImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockMediatorImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PluginLockMediatorImpl$$ExternalSyntheticLambda0(PluginLockMediatorImpl pluginLockMediatorImpl, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = pluginLockMediatorImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PluginLockMediatorImpl pluginLockMediatorImpl = this.f$0;
                boolean z = this.f$1;
                C2456x44c40078 c2456x44c40078 = pluginLockMediatorImpl.mWindowListener;
                c2456x44c40078.getClass();
                String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = c2456x44c40078.this$0;
                NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
                currentState.securedWindow = z;
                secNotificationShadeWindowControllerHelperImpl.apply(currentState);
                break;
            case 1:
                PluginLockMediatorImpl pluginLockMediatorImpl2 = this.f$0;
                pluginLockMediatorImpl2.mWindowListener.updateOverlayUserTimeout(this.f$1);
                break;
            case 2:
                PluginLockMediatorImpl pluginLockMediatorImpl3 = this.f$0;
                pluginLockMediatorImpl3.mWindowListener.updateBiometricRecognition(this.f$1);
                break;
            default:
                PluginLockMediatorImpl pluginLockMediatorImpl4 = this.f$0;
                SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(pluginLockMediatorImpl4.mWindowListener.this$0, this.f$1);
                break;
        }
    }
}
