package com.android.settingslib.applications;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import com.android.settingslib.Utils;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new Intent().setAction("android.intent.action.VIEW").addCategory("android.intent.category.BROWSABLE").setData(Uri.parse("http:"));
    }

    public static boolean isAutoDisabled(ApplicationInfo applicationInfo) {
        SemAppRestrictionManager.RestrictionInfo restrictionInfo = new SemAppRestrictionManager().getRestrictionInfo(0, applicationInfo.packageName, applicationInfo.uid);
        return (restrictionInfo == null || restrictionInfo.getState() != 1 || restrictionInfo.isChangedByUser()) ? false : true;
    }

    public static boolean isInstant(ApplicationInfo applicationInfo) {
        String[] split;
        if (applicationInfo.isInstantApp()) {
            return true;
        }
        String str = SystemProperties.get("settingsdebug.instant.packages");
        if (str != null && !str.isEmpty() && applicationInfo.packageName != null && (split = str.split(",")) != null) {
            for (String str2 : split) {
                if (applicationInfo.packageName.contains(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isLanguagePackApk(Context context, ApplicationInfo applicationInfo) {
        Signature signature;
        PackageInfo packageInfo;
        Signature[] signatureArr;
        boolean z;
        boolean z2;
        PackageInfo packageInfo2;
        Signature[] signatureArr2;
        PackageInfo packageInfo3;
        Signature signature2;
        Signature[] signatureArr3;
        Bundle bundle = applicationInfo.metaData;
        if (!(bundle != null && bundle.getBoolean("settings_langpack_invisible", false))) {
            return false;
        }
        String str = applicationInfo.packageName;
        PackageManager packageManager = context.getPackageManager();
        Signature signature3 = null;
        if (Utils.sSystemSignature == null) {
            try {
                packageInfo = packageManager.getPackageInfo("android", 64);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (packageInfo != null && (signatureArr = packageInfo.signatures) != null && signatureArr.length > 0) {
                signature = signatureArr[0];
                Utils.sSystemSignature = new Signature[]{signature};
            }
            signature = null;
            Utils.sSystemSignature = new Signature[]{signature};
        }
        try {
            packageInfo3 = packageManager.getPackageInfo(str, 64);
            signature2 = Utils.sSystemSignature[0];
        } catch (PackageManager.NameNotFoundException unused2) {
        }
        if (signature2 != null) {
            if (packageInfo3 != null && (signatureArr3 = packageInfo3.signatures) != null && signatureArr3.length > 0) {
                signature3 = signatureArr3[0];
            }
            if (signature2.equals(signature3)) {
                z = true;
                if (!z) {
                    try {
                        packageInfo2 = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 64);
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("AppUtils", "isTTSLanguagePack: " + e);
                    } catch (NoSuchAlgorithmException e2) {
                        Log.e("AppUtils", "isTTSLanguagePack: " + e2);
                    }
                    if (packageInfo2 != null && (signatureArr2 = packageInfo2.signatures) != null && signatureArr2.length > 0) {
                        Signature signature4 = signatureArr2[0];
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                        messageDigest.update(signature4.toByteArray());
                        byte[] digest = messageDigest.digest();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < digest.length; i++) {
                            sb.append(String.format("%02X", Byte.valueOf(digest[i])));
                            if (i != digest.length - 1) {
                                sb.append(":");
                            }
                        }
                        z2 = "06:7B:9C:A8:E1:22:92:71:E9:04:C5:97:6F:EA:8C:B6:1B:6D:C9:C6:20:E3:B3:E4:C8:E4:88:0D:C5:91:E0:E5".equals(sb.toString());
                        if (!z2) {
                            return false;
                        }
                    }
                    z2 = false;
                    if (!z2) {
                    }
                }
                return true;
            }
        }
        z = false;
        if (!z) {
        }
        return true;
    }

    public static boolean isManualDisabled(ApplicationInfo applicationInfo) {
        SemAppRestrictionManager.RestrictionInfo restrictionInfo = new SemAppRestrictionManager().getRestrictionInfo(0, applicationInfo.packageName, applicationInfo.uid);
        return restrictionInfo != null && restrictionInfo.getState() == 1 && restrictionInfo.isChangedByUser();
    }
}
