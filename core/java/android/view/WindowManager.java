package android.view;

import android.annotation.SystemApi;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.admin.DevicePolicyResources;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.provider.CalendarContract;
import android.security.keystore.KeyProperties;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.window.InputTransferToken;
import android.window.TaskFpsCallback;
import android.window.TrustedPresentationThresholds;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.android.window.flags.Flags;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface WindowManager extends ViewManager {
    public static final boolean ACTIVITY_EMBEDDING_GUARD_WITH_ANDROID_15;
    public static final int COMPAT_SMALL_COVER_SCREEN_OPT_IN = 1;

    @SystemApi
    public static final int DISPLAY_IME_POLICY_FALLBACK_DISPLAY = 1;

    @SystemApi
    public static final int DISPLAY_IME_POLICY_HIDE = 2;

    @SystemApi
    public static final int DISPLAY_IME_POLICY_LOCAL = 0;
    public static final int DOCKED_BOTTOM = 4;
    public static final int DOCKED_INVALID = -1;
    public static final int DOCKED_LEFT = 1;
    public static final int DOCKED_RIGHT = 3;
    public static final int DOCKED_TOP = 2;
    public static final long ENABLE_ACTIVITY_EMBEDDING_FOR_ANDROID_15 = 306666082;
    public static final boolean HAS_WINDOW_EXTENSIONS_ON_DEVICE;
    public static final String INPUT_CONSUMER_NAVIGATION = "nav_input_consumer";
    public static final String INPUT_CONSUMER_PIP = "pip_input_consumer";
    public static final String INPUT_CONSUMER_RECENTS_ANIMATION = "recents_animation_input_consumer";
    public static final String INPUT_CONSUMER_WALLPAPER = "wallpaper_input_consumer";
    public static final int KEYGUARD_VISIBILITY_TRANSIT_FLAGS = 276736;
    public static final int LARGE_SCREEN_SMALLEST_SCREEN_WIDTH_DP = 600;
    public static final String PARCEL_KEY_SHORTCUTS_ARRAY = "shortcuts_array";
    public static final String PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE = "android.window.PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE";
    public static final String PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED = "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED";
    public static final String PROPERTY_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING_STATE_SHARING = "android.window.PROPERTY_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING_STATE_SHARING";
    public static final String PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION = "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION";
    public static final String PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH = "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH";
    public static final String PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE = "android.window.PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE";
    public static final String PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE";
    public static final String PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED = "android.window.PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED";
    public static final String PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE";
    public static final String PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE";
    public static final String PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES = "android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES";
    public static final String PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS = "android.window.PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS";
    public static final String PROPERTY_COMPAT_ALLOW_SMALL_COVER_SCREEN = "android.window.PROPERTY_COMPAT_ALLOW_SMALL_COVER_SCREEN";
    public static final String PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_FULLSCREEN_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_FULLSCREEN_OVERRIDE";
    public static final String PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE";
    public static final String PROPERTY_COMPAT_ENABLE_FAKE_FOCUS = "android.window.PROPERTY_COMPAT_ENABLE_FAKE_FOCUS";
    public static final String PROPERTY_COMPAT_IGNORE_REQUESTED_ORIENTATION = "android.window.PROPERTY_COMPAT_IGNORE_REQUESTED_ORIENTATION";
    public static final String PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI = "android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI";
    public static final int REMOVE_CONTENT_MODE_DESTROY = 2;
    public static final int REMOVE_CONTENT_MODE_MOVE_TO_PRIMARY = 1;
    public static final int REMOVE_CONTENT_MODE_UNDEFINED = 0;
    public static final int SCREEN_RECORDING_STATE_NOT_VISIBLE = 0;
    public static final int SCREEN_RECORDING_STATE_VISIBLE = 1;
    public static final int SHELL_ROOT_LAYER_DIVIDER = 0;
    public static final int SHELL_ROOT_LAYER_PIP = 1;
    public static final int TAKE_SCREENSHOT_FULLSCREEN = 1;
    public static final int TAKE_SCREENSHOT_PROVIDED_IMAGE = 3;
    public static final int TAKE_SCREENSHOT_RECT = 101;
    public static final int TAKE_SCREENSHOT_SELECTED_REGION = 2;
    public static final int TAKE_SCREENSHOT_WINDOW = 100;
    public static final int TRANSIT_CHANGE = 6;
    public static final int TRANSIT_CLOSE = 2;
    public static final int TRANSIT_FIRST_CUSTOM = 1000;
    public static final int TRANSIT_FLAG_AOD_APPEARING = 262144;
    public static final int TRANSIT_FLAG_APP_CRASHED = 16;
    public static final int TRANSIT_FLAG_INVISIBLE = 1024;
    public static final int TRANSIT_FLAG_IS_RECENTS = 128;
    public static final int TRANSIT_FLAG_KEYGUARD_APPEARING = 2048;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY = 256;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_NO_ANIMATION = 2;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_SUBTLE_ANIMATION = 8;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_LAUNCHER_CLEAR_SNAPSHOT = 512;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_SHADE = 1;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_WITH_WALLPAPER = 4;
    public static final int TRANSIT_FLAG_KEYGUARD_LOCKED = 64;
    public static final int TRANSIT_FLAG_KEYGUARD_OCCLUDING = 4096;
    public static final int TRANSIT_FLAG_KEYGUARD_UNOCCLUDING = 8192;
    public static final int TRANSIT_FLAG_KEYGUARD_WITH_APP_LAUNCH = 131072;
    public static final int TRANSIT_FLAG_MINIMIZE = 524288;
    public static final int TRANSIT_FLAG_OPEN_BEHIND = 32;
    public static final int TRANSIT_FLAG_PHYSICAL_DISPLAY_SWITCH = 16384;

    @Deprecated
    public static final int TRANSIT_KEYGUARD_GOING_AWAY = 7;
    public static final int TRANSIT_KEYGUARD_OCCLUDE = 8;
    public static final int TRANSIT_KEYGUARD_UNOCCLUDE = 9;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_OLD_ACTIVITY_CLOSE = 7;
    public static final int TRANSIT_OLD_ACTIVITY_OPEN = 6;
    public static final int TRANSIT_OLD_ACTIVITY_RELAUNCH = 18;
    public static final int TRANSIT_OLD_CRASHING_ACTIVITY_CLOSE = 26;
    public static final int TRANSIT_OLD_DREAM_ACTIVITY_CLOSE = 32;
    public static final int TRANSIT_OLD_DREAM_ACTIVITY_OPEN = 31;
    public static final int TRANSIT_OLD_KEYGUARD_GOING_AWAY = 20;
    public static final int TRANSIT_OLD_KEYGUARD_GOING_AWAY_ON_WALLPAPER = 21;
    public static final int TRANSIT_OLD_KEYGUARD_OCCLUDE = 22;
    public static final int TRANSIT_OLD_KEYGUARD_OCCLUDE_BY_DREAM = 33;
    public static final int TRANSIT_OLD_KEYGUARD_UNOCCLUDE = 23;
    public static final int TRANSIT_OLD_NONE = 0;
    public static final int TRANSIT_OLD_TASK_CHANGE_WINDOWING_MODE = 27;
    public static final int TRANSIT_OLD_TASK_CLOSE = 9;
    public static final int TRANSIT_OLD_TASK_FRAGMENT_CHANGE = 30;
    public static final int TRANSIT_OLD_TASK_FRAGMENT_CLOSE = 29;
    public static final int TRANSIT_OLD_TASK_FRAGMENT_OPEN = 28;
    public static final int TRANSIT_OLD_TASK_OPEN = 8;
    public static final int TRANSIT_OLD_TASK_OPEN_BEHIND = 16;
    public static final int TRANSIT_OLD_TASK_TO_BACK = 11;
    public static final int TRANSIT_OLD_TASK_TO_FRONT = 10;
    public static final int TRANSIT_OLD_TRANSLUCENT_ACTIVITY_CLOSE = 25;
    public static final int TRANSIT_OLD_TRANSLUCENT_ACTIVITY_OPEN = 24;
    public static final int TRANSIT_OLD_UNSET = -1;
    public static final int TRANSIT_OLD_WALLPAPER_CLOSE = 12;
    public static final int TRANSIT_OLD_WALLPAPER_INTRA_CLOSE = 15;
    public static final int TRANSIT_OLD_WALLPAPER_INTRA_OPEN = 14;
    public static final int TRANSIT_OLD_WALLPAPER_OPEN = 13;
    public static final int TRANSIT_OPEN = 1;
    public static final int TRANSIT_PIP = 10;
    public static final int TRANSIT_RELAUNCH = 5;
    public static final int TRANSIT_SLEEP = 12;
    public static final int TRANSIT_TO_BACK = 4;
    public static final int TRANSIT_TO_FRONT = 3;
    public static final int TRANSIT_WAKE = 11;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CompatSmallScreenPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayImePolicy {
    }

    public interface KeyboardShortcutsReceiver {
        void onKeyboardShortcutsReceived(List<KeyboardShortcutGroup> list);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RemoveContentMode {
    }

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenRecordingState {
    }

    public @interface ScreenshotSource {
        public static final int SCREENSHOT_ACCESSIBILITY_ACTIONS = 4;
        public static final int SCREENSHOT_GLOBAL_ACTIONS = 0;
        public static final int SCREENSHOT_KEY_CHORD = 1;
        public static final int SCREENSHOT_KEY_OTHER = 2;
        public static final int SCREENSHOT_OTHER = 5;
        public static final int SCREENSHOT_OVERVIEW = 3;
        public static final int SCREENSHOT_VENDOR_GESTURE = 6;
    }

    public @interface ScreenshotType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShellRootLayer {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransitionFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransitionOldType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransitionType {
    }

    @SystemApi
    Region getCurrentImeTouchRegion();

    @Deprecated
    Display getDefaultDisplay();

    void removeViewImmediate(View view);

    void requestAppKeyboardShortcuts(KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i);

    public static class BadTokenException extends RuntimeException {
        public BadTokenException() {
        }

        public BadTokenException(String name) {
            super(name);
        }
    }

    public static class InvalidDisplayException extends RuntimeException {
        public InvalidDisplayException() {
        }

        public InvalidDisplayException(String name) {
            super(name);
        }
    }

    default WindowMetrics getCurrentWindowMetrics() {
        throw new UnsupportedOperationException();
    }

    default WindowMetrics getMaximumWindowMetrics() {
        throw new UnsupportedOperationException();
    }

    default Set<WindowMetrics> getPossibleMaximumWindowMetrics(int displayId) {
        throw new UnsupportedOperationException();
    }

    static {
        ACTIVITY_EMBEDDING_GUARD_WITH_ANDROID_15 = SystemProperties.getBoolean("persist.wm.extensions.activity_embedding_guard_with_android_15", !CoreRune.MW_EMBED_ACTIVITY);
        HAS_WINDOW_EXTENSIONS_ON_DEVICE = SystemProperties.getBoolean("persist.wm.extensions.enabled", false);
    }

    static boolean hasWindowExtensionsEnabled() {
        if ((!Flags.enableWmExtensionsForAllFlag() && ACTIVITY_EMBEDDING_GUARD_WITH_ANDROID_15) || !HAS_WINDOW_EXTENSIONS_ON_DEVICE) {
            return false;
        }
        try {
            Context context = ActivityThread.currentApplication();
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
                return false;
            }
            return ActivityTaskManager.supportsMultiWindow(context);
        } catch (Exception e) {
            Log.e("WindowManager", "Unable to read if the device supports multi window", e);
            return false;
        }
    }

    default void requestImeKeyboardShortcuts(KeyboardShortcutsReceiver receiver, int deviceId) {
    }

    default void setShouldShowWithInsecureKeyguard(int displayId, boolean shouldShow) {
    }

    default void setShouldShowSystemDecors(int displayId, boolean shouldShow) {
    }

    default boolean shouldShowSystemDecors(int displayId) {
        return false;
    }

    default void setDisplayImePolicy(int displayId, int imePolicy) {
    }

    default int getDisplayImePolicy(int displayId) {
        return 1;
    }

    default boolean isGlobalKey(int keyCode) {
        return false;
    }

    default boolean isCrossWindowBlurEnabled() {
        return false;
    }

    default void addCrossWindowBlurEnabledListener(Consumer<Boolean> listener) {
    }

    default void addCrossWindowBlurEnabledListener(Executor executor, Consumer<Boolean> listener) {
    }

    default void removeCrossWindowBlurEnabledListener(Consumer<Boolean> listener) {
    }

    default void addProposedRotationListener(Executor executor, IntConsumer listener) {
    }

    default void removeProposedRotationListener(IntConsumer listener) {
    }

    static String transitTypeToString(int type) {
        switch (type) {
            case 0:
                return KeyProperties.DIGEST_NONE;
            case 1:
                return "OPEN";
            case 2:
                return "CLOSE";
            case 3:
                return "TO_FRONT";
            case 4:
                return "TO_BACK";
            case 5:
                return "RELAUNCH";
            case 6:
                return "CHANGE";
            case 7:
                return "KEYGUARD_GOING_AWAY";
            case 8:
                return "KEYGUARD_OCCLUDE";
            case 9:
                return "KEYGUARD_UNOCCLUDE";
            case 10:
                return "PIP";
            case 11:
                return "WAKE";
            case 12:
                return "SLEEP";
            case 1000:
                return "FIRST_CUSTOM";
            default:
                if (type > 1000) {
                    return "FIRST_CUSTOM+" + (type - 1000);
                }
                return "UNKNOWN(" + type + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    static float fixScale(float scale) {
        return Math.max(Math.min(scale, 20.0f), 0.0f);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams implements Parcelable {
        public static final int ACCESSIBILITY_ANCHOR_CHANGED = 16777216;
        public static final int ACCESSIBILITY_TITLE_CHANGED = 33554432;
        public static final int ALPHA_CHANGED = 128;
        public static final int ANIMATION_CHANGED = 16;
        public static final int BLUR_BEHIND_RADIUS_CHANGED = 536870912;
        public static final float BRIGHTNESS_OVERRIDE_FULL = 1.0f;
        public static final float BRIGHTNESS_OVERRIDE_NONE = -1.0f;
        public static final float BRIGHTNESS_OVERRIDE_OFF = 0.0f;
        public static final int BUTTON_BRIGHTNESS_CHANGED = 8192;
        public static final int COLOR_MODE_CHANGED = 67108864;
        public static final int COVER_MODE_HIDE_SVIEW_ONCE = 2;
        public static final int COVER_MODE_NONE = 0;
        public static final int COVER_MODE_SVIEW = 1;
        public static final int COVER_MODE_SVIEW_SUB_WINDOW = 10;
        public static final int DIM_AMOUNT_CHANGED = 32;
        public static final int DISPLAY_FLAGS_CHANGED = 4194304;
        public static final int DISPLAY_FLAG_DISABLE_HDR_CONVERSION = 1;
        public static final int EXTENSION_FLAG_DECOR_CAPTION_WINDOW = 256;
        public static final int EXTENSION_FLAG_DELIVER_OUTSIDE_TOUCH = 134217728;
        public static final int EXTENSION_FLAG_DEX_TOUCH_PAD_FLAG_ABS_COORDINATE = 1073741824;
        public static final int EXTENSION_FLAG_DEX_TOUCH_PAD_WINDOW = 536870912;
        public static final int EXTENSION_FLAG_DOZE_MODE = 262144;
        public static final int EXTENSION_FLAG_FIXED_ORIENTATION_PORTRAIT = 8;
        public static final int EXTENSION_FLAG_FLEX_SCROLL_WHEEL_WINDOW = 8388608;
        public static final int EXTENSION_FLAG_FORCE_LIGHT_NAVIGATION_BAR = 1048576;
        public static final int EXTENSION_FLAG_HIDE_MINIMIZE_CONTAINER = 33554432;
        public static final int EXTENSION_FLAG_MULTI_WINDOW_HANDLER_HIDDEN = 16777216;
        public static final int EXTENSION_FLAG_SHOULD_NOT_AFFECT_LIGHT_BAR_APPEARANCE = 4194304;
        public static final int EXTENSION_FLAG_SVIEW_COVER = 268435456;
        public static final int EXTENSION_FLAG_TRANSPARENT_POP_OVER = 2;
        public static final int EXTENSION_FLAG_USE_LAYOUT_IN_UDC_CUTOUT = 8192;
        public static final int FIRST_APPLICATION_WINDOW = 1;
        public static final int FIRST_SUB_WINDOW = 1000;
        public static final int FIRST_SYSTEM_WINDOW = 2000;
        public static final int FLAGS_CHANGED = 4;
        public static final int FLAG_ALLOW_LOCK_WHILE_SCREEN_ON = 1;
        public static final int FLAG_ALT_FOCUSABLE_IM = 131072;
        public static final int FLAG_BLUR_BEHIND = 4;
        public static final int FLAG_DIM_BEHIND = 2;

        @Deprecated
        public static final int FLAG_DISMISS_KEYGUARD = 4194304;

        @Deprecated
        public static final int FLAG_DITHER = 4096;
        public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = Integer.MIN_VALUE;

        @Deprecated
        public static final int FLAG_FORCE_NOT_FULLSCREEN = 2048;

        @Deprecated
        public static final int FLAG_FULLSCREEN = 1024;
        public static final int FLAG_HARDWARE_ACCELERATED = 16777216;
        public static final int FLAG_IGNORE_CHEEK_PRESSES = 32768;
        public static final int FLAG_KEEP_SCREEN_ON = 128;

        @Deprecated
        public static final int FLAG_LAYOUT_ATTACHED_IN_DECOR = 1073741824;

        @Deprecated
        public static final int FLAG_LAYOUT_INSET_DECOR = 65536;

        @Deprecated
        public static final int FLAG_LAYOUT_IN_OVERSCAN = 33554432;
        public static final int FLAG_LAYOUT_IN_SCREEN = 256;
        public static final int FLAG_LAYOUT_NO_LIMITS = 512;
        public static final int FLAG_LOCAL_FOCUS_MODE = 268435456;
        public static final int FLAG_NOT_FOCUSABLE = 8;
        public static final int FLAG_NOT_TOUCHABLE = 16;
        public static final int FLAG_NOT_TOUCH_MODAL = 32;
        public static final int FLAG_SCALED = 16384;
        public static final int FLAG_SECURE = 8192;
        public static final int FLAG_SHOW_WALLPAPER = 1048576;

        @Deprecated
        public static final int FLAG_SHOW_WHEN_LOCKED = 524288;
        public static final int FLAG_SLIPPERY = 536870912;
        public static final int FLAG_SPLIT_TOUCH = 8388608;

        @Deprecated
        public static final int FLAG_TOUCHABLE_WHEN_WAKING = 64;

        @Deprecated
        public static final int FLAG_TRANSLUCENT_NAVIGATION = 134217728;

        @Deprecated
        public static final int FLAG_TRANSLUCENT_STATUS = 67108864;

        @Deprecated
        public static final int FLAG_TURN_SCREEN_ON = 2097152;
        public static final int FLAG_WATCH_OUTSIDE_TOUCH = 262144;
        public static final int FORMAT_CHANGED = 8;
        public static final int INPUT_FEATURES_CHANGED = 65536;
        public static final int INPUT_FEATURE_DISABLE_USER_ACTIVITY = 2;
        public static final int INPUT_FEATURE_NO_INPUT_CHANNEL = 1;
        public static final int INPUT_FEATURE_SENSITIVE_FOR_PRIVACY = 8;
        public static final int INPUT_FEATURE_SPY = 4;
        public static final int INSET_FLAGS_CHANGED = 134217728;
        public static final int INVALID_WINDOW_TYPE = -1;
        public static final int LAST_APPLICATION_WINDOW = 99;
        public static final int LAST_SUB_WINDOW = 1999;
        public static final int LAST_SYSTEM_WINDOW = 2999;
        public static final int LAYOUT_CHANGED = 1;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS = 3;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT = 0;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER = 2;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;
        public static final int MEMORY_TYPE_CHANGED = 256;

        @Deprecated
        public static final int MEMORY_TYPE_GPU = 2;

        @Deprecated
        public static final int MEMORY_TYPE_HARDWARE = 1;

        @Deprecated
        public static final int MEMORY_TYPE_NORMAL = 0;

        @Deprecated
        public static final int MEMORY_TYPE_PUSH_BUFFERS = 3;
        public static final int MINIMAL_POST_PROCESSING_PREFERENCE_CHANGED = 268435456;
        public static final int MULTI_WINDOW_FLAG_DIVIDER_RESIZE_LAYOUT = 64;
        public static final int MULTI_WINDOW_FLAG_EAVESDROP_DRAG_EVENT = 16;
        public static final int MULTI_WINDOW_FLAG_FORCE_HIDE_FLOATING_WINDOW = 256;
        public static final int MULTI_WINDOW_FLAG_FORCE_HIDE_FLOATING_WINDOW_WITHOUT_ANIMATION = 512;
        public static final int MULTI_WINDOW_FLAG_MENU = 1;
        public static final int MULTI_WINDOW_FLAG_MENU_POPUP = 2;
        public static final int MULTI_WINDOW_FLAG_MENU_TOOLTIP = 8;
        public static final int MULTI_WINDOW_FLAG_NAVIGATION_BAR_TRANSPARENT = 4;
        public static final int PREFERRED_DISPLAY_MODE_ID = 8388608;
        public static final int PREFERRED_MAX_DISPLAY_REFRESH_RATE = Integer.MIN_VALUE;
        public static final int PREFERRED_MIN_DISPLAY_REFRESH_RATE = 1073741824;
        public static final int PREFERRED_REFRESH_RATE_CHANGED = 2097152;
        public static final int PRIVATE_FLAGS_CHANGED = 131072;
        public static final int PRIVATE_FLAG_APP_PROGRESS_GENERATION_ALLOWED = 128;
        public static final int PRIVATE_FLAG_COLOR_SPACE_AGNOSTIC = 16777216;
        public static final int PRIVATE_FLAG_CONSUME_IME_INSETS = 33554432;
        public static final int PRIVATE_FLAG_DISABLE_WALLPAPER_TOUCH_EVENTS = 1024;
        public static final int PRIVATE_FLAG_EDGE_TO_EDGE_ENFORCED = 2048;
        public static final int PRIVATE_FLAG_EXCLUDE_FROM_SCREEN_MAGNIFICATION = 2097152;
        public static final int PRIVATE_FLAG_FIT_INSETS_CONTROLLED = 268435456;
        public static final int PRIVATE_FLAG_FORCE_DECOR_VIEW_VISIBILITY = 8192;
        public static final int PRIVATE_FLAG_FORCE_DRAW_BAR_BACKGROUNDS = 32768;
        public static final int PRIVATE_FLAG_FORCE_HARDWARE_ACCELERATED = 2;
        public static final int PRIVATE_FLAG_IMMERSIVE_CONFIRMATION_WINDOW = 131072;
        public static final int PRIVATE_FLAG_INSET_PARENT_FRAME_BY_IME = 1073741824;
        public static final int PRIVATE_FLAG_INTERCEPT_GLOBAL_DRAG_AND_DROP = Integer.MIN_VALUE;
        public static final int PRIVATE_FLAG_IS_ROUNDED_CORNERS_OVERLAY = 1048576;
        public static final int PRIVATE_FLAG_LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME = 16384;
        public static final int PRIVATE_FLAG_LAYOUT_SIZE_EXTENDED_BY_CUTOUT = 4096;
        public static final int PRIVATE_FLAG_NOT_MAGNIFIABLE = 4194304;
        public static final int PRIVATE_FLAG_NO_MOVE_ANIMATION = 64;
        public static final int PRIVATE_FLAG_OPTIMIZE_MEASURE = 512;
        public static final int PRIVATE_FLAG_OVERRIDE_LAYOUT_IN_DISPLAY_CUTOUT_MODE = 262144;
        public static final int PRIVATE_FLAG_SUSTAINED_PERFORMANCE_MODE = 65536;
        public static final int PRIVATE_FLAG_SYSTEM_APPLICATION_OVERLAY = 8;
        public static final int PRIVATE_FLAG_SYSTEM_ERROR = 256;
        public static final int PRIVATE_FLAG_TRUSTED_OVERLAY = 536870912;
        public static final int PRIVATE_FLAG_UNRESTRICTED_GESTURE_EXCLUSION = 32;
        public static final int PRIVATE_FLAG_WANTS_OFFSET_NOTIFICATIONS = 4;
        public static final int ROTATION_ANIMATION_CHANGED = 4096;
        public static final int ROTATION_ANIMATION_CROSSFADE = 1;
        public static final int ROTATION_ANIMATION_JUMPCUT = 2;
        public static final int ROTATION_ANIMATION_ROTATE = 0;
        public static final int ROTATION_ANIMATION_SEAMLESS = 3;
        public static final int ROTATION_ANIMATION_UNSPECIFIED = -1;
        public static final int SCREEN_BRIGHTNESS_CHANGED = 2048;
        public static final int SCREEN_DIM_DURATION_CHANGED = 1;
        public static final int SCREEN_ORIENTATION_CHANGED = 1024;
        public static final int SEM_EXTENSION_FLAG_CHANGE_DIM_EFFECT_TO_BLUR = 64;
        public static final int SEM_EXTENSION_FLAG_CONTENT_RESIZE_ANIMATION = 16384;
        public static final int SEM_EXTENSION_FLAG_DELAY_RESIZE_ON_SOFT_INPUT = 512;
        public static final int SEM_EXTENSION_FLAG_FAKE_FOCUS = 65536;
        public static final int SEM_EXTENSION_FLAG_FORCE_HIDE_DEX_LOADING_SCREEN = 4096;
        public static final int SEM_EXTENSION_FLAG_FORCE_HIDE_FLOATING_MULTIWINDOW = 67108864;
        public static final int SEM_EXTENSION_FLAG_FORCE_TRUSTED_OVERLAY = 131072;
        public static final int SEM_EXTENSION_FLAG_INTERNAL_PRESENTATION_ONLY = Integer.MIN_VALUE;
        public static final int SEM_EXTENSION_FLAG_NO_SURFACE_BUFFER = 128;
        public static final int SEM_EXTENSION_FLAG_OVERRIDE_SYSTEM_UI_POLICY = 32;
        public static final int SEM_EXTENSION_FLAG_RESIZE_FULLSCREEN_WINDOW_ON_SOFT_INPUT = 1;
        public static final int SEM_PRIVATE_FLAG_NO_MOVE_ANIMATION = 64;
        public static final int SEM_PRIVATE_FLAG_SHOW_FOR_ALL_USERS = 16;
        public static final int SEM_TYPE_AIR_COMMAND = 2401;
        public static final int SEM_TYPE_AIR_TRANSLATOR = 2442;
        public static final int SEM_TYPE_BOOT_PROGRESS = 2021;
        public static final int SEM_TYPE_CARMODE_BAR = 2270;
        public static final int SEM_TYPE_CARMODE_BAR_OVERLAY = 2271;
        public static final int SEM_TYPE_COCKTAIL_BAR_OVERLAY = 2222;
        public static final int SEM_TYPE_FLOATING_APPLICATION_WINDOW = 2413;
        public static final int SEM_TYPE_INTERNAL_PRESENTATION = 2407;
        public static final int SEM_TYPE_NAVIGATION_BAR_OVERLAY = 2024;
        public static final int SEM_TYPE_POPUP_PLAYER = 2406;
        public static final int SEM_TYPE_SCROLL_CAPTURE = 2441;
        public static final int SEM_TYPE_SMART_SELECT = 2440;
        public static final int SEM_TYPE_STATUS_BAR_OVERLAY = 2280;
        public static final int SEM_TYPE_STATUS_BAR_PANEL_USER = 2281;
        public static final int SEM_TYPE_SVIEW_COVER_DIALOG = 2099;
        public static final int SEM_TYPE_UNIVERSAL_SWITCH = 2405;
        public static final int SOFT_INPUT_ADJUST_NOTHING = 48;
        public static final int SOFT_INPUT_ADJUST_PAN = 32;

        @Deprecated
        public static final int SOFT_INPUT_ADJUST_RESIZE = 16;
        public static final int SOFT_INPUT_ADJUST_UNSPECIFIED = 0;
        public static final int SOFT_INPUT_IS_FORWARD_NAVIGATION = 256;
        public static final int SOFT_INPUT_MASK_ADJUST = 240;
        public static final int SOFT_INPUT_MASK_STATE = 15;
        public static final int SOFT_INPUT_MODE_CHANGED = 512;
        public static final int SOFT_INPUT_STATE_ALWAYS_HIDDEN = 3;
        public static final int SOFT_INPUT_STATE_ALWAYS_VISIBLE = 5;
        public static final int SOFT_INPUT_STATE_HIDDEN = 2;
        public static final int SOFT_INPUT_STATE_UNCHANGED = 1;
        public static final int SOFT_INPUT_STATE_UNSPECIFIED = 0;
        public static final int SOFT_INPUT_STATE_VISIBLE = 4;
        public static final int SURFACE_INSETS_CHANGED = 1048576;
        public static final int SURFACE_TYPE_CAPTION_OF_TASK = 3;
        public static final int SURFACE_TYPE_COLOR_FADE = 2;
        public static final int SURFACE_TYPE_FP_ICON_VIEW = 5;
        public static final int SURFACE_TYPE_FP_MASK_VIEW = 4;
        public static final int SURFACE_TYPE_ROTATION_LAYER = 1;
        public static final int SURFACE_TYPE_UNSUPPORTED = 0;

        @SystemApi
        public static final int SYSTEM_FLAG_HIDE_NON_SYSTEM_OVERLAY_WINDOWS = 524288;

        @SystemApi
        public static final int SYSTEM_FLAG_SHOW_FOR_ALL_USERS = 16;
        public static final int SYSTEM_UI_LISTENER_CHANGED = 32768;
        public static final int SYSTEM_UI_VISIBILITY_CHANGED = 16384;
        public static final int TITLE_CHANGED = 64;
        public static final int TRANSLUCENT_FLAGS_CHANGED = 524288;
        public static final int TYPE_ACCESSIBILITY_MAGNIFICATION_OVERLAY = 2039;
        public static final int TYPE_ACCESSIBILITY_OVERLAY = 2032;
        public static final int TYPE_APPLICATION = 2;
        public static final int TYPE_APPLICATION_ABOVE_SUB_PANEL = 1005;
        public static final int TYPE_APPLICATION_ATTACHED_DIALOG = 1003;
        public static final int TYPE_APPLICATION_MEDIA = 1001;
        public static final int TYPE_APPLICATION_MEDIA_OVERLAY = 1004;
        public static final int TYPE_APPLICATION_OVERLAY = 2038;
        public static final int TYPE_APPLICATION_PANEL = 1000;
        public static final int TYPE_APPLICATION_STARTING = 3;
        public static final int TYPE_APPLICATION_SUB_PANEL = 1002;
        public static final int TYPE_BASE_APPLICATION = 1;
        public static final int TYPE_BIXBY_CLIENT = 2414;
        public static final int TYPE_BOOT_PROGRESS = 2021;
        public static final int TYPE_CHANGED = 2;
        public static final int TYPE_COVER_SCREEN_BASE = 2620;
        public static final int TYPE_DIALOG_UNDER_INPUT_METHOD = 2624;
        public static final int TYPE_DISPLAY_CUTOUT_BACKGROUND = 2617;
        public static final int TYPE_DISPLAY_OVERLAY = 2026;
        public static final int TYPE_DOCK_DIVIDER = 2034;
        public static final int TYPE_DRAG = 2016;
        public static final int TYPE_DRAWN_APPLICATION = 4;
        public static final int TYPE_EDGE_OVERLAY = 2226;
        public static final int TYPE_FINGERPRINT_ON_DISPLAY = 2619;
        public static final int TYPE_FINGERPRINT_OVERLAY = 2618;
        public static final int TYPE_GAME_TOOL = 2430;
        public static final int TYPE_GAME_TOOL_OVERLAY = 2431;
        public static final int TYPE_GLOBAL_ACTION = 2411;
        public static final int TYPE_INPUT_CONSUMER = 2022;
        public static final int TYPE_INPUT_METHOD = 2011;
        public static final int TYPE_INPUT_METHOD_DIALOG = 2012;
        public static final int TYPE_INPUT_METHOD_PANEL = 1100;
        public static final int TYPE_KEYGUARD = 2004;
        public static final int TYPE_KEYGUARD_DIALOG = 2009;
        public static final int TYPE_MAGNIFICATION_OVERLAY = 2027;
        public static final int TYPE_MULTIWINDOW_DISMISS_VIEW = 2606;
        public static final int TYPE_MULTIWINDOW_FLEX_FLOATING_ICON = 2605;
        public static final int TYPE_MULTIWINDOW_FLEX_FLOATING_ICON_MOVABLE = 2606;
        public static final int TYPE_MULTIWINDOW_MINIMIZE_CONTAINER = 2604;
        public static final int TYPE_MULTIWINDOW_SHELL_OVERLAY = 2603;
        public static final int TYPE_MULTI_SPLIT_CELL_DIVIDER = 2614;
        public static final int TYPE_NAVBAR_GESTURE_INTERCEPT_OVERLAY = 2274;
        public static final int TYPE_NAVIGATION_BAR = 2019;
        public static final int TYPE_NAVIGATION_BAR_PANEL = 2024;
        public static final int TYPE_NAVIGATION_BAR_STANDALONE = 2623;
        public static final int TYPE_NIGHT_CLOCK_BACKGROUND = 2228;
        public static final int TYPE_NIGHT_CLOCK_OVERLAY = 2227;
        public static final int TYPE_NOTIFICATION_SHADE = 2040;
        public static final int TYPE_NOTIFICATION_SHADE_STANDALONE = 2622;
        public static final int TYPE_NOTIFICATION_SHADE_WIDGET = 2415;
        public static final int TYPE_ONE_HAND_OP_CONTROLLER = 2600;
        public static final int TYPE_ONE_HAND_OP_HANDLER = 2601;
        public static final int TYPE_PENTASTIC_ANIM = 2403;
        public static final int TYPE_PENTASTIC_ICON = 2402;

        @Deprecated
        public static final int TYPE_PHONE = 2002;
        public static final int TYPE_POINTER = 2018;
        public static final int TYPE_PRESENTATION = 2037;

        @Deprecated
        public static final int TYPE_PRIORITY_PHONE = 2007;
        public static final int TYPE_PRIVATE_PRESENTATION = 2030;
        public static final int TYPE_QS_DIALOG = 2035;
        public static final int TYPE_RECENTS_PANEL = 2095;
        public static final int TYPE_SCREENSHOT = 2036;
        public static final int TYPE_SCREENSHOT_EFFECT = 2408;
        public static final int TYPE_SEARCH_BAR = 2001;
        public static final int TYPE_SECURE_SYSTEM_OVERLAY = 2015;
        public static final int TYPE_STATUS_BAR = 2000;
        public static final int TYPE_STATUS_BAR_ADDITIONAL = 2041;
        public static final int TYPE_STATUS_BAR_PANEL = 2014;
        public static final int TYPE_STATUS_BAR_STANDALONE = 2621;
        public static final int TYPE_STATUS_BAR_SUB_PANEL = 2017;

        @Deprecated
        public static final int TYPE_SYSTEM_ALERT = 2003;
        public static final int TYPE_SYSTEM_DIALOG = 2008;
        public static final int TYPE_SYSTEM_DIALOG_EXTENSION = 2412;

        @Deprecated
        public static final int TYPE_SYSTEM_ERROR = 2010;

        @Deprecated
        public static final int TYPE_SYSTEM_OVERLAY = 2006;

        @Deprecated
        public static final int TYPE_TOAST = 2005;
        public static final int TYPE_TRANSIENT_LAUNCH_OVERLAY = 2632;
        public static final int TYPE_UNDER_APPLICATION_OVERLAY = 2225;
        public static final int TYPE_VIEW_COVER_BACKGROUND = 2631;
        public static final int TYPE_VIEW_COVER_DISPLAY = 2630;
        public static final int TYPE_VOICE_INTERACTION = 2031;
        public static final int TYPE_VOICE_INTERACTION_STARTING = 2033;
        public static final int TYPE_VOLUME_OVERLAY = 2020;
        public static final int TYPE_WALLPAPER = 2013;
        public static final int USER_ACTIVITY_TIMEOUT_CHANGED = 262144;
        public long accessibilityIdOfAnchor;
        public CharSequence accessibilityTitle;
        public float alpha;
        public float buttonBrightness;
        public int coverMode;
        public float dimAmount;
        public long dimDuration;

        @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "ALLOW_LOCK_WHILE_SCREEN_ON"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "DIM_BEHIND"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "BLUR_BEHIND"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "NOT_FOCUSABLE"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "NOT_TOUCHABLE"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "NOT_TOUCH_MODAL"), @ViewDebug.FlagToString(equals = 64, mask = 64, name = "TOUCHABLE_WHEN_WAKING"), @ViewDebug.FlagToString(equals = 128, mask = 128, name = "KEEP_SCREEN_ON"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "LAYOUT_IN_SCREEN"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "LAYOUT_NO_LIMITS"), @ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "FULLSCREEN"), @ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "FORCE_NOT_FULLSCREEN"), @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "DITHER"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "SECURE"), @ViewDebug.FlagToString(equals = 16384, mask = 16384, name = "SCALED"), @ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "IGNORE_CHEEK_PRESSES"), @ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "LAYOUT_INSET_DECOR"), @ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "ALT_FOCUSABLE_IM"), @ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "WATCH_OUTSIDE_TOUCH"), @ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "SHOW_WHEN_LOCKED"), @ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "SHOW_WALLPAPER"), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "TURN_SCREEN_ON"), @ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "DISMISS_KEYGUARD"), @ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "SPLIT_TOUCH"), @ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "HARDWARE_ACCELERATED"), @ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "LOCAL_FOCUS_MODE"), @ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "TRANSLUCENT_STATUS"), @ViewDebug.FlagToString(equals = 134217728, mask = 134217728, name = "TRANSLUCENT_NAVIGATION"), @ViewDebug.FlagToString(equals = 268435456, mask = 268435456, name = "LOCAL_FOCUS_MODE"), @ViewDebug.FlagToString(equals = 536870912, mask = 536870912, name = "FLAG_SLIPPERY"), @ViewDebug.FlagToString(equals = 1073741824, mask = 1073741824, name = "FLAG_LAYOUT_ATTACHED_IN_DECOR"), @ViewDebug.FlagToString(equals = Integer.MIN_VALUE, mask = Integer.MIN_VALUE, name = "DRAWS_SYSTEM_BAR_BACKGROUNDS")}, formatToHexString = true)
        public int flags;
        public int forciblyShownTypes;
        public int format;
        public int gravity;
        public boolean hasManualSurfaceInsets;
        public boolean hasSystemUiListeners;
        public long hideTimeoutMilliseconds;
        public float horizontalMargin;

        @ViewDebug.ExportedProperty
        public float horizontalWeight;
        public int inputFeatures;
        public final InsetsFlags insetsFlags;
        public int layoutInDisplayCutoutMode;
        private int mBlurBehindRadius;
        private int mColorMode;
        private int[] mCompatibilityParamsBackup;
        private float mDesiredHdrHeadroom;
        private int mDisplayFlags;
        private boolean mFitInsetsIgnoringVisibility;

        @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "LEFT"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "TOP"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "RIGHT"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "BOTTOM")})
        private int mFitInsetsSides;

        @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "STATUS_BARS"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "NAVIGATION_BARS"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "CAPTION_BAR"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "IME"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "SYSTEM_GESTURES"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "MANDATORY_SYSTEM_GESTURES"), @ViewDebug.FlagToString(equals = 64, mask = 64, name = "TAPPABLE_ELEMENT"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "WINDOW_DECOR")})
        private int mFitInsetsTypes;
        private boolean mFrameRateBoostOnTouch;
        private boolean mIsFrameRatePowerSavingsBalanced;
        private CharSequence mTitle;
        private boolean mWallpaperTouchEventsEnabled;
        public IBinder mWindowContextToken;

        @Deprecated
        public int memoryType;

        @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "MULTI_WINDOW_FLAG_MENU"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "MULTI_WINDOW_FLAG_MENU_POPUP"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "MULTI_WINDOW_FLAG_MENU_TOOLTIP"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "MULTI_WINDOW_FLAG_NAVIGATION_BAR_TRANSPARENT"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "MULTI_WINDOW_FLAG_EAVESDROP_DRAG_EVENT"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "MULTI_WINDOW_FLAG_FORCE_HIDE_FLOATING_WINDOW"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "MULTI_WINDOW_FLAG_FORCE_HIDE_FLOATING_WINDOW_WITHOUT_ANIMATION")})
        public int multiWindowFlags;
        public int navigationBarIconColor;
        public String packageName;
        public LayoutParams[] paramsForRotation;
        public boolean preferMinimalPostProcessing;
        public int preferredDisplayModeId;
        public float preferredMaxDisplayRefreshRate;
        public float preferredMinDisplayRefreshRate;
        public float preferredRefreshRate;
        public boolean preservePreviousSurfaceInsets;

        @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 2, mask = 2, name = "FORCE_HARDWARE_ACCELERATED"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "WANTS_OFFSET_NOTIFICATIONS"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "SHOW_FOR_ALL_USERS"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "UNRESTRICTED_GESTURE_EXCLUSION"), @ViewDebug.FlagToString(equals = 64, mask = 64, name = "NO_MOVE_ANIMATION"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "SYSTEM_ERROR"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "OPTIMIZE_MEASURE"), @ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "DISABLE_WALLPAPER_TOUCH_EVENTS"), @ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "EDGE_TO_EDGE_ENFORCED"), @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "LAYOUT_SIZE_EXTENDED_BY_CUTOUT"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "FORCE_DECOR_VIEW_VISIBILITY"), @ViewDebug.FlagToString(equals = 16384, mask = 16384, name = "LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME"), @ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "FORCE_DRAW_STATUS_BAR_BACKGROUND"), @ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "SUSTAINED_PERFORMANCE_MODE"), @ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "IMMERSIVE_CONFIRMATION_WINDOW"), @ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "OVERRIDE_LAYOUT_IN_DISPLAY_CUTOUT_MODE"), @ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "HIDE_NON_SYSTEM_OVERLAY_WINDOWS"), @ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "IS_ROUNDED_CORNERS_OVERLAY"), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "EXCLUDE_FROM_SCREEN_MAGNIFICATION"), @ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "NOT_MAGNIFIABLE"), @ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "COLOR_SPACE_AGNOSTIC"), @ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "CONSUME_IME_INSETS"), @ViewDebug.FlagToString(equals = 268435456, mask = 268435456, name = "FIT_INSETS_CONTROLLED"), @ViewDebug.FlagToString(equals = 536870912, mask = 536870912, name = "TRUSTED_OVERLAY"), @ViewDebug.FlagToString(equals = 1073741824, mask = 1073741824, name = "INSET_PARENT_FRAME_BY_IME"), @ViewDebug.FlagToString(equals = Integer.MIN_VALUE, mask = Integer.MIN_VALUE, name = "INTERCEPT_GLOBAL_DRAG_AND_DROP"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "SYSTEM_APPLICATION_OVERLAY")})
        public int privateFlags;
        public InsetsFrameProvider[] providedInsets;
        public boolean receiveInsetsIgnoringZOrder;
        public int rotationAnimation;

        @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "RESIZE_FULLSCREEN_WINDOW_ON_SOFT_INPUT"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "TRANSPARENT_POP_OVER"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "FIXED_ORIENTATION_PORTRAIT"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "OVERRIDE_SYSTEM_UI_POLICY"), @ViewDebug.FlagToString(equals = 64, mask = 64, name = "CHANGE_DIM_EFFECT_TO_BLUR"), @ViewDebug.FlagToString(equals = 128, mask = 128, name = "NO_SURFACE_BUFFER"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "DECOR_CAPTION_WINDOW"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "DELAY_RESIZE_ON_SOFT_INPUT"), @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "FORCE_HIDE_DEX_LOADING_SCREEN"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "USE_LAYOUT_IN_UDC_CUTOUT"), @ViewDebug.FlagToString(equals = 16384, mask = 16384, name = "CONTENT_RESIZE_ANIMATION"), @ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "FAKE_FOCUS"), @ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "FORCE_TRUSTED_OVERLAY"), @ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "DOZE_MODE"), @ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "FORCE_LIGHT_NAVIGATION_BAR"), @ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "SHOULD_NOT_AFFECT_LIGHT_BAR_APPEARANCE"), @ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "FLEX_SCROLL_WHEEL_WINDOW"), @ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "MULTI_WINDOW_HANDLER_HIDDEN"), @ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "FORCE_HIDE_FLOATING_MULTIWINDOW"), @ViewDebug.FlagToString(equals = 134217728, mask = 134217728, name = "DELIVER_OUTSIDE_TOUCH"), @ViewDebug.FlagToString(equals = 268435456, mask = 268435456, name = "SVIEW_COVER"), @ViewDebug.FlagToString(equals = 536870912, mask = 536870912, name = "DEX_TOUCH_PAD_WINDOW"), @ViewDebug.FlagToString(equals = 1073741824, mask = 1073741824, name = "DEX_TOUCH_PAD_FLAG_ABS_COORDINATE"), @ViewDebug.FlagToString(equals = Integer.MIN_VALUE, mask = Integer.MIN_VALUE, name = "INTERNAL_PRESENTATION_ONLY")})
        public int samsungFlags;
        public float screenBrightness;
        public long screenDimDuration;
        public int screenOrientation;
        public int softInputMode;
        public int subtreeSystemUiVisibility;
        public final Rect surfaceInsets;

        @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 3, to = "CAPTION_OF_TASK"), @ViewDebug.IntToString(from = 4, to = "FP_MASK_VIEW"), @ViewDebug.IntToString(from = 5, to = "FP_ICON_VIEW")})
        public int surfaceType;

        @Deprecated
        public int systemUiVisibility;
        public IBinder token;

        @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 1, to = "BASE_APPLICATION"), @ViewDebug.IntToString(from = 2, to = "APPLICATION"), @ViewDebug.IntToString(from = 3, to = "APPLICATION_STARTING"), @ViewDebug.IntToString(from = 4, to = "DRAWN_APPLICATION"), @ViewDebug.IntToString(from = 1000, to = "APPLICATION_PANEL"), @ViewDebug.IntToString(from = 1001, to = "APPLICATION_MEDIA"), @ViewDebug.IntToString(from = 1002, to = "APPLICATION_SUB_PANEL"), @ViewDebug.IntToString(from = 1005, to = "APPLICATION_ABOVE_SUB_PANEL"), @ViewDebug.IntToString(from = 1003, to = "APPLICATION_ATTACHED_DIALOG"), @ViewDebug.IntToString(from = 1004, to = "APPLICATION_MEDIA_OVERLAY"), @ViewDebug.IntToString(from = 2000, to = DevicePolicyResources.Drawables.Source.STATUS_BAR), @ViewDebug.IntToString(from = 2001, to = "SEARCH_BAR"), @ViewDebug.IntToString(from = 2002, to = "PHONE"), @ViewDebug.IntToString(from = 2003, to = "SYSTEM_ALERT"), @ViewDebug.IntToString(from = 2004, to = "KEYGUARD"), @ViewDebug.IntToString(from = 2005, to = "TOAST"), @ViewDebug.IntToString(from = 2006, to = "SYSTEM_OVERLAY"), @ViewDebug.IntToString(from = 2007, to = "PRIORITY_PHONE"), @ViewDebug.IntToString(from = 2008, to = "SYSTEM_DIALOG"), @ViewDebug.IntToString(from = 2009, to = "KEYGUARD_DIALOG"), @ViewDebug.IntToString(from = 2010, to = "SYSTEM_ERROR"), @ViewDebug.IntToString(from = 2011, to = "INPUT_METHOD"), @ViewDebug.IntToString(from = 2012, to = "INPUT_METHOD_DIALOG"), @ViewDebug.IntToString(from = 2013, to = "WALLPAPER"), @ViewDebug.IntToString(from = 2014, to = "STATUS_BAR_PANEL"), @ViewDebug.IntToString(from = 2015, to = "SECURE_SYSTEM_OVERLAY"), @ViewDebug.IntToString(from = 2016, to = "DRAG"), @ViewDebug.IntToString(from = 2017, to = "STATUS_BAR_SUB_PANEL"), @ViewDebug.IntToString(from = 2018, to = "POINTER"), @ViewDebug.IntToString(from = 2019, to = "NAVIGATION_BAR"), @ViewDebug.IntToString(from = 2020, to = "VOLUME_OVERLAY"), @ViewDebug.IntToString(from = 2021, to = "BOOT_PROGRESS"), @ViewDebug.IntToString(from = 2022, to = "INPUT_CONSUMER"), @ViewDebug.IntToString(from = 2024, to = "NAVIGATION_BAR_PANEL"), @ViewDebug.IntToString(from = 2026, to = "DISPLAY_OVERLAY"), @ViewDebug.IntToString(from = 2027, to = "MAGNIFICATION_OVERLAY"), @ViewDebug.IntToString(from = 2037, to = "PRESENTATION"), @ViewDebug.IntToString(from = 2030, to = "PRIVATE_PRESENTATION"), @ViewDebug.IntToString(from = 2031, to = "VOICE_INTERACTION"), @ViewDebug.IntToString(from = 2032, to = "ACCESSIBILITY_OVERLAY"), @ViewDebug.IntToString(from = 2033, to = "VOICE_INTERACTION_STARTING"), @ViewDebug.IntToString(from = 2034, to = "DOCK_DIVIDER"), @ViewDebug.IntToString(from = 2035, to = "QS_DIALOG"), @ViewDebug.IntToString(from = 2036, to = "SCREENSHOT"), @ViewDebug.IntToString(from = 2038, to = "APPLICATION_OVERLAY"), @ViewDebug.IntToString(from = 2039, to = "ACCESSIBILITY_MAGNIFICATION_OVERLAY"), @ViewDebug.IntToString(from = 2040, to = "NOTIFICATION_SHADE"), @ViewDebug.IntToString(from = 2041, to = "STATUS_BAR_ADDITIONAL"), @ViewDebug.IntToString(from = 2040, to = "NOTIFICATION_SHADE"), @ViewDebug.IntToString(from = TYPE_NOTIFICATION_SHADE_WIDGET, to = "NOTIFICATION_SHADE_WIDGET"), @ViewDebug.IntToString(from = TYPE_PENTASTIC_ICON, to = "PENTASTIC_ICON"), @ViewDebug.IntToString(from = TYPE_PENTASTIC_ANIM, to = "PENTASTIC_ANIM"), @ViewDebug.IntToString(from = TYPE_TRANSIENT_LAUNCH_OVERLAY, to = "TRANSIENT_LAUNCH_OVERLAY"), @ViewDebug.IntToString(from = TYPE_GAME_TOOL, to = "GAME_TOOL"), @ViewDebug.IntToString(from = TYPE_GAME_TOOL_OVERLAY, to = "GAME_TOOL_OVERLAY")})
        public int type;
        public long userActivityTimeout;
        public float verticalMargin;

        @ViewDebug.ExportedProperty
        public float verticalWeight;
        public int windowAnimations;

        @ViewDebug.ExportedProperty
        public int x;

        @ViewDebug.ExportedProperty
        public int y;
        private static boolean sToolkitSetFrameRateReadOnlyFlagValue = android.view.flags.Flags.toolkitSetFrameRateReadOnly();
        public static final Parcelable.Creator<LayoutParams> CREATOR = new Parcelable.Creator<LayoutParams>() { // from class: android.view.WindowManager.LayoutParams.1
            @Override // android.os.Parcelable.Creator
            public LayoutParams createFromParcel(Parcel in) {
                return new LayoutParams(in);
            }

            @Override // android.os.Parcelable.Creator
            public LayoutParams[] newArray(int size) {
                return new LayoutParams[size];
            }
        };

        public @interface DisplayFlags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface InputFeatureFlags {
        }

        @Retention(RetentionPolicy.SOURCE)
        @interface LayoutInDisplayCutoutMode {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface PrivateFlags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SoftInputModeFlags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SystemFlags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SystemUiVisibilityFlags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface WindowType {
        }

        public static boolean isSystemAlertWindowType(int type) {
            switch (type) {
                case 2002:
                case 2003:
                case 2006:
                case 2007:
                case 2010:
                case 2038:
                    return true;
                default:
                    return false;
            }
        }

        public static boolean mayUseInputMethod(int flags) {
            return ((flags & 8) == 8 || (flags & 131072) == 131072) ? false : true;
        }

        public void setFitInsetsTypes(int types) {
            this.mFitInsetsTypes = types;
            this.privateFlags |= 268435456;
        }

        public void setFitInsetsSides(int sides) {
            this.mFitInsetsSides = sides;
            this.privateFlags |= 268435456;
        }

        public void setFitInsetsIgnoringVisibility(boolean ignore) {
            this.mFitInsetsIgnoringVisibility = ignore;
            this.privateFlags |= 268435456;
        }

        public void setTrustedOverlay() {
            this.privateFlags |= 536870912;
        }

        @SystemApi
        public void setSystemApplicationOverlay(boolean isSystemApplicationOverlay) {
            if (isSystemApplicationOverlay) {
                this.privateFlags |= 8;
            } else {
                this.privateFlags &= -9;
            }
        }

        @SystemApi
        public boolean isSystemApplicationOverlay() {
            return (this.privateFlags & 8) == 8;
        }

        public void setWallpaperTouchEventsEnabled(boolean enable) {
            this.mWallpaperTouchEventsEnabled = enable;
        }

        public boolean areWallpaperTouchEventsEnabled() {
            return this.mWallpaperTouchEventsEnabled;
        }

        public void setCanPlayMoveAnimation(boolean enable) {
            if (enable) {
                this.privateFlags &= -65;
            } else {
                this.privateFlags |= 64;
            }
        }

        public boolean canPlayMoveAnimation() {
            return (this.privateFlags & 64) == 0;
        }

        public int getFitInsetsTypes() {
            return this.mFitInsetsTypes;
        }

        public int getFitInsetsSides() {
            return this.mFitInsetsSides;
        }

        public boolean isFitInsetsIgnoringVisibility() {
            return this.mFitInsetsIgnoringVisibility;
        }

        private void checkNonRecursiveParams() {
            if (this.paramsForRotation == null) {
                return;
            }
            for (int i = this.paramsForRotation.length - 1; i >= 0; i--) {
                if (this.paramsForRotation[i].paramsForRotation != null) {
                    throw new IllegalArgumentException("Params cannot contain params recursively.");
                }
            }
        }

        public LayoutParams forRotation(int rotation) {
            if (this.paramsForRotation == null || this.paramsForRotation.length <= rotation || this.paramsForRotation[rotation] == null) {
                return this;
            }
            return this.paramsForRotation[rotation];
        }

        public LayoutParams() {
            super(-1, -1);
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.dimDuration = -1L;
            this.screenDimDuration = -1L;
            this.navigationBarIconColor = 0;
            this.surfaceType = 0;
            this.coverMode = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new InsetsFlags();
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = 2;
            this.format = -1;
        }

        public LayoutParams(int _type) {
            super(-1, -1);
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.dimDuration = -1L;
            this.screenDimDuration = -1L;
            this.navigationBarIconColor = 0;
            this.surfaceType = 0;
            this.coverMode = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new InsetsFlags();
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = _type;
            this.format = -1;
        }

        public LayoutParams(int _type, int _flags) {
            super(-1, -1);
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.dimDuration = -1L;
            this.screenDimDuration = -1L;
            this.navigationBarIconColor = 0;
            this.surfaceType = 0;
            this.coverMode = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new InsetsFlags();
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = _type;
            this.flags = _flags;
            this.format = -1;
        }

        public LayoutParams(int _type, int _flags, int _format) {
            super(-1, -1);
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.dimDuration = -1L;
            this.screenDimDuration = -1L;
            this.navigationBarIconColor = 0;
            this.surfaceType = 0;
            this.coverMode = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new InsetsFlags();
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = _type;
            this.flags = _flags;
            this.format = _format;
        }

        public LayoutParams(int w, int h, int _type, int _flags, int _format) {
            super(w, h);
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.dimDuration = -1L;
            this.screenDimDuration = -1L;
            this.navigationBarIconColor = 0;
            this.surfaceType = 0;
            this.coverMode = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new InsetsFlags();
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = _type;
            this.flags = _flags;
            this.format = _format;
        }

        public LayoutParams(int w, int h, int xpos, int ypos, int _type, int _flags, int _format) {
            super(w, h);
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.dimDuration = -1L;
            this.screenDimDuration = -1L;
            this.navigationBarIconColor = 0;
            this.surfaceType = 0;
            this.coverMode = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new InsetsFlags();
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.x = xpos;
            this.y = ypos;
            this.type = _type;
            this.flags = _flags;
            this.format = _format;
        }

        public final void setTitle(CharSequence title) {
            if (title == null) {
                title = "";
            }
            this.mTitle = TextUtils.stringOrSpannedString(title);
        }

        public final CharSequence getTitle() {
            return this.mTitle != null ? this.mTitle : "";
        }

        public final void setSurfaceInsets(View view, boolean manual, boolean preservePrevious) {
            float scaleFactor;
            Configuration config = view.getResources().getConfiguration();
            if (config.windowConfiguration.isPopOver() || (CoreRune.MW_CAPTION_SHELL && (config.isDesktopModeEnabled() || config.windowConfiguration.getWindowingMode() == 5))) {
                scaleFactor = 3.0f;
            } else {
                scaleFactor = 2.0f;
            }
            int surfaceInset = (int) Math.ceil(view.getZ() * scaleFactor);
            if (surfaceInset == 0) {
                this.surfaceInsets.set(0, 0, 0, 0);
            } else {
                this.surfaceInsets.set(Math.max(surfaceInset, this.surfaceInsets.left), Math.max(surfaceInset, this.surfaceInsets.top), Math.max(surfaceInset, this.surfaceInsets.right), Math.max(surfaceInset, this.surfaceInsets.bottom));
            }
            this.hasManualSurfaceInsets = manual;
            this.preservePreviousSurfaceInsets = preservePrevious;
        }

        public boolean isHdrConversionEnabled() {
            return (this.mDisplayFlags & 1) == 0;
        }

        public void setHdrConversionEnabled(boolean enabled) {
            if (!enabled) {
                this.mDisplayFlags |= 1;
            } else {
                this.mDisplayFlags &= -2;
            }
        }

        public void setColorMode(int colorMode) {
            this.mColorMode = colorMode;
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public void setDesiredHdrHeadroom(float desiredHeadroom) {
            if (!Float.isFinite(desiredHeadroom)) {
                throw new IllegalArgumentException("desiredHeadroom must be finite: " + desiredHeadroom);
            }
            if (desiredHeadroom != 0.0f && (desiredHeadroom < 1.0f || desiredHeadroom > 10000.0f)) {
                throw new IllegalArgumentException("desiredHeadroom must be 0.0 or in the range [1.0, 10000.0f], received: " + desiredHeadroom);
            }
            this.mDesiredHdrHeadroom = desiredHeadroom;
        }

        public float getDesiredHdrHeadroom() {
            return this.mDesiredHdrHeadroom;
        }

        public void setFrameRateBoostOnTouchEnabled(boolean enabled) {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mFrameRateBoostOnTouch = enabled;
            }
        }

        public boolean getFrameRateBoostOnTouchEnabled() {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                return this.mFrameRateBoostOnTouch;
            }
            return true;
        }

        public void setFrameRatePowerSavingsBalanced(boolean enabled) {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mIsFrameRatePowerSavingsBalanced = enabled;
            }
        }

        public boolean isFrameRatePowerSavingsBalanced() {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                return this.mIsFrameRatePowerSavingsBalanced;
            }
            return true;
        }

        public void setBlurBehindRadius(int blurBehindRadius) {
            this.mBlurBehindRadius = blurBehindRadius;
        }

        public int getBlurBehindRadius() {
            return this.mBlurBehindRadius;
        }

        public final void semAddPrivateFlags(int flags) {
            this.privateFlags |= flags;
        }

        public final void semClearPrivateFlags(int flags) {
            this.privateFlags &= ~flags;
        }

        public final void semAddExtensionFlags(int flags) {
            this.samsungFlags |= flags;
        }

        public final void semClearExtensionFlags(int flags) {
            this.samsungFlags &= ~flags;
        }

        public final void semSetScreenTimeout(long timeout) {
            this.userActivityTimeout = timeout;
        }

        public final void semSetEnterDimDuration(long enterDuration) {
            this.dimDuration = enterDuration;
        }

        public final void semSetScreenDimDuration(long dimDuration) {
            this.screenDimDuration = dimDuration;
        }

        public final void semSetNavigationBarIconColor(int color) {
            this.navigationBarIconColor = color;
        }

        public final void semSetReceiveInsetsIgnoringZOrder(boolean receive) {
            this.receiveInsetsIgnoringZOrder = receive;
        }

        @SystemApi
        public final void setUserActivityTimeout(long timeout) {
            this.userActivityTimeout = timeout;
        }

        @SystemApi
        public final long getUserActivityTimeout() {
            return this.userActivityTimeout;
        }

        public final void setWindowContextToken(IBinder token) {
            this.mWindowContextToken = token;
        }

        public final IBinder getWindowContextToken() {
            return this.mWindowContextToken;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int parcelableFlags) {
            out.writeInt(this.width);
            out.writeInt(this.height);
            out.writeInt(this.x);
            out.writeInt(this.y);
            out.writeInt(this.type);
            out.writeInt(this.flags);
            out.writeInt(this.privateFlags);
            out.writeInt(this.softInputMode);
            out.writeInt(this.layoutInDisplayCutoutMode);
            out.writeInt(this.gravity);
            out.writeFloat(this.horizontalMargin);
            out.writeFloat(this.verticalMargin);
            out.writeInt(this.format);
            out.writeInt(this.windowAnimations);
            out.writeFloat(this.alpha);
            out.writeFloat(this.dimAmount);
            out.writeFloat(this.screenBrightness);
            out.writeFloat(this.buttonBrightness);
            out.writeInt(this.rotationAnimation);
            out.writeStrongBinder(this.token);
            out.writeStrongBinder(this.mWindowContextToken);
            out.writeString(this.packageName);
            TextUtils.writeToParcel(this.mTitle, out, parcelableFlags);
            out.writeInt(this.screenOrientation);
            out.writeFloat(this.preferredRefreshRate);
            out.writeInt(this.preferredDisplayModeId);
            out.writeFloat(this.preferredMinDisplayRefreshRate);
            out.writeFloat(this.preferredMaxDisplayRefreshRate);
            out.writeInt(this.systemUiVisibility);
            out.writeInt(this.subtreeSystemUiVisibility);
            out.writeBoolean(this.hasSystemUiListeners);
            out.writeInt(this.inputFeatures);
            out.writeLong(this.userActivityTimeout);
            out.writeInt(this.surfaceInsets.left);
            out.writeInt(this.surfaceInsets.top);
            out.writeInt(this.surfaceInsets.right);
            out.writeInt(this.surfaceInsets.bottom);
            out.writeBoolean(this.hasManualSurfaceInsets);
            out.writeBoolean(this.receiveInsetsIgnoringZOrder);
            out.writeBoolean(this.preservePreviousSurfaceInsets);
            out.writeLong(this.accessibilityIdOfAnchor);
            TextUtils.writeToParcel(this.accessibilityTitle, out, parcelableFlags);
            out.writeInt(this.mColorMode);
            out.writeLong(this.hideTimeoutMilliseconds);
            out.writeInt(this.insetsFlags.appearance);
            out.writeInt(this.insetsFlags.behavior);
            out.writeInt(this.mFitInsetsTypes);
            out.writeInt(this.mFitInsetsSides);
            out.writeBoolean(this.mFitInsetsIgnoringVisibility);
            out.writeBoolean(this.preferMinimalPostProcessing);
            out.writeInt(this.mBlurBehindRadius);
            out.writeBoolean(this.mWallpaperTouchEventsEnabled);
            out.writeTypedArray(this.providedInsets, 0);
            out.writeInt(this.forciblyShownTypes);
            checkNonRecursiveParams();
            out.writeTypedArray(this.paramsForRotation, 0);
            out.writeInt(this.mDisplayFlags);
            out.writeFloat(this.mDesiredHdrHeadroom);
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                out.writeBoolean(this.mFrameRateBoostOnTouch);
                out.writeBoolean(this.mIsFrameRatePowerSavingsBalanced);
            }
            out.writeLong(this.dimDuration);
            out.writeLong(this.screenDimDuration);
            out.writeInt(this.navigationBarIconColor);
            out.writeInt(this.samsungFlags);
            out.writeInt(this.surfaceType);
            out.writeInt(this.multiWindowFlags);
            out.writeInt(this.coverMode);
        }

        public LayoutParams(Parcel in) {
            this.surfaceInsets = new Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.dimDuration = -1L;
            this.screenDimDuration = -1L;
            this.navigationBarIconColor = 0;
            this.surfaceType = 0;
            this.coverMode = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new InsetsFlags();
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.width = in.readInt();
            this.height = in.readInt();
            this.x = in.readInt();
            this.y = in.readInt();
            this.type = in.readInt();
            this.flags = in.readInt();
            this.privateFlags = in.readInt();
            this.softInputMode = in.readInt();
            this.layoutInDisplayCutoutMode = in.readInt();
            this.gravity = in.readInt();
            this.horizontalMargin = in.readFloat();
            this.verticalMargin = in.readFloat();
            this.format = in.readInt();
            this.windowAnimations = in.readInt();
            this.alpha = in.readFloat();
            this.dimAmount = in.readFloat();
            this.screenBrightness = in.readFloat();
            this.buttonBrightness = in.readFloat();
            this.rotationAnimation = in.readInt();
            this.token = in.readStrongBinder();
            this.mWindowContextToken = in.readStrongBinder();
            this.packageName = in.readString();
            this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.screenOrientation = in.readInt();
            this.preferredRefreshRate = in.readFloat();
            this.preferredDisplayModeId = in.readInt();
            this.preferredMinDisplayRefreshRate = in.readFloat();
            this.preferredMaxDisplayRefreshRate = in.readFloat();
            this.systemUiVisibility = in.readInt();
            this.subtreeSystemUiVisibility = in.readInt();
            this.hasSystemUiListeners = in.readBoolean();
            this.inputFeatures = in.readInt();
            this.userActivityTimeout = in.readLong();
            this.surfaceInsets.left = in.readInt();
            this.surfaceInsets.top = in.readInt();
            this.surfaceInsets.right = in.readInt();
            this.surfaceInsets.bottom = in.readInt();
            this.hasManualSurfaceInsets = in.readBoolean();
            this.receiveInsetsIgnoringZOrder = in.readBoolean();
            this.preservePreviousSurfaceInsets = in.readBoolean();
            this.accessibilityIdOfAnchor = in.readLong();
            this.accessibilityTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.mColorMode = in.readInt();
            this.hideTimeoutMilliseconds = in.readLong();
            this.insetsFlags.appearance = in.readInt();
            this.insetsFlags.behavior = in.readInt();
            this.mFitInsetsTypes = in.readInt();
            this.mFitInsetsSides = in.readInt();
            this.mFitInsetsIgnoringVisibility = in.readBoolean();
            this.preferMinimalPostProcessing = in.readBoolean();
            this.mBlurBehindRadius = in.readInt();
            this.mWallpaperTouchEventsEnabled = in.readBoolean();
            this.providedInsets = (InsetsFrameProvider[]) in.createTypedArray(InsetsFrameProvider.CREATOR);
            this.forciblyShownTypes = in.readInt();
            this.paramsForRotation = (LayoutParams[]) in.createTypedArray(CREATOR);
            this.mDisplayFlags = in.readInt();
            this.mDesiredHdrHeadroom = in.readFloat();
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mFrameRateBoostOnTouch = in.readBoolean();
                this.mIsFrameRatePowerSavingsBalanced = in.readBoolean();
            }
            this.dimDuration = in.readLong();
            this.screenDimDuration = in.readLong();
            this.navigationBarIconColor = in.readInt();
            this.samsungFlags = in.readInt();
            this.surfaceType = in.readInt();
            this.multiWindowFlags = in.readInt();
            this.coverMode = in.readInt();
        }

        public final int copyFrom(LayoutParams o) {
            int changes = 0;
            if (this.width != o.width) {
                this.width = o.width;
                changes = 0 | 1;
            }
            if (this.height != o.height) {
                this.height = o.height;
                changes |= 1;
            }
            if (this.x != o.x) {
                this.x = o.x;
                changes |= 1;
            }
            if (this.y != o.y) {
                this.y = o.y;
                changes |= 1;
            }
            if (this.horizontalWeight != o.horizontalWeight) {
                this.horizontalWeight = o.horizontalWeight;
                changes |= 1;
            }
            if (this.verticalWeight != o.verticalWeight) {
                this.verticalWeight = o.verticalWeight;
                changes |= 1;
            }
            if (this.horizontalMargin != o.horizontalMargin) {
                this.horizontalMargin = o.horizontalMargin;
                changes |= 1;
            }
            if (this.verticalMargin != o.verticalMargin) {
                this.verticalMargin = o.verticalMargin;
                changes |= 1;
            }
            if (this.type != o.type) {
                this.type = o.type;
                changes |= 2;
            }
            if (this.flags != o.flags) {
                int diff = this.flags ^ o.flags;
                if ((201326592 & diff) != 0) {
                    changes |= 524288;
                }
                this.flags = o.flags;
                changes |= 4;
            }
            int diff2 = this.privateFlags;
            if (diff2 != o.privateFlags) {
                this.privateFlags = o.privateFlags;
                changes |= 131072;
            }
            if (this.softInputMode != o.softInputMode) {
                this.softInputMode = o.softInputMode;
                changes |= 512;
            }
            if (this.layoutInDisplayCutoutMode != o.layoutInDisplayCutoutMode) {
                this.layoutInDisplayCutoutMode = o.layoutInDisplayCutoutMode;
                changes |= 1;
            }
            if (this.gravity != o.gravity) {
                this.gravity = o.gravity;
                changes |= 1;
            }
            if (this.format != o.format) {
                this.format = o.format;
                changes |= 8;
            }
            if (this.windowAnimations != o.windowAnimations) {
                this.windowAnimations = o.windowAnimations;
                changes |= 16;
            }
            if (this.token == null) {
                this.token = o.token;
            }
            if (this.mWindowContextToken == null) {
                this.mWindowContextToken = o.mWindowContextToken;
            }
            if (this.packageName == null) {
                this.packageName = o.packageName;
            }
            if (!Objects.equals(this.mTitle, o.mTitle) && o.mTitle != null) {
                this.mTitle = o.mTitle;
                changes |= 64;
            }
            if (this.alpha != o.alpha) {
                this.alpha = o.alpha;
                changes |= 128;
            }
            if (this.dimAmount != o.dimAmount) {
                this.dimAmount = o.dimAmount;
                changes |= 32;
            }
            if (this.screenBrightness != o.screenBrightness) {
                this.screenBrightness = o.screenBrightness;
                changes |= 2048;
            }
            if (this.buttonBrightness != o.buttonBrightness) {
                this.buttonBrightness = o.buttonBrightness;
                changes |= 8192;
            }
            if (this.rotationAnimation != o.rotationAnimation) {
                this.rotationAnimation = o.rotationAnimation;
                changes |= 4096;
            }
            if (this.screenOrientation != o.screenOrientation) {
                this.screenOrientation = o.screenOrientation;
                changes |= 1024;
            }
            if (this.preferredRefreshRate != o.preferredRefreshRate) {
                this.preferredRefreshRate = o.preferredRefreshRate;
                changes |= 2097152;
            }
            if (this.preferredDisplayModeId != o.preferredDisplayModeId) {
                this.preferredDisplayModeId = o.preferredDisplayModeId;
                changes |= 8388608;
            }
            if (this.preferredMinDisplayRefreshRate != o.preferredMinDisplayRefreshRate) {
                this.preferredMinDisplayRefreshRate = o.preferredMinDisplayRefreshRate;
                changes |= 1073741824;
            }
            if (this.preferredMaxDisplayRefreshRate != o.preferredMaxDisplayRefreshRate) {
                this.preferredMaxDisplayRefreshRate = o.preferredMaxDisplayRefreshRate;
                changes |= Integer.MIN_VALUE;
            }
            if (this.mDisplayFlags != o.mDisplayFlags) {
                this.mDisplayFlags = o.mDisplayFlags;
                changes |= 4194304;
            }
            if (this.systemUiVisibility != o.systemUiVisibility || this.subtreeSystemUiVisibility != o.subtreeSystemUiVisibility) {
                this.systemUiVisibility = o.systemUiVisibility;
                this.subtreeSystemUiVisibility = o.subtreeSystemUiVisibility;
                changes |= 16384;
            }
            if (this.hasSystemUiListeners != o.hasSystemUiListeners) {
                this.hasSystemUiListeners = o.hasSystemUiListeners;
                changes |= 32768;
            }
            if (this.inputFeatures != o.inputFeatures) {
                this.inputFeatures = o.inputFeatures;
                changes |= 65536;
            }
            if (this.userActivityTimeout != o.userActivityTimeout) {
                this.userActivityTimeout = o.userActivityTimeout;
                changes |= 262144;
            }
            if (!this.surfaceInsets.equals(o.surfaceInsets)) {
                this.surfaceInsets.set(o.surfaceInsets);
                changes |= 1048576;
            }
            if (this.hasManualSurfaceInsets != o.hasManualSurfaceInsets) {
                this.hasManualSurfaceInsets = o.hasManualSurfaceInsets;
                changes |= 1048576;
            }
            if (this.receiveInsetsIgnoringZOrder != o.receiveInsetsIgnoringZOrder) {
                this.receiveInsetsIgnoringZOrder = o.receiveInsetsIgnoringZOrder;
                changes |= 1048576;
            }
            if (this.preservePreviousSurfaceInsets != o.preservePreviousSurfaceInsets) {
                this.preservePreviousSurfaceInsets = o.preservePreviousSurfaceInsets;
                changes |= 1048576;
            }
            if (this.accessibilityIdOfAnchor != o.accessibilityIdOfAnchor) {
                this.accessibilityIdOfAnchor = o.accessibilityIdOfAnchor;
                changes |= 16777216;
            }
            if (!Objects.equals(this.accessibilityTitle, o.accessibilityTitle) && o.accessibilityTitle != null) {
                this.accessibilityTitle = o.accessibilityTitle;
                changes |= 33554432;
            }
            if (this.mColorMode != o.mColorMode) {
                this.mColorMode = o.mColorMode;
                changes |= 67108864;
            }
            if (this.mDesiredHdrHeadroom != o.mDesiredHdrHeadroom) {
                this.mDesiredHdrHeadroom = o.mDesiredHdrHeadroom;
                changes |= 67108864;
            }
            if (this.preferMinimalPostProcessing != o.preferMinimalPostProcessing) {
                this.preferMinimalPostProcessing = o.preferMinimalPostProcessing;
                changes |= 268435456;
            }
            if (this.mBlurBehindRadius != o.mBlurBehindRadius) {
                this.mBlurBehindRadius = o.mBlurBehindRadius;
                changes |= 536870912;
            }
            this.hideTimeoutMilliseconds = o.hideTimeoutMilliseconds;
            if (this.insetsFlags.appearance != o.insetsFlags.appearance) {
                this.insetsFlags.appearance = o.insetsFlags.appearance;
                changes |= 134217728;
            }
            if (this.insetsFlags.behavior != o.insetsFlags.behavior) {
                this.insetsFlags.behavior = o.insetsFlags.behavior;
                changes |= 134217728;
            }
            if (this.mFitInsetsTypes != o.mFitInsetsTypes) {
                this.mFitInsetsTypes = o.mFitInsetsTypes;
                changes |= 1;
            }
            if (this.mFitInsetsSides != o.mFitInsetsSides) {
                this.mFitInsetsSides = o.mFitInsetsSides;
                changes |= 1;
            }
            if (this.mFitInsetsIgnoringVisibility != o.mFitInsetsIgnoringVisibility) {
                this.mFitInsetsIgnoringVisibility = o.mFitInsetsIgnoringVisibility;
                changes |= 1;
            }
            if (!Arrays.equals(this.providedInsets, o.providedInsets)) {
                this.providedInsets = o.providedInsets;
                changes |= 1;
            }
            if (this.forciblyShownTypes != o.forciblyShownTypes) {
                this.forciblyShownTypes = o.forciblyShownTypes;
                changes |= 131072;
            }
            if (this.paramsForRotation != o.paramsForRotation) {
                if ((changes & 1) == 0) {
                    if (this.paramsForRotation != null && o.paramsForRotation != null && this.paramsForRotation.length == o.paramsForRotation.length) {
                        int i = this.paramsForRotation.length - 1;
                        while (true) {
                            if (i < 0) {
                                break;
                            }
                            if (!hasLayoutDiff(this.paramsForRotation[i], o.paramsForRotation[i])) {
                                i--;
                            } else {
                                changes |= 1;
                                break;
                            }
                        }
                    } else {
                        changes |= 1;
                    }
                }
                this.paramsForRotation = o.paramsForRotation;
                checkNonRecursiveParams();
            }
            if (this.mWallpaperTouchEventsEnabled != o.mWallpaperTouchEventsEnabled) {
                this.mWallpaperTouchEventsEnabled = o.mWallpaperTouchEventsEnabled;
                changes |= 1;
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mFrameRateBoostOnTouch != o.mFrameRateBoostOnTouch) {
                this.mFrameRateBoostOnTouch = o.mFrameRateBoostOnTouch;
                changes |= 1;
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mIsFrameRatePowerSavingsBalanced != o.mIsFrameRatePowerSavingsBalanced) {
                this.mIsFrameRatePowerSavingsBalanced = o.mIsFrameRatePowerSavingsBalanced;
                changes |= 1;
            }
            if (this.dimDuration != o.dimDuration) {
                this.dimDuration = o.dimDuration;
                changes |= 32;
            }
            if (this.screenDimDuration != o.screenDimDuration) {
                this.screenDimDuration = o.screenDimDuration;
                changes |= 1;
            }
            this.navigationBarIconColor = o.navigationBarIconColor;
            if (this.samsungFlags != o.samsungFlags) {
                this.samsungFlags = o.samsungFlags;
                changes |= 4;
            }
            if (this.surfaceType != o.surfaceType) {
                this.surfaceType = o.surfaceType;
                changes |= 2;
            }
            if (this.multiWindowFlags != o.multiWindowFlags) {
                this.multiWindowFlags = o.multiWindowFlags;
                changes |= 4;
            }
            if (o.coverMode > 0) {
                this.coverMode = o.coverMode;
            }
            return changes;
        }

        private static boolean hasLayoutDiff(LayoutParams a, LayoutParams b) {
            return (a.width == b.width && a.height == b.height && a.x == b.x && a.y == b.y && a.horizontalMargin == b.horizontalMargin && a.verticalMargin == b.verticalMargin && a.layoutInDisplayCutoutMode == b.layoutInDisplayCutoutMode && a.gravity == b.gravity && Arrays.equals(a.providedInsets, b.providedInsets) && a.mFitInsetsTypes == b.mFitInsetsTypes && a.mFitInsetsSides == b.mFitInsetsSides && a.mFitInsetsIgnoringVisibility == b.mFitInsetsIgnoringVisibility) ? false : true;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public String debug(String output) {
            Log.d("Debug", output + "Contents of " + this + ":");
            String output2 = super.debug("");
            Log.d("Debug", output2);
            Log.d("Debug", "");
            Log.d("Debug", "WindowManager.LayoutParams={title=" + ((Object) this.mTitle) + "}");
            return "";
        }

        public String toString() {
            return toString("");
        }

        public void dumpDimensions(StringBuilder sb) {
            String valueOf;
            sb.append('(');
            sb.append(this.x);
            sb.append(',');
            sb.append(this.y);
            sb.append(")(");
            String str = "wrap";
            if (this.width == -1) {
                valueOf = "fill";
            } else {
                valueOf = this.width == -2 ? "wrap" : String.valueOf(this.width);
            }
            sb.append(valueOf);
            sb.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            if (this.height == -1) {
                str = "fill";
            } else if (this.height != -2) {
                str = String.valueOf(this.height);
            }
            sb.append(str);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }

        public String toString(String prefix) {
            StringBuilder sb = new StringBuilder(256);
            sb.append('{');
            dumpDimensions(sb);
            if (this.horizontalMargin != 0.0f) {
                sb.append(" hm=");
                sb.append(this.horizontalMargin);
            }
            if (this.verticalMargin != 0.0f) {
                sb.append(" vm=");
                sb.append(this.verticalMargin);
            }
            if (this.gravity != 0) {
                sb.append(" gr=");
                sb.append(Gravity.toString(this.gravity));
            }
            if (this.softInputMode != 0) {
                sb.append(" sim={");
                sb.append(softInputModeToString(this.softInputMode));
                sb.append('}');
            }
            if (this.layoutInDisplayCutoutMode != 0) {
                sb.append(" layoutInDisplayCutoutMode=");
                sb.append(layoutInDisplayCutoutModeToString(this.layoutInDisplayCutoutMode));
            }
            sb.append(" ty=");
            sb.append(ViewDebug.intToString(LayoutParams.class, "type", this.type));
            if (this.format != -1) {
                sb.append(" fmt=");
                sb.append(PixelFormat.formatToString(this.format));
            }
            if (this.windowAnimations != 0) {
                sb.append(" wanim=0x");
                sb.append(Integer.toHexString(this.windowAnimations));
            }
            if (this.screenOrientation != -1) {
                sb.append(" or=");
                sb.append(ActivityInfo.screenOrientationToString(this.screenOrientation));
            }
            if (this.alpha != 1.0f) {
                sb.append(" alpha=");
                sb.append(this.alpha);
            }
            if (this.screenBrightness != -1.0f) {
                sb.append(" sbrt=");
                sb.append(this.screenBrightness);
            }
            if (this.buttonBrightness != -1.0f) {
                sb.append(" bbrt=");
                sb.append(this.buttonBrightness);
            }
            if (this.rotationAnimation != 0) {
                sb.append(" rotAnim=");
                sb.append(rotationAnimationToString(this.rotationAnimation));
            }
            if (this.preferredRefreshRate != 0.0f) {
                sb.append(" preferredRefreshRate=");
                sb.append(this.preferredRefreshRate);
            }
            if (this.preferredDisplayModeId != 0) {
                sb.append(" preferredDisplayMode=");
                sb.append(this.preferredDisplayModeId);
            }
            if (this.preferredMinDisplayRefreshRate != 0.0f) {
                sb.append(" preferredMinDisplayRefreshRate=");
                sb.append(this.preferredMinDisplayRefreshRate);
            }
            if (this.preferredMaxDisplayRefreshRate != 0.0f) {
                sb.append(" preferredMaxDisplayRefreshRate=");
                sb.append(this.preferredMaxDisplayRefreshRate);
            }
            if (this.mDisplayFlags != 0) {
                sb.append(" displayFlags=0x");
                sb.append(Integer.toHexString(this.mDisplayFlags));
            }
            if (this.hasSystemUiListeners) {
                sb.append(" sysuil=");
                sb.append(this.hasSystemUiListeners);
            }
            if (this.inputFeatures != 0) {
                sb.append(" if=").append(inputFeaturesToString(this.inputFeatures));
            }
            if (this.userActivityTimeout >= 0) {
                sb.append(" userActivityTimeout=").append(this.userActivityTimeout);
            }
            if (this.surfaceInsets.left != 0 || this.surfaceInsets.top != 0 || this.surfaceInsets.right != 0 || this.surfaceInsets.bottom != 0 || this.hasManualSurfaceInsets || !this.preservePreviousSurfaceInsets) {
                sb.append(" surfaceInsets=").append(this.surfaceInsets);
                if (this.hasManualSurfaceInsets) {
                    sb.append(" (manual)");
                }
                if (!this.preservePreviousSurfaceInsets) {
                    sb.append(" (!preservePreviousSurfaceInsets)");
                }
            }
            if (this.receiveInsetsIgnoringZOrder) {
                sb.append(" receive insets ignoring z-order");
            }
            if (this.mColorMode != 0) {
                sb.append(" colorMode=").append(ActivityInfo.colorModeToString(this.mColorMode));
            }
            if (this.mDesiredHdrHeadroom != 0.0f) {
                sb.append(" desiredHdrHeadroom=").append(this.mDesiredHdrHeadroom);
            }
            if (this.preferMinimalPostProcessing) {
                sb.append(" preferMinimalPostProcessing=");
                sb.append(this.preferMinimalPostProcessing);
            }
            if (this.mBlurBehindRadius != 0) {
                sb.append(" blurBehindRadius=");
                sb.append(this.mBlurBehindRadius);
            }
            sb.append(System.lineSeparator());
            sb.append(prefix).append("  fl=").append(Integer.toHexString(this.flags));
            if (this.privateFlags != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  pfl=").append(Integer.toHexString(this.privateFlags));
            }
            if (this.systemUiVisibility != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  sysui=").append(Integer.toHexString(this.systemUiVisibility));
            }
            if (this.subtreeSystemUiVisibility != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  vsysui=").append(Integer.toHexString(this.subtreeSystemUiVisibility));
            }
            if (this.insetsFlags.appearance != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  apr=").append(Integer.toHexString(this.insetsFlags.appearance));
            }
            if (this.insetsFlags.behavior != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  bhv=").append(Integer.toHexString(this.insetsFlags.behavior));
            }
            if (this.mFitInsetsTypes != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  fitTypes=").append(Integer.toHexString(this.mFitInsetsTypes));
            }
            if (this.mFitInsetsSides != WindowInsets.Side.all()) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  fitSides=").append(Integer.toHexString(this.mFitInsetsSides));
            }
            if (this.mFitInsetsIgnoringVisibility) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  fitIgnoreVis");
            }
            if (this.providedInsets != null) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  providedInsets:");
                for (int i = 0; i < this.providedInsets.length; i++) {
                    sb.append(System.lineSeparator());
                    sb.append(prefix).append("    ").append(this.providedInsets[i]);
                }
            }
            int i2 = this.forciblyShownTypes;
            if (i2 != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  forciblyShownTypes=").append(WindowInsets.Type.toString(this.forciblyShownTypes));
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mFrameRateBoostOnTouch) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  frameRateBoostOnTouch=");
                sb.append(this.mFrameRateBoostOnTouch);
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mIsFrameRatePowerSavingsBalanced) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  dvrrWindowFrameRateHint=");
                sb.append(this.mIsFrameRatePowerSavingsBalanced);
            }
            if (this.paramsForRotation != null && this.paramsForRotation.length != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  paramsForRotation:");
                for (int i3 = 0; i3 < this.paramsForRotation.length; i3++) {
                    sb.append(System.lineSeparator()).append(prefix).append("    ");
                    sb.append(Surface.rotationToString(i3)).append("=");
                    sb.append(this.paramsForRotation[i3].toString(prefix + "    "));
                }
            }
            if (this.dimAmount < 1.0f) {
                sb.append(System.lineSeparator());
                sb.append(" dimAmount=").append(this.dimAmount);
            }
            if (this.dimDuration >= 0) {
                sb.append(" dimDuration=").append(this.dimDuration);
            }
            if (this.screenDimDuration >= 0) {
                sb.append(" screenDimDuration=").append(this.screenDimDuration);
            }
            if (this.navigationBarIconColor >= 0) {
                sb.append(" naviIconColor=").append(this.navigationBarIconColor);
            }
            if (this.samsungFlags != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  sfl=").append(Integer.toHexString(this.samsungFlags));
            }
            if (this.surfaceType != 0) {
                sb.append(System.lineSeparator());
                sb.append(prefix).append("  surfaceType=").append(ViewDebug.intToString(LayoutParams.class, "surfaceType", this.surfaceType));
            }
            if (this.multiWindowFlags != 0) {
                sb.append(" mwfl=").append(Integer.toHexString(this.multiWindowFlags));
            }
            if (this.coverMode != 0) {
                sb.append(" cm=").append(this.coverMode);
            }
            sb.append('}');
            return sb.toString();
        }

        public void dumpDebug(ProtoOutputStream proto, long fieldId) {
            long token = proto.start(fieldId);
            proto.write(1120986464257L, this.type);
            proto.write(1120986464258L, this.x);
            proto.write(1120986464259L, this.y);
            proto.write(1120986464260L, this.width);
            proto.write(1120986464261L, this.height);
            proto.write(1108101562374L, this.horizontalMargin);
            proto.write(1108101562375L, this.verticalMargin);
            proto.write(1120986464264L, this.gravity);
            proto.write(1120986464265L, this.softInputMode);
            proto.write(1159641169930L, this.format);
            proto.write(1120986464267L, this.windowAnimations);
            proto.write(1108101562380L, this.alpha);
            proto.write(1108101562381L, this.screenBrightness);
            proto.write(1108101562382L, this.buttonBrightness);
            proto.write(1159641169935L, this.rotationAnimation);
            proto.write(1108101562384L, this.preferredRefreshRate);
            proto.write(1120986464273L, this.preferredDisplayModeId);
            proto.write(1133871366162L, this.hasSystemUiListeners);
            proto.write(WindowLayoutParamsProto.INPUT_FEATURE_FLAGS, this.inputFeatures);
            proto.write(1112396529684L, this.userActivityTimeout);
            proto.write(1159641169943L, this.mColorMode);
            proto.write(WindowLayoutParamsProto.FLAGS, this.flags);
            proto.write(WindowLayoutParamsProto.PRIVATE_FLAGS, this.privateFlags);
            proto.write(WindowLayoutParamsProto.SYSTEM_UI_VISIBILITY_FLAGS, this.systemUiVisibility);
            proto.write(WindowLayoutParamsProto.SUBTREE_SYSTEM_UI_VISIBILITY_FLAGS, this.subtreeSystemUiVisibility);
            proto.write(WindowLayoutParamsProto.APPEARANCE, this.insetsFlags.appearance);
            proto.write(WindowLayoutParamsProto.BEHAVIOR, this.insetsFlags.behavior);
            proto.write(WindowLayoutParamsProto.FIT_INSETS_TYPES, this.mFitInsetsTypes);
            proto.write(WindowLayoutParamsProto.FIT_INSETS_SIDES, this.mFitInsetsSides);
            proto.write(WindowLayoutParamsProto.FIT_IGNORE_VISIBILITY, this.mFitInsetsIgnoringVisibility);
            proto.end(token);
        }

        public void scale(float scale) {
            this.x = (int) ((this.x * scale) + 0.5f);
            this.y = (int) ((this.y * scale) + 0.5f);
            if (this.width > 0) {
                this.width = (int) ((this.width * scale) + 0.5f);
            }
            if (this.height > 0) {
                this.height = (int) ((this.height * scale) + 0.5f);
            }
        }

        void backup() {
            int[] backup = this.mCompatibilityParamsBackup;
            if (backup == null) {
                int[] iArr = new int[4];
                this.mCompatibilityParamsBackup = iArr;
                backup = iArr;
            }
            backup[0] = this.x;
            backup[1] = this.y;
            backup[2] = this.width;
            backup[3] = this.height;
        }

        void restore() {
            int[] backup = this.mCompatibilityParamsBackup;
            if (backup != null) {
                this.x = backup[0];
                this.y = backup[1];
                this.width = backup[2];
                this.height = backup[3];
            }
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder encoder) {
            super.encodeProperties(encoder);
            encoder.addProperty("x", this.x);
            encoder.addProperty("y", this.y);
            encoder.addProperty("horizontalWeight", this.horizontalWeight);
            encoder.addProperty("verticalWeight", this.verticalWeight);
            encoder.addProperty("type", this.type);
            encoder.addProperty("flags", this.flags);
        }

        public boolean isFullscreen() {
            return this.x == 0 && this.y == 0 && this.width == -1 && this.height == -1;
        }

        public static String layoutInDisplayCutoutModeToString(int mode) {
            switch (mode) {
                case 0:
                    return "default";
                case 1:
                    return "shortEdges";
                case 2:
                    return "never";
                case 3:
                    return "always";
                default:
                    return "unknown(" + mode + NavigationBarInflaterView.KEY_CODE_END;
            }
        }

        private static String softInputModeToString(int softInputMode) {
            StringBuilder result = new StringBuilder();
            int state = softInputMode & 15;
            if (state != 0) {
                result.append("state=");
                switch (state) {
                    case 1:
                        result.append("unchanged");
                        break;
                    case 2:
                        result.append("hidden");
                        break;
                    case 3:
                        result.append("always_hidden");
                        break;
                    case 4:
                        result.append(CalendarContract.CalendarColumns.VISIBLE);
                        break;
                    case 5:
                        result.append("always_visible");
                        break;
                    default:
                        result.append(state);
                        break;
                }
                result.append(' ');
            }
            int adjust = softInputMode & 240;
            if (adjust != 0) {
                result.append("adjust=");
                switch (adjust) {
                    case 16:
                        result.append("resize");
                        break;
                    case 32:
                        result.append(TextToSpeech.Engine.KEY_PARAM_PAN);
                        break;
                    case 48:
                        result.append("nothing");
                        break;
                    default:
                        result.append(adjust);
                        break;
                }
                result.append(' ');
            }
            if ((softInputMode & 256) != 0) {
                result.append("forwardNavigation").append(' ');
            }
            result.deleteCharAt(result.length() - 1);
            return result.toString();
        }

        private static String rotationAnimationToString(int rotationAnimation) {
            switch (rotationAnimation) {
                case -1:
                    return "UNSPECIFIED";
                case 0:
                    return "ROTATE";
                case 1:
                    return "CROSSFADE";
                case 2:
                    return "JUMPCUT";
                case 3:
                    return "SEAMLESS";
                default:
                    return Integer.toString(rotationAnimation);
            }
        }

        private static String inputFeaturesToString(int inputFeatures) {
            List<String> features = new ArrayList<>();
            if ((inputFeatures & 1) != 0) {
                inputFeatures &= -2;
                features.add("INPUT_FEATURE_NO_INPUT_CHANNEL");
            }
            if ((inputFeatures & 2) != 0) {
                inputFeatures &= -3;
                features.add("INPUT_FEATURE_DISABLE_USER_ACTIVITY");
            }
            if ((inputFeatures & 4) != 0) {
                inputFeatures &= -5;
                features.add("INPUT_FEATURE_SPY");
            }
            if (inputFeatures != 0) {
                features.add(Integer.toHexString(inputFeatures));
            }
            return String.join(" | ", features);
        }

        public boolean isModal() {
            return (this.flags & 40) == 0;
        }
    }

    default void holdLock(IBinder token, int durationMs) {
        throw new UnsupportedOperationException();
    }

    default boolean isTaskSnapshotSupported() {
        return false;
    }

    @SystemApi
    default void registerTaskFpsCallback(int taskId, Executor executor, TaskFpsCallback callback) {
    }

    @SystemApi
    default void unregisterTaskFpsCallback(TaskFpsCallback callback) {
    }

    default Bitmap snapshotTaskForRecents(int taskId) {
        return null;
    }

    @SystemApi
    default List<ComponentName> notifyScreenshotListeners(int displayId) {
        throw new UnsupportedOperationException();
    }

    default boolean replaceContentOnDisplayWithMirror(int displayId, Window window) {
        throw new UnsupportedOperationException();
    }

    default boolean replaceContentOnDisplayWithSc(int displayId, SurfaceControl sc) {
        throw new UnsupportedOperationException();
    }

    default void registerTrustedPresentationListener(IBinder window, TrustedPresentationThresholds thresholds, Executor executor, Consumer<Boolean> listener) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrustedPresentationListener(Consumer<Boolean> listener) {
        throw new UnsupportedOperationException();
    }

    default InputTransferToken registerBatchedSurfaceControlInputReceiver(InputTransferToken hostInputTransferToken, SurfaceControl surfaceControl, Choreographer choreographer, SurfaceControlInputReceiver receiver) {
        throw new UnsupportedOperationException("registerBatchedSurfaceControlInputReceiver is not implemented");
    }

    default InputTransferToken registerUnbatchedSurfaceControlInputReceiver(InputTransferToken hostInputTransferToken, SurfaceControl surfaceControl, Looper looper, SurfaceControlInputReceiver receiver) {
        throw new UnsupportedOperationException("registerUnbatchedSurfaceControlInputReceiver is not implemented");
    }

    default void unregisterSurfaceControlInputReceiver(SurfaceControl surfaceControl) {
        throw new UnsupportedOperationException("unregisterSurfaceControlInputReceiver is not implemented");
    }

    default IBinder getSurfaceControlInputClientToken(SurfaceControl surfaceControl) {
        throw new UnsupportedOperationException("getSurfaceControlInputClientToken is not implemented");
    }

    default boolean transferTouchGesture(InputTransferToken transferFromToken, InputTransferToken transferToToken) {
        throw new UnsupportedOperationException("transferTouchGesture is not implemented");
    }

    default IBinder getDefaultToken() {
        throw new UnsupportedOperationException("getDefaultToken is not implemented");
    }

    default int addScreenRecordingCallback(Executor executor, Consumer<Integer> callback) {
        throw new UnsupportedOperationException();
    }

    default void removeScreenRecordingCallback(Consumer<Integer> callback) {
        throw new UnsupportedOperationException();
    }
}
