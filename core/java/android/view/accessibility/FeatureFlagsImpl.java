package android.view.accessibility;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yOverlayCallbacks() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yQsShortcut() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean addTypeWindowControl() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean allowShortcutChooserOnLockscreen() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean brailleDisplayHid() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean cleanupAccessibilityWarningDialog() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean collectionInfoItemCounts() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean copyEventsForGestureDetection() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean enableSystemPinchZoomGesture() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean fixMergedContentChangeEventV2() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean flashNotificationSystemApi() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean forceInvertColor() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean granularScrolling() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean migrateEnableShortcuts() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean motionEventObserving() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean preventLeakingViewrootimpl() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean reduceWindowContentChangedEventThrottle() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean restoreA11yShortcutTargetService() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean skipAccessibilityWarningDialogForTrustedServices() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean supportSystemPinchZoomOptOutApis() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean updateAlwaysOnA11yService() {
        return false;
    }
}
