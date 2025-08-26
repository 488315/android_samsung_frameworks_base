package android.view;

import android.annotation.SystemApi;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.Application;
import android.app.admin.DevicePolicyResources;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Insets;
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
import com.samsung.android.rune.CoreRune;
import java.io.IOException;
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

/* loaded from: classes4.dex */
public interface WindowManager extends ViewManager {
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
    public static final String INPUT_CONSUMER_NAVIGATION = "nav_input_consumer";
    public static final String INPUT_CONSUMER_PIP = "pip_input_consumer";
    public static final String INPUT_CONSUMER_RECENTS_ANIMATION = "recents_animation_input_consumer";
    public static final String INPUT_CONSUMER_WALLPAPER = "wallpaper_input_consumer";
    public static final int KEYGUARD_VISIBILITY_TRANSIT_FLAGS = 47360;
    public static final int LARGE_SCREEN_SMALLEST_SCREEN_WIDTH_DP = 600;
    public static final String PARCEL_KEY_SHORTCUTS_ARRAY = "shortcuts_array";
    public static final String PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE = "android.window.PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE";
    public static final String PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED = "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED";
    public static final String PROPERTY_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING_STATE_SHARING = "android.window.PROPERTY_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING_STATE_SHARING";
    public static final String PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION = "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION";
    public static final String PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH = "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH";
    public static final String PROPERTY_CAMERA_COMPAT_ALLOW_SIMULATE_REQUESTED_ORIENTATION = "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_SIMULATE_REQUESTED_ORIENTATION";
    public static final String PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE = "android.window.PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE";
    public static final String PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE";
    public static final String PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED = "android.window.PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED";
    public static final String PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE";
    public static final String PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE";
    public static final String PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES = "android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES";
    public static final String PROPERTY_COMPAT_ALLOW_RESTRICTED_RESIZABILITY = "android.window.PROPERTY_COMPAT_ALLOW_RESTRICTED_RESIZABILITY";
    public static final String PROPERTY_COMPAT_ALLOW_SAFE_REGION_LETTERBOXING = "android.window.PROPERTY_COMPAT_ALLOW_SAFE_REGION_LETTERBOXING";
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
    public static final int TRANSIT_CLOSE_PREPARE_BACK_NAVIGATION = 14;
    public static final int TRANSIT_FIRST_CUSTOM = 1000;
    public static final int TRANSIT_FLAG_AOD_APPEARING = 32768;
    public static final int TRANSIT_FLAG_APP_CRASHED = 16;
    public static final int TRANSIT_FLAG_AVOID_MOVE_TO_FRONT = 65536;
    public static final int TRANSIT_FLAG_DESKTOP_FULLSCREEN_LAUNCH = 2097152;
    public static final int TRANSIT_FLAG_FREEFORM_LAUNCH_OPT = 1048576;
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
    public static final int TRANSIT_FLAG_PREVENT_HOME_IN_SPLIT = 262144;

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
    public static final int TRANSIT_PREPARE_BACK_NAVIGATION = 13;
    public static final int TRANSIT_RELAUNCH = 5;
    public static final int TRANSIT_SLEEP = 12;
    public static final int TRANSIT_TO_BACK = 4;
    public static final int TRANSIT_TO_FRONT = 3;
    public static final int TRANSIT_WAKE = 11;
    public static final boolean ACTIVITY_EMBEDDING_GUARD_WITH_ANDROID_15 = SystemProperties.getBoolean("persist.wm.extensions.activity_embedding_guard_with_android_15", !CoreRune.MW_EMBED_ACTIVITY_DEVICE_ENABLED);
    public static final boolean HAS_WINDOW_EXTENSIONS_ON_DEVICE = SystemProperties.getBoolean("persist.wm.extensions.enabled", false);

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

    default void addCrossWindowBlurEnabledListener(Executor executor, Consumer<Boolean> consumer) {
    }

    default void addCrossWindowBlurEnabledListener(Consumer<Boolean> consumer) {
    }

    default void addProposedRotationListener(Executor executor, IntConsumer intConsumer) {
    }

    KeyboardShortcutGroup getApplicationLaunchKeyboardShortcuts(int i);

    @SystemApi
    Region getCurrentImeTouchRegion();

    @Deprecated
    Display getDefaultDisplay();

    default int getDisplayImePolicy(int i) {
        return 1;
    }

    default boolean isCrossWindowBlurEnabled() {
        return false;
    }

    default boolean isEligibleForDesktopMode(int i) {
        return false;
    }

    default boolean isGlobalKey(int i) {
        return false;
    }

    default boolean isTaskSnapshotSupported() {
        return false;
    }

    @SystemApi
    default void registerTaskFpsCallback(int i, Executor executor, TaskFpsCallback taskFpsCallback) {
    }

    default void removeCrossWindowBlurEnabledListener(Consumer<Boolean> consumer) {
    }

    default void removeProposedRotationListener(IntConsumer intConsumer) {
    }

    void removeViewImmediate(View view);

    void requestAppKeyboardShortcuts(KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i);

    default void requestImeKeyboardShortcuts(KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i) {
    }

    default void setDisplayImePolicy(int i, int i2) {
    }

    default void setShouldShowSystemDecors(int i, boolean z) {
    }

    default void setShouldShowWithInsecureKeyguard(int i, boolean z) {
    }

    default boolean shouldShowSystemDecors(int i) {
        return false;
    }

    default Bitmap snapshotTaskForRecents(int i) {
        return null;
    }

    @SystemApi
    default void unregisterTaskFpsCallback(TaskFpsCallback taskFpsCallback) {
    }

    public static class BadTokenException extends RuntimeException {
        public BadTokenException() {
        }

        public BadTokenException(String str) {
            super(str);
        }
    }

    public static class InvalidDisplayException extends RuntimeException {
        public InvalidDisplayException() {
        }

        public InvalidDisplayException(String str) {
            super(str);
        }
    }

    default WindowMetrics getCurrentWindowMetrics() {
        throw new UnsupportedOperationException();
    }

    default WindowMetrics getMaximumWindowMetrics() {
        throw new UnsupportedOperationException();
    }

    default Set<WindowMetrics> getPossibleMaximumWindowMetrics(int i) {
        throw new UnsupportedOperationException();
    }

    static boolean hasWindowExtensionsEnabled() {
        if (!HAS_WINDOW_EXTENSIONS_ON_DEVICE) {
            return false;
        }
        try {
            Application applicationCurrentApplication = ActivityThread.currentApplication();
            if (applicationCurrentApplication.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
                return false;
            }
            if (CoreRune.MW_EMBED_ACTIVITY) {
                return ActivityTaskManager.deviceSupportsMultiWindow(applicationCurrentApplication);
            }
            return ActivityTaskManager.supportsMultiWindow(applicationCurrentApplication);
        } catch (Exception e) {
            Log.e("WindowManager", "Unable to read if the device supports multi window", e);
            return false;
        }
    }

    static String transitTypeToString(int i) {
        if (i != 1000) {
            switch (i) {
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
                case 13:
                    return "PREDICTIVE_BACK";
                case 14:
                    return "CLOSE_PREDICTIVE_BACK";
                default:
                    if (i > 1000) {
                        return "FIRST_CUSTOM+" + (i - 1000);
                    }
                    return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
            }
        }
        return "FIRST_CUSTOM";
    }

    static float fixScale(float f) {
        return Math.max(Math.min(f, 20.0f), 0.0f);
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
        public static final int PRIVATE_FLAG_ALLOW_ACTION_KEY_EVENTS = 8388608;
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
        public static final int PRIVATE_FLAG_OPT_OUT_EDGE_TO_EDGE = 67108864;
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
        public static final int TYPE_MULTIWINDOW_DISMISS_VIEW = 2607;
        public static final int TYPE_MULTIWINDOW_DRAG_HINT_TO_FULLSCREEN = 2608;
        public static final int TYPE_MULTIWINDOW_FLEX_FLOATING_ICON = 2605;
        public static final int TYPE_MULTIWINDOW_FLEX_FLOATING_ICON_MOVABLE = 2606;
        public static final int TYPE_MULTIWINDOW_MINIMIZE_CONTAINER = 2604;
        public static final int TYPE_MULTIWINDOW_SHELL_OVERLAY = 2603;
        public static final int TYPE_MULTI_FOLD_GUIDE_OVERLAY = 2633;
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

        @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 2, mask = 2, name = "FORCE_HARDWARE_ACCELERATED"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "WANTS_OFFSET_NOTIFICATIONS"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "SHOW_FOR_ALL_USERS"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "UNRESTRICTED_GESTURE_EXCLUSION"), @ViewDebug.FlagToString(equals = 64, mask = 64, name = "NO_MOVE_ANIMATION"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "SYSTEM_ERROR"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "OPTIMIZE_MEASURE"), @ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "DISABLE_WALLPAPER_TOUCH_EVENTS"), @ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "EDGE_TO_EDGE_ENFORCED"), @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "LAYOUT_SIZE_EXTENDED_BY_CUTOUT"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "FORCE_DECOR_VIEW_VISIBILITY"), @ViewDebug.FlagToString(equals = 16384, mask = 16384, name = "LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME"), @ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "FORCE_DRAW_STATUS_BAR_BACKGROUND"), @ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "SUSTAINED_PERFORMANCE_MODE"), @ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "IMMERSIVE_CONFIRMATION_WINDOW"), @ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "OVERRIDE_LAYOUT_IN_DISPLAY_CUTOUT_MODE"), @ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "HIDE_NON_SYSTEM_OVERLAY_WINDOWS"), @ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "IS_ROUNDED_CORNERS_OVERLAY"), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "EXCLUDE_FROM_SCREEN_MAGNIFICATION"), @ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "NOT_MAGNIFIABLE"), @ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "COLOR_SPACE_AGNOSTIC"), @ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "CONSUME_IME_INSETS"), @ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "OPTOUT_EDGE_TO_EDGE"), @ViewDebug.FlagToString(equals = 268435456, mask = 268435456, name = "FIT_INSETS_CONTROLLED"), @ViewDebug.FlagToString(equals = 536870912, mask = 536870912, name = "TRUSTED_OVERLAY"), @ViewDebug.FlagToString(equals = 1073741824, mask = 1073741824, name = "INSET_PARENT_FRAME_BY_IME"), @ViewDebug.FlagToString(equals = Integer.MIN_VALUE, mask = Integer.MIN_VALUE, name = "INTERCEPT_GLOBAL_DRAG_AND_DROP"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "SYSTEM_APPLICATION_OVERLAY")})
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
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LayoutParams createFromParcel(Parcel parcel) {
                return new LayoutParams(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LayoutParams[] newArray(int i) {
                return new LayoutParams[i];
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

        public static boolean isSystemAlertWindowType(int i) {
            return i == 2002 || i == 2003 || i == 2006 || i == 2007 || i == 2010 || i == 2038;
        }

        public static boolean mayUseInputMethod(int i) {
            return ((i & 8) == 8 || (i & 131072) == 131072) ? false : true;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @SystemApi
        public void setInsetsParams(List<InsetsParams> list) {
            if (list.isEmpty()) {
                this.providedInsets = null;
                return;
            }
            this.providedInsets = new InsetsFrameProvider[list.size()];
            for (int i = 0; i < list.size(); i++) {
                InsetsParams insetsParams = list.get(i);
                this.providedInsets[i] = new InsetsFrameProvider(this, i, insetsParams.getType()).setInsetsSize(insetsParams.getInsetsSize());
            }
        }

        public void setFitInsetsTypes(int i) {
            this.mFitInsetsTypes = i;
            this.privateFlags |= 268435456;
        }

        public void setFitInsetsSides(int i) {
            this.mFitInsetsSides = i;
            this.privateFlags |= 268435456;
        }

        public void setFitInsetsIgnoringVisibility(boolean z) {
            this.mFitInsetsIgnoringVisibility = z;
            this.privateFlags |= 268435456;
        }

        public void setTrustedOverlay() {
            this.privateFlags |= 536870912;
        }

        @SystemApi
        public void setSystemApplicationOverlay(boolean z) {
            if (z) {
                this.privateFlags |= 8;
            } else {
                this.privateFlags &= -9;
            }
        }

        @SystemApi
        public boolean isSystemApplicationOverlay() {
            return (this.privateFlags & 8) == 8;
        }

        public void setWallpaperTouchEventsEnabled(boolean z) {
            this.mWallpaperTouchEventsEnabled = z;
        }

        public boolean areWallpaperTouchEventsEnabled() {
            return this.mWallpaperTouchEventsEnabled;
        }

        public void setCanPlayMoveAnimation(boolean z) {
            if (z) {
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
            LayoutParams[] layoutParamsArr = this.paramsForRotation;
            if (layoutParamsArr == null) {
                return;
            }
            for (int length = layoutParamsArr.length - 1; length >= 0; length--) {
                if (this.paramsForRotation[length].paramsForRotation != null) {
                    throw new IllegalArgumentException("Params cannot contain params recursively.");
                }
            }
        }

        public LayoutParams forRotation(int i) {
            LayoutParams layoutParams;
            LayoutParams[] layoutParamsArr = this.paramsForRotation;
            return (layoutParamsArr == null || layoutParamsArr.length <= i || (layoutParams = layoutParamsArr[i]) == null) ? this : layoutParams;
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

        public LayoutParams(int i) {
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
            this.type = i;
            this.format = -1;
        }

        public LayoutParams(int i, int i2) {
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
            this.type = i;
            this.flags = i2;
            this.format = -1;
        }

        public LayoutParams(int i, int i2, int i3) {
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
            this.type = i;
            this.flags = i2;
            this.format = i3;
        }

        public LayoutParams(int i, int i2, int i3, int i4, int i5) {
            super(i, i2);
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
            this.type = i3;
            this.flags = i4;
            this.format = i5;
        }

        public LayoutParams(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            super(i, i2);
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
            this.x = i3;
            this.y = i4;
            this.type = i5;
            this.flags = i6;
            this.format = i7;
        }

        public final void setTitle(CharSequence charSequence) {
            if (charSequence == null) {
                charSequence = "";
            }
            this.mTitle = TextUtils.stringOrSpannedString(charSequence);
        }

        public final CharSequence getTitle() {
            CharSequence charSequence = this.mTitle;
            return charSequence != null ? charSequence : "";
        }

        public final void setSurfaceInsets(View view, boolean z, boolean z2) {
            int iCeil = (int) Math.ceil(view.getZ() * (view.getResources().getConfiguration().windowConfiguration.isPopOver() ? 3.0f : 2.0f));
            if (iCeil == 0) {
                this.surfaceInsets.set(0, 0, 0, 0);
            } else {
                Rect rect = this.surfaceInsets;
                rect.set(Math.max(iCeil, rect.left), Math.max(iCeil, this.surfaceInsets.top), Math.max(iCeil, this.surfaceInsets.right), Math.max(iCeil, this.surfaceInsets.bottom));
            }
            this.hasManualSurfaceInsets = z;
            this.preservePreviousSurfaceInsets = z2;
        }

        public boolean isHdrConversionEnabled() {
            return (this.mDisplayFlags & 1) == 0;
        }

        public void setHdrConversionEnabled(boolean z) {
            if (!z) {
                this.mDisplayFlags |= 1;
            } else {
                this.mDisplayFlags &= -2;
            }
        }

        public void setColorMode(int i) {
            this.mColorMode = i;
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public void setDesiredHdrHeadroom(float f) {
            if (!Float.isFinite(f)) {
                throw new IllegalArgumentException("desiredHeadroom must be finite: " + f);
            }
            if (f != 0.0f && (f < 1.0f || f > 10000.0f)) {
                throw new IllegalArgumentException("desiredHeadroom must be 0.0 or in the range [1.0, 10000.0f], received: " + f);
            }
            this.mDesiredHdrHeadroom = f;
        }

        public float getDesiredHdrHeadroom() {
            return this.mDesiredHdrHeadroom;
        }

        public void setFrameRateBoostOnTouchEnabled(boolean z) {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mFrameRateBoostOnTouch = z;
            }
        }

        public boolean getFrameRateBoostOnTouchEnabled() {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                return this.mFrameRateBoostOnTouch;
            }
            return true;
        }

        public void setFrameRatePowerSavingsBalanced(boolean z) {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mIsFrameRatePowerSavingsBalanced = z;
            }
        }

        public boolean isFrameRatePowerSavingsBalanced() {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                return this.mIsFrameRatePowerSavingsBalanced;
            }
            return true;
        }

        public void setBlurBehindRadius(int i) {
            this.mBlurBehindRadius = i;
        }

        public int getBlurBehindRadius() {
            return this.mBlurBehindRadius;
        }

        public final void semAddPrivateFlags(int i) {
            this.privateFlags = i | this.privateFlags;
        }

        public final void semClearPrivateFlags(int i) {
            this.privateFlags = (~i) & this.privateFlags;
        }

        public final void semAddExtensionFlags(int i) {
            this.samsungFlags = i | this.samsungFlags;
        }

        public final void semClearExtensionFlags(int i) {
            this.samsungFlags = (~i) & this.samsungFlags;
        }

        public final void semSetScreenTimeout(long j) {
            this.userActivityTimeout = j;
        }

        public final void semSetEnterDimDuration(long j) {
            this.dimDuration = j;
        }

        public final void semSetScreenDimDuration(long j) {
            this.screenDimDuration = j;
        }

        public final void semSetNavigationBarIconColor(int i) {
            this.navigationBarIconColor = i;
        }

        public final void semSetReceiveInsetsIgnoringZOrder(boolean z) {
            this.receiveInsetsIgnoringZOrder = z;
        }

        @SystemApi
        public final void setUserActivityTimeout(long j) {
            this.userActivityTimeout = j;
        }

        @SystemApi
        public final long getUserActivityTimeout() {
            return this.userActivityTimeout;
        }

        public final void setWindowContextToken(IBinder iBinder) {
            this.mWindowContextToken = iBinder;
        }

        public final IBinder getWindowContextToken() {
            return this.mWindowContextToken;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.width);
            parcel.writeInt(this.height);
            parcel.writeInt(this.x);
            parcel.writeInt(this.y);
            parcel.writeInt(this.type);
            parcel.writeInt(this.flags);
            parcel.writeInt(this.privateFlags);
            parcel.writeInt(this.softInputMode);
            parcel.writeInt(this.layoutInDisplayCutoutMode);
            parcel.writeInt(this.gravity);
            parcel.writeFloat(this.horizontalMargin);
            parcel.writeFloat(this.verticalMargin);
            parcel.writeInt(this.format);
            parcel.writeInt(this.windowAnimations);
            parcel.writeFloat(this.alpha);
            parcel.writeFloat(this.dimAmount);
            parcel.writeFloat(this.screenBrightness);
            parcel.writeFloat(this.buttonBrightness);
            parcel.writeInt(this.rotationAnimation);
            parcel.writeStrongBinder(this.token);
            parcel.writeStrongBinder(this.mWindowContextToken);
            parcel.writeString(this.packageName);
            TextUtils.writeToParcel(this.mTitle, parcel, i);
            parcel.writeInt(this.screenOrientation);
            parcel.writeFloat(this.preferredRefreshRate);
            parcel.writeInt(this.preferredDisplayModeId);
            parcel.writeFloat(this.preferredMinDisplayRefreshRate);
            parcel.writeFloat(this.preferredMaxDisplayRefreshRate);
            parcel.writeInt(this.systemUiVisibility);
            parcel.writeInt(this.subtreeSystemUiVisibility);
            parcel.writeBoolean(this.hasSystemUiListeners);
            parcel.writeInt(this.inputFeatures);
            parcel.writeLong(this.userActivityTimeout);
            parcel.writeInt(this.surfaceInsets.left);
            parcel.writeInt(this.surfaceInsets.top);
            parcel.writeInt(this.surfaceInsets.right);
            parcel.writeInt(this.surfaceInsets.bottom);
            parcel.writeBoolean(this.hasManualSurfaceInsets);
            parcel.writeBoolean(this.receiveInsetsIgnoringZOrder);
            parcel.writeBoolean(this.preservePreviousSurfaceInsets);
            parcel.writeLong(this.accessibilityIdOfAnchor);
            TextUtils.writeToParcel(this.accessibilityTitle, parcel, i);
            parcel.writeInt(this.mColorMode);
            parcel.writeLong(this.hideTimeoutMilliseconds);
            parcel.writeInt(this.insetsFlags.appearance);
            parcel.writeInt(this.insetsFlags.behavior);
            parcel.writeInt(this.mFitInsetsTypes);
            parcel.writeInt(this.mFitInsetsSides);
            parcel.writeBoolean(this.mFitInsetsIgnoringVisibility);
            parcel.writeBoolean(this.preferMinimalPostProcessing);
            parcel.writeInt(this.mBlurBehindRadius);
            parcel.writeBoolean(this.mWallpaperTouchEventsEnabled);
            parcel.writeTypedArray(this.providedInsets, 0);
            parcel.writeInt(this.forciblyShownTypes);
            checkNonRecursiveParams();
            parcel.writeTypedArray(this.paramsForRotation, 0);
            parcel.writeInt(this.mDisplayFlags);
            parcel.writeFloat(this.mDesiredHdrHeadroom);
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                parcel.writeBoolean(this.mFrameRateBoostOnTouch);
                parcel.writeBoolean(this.mIsFrameRatePowerSavingsBalanced);
            }
            parcel.writeLong(this.dimDuration);
            parcel.writeLong(this.screenDimDuration);
            parcel.writeInt(this.navigationBarIconColor);
            parcel.writeInt(this.samsungFlags);
            parcel.writeInt(this.surfaceType);
            parcel.writeInt(this.multiWindowFlags);
            parcel.writeInt(this.coverMode);
        }

        public LayoutParams(Parcel parcel) {
            Rect rect = new Rect();
            this.surfaceInsets = rect;
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
            InsetsFlags insetsFlags = new InsetsFlags();
            this.insetsFlags = insetsFlags;
            this.mFitInsetsTypes = WindowInsets.Type.systemBars();
            this.mFitInsetsSides = WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.width = parcel.readInt();
            this.height = parcel.readInt();
            this.x = parcel.readInt();
            this.y = parcel.readInt();
            this.type = parcel.readInt();
            this.flags = parcel.readInt();
            this.privateFlags = parcel.readInt();
            this.softInputMode = parcel.readInt();
            this.layoutInDisplayCutoutMode = parcel.readInt();
            this.gravity = parcel.readInt();
            this.horizontalMargin = parcel.readFloat();
            this.verticalMargin = parcel.readFloat();
            this.format = parcel.readInt();
            this.windowAnimations = parcel.readInt();
            this.alpha = parcel.readFloat();
            this.dimAmount = parcel.readFloat();
            this.screenBrightness = parcel.readFloat();
            this.buttonBrightness = parcel.readFloat();
            this.rotationAnimation = parcel.readInt();
            this.token = parcel.readStrongBinder();
            this.mWindowContextToken = parcel.readStrongBinder();
            this.packageName = parcel.readString();
            this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.screenOrientation = parcel.readInt();
            this.preferredRefreshRate = parcel.readFloat();
            this.preferredDisplayModeId = parcel.readInt();
            this.preferredMinDisplayRefreshRate = parcel.readFloat();
            this.preferredMaxDisplayRefreshRate = parcel.readFloat();
            this.systemUiVisibility = parcel.readInt();
            this.subtreeSystemUiVisibility = parcel.readInt();
            this.hasSystemUiListeners = parcel.readBoolean();
            this.inputFeatures = parcel.readInt();
            this.userActivityTimeout = parcel.readLong();
            rect.left = parcel.readInt();
            rect.top = parcel.readInt();
            rect.right = parcel.readInt();
            rect.bottom = parcel.readInt();
            this.hasManualSurfaceInsets = parcel.readBoolean();
            this.receiveInsetsIgnoringZOrder = parcel.readBoolean();
            this.preservePreviousSurfaceInsets = parcel.readBoolean();
            this.accessibilityIdOfAnchor = parcel.readLong();
            this.accessibilityTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mColorMode = parcel.readInt();
            this.hideTimeoutMilliseconds = parcel.readLong();
            insetsFlags.appearance = parcel.readInt();
            insetsFlags.behavior = parcel.readInt();
            this.mFitInsetsTypes = parcel.readInt();
            this.mFitInsetsSides = parcel.readInt();
            this.mFitInsetsIgnoringVisibility = parcel.readBoolean();
            this.preferMinimalPostProcessing = parcel.readBoolean();
            this.mBlurBehindRadius = parcel.readInt();
            this.mWallpaperTouchEventsEnabled = parcel.readBoolean();
            this.providedInsets = (InsetsFrameProvider[]) parcel.createTypedArray(InsetsFrameProvider.CREATOR);
            this.forciblyShownTypes = parcel.readInt();
            this.paramsForRotation = (LayoutParams[]) parcel.createTypedArray(CREATOR);
            this.mDisplayFlags = parcel.readInt();
            this.mDesiredHdrHeadroom = parcel.readFloat();
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mFrameRateBoostOnTouch = parcel.readBoolean();
                this.mIsFrameRatePowerSavingsBalanced = parcel.readBoolean();
            }
            this.dimDuration = parcel.readLong();
            this.screenDimDuration = parcel.readLong();
            this.navigationBarIconColor = parcel.readInt();
            this.samsungFlags = parcel.readInt();
            this.surfaceType = parcel.readInt();
            this.multiWindowFlags = parcel.readInt();
            this.coverMode = parcel.readInt();
        }

        public final int copyFrom(LayoutParams layoutParams) {
            int i;
            CharSequence charSequence;
            CharSequence charSequence2;
            if (this.width != layoutParams.width) {
                this.width = layoutParams.width;
                i = 1;
            } else {
                i = 0;
            }
            if (this.height != layoutParams.height) {
                this.height = layoutParams.height;
                i = 1;
            }
            int i2 = this.x;
            int i3 = layoutParams.x;
            if (i2 != i3) {
                this.x = i3;
                i = 1;
            }
            int i4 = this.y;
            int i5 = layoutParams.y;
            if (i4 != i5) {
                this.y = i5;
                i = 1;
            }
            float f = this.horizontalWeight;
            float f2 = layoutParams.horizontalWeight;
            if (f != f2) {
                this.horizontalWeight = f2;
                i = 1;
            }
            float f3 = this.verticalWeight;
            float f4 = layoutParams.verticalWeight;
            if (f3 != f4) {
                this.verticalWeight = f4;
                i = 1;
            }
            float f5 = this.horizontalMargin;
            float f6 = layoutParams.horizontalMargin;
            if (f5 != f6) {
                this.horizontalMargin = f6;
                i = 1;
            }
            float f7 = this.verticalMargin;
            float f8 = layoutParams.verticalMargin;
            if (f7 != f8) {
                this.verticalMargin = f8;
                i = 1;
            }
            int i6 = this.type;
            int i7 = layoutParams.type;
            if (i6 != i7) {
                this.type = i7;
                i |= 2;
            }
            int i8 = this.flags;
            int i9 = layoutParams.flags;
            if (i8 != i9) {
                if (((i8 ^ i9) & 201326592) != 0) {
                    i |= 524288;
                }
                this.flags = i9;
                i |= 4;
            }
            int i10 = this.privateFlags;
            int i11 = layoutParams.privateFlags;
            if (i10 != i11) {
                this.privateFlags = i11;
                i |= 131072;
            }
            int i12 = this.softInputMode;
            int i13 = layoutParams.softInputMode;
            if (i12 != i13) {
                this.softInputMode = i13;
                i |= 512;
            }
            int i14 = this.layoutInDisplayCutoutMode;
            int i15 = layoutParams.layoutInDisplayCutoutMode;
            if (i14 != i15) {
                this.layoutInDisplayCutoutMode = i15;
                i |= 1;
            }
            int i16 = this.gravity;
            int i17 = layoutParams.gravity;
            if (i16 != i17) {
                this.gravity = i17;
                i |= 1;
            }
            int i18 = this.format;
            int i19 = layoutParams.format;
            if (i18 != i19) {
                this.format = i19;
                i |= 8;
            }
            int i20 = this.windowAnimations;
            int i21 = layoutParams.windowAnimations;
            if (i20 != i21) {
                this.windowAnimations = i21;
                i |= 16;
            }
            if (this.token == null) {
                this.token = layoutParams.token;
            }
            if (this.mWindowContextToken == null) {
                this.mWindowContextToken = layoutParams.mWindowContextToken;
            }
            if (this.packageName == null) {
                this.packageName = layoutParams.packageName;
            }
            if (!Objects.equals(this.mTitle, layoutParams.mTitle) && (charSequence2 = layoutParams.mTitle) != null) {
                this.mTitle = charSequence2;
                i |= 64;
            }
            float f9 = this.alpha;
            float f10 = layoutParams.alpha;
            if (f9 != f10) {
                this.alpha = f10;
                i |= 128;
            }
            float f11 = this.dimAmount;
            float f12 = layoutParams.dimAmount;
            if (f11 != f12) {
                this.dimAmount = f12;
                i |= 32;
            }
            float f13 = this.screenBrightness;
            float f14 = layoutParams.screenBrightness;
            if (f13 != f14) {
                this.screenBrightness = f14;
                i |= 2048;
            }
            float f15 = this.buttonBrightness;
            float f16 = layoutParams.buttonBrightness;
            if (f15 != f16) {
                this.buttonBrightness = f16;
                i |= 8192;
            }
            int i22 = this.rotationAnimation;
            int i23 = layoutParams.rotationAnimation;
            if (i22 != i23) {
                this.rotationAnimation = i23;
                i |= 4096;
            }
            int i24 = this.screenOrientation;
            int i25 = layoutParams.screenOrientation;
            if (i24 != i25) {
                this.screenOrientation = i25;
                i |= 1024;
            }
            float f17 = this.preferredRefreshRate;
            float f18 = layoutParams.preferredRefreshRate;
            if (f17 != f18) {
                this.preferredRefreshRate = f18;
                i |= 2097152;
            }
            int i26 = this.preferredDisplayModeId;
            int i27 = layoutParams.preferredDisplayModeId;
            if (i26 != i27) {
                this.preferredDisplayModeId = i27;
                i |= 8388608;
            }
            float f19 = this.preferredMinDisplayRefreshRate;
            float f20 = layoutParams.preferredMinDisplayRefreshRate;
            if (f19 != f20) {
                this.preferredMinDisplayRefreshRate = f20;
                i |= 1073741824;
            }
            float f21 = this.preferredMaxDisplayRefreshRate;
            float f22 = layoutParams.preferredMaxDisplayRefreshRate;
            if (f21 != f22) {
                this.preferredMaxDisplayRefreshRate = f22;
                i |= Integer.MIN_VALUE;
            }
            int i28 = this.mDisplayFlags;
            int i29 = layoutParams.mDisplayFlags;
            if (i28 != i29) {
                this.mDisplayFlags = i29;
                i |= 4194304;
            }
            int i30 = this.systemUiVisibility;
            int i31 = layoutParams.systemUiVisibility;
            if (i30 != i31 || this.subtreeSystemUiVisibility != layoutParams.subtreeSystemUiVisibility) {
                this.systemUiVisibility = i31;
                this.subtreeSystemUiVisibility = layoutParams.subtreeSystemUiVisibility;
                i |= 16384;
            }
            boolean z = this.hasSystemUiListeners;
            boolean z2 = layoutParams.hasSystemUiListeners;
            if (z != z2) {
                this.hasSystemUiListeners = z2;
                i |= 32768;
            }
            int i32 = this.inputFeatures;
            int i33 = layoutParams.inputFeatures;
            if (i32 != i33) {
                this.inputFeatures = i33;
                i |= 65536;
            }
            long j = this.userActivityTimeout;
            long j2 = layoutParams.userActivityTimeout;
            if (j != j2) {
                this.userActivityTimeout = j2;
                i |= 262144;
            }
            if (!this.surfaceInsets.equals(layoutParams.surfaceInsets)) {
                this.surfaceInsets.set(layoutParams.surfaceInsets);
                i |= 1048576;
            }
            boolean z3 = this.hasManualSurfaceInsets;
            boolean z4 = layoutParams.hasManualSurfaceInsets;
            if (z3 != z4) {
                this.hasManualSurfaceInsets = z4;
                i |= 1048576;
            }
            boolean z5 = this.receiveInsetsIgnoringZOrder;
            boolean z6 = layoutParams.receiveInsetsIgnoringZOrder;
            if (z5 != z6) {
                this.receiveInsetsIgnoringZOrder = z6;
                i |= 1048576;
            }
            boolean z7 = this.preservePreviousSurfaceInsets;
            boolean z8 = layoutParams.preservePreviousSurfaceInsets;
            if (z7 != z8) {
                this.preservePreviousSurfaceInsets = z8;
                i |= 1048576;
            }
            long j3 = this.accessibilityIdOfAnchor;
            long j4 = layoutParams.accessibilityIdOfAnchor;
            if (j3 != j4) {
                this.accessibilityIdOfAnchor = j4;
                i |= 16777216;
            }
            if (!Objects.equals(this.accessibilityTitle, layoutParams.accessibilityTitle) && (charSequence = layoutParams.accessibilityTitle) != null) {
                this.accessibilityTitle = charSequence;
                i |= 33554432;
            }
            int i34 = this.mColorMode;
            int i35 = layoutParams.mColorMode;
            if (i34 != i35) {
                this.mColorMode = i35;
                i |= 67108864;
            }
            float f23 = this.mDesiredHdrHeadroom;
            float f24 = layoutParams.mDesiredHdrHeadroom;
            if (f23 != f24) {
                this.mDesiredHdrHeadroom = f24;
                i |= 67108864;
            }
            boolean z9 = this.preferMinimalPostProcessing;
            boolean z10 = layoutParams.preferMinimalPostProcessing;
            if (z9 != z10) {
                this.preferMinimalPostProcessing = z10;
                i |= 268435456;
            }
            int i36 = this.mBlurBehindRadius;
            int i37 = layoutParams.mBlurBehindRadius;
            if (i36 != i37) {
                this.mBlurBehindRadius = i37;
                i |= 536870912;
            }
            this.hideTimeoutMilliseconds = layoutParams.hideTimeoutMilliseconds;
            if (this.insetsFlags.appearance != layoutParams.insetsFlags.appearance) {
                this.insetsFlags.appearance = layoutParams.insetsFlags.appearance;
                i |= 134217728;
            }
            if (this.insetsFlags.behavior != layoutParams.insetsFlags.behavior) {
                this.insetsFlags.behavior = layoutParams.insetsFlags.behavior;
                i |= 134217728;
            }
            int i38 = this.mFitInsetsTypes;
            int i39 = layoutParams.mFitInsetsTypes;
            if (i38 != i39) {
                this.mFitInsetsTypes = i39;
                i |= 1;
            }
            int i40 = this.mFitInsetsSides;
            int i41 = layoutParams.mFitInsetsSides;
            if (i40 != i41) {
                this.mFitInsetsSides = i41;
                i |= 1;
            }
            boolean z11 = this.mFitInsetsIgnoringVisibility;
            boolean z12 = layoutParams.mFitInsetsIgnoringVisibility;
            if (z11 != z12) {
                this.mFitInsetsIgnoringVisibility = z12;
                i |= 1;
            }
            if (!Arrays.equals(this.providedInsets, layoutParams.providedInsets)) {
                this.providedInsets = layoutParams.providedInsets;
                i |= 1;
            }
            int i42 = this.forciblyShownTypes;
            int i43 = layoutParams.forciblyShownTypes;
            if (i42 != i43) {
                this.forciblyShownTypes = i43;
                i |= 131072;
            }
            LayoutParams[] layoutParamsArr = this.paramsForRotation;
            LayoutParams[] layoutParamsArr2 = layoutParams.paramsForRotation;
            if (layoutParamsArr != layoutParamsArr2) {
                if ((i & 1) == 0) {
                    if (layoutParamsArr == null || layoutParamsArr2 == null || layoutParamsArr.length != layoutParamsArr2.length) {
                        i |= 1;
                        break;
                    }
                    for (int length = layoutParamsArr.length - 1; length >= 0; length--) {
                        if (hasLayoutDiff(this.paramsForRotation[length], layoutParams.paramsForRotation[length])) {
                            i |= 1;
                            break;
                        }
                    }
                }
                this.paramsForRotation = layoutParams.paramsForRotation;
                checkNonRecursiveParams();
            }
            boolean z13 = this.mWallpaperTouchEventsEnabled;
            boolean z14 = layoutParams.mWallpaperTouchEventsEnabled;
            if (z13 != z14) {
                this.mWallpaperTouchEventsEnabled = z14;
                i |= 1;
            }
            boolean z15 = sToolkitSetFrameRateReadOnlyFlagValue;
            if (z15) {
                boolean z16 = this.mFrameRateBoostOnTouch;
                boolean z17 = layoutParams.mFrameRateBoostOnTouch;
                if (z16 != z17) {
                    this.mFrameRateBoostOnTouch = z17;
                    i |= 1;
                }
            }
            if (z15) {
                boolean z18 = this.mIsFrameRatePowerSavingsBalanced;
                boolean z19 = layoutParams.mIsFrameRatePowerSavingsBalanced;
                if (z18 != z19) {
                    this.mIsFrameRatePowerSavingsBalanced = z19;
                    i |= 1;
                }
            }
            long j5 = this.dimDuration;
            long j6 = layoutParams.dimDuration;
            if (j5 != j6) {
                this.dimDuration = j6;
                i |= 32;
            }
            long j7 = this.screenDimDuration;
            long j8 = layoutParams.screenDimDuration;
            if (j7 != j8) {
                this.screenDimDuration = j8;
                i |= 1;
            }
            this.navigationBarIconColor = layoutParams.navigationBarIconColor;
            int i44 = this.samsungFlags;
            int i45 = layoutParams.samsungFlags;
            if (i44 != i45) {
                this.samsungFlags = i45;
                i |= 4;
            }
            int i46 = this.surfaceType;
            int i47 = layoutParams.surfaceType;
            if (i46 != i47) {
                this.surfaceType = i47;
                i |= 2;
            }
            int i48 = this.multiWindowFlags;
            int i49 = layoutParams.multiWindowFlags;
            if (i48 != i49) {
                this.multiWindowFlags = i49;
                i |= 4;
            }
            int i50 = layoutParams.coverMode;
            if (i50 > 0) {
                this.coverMode = i50;
            }
            return i;
        }

        public static boolean hasLayoutDiff(LayoutParams layoutParams, LayoutParams layoutParams2) {
            return (layoutParams.width == layoutParams2.width && layoutParams.height == layoutParams2.height && layoutParams.x == layoutParams2.x && layoutParams.y == layoutParams2.y && layoutParams.horizontalMargin == layoutParams2.horizontalMargin && layoutParams.verticalMargin == layoutParams2.verticalMargin && layoutParams.layoutInDisplayCutoutMode == layoutParams2.layoutInDisplayCutoutMode && layoutParams.gravity == layoutParams2.gravity && Arrays.equals(layoutParams.providedInsets, layoutParams2.providedInsets) && layoutParams.mFitInsetsTypes == layoutParams2.mFitInsetsTypes && layoutParams.mFitInsetsSides == layoutParams2.mFitInsetsSides && layoutParams.mFitInsetsIgnoringVisibility == layoutParams2.mFitInsetsIgnoringVisibility) ? false : true;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public String debug(String str) {
            Log.d("Debug", str + "Contents of " + this + ":");
            Log.d("Debug", super.debug(""));
            Log.d("Debug", "");
            Log.d("Debug", "WindowManager.LayoutParams={title=" + ((Object) this.mTitle) + "}");
            return "";
        }

        public String toString() {
            return toString("");
        }

        public void dumpDimensions(StringBuilder sb) {
            String strValueOf;
            sb.append('(');
            sb.append(this.x);
            sb.append(',');
            sb.append(this.y);
            sb.append(")(");
            String strValueOf2 = "wrap";
            if (this.width == -1) {
                strValueOf = "fill";
            } else {
                strValueOf = this.width == -2 ? "wrap" : String.valueOf(this.width);
            }
            sb.append(strValueOf);
            sb.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            if (this.height == -1) {
                strValueOf2 = "fill";
            } else if (this.height != -2) {
                strValueOf2 = String.valueOf(this.height);
            }
            sb.append(strValueOf2);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }

        public String toString(String str) {
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
                sb.append(" if=");
                sb.append(inputFeaturesToString(this.inputFeatures));
            }
            if (this.userActivityTimeout >= 0) {
                sb.append(" userActivityTimeout=");
                sb.append(this.userActivityTimeout);
            }
            if (this.surfaceInsets.left != 0 || this.surfaceInsets.top != 0 || this.surfaceInsets.right != 0 || this.surfaceInsets.bottom != 0 || this.hasManualSurfaceInsets || !this.preservePreviousSurfaceInsets) {
                sb.append(" surfaceInsets=");
                sb.append(this.surfaceInsets);
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
                sb.append(" colorMode=");
                sb.append(ActivityInfo.colorModeToString(this.mColorMode));
            }
            if (this.mDesiredHdrHeadroom != 0.0f) {
                sb.append(" desiredHdrHeadroom=");
                sb.append(this.mDesiredHdrHeadroom);
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
            sb.append(str);
            sb.append("  fl=");
            sb.append(Integer.toHexString(this.flags));
            if (this.privateFlags != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  pfl=");
                sb.append(Integer.toHexString(this.privateFlags));
            }
            if (this.systemUiVisibility != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  sysui=");
                sb.append(Integer.toHexString(this.systemUiVisibility));
            }
            if (this.subtreeSystemUiVisibility != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  vsysui=");
                sb.append(Integer.toHexString(this.subtreeSystemUiVisibility));
            }
            if (this.insetsFlags.appearance != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  apr=");
                sb.append(Integer.toHexString(this.insetsFlags.appearance));
            }
            if (this.insetsFlags.behavior != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  bhv=");
                sb.append(Integer.toHexString(this.insetsFlags.behavior));
            }
            if (this.mFitInsetsTypes != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  fitTypes=");
                sb.append(Integer.toHexString(this.mFitInsetsTypes));
            }
            if (this.mFitInsetsSides != WindowInsets.Side.all()) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  fitSides=");
                sb.append(Integer.toHexString(this.mFitInsetsSides));
            }
            if (this.mFitInsetsIgnoringVisibility) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  fitIgnoreVis");
            }
            if (this.providedInsets != null) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  providedInsets:");
                for (int i = 0; i < this.providedInsets.length; i++) {
                    sb.append(System.lineSeparator());
                    sb.append(str);
                    sb.append("    ");
                    sb.append(this.providedInsets[i]);
                }
            }
            if (this.forciblyShownTypes != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  forciblyShownTypes=");
                sb.append(WindowInsets.Type.toString(this.forciblyShownTypes));
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mFrameRateBoostOnTouch) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  frameRateBoostOnTouch=");
                sb.append(this.mFrameRateBoostOnTouch);
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mIsFrameRatePowerSavingsBalanced) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  dvrrWindowFrameRateHint=");
                sb.append(this.mIsFrameRatePowerSavingsBalanced);
            }
            LayoutParams[] layoutParamsArr = this.paramsForRotation;
            if (layoutParamsArr != null && layoutParamsArr.length != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  paramsForRotation:");
                for (int i2 = 0; i2 < this.paramsForRotation.length; i2++) {
                    sb.append(System.lineSeparator());
                    sb.append(str);
                    sb.append("    ");
                    sb.append(Surface.rotationToString(i2));
                    sb.append("=");
                    sb.append(this.paramsForRotation[i2].toString(str + "    "));
                }
            }
            if (this.dimAmount < 1.0f) {
                sb.append(System.lineSeparator());
                sb.append(" dimAmount=");
                sb.append(this.dimAmount);
            }
            if (this.dimDuration >= 0) {
                sb.append(" dimDuration=");
                sb.append(this.dimDuration);
            }
            if (this.screenDimDuration >= 0) {
                sb.append(" screenDimDuration=");
                sb.append(this.screenDimDuration);
            }
            if (this.navigationBarIconColor >= 0) {
                sb.append(" naviIconColor=");
                sb.append(this.navigationBarIconColor);
            }
            if (this.samsungFlags != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  sfl=");
                sb.append(Integer.toHexString(this.samsungFlags));
            }
            if (this.surfaceType != 0) {
                sb.append(System.lineSeparator());
                sb.append(str);
                sb.append("  surfaceType=");
                sb.append(ViewDebug.intToString(LayoutParams.class, "surfaceType", this.surfaceType));
            }
            if (this.multiWindowFlags != 0) {
                sb.append(" mwfl=");
                sb.append(Integer.toHexString(this.multiWindowFlags));
            }
            if (this.coverMode != 0) {
                sb.append(" cm=");
                sb.append(this.coverMode);
            }
            sb.append('}');
            return sb.toString();
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long jStart = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.type);
            protoOutputStream.write(1120986464258L, this.x);
            protoOutputStream.write(1120986464259L, this.y);
            protoOutputStream.write(1120986464260L, this.width);
            protoOutputStream.write(1120986464261L, this.height);
            protoOutputStream.write(1108101562374L, this.horizontalMargin);
            protoOutputStream.write(1108101562375L, this.verticalMargin);
            protoOutputStream.write(1120986464264L, this.gravity);
            protoOutputStream.write(1120986464265L, this.softInputMode);
            protoOutputStream.write(1159641169930L, this.format);
            protoOutputStream.write(1120986464267L, this.windowAnimations);
            protoOutputStream.write(1108101562380L, this.alpha);
            protoOutputStream.write(1108101562381L, this.screenBrightness);
            protoOutputStream.write(1108101562382L, this.buttonBrightness);
            protoOutputStream.write(1159641169935L, this.rotationAnimation);
            protoOutputStream.write(1108101562384L, this.preferredRefreshRate);
            protoOutputStream.write(1120986464273L, this.preferredDisplayModeId);
            protoOutputStream.write(1133871366162L, this.hasSystemUiListeners);
            protoOutputStream.write(WindowLayoutParamsProto.INPUT_FEATURE_FLAGS, this.inputFeatures);
            protoOutputStream.write(1112396529684L, this.userActivityTimeout);
            protoOutputStream.write(1159641169943L, this.mColorMode);
            protoOutputStream.write(WindowLayoutParamsProto.FLAGS, this.flags);
            protoOutputStream.write(WindowLayoutParamsProto.PRIVATE_FLAGS, this.privateFlags);
            protoOutputStream.write(WindowLayoutParamsProto.SYSTEM_UI_VISIBILITY_FLAGS, this.systemUiVisibility);
            protoOutputStream.write(WindowLayoutParamsProto.SUBTREE_SYSTEM_UI_VISIBILITY_FLAGS, this.subtreeSystemUiVisibility);
            protoOutputStream.write(WindowLayoutParamsProto.APPEARANCE, this.insetsFlags.appearance);
            protoOutputStream.write(WindowLayoutParamsProto.BEHAVIOR, this.insetsFlags.behavior);
            protoOutputStream.write(WindowLayoutParamsProto.FIT_INSETS_TYPES, this.mFitInsetsTypes);
            protoOutputStream.write(WindowLayoutParamsProto.FIT_INSETS_SIDES, this.mFitInsetsSides);
            protoOutputStream.write(WindowLayoutParamsProto.FIT_IGNORE_VISIBILITY, this.mFitInsetsIgnoringVisibility);
            protoOutputStream.end(jStart);
        }

        public void scale(float f) {
            this.x = (int) ((this.x * f) + 0.5f);
            this.y = (int) ((this.y * f) + 0.5f);
            if (this.width > 0) {
                this.width = (int) ((this.width * f) + 0.5f);
            }
            if (this.height > 0) {
                this.height = (int) ((this.height * f) + 0.5f);
            }
        }

        void backup() {
            int[] iArr = this.mCompatibilityParamsBackup;
            if (iArr == null) {
                iArr = new int[4];
                this.mCompatibilityParamsBackup = iArr;
            }
            iArr[0] = this.x;
            iArr[1] = this.y;
            iArr[2] = this.width;
            iArr[3] = this.height;
        }

        void restore() {
            int[] iArr = this.mCompatibilityParamsBackup;
            if (iArr != null) {
                this.x = iArr[0];
                this.y = iArr[1];
                this.width = iArr[2];
                this.height = iArr[3];
            }
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("x", this.x);
            viewHierarchyEncoder.addProperty("y", this.y);
            viewHierarchyEncoder.addProperty("horizontalWeight", this.horizontalWeight);
            viewHierarchyEncoder.addProperty("verticalWeight", this.verticalWeight);
            viewHierarchyEncoder.addProperty("type", this.type);
            viewHierarchyEncoder.addProperty("flags", this.flags);
        }

        public boolean isFullscreen() {
            return this.x == 0 && this.y == 0 && this.width == -1 && this.height == -1;
        }

        public static String layoutInDisplayCutoutModeToString(int i) {
            if (i == 0) {
                return "default";
            }
            if (i == 1) {
                return "shortEdges";
            }
            if (i == 2) {
                return "never";
            }
            if (i == 3) {
                return "always";
            }
            return "unknown(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }

        private static String softInputModeToString(int i) {
            StringBuilder sb = new StringBuilder();
            int i2 = i & 15;
            if (i2 != 0) {
                sb.append("state=");
                if (i2 == 1) {
                    sb.append("unchanged");
                } else if (i2 == 2) {
                    sb.append("hidden");
                } else if (i2 == 3) {
                    sb.append("always_hidden");
                } else if (i2 == 4) {
                    sb.append(CalendarContract.CalendarColumns.VISIBLE);
                } else if (i2 == 5) {
                    sb.append("always_visible");
                } else {
                    sb.append(i2);
                }
                sb.append(' ');
            }
            int i3 = i & 240;
            if (i3 != 0) {
                sb.append("adjust=");
                if (i3 == 16) {
                    sb.append("resize");
                } else if (i3 == 32) {
                    sb.append(TextToSpeech.Engine.KEY_PARAM_PAN);
                } else if (i3 == 48) {
                    sb.append("nothing");
                } else {
                    sb.append(i3);
                }
                sb.append(' ');
            }
            if ((i & 256) != 0) {
                sb.append("forwardNavigation ");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        private static String rotationAnimationToString(int i) {
            if (i == -1) {
                return "UNSPECIFIED";
            }
            if (i == 0) {
                return "ROTATE";
            }
            if (i == 1) {
                return "CROSSFADE";
            }
            if (i == 2) {
                return "JUMPCUT";
            }
            if (i == 3) {
                return "SEAMLESS";
            }
            return Integer.toString(i);
        }

        private static String inputFeaturesToString(int i) {
            ArrayList arrayList = new ArrayList();
            if ((i & 1) != 0) {
                i &= -2;
                arrayList.add("INPUT_FEATURE_NO_INPUT_CHANNEL");
            }
            if ((i & 2) != 0) {
                i &= -3;
                arrayList.add("INPUT_FEATURE_DISABLE_USER_ACTIVITY");
            }
            if ((i & 4) != 0) {
                i &= -5;
                arrayList.add("INPUT_FEATURE_SPY");
            }
            if (i != 0) {
                arrayList.add(Integer.toHexString(i));
            }
            return String.join(" | ", arrayList);
        }

        public boolean isModal() {
            return (this.flags & 40) == 0;
        }
    }

    @SystemApi
    public static class InsetsParams {
        private Insets mInsets;
        private final int mType;

        public InsetsParams(int i) {
            this.mType = i;
        }

        public InsetsParams setInsetsSize(Insets insets) {
            this.mInsets = insets;
            return this;
        }

        public int getType() {
            return this.mType;
        }

        public Insets getInsetsSize() {
            return this.mInsets;
        }
    }

    default void holdLock(IBinder iBinder, int i) {
        throw new UnsupportedOperationException();
    }

    @SystemApi
    default List<ComponentName> notifyScreenshotListeners(int i) {
        throw new UnsupportedOperationException();
    }

    default boolean replaceContentOnDisplayWithMirror(int i, Window window) {
        throw new UnsupportedOperationException();
    }

    default boolean replaceContentOnDisplayWithSc(int i, SurfaceControl surfaceControl) {
        throw new UnsupportedOperationException();
    }

    default void registerTrustedPresentationListener(IBinder iBinder, TrustedPresentationThresholds trustedPresentationThresholds, Executor executor, Consumer<Boolean> consumer) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrustedPresentationListener(Consumer<Boolean> consumer) {
        throw new UnsupportedOperationException();
    }

    default InputTransferToken registerBatchedSurfaceControlInputReceiver(InputTransferToken inputTransferToken, SurfaceControl surfaceControl, Choreographer choreographer, SurfaceControlInputReceiver surfaceControlInputReceiver) {
        throw new UnsupportedOperationException("registerBatchedSurfaceControlInputReceiver is not implemented");
    }

    default InputTransferToken registerUnbatchedSurfaceControlInputReceiver(InputTransferToken inputTransferToken, SurfaceControl surfaceControl, Looper looper, SurfaceControlInputReceiver surfaceControlInputReceiver) {
        throw new UnsupportedOperationException("registerUnbatchedSurfaceControlInputReceiver is not implemented");
    }

    default void unregisterSurfaceControlInputReceiver(SurfaceControl surfaceControl) {
        throw new UnsupportedOperationException("unregisterSurfaceControlInputReceiver is not implemented");
    }

    default IBinder getSurfaceControlInputClientToken(SurfaceControl surfaceControl) {
        throw new UnsupportedOperationException("getSurfaceControlInputClientToken is not implemented");
    }

    default boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) {
        throw new UnsupportedOperationException("transferTouchGesture is not implemented");
    }

    default IBinder getDefaultToken() {
        throw new UnsupportedOperationException("getDefaultToken is not implemented");
    }

    default int addScreenRecordingCallback(Executor executor, Consumer<Integer> consumer) {
        throw new UnsupportedOperationException();
    }

    default void removeScreenRecordingCallback(Consumer<Integer> consumer) {
        throw new UnsupportedOperationException();
    }
}
