package android.os;

/* loaded from: classes3.dex */
public @interface MotionEventFlag {
    public static final int BY_WHEEL_SCROLL_PAD = 1048576;
    public static final int CANCELED = 32;
    public static final int DISPATCH_WHEN_NON_INTERACTIVE = 2097152;
    public static final int EVENT_BY_TWO_FINGER_GESTURE = 268435456;
    public static final int FROM_WFD = 134217728;
    public static final int HOVER_EXIT_PENDING = 4;
    public static final int INJECTED_FROM_ACCESSIBILITY_TOOL = 4096;
    public static final int INTERNAL_DISPLAY_FOR_USER_ACTIVITY = 67108864;
    public static final int IS_ACCESSIBILITY_EVENT = 2048;
    public static final int IS_GENERATED_GESTURE = 8;
    public static final int KEEP_DEVICE_ID = 4194304;
    public static final int NOT_RESET_USER_ACTIVITY_TIMEOUT = 16777216;
    public static final int NO_FOCUS_CHANGE = 64;
    public static final int PRIVATE_FLAG_SUPPORTS_DIRECTIONAL_ORIENTATION = 256;
    public static final int PRIVATE_FLAG_SUPPORTS_ORIENTATION = 128;
    public static final int SUPPORT_SPEN_ORIENTATION = 524288;
    public static final int TAINTED = Integer.MIN_VALUE;
    public static final int TARGET_ACCESSIBILITY_FOCUS = 1073741824;
    public static final int UP_PENDING = 33554432;
    public static final int WINDOW_IS_ACCESSIBILITY = 8388608;
    public static final int WINDOW_IS_EASYONEHAND = 536870912;
    public static final int WINDOW_IS_OBSCURED = 1;
    public static final int WINDOW_IS_PARTIALLY_OBSCURED = 2;
}
