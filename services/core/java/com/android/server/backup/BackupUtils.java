package com.android.server.backup;

import android.content.pm.Signature;
import android.util.Slog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BackupUtils {
    public static ArrayList hashSignatureArray(Signature[] signatureArr) {
        byte[] bArr;
        if (signatureArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(signatureArr.length);
        for (Signature signature : signatureArr) {
            byte[] byteArray = signature.toByteArray();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(byteArray);
                bArr = messageDigest.digest();
            } catch (NoSuchAlgorithmException unused) {
                Slog.w("BackupUtils", "No SHA-256 algorithm found!");
                bArr = null;
            }
            arrayList.add(bArr);
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x006b, code lost:

       r1 = r1 + 1;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean signaturesMatch(
            java.util.ArrayList r7,
            android.content.pm.PackageInfo r8,
            android.content.pm.PackageManagerInternal r9) {
        /*
            r0 = 0
            if (r8 == 0) goto L73
            java.lang.String r1 = r8.packageName
            if (r1 != 0) goto L8
            goto L73
        L8:
            android.content.pm.ApplicationInfo r1 = r8.applicationInfo
            int r1 = r1.flags
            r2 = 1
            r1 = r1 & r2
            if (r1 == 0) goto L11
            return r2
        L11:
            boolean r1 = com.android.internal.util.ArrayUtils.isEmpty(r7)
            if (r1 == 0) goto L18
            return r0
        L18:
            android.content.pm.SigningInfo r1 = r8.signingInfo
            if (r1 != 0) goto L25
            java.lang.String r7 = "BackupUtils"
            java.lang.String r8 = "signingInfo is empty, app was either unsigned or the flag PackageManager#GET_SIGNING_CERTIFICATES was not specified"
            android.util.Slog.w(r7, r8)
            return r0
        L25:
            int r3 = r7.size()
            if (r3 != r2) goto L47
            java.lang.Object r7 = r7.get(r0)
            byte[] r7 = (byte[]) r7
            java.lang.String r8 = r8.packageName
            com.android.server.pm.PackageManagerService$PackageManagerInternalImpl r9 = (com.android.server.pm.PackageManagerService.PackageManagerInternalImpl) r9
            com.android.server.pm.PackageManagerService r9 = r9.mService
            com.android.server.pm.Computer r9 = r9.snapshotComputer()
            android.content.pm.SigningDetails r8 = r9.getSigningDetails(r8)
            if (r8 != 0) goto L42
            goto L46
        L42:
            boolean r0 = r8.hasSha256Certificate(r7, r2)
        L46:
            return r0
        L47:
            android.content.pm.Signature[] r8 = r1.getApkContentsSigners()
            java.util.ArrayList r8 = hashSignatureArray(r8)
            int r9 = r8.size()
            r1 = r0
        L54:
            if (r1 >= r3) goto L72
            r4 = r0
        L57:
            if (r4 >= r9) goto L71
            java.lang.Object r5 = r7.get(r1)
            byte[] r5 = (byte[]) r5
            java.lang.Object r6 = r8.get(r4)
            byte[] r6 = (byte[]) r6
            boolean r5 = java.util.Arrays.equals(r5, r6)
            if (r5 == 0) goto L6e
            int r1 = r1 + 1
            goto L54
        L6e:
            int r4 = r4 + 1
            goto L57
        L71:
            return r0
        L72:
            return r2
        L73:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.BackupUtils.signaturesMatch(java.util.ArrayList,"
                    + " android.content.pm.PackageInfo,"
                    + " android.content.pm.PackageManagerInternal):boolean");
    }
}
