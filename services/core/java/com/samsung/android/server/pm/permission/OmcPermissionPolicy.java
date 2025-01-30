package com.samsung.android.server.pm.permission;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.Signature;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.IInstalld;
import android.permission.IPermissionManager;
import android.permission.PermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.PackageUtils;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.server.pm.permission.OmcPermissionPolicy;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class OmcPermissionPolicy {
    public final ArrayList mConfigDirs;
    public final PmServiceProxy mPmProxy;

    public OmcPermissionPolicy(PmServiceProxy pmServiceProxy) {
        this.mPmProxy = pmServiceProxy;
        this.mConfigDirs = getConfigDirs();
    }

    public OmcPermissionPolicy(PmServiceProxy pmServiceProxy, String str) {
        this.mPmProxy = pmServiceProxy;
        this.mConfigDirs = new ArrayList(Arrays.asList(str));
    }

    public final ArrayList getConfigDirs() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("/system/etc/omc-default-permissions");
        File file = new File(SystemProperties.get("mdc.sys.omc_etcpath", ""), "omc-default-permissions");
        if (file.isDirectory() && file.canRead()) {
            arrayList.add(file.getPath());
        }
        return arrayList;
    }

    public void grantDefaultPermissions(int[] iArr, boolean z) {
        allowlistRestrictedRuntimePermissionsForAll(iArr);
        Iterator it = this.mConfigDirs.iterator();
        while (it.hasNext()) {
            grantDefaultPermissionsInternal(iArr, (String) it.next(), z);
        }
    }

    public final void grantDefaultPermissionsInternal(int[] iArr, String str, boolean z) {
        Log.d("OmcPermissionPolicy", "grantDefaultPermissionsInternal : " + str + ", firstBootOrUpgrade: " + z);
        File file = new File(str);
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.exists() && file2.getPath().endsWith(".xml")) {
                    try {
                        grantOrRevokePermissions(file2.getPath(), iArr, z);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final void allowlistRestrictedRuntimePermissionsForAll(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return;
        }
        int i = 0;
        List installedPackagesProxy = this.mPmProxy.getInstalledPackagesProxy(671133696, 0);
        int size = installedPackagesProxy.size();
        int i2 = 0;
        while (i2 < size) {
            PackageInfo packageInfo = (PackageInfo) installedPackagesProxy.get(i2);
            String[] strArr = packageInfo.requestedPermissions;
            if (strArr != null) {
                int length = strArr.length;
                int i3 = i;
                while (i3 < length) {
                    String str = strArr[i3];
                    PermissionInfo permissionInfoProxy = this.mPmProxy.getPermissionInfoProxy(str);
                    if (permissionInfoProxy != null && (permissionInfoProxy.flags & 12) != 0) {
                        int length2 = iArr.length;
                        for (int i4 = i; i4 < length2; i4++) {
                            this.mPmProxy.addAllowlistedRestrictedPermissionProxy(packageInfo.packageName, str, 4, iArr[i4]);
                        }
                    }
                    i3++;
                    i = 0;
                }
            }
            i2++;
            i = 0;
        }
    }

    public final boolean isSystemOrSignatureMatchedPackage(PackageInfo packageInfo, String str) {
        if (packageInfo.applicationInfo.isSystemApp()) {
            return true;
        }
        if (packageInfo.signingInfo != null && str != null) {
            if (packageInfo.applicationInfo.isSignedWithPlatformKey() && "android".equals(str)) {
                return true;
            }
            Signature[] apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners();
            if (apkContentsSigners == null) {
                return false;
            }
            for (Signature signature : apkContentsSigners) {
                if (str.contains(PackageUtils.computeSha256Digest(signature.toByteArray()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void grantOrRevokePermissions(String str, int[] iArr, boolean z) {
        String str2;
        boolean z2;
        String str3;
        int[] iArr2 = iArr;
        for (Map.Entry entry : readDefaultPermissionXML(str, z).entrySet()) {
            String str4 = (String) entry.getKey();
            DefaultPermission defaultPermission = (DefaultPermission) entry.getValue();
            if (str4 != null) {
                if (defaultPermission != null) {
                    PackageInfo packageInfoProxy = this.mPmProxy.getPackageInfoProxy(str4, 671133696);
                    if (packageInfoProxy != null) {
                        if (packageInfoProxy.applicationInfo != null && isSystemOrSignatureMatchedPackage(packageInfoProxy, defaultPermission.getCertDigests())) {
                            int length = iArr2.length;
                            boolean z3 = false;
                            int i = 0;
                            while (i < length) {
                                int i2 = iArr2[i];
                                String str5 = "OmcPermissionPolicy";
                                Log.d("OmcPermissionPolicy", "Granting permission for package <" + str4 + "> userId <" + i2 + ">");
                                if (defaultPermission.getFixedGrantedPermission().size() > 0) {
                                    this.mPmProxy.grantRuntimePermissionsProxy(packageInfoProxy, defaultPermission.getFixedGrantedPermission(), true, i2);
                                }
                                if (defaultPermission.getNonFixedGrantedPermission().size() > 0) {
                                    this.mPmProxy.grantRuntimePermissionsProxy(packageInfoProxy, defaultPermission.getNonFixedGrantedPermission(), z3, i2);
                                }
                                if (packageInfoProxy.sharedUserId != null) {
                                    Log.d("OmcPermissionPolicy", str4 + " use shared user id, skip revoke permission.");
                                } else if (defaultPermission.getRevokedPermission().size() > 0) {
                                    for (String str6 : defaultPermission.getRevokedPermission()) {
                                        if (str6 != null) {
                                            try {
                                                str3 = str6;
                                                str2 = str5;
                                                try {
                                                    this.mPmProxy.updatePermissionFlagsProxy(str6, str4, 261119, 0, i2);
                                                    ArraySet arraySet = new ArraySet();
                                                    arraySet.add(str3);
                                                    z2 = false;
                                                    try {
                                                        this.mPmProxy.revokeRuntimePermissionsProxy(str4, arraySet, false, i2);
                                                    } catch (IllegalArgumentException unused) {
                                                        Log.d(str2, "IllegalArgumentException: " + str4 + " - " + str3);
                                                        str5 = str2;
                                                        z3 = z2;
                                                    }
                                                } catch (IllegalArgumentException unused2) {
                                                    z2 = false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                str2 = str5;
                                                z2 = z3;
                                                str3 = str6;
                                            }
                                            str5 = str2;
                                            z3 = z2;
                                        }
                                    }
                                }
                                i++;
                                iArr2 = iArr;
                                z3 = z3;
                            }
                        }
                    }
                }
            }
            iArr2 = iArr;
        }
    }

    public final ArrayMap readDefaultPermissionXML(String str, boolean z) {
        FileInputStream fileInputStream;
        XmlPullParser newPullParser;
        int next;
        boolean z2;
        ArrayMap arrayMap = new ArrayMap();
        File file = new File(str);
        if (!file.exists()) {
            return arrayMap;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                newPullParser = Xml.newPullParser();
                newPullParser.setInput(fileInputStream, StandardCharsets.UTF_8.name());
                do {
                    next = newPullParser.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
            } finally {
            }
        } catch (Exception e) {
            Log.d("OmcPermissionPolicy", "Exception " + e.toString());
        }
        if (next != 2) {
            throw new XmlPullParserException("No start tag found in packages file");
        }
        String systemPropertyProxy = this.mPmProxy.getSystemPropertyProxy("ro.boot.activatedid");
        String systemPropertyProxy2 = this.mPmProxy.getSystemPropertyProxy("persist.omc.sales_code");
        if (TextUtils.isEmpty(systemPropertyProxy2)) {
            systemPropertyProxy2 = this.mPmProxy.getSystemPropertyProxy("ro.csc.sales_code");
        }
        Log.i("OmcPermissionPolicy", "Current sales code : " + systemPropertyProxy2 + ", aid: " + systemPropertyProxy);
        int depth = newPullParser.getDepth();
        while (true) {
            int next2 = newPullParser.next();
            if (next2 == 1 || (next2 == 3 && newPullParser.getDepth() <= depth)) {
                break;
            }
            if (next2 != 3 && next2 != 4) {
                String name = newPullParser.getName();
                if (name.equals("sales-code")) {
                    String attributeValue = newPullParser.getAttributeValue(null, "csc");
                    if (attributeValue == null) {
                        attributeValue = newPullParser.getAttributeValue(null, "code");
                    }
                    z2 = attributeValue == null || !TextUtils.equals(attributeValue, systemPropertyProxy2);
                    boolean parseBoolean = Boolean.parseBoolean(newPullParser.getAttributeValue(null, "firstboot"));
                    if (z && !parseBoolean) {
                        z2 = true;
                    }
                    if (z2) {
                        XmlUtils.skipCurrentTag(newPullParser);
                    }
                } else if (name.equals("activated-id")) {
                    String attributeValue2 = newPullParser.getAttributeValue(null, "code");
                    z2 = attributeValue2 == null || !TextUtils.equals(attributeValue2, systemPropertyProxy);
                    boolean parseBoolean2 = Boolean.parseBoolean(newPullParser.getAttributeValue(null, "firstboot"));
                    if (z && !parseBoolean2) {
                        z2 = true;
                    }
                    if (z2) {
                        XmlUtils.skipCurrentTag(newPullParser);
                    }
                } else if (name.equals("package")) {
                    readDefaultPermissionPackage(newPullParser, arrayMap);
                } else if (z && name.equals("exception")) {
                    readDefaultExceptionsForPackage(newPullParser, arrayMap);
                } else {
                    Log.d("OmcPermissionPolicy", "Unknown element under <defaultgrant>: " + newPullParser.getName());
                    XmlUtils.skipCurrentTag(newPullParser);
                }
            }
        }
        fileInputStream.close();
        return arrayMap;
    }

    public final boolean isSupportedCountry(String str) {
        return str == null || str.contains(this.mPmProxy.getSystemPropertyProxy(ActivationMonitor.COUNTRY_CODE_PROPERTY));
    }

    public final void readDefaultExceptionsForPackage(XmlPullParser xmlPullParser, ArrayMap arrayMap) {
        try {
            String attributeValue = xmlPullParser.getAttributeValue(null, "package");
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "sha256-cert-digest");
            if (!isSupportedCountry(xmlPullParser.getAttributeValue(null, "countrycode"))) {
                XmlUtils.skipCurrentTag(xmlPullParser);
                return;
            }
            DefaultPermission defaultPermission = new DefaultPermission();
            defaultPermission.setCertDigests(attributeValue2);
            int depth = xmlPullParser.getDepth();
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4) {
                    if (xmlPullParser.getName().equals("permission")) {
                        defaultPermission.addGrantedPermission(xmlPullParser.getAttributeValue(null, "name"), Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "systemfixed")));
                    } else {
                        Log.d("OmcPermissionPolicy", "Unknown element under <defaultgrant - package>: " + xmlPullParser.getName());
                        XmlUtils.skipCurrentTag(xmlPullParser);
                    }
                }
            }
            arrayMap.put(attributeValue, defaultPermission);
        } catch (Exception e) {
            Log.d("OmcPermissionPolicy", "Exception " + e.toString());
        }
    }

    public final void readDefaultPermissionPackage(XmlPullParser xmlPullParser, ArrayMap arrayMap) {
        try {
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            Log.d("OmcPermissionPolicy", "Read permission for package <" + attributeValue + ">");
            DefaultPermission defaultPermission = new DefaultPermission();
            int depth = xmlPullParser.getDepth();
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4) {
                    String name = xmlPullParser.getName();
                    if (name.equals("permission")) {
                        defaultPermission.addGrantedPermission(xmlPullParser.getAttributeValue(null, "name"), Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "systemfixed")));
                    } else if (name.equals("revoke-permission")) {
                        defaultPermission.addRevokePermission(xmlPullParser.getAttributeValue(null, "name"));
                    } else {
                        Log.d("OmcPermissionPolicy", "Unknown element under <defaultgrant - package>: " + xmlPullParser.getName());
                        XmlUtils.skipCurrentTag(xmlPullParser);
                    }
                }
            }
            arrayMap.put(attributeValue, defaultPermission);
        } catch (Exception e) {
            Log.d("OmcPermissionPolicy", "Exception " + e.toString());
        }
    }

    public class DefaultPermission {
        public String mCertDigests;
        public final Set mFixedGrantedPermission;
        public final Set mNonFixedGrantedPermission;
        public final Set mRevokedPermission;

        public DefaultPermission() {
            this.mFixedGrantedPermission = new ArraySet();
            this.mNonFixedGrantedPermission = new ArraySet();
            this.mRevokedPermission = new ArraySet();
            this.mCertDigests = null;
        }

        public void setCertDigests(String str) {
            this.mCertDigests = str;
        }

        public String getCertDigests() {
            return this.mCertDigests;
        }

        public void addGrantedPermission(String str, boolean z) {
            if (z) {
                this.mFixedGrantedPermission.add(str);
            } else {
                this.mNonFixedGrantedPermission.add(str);
            }
        }

        public void addRevokePermission(String str) {
            this.mRevokedPermission.add(str);
        }

        public Set getFixedGrantedPermission() {
            return this.mFixedGrantedPermission;
        }

        public Set getNonFixedGrantedPermission() {
            return this.mNonFixedGrantedPermission;
        }

        public Set getRevokedPermission() {
            return this.mRevokedPermission;
        }
    }

    /* renamed from: com.samsung.android.server.pm.permission.OmcPermissionPolicy$1 */
    public class C31901 extends PmServiceProxy {
        public final boolean isFixedOrUserSet(int i) {
            return (i & 23) != 0;
        }

        public C31901(Context context) {
            super(context);
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public void addAllowlistedRestrictedPermissionProxy(String str, String str2, int i, int i2) {
            try {
                IPermissionManager.Stub.asInterface(ServiceManager.getService("permissionmgr")).addAllowlistedRestrictedPermission(str, str2, i, i2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public PermissionInfo getPermissionInfoProxy(String str) {
            try {
                return this.mContext.getPackageManager().getPermissionInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.w("OmcPermissionPolicy", "Permission not found: " + str);
                return null;
            }
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public void updatePermissionFlagsProxy(String str, String str2, int i, int i2, int i3) {
            try {
                this.mContext.getPackageManager().updatePermissionFlags(str, str2, i & (-5), i2, UserHandle.of(i3));
            } catch (SecurityException unused) {
                Log.d("OmcPermissionPolicy", "Can't override a permission flag with POLICY_FIXED");
            }
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public void revokeRuntimePermissionsProxy(String str, Set set, boolean z, int i) {
            PackageManager packageManager = this.mContext.getPackageManager();
            PackageInfo systemPackageInfo = getSystemPackageInfo(str);
            if (systemPackageInfo == null || ArrayUtils.isEmpty(systemPackageInfo.requestedPermissions)) {
                return;
            }
            ArraySet arraySet = new ArraySet(Arrays.asList(systemPackageInfo.requestedPermissions));
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (arraySet.contains(str2)) {
                    UserHandle of = UserHandle.of(i);
                    int permissionFlags = packageManager.getPermissionFlags(str2, str, of);
                    if ((permissionFlags & 32) != 0 && (permissionFlags & 4) == 0 && ((permissionFlags & 16) == 0 || z)) {
                        packageManager.revokeRuntimePermission(str, str2, of);
                        packageManager.updatePermissionFlags(str2, str, 32, 0, of);
                    }
                }
            }
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public void grantRuntimePermissionsProxy(PackageInfo packageInfo, Set set, boolean z, int i) {
            grantRuntimePermissions(packageInfo, set, z, i);
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public List getInstalledPackagesProxy(int i, int i2) {
            return this.mContext.getPackageManager().getInstalledPackagesAsUser(i, i2);
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public PackageInfo getPackageInfoProxy(String str, int i) {
            try {
                return this.mContext.getPackageManager().getPackageInfo(str, i);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        }

        @Override // com.samsung.android.server.pm.permission.OmcPermissionPolicy.PmServiceProxy
        public String getSystemPropertyProxy(String str) {
            return SystemProperties.get(str);
        }

        public final PackageInfo getSystemPackageInfo(String str) {
            PackageInfo packageInfo = getPackageInfo(str);
            if (packageInfo == null || !packageInfo.applicationInfo.isSystemApp()) {
                return null;
            }
            return packageInfo;
        }

        public final PackageInfo getPackageInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return this.mContext.getPackageManager().getPackageInfo(str, 671133696);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("OmcPermissionPolicy", "Package not found: " + str);
                return null;
            }
        }

        public final void grantRuntimePermissions(PackageInfo packageInfo, Set set, boolean z, int i) {
            try {
                grantRuntimePermissions(packageInfo, set, z, false, true, i);
            } catch (Exception unused) {
                Slog.e("OmcPermissionPolicy", "Failed to grant for " + packageInfo);
            }
        }

        public final void grantRuntimePermissions(PackageInfo packageInfo, Set set, boolean z, boolean z2, boolean z3, int i) {
            int i2;
            int i3;
            UserHandle of = UserHandle.of(i);
            if (packageInfo == null) {
                Log.d("OmcPermissionPolicy", "pkg is null");
                return;
            }
            PackageManager packageManager = this.mContext.getPackageManager();
            String[] strArr = packageInfo.requestedPermissions;
            if (ArrayUtils.isEmpty(strArr)) {
                return;
            }
            PackageInfo packageInfo2 = getPackageInfo(packageInfo.packageName);
            if (packageInfo2 == null) {
                Log.d("OmcPermissionPolicy", packageInfo.packageName + " is null");
                return;
            }
            String[] strArr2 = packageInfo2.requestedPermissions;
            int length = strArr.length;
            for (int i4 = 0; i4 < length; i4++) {
                if (!ArrayUtils.contains(strArr2, strArr[i4])) {
                    strArr[i4] = null;
                }
            }
            String[] strArr3 = (String[]) ArrayUtils.filterNotNull(strArr, new IntFunction() { // from class: com.samsung.android.server.pm.permission.OmcPermissionPolicy$1$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i5) {
                    String[] lambda$grantRuntimePermissions$0;
                    lambda$grantRuntimePermissions$0 = OmcPermissionPolicy.C31901.lambda$grantRuntimePermissions$0(i5);
                    return lambda$grantRuntimePermissions$0;
                }
            });
            ArraySet arraySet = new ArraySet(set);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int i5 = z ? 48 : 32;
            List splitPermissions = ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).getSplitPermissions();
            int size = splitPermissions.size();
            for (int i6 = 0; i6 < size; i6++) {
                PermissionManager.SplitPermissionInfo splitPermissionInfo = (PermissionManager.SplitPermissionInfo) splitPermissions.get(i6);
                if (applicationInfo != null && applicationInfo.targetSdkVersion < splitPermissionInfo.getTargetSdk() && set.contains(splitPermissionInfo.getSplitPermission())) {
                    arraySet.addAll(splitPermissionInfo.getNewPermissions());
                }
            }
            int length2 = strArr3.length;
            String[] strArr4 = new String[length2];
            int i7 = 0;
            int i8 = 0;
            for (String str : strArr3) {
                if (getBackgroundPermission(str) != null) {
                    strArr4[i8] = str;
                    i8++;
                } else {
                    strArr4[(length2 - 1) - i7] = str;
                    i7++;
                }
            }
            for (String str2 : strArr3) {
                if (arraySet.contains(str2)) {
                    int permissionFlags = packageManager.getPermissionFlags(str2, packageInfo.packageName, of);
                    boolean z4 = z && (permissionFlags & 16) != 0;
                    if (isFixedOrUserSet(permissionFlags) && !z2 && !z4) {
                        i2 = i5;
                        i3 = permissionFlags;
                    } else if ((permissionFlags & 4) == 0) {
                        i2 = i5 | (permissionFlags & 14336);
                        if (z3 && isPermissionRestricted(str2)) {
                            i3 = permissionFlags;
                            packageManager.updatePermissionFlags(str2, packageInfo.packageName, IInstalld.FLAG_USE_QUOTA, IInstalld.FLAG_USE_QUOTA, of);
                        } else {
                            i3 = permissionFlags;
                        }
                        if (z4) {
                            packageManager.updatePermissionFlags(str2, packageInfo.packageName, i3, i3 & (-17), of);
                        }
                        if (!isGranted(str2, packageInfo, of)) {
                            grantPermission(str2, packageInfo, of);
                        }
                        packageManager.updatePermissionFlags(str2, packageInfo.packageName, i2 | 64, i2, of);
                    }
                    if ((i3 & 32) != 0 && (i3 & 16) != 0 && !z) {
                        packageManager.updatePermissionFlags(str2, packageInfo.packageName, 16, 0, of);
                    }
                    i5 = i2;
                }
            }
        }

        public static /* synthetic */ String[] lambda$grantRuntimePermissions$0(int i) {
            return new String[i];
        }

        public final String getBackgroundPermission(String str) {
            PermissionInfo permissionInfo = getPermissionInfo(str);
            if (permissionInfo == null) {
                return null;
            }
            return permissionInfo.backgroundPermission;
        }

        public final boolean isGranted(String str, PackageInfo packageInfo, UserHandle userHandle) {
            return this.mContext.createContextAsUser(userHandle, 0).getPackageManager().checkPermission(str, packageInfo.packageName) == 0;
        }

        public final boolean isPermissionRestricted(String str) {
            PermissionInfo permissionInfo = getPermissionInfo(str);
            if (permissionInfo == null) {
                return false;
            }
            return permissionInfo.isRestricted();
        }

        public final void grantPermission(String str, PackageInfo packageInfo, UserHandle userHandle) {
            this.mContext.getPackageManager().grantRuntimePermission(packageInfo.packageName, str, userHandle);
        }

        public final PermissionInfo getPermissionInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return this.mContext.getPackageManager().getPermissionInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.w("OmcPermissionPolicy", "Permission not found: " + str);
                return null;
            }
        }
    }

    public static PmServiceProxy createPmServiceProxy(Context context) {
        return new C31901(context);
    }

    public abstract class PmServiceProxy {
        public Context mContext;

        public abstract void addAllowlistedRestrictedPermissionProxy(String str, String str2, int i, int i2);

        public abstract List getInstalledPackagesProxy(int i, int i2);

        public abstract PackageInfo getPackageInfoProxy(String str, int i);

        public abstract PermissionInfo getPermissionInfoProxy(String str);

        public abstract String getSystemPropertyProxy(String str);

        public abstract void grantRuntimePermissionsProxy(PackageInfo packageInfo, Set set, boolean z, int i);

        public abstract void revokeRuntimePermissionsProxy(String str, Set set, boolean z, int i);

        public abstract void updatePermissionFlagsProxy(String str, String str2, int i, int i2, int i3);

        public PmServiceProxy(Context context) {
            this.mContext = context;
        }
    }
}
