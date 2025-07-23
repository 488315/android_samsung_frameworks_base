package com.samsung.android.knox.custom;

import android.content.ComponentName;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PrivateCustomDeviceManager {
    public static final String TAG = "PrivateCustomDeviceManager";
    public static PrivateCustomDeviceManager gPrivateCustomDeviceManager;
    public static ContextInfo mContextInfo;
    public IKnoxCustomManager mService;
    public static final Object mSync = new Object();
    public static final String[] GEAR_PACKAGE_FILTER = {"com.samsung.android.hostmanager", "com.samsung.android.gear1plugin", "com.samsung.android.wms"};

    public static PrivateCustomDeviceManager getInstance() {
        PrivateCustomDeviceManager privateCustomDeviceManager;
        synchronized (mSync) {
            try {
                if (gPrivateCustomDeviceManager == null) {
                    gPrivateCustomDeviceManager = new PrivateCustomDeviceManager();
                }
                if (mContextInfo == null) {
                    mContextInfo = new ContextInfo();
                }
                privateCustomDeviceManager = gPrivateCustomDeviceManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return privateCustomDeviceManager;
    }

    public boolean getGearNotificationStateInternal(int i, String str) {
        if (getService() == null || (i & 2) == 0) {
            return true;
        }
        try {
            if (this.mService.getGearNotificationState()) {
                return true;
            }
            for (String str2 : GEAR_PACKAGE_FILTER) {
                if (str2.equalsIgnoreCase(str)) {
                    return false;
                }
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public String getLoadingLogoPath() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getLoadingLogoPath();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return null;
        }
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public int getUsbConnectionTypeInternal() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getUsbConnectionTypeInternal();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public int isAutoOpenLastAppAllowed() {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.isDexAutoOpenLastAppAllowed();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public boolean registerSystemUICallback(IKnoxCustomManagerSystemUiCallback iKnoxCustomManagerSystemUiCallback) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.registerSystemUiCallback(iKnoxCustomManagerSystemUiCallback);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public boolean stayInForeground(ComponentName componentName) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.stayInDexForegroundMode(componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }
}
