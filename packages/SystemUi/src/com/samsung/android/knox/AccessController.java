package com.samsung.android.knox;

import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AccessController {
    public static final String TAG = "EnterpriseDeviceManager";

    public static ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) {
        if (getService() != null) {
            try {
                return getService().enforceActiveAdminPermissionByContext(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public static ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) {
        if (getService() != null) {
            try {
                return getService().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public static boolean enforceWpcod() {
        try {
            if (getService() == null) {
                return false;
            }
            getService().enforceWpcod(Process.myUid(), true);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static IEnterpriseDeviceManager getService() {
        return IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
    }

    public static void throwIfParentInstance(ContextInfo contextInfo, String str) {
        if (contextInfo.mParent) {
            throw new SecurityException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, " cannot be called on the parent instance"));
        }
    }
}
