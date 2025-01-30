package com.samsung.ucm.ucmservice.security;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.ServiceKeeper;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes2.dex */
public class UcmSecurityHelper {
    public static final List certEnrollSystemApps = Arrays.asList("com.samsung.android.certenrollservice.scep", "com.samsung.android.certenrollservice.cmp", "com.samsung.android.certenrollservice.cmc");
    public Context mContext;
    public IPackageManager mPm = AppGlobals.getPackageManager();

    public void checkDeviceIntegrity() {
    }

    public UcmSecurityHelper(Context context) {
        this.mContext = context;
    }

    public int checkCallerPermissionFor(String str) {
        Log.i("UcmService.SecurityHelper", "checkCallerPermissionFor is called for method [" + str + "]");
        checkDeviceIntegrity();
        if (Binder.getCallingUid() == 0 || ServiceKeeper.isAuthorized(this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), "CredentialManagerService", str) == 0) {
            return 0;
        }
        SecurityException securityException = new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [CredentialManagerService] service");
        StringBuilder sb = new StringBuilder();
        sb.append("The exception occurs ");
        sb.append(securityException.getMessage());
        Log.e("UcmService.SecurityHelper", sb.toString());
        throw securityException;
    }

    public boolean isCallerSystemUI() {
        try {
        } catch (Exception e) {
            Log.i("UcmService.SecurityHelper", "The exception occurs " + e.getMessage());
        }
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
            Log.e("UcmService.SecurityHelper", "The caller is not qualified.");
            return false;
        }
        String nameForUid = this.mPm.getNameForUid(Binder.getCallingUid());
        int lastIndexOf = nameForUid.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (lastIndexOf != -1) {
            nameForUid = nameForUid.substring(0, lastIndexOf);
        }
        Log.i("UcmService.SecurityHelper", "isCallerSystemUI caller " + nameForUid);
        return nameForUid != null && nameForUid.equals("android.uid.systemui");
    }

    public void checkSystemCaller() {
        if (!isSystemCaller()) {
            throw new IllegalStateException("");
        }
    }

    public boolean isSystemCaller() {
        return checkCaller("android.uid.system:1000") == null;
    }

    public final String checkCaller(String str) {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        if (str.equals(nameForUid)) {
            return null;
        }
        return nameForUid;
    }

    public boolean isCallFromSystem(int i) {
        Log.i("UcmService.SecurityHelper", "isCallFromSystem adminUid [" + i + "]");
        try {
            int userId = UserHandle.getUserId(i);
            String[] packagesForUid = this.mPm.getPackagesForUid(i);
            if (packagesForUid != null) {
                for (String str : packagesForUid) {
                    Log.i("UcmService.SecurityHelper", "isCallFromSystem pkgName [" + str + "]");
                    if (certEnrollSystemApps.contains(str) && isSystemApp(str, userId)) {
                        Log.i("UcmService.SecurityHelper", "isCallFromSystem match found....");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("UcmService.SecurityHelper", "The exception occurs " + e.getMessage());
        }
        return false;
    }

    public boolean isSystemApp(String str, int i) {
        Signature[] signatureArr;
        try {
            PackageInfo packageInfo = this.mPm.getPackageInfo(str, 64L, i);
            PackageInfo packageInfo2 = this.mContext.getPackageManager().getPackageInfo("android", 64);
            if (packageInfo == null || (signatureArr = packageInfo.signatures) == null) {
                return false;
            }
            return compareSignatures(packageInfo2.signatures, signatureArr);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("UcmService.SecurityHelper", "isSystemApp exception e -" + e);
            return false;
        } catch (Exception e2) {
            Log.e("UcmService.SecurityHelper", "isSystemApp exception e -" + e2);
            return false;
        }
    }

    public final boolean compareSignatures(Signature[] signatureArr, Signature[] signatureArr2) {
        if (signatureArr == null) {
            Log.d("UcmService.SecurityHelper", "Signature s1 is null");
            return false;
        }
        if (signatureArr2 == null) {
            Log.d("UcmService.SecurityHelper", "Signature s2 is null");
            return false;
        }
        HashSet hashSet = new HashSet();
        for (Signature signature : signatureArr) {
            hashSet.add(signature);
        }
        HashSet hashSet2 = new HashSet();
        for (Signature signature2 : signatureArr2) {
            hashSet2.add(signature2);
        }
        if (hashSet.equals(hashSet2)) {
            Log.d("UcmService.SecurityHelper", "  Signature match");
            return true;
        }
        Log.d("UcmService.SecurityHelper", "  Signature doesn't match");
        return false;
    }
}
