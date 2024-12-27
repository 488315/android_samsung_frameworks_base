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
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_A11Y_OVERLAY_CALLBACKS,
                            Flags.FLAG_A11Y_QS_SHORTCUT,
                            Flags.FLAG_ADD_TYPE_WINDOW_CONTROL,
                            Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN,
                            Flags.FLAG_BRAILLE_DISPLAY_HID,
                            Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG,
                            Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS,
                            Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION,
                            Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE,
                            Flags.FLAG_FIX_MERGED_CONTENT_CHANGE_EVENT_V2,
                            Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API,
                            Flags.FLAG_FORCE_INVERT_COLOR,
                            Flags.FLAG_GRANULAR_SCROLLING,
                            Flags.FLAG_MIGRATE_ENABLE_SHORTCUTS,
                            Flags.FLAG_MOTION_EVENT_OBSERVING,
                            Flags.FLAG_PREVENT_LEAKING_VIEWROOTIMPL,
                            Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE,
                            Flags.FLAG_RESTORE_A11Y_SHORTCUT_TARGET_SERVICE,
                            Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES,
                            Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS,
                            Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yOverlayCallbacks() {
        return getValue(
                Flags.FLAG_A11Y_OVERLAY_CALLBACKS,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).a11yOverlayCallbacks();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yQsShortcut() {
        return getValue(
                Flags.FLAG_A11Y_QS_SHORTCUT,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).a11yQsShortcut();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean addTypeWindowControl() {
        return getValue(
                Flags.FLAG_ADD_TYPE_WINDOW_CONTROL,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).addTypeWindowControl();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean allowShortcutChooserOnLockscreen() {
        return getValue(
                Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).allowShortcutChooserOnLockscreen();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean brailleDisplayHid() {
        return getValue(
                Flags.FLAG_BRAILLE_DISPLAY_HID,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).brailleDisplayHid();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean cleanupAccessibilityWarningDialog() {
        return getValue(
                Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).cleanupAccessibilityWarningDialog();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean collectionInfoItemCounts() {
        return getValue(
                Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).collectionInfoItemCounts();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean copyEventsForGestureDetection() {
        return getValue(
                Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).copyEventsForGestureDetection();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean enableSystemPinchZoomGesture() {
        return getValue(
                Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableSystemPinchZoomGesture();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean fixMergedContentChangeEventV2() {
        return getValue(
                Flags.FLAG_FIX_MERGED_CONTENT_CHANGE_EVENT_V2,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).fixMergedContentChangeEventV2();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean flashNotificationSystemApi() {
        return getValue(
                Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).flashNotificationSystemApi();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean forceInvertColor() {
        return getValue(
                Flags.FLAG_FORCE_INVERT_COLOR,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).forceInvertColor();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean granularScrolling() {
        return getValue(
                Flags.FLAG_GRANULAR_SCROLLING,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).granularScrolling();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean migrateEnableShortcuts() {
        return getValue(
                Flags.FLAG_MIGRATE_ENABLE_SHORTCUTS,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).migrateEnableShortcuts();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean motionEventObserving() {
        return getValue(
                Flags.FLAG_MOTION_EVENT_OBSERVING,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).motionEventObserving();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean preventLeakingViewrootimpl() {
        return getValue(
                Flags.FLAG_PREVENT_LEAKING_VIEWROOTIMPL,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).preventLeakingViewrootimpl();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean reduceWindowContentChangedEventThrottle() {
        return getValue(
                Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reduceWindowContentChangedEventThrottle();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean restoreA11yShortcutTargetService() {
        return getValue(
                Flags.FLAG_RESTORE_A11Y_SHORTCUT_TARGET_SERVICE,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).restoreA11yShortcutTargetService();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean skipAccessibilityWarningDialogForTrustedServices() {
        return getValue(
                Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .skipAccessibilityWarningDialogForTrustedServices();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean supportSystemPinchZoomOptOutApis() {
        return getValue(
                Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).supportSystemPinchZoomOptOutApis();
                    }
                });
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean updateAlwaysOnA11yService() {
        return getValue(
                Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE,
                new Predicate() { // from class:
                                  // android.view.accessibility.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).updateAlwaysOnA11yService();
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
                Flags.FLAG_A11Y_OVERLAY_CALLBACKS,
                Flags.FLAG_A11Y_QS_SHORTCUT,
                Flags.FLAG_ADD_TYPE_WINDOW_CONTROL,
                Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN,
                Flags.FLAG_BRAILLE_DISPLAY_HID,
                Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG,
                Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS,
                Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION,
                Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE,
                Flags.FLAG_FIX_MERGED_CONTENT_CHANGE_EVENT_V2,
                Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API,
                Flags.FLAG_FORCE_INVERT_COLOR,
                Flags.FLAG_GRANULAR_SCROLLING,
                Flags.FLAG_MIGRATE_ENABLE_SHORTCUTS,
                Flags.FLAG_MOTION_EVENT_OBSERVING,
                Flags.FLAG_PREVENT_LEAKING_VIEWROOTIMPL,
                Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE,
                Flags.FLAG_RESTORE_A11Y_SHORTCUT_TARGET_SERVICE,
                Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES,
                Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS,
                Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE);
    }
}
