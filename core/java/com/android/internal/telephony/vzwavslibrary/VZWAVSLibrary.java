package com.android.internal.telephony.vzwavslibrary;

import android.content.Context;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ProviderInfo;
import android.content.p002pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class VZWAVSLibrary {
    private static final String AVS_AUTHORITY_MVS = "com.verizon.vzwavs.mvs.provider";
    private static final String AVS_AUTHORITY_STD = "com.verizon.vzwavs.provider";
    private static final AvsInstance[] AVS_INSTANCES;
    private static final String CERT_FP_MVS = "A1:F6:F0:8B:5D:91:99:55:DD:51:DA:94:88:38:87:14:29:B1:E9:36";
    private static final String CERT_FP_MVS_BYOD = "03:FE:29:EF:A0:6C:0B:D8:64:3A:A1:A7:C3:EC:91:A1:A6:57:00:E6";
    private static final String CERT_FP_STANDALONE = "0B:A7:6D:BD:55:0A:4C:76:68:BD:7C:85:60:C1:2D:AF:95:14:CC:02";

    /* renamed from: EN */
    private static final Locale f1306EN;
    private static final List<String> MVS_CERTS;
    private static final List<String> STANDALONE_CERTS;
    private static final String URI_TEMPLATE = "content://%s/apis";

    private enum AvsResult {
        GRANTED,
        DENIED,
        NOT_FOUND,
        NOT_PERMITTED
    }

    static {
        List<String> asList = Arrays.asList(CERT_FP_MVS, CERT_FP_MVS_BYOD);
        MVS_CERTS = asList;
        List<String> singletonList = Collections.singletonList(CERT_FP_STANDALONE);
        STANDALONE_CERTS = singletonList;
        f1306EN = Locale.ENGLISH;
        AVS_INSTANCES = new AvsInstance[]{new AvsInstance("MvsAvs", AVS_AUTHORITY_MVS, asList, new String[0]), new AvsInstance("StandaloneAvs", AVS_AUTHORITY_STD, singletonList, new String[0])};
    }

    public static boolean isPackageAuthorized(Context context, String packageName, String api) {
        if (api == null || context == null || packageName == null) {
            return false;
        }
        for (AvsInstance avs : AVS_INSTANCES) {
            AvsResult res = queryAvsInstance(context, packageName, api, avs);
            switch (C46771.f1307x4388e3f8[res.ordinal()]) {
                case 1:
                case 2:
                default:
                case 3:
                    return true;
                case 4:
                    return false;
            }
        }
        return false;
    }

    /* renamed from: com.android.internal.telephony.vzwavslibrary.VZWAVSLibrary$1 */
    static /* synthetic */ class C46771 {

        /* renamed from: $SwitchMap$com$android$internal$telephony$vzwavslibrary$VZWAVSLibrary$AvsResult */
        static final /* synthetic */ int[] f1307x4388e3f8;

        static {
            int[] iArr = new int[AvsResult.values().length];
            f1307x4388e3f8 = iArr;
            try {
                iArr[AvsResult.NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1307x4388e3f8[AvsResult.NOT_PERMITTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1307x4388e3f8[AvsResult.GRANTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1307x4388e3f8[AvsResult.DENIED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static AvsResult queryAvsInstance(Context context, String callingPackageName, String api, AvsInstance avsInstance) {
        AvsResult result;
        if (!avsInstance.isAvailable) {
            AvsResult instanceCheckResult = checkAvsInstance(context, avsInstance);
            if (instanceCheckResult != AvsResult.GRANTED) {
                return instanceCheckResult;
            }
            avsInstance.isAvailable = true;
        }
        try {
            Cursor cursor = context.getContentResolver().query(avsInstance.contentProviderUri, null, callingPackageName, null, null);
            try {
                if (cursor == null) {
                    result = AvsResult.NOT_FOUND;
                } else {
                    if (cursor.moveToFirst() && cursor.getString(0) != null) {
                        AvsResult result2 = AvsResult.DENIED;
                        String grantedApis = cursor.getString(0);
                        if (!grantedApis.contains(api)) {
                            result = result2;
                        } else {
                            result = AvsResult.GRANTED;
                        }
                    }
                    result = AvsResult.DENIED;
                }
                if (cursor != null) {
                    cursor.close();
                    return result;
                }
                return result;
            } finally {
            }
        } catch (SecurityException e) {
            AvsResult result3 = AvsResult.NOT_PERMITTED;
            return result3;
        }
    }

    private static AvsResult checkAvsInstance(Context context, AvsInstance avsInstance) {
        int i = 0;
        ProviderInfo cpInfo = context.getPackageManager().resolveContentProvider(avsInstance.authority, 0);
        if (cpInfo == null) {
            return AvsResult.NOT_FOUND;
        }
        boolean allowed = false;
        try {
            Signature[] packageSigs = Utils.getSigningCertificates(context, cpInfo.packageName);
            int length = packageSigs.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                Signature sig = packageSigs[i];
                String fp = Utils.getCertFingerprint(sig);
                if (fp == null || !avsInstance.fingerprints.contains(fp)) {
                    i++;
                } else {
                    allowed = true;
                    break;
                }
            }
            if (!allowed) {
                return AvsResult.NOT_FOUND;
            }
            return AvsResult.GRANTED;
        } catch (PackageManager.NameNotFoundException e) {
            return AvsResult.NOT_FOUND;
        }
    }

    private static class AvsInstance {
        final String authority;
        final Uri contentProviderUri;
        final List<String> fingerprints;
        boolean isAvailable = false;
        final String name;
        final String[] permissions;

        AvsInstance(String name, String authority, List<String> fingerprints, String... perms) {
            this.name = name;
            this.authority = authority;
            this.contentProviderUri = Uri.parse(String.format(VZWAVSLibrary.f1306EN, VZWAVSLibrary.URI_TEMPLATE, authority));
            this.fingerprints = fingerprints;
            this.permissions = perms == null ? new String[0] : perms;
        }
    }
}
