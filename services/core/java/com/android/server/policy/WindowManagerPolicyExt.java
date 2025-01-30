package com.android.server.policy;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.wm.TspStateController;
import com.android.server.wm.WmCoverState;
import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.view.SemWindowManager;
import java.util.List;

/* loaded from: classes3.dex */
public interface WindowManagerPolicyExt {

    public interface WindowManagerFuncs {
        void cancelPendingTakeScreenshotRunnable();

        int getDisplayIdForPointerIcon();

        int getFocusedTaskIdWithoutHomeOrRecents();

        int getPenState();

        void handleDexDpadShortcut(IBinder iBinder, KeyEvent keyEvent);

        void handleDexMetaKeyForSnapping();

        boolean isRunningRecentAnimation();

        void postRotationInfoForAudioManager();

        void reconfigureDisplay(int i);

        void removeTask(int i);

        void resetScreenshotConnections();

        void sendShortcutKey(KeyEvent keyEvent);

        void sendTakeScreenshotRunnable(int i, int i2);
    }

    public interface WindowStateExt {
        WindowManager.LayoutParams getAttrs();

        int getBaseLayer();
    }

    static int getMultiWindowLayerFromTypeLw(int i) {
        if (i == 2603 || i == 2604) {
            return 3;
        }
        return i != 2607 ? -1 : 4;
    }

    static int getSubWindowLayerFromTypeLw(int i) {
        return i != 1100 ? 0 : 1;
    }

    static boolean isAppWindowType(int i) {
        return i >= 1 && i <= 1999;
    }

    static boolean isSystemWindowType(int i) {
        return i >= 2000 && i <= 2999;
    }

    void clearKeyCustomizationInfoByAction(int i, int i2, int i3);

    void clearKeyCustomizationInfoByKeyCode(int i, int i2);

    List getBackupKeyCustomizationInfoList();

    SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3);

    SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2);

    SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2);

    int getWakingUpReason();

    void handleNotifyPogoKeyboardStatus(boolean z);

    void init();

    boolean interceptQuickAccess(int i, float f, float f2);

    boolean isImeBtnOnGestureEnabled();

    boolean isInteractionControlEnabled();

    boolean isKeyguardOccluded(int i);

    boolean isLockTaskModePinned();

    boolean isMetaKeyEventRequested(ComponentName componentName);

    boolean isNavGetureHintEnabled();

    boolean isScreenTurnedOn();

    boolean isSystemKeyEventRequested(int i, ComponentName componentName);

    void keyguardGoingAwayWithFingerprintUnlock(boolean z);

    void launchHomeForDesktopMode(int i);

    void notifyPenSwitchChanged(long j, boolean z, boolean z2);

    void notifyRequestedGameToolsWin(boolean z);

    void onDexModeChangedLw(int i);

    void onFocusChangedLw(WindowManagerPolicy.WindowState windowState, int i);

    void onKeyguardOccludedChangedLw(boolean z, int i, boolean z2);

    void onLockTaskFeaturesChanged(SparseIntArray sparseIntArray);

    void onLockTaskStateChanged(int i);

    void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo);

    void registerSystemKeyEvent(int i, ComponentName componentName, int i2);

    void removeKeyCustomizationInfo(int i, int i2, int i3);

    void removeKeyCustomizationInfoByPackage(String str, int i, int i2);

    void requestMetaKeyEvent(ComponentName componentName, boolean z);

    boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z);

    void restoreKeyCustomizationInfo(List list);

    void setCarLifeDisplay(WindowManagerPolicy.DisplayContentInfo displayContentInfo);

    void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent);

    void setRotation(int i);

    void setTspStateController(TspStateController tspStateController);

    void showBootDialog(int i);

    boolean startGameToolsService(int i, int i2, boolean z);

    void startLockscreenFingerprintAuth();

    void unregisterSystemKeyEvent(int i, ComponentName componentName);

    void updateTopActivity(ComponentName componentName);

    static int getWindowLayerFromTypeLw(int i, boolean z, boolean z2) {
        int windowLayerFromTypeLw;
        switch (i) {
            case FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1080P_FHD /* 2095 */:
                return 6;
            case 2099:
            case 2411:
                if (!WmCoverState.isEnabled() || (windowLayerFromTypeLw = WmCoverState.getInstance().getWindowLayerFromTypeLw(i)) == -1) {
                    return 21;
                }
                return windowLayerFromTypeLw;
            case 2225:
                return 10;
            case 2226:
            case 2415:
                return 18;
            case 2227:
                return 31;
            case 2228:
            case 2601:
            case 2605:
                return 26;
            case 2270:
            case 2271:
            case 2621:
                return 15;
            case 2274:
            case 2281:
            case 2440:
            case 2441:
                return 23;
            case 2280:
            case 2401:
            case 2405:
                return 25;
            case 2402:
            case 2412:
                return 7;
            case 2403:
                return 5;
            case 2406:
            case 2430:
            case 2632:
                return 3;
            case 2407:
            case 2619:
                return 34;
            case 2408:
                return 30;
            case 2414:
                return 22;
            case 2431:
            case FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1080X2400 /* 2618 */:
                return 12;
            case 2442:
            case 2606:
            case 2623:
                return 24;
            case 2600:
                return 31;
            case 2620:
                return 1;
            case 2622:
                return 17;
            case 2630:
                return 28;
            case 2631:
                return 27;
            case 2633:
                return 16;
            default:
                int multiWindowLayerFromTypeLw = getMultiWindowLayerFromTypeLw(i);
                if (multiWindowLayerFromTypeLw > 0) {
                    return multiWindowLayerFromTypeLw;
                }
                return -1;
        }
    }
}
