package androidx.compose.ui.graphics;

import android.graphics.Bitmap;
import androidx.compose.ui.graphics.ImageBitmapConfig;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidImageBitmap_androidKt {
    public static final Bitmap asAndroidBitmap(ImageBitmap imageBitmap) {
        if (imageBitmap instanceof AndroidImageBitmap) {
            return ((AndroidImageBitmap) imageBitmap).bitmap;
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Bitmap");
    }

    /* renamed from: toBitmapConfig-1JJdX4A, reason: not valid java name */
    public static final Bitmap.Config m431toBitmapConfig1JJdX4A(int i) {
        ImageBitmapConfig.Companion companion = ImageBitmapConfig.Companion;
        companion.getClass();
        if (i == 0) {
            return Bitmap.Config.ARGB_8888;
        }
        companion.getClass();
        if (i == ImageBitmapConfig.Alpha8) {
            return Bitmap.Config.ALPHA_8;
        }
        companion.getClass();
        if (i == ImageBitmapConfig.Rgb565) {
            return Bitmap.Config.RGB_565;
        }
        companion.getClass();
        if (i == ImageBitmapConfig.F16) {
            return Bitmap.Config.RGBA_F16;
        }
        companion.getClass();
        return i == ImageBitmapConfig.Gpu ? Bitmap.Config.HARDWARE : Bitmap.Config.ARGB_8888;
    }
}
