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
