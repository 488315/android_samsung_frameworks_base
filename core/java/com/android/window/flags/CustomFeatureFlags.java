package com.android.window.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ACTIVITY_EMBEDDING_ANIMATION_CUSTOMIZATION_FLAG,
                            Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG,
                            Flags.FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG,
                            Flags.FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT,
                            Flags.FLAG_ACTIVITY_WINDOW_INFO_FLAG,
                            Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK,
                            Flags.FLAG_ALLOW_HIDE_SCM_BUTTON,
                            Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT,
                            Flags.FLAG_ALWAYS_DEFER_TRANSITION_WHEN_APPLY_WCT,
                            Flags.FLAG_ALWAYS_DRAW_MAGNIFICATION_FULLSCREEN_BORDER,
                            Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION,
                            Flags.FLAG_APP_COMPAT_PROPERTIES_API,
                            Flags.FLAG_APP_COMPAT_REFACTORING,
                            Flags.FLAG_APP_COMPAT_UI_FRAMEWORK,
                            Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG,
                            Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK,
                            Flags.FLAG_BAL_IMPROVED_METRICS,
                            Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR,
                            Flags.FLAG_BAL_REQUIRE_OPT_IN_SAME_UID,
                            Flags
                                    .FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID,
                            Flags.FLAG_BAL_SHOW_TOASTS,
                            Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED,
                            Flags.FLAG_BLAST_SYNC_NOTIFICATION_SHADE_ON_DISPLAY_SWITCH,
                            Flags.FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG,
                            Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM,
                            Flags.FLAG_CAMERA_COMPAT_FULLSCREEN_PICK_SAME_TASK_ACTIVITY,
                            Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR,
                            Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT,
                            Flags.FLAG_COVER_DISPLAY_OPT_IN,
                            Flags.FLAG_DEFER_DISPLAY_UPDATES,
                            Flags
                                    .FLAG_DELAY_NOTIFICATION_TO_MAGNIFICATION_WHEN_RECENTS_WINDOW_TO_FRONT_TRANSITION,
                            Flags.FLAG_DELEGATE_UNHANDLED_DRAGS,
                            Flags.FLAG_DELETE_CAPTURE_DISPLAY,
                            Flags.FLAG_DENSITY_390_API,
                            Flags.FLAG_DISABLE_OBJECT_POOL,
                            Flags.FLAG_DISABLE_THIN_LETTERBOXING_POLICY,
                            Flags
                                    .FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS,
                            Flags.FLAG_DRAW_SNAPSHOT_ASPECT_RATIO_MATCH,
                            Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT,
                            Flags.FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG,
                            Flags.FLAG_ENABLE_ADDITIONAL_WINDOWS_ABOVE_STATUS_BAR,
                            Flags.FLAG_ENABLE_APP_HEADER_WITH_TASK_DENSITY,
                            Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY,
                            Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING,
                            Flags.FLAG_ENABLE_COMPATUI_SYSUI_LAUNCHER,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODALS_POLICY,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SCVH_CACHE,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASK_LIMIT,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS,
                            Flags.FLAG_ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY,
                            Flags.FLAG_ENABLE_SCALED_RESIZING,
                            Flags.FLAG_ENABLE_TASK_STACK_OBSERVER_IN_SHELL,
                            Flags.FLAG_ENABLE_THEMED_APP_HEADERS,
                            Flags.FLAG_ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS,
                            Flags.FLAG_ENABLE_WINDOWING_EDGE_DRAG_RESIZE,
                            Flags.FLAG_ENABLE_WM_EXTENSIONS_FOR_ALL_FLAG,
                            Flags.FLAG_ENFORCE_EDGE_TO_EDGE,
                            Flags.FLAG_ENSURE_WALLPAPER_IN_TRANSITIONS,
                            Flags.FLAG_EXPLICIT_REFRESH_RATE_HINTS,
                            Flags.FLAG_FIFO_PRIORITY_FOR_MAJOR_UI_PROCESSES,
                            Flags.FLAG_FIX_NO_CONTAINER_UPDATE_WITHOUT_RESIZE,
                            Flags.FLAG_FIX_PIP_RESTORE_TO_OVERLAY,
                            Flags.FLAG_FULLSCREEN_DIM_FLAG,
                            Flags.FLAG_GET_DIMMER_ON_CLOSING,
                            Flags.FLAG_IMMERSIVE_APP_REPOSITIONING,
                            Flags.FLAG_INSETS_CONTROL_CHANGED_ITEM,
                            Flags.FLAG_INSETS_CONTROL_SEQ,
                            Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION,
                            Flags.FLAG_KEYGUARD_APPEAR_TRANSITION,
                            Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER,
                            Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION,
                            Flags.FLAG_MOVE_ANIMATION_OPTIONS_TO_CHANGE,
                            Flags.FLAG_MULTI_CROP,
                            Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT,
                            Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS,
                            Flags.FLAG_NO_VISIBILITY_EVENT_ON_DISPLAY_STATE_CHANGE,
                            Flags.FLAG_OFFLOAD_COLOR_EXTRACTION,
                            Flags.FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS,
                            Flags.FLAG_REAR_DISPLAY_DISABLE_FORCE_DESKTOP_SYSTEM_DECORATIONS,
                            Flags.FLAG_RELEASE_SNAPSHOT_AGGRESSIVELY,
                            Flags.FLAG_REMOVE_PREPARE_SURFACE_IN_PLACEMENT,
                            Flags.FLAG_RESPECT_NON_TOP_VISIBLE_FIXED_ORIENTATION,
                            Flags.FLAG_SCREEN_RECORDING_CALLBACKS,
                            Flags.FLAG_SDK_DESIRED_PRESENT_TIME,
                            Flags.FLAG_SECURE_WINDOW_STATE,
                            Flags.FLAG_SET_SC_PROPERTIES_IN_CLIENT,
                            Flags.FLAG_SKIP_SLEEPING_WHEN_SWITCHING_DISPLAY,
                            Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI,
                            Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER,
                            Flags.FLAG_SURFACE_TRUSTED_OVERLAY,
                            Flags.FLAG_SYNC_SCREEN_CAPTURE,
                            Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG,
                            Flags.FLAG_TRANSIT_READY_TRACKING,
                            Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW,
                            Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION,
                            Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING,
                            Flags.FLAG_USE_TASKS_DIM_ONLY,
                            Flags
                                    .FLAG_USE_WINDOW_ORIGINAL_TOUCHABLE_REGION_WHEN_MAGNIFICATION_RECOMPUTE_BOUNDS,
                            Flags.FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT,
                            Flags.FLAG_WAIT_FOR_TRANSITION_ON_DISPLAY_SWITCH,
                            Flags.FLAG_WALLPAPER_OFFSET_ASYNC,
                            Flags.FLAG_WINDOW_SESSION_RELAYOUT_INFO,
                            Flags.FLAG_WINDOW_TOKEN_CONFIG_THREAD_SAFE,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingAnimationCustomizationFlag() {
        return getValue(
                Flags.FLAG_ACTIVITY_EMBEDDING_ANIMATION_CUSTOMIZATION_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda68
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).activityEmbeddingAnimationCustomizationFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingInteractiveDividerFlag() {
        return getValue(
                Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).activityEmbeddingInteractiveDividerFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingOverlayPresentationFlag() {
        return getValue(
                Flags.FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda80
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).activityEmbeddingOverlayPresentationFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activitySnapshotByDefault() {
        return getValue(
                Flags.FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).activitySnapshotByDefault();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activityWindowInfoFlag() {
        return getValue(
                Flags.FLAG_ACTIVITY_WINDOW_INFO_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda100
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).activityWindowInfoFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean allowDisableActivityRecordInputSink() {
        return getValue(
                Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowDisableActivityRecordInputSink();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean allowHideScmButton() {
        return getValue(
                Flags.FLAG_ALLOW_HIDE_SCM_BUTTON,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowHideScmButton();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean allowsScreenSizeDecoupledFromStatusBarAndCutout() {
        return getValue(
                Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda54
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .allowsScreenSizeDecoupledFromStatusBarAndCutout();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean alwaysDeferTransitionWhenApplyWct() {
        return getValue(
                Flags.FLAG_ALWAYS_DEFER_TRANSITION_WHEN_APPLY_WCT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda99
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).alwaysDeferTransitionWhenApplyWct();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean alwaysDrawMagnificationFullscreenBorder() {
        return getValue(
                Flags.FLAG_ALWAYS_DRAW_MAGNIFICATION_FULLSCREEN_BORDER,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).alwaysDrawMagnificationFullscreenBorder();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean alwaysUpdateWallpaperPermission() {
        return getValue(
                Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda60
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).alwaysUpdateWallpaperPermission();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean appCompatPropertiesApi() {
        return getValue(
                Flags.FLAG_APP_COMPAT_PROPERTIES_API,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda46
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).appCompatPropertiesApi();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean appCompatRefactoring() {
        return getValue(
                Flags.FLAG_APP_COMPAT_REFACTORING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).appCompatRefactoring();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean appCompatUiFramework() {
        return getValue(
                Flags.FLAG_APP_COMPAT_UI_FRAMEWORK,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda61
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).appCompatUiFramework();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balDontBringExistingBackgroundTaskStackToFg() {
        return getValue(
                Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).balDontBringExistingBackgroundTaskStackToFg();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balImproveRealCallerVisibilityCheck() {
        return getValue(
                Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda75
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).balImproveRealCallerVisibilityCheck();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balImprovedMetrics() {
        return getValue(
                Flags.FLAG_BAL_IMPROVED_METRICS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda72
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).balImprovedMetrics();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balRequireOptInByPendingIntentCreator() {
        return getValue(
                Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).balRequireOptInByPendingIntentCreator();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balRequireOptInSameUid() {
        return getValue(
                Flags.FLAG_BAL_REQUIRE_OPT_IN_SAME_UID,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda66
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).balRequireOptInSameUid();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balRespectAppSwitchStateWhenCheckBoundByForegroundUid() {
        return getValue(
                Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .balRespectAppSwitchStateWhenCheckBoundByForegroundUid();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balShowToasts() {
        return getValue(
                Flags.FLAG_BAL_SHOW_TOASTS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda55
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).balShowToasts();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balShowToastsBlocked() {
        return getValue(
                Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda53
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).balShowToastsBlocked();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean blastSyncNotificationShadeOnDisplaySwitch() {
        return getValue(
                Flags.FLAG_BLAST_SYNC_NOTIFICATION_SHADE_ON_DISPLAY_SWITCH,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda47
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).blastSyncNotificationShadeOnDisplaySwitch();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean bundleClientTransactionFlag() {
        return getValue(
                Flags.FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda67
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).bundleClientTransactionFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean cameraCompatForFreeform() {
        return getValue(
                Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda51
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).cameraCompatForFreeform();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean cameraCompatFullscreenPickSameTaskActivity() {
        return getValue(
                Flags.FLAG_CAMERA_COMPAT_FULLSCREEN_PICK_SAME_TASK_ACTIVITY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).cameraCompatFullscreenPickSameTaskActivity();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean closeToSquareConfigIncludesStatusBar() {
        return getValue(
                Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).closeToSquareConfigIncludesStatusBar();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean configurableFontScaleDefault() {
        return getValue(
                Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda82
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).configurableFontScaleDefault();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean coverDisplayOptIn() {
        return getValue(
                Flags.FLAG_COVER_DISPLAY_OPT_IN,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).coverDisplayOptIn();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean deferDisplayUpdates() {
        return getValue(
                Flags.FLAG_DEFER_DISPLAY_UPDATES,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda78
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deferDisplayUpdates();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean delayNotificationToMagnificationWhenRecentsWindowToFrontTransition() {
        return getValue(
                Flags
                        .FLAG_DELAY_NOTIFICATION_TO_MAGNIFICATION_WHEN_RECENTS_WINDOW_TO_FRONT_TRANSITION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .delayNotificationToMagnificationWhenRecentsWindowToFrontTransition();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean delegateUnhandledDrags() {
        return getValue(
                Flags.FLAG_DELEGATE_UNHANDLED_DRAGS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda103
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).delegateUnhandledDrags();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean deleteCaptureDisplay() {
        return getValue(
                Flags.FLAG_DELETE_CAPTURE_DISPLAY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda91
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deleteCaptureDisplay();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean density390Api() {
        return getValue(
                Flags.FLAG_DENSITY_390_API,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda52
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).density390Api();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean disableObjectPool() {
        return getValue(
                Flags.FLAG_DISABLE_OBJECT_POOL,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda70
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disableObjectPool();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean disableThinLetterboxingPolicy() {
        return getValue(
                Flags.FLAG_DISABLE_THIN_LETTERBOXING_POLICY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda41
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disableThinLetterboxingPolicy();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean doNotCheckIntersectionWhenNonMagnifiableWindowTransitions() {
        return getValue(
                Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda37
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .doNotCheckIntersectionWhenNonMagnifiableWindowTransitions();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean drawSnapshotAspectRatioMatch() {
        return getValue(
                Flags.FLAG_DRAW_SNAPSHOT_ASPECT_RATIO_MATCH,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda40
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).drawSnapshotAspectRatioMatch();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean edgeToEdgeByDefault() {
        return getValue(
                Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda93
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).edgeToEdgeByDefault();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean embeddedActivityBackNavFlag() {
        return getValue(
                Flags.FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).embeddedActivityBackNavFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableAdditionalWindowsAboveStatusBar() {
        return getValue(
                Flags.FLAG_ENABLE_ADDITIONAL_WINDOWS_ABOVE_STATUS_BAR,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda42
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableAdditionalWindowsAboveStatusBar();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableAppHeaderWithTaskDensity() {
        return getValue(
                Flags.FLAG_ENABLE_APP_HEADER_WITH_TASK_DENSITY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableAppHeaderWithTaskDensity();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableBufferTransformHintFromDisplay() {
        return getValue(
                Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda58
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableBufferTransformHintFromDisplay();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableCameraCompatForDesktopWindowing() {
        return getValue(
                Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableCameraCompatForDesktopWindowing();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableCompatuiSysuiLauncher() {
        return getValue(
                Flags.FLAG_ENABLE_COMPATUI_SYSUI_LAUNCHER,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda102
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableCompatuiSysuiLauncher();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingImmersiveHandleHiding() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda92
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingImmersiveHandleHiding();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingModalsPolicy() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODALS_POLICY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda45
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingModalsPolicy();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingMode() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda36
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingMode();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingQuickSwitch() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingQuickSwitch();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingScvhCache() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SCVH_CACHE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda43
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingScvhCache();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingSizeConstraints() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingSizeConstraints();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingTaskLimit() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASK_LIMIT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingTaskLimit();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingTaskbarRunningApps() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda79
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingTaskbarRunningApps();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingWallpaperActivity() {
        return getValue(
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda87
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableDesktopWindowingWallpaperActivity();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableScaledResizing() {
        return getValue(
                Flags.FLAG_ENABLE_SCALED_RESIZING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda48
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableScaledResizing();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableTaskStackObserverInShell() {
        return getValue(
                Flags.FLAG_ENABLE_TASK_STACK_OBSERVER_IN_SHELL,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda35
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableTaskStackObserverInShell();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableThemedAppHeaders() {
        return getValue(
                Flags.FLAG_ENABLE_THEMED_APP_HEADERS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda63
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableThemedAppHeaders();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableWindowingDynamicInitialBounds() {
        return getValue(
                Flags.FLAG_ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda97
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableWindowingDynamicInitialBounds();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableWindowingEdgeDragResize() {
        return getValue(
                Flags.FLAG_ENABLE_WINDOWING_EDGE_DRAG_RESIZE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda59
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableWindowingEdgeDragResize();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableWmExtensionsForAllFlag() {
        return getValue(
                Flags.FLAG_ENABLE_WM_EXTENSIONS_FOR_ALL_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda81
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableWmExtensionsForAllFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enforceEdgeToEdge() {
        return getValue(
                Flags.FLAG_ENFORCE_EDGE_TO_EDGE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enforceEdgeToEdge();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean ensureWallpaperInTransitions() {
        return getValue(
                Flags.FLAG_ENSURE_WALLPAPER_IN_TRANSITIONS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).ensureWallpaperInTransitions();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean explicitRefreshRateHints() {
        return getValue(
                Flags.FLAG_EXPLICIT_REFRESH_RATE_HINTS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).explicitRefreshRateHints();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean fifoPriorityForMajorUiProcesses() {
        return getValue(
                Flags.FLAG_FIFO_PRIORITY_FOR_MAJOR_UI_PROCESSES,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fifoPriorityForMajorUiProcesses();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean fixNoContainerUpdateWithoutResize() {
        return getValue(
                Flags.FLAG_FIX_NO_CONTAINER_UPDATE_WITHOUT_RESIZE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda33
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixNoContainerUpdateWithoutResize();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean fixPipRestoreToOverlay() {
        return getValue(
                Flags.FLAG_FIX_PIP_RESTORE_TO_OVERLAY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda85
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixPipRestoreToOverlay();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean fullscreenDimFlag() {
        return getValue(
                Flags.FLAG_FULLSCREEN_DIM_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda32
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fullscreenDimFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean getDimmerOnClosing() {
        return getValue(
                Flags.FLAG_GET_DIMMER_ON_CLOSING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda96
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).getDimmerOnClosing();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean immersiveAppRepositioning() {
        return getValue(
                Flags.FLAG_IMMERSIVE_APP_REPOSITIONING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).immersiveAppRepositioning();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean insetsControlChangedItem() {
        return getValue(
                Flags.FLAG_INSETS_CONTROL_CHANGED_ITEM,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda95
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).insetsControlChangedItem();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean insetsControlSeq() {
        return getValue(
                Flags.FLAG_INSETS_CONTROL_SEQ,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).insetsControlSeq();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean insetsDecoupledConfiguration() {
        return getValue(
                Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).insetsDecoupledConfiguration();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean keyguardAppearTransition() {
        return getValue(
                Flags.FLAG_KEYGUARD_APPEAR_TRANSITION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda69
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).keyguardAppearTransition();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean letterboxBackgroundWallpaper() {
        return getValue(
                Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda106
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).letterboxBackgroundWallpaper();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean movableCutoutConfiguration() {
        return getValue(
                Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda88
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).movableCutoutConfiguration();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean moveAnimationOptionsToChange() {
        return getValue(
                Flags.FLAG_MOVE_ANIMATION_OPTIONS_TO_CHANGE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda76
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).moveAnimationOptionsToChange();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean multiCrop() {
        return getValue(
                Flags.FLAG_MULTI_CROP,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda104
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).multiCrop();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean navBarTransparentByDefault() {
        return getValue(
                Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda65
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).navBarTransparentByDefault();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean noConsecutiveVisibilityEvents() {
        return getValue(
                Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda64
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).noConsecutiveVisibilityEvents();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean noVisibilityEventOnDisplayStateChange() {
        return getValue(
                Flags.FLAG_NO_VISIBILITY_EVENT_ON_DISPLAY_STATE_CHANGE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda56
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).noVisibilityEventOnDisplayStateChange();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean offloadColorExtraction() {
        return getValue(
                Flags.FLAG_OFFLOAD_COLOR_EXTRACTION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda74
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).offloadColorExtraction();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean predictiveBackSystemAnims() {
        return getValue(
                Flags.FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda84
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).predictiveBackSystemAnims();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean rearDisplayDisableForceDesktopSystemDecorations() {
        return getValue(
                Flags.FLAG_REAR_DISPLAY_DISABLE_FORCE_DESKTOP_SYSTEM_DECORATIONS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda57
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .rearDisplayDisableForceDesktopSystemDecorations();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean releaseSnapshotAggressively() {
        return getValue(
                Flags.FLAG_RELEASE_SNAPSHOT_AGGRESSIVELY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda50
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).releaseSnapshotAggressively();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean removePrepareSurfaceInPlacement() {
        return getValue(
                Flags.FLAG_REMOVE_PREPARE_SURFACE_IN_PLACEMENT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda73
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).removePrepareSurfaceInPlacement();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean respectNonTopVisibleFixedOrientation() {
        return getValue(
                Flags.FLAG_RESPECT_NON_TOP_VISIBLE_FIXED_ORIENTATION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda39
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).respectNonTopVisibleFixedOrientation();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean screenRecordingCallbacks() {
        return getValue(
                Flags.FLAG_SCREEN_RECORDING_CALLBACKS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda44
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).screenRecordingCallbacks();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean sdkDesiredPresentTime() {
        return getValue(
                Flags.FLAG_SDK_DESIRED_PRESENT_TIME,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda49
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).sdkDesiredPresentTime();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean secureWindowState() {
        return getValue(
                Flags.FLAG_SECURE_WINDOW_STATE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda86
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).secureWindowState();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean setScPropertiesInClient() {
        return getValue(
                Flags.FLAG_SET_SC_PROPERTIES_IN_CLIENT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).setScPropertiesInClient();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean skipSleepingWhenSwitchingDisplay() {
        return getValue(
                Flags.FLAG_SKIP_SLEEPING_WHEN_SWITCHING_DISPLAY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda38
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).skipSleepingWhenSwitchingDisplay();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean supportsMultiInstanceSystemUi() {
        return getValue(
                Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda89
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).supportsMultiInstanceSystemUi();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean surfaceControlInputReceiver() {
        return getValue(
                Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda34
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).surfaceControlInputReceiver();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean surfaceTrustedOverlay() {
        return getValue(
                Flags.FLAG_SURFACE_TRUSTED_OVERLAY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda101
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).surfaceTrustedOverlay();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean syncScreenCapture() {
        return getValue(
                Flags.FLAG_SYNC_SCREEN_CAPTURE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda62
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).syncScreenCapture();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean taskFragmentSystemOrganizerFlag() {
        return getValue(
                Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda90
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).taskFragmentSystemOrganizerFlag();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean transitReadyTracking() {
        return getValue(
                Flags.FLAG_TRANSIT_READY_TRACKING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda83
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).transitReadyTracking();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean trustedPresentationListenerForWindow() {
        return getValue(
                Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda71
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).trustedPresentationListenerForWindow();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean untrustedEmbeddingAnyAppPermission() {
        return getValue(
                Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda98
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).untrustedEmbeddingAnyAppPermission();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean untrustedEmbeddingStateSharing() {
        return getValue(
                Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).untrustedEmbeddingStateSharing();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean useTasksDimOnly() {
        return getValue(
                Flags.FLAG_USE_TASKS_DIM_ONLY,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda105
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).useTasksDimOnly();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean useWindowOriginalTouchableRegionWhenMagnificationRecomputeBounds() {
        return getValue(
                Flags.FLAG_USE_WINDOW_ORIGINAL_TOUCHABLE_REGION_WHEN_MAGNIFICATION_RECOMPUTE_BOUNDS,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda94
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .useWindowOriginalTouchableRegionWhenMagnificationRecomputeBounds();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean userMinAspectRatioAppDefault() {
        return getValue(
                Flags.FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).userMinAspectRatioAppDefault();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean waitForTransitionOnDisplaySwitch() {
        return getValue(
                Flags.FLAG_WAIT_FOR_TRANSITION_ON_DISPLAY_SWITCH,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda77
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).waitForTransitionOnDisplaySwitch();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean wallpaperOffsetAsync() {
        return getValue(
                Flags.FLAG_WALLPAPER_OFFSET_ASYNC,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).wallpaperOffsetAsync();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean windowSessionRelayoutInfo() {
        return getValue(
                Flags.FLAG_WINDOW_SESSION_RELAYOUT_INFO,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).windowSessionRelayoutInfo();
                    }
                });
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean windowTokenConfigThreadSafe() {
        return getValue(
                Flags.FLAG_WINDOW_TOKEN_CONFIG_THREAD_SAFE,
                new Predicate() { // from class:
                                  // com.android.window.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).windowTokenConfigThreadSafe();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ACTIVITY_EMBEDDING_ANIMATION_CUSTOMIZATION_FLAG,
                Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG,
                Flags.FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG,
                Flags.FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT,
                Flags.FLAG_ACTIVITY_WINDOW_INFO_FLAG,
                Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK,
                Flags.FLAG_ALLOW_HIDE_SCM_BUTTON,
                Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT,
                Flags.FLAG_ALWAYS_DEFER_TRANSITION_WHEN_APPLY_WCT,
                Flags.FLAG_ALWAYS_DRAW_MAGNIFICATION_FULLSCREEN_BORDER,
                Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION,
                Flags.FLAG_APP_COMPAT_PROPERTIES_API,
                Flags.FLAG_APP_COMPAT_REFACTORING,
                Flags.FLAG_APP_COMPAT_UI_FRAMEWORK,
                Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG,
                Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK,
                Flags.FLAG_BAL_IMPROVED_METRICS,
                Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR,
                Flags.FLAG_BAL_REQUIRE_OPT_IN_SAME_UID,
                Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID,
                Flags.FLAG_BAL_SHOW_TOASTS,
                Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED,
                Flags.FLAG_BLAST_SYNC_NOTIFICATION_SHADE_ON_DISPLAY_SWITCH,
                Flags.FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG,
                Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM,
                Flags.FLAG_CAMERA_COMPAT_FULLSCREEN_PICK_SAME_TASK_ACTIVITY,
                Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR,
                Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT,
                Flags.FLAG_COVER_DISPLAY_OPT_IN,
                Flags.FLAG_DEFER_DISPLAY_UPDATES,
                Flags
                        .FLAG_DELAY_NOTIFICATION_TO_MAGNIFICATION_WHEN_RECENTS_WINDOW_TO_FRONT_TRANSITION,
                Flags.FLAG_DELEGATE_UNHANDLED_DRAGS,
                Flags.FLAG_DELETE_CAPTURE_DISPLAY,
                Flags.FLAG_DENSITY_390_API,
                Flags.FLAG_DISABLE_OBJECT_POOL,
                Flags.FLAG_DISABLE_THIN_LETTERBOXING_POLICY,
                Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS,
                Flags.FLAG_DRAW_SNAPSHOT_ASPECT_RATIO_MATCH,
                Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT,
                Flags.FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG,
                Flags.FLAG_ENABLE_ADDITIONAL_WINDOWS_ABOVE_STATUS_BAR,
                Flags.FLAG_ENABLE_APP_HEADER_WITH_TASK_DENSITY,
                Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY,
                Flags.FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING,
                Flags.FLAG_ENABLE_COMPATUI_SYSUI_LAUNCHER,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODALS_POLICY,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SCVH_CACHE,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASK_LIMIT,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS,
                Flags.FLAG_ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY,
                Flags.FLAG_ENABLE_SCALED_RESIZING,
                Flags.FLAG_ENABLE_TASK_STACK_OBSERVER_IN_SHELL,
                Flags.FLAG_ENABLE_THEMED_APP_HEADERS,
                Flags.FLAG_ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS,
                Flags.FLAG_ENABLE_WINDOWING_EDGE_DRAG_RESIZE,
                Flags.FLAG_ENABLE_WM_EXTENSIONS_FOR_ALL_FLAG,
                Flags.FLAG_ENFORCE_EDGE_TO_EDGE,
                Flags.FLAG_ENSURE_WALLPAPER_IN_TRANSITIONS,
                Flags.FLAG_EXPLICIT_REFRESH_RATE_HINTS,
                Flags.FLAG_FIFO_PRIORITY_FOR_MAJOR_UI_PROCESSES,
                Flags.FLAG_FIX_NO_CONTAINER_UPDATE_WITHOUT_RESIZE,
                Flags.FLAG_FIX_PIP_RESTORE_TO_OVERLAY,
                Flags.FLAG_FULLSCREEN_DIM_FLAG,
                Flags.FLAG_GET_DIMMER_ON_CLOSING,
                Flags.FLAG_IMMERSIVE_APP_REPOSITIONING,
                Flags.FLAG_INSETS_CONTROL_CHANGED_ITEM,
                Flags.FLAG_INSETS_CONTROL_SEQ,
                Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION,
                Flags.FLAG_KEYGUARD_APPEAR_TRANSITION,
                Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER,
                Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION,
                Flags.FLAG_MOVE_ANIMATION_OPTIONS_TO_CHANGE,
                Flags.FLAG_MULTI_CROP,
                Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT,
                Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS,
                Flags.FLAG_NO_VISIBILITY_EVENT_ON_DISPLAY_STATE_CHANGE,
                Flags.FLAG_OFFLOAD_COLOR_EXTRACTION,
                Flags.FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS,
                Flags.FLAG_REAR_DISPLAY_DISABLE_FORCE_DESKTOP_SYSTEM_DECORATIONS,
                Flags.FLAG_RELEASE_SNAPSHOT_AGGRESSIVELY,
                Flags.FLAG_REMOVE_PREPARE_SURFACE_IN_PLACEMENT,
                Flags.FLAG_RESPECT_NON_TOP_VISIBLE_FIXED_ORIENTATION,
                Flags.FLAG_SCREEN_RECORDING_CALLBACKS,
                Flags.FLAG_SDK_DESIRED_PRESENT_TIME,
                Flags.FLAG_SECURE_WINDOW_STATE,
                Flags.FLAG_SET_SC_PROPERTIES_IN_CLIENT,
                Flags.FLAG_SKIP_SLEEPING_WHEN_SWITCHING_DISPLAY,
                Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI,
                Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER,
                Flags.FLAG_SURFACE_TRUSTED_OVERLAY,
                Flags.FLAG_SYNC_SCREEN_CAPTURE,
                Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG,
                Flags.FLAG_TRANSIT_READY_TRACKING,
                Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW,
                Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION,
                Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING,
                Flags.FLAG_USE_TASKS_DIM_ONLY,
                Flags.FLAG_USE_WINDOW_ORIGINAL_TOUCHABLE_REGION_WHEN_MAGNIFICATION_RECOMPUTE_BOUNDS,
                Flags.FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT,
                Flags.FLAG_WAIT_FOR_TRANSITION_ON_DISPLAY_SWITCH,
                Flags.FLAG_WALLPAPER_OFFSET_ASYNC,
                Flags.FLAG_WINDOW_SESSION_RELAYOUT_INFO,
                Flags.FLAG_WINDOW_TOKEN_CONFIG_THREAD_SAFE);
    }
}
