package android.view.accessibility;

/* loaded from: classes4.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_A11Y_CHARACTER_IN_WINDOW_API = "android.view.accessibility.a11y_character_in_window_api";
    public static final String FLAG_A11Y_EXPANSION_STATE_API = "android.view.accessibility.a11y_expansion_state_api";
    public static final String FLAG_A11Y_IS_REQUIRED_API = "android.view.accessibility.a11y_is_required_api";
    public static final String FLAG_A11Y_IS_VISITED_API = "android.view.accessibility.a11y_is_visited_api";
    public static final String FLAG_A11Y_OVERLAY_CALLBACKS = "android.view.accessibility.a11y_overlay_callbacks";
    public static final String FLAG_A11Y_QS_SHORTCUT = "android.view.accessibility.a11y_qs_shortcut";
    public static final String FLAG_A11Y_SELECTION_API = "android.view.accessibility.a11y_selection_api";
    public static final String FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN = "android.view.accessibility.allow_shortcut_chooser_on_lockscreen";
    public static final String FLAG_BRAILLE_DISPLAY_HID = "android.view.accessibility.braille_display_hid";
    public static final String FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG = "android.view.accessibility.cleanup_accessibility_warning_dialog";
    public static final String FLAG_COLLECTION_INFO_ITEM_COUNTS = "android.view.accessibility.collection_info_item_counts";
    public static final String FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION = "android.view.accessibility.copy_events_for_gesture_detection";
    public static final String FLAG_DEPRECATE_ACCESSIBILITY_ANNOUNCEMENT_APIS = "android.view.accessibility.deprecate_accessibility_announcement_apis";
    public static final String FLAG_DEPRECATE_ANI_LABEL_FOR_APIS = "android.view.accessibility.deprecate_ani_label_for_apis";
    public static final String FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE = "android.view.accessibility.enable_system_pinch_zoom_gesture";
    public static final String FLAG_ENABLE_TYPE_WINDOW_CONTROL = "android.view.accessibility.enable_type_window_control";
    public static final String FLAG_FLASH_NOTIFICATION_SYSTEM_API = "android.view.accessibility.flash_notification_system_api";
    public static final String FLAG_FOCUS_RECT_MIN_SIZE = "android.view.accessibility.focus_rect_min_size";
    public static final String FLAG_FORCE_INVERT_COLOR = "android.view.accessibility.force_invert_color";
    public static final String FLAG_GLOBAL_ACTION_MEDIA_PLAY_PAUSE = "android.view.accessibility.global_action_media_play_pause";
    public static final String FLAG_GLOBAL_ACTION_MENU = "android.view.accessibility.global_action_menu";
    public static final String FLAG_GRANULAR_SCROLLING = "android.view.accessibility.granular_scrolling";
    public static final String FLAG_INDETERMINATE_RANGE_INFO = "android.view.accessibility.indeterminate_range_info";
    public static final String FLAG_MIGRATE_ENABLE_SHORTCUTS = "android.view.accessibility.migrate_enable_shortcuts";
    public static final String FLAG_MOTION_EVENT_OBSERVING = "android.view.accessibility.motion_event_observing";
    public static final String FLAG_PREVENT_A11Y_NONTOOL_FROM_INJECTING_INTO_SENSITIVE_VIEWS = "android.view.accessibility.prevent_a11y_nontool_from_injecting_into_sensitive_views";
    public static final String FLAG_PREVENT_LEAKING_VIEWROOTIMPL = "android.view.accessibility.prevent_leaking_viewrootimpl";
    public static final String FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE = "android.view.accessibility.reduce_window_content_changed_event_throttle";
    public static final String FLAG_REMOVE_CHILD_HOVER_CHECK_FOR_TOUCH_EXPLORATION = "android.view.accessibility.remove_child_hover_check_for_touch_exploration";
    public static final String FLAG_REQUEST_RECTANGLE_WITH_SOURCE = "android.view.accessibility.request_rectangle_with_source";
    public static final String FLAG_RESTORE_A11Y_SECURE_SETTINGS_ON_HSUM_DEVICE = "android.view.accessibility.restore_a11y_secure_settings_on_hsum_device";
    public static final String FLAG_RESTORE_A11Y_SHORTCUT_TARGET_SERVICE = "android.view.accessibility.restore_a11y_shortcut_target_service";
    public static final String FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES = "android.view.accessibility.skip_accessibility_warning_dialog_for_trusted_services";
    public static final String FLAG_SUPPLEMENTAL_DESCRIPTION = "android.view.accessibility.supplemental_description";
    public static final String FLAG_SUPPORT_MULTIPLE_LABELEDBY = "android.view.accessibility.support_multiple_labeledby";
    public static final String FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS = "android.view.accessibility.support_system_pinch_zoom_opt_out_apis";
    public static final String FLAG_TRI_STATE_CHECKED = "android.view.accessibility.tri_state_checked";
    public static final String FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE = "android.view.accessibility.update_always_on_a11y_service";
    public static final String FLAG_WARNING_USE_DEFAULT_DIALOG_TYPE = "android.view.accessibility.warning_use_default_dialog_type";

    public static boolean a11yCharacterInWindowApi() {
        return FEATURE_FLAGS.a11yCharacterInWindowApi();
    }

    public static boolean a11yExpansionStateApi() {
        return FEATURE_FLAGS.a11yExpansionStateApi();
    }

    public static boolean a11yIsRequiredApi() {
        return FEATURE_FLAGS.a11yIsRequiredApi();
    }

    public static boolean a11yIsVisitedApi() {
        return FEATURE_FLAGS.a11yIsVisitedApi();
    }

    public static boolean a11yOverlayCallbacks() {
        return FEATURE_FLAGS.a11yOverlayCallbacks();
    }

    public static boolean a11yQsShortcut() {
        return FEATURE_FLAGS.a11yQsShortcut();
    }

    public static boolean a11ySelectionApi() {
        return FEATURE_FLAGS.a11ySelectionApi();
    }

    public static boolean allowShortcutChooserOnLockscreen() {
        return FEATURE_FLAGS.allowShortcutChooserOnLockscreen();
    }

    public static boolean brailleDisplayHid() {
        return FEATURE_FLAGS.brailleDisplayHid();
    }

    public static boolean cleanupAccessibilityWarningDialog() {
        return FEATURE_FLAGS.cleanupAccessibilityWarningDialog();
    }

    public static boolean collectionInfoItemCounts() {
        return FEATURE_FLAGS.collectionInfoItemCounts();
    }

    public static boolean copyEventsForGestureDetection() {
        return FEATURE_FLAGS.copyEventsForGestureDetection();
    }

    public static boolean deprecateAccessibilityAnnouncementApis() {
        return FEATURE_FLAGS.deprecateAccessibilityAnnouncementApis();
    }

    public static boolean deprecateAniLabelForApis() {
        return FEATURE_FLAGS.deprecateAniLabelForApis();
    }

    public static boolean enableSystemPinchZoomGesture() {
        return FEATURE_FLAGS.enableSystemPinchZoomGesture();
    }

    public static boolean enableTypeWindowControl() {
        return FEATURE_FLAGS.enableTypeWindowControl();
    }

    public static boolean flashNotificationSystemApi() {
        return FEATURE_FLAGS.flashNotificationSystemApi();
    }

    public static boolean focusRectMinSize() {
        return FEATURE_FLAGS.focusRectMinSize();
    }

    public static boolean forceInvertColor() {
        return FEATURE_FLAGS.forceInvertColor();
    }

    public static boolean globalActionMediaPlayPause() {
        return FEATURE_FLAGS.globalActionMediaPlayPause();
    }

    public static boolean globalActionMenu() {
        return FEATURE_FLAGS.globalActionMenu();
    }

    public static boolean granularScrolling() {
        return FEATURE_FLAGS.granularScrolling();
    }

    public static boolean indeterminateRangeInfo() {
        return FEATURE_FLAGS.indeterminateRangeInfo();
    }

    public static boolean migrateEnableShortcuts() {
        return FEATURE_FLAGS.migrateEnableShortcuts();
    }

    public static boolean motionEventObserving() {
        return FEATURE_FLAGS.motionEventObserving();
    }

    public static boolean preventA11yNontoolFromInjectingIntoSensitiveViews() {
        return FEATURE_FLAGS.preventA11yNontoolFromInjectingIntoSensitiveViews();
    }

    public static boolean preventLeakingViewrootimpl() {
        return FEATURE_FLAGS.preventLeakingViewrootimpl();
    }

    public static boolean reduceWindowContentChangedEventThrottle() {
        return FEATURE_FLAGS.reduceWindowContentChangedEventThrottle();
    }

    public static boolean removeChildHoverCheckForTouchExploration() {
        return FEATURE_FLAGS.removeChildHoverCheckForTouchExploration();
    }

    public static boolean requestRectangleWithSource() {
        return FEATURE_FLAGS.requestRectangleWithSource();
    }

    public static boolean restoreA11ySecureSettingsOnHsumDevice() {
        return FEATURE_FLAGS.restoreA11ySecureSettingsOnHsumDevice();
    }

    public static boolean restoreA11yShortcutTargetService() {
        return FEATURE_FLAGS.restoreA11yShortcutTargetService();
    }

    public static boolean skipAccessibilityWarningDialogForTrustedServices() {
        return FEATURE_FLAGS.skipAccessibilityWarningDialogForTrustedServices();
    }

    public static boolean supplementalDescription() {
        return FEATURE_FLAGS.supplementalDescription();
    }

    public static boolean supportMultipleLabeledby() {
        return FEATURE_FLAGS.supportMultipleLabeledby();
    }

    public static boolean supportSystemPinchZoomOptOutApis() {
        return FEATURE_FLAGS.supportSystemPinchZoomOptOutApis();
    }

    public static boolean triStateChecked() {
        return FEATURE_FLAGS.triStateChecked();
    }

    public static boolean updateAlwaysOnA11yService() {
        return FEATURE_FLAGS.updateAlwaysOnA11yService();
    }

    public static boolean warningUseDefaultDialogType() {
        return FEATURE_FLAGS.warningUseDefaultDialogType();
    }
}
