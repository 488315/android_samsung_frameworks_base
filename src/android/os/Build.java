package android.os;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.media.midi.MidiDeviceInfo;
import android.os.IDeviceIdentifiersPolicyService;
import android.sysprop.BackportedFixesProperties;
import android.sysprop.DeviceProperties;
import android.sysprop.SocProperties;
import android.sysprop.TelephonyProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.util.FrameworkStatsLog;
import dalvik.system.VMRuntime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class Build {
    public static final int BACKPORTED_FIX_STATUS_FIXED = 1;
    public static final int BACKPORTED_FIX_STATUS_NOT_APPLICABLE = 2;
    public static final int BACKPORTED_FIX_STATUS_NOT_FIXED = 3;
    public static final int BACKPORTED_FIX_STATUS_UNKNOWN = 0;

    @Deprecated
    public static final String CPU_ABI;

    @Deprecated
    public static final String CPU_ABI2;
    public static final String FINGERPRINT;
    public static final String HOST;
    public static final int HW_TIMEOUT_MULTIPLIER;
    public static final boolean IS_ARC;
    public static final boolean IS_DEBUGGABLE;
    public static final boolean IS_ENG;
    public static final boolean IS_TREBLE_ENABLED;
    public static final boolean IS_USER;
    public static final boolean IS_USERDEBUG;

    @SystemApi
    public static final boolean PERMISSIONS_REVIEW_REQUIRED = true;
    public static final String[] SUPPORTED_32_BIT_ABIS;
    public static final String[] SUPPORTED_64_BIT_ABIS;
    private static final String TAG = "Build";
    public static final String TAGS;
    public static final long TIME;
    public static final String TYPE;
    public static final String UNKNOWN = "unknown";
    public static final String USER;
    public static final int VENDOR_API_2024_Q2 = 202404;
    public static final String ID = getString("ro.build.id");
    public static final String DISPLAY = getString("ro.build.display.id");
    public static final String PRODUCT = getString("ro.product.name");
    public static final String PRODUCT_FOR_ATTESTATION = getVendorDeviceIdProperty("name");
    public static final String DEVICE = getString("ro.product.device");
    public static final String DEVICE_FOR_ATTESTATION = getVendorDeviceIdProperty("device");
    public static final String BOARD = getString("ro.product.board");
    public static final String MANUFACTURER = getString("ro.product.manufacturer");
    public static final String MANUFACTURER_FOR_ATTESTATION = getVendorDeviceIdProperty(MidiDeviceInfo.PROPERTY_MANUFACTURER);
    public static final String BRAND = getString("ro.product.brand");
    public static final String BRAND_FOR_ATTESTATION = getVendorDeviceIdProperty("brand");
    public static final String MODEL = getString("ro.product.model");
    public static final String MODEL_FOR_ATTESTATION = getVendorDeviceIdProperty("model");
    public static final String SOC_MANUFACTURER = SocProperties.soc_manufacturer().orElse("unknown");
    public static final String SOC_MODEL = SocProperties.soc_model().orElse("unknown");
    public static final String BOOTLOADER = getString("ro.bootloader");

    @Deprecated
    public static final String RADIO = joinListOrElse(TelephonyProperties.baseband_version(), "unknown");
    public static final String HARDWARE = getString("ro.hardware");
    public static final String SKU = getString("ro.boot.hardware.sku");
    public static final String ODM_SKU = getString("ro.boot.product.hardware.sku");
    public static final boolean IS_EMULATOR = getString("ro.boot.qemu").equals("1");

    @Deprecated
    public static final String SERIAL = getString("no.such.thing");
    public static final String[] SUPPORTED_ABIS = getStringList("ro.product.cpu.abilist", ",");

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackportedFixStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SdkIntFull {
    }

    public static class VERSION_CODES {
        public static final int BAKLAVA = 36;
        public static final int BASE = 1;
        public static final int BASE_1_1 = 2;
        public static final int CUPCAKE = 3;
        public static final int CUR_DEVELOPMENT = 10000;
        public static final int DONUT = 4;
        public static final int ECLAIR = 5;
        public static final int ECLAIR_0_1 = 6;
        public static final int ECLAIR_MR1 = 7;
        public static final int FROYO = 8;
        public static final int GINGERBREAD = 9;
        public static final int GINGERBREAD_MR1 = 10;
        public static final int HONEYCOMB = 11;
        public static final int HONEYCOMB_MR1 = 12;
        public static final int HONEYCOMB_MR2 = 13;
        public static final int ICE_CREAM_SANDWICH = 14;
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;
        public static final int JELLY_BEAN = 16;
        public static final int JELLY_BEAN_MR1 = 17;
        public static final int JELLY_BEAN_MR2 = 18;
        public static final int KITKAT = 19;
        public static final int KITKAT_WATCH = 20;
        public static final int L = 21;
        public static final int LOLLIPOP = 21;
        public static final int LOLLIPOP_MR1 = 22;
        public static final int M = 23;
        public static final int N = 24;
        public static final int N_MR1 = 25;
        public static final int O = 26;
        public static final int O_MR1 = 27;
        public static final int P = 28;
        public static final int Q = 29;
        public static final int R = 30;
        public static final int S = 31;
        public static final int S_V2 = 32;
        public static final int TIRAMISU = 33;
        public static final int UPSIDE_DOWN_CAKE = 34;
        public static final int VANILLA_ICE_CREAM = 35;
    }

    static {
        String[] stringList = getStringList("ro.product.cpu.abilist32", ",");
        SUPPORTED_32_BIT_ABIS = stringList;
        String[] stringList2 = getStringList("ro.product.cpu.abilist64", ",");
        SUPPORTED_64_BIT_ABIS = stringList2;
        if (Process.is64Bit()) {
            stringList = stringList2;
        }
        CPU_ABI = stringList[0];
        if (stringList.length > 1) {
            CPU_ABI2 = stringList[1];
        } else {
            CPU_ABI2 = "";
        }
        String string = getString("ro.build.type");
        TYPE = string;
        TAGS = getString("ro.build.tags");
        FINGERPRINT = deriveFingerprint();
        HW_TIMEOUT_MULTIPLIER = SystemProperties.getInt("ro.hw_timeout_multiplier", 1);
        IS_TREBLE_ENABLED = SystemProperties.getBoolean("ro.treble.enabled", false);
        TIME = getLong("ro.build.date.utc") * 1000;
        USER = getString("ro.build.user");
        HOST = getString("ro.build.host");
        IS_DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0) == 1;
        IS_ENG = "eng".equals(string);
        IS_USERDEBUG = "userdebug".equals(string);
        IS_USER = "user".equals(string);
        IS_ARC = SystemProperties.getBoolean("ro.boot.container", false);
    }

    public static String getSerial() {
        IDeviceIdentifiersPolicyService asInterface = IDeviceIdentifiersPolicyService.Stub.asInterface(ServiceManager.getService(Context.DEVICE_IDENTIFIERS_SERVICE));
        try {
            Application currentApplication = ActivityThread.currentApplication();
            return asInterface.getSerialForPackage(currentApplication != null ? currentApplication.getPackageName() : null, null);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return "unknown";
        }
    }

    public static boolean is64BitAbi(String str) {
        return VMRuntime.is64BitAbi(str);
    }

    public static class VERSION {
        public static final String[] ACTIVE_CODENAMES;
        private static final String[] ALL_CODENAMES;
        public static final String CODENAME;

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public static final int DEVICE_INITIAL_SDK_INT;

        @SystemApi
        public static final Set<String> KNOWN_CODENAMES;
        public static final int MIN_SUPPORTED_TARGET_SDK_INT;

        @SystemApi
        public static final String PREVIEW_SDK_FINGERPRINT;
        public static final int PREVIEW_SDK_INT;
        public static final int RESOURCES_SDK_INT;
        public static final int SDK_INT;
        public static final int SDK_INT_FULL;
        public static final int SEM_FIRST_SDK_INT;
        public static final int SEM_INT;
        public static final int SEM_PLATFORM_INT;
        public static final String INCREMENTAL = Build.getString("ro.build.version.incremental");
        public static final String RELEASE = Build.getString("ro.build.version.release");
        public static final String RELEASE_OR_CODENAME = Build.getString("ro.build.version.release_or_codename");
        public static final String RELEASE_OR_PREVIEW_DISPLAY = Build.getString("ro.build.version.release_or_preview_display");
        public static final String BASE_OS = SystemProperties.get("ro.build.version.base_os", "");
        public static final String SECURITY_PATCH = SystemProperties.get("ro.build.version.security_patch", "");
        public static final String SECURITY_INDEX = SystemProperties.get("ro.build.version.security_index", "");
        public static final int MEDIA_PERFORMANCE_CLASS = DeviceProperties.media_performance_class().orElse(0).intValue();

        @Deprecated
        public static final String SDK = Build.getString("ro.build.version.sdk");

        static {
            int i = SystemProperties.getInt("ro.build.version.sdk", 0);
            SDK_INT = i;
            SDK_INT_FULL = Build.parseFullVersion(SystemProperties.get("ro.build.version.sdk_full", ""));
            int i2 = SystemProperties.getInt("ro.product.first_api_level", 0);
            DEVICE_INITIAL_SDK_INT = i2;
            SEM_FIRST_SDK_INT = i2;
            PREVIEW_SDK_INT = SystemProperties.getInt("ro.build.version.preview_sdk", 0);
            SEM_INT = SystemProperties.getInt("ro.build.version.sem", 0);
            SEM_PLATFORM_INT = SystemProperties.getInt("ro.build.version.sep", 0);
            PREVIEW_SDK_FINGERPRINT = SystemProperties.get("ro.build.version.preview_sdk_fingerprint", "REL");
            CODENAME = Build.getString("ro.build.version.codename");
            KNOWN_CODENAMES = new ArraySet(Build.getStringList("ro.build.version.known_codenames", ","));
            String[] stringList = Build.getStringList("ro.build.version.all_codenames", ",");
            ALL_CODENAMES = stringList;
            if ("REL".equals(stringList[0])) {
                stringList = new String[0];
            }
            ACTIVE_CODENAMES = stringList;
            RESOURCES_SDK_INT = i + stringList.length;
            MIN_SUPPORTED_TARGET_SDK_INT = SystemProperties.getInt("ro.build.version.min_supported_target_sdk", 0);
        }
    }

    public static class VERSION_CODES_FULL {
        public static final int BAKLAVA = 3600000;
        public static final int BASE = 100000;
        public static final int BASE_1_1 = 200000;
        public static final int CUPCAKE = 300000;
        public static final int DONUT = 400000;
        public static final int ECLAIR = 500000;
        public static final int ECLAIR_0_1 = 600000;
        public static final int ECLAIR_MR1 = 700000;
        public static final int FROYO = 800000;
        public static final int GINGERBREAD = 900000;
        public static final int GINGERBREAD_MR1 = 1000000;
        public static final int HONEYCOMB = 1100000;
        public static final int HONEYCOMB_MR1 = 1200000;
        public static final int HONEYCOMB_MR2 = 1300000;
        public static final int ICE_CREAM_SANDWICH = 1400000;
        public static final int ICE_CREAM_SANDWICH_MR1 = 1500000;
        public static final int JELLY_BEAN = 1600000;
        public static final int JELLY_BEAN_MR1 = 1700000;
        public static final int JELLY_BEAN_MR2 = 1800000;
        public static final int KITKAT = 1900000;
        public static final int KITKAT_WATCH = 2000000;
        public static final int LOLLIPOP = 2100000;
        public static final int LOLLIPOP_MR1 = 2200000;
        public static final int M = 2300000;
        public static final int N = 2400000;
        public static final int N_MR1 = 2500000;
        public static final int O = 2600000;
        public static final int O_MR1 = 2700000;
        public static final int P = 2800000;
        public static final int Q = 2900000;
        public static final int R = 3000000;
        public static final int S = 3100000;
        private static final int SDK_INT_MULTIPLIER = 100000;
        public static final int S_V2 = 3200000;
        public static final int TIRAMISU = 3300000;
        public static final int UPSIDE_DOWN_CAKE = 3400000;
        public static final int VANILLA_ICE_CREAM = 3500000;

        private VERSION_CODES_FULL() {
        }
    }

    public static int getMajorSdkVersion(int i) {
        return i / 100000;
    }

    public static int getMinorSdkVersion(int i) {
        return i % 100000;
    }

    public static int parseFullVersion(String str) {
        int i;
        int indexOf = str.indexOf(46);
        int i2 = 0;
        try {
            if (indexOf == -1) {
                i = Integer.parseInt(str);
            } else {
                int parseInt = Integer.parseInt(str.substring(0, indexOf));
                i2 = Integer.parseInt(str.substring(indexOf + 1));
                i = parseInt;
            }
            if (i < 0) {
                throw new NumberFormatException("negative major version");
            }
            if (i >= 21474) {
                throw new NumberFormatException("major version too large, must be less than 21474");
            }
            if (i2 < 0) {
                throw new NumberFormatException("negative minor version");
            }
            if (i2 < 100000) {
                return (i * 100000) + i2;
            }
            throw new NumberFormatException("minor version too large, must be less than 100000");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("failed to parse '" + str + "' as a major.minor version code", e);
        }
    }

    public static String fullVersionToString(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("failed to convert '" + i + "' to string: not a valid major.minor version code");
        }
        return String.format("%d.%d", Integer.valueOf(getMajorSdkVersion(i)), Integer.valueOf(getMinorSdkVersion(i)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static int getBackportedFixStatus(long j) {
        int callingUid = Binder.getCallingUid();
        int isBitSet = (j <= 0 || j > 1023) ? 0 : isBitSet(BackportedFixesProperties.alias_bitset(), (int) j);
        FrameworkStatsLog.write(987, callingUid, j, isBitSet);
        return isBitSet;
    }

    private static boolean isBitSet(List<Long> list, int i) {
        int i2;
        if (i >= 0 && list.size() > (i2 = i >> 6)) {
            return ((1 << i) & list.get(i2).longValue()) != 0;
        }
        return false;
    }

    private static String deriveFingerprint() {
        String str = SystemProperties.get("ro.build.fingerprint");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        return getString("ro.product.brand") + '/' + getString("ro.product.name") + '/' + getString("ro.product.device") + ShortcutConstants.SERVICES_SEPARATOR + getString("ro.build.version.release") + '/' + getString("ro.build.id") + '/' + getString("ro.build.version.incremental") + ShortcutConstants.SERVICES_SEPARATOR + getString("ro.build.type") + '/' + getString("ro.build.tags");
    }

    public static void ensureFingerprintProperty() {
        if (TextUtils.isEmpty(SystemProperties.get("ro.build.fingerprint"))) {
            try {
                SystemProperties.set("ro.build.fingerprint", FINGERPRINT);
            } catch (IllegalArgumentException e) {
                Slog.e(TAG, "Failed to set fingerprint property", e);
            }
        }
    }

    public static boolean isBuildConsistent() {
        if (IS_ENG) {
            return true;
        }
        if (IS_TREBLE_ENABLED) {
            int verifyBuildAtBoot = VintfObject.verifyBuildAtBoot();
            if (verifyBuildAtBoot != 0) {
                Slog.e(TAG, "Vendor interface is incompatible, error=" + String.valueOf(verifyBuildAtBoot));
            }
            return verifyBuildAtBoot == 0;
        }
        String str = SystemProperties.get("ro.system.build.fingerprint");
        String str2 = SystemProperties.get("ro.vendor.build.fingerprint");
        SystemProperties.get("ro.bootimage.build.fingerprint");
        SystemProperties.get("ro.build.expect.bootloader");
        SystemProperties.get("ro.bootloader");
        SystemProperties.get("ro.build.expect.baseband");
        joinListOrElse(TelephonyProperties.baseband_version(), "");
        if (TextUtils.isEmpty(str)) {
            Slog.e(TAG, "Required ro.system.build.fingerprint is empty!");
            return false;
        }
        if (TextUtils.isEmpty(str2) || Objects.equals(str, str2)) {
            return true;
        }
        Slog.e(TAG, "Mismatched fingerprints; system reported " + str + " but vendor reported " + str2);
        return false;
    }

    public static class Partition {
        public static final String PARTITION_NAME_BOOTIMAGE = "bootimage";
        public static final String PARTITION_NAME_ODM = "odm";
        public static final String PARTITION_NAME_OEM = "oem";
        public static final String PARTITION_NAME_PRODUCT = "product";
        public static final String PARTITION_NAME_SYSTEM = "system";
        public static final String PARTITION_NAME_SYSTEM_EXT = "system_ext";
        public static final String PARTITION_NAME_VENDOR = "vendor";
        private final String mFingerprint;
        private final String mName;
        private final long mTimeMs;

        private Partition(String str, String str2, long j) {
            this.mName = str;
            this.mFingerprint = str2;
            this.mTimeMs = j;
        }

        public String getName() {
            return this.mName;
        }

        public String getFingerprint() {
            return this.mFingerprint;
        }

        public long getBuildTimeMillis() {
            return this.mTimeMs;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Partition)) {
                return false;
            }
            Partition partition = (Partition) obj;
            return this.mName.equals(partition.mName) && this.mFingerprint.equals(partition.mFingerprint) && this.mTimeMs == partition.mTimeMs;
        }

        public int hashCode() {
            return Objects.hash(this.mName, this.mFingerprint, Long.valueOf(this.mTimeMs));
        }
    }

    public static List<Partition> getFingerprintedPartitions() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = {Partition.PARTITION_NAME_BOOTIMAGE, Partition.PARTITION_NAME_ODM, "product", Partition.PARTITION_NAME_SYSTEM_EXT, "system", "vendor"};
        for (int i = 0; i < 6; i++) {
            String str = strArr[i];
            String str2 = SystemProperties.get("ro." + str + ".build.fingerprint");
            if (!TextUtils.isEmpty(str2)) {
                arrayList.add(new Partition(str, str2, 1000 * getLong("ro." + str + ".build.date.utc")));
            }
        }
        return arrayList;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static boolean isDebuggable() {
        return IS_DEBUGGABLE;
    }

    public static String getRadioVersion() {
        return joinListOrElse(TelephonyProperties.baseband_version(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getString(String str) {
        return SystemProperties.get(str, "unknown");
    }

    private static String getVendorDeviceIdProperty(String str) {
        String string = getString(TextUtils.formatSimple("ro.product.%s_for_attestation", str));
        return string.equals("unknown") ? getString(TextUtils.formatSimple("ro.product.vendor.%s", str)) : string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] getStringList(String str, String str2) {
        String str3 = SystemProperties.get(str);
        if (str3.isEmpty()) {
            return new String[0];
        }
        return str3.split(str2);
    }

    private static long getLong(String str) {
        try {
            return Long.parseLong(SystemProperties.get(str));
        } catch (NumberFormatException unused) {
            return -1L;
        }
    }

    private static <T> String joinListOrElse(List<T> list, String str) {
        String str2 = (String) list.stream().map(new Function() { // from class: android.os.Build$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Build.lambda$joinListOrElse$0(obj);
            }
        }).collect(Collectors.joining(","));
        return str2.isEmpty() ? str : str2;
    }

    static /* synthetic */ String lambda$joinListOrElse$0(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
