package android.opengl;

import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public final class GLUtils {
    private static native int native_getInternalFormat(Bitmap bitmap);

    private static native int native_getType(Bitmap bitmap);

    private static native int native_texImage2D(int i, int i2, int i3, Bitmap bitmap, int i4, int i5);

    private static native int native_texSubImage2D(int i, int i2, int i3, int i4, Bitmap bitmap, int i5, int i6);

    private GLUtils() {
    }

    public static int getInternalFormat(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("getInternalFormat can't be used with a null Bitmap");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap is recycled");
        }
        int native_getInternalFormat = native_getInternalFormat(bitmap);
        if (native_getInternalFormat >= 0) {
            return native_getInternalFormat;
        }
        throw new IllegalArgumentException("Unknown internalformat");
    }

    public static int getType(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("getType can't be used with a null Bitmap");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap is recycled");
        }
        int native_getType = native_getType(bitmap);
        if (native_getType >= 0) {
            return native_getType;
        }
        throw new IllegalArgumentException("Unknown type");
    }

    public static void texImage2D(int i, int i2, int i3, Bitmap bitmap, int i4) {
        if (bitmap == null) {
            throw new NullPointerException("texImage2D can't be used with a null Bitmap");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap is recycled");
        }
        if (native_texImage2D(i, i2, i3, bitmap, -1, i4) != 0) {
            throw new IllegalArgumentException("invalid Bitmap format");
        }
    }

    public static void texImage2D(int i, int i2, int i3, Bitmap bitmap, int i4, int i5) {
        if (bitmap == null) {
            throw new NullPointerException("texImage2D can't be used with a null Bitmap");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap is recycled");
        }
        if (native_texImage2D(i, i2, i3, bitmap, i4, i5) != 0) {
            throw new IllegalArgumentException("invalid Bitmap format");
        }
    }

    public static void texImage2D(int i, int i2, Bitmap bitmap, int i3) {
        if (bitmap == null) {
            throw new NullPointerException("texImage2D can't be used with a null Bitmap");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap is recycled");
        }
        if (native_texImage2D(i, i2, -1, bitmap, -1, i3) != 0) {
            throw new IllegalArgumentException("invalid Bitmap format");
        }
    }

    public static void texSubImage2D(int i, int i2, int i3, int i4, Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("texSubImage2D can't be used with a null Bitmap");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap is recycled");
        }
        if (native_texSubImage2D(i, i2, i3, i4, bitmap, -1, getType(bitmap)) != 0) {
            throw new IllegalArgumentException("invalid Bitmap format");
        }
    }

    public static void texSubImage2D(int i, int i2, int i3, int i4, Bitmap bitmap, int i5, int i6) {
        if (bitmap == null) {
            throw new NullPointerException("texSubImage2D can't be used with a null Bitmap");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap is recycled");
        }
        if (native_texSubImage2D(i, i2, i3, i4, bitmap, i5, i6) != 0) {
            throw new IllegalArgumentException("invalid Bitmap format");
        }
    }

    public static String getEGLErrorString(int i) {
        switch (i) {
            case 12288:
                return "EGL_SUCCESS";
            case 12289:
                return "EGL_NOT_INITIALIZED";
            case 12290:
                return "EGL_BAD_ACCESS";
            case 12291:
                return "EGL_BAD_ALLOC";
            case 12292:
                return "EGL_BAD_ATTRIBUTE";
            case 12293:
                return "EGL_BAD_CONFIG";
            case 12294:
                return "EGL_BAD_CONTEXT";
            case 12295:
                return "EGL_BAD_CURRENT_SURFACE";
            case 12296:
                return "EGL_BAD_DISPLAY";
            case 12297:
                return "EGL_BAD_MATCH";
            case 12298:
                return "EGL_BAD_NATIVE_PIXMAP";
            case 12299:
                return "EGL_BAD_NATIVE_WINDOW";
            case 12300:
                return "EGL_BAD_PARAMETER";
            case 12301:
                return "EGL_BAD_SURFACE";
            case 12302:
                return "EGL_CONTEXT_LOST";
            default:
                return "0x" + Integer.toHexString(i);
        }
    }
}
