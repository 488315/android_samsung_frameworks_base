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
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MamDataManager {
    public static final String KNOX_CUSTOM_MANAGER_SERVICE = "knoxcustom";
    public static final String TAG = "MamDataManager";
    public static ContextInfo sContextInfo;
    public static MamDataManager sMamDeviceManager;
    public ContentResolver mContentResolver = null;
    public IKnoxCustomManager mService;

    private MamDataManager() {
    }

    public static synchronized MamDataManager getInstance() {
        MamDataManager mamDataManager;
        synchronized (MamDataManager.class) {
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
            mamDataManager = sMamDeviceManager;
        }
        return mamDataManager;
    }

    public final ContentResolver getContentResolver() {
        if (this.mContentResolver == null) {
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, null);
                Method method = cls.getMethod("getSystemContext", new Class[0]);
                if (method != null) {
                    this.mContentResolver = ((Context) method.invoke(invoke, new Object[0])).getContentResolver();
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

    public final boolean isKnoxPrivacyPolicyAcceptedInitially() {
        return false;
    }

    public final boolean isKnoxPrivacyPolicyAcceptedOrWithdrawnByUserSettings() {
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

    public final void setKnoxPrivacyPolicyByUserSettings(boolean z) {
        if (getService() != null) {
            try {
                this.mService.setKnoxPrivacyPolicyByUserSettings(z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            }
        }
    }
}
