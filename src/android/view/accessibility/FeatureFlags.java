package android.view.accessibility;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean a11yCharacterInWindowApi();

    boolean a11yExpansionStateApi();

    boolean a11yIsRequiredApi();

    boolean a11yIsVisitedApi();

    boolean a11yOverlayCallbacks();

    boolean a11yQsShortcut();

    boolean a11ySelectionApi();

    boolean allowShortcutChooserOnLockscreen();

    boolean brailleDisplayHid();

    boolean cleanupAccessibilityWarningDialog();

    boolean collectionInfoItemCounts();

    boolean copyEventsForGestureDetection();

    boolean deprecateAccessibilityAnnouncementApis();

    boolean deprecateAniLabelForApis();

    boolean enableSystemPinchZoomGesture();

    boolean enableTypeWindowControl();

    boolean flashNotificationSystemApi();

    boolean focusRectMinSize();

    boolean forceInvertColor();

    boolean globalActionMediaPlayPause();

    boolean globalActionMenu();

    boolean granularScrolling();

    boolean indeterminateRangeInfo();

    boolean migrateEnableShortcuts();

    boolean motionEventObserving();

    boolean preventA11yNontoolFromInjectingIntoSensitiveViews();

    boolean preventLeakingViewrootimpl();

    boolean reduceWindowContentChangedEventThrottle();

    boolean removeChildHoverCheckForTouchExploration();

    boolean requestRectangleWithSource();

    boolean restoreA11ySecureSettingsOnHsumDevice();

    boolean restoreA11yShortcutTargetService();

    boolean skipAccessibilityWarningDialogForTrustedServices();

    boolean supplementalDescription();

    boolean supportMultipleLabeledby();

    boolean supportSystemPinchZoomOptOutApis();

    boolean triStateChecked();

    boolean updateAlwaysOnA11yService();

    boolean warningUseDefaultDialogType();
}
