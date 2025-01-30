package com.android.server;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.FeatureInfo;
import android.os.Build;
import android.os.CarrierAssociatedAppEntry;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Process;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.VintfRuntimeInfo;
import android.os.incremental.IncrementalManager;
import android.os.storage.StorageManager;
import android.permission.PermissionManager;
import android.sysprop.ApexProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimingsTraceLog;
import android.util.Xml;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.build.UnboundedSdkLevel;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.permission.PermissionAllowlist;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SystemConfig {
    public static final ArrayMap EMPTY_PERMISSIONS = new ArrayMap();
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
    public ArrayMap mPackageToUserTypeWhitelist = new ArrayMap();
    public ArrayMap mPackageToUserTypeBlacklist = new ArrayMap();
    public final ArraySet mRollbackWhitelistedPackages = new ArraySet();
    public final ArraySet mAutomaticRollbackDenylistedPackages = new ArraySet();
    public final ArraySet mWhitelistedStagedInstallers = new ArraySet();
    public final ArrayMap mAllowedVendorApexes = new ArrayMap();
    public final Set mInstallConstraintsAllowlist = new ArraySet();
    public final ArrayMap mUpdateOwnersForSystemApps = new ArrayMap();
    public final SparseArray mDataUsageSystemUidPackages = new SparseArray();
    public final Set mInitialNonStoppedSystemPackages = new ArraySet();
    public final Set mRequiredSystemPackages = new ArraySet();
    public final ArrayMap mAppMetadataFilePaths = new ArrayMap();
    public Map mNamedActors = null;
    public boolean mAerSupported = false;

    public static boolean isAtLeastSdkLevel(String str) {
        try {
            return UnboundedSdkLevel.isAtLeast(str);
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public static boolean isAtMostSdkLevel(String str) {
        try {
            return UnboundedSdkLevel.isAtMost(str);
        } catch (IllegalArgumentException unused) {
            return true;
        }
    }

    public final class SharedLibraryEntry {
        public final boolean canBeSafelyIgnored;
        public final String[] dependencies;
        public final String filename;
        public final boolean isNative;
        public final String name;
        public final String onBootclasspathBefore;
        public final String onBootclasspathSince;

        public SharedLibraryEntry(String str, String str2, String[] strArr, boolean z) {
            this(str, str2, strArr, null, null, z);
        }

        public SharedLibraryEntry(String str, String str2, String[] strArr, String str3, String str4) {
            this(str, str2, strArr, str3, str4, false);
        }

        public SharedLibraryEntry(String str, String str2, String[] strArr, String str3, String str4, boolean z) {
            this.name = str;
            this.filename = str2;
            this.dependencies = strArr;
            this.onBootclasspathSince = str3;
            this.onBootclasspathBefore = str4;
            this.isNative = z;
            this.canBeSafelyIgnored = (str3 != null && SystemConfig.isAtLeastSdkLevel(str3)) || !(str4 == null || SystemConfig.isAtLeastSdkLevel(str4));
        }
    }

    public final class PermissionEntry {
        public int[] gids;
        public final String name;
        public boolean perUser;

        public PermissionEntry(String str, boolean z) {
            this.name = str;
            this.perUser = z;
        }
    }

    public static SystemConfig getInstance() {
        SystemConfig systemConfig;
        if (!isSystemProcess()) {
            Slog.wtf("SystemConfig", "SystemConfig is being accessed by a process other than system_server.");
        }
        synchronized (SystemConfig.class) {
            if (sInstance == null) {
                sInstance = new SystemConfig();
            }
            systemConfig = sInstance;
        }
        return systemConfig;
    }

    public int[] getGlobalGids() {
        return this.mGlobalGids;
    }

    public SparseArray getSystemPermissions() {
        return this.mSystemPermissions;
    }

    public ArrayList getSplitPermissions() {
        return this.mSplitPermissions;
    }

    public ArrayMap getSharedLibraries() {
        return this.mSharedLibraries;
    }

    public ArrayMap getAvailableFeatures() {
        return this.mAvailableFeatures;
    }

    public ArrayMap getPermissions() {
        return this.mPermissions;
    }

    public ArraySet getAllowImplicitBroadcasts() {
        return this.mAllowImplicitBroadcasts;
    }

    public ArraySet getAllowInPowerSaveExceptIdle() {
        return this.mAllowInPowerSaveExceptIdle;
    }

    public ArraySet getAllowInPowerSave() {
        return this.mAllowInPowerSave;
    }

    public ArraySet getAllowInDataUsageSave() {
        return this.mAllowInDataUsageSave;
    }

    public ArraySet getAllowUnthrottledLocation() {
        return this.mAllowUnthrottledLocation;
    }

    public ArrayMap getAllowAdasLocationSettings() {
        return this.mAllowAdasSettings;
    }

    public ArrayMap getAllowIgnoreLocationSettings() {
        return this.mAllowIgnoreLocationSettings;
    }

    public ArraySet getBgRestrictionExemption() {
        return this.mBgRestrictionExemption;
    }

    public ArraySet getLinkedApps() {
        return this.mLinkedApps;
    }

    public ArraySet getHiddenApiWhitelistedApps() {
        return this.mHiddenApiPackageWhitelist;
    }

    public ArraySet getDefaultVrComponents() {
        return this.mDefaultVrComponents;
    }

    public ArraySet getBackupTransportWhitelist() {
        return this.mBackupTransportWhitelist;
    }

    public ArrayMap getComponentsEnabledStates(String str) {
        return (ArrayMap) this.mPackageComponentEnabledState.get(str);
    }

    public ArraySet getDisabledUntilUsedPreinstalledCarrierApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierApps;
    }

    public ArrayMap getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps;
    }

    public PermissionAllowlist getPermissionAllowlist() {
        return this.mPermissionAllowlist;
    }

    public ArrayMap getAllowedAssociations() {
        return this.mAllowedAssociations;
    }

    public ArraySet getBugreportWhitelistedPackages() {
        return this.mBugreportWhitelistedPackages;
    }

    public Set getRollbackWhitelistedPackages() {
        return this.mRollbackWhitelistedPackages;
    }

    public Set getAutomaticRollbackDenylistedPackages() {
        return this.mAutomaticRollbackDenylistedPackages;
    }

    public Set getWhitelistedStagedInstallers() {
        return this.mWhitelistedStagedInstallers;
    }

    public Map getAllowedVendorApexes() {
        return this.mAllowedVendorApexes;
    }

    public Set getInstallConstraintsAllowlist() {
        return this.mInstallConstraintsAllowlist;
    }

    public String getModulesInstallerPackageName() {
        return this.mModulesInstallerPackageName;
    }

    public String getSystemAppUpdateOwnerPackageName(String str) {
        return (String) this.mUpdateOwnersForSystemApps.get(str);
    }

    public ArraySet getAppDataIsolationWhitelistedApps() {
        return this.mAppDataIsolationWhitelistedApps;
    }

    public ArrayMap getAndClearPackageToUserTypeWhitelist() {
        ArrayMap arrayMap = this.mPackageToUserTypeWhitelist;
        this.mPackageToUserTypeWhitelist = new ArrayMap(0);
        return arrayMap;
    }

    public ArrayMap getAndClearPackageToUserTypeBlacklist() {
        ArrayMap arrayMap = this.mPackageToUserTypeBlacklist;
        this.mPackageToUserTypeBlacklist = new ArrayMap(0);
        return arrayMap;
    }

    public Map getNamedActors() {
        Map map = this.mNamedActors;
        return map != null ? map : Collections.emptyMap();
    }

    public String getOverlayConfigSignaturePackage() {
        if (TextUtils.isEmpty(this.mOverlayConfigSignaturePackage)) {
            return null;
        }
        return this.mOverlayConfigSignaturePackage;
    }

    public Set getInitialNonStoppedSystemPackages() {
        return this.mInitialNonStoppedSystemPackages;
    }

    public Set getRequiredSystemPackages() {
        return this.mRequiredSystemPackages;
    }

    public ArrayMap getAppMetadataFilePaths() {
        return this.mAppMetadataFilePaths;
    }

    public SystemConfig(boolean z) {
        if (z) {
            Slog.w("SystemConfig", "Constructing a test SystemConfig");
            readAllPermissions();
        } else {
            Slog.w("SystemConfig", "Constructing an empty test SystemConfig");
        }
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

    public final void readAllPermissions() {
        String str;
        XmlPullParser newPullParser = Xml.newPullParser();
        readPermissions(newPullParser, Environment.buildPath(Environment.getRootDirectory(), new String[]{"etc", "sysconfig"}), -1);
        readPermissions(newPullParser, Environment.buildPath(Environment.getRootDirectory(), new String[]{"etc", "permissions"}), -1);
        int i = Build.VERSION.DEVICE_INITIAL_SDK_INT <= 27 ? 1183 : 1171;
        readPermissions(newPullParser, Environment.buildPath(Environment.getVendorDirectory(), new String[]{"etc", "sysconfig"}), i);
        readPermissions(newPullParser, Environment.buildPath(Environment.getVendorDirectory(), new String[]{"etc", "permissions"}), i);
        String str2 = SystemProperties.get("ro.boot.product.vendor.sku", "");
        if (!str2.isEmpty()) {
            String str3 = "sku_" + str2;
            readPermissions(newPullParser, Environment.buildPath(Environment.getVendorDirectory(), new String[]{"etc", "sysconfig", str3}), i);
            readPermissions(newPullParser, Environment.buildPath(Environment.getVendorDirectory(), new String[]{"etc", "permissions", str3}), i);
        }
        readPermissions(newPullParser, Environment.buildPath(Environment.getOdmDirectory(), new String[]{"etc", "sysconfig"}), i);
        readPermissions(newPullParser, Environment.buildPath(Environment.getOdmDirectory(), new String[]{"etc", "permissions"}), i);
        String str4 = SystemProperties.get("ro.boot.product.hardware.sku", "");
        if (!str4.isEmpty()) {
            String str5 = "sku_" + str4;
            readPermissions(newPullParser, Environment.buildPath(Environment.getOdmDirectory(), new String[]{"etc", "sysconfig", str5}), i);
            readPermissions(newPullParser, Environment.buildPath(Environment.getOdmDirectory(), new String[]{"etc", "permissions", str5}), i);
        }
        readPermissions(newPullParser, Environment.buildPath(Environment.getOemDirectory(), new String[]{"etc", "sysconfig"}), 1185);
        readPermissions(newPullParser, Environment.buildPath(Environment.getOemDirectory(), new String[]{"etc", "permissions"}), 1185);
        int i2 = Build.VERSION.DEVICE_INITIAL_SDK_INT <= 30 ? -1 : 2015;
        readAerSupportedFromXml(-1);
        readPermissions(newPullParser, Environment.buildPath(Environment.getProductDirectory(), new String[]{"etc", "sysconfig"}), i2);
        readPermissions(newPullParser, Environment.buildPath(Environment.getProductDirectory(), new String[]{"etc", "permissions"}), i2);
        readPermissions(newPullParser, Environment.buildPath(Environment.getSystemExtDirectory(), new String[]{"etc", "sysconfig"}), -1);
        readPermissions(newPullParser, Environment.buildPath(Environment.getSystemExtDirectory(), new String[]{"etc", "permissions"}), -1);
        String str6 = SystemProperties.get("ro.csc.sales_code");
        if (!TextUtils.isEmpty(str6)) {
            readPermissions(newPullParser, Environment.buildPath(Environment.getVendorDirectory(), new String[]{"etc", "carrier", str6, "permissions"}), 1);
        }
        readPermissions(newPullParser, Environment.buildPath(Environment.getRootDirectory(), new String[]{"carrier", "sysconfig"}), -1);
        readPermissions(newPullParser, Environment.buildPath(Environment.getRootDirectory(), new String[]{"carrier", "permissions"}), -1);
        String str7 = SystemProperties.get("mdc.sys.omc_etcpath", "");
        if (!TextUtils.isEmpty(str7)) {
            File file = new File(str7);
            readPermissions(newPullParser, Environment.buildPath(file, new String[]{"sysconfig"}), -1);
            readPermissions(newPullParser, Environment.buildPath(file, new String[]{"permissions"}), -1);
        }
        String str8 = Build.PRODUCT;
        if (!TextUtils.isEmpty(str8) && str8.contains("eea")) {
            if (TextUtils.isEmpty(SystemProperties.get("ro.boot.carrierid"))) {
                str = SystemProperties.get("mdc.sys.omc_etcpath", "");
            } else {
                str = SystemProperties.get("mdc.sys.carrierid_etcpath", "");
            }
            Slog.i("SystemConfig", "omcEtcPathCid " + str);
            if (!TextUtils.isEmpty(str)) {
                File file2 = new File(str);
                readPermissions(newPullParser, Environment.buildPath(file2, new String[]{"cid/sysconfig"}), -1);
                readPermissions(newPullParser, Environment.buildPath(file2, new String[]{"cid/permissions"}), -1);
            } else {
                readPermissions(newPullParser, Environment.buildPath(Environment.getRootDirectory(), new String[]{"etc", "cid/sysconfig"}), -1);
                readPermissions(newPullParser, Environment.buildPath(Environment.getRootDirectory(), new String[]{"etc", "cid/permissions"}), -1);
            }
        }
        SEServiceFeature();
        if (isSystemProcess()) {
            for (File file3 : FileUtils.listFilesOrEmpty(Environment.getApexDirectory())) {
                if (!file3.isFile() && !file3.getPath().contains("@")) {
                    readPermissions(newPullParser, Environment.buildPath(file3, new String[]{"etc", "permissions"}), 19);
                }
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
                    Slog.i("SystemConfig", "Non-xml file " + file3 + " in " + file + " directory, ignoring");
                } else if (!file3.canRead()) {
                    Slog.w("SystemConfig", "Permissions library file " + file3 + " cannot be read");
                } else if (!this.mAerSupported && file3.getPath().contains("aer")) {
                    Slog.e("SystemConfig", "aer = " + this.mAerSupported + ", f.getPath().contains = " + file3.getPath().contains("aer"));
                } else {
                    readPermissionsFromXml(xmlPullParser, file3, i);
                }
            }
        }
        if (file2 != null) {
            readPermissionsFromXml(xmlPullParser, file2, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00b1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0087 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readAerSupportedFromXml(int i) {
        XmlPullParser newPullParser;
        int next;
        char c;
        File buildPath = Environment.buildPath(Environment.getRootDirectory(), new String[]{"etc", "aer_product_name.xml"});
        try {
            FileReader fileReader = new FileReader(buildPath);
            Slog.i("SystemConfig", "Reading aerProductName from " + buildPath);
            boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
            try {
                try {
                    newPullParser = Xml.newPullParser();
                    newPullParser.setInput(fileReader);
                    do {
                        next = newPullParser.next();
                        if (next == 2) {
                            break;
                        }
                    } while (next != 1);
                } catch (IOException e) {
                    Slog.w("SystemConfig", "Got exception parsing permissions.", e);
                } catch (XmlPullParserException e2) {
                    Slog.w("SystemConfig", "Got exception parsing permissions.", e2);
                }
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                if (!newPullParser.getName().equals("device")) {
                    throw new XmlPullParserException("Unexpected start tag in " + buildPath + ": found " + newPullParser.getName() + ", expected 'product'");
                }
                boolean z = (i & 1) != 0;
                while (true) {
                    XmlUtils.nextElement(newPullParser);
                    if (newPullParser.getEventType() == 1) {
                        break;
                    }
                    String name = newPullParser.getName();
                    if (name == null) {
                        XmlUtils.skipCurrentTag(newPullParser);
                    } else {
                        if (name.hashCode() == -309474065 && name.equals("product")) {
                            c = 0;
                            if (c != 0) {
                                if (z) {
                                    String attributeValue = newPullParser.getAttributeValue(null, "name");
                                    boolean z2 = !isLowRamDeviceStatic ? true : !"true".equals(newPullParser.getAttributeValue(null, "notLowRam"));
                                    if (attributeValue == null) {
                                        Slog.w("SystemConfig", "<" + name + "> without name in " + buildPath + " at " + newPullParser.getPositionDescription());
                                    } else if (z2 && attributeValue.equals(SystemProperties.get("ro.product.name"))) {
                                        this.mAerSupported = true;
                                    }
                                }
                                XmlUtils.skipCurrentTag(newPullParser);
                            } else {
                                Slog.w("SystemConfig", "Tag " + name + " is unknown in " + buildPath + " at " + newPullParser.getPositionDescription());
                                XmlUtils.skipCurrentTag(newPullParser);
                            }
                        }
                        c = 65535;
                        if (c != 0) {
                        }
                    }
                }
            } finally {
                IoUtils.closeQuietly(fileReader);
            }
        } catch (FileNotFoundException unused) {
            Slog.w("SystemConfig", "Couldn't find or open aerProductName file " + buildPath);
        }
    }

    public final void logNotAllowedInPartition(String str, File file, XmlPullParser xmlPullParser) {
        Slog.w("SystemConfig", "<" + str + "> not allowed in partition of " + file + " at " + xmlPullParser.getPositionDescription());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Not initialized variable reg: 25, insn: 0x11fa: MOVE (r3 I:??[OBJECT, ARRAY]) = (r25 I:??[OBJECT, ARRAY]), block:B:676:0x11f9 */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0a54 A[Catch: IOException -> 0x11f6, XmlPullParserException -> 0x11f8, all -> 0x12aa, TryCatch #8 {all -> 0x12aa, blocks: (B:66:0x11b0, B:70:0x0320, B:72:0x032a, B:74:0x034e, B:75:0x0355, B:77:0x0366, B:78:0x038c, B:80:0x0392, B:81:0x03b8, B:82:0x03bf, B:84:0x03d0, B:85:0x03f4, B:87:0x03fa, B:88:0x0420, B:90:0x0426, B:91:0x042d, B:93:0x043d, B:94:0x0492, B:95:0x0462, B:97:0x0468, B:98:0x048d, B:100:0x0499, B:102:0x049f, B:103:0x04cb, B:104:0x04c2, B:105:0x04c8, B:107:0x04d2, B:109:0x04d8, B:110:0x04ff, B:112:0x0508, B:113:0x0531, B:118:0x053e, B:120:0x0548, B:121:0x0552, B:122:0x058a, B:123:0x0556, B:124:0x0587, B:126:0x0591, B:128:0x059d, B:130:0x05c1, B:133:0x05e9, B:134:0x05f2, B:135:0x05ef, B:137:0x05f9, B:139:0x0606, B:141:0x0630, B:143:0x0634, B:145:0x0637, B:146:0x063e, B:148:0x0642, B:149:0x0629, B:150:0x063f, B:151:0x0647, B:153:0x064d, B:154:0x0675, B:155:0x0670, B:156:0x067a, B:158:0x0680, B:159:0x06a8, B:160:0x06a3, B:162:0x06af, B:164:0x06b5, B:165:0x070b, B:166:0x06d8, B:168:0x06e0, B:170:0x06e7, B:171:0x0707, B:172:0x0708, B:173:0x0710, B:175:0x072d, B:176:0x07da, B:177:0x0753, B:179:0x0759, B:180:0x077e, B:182:0x0784, B:183:0x07a9, B:185:0x07b1, B:187:0x07b5, B:188:0x07bc, B:190:0x07c6, B:191:0x07d7, B:192:0x07d1, B:195:0x07df, B:196:0x0811, B:198:0x0812, B:199:0x0835, B:200:0x0836, B:201:0x083f, B:203:0x0845, B:204:0x086d, B:205:0x0868, B:206:0x0872, B:208:0x0878, B:209:0x08a0, B:210:0x089b, B:212:0x08a7, B:214:0x08b0, B:215:0x08d9, B:217:0x08e1, B:218:0x090a, B:220:0x091c, B:221:0x0926, B:222:0x0949, B:223:0x0946, B:225:0x0950, B:227:0x0956, B:228:0x0982, B:229:0x0979, B:230:0x097f, B:232:0x0989, B:233:0x098e, B:235:0x0998, B:237:0x09b9, B:241:0x09de, B:243:0x0a3d, B:247:0x0a54, B:249:0x0a61, B:251:0x0a6e, B:253:0x0a7b, B:254:0x0a88, B:257:0x0a93, B:259:0x0a9d, B:261:0x0aa3, B:262:0x0acf, B:263:0x0ac6, B:264:0x0acc, B:266:0x0ad6, B:270:0x0ae5, B:279:0x0af1, B:273:0x0b20, B:275:0x0b2a, B:276:0x0b34, B:277:0x0b65, B:281:0x0af6, B:282:0x0b3d, B:283:0x0b62, B:285:0x0b6c, B:287:0x0b75, B:288:0x0bd6, B:289:0x0b9a, B:291:0x0ba0, B:292:0x0bcd, B:293:0x0bd3, B:294:0x0bda, B:296:0x0be3, B:298:0x0bef, B:299:0x0c47, B:301:0x0c14, B:302:0x0c39, B:303:0x0c44, B:305:0x0c4d, B:307:0x0c53, B:308:0x0c7f, B:309:0x0c76, B:310:0x0c7c, B:312:0x0c86, B:314:0x0c8c, B:315:0x0cb8, B:316:0x0caf, B:317:0x0cb5, B:319:0x0cbf, B:321:0x0cc7, B:322:0x0cf5, B:323:0x0cec, B:324:0x0cf2, B:326:0x0cfc, B:328:0x0d08, B:329:0x0d62, B:330:0x0d2b, B:332:0x0d35, B:335:0x0d3d, B:336:0x0d48, B:338:0x0d50, B:341:0x0d5b, B:343:0x0d5f, B:345:0x0d69, B:347:0x0d75, B:348:0x0dcf, B:349:0x0d98, B:351:0x0da2, B:354:0x0daa, B:355:0x0db5, B:357:0x0dbd, B:360:0x0dc8, B:362:0x0dcc, B:364:0x0dd6, B:366:0x0ddc, B:367:0x0e08, B:368:0x0dff, B:369:0x0e05, B:371:0x0e0f, B:373:0x0e15, B:374:0x0e41, B:375:0x0e38, B:376:0x0e3e, B:378:0x0e48, B:380:0x0e4e, B:381:0x0e7a, B:382:0x0e71, B:383:0x0e77, B:385:0x0e81, B:387:0x0e87, B:388:0x0eb3, B:389:0x0eaa, B:390:0x0eb0, B:392:0x0eba, B:394:0x0ec0, B:395:0x0eee, B:396:0x0ee5, B:397:0x0eeb, B:400:0x0ef7, B:404:0x0f1d, B:405:0x0f4a, B:407:0x0f42, B:408:0x0f09, B:409:0x0f46, B:412:0x0f54, B:414:0x0f76, B:415:0x1052, B:417:0x0f9c, B:419:0x0fc4, B:424:0x0fd0, B:428:0x0fda, B:432:0x0fe9, B:434:0x0ffb, B:435:0x1007, B:436:0x1000, B:437:0x1013, B:439:0x1024, B:441:0x102e, B:443:0x1038, B:444:0x1045, B:447:0x104d, B:450:0x105b, B:451:0x1060, B:454:0x106e, B:456:0x1074, B:457:0x109b, B:459:0x10a4, B:460:0x10cd, B:462:0x10d3, B:463:0x1104, B:465:0x1112, B:466:0x111c, B:467:0x1123, B:468:0x1120, B:471:0x112e, B:473:0x1134, B:474:0x115b, B:475:0x1164, B:478:0x116f, B:480:0x1177, B:481:0x11ac, B:482:0x1184, B:483:0x11a9, B:662:0x1211, B:666:0x121a, B:671:0x11ea, B:672:0x11f5), top: B:5:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0a5f  */
    /* JADX WARN: Removed duplicated region for block: B:430:0x0fe5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:439:0x1024 A[Catch: IOException -> 0x11f6, XmlPullParserException -> 0x11f8, all -> 0x12aa, TryCatch #8 {all -> 0x12aa, blocks: (B:66:0x11b0, B:70:0x0320, B:72:0x032a, B:74:0x034e, B:75:0x0355, B:77:0x0366, B:78:0x038c, B:80:0x0392, B:81:0x03b8, B:82:0x03bf, B:84:0x03d0, B:85:0x03f4, B:87:0x03fa, B:88:0x0420, B:90:0x0426, B:91:0x042d, B:93:0x043d, B:94:0x0492, B:95:0x0462, B:97:0x0468, B:98:0x048d, B:100:0x0499, B:102:0x049f, B:103:0x04cb, B:104:0x04c2, B:105:0x04c8, B:107:0x04d2, B:109:0x04d8, B:110:0x04ff, B:112:0x0508, B:113:0x0531, B:118:0x053e, B:120:0x0548, B:121:0x0552, B:122:0x058a, B:123:0x0556, B:124:0x0587, B:126:0x0591, B:128:0x059d, B:130:0x05c1, B:133:0x05e9, B:134:0x05f2, B:135:0x05ef, B:137:0x05f9, B:139:0x0606, B:141:0x0630, B:143:0x0634, B:145:0x0637, B:146:0x063e, B:148:0x0642, B:149:0x0629, B:150:0x063f, B:151:0x0647, B:153:0x064d, B:154:0x0675, B:155:0x0670, B:156:0x067a, B:158:0x0680, B:159:0x06a8, B:160:0x06a3, B:162:0x06af, B:164:0x06b5, B:165:0x070b, B:166:0x06d8, B:168:0x06e0, B:170:0x06e7, B:171:0x0707, B:172:0x0708, B:173:0x0710, B:175:0x072d, B:176:0x07da, B:177:0x0753, B:179:0x0759, B:180:0x077e, B:182:0x0784, B:183:0x07a9, B:185:0x07b1, B:187:0x07b5, B:188:0x07bc, B:190:0x07c6, B:191:0x07d7, B:192:0x07d1, B:195:0x07df, B:196:0x0811, B:198:0x0812, B:199:0x0835, B:200:0x0836, B:201:0x083f, B:203:0x0845, B:204:0x086d, B:205:0x0868, B:206:0x0872, B:208:0x0878, B:209:0x08a0, B:210:0x089b, B:212:0x08a7, B:214:0x08b0, B:215:0x08d9, B:217:0x08e1, B:218:0x090a, B:220:0x091c, B:221:0x0926, B:222:0x0949, B:223:0x0946, B:225:0x0950, B:227:0x0956, B:228:0x0982, B:229:0x0979, B:230:0x097f, B:232:0x0989, B:233:0x098e, B:235:0x0998, B:237:0x09b9, B:241:0x09de, B:243:0x0a3d, B:247:0x0a54, B:249:0x0a61, B:251:0x0a6e, B:253:0x0a7b, B:254:0x0a88, B:257:0x0a93, B:259:0x0a9d, B:261:0x0aa3, B:262:0x0acf, B:263:0x0ac6, B:264:0x0acc, B:266:0x0ad6, B:270:0x0ae5, B:279:0x0af1, B:273:0x0b20, B:275:0x0b2a, B:276:0x0b34, B:277:0x0b65, B:281:0x0af6, B:282:0x0b3d, B:283:0x0b62, B:285:0x0b6c, B:287:0x0b75, B:288:0x0bd6, B:289:0x0b9a, B:291:0x0ba0, B:292:0x0bcd, B:293:0x0bd3, B:294:0x0bda, B:296:0x0be3, B:298:0x0bef, B:299:0x0c47, B:301:0x0c14, B:302:0x0c39, B:303:0x0c44, B:305:0x0c4d, B:307:0x0c53, B:308:0x0c7f, B:309:0x0c76, B:310:0x0c7c, B:312:0x0c86, B:314:0x0c8c, B:315:0x0cb8, B:316:0x0caf, B:317:0x0cb5, B:319:0x0cbf, B:321:0x0cc7, B:322:0x0cf5, B:323:0x0cec, B:324:0x0cf2, B:326:0x0cfc, B:328:0x0d08, B:329:0x0d62, B:330:0x0d2b, B:332:0x0d35, B:335:0x0d3d, B:336:0x0d48, B:338:0x0d50, B:341:0x0d5b, B:343:0x0d5f, B:345:0x0d69, B:347:0x0d75, B:348:0x0dcf, B:349:0x0d98, B:351:0x0da2, B:354:0x0daa, B:355:0x0db5, B:357:0x0dbd, B:360:0x0dc8, B:362:0x0dcc, B:364:0x0dd6, B:366:0x0ddc, B:367:0x0e08, B:368:0x0dff, B:369:0x0e05, B:371:0x0e0f, B:373:0x0e15, B:374:0x0e41, B:375:0x0e38, B:376:0x0e3e, B:378:0x0e48, B:380:0x0e4e, B:381:0x0e7a, B:382:0x0e71, B:383:0x0e77, B:385:0x0e81, B:387:0x0e87, B:388:0x0eb3, B:389:0x0eaa, B:390:0x0eb0, B:392:0x0eba, B:394:0x0ec0, B:395:0x0eee, B:396:0x0ee5, B:397:0x0eeb, B:400:0x0ef7, B:404:0x0f1d, B:405:0x0f4a, B:407:0x0f42, B:408:0x0f09, B:409:0x0f46, B:412:0x0f54, B:414:0x0f76, B:415:0x1052, B:417:0x0f9c, B:419:0x0fc4, B:424:0x0fd0, B:428:0x0fda, B:432:0x0fe9, B:434:0x0ffb, B:435:0x1007, B:436:0x1000, B:437:0x1013, B:439:0x1024, B:441:0x102e, B:443:0x1038, B:444:0x1045, B:447:0x104d, B:450:0x105b, B:451:0x1060, B:454:0x106e, B:456:0x1074, B:457:0x109b, B:459:0x10a4, B:460:0x10cd, B:462:0x10d3, B:463:0x1104, B:465:0x1112, B:466:0x111c, B:467:0x1123, B:468:0x1120, B:471:0x112e, B:473:0x1134, B:474:0x115b, B:475:0x1164, B:478:0x116f, B:480:0x1177, B:481:0x11ac, B:482:0x1184, B:483:0x11a9, B:662:0x1211, B:666:0x121a, B:671:0x11ea, B:672:0x11f5), top: B:5:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:441:0x102e A[Catch: IOException -> 0x11f6, XmlPullParserException -> 0x11f8, all -> 0x12aa, TryCatch #8 {all -> 0x12aa, blocks: (B:66:0x11b0, B:70:0x0320, B:72:0x032a, B:74:0x034e, B:75:0x0355, B:77:0x0366, B:78:0x038c, B:80:0x0392, B:81:0x03b8, B:82:0x03bf, B:84:0x03d0, B:85:0x03f4, B:87:0x03fa, B:88:0x0420, B:90:0x0426, B:91:0x042d, B:93:0x043d, B:94:0x0492, B:95:0x0462, B:97:0x0468, B:98:0x048d, B:100:0x0499, B:102:0x049f, B:103:0x04cb, B:104:0x04c2, B:105:0x04c8, B:107:0x04d2, B:109:0x04d8, B:110:0x04ff, B:112:0x0508, B:113:0x0531, B:118:0x053e, B:120:0x0548, B:121:0x0552, B:122:0x058a, B:123:0x0556, B:124:0x0587, B:126:0x0591, B:128:0x059d, B:130:0x05c1, B:133:0x05e9, B:134:0x05f2, B:135:0x05ef, B:137:0x05f9, B:139:0x0606, B:141:0x0630, B:143:0x0634, B:145:0x0637, B:146:0x063e, B:148:0x0642, B:149:0x0629, B:150:0x063f, B:151:0x0647, B:153:0x064d, B:154:0x0675, B:155:0x0670, B:156:0x067a, B:158:0x0680, B:159:0x06a8, B:160:0x06a3, B:162:0x06af, B:164:0x06b5, B:165:0x070b, B:166:0x06d8, B:168:0x06e0, B:170:0x06e7, B:171:0x0707, B:172:0x0708, B:173:0x0710, B:175:0x072d, B:176:0x07da, B:177:0x0753, B:179:0x0759, B:180:0x077e, B:182:0x0784, B:183:0x07a9, B:185:0x07b1, B:187:0x07b5, B:188:0x07bc, B:190:0x07c6, B:191:0x07d7, B:192:0x07d1, B:195:0x07df, B:196:0x0811, B:198:0x0812, B:199:0x0835, B:200:0x0836, B:201:0x083f, B:203:0x0845, B:204:0x086d, B:205:0x0868, B:206:0x0872, B:208:0x0878, B:209:0x08a0, B:210:0x089b, B:212:0x08a7, B:214:0x08b0, B:215:0x08d9, B:217:0x08e1, B:218:0x090a, B:220:0x091c, B:221:0x0926, B:222:0x0949, B:223:0x0946, B:225:0x0950, B:227:0x0956, B:228:0x0982, B:229:0x0979, B:230:0x097f, B:232:0x0989, B:233:0x098e, B:235:0x0998, B:237:0x09b9, B:241:0x09de, B:243:0x0a3d, B:247:0x0a54, B:249:0x0a61, B:251:0x0a6e, B:253:0x0a7b, B:254:0x0a88, B:257:0x0a93, B:259:0x0a9d, B:261:0x0aa3, B:262:0x0acf, B:263:0x0ac6, B:264:0x0acc, B:266:0x0ad6, B:270:0x0ae5, B:279:0x0af1, B:273:0x0b20, B:275:0x0b2a, B:276:0x0b34, B:277:0x0b65, B:281:0x0af6, B:282:0x0b3d, B:283:0x0b62, B:285:0x0b6c, B:287:0x0b75, B:288:0x0bd6, B:289:0x0b9a, B:291:0x0ba0, B:292:0x0bcd, B:293:0x0bd3, B:294:0x0bda, B:296:0x0be3, B:298:0x0bef, B:299:0x0c47, B:301:0x0c14, B:302:0x0c39, B:303:0x0c44, B:305:0x0c4d, B:307:0x0c53, B:308:0x0c7f, B:309:0x0c76, B:310:0x0c7c, B:312:0x0c86, B:314:0x0c8c, B:315:0x0cb8, B:316:0x0caf, B:317:0x0cb5, B:319:0x0cbf, B:321:0x0cc7, B:322:0x0cf5, B:323:0x0cec, B:324:0x0cf2, B:326:0x0cfc, B:328:0x0d08, B:329:0x0d62, B:330:0x0d2b, B:332:0x0d35, B:335:0x0d3d, B:336:0x0d48, B:338:0x0d50, B:341:0x0d5b, B:343:0x0d5f, B:345:0x0d69, B:347:0x0d75, B:348:0x0dcf, B:349:0x0d98, B:351:0x0da2, B:354:0x0daa, B:355:0x0db5, B:357:0x0dbd, B:360:0x0dc8, B:362:0x0dcc, B:364:0x0dd6, B:366:0x0ddc, B:367:0x0e08, B:368:0x0dff, B:369:0x0e05, B:371:0x0e0f, B:373:0x0e15, B:374:0x0e41, B:375:0x0e38, B:376:0x0e3e, B:378:0x0e48, B:380:0x0e4e, B:381:0x0e7a, B:382:0x0e71, B:383:0x0e77, B:385:0x0e81, B:387:0x0e87, B:388:0x0eb3, B:389:0x0eaa, B:390:0x0eb0, B:392:0x0eba, B:394:0x0ec0, B:395:0x0eee, B:396:0x0ee5, B:397:0x0eeb, B:400:0x0ef7, B:404:0x0f1d, B:405:0x0f4a, B:407:0x0f42, B:408:0x0f09, B:409:0x0f46, B:412:0x0f54, B:414:0x0f76, B:415:0x1052, B:417:0x0f9c, B:419:0x0fc4, B:424:0x0fd0, B:428:0x0fda, B:432:0x0fe9, B:434:0x0ffb, B:435:0x1007, B:436:0x1000, B:437:0x1013, B:439:0x1024, B:441:0x102e, B:443:0x1038, B:444:0x1045, B:447:0x104d, B:450:0x105b, B:451:0x1060, B:454:0x106e, B:456:0x1074, B:457:0x109b, B:459:0x10a4, B:460:0x10cd, B:462:0x10d3, B:463:0x1104, B:465:0x1112, B:466:0x111c, B:467:0x1123, B:468:0x1120, B:471:0x112e, B:473:0x1134, B:474:0x115b, B:475:0x1164, B:478:0x116f, B:480:0x1177, B:481:0x11ac, B:482:0x1184, B:483:0x11a9, B:662:0x1211, B:666:0x121a, B:671:0x11ea, B:672:0x11f5), top: B:5:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:443:0x1038 A[Catch: IOException -> 0x11f6, XmlPullParserException -> 0x11f8, all -> 0x12aa, TryCatch #8 {all -> 0x12aa, blocks: (B:66:0x11b0, B:70:0x0320, B:72:0x032a, B:74:0x034e, B:75:0x0355, B:77:0x0366, B:78:0x038c, B:80:0x0392, B:81:0x03b8, B:82:0x03bf, B:84:0x03d0, B:85:0x03f4, B:87:0x03fa, B:88:0x0420, B:90:0x0426, B:91:0x042d, B:93:0x043d, B:94:0x0492, B:95:0x0462, B:97:0x0468, B:98:0x048d, B:100:0x0499, B:102:0x049f, B:103:0x04cb, B:104:0x04c2, B:105:0x04c8, B:107:0x04d2, B:109:0x04d8, B:110:0x04ff, B:112:0x0508, B:113:0x0531, B:118:0x053e, B:120:0x0548, B:121:0x0552, B:122:0x058a, B:123:0x0556, B:124:0x0587, B:126:0x0591, B:128:0x059d, B:130:0x05c1, B:133:0x05e9, B:134:0x05f2, B:135:0x05ef, B:137:0x05f9, B:139:0x0606, B:141:0x0630, B:143:0x0634, B:145:0x0637, B:146:0x063e, B:148:0x0642, B:149:0x0629, B:150:0x063f, B:151:0x0647, B:153:0x064d, B:154:0x0675, B:155:0x0670, B:156:0x067a, B:158:0x0680, B:159:0x06a8, B:160:0x06a3, B:162:0x06af, B:164:0x06b5, B:165:0x070b, B:166:0x06d8, B:168:0x06e0, B:170:0x06e7, B:171:0x0707, B:172:0x0708, B:173:0x0710, B:175:0x072d, B:176:0x07da, B:177:0x0753, B:179:0x0759, B:180:0x077e, B:182:0x0784, B:183:0x07a9, B:185:0x07b1, B:187:0x07b5, B:188:0x07bc, B:190:0x07c6, B:191:0x07d7, B:192:0x07d1, B:195:0x07df, B:196:0x0811, B:198:0x0812, B:199:0x0835, B:200:0x0836, B:201:0x083f, B:203:0x0845, B:204:0x086d, B:205:0x0868, B:206:0x0872, B:208:0x0878, B:209:0x08a0, B:210:0x089b, B:212:0x08a7, B:214:0x08b0, B:215:0x08d9, B:217:0x08e1, B:218:0x090a, B:220:0x091c, B:221:0x0926, B:222:0x0949, B:223:0x0946, B:225:0x0950, B:227:0x0956, B:228:0x0982, B:229:0x0979, B:230:0x097f, B:232:0x0989, B:233:0x098e, B:235:0x0998, B:237:0x09b9, B:241:0x09de, B:243:0x0a3d, B:247:0x0a54, B:249:0x0a61, B:251:0x0a6e, B:253:0x0a7b, B:254:0x0a88, B:257:0x0a93, B:259:0x0a9d, B:261:0x0aa3, B:262:0x0acf, B:263:0x0ac6, B:264:0x0acc, B:266:0x0ad6, B:270:0x0ae5, B:279:0x0af1, B:273:0x0b20, B:275:0x0b2a, B:276:0x0b34, B:277:0x0b65, B:281:0x0af6, B:282:0x0b3d, B:283:0x0b62, B:285:0x0b6c, B:287:0x0b75, B:288:0x0bd6, B:289:0x0b9a, B:291:0x0ba0, B:292:0x0bcd, B:293:0x0bd3, B:294:0x0bda, B:296:0x0be3, B:298:0x0bef, B:299:0x0c47, B:301:0x0c14, B:302:0x0c39, B:303:0x0c44, B:305:0x0c4d, B:307:0x0c53, B:308:0x0c7f, B:309:0x0c76, B:310:0x0c7c, B:312:0x0c86, B:314:0x0c8c, B:315:0x0cb8, B:316:0x0caf, B:317:0x0cb5, B:319:0x0cbf, B:321:0x0cc7, B:322:0x0cf5, B:323:0x0cec, B:324:0x0cf2, B:326:0x0cfc, B:328:0x0d08, B:329:0x0d62, B:330:0x0d2b, B:332:0x0d35, B:335:0x0d3d, B:336:0x0d48, B:338:0x0d50, B:341:0x0d5b, B:343:0x0d5f, B:345:0x0d69, B:347:0x0d75, B:348:0x0dcf, B:349:0x0d98, B:351:0x0da2, B:354:0x0daa, B:355:0x0db5, B:357:0x0dbd, B:360:0x0dc8, B:362:0x0dcc, B:364:0x0dd6, B:366:0x0ddc, B:367:0x0e08, B:368:0x0dff, B:369:0x0e05, B:371:0x0e0f, B:373:0x0e15, B:374:0x0e41, B:375:0x0e38, B:376:0x0e3e, B:378:0x0e48, B:380:0x0e4e, B:381:0x0e7a, B:382:0x0e71, B:383:0x0e77, B:385:0x0e81, B:387:0x0e87, B:388:0x0eb3, B:389:0x0eaa, B:390:0x0eb0, B:392:0x0eba, B:394:0x0ec0, B:395:0x0eee, B:396:0x0ee5, B:397:0x0eeb, B:400:0x0ef7, B:404:0x0f1d, B:405:0x0f4a, B:407:0x0f42, B:408:0x0f09, B:409:0x0f46, B:412:0x0f54, B:414:0x0f76, B:415:0x1052, B:417:0x0f9c, B:419:0x0fc4, B:424:0x0fd0, B:428:0x0fda, B:432:0x0fe9, B:434:0x0ffb, B:435:0x1007, B:436:0x1000, B:437:0x1013, B:439:0x1024, B:441:0x102e, B:443:0x1038, B:444:0x1045, B:447:0x104d, B:450:0x105b, B:451:0x1060, B:454:0x106e, B:456:0x1074, B:457:0x109b, B:459:0x10a4, B:460:0x10cd, B:462:0x10d3, B:463:0x1104, B:465:0x1112, B:466:0x111c, B:467:0x1123, B:468:0x1120, B:471:0x112e, B:473:0x1134, B:474:0x115b, B:475:0x1164, B:478:0x116f, B:480:0x1177, B:481:0x11ac, B:482:0x1184, B:483:0x11a9, B:662:0x1211, B:666:0x121a, B:671:0x11ea, B:672:0x11f5), top: B:5:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:612:0x122c  */
    /* JADX WARN: Removed duplicated region for block: B:615:0x123c  */
    /* JADX WARN: Removed duplicated region for block: B:618:0x1247  */
    /* JADX WARN: Removed duplicated region for block: B:621:0x1258  */
    /* JADX WARN: Removed duplicated region for block: B:624:0x1268  */
    /* JADX WARN: Removed duplicated region for block: B:627:0x1276  */
    /* JADX WARN: Removed duplicated region for block: B:636:0x129f A[LOOP:2: B:634:0x1299->B:636:0x129f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:639:0x124d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readPermissionsFromXml(XmlPullParser xmlPullParser, File file, int i) {
        FileReader fileReader;
        String str;
        FileReader fileReader2;
        String str2;
        XmlPullParserException xmlPullParserException;
        Throwable th;
        IOException iOException;
        int version;
        Iterator it;
        int next;
        int i2;
        String str3;
        char c;
        boolean z;
        boolean z2;
        boolean z3;
        boolean exists;
        int parseInt;
        boolean z4;
        String str4 = "Got exception parsing permissions.";
        try {
            FileReader fileReader3 = new FileReader(file);
            Slog.i("SystemConfig", "Reading permissions from " + file);
            boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
            try {
                try {
                    try {
                        xmlPullParser.setInput(fileReader3);
                    } catch (Throwable th2) {
                        th = th2;
                        th = th;
                        IoUtils.closeQuietly(fileReader);
                        throw th;
                    }
                } catch (XmlPullParserException e) {
                    str2 = "Got exception parsing permissions.";
                    fileReader2 = fileReader3;
                    xmlPullParserException = e;
                }
            } catch (IOException e2) {
                e = e2;
                str = str4;
                fileReader2 = fileReader3;
            } catch (Throwable th3) {
                th = th3;
                fileReader = fileReader3;
                th = th;
                IoUtils.closeQuietly(fileReader);
                throw th;
            }
            try {
                do {
                    try {
                        next = xmlPullParser.next();
                        i2 = 1;
                        if (next != 2) {
                        }
                        break;
                    } catch (XmlPullParserException e3) {
                        fileReader2 = fileReader3;
                        xmlPullParserException = e3;
                        str2 = str4;
                    }
                } while (next != 1);
                break;
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                if (!xmlPullParser.getName().equals("permissions")) {
                    try {
                        if (!xmlPullParser.getName().equals("config")) {
                            throw new XmlPullParserException("Unexpected start tag in " + file + ": found " + xmlPullParser.getName() + ", expected 'permissions' or 'config'");
                        }
                    } catch (IOException e4) {
                        iOException = e4;
                        str = "Got exception parsing permissions.";
                        fileReader2 = fileReader3;
                        Slog.w("SystemConfig", str, iOException);
                        IoUtils.closeQuietly(fileReader2);
                        addFeature("com.samsung.feature.support_repair_mode", 0);
                        if (StorageManager.isFileEncrypted()) {
                        }
                        if (StorageManager.hasAdoptable()) {
                        }
                        if (ActivityManager.isLowRamDeviceStatic()) {
                        }
                        version = IncrementalManager.getVersion();
                        if (version > 0) {
                        }
                        addFeature("android.software.app_enumeration", 0);
                        if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 29) {
                        }
                        enableIpSecTunnelMigrationOnVsrUAndAbove();
                        if (isErofsSupported()) {
                        }
                        it = this.mUnavailableFeatures.iterator();
                        while (it.hasNext()) {
                        }
                    } catch (XmlPullParserException e5) {
                        xmlPullParserException = e5;
                        str2 = "Got exception parsing permissions.";
                        fileReader2 = fileReader3;
                        Slog.w("SystemConfig", str2, xmlPullParserException);
                        IoUtils.closeQuietly(fileReader2);
                        addFeature("com.samsung.feature.support_repair_mode", 0);
                        if (StorageManager.isFileEncrypted()) {
                        }
                        if (StorageManager.hasAdoptable()) {
                        }
                        if (ActivityManager.isLowRamDeviceStatic()) {
                        }
                        version = IncrementalManager.getVersion();
                        if (version > 0) {
                        }
                        addFeature("android.software.app_enumeration", 0);
                        if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 29) {
                        }
                        enableIpSecTunnelMigrationOnVsrUAndAbove();
                        if (isErofsSupported()) {
                        }
                        it = this.mUnavailableFeatures.iterator();
                        while (it.hasNext()) {
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        fileReader = fileReader3;
                        IoUtils.closeQuietly(fileReader);
                        throw th;
                    }
                }
                boolean z5 = i == -1;
                boolean z6 = (i & 2) != 0;
                boolean z7 = (i & 1) != 0;
                boolean z8 = (i & 4) != 0;
                boolean z9 = (i & 8) != 0;
                boolean z10 = (i & 16) != 0;
                boolean z11 = (i & 32) != 0;
                boolean z12 = (i & 64) != 0;
                boolean z13 = (i & 128) != 0;
                boolean z14 = (i & 256) != 0;
                boolean z15 = (i & 512) != 0;
                boolean z16 = (i & 1024) != 0;
                while (true) {
                    XmlUtils.nextElement(xmlPullParser);
                    if (xmlPullParser.getEventType() != i2) {
                        String name = xmlPullParser.getName();
                        if (name == null) {
                            XmlUtils.skipCurrentTag(xmlPullParser);
                        } else {
                            switch (name.hashCode()) {
                                case -2040330235:
                                    if (name.equals("allow-unthrottled-location")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1882490007:
                                    if (name.equals("allow-in-power-save")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1582324217:
                                    if (name.equals("allow-adas-location-settings")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1554938271:
                                    if (name.equals("named-actor")) {
                                        c = 29;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1461465444:
                                    if (name.equals("component-override")) {
                                        c = 18;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1390350881:
                                    if (name.equals("install-constraints-allowed")) {
                                        c = '$';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1005864890:
                                    if (name.equals("disabled-until-used-preinstalled-carrier-app")) {
                                        c = 21;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -980620291:
                                    if (name.equals("allow-association")) {
                                        c = 25;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -979207434:
                                    if (name.equals(LauncherConfigurationInternal.KEY_FEATURE_INT)) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -972849788:
                                    if (name.equals("automatic-rollback-denylisted-app")) {
                                        c = ' ';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -828905863:
                                    if (name.equals("unavailable-feature")) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -642819164:
                                    if (name.equals("allow-in-power-save-except-idle")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -634266752:
                                    if (name.equals("bg-restriction-exemption")) {
                                        c = 16;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -625731345:
                                    if (name.equals("asl-file")) {
                                        c = '\'';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -560717308:
                                    if (name.equals("allow-ignore-location-settings")) {
                                        c = '\r';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -517618225:
                                    if (name.equals("permission")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -150068154:
                                    if (name.equals("install-in-user-type")) {
                                        c = 28;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 98629247:
                                    if (name.equals("group")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 166208699:
                                    if (name.equals("library")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 180165796:
                                    if (name.equals("hidden-api-whitelisted-app")) {
                                        c = 24;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 347247519:
                                    if (name.equals("backup-transport-whitelisted-service")) {
                                        c = 19;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 414198242:
                                    if (name.equals("allowed-vendor-apex")) {
                                        c = '\"';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 783200107:
                                    if (name.equals("update-ownership")) {
                                        c = '%';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 802332808:
                                    if (name.equals("allow-in-data-usage-save")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 953292141:
                                    if (name.equals("assign-permission")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 968751633:
                                    if (name.equals("rollback-whitelisted-app")) {
                                        c = 31;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1005096720:
                                    if (name.equals("apex-library")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1044015374:
                                    if (name.equals("oem-permissions")) {
                                        c = 23;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1046683496:
                                    if (name.equals("whitelisted-staged-installer")) {
                                        c = '!';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1121420326:
                                    if (name.equals("app-link")) {
                                        c = 15;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1269564002:
                                    if (name.equals("split-permission")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1347585732:
                                    if (name.equals("app-data-isolation-whitelisted-app")) {
                                        c = 26;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1567330472:
                                    if (name.equals("default-enabled-vr-app")) {
                                        c = 17;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1633270165:
                                    if (name.equals("disabled-until-used-preinstalled-carrier-associated-app")) {
                                        c = 20;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1723146313:
                                    if (name.equals("privapp-permissions")) {
                                        c = 22;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1723586945:
                                    if (name.equals("bugreport-whitelisted")) {
                                        c = 27;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1748566401:
                                    if (name.equals("initial-package-state")) {
                                        c = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1793277898:
                                    if (name.equals("overlay-config-signature")) {
                                        c = 30;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1860133373:
                                    if (name.equals("assign-uid-for-data-usage")) {
                                        c = '#';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1865495576:
                                    if (name.equals("required-package")) {
                                        c = '(';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1954925533:
                                    if (name.equals("allow-implicit-broadcast")) {
                                        c = 14;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            FileReader fileReader4 = fileReader3;
                            String str5 = str4;
                            boolean z17 = isLowRamDeviceStatic;
                            boolean z18 = z15;
                            boolean z19 = z14;
                            boolean z20 = z13;
                            String str6 = null;
                            switch (c) {
                                case 0:
                                    z = z16;
                                    if (z5) {
                                        String attributeValue = xmlPullParser.getAttributeValue(null, "gid");
                                        if (attributeValue != null) {
                                            this.mGlobalGids = ArrayUtils.appendInt(this.mGlobalGids, Process.getGidForName(attributeValue));
                                        } else {
                                            Slog.w("SystemConfig", "<" + name + "> without gid in " + file + " at " + xmlPullParser.getPositionDescription());
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 1:
                                    z = z16;
                                    if (z8) {
                                        String attributeValue2 = xmlPullParser.getAttributeValue(null, "name");
                                        if (attributeValue2 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(xmlPullParser);
                                        } else {
                                            readPermission(xmlPullParser, attributeValue2.intern());
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                        XmlUtils.skipCurrentTag(xmlPullParser);
                                    }
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 2:
                                    z = z16;
                                    if (z8) {
                                        String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                                        if (attributeValue3 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(xmlPullParser);
                                        } else {
                                            String attributeValue4 = xmlPullParser.getAttributeValue(null, "uid");
                                            if (attributeValue4 == null) {
                                                Slog.w("SystemConfig", "<" + name + "> without uid in " + file + " at " + xmlPullParser.getPositionDescription());
                                                XmlUtils.skipCurrentTag(xmlPullParser);
                                            } else {
                                                int uidForName = Process.getUidForName(attributeValue4);
                                                if (uidForName < 0) {
                                                    Slog.w("SystemConfig", "<" + name + "> with unknown uid \"" + attributeValue4 + "  in " + file + " at " + xmlPullParser.getPositionDescription());
                                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                                } else {
                                                    String intern = attributeValue3.intern();
                                                    ArraySet arraySet = (ArraySet) this.mSystemPermissions.get(uidForName);
                                                    if (arraySet == null) {
                                                        arraySet = new ArraySet();
                                                        this.mSystemPermissions.put(uidForName, arraySet);
                                                    }
                                                    arraySet.add(intern);
                                                }
                                            }
                                        }
                                        z16 = z;
                                        fileReader3 = fileReader4;
                                        str4 = str5;
                                        isLowRamDeviceStatic = z17;
                                        z15 = z18;
                                        z14 = z19;
                                        z13 = z20;
                                        i2 = 1;
                                        break;
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                case 3:
                                    z = z16;
                                    if (z8) {
                                        readSplitPermission(xmlPullParser, file);
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                        XmlUtils.skipCurrentTag(xmlPullParser);
                                    }
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 4:
                                case 5:
                                    if (z6) {
                                        String attributeValue5 = xmlPullParser.getAttributeValue(null, "name");
                                        String attributeValue6 = xmlPullParser.getAttributeValue(null, "file");
                                        String attributeValue7 = xmlPullParser.getAttributeValue(null, "dependency");
                                        String attributeValue8 = xmlPullParser.getAttributeValue(null, "min-device-sdk");
                                        z = z16;
                                        String attributeValue9 = xmlPullParser.getAttributeValue(null, "max-device-sdk");
                                        if (attributeValue5 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (attributeValue6 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without file in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            if (attributeValue8 != null && !isAtLeastSdkLevel(attributeValue8)) {
                                                z2 = false;
                                                if (attributeValue9 != null && !isAtMostSdkLevel(attributeValue9)) {
                                                    z3 = false;
                                                    exists = new File(attributeValue6).exists();
                                                    if (!z2 && z3 && exists) {
                                                        this.mSharedLibraries.put(attributeValue5, new SharedLibraryEntry(attributeValue5, attributeValue6, attributeValue7 == null ? new String[0] : attributeValue7.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR), xmlPullParser.getAttributeValue(null, "on-bootclasspath-since"), xmlPullParser.getAttributeValue(null, "on-bootclasspath-before")));
                                                    } else {
                                                        StringBuilder sb = new StringBuilder("Ignore shared library ");
                                                        sb.append(attributeValue5);
                                                        sb.append(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                                                        if (!z2) {
                                                            sb.append(" min-device-sdk=");
                                                            sb.append(attributeValue8);
                                                        }
                                                        if (!z3) {
                                                            sb.append(" max-device-sdk=");
                                                            sb.append(attributeValue9);
                                                        }
                                                        if (!exists) {
                                                            sb.append(" ");
                                                            sb.append(attributeValue6);
                                                            sb.append(" does not exist");
                                                        }
                                                        Slog.i("SystemConfig", sb.toString());
                                                    }
                                                }
                                                z3 = true;
                                                exists = new File(attributeValue6).exists();
                                                if (!z2) {
                                                }
                                                StringBuilder sb2 = new StringBuilder("Ignore shared library ");
                                                sb2.append(attributeValue5);
                                                sb2.append(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                                                if (!z2) {
                                                }
                                                if (!z3) {
                                                }
                                                if (!exists) {
                                                }
                                                Slog.i("SystemConfig", sb2.toString());
                                            }
                                            z2 = true;
                                            if (attributeValue9 != null) {
                                                z3 = false;
                                                exists = new File(attributeValue6).exists();
                                                if (!z2) {
                                                }
                                                StringBuilder sb22 = new StringBuilder("Ignore shared library ");
                                                sb22.append(attributeValue5);
                                                sb22.append(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                                                if (!z2) {
                                                }
                                                if (!z3) {
                                                }
                                                if (!exists) {
                                                }
                                                Slog.i("SystemConfig", sb22.toString());
                                            }
                                            z3 = true;
                                            exists = new File(attributeValue6).exists();
                                            if (!z2) {
                                            }
                                            StringBuilder sb222 = new StringBuilder("Ignore shared library ");
                                            sb222.append(attributeValue5);
                                            sb222.append(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                                            if (!z2) {
                                            }
                                            if (!z3) {
                                            }
                                            if (!exists) {
                                            }
                                            Slog.i("SystemConfig", sb222.toString());
                                        }
                                    } else {
                                        z = z16;
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 6:
                                    if (z7) {
                                        String attributeValue10 = xmlPullParser.getAttributeValue(null, "name");
                                        int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "version", 0);
                                        boolean z21 = !z17 ? true : !"true".equals(xmlPullParser.getAttributeValue(null, "notLowRam"));
                                        if (attributeValue10 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (z21) {
                                            addFeature(attributeValue10, readIntAttribute);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 7:
                                    if (z7) {
                                        String attributeValue11 = xmlPullParser.getAttributeValue(null, "name");
                                        if (attributeValue11 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mUnavailableFeatures.add(attributeValue11);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '\b':
                                    if (z19) {
                                        String attributeValue12 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue12 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mAllowInPowerSaveExceptIdle.add(attributeValue12);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '\t':
                                    if (z19) {
                                        String attributeValue13 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue13 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mAllowInPowerSave.add(attributeValue13);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '\n':
                                    if (z19) {
                                        String attributeValue14 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue14 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mAllowInDataUsageSave.add(attributeValue14);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 11:
                                    if (z19) {
                                        String attributeValue15 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue15 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mAllowUnthrottledLocation.add(attributeValue15);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '\f':
                                    if (z19) {
                                        String attributeValue16 = xmlPullParser.getAttributeValue(null, "package");
                                        String attributeValue17 = xmlPullParser.getAttributeValue(null, "attributionTag");
                                        if (attributeValue16 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            ArraySet arraySet2 = (ArraySet) this.mAllowAdasSettings.get(attributeValue16);
                                            if (arraySet2 == null || !arraySet2.isEmpty()) {
                                                if (arraySet2 == null) {
                                                    arraySet2 = new ArraySet(1);
                                                    this.mAllowAdasSettings.put(attributeValue16, arraySet2);
                                                }
                                                if (!"*".equals(attributeValue17)) {
                                                    if (!"null".equals(attributeValue17)) {
                                                        str6 = attributeValue17;
                                                    }
                                                    arraySet2.add(str6);
                                                }
                                            }
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '\r':
                                    if (z19) {
                                        String attributeValue18 = xmlPullParser.getAttributeValue(null, "package");
                                        String attributeValue19 = xmlPullParser.getAttributeValue(null, "attributionTag");
                                        if (attributeValue18 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            ArraySet arraySet3 = (ArraySet) this.mAllowIgnoreLocationSettings.get(attributeValue18);
                                            if (arraySet3 == null || !arraySet3.isEmpty()) {
                                                if (arraySet3 == null) {
                                                    arraySet3 = new ArraySet(1);
                                                    this.mAllowIgnoreLocationSettings.put(attributeValue18, arraySet3);
                                                }
                                                if (!"*".equals(attributeValue19)) {
                                                    if (!"null".equals(attributeValue19)) {
                                                        str6 = attributeValue19;
                                                    }
                                                    arraySet3.add(str6);
                                                }
                                            }
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 14:
                                    if (z18) {
                                        String attributeValue20 = xmlPullParser.getAttributeValue(null, "action");
                                        if (attributeValue20 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without action in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mAllowImplicitBroadcasts.add(attributeValue20);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 15:
                                    if (z9) {
                                        String attributeValue21 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue21 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mLinkedApps.add(attributeValue21);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 16:
                                    if (z19) {
                                        String attributeValue22 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue22 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mBgRestrictionExemption.add(attributeValue22);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 17:
                                    if (z9) {
                                        String attributeValue23 = xmlPullParser.getAttributeValue(null, "package");
                                        String attributeValue24 = xmlPullParser.getAttributeValue(null, "class");
                                        if (attributeValue23 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (attributeValue24 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without class in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mDefaultVrComponents.add(new ComponentName(attributeValue23, attributeValue24));
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 18:
                                    readComponentOverrides(xmlPullParser, file);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 19:
                                    if (z7) {
                                        String attributeValue25 = xmlPullParser.getAttributeValue(null, "service");
                                        if (attributeValue25 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without service in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            ComponentName unflattenFromString = ComponentName.unflattenFromString(attributeValue25);
                                            if (unflattenFromString == null) {
                                                Slog.w("SystemConfig", "<" + name + "> with invalid service name " + attributeValue25 + " in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mBackupTransportWhitelist.add(unflattenFromString);
                                            }
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 20:
                                    if (z9) {
                                        String attributeValue26 = xmlPullParser.getAttributeValue(null, "package");
                                        String attributeValue27 = xmlPullParser.getAttributeValue(null, "carrierAppPackage");
                                        if (attributeValue26 != null && attributeValue27 != null) {
                                            String attributeValue28 = xmlPullParser.getAttributeValue(null, "addedInSdk");
                                            if (TextUtils.isEmpty(attributeValue28)) {
                                                parseInt = -1;
                                            } else {
                                                try {
                                                    parseInt = Integer.parseInt(attributeValue28);
                                                } catch (NumberFormatException unused) {
                                                    Slog.w("SystemConfig", "<" + name + "> addedInSdk not an integer in " + file + " at " + xmlPullParser.getPositionDescription());
                                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                                }
                                            }
                                            List list = (List) this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps.get(attributeValue27);
                                            if (list == null) {
                                                list = new ArrayList();
                                                this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps.put(attributeValue27, list);
                                            }
                                            list.add(new CarrierAssociatedAppEntry(attributeValue26, parseInt));
                                        }
                                        Slog.w("SystemConfig", "<" + name + "> without package or carrierAppPackage in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 21:
                                    if (z9) {
                                        String attributeValue29 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue29 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mDisabledUntilUsedPreinstalledCarrierApps.add(attributeValue29);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 22:
                                    if (z10) {
                                        if (!file.toPath().startsWith(Environment.getVendorDirectory().toPath() + "/")) {
                                            if (!file.toPath().startsWith(Environment.getOdmDirectory().toPath() + "/")) {
                                                z4 = false;
                                                boolean startsWith = file.toPath().startsWith(Environment.getProductDirectory().toPath() + "/");
                                                boolean startsWith2 = file.toPath().startsWith(Environment.getSystemExtDirectory().toPath() + "/");
                                                Path path = file.toPath();
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append(Environment.getApexDirectory().toPath());
                                                sb3.append("/");
                                                boolean z22 = !path.startsWith(sb3.toString()) && ((Boolean) ApexProperties.updatable().orElse(Boolean.FALSE)).booleanValue();
                                                if (!z4) {
                                                    readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getVendorPrivilegedAppAllowlist());
                                                } else if (startsWith) {
                                                    readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getProductPrivilegedAppAllowlist());
                                                } else if (startsWith2) {
                                                    readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getSystemExtPrivilegedAppAllowlist());
                                                } else if (z22) {
                                                    readApexPrivAppPermissions(xmlPullParser, file, Environment.getApexDirectory().toPath());
                                                } else {
                                                    readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getPrivilegedAppAllowlist());
                                                }
                                            }
                                        }
                                        z4 = true;
                                        boolean startsWith3 = file.toPath().startsWith(Environment.getProductDirectory().toPath() + "/");
                                        boolean startsWith22 = file.toPath().startsWith(Environment.getSystemExtDirectory().toPath() + "/");
                                        Path path2 = file.toPath();
                                        StringBuilder sb32 = new StringBuilder();
                                        sb32.append(Environment.getApexDirectory().toPath());
                                        sb32.append("/");
                                        if (path2.startsWith(sb32.toString())) {
                                        }
                                        if (!z4) {
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                        XmlUtils.skipCurrentTag(xmlPullParser);
                                    }
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 23:
                                    if (z11) {
                                        readOemPermissions(xmlPullParser);
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                        XmlUtils.skipCurrentTag(xmlPullParser);
                                    }
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 24:
                                    if (z12) {
                                        String attributeValue30 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue30 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mHiddenApiPackageWhitelist.add(attributeValue30);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 25:
                                    if (z20) {
                                        String attributeValue31 = xmlPullParser.getAttributeValue(null, "target");
                                        if (attributeValue31 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without target in " + file + " at " + xmlPullParser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(xmlPullParser);
                                        } else {
                                            String attributeValue32 = xmlPullParser.getAttributeValue(null, "allowed");
                                            if (attributeValue32 == null) {
                                                Slog.w("SystemConfig", "<" + name + "> without allowed in " + file + " at " + xmlPullParser.getPositionDescription());
                                                XmlUtils.skipCurrentTag(xmlPullParser);
                                            } else {
                                                String intern2 = attributeValue31.intern();
                                                String intern3 = attributeValue32.intern();
                                                ArraySet arraySet4 = (ArraySet) this.mAllowedAssociations.get(intern2);
                                                if (arraySet4 == null) {
                                                    arraySet4 = new ArraySet();
                                                    this.mAllowedAssociations.put(intern2, arraySet4);
                                                }
                                                Slog.i("SystemConfig", "Adding association: " + intern2 + " <- " + intern3);
                                                arraySet4.add(intern3);
                                            }
                                        }
                                        z = z16;
                                        z16 = z;
                                        fileReader3 = fileReader4;
                                        str4 = str5;
                                        isLowRamDeviceStatic = z17;
                                        z15 = z18;
                                        z14 = z19;
                                        z13 = z20;
                                        i2 = 1;
                                        break;
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                case 26:
                                    String attributeValue33 = xmlPullParser.getAttributeValue(null, "package");
                                    if (attributeValue33 == null) {
                                        Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        this.mAppDataIsolationWhitelistedApps.add(attributeValue33);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 27:
                                    String attributeValue34 = xmlPullParser.getAttributeValue(null, "package");
                                    if (attributeValue34 == null) {
                                        Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        this.mBugreportWhitelistedPackages.add(attributeValue34);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 28:
                                    readInstallInUserType(xmlPullParser, this.mPackageToUserTypeWhitelist, this.mPackageToUserTypeBlacklist);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 29:
                                    String safeIntern = TextUtils.safeIntern(xmlPullParser.getAttributeValue(null, "namespace"));
                                    String attributeValue35 = xmlPullParser.getAttributeValue(null, "name");
                                    String safeIntern2 = TextUtils.safeIntern(xmlPullParser.getAttributeValue(null, "package"));
                                    if (TextUtils.isEmpty(safeIntern)) {
                                        Slog.wtf("SystemConfig", "<" + name + "> without namespace in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else if (TextUtils.isEmpty(attributeValue35)) {
                                        Slog.wtf("SystemConfig", "<" + name + "> without actor name in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else if (TextUtils.isEmpty(safeIntern2)) {
                                        Slog.wtf("SystemConfig", "<" + name + "> without package name in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        if ("android".equalsIgnoreCase(safeIntern)) {
                                            throw new IllegalStateException("Defining " + attributeValue35 + " as " + safeIntern2 + " for the android namespace is not allowed");
                                        }
                                        if (this.mNamedActors == null) {
                                            this.mNamedActors = new ArrayMap();
                                        }
                                        Map map = (Map) this.mNamedActors.get(safeIntern);
                                        if (map == null) {
                                            map = new ArrayMap();
                                            this.mNamedActors.put(safeIntern, map);
                                        } else if (map.containsKey(attributeValue35)) {
                                            throw new IllegalStateException("Duplicate actor definition for " + safeIntern + "/" + attributeValue35 + "; defined as both " + ((String) map.get(attributeValue35)) + " and " + safeIntern2);
                                        }
                                        map.put(attributeValue35, safeIntern2);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 30:
                                    if (z5) {
                                        String attributeValue36 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue36 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (TextUtils.isEmpty(this.mOverlayConfigSignaturePackage)) {
                                            this.mOverlayConfigSignaturePackage = attributeValue36.intern();
                                        } else {
                                            throw new IllegalStateException("Reference signature package defined as both " + this.mOverlayConfigSignaturePackage + " and " + attributeValue36);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case 31:
                                    String attributeValue37 = xmlPullParser.getAttributeValue(null, "package");
                                    if (attributeValue37 == null) {
                                        Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        this.mRollbackWhitelistedPackages.add(attributeValue37);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case ' ':
                                    String attributeValue38 = xmlPullParser.getAttributeValue(null, "package");
                                    if (attributeValue38 == null) {
                                        Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        this.mAutomaticRollbackDenylistedPackages.add(attributeValue38);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '!':
                                    if (z9) {
                                        String attributeValue39 = xmlPullParser.getAttributeValue(null, "package");
                                        boolean readBooleanAttribute = XmlUtils.readBooleanAttribute(xmlPullParser, "isModulesInstaller", false);
                                        if (attributeValue39 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mWhitelistedStagedInstallers.add(attributeValue39);
                                        }
                                        if (readBooleanAttribute) {
                                            if (this.mModulesInstallerPackageName != null) {
                                                throw new IllegalStateException("Multiple modules installers");
                                            }
                                            this.mModulesInstallerPackageName = attributeValue39;
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '\"':
                                    if (z16) {
                                        String attributeValue40 = xmlPullParser.getAttributeValue(null, "package");
                                        String attributeValue41 = xmlPullParser.getAttributeValue(null, "installerPackage");
                                        if (attributeValue40 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        }
                                        if (attributeValue41 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without installerPackage in " + file + " at " + xmlPullParser.getPositionDescription());
                                        }
                                        if (attributeValue40 != null && attributeValue41 != null) {
                                            this.mAllowedVendorApexes.put(attributeValue40, attributeValue41);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '#':
                                    if (z5) {
                                        String attributeValue42 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue42 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            XmlUtils.skipCurrentTag(xmlPullParser);
                                        } else {
                                            String attributeValue43 = xmlPullParser.getAttributeValue(null, "uid");
                                            if (attributeValue43 == null) {
                                                Slog.w("SystemConfig", "<" + name + "> without uid in " + file + " at " + xmlPullParser.getPositionDescription());
                                                XmlUtils.skipCurrentTag(xmlPullParser);
                                            } else {
                                                int parseInt2 = Integer.parseInt(attributeValue43);
                                                if (parseInt2 >= 2900 && parseInt2 <= 2999) {
                                                    ArrayList arrayList = (ArrayList) this.mDataUsageSystemUidPackages.get(parseInt2);
                                                    if (arrayList == null) {
                                                        arrayList = new ArrayList();
                                                        this.mDataUsageSystemUidPackages.put(parseInt2, arrayList);
                                                    }
                                                    arrayList.add(attributeValue42);
                                                }
                                                Slog.w("SystemConfig", "<" + name + "> with unknown uid " + attributeValue43 + " in " + file + " at " + xmlPullParser.getPositionDescription());
                                                XmlUtils.skipCurrentTag(xmlPullParser);
                                            }
                                        }
                                        z = z16;
                                        z16 = z;
                                        fileReader3 = fileReader4;
                                        str4 = str5;
                                        isLowRamDeviceStatic = z17;
                                        z15 = z18;
                                        z14 = z19;
                                        z13 = z20;
                                        i2 = 1;
                                        break;
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '$':
                                    if (z9) {
                                        String attributeValue44 = xmlPullParser.getAttributeValue(null, "package");
                                        if (attributeValue44 == null) {
                                            Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mInstallConstraintsAllowlist.add(attributeValue44);
                                        }
                                    } else {
                                        logNotAllowedInPartition(name, file, xmlPullParser);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '%':
                                    String attributeValue45 = xmlPullParser.getAttributeValue(null, "package");
                                    String attributeValue46 = xmlPullParser.getAttributeValue(null, "installer");
                                    if (TextUtils.isEmpty(attributeValue45)) {
                                        Slog.w("SystemConfig", "<" + name + "> without valid package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else if (TextUtils.isEmpty(attributeValue46)) {
                                        Slog.w("SystemConfig", "<" + name + "> without valid installer in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        this.mUpdateOwnersForSystemApps.put(attributeValue45, attributeValue46);
                                    }
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '&':
                                    String attributeValue47 = xmlPullParser.getAttributeValue(null, "package");
                                    String attributeValue48 = xmlPullParser.getAttributeValue(null, "stopped");
                                    if (TextUtils.isEmpty(attributeValue47)) {
                                        Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else if (TextUtils.isEmpty(attributeValue48)) {
                                        Slog.w("SystemConfig", "<" + name + "> without stopped in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else if (!Boolean.parseBoolean(attributeValue48)) {
                                        this.mInitialNonStoppedSystemPackages.add(attributeValue47);
                                    }
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '\'':
                                    String attributeValue49 = xmlPullParser.getAttributeValue(null, "package");
                                    String attributeValue50 = xmlPullParser.getAttributeValue(null, "path");
                                    if (TextUtils.isEmpty(attributeValue49)) {
                                        Slog.w("SystemConfig", "<" + name + "> without valid package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else if (TextUtils.isEmpty(attributeValue50)) {
                                        Slog.w("SystemConfig", "<" + name + "> without valid path in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        this.mAppMetadataFilePaths.put(attributeValue49, attributeValue50);
                                    }
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                case '(':
                                    String attributeValue51 = xmlPullParser.getAttributeValue(null, "package");
                                    if (TextUtils.isEmpty(attributeValue51)) {
                                        Slog.w("SystemConfig", "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                    } else {
                                        this.mRequiredSystemPackages.add(attributeValue51);
                                    }
                                    z = z16;
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                                default:
                                    z = z16;
                                    Slog.w("SystemConfig", "Tag " + name + " is unknown in " + file + " at " + xmlPullParser.getPositionDescription());
                                    XmlUtils.skipCurrentTag(xmlPullParser);
                                    z16 = z;
                                    fileReader3 = fileReader4;
                                    str4 = str5;
                                    isLowRamDeviceStatic = z17;
                                    z15 = z18;
                                    z14 = z19;
                                    z13 = z20;
                                    i2 = 1;
                                    break;
                            }
                        }
                    } else {
                        IoUtils.closeQuietly(fileReader3);
                    }
                }
            } catch (IOException e6) {
                e = e6;
                iOException = e;
                Slog.w("SystemConfig", str, iOException);
                IoUtils.closeQuietly(fileReader2);
                addFeature("com.samsung.feature.support_repair_mode", 0);
                if (StorageManager.isFileEncrypted()) {
                    addFeature("android.software.file_based_encryption", 0);
                    addFeature("android.software.securely_removes_users", 0);
                }
                if (StorageManager.hasAdoptable()) {
                    addFeature("android.software.adoptable_storage", 0);
                }
                if (ActivityManager.isLowRamDeviceStatic()) {
                    addFeature("android.hardware.ram.low", 0);
                } else {
                    addFeature("android.hardware.ram.normal", 0);
                }
                version = IncrementalManager.getVersion();
                if (version > 0) {
                    addFeature("android.software.incremental_delivery", version);
                }
                addFeature("android.software.app_enumeration", 0);
                if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 29) {
                    addFeature("android.software.ipsec_tunnels", 0);
                }
                enableIpSecTunnelMigrationOnVsrUAndAbove();
                if (isErofsSupported()) {
                    if (isKernelVersionAtLeast(5, 10)) {
                        addFeature("android.software.erofs", 0);
                    } else if (isKernelVersionAtLeast(4, 19)) {
                        addFeature("android.software.erofs_legacy", 0);
                    }
                }
                it = this.mUnavailableFeatures.iterator();
                while (it.hasNext()) {
                    removeFeature((String) it.next());
                }
            } catch (XmlPullParserException e7) {
                xmlPullParserException = e7;
                str2 = str3;
            }
        } catch (FileNotFoundException unused2) {
            Slog.w("SystemConfig", "Couldn't find or open permissions file " + file);
        }
    }

    public final void enableIpSecTunnelMigrationOnVsrUAndAbove() {
        if (SystemProperties.getInt("ro.vendor.api_level", Build.VERSION.DEVICE_INITIAL_SDK_INT) > 33) {
            addFeature("android.software.ipsec_tunnel_migration", 0);
        }
    }

    public final void addFeature(String str, int i) {
        FeatureInfo featureInfo = (FeatureInfo) this.mAvailableFeatures.get(str);
        if (featureInfo == null) {
            FeatureInfo featureInfo2 = new FeatureInfo();
            featureInfo2.name = str;
            featureInfo2.version = i;
            this.mAvailableFeatures.put(str, featureInfo2);
            return;
        }
        featureInfo.version = Math.max(featureInfo.version, i);
    }

    public final void removeFeature(String str) {
        if (this.mAvailableFeatures.remove(str) != null) {
            Slog.d("SystemConfig", "Removed unavailable feature " + str);
        }
    }

    public void readPermission(XmlPullParser xmlPullParser, String str) {
        if (this.mPermissions.containsKey(str)) {
            throw new IllegalStateException("Duplicate permission definition for " + str);
        }
        PermissionEntry permissionEntry = new PermissionEntry(str, XmlUtils.readBooleanAttribute(xmlPullParser, "perUser", false));
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
                        permissionEntry.gids = ArrayUtils.appendInt(permissionEntry.gids, Process.getGidForName(attributeValue));
                    } else {
                        Slog.w("SystemConfig", "<group> without gid at " + xmlPullParser.getPositionDescription());
                    }
                }
                XmlUtils.skipCurrentTag(xmlPullParser);
            }
        }
    }

    public final void readPrivAppPermissions(XmlPullParser xmlPullParser, ArrayMap arrayMap) {
        readPermissionAllowlist(xmlPullParser, arrayMap, "privapp-permissions");
    }

    public final void readInstallInUserType(XmlPullParser xmlPullParser, Map map, Map map2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(attributeValue)) {
            Slog.w("SystemConfig", "package is required for <install-in-user-type> in " + xmlPullParser.getPositionDescription());
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
                    Slog.w("SystemConfig", "user-type is required for <install-in-user-type> in " + xmlPullParser.getPositionDescription());
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
                    Slog.w("SystemConfig", "user-type is required for <install-in-user-type> in " + xmlPullParser.getPositionDescription());
                } else {
                    if (set2 == null) {
                        set2 = new ArraySet();
                        map2.put(attributeValue, set2);
                    }
                    set2.add(attributeValue3);
                }
            } else {
                Slog.w("SystemConfig", "unrecognized tag in <install-in-user-type> in " + xmlPullParser.getPositionDescription());
            }
        }
    }

    public void readOemPermissions(XmlPullParser xmlPullParser) {
        readPermissionAllowlist(xmlPullParser, this.mPermissionAllowlist.getOemAppAllowlist(), "oem-permissions");
    }

    public static void readPermissionAllowlist(XmlPullParser xmlPullParser, ArrayMap arrayMap, String str) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (TextUtils.isEmpty(attributeValue)) {
            Slog.w("SystemConfig", "package is required for <" + str + "> in " + xmlPullParser.getPositionDescription());
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
                    Slog.w("SystemConfig", "name is required for <permission> in " + xmlPullParser.getPositionDescription());
                } else {
                    arrayMap2.put(attributeValue2, Boolean.TRUE);
                }
            } else if ("deny-permission".equals(name)) {
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                if (TextUtils.isEmpty(attributeValue3)) {
                    Slog.w("SystemConfig", "name is required for <deny-permission> in " + xmlPullParser.getPositionDescription());
                } else {
                    arrayMap2.put(attributeValue3, Boolean.FALSE);
                }
            }
        }
        arrayMap.put(attributeValue, arrayMap2);
    }

    public final void readSplitPermission(XmlPullParser xmlPullParser, File file) {
        int parseInt;
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue == null) {
            Slog.w("SystemConfig", "<split-permission> without name in " + file + " at " + xmlPullParser.getPositionDescription());
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
                Slog.w("SystemConfig", "<split-permission> targetSdk not an integer in " + file + " at " + xmlPullParser.getPositionDescription());
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
                    Slog.w("SystemConfig", "name is required for <new-permission> in " + xmlPullParser.getPositionDescription());
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
        this.mSplitPermissions.add(new PermissionManager.SplitPermissionInfo(attributeValue, arrayList, parseInt));
    }

    public final void readComponentOverrides(XmlPullParser xmlPullParser, File file) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (attributeValue == null) {
            Slog.w("SystemConfig", "<component-override> without package in " + file + " at " + xmlPullParser.getPositionDescription());
            return;
        }
        String intern = attributeValue.intern();
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("component".equals(xmlPullParser.getName())) {
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "class");
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "enabled");
                if (attributeValue2 == null) {
                    Slog.w("SystemConfig", "<component> without class in " + file + " at " + xmlPullParser.getPositionDescription());
                    return;
                }
                if (attributeValue3 == null) {
                    Slog.w("SystemConfig", "<component> without enabled in " + file + " at " + xmlPullParser.getPositionDescription());
                    return;
                }
                if (attributeValue2.startsWith(".")) {
                    attributeValue2 = intern + attributeValue2;
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

    public final void readPublicNativeLibrariesList() {
        readPublicLibrariesListFile(new File("/vendor/etc/public.libraries.txt"));
        String[] strArr = {"/system/etc", "/system_ext/etc", "/product/etc"};
        for (int i = 0; i < 3; i++) {
            String str = strArr[i];
            File[] listFiles = new File(str).listFiles();
            if (listFiles == null) {
                Slog.w("SystemConfig", "Public libraries file folder missing: " + str);
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

    public final void readPublicLibrariesListFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (!readLine.isEmpty() && !readLine.startsWith("#")) {
                            String str = readLine.trim().split(" ")[0];
                            SharedLibraryEntry sharedLibraryEntry = new SharedLibraryEntry(str, str, new String[0], true);
                            this.mSharedLibraries.put(sharedLibraryEntry.name, sharedLibraryEntry);
                        }
                    } else {
                        bufferedReader.close();
                        return;
                    }
                } finally {
                }
            }
        } catch (IOException e) {
            Slog.w("SystemConfig", "Failed to read public libraries file " + file, e);
        }
    }

    public final String getApexModuleNameFromFilePath(Path path, Path path2) {
        if (!path.startsWith(path2)) {
            throw new IllegalArgumentException("File " + path + " is not part of an APEX.");
        }
        if (path.getNameCount() <= path2.getNameCount() + 1) {
            throw new IllegalArgumentException("File " + path + " is in the APEX partition, but not inside a module.");
        }
        return path.getName(path2.getNameCount()).toString();
    }

    public void readApexPrivAppPermissions(XmlPullParser xmlPullParser, File file, Path path) {
        String apexModuleNameFromFilePath = getApexModuleNameFromFilePath(file.toPath(), path);
        ArrayMap apexPrivilegedAppAllowlists = this.mPermissionAllowlist.getApexPrivilegedAppAllowlists();
        ArrayMap arrayMap = (ArrayMap) apexPrivilegedAppAllowlists.get(apexModuleNameFromFilePath);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            apexPrivilegedAppAllowlists.put(apexModuleNameFromFilePath, arrayMap);
        }
        readPrivAppPermissions(xmlPullParser, arrayMap);
    }

    public static boolean isSystemProcess() {
        return Process.myUid() == 1000;
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

    public final void SEServiceFeature() {
        boolean z;
        Slog.i("SystemConfig", "SEServiceFeature running");
        String trim = SystemProperties.get("ro.boot.product.hardware.sku", "").trim();
        String upperCase = SemSystemProperties.getCountryCode().toUpperCase(Locale.getDefault());
        Slog.d("SystemConfig", "NFC SKU: " + trim);
        Slog.d("SystemConfig", "eSE_COS: ");
        boolean z2 = true;
        boolean z3 = trim.startsWith("hce") && trim.contains("ese");
        if (((FeatureInfo) this.mAvailableFeatures.get("com.samsung.android.nfc.gpfelica")) != null) {
            Slog.d("SystemConfig", "support GP Felica");
            z = true;
        } else {
            z = false;
        }
        boolean z4 = "JAPAN".equals(upperCase) || "JP".equals(upperCase);
        Slog.d("SystemConfig", "countryCode: " + upperCase);
        Slog.d("SystemConfig", "isJapan: " + z4);
        if ((!z3 || z4) && !z) {
            z2 = false;
        }
        Slog.d("SystemConfig", "support eSE: " + z2);
        if (z2) {
            addFeature("android.hardware.se.omapi.ese", 0);
            Slog.i("SystemConfig", "add eSE secure element feature");
        } else {
            removeFeature("android.hardware.se.omapi.ese");
            Slog.i("SystemConfig", "removed eSE secure element feature: not support se");
        }
    }
}
