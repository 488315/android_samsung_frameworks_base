package android.view.accessibility;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean a11yOverlayCallbacks();

    boolean a11yQsShortcut();

    boolean addTypeWindowControl();

    boolean allowShortcutChooserOnLockscreen();

    boolean brailleDisplayHid();

    boolean cleanupAccessibilityWarningDialog();

    boolean collectionInfoItemCounts();

    boolean copyEventsForGestureDetection();

    boolean enableSystemPinchZoomGesture();

    boolean fixMergedContentChangeEventV2();

    boolean flashNotificationSystemApi();

    boolean forceInvertColor();

    boolean granularScrolling();

    boolean migrateEnableShortcuts();

    boolean motionEventObserving();

    boolean preventLeakingViewrootimpl();

    boolean reduceWindowContentChangedEventThrottle();

    boolean restoreA11yShortcutTargetService();

    boolean skipAccessibilityWarningDialogForTrustedServices();

    boolean supportSystemPinchZoomOptOutApis();

    boolean updateAlwaysOnA11yService();
}
