package com.android.net.module.util;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.NetworkStack;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LocationPermissionChecker {
    public static final int ERROR_LOCATION_MODE_OFF = 1;
    public static final int ERROR_LOCATION_PERMISSION_MISSING = 2;
    public static final int SUCCEEDED = 0;
    private static final String TAG = "LocationPermissionChecker";
    private final AppOpsManager mAppOpsManager;
    private final Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LocationPermissionCheckStatus {}

    public LocationPermissionChecker(Context context) {
        this.mContext = context;
        this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
    }

    public boolean checkLocationPermission(
            String pkgName, String featureId, int uid, String message) {
        return checkLocationPermissionInternal(pkgName, featureId, uid, message) == 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x002d, code lost:

       return r0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int checkLocationPermissionWithDetailInfo(
            java.lang.String r5, java.lang.String r6, int r7, java.lang.String r8) {
        /*
            r4 = this;
            int r0 = r4.checkLocationPermissionInternal(r5, r6, r7, r8)
            java.lang.String r1 = "LocationPermissionChecker"
            switch(r0) {
                case 1: goto L27;
                case 2: goto La;
                default: goto L9;
            }
        L9:
            goto L2d
        La:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "UID "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r3 = " has no location permission"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2)
            goto L2d
        L27:
            java.lang.String r2 = "Location mode is disabled for the device"
            android.util.Log.e(r1, r2)
        L2d:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.net.module.util.LocationPermissionChecker.checkLocationPermissionWithDetailInfo(java.lang.String,"
                    + " java.lang.String, int, java.lang.String):int");
    }

    public void enforceLocationPermission(String pkgName, String featureId, int uid, String message)
            throws SecurityException {
        int result = checkLocationPermissionInternal(pkgName, featureId, uid, message);
        switch (result) {
            case 1:
                throw new SecurityException("Location mode is disabled for the device");
            case 2:
                throw new SecurityException("UID " + uid + " has no location permission");
            default:
                return;
        }
    }

    private int checkLocationPermissionInternal(
            String pkgName, String featureId, int uid, String message) {
        checkPackage(uid, pkgName);
        if (checkNetworkSettingsPermission(uid)
                || checkNetworkSetupWizardPermission(uid)
                || checkNetworkStackPermission(uid)
                || checkMainlineNetworkStackPermission(uid)) {
            return 0;
        }
        if (isLocationModeEnabled()) {
            return !checkCallersLocationPermission(pkgName, featureId, uid, true, message) ? 2 : 0;
        }
        return 1;
    }

    public boolean checkCallersLocationPermission(
            String pkgName,
            String featureId,
            int uid,
            boolean coarseForTargetSdkLessThanQ,
            String message) {
        String permissionType;
        boolean isTargetSdkLessThanQ = isTargetSdkLessThan(pkgName, 29, uid);
        if (coarseForTargetSdkLessThanQ && isTargetSdkLessThanQ) {
            permissionType = Manifest.permission.ACCESS_COARSE_LOCATION;
        } else {
            permissionType = Manifest.permission.ACCESS_FINE_LOCATION;
        }
        if (getUidPermission(permissionType, uid) == -1) {
            return false;
        }
        boolean isFineLocationAllowed =
                noteAppOpAllowed(
                        AppOpsManager.OPSTR_FINE_LOCATION, pkgName, featureId, uid, message);
        if (isFineLocationAllowed) {
            return true;
        }
        if (coarseForTargetSdkLessThanQ && isTargetSdkLessThanQ) {
            return noteAppOpAllowed(
                    AppOpsManager.OPSTR_COARSE_LOCATION, pkgName, featureId, uid, message);
        }
        return false;
    }

    public boolean isLocationModeEnabled() {
        LocationManager LocationManager =
                (LocationManager) this.mContext.getSystemService(LocationManager.class);
        try {
            return LocationManager.isLocationEnabledForUser(UserHandle.of(getCurrentUser()));
        } catch (Exception e) {
            Log.e(TAG, "Failure to get location mode via API, falling back to settings", e);
            return false;
        }
    }

    private boolean isTargetSdkLessThan(String packageName, int versionCode, int callingUid) {
        long ident = Binder.clearCallingIdentity();
        try {
            if (this.mContext
                            .getPackageManager()
                            .getApplicationInfoAsUser(
                                    packageName, 0, UserHandle.getUserHandleForUid(callingUid))
                            .targetSdkVersion
                    < versionCode) {
                Binder.restoreCallingIdentity(ident);
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(ident);
            throw th;
        }
        Binder.restoreCallingIdentity(ident);
        return false;
    }

    private boolean noteAppOpAllowed(
            String op, String pkgName, String featureId, int uid, String message) {
        return this.mAppOpsManager.noteOp(op, uid, pkgName, featureId, message) == 0;
    }

    private void checkPackage(int uid, String pkgName) throws SecurityException {
        if (pkgName == null) {
            throw new SecurityException("Checking UID " + uid + " but Package Name is Null");
        }
        this.mAppOpsManager.checkPackage(uid, pkgName);
    }

    protected int getCurrentUser() {
        return ActivityManager.getCurrentUser();
    }

    private int getUidPermission(String permissionType, int uid) {
        return this.mContext.checkPermission(permissionType, -1, uid);
    }

    public boolean checkNetworkSettingsPermission(int uid) {
        return getUidPermission(Manifest.permission.NETWORK_SETTINGS, uid) == 0;
    }

    public boolean checkNetworkSetupWizardPermission(int uid) {
        return getUidPermission(Manifest.permission.NETWORK_SETUP_WIZARD, uid) == 0;
    }

    public boolean checkNetworkStackPermission(int uid) {
        return getUidPermission(Manifest.permission.NETWORK_STACK, uid) == 0;
    }

    public boolean checkMainlineNetworkStackPermission(int uid) {
        return getUidPermission(NetworkStack.PERMISSION_MAINLINE_NETWORK_STACK, uid) == 0;
    }
}
