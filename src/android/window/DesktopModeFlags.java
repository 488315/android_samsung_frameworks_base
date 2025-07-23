package android.window;

import android.app.ActivityThread;
import android.app.Application;
import android.content.ContentResolver;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.util.function.BooleanSupplier;

/* loaded from: classes5.dex */
public enum DesktopModeFlags {
    DISABLE_DESKTOP_LAUNCH_PARAMS_OUTSIDE_DESKTOP_BUG_FIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda0
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.disableDesktopLaunchParamsOutsideDesktopBugFix();
        }
    }, true),
    DISABLE_NON_RESIZABLE_APP_SNAP_RESIZE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda11
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.disableNonResizableAppSnapResizing();
        }
    }, true),
    ENABLE_ACCESSIBLE_CUSTOM_HEADERS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda22
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableAccessibleCustomHeaders();
        }
    }, true),
    ENABLE_APP_HEADER_WITH_TASK_DENSITY(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda33
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableAppHeaderWithTaskDensity();
        }
    }, true),
    ENABLE_CAMERA_COMPAT_SIMULATE_REQUESTED_ORIENTATION(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda44
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableCameraCompatForDesktopWindowing();
        }
    }, true),
    ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda55
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableCaptionCompatInsetForceConsumption();
        }
    }, true),
    ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda66
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableCaptionCompatInsetForceConsumptionAlways();
        }
    }, true),
    ENABLE_CASCADING_WINDOWS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda72
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableCascadingWindows();
        }
    }, true),
    ENABLE_DESKTOP_APP_HANDLE_ANIMATION(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda73
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopAppHandleAnimation();
        }
    }, true),
    ENABLE_DESKTOP_APP_LAUNCH_ALTTAB_TRANSITIONS_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda74
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopAppLaunchAlttabTransitionsBugfix();
        }
    }, true),
    ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda1
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopAppLaunchTransitionsBugfix();
        }
    }, true),
    ENABLE_DESKTOP_CLOSE_SHORTCUT_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda2
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopCloseShortcutBugfix();
        }
    }, false),
    ENABLE_DESKTOP_COMPAT_UI_VISIBILITY_STATUS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda3
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableCompatUiVisibilityStatus();
        }
    }, true),
    ENABLE_DESKTOP_IMMERSIVE_DRAG_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda4
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopImmersiveDragBugfix();
        }
    }, true),
    ENABLE_DESKTOP_INDICATOR_IN_SEPARATE_THREAD_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda5
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopIndicatorInSeparateThreadBugfix();
        }
    }, true),
    ENABLE_DESKTOP_OPENING_DEEPLINK_MINIMIZE_ANIMATION_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda6
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopOpeningDeeplinkMinimizeAnimationBugfix();
        }
    }, true),
    ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda7
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopRecentsTransitionsCornersBugfix();
        }
    }, true),
    ENABLE_DESKTOP_SKIP_COMPAT_UI_EDUCATION_IN_DESKTOP_MODE_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda8
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.skipCompatUiEducationInDesktopMode();
        }
    }, true),
    ENABLE_DESKTOP_SYSTEM_DIALOGS_TRANSITIONS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda9
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopSystemDialogsTransitions();
        }
    }, true),
    ENABLE_DESKTOP_TAB_TEARING_MINIMIZE_ANIMATION_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda10
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopTabTearingMinimizeAnimationBugfix();
        }
    }, true),
    ENABLE_DESKTOP_TRAMPOLINE_CLOSE_ANIMATION_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda12
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopTrampolineCloseAnimationBugfix();
        }
    }, true),
    ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda13
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWallpaperActivityForSystemUser();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_APP_TO_WEB(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda14
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingAppToWeb();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_APP_TO_WEB_EDUCATION(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda15
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingAppToWebEducation();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda16
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingBackNavigation();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_ENTER_TRANSITIONS_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda17
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingEnterTransitionBugfix();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_EXIT_BY_MINIMIZE_TRANSITION_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda18
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingExitByMinimizeTransitionBugfix();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda19
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingExitTransitionsBugfix();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_HSUM(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda20
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingHsum();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda21
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingImmersiveHandleHiding();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_MODALS_POLICY(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda23
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingModalsPolicy();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_MODE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda24
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingMode();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_MULTI_INSTANCE_FEATURES(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda25
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingMultiInstanceFeatures();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_PERSISTENCE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda26
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingPersistence();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_PIP(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda27
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingPip();
        }
    }, false),
    ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda28
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingQuickSwitch();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_SCVH_CACHE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda29
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingScvhCacheBugFix();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda30
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingSizeConstraints();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda31
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingTaskbarRunningApps();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_TASK_LIMIT(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda32
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingTaskLimit();
        }
    }, true),
    ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda34
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDesktopWindowingWallpaperActivity();
        }
    }, true),
    ENABLE_DRAG_RESIZE_SET_UP_IN_BG_THREAD(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda35
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDragResizeSetUpInBgThread();
        }
    }, true),
    ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda36
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDragToDesktopIncomingTransitionsBugfix();
        }
    }, true),
    ENABLE_FULLY_IMMERSIVE_IN_DESKTOP(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda37
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableFullyImmersiveInDesktop();
        }
    }, true),
    ENABLE_HANDLE_INPUT_FIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda38
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableHandleInputFix();
        }
    }, true),
    ENABLE_HOLD_TO_DRAG_APP_HANDLE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda39
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableHoldToDragAppHandle();
        }
    }, true),
    ENABLE_INPUT_LAYER_TRANSITION_FIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda40
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableInputLayerTransitionFix();
        }
    }, true),
    ENABLE_MINIMIZE_BUTTON(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda41
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableMinimizeButton();
        }
    }, true),
    ENABLE_MODALS_FULLSCREEN_WITH_PERMISSIONS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda42
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableModalsFullscreenWithPermission();
        }
    }, true),
    ENABLE_MODALS_FULLSCREEN_WITH_PLATFORM_SIGNATURE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda43
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableModalsFullscreenWithPlatformSignature();
        }
    }, true),
    ENABLE_OPAQUE_BACKGROUND_FOR_TRANSPARENT_WINDOWS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda45
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableOpaqueBackgroundForTransparentWindows();
        }
    }, true),
    ENABLE_QUICKSWITCH_DESKTOP_SPLIT_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda46
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableQuickswitchDesktopSplitBugfix();
        }
    }, true),
    ENABLE_REQUEST_FULLSCREEN_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda47
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableRequestFullscreenBugfix();
        }
    }, true),
    ENABLE_RESIZING_METRICS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda48
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableResizingMetrics();
        }
    }, true),
    ENABLE_RESTORE_TO_PREVIOUS_SIZE_FROM_DESKTOP_IMMERSIVE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda49
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableRestoreToPreviousSizeFromDesktopImmersive();
        }
    }, true),
    ENABLE_SHELL_INITIAL_BOUNDS_REGRESSION_BUG_FIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda50
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableShellInitialBoundsRegressionBugFix();
        }
    }, true),
    ENABLE_START_LAUNCH_TRANSITION_FROM_TASKBAR_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda51
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableStartLaunchTransitionFromTaskbarBugfix();
        }
    }, true),
    ENABLE_TASKBAR_OVERFLOW(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda52
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableTaskbarOverflow();
        }
    }, false),
    ENABLE_TASKBAR_RECENTS_LAYOUT_TRANSITION(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda53
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableTaskbarRecentsLayoutTransition();
        }
    }, false),
    ENABLE_TASK_RESIZING_KEYBOARD_SHORTCUTS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda54
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableTaskResizingKeyboardShortcuts();
        }
    }, true),
    ENABLE_TASK_STACK_OBSERVER_IN_SHELL(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda56
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableTaskStackObserverInShell();
        }
    }, true),
    ENABLE_THEMED_APP_HEADERS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda57
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableThemedAppHeaders();
        }
    }, true),
    ENABLE_TILE_RESIZING(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda58
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableTileResizing();
        }
    }, true),
    ENABLE_TOP_VISIBLE_ROOT_TASK_PER_USER_TRACKING(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda59
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableTopVisibleRootTaskPerUserTracking();
        }
    }, true),
    ENABLE_VISUAL_INDICATOR_IN_TRANSITION_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda60
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableVisualIndicatorInTransitionBugfix();
        }
    }, true),
    ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda61
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableWindowingDynamicInitialBounds();
        }
    }, true),
    ENABLE_WINDOWING_EDGE_DRAG_RESIZE(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda62
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableWindowingEdgeDragResize();
        }
    }, true),
    ENABLE_WINDOWING_SCALED_RESIZING(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda63
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableWindowingScaledResizing();
        }
    }, true),
    ENABLE_WINDOWING_TRANSITION_HANDLERS_OBSERVERS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda64
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableWindowingTransitionHandlersObservers();
        }
    }, false),
    EXCLUDE_CAPTION_FROM_APP_BOUNDS(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda65
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.excludeCaptionFromAppBounds();
        }
    }, true),
    FORCE_CLOSE_TOP_TRANSPARENT_FULLSCREEN_TASK(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda67
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.forceCloseTopTransparentFullscreenTask();
        }
    }, false),
    IGNORE_ASPECT_RATIO_RESTRICTIONS_FOR_RESIZEABLE_FREEFORM_ACTIVITIES(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda68
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.ignoreAspectRatioRestrictionsForResizeableFreeformActivities();
        }
    }, true),
    INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda69
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.includeTopTransparentFullscreenTaskInDesktopHeuristic();
        }
    }, true),
    INHERIT_TASK_BOUNDS_FOR_TRAMPOLINE_TASK_LAUNCHES(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda70
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.inheritTaskBoundsForTrampolineTaskLaunches();
        }
    }, true),
    SKIP_DECOR_VIEW_RELAYOUT_WHEN_CLOSING_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopModeFlags$$ExternalSyntheticLambda71
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.skipDecorViewRelayoutWhenClosingBugfix();
        }
    }, true);

    public static final String SYSTEM_PROPERTY_NAME = "persist.wm.debug.desktop_experience_devopts";
    private static final String TAG = "DesktopModeFlags";
    private static ToggleOverride sCachedToggleOverride;
    private final BooleanSupplier mFlagFunction;
    private final boolean mShouldOverrideByDevOption;

    public static class DesktopModeFlag {
        private final BooleanSupplier mFlagFunction;
        private final boolean mShouldOverrideByDevOption;

        public DesktopModeFlag(BooleanSupplier booleanSupplier, boolean z) {
            this.mFlagFunction = booleanSupplier;
            this.mShouldOverrideByDevOption = z;
        }

        public boolean isTrue() {
            return DesktopModeFlags.isFlagTrue(this.mFlagFunction, this.mShouldOverrideByDevOption);
        }
    }

    DesktopModeFlags(BooleanSupplier booleanSupplier, boolean z) {
        this.mFlagFunction = booleanSupplier;
        this.mShouldOverrideByDevOption = z;
    }

    public boolean isTrue() {
        return isFlagTrue(this.mFlagFunction, this.mShouldOverrideByDevOption);
    }

    public static boolean isDesktopModeForcedEnabled() {
        return getToggleOverride() == ToggleOverride.OVERRIDE_ON;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFlagTrue(BooleanSupplier booleanSupplier, boolean z) {
        if (!z) {
            return booleanSupplier.getAsBoolean();
        }
        if (Flags.showDesktopExperienceDevOption()) {
            int ordinal = getToggleOverride().ordinal();
            if (ordinal == 0 || ordinal == 1) {
                return booleanSupplier.getAsBoolean();
            }
            if (ordinal == 2) {
                return true;
            }
            throw new RuntimeException(null, null);
        }
        if (Flags.showDesktopWindowingDevOption()) {
            boolean enableDesktopWindowingMode = Flags.enableDesktopWindowingMode();
            int ordinal2 = getToggleOverride().ordinal();
            if (ordinal2 == 0) {
                return booleanSupplier.getAsBoolean();
            }
            if (ordinal2 == 1) {
                return !enableDesktopWindowingMode && booleanSupplier.getAsBoolean();
            }
            if (ordinal2 == 2) {
                return !enableDesktopWindowingMode || booleanSupplier.getAsBoolean();
            }
            throw new RuntimeException(null, null);
        }
        return booleanSupplier.getAsBoolean();
    }

    private static ToggleOverride getToggleOverride() {
        ToggleOverride toggleOverride = sCachedToggleOverride;
        if (toggleOverride != null) {
            return toggleOverride;
        }
        ToggleOverride toggleOverrideFromSystem = getToggleOverrideFromSystem();
        sCachedToggleOverride = toggleOverrideFromSystem;
        Log.d(TAG, "Toggle override initialized to: " + toggleOverrideFromSystem);
        return toggleOverrideFromSystem;
    }

    private static ToggleOverride getToggleOverrideFromSystem() {
        int i;
        if (Flags.showDesktopExperienceDevOption()) {
            i = SystemProperties.getInt("persist.wm.debug.desktop_experience_devopts", ToggleOverride.OVERRIDE_UNSET.getSetting());
        } else {
            Application currentApplication = ActivityThread.currentApplication();
            if (currentApplication == null) {
                Log.w(TAG, "Could not get the current application.");
                return ToggleOverride.OVERRIDE_UNSET;
            }
            ContentResolver contentResolver = currentApplication.getContentResolver();
            if (contentResolver == null) {
                Log.w(TAG, "Could not get the content resolver for the application.");
                return ToggleOverride.OVERRIDE_UNSET;
            }
            i = Settings.Global.getInt(contentResolver, Settings.Global.DEVELOPMENT_OVERRIDE_DESKTOP_MODE_FEATURES, ToggleOverride.OVERRIDE_UNSET.getSetting());
        }
        return ToggleOverride.fromSetting(i, ToggleOverride.OVERRIDE_UNSET);
    }

    public enum ToggleOverride {
        OVERRIDE_UNSET,
        OVERRIDE_OFF,
        OVERRIDE_ON;

        public int getSetting() {
            int ordinal = ordinal();
            if (ordinal == 0) {
                return -1;
            }
            if (ordinal == 1) {
                return 0;
            }
            if (ordinal == 2) {
                return 1;
            }
            throw new RuntimeException(null, null);
        }

        public static ToggleOverride fromSetting(int i, ToggleOverride toggleOverride) {
            if (i == -1) {
                return OVERRIDE_UNSET;
            }
            if (i != 0) {
                return i != 1 ? toggleOverride : OVERRIDE_ON;
            }
            return OVERRIDE_OFF;
        }
    }
}
