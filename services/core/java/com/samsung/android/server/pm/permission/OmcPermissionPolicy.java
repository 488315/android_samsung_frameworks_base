package com.samsung.android.server.pm.permission;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.analytics.activation.ActivationMonitor;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OmcPermissionPolicy {
    public final ArrayList mConfigDirs;
    public final PmServiceProxy mPmProxy;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.pm.permission.OmcPermissionPolicy$1, reason: invalid class name */
    public final class AnonymousClass1 extends PmServiceProxy {
        public final PackageInfo getPackageInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return this.mContext.getPackageManager().getPackageInfo(str, 671133696);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("OmcPermissionPolicy", "Package not found: ".concat(str));
                return null;
            }
        }

        public final PermissionInfo getPermissionInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return this.mContext.getPackageManager().getPermissionInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.w("OmcPermissionPolicy", "Permission not found: ".concat(str));
                return null;
            }
        }

        public final void grantRuntimePermissions(
                PackageInfo packageInfo, Set set, boolean z, int i) {
            int i2;
            int i3;
            UserHandle of = UserHandle.of(i);
            PackageManager packageManager = this.mContext.getPackageManager();
            String[] strArr = packageInfo.requestedPermissions;
            if (ArrayUtils.isEmpty(strArr)) {
                return;
            }
            PackageInfo packageInfo2 = getPackageInfo(packageInfo.packageName);
            if (packageInfo2 == null) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        new StringBuilder(),
                        packageInfo.packageName,
                        " is null",
                        "OmcPermissionPolicy");
                return;
            }
            String[] strArr2 = packageInfo2.requestedPermissions;
            int length = strArr.length;
            for (int i4 = 0; i4 < length; i4++) {
                if (!ArrayUtils.contains(strArr2, strArr[i4])) {
                    strArr[i4] = null;
                }
            }
            String[] strArr3 =
                    (String[])
                            ArrayUtils.filterNotNull(
                                    strArr, new OmcPermissionPolicy$1$$ExternalSyntheticLambda0());
            ArraySet arraySet = new ArraySet(set);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int i5 = z ? 48 : 32;
            List splitPermissions =
                    ((PermissionManager) this.mContext.getSystemService(PermissionManager.class))
                            .getSplitPermissions();
            int size = splitPermissions.size();
            for (int i6 = 0; i6 < size; i6++) {
                PermissionManager.SplitPermissionInfo splitPermissionInfo =
                        (PermissionManager.SplitPermissionInfo) splitPermissions.get(i6);
                if (applicationInfo != null
                        && applicationInfo.targetSdkVersion < splitPermissionInfo.getTargetSdk()) {
                    if (((ArraySet) set).contains(splitPermissionInfo.getSplitPermission())) {
                        arraySet.addAll(splitPermissionInfo.getNewPermissions());
                    }
                }
            }
            int length2 = strArr3.length;
            String[] strArr4 = new String[length2];
            int i7 = 0;
            int i8 = 0;
            for (String str : strArr3) {
                PermissionInfo permissionInfo = getPermissionInfo(str);
                if ((permissionInfo == null ? null : permissionInfo.backgroundPermission) != null) {
                    strArr4[i8] = str;
                    i8++;
                } else {
                    strArr4[(length2 - 1) - i7] = str;
                    i7++;
                }
            }
            for (String str2 : strArr3) {
                if (arraySet.contains(str2)) {
                    int permissionFlags =
                            packageManager.getPermissionFlags(str2, packageInfo.packageName, of);
                    boolean z2 = z && (permissionFlags & 16) != 0;
                    if ((permissionFlags & 23) != 0 && !z2) {
                        i2 = i5;
                        i3 = permissionFlags;
                    } else if ((permissionFlags & 4) == 0) {
                        i2 = i5 | (permissionFlags & 14336);
                        PermissionInfo permissionInfo2 = getPermissionInfo(str2);
                        if (permissionInfo2 == null ? false : permissionInfo2.isRestricted()) {
                            i3 = permissionFlags;
                            packageManager.updatePermissionFlags(
                                    str2, packageInfo.packageName, 4096, 4096, of);
                        } else {
                            i3 = permissionFlags;
                        }
                        if (z2) {
                            packageManager.updatePermissionFlags(
                                    str2, packageInfo.packageName, i3, i3 & (-17), of);
                        }
                        if (!(this.mContext
                                        .createContextAsUser(of, 0)
                                        .getPackageManager()
                                        .checkPermission(str2, packageInfo.packageName)
                                == 0)) {
                            this.mContext
                                    .getPackageManager()
                                    .grantRuntimePermission(packageInfo.packageName, str2, of);
                        }
                        packageManager.updatePermissionFlags(
                                str2, packageInfo.packageName, i2 | 64, i2, of);
                    }
                    if ((i3 & 32) != 0 && (i3 & 16) != 0 && !z) {
                        packageManager.updatePermissionFlags(
                                str2, packageInfo.packageName, 16, 0, of);
                    }
                    i5 = i2;
                }
            }
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public final void revokeRuntimePermissionsProxy(int i, String str, Set set) {
            PackageManager packageManager = this.mContext.getPackageManager();
            PackageInfo packageInfo = getPackageInfo(str);
            if (packageInfo == null || !packageInfo.applicationInfo.isSystemApp()) {
                packageInfo = null;
            }
            if (packageInfo == null || ArrayUtils.isEmpty(packageInfo.requestedPermissions)) {
                return;
            }
            ArraySet arraySet = new ArraySet(Arrays.asList(packageInfo.requestedPermissions));
            Iterator it = ((ArraySet) set).iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (arraySet.contains(str2)) {
                    UserHandle of = UserHandle.of(i);
                    int permissionFlags = packageManager.getPermissionFlags(str2, str, of);
                    if ((permissionFlags & 32) == 0
                            && (permissionFlags & 4) == 0
                            && (permissionFlags & 16) == 0) {
                        packageManager.revokeRuntimePermission(str, str2, of);
                        packageManager.updatePermissionFlags(str2, str, 32, 0, of);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultPermission {
        public final Set mFixedGrantedPermission = new ArraySet();
        public final Set mNonFixedGrantedPermission = new ArraySet();
        public final Set mRevokedPermission = new ArraySet();
        public String mCertDigests = null;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class PmServiceProxy {
        public Context mContext;

        public abstract void revokeRuntimePermissionsProxy(int i, String str, Set set);
    }

    public OmcPermissionPolicy(AnonymousClass1 anonymousClass1) {
        this.mPmProxy = anonymousClass1;
        ArrayList m =
                PortStatus_1_1$$ExternalSyntheticOutline0.m("/system/etc/omc-default-permissions");
        File file =
                new File(
                        SystemProperties.get("mdc.sys.omc_etcpath", ""), "omc-default-permissions");
        if (file.isDirectory() && file.canRead()) {
            m.add(file.getPath());
        }
        this.mConfigDirs = m;
    }

    public OmcPermissionPolicy(PmServiceProxy pmServiceProxy, String str) {
        this.mPmProxy = pmServiceProxy;
        this.mConfigDirs = new ArrayList(Arrays.asList(str));
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003f, code lost:

       if (r6 != 4) goto L36;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0042, code lost:

       r6 = r9.getName();
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004d, code lost:

       if (r6.equals("permission") == false) goto L37;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0077, code lost:

       if (r6.equals("revoke-permission") == false) goto L42;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0085, code lost:

       android.util.Log.d("OmcPermissionPolicy", "Unknown element under <defaultgrant - package>: " + r9.getName());
       com.android.internal.util.XmlUtils.skipCurrentTag(r9);
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0079, code lost:

       ((android.util.ArraySet) r2.mRevokedPermission).add(r9.getAttributeValue(null, "name"));
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x004f, code lost:

       r6 = r9.getAttributeValue(null, "name");
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x005e, code lost:

       if (java.lang.Boolean.parseBoolean(r9.getAttributeValue(null, "systemfixed")) == false) goto L38;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0068, code lost:

       ((android.util.ArraySet) r2.mNonFixedGrantedPermission).add(r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0060, code lost:

       ((android.util.ArraySet) r2.mFixedGrantedPermission).add(r6);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void readDefaultPermissionPackage(
            org.xmlpull.v1.XmlPullParser r9, android.util.ArrayMap r10) {
        /*
            java.lang.String r0 = "OmcPermissionPolicy"
            java.lang.String r1 = "name"
            java.lang.String r2 = "Read permission for package <"
            r3 = 0
            java.lang.String r4 = r9.getAttributeValue(r3, r1)     // Catch: java.lang.Exception -> L3a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L3a
            r5.<init>(r2)     // Catch: java.lang.Exception -> L3a
            r5.append(r4)     // Catch: java.lang.Exception -> L3a
            java.lang.String r2 = ">"
            r5.append(r2)     // Catch: java.lang.Exception -> L3a
            java.lang.String r2 = r5.toString()     // Catch: java.lang.Exception -> L3a
            android.util.Log.d(r0, r2)     // Catch: java.lang.Exception -> L3a
            com.samsung.android.server.pm.permission.OmcPermissionPolicy$DefaultPermission r2 = new com.samsung.android.server.pm.permission.OmcPermissionPolicy$DefaultPermission     // Catch: java.lang.Exception -> L3a
            r2.<init>()     // Catch: java.lang.Exception -> L3a
            int r5 = r9.getDepth()     // Catch: java.lang.Exception -> L3a
        L29:
            int r6 = r9.next()     // Catch: java.lang.Exception -> L3a
            r7 = 1
            if (r6 == r7) goto La1
            r7 = 3
            if (r6 != r7) goto L3c
            int r8 = r9.getDepth()     // Catch: java.lang.Exception -> L3a
            if (r8 <= r5) goto La1
            goto L3c
        L3a:
            r9 = move-exception
            goto La5
        L3c:
            if (r6 == r7) goto L29
            r7 = 4
            if (r6 != r7) goto L42
            goto L29
        L42:
            java.lang.String r6 = r9.getName()     // Catch: java.lang.Exception -> L3a
            java.lang.String r7 = "permission"
            boolean r7 = r6.equals(r7)     // Catch: java.lang.Exception -> L3a
            if (r7 == 0) goto L70
            java.lang.String r6 = r9.getAttributeValue(r3, r1)     // Catch: java.lang.Exception -> L3a
            java.lang.String r7 = "systemfixed"
            java.lang.String r7 = r9.getAttributeValue(r3, r7)     // Catch: java.lang.Exception -> L3a
            boolean r7 = java.lang.Boolean.parseBoolean(r7)     // Catch: java.lang.Exception -> L3a
            if (r7 == 0) goto L68
            java.util.Set r7 = r2.mFixedGrantedPermission     // Catch: java.lang.Exception -> L3a
            android.util.ArraySet r7 = (android.util.ArraySet) r7     // Catch: java.lang.Exception -> L3a
            r7.add(r6)     // Catch: java.lang.Exception -> L3a
            goto L29
        L68:
            java.util.Set r7 = r2.mNonFixedGrantedPermission     // Catch: java.lang.Exception -> L3a
            android.util.ArraySet r7 = (android.util.ArraySet) r7     // Catch: java.lang.Exception -> L3a
            r7.add(r6)     // Catch: java.lang.Exception -> L3a
            goto L29
        L70:
            java.lang.String r7 = "revoke-permission"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Exception -> L3a
            if (r6 == 0) goto L85
            java.lang.String r6 = r9.getAttributeValue(r3, r1)     // Catch: java.lang.Exception -> L3a
            java.util.Set r7 = r2.mRevokedPermission     // Catch: java.lang.Exception -> L3a
            android.util.ArraySet r7 = (android.util.ArraySet) r7     // Catch: java.lang.Exception -> L3a
            r7.add(r6)     // Catch: java.lang.Exception -> L3a
            goto L29
        L85:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L3a
            r6.<init>()     // Catch: java.lang.Exception -> L3a
            java.lang.String r7 = "Unknown element under <defaultgrant - package>: "
            r6.append(r7)     // Catch: java.lang.Exception -> L3a
            java.lang.String r7 = r9.getName()     // Catch: java.lang.Exception -> L3a
            r6.append(r7)     // Catch: java.lang.Exception -> L3a
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> L3a
            android.util.Log.d(r0, r6)     // Catch: java.lang.Exception -> L3a
            com.android.internal.util.XmlUtils.skipCurrentTag(r9)     // Catch: java.lang.Exception -> L3a
            goto L29
        La1:
            r10.put(r4, r2)     // Catch: java.lang.Exception -> L3a
            goto Lba
        La5:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r1 = "Exception "
            r10.<init>(r1)
            java.lang.String r9 = r9.toString()
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.d(r0, r9)
        Lba:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.pm.permission.OmcPermissionPolicy.readDefaultPermissionPackage(org.xmlpull.v1.XmlPullParser,"
                    + " android.util.ArrayMap):void");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:48|49|50|51|52|(2:53|54)|55|56|57|58|59|60) */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0107 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x008f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x00dc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x008f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x01fa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x017a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void grantOrRevokePermissions(java.lang.String r22, boolean r23, int[] r24) {
        /*
            Method dump skipped, instructions count: 779
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.pm.permission.OmcPermissionPolicy.grantOrRevokePermissions(java.lang.String,"
                    + " boolean, int[]):void");
    }

    public final void readDefaultExceptionsForPackage(
            XmlPullParser xmlPullParser, ArrayMap arrayMap) {
        try {
            String attributeValue = xmlPullParser.getAttributeValue(null, "package");
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "sha256-cert-digest");
            String attributeValue3 = xmlPullParser.getAttributeValue(null, "countrycode");
            if (attributeValue3 != null) {
                ((AnonymousClass1) this.mPmProxy).getClass();
                if (!attributeValue3.contains(
                        SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                    XmlUtils.skipCurrentTag(xmlPullParser);
                    return;
                }
            }
            DefaultPermission defaultPermission = new DefaultPermission();
            defaultPermission.mCertDigests = attributeValue2;
            int depth = xmlPullParser.getDepth();
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4) {
                    if (xmlPullParser.getName().equals("permission")) {
                        String attributeValue4 = xmlPullParser.getAttributeValue(null, "name");
                        if (Boolean.parseBoolean(
                                xmlPullParser.getAttributeValue(null, "systemfixed"))) {
                            ((ArraySet) defaultPermission.mFixedGrantedPermission)
                                    .add(attributeValue4);
                        } else {
                            ((ArraySet) defaultPermission.mNonFixedGrantedPermission)
                                    .add(attributeValue4);
                        }
                    } else {
                        Log.d(
                                "OmcPermissionPolicy",
                                "Unknown element under <defaultgrant - package>: "
                                        + xmlPullParser.getName());
                        XmlUtils.skipCurrentTag(xmlPullParser);
                    }
                }
            }
            arrayMap.put(attributeValue, defaultPermission);
        } catch (Exception e) {
            Log.d("OmcPermissionPolicy", "Exception " + e.toString());
        }
    }
}
