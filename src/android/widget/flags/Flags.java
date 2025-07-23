package android.widget.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_BIG_PICTURE_STYLE_DISCARD_EMPTY_ICON_BITMAP_DRAWABLES = "android.widget.flags.big_picture_style_discard_empty_icon_bitmap_drawables";
    public static final String FLAG_CALL_STYLE_SET_DATA_ASYNC = "android.widget.flags.call_style_set_data_async";
    public static final String FLAG_CONVERSATION_LAYOUT_USE_MAXIMUM_CHILD_HEIGHT = "android.widget.flags.conversation_layout_use_maximum_child_height";
    public static final String FLAG_CONVERSATION_STYLE_SET_AVATAR_ASYNC = "android.widget.flags.conversation_style_set_avatar_async";
    public static final String FLAG_DROP_NON_EXISTING_MESSAGES = "android.widget.flags.drop_non_existing_messages";
    public static final String FLAG_ENABLE_FADING_VIEW_GROUP = "android.widget.flags.enable_fading_view_group";
    public static final String FLAG_ENABLE_PLATFORM_WIDGET_DIFFERENTIAL_MOTION_FLING = "android.widget.flags.enable_platform_widget_differential_motion_fling";
    public static final String FLAG_FIX_UNBOLDED_TYPEFACE_FOR_NUMBERPICKER = "android.widget.flags.fix_unbolded_typeface_for_numberpicker";
    public static final String FLAG_MESSAGING_CHILD_REQUEST_LAYOUT = "android.widget.flags.messaging_child_request_layout";
    public static final String FLAG_NOTIF_LINEARLAYOUT_OPTIMIZED = "android.widget.flags.notif_linearlayout_optimized";
    public static final String FLAG_TOAST_NO_WEAKREF = "android.widget.flags.toast_no_weakref";
    public static final String FLAG_USE_WEAR_MATERIAL3_UI = "android.widget.flags.use_wear_material3_ui";

    public static boolean bigPictureStyleDiscardEmptyIconBitmapDrawables() {
        return FEATURE_FLAGS.bigPictureStyleDiscardEmptyIconBitmapDrawables();
    }

    public static boolean callStyleSetDataAsync() {
        return FEATURE_FLAGS.callStyleSetDataAsync();
    }

    public static boolean conversationLayoutUseMaximumChildHeight() {
        return FEATURE_FLAGS.conversationLayoutUseMaximumChildHeight();
    }

    public static boolean conversationStyleSetAvatarAsync() {
        return FEATURE_FLAGS.conversationStyleSetAvatarAsync();
    }

    public static boolean dropNonExistingMessages() {
        return FEATURE_FLAGS.dropNonExistingMessages();
    }

    public static boolean enableFadingViewGroup() {
        return FEATURE_FLAGS.enableFadingViewGroup();
    }

    public static boolean enablePlatformWidgetDifferentialMotionFling() {
        return FEATURE_FLAGS.enablePlatformWidgetDifferentialMotionFling();
    }

    public static boolean fixUnboldedTypefaceForNumberpicker() {
        return FEATURE_FLAGS.fixUnboldedTypefaceForNumberpicker();
    }

    public static boolean messagingChildRequestLayout() {
        return FEATURE_FLAGS.messagingChildRequestLayout();
    }

    public static boolean notifLinearlayoutOptimized() {
        return FEATURE_FLAGS.notifLinearlayoutOptimized();
    }

    public static boolean toastNoWeakref() {
        return FEATURE_FLAGS.toastNoWeakref();
    }

    public static boolean useWearMaterial3Ui() {
        return FEATURE_FLAGS.useWearMaterial3Ui();
    }
}
