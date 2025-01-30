package com.android.server.accessibility;

import android.R;
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
import com.android.server.wm.WindowManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class SystemActionPerformer {
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
    public Supplier mScreenshotHelperSupplier;
    public final Object mSystemActionLock;
    public final WindowManagerInternal mWindowManagerService;

    public interface DisplayUpdateCallBack {
        int getLastNonProxyTopFocusedDisplayId();

        void moveNonProxyTopFocusedDisplayToTopIfNeeded();
    }

    public interface SystemActionsChangedListener {
        void onSystemActionsChanged();
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
        this.mLegacyHomeAction = new AccessibilityNodeInfo.AccessibilityAction(2, context.getResources().getString(R.string.autofill_save_notnow));
        this.mLegacyBackAction = new AccessibilityNodeInfo.AccessibilityAction(1, context.getResources().getString(R.string.auto_data_switch_title));
        this.mLegacyRecentsAction = new AccessibilityNodeInfo.AccessibilityAction(3, context.getResources().getString(R.string.autofill_save_type_debit_card));
        this.mLegacyNotificationsAction = new AccessibilityNodeInfo.AccessibilityAction(4, context.getResources().getString(R.string.autofill_save_title_with_2types));
        this.mLegacyQuickSettingsAction = new AccessibilityNodeInfo.AccessibilityAction(5, context.getResources().getString(R.string.autofill_save_type_credit_card));
        this.mLegacyPowerDialogAction = new AccessibilityNodeInfo.AccessibilityAction(6, context.getResources().getString(R.string.autofill_save_type_address));
        this.mLegacyLockScreenAction = new AccessibilityNodeInfo.AccessibilityAction(8, context.getResources().getString(R.string.autofill_save_title));
        this.mLegacyTakeScreenshotAction = new AccessibilityNodeInfo.AccessibilityAction(9, context.getResources().getString(R.string.autofill_save_type_email_address));
    }

    public void registerSystemAction(int i, RemoteAction remoteAction) {
        synchronized (this.mSystemActionLock) {
            this.mRegisteredSystemActions.put(Integer.valueOf(i), remoteAction);
        }
        SystemActionsChangedListener systemActionsChangedListener = this.mListener;
        if (systemActionsChangedListener != null) {
            systemActionsChangedListener.onSystemActionsChanged();
        }
    }

    public void unregisterSystemAction(int i) {
        synchronized (this.mSystemActionLock) {
            this.mRegisteredSystemActions.remove(Integer.valueOf(i));
        }
        SystemActionsChangedListener systemActionsChangedListener = this.mListener;
        if (systemActionsChangedListener != null) {
            systemActionsChangedListener.onSystemActionsChanged();
        }
    }

    public List getSystemActions() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSystemActionLock) {
            for (Map.Entry entry : this.mRegisteredSystemActions.entrySet()) {
                arrayList.add(new AccessibilityNodeInfo.AccessibilityAction(((Integer) entry.getKey()).intValue(), ((RemoteAction) entry.getValue()).getTitle()));
            }
            addLegacySystemActions(arrayList);
        }
        return arrayList;
    }

    public final void addLegacySystemActions(List list) {
        if (!this.mRegisteredSystemActions.containsKey(1)) {
            list.add(this.mLegacyBackAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(2)) {
            list.add(this.mLegacyHomeAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(3)) {
            list.add(this.mLegacyRecentsAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(4)) {
            list.add(this.mLegacyNotificationsAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(5)) {
            list.add(this.mLegacyQuickSettingsAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(6)) {
            list.add(this.mLegacyPowerDialogAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(8)) {
            list.add(this.mLegacyLockScreenAction);
        }
        if (this.mRegisteredSystemActions.containsKey(9)) {
            return;
        }
        list.add(this.mLegacyTakeScreenshotAction);
    }

    public boolean performSystemAction(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSystemActionLock) {
                this.mDisplayUpdateCallBack.moveNonProxyTopFocusedDisplayToTopIfNeeded();
                RemoteAction remoteAction = (RemoteAction) this.mRegisteredSystemActions.get(Integer.valueOf(i));
                if (remoteAction != null) {
                    try {
                        remoteAction.getActionIntent().send();
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
                        return openRecents();
                    case 4:
                        expandNotifications();
                        return true;
                    case 5:
                        expandQuickSettings();
                        return true;
                    case 6:
                        showGlobalActions();
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
                        return lockScreen();
                    case 9:
                        return takeScreenshot();
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
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendDownAndUpKeyEvents(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            sendKeyEventIdentityCleared(i, 0, uptimeMillis, uptimeMillis, i2);
            sendKeyEventIdentityCleared(i, 1, uptimeMillis, SystemClock.uptimeMillis(), i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendKeyEventIdentityCleared(int i, int i2, long j, long j2, int i3) {
        KeyEvent obtain = KeyEvent.obtain(j, j2, i2, i, 0, 0, -1, 0, 8, i3, this.mDisplayUpdateCallBack.getLastNonProxyTopFocusedDisplayId(), null);
        ((InputManager) this.mContext.getSystemService(InputManager.class)).injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    public final void expandNotifications() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((StatusBarManager) this.mContext.getSystemService("statusbar")).expandNotificationsPanel();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void expandQuickSettings() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((StatusBarManager) this.mContext.getSystemService("statusbar")).expandSettingsPanel();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean openRecents() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            StatusBarManagerInternal statusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.toggleRecentApps();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void showGlobalActions() {
        this.mWindowManagerService.showGlobalActions();
    }

    public final boolean lockScreen() {
        ((PowerManager) this.mContext.getSystemService(PowerManager.class)).goToSleep(SystemClock.uptimeMillis(), 7, 0);
        this.mWindowManagerService.lockNow();
        return true;
    }

    public final boolean takeScreenshot() {
        Supplier supplier = this.mScreenshotHelperSupplier;
        (supplier != null ? (ScreenshotHelper) supplier.get() : new ScreenshotHelper(this.mContext)).takeScreenshot(4, new Handler(Looper.getMainLooper()), (Consumer) null);
        return true;
    }
}
