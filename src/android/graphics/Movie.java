package android.graphics;

import android.content.res.AssetManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
/* loaded from: classes.dex */
public class Movie {
    private long mNativeMovie;

    public static native Movie decodeByteArray(byte[] bArr, int i, int i2);

    private native void nDraw(long j, float f, float f2, long j2);

    private static native Movie nativeDecodeAsset(long j);

    private static native Movie nativeDecodeStream(InputStream inputStream);

    private static native void nativeDestructor(long j);

    public native int duration();

    public native int height();

    public native boolean isOpaque();

    public native boolean setTime(int i);

    public native int width();

    private Movie(long j) {
        if (j == 0) {
            throw new RuntimeException("native movie creation failed");
        }
        this.mNativeMovie = j;
    }

    public void draw(Canvas canvas, float f, float f2, Paint paint) {
        nDraw(canvas.getNativeCanvasWrapper(), f, f2, paint != null ? paint.getNativeInstance() : 0L);
    }

    public void draw(Canvas canvas, float f, float f2) {
        nDraw(canvas.getNativeCanvasWrapper(), f, f2, 0L);
    }

    public static Movie decodeStream(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        if (inputStream instanceof AssetManager.AssetInputStream) {
            return nativeDecodeAsset(((AssetManager.AssetInputStream) inputStream).getNativeAsset());
        }
        return nativeDecodeStream(inputStream);
    }

    public static Movie decodeFile(String str) {
        try {
            return decodeTempStream(new FileInputStream(str));
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestructor(this.mNativeMovie);
            this.mNativeMovie = 0L;
        } finally {
            super.finalize();
        }
    }

    private static Movie decodeTempStream(InputStream inputStream) {
        try {
            Movie decodeStream = decodeStream(inputStream);
            try {
                inputStream.close();
                return decodeStream;
            } catch (IOException unused) {
                return decodeStream;
            }
        } catch (IOException unused2) {
            return null;
        }
    }
}
