package androidx.compose.ui.graphics;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public final class AndroidImageBitmap implements ImageBitmap {
    public final Bitmap bitmap;

    public AndroidImageBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /* renamed from: getConfig-_sVssgQ, reason: not valid java name */
    public final int m432getConfig_sVssgQ() {
        Bitmap.Config config = this.bitmap.getConfig();
        config.getClass();
        if (config == Bitmap.Config.ALPHA_8) {
            ImageBitmapConfig.Companion.getClass();
            return ImageBitmapConfig.Alpha8;
        }
        if (config == Bitmap.Config.RGB_565) {
            ImageBitmapConfig.Companion.getClass();
            return ImageBitmapConfig.Rgb565;
        }
        if (config == Bitmap.Config.ARGB_4444) {
            ImageBitmapConfig.Companion.getClass();
            return 0;
        }
        if (config == Bitmap.Config.RGBA_F16) {
            ImageBitmapConfig.Companion.getClass();
            return ImageBitmapConfig.F16;
        }
        if (config == Bitmap.Config.HARDWARE) {
            ImageBitmapConfig.Companion.getClass();
            return ImageBitmapConfig.Gpu;
        }
        ImageBitmapConfig.Companion.getClass();
        return 0;
    }
}
