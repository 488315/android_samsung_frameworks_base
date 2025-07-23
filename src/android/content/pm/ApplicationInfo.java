package android.content.pm;

import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes.dex */
public class ApplicationInfo extends PackageItemInfo implements Parcelable {
    public static final int AUTO_REVOKE_ALLOWED = 0;
    public static final int AUTO_REVOKE_DISALLOWED = 2;
    public static final int AUTO_REVOKE_DISCOURAGED = 1;
    public static final int CATEGORY_ACCESSIBILITY = 8;
    public static final int CATEGORY_AUDIO = 1;
    public static final int CATEGORY_GAME = 0;
    public static final int CATEGORY_IMAGE = 3;
    public static final int CATEGORY_MAPS = 6;
    public static final int CATEGORY_NEWS = 5;
    public static final int CATEGORY_PRODUCTIVITY = 7;
    public static final int CATEGORY_SOCIAL = 4;
    public static final int CATEGORY_UNDEFINED = -1;
    public static final int CATEGORY_VIDEO = 2;
    public static final int FLAG_ALLOW_BACKUP = 32768;
    public static final int FLAG_ALLOW_CLEAR_USER_DATA = 64;
    public static final int FLAG_ALLOW_TASK_REPARENTING = 32;
    public static final int FLAG_DEBUGGABLE = 2;
    public static final int FLAG_EXTERNAL_STORAGE = 262144;
    public static final int FLAG_EXTRACT_NATIVE_LIBS = 268435456;
    public static final int FLAG_FACTORY_TEST = 16;
    public static final int FLAG_FULL_BACKUP_ONLY = 67108864;
    public static final int FLAG_HARDWARE_ACCELERATED = 536870912;
    public static final int FLAG_HAS_CODE = 4;
    public static final int FLAG_INSTALLED = 8388608;
    public static final int FLAG_IS_DATA_ONLY = 16777216;

    @Deprecated
    public static final int FLAG_IS_GAME = 33554432;
    public static final int FLAG_KILL_AFTER_RESTORE = 65536;
    public static final int FLAG_LARGE_HEAP = 1048576;
    public static final int FLAG_MULTIARCH = Integer.MIN_VALUE;
    public static final int FLAG_PERSISTENT = 8;
    public static final int FLAG_RESIZEABLE_FOR_SCREENS = 4096;
    public static final int FLAG_RESTORE_ANY_VERSION = 131072;
    public static final int FLAG_STOPPED = 2097152;
    public static final int FLAG_SUPPORTS_LARGE_SCREENS = 2048;
    public static final int FLAG_SUPPORTS_NORMAL_SCREENS = 1024;
    public static final int FLAG_SUPPORTS_RTL = 4194304;

    @Deprecated
    public static final int FLAG_SUPPORTS_SCREEN_DENSITIES = 8192;
    public static final int FLAG_SUPPORTS_SMALL_SCREENS = 512;
    public static final int FLAG_SUPPORTS_XLARGE_SCREENS = 524288;
    public static final int FLAG_SUSPENDED = 1073741824;
    public static final int FLAG_SYSTEM = 1;
    public static final int FLAG_TEST_ONLY = 256;
    public static final int FLAG_UPDATED_SYSTEM_APP = 128;
    public static final int FLAG_USES_CLEARTEXT_TRAFFIC = 134217728;
    public static final int FLAG_VM_SAFE_MODE = 16384;
    public static final int GWP_ASAN_ALWAYS = 1;
    public static final int GWP_ASAN_DEFAULT = -1;
    public static final int GWP_ASAN_NEVER = 0;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_DEFAULT = -1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_DISABLED = 0;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_ENABLED = 2;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_JUST_WARN = 1;
    private static final int HIDDEN_API_ENFORCEMENT_MAX = 2;
    private static final int HIDDEN_API_ENFORCEMENT_MIN = -1;
    public static final int MEMTAG_ASYNC = 1;
    public static final int MEMTAG_DEFAULT = -1;
    public static final int MEMTAG_OFF = 0;
    public static final int MEMTAG_SYNC = 2;
    public static final String METADATA_PRELOADED_FONTS = "preloaded_fonts";
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_ELF_NOT_ALIGNED = 4;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_ERROR = -1;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_MANIFEST_OVERRIDE_DISABLED = 64;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_MANIFEST_OVERRIDE_ENABLED = 32;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_MAX = 128;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_SETTINGS_OVERRIDE_DISABLED = 16;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_SETTINGS_OVERRIDE_ENABLED = 8;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_UNCOMPRESSED_LIBS_NOT_ALIGNED = 2;
    public static final int PAGE_SIZE_APP_COMPAT_FLAG_UNDEFINED = 0;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE = 1024;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION = 4096;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE = 2048;
    public static final int PRIVATE_FLAG_ALLOW_AUDIO_PLAYBACK_CAPTURE = 134217728;
    public static final int PRIVATE_FLAG_ALLOW_CLEAR_USER_DATA_ON_FAILED_RESTORE = 67108864;
    public static final int PRIVATE_FLAG_ALLOW_NATIVE_HEAP_POINTER_TAGGING = Integer.MIN_VALUE;
    public static final int PRIVATE_FLAG_BACKUP_IN_FOREGROUND = 8192;
    public static final int PRIVATE_FLAG_CANT_SAVE_STATE = 2;
    public static final int PRIVATE_FLAG_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = 32;
    public static final int PRIVATE_FLAG_DIRECT_BOOT_AWARE = 64;
    public static final int PRIVATE_FLAG_EXT_ALLOWLISTED_FOR_HIDDEN_APIS = 16;
    public static final int PRIVATE_FLAG_EXT_ATTRIBUTIONS_ARE_USER_VISIBLE = 4;
    public static final int PRIVATE_FLAG_EXT_CPU_OVERRIDE = 32;
    public static final int PRIVATE_FLAG_EXT_ENABLE_ON_BACK_INVOKED_CALLBACK = 8;
    public static final int PRIVATE_FLAG_EXT_NOT_LAUNCHED = 64;
    public static final int PRIVATE_FLAG_EXT_PROFILEABLE = 1;
    public static final int PRIVATE_FLAG_EXT_REQUEST_FOREGROUND_SERVICE_EXEMPTION = 2;
    public static final int PRIVATE_FLAG_HAS_DOMAIN_URLS = 16;
    public static final int PRIVATE_FLAG_HAS_FRAGILE_USER_DATA = 16777216;
    public static final int PRIVATE_FLAG_HIDDEN = 1;
    public static final int PRIVATE_FLAG_INSTANT = 128;
    public static final int PRIVATE_FLAG_ISOLATED_SPLIT_LOADING = 32768;
    public static final int PRIVATE_FLAG_IS_RESOURCE_OVERLAY = 268435456;
    public static final int PRIVATE_FLAG_ODM = 1073741824;
    public static final int PRIVATE_FLAG_OEM = 131072;
    public static final int PRIVATE_FLAG_PARTIALLY_DIRECT_BOOT_AWARE = 256;
    public static final int PRIVATE_FLAG_PRIVILEGED = 8;
    public static final int PRIVATE_FLAG_PRODUCT = 524288;
    public static final int PRIVATE_FLAG_PROFILEABLE_BY_SHELL = 8388608;
    public static final int PRIVATE_FLAG_REQUEST_LEGACY_EXTERNAL_STORAGE = 536870912;
    public static final int PRIVATE_FLAG_REQUIRED_FOR_SYSTEM_USER = 512;
    public static final int PRIVATE_FLAG_SIGNED_WITH_PLATFORM_KEY = 1048576;
    public static final int PRIVATE_FLAG_STATIC_SHARED_LIBRARY = 16384;
    public static final int PRIVATE_FLAG_SYSTEM_EXT = 2097152;
    public static final int PRIVATE_FLAG_USES_NON_SDK_API = 4194304;
    public static final int PRIVATE_FLAG_USE_EMBEDDED_DEX = 33554432;
    public static final int PRIVATE_FLAG_VENDOR = 262144;
    public static final int PRIVATE_FLAG_VIRTUAL_PRELOAD = 65536;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_DEFAULT = 0;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_NOT_REQUESTED = 2;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_REQUESTED = 1;
    public static final int ZEROINIT_DEFAULT = -1;
    public static final int ZEROINIT_DISABLED = 0;
    public static final int ZEROINIT_ENABLED = 1;
    public boolean allowCrossUidActivitySwitchFromBelow;
    public String appComponentFactory;
    public String backupAgentName;
    public int category;
    public String classLoaderName;
    public String className;
    public int compatibleWidthLimitDp;
    public int compileSdkVersion;
    public String compileSdkVersionCodename;
    public long createTimestamp;

    @SystemApi
    public String credentialProtectedDataDir;
    public boolean crossProfile;
    public String dataDir;
    public int dataExtractionRulesRes;
    public int descriptionRes;
    public String deviceProtectedDataDir;
    public boolean enabled;
    public int enabledSetting;
    public int flags;
    public int fullBackupContent;
    private int gwpAsanMode;
    public boolean hiddenUntilInstalled;
    public int iconRes;
    public int installLocation;
    public int largestWidthLimitDp;
    private int localeConfigRes;
    public long longVersionCode;
    private ArrayMap<String, String> mAppClassNamesByProcess;
    private int mHiddenApiPolicy;
    private Set<String> mKnownActivityEmbeddingCerts;
    private int mPageSizeAppCompatFlags;
    public String manageSpaceActivityName;
    public float maxAspectRatio;
    private int memtagMode;
    public float minAspectRatio;
    public int minSdkVersion;
    private int nativeHeapZeroInitialized;
    public String nativeLibraryDir;
    public String nativeLibraryRootDir;
    public boolean nativeLibraryRootRequiresIsa;
    public int networkSecurityConfigRes;
    public List<SharedLibraryInfo> optionalSharedLibraryInfos;
    public String[] overlayPaths;
    public String permission;
    public String primaryCpuAbi;
    public int privateFlags;
    public int privateFlagsExt;
    public String processName;
    public String publicSourceDir;
    private Boolean requestRawExternalStorageAccess;
    public int requiresSmallestWidthDp;
    public String[] resourceDirs;
    public int roundIconRes;
    public String scanPublicSourceDir;
    public String scanSourceDir;
    public String seInfo;
    public String seInfoUser;
    public String secondaryCpuAbi;
    public String secondaryNativeLibraryDir;
    public String[] sharedLibraryFiles;
    public List<SharedLibraryInfo> sharedLibraryInfos;
    public String sourceDir;
    public String[] splitClassLoaderNames;
    public SparseArray<int[]> splitDependencies;
    public String[] splitNames;
    public String[] splitPublicSourceDirs;
    public String[] splitSourceDirs;
    public UUID storageUuid;

    @SystemApi
    public int targetSandboxVersion;
    public int targetSdkVersion;
    public String taskAffinity;
    public int theme;
    public int uiOptions;
    public int uid;

    @Deprecated
    public int versionCode;
    public String volumeUuid;
    public String zygotePreloadName;
    private static final Parcelling.BuiltIn.ForBoolean sForBoolean = (Parcelling.BuiltIn.ForBoolean) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForBoolean.class);
    private static final Parcelling.BuiltIn.ForStringSet sForStringSet = (Parcelling.BuiltIn.ForStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelable.Creator<ApplicationInfo> CREATOR = new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationInfoPrivateFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationInfoPrivateFlagsExt {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GwpAsanMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HiddenApiEnforcementPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MemtagMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NativeHeapZeroInitialized {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PageSizeAppCompatFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RawExternalStorage {
    }

    public static boolean isValidHiddenApiEnforcementPolicy(int i) {
        return i >= -1 && i <= 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo
    public ApplicationInfo getApplicationInfo() {
        return this;
    }

    public static CharSequence getCategoryTitle(Context context, int i) {
        switch (i) {
            case 0:
                return context.getText(R.string.app_category_game);
            case 1:
                return context.getText(R.string.app_category_audio);
            case 2:
                return context.getText(R.string.app_category_video);
            case 3:
                return context.getText(R.string.app_category_image);
            case 4:
                return context.getText(R.string.app_category_social);
            case 5:
                return context.getText(R.string.app_category_news);
            case 6:
                return context.getText(R.string.app_category_maps);
            case 7:
                return context.getText(R.string.app_category_productivity);
            case 8:
                return context.getText(R.string.app_category_accessibility);
            default:
                return null;
        }
    }

    public void dump(Printer printer, String str) {
        dump(printer, str, 3);
    }

    public void dump(Printer printer, String str, int i) {
        super.dumpFront(printer, str);
        int i2 = i & 1;
        if (i2 != 0) {
            if (this.className != null) {
                printer.println(str + "className=" + this.className);
            }
            for (int i3 = 0; i3 < ArrayUtils.size(this.mAppClassNamesByProcess); i3++) {
                printer.println(str + "  process=" + this.mAppClassNamesByProcess.keyAt(i3) + " className=" + this.mAppClassNamesByProcess.valueAt(i3));
            }
        }
        if (this.permission != null) {
            printer.println(str + "permission=" + this.permission);
        }
        printer.println(str + "processName=" + this.processName);
        if (i2 != 0) {
            printer.println(str + "taskAffinity=" + this.taskAffinity);
        }
        printer.println(str + "uid=" + this.uid + " flags=0x" + Integer.toHexString(this.flags) + " privateFlags=0x" + Integer.toHexString(this.privateFlags) + " theme=0x" + Integer.toHexString(this.theme));
        if (i2 != 0) {
            printer.println(str + "requiresSmallestWidthDp=" + this.requiresSmallestWidthDp + " compatibleWidthLimitDp=" + this.compatibleWidthLimitDp + " largestWidthLimitDp=" + this.largestWidthLimitDp);
        }
        printer.println(str + "sourceDir=" + this.sourceDir);
        if (!Objects.equals(this.sourceDir, this.publicSourceDir)) {
            printer.println(str + "publicSourceDir=" + this.publicSourceDir);
        }
        if (!ArrayUtils.isEmpty(this.splitSourceDirs)) {
            printer.println(str + "splitSourceDirs=" + Arrays.toString(this.splitSourceDirs));
        }
        if (!ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            printer.println(str + "splitPublicSourceDirs=" + Arrays.toString(this.splitPublicSourceDirs));
        }
        if (this.resourceDirs != null) {
            printer.println(str + "resourceDirs=" + Arrays.toString(this.resourceDirs));
        }
        if (this.overlayPaths != null) {
            printer.println(str + "overlayPaths=" + Arrays.toString(this.overlayPaths));
        }
        if (i2 != 0 && this.seInfo != null) {
            printer.println(str + "seinfo=" + this.seInfo);
            printer.println(str + "seinfoUser=" + this.seInfoUser);
        }
        printer.println(str + "dataDir=" + this.dataDir);
        if (i2 != 0) {
            printer.println(str + "deviceProtectedDataDir=" + this.deviceProtectedDataDir);
            printer.println(str + "credentialProtectedDataDir=" + this.credentialProtectedDataDir);
            if (this.sharedLibraryFiles != null) {
                printer.println(str + "sharedLibraryFiles=" + Arrays.toString(this.sharedLibraryFiles));
            }
        }
        if (this.classLoaderName != null) {
            printer.println(str + "classLoaderName=" + this.classLoaderName);
        }
        if (!ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            printer.println(str + "splitClassLoaderNames=" + Arrays.toString(this.splitClassLoaderNames));
        }
        printer.println(str + "enabled=" + this.enabled + " minSdkVersion=" + this.minSdkVersion + " targetSdkVersion=" + this.targetSdkVersion + " versionCode=" + this.longVersionCode + " targetSandboxVersion=" + this.targetSandboxVersion);
        if (i2 != 0) {
            if (this.manageSpaceActivityName != null) {
                printer.println(str + "manageSpaceActivityName=" + this.manageSpaceActivityName);
            }
            if (this.descriptionRes != 0) {
                printer.println(str + "description=0x" + Integer.toHexString(this.descriptionRes));
            }
            if (this.uiOptions != 0) {
                printer.println(str + "uiOptions=0x" + Integer.toHexString(this.uiOptions));
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("supportsRtl=");
            sb.append(hasRtlSupport() ? "true" : "false");
            printer.println(sb.toString());
            if (this.fullBackupContent > 0) {
                printer.println(str + "fullBackupContent=@xml/" + this.fullBackupContent);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("fullBackupContent=");
                sb2.append(this.fullBackupContent < 0 ? "false" : "true");
                printer.println(sb2.toString());
            }
            if (this.dataExtractionRulesRes != 0) {
                printer.println(str + "dataExtractionRules=@xml/" + this.dataExtractionRulesRes);
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("crossProfile=");
            sb3.append(this.crossProfile ? "true" : "false");
            printer.println(sb3.toString());
            if (this.networkSecurityConfigRes != 0) {
                printer.println(str + "networkSecurityConfigRes=0x" + Integer.toHexString(this.networkSecurityConfigRes));
            }
            if (this.category != -1) {
                printer.println(str + "category=" + this.category);
            }
            printer.println(str + "HiddenApiEnforcementPolicy=" + getHiddenApiEnforcementPolicy());
            printer.println(str + "usesNonSdkApi=" + usesNonSdkApi());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("allowsPlaybackCapture=");
            sb4.append(isAudioPlaybackCaptureAllowed() ? "true" : "false");
            printer.println(sb4.toString());
            if (this.gwpAsanMode != -1) {
                printer.println(str + "gwpAsanMode=" + this.gwpAsanMode);
            }
            if (this.memtagMode != -1) {
                printer.println(str + "memtagMode=" + this.memtagMode);
            }
            if (this.nativeHeapZeroInitialized != -1) {
                printer.println(str + "nativeHeapZeroInitialized=" + this.nativeHeapZeroInitialized);
            }
            if (this.requestRawExternalStorageAccess != null) {
                printer.println(str + "requestRawExternalStorageAccess=" + this.requestRawExternalStorageAccess);
            }
            if (this.localeConfigRes != 0) {
                printer.println(str + "localeConfigRes=0x" + Integer.toHexString(this.localeConfigRes));
            }
            printer.println(str + "enableOnBackInvokedCallback=" + isOnBackInvokedCallbackEnabled());
            printer.println(str + "allowCrossUidActivitySwitchFromBelow=" + this.allowCrossUidActivitySwitchFromBelow);
            printer.println(str + "mPageSizeAppCompatFlags=" + this.mPageSizeAppCompatFlags);
        }
        printer.println(str + "createTimestamp=" + this.createTimestamp);
        if (this.mKnownActivityEmbeddingCerts != null) {
            printer.println(str + "knownActivityEmbeddingCerts=" + this.mKnownActivityEmbeddingCerts);
        }
        super.dumpBack(printer, str);
    }

    @Override // android.content.pm.PackageItemInfo
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        protoOutputStream.write(1138166333442L, this.permission);
        protoOutputStream.write(1138166333443L, this.processName);
        protoOutputStream.write(1120986464260L, this.uid);
        protoOutputStream.write(1120986464261L, this.flags);
        protoOutputStream.write(1120986464262L, this.privateFlags);
        protoOutputStream.write(1120986464263L, this.theme);
        protoOutputStream.write(1138166333448L, this.sourceDir);
        if (!Objects.equals(this.sourceDir, this.publicSourceDir)) {
            protoOutputStream.write(1138166333449L, this.publicSourceDir);
        }
        if (!ArrayUtils.isEmpty(this.splitSourceDirs)) {
            for (String str : this.splitSourceDirs) {
                protoOutputStream.write(2237677961226L, str);
            }
        }
        if (!ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            for (String str2 : this.splitPublicSourceDirs) {
                protoOutputStream.write(2237677961227L, str2);
            }
        }
        String[] strArr = this.resourceDirs;
        if (strArr != null) {
            for (String str3 : strArr) {
                protoOutputStream.write(2237677961228L, str3);
            }
        }
        String[] strArr2 = this.overlayPaths;
        if (strArr2 != null) {
            for (String str4 : strArr2) {
                protoOutputStream.write(ApplicationInfoProto.OVERLAY_PATHS, str4);
            }
        }
        protoOutputStream.write(1138166333453L, this.dataDir);
        protoOutputStream.write(1138166333454L, this.classLoaderName);
        if (!ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            for (String str5 : this.splitClassLoaderNames) {
                protoOutputStream.write(ApplicationInfoProto.SPLIT_CLASS_LOADER_NAMES, str5);
            }
        }
        long start2 = protoOutputStream.start(1146756268048L);
        protoOutputStream.write(1133871366145L, this.enabled);
        protoOutputStream.write(1120986464258L, this.minSdkVersion);
        protoOutputStream.write(1120986464259L, this.targetSdkVersion);
        protoOutputStream.write(1120986464260L, this.longVersionCode);
        protoOutputStream.write(1120986464261L, this.targetSandboxVersion);
        protoOutputStream.end(start2);
        if ((i & 1) != 0) {
            long start3 = protoOutputStream.start(1146756268049L);
            String str6 = this.className;
            if (str6 != null) {
                protoOutputStream.write(1138166333441L, str6);
            }
            protoOutputStream.write(1138166333442L, this.taskAffinity);
            protoOutputStream.write(1120986464259L, this.requiresSmallestWidthDp);
            protoOutputStream.write(1120986464260L, this.compatibleWidthLimitDp);
            protoOutputStream.write(1120986464261L, this.largestWidthLimitDp);
            String str7 = this.seInfo;
            if (str7 != null) {
                protoOutputStream.write(1138166333446L, str7);
                protoOutputStream.write(1138166333447L, this.seInfoUser);
            }
            protoOutputStream.write(1138166333448L, this.deviceProtectedDataDir);
            protoOutputStream.write(1138166333449L, this.credentialProtectedDataDir);
            String[] strArr3 = this.sharedLibraryFiles;
            if (strArr3 != null) {
                for (String str8 : strArr3) {
                    protoOutputStream.write(2237677961226L, str8);
                }
            }
            String str9 = this.manageSpaceActivityName;
            if (str9 != null) {
                protoOutputStream.write(1138166333451L, str9);
            }
            int i2 = this.descriptionRes;
            if (i2 != 0) {
                protoOutputStream.write(1120986464268L, i2);
            }
            int i3 = this.uiOptions;
            if (i3 != 0) {
                protoOutputStream.write(1120986464269L, i3);
            }
            protoOutputStream.write(1133871366158L, hasRtlSupport());
            int i4 = this.fullBackupContent;
            if (i4 > 0) {
                protoOutputStream.write(1138166333455L, "@xml/" + this.fullBackupContent);
            } else {
                protoOutputStream.write(1133871366160L, i4 == 0);
            }
            int i5 = this.networkSecurityConfigRes;
            if (i5 != 0) {
                protoOutputStream.write(1120986464273L, i5);
            }
            int i6 = this.category;
            if (i6 != -1) {
                protoOutputStream.write(1120986464274L, i6);
            }
            int i7 = this.gwpAsanMode;
            if (i7 != -1) {
                protoOutputStream.write(1120986464275L, i7);
            }
            int i8 = this.memtagMode;
            if (i8 != -1) {
                protoOutputStream.write(1120986464276L, i8);
            }
            int i9 = this.nativeHeapZeroInitialized;
            if (i9 != -1) {
                protoOutputStream.write(1133871366165L, i9);
            }
            protoOutputStream.write(1133871366166L, this.allowCrossUidActivitySwitchFromBelow);
            protoOutputStream.write(1120986464279L, this.mPageSizeAppCompatFlags);
            protoOutputStream.end(start3);
        }
        if (!ArrayUtils.isEmpty(this.mKnownActivityEmbeddingCerts)) {
            Iterator<String> it = this.mKnownActivityEmbeddingCerts.iterator();
            while (it.hasNext()) {
                protoOutputStream.write(ApplicationInfoProto.KNOWN_ACTIVITY_EMBEDDING_CERTS, it.next());
            }
        }
        protoOutputStream.end(start);
    }

    public boolean hasRtlSupport() {
        return (this.flags & 4194304) == 4194304;
    }

    public boolean hasCode() {
        return (this.flags & 4) != 0;
    }

    public static class DisplayNameComparator implements Comparator<ApplicationInfo> {
        private final PackageManager mPM;
        private final Collator sCollator = Collator.getInstance();

        public DisplayNameComparator(PackageManager packageManager) {
            this.mPM = packageManager;
        }

        @Override // java.util.Comparator
        public final int compare(ApplicationInfo applicationInfo, ApplicationInfo applicationInfo2) {
            CharSequence applicationLabel = this.mPM.getApplicationLabel(applicationInfo);
            if (applicationLabel == null) {
                applicationLabel = applicationInfo.packageName;
            }
            CharSequence applicationLabel2 = this.mPM.getApplicationLabel(applicationInfo2);
            if (applicationLabel2 == null) {
                applicationLabel2 = applicationInfo2.packageName;
            }
            return this.sCollator.compare(applicationLabel.toString(), applicationLabel2.toString());
        }
    }

    public ApplicationInfo() {
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.mPageSizeAppCompatFlags = 0;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.createTimestamp = SystemClock.uptimeMillis();
    }

    public ApplicationInfo(ApplicationInfo applicationInfo) {
        super(applicationInfo);
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.mPageSizeAppCompatFlags = 0;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = applicationInfo.taskAffinity;
        this.permission = applicationInfo.permission;
        this.mKnownActivityEmbeddingCerts = applicationInfo.mKnownActivityEmbeddingCerts;
        this.processName = applicationInfo.processName;
        this.className = applicationInfo.className;
        this.theme = applicationInfo.theme;
        this.flags = applicationInfo.flags;
        this.privateFlags = applicationInfo.privateFlags;
        this.privateFlagsExt = applicationInfo.privateFlagsExt;
        this.requiresSmallestWidthDp = applicationInfo.requiresSmallestWidthDp;
        this.compatibleWidthLimitDp = applicationInfo.compatibleWidthLimitDp;
        this.largestWidthLimitDp = applicationInfo.largestWidthLimitDp;
        this.volumeUuid = applicationInfo.volumeUuid;
        this.storageUuid = applicationInfo.storageUuid;
        this.scanSourceDir = applicationInfo.scanSourceDir;
        this.scanPublicSourceDir = applicationInfo.scanPublicSourceDir;
        this.sourceDir = applicationInfo.sourceDir;
        this.publicSourceDir = applicationInfo.publicSourceDir;
        this.splitNames = applicationInfo.splitNames;
        this.splitSourceDirs = applicationInfo.splitSourceDirs;
        this.splitPublicSourceDirs = applicationInfo.splitPublicSourceDirs;
        this.splitDependencies = applicationInfo.splitDependencies;
        this.nativeLibraryDir = applicationInfo.nativeLibraryDir;
        this.secondaryNativeLibraryDir = applicationInfo.secondaryNativeLibraryDir;
        this.nativeLibraryRootDir = applicationInfo.nativeLibraryRootDir;
        this.nativeLibraryRootRequiresIsa = applicationInfo.nativeLibraryRootRequiresIsa;
        this.primaryCpuAbi = applicationInfo.primaryCpuAbi;
        this.secondaryCpuAbi = applicationInfo.secondaryCpuAbi;
        this.resourceDirs = applicationInfo.resourceDirs;
        this.overlayPaths = applicationInfo.overlayPaths;
        this.seInfo = applicationInfo.seInfo;
        this.seInfoUser = applicationInfo.seInfoUser;
        this.sharedLibraryFiles = applicationInfo.sharedLibraryFiles;
        this.sharedLibraryInfos = applicationInfo.sharedLibraryInfos;
        this.optionalSharedLibraryInfos = applicationInfo.optionalSharedLibraryInfos;
        this.dataDir = applicationInfo.dataDir;
        this.deviceProtectedDataDir = applicationInfo.deviceProtectedDataDir;
        this.credentialProtectedDataDir = applicationInfo.credentialProtectedDataDir;
        this.uid = applicationInfo.uid;
        this.minSdkVersion = applicationInfo.minSdkVersion;
        this.targetSdkVersion = applicationInfo.targetSdkVersion;
        setVersionCode(applicationInfo.longVersionCode);
        this.enabled = applicationInfo.enabled;
        this.enabledSetting = applicationInfo.enabledSetting;
        this.installLocation = applicationInfo.installLocation;
        this.manageSpaceActivityName = applicationInfo.manageSpaceActivityName;
        this.descriptionRes = applicationInfo.descriptionRes;
        this.uiOptions = applicationInfo.uiOptions;
        this.backupAgentName = applicationInfo.backupAgentName;
        this.fullBackupContent = applicationInfo.fullBackupContent;
        this.dataExtractionRulesRes = applicationInfo.dataExtractionRulesRes;
        this.crossProfile = applicationInfo.crossProfile;
        this.networkSecurityConfigRes = applicationInfo.networkSecurityConfigRes;
        this.category = applicationInfo.category;
        this.targetSandboxVersion = applicationInfo.targetSandboxVersion;
        this.classLoaderName = applicationInfo.classLoaderName;
        this.splitClassLoaderNames = applicationInfo.splitClassLoaderNames;
        this.appComponentFactory = applicationInfo.appComponentFactory;
        this.iconRes = applicationInfo.iconRes;
        this.roundIconRes = applicationInfo.roundIconRes;
        this.compileSdkVersion = applicationInfo.compileSdkVersion;
        this.compileSdkVersionCodename = applicationInfo.compileSdkVersionCodename;
        this.mHiddenApiPolicy = applicationInfo.mHiddenApiPolicy;
        this.hiddenUntilInstalled = applicationInfo.hiddenUntilInstalled;
        this.zygotePreloadName = applicationInfo.zygotePreloadName;
        this.gwpAsanMode = applicationInfo.gwpAsanMode;
        this.memtagMode = applicationInfo.memtagMode;
        this.nativeHeapZeroInitialized = applicationInfo.nativeHeapZeroInitialized;
        this.requestRawExternalStorageAccess = applicationInfo.requestRawExternalStorageAccess;
        this.localeConfigRes = applicationInfo.localeConfigRes;
        this.allowCrossUidActivitySwitchFromBelow = applicationInfo.allowCrossUidActivitySwitchFromBelow;
        this.createTimestamp = SystemClock.uptimeMillis();
        this.mPageSizeAppCompatFlags = applicationInfo.mPageSizeAppCompatFlags;
    }

    public String toString() {
        return "ApplicationInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel.maybeWriteSquashed(this)) {
            return;
        }
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.taskAffinity);
        parcel.writeString8(this.permission);
        parcel.writeString8(this.processName);
        parcel.writeString8(this.className);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.privateFlags);
        parcel.writeInt(this.privateFlagsExt);
        parcel.writeInt(this.requiresSmallestWidthDp);
        parcel.writeInt(this.compatibleWidthLimitDp);
        parcel.writeInt(this.largestWidthLimitDp);
        if (this.storageUuid != null) {
            parcel.writeInt(1);
            parcel.writeLong(this.storageUuid.getMostSignificantBits());
            parcel.writeLong(this.storageUuid.getLeastSignificantBits());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.scanSourceDir);
        parcel.writeString8(this.scanPublicSourceDir);
        parcel.writeString8(this.sourceDir);
        parcel.writeString8(this.publicSourceDir);
        parcel.writeString8Array(this.splitNames);
        parcel.writeString8Array(this.splitSourceDirs);
        parcel.writeString8Array(this.splitPublicSourceDirs);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeString8(this.nativeLibraryDir);
        parcel.writeString8(this.secondaryNativeLibraryDir);
        parcel.writeString8(this.nativeLibraryRootDir);
        parcel.writeInt(this.nativeLibraryRootRequiresIsa ? 1 : 0);
        parcel.writeString8(this.primaryCpuAbi);
        parcel.writeString8(this.secondaryCpuAbi);
        parcel.writeString8Array(this.resourceDirs);
        parcel.writeString8Array(this.overlayPaths);
        parcel.writeString8(this.seInfo);
        parcel.writeString8(this.seInfoUser);
        parcel.writeString8Array(this.sharedLibraryFiles);
        parcel.writeTypedList(this.sharedLibraryInfos);
        parcel.writeTypedList(this.optionalSharedLibraryInfos);
        parcel.writeString8(this.dataDir);
        parcel.writeString8(this.deviceProtectedDataDir);
        parcel.writeString8(this.credentialProtectedDataDir);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.minSdkVersion);
        parcel.writeInt(this.targetSdkVersion);
        parcel.writeLong(this.longVersionCode);
        parcel.writeInt(this.enabled ? 1 : 0);
        parcel.writeInt(this.enabledSetting);
        parcel.writeInt(this.installLocation);
        parcel.writeString8(this.manageSpaceActivityName);
        parcel.writeString8(this.backupAgentName);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.uiOptions);
        parcel.writeInt(this.fullBackupContent);
        parcel.writeInt(this.dataExtractionRulesRes);
        parcel.writeBoolean(this.crossProfile);
        parcel.writeInt(this.networkSecurityConfigRes);
        parcel.writeInt(this.category);
        parcel.writeInt(this.targetSandboxVersion);
        parcel.writeString8(this.classLoaderName);
        parcel.writeString8Array(this.splitClassLoaderNames);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString8(this.compileSdkVersionCodename);
        parcel.writeString8(this.appComponentFactory);
        parcel.writeInt(this.iconRes);
        parcel.writeInt(this.roundIconRes);
        parcel.writeInt(this.mHiddenApiPolicy);
        parcel.writeInt(this.hiddenUntilInstalled ? 1 : 0);
        parcel.writeString8(this.zygotePreloadName);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
        sForBoolean.parcel(this.requestRawExternalStorageAccess, parcel, i);
        parcel.writeLong(this.createTimestamp);
        ArrayMap<String, String> arrayMap = this.mAppClassNamesByProcess;
        if (arrayMap == null) {
            parcel.writeInt(0);
        } else {
            int size = arrayMap.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeString(this.mAppClassNamesByProcess.keyAt(i2));
                parcel.writeString(this.mAppClassNamesByProcess.valueAt(i2));
            }
        }
        parcel.writeInt(this.localeConfigRes);
        parcel.writeInt(this.allowCrossUidActivitySwitchFromBelow ? 1 : 0);
        parcel.writeInt(this.mPageSizeAppCompatFlags);
        sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, this.flags);
    }

    /* renamed from: android.content.pm.ApplicationInfo$1, reason: invalid class name */
    class AnonymousClass1 implements Parcelable.Creator<ApplicationInfo> {
        /* renamed from: $r8$lambda$1E1P6HJEl7Ns7qcxzJ0zM-xcHGA, reason: not valid java name */
        public static /* synthetic */ ApplicationInfo m1018$r8$lambda$1E1P6HJEl7Ns7qcxzJ0zMxcHGA(Parcel parcel) {
            return new ApplicationInfo(parcel);
        }

        AnonymousClass1() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationInfo createFromParcel(Parcel parcel) {
            return (ApplicationInfo) parcel.readSquashed(new Parcel.SquashReadHelper() { // from class: android.content.pm.ApplicationInfo$1$$ExternalSyntheticLambda0
                @Override // android.os.Parcel.SquashReadHelper
                public final Object readRawParceled(Parcel parcel2) {
                    return ApplicationInfo.AnonymousClass1.m1018$r8$lambda$1E1P6HJEl7Ns7qcxzJ0zMxcHGA(parcel2);
                }
            });
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationInfo[] newArray(int i) {
            return new ApplicationInfo[i];
        }
    }

    private ApplicationInfo(Parcel parcel) {
        super(parcel);
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.mPageSizeAppCompatFlags = 0;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = parcel.readString8();
        this.permission = parcel.readString8();
        this.processName = parcel.readString8();
        this.className = parcel.readString8();
        this.theme = parcel.readInt();
        this.flags = parcel.readInt();
        this.privateFlags = parcel.readInt();
        this.privateFlagsExt = parcel.readInt();
        this.requiresSmallestWidthDp = parcel.readInt();
        this.compatibleWidthLimitDp = parcel.readInt();
        this.largestWidthLimitDp = parcel.readInt();
        if (parcel.readInt() != 0) {
            UUID uuid = new UUID(parcel.readLong(), parcel.readLong());
            this.storageUuid = uuid;
            this.volumeUuid = StorageManager.convert(uuid);
        }
        this.scanSourceDir = parcel.readString8();
        this.scanPublicSourceDir = parcel.readString8();
        this.sourceDir = parcel.readString8();
        this.publicSourceDir = parcel.readString8();
        this.splitNames = parcel.createString8Array();
        this.splitSourceDirs = parcel.createString8Array();
        this.splitPublicSourceDirs = parcel.createString8Array();
        this.splitDependencies = parcel.readSparseArray(null, int[].class);
        this.nativeLibraryDir = parcel.readString8();
        this.secondaryNativeLibraryDir = parcel.readString8();
        this.nativeLibraryRootDir = parcel.readString8();
        this.nativeLibraryRootRequiresIsa = parcel.readInt() != 0;
        this.primaryCpuAbi = parcel.readString8();
        this.secondaryCpuAbi = parcel.readString8();
        this.resourceDirs = parcel.createString8Array();
        this.overlayPaths = parcel.createString8Array();
        this.seInfo = parcel.readString8();
        this.seInfoUser = parcel.readString8();
        this.sharedLibraryFiles = parcel.createString8Array();
        this.sharedLibraryInfos = parcel.createTypedArrayList(SharedLibraryInfo.CREATOR);
        this.optionalSharedLibraryInfos = parcel.createTypedArrayList(SharedLibraryInfo.CREATOR);
        this.dataDir = parcel.readString8();
        this.deviceProtectedDataDir = parcel.readString8();
        this.credentialProtectedDataDir = parcel.readString8();
        this.uid = parcel.readInt();
        this.minSdkVersion = parcel.readInt();
        this.targetSdkVersion = parcel.readInt();
        setVersionCode(parcel.readLong());
        this.enabled = parcel.readInt() != 0;
        this.enabledSetting = parcel.readInt();
        this.installLocation = parcel.readInt();
        this.manageSpaceActivityName = parcel.readString8();
        this.backupAgentName = parcel.readString8();
        this.descriptionRes = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.fullBackupContent = parcel.readInt();
        this.dataExtractionRulesRes = parcel.readInt();
        this.crossProfile = parcel.readBoolean();
        this.networkSecurityConfigRes = parcel.readInt();
        this.category = parcel.readInt();
        this.targetSandboxVersion = parcel.readInt();
        this.classLoaderName = parcel.readString8();
        this.splitClassLoaderNames = parcel.createString8Array();
        this.compileSdkVersion = parcel.readInt();
        this.compileSdkVersionCodename = parcel.readString8();
        this.appComponentFactory = parcel.readString8();
        this.iconRes = parcel.readInt();
        this.roundIconRes = parcel.readInt();
        this.mHiddenApiPolicy = parcel.readInt();
        this.hiddenUntilInstalled = parcel.readInt() != 0;
        this.zygotePreloadName = parcel.readString8();
        this.gwpAsanMode = parcel.readInt();
        this.memtagMode = parcel.readInt();
        this.nativeHeapZeroInitialized = parcel.readInt();
        this.requestRawExternalStorageAccess = sForBoolean.unparcel(parcel);
        this.createTimestamp = parcel.readLong();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mAppClassNamesByProcess = new ArrayMap<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mAppClassNamesByProcess.put(parcel.readString(), parcel.readString());
            }
        }
        this.localeConfigRes = parcel.readInt();
        this.allowCrossUidActivitySwitchFromBelow = parcel.readInt() != 0;
        this.mPageSizeAppCompatFlags = parcel.readInt();
        Set<String> unparcel = sForStringSet.unparcel(parcel);
        this.mKnownActivityEmbeddingCerts = unparcel;
        if (unparcel.isEmpty()) {
            this.mKnownActivityEmbeddingCerts = null;
        }
    }

    public CharSequence loadDescription(PackageManager packageManager) {
        CharSequence text;
        if (this.descriptionRes == 0 || (text = packageManager.getText(this.packageName, this.descriptionRes, this)) == null) {
            return null;
        }
        return text;
    }

    public void disableCompatibilityMode() {
        this.flags |= 540160;
    }

    public boolean usesCompatibilityMode() {
        return this.targetSdkVersion < 4 || (this.flags & 540160) == 0;
    }

    public void initForUser(int i) {
        this.uid = UserHandle.getUid(i, UserHandle.getAppId(this.uid));
        if ("android".equals(this.packageName)) {
            this.dataDir = Environment.getDataSystemDirectory().getAbsolutePath();
            return;
        }
        this.deviceProtectedDataDir = Environment.getDataUserDePackageDirectory(this.volumeUuid, i, this.packageName).getAbsolutePath();
        String absolutePath = Environment.getDataUserCePackageDirectory(this.volumeUuid, i, this.packageName).getAbsolutePath();
        this.credentialProtectedDataDir = absolutePath;
        if ((this.privateFlags & 32) != 0) {
            this.dataDir = this.deviceProtectedDataDir;
        } else {
            this.dataDir = absolutePath;
        }
    }

    private boolean isPackageWhitelistedForHiddenApis() {
        return (this.privateFlagsExt & 16) != 0;
    }

    public boolean usesNonSdkApi() {
        return (this.privateFlags & 4194304) != 0;
    }

    @SystemApi
    public boolean hasFragileUserData() {
        return (this.privateFlags & 16777216) != 0;
    }

    public boolean isAudioPlaybackCaptureAllowed() {
        return (this.privateFlags & 134217728) != 0;
    }

    public boolean hasRequestedLegacyExternalStorage() {
        return (this.privateFlags & 536870912) != 0;
    }

    public int getRequestRawExternalStorageAccess() {
        Boolean bool = this.requestRawExternalStorageAccess;
        if (bool == null) {
            return 0;
        }
        return bool.booleanValue() ? 1 : 2;
    }

    public boolean allowsNativeHeapPointerTagging() {
        return (this.privateFlags & Integer.MIN_VALUE) != 0;
    }

    private boolean isAllowedToUseHiddenApis() {
        if (isSignedWithPlatformKey()) {
            return true;
        }
        if (isSystemApp() || isUpdatedSystemApp()) {
            return usesNonSdkApi() || isPackageWhitelistedForHiddenApis();
        }
        return false;
    }

    public int getHiddenApiEnforcementPolicy() {
        if (isAllowedToUseHiddenApis()) {
            return 0;
        }
        int i = this.mHiddenApiPolicy;
        if (i != -1) {
            return i;
        }
        return 2;
    }

    public void setHiddenApiEnforcementPolicy(int i) {
        if (!isValidHiddenApiEnforcementPolicy(i)) {
            throw new IllegalArgumentException("Invalid API enforcement policy: " + i);
        }
        this.mHiddenApiPolicy = i;
    }

    public void maybeUpdateHiddenApiEnforcementPolicy(int i) {
        if (isPackageWhitelistedForHiddenApis()) {
            return;
        }
        setHiddenApiEnforcementPolicy(i);
    }

    public void setVersionCode(long j) {
        this.longVersionCode = j;
        this.versionCode = (int) j;
    }

    @Override // android.content.pm.PackageItemInfo
    public Drawable loadDefaultIcon(PackageManager packageManager) {
        if ((this.flags & 262144) != 0 && isPackageUnavailable(packageManager)) {
            return Resources.getSystem().getDrawable(R.drawable.sym_app_on_sd_unavailable_icon);
        }
        return packageManager.getDefaultActivityIcon();
    }

    private boolean isPackageUnavailable(PackageManager packageManager) {
        try {
            return packageManager.getPackageInfo(this.packageName, 0) == null;
        } catch (PackageManager.NameNotFoundException unused) {
            return true;
        }
    }

    public boolean isDefaultToDeviceProtectedStorage() {
        return (this.privateFlags & 32) != 0;
    }

    public boolean isDirectBootAware() {
        return (this.privateFlags & 64) != 0;
    }

    @SystemApi
    public boolean isEncryptionAware() {
        return isDirectBootAware() || isPartiallyDirectBootAware();
    }

    public boolean isExternal() {
        return (this.flags & 262144) != 0;
    }

    @SystemApi
    public boolean isInstantApp() {
        return (this.privateFlags & 128) != 0;
    }

    public boolean isInternal() {
        return (this.flags & 262144) == 0;
    }

    @SystemApi
    public boolean isOem() {
        return (this.privateFlags & 131072) != 0;
    }

    public boolean isOdm() {
        return (this.privateFlags & 1073741824) != 0;
    }

    public boolean isPartiallyDirectBootAware() {
        return (this.privateFlags & 256) != 0;
    }

    public boolean isSignedWithPlatformKey() {
        return (this.privateFlags & 1048576) != 0;
    }

    @SystemApi
    public boolean isPrivilegedApp() {
        return (this.privateFlags & 8) != 0;
    }

    public boolean isRequiredForSystemUser() {
        return (this.privateFlags & 512) != 0;
    }

    public boolean isStaticSharedLibrary() {
        return (this.privateFlags & 16384) != 0;
    }

    public boolean isSystemApp() {
        return (this.flags & 1) != 0;
    }

    public boolean isUpdatedSystemApp() {
        return (this.flags & 128) != 0;
    }

    @SystemApi
    public boolean isVendor() {
        return (this.privateFlags & 262144) != 0;
    }

    @SystemApi
    public boolean isProduct() {
        return (this.privateFlags & 524288) != 0;
    }

    public boolean isSystemExt() {
        return (this.privateFlags & 2097152) != 0;
    }

    public boolean isEmbeddedDexUsed() {
        return (this.privateFlags & 33554432) != 0;
    }

    public boolean isVirtualPreload() {
        return (this.privateFlags & 65536) != 0;
    }

    public boolean isProfileableByShell() {
        return (this.privateFlags & 8388608) != 0;
    }

    public boolean isProfileable() {
        return (this.privateFlagsExt & 1) != 0;
    }

    public boolean areAttributionsUserVisible() {
        return (this.privateFlagsExt & 4) != 0;
    }

    public boolean requestsIsolatedSplitLoading() {
        return (this.privateFlags & 32768) != 0;
    }

    public boolean isResourceOverlay() {
        return (this.privateFlags & 268435456) != 0;
    }

    public boolean isStopped() {
        return (this.flags & 2097152) != 0;
    }

    public boolean isNotLaunched() {
        return (this.privateFlagsExt & 64) != 0;
    }

    public boolean isChangeEnabled(long j) {
        return CompatChanges.isChangeEnabled(j, this.packageName, UserHandle.getUserHandleForUid(this.uid));
    }

    public boolean hasRequestForegroundServiceExemption() {
        return (this.privateFlagsExt & 2) != 0;
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        return (this.privateFlagsExt & 8) != 0;
    }

    public String[] getAllApkPaths() {
        String[][] strArr = {this.splitSourceDirs, this.sharedLibraryFiles, this.resourceDirs, this.overlayPaths};
        ArrayList arrayList = new ArrayList(10);
        String str = this.sourceDir;
        if (str != null) {
            arrayList.add(str);
        }
        for (int i = 0; i < 4; i++) {
            String[] strArr2 = strArr[i];
            if (strArr2 != null) {
                for (String str2 : strArr2) {
                    arrayList.add(str2);
                }
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public void setCodePath(String str) {
        this.scanSourceDir = str;
    }

    public void setBaseCodePath(String str) {
        this.sourceDir = str;
    }

    public void setSplitCodePaths(String[] strArr) {
        this.splitSourceDirs = strArr;
    }

    public void setResourcePath(String str) {
        this.scanPublicSourceDir = str;
    }

    public void setBaseResourcePath(String str) {
        this.publicSourceDir = str;
    }

    public void setSplitResourcePaths(String[] strArr) {
        this.splitPublicSourceDirs = strArr;
    }

    public void setGwpAsanMode(int i) {
        this.gwpAsanMode = i;
    }

    public void setMemtagMode(int i) {
        this.memtagMode = i;
    }

    public void setNativeHeapZeroInitialized(int i) {
        this.nativeHeapZeroInitialized = i;
    }

    public void setRequestRawExternalStorageAccess(Boolean bool) {
        this.requestRawExternalStorageAccess = bool;
    }

    public void setPageSizeAppCompatFlags(int i) {
        this.mPageSizeAppCompatFlags = i | this.mPageSizeAppCompatFlags;
    }

    public void setAppClassNamesByProcess(ArrayMap<String, String> arrayMap) {
        if (ArrayUtils.size(arrayMap) == 0) {
            this.mAppClassNamesByProcess = null;
        } else {
            this.mAppClassNamesByProcess = arrayMap;
        }
    }

    public String getCodePath() {
        return this.scanSourceDir;
    }

    public String getBaseCodePath() {
        return this.sourceDir;
    }

    public String[] getSplitCodePaths() {
        return this.splitSourceDirs;
    }

    public String getResourcePath() {
        return this.scanPublicSourceDir;
    }

    public String getBaseResourcePath() {
        return this.publicSourceDir;
    }

    public String[] getSplitResourcePaths() {
        return this.splitPublicSourceDirs;
    }

    public int getGwpAsanMode() {
        return this.gwpAsanMode;
    }

    public int getMemtagMode() {
        return this.memtagMode;
    }

    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    public String getCustomApplicationClassNameForProcess(String str) {
        String str2;
        ArrayMap<String, String> arrayMap = this.mAppClassNamesByProcess;
        return (arrayMap == null || (str2 = arrayMap.get(str)) == null) ? this.className : str2;
    }

    public void setLocaleConfigRes(int i) {
        this.localeConfigRes = i;
    }

    public int getLocaleConfigRes() {
        return this.localeConfigRes;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<SharedLibraryInfo> getSharedLibraryInfos() {
        List<SharedLibraryInfo> list = this.sharedLibraryInfos;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<SharedLibraryInfo> getOptionalSharedLibraryInfos() {
        List<SharedLibraryInfo> list = this.optionalSharedLibraryInfos;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public Set<String> getKnownActivityEmbeddingCerts() {
        Set<String> set = this.mKnownActivityEmbeddingCerts;
        return set == null ? Collections.EMPTY_SET : set;
    }

    public void setKnownActivityEmbeddingCerts(Set<String> set) {
        this.mKnownActivityEmbeddingCerts = new ArraySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            this.mKnownActivityEmbeddingCerts.add(it.next().toUpperCase(Locale.US));
        }
    }

    public void setEnableOnBackInvokedCallback(boolean z) {
        if (z) {
            this.privateFlagsExt |= 8;
        } else {
            this.privateFlagsExt &= -9;
        }
    }
}
