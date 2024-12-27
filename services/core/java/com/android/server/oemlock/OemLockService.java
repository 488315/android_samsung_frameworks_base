package com.android.server.oemlock;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.oemlock.IOemLockService;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.pdb.PersistentDataBlockManagerInternal;
import com.android.server.pdb.PersistentDataBlockService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserRestrictionsUtils;

public final class OemLockService extends SystemService {
    public final Context mContext;
    public final OemLock mOemLock;
    public final AnonymousClass2 mService;
    public final AnonymousClass1 mUserRestrictionsListener;

    /* renamed from: -$$Nest$menforceUserIsAdmin, reason: not valid java name */
    public static void m736$$Nest$menforceUserIsAdmin(OemLockService oemLockService) {
        oemLockService.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (UserManager.get(oemLockService.mContext).isUserAdmin(callingUserId)) {
            } else {
                throw new SecurityException("Must be an admin user");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: -$$Nest$msetPersistentDataBlockOemUnlockAllowedBit, reason: not valid java name */
    public static void m737$$Nest$msetPersistentDataBlockOemUnlockAllowedBit(
            OemLockService oemLockService, boolean z) {
        oemLockService.getClass();
        PersistentDataBlockManagerInternal persistentDataBlockManagerInternal =
                (PersistentDataBlockManagerInternal)
                        LocalServices.getService(PersistentDataBlockManagerInternal.class);
        if (persistentDataBlockManagerInternal == null
                || (oemLockService.mOemLock instanceof PersistentDataBlockLock)) {
            return;
        }
        Slog.i("OemLock", "Update OEM Unlock bit in pst partition to " + z);
        PersistentDataBlockService.InternalService internalService =
                (PersistentDataBlockService.InternalService) persistentDataBlockManagerInternal;
        synchronized (PersistentDataBlockService.this.mLock) {
            PersistentDataBlockService.this.doSetOemUnlockEnabledLocked(z);
            PersistentDataBlockService.this.computeAndWriteDigestLocked();
        }
    }

    public OemLockService(Context context) {
        super(context);
        PersistentDataBlockLock persistentDataBlockLock;
        if (VendorLockAidl.getOemLockHalService() != null) {
            Slog.i("OemLock", "Using vendor lock via the HAL(aidl)");
            VendorLockAidl vendorLockAidl = new VendorLockAidl();
            vendorLockAidl.mOemLock = VendorLockAidl.getOemLockHalService();
            persistentDataBlockLock = vendorLockAidl;
        } else if (VendorLockHidl.getOemLockHalService() != null) {
            Slog.i("OemLock", "Using vendor lock via the HAL(hidl)");
            VendorLockHidl vendorLockHidl = new VendorLockHidl();
            vendorLockHidl.mOemLock = VendorLockHidl.getOemLockHalService();
            persistentDataBlockLock = vendorLockHidl;
        } else {
            Slog.i("OemLock", "Using persistent data block based lock");
            PersistentDataBlockLock persistentDataBlockLock2 = new PersistentDataBlockLock();
            persistentDataBlockLock2.mContext = context;
            persistentDataBlockLock = persistentDataBlockLock2;
        }
        UserManagerInternal.UserRestrictionsListener userRestrictionsListener =
                new UserManagerInternal
                        .UserRestrictionsListener() { // from class:
                                                      // com.android.server.oemlock.OemLockService.1
                    @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
                    public final void onUserRestrictionsChanged(
                            int i, Bundle bundle, Bundle bundle2) {
                        if (!UserRestrictionsUtils.restrictionsChanged(
                                        bundle2, bundle, "no_factory_reset")
                                || (!bundle.getBoolean("no_factory_reset"))) {
                            return;
                        }
                        OemLockService oemLockService = OemLockService.this;
                        oemLockService.mOemLock.setOemUnlockAllowedByDevice(false);
                        OemLockService.m737$$Nest$msetPersistentDataBlockOemUnlockAllowedBit(
                                oemLockService, false);
                    }
                };
        this.mService =
                new IOemLockService
                        .Stub() { // from class: com.android.server.oemlock.OemLockService.2
                    public final String getLockName() {
                        getLockName_enforcePermission();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            return OemLockService.this.mOemLock.getLockName();
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final boolean isDeviceOemUnlocked() {
                        isDeviceOemUnlocked_enforcePermission();
                        String str = SystemProperties.get("ro.boot.flash.locked");
                        str.getClass();
                        return str.equals("0");
                    }

                    public final boolean isOemUnlockAllowed() {
                        isOemUnlockAllowed_enforcePermission();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            boolean z =
                                    OemLockService.this.mOemLock.isOemUnlockAllowedByCarrier()
                                            && OemLockService.this.mOemLock
                                                    .isOemUnlockAllowedByDevice();
                            OemLockService.m737$$Nest$msetPersistentDataBlockOemUnlockAllowedBit(
                                    OemLockService.this, z);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return z;
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }

                    public final boolean isOemUnlockAllowedByCarrier() {
                        isOemUnlockAllowedByCarrier_enforcePermission();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            return OemLockService.this.mOemLock.isOemUnlockAllowedByCarrier();
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final boolean isOemUnlockAllowedByUser() {
                        isOemUnlockAllowedByUser_enforcePermission();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            return OemLockService.this.mOemLock.isOemUnlockAllowedByDevice();
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
                        setOemUnlockAllowedByCarrier_enforcePermission();
                        OemLockService.m736$$Nest$menforceUserIsAdmin(OemLockService.this);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            OemLockService.this.mOemLock.setOemUnlockAllowedByCarrier(z, bArr);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final void setOemUnlockAllowedByUser(boolean z) {
                        setOemUnlockAllowedByUser_enforcePermission();
                        if (ActivityManager.isUserAMonkey()) {
                            return;
                        }
                        OemLockService.m736$$Nest$menforceUserIsAdmin(OemLockService.this);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (!(!UserManager.get(OemLockService.this.mContext)
                                    .hasUserRestriction("no_factory_reset", UserHandle.SYSTEM))) {
                                throw new SecurityException("Admin does not allow OEM unlock");
                            }
                            if (!OemLockService.this.mOemLock.isOemUnlockAllowedByCarrier()) {
                                throw new SecurityException("Carrier does not allow OEM unlock");
                            }
                            OemLockService.this.mOemLock.setOemUnlockAllowedByDevice(z);
                            OemLockService.m737$$Nest$msetPersistentDataBlockOemUnlockAllowedBit(
                                    OemLockService.this, z);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                };
        this.mContext = context;
        this.mOemLock = persistentDataBlockLock;
        ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                .addUserRestrictionsListener(userRestrictionsListener);
    }

    public static boolean isHalPresent() {
        return (VendorLockHidl.getOemLockHalService() == null
                        && VendorLockAidl.getOemLockHalService() == null)
                ? false
                : true;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("oem_lock", this.mService);
    }
}
