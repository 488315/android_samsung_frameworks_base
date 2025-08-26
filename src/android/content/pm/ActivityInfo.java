package android.content.pm;

import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.NtpTrustedTime;
import android.util.Printer;
import com.android.internal.util.Parcelling;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public class ActivityInfo extends ComponentInfo implements Parcelable {
    public static final long ALWAYS_SANDBOX_DISPLAY_APIS = 185004937;
    private static final long CHECK_MIN_WIDTH_HEIGHT_FOR_MULTI_WINDOW = 197654537;
    public static final int COLOR_MODE_A8 = 4;
    public static final int COLOR_MODE_DEFAULT = 0;
    public static final int COLOR_MODE_HDR = 2;
    public static final int COLOR_MODE_HDR10 = 3;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT = 1;
    public static final int CONFIG_ASSETS_PATHS = Integer.MIN_VALUE;
    public static final int CONFIG_BOLD_TEXT = 16777216;
    public static final int CONFIG_COLOR_MODE = 16384;
    public static final int CONFIG_CURSOR_THICKNESS = 8388608;
    public static final int CONFIG_DENSITY = 4096;
    public static final int CONFIG_DEX_MODE = 1048576;
    public static final int CONFIG_DISPLAY_DEVICE_TYPE = 67108864;
    public static final int CONFIG_FLIPFONT = 33554432;
    public static final int CONFIG_FONT_SCALE = 1073741824;
    public static final int CONFIG_FONT_WEIGHT_ADJUSTMENT = 268435456;
    public static final int CONFIG_GRAMMATICAL_GENDER = 32768;
    public static final int CONFIG_KEYBOARD = 16;
    public static final int CONFIG_KEYBOARD_HIDDEN = 32;
    public static final int CONFIG_LAYOUT_DIRECTION = 8192;
    public static final int CONFIG_LOCALE = 4;
    public static final int CONFIG_MCC = 1;
    public static final int CONFIG_MNC = 2;
    public static final int CONFIG_NAVIGATION = 64;
    public static final int CONFIG_NIGHT_DIM = 4194304;
    public static final int CONFIG_ORIENTATION = 128;
    public static final int CONFIG_RESOURCES_UNUSED = 134217728;
    public static final int CONFIG_SCREEN_LAYOUT = 256;
    public static final int CONFIG_SCREEN_SIZE = 1024;
    public static final int CONFIG_SHOW_BUTTON_SHAPE = 2097152;
    public static final int CONFIG_SMALLEST_SCREEN_SIZE = 2048;
    public static final int CONFIG_THEME_DISPLAY = 131072;
    public static final int CONFIG_THEME_SEQ = 65536;
    public static final int CONFIG_TOUCHSCREEN = 8;
    public static final int CONFIG_UI_MODE = 512;
    public static final int CONFIG_WINDOW_CONFIGURATION = 536870912;
    public static final int CONTENT_URI_PERMISSION_NONE = 0;
    public static final int CONTENT_URI_PERMISSION_READ = 1;
    public static final int CONTENT_URI_PERMISSION_READ_AND_WRITE = 4;
    public static final int CONTENT_URI_PERMISSION_READ_OR_WRITE = 3;
    public static final int CONTENT_URI_PERMISSION_WRITE = 2;
    public static final int DOCUMENT_LAUNCH_ALWAYS = 2;
    public static final int DOCUMENT_LAUNCH_INTO_EXISTING = 1;
    public static final int DOCUMENT_LAUNCH_NEVER = 3;
    public static final int DOCUMENT_LAUNCH_NONE = 0;

    @Deprecated
    public static final int FLAG_ALLOW_EMBEDDED = Integer.MIN_VALUE;
    public static final int FLAG_ALLOW_TASK_REPARENTING = 64;
    public static final int FLAG_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING = 268435456;
    public static final int FLAG_ALWAYS_FOCUSABLE = 262144;
    public static final int FLAG_ALWAYS_RETAIN_TASK_STATE = 8;
    public static final int FLAG_AUTO_REMOVE_FROM_RECENTS = 8192;
    public static final int FLAG_CAN_DISPLAY_ON_REMOTE_DEVICES = 65536;
    public static final int FLAG_CLEAR_TASK_ON_LAUNCH = 4;
    public static final int FLAG_ENABLE_VR_MODE = 32768;
    public static final int FLAG_EXCLUDE_FROM_RECENTS = 32;
    public static final int FLAG_FINISH_ON_CLOSE_SYSTEM_DIALOGS = 256;
    public static final int FLAG_FINISH_ON_TASK_LAUNCH = 2;
    public static final int FLAG_HARDWARE_ACCELERATED = 512;
    public static final int FLAG_IMMERSIVE = 2048;
    public static final int FLAG_IMPLICITLY_VISIBLE_TO_INSTANT_APP = 2097152;
    public static final int FLAG_INHERIT_SHOW_WHEN_LOCKED = 1;
    public static final int FLAG_MULTIPROCESS = 1;
    public static final int FLAG_NO_HISTORY = 128;
    public static final int FLAG_PREFER_MINIMAL_POST_PROCESSING = 33554432;
    public static final int FLAG_RELINQUISH_TASK_IDENTITY = 4096;
    public static final int FLAG_RESUME_WHILE_PAUSING = 16384;
    public static final int FLAG_SHOW_FOR_ALL_USERS = 1024;
    public static final int FLAG_SHOW_WHEN_LOCKED = 8388608;
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_STATE_NOT_NEEDED = 16;
    public static final int FLAG_SUPPORTS_PICTURE_IN_PICTURE = 4194304;
    public static final int FLAG_SYSTEM_USER_ONLY = 536870912;
    public static final int FLAG_TURN_SCREEN_ON = 16777216;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public static final long FORCE_NON_RESIZE_APP = 181136395;
    public static final long FORCE_RESIZE_APP = 174042936;
    public static final long INSETS_DECOUPLED_CONFIGURATION_ENFORCED = 151861875;
    public static final int LAUNCH_MULTIPLE = 0;
    public static final int LAUNCH_SINGLE_INSTANCE = 3;
    public static final int LAUNCH_SINGLE_INSTANCE_PER_TASK = 4;
    public static final int LAUNCH_SINGLE_TASK = 2;
    public static final int LAUNCH_SINGLE_TOP = 1;
    public static final int LOCK_TASK_LAUNCH_MODE_ALWAYS = 2;
    public static final int LOCK_TASK_LAUNCH_MODE_DEFAULT = 0;
    public static final int LOCK_TASK_LAUNCH_MODE_IF_ALLOWLISTED = 3;
    public static final int LOCK_TASK_LAUNCH_MODE_NEVER = 1;
    public static final long NEVER_SANDBOX_DISPLAY_APIS = 184838306;
    public static final long OVERRIDE_ANY_ORIENTATION = 265464455;
    public static final long OVERRIDE_ANY_ORIENTATION_TO_USER = 310816437;
    public static final long OVERRIDE_CAMERA_COMPAT_DISABLE_FORCE_ROTATION = 263959004;
    public static final long OVERRIDE_CAMERA_COMPAT_DISABLE_REFRESH = 264304459;
    public static final long OVERRIDE_CAMERA_COMPAT_DISABLE_SIMULATE_REQUESTED_ORIENTATION = 398195815;
    public static final long OVERRIDE_CAMERA_COMPAT_ENABLE_FREEFORM_WINDOWING_TREATMENT = 314961188;
    public static final long OVERRIDE_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE = 264301586;
    public static final long OVERRIDE_ENABLE_COMPAT_FAKE_FOCUS = 263259275;
    public static final long OVERRIDE_ENABLE_COMPAT_IGNORE_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED = 273509367;
    public static final long OVERRIDE_ENABLE_COMPAT_IGNORE_REQUESTED_ORIENTATION = 254631730;
    public static final long OVERRIDE_ENABLE_INSETS_DECOUPLED_CONFIGURATION = 327313645;
    public static final long OVERRIDE_EXCLUDE_CAPTION_INSETS_FROM_APP_BOUNDS = 388014743;
    public static final long OVERRIDE_LANDSCAPE_ORIENTATION_TO_REVERSE_LANDSCAPE = 266124927;
    public static final long OVERRIDE_MIN_ASPECT_RATIO = 174042980;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_EXCLUDE_PORTRAIT_FULLSCREEN = 218959984;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_LARGE = 180326787;
    public static final float OVERRIDE_MIN_ASPECT_RATIO_LARGE_VALUE = 1.7777778f;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_MEDIUM = 180326845;
    public static final float OVERRIDE_MIN_ASPECT_RATIO_MEDIUM_VALUE = 1.5f;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_ONLY_FOR_CAMERA = 325586858;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_PORTRAIT_ONLY = 203647190;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_SMALL = 349045028;
    public static final float OVERRIDE_MIN_ASPECT_RATIO_SMALL_VALUE = 1.3333334f;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_TO_ALIGN_WITH_SPLIT_SCREEN = 208648326;
    public static final long OVERRIDE_ORIENTATION_ONLY_FOR_CAMERA = 265456536;
    public static final long OVERRIDE_RESPECT_REQUESTED_ORIENTATION = 236283604;
    public static final long OVERRIDE_SANDBOX_VIEW_BOUNDS_APIS = 237531167;
    public static final long OVERRIDE_UNDEFINED_ORIENTATION_TO_NOSENSOR = 265451093;
    public static final long OVERRIDE_UNDEFINED_ORIENTATION_TO_PORTRAIT = 265452344;
    public static final long OVERRIDE_USE_DISPLAY_LANDSCAPE_NATURAL_ORIENTATION = 255940284;
    public static final int PERSIST_ACROSS_REBOOTS = 2;
    public static final int PERSIST_NEVER = 1;
    public static final int PERSIST_ROOT_ONLY = 0;
    public static final int PRIVATE_FLAG_DISABLE_ON_BACK_INVOKED_CALLBACK = 8;
    public static final int PRIVATE_FLAG_ENABLE_ON_BACK_INVOKED_CALLBACK = 4;
    public static final int PRIVATE_FLAG_HOME_TRANSITION_SOUND = 2;
    public static final int RESIZE_MODE_FORCE_NONRESIZEABLE = 10;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_LANDSCAPE_ONLY = 5;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_PORTRAIT_ONLY = 6;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_PRESERVE_ORIENTATION = 7;
    public static final int RESIZE_MODE_FORCE_RESIZEABLE = 4;
    public static final int RESIZE_MODE_RESIZABLE_ALLOW_LIST = 1048576;
    public static final int RESIZE_MODE_RESIZEABLE = 2;
    public static final int RESIZE_MODE_RESIZEABLE_AND_PIPABLE_DEPRECATED = 3;
    public static final int RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION = 1;
    public static final int RESIZE_MODE_UNRESIZABLE_BLOCK_LIST = 2097152;
    public static final int RESIZE_MODE_UNRESIZEABLE = 0;
    public static final int SCREEN_ORIENTATION_BEHIND = 3;
    public static final int SCREEN_ORIENTATION_FULL_SENSOR = 10;
    public static final int SCREEN_ORIENTATION_FULL_USER = 13;
    public static final int SCREEN_ORIENTATION_LANDSCAPE = 0;
    public static final int SCREEN_ORIENTATION_LOCKED = 14;
    public static final int SCREEN_ORIENTATION_NOSENSOR = 5;
    public static final int SCREEN_ORIENTATION_PORTRAIT = 1;
    public static final int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
    public static final int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;
    public static final int SCREEN_ORIENTATION_SENSOR = 4;
    public static final int SCREEN_ORIENTATION_SENSOR_LANDSCAPE = 6;
    public static final int SCREEN_ORIENTATION_SENSOR_PORTRAIT = 7;
    public static final int SCREEN_ORIENTATION_UNSET = -2;
    public static final int SCREEN_ORIENTATION_UNSPECIFIED = -1;
    public static final int SCREEN_ORIENTATION_USER = 2;
    public static final int SCREEN_ORIENTATION_USER_LANDSCAPE = 11;
    public static final int SCREEN_ORIENTATION_USER_PORTRAIT = 12;
    public static final int SIZE_CHANGES_SUPPORTED_METADATA = 2;
    public static final int SIZE_CHANGES_SUPPORTED_OVERRIDE = 3;
    public static final int SIZE_CHANGES_UNSUPPORTED_METADATA = 0;
    public static final int SIZE_CHANGES_UNSUPPORTED_OVERRIDE = 1;
    public static final int UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW = 1;
    public static final long UNIVERSAL_RESIZABLE_BY_DEFAULT = 357141415;
    public int colorMode;
    public int configChanges;
    public int documentLaunchMode;
    public int flags;
    public boolean isLaunchedFromAppsCoverLauncher;
    public boolean isLaunchedFromMultistarCoverLauncher;
    public int launchMode;
    public String launchToken;
    public int lockTaskLaunchMode;
    private Set<String> mKnownActivityEmbeddingCerts;
    private float mMaxAspectRatio;
    private float mMinAspectRatio;
    public int maxRecents;
    public boolean mockMultiWindow;
    public String parentActivityName;
    public String permission;
    public int persistableMode;
    public int privateFlags;
    public String requestedVrComponent;
    public int requireContentUriPermissionFromCaller;
    public String requiredDisplayCategory;
    public int resizeMode;
    public int rotationAnimation;
    public int screenOrientation;
    public int softInputMode;
    public boolean supportsSizeChanges;
    public String targetActivity;
    public String taskAffinity;
    public int theme;
    public int transientBarShowingDelayMillis;
    public int uiOptions;
    public WindowLayout windowLayout;
    private static final Parcelling.BuiltIn.ForStringSet sForStringSet = (Parcelling.BuiltIn.ForStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static int[] CONFIG_NATIVE_BITS = {2, 1, 4, 8, 16, 32, 64, 128, 2048, 4096, 512, 8192, 256, 16384, 65536, 131072, 131072, 32768, 262144, 524288, 2097152, 1048576};
    public static final Parcelable.Creator<ActivityInfo> CREATOR = new Parcelable.Creator<ActivityInfo>() { // from class: android.content.pm.ActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityInfo createFromParcel(Parcel parcel) {
            return new ActivityInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityInfo[] newArray(int i) {
            return new ActivityInfo[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Config {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequiredContentUriPermission {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SizeChangesSupportMode {
    }

    private int getRealConfigChangedIfNeeded() {
        return 4194304;
    }

    public static boolean isFixedOrientationLandscape(int i) {
        return i == 0 || i == 6 || i == 8 || i == 11;
    }

    public static boolean isFixedOrientationPortrait(int i) {
        return i == 1 || i == 7 || i == 9 || i == 12;
    }

    public static boolean isPreserveOrientationMode(int i) {
        return i == 6 || i == 5 || i == 7;
    }

    public static boolean isRequiredContentUriPermissionRead(int i) {
        return i == 1 || i == 3 || i == 4;
    }

    public static boolean isRequiredContentUriPermissionWrite(int i) {
        return i == 2 || i == 3 || i == 4;
    }

    public static boolean isResizeableMode(int i) {
        return i == 2 || i == 4 || (1048576 & i) != 0 || i == 6 || i == 5 || i == 7 || i == 1;
    }

    public static int reverseOrientation(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 0;
        }
        if (i == 11) {
            return 12;
        }
        if (i == 12) {
            return 11;
        }
        switch (i) {
            case 6:
                return 7;
            case 7:
                return 6;
            case 8:
                return 9;
            case 9:
                return 8;
            default:
                return i;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String launchModeToString(int i) {
        if (i == 0) {
            return "LAUNCH_MULTIPLE";
        }
        if (i == 1) {
            return "LAUNCH_SINGLE_TOP";
        }
        if (i == 2) {
            return "LAUNCH_SINGLE_TASK";
        }
        if (i == 3) {
            return "LAUNCH_SINGLE_INSTANCE";
        }
        if (i == 4) {
            return "LAUNCH_SINGLE_INSTANCE_PER_TASK";
        }
        return "unknown=" + i;
    }

    private String requiredContentUriPermissionToFullString(int i) {
        if (i == 0) {
            return "CONTENT_URI_PERMISSION_NONE";
        }
        if (i == 1) {
            return "CONTENT_URI_PERMISSION_READ";
        }
        if (i == 2) {
            return "CONTENT_URI_PERMISSION_WRITE";
        }
        if (i == 3) {
            return "CONTENT_URI_PERMISSION_READ_OR_WRITE";
        }
        if (i == 4) {
            return "CONTENT_URI_PERMISSION_READ_AND_WRITE";
        }
        return "unknown=" + i;
    }

    public static String requiredContentUriPermissionToShortString(int i) {
        if (i == 0) {
            return "none";
        }
        if (i == 1) {
            return "read";
        }
        if (i == 2) {
            return "write";
        }
        if (i == 3) {
            return "read or write";
        }
        if (i == 4) {
            return "read and write";
        }
        return "unknown=" + i;
    }

    public static int activityInfoConfigJavaToNative(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = CONFIG_NATIVE_BITS;
            if (i2 >= iArr.length) {
                return i3;
            }
            if (((1 << i2) & i) != 0) {
                i3 |= iArr[i2];
            }
            i2++;
        }
    }

    public static int activityInfoConfigNativeToJava(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = CONFIG_NATIVE_BITS;
            if (i2 >= iArr.length) {
                return i3;
            }
            if ((iArr[i2] & i) != 0) {
                i3 |= 1 << i2;
            }
            i2++;
        }
    }

    public int getRealConfigChanged() {
        int i;
        if (this.applicationInfo.targetSdkVersion < 13) {
            i = this.configChanges | 3072;
        } else {
            i = this.configChanges;
        }
        return getRealConfigChangedIfNeeded() | i;
    }

    public static final String lockTaskLaunchModeToString(int i) {
        if (i == 0) {
            return "LOCK_TASK_LAUNCH_MODE_DEFAULT";
        }
        if (i == 1) {
            return "LOCK_TASK_LAUNCH_MODE_NEVER";
        }
        if (i == 2) {
            return "LOCK_TASK_LAUNCH_MODE_ALWAYS";
        }
        if (i == 3) {
            return "LOCK_TASK_LAUNCH_MODE_IF_ALLOWLISTED";
        }
        return "unknown=" + i;
    }

    public ActivityInfo() {
        this.resizeMode = 2;
        this.colorMode = 0;
        this.screenOrientation = -1;
        this.uiOptions = 0;
        this.rotationAnimation = -1;
        this.transientBarShowingDelayMillis = -1;
        this.mockMultiWindow = false;
        this.isLaunchedFromAppsCoverLauncher = false;
        this.isLaunchedFromMultistarCoverLauncher = false;
    }

    public ActivityInfo(ActivityInfo activityInfo) {
        super(activityInfo);
        this.resizeMode = 2;
        this.colorMode = 0;
        this.screenOrientation = -1;
        this.uiOptions = 0;
        this.rotationAnimation = -1;
        this.transientBarShowingDelayMillis = -1;
        this.mockMultiWindow = false;
        this.isLaunchedFromAppsCoverLauncher = false;
        this.isLaunchedFromMultistarCoverLauncher = false;
        this.theme = activityInfo.theme;
        this.launchMode = activityInfo.launchMode;
        this.documentLaunchMode = activityInfo.documentLaunchMode;
        this.permission = activityInfo.permission;
        this.mKnownActivityEmbeddingCerts = activityInfo.mKnownActivityEmbeddingCerts;
        this.taskAffinity = activityInfo.taskAffinity;
        this.targetActivity = activityInfo.targetActivity;
        this.flags = activityInfo.flags;
        this.privateFlags = activityInfo.privateFlags;
        this.screenOrientation = activityInfo.screenOrientation;
        this.configChanges = activityInfo.configChanges;
        this.softInputMode = activityInfo.softInputMode;
        this.uiOptions = activityInfo.uiOptions;
        this.parentActivityName = activityInfo.parentActivityName;
        this.maxRecents = activityInfo.maxRecents;
        this.lockTaskLaunchMode = activityInfo.lockTaskLaunchMode;
        this.windowLayout = activityInfo.windowLayout;
        this.resizeMode = activityInfo.resizeMode;
        this.requestedVrComponent = activityInfo.requestedVrComponent;
        this.rotationAnimation = activityInfo.rotationAnimation;
        this.colorMode = activityInfo.colorMode;
        this.mMaxAspectRatio = activityInfo.mMaxAspectRatio;
        this.mMinAspectRatio = activityInfo.mMinAspectRatio;
        this.supportsSizeChanges = activityInfo.supportsSizeChanges;
        this.requiredDisplayCategory = activityInfo.requiredDisplayCategory;
        this.requireContentUriPermissionFromCaller = activityInfo.requireContentUriPermissionFromCaller;
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT) {
            this.isLaunchedFromAppsCoverLauncher = activityInfo.isLaunchedFromAppsCoverLauncher;
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT) {
            this.isLaunchedFromMultistarCoverLauncher = activityInfo.isLaunchedFromMultistarCoverLauncher;
        }
    }

    public final int getThemeResource() {
        int i = this.theme;
        return i != 0 ? i : this.applicationInfo.theme;
    }

    private String persistableModeToString() {
        int i = this.persistableMode;
        if (i == 0) {
            return "PERSIST_ROOT_ONLY";
        }
        if (i == 1) {
            return "PERSIST_NEVER";
        }
        if (i == 2) {
            return "PERSIST_ACROSS_REBOOTS";
        }
        return "UNKNOWN=" + this.persistableMode;
    }

    public boolean hasFixedAspectRatio() {
        return (getMaxAspectRatio() == 0.0f && getMinAspectRatio() == 0.0f) ? false : true;
    }

    public boolean isFixedOrientation() {
        return isFixedOrientation(this.screenOrientation);
    }

    public static boolean isFixedOrientation(int i) {
        return i == 14 || i == 5 || isFixedOrientationLandscape(i) || isFixedOrientationPortrait(i);
    }

    boolean isFixedOrientationLandscape() {
        return isFixedOrientationLandscape(this.screenOrientation);
    }

    boolean isFixedOrientationPortrait() {
        return isFixedOrientationPortrait(this.screenOrientation);
    }

    public boolean supportsPictureInPicture() {
        return (this.flags & 4194304) != 0;
    }

    public boolean neverSandboxDisplayApis(ConstrainDisplayApisConfig constrainDisplayApisConfig) {
        return isChangeEnabled(NEVER_SANDBOX_DISPLAY_APIS) || constrainDisplayApisConfig.getNeverConstrainDisplayApis(this.applicationInfo);
    }

    public boolean alwaysSandboxDisplayApis(ConstrainDisplayApisConfig constrainDisplayApisConfig) {
        return isChangeEnabled(ALWAYS_SANDBOX_DISPLAY_APIS) || constrainDisplayApisConfig.getAlwaysConstrainDisplayApis(this.applicationInfo);
    }

    public void setMaxAspectRatio(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.mMaxAspectRatio = f;
    }

    public float getMaxAspectRatio() {
        return this.mMaxAspectRatio;
    }

    public void setMinAspectRatio(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.mMinAspectRatio = f;
    }

    public float getMinAspectRatio() {
        return this.mMinAspectRatio;
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

    public boolean isChangeEnabled(long j) {
        return this.applicationInfo.isChangeEnabled(j);
    }

    public float getManifestMinAspectRatio() {
        return this.mMinAspectRatio;
    }

    public static String resizeModeToString(int i) {
        if (i == 0) {
            return "RESIZE_MODE_UNRESIZEABLE";
        }
        if (i == 1) {
            return "RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION";
        }
        if (i == 2) {
            return "RESIZE_MODE_RESIZEABLE";
        }
        if (i == 4) {
            return "RESIZE_MODE_FORCE_RESIZEABLE";
        }
        if (i == 5) {
            return "RESIZE_MODE_FORCE_RESIZABLE_LANDSCAPE_ONLY";
        }
        if (i == 6) {
            return "RESIZE_MODE_FORCE_RESIZABLE_PORTRAIT_ONLY";
        }
        if (i == 7) {
            return "RESIZE_MODE_FORCE_RESIZABLE_PRESERVE_ORIENTATION";
        }
        if ((1048576 & i) != 0) {
            return "RESIZE_MODE_RESIZEABLE_S";
        }
        if ((2097152 & i) != 0) {
            return "RESIZE_MODE_UNRESIZEABLE_S";
        }
        if (i == 10) {
            return "RESIZE_MODE_FORCE_NONRESIZEABLE";
        }
        return "unknown=" + i;
    }

    public static String sizeChangesSupportModeToString(int i) {
        if (i == 0) {
            return "SIZE_CHANGES_UNSUPPORTED_METADATA";
        }
        if (i == 1) {
            return "SIZE_CHANGES_UNSUPPORTED_OVERRIDE";
        }
        if (i == 2) {
            return "SIZE_CHANGES_SUPPORTED_METADATA";
        }
        if (i == 3) {
            return "SIZE_CHANGES_SUPPORTED_OVERRIDE";
        }
        return "unknown=" + i;
    }

    public boolean shouldCheckMinWidthHeightForMultiWindow() {
        return isChangeEnabled(CHECK_MIN_WIDTH_HEIGHT_FOR_MULTI_WINDOW);
    }

    public boolean hasOnBackInvokedCallbackEnabled() {
        return (this.privateFlags & 12) != 0;
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        return hasOnBackInvokedCallbackEnabled() && (this.privateFlags & 4) != 0;
    }

    public void dump(Printer printer, String str) {
        dump(printer, str, 3);
    }

    public void dump(Printer printer, String str, int i) {
        super.dumpFront(printer, str);
        if (this.permission != null) {
            printer.println(str + "permission=" + this.permission);
        }
        int i2 = i & 1;
        if (i2 != 0) {
            printer.println(str + "taskAffinity=" + this.taskAffinity + " targetActivity=" + this.targetActivity + " persistableMode=" + persistableModeToString());
        }
        if (this.launchMode != 0 || this.flags != 0 || this.privateFlags != 0 || this.theme != 0) {
            printer.println(str + "launchMode=" + launchModeToString(this.launchMode) + " flags=0x" + Integer.toHexString(this.flags) + " privateFlags=0x" + Integer.toHexString(this.privateFlags) + " theme=0x" + Integer.toHexString(this.theme));
        }
        if (this.screenOrientation != -1 || this.configChanges != 0 || this.softInputMode != 0) {
            printer.println(str + "screenOrientation=" + this.screenOrientation + " configChanges=0x" + Integer.toHexString(this.configChanges) + " softInputMode=0x" + Integer.toHexString(this.softInputMode));
        }
        if (this.uiOptions != 0) {
            printer.println(str + " uiOptions=0x" + Integer.toHexString(this.uiOptions));
        }
        if (i2 != 0) {
            printer.println(str + "lockTaskLaunchMode=" + lockTaskLaunchModeToString(this.lockTaskLaunchMode));
        }
        if (this.windowLayout != null) {
            printer.println(str + "windowLayout=" + this.windowLayout.width + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.windowLayout.widthFraction + ", " + this.windowLayout.height + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.windowLayout.heightFraction + ", " + this.windowLayout.gravity);
        }
        printer.println(str + "resizeMode=" + resizeModeToString(this.resizeMode));
        if (this.requestedVrComponent != null) {
            printer.println(str + "requestedVrComponent=" + this.requestedVrComponent);
        }
        if (getMaxAspectRatio() != 0.0f) {
            printer.println(str + "maxAspectRatio=" + getMaxAspectRatio());
        }
        float minAspectRatio = getMinAspectRatio();
        if (minAspectRatio != 0.0f) {
            printer.println(str + "minAspectRatio=" + minAspectRatio);
        }
        if (this.supportsSizeChanges) {
            printer.println(str + "supportsSizeChanges=true");
        }
        if (this.mKnownActivityEmbeddingCerts != null) {
            printer.println(str + "knownActivityEmbeddingCerts=" + this.mKnownActivityEmbeddingCerts);
        }
        if (this.requiredDisplayCategory != null) {
            printer.println(str + "requiredDisplayCategory=" + this.requiredDisplayCategory);
        }
        if (i2 != 0) {
            printer.println(str + "requireContentUriPermissionFromCaller=" + requiredContentUriPermissionToFullString(this.requireContentUriPermissionFromCaller));
        }
        super.dumpBack(printer, str, i);
    }

    public String toString() {
        return "ActivityInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.name + "}";
    }

    @Override // android.content.pm.ComponentInfo, android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.launchMode);
        parcel.writeInt(this.documentLaunchMode);
        parcel.writeString8(this.permission);
        parcel.writeString8(this.taskAffinity);
        parcel.writeString8(this.targetActivity);
        parcel.writeString8(this.launchToken);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.privateFlags);
        parcel.writeInt(this.screenOrientation);
        parcel.writeInt(this.configChanges);
        parcel.writeInt(this.softInputMode);
        parcel.writeInt(this.uiOptions);
        parcel.writeString8(this.parentActivityName);
        parcel.writeInt(this.persistableMode);
        parcel.writeInt(this.maxRecents);
        parcel.writeInt(this.lockTaskLaunchMode);
        if (this.windowLayout != null) {
            parcel.writeInt(1);
            this.windowLayout.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.resizeMode);
        parcel.writeString8(this.requestedVrComponent);
        parcel.writeInt(this.rotationAnimation);
        parcel.writeInt(this.colorMode);
        parcel.writeFloat(this.mMaxAspectRatio);
        parcel.writeFloat(this.mMinAspectRatio);
        parcel.writeBoolean(this.supportsSizeChanges);
        sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, this.flags);
        parcel.writeString8(this.requiredDisplayCategory);
        parcel.writeInt(this.requireContentUriPermissionFromCaller);
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT) {
            parcel.writeBoolean(this.isLaunchedFromAppsCoverLauncher);
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT) {
            parcel.writeBoolean(this.isLaunchedFromMultistarCoverLauncher);
        }
    }

    public static boolean isTranslucentOrFloating(TypedArray typedArray) {
        return typedArray.getBoolean(4, false) || typedArray.getBoolean(5, false);
    }

    public static String screenOrientationToString(int i) {
        switch (i) {
            case -2:
                return "SCREEN_ORIENTATION_UNSET";
            case -1:
                return "SCREEN_ORIENTATION_UNSPECIFIED";
            case 0:
                return "SCREEN_ORIENTATION_LANDSCAPE";
            case 1:
                return "SCREEN_ORIENTATION_PORTRAIT";
            case 2:
                return "SCREEN_ORIENTATION_USER";
            case 3:
                return "SCREEN_ORIENTATION_BEHIND";
            case 4:
                return "SCREEN_ORIENTATION_SENSOR";
            case 5:
                return "SCREEN_ORIENTATION_NOSENSOR";
            case 6:
                return "SCREEN_ORIENTATION_SENSOR_LANDSCAPE";
            case 7:
                return "SCREEN_ORIENTATION_SENSOR_PORTRAIT";
            case 8:
                return "SCREEN_ORIENTATION_REVERSE_LANDSCAPE";
            case 9:
                return "SCREEN_ORIENTATION_REVERSE_PORTRAIT";
            case 10:
                return "SCREEN_ORIENTATION_FULL_SENSOR";
            case 11:
                return "SCREEN_ORIENTATION_USER_LANDSCAPE";
            case 12:
                return "SCREEN_ORIENTATION_USER_PORTRAIT";
            case 13:
                return "SCREEN_ORIENTATION_FULL_USER";
            case 14:
                return "SCREEN_ORIENTATION_LOCKED";
            default:
                return Integer.toString(i);
        }
    }

    public static String colorModeToString(int i) {
        if (i == 0) {
            return "COLOR_MODE_DEFAULT";
        }
        if (i == 1) {
            return "COLOR_MODE_WIDE_COLOR_GAMUT";
        }
        if (i == 2) {
            return "COLOR_MODE_HDR";
        }
        if (i == 4) {
            return "COLOR_MODE_A8";
        }
        return Integer.toString(i);
    }

    private ActivityInfo(Parcel parcel) {
        super(parcel);
        this.resizeMode = 2;
        this.colorMode = 0;
        this.screenOrientation = -1;
        this.uiOptions = 0;
        this.rotationAnimation = -1;
        this.transientBarShowingDelayMillis = -1;
        this.mockMultiWindow = false;
        this.isLaunchedFromAppsCoverLauncher = false;
        this.isLaunchedFromMultistarCoverLauncher = false;
        this.theme = parcel.readInt();
        this.launchMode = parcel.readInt();
        this.documentLaunchMode = parcel.readInt();
        this.permission = parcel.readString8();
        this.taskAffinity = parcel.readString8();
        this.targetActivity = parcel.readString8();
        this.launchToken = parcel.readString8();
        this.flags = parcel.readInt();
        this.privateFlags = parcel.readInt();
        this.screenOrientation = parcel.readInt();
        this.configChanges = parcel.readInt();
        this.softInputMode = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.parentActivityName = parcel.readString8();
        this.persistableMode = parcel.readInt();
        this.maxRecents = parcel.readInt();
        this.lockTaskLaunchMode = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.windowLayout = new WindowLayout(parcel);
        }
        this.resizeMode = parcel.readInt();
        this.requestedVrComponent = parcel.readString8();
        this.rotationAnimation = parcel.readInt();
        this.colorMode = parcel.readInt();
        this.mMaxAspectRatio = parcel.readFloat();
        this.mMinAspectRatio = parcel.readFloat();
        this.supportsSizeChanges = parcel.readBoolean();
        Set<String> setUnparcel = sForStringSet.unparcel(parcel);
        this.mKnownActivityEmbeddingCerts = setUnparcel;
        if (setUnparcel.isEmpty()) {
            this.mKnownActivityEmbeddingCerts = null;
        }
        this.requiredDisplayCategory = parcel.readString8();
        this.requireContentUriPermissionFromCaller = parcel.readInt();
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT) {
            this.isLaunchedFromAppsCoverLauncher = parcel.readBoolean();
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT) {
            this.isLaunchedFromMultistarCoverLauncher = parcel.readBoolean();
        }
    }

    public static final class WindowLayout {
        public final int gravity;
        public final int height;
        public final float heightFraction;
        public final int minHeight;
        public final int minWidth;
        public final int width;
        public final float widthFraction;
        public String windowLayoutAffinity;

        public WindowLayout(int i, float f, int i2, float f2, int i3, int i4, int i5) {
            this(i, f, i2, f2, i3, i4, i5, null);
        }

        public WindowLayout(int i, float f, int i2, float f2, int i3, int i4, int i5, String str) {
            this.width = i;
            this.widthFraction = f;
            this.height = i2;
            this.heightFraction = f2;
            this.gravity = i3;
            this.minWidth = i4;
            this.minHeight = i5;
            this.windowLayoutAffinity = str;
        }

        public WindowLayout(Parcel parcel) {
            this.width = parcel.readInt();
            this.widthFraction = parcel.readFloat();
            this.height = parcel.readInt();
            this.heightFraction = parcel.readFloat();
            this.gravity = parcel.readInt();
            this.minWidth = parcel.readInt();
            this.minHeight = parcel.readInt();
            this.windowLayoutAffinity = parcel.readString8();
        }

        public boolean hasSpecifiedSize() {
            return this.width >= 0 || this.height >= 0 || this.widthFraction >= 0.0f || this.heightFraction >= 0.0f;
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.width);
            parcel.writeFloat(this.widthFraction);
            parcel.writeInt(this.height);
            parcel.writeFloat(this.heightFraction);
            parcel.writeInt(this.gravity);
            parcel.writeInt(this.minWidth);
            parcel.writeInt(this.minHeight);
            parcel.writeString8(this.windowLayoutAffinity);
        }
    }
}
