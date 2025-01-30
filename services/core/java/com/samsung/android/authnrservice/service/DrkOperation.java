package com.samsung.android.authnrservice.service;

import android.content.Context;
import android.os.Binder;
import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;
import com.samsung.android.service.DeviceRootKeyService.Tlv;

/* loaded from: classes2.dex */
public final class DrkOperation {
    public static DrkOperation sDrkOperation;
    public DeviceRootKeyServiceManager mDrkServiceManager;

    public static synchronized DrkOperation getInstance() {
        DrkOperation drkOperation;
        synchronized (DrkOperation.class) {
            if (sDrkOperation == null) {
                sDrkOperation = new DrkOperation();
            }
            drkOperation = sDrkOperation;
        }
        return drkOperation;
    }

    public synchronized boolean initialize(Context context) {
        AuthnrLog.m123v("DO", "initialize");
        if (this.mDrkServiceManager == null) {
            this.mDrkServiceManager = new DeviceRootKeyServiceManager(context);
        }
        if (!this.mDrkServiceManager.isAliveDeviceRootKeyService()) {
            AuthnrLog.m121e("DO", "isAliveDeviceRootKeyService failed");
            return false;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean isExistDeviceRootKey = this.mDrkServiceManager.isExistDeviceRootKey(1);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (isExistDeviceRootKey) {
                return true;
            }
            AuthnrLog.m121e("DO", "isExistDeviceRootKey failed");
            return false;
        } catch (Exception e) {
            AuthnrLog.m121e("DO", "drk init failed : " + e.getMessage());
            return false;
        }
    }

    public synchronized boolean terminate() {
        AuthnrLog.m123v("DO", "terminate");
        if (this.mDrkServiceManager == null) {
            AuthnrLog.m121e("DO", "not initialized");
            return false;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            int releaseServiceKeySession = this.mDrkServiceManager.releaseServiceKeySession();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (releaseServiceKeySession != 0) {
                AuthnrLog.m121e("DO", "releaseServiceKeySession failed");
                return false;
            }
            this.mDrkServiceManager = null;
            return true;
        } catch (Exception e) {
            AuthnrLog.m121e("DO", "drk terminate failed : " + e.getMessage());
            return false;
        }
    }

    public synchronized byte[] getDrkKeyHandle() {
        AuthnrLog.m123v("DO", "getDrkKeyHandle");
        if (this.mDrkServiceManager == null) {
            AuthnrLog.m121e("DO", "not initialized");
            return new byte[0];
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            byte[] createServiceKeySession = this.mDrkServiceManager.createServiceKeySession("AUTHNR", 1, (Tlv) null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (createServiceKeySession != null && createServiceKeySession.length != 0) {
                return createServiceKeySession;
            }
            AuthnrLog.m121e("DO", "createServiceKeySession failed");
            return new byte[0];
        } catch (Exception e) {
            AuthnrLog.m121e("DO", "get drk fail. " + e.getMessage());
            return new byte[0];
        }
    }
}
