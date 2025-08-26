package com.android.internal.telephony.vzwavslibrary;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class VZWAVSLibrary {
    private static final String AVS_AUTHORITY_MVS = "com.verizon.vzwavs.mvs.provider";
    private static final String AVS_AUTHORITY_STD = "com.verizon.vzwavs.provider";
    private static final AvsInstance[] AVS_INSTANCES;
    private static final String CERT_FP_MVS = "A1:F6:F0:8B:5D:91:99:55:DD:51:DA:94:88:38:87:14:29:B1:E9:36";
    private static final String CERT_FP_MVS_BYOD = "03:FE:29:EF:A0:6C:0B:D8:64:3A:A1:A7:C3:EC:91:A1:A6:57:00:E6";
    private static final String CERT_FP_STANDALONE = "0B:A7:6D:BD:55:0A:4C:76:68:BD:7C:85:60:C1:2D:AF:95:14:CC:02";
    private static final Locale EN;
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
        List<String> listAsList = Arrays.asList(CERT_FP_MVS, CERT_FP_MVS_BYOD);
        MVS_CERTS = listAsList;
        List<String> listSingletonList = Collections.singletonList(CERT_FP_STANDALONE);
        STANDALONE_CERTS = listSingletonList;
        EN = Locale.ENGLISH;
        AVS_INSTANCES = new AvsInstance[]{new AvsInstance("MvsAvs", AVS_AUTHORITY_MVS, listAsList, new String[0]), new AvsInstance("StandaloneAvs", AVS_AUTHORITY_STD, listSingletonList, new String[0])};
    }

    public static boolean isPackageAuthorized(Context context, String str, String str2) {
        if (str2 == null || context == null || str == null) {
            return false;
        }
        for (AvsInstance avsInstance : AVS_INSTANCES) {
            int iOrdinal = queryAvsInstance(context, str, str2, avsInstance).ordinal();
            if (iOrdinal == 0) {
                return true;
            }
            if (iOrdinal == 1) {
                return false;
            }
        }
        return false;
    }

    private static AvsResult queryAvsInstance(Context context, String str, String str2, AvsInstance avsInstance) throws IOException {
        AvsResult avsResult;
        if (!avsInstance.isAvailable) {
            AvsResult avsResultCheckAvsInstance = checkAvsInstance(context, avsInstance);
            if (avsResultCheckAvsInstance != AvsResult.GRANTED) {
                return avsResultCheckAvsInstance;
            }
            avsInstance.isAvailable = true;
        }
        try {
            Cursor cursorQuery = context.getContentResolver().query(avsInstance.contentProviderUri, null, str, null, null);
            try {
                if (cursorQuery == null) {
                    avsResult = AvsResult.NOT_FOUND;
                } else if (!cursorQuery.moveToFirst() || cursorQuery.getString(0) == null) {
                    avsResult = AvsResult.DENIED;
                } else {
                    avsResult = cursorQuery.getString(0).contains(str2) ? AvsResult.GRANTED : AvsResult.DENIED;
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return avsResult;
            } finally {
            }
        } catch (SecurityException unused) {
            return AvsResult.NOT_PERMITTED;
        }
    }

    private static AvsResult checkAvsInstance(Context context, AvsInstance avsInstance) throws IOException {
        ProviderInfo providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider(avsInstance.authority, 0);
        if (providerInfoResolveContentProvider == null) {
            return AvsResult.NOT_FOUND;
        }
        try {
            for (Signature signature : Utils.getSigningCertificates(context, providerInfoResolveContentProvider.packageName)) {
                String certFingerprint = Utils.getCertFingerprint(signature);
                if (certFingerprint != null && avsInstance.fingerprints.contains(certFingerprint)) {
                    return AvsResult.GRANTED;
                }
            }
            return AvsResult.NOT_FOUND;
        } catch (PackageManager.NameNotFoundException unused) {
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

        AvsInstance(String str, String str2, List<String> list, String... strArr) {
            this.name = str;
            this.authority = str2;
            this.contentProviderUri = Uri.parse(String.format(VZWAVSLibrary.EN, VZWAVSLibrary.URI_TEMPLATE, str2));
            this.fingerprints = list;
            this.permissions = strArr == null ? new String[0] : strArr;
        }
    }
}
