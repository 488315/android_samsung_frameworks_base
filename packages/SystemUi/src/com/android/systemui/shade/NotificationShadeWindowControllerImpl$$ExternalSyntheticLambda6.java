package com.android.systemui.shade;

import android.view.ViewGroup;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.Log;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.StatusBarState;
import java.io.PrintWriter;
import java.util.function.Consumer;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6(NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationShadeWindowControllerImpl;
        this.f$1 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$1] */
    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
                secNotificationShadeWindowControllerHelperImpl.getClass();
                if (LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW) {
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  EMM=", secNotificationShadeWindowControllerHelperImpl.engineerModeManager.isCaptureEnabled, printWriter);
                    break;
                }
                break;
            default:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = this.f$0;
                ViewGroup viewGroup = (ViewGroup) this.f$1;
                final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = notificationShadeWindowControllerImpl2.mHelper;
                secNotificationShadeWindowControllerHelperImpl2.notificationShadeView = viewGroup;
                secNotificationShadeWindowControllerHelperImpl2.visibilityMonitor.registerMonitor(viewGroup, new Consumer() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initView$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                        SecNotificationShadeWindowControllerHelperImpl.Provider provider = SecNotificationShadeWindowControllerHelperImpl.this.provider;
                        if (provider == null) {
                            provider = null;
                        }
                        boolean test = provider.isExpandedPredicate.test(Boolean.TRUE);
                        String statusBarState = StatusBarState.toString(SecNotificationShadeWindowControllerHelperImpl.this.getCurrentState().statusBarState);
                        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("verifyVisibility needsExpand=", booleanValue, ", isExpanded=", test, ", state=");
                        m69m.append(statusBarState);
                        Log.m138d(str, m69m.toString());
                    }
                });
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    BuildersKt.launch$default(secNotificationShadeWindowControllerHelperImpl2.scope, null, null, new SecNotificationShadeWindowControllerHelperImpl$initView$2(secNotificationShadeWindowControllerHelperImpl2, null), 3);
                    break;
                }
                break;
        }
    }
}
