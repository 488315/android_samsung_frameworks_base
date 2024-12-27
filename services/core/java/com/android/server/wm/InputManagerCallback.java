package com.android.server.wm;

import android.content.Intent;
import android.gui.StalledTransactionInfo;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.SurfaceControl;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;
import com.android.server.policy.KeyCustomizationConstants;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.policy.WindowWakeUpPolicy;
import com.android.server.policy.WindowWakeUpPolicyInternal;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;

import java.util.OptionalInt;

public final class InputManagerCallback implements InputManagerService.WindowManagerCallbacks {
    public boolean mInputDevicesReady;
    public final Object mInputDevicesReadyMonitor = new Object();
    public boolean mInputDispatchEnabled;
    public boolean mInputDispatchFrozen;
    public final WindowManagerService mService;

    public InputManagerCallback(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    public static String timeoutMessage(OptionalInt optionalInt, String str) {
        StalledTransactionInfo stalledTransactionInfo;
        String m =
                str == null
                        ? "Input dispatching timed out."
                        : XmlUtils$$ExternalSyntheticOutline0.m(
                                "Input dispatching timed out (", str, ").");
        return (optionalInt.isEmpty()
                        || (stalledTransactionInfo =
                                        SurfaceControl.getStalledTransactionInfo(
                                                optionalInt.getAsInt()))
                                == null)
                ? m
                : String.format(
                        "%s Buffer processing for the associated surface is stuck due to an"
                            + " unsignaled fence (window=%s, bufferId=0x%016X, frameNumber=%s)."
                            + " This potentially indicates a GPU hang.",
                        m,
                        stalledTransactionInfo.layerName,
                        Long.valueOf(stalledTransactionInfo.bufferId),
                        Long.valueOf(stalledTransactionInfo.frameNumber));
    }

    public final SurfaceControl createSurfaceForGestureMonitor(int i, String str) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.e(
                            "WindowManager",
                            "Failed to create a gesture monitor on display: "
                                    + i
                                    + " - DisplayContent not found.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                SurfaceControl surfaceControl = displayContent.mInputOverlayLayer;
                if (surfaceControl != null) {
                    SurfaceControl build =
                            this.mService
                                    .makeSurfaceBuilder(displayContent.mSession)
                                    .setContainerLayer()
                                    .setName(str)
                                    .setCallsite("createSurfaceForGestureMonitor")
                                    .setParent(surfaceControl)
                                    .setCallsite(
                                            "InputManagerCallback.createSurfaceForGestureMonitor")
                                    .build();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return build;
                }
                Slog.e(
                        "WindowManager",
                        "Failed to create a gesture monitor on display: "
                                + i
                                + " - Input overlay layer is not initialized.");
                WindowManagerService.resetPriorityAfterLockedSection();
                return null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.KeyEvent dispatchUnhandledKey(
            android.os.IBinder r27, android.view.KeyEvent r28, int r29) {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.InputManagerCallback.dispatchUnhandledKey(android.os.IBinder,"
                    + " android.view.KeyEvent, int):android.view.KeyEvent");
    }

    public final int getPointerDisplayId() {
        int i;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mService.mAtmService.mDexController.getDexModeLocked() == 2) {
                    int i2 = this.mService.mInputManager.mDisplayIdForPointerIcon;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return i2;
                }
                WindowManagerService windowManagerService = this.mService;
                int i3 = 0;
                if (!windowManagerService.mForceDesktopModeOnExternalDisplays) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                }
                for (int size = windowManagerService.mRoot.mChildren.size() - 1;
                        size >= 0;
                        size--) {
                    DisplayContent displayContent =
                            (DisplayContent) this.mService.mRoot.mChildren.get(size);
                    if (displayContent.mDisplayInfo.state != 1
                            && displayContent.mDisplay.getType() != 1) {
                        if (displayContent.getWindowingMode() == 5) {
                            int i4 = displayContent.mDisplayId;
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return i4;
                        }
                        if (i3 == 0 && (i = displayContent.mDisplayId) != 0) {
                            i3 = i;
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return i3;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final int getPointerLayer() {
        this.mService.mPolicy.getClass();
        return (WindowManagerPolicy.getWindowLayerFromTypeLw(2018) * 10000) + 1000;
    }

    public final long interceptKeyBeforeDispatching(IBinder iBinder, KeyEvent keyEvent, int i) {
        return ((PhoneWindowManager) this.mService.mPolicy)
                .interceptKeyBeforeDispatching(iBinder, keyEvent, i);
    }

    public final int interceptKeyBeforeQueueing(KeyEvent keyEvent, int i) {
        return ((PhoneWindowManager) this.mService.mPolicy).interceptKeyBeforeQueueing(keyEvent, i);
    }

    public final int interceptMotionBeforeQueueingNonInteractive(
            int i, int i2, int i3, long j, int i4) {
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) this.mService.mPolicy;
        int i5 = i4 & 1;
        if (i5 != 0) {
            WindowWakeUpPolicy windowWakeUpPolicy = phoneWindowManager.mWindowWakeUpPolicy;
            long j2 = j / 1000000;
            boolean z = i3 == 0;
            if (windowWakeUpPolicy.canWakeUp(windowWakeUpPolicy.mAllowTheaterModeWakeFromMotion)) {
                WindowWakeUpPolicyInternal.InputWakeUpDelegate inputWakeUpDelegate =
                        windowWakeUpPolicy.mInputWakeUpDelegate;
                if (inputWakeUpDelegate != null
                        && inputWakeUpDelegate.wakeUpFromMotion(j2, i2, z)) {
                    return 1;
                }
                windowWakeUpPolicy.wakeUp(j2, 7, "MOTION");
                return 1;
            }
        }
        if (phoneWindowManager.shouldDispatchInputWhenNonInteractive(i)) {
            return 1;
        }
        if (Settings.Global.getInt(
                                phoneWindowManager.mContext.getContentResolver(),
                                "theater_mode_on",
                                0)
                        == 1
                && i5 != 0) {
            WindowWakeUpPolicy windowWakeUpPolicy2 = phoneWindowManager.mWindowWakeUpPolicy;
            long j3 = j / 1000000;
            boolean z2 = i3 == 0;
            if (windowWakeUpPolicy2.canWakeUp(
                    windowWakeUpPolicy2.mAllowTheaterModeWakeFromMotion)) {
                WindowWakeUpPolicyInternal.InputWakeUpDelegate inputWakeUpDelegate2 =
                        windowWakeUpPolicy2.mInputWakeUpDelegate;
                if (inputWakeUpDelegate2 != null
                        && inputWakeUpDelegate2.wakeUpFromMotion(j3, i2, z2)) {
                    return 1;
                }
                windowWakeUpPolicy2.wakeUp(j3, 7, "MOTION");
                return 1;
            }
        }
        return 0;
    }

    public final void interceptQuickAccess(int i, float f, float f2) {
        PhoneWindowManagerExt phoneWindowManagerExt = this.mService.mExt.mPolicyExt;
        phoneWindowManagerExt.getClass();
        if (i != 4) {
            if (i != 225 && i != 226) {
                switch (i) {
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        Log.d("PhoneWindowManagerExt", "interceptQuickAccess, CHANGE_AOD_MODE");
                        Intent intent =
                                new Intent(
                                        "com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
                        intent.putExtra("info", i);
                        intent.putExtra("location", new float[] {f, f2});
                        intent.addFlags(32);
                        phoneWindowManagerExt.mContext.sendBroadcastAsUser(
                                intent,
                                UserHandle.CURRENT,
                                "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER");
                        if (CoreRune.FW_FOLD_SA_LOGGING && i == 8) {
                            phoneWindowManagerExt.sendFoldSaLoggingCanceledIfNeeded();
                            break;
                        }
                        break;
                    default:
                        switch (i) {
                        }
                }
            }
            Intent intent2 = new Intent("com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY");
            intent2.putExtra("info", i);
            intent2.putExtra("location", new float[] {f, f2});
            intent2.addFlags(32);
            phoneWindowManagerExt.mContext.sendBroadcastAsUser(
                    intent2,
                    UserHandle.CURRENT,
                    "com.samsung.android.permission.BROADCAST_QUICKACCESS");
        } else {
            Log.d("PhoneWindowManagerExt", "interceptQuickAccess, quickpay");
            Intent intent3 = new Intent("com.samsung.android.spay.quickpay");
            intent3.addFlags(32);
            intent3.putExtra("displayId", phoneWindowManagerExt.getDisplayId(null));
            phoneWindowManagerExt.mContext.sendBroadcastAsUser(
                    intent3, UserHandle.CURRENT, "com.samsung.android.spay.permission.SIMPLE_PAY");
        }
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
    }

    public final void notifyCameraLensCoverSwitchChanged(long j, boolean z) {
        ((PhoneWindowManager) this.mService.mPolicy).notifyCameraLensCoverSwitchChanged(j, z);
    }

    @Override // com.android.server.input.InputManagerInternal$LidSwitchCallback
    public final void notifyLidSwitchChanged(boolean z) {
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) this.mService.mPolicy;
        if (z == phoneWindowManager.mDefaultDisplayPolicy.mLidState) {
            return;
        }
        phoneWindowManager.mDefaultDisplayPolicy.mLidState = z ? 1 : 0;
        phoneWindowManager.applyLidSwitchState();
        phoneWindowManager.mWindowManagerFuncs.updateRotation(true, false);
        if (!z) {
            if (Settings.Global.getInt(
                            phoneWindowManager.mContext.getContentResolver(), "lid_behavior", 0)
                    != 1) {
                phoneWindowManager.mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
            }
        } else {
            WindowWakeUpPolicy windowWakeUpPolicy = phoneWindowManager.mWindowWakeUpPolicy;
            if (windowWakeUpPolicy.canWakeUp(
                    windowWakeUpPolicy.mAllowTheaterModeWakeFromLidSwitch)) {
                windowWakeUpPolicy.wakeUp(windowWakeUpPolicy.mClock.uptimeMillis(), 9, "LID");
            }
        }
    }

    public final void notifyStickyModifierStateChanged(int i) {
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) this.mService.mPolicy;
        int i2 = phoneWindowManager.mLastModifierState ^ i;
        phoneWindowManager.mLastModifierState = i;
        if ((i2 & 2) != 0) {
            if (phoneWindowManager.mExt.hasMetaKeyPass()) {
                return;
            }
            if ((i & 2) == 0) {
                if (phoneWindowManager.mAltTabConsumedByDeX) {
                    phoneWindowManager.mAltTabConsumedByDeX = false;
                    phoneWindowManager.mActivityTaskManagerInternal.releaseAltTabKeyConsumer();
                }
                int i3 = phoneWindowManager.mRecentAppsHeldModifiers;
                if (i3 != 0 && (i3 & i) == 0) {
                    phoneWindowManager.mRecentAppsHeldModifiers = 0;
                    if (phoneWindowManager.mExt.isInDexMode()) {
                        phoneWindowManager.hideRecentApps(
                                phoneWindowManager.mContext.getDisplay().getDisplayId(),
                                true,
                                false);
                        return;
                    } else {
                        phoneWindowManager.hideRecentApps(0, true, false);
                        return;
                    }
                }
                if (phoneWindowManager.mPendingCapsLockToggle) {
                    phoneWindowManager.mPendingCapsLockToggle = false;
                    return;
                }
            } else if ((i & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) != 0) {
                phoneWindowManager.mPendingCapsLockToggle = true;
                phoneWindowManager.mPendingMetaAction = false;
            } else {
                phoneWindowManager.mPendingCapsLockToggle = false;
            }
        }
        if ((i2 & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) == 0
                || phoneWindowManager.mExt.hasMetaKeyPass()) {
            return;
        }
        if ((i & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) != 0) {
            if ((i & 2) != 0) {
                phoneWindowManager.mPendingCapsLockToggle = true;
                phoneWindowManager.mPendingMetaAction = false;
                return;
            } else {
                phoneWindowManager.mPendingCapsLockToggle = false;
                phoneWindowManager.mPendingMetaAction = true;
                return;
            }
        }
        if (phoneWindowManager.mPendingCapsLockToggle) {
            phoneWindowManager.mPendingCapsLockToggle = false;
        } else if (phoneWindowManager.mPendingMetaAction) {
            phoneWindowManager.launchAllAppsViaA11y();
            phoneWindowManager.mPendingMetaAction = false;
        }
    }
}
