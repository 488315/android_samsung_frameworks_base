package com.android.systemui.shade;

import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$1$1;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.Iterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

    public /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationShadeWindowControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = this.f$0;
        switch (i) {
            case 0:
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
                                secNotificationShadeWindowControllerHelperImpl.activityManager.setHasTopUi(z);
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
            default:
                WindowRootView windowRootView = notificationShadeWindowControllerImpl.mWindowRootView;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = notificationShadeWindowControllerImpl.mHelper;
                secNotificationShadeWindowControllerHelperImpl2.notificationShadeView = windowRootView;
                SecNotificationShadeWindowControllerHelperImpl$initView$1 secNotificationShadeWindowControllerHelperImpl$initView$1 = new SecNotificationShadeWindowControllerHelperImpl$initView$1(secNotificationShadeWindowControllerHelperImpl2);
                final KeyguardVisibilityMonitor keyguardVisibilityMonitor = secNotificationShadeWindowControllerHelperImpl2.visibilityMonitor;
                keyguardVisibilityMonitor.listener = secNotificationShadeWindowControllerHelperImpl$initView$1;
                NotificationShadeWindowView notificationShadeWindowView = windowRootView instanceof NotificationShadeWindowView ? (NotificationShadeWindowView) windowRootView : null;
                if (notificationShadeWindowView != null) {
                    notificationShadeWindowView.mVisibilityChangedListener = new KeyguardVisibilityMonitor$registerMonitor$1$1(keyguardVisibilityMonitor);
                }
                ((KeyguardStateControllerImpl) keyguardVisibilityMonitor.getKeyguardStateController()).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$2
                    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                    public final void onKeyguardFadingAwayChanged() {
                        int i3 = KeyguardVisibilityMonitor.$r8$clinit;
                        KeyguardVisibilityMonitor keyguardVisibilityMonitor2 = keyguardVisibilityMonitor;
                        Log.d("KeyguardVisible", "onKeyguardFadingAwayChanged " + ((KeyguardStateControllerImpl) keyguardVisibilityMonitor2.getKeyguardStateController()).mKeyguardFadingAway);
                        if (((KeyguardStateControllerImpl) keyguardVisibilityMonitor2.getKeyguardStateController()).mKeyguardFadingAway) {
                            return;
                        }
                        SystemClock.elapsedRealtime();
                    }

                    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                    public final void onKeyguardGoingAwayChanged() {
                        int i3 = KeyguardVisibilityMonitor.$r8$clinit;
                        KeyguardVisibilityMonitor keyguardVisibilityMonitor2 = keyguardVisibilityMonitor;
                        Log.d("KeyguardVisible", "onKeyguardGoingAwayChanged " + ((KeyguardStateControllerImpl) keyguardVisibilityMonitor2.getKeyguardStateController()).mKeyguardGoingAway);
                        if (((KeyguardStateControllerImpl) keyguardVisibilityMonitor2.getKeyguardStateController()).mKeyguardGoingAway) {
                            return;
                        }
                        SystemClock.elapsedRealtime();
                    }
                });
                ShadeExpansionStateManager shadeExpansionStateManager = (ShadeExpansionStateManager) keyguardVisibilityMonitor.shadeExpansionStateManagerLazy.get();
                shadeExpansionStateManager.addExpansionListener(new ShadeExpansionListener() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$3$1
                    @Override // com.android.systemui.shade.ShadeExpansionListener
                    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                        int i3 = KeyguardVisibilityMonitor.$r8$clinit;
                        KeyguardVisibilityMonitor keyguardVisibilityMonitor2 = keyguardVisibilityMonitor;
                        keyguardVisibilityMonitor2.panelLog(shadeExpansionChangeEvent, null);
                        keyguardVisibilityMonitor2.panelExpansionChangeEvent = shadeExpansionChangeEvent;
                    }
                });
                shadeExpansionStateManager.stateListeners.add(new ShadeStateListener() { // from class: com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$3$2
                    @Override // com.android.systemui.shade.ShadeStateListener
                    public final void onPanelStateChanged$2(int i3) {
                        Integer numValueOf = Integer.valueOf(i3);
                        int i4 = KeyguardVisibilityMonitor.$r8$clinit;
                        KeyguardVisibilityMonitor keyguardVisibilityMonitor2 = keyguardVisibilityMonitor;
                        keyguardVisibilityMonitor2.panelLog(null, numValueOf);
                        if (keyguardVisibilityMonitor2.panelState != i3) {
                            Iterator it = CollectionsKt___CollectionsKt.toList(keyguardVisibilityMonitor2.panelStateChangedListeners).iterator();
                            while (it.hasNext()) {
                                ((Function2) it.next()).invoke(Integer.valueOf(keyguardVisibilityMonitor2.panelState), Integer.valueOf(i3));
                            }
                        }
                        keyguardVisibilityMonitor2.panelState = i3;
                    }
                });
                secNotificationShadeWindowControllerHelperImpl2.fullscreenHelper.getClass();
                break;
        }
    }
}
