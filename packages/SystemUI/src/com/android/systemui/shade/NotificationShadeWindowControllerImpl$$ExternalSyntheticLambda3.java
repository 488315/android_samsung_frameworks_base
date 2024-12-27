package com.android.systemui.shade;

import android.os.RemoteException;
import android.util.Log;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.StatusBarState;
import java.util.function.Consumer;
import kotlinx.coroutines.BuildersKt;

public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$1] */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) obj;
                final boolean z = notificationShadeWindowControllerImpl.mHasTopUiChanged;
                final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
                secNotificationShadeWindowControllerHelperImpl.getClass();
                int i2 = KeyguardFastBioUnlockController.MODE_FLAG_ENABLED;
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = secNotificationShadeWindowControllerHelperImpl.fastUnlockController;
                if (keyguardFastBioUnlockController.isMode(i2)) {
                    keyguardFastBioUnlockController.executor.submit(new KeyguardFastBioUnlockController.Task(new Runnable() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$setHasTopUi$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                SecNotificationShadeWindowControllerHelperImpl.this.activityManager.setHasTopUi(z);
                            } catch (RemoteException e) {
                                Log.e("NotificationShadeWindowController", "Failed to call setHasTopUi", e);
                            }
                        }
                    }, "IActivityManager#setHasTopUi"));
                    notificationShadeWindowControllerImpl.mHasTopUi = notificationShadeWindowControllerImpl.mHasTopUiChanged;
                    break;
                } else {
                    try {
                        notificationShadeWindowControllerImpl.mActivityManager.setHasTopUi(notificationShadeWindowControllerImpl.mHasTopUiChanged);
                        break;
                    } catch (RemoteException e) {
                        Log.e("NotificationShadeWindowController", "Failed to call setHasTopUi", e);
                        return;
                    }
                }
            case 1:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = (NotificationShadeWindowControllerImpl) obj;
                WindowRootView windowRootView = notificationShadeWindowControllerImpl2.mWindowRootView;
                final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = notificationShadeWindowControllerImpl2.mHelper;
                secNotificationShadeWindowControllerHelperImpl2.notificationShadeView = windowRootView;
                secNotificationShadeWindowControllerHelperImpl2.visibilityMonitor.registerMonitor(windowRootView, new Consumer() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                        SecNotificationShadeWindowControllerHelperImpl.Provider provider = SecNotificationShadeWindowControllerHelperImpl.this.provider;
                        if (provider == null) {
                            provider = null;
                        }
                        boolean test = provider.isExpandedPredicate.test(Boolean.TRUE);
                        String statusBarState = StatusBarState.toString(SecNotificationShadeWindowControllerHelperImpl.this.getCurrentState().statusBarState);
                        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("verifyVisibility needsExpand=", ", isExpanded=", ", state=", booleanValue, test);
                        m.append(statusBarState);
                        com.android.systemui.keyguard.Log.d(str, m.toString());
                    }
                });
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    BuildersKt.launch$default(secNotificationShadeWindowControllerHelperImpl2.scope, null, null, new SecNotificationShadeWindowControllerHelperImpl$initView$2(secNotificationShadeWindowControllerHelperImpl2, null), 3);
                    break;
                }
                break;
            default:
                ((SecNotificationShadeWindowControllerHelperImpl) obj).initPost();
                break;
        }
    }
}
