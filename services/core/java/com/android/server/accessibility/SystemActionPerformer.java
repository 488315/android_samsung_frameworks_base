package com.android.server.accessibility;

import android.R;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.app.StatusBarManager;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ScreenshotHelper;
import com.android.server.LocalServices;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.WindowManagerInternal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemActionPerformer {
    public final Context mContext;
    public final DisplayUpdateCallBack mDisplayUpdateCallBack;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyBackAction;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyHomeAction;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyLockScreenAction;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyNotificationsAction;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyPowerDialogAction;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyQuickSettingsAction;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyRecentsAction;
    public final AccessibilityNodeInfo.AccessibilityAction mLegacyTakeScreenshotAction;
    public final SystemActionsChangedListener mListener;
    public final Map mRegisteredSystemActions;
    public final Supplier mScreenshotHelperSupplier;
    public final Object mSystemActionLock;
    public final WindowManagerInternal mWindowManagerService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DisplayUpdateCallBack {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface SystemActionsChangedListener {
    }

    public SystemActionPerformer(Context context, WindowManagerInternal windowManagerInternal, Supplier supplier) {
        this(context, windowManagerInternal, supplier, null, null);
    }

    public SystemActionPerformer(Context context, WindowManagerInternal windowManagerInternal, Supplier supplier, SystemActionsChangedListener systemActionsChangedListener, DisplayUpdateCallBack displayUpdateCallBack) {
        this.mSystemActionLock = new Object();
        this.mRegisteredSystemActions = new ArrayMap();
        this.mContext = context;
        this.mWindowManagerService = windowManagerInternal;
        this.mListener = systemActionsChangedListener;
        this.mDisplayUpdateCallBack = displayUpdateCallBack;
        this.mScreenshotHelperSupplier = supplier;
        this.mLegacyHomeAction = new AccessibilityNodeInfo.AccessibilityAction(2, context.getResources().getString(R.string.autofill_save_type_password));
        this.mLegacyBackAction = new AccessibilityNodeInfo.AccessibilityAction(1, context.getResources().getString(R.string.autofill_save_title));
        this.mLegacyRecentsAction = new AccessibilityNodeInfo.AccessibilityAction(3, context.getResources().getString(R.string.autofill_update_title_with_type));
        this.mLegacyNotificationsAction = new AccessibilityNodeInfo.AccessibilityAction(4, context.getResources().getString(R.string.autofill_save_type_username));
        this.mLegacyQuickSettingsAction = new AccessibilityNodeInfo.AccessibilityAction(5, context.getResources().getString(R.string.autofill_update_title_with_3types));
        this.mLegacyPowerDialogAction = new AccessibilityNodeInfo.AccessibilityAction(6, context.getResources().getString(R.string.autofill_update_title_with_2types));
        this.mLegacyLockScreenAction = new AccessibilityNodeInfo.AccessibilityAction(8, context.getResources().getString(R.string.autofill_save_type_payment_card));
        this.mLegacyTakeScreenshotAction = new AccessibilityNodeInfo.AccessibilityAction(9, context.getResources().getString(R.string.autofill_update_yes));
    }

    public final void addLegacySystemActions(List list) {
        if (!((ArrayMap) this.mRegisteredSystemActions).containsKey(1)) {
            ((ArrayList) list).add(this.mLegacyBackAction);
        }
        if (!((ArrayMap) this.mRegisteredSystemActions).containsKey(2)) {
            ((ArrayList) list).add(this.mLegacyHomeAction);
        }
        if (!((ArrayMap) this.mRegisteredSystemActions).containsKey(3)) {
            ((ArrayList) list).add(this.mLegacyRecentsAction);
        }
        if (!((ArrayMap) this.mRegisteredSystemActions).containsKey(4)) {
            ((ArrayList) list).add(this.mLegacyNotificationsAction);
        }
        if (!((ArrayMap) this.mRegisteredSystemActions).containsKey(5)) {
            ((ArrayList) list).add(this.mLegacyQuickSettingsAction);
        }
        if (!((ArrayMap) this.mRegisteredSystemActions).containsKey(6)) {
            ((ArrayList) list).add(this.mLegacyPowerDialogAction);
        }
        if (!((ArrayMap) this.mRegisteredSystemActions).containsKey(8)) {
            ((ArrayList) list).add(this.mLegacyLockScreenAction);
        }
        if (((ArrayMap) this.mRegisteredSystemActions).containsKey(9)) {
            return;
        }
        ((ArrayList) list).add(this.mLegacyTakeScreenshotAction);
    }

    public List getSystemActions() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSystemActionLock) {
            try {
                for (Map.Entry entry : ((ArrayMap) this.mRegisteredSystemActions).entrySet()) {
                    arrayList.add(new AccessibilityNodeInfo.AccessibilityAction(((Integer) entry.getKey()).intValue(), ((RemoteAction) entry.getValue()).getTitle()));
                }
                addLegacySystemActions(arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final boolean performSystemAction(int i) {
        int i2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
        } catch (Throwable th) {
            throw th;
        } finally {
        }
        synchronized (this.mSystemActionLock) {
            AccessibilityWindowManager accessibilityWindowManager = ((AccessibilityManagerService) this.mDisplayUpdateCallBack).mA11yWindowManager;
            if (accessibilityWindowManager.mHasProxy && (i2 = accessibilityWindowManager.mLastNonProxyTopFocusedDisplayId) != accessibilityWindowManager.mTopFocusedDisplayId) {
                accessibilityWindowManager.mWindowManagerInternal.moveDisplayToTopIfAllowed(i2);
            }
            RemoteAction remoteAction = (RemoteAction) ((ArrayMap) this.mRegisteredSystemActions).get(Integer.valueOf(i));
            boolean z = false;
            if (remoteAction != null) {
                try {
                    if (i != 14) {
                        remoteAction.getActionIntent().send();
                        return true;
                    }
                    ActivityOptions pendingIntentBackgroundActivityStartMode = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
                    pendingIntentBackgroundActivityStartMode.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
                    remoteAction.getActionIntent().send(pendingIntentBackgroundActivityStartMode.toBundle());
                    return true;
                } catch (PendingIntent.CanceledException e) {
                    Slog.e("SystemActionPerformer", "canceled PendingIntent for global action " + ((Object) remoteAction.getTitle()), e);
                    return false;
                }
            }
            switch (i) {
                case 1:
                    sendDownAndUpKeyEvents(4, FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
                    return true;
                case 2:
                    sendDownAndUpKeyEvents(3, FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
                    return true;
                case 3:
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    StatusBarManagerInternal statusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
                    if (statusBarManagerInternal == null) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } else {
                        ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal).toggleRecentApps();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        z = true;
                    }
                    return z;
                case 4:
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    ((StatusBarManager) this.mContext.getSystemService("statusbar")).expandNotificationsPanel();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                case 5:
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    ((StatusBarManager) this.mContext.getSystemService("statusbar")).expandSettingsPanel();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                case 6:
                    this.mWindowManagerService.showGlobalActions();
                    return true;
                case 7:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                default:
                    Slog.e("SystemActionPerformer", "Invalid action id: " + i);
                    return false;
                case 8:
                    ((PowerManager) this.mContext.getSystemService(PowerManager.class)).goToSleep(SystemClock.uptimeMillis(), 7, 0);
                    this.mWindowManagerService.lockNow();
                    return true;
                case 9:
                    Supplier supplier = this.mScreenshotHelperSupplier;
                    (supplier != null ? (ScreenshotHelper) supplier.get() : new ScreenshotHelper(this.mContext)).takeScreenshot(4, new Handler(Looper.getMainLooper()), (Consumer) null);
                    return true;
                case 10:
                    if (!AccessibilityUtils.interceptHeadsetHookForActiveCall(this.mContext)) {
                        sendDownAndUpKeyEvents(79, FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
                    }
                    return true;
                case 16:
                    sendDownAndUpKeyEvents(19, 769);
                    return true;
                case 17:
                    sendDownAndUpKeyEvents(20, 769);
                    return true;
                case 18:
                    sendDownAndUpKeyEvents(21, 769);
                    return true;
                case 19:
                    sendDownAndUpKeyEvents(22, 769);
                    return true;
                case 20:
                    sendDownAndUpKeyEvents(23, 769);
                    return true;
            }
        }
    }

    public void registerSystemAction(int i, RemoteAction remoteAction) {
        synchronized (this.mSystemActionLock) {
            ((ArrayMap) this.mRegisteredSystemActions).put(Integer.valueOf(i), remoteAction);
        }
        SystemActionsChangedListener systemActionsChangedListener = this.mListener;
        if (systemActionsChangedListener != null) {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) systemActionsChangedListener;
            synchronized (accessibilityManagerService.mLock) {
                accessibilityManagerService.notifySystemActionsChangedLocked(accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId));
            }
        }
    }

    public final void sendDownAndUpKeyEvents(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            sendKeyEventIdentityCleared(i, 0, i2, uptimeMillis, uptimeMillis);
            sendKeyEventIdentityCleared(i, 1, i2, uptimeMillis, SystemClock.uptimeMillis());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendKeyEventIdentityCleared(int i, int i2, int i3, long j, long j2) {
        KeyEvent obtain = KeyEvent.obtain(j, j2, i2, i, 0, 0, -1, 0, 8, i3, ((AccessibilityManagerService) this.mDisplayUpdateCallBack).mA11yWindowManager.mLastNonProxyTopFocusedDisplayId, null);
        ((InputManager) this.mContext.getSystemService(InputManager.class)).injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    public void unregisterSystemAction(int i) {
        synchronized (this.mSystemActionLock) {
            ((ArrayMap) this.mRegisteredSystemActions).remove(Integer.valueOf(i));
        }
        SystemActionsChangedListener systemActionsChangedListener = this.mListener;
        if (systemActionsChangedListener != null) {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) systemActionsChangedListener;
            synchronized (accessibilityManagerService.mLock) {
                accessibilityManagerService.notifySystemActionsChangedLocked(accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId));
            }
        }
    }
}
