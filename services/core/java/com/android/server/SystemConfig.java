package com.android.server;

import android.content.pm.FeatureInfo;
import android.content.pm.Signature;
import android.content.pm.SignedPackage;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.Process;
import android.os.SystemProperties;
import android.os.VintfRuntimeInfo;
import android.permission.PermissionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimingsTraceLog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.build.UnboundedSdkLevel;
import com.android.server.pm.permission.PermissionAllowlist;

import libcore.util.EmptyArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public final class SystemConfig {
    public static SystemConfig sInstance;
    public String mModulesInstallerPackageName;
    public String mOverlayConfigSignaturePackage;
    public int[] mGlobalGids = EmptyArray.INT;
    public final SparseArray mSystemPermissions = new SparseArray();
    public final ArrayList mSplitPermissions = new ArrayList();
    public final ArrayMap mSharedLibraries = new ArrayMap();
    public final ArrayMap mAvailableFeatures = new ArrayMap();
    public final ArraySet mUnavailableFeatures = new ArraySet();
    public final ArrayMap mPermissions = new ArrayMap();
    public final ArraySet mAllowInPowerSaveExceptIdle = new ArraySet();
    public final ArraySet mAllowInPowerSave = new ArraySet();
    public final ArraySet mAllowInDataUsageSave = new ArraySet();
    public final ArraySet mAllowUnthrottledLocation = new ArraySet();
    public final ArrayMap mAllowAdasSettings = new ArrayMap();
    public final ArrayMap mAllowIgnoreLocationSettings = new ArrayMap();
    public final ArraySet mAllowlistCameraPrivacy = new ArraySet();
    public final ArraySet mAllowImplicitBroadcasts = new ArraySet();
    public final ArraySet mBgRestrictionExemption = new ArraySet();
    public final ArraySet mLinkedApps = new ArraySet();
    public final ArraySet mDefaultVrComponents = new ArraySet();
    public final ArraySet mBackupTransportWhitelist = new ArraySet();
    public final ArrayMap mPackageComponentEnabledState = new ArrayMap();
    public final ArraySet mHiddenApiPackageWhitelist = new ArraySet();
    public final ArraySet mDisabledUntilUsedPreinstalledCarrierApps = new ArraySet();
    public final ArrayMap mDisabledUntilUsedPreinstalledCarrierAssociatedApps = new ArrayMap();
    public final PermissionAllowlist mPermissionAllowlist = new PermissionAllowlist();
    public final ArrayMap mAllowedAssociations = new ArrayMap();
    public final ArraySet mBugreportWhitelistedPackages = new ArraySet();
    public final ArraySet mAppDataIsolationWhitelistedApps = new ArraySet();
    public final ArrayList mPreventUserDisablePackages = new ArrayList();
    public ArrayMap mPackageToUserTypeWhitelist = new ArrayMap();
    public ArrayMap mPackageToUserTypeBlacklist = new ArrayMap();
    public final ArraySet mRollbackWhitelistedPackages = new ArraySet();
    public final ArraySet mWhitelistedStagedInstallers = new ArraySet();
    public final ArrayMap mAllowedVendorApexes = new ArrayMap();
    public final Set mInstallConstraintsAllowlist = new ArraySet();
    public final ArrayMap mUpdateOwnersForSystemApps = new ArrayMap();
    public final SparseArray mDataUsageSystemUidPackages = new SparseArray();
    public final Set mInitialNonStoppedSystemPackages = new ArraySet();
    public final ArrayMap mPackageToSharedUidAllowList = new ArrayMap();
    public final Set mRequiredSystemPackages = new ArraySet();
    public final ArrayMap mAppMetadataFilePaths = new ArrayMap();
    public final Set mPreinstallPackagesWithStrictSignatureCheck = new ArraySet();
    public final ArraySet mEnhancedConfirmationTrustedPackages = new ArraySet();
    public final ArraySet mEnhancedConfirmationTrustedInstallers = new ArraySet();
    public Map mNamedActors = null;
    public boolean mAerSupported = false;

    public final class PermissionEntry {
        public int[] gids;
        public final String name;
        public final boolean perUser;

        public PermissionEntry(String str, boolean z) {
            this.name = str;
            this.perUser = z;
        }
    }

    public final class SharedLibraryEntry {
        public final boolean canBeSafelyIgnored;
        public final String[] dependencies;
        public final String filename;
        public final boolean isNative;
        public final String name;
        public final String onBootclasspathBefore;

        public SharedLibraryEntry(
                String str, String str2, String[] strArr, String str3, String str4) {
            this(str, str2, strArr, str3, str4, false);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:

           if (r2 != false) goto L16;
        */
        /* JADX WARN: Code restructure failed: missing block: B:6:0x0018, code lost:

           if (r2 != false) goto L15;
        */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x0026, code lost:

           r1 = true;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public SharedLibraryEntry(
                java.lang.String r1,
                java.lang.String r2,
                java.lang.String[] r3,
                java.lang.String r4,
                java.lang.String r5,
                boolean r6) {
            /*
                r0 = this;
                r0.<init>()
                r0.name = r1
                r0.filename = r2
                r0.dependencies = r3
                r0.onBootclasspathBefore = r5
                r0.isNative = r6
                r1 = 0
                if (r4 == 0) goto L1a
                com.android.server.SystemConfig r2 = com.android.server.SystemConfig.sInstance
                boolean r2 = com.android.modules.utils.build.UnboundedSdkLevel.isAtLeast(r4)     // Catch: java.lang.IllegalArgumentException -> L17
                goto L18
            L17:
                r2 = r1
            L18:
                if (r2 != 0) goto L26
            L1a:
                if (r5 == 0) goto L27
                com.android.server.SystemConfig r2 = com.android.server.SystemConfig.sInstance
                boolean r2 = com.android.modules.utils.build.UnboundedSdkLevel.isAtLeast(r5)     // Catch: java.lang.IllegalArgumentException -> L23
                goto L24
            L23:
                r2 = r1
            L24:
                if (r2 != 0) goto L27
            L26:
                r1 = 1
            L27:
                r0.canBeSafelyIgnored = r1
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.SystemConfig.SharedLibraryEntry.<init>(java.lang.String,"
                        + " java.lang.String, java.lang.String[], java.lang.String,"
                        + " java.lang.String, boolean):void");
        }

        public SharedLibraryEntry(String str, String str2, String[] strArr, boolean z) {
            this(str, str2, strArr, null, null, z);
        }
    }

    static {
        new ArrayMap();
    }

    public SystemConfig() {
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SystemConfig", 524288L);
        timingsTraceLog.traceBegin("readAllPermissions");
        try {
            readAllPermissions();
            readPublicNativeLibrariesList();
        } finally {
            timingsTraceLog.traceEnd();
        }
    }

    public SystemConfig(boolean z) {
        if (!z) {
            Slog.w("SystemConfig", "Constructing an empty test SystemConfig");
        } else {
            Slog.w("SystemConfig", "Constructing a test SystemConfig");
            readAllPermissions();
        }
    }

    public static SystemConfig getInstance() {
        SystemConfig systemConfig;
        if (Process.myUid() != 1000) {
            Slog.wtf(
                    "SystemConfig",
                    "SystemConfig is being accessed by a process other than system_server.");
        }
        synchronized (SystemConfig.class) {
            try {
                if (sInstance == null) {
                    sInstance = new SystemConfig();
                }
                systemConfig = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return systemConfig;
    }

    public static boolean isAtMostSdkLevel(String str) {
        try {
            return UnboundedSdkLevel.isAtMost(str);
        } catch (IllegalArgumentException unused) {
            return true;
        }
    }

    public static boolean isErofsSupported() {
        try {
            return Files.exists(Paths.get("/sys/fs/erofs", new String[0]), new LinkOption[0]);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isKernelVersionAtLeast(int i, int i2) {
        String[] split = VintfRuntimeInfo.getKernelVersion().split("\\.");
        if (split.length < 2) {
            return false;
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            return parseInt > i || (parseInt == i && Integer.parseInt(split[1]) >= i2);
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static void logNotAllowedInPartition(
            File file, String str, XmlPullParser xmlPullParser) {
        Slog.w(
                "SystemConfig",
                "<"
                        + str
                        + "> not allowed in partition of "
                        + file
                        + " at "
                        + xmlPullParser.getPositionDescription());
    }

    public static SignedPackage parseEnhancedConfirmationTrustedPackage(
            File file, String str, XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(attributeValue)) {
            Slog.w(
                    "SystemConfig",
                    "<"
                            + str
                            + "> without package "
                            + file
                            + " at "
                            + xmlPullParser.getPositionDescription());
            return null;
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "sha256-cert-digest");
        if (TextUtils.isEmpty(attributeValue2)) {
            Slog.w(
                    "SystemConfig",
                    "<"
                            + str
                            + "> without sha256-cert-digest in "
                            + file
                            + " at "
                            + xmlPullParser.getPositionDescription());
            return null;
        }
        try {
            return new SignedPackage(
                    attributeValue, new Signature(attributeValue2.replace(":", "")).toByteArray());
        } catch (IllegalArgumentException unused) {
            Slog.w(
                    "SystemConfig",
                    "<"
                            + str
                            + "> with invalid sha256-cert-digest in "
                            + file
                            + " at "
                            + xmlPullParser.getPositionDescription());
            return null;
        }
    }

    public static void readInstallInUserType(XmlPullParser xmlPullParser, Map map, Map map2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(attributeValue)) {
            Slog.w(
                    "SystemConfig",
                    "package is required for <install-in-user-type> in "
                            + xmlPullParser.getPositionDescription());
            return;
        }
        Set set = (Set) map.get(attributeValue);
        Set set2 = (Set) map2.get(attributeValue);
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            String name = xmlPullParser.getName();
            if ("install-in".equals(name)) {
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "user-type");
                if (TextUtils.isEmpty(attributeValue2)) {
                    Slog.w(
                            "SystemConfig",
                            "user-type is required for <install-in-user-type> in "
                                    + xmlPullParser.getPositionDescription());
                } else {
                    if (set == null) {
                        set = new ArraySet();
                        map.put(attributeValue, set);
                    }
                    set.add(attributeValue2);
                }
            } else if ("do-not-install-in".equals(name)) {
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "user-type");
                if (TextUtils.isEmpty(attributeValue3)) {
                    Slog.w(
                            "SystemConfig",
                            "user-type is required for <install-in-user-type> in "
                                    + xmlPullParser.getPositionDescription());
                } else {
                    if (set2 == null) {
                        set2 = new ArraySet();
                        map2.put(attributeValue, set2);
                    }
                    set2.add(attributeValue3);
                }
            } else {
                Slog.w(
                        "SystemConfig",
                        "unrecognized tag in <install-in-user-type> in "
                                + xmlPullParser.getPositionDescription());
            }
        }
    }

    public static void readPermissionAllowlist(
            XmlPullParser xmlPullParser, ArrayMap arrayMap, String str) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(attributeValue)) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "package is required for <", str, "> in ");
            m.append(xmlPullParser.getPositionDescription());
            Slog.w("SystemConfig", m.toString());
            return;
        }
        ArrayMap arrayMap2 = (ArrayMap) arrayMap.get(attributeValue);
        if (arrayMap2 == null) {
            arrayMap2 = new ArrayMap();
        }
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            String name = xmlPullParser.getName();
            if ("permission".equals(name)) {
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(attributeValue2)) {
                    Slog.w(
                            "SystemConfig",
                            "name is required for <permission> in "
                                    + xmlPullParser.getPositionDescription());
                } else {
                    arrayMap2.put(attributeValue2, Boolean.TRUE);
                }
            } else if ("deny-permission".equals(name)) {
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(attributeValue3)) {
                    Slog.w(
                            "SystemConfig",
                            "name is required for <deny-permission> in "
                                    + xmlPullParser.getPositionDescription());
                } else {
                    arrayMap2.put(attributeValue3, Boolean.FALSE);
                }
            }
        }
        arrayMap.put(attributeValue, arrayMap2);
    }

    public final void addFeature(int i, String str) {
        FeatureInfo featureInfo = (FeatureInfo) this.mAvailableFeatures.get(str);
        if (featureInfo != null) {
            featureInfo.version = Math.max(featureInfo.version, i);
            return;
        }
        FeatureInfo featureInfo2 = new FeatureInfo();
        featureInfo2.name = str;
        featureInfo2.version = i;
        this.mAvailableFeatures.put(str, featureInfo2);
    }

    public final void enableIpSecTunnelMigrationOnVsrUAndAbove() {
        if (SystemProperties.getInt("ro.vendor.api_level", Build.VERSION.DEVICE_INITIAL_SDK_INT)
                > 33) {
            addFeature(0, "android.software.ipsec_tunnel_migration");
        }
    }

    public final ArrayMap getAppMetadataFilePaths() {
        return this.mAppMetadataFilePaths;
    }

    public final Set getInitialNonStoppedSystemPackages() {
        return this.mInitialNonStoppedSystemPackages;
    }

    public final String getOverlayConfigSignaturePackage() {
        if (TextUtils.isEmpty(this.mOverlayConfigSignaturePackage)) {
            return null;
        }
        return this.mOverlayConfigSignaturePackage;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x048f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x04c4  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x04fd A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03d8  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x045d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readAllPermissions() {
        /*
            Method dump skipped, instructions count: 1278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.SystemConfig.readAllPermissions():void");
    }

    public void readApexPrivAppPermissions(XmlPullParser xmlPullParser, File file, Path path)
            throws IOException, XmlPullParserException {
        Path path2 = file.toPath();
        if (!path2.startsWith(path)) {
            throw new IllegalArgumentException("File " + path2 + " is not part of an APEX.");
        }
        if (path2.getNameCount() <= path.getNameCount() + 1) {
            throw new IllegalArgumentException(
                    "File " + path2 + " is in the APEX partition, but not inside a module.");
        }
        String path3 = path2.getName(path.getNameCount()).toString();
        ArrayMap arrayMap = this.mPermissionAllowlist.mApexPrivilegedAppAllowlists;
        ArrayMap arrayMap2 = (ArrayMap) arrayMap.get(path3);
        if (arrayMap2 == null) {
            arrayMap2 = new ArrayMap();
            arrayMap.put(path3, arrayMap2);
        }
        readPermissionAllowlist(xmlPullParser, arrayMap2, "privapp-permissions");
    }

    public final void readComponentOverrides(XmlPullParser xmlPullParser, File file) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (attributeValue == null) {
            Slog.w(
                    "SystemConfig",
                    "<component-override> without package in "
                            + file
                            + " at "
                            + xmlPullParser.getPositionDescription());
            return;
        }
        String intern = attributeValue.intern();
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("component".equals(xmlPullParser.getName())) {
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "class");
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "enabled");
                if (attributeValue2 == null) {
                    Slog.w(
                            "SystemConfig",
                            "<component> without class in "
                                    + file
                                    + " at "
                                    + xmlPullParser.getPositionDescription());
                    return;
                }
                if (attributeValue3 == null) {
                    Slog.w(
                            "SystemConfig",
                            "<component> without enabled in "
                                    + file
                                    + " at "
                                    + xmlPullParser.getPositionDescription());
                    return;
                }
                if (attributeValue2.startsWith(".")) {
                    attributeValue2 =
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                    intern, attributeValue2);
                }
                String intern2 = attributeValue2.intern();
                ArrayMap arrayMap = (ArrayMap) this.mPackageComponentEnabledState.get(intern);
                if (arrayMap == null) {
                    arrayMap = new ArrayMap();
                    this.mPackageComponentEnabledState.put(intern, arrayMap);
                }
                arrayMap.put(intern2, Boolean.valueOf(!"false".equals(attributeValue3)));
            }
        }
    }

    public final void readOemPermissions(XmlPullParser xmlPullParser) {
        readPermissionAllowlist(
                xmlPullParser, this.mPermissionAllowlist.mOemAppAllowlist, "oem-permissions");
    }

    public final void readPermission(XmlPullParser xmlPullParser, String str) {
        if (this.mPermissions.containsKey(str)) {
            throw new IllegalStateException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Duplicate permission definition for ", str));
        }
        PermissionEntry permissionEntry =
                new PermissionEntry(
                        str, XmlUtils.readBooleanAttribute(xmlPullParser, "perUser", false));
        this.mPermissions.put(str, permissionEntry);
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if ("group".equals(xmlPullParser.getName())) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "gid");
                    if (attributeValue != null) {
                        int gidForName = Process.getGidForName(attributeValue);
                        if (gidForName != -1) {
                            permissionEntry.gids =
                                    ArrayUtils.appendInt(permissionEntry.gids, gidForName);
                        } else {
                            StringBuilder m =
                                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                                            "<group> with unknown gid \"",
                                            attributeValue,
                                            " for permission ",
                                            str,
                                            " in ");
                            m.append(xmlPullParser.getPositionDescription());
                            Slog.w("SystemConfig", m.toString());
                        }
                    } else {
                        Slog.w(
                                "SystemConfig",
                                "<group> without gid at " + xmlPullParser.getPositionDescription());
                    }
                }
                XmlUtils.skipCurrentTag(xmlPullParser);
            }
        }
    }

    public void readPermissions(XmlPullParser xmlPullParser, File file, int i) {
        if (!file.exists() || !file.isDirectory()) {
            if (i == -1) {
                Slog.w("SystemConfig", "No directory " + file + ", skipping");
                return;
            }
            return;
        }
        if (!file.canRead()) {
            Slog.w("SystemConfig", "Directory " + file + " cannot be read");
            return;
        }
        File file2 = null;
        for (File file3 : file.listFiles()) {
            if (file3.isFile()) {
                if (file3.getPath().endsWith("etc/permissions/platform.xml")) {
                    file2 = file3;
                } else if (!file3.getPath().endsWith(".xml")) {
                    Slog.i(
                            "SystemConfig",
                            "Non-xml file " + file3 + " in " + file + " directory, ignoring");
                } else if (!file3.canRead()) {
                    Slog.w("SystemConfig", "Permissions library file " + file3 + " cannot be read");
                } else if (this.mAerSupported || !file3.getPath().contains("aer")) {
                    readPermissionsFromXml(xmlPullParser, file3, i);
                } else {
                    Slog.e(
                            "SystemConfig",
                            "aer = "
                                    + this.mAerSupported
                                    + ", f.getPath().contains = "
                                    + file3.getPath().contains("aer"));
                }
            }
        }
        if (file2 != null) {
            readPermissionsFromXml(xmlPullParser, file2, i);
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:64:0x0119. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:101:0x1523  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x152e  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x153f  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x154f  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x155d  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x1586  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x1534  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0c51 A[Catch: all -> 0x0130, IOException -> 0x0134, XmlPullParserException -> 0x013a, TryCatch #3 {all -> 0x0130, blocks: (B:69:0x0430, B:71:0x0436, B:73:0x043c, B:78:0x0445, B:80:0x044b, B:82:0x0451, B:83:0x0481, B:85:0x0487, B:87:0x0491, B:88:0x04b4, B:90:0x04b6, B:94:0x14f6, B:135:0x1502, B:136:0x04c2, B:138:0x04cc, B:139:0x04f0, B:140:0x04f2, B:141:0x04f9, B:143:0x050a, B:144:0x052e, B:146:0x0534, B:147:0x055a, B:148:0x0561, B:150:0x0572, B:151:0x0596, B:153:0x059c, B:154:0x05c2, B:155:0x05c9, B:157:0x05da, B:158:0x05fe, B:160:0x0604, B:161:0x062a, B:163:0x0630, B:164:0x0632, B:165:0x0639, B:167:0x064a, B:168:0x069d, B:169:0x066d, B:171:0x0673, B:172:0x0698, B:174:0x06a4, B:176:0x06aa, B:177:0x06d8, B:178:0x06cd, B:179:0x06cf, B:180:0x06d5, B:182:0x06df, B:184:0x06e5, B:185:0x070c, B:187:0x0712, B:188:0x0739, B:193:0x0746, B:195:0x0750, B:196:0x075a, B:197:0x0792, B:198:0x075e, B:199:0x078f, B:201:0x0799, B:203:0x07a6, B:205:0x07ca, B:208:0x07f2, B:209:0x07fb, B:210:0x07f8, B:212:0x0802, B:214:0x0810, B:216:0x083a, B:218:0x083e, B:220:0x0841, B:221:0x0848, B:222:0x084c, B:223:0x0833, B:224:0x0849, B:225:0x0851, B:227:0x0857, B:228:0x087f, B:229:0x087a, B:231:0x0886, B:233:0x088c, B:234:0x08e2, B:235:0x08af, B:237:0x08b7, B:239:0x08be, B:240:0x08de, B:241:0x08df, B:242:0x08e7, B:244:0x0908, B:245:0x09b9, B:246:0x092e, B:248:0x0934, B:249:0x0959, B:251:0x095f, B:252:0x0984, B:254:0x098c, B:256:0x0990, B:257:0x0997, B:258:0x0999, B:259:0x099f, B:261:0x09a3, B:262:0x09aa, B:263:0x09b6, B:264:0x09b0, B:267:0x09be, B:268:0x09f0, B:270:0x09f1, B:271:0x0a14, B:272:0x0a15, B:273:0x0a1e, B:275:0x0a24, B:276:0x0a4c, B:277:0x0a47, B:278:0x0a51, B:280:0x0a57, B:281:0x0a7f, B:282:0x0a7a, B:283:0x0a84, B:285:0x0a8a, B:286:0x0ab2, B:287:0x0aad, B:289:0x0ab9, B:291:0x0ac2, B:292:0x0aeb, B:294:0x0af3, B:295:0x0b1c, B:297:0x0b2e, B:298:0x0b38, B:299:0x0b5b, B:300:0x0b58, B:302:0x0b62, B:304:0x0b68, B:305:0x0b94, B:306:0x0b8b, B:307:0x0b91, B:309:0x0b9b, B:310:0x0ba0, B:313:0x0bac, B:315:0x0bcd, B:319:0x0bf2, B:321:0x0c51, B:324:0x0c62, B:326:0x0c6b, B:328:0x0c74, B:329:0x0c7b, B:331:0x0c82, B:334:0x0c90, B:336:0x0cb1, B:340:0x0cd6, B:342:0x0d35, B:345:0x0d42, B:347:0x0d4b, B:349:0x0d54, B:350:0x0d61, B:352:0x0d68, B:354:0x0d72, B:356:0x0d78, B:357:0x0da4, B:358:0x0d9b, B:359:0x0da1, B:361:0x0dab, B:365:0x0dbb, B:374:0x0dc7, B:368:0x0df6, B:370:0x0e00, B:371:0x0e0a, B:372:0x0e3b, B:376:0x0dcc, B:377:0x0e13, B:378:0x0e38, B:381:0x0e44, B:383:0x0e4d, B:384:0x0eac, B:385:0x0e72, B:387:0x0e78, B:388:0x0ea3, B:389:0x0ea9, B:390:0x0eb1, B:392:0x0eb8, B:394:0x0ec5, B:395:0x0f1d, B:397:0x0eea, B:398:0x0f0f, B:399:0x0f1a, B:401:0x0f24, B:403:0x0f2a, B:404:0x0f56, B:405:0x0f4d, B:406:0x0f53, B:408:0x0f5d, B:410:0x0f63, B:411:0x0f8f, B:412:0x0f86, B:413:0x0f8c, B:415:0x0f96, B:417:0x0f9e, B:418:0x0fcc, B:419:0x0fc3, B:420:0x0fc9, B:422:0x0fd3, B:424:0x0fdf, B:425:0x1038, B:426:0x1002, B:428:0x100c, B:431:0x1014, B:432:0x101f, B:434:0x1027, B:437:0x1031, B:439:0x1035, B:441:0x103f, B:443:0x1045, B:444:0x1071, B:445:0x1068, B:446:0x106e, B:449:0x107e, B:451:0x1088, B:452:0x10dd, B:453:0x10ab, B:455:0x10b5, B:458:0x10bd, B:459:0x10c8, B:461:0x10ce, B:464:0x10d6, B:466:0x10da, B:468:0x10e4, B:470:0x10ea, B:471:0x1116, B:472:0x110d, B:473:0x1113, B:475:0x111d, B:477:0x1123, B:478:0x114f, B:479:0x1146, B:480:0x114c, B:482:0x1156, B:484:0x115c, B:485:0x1188, B:486:0x117f, B:487:0x1185, B:489:0x118f, B:491:0x1195, B:492:0x11c1, B:493:0x11b8, B:494:0x11be, B:497:0x11ca, B:499:0x11d0, B:500:0x11fe, B:501:0x11f5, B:502:0x11fb, B:505:0x1209, B:509:0x122f, B:510:0x125d, B:512:0x1254, B:513:0x121b, B:514:0x1258, B:517:0x126a, B:519:0x128c, B:520:0x1368, B:522:0x12b2, B:550:0x12da, B:526:0x12e9, B:530:0x12f4, B:535:0x1305, B:537:0x1317, B:538:0x1321, B:539:0x131c, B:540:0x132d, B:542:0x133c, B:544:0x1346, B:546:0x1350, B:547:0x135d, B:555:0x1365, B:558:0x1371, B:559:0x1376, B:562:0x1386, B:564:0x138c, B:565:0x13b3, B:567:0x13b9, B:568:0x13e0, B:570:0x13e6, B:571:0x1417, B:573:0x1425, B:574:0x142f, B:575:0x1436, B:576:0x1433, B:579:0x1443, B:581:0x1449, B:582:0x146f, B:583:0x1477, B:586:0x1482, B:588:0x148b, B:589:0x14c0, B:590:0x1498, B:591:0x14bd, B:592:0x0457, B:594:0x0122, B:598:0x0140, B:601:0x014d, B:604:0x015b, B:607:0x0169, B:610:0x0177, B:613:0x0185, B:616:0x0196, B:619:0x01a1, B:622:0x01b0, B:625:0x01bf, B:628:0x01ce, B:631:0x01dc, B:634:0x01eb, B:637:0x01fa, B:640:0x0209, B:643:0x0218, B:646:0x0227, B:649:0x0236, B:652:0x0246, B:655:0x0256, B:658:0x0265, B:661:0x0273, B:664:0x0282, B:667:0x0291, B:670:0x02a0, B:673:0x02af, B:676:0x02bf, B:679:0x02cf, B:682:0x02db, B:685:0x02ea, B:688:0x02f9, B:691:0x0309, B:694:0x0317, B:697:0x0326, B:700:0x0335, B:703:0x0343, B:706:0x0351, B:709:0x035f, B:712:0x036d, B:715:0x037c, B:718:0x038b, B:721:0x039b, B:724:0x03aa, B:727:0x03b8, B:730:0x03c6, B:733:0x03d4, B:769:0x14ea, B:770:0x14f5), top: B:5:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0c5c  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0d35 A[Catch: all -> 0x0130, IOException -> 0x0134, XmlPullParserException -> 0x013a, TryCatch #3 {all -> 0x0130, blocks: (B:69:0x0430, B:71:0x0436, B:73:0x043c, B:78:0x0445, B:80:0x044b, B:82:0x0451, B:83:0x0481, B:85:0x0487, B:87:0x0491, B:88:0x04b4, B:90:0x04b6, B:94:0x14f6, B:135:0x1502, B:136:0x04c2, B:138:0x04cc, B:139:0x04f0, B:140:0x04f2, B:141:0x04f9, B:143:0x050a, B:144:0x052e, B:146:0x0534, B:147:0x055a, B:148:0x0561, B:150:0x0572, B:151:0x0596, B:153:0x059c, B:154:0x05c2, B:155:0x05c9, B:157:0x05da, B:158:0x05fe, B:160:0x0604, B:161:0x062a, B:163:0x0630, B:164:0x0632, B:165:0x0639, B:167:0x064a, B:168:0x069d, B:169:0x066d, B:171:0x0673, B:172:0x0698, B:174:0x06a4, B:176:0x06aa, B:177:0x06d8, B:178:0x06cd, B:179:0x06cf, B:180:0x06d5, B:182:0x06df, B:184:0x06e5, B:185:0x070c, B:187:0x0712, B:188:0x0739, B:193:0x0746, B:195:0x0750, B:196:0x075a, B:197:0x0792, B:198:0x075e, B:199:0x078f, B:201:0x0799, B:203:0x07a6, B:205:0x07ca, B:208:0x07f2, B:209:0x07fb, B:210:0x07f8, B:212:0x0802, B:214:0x0810, B:216:0x083a, B:218:0x083e, B:220:0x0841, B:221:0x0848, B:222:0x084c, B:223:0x0833, B:224:0x0849, B:225:0x0851, B:227:0x0857, B:228:0x087f, B:229:0x087a, B:231:0x0886, B:233:0x088c, B:234:0x08e2, B:235:0x08af, B:237:0x08b7, B:239:0x08be, B:240:0x08de, B:241:0x08df, B:242:0x08e7, B:244:0x0908, B:245:0x09b9, B:246:0x092e, B:248:0x0934, B:249:0x0959, B:251:0x095f, B:252:0x0984, B:254:0x098c, B:256:0x0990, B:257:0x0997, B:258:0x0999, B:259:0x099f, B:261:0x09a3, B:262:0x09aa, B:263:0x09b6, B:264:0x09b0, B:267:0x09be, B:268:0x09f0, B:270:0x09f1, B:271:0x0a14, B:272:0x0a15, B:273:0x0a1e, B:275:0x0a24, B:276:0x0a4c, B:277:0x0a47, B:278:0x0a51, B:280:0x0a57, B:281:0x0a7f, B:282:0x0a7a, B:283:0x0a84, B:285:0x0a8a, B:286:0x0ab2, B:287:0x0aad, B:289:0x0ab9, B:291:0x0ac2, B:292:0x0aeb, B:294:0x0af3, B:295:0x0b1c, B:297:0x0b2e, B:298:0x0b38, B:299:0x0b5b, B:300:0x0b58, B:302:0x0b62, B:304:0x0b68, B:305:0x0b94, B:306:0x0b8b, B:307:0x0b91, B:309:0x0b9b, B:310:0x0ba0, B:313:0x0bac, B:315:0x0bcd, B:319:0x0bf2, B:321:0x0c51, B:324:0x0c62, B:326:0x0c6b, B:328:0x0c74, B:329:0x0c7b, B:331:0x0c82, B:334:0x0c90, B:336:0x0cb1, B:340:0x0cd6, B:342:0x0d35, B:345:0x0d42, B:347:0x0d4b, B:349:0x0d54, B:350:0x0d61, B:352:0x0d68, B:354:0x0d72, B:356:0x0d78, B:357:0x0da4, B:358:0x0d9b, B:359:0x0da1, B:361:0x0dab, B:365:0x0dbb, B:374:0x0dc7, B:368:0x0df6, B:370:0x0e00, B:371:0x0e0a, B:372:0x0e3b, B:376:0x0dcc, B:377:0x0e13, B:378:0x0e38, B:381:0x0e44, B:383:0x0e4d, B:384:0x0eac, B:385:0x0e72, B:387:0x0e78, B:388:0x0ea3, B:389:0x0ea9, B:390:0x0eb1, B:392:0x0eb8, B:394:0x0ec5, B:395:0x0f1d, B:397:0x0eea, B:398:0x0f0f, B:399:0x0f1a, B:401:0x0f24, B:403:0x0f2a, B:404:0x0f56, B:405:0x0f4d, B:406:0x0f53, B:408:0x0f5d, B:410:0x0f63, B:411:0x0f8f, B:412:0x0f86, B:413:0x0f8c, B:415:0x0f96, B:417:0x0f9e, B:418:0x0fcc, B:419:0x0fc3, B:420:0x0fc9, B:422:0x0fd3, B:424:0x0fdf, B:425:0x1038, B:426:0x1002, B:428:0x100c, B:431:0x1014, B:432:0x101f, B:434:0x1027, B:437:0x1031, B:439:0x1035, B:441:0x103f, B:443:0x1045, B:444:0x1071, B:445:0x1068, B:446:0x106e, B:449:0x107e, B:451:0x1088, B:452:0x10dd, B:453:0x10ab, B:455:0x10b5, B:458:0x10bd, B:459:0x10c8, B:461:0x10ce, B:464:0x10d6, B:466:0x10da, B:468:0x10e4, B:470:0x10ea, B:471:0x1116, B:472:0x110d, B:473:0x1113, B:475:0x111d, B:477:0x1123, B:478:0x114f, B:479:0x1146, B:480:0x114c, B:482:0x1156, B:484:0x115c, B:485:0x1188, B:486:0x117f, B:487:0x1185, B:489:0x118f, B:491:0x1195, B:492:0x11c1, B:493:0x11b8, B:494:0x11be, B:497:0x11ca, B:499:0x11d0, B:500:0x11fe, B:501:0x11f5, B:502:0x11fb, B:505:0x1209, B:509:0x122f, B:510:0x125d, B:512:0x1254, B:513:0x121b, B:514:0x1258, B:517:0x126a, B:519:0x128c, B:520:0x1368, B:522:0x12b2, B:550:0x12da, B:526:0x12e9, B:530:0x12f4, B:535:0x1305, B:537:0x1317, B:538:0x1321, B:539:0x131c, B:540:0x132d, B:542:0x133c, B:544:0x1346, B:546:0x1350, B:547:0x135d, B:555:0x1365, B:558:0x1371, B:559:0x1376, B:562:0x1386, B:564:0x138c, B:565:0x13b3, B:567:0x13b9, B:568:0x13e0, B:570:0x13e6, B:571:0x1417, B:573:0x1425, B:574:0x142f, B:575:0x1436, B:576:0x1433, B:579:0x1443, B:581:0x1449, B:582:0x146f, B:583:0x1477, B:586:0x1482, B:588:0x148b, B:589:0x14c0, B:590:0x1498, B:591:0x14bd, B:592:0x0457, B:594:0x0122, B:598:0x0140, B:601:0x014d, B:604:0x015b, B:607:0x0169, B:610:0x0177, B:613:0x0185, B:616:0x0196, B:619:0x01a1, B:622:0x01b0, B:625:0x01bf, B:628:0x01ce, B:631:0x01dc, B:634:0x01eb, B:637:0x01fa, B:640:0x0209, B:643:0x0218, B:646:0x0227, B:649:0x0236, B:652:0x0246, B:655:0x0256, B:658:0x0265, B:661:0x0273, B:664:0x0282, B:667:0x0291, B:670:0x02a0, B:673:0x02af, B:676:0x02bf, B:679:0x02cf, B:682:0x02db, B:685:0x02ea, B:688:0x02f9, B:691:0x0309, B:694:0x0317, B:697:0x0326, B:700:0x0335, B:703:0x0343, B:706:0x0351, B:709:0x035f, B:712:0x036d, B:715:0x037c, B:718:0x038b, B:721:0x039b, B:724:0x03aa, B:727:0x03b8, B:730:0x03c6, B:733:0x03d4, B:769:0x14ea, B:770:0x14f5), top: B:5:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0d3e  */
    /* JADX WARN: Removed duplicated region for block: B:533:0x1301 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:542:0x133c A[Catch: all -> 0x0130, IOException -> 0x0134, XmlPullParserException -> 0x013a, TryCatch #3 {all -> 0x0130, blocks: (B:69:0x0430, B:71:0x0436, B:73:0x043c, B:78:0x0445, B:80:0x044b, B:82:0x0451, B:83:0x0481, B:85:0x0487, B:87:0x0491, B:88:0x04b4, B:90:0x04b6, B:94:0x14f6, B:135:0x1502, B:136:0x04c2, B:138:0x04cc, B:139:0x04f0, B:140:0x04f2, B:141:0x04f9, B:143:0x050a, B:144:0x052e, B:146:0x0534, B:147:0x055a, B:148:0x0561, B:150:0x0572, B:151:0x0596, B:153:0x059c, B:154:0x05c2, B:155:0x05c9, B:157:0x05da, B:158:0x05fe, B:160:0x0604, B:161:0x062a, B:163:0x0630, B:164:0x0632, B:165:0x0639, B:167:0x064a, B:168:0x069d, B:169:0x066d, B:171:0x0673, B:172:0x0698, B:174:0x06a4, B:176:0x06aa, B:177:0x06d8, B:178:0x06cd, B:179:0x06cf, B:180:0x06d5, B:182:0x06df, B:184:0x06e5, B:185:0x070c, B:187:0x0712, B:188:0x0739, B:193:0x0746, B:195:0x0750, B:196:0x075a, B:197:0x0792, B:198:0x075e, B:199:0x078f, B:201:0x0799, B:203:0x07a6, B:205:0x07ca, B:208:0x07f2, B:209:0x07fb, B:210:0x07f8, B:212:0x0802, B:214:0x0810, B:216:0x083a, B:218:0x083e, B:220:0x0841, B:221:0x0848, B:222:0x084c, B:223:0x0833, B:224:0x0849, B:225:0x0851, B:227:0x0857, B:228:0x087f, B:229:0x087a, B:231:0x0886, B:233:0x088c, B:234:0x08e2, B:235:0x08af, B:237:0x08b7, B:239:0x08be, B:240:0x08de, B:241:0x08df, B:242:0x08e7, B:244:0x0908, B:245:0x09b9, B:246:0x092e, B:248:0x0934, B:249:0x0959, B:251:0x095f, B:252:0x0984, B:254:0x098c, B:256:0x0990, B:257:0x0997, B:258:0x0999, B:259:0x099f, B:261:0x09a3, B:262:0x09aa, B:263:0x09b6, B:264:0x09b0, B:267:0x09be, B:268:0x09f0, B:270:0x09f1, B:271:0x0a14, B:272:0x0a15, B:273:0x0a1e, B:275:0x0a24, B:276:0x0a4c, B:277:0x0a47, B:278:0x0a51, B:280:0x0a57, B:281:0x0a7f, B:282:0x0a7a, B:283:0x0a84, B:285:0x0a8a, B:286:0x0ab2, B:287:0x0aad, B:289:0x0ab9, B:291:0x0ac2, B:292:0x0aeb, B:294:0x0af3, B:295:0x0b1c, B:297:0x0b2e, B:298:0x0b38, B:299:0x0b5b, B:300:0x0b58, B:302:0x0b62, B:304:0x0b68, B:305:0x0b94, B:306:0x0b8b, B:307:0x0b91, B:309:0x0b9b, B:310:0x0ba0, B:313:0x0bac, B:315:0x0bcd, B:319:0x0bf2, B:321:0x0c51, B:324:0x0c62, B:326:0x0c6b, B:328:0x0c74, B:329:0x0c7b, B:331:0x0c82, B:334:0x0c90, B:336:0x0cb1, B:340:0x0cd6, B:342:0x0d35, B:345:0x0d42, B:347:0x0d4b, B:349:0x0d54, B:350:0x0d61, B:352:0x0d68, B:354:0x0d72, B:356:0x0d78, B:357:0x0da4, B:358:0x0d9b, B:359:0x0da1, B:361:0x0dab, B:365:0x0dbb, B:374:0x0dc7, B:368:0x0df6, B:370:0x0e00, B:371:0x0e0a, B:372:0x0e3b, B:376:0x0dcc, B:377:0x0e13, B:378:0x0e38, B:381:0x0e44, B:383:0x0e4d, B:384:0x0eac, B:385:0x0e72, B:387:0x0e78, B:388:0x0ea3, B:389:0x0ea9, B:390:0x0eb1, B:392:0x0eb8, B:394:0x0ec5, B:395:0x0f1d, B:397:0x0eea, B:398:0x0f0f, B:399:0x0f1a, B:401:0x0f24, B:403:0x0f2a, B:404:0x0f56, B:405:0x0f4d, B:406:0x0f53, B:408:0x0f5d, B:410:0x0f63, B:411:0x0f8f, B:412:0x0f86, B:413:0x0f8c, B:415:0x0f96, B:417:0x0f9e, B:418:0x0fcc, B:419:0x0fc3, B:420:0x0fc9, B:422:0x0fd3, B:424:0x0fdf, B:425:0x1038, B:426:0x1002, B:428:0x100c, B:431:0x1014, B:432:0x101f, B:434:0x1027, B:437:0x1031, B:439:0x1035, B:441:0x103f, B:443:0x1045, B:444:0x1071, B:445:0x1068, B:446:0x106e, B:449:0x107e, B:451:0x1088, B:452:0x10dd, B:453:0x10ab, B:455:0x10b5, B:458:0x10bd, B:459:0x10c8, B:461:0x10ce, B:464:0x10d6, B:466:0x10da, B:468:0x10e4, B:470:0x10ea, B:471:0x1116, B:472:0x110d, B:473:0x1113, B:475:0x111d, B:477:0x1123, B:478:0x114f, B:479:0x1146, B:480:0x114c, B:482:0x1156, B:484:0x115c, B:485:0x1188, B:486:0x117f, B:487:0x1185, B:489:0x118f, B:491:0x1195, B:492:0x11c1, B:493:0x11b8, B:494:0x11be, B:497:0x11ca, B:499:0x11d0, B:500:0x11fe, B:501:0x11f5, B:502:0x11fb, B:505:0x1209, B:509:0x122f, B:510:0x125d, B:512:0x1254, B:513:0x121b, B:514:0x1258, B:517:0x126a, B:519:0x128c, B:520:0x1368, B:522:0x12b2, B:550:0x12da, B:526:0x12e9, B:530:0x12f4, B:535:0x1305, B:537:0x1317, B:538:0x1321, B:539:0x131c, B:540:0x132d, B:542:0x133c, B:544:0x1346, B:546:0x1350, B:547:0x135d, B:555:0x1365, B:558:0x1371, B:559:0x1376, B:562:0x1386, B:564:0x138c, B:565:0x13b3, B:567:0x13b9, B:568:0x13e0, B:570:0x13e6, B:571:0x1417, B:573:0x1425, B:574:0x142f, B:575:0x1436, B:576:0x1433, B:579:0x1443, B:581:0x1449, B:582:0x146f, B:583:0x1477, B:586:0x1482, B:588:0x148b, B:589:0x14c0, B:590:0x1498, B:591:0x14bd, B:592:0x0457, B:594:0x0122, B:598:0x0140, B:601:0x014d, B:604:0x015b, B:607:0x0169, B:610:0x0177, B:613:0x0185, B:616:0x0196, B:619:0x01a1, B:622:0x01b0, B:625:0x01bf, B:628:0x01ce, B:631:0x01dc, B:634:0x01eb, B:637:0x01fa, B:640:0x0209, B:643:0x0218, B:646:0x0227, B:649:0x0236, B:652:0x0246, B:655:0x0256, B:658:0x0265, B:661:0x0273, B:664:0x0282, B:667:0x0291, B:670:0x02a0, B:673:0x02af, B:676:0x02bf, B:679:0x02cf, B:682:0x02db, B:685:0x02ea, B:688:0x02f9, B:691:0x0309, B:694:0x0317, B:697:0x0326, B:700:0x0335, B:703:0x0343, B:706:0x0351, B:709:0x035f, B:712:0x036d, B:715:0x037c, B:718:0x038b, B:721:0x039b, B:724:0x03aa, B:727:0x03b8, B:730:0x03c6, B:733:0x03d4, B:769:0x14ea, B:770:0x14f5), top: B:5:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:544:0x1346 A[Catch: all -> 0x0130, IOException -> 0x0134, XmlPullParserException -> 0x013a, TryCatch #3 {all -> 0x0130, blocks: (B:69:0x0430, B:71:0x0436, B:73:0x043c, B:78:0x0445, B:80:0x044b, B:82:0x0451, B:83:0x0481, B:85:0x0487, B:87:0x0491, B:88:0x04b4, B:90:0x04b6, B:94:0x14f6, B:135:0x1502, B:136:0x04c2, B:138:0x04cc, B:139:0x04f0, B:140:0x04f2, B:141:0x04f9, B:143:0x050a, B:144:0x052e, B:146:0x0534, B:147:0x055a, B:148:0x0561, B:150:0x0572, B:151:0x0596, B:153:0x059c, B:154:0x05c2, B:155:0x05c9, B:157:0x05da, B:158:0x05fe, B:160:0x0604, B:161:0x062a, B:163:0x0630, B:164:0x0632, B:165:0x0639, B:167:0x064a, B:168:0x069d, B:169:0x066d, B:171:0x0673, B:172:0x0698, B:174:0x06a4, B:176:0x06aa, B:177:0x06d8, B:178:0x06cd, B:179:0x06cf, B:180:0x06d5, B:182:0x06df, B:184:0x06e5, B:185:0x070c, B:187:0x0712, B:188:0x0739, B:193:0x0746, B:195:0x0750, B:196:0x075a, B:197:0x0792, B:198:0x075e, B:199:0x078f, B:201:0x0799, B:203:0x07a6, B:205:0x07ca, B:208:0x07f2, B:209:0x07fb, B:210:0x07f8, B:212:0x0802, B:214:0x0810, B:216:0x083a, B:218:0x083e, B:220:0x0841, B:221:0x0848, B:222:0x084c, B:223:0x0833, B:224:0x0849, B:225:0x0851, B:227:0x0857, B:228:0x087f, B:229:0x087a, B:231:0x0886, B:233:0x088c, B:234:0x08e2, B:235:0x08af, B:237:0x08b7, B:239:0x08be, B:240:0x08de, B:241:0x08df, B:242:0x08e7, B:244:0x0908, B:245:0x09b9, B:246:0x092e, B:248:0x0934, B:249:0x0959, B:251:0x095f, B:252:0x0984, B:254:0x098c, B:256:0x0990, B:257:0x0997, B:258:0x0999, B:259:0x099f, B:261:0x09a3, B:262:0x09aa, B:263:0x09b6, B:264:0x09b0, B:267:0x09be, B:268:0x09f0, B:270:0x09f1, B:271:0x0a14, B:272:0x0a15, B:273:0x0a1e, B:275:0x0a24, B:276:0x0a4c, B:277:0x0a47, B:278:0x0a51, B:280:0x0a57, B:281:0x0a7f, B:282:0x0a7a, B:283:0x0a84, B:285:0x0a8a, B:286:0x0ab2, B:287:0x0aad, B:289:0x0ab9, B:291:0x0ac2, B:292:0x0aeb, B:294:0x0af3, B:295:0x0b1c, B:297:0x0b2e, B:298:0x0b38, B:299:0x0b5b, B:300:0x0b58, B:302:0x0b62, B:304:0x0b68, B:305:0x0b94, B:306:0x0b8b, B:307:0x0b91, B:309:0x0b9b, B:310:0x0ba0, B:313:0x0bac, B:315:0x0bcd, B:319:0x0bf2, B:321:0x0c51, B:324:0x0c62, B:326:0x0c6b, B:328:0x0c74, B:329:0x0c7b, B:331:0x0c82, B:334:0x0c90, B:336:0x0cb1, B:340:0x0cd6, B:342:0x0d35, B:345:0x0d42, B:347:0x0d4b, B:349:0x0d54, B:350:0x0d61, B:352:0x0d68, B:354:0x0d72, B:356:0x0d78, B:357:0x0da4, B:358:0x0d9b, B:359:0x0da1, B:361:0x0dab, B:365:0x0dbb, B:374:0x0dc7, B:368:0x0df6, B:370:0x0e00, B:371:0x0e0a, B:372:0x0e3b, B:376:0x0dcc, B:377:0x0e13, B:378:0x0e38, B:381:0x0e44, B:383:0x0e4d, B:384:0x0eac, B:385:0x0e72, B:387:0x0e78, B:388:0x0ea3, B:389:0x0ea9, B:390:0x0eb1, B:392:0x0eb8, B:394:0x0ec5, B:395:0x0f1d, B:397:0x0eea, B:398:0x0f0f, B:399:0x0f1a, B:401:0x0f24, B:403:0x0f2a, B:404:0x0f56, B:405:0x0f4d, B:406:0x0f53, B:408:0x0f5d, B:410:0x0f63, B:411:0x0f8f, B:412:0x0f86, B:413:0x0f8c, B:415:0x0f96, B:417:0x0f9e, B:418:0x0fcc, B:419:0x0fc3, B:420:0x0fc9, B:422:0x0fd3, B:424:0x0fdf, B:425:0x1038, B:426:0x1002, B:428:0x100c, B:431:0x1014, B:432:0x101f, B:434:0x1027, B:437:0x1031, B:439:0x1035, B:441:0x103f, B:443:0x1045, B:444:0x1071, B:445:0x1068, B:446:0x106e, B:449:0x107e, B:451:0x1088, B:452:0x10dd, B:453:0x10ab, B:455:0x10b5, B:458:0x10bd, B:459:0x10c8, B:461:0x10ce, B:464:0x10d6, B:466:0x10da, B:468:0x10e4, B:470:0x10ea, B:471:0x1116, B:472:0x110d, B:473:0x1113, B:475:0x111d, B:477:0x1123, B:478:0x114f, B:479:0x1146, B:480:0x114c, B:482:0x1156, B:484:0x115c, B:485:0x1188, B:486:0x117f, B:487:0x1185, B:489:0x118f, B:491:0x1195, B:492:0x11c1, B:493:0x11b8, B:494:0x11be, B:497:0x11ca, B:499:0x11d0, B:500:0x11fe, B:501:0x11f5, B:502:0x11fb, B:505:0x1209, B:509:0x122f, B:510:0x125d, B:512:0x1254, B:513:0x121b, B:514:0x1258, B:517:0x126a, B:519:0x128c, B:520:0x1368, B:522:0x12b2, B:550:0x12da, B:526:0x12e9, B:530:0x12f4, B:535:0x1305, B:537:0x1317, B:538:0x1321, B:539:0x131c, B:540:0x132d, B:542:0x133c, B:544:0x1346, B:546:0x1350, B:547:0x135d, B:555:0x1365, B:558:0x1371, B:559:0x1376, B:562:0x1386, B:564:0x138c, B:565:0x13b3, B:567:0x13b9, B:568:0x13e0, B:570:0x13e6, B:571:0x1417, B:573:0x1425, B:574:0x142f, B:575:0x1436, B:576:0x1433, B:579:0x1443, B:581:0x1449, B:582:0x146f, B:583:0x1477, B:586:0x1482, B:588:0x148b, B:589:0x14c0, B:590:0x1498, B:591:0x14bd, B:592:0x0457, B:594:0x0122, B:598:0x0140, B:601:0x014d, B:604:0x015b, B:607:0x0169, B:610:0x0177, B:613:0x0185, B:616:0x0196, B:619:0x01a1, B:622:0x01b0, B:625:0x01bf, B:628:0x01ce, B:631:0x01dc, B:634:0x01eb, B:637:0x01fa, B:640:0x0209, B:643:0x0218, B:646:0x0227, B:649:0x0236, B:652:0x0246, B:655:0x0256, B:658:0x0265, B:661:0x0273, B:664:0x0282, B:667:0x0291, B:670:0x02a0, B:673:0x02af, B:676:0x02bf, B:679:0x02cf, B:682:0x02db, B:685:0x02ea, B:688:0x02f9, B:691:0x0309, B:694:0x0317, B:697:0x0326, B:700:0x0335, B:703:0x0343, B:706:0x0351, B:709:0x035f, B:712:0x036d, B:715:0x037c, B:718:0x038b, B:721:0x039b, B:724:0x03aa, B:727:0x03b8, B:730:0x03c6, B:733:0x03d4, B:769:0x14ea, B:770:0x14f5), top: B:5:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:546:0x1350 A[Catch: all -> 0x0130, IOException -> 0x0134, XmlPullParserException -> 0x013a, TryCatch #3 {all -> 0x0130, blocks: (B:69:0x0430, B:71:0x0436, B:73:0x043c, B:78:0x0445, B:80:0x044b, B:82:0x0451, B:83:0x0481, B:85:0x0487, B:87:0x0491, B:88:0x04b4, B:90:0x04b6, B:94:0x14f6, B:135:0x1502, B:136:0x04c2, B:138:0x04cc, B:139:0x04f0, B:140:0x04f2, B:141:0x04f9, B:143:0x050a, B:144:0x052e, B:146:0x0534, B:147:0x055a, B:148:0x0561, B:150:0x0572, B:151:0x0596, B:153:0x059c, B:154:0x05c2, B:155:0x05c9, B:157:0x05da, B:158:0x05fe, B:160:0x0604, B:161:0x062a, B:163:0x0630, B:164:0x0632, B:165:0x0639, B:167:0x064a, B:168:0x069d, B:169:0x066d, B:171:0x0673, B:172:0x0698, B:174:0x06a4, B:176:0x06aa, B:177:0x06d8, B:178:0x06cd, B:179:0x06cf, B:180:0x06d5, B:182:0x06df, B:184:0x06e5, B:185:0x070c, B:187:0x0712, B:188:0x0739, B:193:0x0746, B:195:0x0750, B:196:0x075a, B:197:0x0792, B:198:0x075e, B:199:0x078f, B:201:0x0799, B:203:0x07a6, B:205:0x07ca, B:208:0x07f2, B:209:0x07fb, B:210:0x07f8, B:212:0x0802, B:214:0x0810, B:216:0x083a, B:218:0x083e, B:220:0x0841, B:221:0x0848, B:222:0x084c, B:223:0x0833, B:224:0x0849, B:225:0x0851, B:227:0x0857, B:228:0x087f, B:229:0x087a, B:231:0x0886, B:233:0x088c, B:234:0x08e2, B:235:0x08af, B:237:0x08b7, B:239:0x08be, B:240:0x08de, B:241:0x08df, B:242:0x08e7, B:244:0x0908, B:245:0x09b9, B:246:0x092e, B:248:0x0934, B:249:0x0959, B:251:0x095f, B:252:0x0984, B:254:0x098c, B:256:0x0990, B:257:0x0997, B:258:0x0999, B:259:0x099f, B:261:0x09a3, B:262:0x09aa, B:263:0x09b6, B:264:0x09b0, B:267:0x09be, B:268:0x09f0, B:270:0x09f1, B:271:0x0a14, B:272:0x0a15, B:273:0x0a1e, B:275:0x0a24, B:276:0x0a4c, B:277:0x0a47, B:278:0x0a51, B:280:0x0a57, B:281:0x0a7f, B:282:0x0a7a, B:283:0x0a84, B:285:0x0a8a, B:286:0x0ab2, B:287:0x0aad, B:289:0x0ab9, B:291:0x0ac2, B:292:0x0aeb, B:294:0x0af3, B:295:0x0b1c, B:297:0x0b2e, B:298:0x0b38, B:299:0x0b5b, B:300:0x0b58, B:302:0x0b62, B:304:0x0b68, B:305:0x0b94, B:306:0x0b8b, B:307:0x0b91, B:309:0x0b9b, B:310:0x0ba0, B:313:0x0bac, B:315:0x0bcd, B:319:0x0bf2, B:321:0x0c51, B:324:0x0c62, B:326:0x0c6b, B:328:0x0c74, B:329:0x0c7b, B:331:0x0c82, B:334:0x0c90, B:336:0x0cb1, B:340:0x0cd6, B:342:0x0d35, B:345:0x0d42, B:347:0x0d4b, B:349:0x0d54, B:350:0x0d61, B:352:0x0d68, B:354:0x0d72, B:356:0x0d78, B:357:0x0da4, B:358:0x0d9b, B:359:0x0da1, B:361:0x0dab, B:365:0x0dbb, B:374:0x0dc7, B:368:0x0df6, B:370:0x0e00, B:371:0x0e0a, B:372:0x0e3b, B:376:0x0dcc, B:377:0x0e13, B:378:0x0e38, B:381:0x0e44, B:383:0x0e4d, B:384:0x0eac, B:385:0x0e72, B:387:0x0e78, B:388:0x0ea3, B:389:0x0ea9, B:390:0x0eb1, B:392:0x0eb8, B:394:0x0ec5, B:395:0x0f1d, B:397:0x0eea, B:398:0x0f0f, B:399:0x0f1a, B:401:0x0f24, B:403:0x0f2a, B:404:0x0f56, B:405:0x0f4d, B:406:0x0f53, B:408:0x0f5d, B:410:0x0f63, B:411:0x0f8f, B:412:0x0f86, B:413:0x0f8c, B:415:0x0f96, B:417:0x0f9e, B:418:0x0fcc, B:419:0x0fc3, B:420:0x0fc9, B:422:0x0fd3, B:424:0x0fdf, B:425:0x1038, B:426:0x1002, B:428:0x100c, B:431:0x1014, B:432:0x101f, B:434:0x1027, B:437:0x1031, B:439:0x1035, B:441:0x103f, B:443:0x1045, B:444:0x1071, B:445:0x1068, B:446:0x106e, B:449:0x107e, B:451:0x1088, B:452:0x10dd, B:453:0x10ab, B:455:0x10b5, B:458:0x10bd, B:459:0x10c8, B:461:0x10ce, B:464:0x10d6, B:466:0x10da, B:468:0x10e4, B:470:0x10ea, B:471:0x1116, B:472:0x110d, B:473:0x1113, B:475:0x111d, B:477:0x1123, B:478:0x114f, B:479:0x1146, B:480:0x114c, B:482:0x1156, B:484:0x115c, B:485:0x1188, B:486:0x117f, B:487:0x1185, B:489:0x118f, B:491:0x1195, B:492:0x11c1, B:493:0x11b8, B:494:0x11be, B:497:0x11ca, B:499:0x11d0, B:500:0x11fe, B:501:0x11f5, B:502:0x11fb, B:505:0x1209, B:509:0x122f, B:510:0x125d, B:512:0x1254, B:513:0x121b, B:514:0x1258, B:517:0x126a, B:519:0x128c, B:520:0x1368, B:522:0x12b2, B:550:0x12da, B:526:0x12e9, B:530:0x12f4, B:535:0x1305, B:537:0x1317, B:538:0x1321, B:539:0x131c, B:540:0x132d, B:542:0x133c, B:544:0x1346, B:546:0x1350, B:547:0x135d, B:555:0x1365, B:558:0x1371, B:559:0x1376, B:562:0x1386, B:564:0x138c, B:565:0x13b3, B:567:0x13b9, B:568:0x13e0, B:570:0x13e6, B:571:0x1417, B:573:0x1425, B:574:0x142f, B:575:0x1436, B:576:0x1433, B:579:0x1443, B:581:0x1449, B:582:0x146f, B:583:0x1477, B:586:0x1482, B:588:0x148b, B:589:0x14c0, B:590:0x1498, B:591:0x14bd, B:592:0x0457, B:594:0x0122, B:598:0x0140, B:601:0x014d, B:604:0x015b, B:607:0x0169, B:610:0x0177, B:613:0x0185, B:616:0x0196, B:619:0x01a1, B:622:0x01b0, B:625:0x01bf, B:628:0x01ce, B:631:0x01dc, B:634:0x01eb, B:637:0x01fa, B:640:0x0209, B:643:0x0218, B:646:0x0227, B:649:0x0236, B:652:0x0246, B:655:0x0256, B:658:0x0265, B:661:0x0273, B:664:0x0282, B:667:0x0291, B:670:0x02a0, B:673:0x02af, B:676:0x02bf, B:679:0x02cf, B:682:0x02db, B:685:0x02ea, B:688:0x02f9, B:691:0x0309, B:694:0x0317, B:697:0x0326, B:700:0x0335, B:703:0x0343, B:706:0x0351, B:709:0x035f, B:712:0x036d, B:715:0x037c, B:718:0x038b, B:721:0x039b, B:724:0x03aa, B:727:0x03b8, B:730:0x03c6, B:733:0x03d4, B:769:0x14ea, B:770:0x14f5), top: B:5:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x1513  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readPermissionsFromXml(
            org.xmlpull.v1.XmlPullParser r43, java.io.File r44, int r45) {
        /*
            Method dump skipped, instructions count: 5842
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.SystemConfig.readPermissionsFromXml(org.xmlpull.v1.XmlPullParser,"
                    + " java.io.File, int):void");
    }

    public final void readPublicLibrariesListFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        return;
                    } else if (!readLine.isEmpty() && !readLine.startsWith("#")) {
                        String str = readLine.trim().split(" ")[0];
                        SharedLibraryEntry sharedLibraryEntry =
                                new SharedLibraryEntry(str, str, new String[0], true);
                        this.mSharedLibraries.put(sharedLibraryEntry.name, sharedLibraryEntry);
                    }
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        } catch (FileNotFoundException unused) {
            Slog.d("SystemConfig", file + " does not exist");
        } catch (IOException e) {
            Slog.w("SystemConfig", "Failed to read public libraries file " + file, e);
        }
    }

    public final void readPublicNativeLibrariesList() {
        readPublicLibrariesListFile(new File("/vendor/etc/public.libraries.txt"));
        String[] strArr = {"/system/etc", "/system_ext/etc", "/product/etc"};
        for (int i = 0; i < 3; i++) {
            String str = strArr[i];
            File[] listFiles = new File(str).listFiles();
            if (listFiles == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Public libraries file folder missing: ", str, "SystemConfig");
            } else {
                for (File file : listFiles) {
                    String name = file.getName();
                    if (name.startsWith("public.libraries-") && name.endsWith(".txt")) {
                        readPublicLibrariesListFile(file);
                    }
                }
            }
        }
    }

    public final void readSplitPermission(XmlPullParser xmlPullParser, File file) {
        int parseInt;
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue == null) {
            Slog.w(
                    "SystemConfig",
                    "<split-permission> without name in "
                            + file
                            + " at "
                            + xmlPullParser.getPositionDescription());
            XmlUtils.skipCurrentTag(xmlPullParser);
            return;
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "targetSdk");
        if (TextUtils.isEmpty(attributeValue2)) {
            parseInt = 10001;
        } else {
            try {
                parseInt = Integer.parseInt(attributeValue2);
            } catch (NumberFormatException unused) {
                Slog.w(
                        "SystemConfig",
                        "<split-permission> targetSdk not an integer in "
                                + file
                                + " at "
                                + xmlPullParser.getPositionDescription());
                XmlUtils.skipCurrentTag(xmlPullParser);
                return;
            }
        }
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("new-permission".equals(xmlPullParser.getName())) {
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(attributeValue3)) {
                    Slog.w(
                            "SystemConfig",
                            "name is required for <new-permission> in "
                                    + xmlPullParser.getPositionDescription());
                } else {
                    arrayList.add(attributeValue3);
                }
            } else {
                XmlUtils.skipCurrentTag(xmlPullParser);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mSplitPermissions.add(
                new PermissionManager.SplitPermissionInfo(attributeValue, arrayList, parseInt));
    }
}
