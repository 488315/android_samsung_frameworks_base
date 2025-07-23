package android.view.accessibility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_A11Y_CHARACTER_IN_WINDOW_API, Flags.FLAG_A11Y_EXPANSION_STATE_API, Flags.FLAG_A11Y_IS_REQUIRED_API, Flags.FLAG_A11Y_IS_VISITED_API, Flags.FLAG_A11Y_OVERLAY_CALLBACKS, Flags.FLAG_A11Y_QS_SHORTCUT, Flags.FLAG_A11Y_SELECTION_API, Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN, Flags.FLAG_BRAILLE_DISPLAY_HID, Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG, Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS, Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION, Flags.FLAG_DEPRECATE_ACCESSIBILITY_ANNOUNCEMENT_APIS, Flags.FLAG_DEPRECATE_ANI_LABEL_FOR_APIS, Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE, Flags.FLAG_ENABLE_TYPE_WINDOW_CONTROL, Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API, Flags.FLAG_FOCUS_RECT_MIN_SIZE, Flags.FLAG_FORCE_INVERT_COLOR, Flags.FLAG_GLOBAL_ACTION_MEDIA_PLAY_PAUSE, Flags.FLAG_GLOBAL_ACTION_MENU, Flags.FLAG_GRANULAR_SCROLLING, Flags.FLAG_INDETERMINATE_RANGE_INFO, Flags.FLAG_MIGRATE_ENABLE_SHORTCUTS, Flags.FLAG_MOTION_EVENT_OBSERVING, Flags.FLAG_PREVENT_A11Y_NONTOOL_FROM_INJECTING_INTO_SENSITIVE_VIEWS, Flags.FLAG_PREVENT_LEAKING_VIEWROOTIMPL, Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE, Flags.FLAG_REMOVE_CHILD_HOVER_CHECK_FOR_TOUCH_EXPLORATION, Flags.FLAG_REQUEST_RECTANGLE_WITH_SOURCE, Flags.FLAG_RESTORE_A11Y_SECURE_SETTINGS_ON_HSUM_DEVICE, Flags.FLAG_RESTORE_A11Y_SHORTCUT_TARGET_SERVICE, Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES, Flags.FLAG_SUPPLEMENTAL_DESCRIPTION, Flags.FLAG_SUPPORT_MULTIPLE_LABELEDBY, Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS, Flags.FLAG_TRI_STATE_CHECKED, Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE, Flags.FLAG_WARNING_USE_DEFAULT_DIALOG_TYPE, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yCharacterInWindowApi() {
        return getValue(Flags.FLAG_A11Y_CHARACTER_IN_WINDOW_API, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yCharacterInWindowApi();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yExpansionStateApi() {
        return getValue(Flags.FLAG_A11Y_EXPANSION_STATE_API, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yExpansionStateApi();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yIsRequiredApi() {
        return getValue(Flags.FLAG_A11Y_IS_REQUIRED_API, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yIsRequiredApi();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yIsVisitedApi() {
        return getValue(Flags.FLAG_A11Y_IS_VISITED_API, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yIsVisitedApi();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yOverlayCallbacks() {
        return getValue(Flags.FLAG_A11Y_OVERLAY_CALLBACKS, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yOverlayCallbacks();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yQsShortcut() {
        return getValue(Flags.FLAG_A11Y_QS_SHORTCUT, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yQsShortcut();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11ySelectionApi() {
        return getValue(Flags.FLAG_A11Y_SELECTION_API, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11ySelectionApi();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean allowShortcutChooserOnLockscreen() {
        return getValue(Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowShortcutChooserOnLockscreen();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean brailleDisplayHid() {
        return getValue(Flags.FLAG_BRAILLE_DISPLAY_HID, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).brailleDisplayHid();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean cleanupAccessibilityWarningDialog() {
        return getValue(Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cleanupAccessibilityWarningDialog();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean collectionInfoItemCounts() {
        return getValue(Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).collectionInfoItemCounts();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean copyEventsForGestureDetection() {
        return getValue(Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).copyEventsForGestureDetection();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean deprecateAccessibilityAnnouncementApis() {
        return getValue(Flags.FLAG_DEPRECATE_ACCESSIBILITY_ANNOUNCEMENT_APIS, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateAccessibilityAnnouncementApis();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean deprecateAniLabelForApis() {
        return getValue(Flags.FLAG_DEPRECATE_ANI_LABEL_FOR_APIS, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateAniLabelForApis();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean enableSystemPinchZoomGesture() {
        return getValue(Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSystemPinchZoomGesture();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean enableTypeWindowControl() {
        return getValue(Flags.FLAG_ENABLE_TYPE_WINDOW_CONTROL, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTypeWindowControl();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean flashNotificationSystemApi() {
        return getValue(Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).flashNotificationSystemApi();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean focusRectMinSize() {
        return getValue(Flags.FLAG_FOCUS_RECT_MIN_SIZE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).focusRectMinSize();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean forceInvertColor() {
        return getValue(Flags.FLAG_FORCE_INVERT_COLOR, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).forceInvertColor();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean globalActionMediaPlayPause() {
        return getValue(Flags.FLAG_GLOBAL_ACTION_MEDIA_PLAY_PAUSE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).globalActionMediaPlayPause();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean globalActionMenu() {
        return getValue(Flags.FLAG_GLOBAL_ACTION_MENU, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).globalActionMenu();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean granularScrolling() {
        return getValue(Flags.FLAG_GRANULAR_SCROLLING, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).granularScrolling();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean indeterminateRangeInfo() {
        return getValue(Flags.FLAG_INDETERMINATE_RANGE_INFO, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).indeterminateRangeInfo();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean migrateEnableShortcuts() {
        return getValue(Flags.FLAG_MIGRATE_ENABLE_SHORTCUTS, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).migrateEnableShortcuts();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean motionEventObserving() {
        return getValue(Flags.FLAG_MOTION_EVENT_OBSERVING, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).motionEventObserving();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean preventA11yNontoolFromInjectingIntoSensitiveViews() {
        return getValue(Flags.FLAG_PREVENT_A11Y_NONTOOL_FROM_INJECTING_INTO_SENSITIVE_VIEWS, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventA11yNontoolFromInjectingIntoSensitiveViews();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean preventLeakingViewrootimpl() {
        return getValue(Flags.FLAG_PREVENT_LEAKING_VIEWROOTIMPL, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventLeakingViewrootimpl();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean reduceWindowContentChangedEventThrottle() {
        return getValue(Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).reduceWindowContentChangedEventThrottle();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean removeChildHoverCheckForTouchExploration() {
        return getValue(Flags.FLAG_REMOVE_CHILD_HOVER_CHECK_FOR_TOUCH_EXPLORATION, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeChildHoverCheckForTouchExploration();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean requestRectangleWithSource() {
        return getValue(Flags.FLAG_REQUEST_RECTANGLE_WITH_SOURCE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).requestRectangleWithSource();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean restoreA11ySecureSettingsOnHsumDevice() {
        return getValue(Flags.FLAG_RESTORE_A11Y_SECURE_SETTINGS_ON_HSUM_DEVICE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).restoreA11ySecureSettingsOnHsumDevice();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean restoreA11yShortcutTargetService() {
        return getValue(Flags.FLAG_RESTORE_A11Y_SHORTCUT_TARGET_SERVICE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).restoreA11yShortcutTargetService();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean skipAccessibilityWarningDialogForTrustedServices() {
        return getValue(Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).skipAccessibilityWarningDialogForTrustedServices();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean supplementalDescription() {
        return getValue(Flags.FLAG_SUPPLEMENTAL_DESCRIPTION, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supplementalDescription();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean supportMultipleLabeledby() {
        return getValue(Flags.FLAG_SUPPORT_MULTIPLE_LABELEDBY, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportMultipleLabeledby();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean supportSystemPinchZoomOptOutApis() {
        return getValue(Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportSystemPinchZoomOptOutApis();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean triStateChecked() {
        return getValue(Flags.FLAG_TRI_STATE_CHECKED, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).triStateChecked();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean updateAlwaysOnA11yService() {
        return getValue(Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).updateAlwaysOnA11yService();
            }
        });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean warningUseDefaultDialogType() {
        return getValue(Flags.FLAG_WARNING_USE_DEFAULT_DIALOG_TYPE, new Predicate() { // from class: android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).warningUseDefaultDialogType();
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
        return Arrays.asList(Flags.FLAG_A11Y_CHARACTER_IN_WINDOW_API, Flags.FLAG_A11Y_EXPANSION_STATE_API, Flags.FLAG_A11Y_IS_REQUIRED_API, Flags.FLAG_A11Y_IS_VISITED_API, Flags.FLAG_A11Y_OVERLAY_CALLBACKS, Flags.FLAG_A11Y_QS_SHORTCUT, Flags.FLAG_A11Y_SELECTION_API, Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN, Flags.FLAG_BRAILLE_DISPLAY_HID, Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG, Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS, Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION, Flags.FLAG_DEPRECATE_ACCESSIBILITY_ANNOUNCEMENT_APIS, Flags.FLAG_DEPRECATE_ANI_LABEL_FOR_APIS, Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE, Flags.FLAG_ENABLE_TYPE_WINDOW_CONTROL, Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API, Flags.FLAG_FOCUS_RECT_MIN_SIZE, Flags.FLAG_FORCE_INVERT_COLOR, Flags.FLAG_GLOBAL_ACTION_MEDIA_PLAY_PAUSE, Flags.FLAG_GLOBAL_ACTION_MENU, Flags.FLAG_GRANULAR_SCROLLING, Flags.FLAG_INDETERMINATE_RANGE_INFO, Flags.FLAG_MIGRATE_ENABLE_SHORTCUTS, Flags.FLAG_MOTION_EVENT_OBSERVING, Flags.FLAG_PREVENT_A11Y_NONTOOL_FROM_INJECTING_INTO_SENSITIVE_VIEWS, Flags.FLAG_PREVENT_LEAKING_VIEWROOTIMPL, Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE, Flags.FLAG_REMOVE_CHILD_HOVER_CHECK_FOR_TOUCH_EXPLORATION, Flags.FLAG_REQUEST_RECTANGLE_WITH_SOURCE, Flags.FLAG_RESTORE_A11Y_SECURE_SETTINGS_ON_HSUM_DEVICE, Flags.FLAG_RESTORE_A11Y_SHORTCUT_TARGET_SERVICE, Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES, Flags.FLAG_SUPPLEMENTAL_DESCRIPTION, Flags.FLAG_SUPPORT_MULTIPLE_LABELEDBY, Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS, Flags.FLAG_TRI_STATE_CHECKED, Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE, Flags.FLAG_WARNING_USE_DEFAULT_DIALOG_TYPE);
    }
}
