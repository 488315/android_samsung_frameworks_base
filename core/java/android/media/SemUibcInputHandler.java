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
        } catch (Exception e) {
            Log.d(TAG, "library loading is failed");
        } catch (UnsatisfiedLinkError e2) {
            Log.d(TAG, "library loading is failed");
        }
    }

    public static boolean isActive() {
        try {
            boolean isActive = isActiveUIBC();
            return isActive;
        } catch (NoSuchMethodError e) {
            Log.e(TAG, "NoSuchMethod - mWfdSinkManager.isActiveUIBC()");
            return false;
        }
    }

    public static boolean isPenAvailable() {
        try {
            boolean isPenAvailable = isPenEnabled();
            return isPenAvailable;
        } catch (NoSuchMethodError e) {
            Log.e(TAG, "NoSuchMethod - mWfdSinkManager.isPenAvailable()");
            return false;
        }
    }

    public static void handlePenEvent(
            Context context,
            int states,
            int x,
            int y,
            float pressure,
            float tilt,
            float orientation) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        penEvent(states, x, y, pressure, tilt, orientation);
    }

    public static void handleTouchMove(
            Context context, int pointers, int[] id, int[] moveX, int[] moveY) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        touchMove(pointers, id, moveX, moveY);
    }

    public static void handleTouchDown(Context context, int pointers, int[] id, int[] X, int[] Y) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        touchDown(pointers, id, X, Y);
    }

    public static void handleTouchUp(Context context, int pointers, int[] id, int[] X, int[] Y) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        touchUp(pointers, id, X, Y);
    }

    public static void handleKeyDown(Context context, int key1, int key2) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyDown(key1, key2);
    }

    public static void handleKeyUp(Context context, int key1, int key2) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyUp(key1, key2);
    }

    public static void handleKeyDownASCII(Context context, int asciiKey1, int asciiKey2) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyDownASCII(asciiKey1, asciiKey2);
    }

    public static void handleKeyUpASCII(Context context, int asciiKey1, int asciiKey2) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        keyUpASCII(asciiKey1, asciiKey2);
    }

    public static void handleScroll(Context context, float X, float Y) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        scroll(X, Y);
    }

    public static void handleRotate(Context context, int radians, int fraction) {
        context.enforceCallingOrSelfPermission(
                "android.permission.INJECT_EVENTS", "Need INJECT_EVENT Permission");
        rotate(radians, fraction);
    }
}
