package android.media;

import android.content.Context;
import android.util.Log;

/* loaded from: classes2.dex */
public class SemUibcInputHandler {
    private static final String TAG = "SemUibcInputHandler.java";

    public static native void handleKeyDownASCII(int i, int i2);

    public static native void handleKeyUpASCII(int i, int i2);

    public static native void handleRotate(int i, int i2);

    public static native void handleScroll(float f, float f2);

    public static native boolean isActiveUIBC();

    public static native boolean isPenEnabled();

    private static native void keyDown(int i, int i2);

    private static native void keyDownASCII(int i, int i2);

    private static native void keyUp(int i, int i2);

    private static native void keyUpASCII(int i, int i2);

    private static native void penEvent(int i, int i2, int i3, float f, float f2, float f3);

    private static native void rotate(int i, int i2);

    private static native void scroll(float f, float f2);

    private static native void touchDown(int i, int[] iArr, int[] iArr2, int[] iArr3);

    private static native void touchMove(int i, int[] iArr, int[] iArr2, int[] iArr3);

    private static native void touchUp(int i, int[] iArr, int[] iArr2, int[] iArr3);

    static {
        try {
            Log.d(TAG, "try to load libuibc.so");
            System.loadLibrary("uibc");
        } catch (Exception unused) {
            Log.d(TAG, "library loading is failed");
        } catch (UnsatisfiedLinkError unused2) {
            Log.d(TAG, "library loading is failed");
        }
    }

    public static boolean isActive() {
        try {
            return isActiveUIBC();
        } catch (NoSuchMethodError unused) {
            Log.e(TAG, "NoSuchMethod - mWfdSinkManager.isActiveUIBC()");
            return false;
        }
    }

    public static boolean isPenAvailable() {
        try {
            return isPenEnabled();
        } catch (NoSuchMethodError unused) {
            Log.e(TAG, "NoSuchMethod - mWfdSinkManager.isPenAvailable()");
            return false;
        }
    }

    public static void handlePenEvent(Context context, int i, int i2, int i3, float f, float f2, float f3) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        penEvent(i, i2, i3, f, f2, f3);
    }

    public static void handleTouchMove(Context context, int i, int[] iArr, int[] iArr2, int[] iArr3) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        touchMove(i, iArr, iArr2, iArr3);
    }

    public static void handleTouchDown(Context context, int i, int[] iArr, int[] iArr2, int[] iArr3) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        touchDown(i, iArr, iArr2, iArr3);
    }

    public static void handleTouchUp(Context context, int i, int[] iArr, int[] iArr2, int[] iArr3) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        touchUp(i, iArr, iArr2, iArr3);
    }

    public static void handleKeyDown(Context context, int i, int i2) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyDown(i, i2);
    }

    public static void handleKeyUp(Context context, int i, int i2) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyUp(i, i2);
    }

    public static void handleKeyDownASCII(Context context, int i, int i2) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyDownASCII(i, i2);
    }

    public static void handleKeyUpASCII(Context context, int i, int i2) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyUpASCII(i, i2);
    }

    public static void handleScroll(Context context, float f, float f2) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        scroll(f, f2);
    }

    public static void handleRotate(Context context, int i, int i2) {
        context.enforceCallingOrSelfPermission("android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        rotate(i, i2);
    }
}
