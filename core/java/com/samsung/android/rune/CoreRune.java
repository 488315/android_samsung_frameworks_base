package com.samsung.android.rune;

import android.media.audio.common.AudioDeviceDescription;
import android.os.Build;
import android.os.FactoryTest;
import android.os.Process;
import android.os.SystemProperties;
import android.util.ArraySet;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class CoreRune {
    public static final boolean ALLIED_PROC_PROTECTION_LMKD;
    public static final boolean AM = true;
    public static final boolean APPCOMPACTOR_SYSTEM_FULL_COMPACTION_DISABLE = true;
    public static final boolean APP_CREATED_SIGNAL_ENABLED = true;
    public static final boolean BAIDU_CARLIFE;
    public static final boolean BIXBY_TOUCH;
    public static final boolean CARLIFE_DISPLAY_GROUP;
    public static final boolean CARLIFE_NAVBAR;
    public static final boolean CFMS_MONITOR_ISOLATED_UID_ENABLE = true;
    public static final boolean CHIMERA_ENABLED = true;
    public static final boolean COOLDOWN_ENABLED = true;
    private static final String DEBUG_LEVEL_MID = "0x494d";
    public static final boolean DIRECT_WRITING;
    public static final boolean DRM_EVENT_SERVICE_BR_LAZY_BOOT_COMPLETE = true;
    public static final boolean ENABLE_EARLY_PHONE_START = true;
    public static final boolean FAST_MADVISE_ENABLED;
    public static final boolean FEATURE_SF_EFFECTS = true;
    public static final boolean FGS_FILTER_ENABLED = true;
    public static final boolean FW = true;
    public static final boolean FW_ACTIVITY_CONTROLLER_EXTENSION = true;
    public static final boolean FW_ACTIVITY_CONTROLLER_LOG = true;
    public static final boolean FW_ADAPTIVE_DISPLAY = true;
    public static final boolean FW_ADAPTIVE_DISPLAY_CUTOUT = true;
    public static final boolean FW_ADAPTIVE_DISPLAY_ROUNDED_CORNER = true;
    public static final boolean FW_ADD_PRODUCT_PROPERTY_IN_ANDROID_RUNTIME = true;
    public static final boolean FW_ADJUST_MISMATCH_CUTOUT_HEIGHT = true;
    public static final boolean FW_ALLOW_ALL_ROTATION;
    public static final boolean FW_ALLOW_SET_HAS_OVERLAY_UI = true;
    public static final boolean FW_ALLOW_TOUCH_TO_KEYGUARD_WALLPAPER;
    public static final boolean FW_AMS_DEBUG_LOG = true;
    public static final boolean FW_AMS_PROC_DEBUG_LOG = true;
    public static final boolean FW_ANR_FORCE_DUMP_MP = true;
    public static final boolean FW_AOD_FACE_WIDGET;
    public static final boolean FW_APPCATEGORY_WITH_SCPM = true;
    public static final boolean FW_APPCORE_LOGGING_TOOL = false;
    public static final boolean FW_APPLOCK;
    public static final boolean FW_APPLY_TINT_BUG_FIX = true;
    public static final boolean FW_AUDIO_SPEAKER_LR_SWITCHING = true;
    public static final boolean FW_AVOID_DISPLAYING_STATUS_BAR_PANEL_FOR_ALL_USERS = true;
    public static final boolean FW_BACKGROUND_DISPLAY = false;
    public static final boolean FW_BLACK_SNAPSHOT_TRANSITION;
    public static final boolean FW_BLOCK_RECENTS_TRIM_TASKS = true;
    public static final boolean FW_BLOCK_TOO_MANY_CONNECTION_REQUESTS = true;
    public static final boolean FW_BLOCK_UNINTENDED_PALM_MOTION = true;
    public static final boolean FW_BOOST_SHELL_TRANSITION = true;
    public static final boolean FW_BOOT_DELAY_REPORT = true;
    public static final boolean FW_BOOT_PROGRESS_DIALOG = true;
    public static final boolean FW_BOOT_PROGRESS_LOG = true;
    public static final boolean FW_BROADCAST_LOGS = true;
    public static final boolean FW_BROADCAST_LOGS_TO_DROPBOX = false;
    public static final boolean FW_BR_ALLOW_LIST = true;
    public static final boolean FW_BR_ALLOW_LIST_ENFORCE = true;
    public static final boolean FW_BR_ALLOW_LIST_WITH_SCPM = true;
    public static final boolean FW_BR_LAZY_BOOT_COMPLETE = true;
    public static final boolean FW_BR_PROMOTE_SYSTEM_UID = true;
    public static final boolean FW_BR_RECEIVER_AGENT = true;
    public static final boolean FW_BUG_FIX = true;
    public static final boolean FW_CAN_RECEIVE_INPUT = true;
    public static final boolean FW_CAN_TURN_SCREEN_ON_WHEN_NONE_LOCK_SCREEN = true;
    public static final boolean FW_CCM_BUG_FIX;
    public static final boolean FW_CHECKING_VISIBLE_TRANSPARENT_ACTIVITY = true;
    public static final boolean FW_CHECK_FROZEN_STATE = true;
    public static final boolean FW_CHECK_PERMISSION_WITH_CALLING_PKG_THAN_UID = true;
    public static final boolean FW_CHECK_SNAPSHOT_SIZE_MATCH = true;
    public static final boolean FW_CHECK_THREAD_OF_SCHEDULETRAVERSALS = true;
    public static final boolean FW_CHN_PREMIUM_WATCH;
    public static final boolean FW_CLEANUP_DEFERRED_STARTING_WINDOW = true;
    public static final boolean FW_CLEANUP_DISABLED_PROVIDER = true;
    public static final boolean FW_COLLECT_COMPONENT_RECORDS = true;
    public static final boolean FW_CONFIG_BUG_FIX = true;
    public static final boolean FW_CONSECUTIVE_LAUNCH = true;
    public static final boolean FW_CORE_SCPM = true;
    public static final boolean FW_COVER_POLICY = true;
    public static final boolean FW_COVER_VIEW_DISPLAY = true;
    public static final boolean FW_CUSTOM_BASIC_ANIM;
    public static final boolean FW_CUSTOM_BASIC_ANIM_BUG_FIX = true;
    public static final boolean FW_CUSTOM_BASIC_ANIM_WITH_DIM;
    public static final boolean FW_CUSTOM_BUGREPORT_WRITER = true;
    public static final boolean FW_CUSTOM_DISPLAY_CHANGE_ANIM;
    public static final boolean FW_CUSTOM_LETTERBOX;
    public static final boolean FW_DARK_MODE_MANAGER = true;
    public static final boolean FW_DEDICATED_MEMORY;
    public static final boolean FW_DEFAULT_LANDSCAPE_ROTATION = false;
    public static final boolean FW_DELETE_USAGE_EVENT = true;
    public static final boolean FW_DELETE_USAGE_FILE = true;
    public static final boolean FW_DELETE_USAGE_STATS = true;
    public static final boolean FW_DEVELOPMENT_DISABLE_STARTING_WINDOW;
    public static final boolean FW_DIM_BUG_FIX = true;
    public static final boolean FW_DISABLE_EXIT_SPLASH_WINDOW_ANIMATION = true;
    public static final boolean FW_DISABLE_IMMERSIVE_CONFIRMATION = true;
    public static final boolean FW_DISABLE_SNAPSHOT_STARTING_WINDOW_BY_METADATA = true;
    public static final boolean FW_DISABLE_SPLASH_SCREEN = true;
    public static final boolean FW_DISABLE_SUPER_HDR = true;
    public static final boolean FW_DISPLAY_CUTOUT_BG = true;
    public static final boolean FW_DISPLAY_CUTOUT_CONTROL = true;
    public static final boolean FW_DISPLAY_POLICY_RESOURCES_DEBUG = true;
    public static final boolean FW_DO_NOT_SHOW_NFC_AID = true;
    public static final boolean FW_DO_NOT_SHOW_SENSISTIVE_INFO = true;
    public static final boolean FW_DO_NOT_SHOW_TRANSIENT_NAVBAR = true;
    public static final boolean FW_DVFS_BOOSTER = true;
    public static final boolean FW_DVRR_TOOLKIT_BUG_FIX;
    public static final boolean FW_DVRR_TOOLKIT_FORCE_DEFAULT_NORMAL;
    public static final boolean FW_DVRR_TOOLKIT_POLICY;
    public static final boolean FW_DVRR_TOOLKIT_POLICY_FOR_SCROLL;
    public static final boolean FW_DVRR_TOOLKIT_PRIORITIZE_HIGH_HINT;
    public static final boolean FW_DVRR_TOOLKIT_PROLONG_TOUCH_BOOST;
    public static final boolean FW_DVRR_TOOLKIT_REQUESTED_REFRESH_RATE;
    public static final boolean FW_DVRR_TOOLKIT_SUPPORT_HIGH_FRAME_RATE;
    public static final boolean FW_DVRR_TOOLKIT_SUPPORT_HRR;
    public static final boolean FW_DYNAMIC_RESOLUTION_CONTROL;
    public static final boolean FW_EDGE_EXTENSION_ANIM_DEBUG;
    public static final boolean FW_EDGE_EXTENSION_RESTRICTION = true;
    public static final boolean FW_ENCRYPT_ALLOW_BLOCK_LIST = true;
    public static final boolean FW_EXEMPT_AUTHORITY_ENABLED = true;
    public static final boolean FW_EXTRA_DIM_BUG_FIX = true;
    public static final boolean FW_FACTORY_TEST = true;
    public static final boolean FW_FADE_ANIMATION_FOR_NAVBAR_WHILE_ROTATION = true;
    public static final boolean FW_FIX_FIXED_ROTATION = true;
    public static final boolean FW_FIX_SYSTEM_UID_PROVIDER_ADJ = true;
    public static final boolean FW_FIX_WMTEST_CASE = true;
    public static final boolean FW_FLAG_TO_HEX_STRING = true;
    public static final boolean FW_FLEXIBLE_BOOTING_POLICY = false;
    public static final boolean FW_FLEXIBLE_BUG_FIX = false;
    public static final boolean FW_FLEXIBLE_CONTROL_FOLDING_SENSOR;
    public static final boolean FW_FLEXIBLE_DEVICE_STATE_CONFIG = false;
    public static final boolean FW_FLEXIBLE_DISPLAY = false;
    public static final boolean FW_FLEXIBLE_DUAL_MODE;
    public static final boolean FW_FLEXIBLE_JETPACK_LIBRARY = false;
    public static final boolean FW_FLEXIBLE_MULTI_DISPLAY_CUTOUT = false;
    public static final boolean FW_FLEXIBLE_OVERRIDE_STATE_POLICY = false;
    public static final boolean FW_FLEXIBLE_PERFORMANCE = false;
    public static final boolean FW_FLEXIBLE_TABLE_MODE;
    public static final boolean FW_FLEXIBLE_TENT_MODE;
    public static final boolean FW_FLEX_MODE_APP_LIST;
    public static final boolean FW_FLEX_PANEL;
    public static final boolean FW_FLEX_PANEL_CONTROL;
    public static final boolean FW_FLEX_PANEL_DEFAULT_LIST;
    public static final boolean FW_FLIP = false;
    public static final boolean FW_FLIP_ANDROID_APPS_ON_WINDOWS = false;
    public static final boolean FW_FLIP_BUG_FIX = false;
    public static final boolean FW_FLIP_CONFIG_MANAGEMENT = false;
    public static final boolean FW_FLIP_LARGE_COVER_SCREEN = false;
    public static final boolean FW_FLIP_LARGE_COVER_SCREEN_SA_LOGGING;
    public static final boolean FW_FLIP_LARGE_COVER_SCREEN_SIZE_COMPAT_MODE = false;
    public static final boolean FW_FLIP_NO_SPLASH_FROM_COVER_SCREEN = false;
    public static final boolean FW_FLIP_POLICY = false;
    public static final boolean FW_FLIP_RESTORE_DEFAULT_DISPLAY_DENSITY_IN_BOOT;
    public static final boolean FW_FLIP_TENT_MODE = false;
    public static final boolean FW_FOCUSED_CAN_BE_NAV_COLOR_WIN = true;
    public static final boolean FW_FOLD = false;
    public static final boolean FW_FOLDABLE_PACKAGE_FEATURE = true;
    public static final boolean FW_FOLDSTAR = false;
    public static final boolean FW_FOLD_ADJUST_EXTRA_DISPLAY_SIZE = false;
    public static final boolean FW_FOLD_APP_CONTINUITY_GUIDE_CONFIRMATION = false;
    public static final boolean FW_FOLD_APP_CONTINUITY_MODE = false;
    public static final boolean FW_FOLD_BUG_FIX = false;
    public static final boolean FW_FOLD_CONFIG = false;
    public static final boolean FW_FOLD_DISALLOW_EXTRA_DISPLAY_SIZE = false;
    public static final boolean FW_FOLD_FOLDING_POLICY = false;
    public static final boolean FW_FOLD_HIDE_CUTOUT = false;
    public static final boolean FW_FOLD_NO_SPLASH_INSTEAD_OF_SNAPSHOT = false;
    public static final boolean FW_FOLD_POLICY = false;
    public static final boolean FW_FOLD_RESTART_APP_POLICY = false;
    public static final boolean FW_FOLD_RESTORE_APP_POLICY = false;
    public static final boolean FW_FOLD_RESTORE_DEFAULT_DISPLAY_SIZE_IN_BOOT = false;
    public static final boolean FW_FOLD_RUNESTONE_LOGGING = false;
    public static final boolean FW_FOLD_SA_LOGGING;
    public static final boolean FW_FOLD_SETTINGS = false;
    public static final boolean FW_FOLD_SWAP_DISPLAY_ANIM = false;
    public static final boolean FW_FOLD_SWAP_DISPLAY_POLICY = false;
    public static final boolean FW_FOLD_TASKBAR_FORCE_HIDE = false;
    public static final boolean FW_FOLD_WALLPAPER_POLICY;
    public static final boolean FW_FORCED_DISPLAY_SIZE_DENSITY = true;
    public static final boolean FW_FORCE_ENABLE_LOG = true;
    public static final boolean FW_FORCE_SCREEN_TIMEOUT_CONCEPT = true;
    public static final boolean FW_FREEZE_TASK_DISPLAY_AREA = true;
    public static final boolean FW_GAME_TOOLS_WINDOW_POLICY = true;
    public static final boolean FW_GATE_MESSAGE_LOG = true;
    public static final boolean FW_GESTURE_HINT = true;
    public static final boolean FW_GET_META_DATA = true;
    public static final boolean FW_GFX_INFO_DUMP_TEXTURE_CACHE = true;
    public static final boolean FW_HIDE_APPINFO_IN_ERROR_DIALOG_BEFORE_PROVISIONED = true;
    public static final boolean FW_HIDE_CUTOUT = true;
    public static final boolean FW_HIDE_IME_ON_PRESSING_HOME = true;
    public static final boolean FW_IGNORE_HIDE_NON_SYSTEM_OVERLAY_WINDOWS = true;
    public static final boolean FW_IGNORE_NAVBAR_PARAMS = true;
    public static final boolean FW_IGNORE_NOTIFY_FINISH_FOR_INSETS_ANIM_ALREADY_CANCELED = true;
    public static final boolean FW_IGNORE_STARTSEQ_AT_SECOND_CHECK = true;
    public static final boolean FW_IME_BUG_FIX = true;
    public static final boolean FW_IMPROVED_MOVED_ANIMATION_FOR_IME;
    public static final boolean FW_INSETS_BUG_FIX = true;
    public static final boolean FW_INSETS_LOG = true;
    public static final boolean FW_INSETS_LOG_DEBUG;
    public static final boolean FW_INSET_ANIM;
    public static final boolean FW_INTERNAL_PRESENTATION_ONLY = true;
    public static final boolean FW_IS_CURRENT_ACTIVE_USER = true;
    public static final boolean FW_KEEP_ALIVE_MOVED_BACKWARD_TASK = true;
    public static final boolean FW_KEEP_SCREEN_ON_FIX = true;
    public static final boolean FW_KILL_THREAD_GROUP_LEADER_ONLY = true;
    public static final boolean FW_LABS = true;
    public static final boolean FW_LARGE_FLIP_LAUNCHER_POLICY = false;
    public static final boolean FW_LARGE_FLIP_LAUNCHER_WIDGET = false;
    public static final boolean FW_LARGE_FLIP_LAUNCHER_WIDGET_AVOID_START = false;
    public static final boolean FW_LARGE_FLIP_LAUNCHER_WIDGET_INSETS = false;
    public static final boolean FW_LARGE_FLIP_LAUNCHER_WIDGET_POLICY_CHN;
    public static final boolean FW_LARGE_FLIP_LAUNCHER_WIDGET_RELAUNCH_WEBVIEW_ATTACHED_ACTIVITY =
            false;
    public static final boolean FW_LARGE_FLIP_LAUNCHER_WIDGET_SUPPORT_CUSTOM_DENSITY = false;
    public static final boolean FW_LARGE_FLIP_NAVBAR = false;
    public static final boolean FW_LARGE_FLIP_PENDING_INTENT = false;
    public static final boolean FW_LARGE_FLIP_POLICY = false;
    public static final boolean FW_LARGE_FLIP_PREDICTIVE_BACK_ANIM;
    public static final boolean FW_LARGE_FLIP_RECENTS_ANIM;
    public static final boolean FW_LARGE_FLIP_ROTATION_POLICY = false;
    public static final boolean FW_LARGE_FLIP_SET_COVER_OCCLUDED = false;
    public static final boolean FW_LARGE_FLIP_TRANSITION;
    public static final boolean FW_LARGE_FLIP_USE_SYSTEM_USER_WHILE_LAUNCHING_SUBHOME = false;
    public static final boolean FW_LIGHT_SOURCE_GEOMETRY = true;
    public static final boolean FW_LOCALE_CHANGED_HISTORY = true;
    public static final boolean FW_LOCK_CURRENT_ROTATION = true;
    public static final boolean FW_LOCK_TASK_MODE_BUG_FIX = false;
    public static final boolean FW_LOCK_TASK_MODE_DISABLE_TRANSIENT_STATUS_BAR = true;
    public static final boolean FW_LOCK_TASK_MODE_LOG = true;
    public static final boolean FW_LONG_LIVE_THE_APP = true;
    public static final boolean FW_LOW_POWER_LOCATION_REQUEST = true;
    public static final boolean FW_LOW_RUNNING_TASK_SNAPSHOT_SCALE = true;
    public static final boolean FW_LOW_TASK_SNAPSHOT_SCALE_FOR_TABLET;
    public static final boolean FW_MAGNIFY_POLICY = true;
    public static final boolean FW_MANAGE_AOD_POLICY = true;
    public static final boolean FW_MANAGE_AOD_WALLPAPER_POLICY = true;
    public static final boolean FW_MANAGE_UNKNOWN_APPS = true;
    public static final boolean FW_MANAGE_VIEW_ON_AOD = true;
    public static final boolean FW_MAX_BR_HISTORY_SIZE_ADJUST = true;
    public static final boolean FW_METADATA_COLLECTOR = true;
    public static final boolean FW_MINIMIZED_IME_INSET_ANIM;
    public static final boolean FW_MULTI_BUILT_IN_DISPLAY = true;
    public static final boolean FW_MULTI_DISPLAY_BUG_FIX = true;
    public static final boolean FW_MULTI_FOLD;
    public static final boolean FW_MULTI_FOLD_TESTING;
    public static final boolean FW_NAVBAR_CUSTOM_COLOR = true;
    public static final boolean FW_NAVBAR_DEFAULT_LIGHT = true;
    public static final boolean FW_NAVBAR_GESTURE = true;
    public static final boolean FW_NAVBAR_IGNORE_HWKEY_SYSTEM_PROPERTY = true;
    public static final boolean FW_NAVBAR_MOVABLE_POLICY;
    public static final boolean FW_NIGHT_DIM = true;
    public static final boolean FW_NOTE_PROCSTATE_AFTER_CORE_UID_REMOVED = true;
    public static final boolean FW_NOTIFY_TASKBAR_VISIBLE;
    public static final boolean FW_ONE_HAND_OPERATION = true;
    public static final boolean FW_ONE_HAND_OPERATION_SW_BLUELIGHT_FILTER = false;
    public static final boolean FW_ORIENTATION_CONTROL;
    public static final boolean FW_ORIENTATION_CONTROL_DEFAULT_ENABLED;
    public static final boolean FW_ORIGIN_DISPLAY_ID_OF_RESOURCE = true;
    public static final boolean FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT;
    public static final boolean FW_PACKAGE_FEATURE = true;
    public static final boolean FW_PENDING_COMMANDED_BR_LOG = true;
    public static final boolean FW_PERFORMANCE_BOOST_PRIORITY_WHILE_LAUNCHING = true;
    public static final boolean FW_PERFORMANCE_UPDATE_SYSUI_FIRST = true;
    public static final boolean FW_PERFORMANCE_WAKE_AND_UNLOCK = true;
    public static final boolean FW_PER_APP_DARK_MODE = true;
    public static final boolean FW_POINTERICON_CONCEPT = true;
    public static final boolean FW_POLICY_CONTROL = true;
    public static final boolean FW_POP_OVER = true;
    public static final boolean FW_POP_OVER_ADJUST_ANIMATION = true;
    public static final boolean FW_POP_OVER_OUTSIDE_TOUCH = true;
    public static final boolean FW_POP_OVER_VIEW = true;
    public static final boolean FW_POP_OVER_WINDOW_FOCUS_IN_TASK = true;
    public static final boolean FW_PORTRAIT_SYSTEM_WINDOW = true;
    public static final boolean FW_PORTRAIT_WALLPAPER = true;
    public static final boolean FW_POWER_SAVING_DARK_MODE_SETTINGS = true;
    public static final boolean FW_PREDICTIVE_BACK_ANIM;
    public static final boolean FW_PREDICTIVE_BACK_ANIM_BUG_FIX;
    public static final boolean FW_PRELOAD_APPICON_WHILE_LAUNCHING = true;
    public static final boolean FW_PREVENT_ADB_KILL_FORCE_STOP = true;
    public static final boolean FW_PREVENT_BOOT_FAILURE_BY_BROKEN_XML = true;
    public static final boolean FW_PREVENT_CLEAN_UP_RECEIVERS = true;
    public static final boolean FW_PREVENT_DOS_ATTACK = true;
    public static final boolean FW_PREVENT_NPE_BUG_FIX = true;
    public static final boolean FW_PREVENT_SOLID_RIPPLE_EXCEPTION = true;
    public static final boolean FW_PRE_BOOT_COMPLETED = true;
    public static final boolean FW_PRE_UPDATE_SYSTEM_UI_CONTEXT_RESOURCE = true;
    public static final boolean FW_PROVIDE_BARS_INFO_TO_EDGE = true;
    public static final boolean FW_PROVIDE_PID_TO_SURFACE_FLINGER = true;
    public static final boolean FW_PROVIDE_PKG_NAME_WHO_CONTROL_BRIGHTNESS = true;
    public static final boolean FW_PROVIDE_PKG_NAME_WHO_WAKES_UP = true;
    public static final boolean FW_PROVIDE_SAMSUNG_PAY_INFO_TO_NAVBAR = true;
    public static final boolean FW_RECENTS_GESTURE_HINT_OFF = true;
    public static final boolean FW_RECENT_TASKS_BUG_FIX = true;
    public static final boolean FW_RECONFIGURE_MONITOR_SURFACE = true;
    public static final boolean FW_REDUCE_STARTING_WINDOW_EXIT_DURATION = true;
    public static final boolean FW_RELEASE_DVFS_LOCK_OF_OVERSCROLLER = true;
    public static final boolean FW_REMOTE_WALLPAPER_ANIM;
    public static final boolean FW_REPORT_ERROR_TO_SAMSUNG_MEMBERS = true;
    public static final boolean FW_REPORT_SHUTDOWN_USAGE_EVENT = true;
    public static final boolean FW_RESPECT_LIGHT_APPEARANCE_OF_BLURRING_WINDOW = true;
    public static final boolean FW_RESTRICT_BROADCAST_FROM_ABNORMAL_APP = true;
    public static final boolean FW_RIPPLE_CONCEPT = true;
    public static final boolean FW_ROTATION_BUG_FIX = true;
    public static final boolean FW_ROTATION_LOG;
    public static final boolean FW_SAFETY_SYSTEM_SERVICE = true;
    public static final boolean FW_SAFE_MODE = true;
    public static final boolean FW_SA_LOGGING;
    public static final boolean FW_SA_LOGGING_FOR_HALF_OPEN_MODE;
    public static final boolean FW_SCREENSHOT = true;
    public static final boolean FW_SCREENSHOT_BY_PALM_MOTION = true;
    public static final boolean FW_SCREENSHOT_CONTAINS_BLUR_LAYERS = true;
    public static final boolean FW_SCREENSHOT_CONTAINS_SECURE_LAYERS = true;
    public static final boolean FW_SCREENSHOT_FOR_HDR;
    public static final boolean FW_SCREENSHOT_FROM_BIXBY = true;
    public static final boolean FW_SCREENSHOT_FROM_DEX = true;
    public static final boolean FW_SCREENSHOT_FROM_FLEX_PANEL;
    public static final boolean FW_SCREENSHOT_FROM_QUICK_PANEL = true;
    public static final boolean FW_SCREENSHOT_SENSITIVE_CONTENT = true;
    public static final boolean FW_SCREENSHOT_SHELL_COMMAND = true;
    public static final boolean FW_SCREENSHOT_TARGET_WINDOW = true;
    public static final boolean FW_SCREEN_DIM_DURATION = true;
    public static final boolean FW_SCREEN_MODE_SETTING;
    public static final boolean FW_SEPARATE_TOAST_TOKEN = true;
    public static final boolean FW_SEP_LAYOUT = true;
    public static final boolean FW_SEP_SURFACE_METADATA = true;
    public static final boolean FW_SERVICE_BUG_FIX = true;
    public static final boolean FW_SET_MAX_SNAPSHOT_CACHE = true;
    public static final boolean FW_SET_SKIP_SCREENSHOT = true;
    public static final boolean FW_SHELL_DRAWER_BUG_FIX = true;
    public static final boolean FW_SHELL_TRANSITION;
    public static final boolean FW_SHELL_TRANSITION_AOD_APPEAR;
    public static final boolean FW_SHELL_TRANSITION_BLACK_SNAPSHOT;
    public static final boolean FW_SHELL_TRANSITION_BUG_FIX;
    public static final boolean FW_SHELL_TRANSITION_DISPLAY_CHANGE;
    public static final boolean FW_SHELL_TRANSITION_EXTENSION;
    public static final boolean FW_SHELL_TRANSITION_LOG;
    public static final boolean FW_SHELL_TRANSITION_MERGE;
    public static final boolean FW_SHELL_TRANSITION_MERGE_TRANSFER;
    public static final boolean FW_SHELL_TRANSITION_RECENTS_BUG_FIX;
    public static final boolean FW_SHELL_TRANSITION_REMOTE;
    public static final boolean FW_SHELL_TRANSITION_RESUMED_AFFORDANCE;
    public static final boolean FW_SHELL_TRANSITION_ROTATED_WALLPAPER_SNAPSHOT;
    public static final boolean FW_SHELL_TRANSITION_SEPARATE_RECENTS;
    public static final boolean FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY;
    public static final boolean FW_SHELL_TRANSITION_WITH_DIM;
    public static final boolean FW_SHOULD_DISABLE_ROTATION_SENSOR = true;
    public static final boolean FW_SHOW_ALWAYS_ACTIVE_APP_LIST_POPUP = true;
    public static final boolean FW_SKIP_FORCE_STOP_TMO_PKG = true;
    public static final boolean FW_SPEN_HOVER;
    public static final boolean FW_STACKED_BLUR_SUPPORTED;
    public static final boolean FW_STARTING_WINDOW_BUG_FIX = true;
    public static final boolean FW_STARTING_WINDOW_PERFORMANCE = true;
    public static final boolean FW_START_ACTIVITY_DEBUG_LOG = true;
    public static final boolean FW_SUPPORT_ADD_JANK_CUJ = true;
    public static final boolean FW_SUPPORT_API_GET_REQUESTED_ORIENTATION_BY_TASKID = true;
    public static final boolean FW_SUPPORT_API_GET_RESUMED_TASK_THUMBNAIL = true;
    public static final boolean FW_SUPPORT_API_QUERY_REGISTERED_RECEIVER_PACKAGES = true;
    public static final boolean FW_SUPPORT_APPLOCK;
    public static final boolean FW_SUPPORT_COMPANION_DEVICE_BATTERY_INFO = true;
    public static final boolean FW_SUPPORT_CUSTOM_NO_KILL_DURATION = true;
    public static final boolean FW_SUPPORT_DARK_THEME_ON_USER_SWITCHING_DIALOG = true;
    public static final boolean FW_SUPPORT_DISMISS_USER_SWITCHING_DIALOG = true;
    public static final boolean FW_SUPPORT_FORCE_TRUSTED_OVERLAY = true;
    public static final boolean FW_SUPPORT_INSTALL_ISSUE_TRACKER_PROVIDER = true;
    public static final boolean FW_SUPPORT_KILL_EMPTY_PROCESS_UNTIL_BOOT_COMPLETED = true;
    public static final boolean FW_SUPPORT_LOCK_TASK_MODE_BROADCAST;
    public static final boolean FW_SUPPORT_LOCK_TASK_MODE_CALLBACK = true;
    public static final boolean FW_SUPPORT_LOCK_TASK_MODE_SHOW_KEYGUARD = true;
    public static final boolean FW_SUPPORT_META_DATA_TO_ALLOW_SEAMLESS_ROTATION = true;
    public static final boolean FW_SUPPORT_MULTI_DISPLAY = true;
    public static final boolean FW_SUPPORT_NATIVE_AI;
    public static final boolean FW_SUPPORT_NO_DELAY_BR_FOR_PERCEPTIBLE = true;
    public static final boolean FW_SUPPORT_ONE_TOUCH;
    public static final boolean FW_SUPPORT_PERSISTENT_DOWNLOADABLE = true;
    public static final boolean FW_SUPPORT_RESCUE_PARTY_DIALOG = true;
    public static final boolean FW_SUPPORT_SINGLE_SKU = true;
    public static final boolean FW_SUPPORT_SPROTECT = false;
    public static final boolean FW_SUPPORT_SURVIVAL_TICKET_IN_RESOLUTION_CHANGING = true;
    public static final boolean FW_SUPPORT_SYSTEMUI_BOOTING_SPEED = true;
    public static final boolean FW_SUPPORT_USER_SWITCHING_TOAST_TEXT = true;
    public static final boolean FW_SUPPORT_VIEW_BLUR = true;
    public static final boolean FW_SURFACEVIEW_BUG_FIX = true;
    public static final boolean FW_SURFACE_CREATION_TIME = true;
    public static final boolean FW_SURFACE_DEBUG = true;
    public static final boolean FW_SURFACE_DEBUG_ALPHA;
    public static final boolean FW_SURFACE_DEBUG_APPLY;
    public static final boolean FW_SURFACE_DEBUG_CREATION;
    public static final boolean FW_SURFACE_DEBUG_CROP;
    public static final boolean FW_SURFACE_DEBUG_LAYER;
    public static final boolean FW_SURFACE_DEBUG_REMOVE;
    public static final boolean FW_SURFACE_DEBUG_REPARENT;
    public static final boolean FW_SURFACE_DEBUG_TRANSFORM;
    public static final boolean FW_SURFACE_DEBUG_VISIBILITY;
    public static final boolean FW_SYSTEMUI_REVIVAL = true;
    public static final boolean FW_SYSTEM_DUMP_WRITER = true;
    public static final boolean FW_SYSTEM_TIMEOUT_ADJUST = true;
    public static final boolean FW_TASKBAR = true;
    public static final boolean FW_TASK_ORG_BUG_FIX = true;
    public static final boolean FW_THEME_STARTING_WINDOW = true;
    public static final boolean FW_TOUCHSLOP = true;
    public static final boolean FW_TRANSACTION_DEBUG_LOG = true;
    public static final boolean FW_TRIM_MEMORY_LOG;
    public static final boolean FW_TSP_DEADZONE;
    public static final boolean FW_TSP_DEADZONEHOLE_LAND;
    public static final boolean FW_TSP_DEADZONE_V3;
    public static final boolean FW_TSP_NOTE_MODE;
    public static final boolean FW_TSP_SIP_MODE;
    public static final boolean FW_TSP_STATE_CONTROLLER;
    public static final boolean FW_UDC_CUTOUT = true;
    public static final boolean FW_UI_MODE_ANIMATION;
    public static final boolean FW_UNLEASH_WATCHDOG_ON_PROCSTART = true;
    public static final boolean FW_UPDATE_APPLICATION_INFO_OF_BR = true;
    public static final boolean FW_UPDATE_CONFIG_FOR_ALL_CHILD_VIEW_ROOTS = true;
    public static final boolean FW_UPDATE_HAS_NAVBAR = true;
    public static final boolean FW_UPDATE_OVERLAY_PATHS_BUG_FIX = true;
    public static final boolean FW_USAGE_BUG_FIX = true;
    public static final boolean FW_USAGE_FROM_CURRENT_ACTIVITY = true;
    public static final boolean FW_USAGE_STATS_DEBUG_LOGS = true;
    public static final boolean FW_USAGE_STATS_WATCHER = true;
    public static final boolean FW_USE_PENDING_INTENT_RUNNABLE_WHILE_TRANSITION = true;
    public static final boolean FW_USE_SMALLER_GRIPZONE_ON_GAME;
    public static final boolean FW_VALIDATE_WAKE_LOCK_CALLER = true;
    public static final boolean FW_VIEW_CONCEPT = true;
    public static final boolean FW_VIEW_DEBUG_LOG = true;
    public static final boolean FW_VIEW_ON_DEX = true;
    public static final boolean FW_VIEW_SURFACE_CONTROLLER = true;
    public static final boolean FW_VIVID_WCG_ON = true;
    public static final boolean FW_VRR_BUG_FIX = true;
    public static final boolean FW_VRR_DISCRETE;
    public static final boolean FW_VRR_FIXED_REFRESH_RATE_PACKAGE;
    public static final boolean FW_VRR_FOR_SUB_DISPLAY;
    public static final boolean FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST;
    public static final boolean FW_VRR_HRR_CHINA_DELTA;
    public static final boolean FW_VRR_IGNORE_RESTRICTED_RANGE;
    public static final boolean FW_VRR_LOW_REFRESH_RATE_LIST;
    public static final boolean FW_VRR_NAVIGATION_LOW_REFRESH_RATE;
    public static final boolean FW_VRR_PERFORMANCE;
    public static final boolean FW_VRR_POLICY;
    public static final boolean FW_VRR_POWER_SAVING = true;
    public static final boolean FW_VRR_REFRESH_RATE_MODE;
    public static final boolean FW_VRR_REFRESH_RATE_TOKEN;
    public static final boolean FW_VRR_RESOLUTION_POLICY;
    public static final boolean FW_VRR_RESOLUTION_POLICY_FOR_SHELL_TRANSITION;
    public static final boolean FW_VRR_SEAMLESS;
    public static final boolean FW_VRR_SEND_TOUCH_HINT;
    public static final boolean FW_VRR_SYSTEM_HISTORY;
    public static final boolean FW_WAIT_OCCLUDING = true;
    public static final boolean FW_WAIT_TO_HANDLE_RESIZING_FROM_CLIENT;
    public static final boolean FW_WALLPAPER_BUG_FIX = true;
    public static final boolean FW_WALLPAPER_LOG = true;
    public static final boolean FW_WINDOW_BLUR_SUPPORTED;
    public static final boolean FW_WM_EVENTS_LOG = true;
    public static final boolean FW_WM_FILE_LOGGING = true;
    public static final boolean FW_WM_MIRRORING_FIX = true;
    public static final boolean FW_WORKAROUND_LARGE_FLIP_REUSE_TASK = false;
    public static final boolean FW_WORKAROUND_MAX_WINDOW_COUNT = true;
    public static final boolean FW_WORKAROUND_PREVENT_REPEAT_ADD_WINDOW = true;
    public static final boolean FW_WORKAROUND_RESPONSE_SPEED = false;
    public static final boolean GFW_DEBUG_DISABLE_HWRENDERING;
    public static final boolean GRAPHICS_RENDER_DEBUG = true;
    public static final boolean GRAPHICS_RENDER_ENGINE_POLICY;
    public static final boolean GSC_ENABLED = true;
    public static final boolean IS_PREDICTIVE_BACK_ANIM_ENABLED;
    public static final boolean IS_SHELL_TRANSITION_ENABLED;
    public static final boolean KILL_PROCESS_TIMEOUT_WHEN_DEX_EXIT = true;
    public static final boolean KILL_TOO_MANY_VIEW_REQUEST = true;
    public static final boolean KLMS_LICENSE_AGENT_DUMP_LOG = true;
    public static final boolean KNOX_SUPPORT_CUSTOMIZATION_SDK = true;
    public static final boolean KNOX_SUPPORT_DAR = true;
    public static final boolean KNOX_SUPPORT_DAR_DUAL = true;
    public static final boolean KNOX_SUPPORT_DAR_DUAL_DO = true;
    public static final boolean KNOX_SUPPORT_DAR_SDP = false;
    public static final boolean KNOX_SUPPORT_DUAL_APP = true;
    public static final boolean KNOX_SUPPORT_ENDPOINT_MONITORING = true;
    public static final boolean KNOX_SUPPORT_KEYGUARD = true;
    public static final boolean KNOX_SUPPORT_MDM = true;
    public static final boolean KNOX_SUPPORT_UCS = true;
    public static final boolean KNOX_SUPPORT_VPN = true;
    public static final boolean KNOX_SUPPORT_ZERO_TRUST = true;
    public static final boolean KNOX_ZERO_TRUST = true;
    public static final boolean MD = true;
    public static final boolean MD_APP_CASTING = true;
    public static final boolean MD_BUBBLE_DEX_LAUNCH_POLICY = true;
    public static final boolean MD_BUG_FIX = true;
    public static final boolean MD_CLOSE_SYSTEM_DIALOGS = true;
    public static final boolean MD_DEBUG_LOG = false;
    public static final boolean MD_DEX = true;
    public static final boolean MD_DEXSTAR = true;
    public static final boolean MD_DEX_APP_CONFIG_ADJUSTMENT = true;
    public static final boolean MD_DEX_BAR_TYPE_INTERFACE = true;
    public static final boolean MD_DEX_BOUNDS_POLICY = true;
    public static final boolean MD_DEX_CHANGE_FONT_SCALE = true;
    public static final boolean MD_DEX_COMPAT_BOUNDS_POLICY = true;
    public static final boolean MD_DEX_COMPAT_CAPTION_SHELL;
    public static final boolean MD_DEX_COMPAT_CONFIG_ADJUSTMENT = true;
    public static final boolean MD_DEX_COMPAT_FORCE_RESIZABLE = true;
    public static final boolean MD_DEX_COMPAT_MODE = true;
    public static final boolean MD_DEX_COMPAT_RESTART_DIALOG = true;
    public static final boolean MD_DEX_COMPAT_TRANSLATOR = true;
    public static final boolean MD_DEX_CONFIG_MANAGEMENT = true;
    public static final boolean MD_DEX_DELIVER_KEY_EVENT_TO_TASK_BAR = true;
    public static final boolean MD_DEX_DEVELOPER_MODE = true;
    public static final boolean MD_DEX_DISPLAY_MANAGEMENT = true;
    public static final boolean MD_DEX_DISPLAY_ORDERING = true;
    public static final boolean MD_DEX_EMULATOR = false;
    public static final boolean MD_DEX_FREEFORM_RESIZE_GUIDEVIEW = true;
    public static final boolean MD_DEX_HOME_MANAGEMENT = true;
    public static final boolean MD_DEX_IMMERSIVE_POLICY = true;
    public static final boolean MD_DEX_KEYGUARD_OCCLUDED = true;
    public static final boolean MD_DEX_LAUNCH_POLICY = true;
    public static final boolean MD_DEX_LIMIT_RUNNING_APPS = true;
    public static final boolean MD_DEX_MINIMIZE = true;
    public static final boolean MD_DEX_MINIMIZE_SHELL_TRANSITION;
    public static final boolean MD_DEX_MIRRORING_DISPLAY = true;
    public static final boolean MD_DEX_MODE_SWITCH = true;
    public static final boolean MD_DEX_NOT_CROP_TRANSLUCENT_TASK = true;
    public static final boolean MD_DEX_NOT_SUPPORT_CUTOUT;
    public static final boolean MD_DEX_NOT_SUPPORT_MULTI_USER = true;
    public static final boolean MD_DEX_PERSIST_BOUNDS = true;
    public static final boolean MD_DEX_POINTER_ICON = true;
    public static final boolean MD_DEX_POWER_MANAGEMENT = true;
    public static final boolean MD_DEX_PROCESS_DISPLAY_CONFIG_ADJUSTMENT = true;
    public static final boolean MD_DEX_RESTART_POPUP = true;
    public static final boolean MD_DEX_SA_LOGGING;
    public static final boolean MD_DEX_SHELL_TRANSITION;
    public static final boolean MD_DEX_SHORTCUT_KEY = true;
    public static final boolean MD_DEX_SHOW_PREVIEW_WHEN_HOVERED_ON_MINIMIZE_ICON = true;
    public static final boolean MD_DEX_SIP_POLICY = true;
    public static final boolean MD_DEX_STANDALONE_LAUNCH_POLICY;
    public static final boolean MD_DEX_STANDALONE_LAYOUT = true;
    public static final boolean MD_DEX_STANDALONE_ROTATION = true;
    public static final boolean MD_DEX_STANDALONE_SYSTEM_BAR_REPLACEMENT = true;
    public static final boolean MD_DEX_SUPPORT_STANDALONE;
    public static final boolean MD_DEX_TASKBAR_FORCE_IMMERSIVE = true;
    public static final boolean MD_DEX_TASKBAR_LAYOUT = true;
    public static final boolean MD_DEX_TASK_DOCKING = true;
    public static final boolean MD_DEX_TASK_INFO = true;
    public static final boolean MD_DEX_TASK_ORDERING = true;
    public static final boolean MD_DEX_TASK_POSITIONING = true;
    public static final boolean MD_DEX_TOUCH_PAD = true;
    public static final boolean MD_DEX_TOUCH_POLICY = true;
    public static final boolean MD_DEX_WIRELESS;
    public static final boolean MD_DEX_WORKSPACE_MANAGEMENT = true;
    public static final boolean MD_DISPLAY_ORDERING = true;
    public static final boolean MD_DISPLAY_TOKEN_FOR_LAYER_STACK = true;
    public static final boolean MD_DND_DEX_SUPPORT = true;
    public static final boolean MD_DND_POLICY = true;
    public static final boolean MD_ENABLE_PIXEL_ANISOTROPY_CORRECTION = false;
    public static final boolean MD_EXTERNAL_KEYBOARD_SHORTCUT = true;
    public static final boolean MD_FIXED_APP_CONTEXT_DISPLAY = true;
    public static final boolean MD_FORCE_ENABLE_DESKTOP_MODE;
    public static final boolean MD_INPUT_DEVICES = true;
    public static final boolean MD_KEY_POLICY = true;
    public static final boolean MD_MULTI_DISPLAY_KEYGUARD = true;
    public static final boolean MD_MULTI_DISPLAY_SECONDARY_HOME = true;
    public static final boolean MD_NOTIFY_SECURE_BASE_WINDOW_EVENT = true;
    public static final boolean MD_REMOTE_APP = true;
    public static final boolean MD_REMOTE_APP_DISPLAY_MANAGEMENT = true;
    public static final boolean MD_REMOTE_APP_LAUNCH_POLICY = true;
    public static final boolean MD_REMOTE_APP_WINDOW_POLICY = true;
    public static final boolean MD_STATIC_DISPLAY_ID = true;
    public static final boolean MD_TASK_CHANGE_LISTENER = true;
    public static final boolean MD_VIRTUAL_DISPLAY_POLICY = true;
    public static final boolean MNO_TMO_DEVICE_REPORTING;
    public static final boolean MT = true;
    public static final boolean MT_API_MINIMIZE = true;
    public static final boolean MT_APP_COMPAT = true;
    public static final boolean MT_APP_COMPAT_ASPECT_RATIO_POLICY;
    public static final boolean MT_APP_COMPAT_BUG_FIX = true;
    public static final boolean MT_APP_COMPAT_CAMERA_POLICY;
    public static final boolean MT_APP_COMPAT_CONFIGURATION;
    public static final boolean MT_APP_COMPAT_DISPLAY_COMPAT_MODE = false;
    public static final boolean MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
    public static final boolean MT_APP_COMPAT_LARGE_SCREEN;
    public static final boolean MT_APP_COMPAT_LETTERBOX_LEAK_BUG_FIX = true;
    public static final boolean MT_APP_COMPAT_LETTERBOX_OCCLUDES_BUG_FIX = true;
    public static final boolean MT_APP_COMPAT_LETTERBOX_TRANSITION_BUG_FIX = true;
    public static final boolean MT_APP_COMPAT_MIN_ASPECT_RATIO_LIST;
    public static final boolean MT_APP_COMPAT_ORIENTATION_POLICY;
    public static final boolean MT_APP_COMPAT_PATCH = true;
    public static final boolean MT_APP_COMPAT_POSITIONING_POLICY = true;
    public static final boolean MT_APP_COMPAT_ROTATION_COMPAT_MODE;
    public static final boolean MT_APP_COMPAT_SIZE_COMPAT_MODE_POLICY = true;
    public static final boolean MT_APP_COMPAT_STATUS_LOGGING;
    public static final boolean MT_APP_COMPAT_TRANSPARENT_POLICY;
    public static final boolean MT_APP_COMPAT_UI = true;
    public static final boolean MT_COMPAT_SANDBOX_POLICY = true;
    public static final boolean MT_DECOR_ROUNDED_CORNER_FOR_FLIP_COVER = false;
    public static final boolean MT_DECOR_ROUNDED_CORNER_FOR_LETTERBOX = true;
    public static final boolean MT_DEX_SIZE_COMPAT_DRAG;
    public static final boolean MT_DEX_SIZE_COMPAT_MODE;
    public static final boolean MT_DND_ANIMATION;
    public static final boolean MT_DND_DISABLE_CANCEL_ANIMATION;
    public static final boolean MT_DND_OBJECT_CAPTURE;
    public static final boolean MT_DND_SEAMLESS_ANIMATION;
    public static final boolean MT_DUMPSYS = true;
    public static final boolean MT_FORCED_SNAPSHOT = true;
    public static final boolean MT_FULL_SCREEN_APPS_SUPPORT_MODE = true;
    public static final boolean MT_LAUNCH_POLICY = true;
    public static final boolean MT_MINIMIZE = true;
    public static final boolean MT_MULTI_USER = true;
    public static final boolean MT_NEW_DEX;
    public static final boolean MT_NEW_DEX_BOUNDS_POLICY;
    public static final boolean MT_NEW_DEX_CONFIG_MANAGEMENT;
    public static final boolean MT_NEW_DEX_LAUNCH_POLICY;
    public static final boolean MT_NEW_DEX_LIMIT_RUNNING_APPS;
    public static final boolean MT_NEW_DEX_PERSIST_BOUNDS;
    public static final boolean MT_NEW_DEX_PIP;
    public static final boolean MT_NEW_DEX_PIP_ON_FREEFORM;
    public static final boolean MT_NEW_DEX_RESUMED_AFFORDANCE_ANIMATION;
    public static final boolean MT_NEW_DEX_SHELL_TRANSITION;
    public static final boolean MT_NEW_DEX_TASK_ORDERING;
    public static final boolean MT_NEW_DEX_TASK_PINNING;
    public static final boolean MT_NOT_SUPPORT_WALLPAPER = true;
    public static final boolean MT_SIZE_COMPAT_POLICY;
    public static final boolean MT_SIZE_COMPAT_POLICY_COORDINATION;
    public static final boolean MT_SIZE_COMPAT_POLICY_DRAG;
    public static final boolean MT_VIEW_ROUNDED_CORNER = true;
    public static final boolean MW = true;
    public static final boolean MW_AFFORDANCE_ANIM = true;
    public static final boolean MW_ALIAS_ACTIVITY = true;
    public static final boolean MW_ANIMATION_TRANSITION_COORDINATOR = true;
    public static final boolean MW_API_VISIBLE_TASKS = true;
    public static final boolean MW_ASYNC_TRANSACTION = true;
    public static final boolean MW_BIXBY = true;
    public static final boolean MW_BIXBY_SUPPORT_API = true;
    public static final boolean MW_BUBBLE_BUG_FIX = true;
    public static final boolean MW_BUG_FIX = true;
    public static final boolean MW_CAPTION_SHELL;
    public static final boolean MW_CAPTION_SHELL_BUG_FIX;
    public static final boolean MW_CAPTION_SHELL_CONSUME_CAPTION_INSET = true;
    public static final boolean MW_CAPTION_SHELL_CUSTOMIZABLE_WINDOW_HEADERS;
    public static final boolean MW_CAPTION_SHELL_DEBUG;
    public static final boolean MW_CAPTION_SHELL_DEX;
    public static final boolean MW_CAPTION_SHELL_DEX_SNAPPING_WINDOW;
    public static final boolean MW_CAPTION_SHELL_FREEFORM_MOTION;
    public static final boolean MW_CAPTION_SHELL_FREEFORM_OUTLINE = true;
    public static final boolean MW_CAPTION_SHELL_FREEFORM_PINNING;
    public static final boolean MW_CAPTION_SHELL_FREEFORM_RESIZE_GESTURE;
    public static final boolean MW_CAPTION_SHELL_FREEFORM_RESIZE_GESTURE_SA_LOGGING;
    public static final boolean MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW;
    public static final boolean MW_CAPTION_SHELL_FREEFORM_STASH = true;
    public static final boolean MW_CAPTION_SHELL_FULL_SCREEN;
    public static final boolean MW_CAPTION_SHELL_HANDLE_VIEW;
    public static final boolean MW_CAPTION_SHELL_IMMERSIVE_MODE;
    public static final boolean MW_CAPTION_SHELL_INSETS;
    public static final boolean MW_CAPTION_SHELL_KEEP_SCREEN_ON;
    public static final boolean MW_CAPTION_SHELL_NEW_DEX;
    public static final boolean MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE;
    public static final boolean MW_CAPTION_SHELL_OPACITY = false;
    public static final boolean MW_CAPTION_SHELL_OVERFLOW_MENU;
    public static final boolean MW_CAPTION_SHELL_POPUP;
    public static final boolean MW_CAPTION_SHELL_POPUP_HELP;
    public static final boolean MW_CAPTION_SHELL_SHADOW_ANIM;
    public static final boolean MW_CAPTION_SHELL_SUPPORT_WINDOW_OPACITY;
    public static final boolean MW_COMPAT_POLICY = true;
    public static final boolean MW_CORE_STATE = true;
    public static final boolean MW_DEBUG_LOG = true;
    public static final boolean MW_DISPLAY_AREA_ORGANIZER_BUG_FIX = true;
    public static final boolean MW_DISPLAY_IME_LAYOUT = true;
    public static final boolean MW_DND = true;
    public static final boolean MW_DND_BUG_FIX = true;
    public static final boolean MW_DND_CONTENTS_TO_WINDOW = true;
    public static final boolean MW_DND_CONTENTS_TO_WINDOW_PASS_CALLER_INFO = true;
    public static final boolean MW_DND_CONTENTS_TO_WINDOW_SMART_TIP = true;
    public static final boolean MW_DND_CUSTOM_DROP_TARGET = true;
    public static final boolean MW_DND_DISMISS_VIEW = true;
    public static final boolean MW_DND_DISMISS_VIEW_SA_LOGGING = true;
    public static final boolean MW_DND_DRAG_AND_DROP_CONTROLLER_PROXY = true;
    public static final boolean MW_DND_DRAG_ANIMATION = true;
    public static final boolean MW_DND_FREEFORM_DISMISS_VIEW;
    public static final boolean MW_DND_FROM_RECENT = true;
    public static final boolean MW_DND_HAPTIC = true;
    public static final boolean MW_DND_HIDE_SHELL_DROP_TARGET_FROM_EXTERNAL = true;
    public static final boolean MW_DND_LAUNCH_MULTI_WINDOW_MODE = true;
    public static final boolean MW_DND_MULTI_SPLIT_DROP_TARGET;
    public static final boolean MW_DND_MULTI_WINDOW_FOR_ALL_APPS_IN_LABS = true;
    public static final boolean MW_DND_ON_TRANSPARENT_FULLSCREEN = true;
    public static final boolean MW_DND_PIP_DISMISS_VIEW = true;
    public static final boolean MW_DND_POINTERICON_CONCEPT = true;
    public static final boolean MW_DND_SA_LOGGING;
    public static final boolean MW_DND_SHELL_DROP_TARGET_VISIBILITY = true;
    public static final boolean MW_DND_SHELL_TEST = true;
    public static final boolean MW_DND_SPLIT_ON_HOMESCREEN = true;
    public static final boolean MW_DND_TWO_HANDED = true;
    public static final boolean MW_DND_UNHANDLED_DRAG;
    public static final boolean MW_DYNAMIC_ENABLED = true;
    public static final boolean MW_EMBED_ACTIVITY;
    public static final boolean MW_EMBED_ACTIVITY_ANIMATION;
    public static final boolean MW_EMBED_ACTIVITY_DEBUG_LOG;
    public static final boolean MW_EMBED_ACTIVITY_MODE;
    public static final boolean MW_EMBED_ACTIVITY_PACKAGE_ENABLED;
    public static final boolean MW_EMBED_ACTIVITY_SYSTEM_BAR_POLICY;
    public static final boolean MW_ENSURE_LAUNCH_WINDOWING_MODE = true;
    public static final boolean MW_EVENT_CALLBACK = true;
    public static final boolean MW_EVENT_LOG = true;
    public static final boolean MW_FIXED_ROTATION_POLICY = true;
    public static final boolean MW_FORCED_RESIZABLE_INFO = true;
    public static final boolean MW_FORCE_LAUNCH_WINDOWING_MODE = true;
    public static final boolean MW_FREEFORM = true;
    public static final boolean MW_FREEFORM_BOUNDS_POLICY = true;
    public static final boolean MW_FREEFORM_BUG_FIX = true;
    public static final boolean MW_FREEFORM_CORNER_GESTURE = true;
    public static final boolean MW_FREEFORM_CORNER_GESTURE_CUSTOM = true;
    public static final boolean MW_FREEFORM_CORNER_GESTURE_SA_LOGGING;
    public static final boolean MW_FREEFORM_DIM_LAYER = true;
    public static final boolean MW_FREEFORM_DISABLE_INPUT_SINK = true;
    public static final boolean MW_FREEFORM_EXCLUDE_FROM_RECENTS_BUG_FIX = true;
    public static final boolean MW_FREEFORM_FINISH_MOVING_TASK_CALLBACK = true;
    public static final boolean MW_FREEFORM_FORCE_HIDING = true;
    public static final boolean MW_FREEFORM_FORCE_HIDING_TRANSITION;
    public static final boolean MW_FREEFORM_HEADER_TYPE = true;
    public static final boolean MW_FREEFORM_HEADER_TYPE_SA_LOGGING;
    public static final boolean MW_FREEFORM_HEADER_TYPE_SHELL_TRANSITION;
    public static final boolean MW_FREEFORM_IME_INSET_UPDATE = true;
    public static final boolean MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY;
    public static final boolean MW_FREEFORM_LAUNCH_POLICY = true;
    public static final boolean MW_FREEFORM_LAYOUT = true;
    public static final boolean MW_FREEFORM_LIMIT_RUNNING_APPS = true;
    public static final boolean MW_FREEFORM_MINIMIZE = true;
    public static final boolean MW_FREEFORM_MINIMIZED_PREVIEW;
    public static final boolean MW_FREEFORM_MINIMIZE_CONTAINER;
    public static final boolean MW_FREEFORM_MINIMIZE_CONTAINER_BINDER = true;
    public static final boolean MW_FREEFORM_MINIMIZE_CONTAINER_CALLBACK = true;
    public static final boolean MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW;
    public static final boolean MW_FREEFORM_MINIMIZE_SA_LOGGING;
    public static final boolean MW_FREEFORM_MINIMIZE_SHELL_TRANSITION;
    public static final boolean MW_FREEFORM_NAV_POLICY = true;
    public static final boolean MW_FREEFORM_OPACITY_ADJUSTMENT = true;
    public static final boolean MW_FREEFORM_ORDER = true;
    public static final boolean MW_FREEFORM_PERSIST_BOUNDS = true;
    public static final boolean MW_FREEFORM_PREVENT_HOME_FROM_MOVING_FORWARD = true;
    public static final boolean MW_FREEFORM_REMAIN_IN_RECENTS = true;
    public static final boolean MW_FREEFORM_RESIZE_TOUCHABLE_REGION;
    public static final boolean MW_FREEFORM_SHADOW_WITH_VIEW_ELEVATION = true;
    public static final boolean MW_FREEFORM_SHELL_TRANSITION;
    public static final boolean MW_FREEFORM_SIP_LAYOUT = true;
    public static final boolean MW_FREEFORM_SIP_ORDERING = true;
    public static final boolean MW_FREEFORM_SKIP_OPAQUE = true;
    public static final boolean MW_FREEFORM_SMART_POPUP_VIEW;
    public static final boolean MW_FREEFORM_SMART_POPUP_VIEW_SA_LOGGING;
    public static final boolean MW_FREEFORM_SYSTEMUI_VISIBILITY = true;
    public static final boolean MW_FREEFORM_SYSTEM_MODALS = true;
    public static final boolean MW_FREEFORM_TOUCH_REGION = true;
    public static final boolean MW_FREEFORM_TRUSTED_OVERLAY = true;
    public static final boolean MW_INTERNAL_PRESENTATION_ONLY = true;
    public static final boolean MW_LAUNCH_OVER_TARGET_TASK = true;
    public static final boolean MW_LAUNCH_PARAM_PERSISTER_DUMP = false;
    public static final boolean MW_LIVE_TILE_BUG_FIX = false;
    public static final boolean MW_MULTISTAR = true;
    public static final boolean MW_MULTISTAR_BLOCKED_MINIMIZE_FREEFORM = true;
    public static final boolean MW_MULTISTAR_ENSURE_LAUNCH_SPLIT = true;
    public static final boolean MW_MULTISTAR_LONG_PRESS = true;
    public static final boolean MW_MULTISTAR_MULTI_SPLIT_GUIDE_VIEW;
    public static final boolean MW_MULTISTAR_SPLIT_GUIDE_VIEW = true;
    public static final boolean MW_MULTISTAR_SUPPORT_CUSTOM_DENSITY = true;
    public static final boolean MW_MULTISTAR_SUPPORT_STAY_FOCUS_ACTIVITY = true;
    public static final boolean MW_MULTI_SPLIT;
    public static final boolean MW_MULTI_SPLIT_ACCESSIBILITY;
    public static final boolean MW_MULTI_SPLIT_ADJUST_FOR_IME;
    public static final boolean MW_MULTI_SPLIT_APP_PAIR;
    public static final boolean MW_MULTI_SPLIT_APP_PAIR_FOLDING_POLICY;
    public static final boolean MW_MULTI_SPLIT_BACKGROUND;
    public static final boolean MW_MULTI_SPLIT_BOUNDS_POLICY;
    public static final boolean MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT;
    public static final boolean MW_MULTI_SPLIT_CELL_DIVIDER;
    private static final String MW_MULTI_SPLIT_COUNT_STR = "0";
    public static final boolean MW_MULTI_SPLIT_CREATE_MODE;
    public static final boolean MW_MULTI_SPLIT_DIVIDER;
    public static final boolean MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD;
    public static final boolean MW_MULTI_SPLIT_ENSURE_APP_SIZE;
    public static final boolean MW_MULTI_SPLIT_FOLDING_POLICY;
    public static final boolean MW_MULTI_SPLIT_FOR_COVER_DISPLAY;
    public static final boolean MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY;
    public static final boolean MW_MULTI_SPLIT_FREE_POSITION;
    public static final boolean MW_MULTI_SPLIT_FREE_POSITION_SA_LOGGING;
    public static final boolean MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE;
    public static final boolean MW_MULTI_SPLIT_LAUNCH_ADJACENT;
    public static final boolean MW_MULTI_SPLIT_NATURAL_RESIZING;
    public static final boolean MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY;
    public static final boolean MW_MULTI_SPLIT_RECENT_TASKS;
    public static final boolean MW_MULTI_SPLIT_ROUNDED_CORNER;
    public static final boolean MW_MULTI_SPLIT_SHELL_DUMP;
    public static final boolean MW_MULTI_SPLIT_SHELL_TRANSITION;
    public static final boolean MW_MULTI_SPLIT_SHOW_INCALL_WHEN_FOLDING;
    public static final boolean MW_MULTI_SPLIT_SNAP_ALGORITHM;
    public static final boolean MW_MULTI_SPLIT_TASK_ORGANIZER;
    public static final boolean MW_MULTI_SPLIT_TASK_VISIBILITY;
    public static final boolean MW_NATURAL_SWITCHING = true;
    public static final boolean MW_NATURAL_SWITCHING_ANIM = true;
    public static final boolean MW_NATURAL_SWITCHING_FULLSCREEN;
    public static final boolean MW_NATURAL_SWITCHING_MULTI_SPLIT;
    public static final boolean MW_NATURAL_SWITCHING_PIP;
    public static final boolean MW_NATURAL_SWITCHING_SA_LOGGING;
    public static final boolean MW_NAVISTAR_IMMERSIVE_SPLIT_MODE = true;
    public static final boolean MW_NON_RESIZABLE_POLICY = true;
    public static final boolean MW_NOT_SUPPORT_WALLPAPER = true;
    public static final boolean MW_OUTSIDE_FOCUS_POLICY = true;
    public static final boolean MW_OVERHEAT_POLICY = true;
    public static final boolean MW_PIP = true;
    public static final boolean MW_PIP_ANIM_BUG_FIX = true;
    public static final boolean MW_PIP_BUG_FIX = true;
    public static final boolean MW_PIP_DISABLE_ROUNDED_CORNER;
    public static final boolean MW_PIP_DISPLAY_POLICY = true;
    public static final boolean MW_PIP_FLIP_FOLDING_POLICY = false;
    public static final boolean MW_PIP_FORCE_ENABLE_LOG = true;
    public static final boolean MW_PIP_GUI = true;
    public static final boolean MW_PIP_HIDE_IME = true;
    public static final boolean MW_PIP_LAUNCH_POLICY = true;
    public static final boolean MW_PIP_MENU_API = true;
    public static final boolean MW_PIP_MENU_BUG_FIX = true;
    public static final boolean MW_PIP_NAVI_ALPHA = true;
    public static final boolean MW_PIP_REMOTE_TRANSITION;
    public static final boolean MW_PIP_RESIZE = true;
    public static final boolean MW_PIP_ROTATION_BUG_FIX = true;
    public static final boolean MW_PIP_SA_LOGGING;
    public static final boolean MW_PIP_SECURE_FOLDER_POLICY = true;
    public static final boolean MW_PIP_SHELL_TRANSITION;
    public static final boolean MW_PIP_STASH = true;
    public static final boolean MW_PIP_SWIPE_TO_HOME = true;
    public static final boolean MW_PIP_SYNC_DRAW_HANDLER = true;
    public static final boolean MW_PIP_TO_SPLIT = true;
    public static final boolean MW_PRESERVE_WINDOW_BUG_FIX = true;
    public static final boolean MW_REMOTE_ROTATION_BUG_FIX = true;
    public static final boolean MW_RESUMED_AFFORDANCE_SHELL_TRANSITION;
    public static final boolean MW_ROUNDED_CORNER_BUG_FIX = true;
    public static final boolean MW_SA_LOGGING;
    public static final boolean MW_SA_RUNESTONE_LOGGING;
    public static final boolean MW_SEP_API = true;
    public static final boolean MW_SHELL_CHANGE_TRANSITION;
    public static final boolean MW_SHELL_COMMAND = true;
    public static final boolean MW_SHELL_DISPLAY_CHANGE_TRANSITION;
    public static final boolean MW_SHELL_FREEFORM_CAPTION_TYPE;
    public static final boolean MW_SHELL_FREEFORM_SHADOW_WITH_VIEW_ELEVATION;
    public static final boolean MW_SHELL_FREEFORM_TASK_POSITIONER;
    public static final boolean MW_SHELL_KEYBOARD_SHORTCUT = true;
    public static final boolean MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING;
    public static final boolean MW_SHELL_TASK_VIEW_TRANSITION = true;
    public static final boolean MW_SHELL_TRANSITION;
    public static final boolean MW_SHELL_TRANSITION_BUG_FIX;
    public static final boolean MW_SHELL_TRANSITION_LOG;
    public static final boolean MW_SINGLE_INSTANCE_PER_TASK_LAUNCH_POLICY = true;
    public static final boolean MW_SPLIT = true;
    public static final boolean MW_SPLIT_ACCESSIBILITY = true;
    public static final boolean MW_SPLIT_ACTIVITY = false;
    public static final boolean MW_SPLIT_ACTIVITY_ALLOW_LIST = false;
    public static final boolean MW_SPLIT_ACTIVITY_PACKAGE_ENABLED = false;
    public static final boolean MW_SPLIT_ADJACENT_SMART_TIP = false;
    public static final boolean MW_SPLIT_ADJUST_DIM_BOUNDS = true;
    public static final boolean MW_SPLIT_ADJUST_LAUNCH_ROOT_TASK = true;
    public static final boolean MW_SPLIT_API = true;
    public static final boolean MW_SPLIT_API_FOR_CHANGE_APP = true;
    public static final boolean MW_SPLIT_API_LAUNCH_ADJACENT_EXTENSION = true;
    public static final boolean MW_SPLIT_API_TO_HORIZONTAL_FLEX_MODE = true;
    public static final boolean MW_SPLIT_APP_PAIR = true;
    public static final boolean MW_SPLIT_APP_PAIR_SA_LOGGING;
    public static final boolean MW_SPLIT_BACKGROUND = true;
    public static final boolean MW_SPLIT_BOUNDS_POLICY = true;
    public static final boolean MW_SPLIT_BUG_FIX = true;
    public static final boolean MW_SPLIT_CHANGE_FOCUS_BY_TAB_KEY = true;
    public static final boolean MW_SPLIT_DISMISS_SNAPSHOT = true;
    public static final boolean MW_SPLIT_DIVIDER = true;
    public static final boolean MW_SPLIT_DIVIDER_HANDLE = true;
    public static final boolean MW_SPLIT_DIVIDER_HANDLE_MOUSE_OVER = true;
    public static final boolean MW_SPLIT_DIVIDER_PANEL = true;
    public static final boolean MW_SPLIT_DIVIDER_PANEL_FIRST_AUTO_OPEN = true;
    public static final boolean MW_SPLIT_DIVIDER_SA_LOGGING;
    public static final boolean MW_SPLIT_ENSURE_DOCK = true;
    public static final boolean MW_SPLIT_ENSURE_TASK_CHANGED_LISTENER = true;
    public static final boolean MW_SPLIT_FLEXIBLE_SNAP_ALGORITHM = true;
    public static final boolean MW_SPLIT_FLEX_MODE_SA_LOGGING;
    public static final boolean MW_SPLIT_FLEX_PANEL_FLOATING_ICON;
    public static final boolean MW_SPLIT_FLEX_PANEL_FLOATING_ICON_MOVABLE;
    public static final boolean MW_SPLIT_FLEX_PANEL_LAUNCH_POLICY;
    public static final boolean MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE;
    public static final boolean MW_SPLIT_FLEX_PANEL_MODE;
    public static final boolean MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING;
    public static final boolean MW_SPLIT_FLEX_PANEL_ORIENTATION_POLICY;
    public static final boolean MW_SPLIT_FLEX_PANEL_SYSTEMUI_VISIBILITY;
    public static final boolean MW_SPLIT_FLIP_FOLDING_POLICY = false;
    public static final boolean MW_SPLIT_FOCUS_ADJUSTMENT = true;
    public static final boolean MW_SPLIT_FULL_TO_SPLIT_BY_GESTURE = true;
    public static final boolean MW_SPLIT_FULL_TO_SPLIT_BY_GESTURE_SA_LOGGING;
    public static final boolean MW_SPLIT_IME_ANIMATION = true;
    public static final boolean MW_SPLIT_IMMERSIVE_MODE = true;
    public static final boolean MW_SPLIT_INHERIT_SOURCE_OR_FOCUSED_TASK_WINDOWING_MODE = true;
    public static final boolean MW_SPLIT_IS_FLEX_SCROLL_WHEEL;
    public static final boolean MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY;
    public static final boolean MW_SPLIT_LAUNCH_ADJACENT = true;
    public static final boolean MW_SPLIT_LAUNCH_ADJACENT_SA_LOGGING;
    public static final boolean MW_SPLIT_LAYOUT = true;
    public static final boolean MW_SPLIT_LAYOUT_CHANGED = true;
    public static final boolean MW_SPLIT_LETTERBOX_FOR_DISPLAY_CUTOUT = true;
    public static final boolean MW_SPLIT_NATURAL_RESIZING = true;
    public static final boolean MW_SPLIT_OVERCOME_TASK_ORGANIZER_UNSYNC_LIMITATION = true;
    public static final boolean MW_SPLIT_RECENT_TASKS = true;
    public static final boolean MW_SPLIT_RECENT_TASKS_SA_LOGGING;
    public static final boolean MW_SPLIT_REMOTE_APP_TRANSITION_LISTENER = true;
    public static final boolean MW_SPLIT_REMOTE_CONFIG_CHANGES = true;
    public static final boolean MW_SPLIT_REMOTE_SERVICE = true;
    public static final boolean MW_SPLIT_RESTORE_SPLIT_RATIO = true;
    public static final boolean MW_SPLIT_ROUNDED_CORNER = true;
    public static final boolean MW_SPLIT_SHELL_MANAGE_CHILD_IN_LIST = true;
    public static final boolean MW_SPLIT_SHELL_TRANSITION;
    public static final boolean MW_SPLIT_SHORTCUT_KEY = true;
    public static final boolean MW_SPLIT_SIP_ORDERING = true;
    public static final boolean MW_SPLIT_STACKING;
    public static final boolean MW_SPLIT_START_EDGE_ALL_APPS = true;
    public static final boolean MW_SPLIT_START_EDGE_ALL_APPS_SA_LOGGING;
    public static final boolean MW_SPLIT_STATE_SEND_INFO = true;
    public static final boolean MW_SPLIT_SUPPORT_POSITION_TOP_AND_RIGHT = true;
    public static final boolean MW_SPLIT_SYSTEMUI_VISIBILITY = true;
    public static final boolean MW_SPLIT_SYSTEM_BAR_OPACITY_POLICY = true;
    public static final boolean MW_SPLIT_SYSTEM_BAR_POLICY = true;
    public static final boolean MW_SPLIT_TASK_ORGANIZER_SYNC_TRANSACTION = true;
    public static final boolean MW_SPLIT_TASK_SNAPSHOT_CACHE_POLICY = true;
    public static final boolean MW_SPLIT_TASK_VISIBILITY = true;
    public static final boolean MW_SPLIT_TRANSPARENT_NAVIGATION_BAR;
    public static final boolean MW_SPLIT_WINDOW_CONFIG = true;
    public static final boolean MW_SPLIT_WM_TEST = true;
    private static final boolean MW_SUPPORT_3D_SURFACE_TRANSITION_FLAG;
    public static final boolean MW_SUPPORT_ASSISTANT_HOT_KEY = false;
    public static final boolean MW_SUPPORT_DRAG_AND_DROP_CAPTURED_BLUR;
    public static final boolean MW_SUPPORT_DRAG_AND_DROP_PARTIAL_BLUR;
    public static final boolean MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR;
    public static final boolean MW_SUPPORT_EDGE_AS_RECENTS = true;
    public static final boolean MW_SUPPORT_GAIN_FOCUS_TIME = true;
    public static final boolean MW_SUPPORT_POLICY = true;
    public static final boolean MW_SUPPORT_WINDOW_HANDLER = true;
    public static final boolean MW_SWITCH_DEVICE_WHEN_FOLD = false;
    public static final boolean MW_TASK_INFO_HAS_CONFIG_CHANGE = true;
    public static final boolean MW_TASK_POSITIONER_BUG_FIX = true;
    public static final boolean MW_TASK_SNAPSHOT_CONTROL = true;
    public static final boolean MW_TASK_VIEW_BUG_FIX = true;
    public static final boolean MW_TASK_VIEW_DISABLE_INPUT_SINK = true;
    public static final boolean MW_UPDATE_CONFIG_BASED_ON_TOP_TASK = true;
    public static final boolean MW_WCT_EXT = true;
    public static final boolean MW_WINDOWING_MODE_CHANGED_CALLBACK = true;
    public static final boolean MW_WINDOWLESS_WINDOW_BUG_FIX = true;
    public static final boolean MW_WINDOWLESS_WINDOW_DUMP = true;
    public static final boolean MW_WINDOWLESS_WINDOW_UI_AUTOMATION = true;
    public static final boolean ONE_UI_4_1;
    public static final boolean ONE_UI_4_1_1;
    public static final boolean ONE_UI_5_1;
    public static final boolean ONE_UI_5_1_1;
    public static final boolean ONE_UI_6_1;
    public static final boolean ONE_UI_6_1_1;
    public static final boolean PAGEBOOST_ENABLED = true;
    public static final String PERSIST_SYS_EMERGENCY_RESET = "persist.sys.emergency_reset";
    public static final boolean PMM_ENABLED = true;
    public static final boolean PPNANDSWAP_ENABLED = true;
    public static final String RESCUE_PARTY_EMERGENCY = "emergency";
    public static final boolean SAFE_DEBUG = false;
    public static final boolean SAMSUNG_DEX = true;
    public static final boolean SAMSUNG_DEX_SUPPORT_SYSTEMUI_FEATURE = true;
    private static final String SAMSUNG_LOG_PROP_NAME = "persist.log.semlevel";
    private static final String SAMSUNG_LOG_PROP_VALUE_DEFAULT = "0";
    private static final String SAMSUNG_LOG_PROP_VALUE_ON = "0xFFFFFFFF";
    public static final boolean SECONDARY_LAUNCHER_ACTIVITY_SUPPORT_FOR_DEX;
    public static final boolean SEM_DYNAMIC_FEATURE = true;
    public static final boolean SEP = true;
    public static final boolean SLOWDOWN_ENABLED = true;
    private static final Set<String> SUPPORTED_MODES;
    public static final boolean SUPPORT_ACTUAL_GPS = true;
    public static final boolean SUPPORT_APP_CRASH_ANR_HISTORY = true;
    public static final boolean SUPPORT_APP_JUMP_BLOCK;
    public static final boolean SUPPORT_BINDER_CACHING = true;
    public static final boolean SUPPORT_ESCROW_VAULT = true;
    public static final boolean SUPPORT_IAFD = true;
    public static final boolean SUPPORT_IQI;
    public static final boolean SUPPORT_JDM_MODEL_MANAGED_PROFILE_SCREENSHOT = false;
    public static final boolean SUPPORT_KNOX = true;
    public static final boolean SUPPORT_MARS = true;
    public static final boolean SUPPORT_OLAF = true;
    public static final boolean SUPPORT_PDS = true;
    public static final boolean SUPPORT_RLL = false;
    public static final boolean SUPPORT_SMARTMANAGER_CN;
    public static final boolean SUPPORT_SMART_CLIP = true;
    public static final boolean SUPPORT_TRAFFIC_MANAGER;
    public static final boolean SYSFW_ANR_PREDUMP_ENABLE = true;
    public static final boolean SYSFW_APP_SPEG;
    public static final boolean SYSFW_BINDERCALLS_STATS_PER_PACKAGE = true;
    public static final boolean SYSFW_OOM_HEAPDUMP_ENABLE = true;
    public static final boolean SYSHT_CONVERT_HIDDEN_TO_EMPTY = true;
    public static final boolean SYSHT_SUPPORT_LMKD = true;
    public static boolean SYSPERF_ACTIVE_APP_ADCP_ENABLE = false;
    public static final boolean SYSPERF_ACTIVE_APP_ADCP_WEBVIEW_PRELOAD_ENABLE = true;
    public static final boolean SYSPERF_ACTIVE_APP_BBA_ENABLE = true;
    public static final boolean SYSPERF_ACTIVE_APP_GVS_ENABLE;
    public static final boolean SYSPERF_ACTIVE_APP_LAUNCH = true;
    public static final boolean SYSPERF_ACTIVE_APP_RECORD_RECENTKILL_ENABLE = true;
    public static final boolean SYSPERF_ACTIVITY_MANAGER_PERFORMANCE = true;
    public static final boolean SYSPERF_ACTIVITY_MANAGER_SLUGGISH = true;
    public static final boolean SYSPERF_ADVANCED_ACTIVE_APP_LAUNCH = true;
    public static final boolean SYSPERF_ALWAYS_PRINT_SWAPPSS = true;
    public static final boolean SYSPERF_BIGDATA_SLUGGISH_SLUG_REPORT = true;
    public static final boolean SYSPERF_BIGDATA_SLUGGISH_TTLS_REPORT = true;
    public static final boolean SYSPERF_BOOST_DISABLE_WHEN_FOLDED;
    public static final boolean SYSPERF_BOOST_OPT;
    public static final boolean SYSPERF_BROADCAST_DELAY_ENABLE = true;
    public static final boolean SYSPERF_ENABLE_APP_TRANSITION_LOG = true;
    public static final boolean SYSPERF_JDM_MODEL;
    public static final boolean SYSPERF_LAUNCHER_PROMOTION;
    public static final String SYSPERF_PRODUCT_DEVICE;
    public static final boolean SYSPERF_QC_CHIPSET;
    public static final boolean SYSPERF_QC_IOP_V3_ENABLE;
    public static final boolean SYSPERF_QC_TASK_BOOST_ENABLE;
    public static final boolean SYSPERF_SAMSUNG_BOOST_ENBALE = true;
    public static final boolean SYSPERF_SDHMS_SEND_CMD = true;
    public static final boolean SYSPERF_SUPPORT_AM_CMD_BOOSTER = true;
    public static final boolean SYSPERF_SYSTEM_MAIN_THREAD_MONITOR_ENABLE = true;
    public static final boolean SYSPERF_TASKSNAPSHOT_HWBUFFER_CLEAR = true;
    public static final boolean SYSPERF_VI_BOOST;
    public static final boolean SYSTEM_AIMEMBOOST_ACCELERATE_USING_FCA_QUEUE = true;
    public static final boolean SYSTEM_FCA_FOR_GENIE = true;
    public static final boolean SYSTEM_FCA_ON_FREEZE = true;
    public static final boolean SYSTEM_MEMORY_GENIE_ENABLED = true;
    public static final boolean SYSUI_GRADLE_BUILD;
    public static final boolean UMR_ENABLED = true;
    private static String mCachedSamsungLogPropValue;
    private static CoreRune sInstance;
    public static final boolean IS_TABLET_DEVICE = BnRConstants.DEVICETYPE_TABLET.equals("phone");
    private static final String DEBUG_LEVEL = SystemProperties.get("ro.boot.debug_level");
    private static final String DEBUG_LEVEL_LOW = "0x4f4c";
    public static final boolean IS_DEBUG_LEVEL_LOW = DEBUG_LEVEL_LOW.equals(DEBUG_LEVEL);
    public static final boolean IS_DEBUG_LEVEL_MID = "0x494d".equals(DEBUG_LEVEL);
    private static final String DEBUG_LEVEL_HIGH = "0x4948";
    public static final boolean IS_DEBUG_LEVEL_HIGH = DEBUG_LEVEL_HIGH.equals(DEBUG_LEVEL);
    public static final boolean IS_FACTORY_BINARY = FactoryTest.isFactoryMode();

    private CoreRune() {}

    public static CoreRune getInstance() {
        if (sInstance == null) {
            sInstance = new CoreRune();
        }
        return sInstance;
    }

    static {
        ONE_UI_4_1 = Build.VERSION.SEM_PLATFORM_INT >= 130100;
        ONE_UI_4_1_1 = Build.VERSION.SEM_PLATFORM_INT >= 130500;
        ONE_UI_5_1 = Build.VERSION.SEM_PLATFORM_INT >= 140100;
        ONE_UI_5_1_1 = Build.VERSION.SEM_PLATFORM_INT >= 140500;
        ONE_UI_6_1 = Build.VERSION.SEM_PLATFORM_INT >= 150100;
        ONE_UI_6_1_1 = Build.VERSION.SEM_PLATFORM_INT >= 150500;
        IS_SHELL_TRANSITION_ENABLED =
                SystemProperties.getBoolean("persist.wm.debug.shell_transit", true);
        IS_PREDICTIVE_BACK_ANIM_ENABLED =
                SystemProperties.getBoolean("persist.wm.debug.predictive_back_anim", true);
        FW_SA_LOGGING =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
        FW_ROTATION_LOG = FW_SA_LOGGING;
        FW_SURFACE_DEBUG_CREATION =
                SystemProperties.getBoolean("persist.debug.surface.creation", false);
        FW_SURFACE_DEBUG_REMOVE =
                SystemProperties.getBoolean("persist.debug.surface.remove", false);
        FW_SURFACE_DEBUG_REPARENT =
                SystemProperties.getBoolean("persist.debug.surface.reparent", false);
        FW_SURFACE_DEBUG_ALPHA = SystemProperties.getBoolean("persist.debug.surface.alpha", false);
        FW_SURFACE_DEBUG_VISIBILITY =
                SystemProperties.getBoolean("persist.debug.surface.visibility", false);
        FW_SURFACE_DEBUG_TRANSFORM =
                SystemProperties.getBoolean("persist.debug.surface.transform", false);
        FW_SURFACE_DEBUG_LAYER = SystemProperties.getBoolean("persist.debug.surface.layer", false);
        FW_SURFACE_DEBUG_CROP = SystemProperties.getBoolean("persist.debug.surface.crop", false);
        FW_SURFACE_DEBUG_APPLY = SystemProperties.getBoolean("persist.debug.surface.apply", true);
        FW_WAIT_TO_HANDLE_RESIZING_FROM_CLIENT = IS_SHELL_TRANSITION_ENABLED;
        FW_MULTI_FOLD = false;
        FW_FLEXIBLE_TABLE_MODE = false;
        FW_FLEXIBLE_TENT_MODE = false;
        FW_FLEXIBLE_DUAL_MODE = false;
        FW_FLEXIBLE_CONTROL_FOLDING_SENSOR = false;
        FW_SA_LOGGING_FOR_HALF_OPEN_MODE = FW_FLEXIBLE_TABLE_MODE && FW_SA_LOGGING;
        FW_FOLD_SA_LOGGING = false;
        FW_FOLD_WALLPAPER_POLICY = false;
        FW_CHN_PREMIUM_WATCH =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_CLOCK_SUPPORT_PREMIUM_WATCH");
        FW_MULTI_FOLD_TESTING = FW_MULTI_FOLD;
        FW_FLIP_RESTORE_DEFAULT_DISPLAY_DENSITY_IN_BOOT = false;
        FW_FLEX_PANEL = FW_FLEXIBLE_TABLE_MODE;
        FW_SCREENSHOT_FROM_FLEX_PANEL = FW_FLEX_PANEL;
        FW_FLEX_PANEL_CONTROL = FW_FLEX_PANEL;
        FW_LARGE_FLIP_TRANSITION = false;
        FW_LARGE_FLIP_RECENTS_ANIM = false;
        FW_LARGE_FLIP_PREDICTIVE_BACK_ANIM = false;
        FW_LARGE_FLIP_LAUNCHER_WIDGET_POLICY_CHN = false;
        FW_FLIP_LARGE_COVER_SCREEN_SA_LOGGING = false;
        FW_TSP_STATE_CONTROLLER = !IS_FACTORY_BINARY;
        FW_TSP_SIP_MODE = FW_TSP_STATE_CONTROLLER;
        FW_TSP_DEADZONE =
                FW_TSP_STATE_CONTROLLER
                        && SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_TSP_STATE_MANAGER")
                                .contains("deadzone");
        FW_TSP_NOTE_MODE = FW_TSP_STATE_CONTROLLER;
        FW_TSP_DEADZONE_V3 =
                FW_TSP_STATE_CONTROLLER
                        && SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_TSP_STATE_MANAGER")
                                .contains("deadzone_v3");
        FW_USE_SMALLER_GRIPZONE_ON_GAME = FW_TSP_STATE_CONTROLLER && !IS_TABLET_DEVICE;
        FW_TSP_DEADZONEHOLE_LAND =
                FW_TSP_DEADZONE && "bsxasm1".equals(SystemProperties.get("ro.com.google.cdb.spa1"));
        FW_VRR_POLICY = Integer.parseInt("3") > 0;
        FW_VRR_SEAMLESS = FW_VRR_POLICY && !IS_FACTORY_BINARY && Integer.parseInt("3") > 1;
        FW_VRR_DISCRETE = Integer.parseInt("3") == 4;
        FW_VRR_REFRESH_RATE_MODE = FW_VRR_POLICY;
        FW_VRR_RESOLUTION_POLICY = FW_VRR_POLICY && "WQHD,FHD,HD".equals("WQHD,FHD,HD");
        FW_VRR_RESOLUTION_POLICY_FOR_SHELL_TRANSITION =
                FW_VRR_RESOLUTION_POLICY && IS_SHELL_TRANSITION_ENABLED;
        FW_VRR_FIXED_REFRESH_RATE_PACKAGE = FW_VRR_POLICY;
        FW_VRR_REFRESH_RATE_TOKEN = FW_VRR_POLICY;
        FW_VRR_SYSTEM_HISTORY = FW_VRR_POLICY;
        FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST =
                FW_VRR_SEAMLESS
                        && SystemProperties.getBoolean("persist.debug.hrr.block.enabled", true);
        FW_VRR_HRR_CHINA_DELTA =
                FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST
                        && "CHINA".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
        FW_VRR_LOW_REFRESH_RATE_LIST =
                FW_VRR_POLICY && SystemProperties.getBoolean("persist.debug.lrr.enabled", true);
        FW_VRR_NAVIGATION_LOW_REFRESH_RATE =
                FW_VRR_POLICY
                        && !IS_TABLET_DEVICE
                        && Integer.parseInt("3") == 1
                        && SystemProperties.getBoolean("persist.debug.lrr_navi.enabled", true);
        FW_VRR_FOR_SUB_DISPLAY = false;
        FW_VRR_IGNORE_RESTRICTED_RANGE = FW_VRR_POLICY;
        FW_VRR_PERFORMANCE = FW_VRR_POLICY;
        FW_VRR_SEND_TOUCH_HINT = !FW_VRR_DISCRETE;
        FW_DVRR_TOOLKIT_POLICY = FW_VRR_DISCRETE;
        FW_DVRR_TOOLKIT_POLICY_FOR_SCROLL = FW_DVRR_TOOLKIT_POLICY;
        FW_DVRR_TOOLKIT_REQUESTED_REFRESH_RATE = FW_DVRR_TOOLKIT_POLICY;
        FW_DVRR_TOOLKIT_FORCE_DEFAULT_NORMAL = FW_DVRR_TOOLKIT_POLICY;
        FW_DVRR_TOOLKIT_SUPPORT_HRR = FW_DVRR_TOOLKIT_POLICY && FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST;
        FW_DVRR_TOOLKIT_SUPPORT_HIGH_FRAME_RATE = FW_DVRR_TOOLKIT_POLICY;
        FW_DVRR_TOOLKIT_PRIORITIZE_HIGH_HINT = FW_DVRR_TOOLKIT_POLICY;
        FW_DVRR_TOOLKIT_PROLONG_TOUCH_BOOST = FW_DVRR_TOOLKIT_POLICY;
        FW_DVRR_TOOLKIT_BUG_FIX = FW_DVRR_TOOLKIT_POLICY;
        FW_SUPPORT_LOCK_TASK_MODE_BROADCAST =
                SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Framework_SupportBroadcastScreenPinning", false);
        FW_ALLOW_TOUCH_TO_KEYGUARD_WALLPAPER = ONE_UI_6_1;
        FW_NAVBAR_MOVABLE_POLICY = IS_TABLET_DEVICE;
        FW_NOTIFY_TASKBAR_VISIBLE = ONE_UI_4_1_1;
        FW_INSETS_LOG_DEBUG = SystemProperties.getBoolean("persist.wm.enable.insets_debug", false);
        FW_FLEX_MODE_APP_LIST = FW_FLEX_PANEL_CONTROL;
        FW_FLEX_PANEL_DEFAULT_LIST = FW_FLEX_PANEL_CONTROL;
        FW_SCREENSHOT_FOR_HDR =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_PHOTOHDR");
        FW_CUSTOM_BASIC_ANIM = SystemProperties.getBoolean("persist.wm.enable.custom.anim", true);
        FW_INSET_ANIM = FW_CUSTOM_BASIC_ANIM;
        FW_MINIMIZED_IME_INSET_ANIM =
                FW_INSET_ANIM
                        && SystemProperties.getBoolean(
                                "persist.wm.enable.minimized_ime.anim", false);
        FW_CUSTOM_BASIC_ANIM_WITH_DIM = FW_CUSTOM_BASIC_ANIM;
        FW_CUSTOM_DISPLAY_CHANGE_ANIM = FW_CUSTOM_BASIC_ANIM;
        FW_UI_MODE_ANIMATION = FW_CUSTOM_BASIC_ANIM;
        FW_BLACK_SNAPSHOT_TRANSITION = FW_CUSTOM_BASIC_ANIM;
        FW_REMOTE_WALLPAPER_ANIM = FW_CUSTOM_BASIC_ANIM;
        FW_EDGE_EXTENSION_ANIM_DEBUG =
                SystemProperties.getBoolean("persist.wm.edge.extension.debug", false);
        FW_PREDICTIVE_BACK_ANIM = FW_CUSTOM_BASIC_ANIM && IS_PREDICTIVE_BACK_ANIM_ENABLED;
        FW_PREDICTIVE_BACK_ANIM_BUG_FIX = FW_PREDICTIVE_BACK_ANIM;
        FW_SHELL_TRANSITION = FW_CUSTOM_BASIC_ANIM && IS_SHELL_TRANSITION_ENABLED;
        FW_SHELL_TRANSITION_WITH_DIM = FW_SHELL_TRANSITION && FW_CUSTOM_BASIC_ANIM_WITH_DIM;
        FW_SHELL_TRANSITION_DISPLAY_CHANGE = FW_SHELL_TRANSITION && FW_CUSTOM_DISPLAY_CHANGE_ANIM;
        FW_SHELL_TRANSITION_RESUMED_AFFORDANCE = FW_SHELL_TRANSITION;
        FW_SHELL_TRANSITION_BLACK_SNAPSHOT = FW_SHELL_TRANSITION && FW_BLACK_SNAPSHOT_TRANSITION;
        FW_SHELL_TRANSITION_REMOTE = FW_SHELL_TRANSITION;
        FW_SHELL_TRANSITION_MERGE =
                FW_SHELL_TRANSITION_REMOTE
                        && SystemProperties.getBoolean("persist.wm.enable.merge.transit", true);
        FW_SHELL_TRANSITION_MERGE_TRANSFER =
                FW_SHELL_TRANSITION_MERGE
                        && SystemProperties.getBoolean(
                                "persist.wm.enable.merge_transfer.transit", true);
        FW_SHELL_TRANSITION_SEPARATE_RECENTS = FW_SHELL_TRANSITION;
        FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY =
                FW_SHELL_TRANSITION_SEPARATE_RECENTS
                        && SystemProperties.getBoolean("persist.wm.transient_launch.overlay", true);
        FW_SHELL_TRANSITION_AOD_APPEAR = FW_SHELL_TRANSITION;
        FW_SHELL_TRANSITION_LOG = FW_SHELL_TRANSITION;
        FW_SHELL_TRANSITION_BUG_FIX = FW_SHELL_TRANSITION;
        FW_SHELL_TRANSITION_EXTENSION = FW_SHELL_TRANSITION_BUG_FIX;
        FW_SHELL_TRANSITION_RECENTS_BUG_FIX =
                FW_SHELL_TRANSITION_BUG_FIX && FW_SHELL_TRANSITION_SEPARATE_RECENTS;
        FW_SHELL_TRANSITION_ROTATED_WALLPAPER_SNAPSHOT = FW_SHELL_TRANSITION_BUG_FIX;
        FW_DYNAMIC_RESOLUTION_CONTROL = "WQHD,FHD,HD".equals("WQHD,FHD,HD");
        FW_LOW_TASK_SNAPSHOT_SCALE_FOR_TABLET = IS_TABLET_DEVICE;
        FW_DEVELOPMENT_DISABLE_STARTING_WINDOW =
                SystemProperties.getBoolean("persist.debug.disable.starting_window", false);
        FW_ALLOW_ALL_ROTATION = IS_TABLET_DEVICE;
        FW_AOD_FACE_WIDGET =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                        .contains("aodversion");
        MT_DND_ANIMATION = ONE_UI_4_1;
        MT_DND_DISABLE_CANCEL_ANIMATION = MT_DND_ANIMATION;
        MT_DND_SEAMLESS_ANIMATION = MT_DND_ANIMATION;
        MT_DND_OBJECT_CAPTURE = MT_DND_ANIMATION;
        FW_IMPROVED_MOVED_ANIMATION_FOR_IME =
                SystemProperties.getBoolean("persist.debug.improved.move.anim", true);
        FW_SPEN_HOVER =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION", -1)
                        > 0;
        FW_SUPPORT_APPLOCK =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME")
                        .equals("com.samsung.android.sm_cn");
        FW_APPLOCK = FW_SUPPORT_APPLOCK;
        FW_SCREEN_MODE_SETTING =
                SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_WIDE_COLOR_GAMUT")
                        || SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_NATURAL_SCREEN_MODE");
        FW_SUPPORT_NATIVE_AI =
                !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI");
        FW_CCM_BUG_FIX = FW_SUPPORT_NATIVE_AI;
        MW_SA_LOGGING = FW_SA_LOGGING;
        MW_SHELL_TRANSITION = IS_SHELL_TRANSITION_ENABLED;
        MW_SHELL_TRANSITION_BUG_FIX = MW_SHELL_TRANSITION;
        MW_SHELL_TRANSITION_LOG = MW_SHELL_TRANSITION;
        MW_SHELL_CHANGE_TRANSITION = MW_SHELL_TRANSITION;
        MW_SHELL_DISPLAY_CHANGE_TRANSITION = MW_SHELL_CHANGE_TRANSITION;
        MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING = MW_SA_LOGGING;
        MW_SUPPORT_3D_SURFACE_TRANSITION_FLAG =
                SemFloatingFeature.getInstance()
                        .getBoolean(
                                "SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        MW_SUPPORT_DRAG_AND_DROP_PARTIAL_BLUR =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_PARTIAL_BLUR");
        MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR =
                MW_SUPPORT_3D_SURFACE_TRANSITION_FLAG && MW_SUPPORT_DRAG_AND_DROP_PARTIAL_BLUR;
        MW_SUPPORT_DRAG_AND_DROP_CAPTURED_BLUR =
                !MW_SUPPORT_3D_SURFACE_TRANSITION_FLAG
                        && MW_SUPPORT_DRAG_AND_DROP_PARTIAL_BLUR
                        && SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
        MW_MULTI_SPLIT = "13".equals("0") || "23".equals("0");
        MW_MULTI_SPLIT_ROUNDED_CORNER = MW_MULTI_SPLIT;
        MW_SPLIT_FLEX_MODE_SA_LOGGING = FW_FLEXIBLE_TABLE_MODE;
        MW_SPLIT_FULL_TO_SPLIT_BY_GESTURE_SA_LOGGING = MW_SA_LOGGING;
        MW_SPLIT_SHELL_TRANSITION = MW_SHELL_TRANSITION;
        MW_SPLIT_STACKING = MW_SPLIT_SHELL_TRANSITION;
        MW_SA_RUNESTONE_LOGGING = MW_SA_LOGGING;
        MW_SPLIT_APP_PAIR_SA_LOGGING = MW_SA_LOGGING;
        MW_SPLIT_RECENT_TASKS_SA_LOGGING = MW_SA_LOGGING;
        MW_SPLIT_LAUNCH_ADJACENT_SA_LOGGING = MW_SA_LOGGING;
        MW_SPLIT_DIVIDER_SA_LOGGING = MW_SA_LOGGING;
        MW_SPLIT_FLEX_PANEL_MODE = FW_FLEX_PANEL;
        MW_SPLIT_FLEX_PANEL_LAUNCH_POLICY = MW_SPLIT_FLEX_PANEL_MODE;
        MW_SPLIT_FLEX_PANEL_ORIENTATION_POLICY = MW_SPLIT_FLEX_PANEL_MODE;
        MW_SPLIT_FLEX_PANEL_SYSTEMUI_VISIBILITY = MW_SPLIT_FLEX_PANEL_MODE;
        MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING = MW_SPLIT_FLEX_PANEL_MODE && MW_SA_LOGGING;
        MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE = MW_SPLIT_FLEX_PANEL_MODE;
        MW_SPLIT_FLEX_PANEL_FLOATING_ICON = MW_SPLIT_FLEX_PANEL_MODE;
        MW_SPLIT_FLEX_PANEL_FLOATING_ICON_MOVABLE = MW_SPLIT_FLEX_PANEL_FLOATING_ICON;
        MW_SPLIT_IS_FLEX_SCROLL_WHEEL = MW_SPLIT_FLEX_PANEL_MODE;
        MW_SPLIT_TRANSPARENT_NAVIGATION_BAR = !MW_MULTI_SPLIT;
        MW_SPLIT_LARGE_SCREEN_BOUNDS_POLICY = IS_TABLET_DEVICE;
        MW_SPLIT_START_EDGE_ALL_APPS_SA_LOGGING = MW_SA_LOGGING;
        MW_MULTI_SPLIT_SNAP_ALGORITHM = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY = false;
        MW_MULTI_SPLIT_FOR_COVER_DISPLAY = false;
        MW_MULTI_SPLIT_SHELL_TRANSITION = MW_MULTI_SPLIT && MW_SPLIT_SHELL_TRANSITION;
        MW_MULTI_SPLIT_CREATE_MODE = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_FREE_POSITION = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_FREE_POSITION_SA_LOGGING = MW_MULTI_SPLIT_FREE_POSITION && MW_SA_LOGGING;
        MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE = MW_MULTI_SPLIT_FREE_POSITION;
        MW_MULTI_SPLIT_BOUNDS_POLICY = MW_MULTI_SPLIT_FREE_POSITION;
        MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT =
                IS_TABLET_DEVICE && MW_MULTI_SPLIT_BOUNDS_POLICY;
        MW_MULTI_SPLIT_BACKGROUND = MW_MULTI_SPLIT_BOUNDS_POLICY;
        MW_MULTI_SPLIT_TASK_ORGANIZER = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_TASK_VISIBILITY = MW_MULTI_SPLIT_TASK_ORGANIZER;
        MW_MULTI_SPLIT_APP_PAIR = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_DIVIDER = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD = false;
        MW_MULTI_SPLIT_CELL_DIVIDER = MW_MULTI_SPLIT_DIVIDER;
        MW_MULTI_SPLIT_NATURAL_RESIZING = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_RECENT_TASKS = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_SHELL_DUMP = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_FOLDING_POLICY = false;
        MW_MULTI_SPLIT_APP_PAIR_FOLDING_POLICY =
                MW_MULTI_SPLIT_APP_PAIR && MW_MULTI_SPLIT_FOLDING_POLICY;
        MW_MULTI_SPLIT_SHOW_INCALL_WHEN_FOLDING = MW_MULTI_SPLIT_FOLDING_POLICY;
        MW_MULTI_SPLIT_ENSURE_APP_SIZE = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_ADJUST_FOR_IME = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_ACCESSIBILITY = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_LAUNCH_ADJACENT = MW_MULTI_SPLIT;
        MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY = MW_MULTI_SPLIT && MW_MULTI_SPLIT_FOR_COVER_DISPLAY;
        MW_RESUMED_AFFORDANCE_SHELL_TRANSITION = FW_SHELL_TRANSITION_RESUMED_AFFORDANCE;
        MW_CAPTION_SHELL = SystemProperties.getBoolean("captiononshell.debug", true);
        MW_FREEFORM_HEADER_TYPE_SHELL_TRANSITION = MW_SHELL_TRANSITION;
        MW_FREEFORM_HEADER_TYPE_SA_LOGGING = MW_SA_LOGGING;
        MW_FREEFORM_SHELL_TRANSITION = MW_SHELL_TRANSITION;
        MW_FREEFORM_MINIMIZE_SA_LOGGING = MW_SA_LOGGING;
        MW_FREEFORM_MINIMIZE_SHELL_TRANSITION = MW_FREEFORM_SHELL_TRANSITION;
        MW_FREEFORM_MINIMIZE_CONTAINER = MW_FREEFORM_MINIMIZE_SHELL_TRANSITION;
        MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW = MW_FREEFORM_MINIMIZE_CONTAINER;
        MW_FREEFORM_SMART_POPUP_VIEW = MW_FREEFORM_MINIMIZE_CONTAINER;
        MW_FREEFORM_SMART_POPUP_VIEW_SA_LOGGING = MW_FREEFORM_SMART_POPUP_VIEW && MW_SA_LOGGING;
        MW_DND_FREEFORM_DISMISS_VIEW = MW_FREEFORM_MINIMIZE_CONTAINER;
        MW_FREEFORM_MINIMIZED_PREVIEW = MW_FREEFORM_MINIMIZE_CONTAINER;
        MW_FREEFORM_FORCE_HIDING_TRANSITION = MW_FREEFORM_SHELL_TRANSITION;
        MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY = IS_TABLET_DEVICE;
        MW_FREEFORM_CORNER_GESTURE_SA_LOGGING = MW_SA_LOGGING;
        MW_CAPTION_SHELL_BUG_FIX = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_INSETS =
                MW_CAPTION_SHELL
                        && SystemProperties.getBoolean("persist.debug.caption.insets", true);
        MW_CAPTION_SHELL_DEBUG = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_SUPPORT_WINDOW_OPACITY =
                MW_CAPTION_SHELL
                        && (SystemProperties.get("ro.surface_flinger.protected_contents", "")
                                        .contains("true")
                                || SystemProperties.get("ro.surface_flinger.protected_contents", "")
                                        .contains("1"));
        MW_CAPTION_SHELL_POPUP = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_POPUP_HELP = MW_CAPTION_SHELL_POPUP;
        MW_CAPTION_SHELL_DEX = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_FREEFORM_PINNING = MW_CAPTION_SHELL_DEX;
        MW_CAPTION_SHELL_KEEP_SCREEN_ON = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_IMMERSIVE_MODE = MW_CAPTION_SHELL && MW_CAPTION_SHELL_DEX;
        MW_CAPTION_SHELL_OVERFLOW_MENU = MW_CAPTION_SHELL && MW_CAPTION_SHELL_DEX;
        MW_CAPTION_SHELL_FULL_SCREEN = MW_CAPTION_SHELL && IS_TABLET_DEVICE;
        MW_CAPTION_SHELL_SHADOW_ANIM = MW_CAPTION_SHELL;
        MW_SHELL_FREEFORM_CAPTION_TYPE = MW_CAPTION_SHELL;
        MW_SHELL_FREEFORM_SHADOW_WITH_VIEW_ELEVATION = MW_CAPTION_SHELL;
        MW_SHELL_FREEFORM_TASK_POSITIONER = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_FREEFORM_MOTION =
                MW_SHELL_FREEFORM_TASK_POSITIONER && MW_FREEFORM_SHELL_TRANSITION;
        MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_FREEFORM_RESIZE_GESTURE = MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW;
        MW_CAPTION_SHELL_FREEFORM_RESIZE_GESTURE_SA_LOGGING =
                MW_CAPTION_SHELL_FREEFORM_RESIZE_GESTURE && MW_SA_LOGGING;
        MW_FREEFORM_RESIZE_TOUCHABLE_REGION = MW_CAPTION_SHELL_FREEFORM_RESIZE_VIEW;
        MW_CAPTION_SHELL_HANDLE_VIEW = MW_CAPTION_SHELL;
        MW_CAPTION_SHELL_CUSTOMIZABLE_WINDOW_HEADERS = MW_CAPTION_SHELL_INSETS;
        MW_PIP_SHELL_TRANSITION = MW_SHELL_TRANSITION;
        MW_PIP_REMOTE_TRANSITION = MW_PIP_SHELL_TRANSITION;
        MW_PIP_DISABLE_ROUNDED_CORNER =
                (SystemProperties.get("ro.surface_flinger.protected_contents", "").contains("true")
                                || SystemProperties.get("ro.surface_flinger.protected_contents", "")
                                        .contains("1"))
                        ? false
                        : true;
        MW_PIP_SA_LOGGING = MW_SA_LOGGING;
        MW_NATURAL_SWITCHING_FULLSCREEN = ONE_UI_6_1_1;
        MW_NATURAL_SWITCHING_PIP = ONE_UI_6_1_1;
        MW_NATURAL_SWITCHING_MULTI_SPLIT = MW_MULTI_SPLIT;
        MW_NATURAL_SWITCHING_SA_LOGGING = MW_SA_LOGGING;
        MW_DND_SA_LOGGING = MW_SA_LOGGING;
        MW_DND_UNHANDLED_DRAG = MW_CAPTION_SHELL_CUSTOMIZABLE_WINDOW_HEADERS;
        MW_DND_MULTI_SPLIT_DROP_TARGET = MW_MULTI_SPLIT;
        MW_EMBED_ACTIVITY = IS_TABLET_DEVICE;
        MW_EMBED_ACTIVITY_PACKAGE_ENABLED = MW_EMBED_ACTIVITY;
        MW_EMBED_ACTIVITY_SYSTEM_BAR_POLICY = MW_EMBED_ACTIVITY;
        MW_EMBED_ACTIVITY_MODE = MW_EMBED_ACTIVITY;
        MW_EMBED_ACTIVITY_ANIMATION = MW_EMBED_ACTIVITY;
        MW_EMBED_ACTIVITY_DEBUG_LOG = false;
        MD_DEX_SA_LOGGING = FW_SA_LOGGING;
        MD_DEX_SHELL_TRANSITION = MW_SHELL_TRANSITION;
        MD_DEX_MINIMIZE_SHELL_TRANSITION = MD_DEX_SHELL_TRANSITION;
        MW_CAPTION_SHELL_DEX_SNAPPING_WINDOW = MW_SHELL_FREEFORM_TASK_POSITIONER;
        MD_DEX_NOT_SUPPORT_CUTOUT = IS_TABLET_DEVICE;
        SUPPORTED_MODES =
                Collections.unmodifiableSet(
                        new ArraySet(
                                Arrays.asList(
                                        SemFloatingFeature.getInstance()
                                                .getString(
                                                        "SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE")
                                                .split(","))));
        MD_DEX_WIRELESS = SUPPORTED_MODES.contains(AudioDeviceDescription.CONNECTION_WIRELESS);
        MD_DEX_SUPPORT_STANDALONE =
                IS_TABLET_DEVICE && true && SUPPORTED_MODES.contains("standalone");
        MD_DEX_COMPAT_CAPTION_SHELL = MW_CAPTION_SHELL_DEX;
        MD_FORCE_ENABLE_DESKTOP_MODE =
                SystemProperties.getBoolean("persist.wm.debug.force_enable_desktop_mode", false);
        MT_NEW_DEX = MD_DEX_SUPPORT_STANDALONE;
        MD_DEX_STANDALONE_LAUNCH_POLICY = MT_NEW_DEX;
        MT_NEW_DEX_LAUNCH_POLICY = MT_NEW_DEX;
        MT_NEW_DEX_TASK_ORDERING = MT_NEW_DEX;
        MT_NEW_DEX_SHELL_TRANSITION = MT_NEW_DEX && MW_SHELL_TRANSITION;
        MT_DEX_SIZE_COMPAT_MODE = MT_NEW_DEX;
        MT_DEX_SIZE_COMPAT_DRAG = MT_DEX_SIZE_COMPAT_MODE;
        MT_NEW_DEX_BOUNDS_POLICY = MT_NEW_DEX;
        MT_NEW_DEX_PERSIST_BOUNDS = MT_NEW_DEX_BOUNDS_POLICY;
        MT_NEW_DEX_LIMIT_RUNNING_APPS = MT_NEW_DEX;
        MT_NEW_DEX_TASK_PINNING = MT_NEW_DEX;
        MT_NEW_DEX_RESUMED_AFFORDANCE_ANIMATION =
                MT_NEW_DEX && MW_RESUMED_AFFORDANCE_SHELL_TRANSITION;
        MT_NEW_DEX_PIP = MT_NEW_DEX;
        MT_NEW_DEX_PIP_ON_FREEFORM =
                MT_NEW_DEX_PIP && SystemProperties.getBoolean("persist.debug.pip.newdex", false);
        MT_NEW_DEX_CONFIG_MANAGEMENT = MT_NEW_DEX;
        MW_CAPTION_SHELL_NEW_DEX = MW_CAPTION_SHELL && MT_NEW_DEX;
        MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE = MW_CAPTION_SHELL_NEW_DEX;
        MT_APP_COMPAT_ASPECT_RATIO_POLICY = IS_TABLET_DEVICE;
        MT_APP_COMPAT_MIN_ASPECT_RATIO_LIST = false;
        MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS = IS_TABLET_DEVICE;
        MT_APP_COMPAT_ORIENTATION_POLICY = MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
        MT_APP_COMPAT_ROTATION_COMPAT_MODE =
                MT_APP_COMPAT_ORIENTATION_POLICY && MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
        MT_APP_COMPAT_STATUS_LOGGING = FW_SA_LOGGING;
        MT_APP_COMPAT_LARGE_SCREEN = MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
        MT_APP_COMPAT_TRANSPARENT_POLICY = MT_APP_COMPAT_LARGE_SCREEN;
        MT_APP_COMPAT_CAMERA_POLICY = MT_APP_COMPAT_LARGE_SCREEN;
        MT_APP_COMPAT_CONFIGURATION = MT_APP_COMPAT_LARGE_SCREEN;
        MT_SIZE_COMPAT_POLICY = MT_DEX_SIZE_COMPAT_MODE;
        MT_SIZE_COMPAT_POLICY_DRAG = MT_SIZE_COMPAT_POLICY && MT_DEX_SIZE_COMPAT_DRAG;
        MT_SIZE_COMPAT_POLICY_COORDINATION = IS_TABLET_DEVICE && MT_DEX_SIZE_COMPAT_MODE;
        FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT = IS_TABLET_DEVICE;
        FW_CUSTOM_LETTERBOX = MT_APP_COMPAT_CONFIGURATION;
        FW_ORIENTATION_CONTROL = MT_APP_COMPAT_ORIENTATION_POLICY;
        FW_ORIENTATION_CONTROL_DEFAULT_ENABLED = MT_APP_COMPAT_ORIENTATION_POLICY;
        MW_MULTISTAR_MULTI_SPLIT_GUIDE_VIEW = MW_MULTI_SPLIT;
        FW_TRIM_MEMORY_LOG = Build.IS_ENG;
        FW_DEDICATED_MEMORY = Process.getTotalMemory() / 1048576 > 6144;
        FW_WINDOW_BLUR_SUPPORTED =
                SemFloatingFeature.getInstance()
                        .getBoolean(
                                "SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        FW_STACKED_BLUR_SUPPORTED = FW_WINDOW_BLUR_SUPPORTED;
        SUPPORT_APP_JUMP_BLOCK =
                SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal");
        SYSPERF_PRODUCT_DEVICE = SystemProperties.get("ro.product.device");
        SYSPERF_BOOST_DISABLE_WHEN_FOLDED =
                "b2q".equalsIgnoreCase(SYSPERF_PRODUCT_DEVICE)
                        || "b4q".equalsIgnoreCase(SYSPERF_PRODUCT_DEVICE)
                        || "b5q".equalsIgnoreCase(SYSPERF_PRODUCT_DEVICE)
                        || "b6q".equalsIgnoreCase(SYSPERF_PRODUCT_DEVICE);
        SYSPERF_QC_CHIPSET = "qcom".equalsIgnoreCase(SystemProperties.get("ro.hardware"));
        SYSPERF_QC_TASK_BOOST_ENABLE = SYSPERF_QC_CHIPSET;
        SYSPERF_QC_IOP_V3_ENABLE = SYSPERF_QC_CHIPSET;
        SYSPERF_JDM_MODEL = "jdm".equalsIgnoreCase("in_house");
        SYSPERF_BOOST_OPT = SystemProperties.getBoolean("sys.perf.boostopt", false);
        SYSPERF_VI_BOOST = SystemProperties.getBoolean("sys.perf.viboost", false);
        SYSPERF_LAUNCHER_PROMOTION =
                !SystemProperties.getBoolean("debug.sf.enable_adpf_cpu_hint", false);
        ALLIED_PROC_PROTECTION_LMKD =
                "true".equals(SystemProperties.get("ro.slmk.allied_proc_protect", "false"));
        SYSFW_APP_SPEG = !SystemProperties.getBoolean("com.samsung.speg.disable", false);
        SYSUI_GRADLE_BUILD = !Build.IS_USER;
        SYSPERF_ACTIVE_APP_ADCP_ENABLE = true;
        SYSPERF_ACTIVE_APP_GVS_ENABLE =
                SystemProperties.get("ro.csc.country_code").equalsIgnoreCase("CHINA");
        FAST_MADVISE_ENABLED =
                SystemProperties.get("ro.csc.country_code").equalsIgnoreCase("CHINA")
                        && SystemProperties.get("ro.product.model").startsWith("SM-S91");
        SUPPORT_IQI = false;
        MNO_TMO_DEVICE_REPORTING =
                "TMB".equals(SystemProperties.get("ro.csc.sales_code"))
                        || "TMK".equals(SystemProperties.get("ro.csc.sales_code"));
        GFW_DEBUG_DISABLE_HWRENDERING =
                SystemProperties.getBoolean("debug.skia.force_sw_gles", false);
        SUPPORT_TRAFFIC_MANAGER =
                SemCscFeature.getInstance()
                        .getString("CscFeature_SmartManager_ConfigSubFeatures")
                        .contains("trafficmanager");
        BAIDU_CARLIFE =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_CARLIFE");
        CARLIFE_DISPLAY_GROUP = BAIDU_CARLIFE;
        CARLIFE_NAVBAR = BAIDU_CARLIFE;
        DIRECT_WRITING =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION")
                        > 0;
        SECONDARY_LAUNCHER_ACTIVITY_SUPPORT_FOR_DEX = Build.IS_ENG;
        SUPPORT_SMARTMANAGER_CN =
                "com.samsung.android.sm_cn"
                        .equals(
                                SemFloatingFeature.getInstance()
                                        .getString(
                                                "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME"));
        BIXBY_TOUCH =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BIXBY_TOUCH");
        FW_SUPPORT_ONE_TOUCH = "true".equals(SystemProperties.get("ro.otch.support.entrance"));
        GRAPHICS_RENDER_ENGINE_POLICY =
                SystemProperties.getBoolean("debug.graphics.render.engine.policy", true);
    }

    public static boolean isSamsungLogEnabled() {
        if (mCachedSamsungLogPropValue == null) {
            mCachedSamsungLogPropValue = SystemProperties.get(SAMSUNG_LOG_PROP_NAME, "0");
        }
        return SAMSUNG_LOG_PROP_VALUE_ON.equals(mCachedSamsungLogPropValue);
    }
}
