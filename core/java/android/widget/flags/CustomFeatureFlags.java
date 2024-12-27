package android.widget.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_BIG_PICTURE_STYLE_DISCARD_EMPTY_ICON_BITMAP_DRAWABLES,
                            Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC,
                            Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC,
                            Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING,
                            Flags.FLAG_MESSAGING_CHILD_REQUEST_LAYOUT,
                            Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED,
                            Flags.FLAG_TOAST_NO_WEAKREF,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean bigPictureStyleDiscardEmptyIconBitmapDrawables() {
        return getValue(
                Flags.FLAG_BIG_PICTURE_STYLE_DISCARD_EMPTY_ICON_BITMAP_DRAWABLES,
                new Predicate() { // from class:
                                  // android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .bigPictureStyleDiscardEmptyIconBitmapDrawables();
                    }
                });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean callStyleSetDataAsync() {
        return getValue(
                Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC,
                new Predicate() { // from class:
                                  // android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).callStyleSetDataAsync();
                    }
                });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean conversationStyleSetAvatarAsync() {
        return getValue(
                Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC,
                new Predicate() { // from class:
                                  // android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).conversationStyleSetAvatarAsync();
                    }
                });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean enablePlatformWidgetDifferentialMotionFling() {
        return getValue(
                Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING,
                new Predicate() { // from class:
                                  // android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePlatformWidgetDifferentialMotionFling();
                    }
                });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean messagingChildRequestLayout() {
        return getValue(
                Flags.FLAG_MESSAGING_CHILD_REQUEST_LAYOUT,
                new Predicate() { // from class:
                                  // android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).messagingChildRequestLayout();
                    }
                });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean notifLinearlayoutOptimized() {
        return getValue(
                Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED,
                new Predicate() { // from class:
                                  // android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).notifLinearlayoutOptimized();
                    }
                });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean toastNoWeakref() {
        return getValue(
                Flags.FLAG_TOAST_NO_WEAKREF,
                new Predicate() { // from class:
                                  // android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).toastNoWeakref();
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
                Flags.FLAG_BIG_PICTURE_STYLE_DISCARD_EMPTY_ICON_BITMAP_DRAWABLES,
                Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC,
                Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC,
                Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING,
                Flags.FLAG_MESSAGING_CHILD_REQUEST_LAYOUT,
                Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED,
                Flags.FLAG_TOAST_NO_WEAKREF);
    }
}
