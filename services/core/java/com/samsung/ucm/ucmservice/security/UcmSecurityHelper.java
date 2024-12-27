package com.samsung.ucm.ucmservice.security;

import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Binder;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper;
import com.android.server.enterprise.RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UcmSecurityHelper {
    public Context mContext;
    public IPackageManager mPm;

    public static boolean compareSignatures(Signature[] signatureArr, Signature[] signatureArr2) {
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

    public static int getCallingUid(UniversalCredentialUtil.UcmUri ucmUri) {
        int callingUid = Binder.getCallingUid();
        int uid = ucmUri.getUid();
        return (uid == -1 || callingUid == uid || callingUid != 1000) ? callingUid : uid;
    }

    public final void checkCallerPermissionFor(String str) {
        Log.i(
                "UcmService.SecurityHelper",
                "checkCallerPermissionFor is called for method [" + str + "]");
        if (Binder.getCallingUid() == 0) {
            return;
        }
        if (ServiceKeeper.isAuthorized(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        this.mContext,
                        "CredentialManagerService",
                        str)
                == 0) {
            return;
        }
        SecurityException securityException =
                new SecurityException(
                        "Security Exception Occurred while pid["
                                + Binder.getCallingPid()
                                + "] with uid["
                                + Binder.getCallingUid()
                                + "] trying to access methodName ["
                                + str
                                + "] in [CredentialManagerService] service");
        StringBuilder sb = new StringBuilder("The exception occurs ");
        sb.append(securityException.getMessage());
        Log.e("UcmService.SecurityHelper", sb.toString());
        throw securityException;
    }

    public final boolean isCallerSystemUI() {
        try {
        } catch (Exception e) {
            RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("The exception occurs "), "UcmService.SecurityHelper");
        }
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
            Log.e("UcmService.SecurityHelper", "The caller is not qualified.");
            return false;
        }
        String nameForUid = this.mPm.getNameForUid(Binder.getCallingUid());
        int lastIndexOf = nameForUid.lastIndexOf(":");
        if (lastIndexOf != -1) {
            nameForUid = nameForUid.substring(0, lastIndexOf);
        }
        Log.i("UcmService.SecurityHelper", "isCallerSystemUI caller " + nameForUid);
        return nameForUid != null && nameForUid.equals("android.uid.systemui");
    }

    public final boolean isSystemApp(String str) {
        Signature[] signatureArr;
        try {
            PackageInfo packageInfo = this.mPm.getPackageInfo(str, 64L, 0);
            PackageInfo packageInfo2 =
                    this.mContext.getPackageManager().getPackageInfo("android", 64);
            if (packageInfo == null || (signatureArr = packageInfo.signatures) == null) {
                return false;
            }
            return compareSignatures(packageInfo2.signatures, signatureArr);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("UcmService.SecurityHelper", "isSystemApp exception e -" + e);
            return false;
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e2, "isSystemApp exception e -", "UcmService.SecurityHelper");
            return false;
        }
    }

    public final boolean isSystemCaller() {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        if ("android.uid.system:1000".equals(nameForUid)) {
            nameForUid = null;
        }
        return nameForUid == null;
    }
}
