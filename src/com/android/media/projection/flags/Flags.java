package com.android.media.projection.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_APP_CONTENT_SHARING = "com.android.media.projection.flags.app_content_sharing";
    public static final String FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY = "com.android.media.projection.flags.media_projection_connected_display";
    public static final String FLAG_MEDIA_PROJECTION_CONNECTED_DISPLAY_NO_VIRTUAL_DEVICE = "com.android.media.projection.flags.media_projection_connected_display_no_virtual_device";
    public static final String FLAG_SHOW_STOP_DIALOG_POST_CALL_END = "com.android.media.projection.flags.show_stop_dialog_post_call_end";
    public static final String FLAG_STOP_MEDIA_PROJECTION_ON_CALL_END = "com.android.media.projection.flags.stop_media_projection_on_call_end";

    public static boolean appContentSharing() {
        return FEATURE_FLAGS.appContentSharing();
    }

    public static boolean mediaProjectionConnectedDisplay() {
        return FEATURE_FLAGS.mediaProjectionConnectedDisplay();
    }

    public static boolean mediaProjectionConnectedDisplayNoVirtualDevice() {
        return FEATURE_FLAGS.mediaProjectionConnectedDisplayNoVirtualDevice();
    }

    public static boolean showStopDialogPostCallEnd() {
        return FEATURE_FLAGS.showStopDialogPostCallEnd();
    }

    public static boolean stopMediaProjectionOnCallEnd() {
        return FEATURE_FLAGS.stopMediaProjectionOnCallEnd();
    }
}
