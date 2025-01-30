package com.samsung.android.desktopsystemui.sharedlib.statusbar;

import android.app.Notification;
import android.content.res.TypedArray;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.statusbar.RegisterStatusBarResult;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StatusBarManagerWrapper {
    private static final String TAG = "[DSU]StatusBarManagerWrapper";
    private static final CommandQueue commandQueue = new CommandQueue();
    private static final StatusBarManagerWrapper sInstance = new StatusBarManagerWrapper();
    private IStatusBarService mService;

    private StatusBarManagerWrapper() {
    }

    public static StatusBarManagerWrapper getInstance() {
        return sInstance;
    }

    private IStatusBarService getService() {
        if (this.mService == null) {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            this.mService = asInterface;
            if (asInterface == null) {
                Log.w(TAG, "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mService;
    }

    public void addCallbacks(CommandQueueCallbacks commandQueueCallbacks) {
        commandQueue.addCallback(commandQueueCallbacks);
    }

    public void clearCallbacks() {
        commandQueue.clearCallback();
    }

    public int getThemeAttributeId(TypedArray typedArray, int i, int i2) {
        return typedArray.getThemeAttributeId(i, i2);
    }

    public void onClearAllNotifications(int i) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.onClearAllNotifications(i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onNotificationActionClick(String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.onNotificationActionClick(str, i, action, notificationVisibility, z);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onNotificationClear(String str, int i, String str2, int i2, int i3) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.onNotificationClear(str, i, str2, i2, i3, NotificationVisibility.obtain(str2, 0, 0, true));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onNotificationClick(String str, NotificationVisibility notificationVisibility) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.onNotificationClick(str, notificationVisibility);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerDesktopbar() {
        try {
            RegisterStatusBarResult registerStatusBarAsType = getService().registerStatusBarAsType(commandQueue, 1);
            Log.i(TAG, "registerStatusBarAsType result:" + registerStatusBarAsType + " mDisabledFlags1:" + registerStatusBarAsType.mDisabledFlags1 + " mTransientBarTypes:");
        } catch (RemoteException e) {
            Log.e(TAG, "Fail to register DesktopBar:" + e);
        }
    }

    public void removeCallbacks(CommandQueueCallbacks commandQueueCallbacks) {
        commandQueue.removeCallback(commandQueueCallbacks);
    }
}
