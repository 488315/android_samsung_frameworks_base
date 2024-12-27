package android.widget.flags;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.widget.flags.FeatureFlags
    public boolean bigPictureStyleDiscardEmptyIconBitmapDrawables() {
        return true;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean callStyleSetDataAsync() {
        return true;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean conversationStyleSetAvatarAsync() {
        return true;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean enablePlatformWidgetDifferentialMotionFling() {
        return true;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean messagingChildRequestLayout() {
        return false;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean notifLinearlayoutOptimized() {
        return true;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean toastNoWeakref() {
        return true;
    }
}
