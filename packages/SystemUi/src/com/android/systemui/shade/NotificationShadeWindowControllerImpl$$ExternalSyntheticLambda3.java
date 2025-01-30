package com.android.systemui.shade;

import android.net.Uri;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.blur.BouncerColorCurve;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v18, types: [com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initPost$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    @Override // java.lang.Runnable
    public final void run() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = (SecNotificationShadeWindowControllerHelperImpl) this.f$0;
                final NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
                secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation = secNotificationShadeWindowControllerHelperImpl.isLockScreenRotationAllowed();
                ArrayList arrayList = new ArrayList();
                if (((KeyguardStateControllerImpl) secNotificationShadeWindowControllerHelperImpl.keyguardStateController).isKeyguardScreenRotationAllowed()) {
                    arrayList.add(Settings.System.getUriFor("lock_screen_allow_rotation"));
                }
                currentState.keyguardUserActivityTimeout = secNotificationShadeWindowControllerHelperImpl.getUserActivityTimeout();
                arrayList.add(Settings.System.getUriFor("powersaving_switch"));
                arrayList.add(Settings.System.getUriFor("psm_switch"));
                arrayList.add(Settings.System.getUriFor("emergency_mode"));
                arrayList.add(Settings.Global.getUriFor("low_power"));
                arrayList.add(Settings.Secure.getUriFor("accessibility_interactive_ui_timeout_ms"));
                if (!arrayList.isEmpty()) {
                    ?? r4 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initPost$2
                        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                        public final void onChanged(Uri uri) {
                            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = SecNotificationShadeWindowControllerHelperImpl.this;
                            if (uri != null && Intrinsics.areEqual(uri, Settings.System.getUriFor("lock_screen_allow_rotation"))) {
                                String str = SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG;
                                secNotificationShadeWindowControllerHelperImpl2.isKeyguardScreenRotation = secNotificationShadeWindowControllerHelperImpl2.isLockScreenRotationAllowed();
                                return;
                            }
                            SecNotificationShadeWindowControllerHelperImpl$handler$1 secNotificationShadeWindowControllerHelperImpl$handler$1 = secNotificationShadeWindowControllerHelperImpl2.handler;
                            int i = SecNotificationShadeWindowControllerHelperImpl.MSG_USER_ACTIVITY_TIMEOUT_CHANGED;
                            if (secNotificationShadeWindowControllerHelperImpl$handler$1.hasMessages(i)) {
                                secNotificationShadeWindowControllerHelperImpl$handler$1.removeMessages(i);
                            }
                            secNotificationShadeWindowControllerHelperImpl$handler$1.sendMessage(secNotificationShadeWindowControllerHelperImpl$handler$1.obtainMessage(i, 0, 0));
                        }
                    };
                    secNotificationShadeWindowControllerHelperImpl.settingsHelperCallback = r4;
                    Uri[] uriArr = (Uri[]) arrayList.toArray(new Uri[0]);
                    secNotificationShadeWindowControllerHelperImpl.settingsHelper.registerCallback(r4, (Uri[]) Arrays.copyOf(uriArr, uriArr.length));
                }
                PluginLockMediator pluginLockMediator = secNotificationShadeWindowControllerHelperImpl.pluginLockMediator;
                if (pluginLockMediator != null) {
                    ((PluginLockMediatorImpl) pluginLockMediator).mWindowListener = secNotificationShadeWindowControllerHelperImpl.pluginLockListener;
                }
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && !LsRune.KEYGUARD_SUB_DISPLAY_ROTATIONAL) {
                    secNotificationShadeWindowControllerHelperImpl.displayLifecycle.addObserver(new DisplayLifecycle.Observer() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$initPost$3
                        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
                        public final void onFolderStateChanged(boolean z2) {
                            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = SecNotificationShadeWindowControllerHelperImpl.this;
                            boolean z3 = secNotificationShadeWindowControllerHelperImpl2.isKeyguardScreenRotation;
                            if (z2) {
                                secNotificationShadeWindowControllerHelperImpl2.isKeyguardScreenRotation = secNotificationShadeWindowControllerHelperImpl2.isLockScreenRotationAllowed();
                            } else {
                                secNotificationShadeWindowControllerHelperImpl2.isKeyguardScreenRotation = false;
                            }
                            if (z3 != secNotificationShadeWindowControllerHelperImpl2.isKeyguardScreenRotation) {
                                secNotificationShadeWindowControllerHelperImpl2.apply(currentState);
                            }
                        }
                    });
                }
                if (LsRune.SECURITY_COLOR_CURVE_BLUR) {
                    secNotificationShadeWindowControllerHelperImpl.bouncerColorCurve = new BouncerColorCurve();
                }
                secNotificationShadeWindowControllerHelperImpl.isInitFinished = true;
                break;
            default:
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.f$0;
                final boolean z2 = notificationShadeWindowControllerImpl.mHasTopUiChanged;
                final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = notificationShadeWindowControllerImpl.mHelper;
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = secNotificationShadeWindowControllerHelperImpl2.fastUnlockController;
                if (keyguardFastBioUnlockController.isEnabled()) {
                    keyguardFastBioUnlockController.executor.submit(new KeyguardFastBioUnlockController.Task(new Runnable() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$setHasTopUi$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                SecNotificationShadeWindowControllerHelperImpl.this.activityManager.setHasTopUi(z2);
                            } catch (RemoteException e) {
                                Log.e("NotificationShadeWindowController", "Failed to call setHasTopUi", e);
                            }
                        }
                    }, "IActivityManager#setHasTopUi"));
                    z = true;
                }
                if (!z) {
                    try {
                        notificationShadeWindowControllerImpl.mActivityManager.setHasTopUi(notificationShadeWindowControllerImpl.mHasTopUiChanged);
                    } catch (RemoteException e) {
                        Log.e("NotificationShadeWindowController", "Failed to call setHasTopUi", e);
                    }
                    notificationShadeWindowControllerImpl.mHasTopUi = notificationShadeWindowControllerImpl.mHasTopUiChanged;
                    break;
                } else {
                    notificationShadeWindowControllerImpl.mHasTopUi = notificationShadeWindowControllerImpl.mHasTopUiChanged;
                    break;
                }
        }
    }
}
