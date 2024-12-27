package android.hardware.biometrics.face;

/* loaded from: classes2.dex */
public @interface Error {
    public static final byte CANCELED = 5;
    public static final byte HW_UNAVAILABLE = 1;
    public static final byte NO_SPACE = 4;
    public static final byte REENROLL_REQUIRED = 8;
    public static final byte TIMEOUT = 3;
    public static final byte UNABLE_TO_PROCESS = 2;
    public static final byte UNABLE_TO_REMOVE = 6;
    public static final byte UNKNOWN = 0;
    public static final byte VENDOR = 7;
}
