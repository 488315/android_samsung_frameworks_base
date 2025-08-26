package com.android.internal.hidden_from_bootclasspath.com.android.window.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ACTION_MODE_EDGE_TO_EDGE, Flags.FLAG_ACTIVITY_EMBEDDING_ANIMATION_CUSTOMIZATION_FLAG, Flags.FLAG_ACTIVITY_EMBEDDING_DELAY_TASK_FRAGMENT_FINISH_FOR_ACTIVITY_LAUNCH, Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG, Flags.FLAG_ACTIVITY_EMBEDDING_METRICS, Flags.FLAG_ACTIVITY_EMBEDDING_SUPPORT_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK, Flags.FLAG_ALLOW_HIDE_SCM_BUTTON, Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT, Flags.FLAG_ALWAYS_DRAW_MAGNIFICATION_FULLSCREEN_BORDER, Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION, Flags.FLAG_AOD_TRANSITION, Flags.FLAG_APP_COMPAT_ASYNC_RELAYOUT, Flags.FLAG_APP_COMPAT_PROPERTIES_API, Flags.FLAG_APP_COMPAT_REFACTORING, Flags.FLAG_APP_COMPAT_UI_FRAMEWORK, Flags.FLAG_APP_HANDLE_NO_RELAYOUT_ON_EXCLUSION_CHANGE, Flags.FLAG_APPLY_LIFECYCLE_ON_PIP_CHANGE, Flags.FLAG_AVOID_REBINDING_INTENTIONALLY_DISCONNECTED_WALLPAPER, Flags.FLAG_BACKUP_AND_RESTORE_FOR_USER_ASPECT_RATIO_SETTINGS, Flags.FLAG_BAL_ADDITIONAL_LOGGING, Flags.FLAG_BAL_ADDITIONAL_START_MODES, Flags.FLAG_BAL_CLEAR_ALLOWLIST_DURATION, Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG, Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK, Flags.FLAG_BAL_IMPROVED_METRICS, Flags.FLAG_BAL_REDUCE_GRACE_PERIOD, Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR, Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID, Flags.FLAG_BAL_SEND_INTENT_WITH_OPTIONS, Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED, Flags.FLAG_BAL_STRICT_MODE_GRACE_PERIOD, Flags.FLAG_BAL_STRICT_MODE_RO, Flags.FLAG_BETTER_SUPPORT_NON_MATCH_PARENT_ACTIVITY, Flags.FLAG_CACHE_WINDOW_STYLE, Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM, Flags.FLAG_CAMERA_COMPAT_FULLSCREEN_PICK_SAME_TASK_ACTIVITY, Flags.FLAG_CHECK_DISABLED_SNAPSHOTS_IN_TASK_PERSISTER, Flags.FLAG_CLEANUP_DISPATCH_PENDING_TRANSACTIONS_REMOTE_EXCEPTION, Flags.FLAG_CLEAR_SYSTEM_VIBRATOR, Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR, Flags.FLAG_CONDENSE_CONFIGURATION_CHANGE_FOR_SIMPLE_MODE, Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT, Flags.FLAG_COVER_DISPLAY_OPT_IN, Flags.FLAG_DELAY_NOTIFICATION_TO_MAGNIFICATION_WHEN_RECENTS_WINDOW_TO_FRONT_TRANSITION, Flags.FLAG_DELEGATE_BACK_GESTURE_TO_SHELL, Flags.FLAG_DELEGATE_UNHANDLED_DRAGS, Flags.FLAG_DELETE_CAPTURE_DISPLAY, Flags.FLAG_DENSITY_390_API, Flags.FLAG_DISABLE_DESKTOP_LAUNCH_PARAMS_OUTSIDE_DESKTOP_BUG_FIX, Flags.FLAG_DISABLE_NON_RESIZABLE_APP_SNAP_RESIZING, Flags.FLAG_DISABLE_OPT_OUT_EDGE_TO_EDGE, Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS, Flags.FLAG_EARLY_LAUNCH_HINT, Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT, Flags.FLAG_ENABLE_ACCESSIBLE_CUSTOM_HEADERS, Flags.FLAG_ENABLE_ACTIVITY_EMBEDDING_SUPPORT_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_APP_HEADER_WITH_TASK_DENSITY, Flags.FLAG_ENABLE_BORDER_SETTINGS, Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY, Flags.FLAG_ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY, Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING, Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING_OPT_OUT, Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING_OPT_OUT_API, Flags.FLAG_ENABLE_CAMERA_COMPAT_TRACK_TASK_AND_APP_BUGFIX, Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_CONVERSION, Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION, Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS, Flags.FLAG_ENABLE_CASCADING_WINDOWS, Flags.FLAG_ENABLE_COMPAT_UI_VISIBILITY_STATUS, Flags.FLAG_ENABLE_COMPATUI_SYSUI_LAUNCHER, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_DND, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_PIP, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_WINDOW_DRAG, Flags.FLAG_ENABLE_DESKTOP_APP_HANDLE_ANIMATION, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_ALTTAB_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_ALTTAB_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_CLOSE_SHORTCUT_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_CLOSE_TASK_ANIMATION_IN_DTC_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_IME_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_IMMERSIVE_DRAG_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_INDICATOR_IN_SEPARATE_THREAD_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_MODE_THROUGH_DEV_OPTION, Flags.FLAG_ENABLE_DESKTOP_OPENING_DEEPLINK_MINIMIZE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_SWIPE_BACK_MINIMIZE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_SYSTEM_DIALOGS_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_TAB_TEARING_MINIMIZE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_TASKBAR_ON_FREEFORM_DISPLAYS, Flags.FLAG_ENABLE_DESKTOP_TRAMPOLINE_CLOSE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_HANDLE_EDUCATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB_EDUCATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB_EDUCATION_INTEGRATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_ENTER_TRANSITION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_ENTER_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_BY_MINIMIZE_TRANSITION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_HSUM, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODALS_POLICY, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MULTI_INSTANCE_FEATURES, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_PERSISTENCE, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_PIP, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SCVH_CACHE_BUG_FIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASK_LIMIT, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY, Flags.FLAG_ENABLE_DEVICE_STATE_AUTO_ROTATE_SETTING_LOGGING, Flags.FLAG_ENABLE_DEVICE_STATE_AUTO_ROTATE_SETTING_REFACTOR, Flags.FLAG_ENABLE_DISPLAY_DISCONNECT_INTERACTION, Flags.FLAG_ENABLE_DISPLAY_FOCUS_IN_SHELL_TRANSITIONS, Flags.FLAG_ENABLE_DISPLAY_RECONNECT_INTERACTION, Flags.FLAG_ENABLE_DISPLAY_WINDOWING_MODE_SWITCHING, Flags.FLAG_ENABLE_DRAG_RESIZE_SET_UP_IN_BG_THREAD, Flags.FLAG_ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DRAG_TO_MAXIMIZE, Flags.FLAG_ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX, Flags.FLAG_ENABLE_FULL_SCREEN_WINDOW_ON_REMOVING_SPLIT_SCREEN_STAGE_BUGFIX, Flags.FLAG_ENABLE_FULLY_IMMERSIVE_IN_DESKTOP, Flags.FLAG_ENABLE_HANDLE_INPUT_FIX, Flags.FLAG_ENABLE_HOLD_TO_DRAG_APP_HANDLE, Flags.FLAG_ENABLE_INDEPENDENT_BACK_IN_PROJECTED, Flags.FLAG_ENABLE_INPUT_LAYER_TRANSITION_FIX, Flags.FLAG_ENABLE_MINIMIZE_BUTTON, Flags.FLAG_ENABLE_MODALS_FULLSCREEN_WITH_PERMISSION, Flags.FLAG_ENABLE_MODALS_FULLSCREEN_WITH_PLATFORM_SIGNATURE, Flags.FLAG_ENABLE_MOVE_TO_NEXT_DISPLAY_SHORTCUT, Flags.FLAG_ENABLE_MULTI_DISPLAY_SPLIT, Flags.FLAG_ENABLE_MULTIDISPLAY_TRACKPAD_BACK_GESTURE, Flags.FLAG_ENABLE_MULTIPLE_DESKTOPS_BACKEND, Flags.FLAG_ENABLE_MULTIPLE_DESKTOPS_FRONTEND, Flags.FLAG_ENABLE_NON_DEFAULT_DISPLAY_SPLIT, Flags.FLAG_ENABLE_OPAQUE_BACKGROUND_FOR_TRANSPARENT_WINDOWS, Flags.FLAG_ENABLE_PER_DISPLAY_DESKTOP_WALLPAPER_ACTIVITY, Flags.FLAG_ENABLE_PER_DISPLAY_PACKAGE_CONTEXT_CACHE_IN_STATUSBAR_NOTIF, Flags.FLAG_ENABLE_PERSISTING_DISPLAY_SIZE_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_PRESENTATION_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_PROJECTED_DISPLAY_DESKTOP_MODE, Flags.FLAG_ENABLE_QUICKSWITCH_DESKTOP_SPLIT_BUGFIX, Flags.FLAG_ENABLE_REQUEST_FULLSCREEN_BUGFIX, Flags.FLAG_ENABLE_RESIZING_METRICS, Flags.FLAG_ENABLE_RESTART_MENU_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_RESTORE_TO_PREVIOUS_SIZE_FROM_DESKTOP_IMMERSIVE, Flags.FLAG_ENABLE_SHELL_INITIAL_BOUNDS_REGRESSION_BUG_FIX, Flags.FLAG_ENABLE_SIZE_COMPAT_MODE_IMPROVEMENTS_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_START_LAUNCH_TRANSITION_FROM_TASKBAR_BUGFIX, Flags.FLAG_ENABLE_TASK_RESIZING_KEYBOARD_SHORTCUTS, Flags.FLAG_ENABLE_TASK_STACK_OBSERVER_IN_SHELL, Flags.FLAG_ENABLE_TASKBAR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_TASKBAR_OVERFLOW, Flags.FLAG_ENABLE_TASKBAR_RECENTS_LAYOUT_TRANSITION, Flags.FLAG_ENABLE_THEMED_APP_HEADERS, Flags.FLAG_ENABLE_TILE_RESIZING, Flags.FLAG_ENABLE_TOP_VISIBLE_ROOT_TASK_PER_USER_TRACKING, Flags.FLAG_ENABLE_VISUAL_INDICATOR_IN_TRANSITION_BUGFIX, Flags.FLAG_ENABLE_WINDOW_CONTEXT_RESOURCES_UPDATE_ON_CONFIG_CHANGE, Flags.FLAG_ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS, Flags.FLAG_ENABLE_WINDOWING_EDGE_DRAG_RESIZE, Flags.FLAG_ENABLE_WINDOWING_SCALED_RESIZING, Flags.FLAG_ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS, Flags.FLAG_ENFORCE_EDGE_TO_EDGE, Flags.FLAG_ENSURE_KEYGUARD_DOES_TRANSITION_STARTING, Flags.FLAG_ENSURE_WALLPAPER_IN_TRANSITIONS, Flags.FLAG_ENSURE_WALLPAPER_IN_WEAR_TRANSITIONS, Flags.FLAG_ENTER_DESKTOP_BY_DEFAULT_ON_FREEFORM_DISPLAYS, Flags.FLAG_EXCLUDE_CAPTION_FROM_APP_BOUNDS, Flags.FLAG_EXCLUDE_DRAWING_APP_THEME_SNAPSHOT_FROM_LOCK, Flags.FLAG_EXCLUDE_TASK_FROM_RECENTS, Flags.FLAG_FIFO_PRIORITY_FOR_MAJOR_UI_PROCESSES, Flags.FLAG_FIX_HIDE_OVERLAY_API, Flags.FLAG_FIX_LAYOUT_EXISTING_TASK, Flags.FLAG_FIX_VIEW_ROOT_CALL_TRACE, Flags.FLAG_FORCE_CLOSE_TOP_TRANSPARENT_FULLSCREEN_TASK, Flags.FLAG_FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH, Flags.FLAG_GET_DIMMER_ON_CLOSING, Flags.FLAG_IGNORE_ASPECT_RATIO_RESTRICTIONS_FOR_RESIZEABLE_FREEFORM_ACTIVITIES, Flags.FLAG_IGNORE_CORNER_RADIUS_AND_SHADOWS, Flags.FLAG_INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC, Flags.FLAG_INHERIT_TASK_BOUNDS_FOR_TRAMPOLINE_TASK_LAUNCHES, Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION, Flags.FLAG_JANK_API, Flags.FLAG_KEYBOARD_SHORTCUTS_TO_SWITCH_DESKS, Flags.FLAG_KEYGUARD_GOING_AWAY_TIMEOUT, Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER, Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION, Flags.FLAG_MOVE_TO_EXTERNAL_DISPLAY_SHORTCUT, Flags.FLAG_MULTI_CROP, Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT, Flags.FLAG_NESTED_TASKS_WITH_INDEPENDENT_BOUNDS, Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS, Flags.FLAG_NO_DUPLICATE_SURFACE_DESTROYED_EVENTS, Flags.FLAG_NO_VISIBILITY_EVENT_ON_DISPLAY_STATE_CHANGE, Flags.FLAG_OFFLOAD_COLOR_EXTRACTION, Flags.FLAG_PORT_WINDOW_SIZE_ANIMATION, Flags.FLAG_PREDICTIVE_BACK_DEFAULT_ENABLE_SDK_36, Flags.FLAG_PREDICTIVE_BACK_PRIORITY_SYSTEM_NAVIGATION_OBSERVER, Flags.FLAG_PREDICTIVE_BACK_SWIPE_EDGE_NONE_API, Flags.FLAG_PREDICTIVE_BACK_SYSTEM_OVERRIDE_CALLBACK, Flags.FLAG_PREDICTIVE_BACK_THREE_BUTTON_NAV, Flags.FLAG_PREDICTIVE_BACK_TIMESTAMP_API, Flags.FLAG_PROCESS_PRIORITY_POLICY_FOR_MULTI_WINDOW_MODE, Flags.FLAG_REAR_DISPLAY_DISABLE_FORCE_DESKTOP_SYSTEM_DECORATIONS, Flags.FLAG_RECORD_TASK_SNAPSHOTS_BEFORE_SHUTDOWN, Flags.FLAG_REDUCE_CHANGED_EXCLUSION_RECTS_MSGS, Flags.FLAG_REDUCE_KEYGUARD_TRANSITIONS, Flags.FLAG_REDUCE_TASK_SNAPSHOT_MEMORY_USAGE, Flags.FLAG_REDUCE_UNNECESSARY_MEASURE, Flags.FLAG_RELATIVE_INSETS, Flags.FLAG_RELEASE_SNAPSHOT_AGGRESSIVELY, Flags.FLAG_RELEASE_USER_ASPECT_RATIO_WM, Flags.FLAG_REMOVE_ACTIVITY_STARTER_DREAM_CALLBACK, Flags.FLAG_REMOVE_DEFER_HIDING_CLIENT, Flags.FLAG_REMOVE_DEPART_TARGET_FROM_MOTION, Flags.FLAG_REPARENT_WINDOW_TOKEN_API, Flags.FLAG_RESPECT_NON_TOP_VISIBLE_FIXED_ORIENTATION, Flags.FLAG_RESPECT_ORIENTATION_CHANGE_FOR_UNRESIZEABLE, Flags.FLAG_SAFE_REGION_LETTERBOXING, Flags.FLAG_SAFE_RELEASE_SNAPSHOT_AGGRESSIVELY, Flags.FLAG_SCHEDULING_FOR_NOTIFICATION_SHADE, Flags.FLAG_SCRAMBLE_SNAPSHOT_FILE_NAME, Flags.FLAG_SCREEN_RECORDING_CALLBACKS, Flags.FLAG_SCROLLING_FROM_LETTERBOX, Flags.FLAG_SDK_DESIRED_PRESENT_TIME, Flags.FLAG_SET_SC_PROPERTIES_IN_CLIENT, Flags.FLAG_SHOW_APP_HANDLE_LARGE_SCREENS, Flags.FLAG_SHOW_DESKTOP_EXPERIENCE_DEV_OPTION, Flags.FLAG_SHOW_DESKTOP_WINDOWING_DEV_OPTION, Flags.FLAG_SHOW_HOME_BEHIND_DESKTOP, Flags.FLAG_SKIP_COMPAT_UI_EDUCATION_IN_DESKTOP_MODE, Flags.FLAG_SKIP_DECOR_VIEW_RELAYOUT_WHEN_CLOSING_BUGFIX, Flags.FLAG_SUPPORT_WIDGET_INTENTS_ON_CONNECTED_DISPLAY, Flags.FLAG_SUPPORTS_DRAG_ASSISTANT_TO_MULTIWINDOW, Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI, Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER, Flags.FLAG_SURFACE_TRUSTED_OVERLAY, Flags.FLAG_SYNC_SCREEN_CAPTURE, Flags.FLAG_SYSTEM_UI_POST_ANIMATION_END, Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG, Flags.FLAG_TOUCH_PASS_THROUGH_OPT_IN, Flags.FLAG_TRACK_SYSTEM_UI_CONTEXT_BEFORE_WMS, Flags.FLAG_TRANSIT_READY_TRACKING, Flags.FLAG_TRANSIT_TRACKER_PLUMBING, Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW, Flags.FLAG_UNIFY_BACK_NAVIGATION_TRANSITION, Flags.FLAG_UNIVERSAL_RESIZABLE_BY_DEFAULT, Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION, Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING, Flags.FLAG_UPDATE_DIMS_WHEN_WINDOW_SHOWN, Flags.FLAG_USE_CACHED_INSETS_FOR_DISPLAY_SWITCH, Flags.FLAG_USE_RT_FRAME_CALLBACK_FOR_SPLASH_SCREEN_TRANSFER, Flags.FLAG_USE_TASKS_DIM_ONLY, Flags.FLAG_USE_VISIBLE_REQUESTED_FOR_PROCESS_TRACKER, Flags.FLAG_USE_WINDOW_ORIGINAL_TOUCHABLE_REGION_WHEN_MAGNIFICATION_RECOMPUTE_BOUNDS, Flags.FLAG_VDM_FORCE_APP_UNIVERSAL_RESIZABLE_API, Flags.FLAG_WALLPAPER_OFFSET_ASYNC, Flags.FLAG_WLINFO_ONCREATE, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean actionModeEdgeToEdge() {
        return getValue(Flags.FLAG_ACTION_MODE_EDGE_TO_EDGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda105
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).actionModeEdgeToEdge();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingAnimationCustomizationFlag() {
        return getValue(Flags.FLAG_ACTIVITY_EMBEDDING_ANIMATION_CUSTOMIZATION_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda139
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).activityEmbeddingAnimationCustomizationFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingDelayTaskFragmentFinishForActivityLaunch() {
        return getValue(Flags.FLAG_ACTIVITY_EMBEDDING_DELAY_TASK_FRAGMENT_FINISH_FOR_ACTIVITY_LAUNCH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda179
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).activityEmbeddingDelayTaskFragmentFinishForActivityLaunch();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingInteractiveDividerFlag() {
        return getValue(Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda251
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).activityEmbeddingInteractiveDividerFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingMetrics() {
        return getValue(Flags.FLAG_ACTIVITY_EMBEDDING_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda260
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).activityEmbeddingMetrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingSupportForConnectedDisplays() {
        return getValue(Flags.FLAG_ACTIVITY_EMBEDDING_SUPPORT_FOR_CONNECTED_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda83
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).activityEmbeddingSupportForConnectedDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean allowDisableActivityRecordInputSink() {
        return getValue(Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda209
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowDisableActivityRecordInputSink();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean allowHideScmButton() {
        return getValue(Flags.FLAG_ALLOW_HIDE_SCM_BUTTON, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda53
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowHideScmButton();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean allowsScreenSizeDecoupledFromStatusBarAndCutout() {
        return getValue(Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda224
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowsScreenSizeDecoupledFromStatusBarAndCutout();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean alwaysDrawMagnificationFullscreenBorder() {
        return getValue(Flags.FLAG_ALWAYS_DRAW_MAGNIFICATION_FULLSCREEN_BORDER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).alwaysDrawMagnificationFullscreenBorder();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean alwaysUpdateWallpaperPermission() {
        return getValue(Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda152
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).alwaysUpdateWallpaperPermission();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean aodTransition() {
        return getValue(Flags.FLAG_AOD_TRANSITION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).aodTransition();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean appCompatAsyncRelayout() {
        return getValue(Flags.FLAG_APP_COMPAT_ASYNC_RELAYOUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda257
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appCompatAsyncRelayout();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean appCompatPropertiesApi() {
        return getValue(Flags.FLAG_APP_COMPAT_PROPERTIES_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda51
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appCompatPropertiesApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean appCompatRefactoring() {
        return getValue(Flags.FLAG_APP_COMPAT_REFACTORING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda159
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appCompatRefactoring();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean appCompatUiFramework() {
        return getValue(Flags.FLAG_APP_COMPAT_UI_FRAMEWORK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda150
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appCompatUiFramework();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean appHandleNoRelayoutOnExclusionChange() {
        return getValue(Flags.FLAG_APP_HANDLE_NO_RELAYOUT_ON_EXCLUSION_CHANGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).appHandleNoRelayoutOnExclusionChange();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean applyLifecycleOnPipChange() {
        return getValue(Flags.FLAG_APPLY_LIFECYCLE_ON_PIP_CHANGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda241
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).applyLifecycleOnPipChange();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean avoidRebindingIntentionallyDisconnectedWallpaper() {
        return getValue(Flags.FLAG_AVOID_REBINDING_INTENTIONALLY_DISCONNECTED_WALLPAPER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda199
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).avoidRebindingIntentionallyDisconnectedWallpaper();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean backupAndRestoreForUserAspectRatioSettings() {
        return getValue(Flags.FLAG_BACKUP_AND_RESTORE_FOR_USER_ASPECT_RATIO_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda206
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).backupAndRestoreForUserAspectRatioSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balAdditionalLogging() {
        return getValue(Flags.FLAG_BAL_ADDITIONAL_LOGGING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda185
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balAdditionalLogging();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balAdditionalStartModes() {
        return getValue(Flags.FLAG_BAL_ADDITIONAL_START_MODES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda79
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balAdditionalStartModes();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balClearAllowlistDuration() {
        return getValue(Flags.FLAG_BAL_CLEAR_ALLOWLIST_DURATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda265
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balClearAllowlistDuration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balDontBringExistingBackgroundTaskStackToFg() {
        return getValue(Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balDontBringExistingBackgroundTaskStackToFg();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balImproveRealCallerVisibilityCheck() {
        return getValue(Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda160
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balImproveRealCallerVisibilityCheck();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balImprovedMetrics() {
        return getValue(Flags.FLAG_BAL_IMPROVED_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda70
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balImprovedMetrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balReduceGracePeriod() {
        return getValue(Flags.FLAG_BAL_REDUCE_GRACE_PERIOD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balReduceGracePeriod();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balRequireOptInByPendingIntentCreator() {
        return getValue(Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda89
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balRequireOptInByPendingIntentCreator();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balRespectAppSwitchStateWhenCheckBoundByForegroundUid() {
        return getValue(Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda170
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balRespectAppSwitchStateWhenCheckBoundByForegroundUid();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balSendIntentWithOptions() {
        return getValue(Flags.FLAG_BAL_SEND_INTENT_WITH_OPTIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balSendIntentWithOptions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balShowToastsBlocked() {
        return getValue(Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda158
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balShowToastsBlocked();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balStrictModeGracePeriod() {
        return getValue(Flags.FLAG_BAL_STRICT_MODE_GRACE_PERIOD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda168
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balStrictModeGracePeriod();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean balStrictModeRo() {
        return getValue(Flags.FLAG_BAL_STRICT_MODE_RO, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda230
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).balStrictModeRo();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean betterSupportNonMatchParentActivity() {
        return getValue(Flags.FLAG_BETTER_SUPPORT_NON_MATCH_PARENT_ACTIVITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda102
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).betterSupportNonMatchParentActivity();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean cacheWindowStyle() {
        return getValue(Flags.FLAG_CACHE_WINDOW_STYLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cacheWindowStyle();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean cameraCompatForFreeform() {
        return getValue(Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda141
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraCompatForFreeform();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean cameraCompatFullscreenPickSameTaskActivity() {
        return getValue(Flags.FLAG_CAMERA_COMPAT_FULLSCREEN_PICK_SAME_TASK_ACTIVITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda92
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cameraCompatFullscreenPickSameTaskActivity();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean checkDisabledSnapshotsInTaskPersister() {
        return getValue(Flags.FLAG_CHECK_DISABLED_SNAPSHOTS_IN_TASK_PERSISTER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).checkDisabledSnapshotsInTaskPersister();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean cleanupDispatchPendingTransactionsRemoteException() {
        return getValue(Flags.FLAG_CLEANUP_DISPATCH_PENDING_TRANSACTIONS_REMOTE_EXCEPTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda211
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cleanupDispatchPendingTransactionsRemoteException();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean clearSystemVibrator() {
        return getValue(Flags.FLAG_CLEAR_SYSTEM_VIBRATOR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda183
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).clearSystemVibrator();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean closeToSquareConfigIncludesStatusBar() {
        return getValue(Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda59
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).closeToSquareConfigIncludesStatusBar();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean condenseConfigurationChangeForSimpleMode() {
        return getValue(Flags.FLAG_CONDENSE_CONFIGURATION_CHANGE_FOR_SIMPLE_MODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda65
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).condenseConfigurationChangeForSimpleMode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean configurableFontScaleDefault() {
        return getValue(Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda122
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).configurableFontScaleDefault();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean coverDisplayOptIn() {
        return getValue(Flags.FLAG_COVER_DISPLAY_OPT_IN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda142
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).coverDisplayOptIn();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean delayNotificationToMagnificationWhenRecentsWindowToFrontTransition() {
        return getValue(Flags.FLAG_DELAY_NOTIFICATION_TO_MAGNIFICATION_WHEN_RECENTS_WINDOW_TO_FRONT_TRANSITION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).delayNotificationToMagnificationWhenRecentsWindowToFrontTransition();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean delegateBackGestureToShell() {
        return getValue(Flags.FLAG_DELEGATE_BACK_GESTURE_TO_SHELL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).delegateBackGestureToShell();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean delegateUnhandledDrags() {
        return getValue(Flags.FLAG_DELEGATE_UNHANDLED_DRAGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda140
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).delegateUnhandledDrags();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean deleteCaptureDisplay() {
        return getValue(Flags.FLAG_DELETE_CAPTURE_DISPLAY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda54
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deleteCaptureDisplay();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean density390Api() {
        return getValue(Flags.FLAG_DENSITY_390_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda259
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).density390Api();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean disableDesktopLaunchParamsOutsideDesktopBugFix() {
        return getValue(Flags.FLAG_DISABLE_DESKTOP_LAUNCH_PARAMS_OUTSIDE_DESKTOP_BUG_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda108
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableDesktopLaunchParamsOutsideDesktopBugFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean disableNonResizableAppSnapResizing() {
        return getValue(Flags.FLAG_DISABLE_NON_RESIZABLE_APP_SNAP_RESIZING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda130
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableNonResizableAppSnapResizing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean disableOptOutEdgeToEdge() {
        return getValue(Flags.FLAG_DISABLE_OPT_OUT_EDGE_TO_EDGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda138
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableOptOutEdgeToEdge();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean doNotCheckIntersectionWhenNonMagnifiableWindowTransitions() {
        return getValue(Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda63
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).doNotCheckIntersectionWhenNonMagnifiableWindowTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean earlyLaunchHint() {
        return getValue(Flags.FLAG_EARLY_LAUNCH_HINT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda100
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).earlyLaunchHint();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean edgeToEdgeByDefault() {
        return getValue(Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda193
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).edgeToEdgeByDefault();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableAccessibleCustomHeaders() {
        return getValue(Flags.FLAG_ENABLE_ACCESSIBLE_CUSTOM_HEADERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAccessibleCustomHeaders();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableActivityEmbeddingSupportForConnectedDisplays() {
        return getValue(Flags.FLAG_ENABLE_ACTIVITY_EMBEDDING_SUPPORT_FOR_CONNECTED_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda201
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableActivityEmbeddingSupportForConnectedDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableAppHeaderWithTaskDensity() {
        return getValue(Flags.FLAG_ENABLE_APP_HEADER_WITH_TASK_DENSITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda186
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAppHeaderWithTaskDensity();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableBorderSettings() {
        return getValue(Flags.FLAG_ENABLE_BORDER_SETTINGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda91
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBorderSettings();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableBufferTransformHintFromDisplay() {
        return getValue(Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda229
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBufferTransformHintFromDisplay();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableBugFixesForSecondaryDisplay() {
        return getValue(Flags.FLAG_ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBugFixesForSecondaryDisplay();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCameraCompatForDesktopWindowing() {
        return getValue(Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda56
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCameraCompatForDesktopWindowing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCameraCompatForDesktopWindowingOptOut() {
        return getValue(Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING_OPT_OUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda161
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCameraCompatForDesktopWindowingOptOut();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCameraCompatForDesktopWindowingOptOutApi() {
        return getValue(Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING_OPT_OUT_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda236
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCameraCompatForDesktopWindowingOptOutApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCameraCompatTrackTaskAndAppBugfix() {
        return getValue(Flags.FLAG_ENABLE_CAMERA_COMPAT_TRACK_TASK_AND_APP_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda48
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCameraCompatTrackTaskAndAppBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCaptionCompatInsetConversion() {
        return getValue(Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_CONVERSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda216
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCaptionCompatInsetConversion();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCaptionCompatInsetForceConsumption() {
        return getValue(Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda149
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCaptionCompatInsetForceConsumption();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCaptionCompatInsetForceConsumptionAlways() {
        return getValue(Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda263
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCaptionCompatInsetForceConsumptionAlways();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCascadingWindows() {
        return getValue(Flags.FLAG_ENABLE_CASCADING_WINDOWS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda151
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCascadingWindows();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCompatUiVisibilityStatus() {
        return getValue(Flags.FLAG_ENABLE_COMPAT_UI_VISIBILITY_STATUS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda240
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCompatUiVisibilityStatus();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableCompatuiSysuiLauncher() {
        return getValue(Flags.FLAG_ENABLE_COMPATUI_SYSUI_LAUNCHER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda127
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCompatuiSysuiLauncher();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableConnectedDisplaysDnd() {
        return getValue(Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_DND, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda67
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableConnectedDisplaysDnd();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableConnectedDisplaysPip() {
        return getValue(Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_PIP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda144
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableConnectedDisplaysPip();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableConnectedDisplaysWindowDrag() {
        return getValue(Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_WINDOW_DRAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda145
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableConnectedDisplaysWindowDrag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopAppHandleAnimation() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_APP_HANDLE_ANIMATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopAppHandleAnimation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopAppLaunchAlttabTransitions() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_ALTTAB_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda90
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopAppLaunchAlttabTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopAppLaunchAlttabTransitionsBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_ALTTAB_TRANSITIONS_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda98
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopAppLaunchAlttabTransitionsBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopAppLaunchTransitions() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda154
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopAppLaunchTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopAppLaunchTransitionsBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda234
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopAppLaunchTransitionsBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopCloseShortcutBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_CLOSE_SHORTCUT_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda214
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopCloseShortcutBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopCloseTaskAnimationInDtcBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_CLOSE_TASK_ANIMATION_IN_DTC_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda88
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopCloseTaskAnimationInDtcBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopImeBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_IME_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda125
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopImeBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopImmersiveDragBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_IMMERSIVE_DRAG_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopImmersiveDragBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopIndicatorInSeparateThreadBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_INDICATOR_IN_SEPARATE_THREAD_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda217
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopIndicatorInSeparateThreadBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopModeThroughDevOption() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_MODE_THROUGH_DEV_OPTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopModeThroughDevOption();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopOpeningDeeplinkMinimizeAnimationBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_OPENING_DEEPLINK_MINIMIZE_ANIMATION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda210
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopOpeningDeeplinkMinimizeAnimationBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopRecentsTransitionsCornersBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda134
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopRecentsTransitionsCornersBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopSwipeBackMinimizeAnimationBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_SWIPE_BACK_MINIMIZE_ANIMATION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopSwipeBackMinimizeAnimationBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopSystemDialogsTransitions() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_SYSTEM_DIALOGS_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopSystemDialogsTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopTabTearingMinimizeAnimationBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_TAB_TEARING_MINIMIZE_ANIMATION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda225
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopTabTearingMinimizeAnimationBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopTaskbarOnFreeformDisplays() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_TASKBAR_ON_FREEFORM_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopTaskbarOnFreeformDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopTrampolineCloseAnimationBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_TRAMPOLINE_CLOSE_ANIMATION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda68
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopTrampolineCloseAnimationBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWallpaperActivityForSystemUser() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda174
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWallpaperActivityForSystemUser();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingAppHandleEducation() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_HANDLE_EDUCATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingAppHandleEducation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingAppToWeb() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda213
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingAppToWeb();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingAppToWebEducation() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB_EDUCATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda254
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingAppToWebEducation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingAppToWebEducationIntegration() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB_EDUCATION_INTEGRATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda118
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingAppToWebEducationIntegration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingBackNavigation() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda80
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingBackNavigation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingEnterTransitionBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_ENTER_TRANSITION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda247
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingEnterTransitionBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingEnterTransitions() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_ENTER_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingEnterTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingExitByMinimizeTransitionBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_BY_MINIMIZE_TRANSITION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda103
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingExitByMinimizeTransitionBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingExitTransitions() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda198
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingExitTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingExitTransitionsBugfix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda132
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingExitTransitionsBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingHsum() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_HSUM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda231
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingHsum();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingImmersiveHandleHiding() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda119
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingImmersiveHandleHiding();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingModalsPolicy() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODALS_POLICY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda169
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingModalsPolicy();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingMode() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda255
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingMode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingMultiInstanceFeatures() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MULTI_INSTANCE_FEATURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingMultiInstanceFeatures();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingPersistence() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_PERSISTENCE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda205
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingPersistence();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingPip() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_PIP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda143
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingPip();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingQuickSwitch() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingQuickSwitch();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingScvhCacheBugFix() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SCVH_CACHE_BUG_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingScvhCacheBugFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingSizeConstraints() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda232
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingSizeConstraints();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingTaskLimit() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASK_LIMIT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda117
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingTaskLimit();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingTaskbarRunningApps() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda262
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingTaskbarRunningApps();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingTransitions() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda64
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingWallpaperActivity() {
        return getValue(Flags.FLAG_ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda252
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDesktopWindowingWallpaperActivity();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDeviceStateAutoRotateSettingLogging() {
        return getValue(Flags.FLAG_ENABLE_DEVICE_STATE_AUTO_ROTATE_SETTING_LOGGING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda106
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDeviceStateAutoRotateSettingLogging();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDeviceStateAutoRotateSettingRefactor() {
        return getValue(Flags.FLAG_ENABLE_DEVICE_STATE_AUTO_ROTATE_SETTING_REFACTOR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda175
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDeviceStateAutoRotateSettingRefactor();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDisplayDisconnectInteraction() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_DISCONNECT_INTERACTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda189
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayDisconnectInteraction();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDisplayFocusInShellTransitions() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_FOCUS_IN_SHELL_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda74
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayFocusInShellTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDisplayReconnectInteraction() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_RECONNECT_INTERACTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda253
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayReconnectInteraction();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDisplayWindowingModeSwitching() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_WINDOWING_MODE_SWITCHING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayWindowingModeSwitching();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDragResizeSetUpInBgThread() {
        return getValue(Flags.FLAG_ENABLE_DRAG_RESIZE_SET_UP_IN_BG_THREAD, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda96
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDragResizeSetUpInBgThread();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDragToDesktopIncomingTransitionsBugfix() {
        return getValue(Flags.FLAG_ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda111
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDragToDesktopIncomingTransitionsBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDragToMaximize() {
        return getValue(Flags.FLAG_ENABLE_DRAG_TO_MAXIMIZE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda207
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDragToMaximize();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableDynamicRadiusComputationBugfix() {
        return getValue(Flags.FLAG_ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda137
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDynamicRadiusComputationBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableFullScreenWindowOnRemovingSplitScreenStageBugfix() {
        return getValue(Flags.FLAG_ENABLE_FULL_SCREEN_WINDOW_ON_REMOVING_SPLIT_SCREEN_STAGE_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableFullScreenWindowOnRemovingSplitScreenStageBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableFullyImmersiveInDesktop() {
        return getValue(Flags.FLAG_ENABLE_FULLY_IMMERSIVE_IN_DESKTOP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda135
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableFullyImmersiveInDesktop();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableHandleInputFix() {
        return getValue(Flags.FLAG_ENABLE_HANDLE_INPUT_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda197
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableHandleInputFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableHoldToDragAppHandle() {
        return getValue(Flags.FLAG_ENABLE_HOLD_TO_DRAG_APP_HANDLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda245
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableHoldToDragAppHandle();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableIndependentBackInProjected() {
        return getValue(Flags.FLAG_ENABLE_INDEPENDENT_BACK_IN_PROJECTED, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableIndependentBackInProjected();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableInputLayerTransitionFix() {
        return getValue(Flags.FLAG_ENABLE_INPUT_LAYER_TRANSITION_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableInputLayerTransitionFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableMinimizeButton() {
        return getValue(Flags.FLAG_ENABLE_MINIMIZE_BUTTON, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda115
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMinimizeButton();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableModalsFullscreenWithPermission() {
        return getValue(Flags.FLAG_ENABLE_MODALS_FULLSCREEN_WITH_PERMISSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda126
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableModalsFullscreenWithPermission();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableModalsFullscreenWithPlatformSignature() {
        return getValue(Flags.FLAG_ENABLE_MODALS_FULLSCREEN_WITH_PLATFORM_SIGNATURE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda248
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableModalsFullscreenWithPlatformSignature();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableMoveToNextDisplayShortcut() {
        return getValue(Flags.FLAG_ENABLE_MOVE_TO_NEXT_DISPLAY_SHORTCUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda203
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMoveToNextDisplayShortcut();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableMultiDisplaySplit() {
        return getValue(Flags.FLAG_ENABLE_MULTI_DISPLAY_SPLIT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda237
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultiDisplaySplit();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableMultidisplayTrackpadBackGesture() {
        return getValue(Flags.FLAG_ENABLE_MULTIDISPLAY_TRACKPAD_BACK_GESTURE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultidisplayTrackpadBackGesture();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableMultipleDesktopsBackend() {
        return getValue(Flags.FLAG_ENABLE_MULTIPLE_DESKTOPS_BACKEND, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda187
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultipleDesktopsBackend();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableMultipleDesktopsFrontend() {
        return getValue(Flags.FLAG_ENABLE_MULTIPLE_DESKTOPS_FRONTEND, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda171
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultipleDesktopsFrontend();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableNonDefaultDisplaySplit() {
        return getValue(Flags.FLAG_ENABLE_NON_DEFAULT_DISPLAY_SPLIT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda113
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNonDefaultDisplaySplit();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableOpaqueBackgroundForTransparentWindows() {
        return getValue(Flags.FLAG_ENABLE_OPAQUE_BACKGROUND_FOR_TRANSPARENT_WINDOWS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda72
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOpaqueBackgroundForTransparentWindows();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enablePerDisplayDesktopWallpaperActivity() {
        return getValue(Flags.FLAG_ENABLE_PER_DISPLAY_DESKTOP_WALLPAPER_ACTIVITY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda195
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePerDisplayDesktopWallpaperActivity();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enablePerDisplayPackageContextCacheInStatusbarNotif() {
        return getValue(Flags.FLAG_ENABLE_PER_DISPLAY_PACKAGE_CONTEXT_CACHE_IN_STATUSBAR_NOTIF, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda78
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePerDisplayPackageContextCacheInStatusbarNotif();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enablePersistingDisplaySizeForConnectedDisplays() {
        return getValue(Flags.FLAG_ENABLE_PERSISTING_DISPLAY_SIZE_FOR_CONNECTED_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda239
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePersistingDisplaySizeForConnectedDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enablePresentationForConnectedDisplays() {
        return getValue(Flags.FLAG_ENABLE_PRESENTATION_FOR_CONNECTED_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda192
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePresentationForConnectedDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableProjectedDisplayDesktopMode() {
        return getValue(Flags.FLAG_ENABLE_PROJECTED_DISPLAY_DESKTOP_MODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda66
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableProjectedDisplayDesktopMode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableQuickswitchDesktopSplitBugfix() {
        return getValue(Flags.FLAG_ENABLE_QUICKSWITCH_DESKTOP_SPLIT_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda104
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableQuickswitchDesktopSplitBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableRequestFullscreenBugfix() {
        return getValue(Flags.FLAG_ENABLE_REQUEST_FULLSCREEN_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda101
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRequestFullscreenBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableResizingMetrics() {
        return getValue(Flags.FLAG_ENABLE_RESIZING_METRICS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda180
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableResizingMetrics();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableRestartMenuForConnectedDisplays() {
        return getValue(Flags.FLAG_ENABLE_RESTART_MENU_FOR_CONNECTED_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda155
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRestartMenuForConnectedDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableRestoreToPreviousSizeFromDesktopImmersive() {
        return getValue(Flags.FLAG_ENABLE_RESTORE_TO_PREVIOUS_SIZE_FROM_DESKTOP_IMMERSIVE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda95
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableRestoreToPreviousSizeFromDesktopImmersive();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableShellInitialBoundsRegressionBugFix() {
        return getValue(Flags.FLAG_ENABLE_SHELL_INITIAL_BOUNDS_REGRESSION_BUG_FIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda81
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableShellInitialBoundsRegressionBugFix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableSizeCompatModeImprovementsForConnectedDisplays() {
        return getValue(Flags.FLAG_ENABLE_SIZE_COMPAT_MODE_IMPROVEMENTS_FOR_CONNECTED_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda188
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSizeCompatModeImprovementsForConnectedDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableStartLaunchTransitionFromTaskbarBugfix() {
        return getValue(Flags.FLAG_ENABLE_START_LAUNCH_TRANSITION_FROM_TASKBAR_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda221
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableStartLaunchTransitionFromTaskbarBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableTaskResizingKeyboardShortcuts() {
        return getValue(Flags.FLAG_ENABLE_TASK_RESIZING_KEYBOARD_SHORTCUTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda157
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTaskResizingKeyboardShortcuts();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableTaskStackObserverInShell() {
        return getValue(Flags.FLAG_ENABLE_TASK_STACK_OBSERVER_IN_SHELL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTaskStackObserverInShell();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableTaskbarConnectedDisplays() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_CONNECTED_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda249
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTaskbarConnectedDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableTaskbarOverflow() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_OVERFLOW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda176
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTaskbarOverflow();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableTaskbarRecentsLayoutTransition() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_RECENTS_LAYOUT_TRANSITION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTaskbarRecentsLayoutTransition();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableThemedAppHeaders() {
        return getValue(Flags.FLAG_ENABLE_THEMED_APP_HEADERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda114
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableThemedAppHeaders();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableTileResizing() {
        return getValue(Flags.FLAG_ENABLE_TILE_RESIZING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda246
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTileResizing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableTopVisibleRootTaskPerUserTracking() {
        return getValue(Flags.FLAG_ENABLE_TOP_VISIBLE_ROOT_TASK_PER_USER_TRACKING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda131
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTopVisibleRootTaskPerUserTracking();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableVisualIndicatorInTransitionBugfix() {
        return getValue(Flags.FLAG_ENABLE_VISUAL_INDICATOR_IN_TRANSITION_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda124
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableVisualIndicatorInTransitionBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableWindowContextResourcesUpdateOnConfigChange() {
        return getValue(Flags.FLAG_ENABLE_WINDOW_CONTEXT_RESOURCES_UPDATE_ON_CONFIG_CHANGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda62
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWindowContextResourcesUpdateOnConfigChange();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableWindowingDynamicInitialBounds() {
        return getValue(Flags.FLAG_ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda84
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWindowingDynamicInitialBounds();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableWindowingEdgeDragResize() {
        return getValue(Flags.FLAG_ENABLE_WINDOWING_EDGE_DRAG_RESIZE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda242
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWindowingEdgeDragResize();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableWindowingScaledResizing() {
        return getValue(Flags.FLAG_ENABLE_WINDOWING_SCALED_RESIZING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda243
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWindowingScaledResizing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enableWindowingTransitionHandlersObservers() {
        return getValue(Flags.FLAG_ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda191
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableWindowingTransitionHandlersObservers();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enforceEdgeToEdge() {
        return getValue(Flags.FLAG_ENFORCE_EDGE_TO_EDGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda215
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceEdgeToEdge();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean ensureKeyguardDoesTransitionStarting() {
        return getValue(Flags.FLAG_ENSURE_KEYGUARD_DOES_TRANSITION_STARTING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda129
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ensureKeyguardDoesTransitionStarting();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean ensureWallpaperInTransitions() {
        return getValue(Flags.FLAG_ENSURE_WALLPAPER_IN_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda110
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ensureWallpaperInTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean ensureWallpaperInWearTransitions() {
        return getValue(Flags.FLAG_ENSURE_WALLPAPER_IN_WEAR_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda164
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ensureWallpaperInWearTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean enterDesktopByDefaultOnFreeformDisplays() {
        return getValue(Flags.FLAG_ENTER_DESKTOP_BY_DEFAULT_ON_FREEFORM_DISPLAYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enterDesktopByDefaultOnFreeformDisplays();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean excludeCaptionFromAppBounds() {
        return getValue(Flags.FLAG_EXCLUDE_CAPTION_FROM_APP_BOUNDS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda258
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).excludeCaptionFromAppBounds();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean excludeDrawingAppThemeSnapshotFromLock() {
        return getValue(Flags.FLAG_EXCLUDE_DRAWING_APP_THEME_SNAPSHOT_FROM_LOCK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda166
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).excludeDrawingAppThemeSnapshotFromLock();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean excludeTaskFromRecents() {
        return getValue(Flags.FLAG_EXCLUDE_TASK_FROM_RECENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda128
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).excludeTaskFromRecents();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean fifoPriorityForMajorUiProcesses() {
        return getValue(Flags.FLAG_FIFO_PRIORITY_FOR_MAJOR_UI_PROCESSES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda218
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fifoPriorityForMajorUiProcesses();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean fixHideOverlayApi() {
        return getValue(Flags.FLAG_FIX_HIDE_OVERLAY_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixHideOverlayApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean fixLayoutExistingTask() {
        return getValue(Flags.FLAG_FIX_LAYOUT_EXISTING_TASK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixLayoutExistingTask();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean fixViewRootCallTrace() {
        return getValue(Flags.FLAG_FIX_VIEW_ROOT_CALL_TRACE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda196
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixViewRootCallTrace();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean forceCloseTopTransparentFullscreenTask() {
        return getValue(Flags.FLAG_FORCE_CLOSE_TOP_TRANSPARENT_FULLSCREEN_TASK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda147
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).forceCloseTopTransparentFullscreenTask();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean formFactorBasedDesktopFirstSwitch() {
        return getValue(Flags.FLAG_FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).formFactorBasedDesktopFirstSwitch();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean getDimmerOnClosing() {
        return getValue(Flags.FLAG_GET_DIMMER_ON_CLOSING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda55
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getDimmerOnClosing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean ignoreAspectRatioRestrictionsForResizeableFreeformActivities() {
        return getValue(Flags.FLAG_IGNORE_ASPECT_RATIO_RESTRICTIONS_FOR_RESIZEABLE_FREEFORM_ACTIVITIES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreAspectRatioRestrictionsForResizeableFreeformActivities();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean ignoreCornerRadiusAndShadows() {
        return getValue(Flags.FLAG_IGNORE_CORNER_RADIUS_AND_SHADOWS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreCornerRadiusAndShadows();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean includeTopTransparentFullscreenTaskInDesktopHeuristic() {
        return getValue(Flags.FLAG_INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda208
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).includeTopTransparentFullscreenTaskInDesktopHeuristic();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean inheritTaskBoundsForTrampolineTaskLaunches() {
        return getValue(Flags.FLAG_INHERIT_TASK_BOUNDS_FOR_TRAMPOLINE_TASK_LAUNCHES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda82
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).inheritTaskBoundsForTrampolineTaskLaunches();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean insetsDecoupledConfiguration() {
        return getValue(Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda153
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).insetsDecoupledConfiguration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean jankApi() {
        return getValue(Flags.FLAG_JANK_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda256
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).jankApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean keyboardShortcutsToSwitchDesks() {
        return getValue(Flags.FLAG_KEYBOARD_SHORTCUTS_TO_SWITCH_DESKS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda202
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardShortcutsToSwitchDesks();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean keyguardGoingAwayTimeout() {
        return getValue(Flags.FLAG_KEYGUARD_GOING_AWAY_TIMEOUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda219
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyguardGoingAwayTimeout();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean letterboxBackgroundWallpaper() {
        return getValue(Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda261
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).letterboxBackgroundWallpaper();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean movableCutoutConfiguration() {
        return getValue(Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda97
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).movableCutoutConfiguration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean moveToExternalDisplayShortcut() {
        return getValue(Flags.FLAG_MOVE_TO_EXTERNAL_DISPLAY_SHORTCUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda57
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).moveToExternalDisplayShortcut();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean multiCrop() {
        return getValue(Flags.FLAG_MULTI_CROP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda167
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).multiCrop();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean navBarTransparentByDefault() {
        return getValue(Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda238
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).navBarTransparentByDefault();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean nestedTasksWithIndependentBounds() {
        return getValue(Flags.FLAG_NESTED_TASKS_WITH_INDEPENDENT_BOUNDS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).nestedTasksWithIndependentBounds();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean noConsecutiveVisibilityEvents() {
        return getValue(Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda264
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noConsecutiveVisibilityEvents();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean noDuplicateSurfaceDestroyedEvents() {
        return getValue(Flags.FLAG_NO_DUPLICATE_SURFACE_DESTROYED_EVENTS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda99
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noDuplicateSurfaceDestroyedEvents();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean noVisibilityEventOnDisplayStateChange() {
        return getValue(Flags.FLAG_NO_VISIBILITY_EVENT_ON_DISPLAY_STATE_CHANGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).noVisibilityEventOnDisplayStateChange();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean offloadColorExtraction() {
        return getValue(Flags.FLAG_OFFLOAD_COLOR_EXTRACTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda93
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).offloadColorExtraction();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean portWindowSizeAnimation() {
        return getValue(Flags.FLAG_PORT_WINDOW_SIZE_ANIMATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda71
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).portWindowSizeAnimation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean predictiveBackDefaultEnableSdk36() {
        return getValue(Flags.FLAG_PREDICTIVE_BACK_DEFAULT_ENABLE_SDK_36, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda73
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).predictiveBackDefaultEnableSdk36();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean predictiveBackPrioritySystemNavigationObserver() {
        return getValue(Flags.FLAG_PREDICTIVE_BACK_PRIORITY_SYSTEM_NAVIGATION_OBSERVER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).predictiveBackPrioritySystemNavigationObserver();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean predictiveBackSwipeEdgeNoneApi() {
        return getValue(Flags.FLAG_PREDICTIVE_BACK_SWIPE_EDGE_NONE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda86
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).predictiveBackSwipeEdgeNoneApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean predictiveBackSystemOverrideCallback() {
        return getValue(Flags.FLAG_PREDICTIVE_BACK_SYSTEM_OVERRIDE_CALLBACK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda75
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).predictiveBackSystemOverrideCallback();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean predictiveBackThreeButtonNav() {
        return getValue(Flags.FLAG_PREDICTIVE_BACK_THREE_BUTTON_NAV, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda223
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).predictiveBackThreeButtonNav();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean predictiveBackTimestampApi() {
        return getValue(Flags.FLAG_PREDICTIVE_BACK_TIMESTAMP_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda250
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).predictiveBackTimestampApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean processPriorityPolicyForMultiWindowMode() {
        return getValue(Flags.FLAG_PROCESS_PRIORITY_POLICY_FOR_MULTI_WINDOW_MODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda165
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).processPriorityPolicyForMultiWindowMode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean rearDisplayDisableForceDesktopSystemDecorations() {
        return getValue(Flags.FLAG_REAR_DISPLAY_DISABLE_FORCE_DESKTOP_SYSTEM_DECORATIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda220
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rearDisplayDisableForceDesktopSystemDecorations();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean recordTaskSnapshotsBeforeShutdown() {
        return getValue(Flags.FLAG_RECORD_TASK_SNAPSHOTS_BEFORE_SHUTDOWN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).recordTaskSnapshotsBeforeShutdown();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean reduceChangedExclusionRectsMsgs() {
        return getValue(Flags.FLAG_REDUCE_CHANGED_EXCLUSION_RECTS_MSGS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda163
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reduceChangedExclusionRectsMsgs();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean reduceKeyguardTransitions() {
        return getValue(Flags.FLAG_REDUCE_KEYGUARD_TRANSITIONS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda162
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reduceKeyguardTransitions();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean reduceTaskSnapshotMemoryUsage() {
        return getValue(Flags.FLAG_REDUCE_TASK_SNAPSHOT_MEMORY_USAGE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda244
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reduceTaskSnapshotMemoryUsage();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean reduceUnnecessaryMeasure() {
        return getValue(Flags.FLAG_REDUCE_UNNECESSARY_MEASURE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda69
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reduceUnnecessaryMeasure();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean relativeInsets() {
        return getValue(Flags.FLAG_RELATIVE_INSETS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda121
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).relativeInsets();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean releaseSnapshotAggressively() {
        return getValue(Flags.FLAG_RELEASE_SNAPSHOT_AGGRESSIVELY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda94
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).releaseSnapshotAggressively();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean releaseUserAspectRatioWm() {
        return getValue(Flags.FLAG_RELEASE_USER_ASPECT_RATIO_WM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).releaseUserAspectRatioWm();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean removeActivityStarterDreamCallback() {
        return getValue(Flags.FLAG_REMOVE_ACTIVITY_STARTER_DREAM_CALLBACK, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda227
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeActivityStarterDreamCallback();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean removeDeferHidingClient() {
        return getValue(Flags.FLAG_REMOVE_DEFER_HIDING_CLIENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda76
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeDeferHidingClient();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean removeDepartTargetFromMotion() {
        return getValue(Flags.FLAG_REMOVE_DEPART_TARGET_FROM_MOTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda173
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeDepartTargetFromMotion();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean reparentWindowTokenApi() {
        return getValue(Flags.FLAG_REPARENT_WINDOW_TOKEN_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda109
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reparentWindowTokenApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean respectNonTopVisibleFixedOrientation() {
        return getValue(Flags.FLAG_RESPECT_NON_TOP_VISIBLE_FIXED_ORIENTATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).respectNonTopVisibleFixedOrientation();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean respectOrientationChangeForUnresizeable() {
        return getValue(Flags.FLAG_RESPECT_ORIENTATION_CHANGE_FOR_UNRESIZEABLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).respectOrientationChangeForUnresizeable();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean safeRegionLetterboxing() {
        return getValue(Flags.FLAG_SAFE_REGION_LETTERBOXING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda146
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).safeRegionLetterboxing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean safeReleaseSnapshotAggressively() {
        return getValue(Flags.FLAG_SAFE_RELEASE_SNAPSHOT_AGGRESSIVELY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda87
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).safeReleaseSnapshotAggressively();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean schedulingForNotificationShade() {
        return getValue(Flags.FLAG_SCHEDULING_FOR_NOTIFICATION_SHADE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda112
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).schedulingForNotificationShade();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean scrambleSnapshotFileName() {
        return getValue(Flags.FLAG_SCRAMBLE_SNAPSHOT_FILE_NAME, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda133
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).scrambleSnapshotFileName();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean screenRecordingCallbacks() {
        return getValue(Flags.FLAG_SCREEN_RECORDING_CALLBACKS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda123
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).screenRecordingCallbacks();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean scrollingFromLetterbox() {
        return getValue(Flags.FLAG_SCROLLING_FROM_LETTERBOX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda120
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).scrollingFromLetterbox();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean sdkDesiredPresentTime() {
        return getValue(Flags.FLAG_SDK_DESIRED_PRESENT_TIME, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda222
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sdkDesiredPresentTime();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean setScPropertiesInClient() {
        return getValue(Flags.FLAG_SET_SC_PROPERTIES_IN_CLIENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setScPropertiesInClient();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean showAppHandleLargeScreens() {
        return getValue(Flags.FLAG_SHOW_APP_HANDLE_LARGE_SCREENS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda136
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showAppHandleLargeScreens();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean showDesktopExperienceDevOption() {
        return getValue(Flags.FLAG_SHOW_DESKTOP_EXPERIENCE_DEV_OPTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda172
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showDesktopExperienceDevOption();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean showDesktopWindowingDevOption() {
        return getValue(Flags.FLAG_SHOW_DESKTOP_WINDOWING_DEV_OPTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda85
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showDesktopWindowingDevOption();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean showHomeBehindDesktop() {
        return getValue(Flags.FLAG_SHOW_HOME_BEHIND_DESKTOP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showHomeBehindDesktop();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean skipCompatUiEducationInDesktopMode() {
        return getValue(Flags.FLAG_SKIP_COMPAT_UI_EDUCATION_IN_DESKTOP_MODE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda77
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipCompatUiEducationInDesktopMode();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean skipDecorViewRelayoutWhenClosingBugfix() {
        return getValue(Flags.FLAG_SKIP_DECOR_VIEW_RELAYOUT_WHEN_CLOSING_BUGFIX, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipDecorViewRelayoutWhenClosingBugfix();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean supportWidgetIntentsOnConnectedDisplay() {
        return getValue(Flags.FLAG_SUPPORT_WIDGET_INTENTS_ON_CONNECTED_DISPLAY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda148
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportWidgetIntentsOnConnectedDisplay();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean supportsDragAssistantToMultiwindow() {
        return getValue(Flags.FLAG_SUPPORTS_DRAG_ASSISTANT_TO_MULTIWINDOW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportsDragAssistantToMultiwindow();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean supportsMultiInstanceSystemUi() {
        return getValue(Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportsMultiInstanceSystemUi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean surfaceControlInputReceiver() {
        return getValue(Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).surfaceControlInputReceiver();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean surfaceTrustedOverlay() {
        return getValue(Flags.FLAG_SURFACE_TRUSTED_OVERLAY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda200
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).surfaceTrustedOverlay();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean syncScreenCapture() {
        return getValue(Flags.FLAG_SYNC_SCREEN_CAPTURE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda233
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).syncScreenCapture();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean systemUiPostAnimationEnd() {
        return getValue(Flags.FLAG_SYSTEM_UI_POST_ANIMATION_END, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).systemUiPostAnimationEnd();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean taskFragmentSystemOrganizerFlag() {
        return getValue(Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda184
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).taskFragmentSystemOrganizerFlag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean touchPassThroughOptIn() {
        return getValue(Flags.FLAG_TOUCH_PASS_THROUGH_OPT_IN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda156
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).touchPassThroughOptIn();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean trackSystemUiContextBeforeWms() {
        return getValue(Flags.FLAG_TRACK_SYSTEM_UI_CONTEXT_BEFORE_WMS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda116
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).trackSystemUiContextBeforeWms();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean transitReadyTracking() {
        return getValue(Flags.FLAG_TRANSIT_READY_TRACKING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda226
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).transitReadyTracking();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean transitTrackerPlumbing() {
        return getValue(Flags.FLAG_TRANSIT_TRACKER_PLUMBING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda52
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).transitTrackerPlumbing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean trustedPresentationListenerForWindow() {
        return getValue(Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda194
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).trustedPresentationListenerForWindow();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean unifyBackNavigationTransition() {
        return getValue(Flags.FLAG_UNIFY_BACK_NAVIGATION_TRANSITION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda178
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).unifyBackNavigationTransition();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean universalResizableByDefault() {
        return getValue(Flags.FLAG_UNIVERSAL_RESIZABLE_BY_DEFAULT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda182
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).universalResizableByDefault();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean untrustedEmbeddingAnyAppPermission() {
        return getValue(Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).untrustedEmbeddingAnyAppPermission();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean untrustedEmbeddingStateSharing() {
        return getValue(Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda228
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).untrustedEmbeddingStateSharing();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean updateDimsWhenWindowShown() {
        return getValue(Flags.FLAG_UPDATE_DIMS_WHEN_WINDOW_SHOWN, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda107
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateDimsWhenWindowShown();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean useCachedInsetsForDisplaySwitch() {
        return getValue(Flags.FLAG_USE_CACHED_INSETS_FOR_DISPLAY_SWITCH, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda235
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useCachedInsetsForDisplaySwitch();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean useRtFrameCallbackForSplashScreenTransfer() {
        return getValue(Flags.FLAG_USE_RT_FRAME_CALLBACK_FOR_SPLASH_SCREEN_TRANSFER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda181
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useRtFrameCallbackForSplashScreenTransfer();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean useTasksDimOnly() {
        return getValue(Flags.FLAG_USE_TASKS_DIM_ONLY, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useTasksDimOnly();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean useVisibleRequestedForProcessTracker() {
        return getValue(Flags.FLAG_USE_VISIBLE_REQUESTED_FOR_PROCESS_TRACKER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda190
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useVisibleRequestedForProcessTracker();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean useWindowOriginalTouchableRegionWhenMagnificationRecomputeBounds() {
        return getValue(Flags.FLAG_USE_WINDOW_ORIGINAL_TOUCHABLE_REGION_WHEN_MAGNIFICATION_RECOMPUTE_BOUNDS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda177
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useWindowOriginalTouchableRegionWhenMagnificationRecomputeBounds();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean vdmForceAppUniversalResizableApi() {
        return getValue(Flags.FLAG_VDM_FORCE_APP_UNIVERSAL_RESIZABLE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda212
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vdmForceAppUniversalResizableApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean wallpaperOffsetAsync() {
        return getValue(Flags.FLAG_WALLPAPER_OFFSET_ASYNC, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda60
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wallpaperOffsetAsync();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.window.flags.FeatureFlags
    public boolean wlinfoOncreate() {
        return getValue(Flags.FLAG_WLINFO_ONCREATE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda204
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).wlinfoOncreate();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ACTION_MODE_EDGE_TO_EDGE, Flags.FLAG_ACTIVITY_EMBEDDING_ANIMATION_CUSTOMIZATION_FLAG, Flags.FLAG_ACTIVITY_EMBEDDING_DELAY_TASK_FRAGMENT_FINISH_FOR_ACTIVITY_LAUNCH, Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG, Flags.FLAG_ACTIVITY_EMBEDDING_METRICS, Flags.FLAG_ACTIVITY_EMBEDDING_SUPPORT_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK, Flags.FLAG_ALLOW_HIDE_SCM_BUTTON, Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT, Flags.FLAG_ALWAYS_DRAW_MAGNIFICATION_FULLSCREEN_BORDER, Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION, Flags.FLAG_AOD_TRANSITION, Flags.FLAG_APP_COMPAT_ASYNC_RELAYOUT, Flags.FLAG_APP_COMPAT_PROPERTIES_API, Flags.FLAG_APP_COMPAT_REFACTORING, Flags.FLAG_APP_COMPAT_UI_FRAMEWORK, Flags.FLAG_APP_HANDLE_NO_RELAYOUT_ON_EXCLUSION_CHANGE, Flags.FLAG_APPLY_LIFECYCLE_ON_PIP_CHANGE, Flags.FLAG_AVOID_REBINDING_INTENTIONALLY_DISCONNECTED_WALLPAPER, Flags.FLAG_BACKUP_AND_RESTORE_FOR_USER_ASPECT_RATIO_SETTINGS, Flags.FLAG_BAL_ADDITIONAL_LOGGING, Flags.FLAG_BAL_ADDITIONAL_START_MODES, Flags.FLAG_BAL_CLEAR_ALLOWLIST_DURATION, Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG, Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK, Flags.FLAG_BAL_IMPROVED_METRICS, Flags.FLAG_BAL_REDUCE_GRACE_PERIOD, Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR, Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID, Flags.FLAG_BAL_SEND_INTENT_WITH_OPTIONS, Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED, Flags.FLAG_BAL_STRICT_MODE_GRACE_PERIOD, Flags.FLAG_BAL_STRICT_MODE_RO, Flags.FLAG_BETTER_SUPPORT_NON_MATCH_PARENT_ACTIVITY, Flags.FLAG_CACHE_WINDOW_STYLE, Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM, Flags.FLAG_CAMERA_COMPAT_FULLSCREEN_PICK_SAME_TASK_ACTIVITY, Flags.FLAG_CHECK_DISABLED_SNAPSHOTS_IN_TASK_PERSISTER, Flags.FLAG_CLEANUP_DISPATCH_PENDING_TRANSACTIONS_REMOTE_EXCEPTION, Flags.FLAG_CLEAR_SYSTEM_VIBRATOR, Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR, Flags.FLAG_CONDENSE_CONFIGURATION_CHANGE_FOR_SIMPLE_MODE, Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT, Flags.FLAG_COVER_DISPLAY_OPT_IN, Flags.FLAG_DELAY_NOTIFICATION_TO_MAGNIFICATION_WHEN_RECENTS_WINDOW_TO_FRONT_TRANSITION, Flags.FLAG_DELEGATE_BACK_GESTURE_TO_SHELL, Flags.FLAG_DELEGATE_UNHANDLED_DRAGS, Flags.FLAG_DELETE_CAPTURE_DISPLAY, Flags.FLAG_DENSITY_390_API, Flags.FLAG_DISABLE_DESKTOP_LAUNCH_PARAMS_OUTSIDE_DESKTOP_BUG_FIX, Flags.FLAG_DISABLE_NON_RESIZABLE_APP_SNAP_RESIZING, Flags.FLAG_DISABLE_OPT_OUT_EDGE_TO_EDGE, Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS, Flags.FLAG_EARLY_LAUNCH_HINT, Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT, Flags.FLAG_ENABLE_ACCESSIBLE_CUSTOM_HEADERS, Flags.FLAG_ENABLE_ACTIVITY_EMBEDDING_SUPPORT_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_APP_HEADER_WITH_TASK_DENSITY, Flags.FLAG_ENABLE_BORDER_SETTINGS, Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY, Flags.FLAG_ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY, Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING, Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING_OPT_OUT, Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING_OPT_OUT_API, Flags.FLAG_ENABLE_CAMERA_COMPAT_TRACK_TASK_AND_APP_BUGFIX, Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_CONVERSION, Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION, Flags.FLAG_ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS, Flags.FLAG_ENABLE_CASCADING_WINDOWS, Flags.FLAG_ENABLE_COMPAT_UI_VISIBILITY_STATUS, Flags.FLAG_ENABLE_COMPATUI_SYSUI_LAUNCHER, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_DND, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_PIP, Flags.FLAG_ENABLE_CONNECTED_DISPLAYS_WINDOW_DRAG, Flags.FLAG_ENABLE_DESKTOP_APP_HANDLE_ANIMATION, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_ALTTAB_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_ALTTAB_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_CLOSE_SHORTCUT_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_CLOSE_TASK_ANIMATION_IN_DTC_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_IME_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_IMMERSIVE_DRAG_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_INDICATOR_IN_SEPARATE_THREAD_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_MODE_THROUGH_DEV_OPTION, Flags.FLAG_ENABLE_DESKTOP_OPENING_DEEPLINK_MINIMIZE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_SWIPE_BACK_MINIMIZE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_SYSTEM_DIALOGS_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_TAB_TEARING_MINIMIZE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_TASKBAR_ON_FREEFORM_DISPLAYS, Flags.FLAG_ENABLE_DESKTOP_TRAMPOLINE_CLOSE_ANIMATION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_HANDLE_EDUCATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB_EDUCATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_APP_TO_WEB_EDUCATION_INTEGRATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_ENTER_TRANSITION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_ENTER_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_BY_MINIMIZE_TRANSITION_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_HSUM, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODALS_POLICY, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MULTI_INSTANCE_FEATURES, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_PERSISTENCE, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_PIP, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SCVH_CACHE_BUG_FIX, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASK_LIMIT, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TRANSITIONS, Flags.FLAG_ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY, Flags.FLAG_ENABLE_DEVICE_STATE_AUTO_ROTATE_SETTING_LOGGING, Flags.FLAG_ENABLE_DEVICE_STATE_AUTO_ROTATE_SETTING_REFACTOR, Flags.FLAG_ENABLE_DISPLAY_DISCONNECT_INTERACTION, Flags.FLAG_ENABLE_DISPLAY_FOCUS_IN_SHELL_TRANSITIONS, Flags.FLAG_ENABLE_DISPLAY_RECONNECT_INTERACTION, Flags.FLAG_ENABLE_DISPLAY_WINDOWING_MODE_SWITCHING, Flags.FLAG_ENABLE_DRAG_RESIZE_SET_UP_IN_BG_THREAD, Flags.FLAG_ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX, Flags.FLAG_ENABLE_DRAG_TO_MAXIMIZE, Flags.FLAG_ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX, Flags.FLAG_ENABLE_FULL_SCREEN_WINDOW_ON_REMOVING_SPLIT_SCREEN_STAGE_BUGFIX, Flags.FLAG_ENABLE_FULLY_IMMERSIVE_IN_DESKTOP, Flags.FLAG_ENABLE_HANDLE_INPUT_FIX, Flags.FLAG_ENABLE_HOLD_TO_DRAG_APP_HANDLE, Flags.FLAG_ENABLE_INDEPENDENT_BACK_IN_PROJECTED, Flags.FLAG_ENABLE_INPUT_LAYER_TRANSITION_FIX, Flags.FLAG_ENABLE_MINIMIZE_BUTTON, Flags.FLAG_ENABLE_MODALS_FULLSCREEN_WITH_PERMISSION, Flags.FLAG_ENABLE_MODALS_FULLSCREEN_WITH_PLATFORM_SIGNATURE, Flags.FLAG_ENABLE_MOVE_TO_NEXT_DISPLAY_SHORTCUT, Flags.FLAG_ENABLE_MULTI_DISPLAY_SPLIT, Flags.FLAG_ENABLE_MULTIDISPLAY_TRACKPAD_BACK_GESTURE, Flags.FLAG_ENABLE_MULTIPLE_DESKTOPS_BACKEND, Flags.FLAG_ENABLE_MULTIPLE_DESKTOPS_FRONTEND, Flags.FLAG_ENABLE_NON_DEFAULT_DISPLAY_SPLIT, Flags.FLAG_ENABLE_OPAQUE_BACKGROUND_FOR_TRANSPARENT_WINDOWS, Flags.FLAG_ENABLE_PER_DISPLAY_DESKTOP_WALLPAPER_ACTIVITY, Flags.FLAG_ENABLE_PER_DISPLAY_PACKAGE_CONTEXT_CACHE_IN_STATUSBAR_NOTIF, Flags.FLAG_ENABLE_PERSISTING_DISPLAY_SIZE_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_PRESENTATION_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_PROJECTED_DISPLAY_DESKTOP_MODE, Flags.FLAG_ENABLE_QUICKSWITCH_DESKTOP_SPLIT_BUGFIX, Flags.FLAG_ENABLE_REQUEST_FULLSCREEN_BUGFIX, Flags.FLAG_ENABLE_RESIZING_METRICS, Flags.FLAG_ENABLE_RESTART_MENU_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_RESTORE_TO_PREVIOUS_SIZE_FROM_DESKTOP_IMMERSIVE, Flags.FLAG_ENABLE_SHELL_INITIAL_BOUNDS_REGRESSION_BUG_FIX, Flags.FLAG_ENABLE_SIZE_COMPAT_MODE_IMPROVEMENTS_FOR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_START_LAUNCH_TRANSITION_FROM_TASKBAR_BUGFIX, Flags.FLAG_ENABLE_TASK_RESIZING_KEYBOARD_SHORTCUTS, Flags.FLAG_ENABLE_TASK_STACK_OBSERVER_IN_SHELL, Flags.FLAG_ENABLE_TASKBAR_CONNECTED_DISPLAYS, Flags.FLAG_ENABLE_TASKBAR_OVERFLOW, Flags.FLAG_ENABLE_TASKBAR_RECENTS_LAYOUT_TRANSITION, Flags.FLAG_ENABLE_THEMED_APP_HEADERS, Flags.FLAG_ENABLE_TILE_RESIZING, Flags.FLAG_ENABLE_TOP_VISIBLE_ROOT_TASK_PER_USER_TRACKING, Flags.FLAG_ENABLE_VISUAL_INDICATOR_IN_TRANSITION_BUGFIX, Flags.FLAG_ENABLE_WINDOW_CONTEXT_RESOURCES_UPDATE_ON_CONFIG_CHANGE, Flags.FLAG_ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS, Flags.FLAG_ENABLE_WINDOWING_EDGE_DRAG_RESIZE, Flags.FLAG_ENABLE_WINDOWING_SCALED_RESIZING, Flags.FLAG_ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS, Flags.FLAG_ENFORCE_EDGE_TO_EDGE, Flags.FLAG_ENSURE_KEYGUARD_DOES_TRANSITION_STARTING, Flags.FLAG_ENSURE_WALLPAPER_IN_TRANSITIONS, Flags.FLAG_ENSURE_WALLPAPER_IN_WEAR_TRANSITIONS, Flags.FLAG_ENTER_DESKTOP_BY_DEFAULT_ON_FREEFORM_DISPLAYS, Flags.FLAG_EXCLUDE_CAPTION_FROM_APP_BOUNDS, Flags.FLAG_EXCLUDE_DRAWING_APP_THEME_SNAPSHOT_FROM_LOCK, Flags.FLAG_EXCLUDE_TASK_FROM_RECENTS, Flags.FLAG_FIFO_PRIORITY_FOR_MAJOR_UI_PROCESSES, Flags.FLAG_FIX_HIDE_OVERLAY_API, Flags.FLAG_FIX_LAYOUT_EXISTING_TASK, Flags.FLAG_FIX_VIEW_ROOT_CALL_TRACE, Flags.FLAG_FORCE_CLOSE_TOP_TRANSPARENT_FULLSCREEN_TASK, Flags.FLAG_FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH, Flags.FLAG_GET_DIMMER_ON_CLOSING, Flags.FLAG_IGNORE_ASPECT_RATIO_RESTRICTIONS_FOR_RESIZEABLE_FREEFORM_ACTIVITIES, Flags.FLAG_IGNORE_CORNER_RADIUS_AND_SHADOWS, Flags.FLAG_INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC, Flags.FLAG_INHERIT_TASK_BOUNDS_FOR_TRAMPOLINE_TASK_LAUNCHES, Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION, Flags.FLAG_JANK_API, Flags.FLAG_KEYBOARD_SHORTCUTS_TO_SWITCH_DESKS, Flags.FLAG_KEYGUARD_GOING_AWAY_TIMEOUT, Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER, Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION, Flags.FLAG_MOVE_TO_EXTERNAL_DISPLAY_SHORTCUT, Flags.FLAG_MULTI_CROP, Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT, Flags.FLAG_NESTED_TASKS_WITH_INDEPENDENT_BOUNDS, Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS, Flags.FLAG_NO_DUPLICATE_SURFACE_DESTROYED_EVENTS, Flags.FLAG_NO_VISIBILITY_EVENT_ON_DISPLAY_STATE_CHANGE, Flags.FLAG_OFFLOAD_COLOR_EXTRACTION, Flags.FLAG_PORT_WINDOW_SIZE_ANIMATION, Flags.FLAG_PREDICTIVE_BACK_DEFAULT_ENABLE_SDK_36, Flags.FLAG_PREDICTIVE_BACK_PRIORITY_SYSTEM_NAVIGATION_OBSERVER, Flags.FLAG_PREDICTIVE_BACK_SWIPE_EDGE_NONE_API, Flags.FLAG_PREDICTIVE_BACK_SYSTEM_OVERRIDE_CALLBACK, Flags.FLAG_PREDICTIVE_BACK_THREE_BUTTON_NAV, Flags.FLAG_PREDICTIVE_BACK_TIMESTAMP_API, Flags.FLAG_PROCESS_PRIORITY_POLICY_FOR_MULTI_WINDOW_MODE, Flags.FLAG_REAR_DISPLAY_DISABLE_FORCE_DESKTOP_SYSTEM_DECORATIONS, Flags.FLAG_RECORD_TASK_SNAPSHOTS_BEFORE_SHUTDOWN, Flags.FLAG_REDUCE_CHANGED_EXCLUSION_RECTS_MSGS, Flags.FLAG_REDUCE_KEYGUARD_TRANSITIONS, Flags.FLAG_REDUCE_TASK_SNAPSHOT_MEMORY_USAGE, Flags.FLAG_REDUCE_UNNECESSARY_MEASURE, Flags.FLAG_RELATIVE_INSETS, Flags.FLAG_RELEASE_SNAPSHOT_AGGRESSIVELY, Flags.FLAG_RELEASE_USER_ASPECT_RATIO_WM, Flags.FLAG_REMOVE_ACTIVITY_STARTER_DREAM_CALLBACK, Flags.FLAG_REMOVE_DEFER_HIDING_CLIENT, Flags.FLAG_REMOVE_DEPART_TARGET_FROM_MOTION, Flags.FLAG_REPARENT_WINDOW_TOKEN_API, Flags.FLAG_RESPECT_NON_TOP_VISIBLE_FIXED_ORIENTATION, Flags.FLAG_RESPECT_ORIENTATION_CHANGE_FOR_UNRESIZEABLE, Flags.FLAG_SAFE_REGION_LETTERBOXING, Flags.FLAG_SAFE_RELEASE_SNAPSHOT_AGGRESSIVELY, Flags.FLAG_SCHEDULING_FOR_NOTIFICATION_SHADE, Flags.FLAG_SCRAMBLE_SNAPSHOT_FILE_NAME, Flags.FLAG_SCREEN_RECORDING_CALLBACKS, Flags.FLAG_SCROLLING_FROM_LETTERBOX, Flags.FLAG_SDK_DESIRED_PRESENT_TIME, Flags.FLAG_SET_SC_PROPERTIES_IN_CLIENT, Flags.FLAG_SHOW_APP_HANDLE_LARGE_SCREENS, Flags.FLAG_SHOW_DESKTOP_EXPERIENCE_DEV_OPTION, Flags.FLAG_SHOW_DESKTOP_WINDOWING_DEV_OPTION, Flags.FLAG_SHOW_HOME_BEHIND_DESKTOP, Flags.FLAG_SKIP_COMPAT_UI_EDUCATION_IN_DESKTOP_MODE, Flags.FLAG_SKIP_DECOR_VIEW_RELAYOUT_WHEN_CLOSING_BUGFIX, Flags.FLAG_SUPPORT_WIDGET_INTENTS_ON_CONNECTED_DISPLAY, Flags.FLAG_SUPPORTS_DRAG_ASSISTANT_TO_MULTIWINDOW, Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI, Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER, Flags.FLAG_SURFACE_TRUSTED_OVERLAY, Flags.FLAG_SYNC_SCREEN_CAPTURE, Flags.FLAG_SYSTEM_UI_POST_ANIMATION_END, Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG, Flags.FLAG_TOUCH_PASS_THROUGH_OPT_IN, Flags.FLAG_TRACK_SYSTEM_UI_CONTEXT_BEFORE_WMS, Flags.FLAG_TRANSIT_READY_TRACKING, Flags.FLAG_TRANSIT_TRACKER_PLUMBING, Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW, Flags.FLAG_UNIFY_BACK_NAVIGATION_TRANSITION, Flags.FLAG_UNIVERSAL_RESIZABLE_BY_DEFAULT, Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION, Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING, Flags.FLAG_UPDATE_DIMS_WHEN_WINDOW_SHOWN, Flags.FLAG_USE_CACHED_INSETS_FOR_DISPLAY_SWITCH, Flags.FLAG_USE_RT_FRAME_CALLBACK_FOR_SPLASH_SCREEN_TRANSFER, Flags.FLAG_USE_TASKS_DIM_ONLY, Flags.FLAG_USE_VISIBLE_REQUESTED_FOR_PROCESS_TRACKER, Flags.FLAG_USE_WINDOW_ORIGINAL_TOUCHABLE_REGION_WHEN_MAGNIFICATION_RECOMPUTE_BOUNDS, Flags.FLAG_VDM_FORCE_APP_UNIVERSAL_RESIZABLE_API, Flags.FLAG_WALLPAPER_OFFSET_ASYNC, Flags.FLAG_WLINFO_ONCREATE);
    }
}
