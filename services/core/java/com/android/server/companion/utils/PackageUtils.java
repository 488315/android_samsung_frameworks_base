package com.android.server.companion.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;

import java.util.HashSet;

public abstract class PackageUtils {
    public static final Intent COMPANION_SERVICE_INTENT =
            new Intent("android.companion.CompanionDeviceService");

    public static void enforceUsesCompanionDeviceFeature(Context context, String str, int i) {
        if (Binder.getCallingUid() == 1000) {
            return;
        }
        PackageInfo packageInfo =
                (PackageInfo)
                        Binder.withCleanCallingIdentity(
                                new PackageUtils$$ExternalSyntheticLambda1(
                                        context.getPackageManager(),
                                        str,
                                        PackageManager.PackageInfoFlags.of(20480L),
                                        i));
        if (packageInfo == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " doesn't exist."));
        }
        FeatureInfo[] featureInfoArr = packageInfo.reqFeatures;
        if (featureInfoArr != null) {
            for (FeatureInfo featureInfo : featureInfoArr) {
                if ("android.software.companion_device_setup".equals(featureInfo.name)) {
                    return;
                }
            }
        }
        throw new IllegalStateException(
                "Must declare uses-feature android.software.companion_device_setup in manifest to"
                    + " use this API");
    }

    public static boolean isPackageAllowlisted(
            Context context,
            PackageManagerInternal packageManagerInternal,
            String str,
            int i,
            int i2) {
        String[] stringArray = context.getResources().getStringArray(i);
        boolean z = false;
        if (!ArrayUtils.contains(stringArray, str)) {
            Slog.d("CDM_PackageUtils", str + " is not allowlisted.");
            return false;
        }
        String[] stringArray2 = context.getResources().getStringArray(i2);
        HashSet hashSet = new HashSet();
        for (int i3 = 0; i3 < stringArray.length; i3++) {
            if (stringArray[i3].equals(str)) {
                hashSet.add(stringArray2[i3].replaceAll(":", ""));
            }
        }
        String[] computeSignaturesSha256Digests =
                android.util.PackageUtils.computeSignaturesSha256Digests(
                        packageManagerInternal.getPackage(str).getSigningDetails().getSignatures());
        int length = computeSignaturesSha256Digests.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                break;
            }
            if (hashSet.contains(computeSignaturesSha256Digests[i4])) {
                z = true;
                break;
            }
            i4++;
        }
        if (!z) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Certificate mismatch for allowlisted package ", str, "CDM_PackageUtils");
        }
        return z;
    }
}
