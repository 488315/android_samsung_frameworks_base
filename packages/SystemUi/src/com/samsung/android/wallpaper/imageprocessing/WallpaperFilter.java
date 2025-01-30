package com.samsung.android.wallpaper.imageprocessing;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import java.util.function.Consumer;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class WallpaperFilter {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ProcessingRange {
        public int length;
        public int start;
        public final int totalSize;

        public ProcessingRange(int i) {
            this.totalSize = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Range ( totalSize = ");
            sb.append(this.totalSize);
            sb.append(", start = ");
            sb.append(this.start);
            sb.append(", length = ");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.length, " )");
        }
    }

    static {
        System.loadLibrary("WallpaperFilter");
    }

    public final void applyFilter(final Bitmap bitmap, String str) {
        ImageFilterParams imageFilterParams = new ImageFilterParams(str);
        if (bitmap == null) {
            Log.e("WallpaperFilter", "applyFilter: null bitmap. skipped");
            return;
        }
        float f = imageFilterParams.mBlurRadius;
        if (0.0f < f) {
            int round = Math.round(f);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Log.i("WallpaperFilter", "applyStackBlur : " + width + " x " + height);
            applyFilterOnMultiThread("StackBlur1", new ProcessingRange(height), new WallpaperFilter$$ExternalSyntheticLambda0(this, bitmap, round, 0));
            applyFilterOnMultiThread("StackBlur2", new ProcessingRange(width), new WallpaperFilter$$ExternalSyntheticLambda0(this, bitmap, round, 1));
        }
        float f2 = imageFilterParams.mNoiseValue;
        if (0.0f < f2) {
            final int[] nativeCreateGaussianNoiseSamples = nativeCreateGaussianNoiseSamples(f2, 20000);
            applyFilterOnMultiThread("GaussianNoise", new ProcessingRange(bitmap.getHeight()), new Consumer() { // from class: com.samsung.android.wallpaper.imageprocessing.WallpaperFilter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WallpaperFilter wallpaperFilter = WallpaperFilter.this;
                    Bitmap bitmap2 = bitmap;
                    int[] iArr = nativeCreateGaussianNoiseSamples;
                    WallpaperFilter.ProcessingRange processingRange = (WallpaperFilter.ProcessingRange) obj;
                    int i = WallpaperFilter.$r8$clinit;
                    wallpaperFilter.getClass();
                    wallpaperFilter.nativeSetGaussianNoise(bitmap2, processingRange.start, processingRange.length, iArr);
                }
            });
        }
        float f3 = imageFilterParams.mHighlightAmount;
        if (0.0f < f3) {
            applyFilterOnMultiThread("highlight", new ProcessingRange(bitmap.getHeight()), new WallpaperFilter$$ExternalSyntheticLambda0(this, bitmap, Math.round(f3), 2));
        }
    }

    public final void applyFilterOnMultiThread(final String str, ProcessingRange processingRange, final Consumer consumer) {
        final boolean[] zArr = new boolean[4];
        for (int i = 0; i < 4; i++) {
            zArr[i] = false;
        }
        synchronized (zArr) {
            final int i2 = processingRange.totalSize;
            final int i3 = ((i2 + 4) - 1) / 4;
            int i4 = 0;
            for (int i5 = 4; i4 < i5; i5 = 4) {
                final long elapsedRealtime = SystemClock.elapsedRealtime();
                final int i6 = 4;
                final int i7 = i4;
                Thread thread = new Thread(new Runnable(this) { // from class: com.samsung.android.wallpaper.imageprocessing.WallpaperFilter.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i8 = i3;
                        int i9 = i7;
                        int i10 = i8 * i9;
                        int i11 = i6;
                        boolean z = true;
                        if (i9 >= i11 - 1) {
                            i8 = i2 - ((i11 - 1) * i8);
                        }
                        long elapsedRealtime2 = SystemClock.elapsedRealtime();
                        ProcessingRange processingRange2 = new ProcessingRange(i2);
                        processingRange2.start = i10;
                        processingRange2.length = i8;
                        int i12 = WallpaperFilter.$r8$clinit;
                        Log.i("WallpaperFilter", "applyFilterOnMultiThread before [" + str + "] : tid " + i7 + processingRange2);
                        consumer.accept(processingRange2);
                        synchronized (zArr) {
                            Log.i("WallpaperFilter", "applyFilterOnMultiThread[" + str + "] : tid " + i7 + " finished. startDelay=" + (elapsedRealtime2 - elapsedRealtime) + ", pureJniDur=" + (SystemClock.elapsedRealtime() - elapsedRealtime2));
                            zArr[i7] = true;
                            int i13 = 0;
                            while (true) {
                                if (i13 >= i6) {
                                    break;
                                }
                                if (!zArr[i13]) {
                                    z = false;
                                    break;
                                }
                                i13++;
                            }
                            if (z) {
                                zArr.notify();
                            }
                        }
                    }
                });
                thread.setPriority(10);
                thread.start();
                i4++;
            }
            try {
                zArr.wait();
            } catch (Exception unused) {
            }
        }
    }

    public native int[] nativeCreateGaussianNoiseSamples(double d, int i);

    public native void nativeSetGaussianNoise(Bitmap bitmap, int i, int i2, int[] iArr);

    public native void nativeSetHighLightFilter(Bitmap bitmap, int i, int i2, int i3);

    public native void nativeStackBlur(Bitmap bitmap, int i, int i2, int i3, int i4);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ImageFilterParams {
        public float mBlurRadius;
        public float mHighlightAmount;
        public float mNoiseValue;

        public ImageFilterParams() {
            this.mBlurRadius = 0.0f;
            this.mNoiseValue = 0.0f;
            this.mHighlightAmount = 0.0f;
        }

        public ImageFilterParams(String str) {
            this.mBlurRadius = 0.0f;
            this.mNoiseValue = 0.0f;
            this.mHighlightAmount = 0.0f;
            if (str == null) {
                int i = WallpaperFilter.$r8$clinit;
                Log.e("WallpaperFilter", "decode: null data");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("blur_radius")) {
                    this.mBlurRadius = Float.parseFloat(jSONObject.getString("blur_radius"));
                }
                if (jSONObject.has("noise_value")) {
                    this.mNoiseValue = Float.parseFloat(jSONObject.getString("noise_value"));
                }
                if (jSONObject.has("highlight_amount")) {
                    this.mHighlightAmount = Float.parseFloat(jSONObject.getString("highlight_amount"));
                }
            } catch (JSONException e) {
                int i2 = WallpaperFilter.$r8$clinit;
                Log.e("WallpaperFilter", "decode : e=" + e);
            }
        }
    }
}
