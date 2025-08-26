package com.samsung.android.knox.mam;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class MamDataManager {
    public static final String KNOX_CUSTOM_MANAGER_SERVICE = "knoxcustom";
    public static final String TAG = "MamDataManager";
    public static ContextInfo sContextInfo;
    public static MamDataManager sMamDeviceManager;
    public ContentResolver mContentResolver = null;
    public IKnoxCustomManager mService;

    private MamDataManager() {
    }

    public static synchronized MamDataManager getInstance() {
        try {
            if (sMamDeviceManager == null) {
                sMamDeviceManager = new MamDataManager();
            }
            if (sContextInfo == null) {
                if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
                    sContextInfo = new ContextInfo();
                } else {
                    sContextInfo = new ContextInfo(Process.myUid(), true);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return sMamDeviceManager;
    }

    public final ContentResolver getContentResolver() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.mContentResolver == null) {
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Class[] clsArr = new Class[0];
                Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(null, null);
                Class[] clsArr2 = new Class[0];
                Method method = cls.getMethod("getSystemContext", null);
                if (method != null) {
                    this.mContentResolver = ((Context) method.invoke(objInvoke, null)).getContentResolver();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.mContentResolver;
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public boolean isKnoxPrivacyPolicyAcceptedInitially() {
        return false;
    }

    public boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public void setKnoxPrivacyPolicyByUserSettings(boolean z) {
        if (getService() != null) {
            try {
                this.mService.setKnoxPrivacyPolicyByUserSettings(z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            }
        }
    }
}
