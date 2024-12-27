package com.samsung.android.authnrservice.service;

import android.content.Context;
import android.os.Binder;

import com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager;
import com.samsung.android.service.DeviceRootKeyService.Tlv;

public final class DrkOperation {
    public static DrkOperation sDrkOperation;
    public DeviceRootKeyServiceManager mDrkServiceManager;

    public static synchronized DrkOperation getInstance() {
        DrkOperation drkOperation;
        synchronized (DrkOperation.class) {
            try {
                if (sDrkOperation == null) {
                    sDrkOperation = new DrkOperation();
                }
                drkOperation = sDrkOperation;
            } catch (Throwable th) {
                throw th;
            }
        }
        return drkOperation;
    }

    public final synchronized byte[] getDrkKeyHandle() {
        if (this.mDrkServiceManager == null) {
            AuthnrLog.e("DO", "not initialized");
            return new byte[0];
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            byte[] createServiceKeySession =
                    this.mDrkServiceManager.createServiceKeySession("AUTHNR", 1, (Tlv) null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (createServiceKeySession != null && createServiceKeySession.length != 0) {
                return createServiceKeySession;
            }
            AuthnrLog.e("DO", "createServiceKeySession failed");
            return new byte[0];
        } catch (Exception e) {
            AuthnrLog.e("DO", "get drk fail. " + e.getMessage());
            return new byte[0];
        }
    }

    public final synchronized boolean initialize(Context context) {
        try {
            if (this.mDrkServiceManager == null) {
                this.mDrkServiceManager = new DeviceRootKeyServiceManager(context);
            }
            if (!this.mDrkServiceManager.isAliveDeviceRootKeyService()) {
                AuthnrLog.e("DO", "isAliveDeviceRootKeyService failed");
                return false;
            }
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                boolean isExistDeviceRootKey = this.mDrkServiceManager.isExistDeviceRootKey(1);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (isExistDeviceRootKey) {
                    return true;
                }
                AuthnrLog.e("DO", "isExistDeviceRootKey failed");
                return false;
            } catch (Exception e) {
                AuthnrLog.e("DO", "drk init failed : " + e.getMessage());
                return false;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized boolean terminate() {
        if (this.mDrkServiceManager == null) {
            AuthnrLog.e("DO", "not initialized");
            return false;
        }
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            int releaseServiceKeySession = this.mDrkServiceManager.releaseServiceKeySession();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (releaseServiceKeySession != 0) {
                AuthnrLog.e("DO", "releaseServiceKeySession failed");
                return false;
            }
            this.mDrkServiceManager = null;
            return true;
        } catch (Exception e) {
            AuthnrLog.e("DO", "drk terminate failed : " + e.getMessage());
            return false;
        }
    }
}
