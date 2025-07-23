package android.widget.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_BIG_PICTURE_STYLE_DISCARD_EMPTY_ICON_BITMAP_DRAWABLES, Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC, Flags.FLAG_CONVERSATION_LAYOUT_USE_MAXIMUM_CHILD_HEIGHT, Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC, Flags.FLAG_DROP_NON_EXISTING_MESSAGES, Flags.FLAG_ENABLE_FADING_VIEW_GROUP, Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING, Flags.FLAG_FIX_UNBOLDED_TYPEFACE_FOR_NUMBERPICKER, Flags.FLAG_MESSAGING_CHILD_REQUEST_LAYOUT, Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED, Flags.FLAG_TOAST_NO_WEAKREF, Flags.FLAG_USE_WEAR_MATERIAL3_UI, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean bigPictureStyleDiscardEmptyIconBitmapDrawables() {
        return getValue(Flags.FLAG_BIG_PICTURE_STYLE_DISCARD_EMPTY_ICON_BITMAP_DRAWABLES, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).bigPictureStyleDiscardEmptyIconBitmapDrawables();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean callStyleSetDataAsync() {
        return getValue(Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callStyleSetDataAsync();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean conversationLayoutUseMaximumChildHeight() {
        return getValue(Flags.FLAG_CONVERSATION_LAYOUT_USE_MAXIMUM_CHILD_HEIGHT, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).conversationLayoutUseMaximumChildHeight();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean conversationStyleSetAvatarAsync() {
        return getValue(Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).conversationStyleSetAvatarAsync();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean dropNonExistingMessages() {
        return getValue(Flags.FLAG_DROP_NON_EXISTING_MESSAGES, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).dropNonExistingMessages();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean enableFadingViewGroup() {
        return getValue(Flags.FLAG_ENABLE_FADING_VIEW_GROUP, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableFadingViewGroup();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean enablePlatformWidgetDifferentialMotionFling() {
        return getValue(Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePlatformWidgetDifferentialMotionFling();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean fixUnboldedTypefaceForNumberpicker() {
        return getValue(Flags.FLAG_FIX_UNBOLDED_TYPEFACE_FOR_NUMBERPICKER, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixUnboldedTypefaceForNumberpicker();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean messagingChildRequestLayout() {
        return getValue(Flags.FLAG_MESSAGING_CHILD_REQUEST_LAYOUT, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).messagingChildRequestLayout();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean notifLinearlayoutOptimized() {
        return getValue(Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notifLinearlayoutOptimized();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean toastNoWeakref() {
        return getValue(Flags.FLAG_TOAST_NO_WEAKREF, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toastNoWeakref();
            }
        });
    }

    @Override // android.widget.flags.FeatureFlags
    public boolean useWearMaterial3Ui() {
        return getValue(Flags.FLAG_USE_WEAR_MATERIAL3_UI, new Predicate() { // from class: android.widget.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useWearMaterial3Ui();
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
        return Arrays.asList(Flags.FLAG_BIG_PICTURE_STYLE_DISCARD_EMPTY_ICON_BITMAP_DRAWABLES, Flags.FLAG_CALL_STYLE_SET_DATA_ASYNC, Flags.FLAG_CONVERSATION_LAYOUT_USE_MAXIMUM_CHILD_HEIGHT, Flags.FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC, Flags.FLAG_DROP_NON_EXISTING_MESSAGES, Flags.FLAG_ENABLE_FADING_VIEW_GROUP, Flags.FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING, Flags.FLAG_FIX_UNBOLDED_TYPEFACE_FOR_NUMBERPICKER, Flags.FLAG_MESSAGING_CHILD_REQUEST_LAYOUT, Flags.FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED, Flags.FLAG_TOAST_NO_WEAKREF, Flags.FLAG_USE_WEAR_MATERIAL3_UI);
    }
}
