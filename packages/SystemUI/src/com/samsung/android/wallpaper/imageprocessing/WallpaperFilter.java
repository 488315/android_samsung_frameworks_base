package com.samsung.android.wallpaper.imageprocessing;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.function.Consumer;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WallpaperFilter {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ProcessingRange {
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
            return ReorderTile$$ExternalSyntheticOutline0.m(this.length, " )", sb);
        }
    }

    static {
        System.loadLibrary("WallpaperFilter");
    }

    public final void applyFilter(String str, final Bitmap bitmap) {
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
            final int[] nativeCreateGaussianNoiseSamples = nativeCreateGaussianNoiseSamples(f2, PluginLockInstancePolicy.DISABLED_BY_MODE);
            applyFilterOnMultiThread("GaussianNoise", new ProcessingRange(bitmap.getHeight()), new Consumer() { // from class: com.samsung.android.wallpaper.imageprocessing.WallpaperFilter$$ExternalSyntheticLambda3
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

    public final void applyFilterOnMultiThread(String str, ProcessingRange processingRange, Consumer consumer) {
        final int i = 4;
        final boolean[] zArr = new boolean[4];
        for (int i2 = 0; i2 < 4; i2++) {
            zArr[i2] = false;
        }
        synchronized (zArr) {
            final int i3 = processingRange.totalSize;
            final int i4 = (i3 + 3) / 4;
            final int i5 = 0;
            while (i5 < 4) {
                final long elapsedRealtime = SystemClock.elapsedRealtime();
                WallpaperFilter wallpaperFilter = this;
                final String str2 = str;
                final Consumer consumer2 = consumer;
                Thread thread = new Thread(new Runnable(wallpaperFilter) { // from class: com.samsung.android.wallpaper.imageprocessing.WallpaperFilter.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i6 = i4;
                        int i7 = i5;
                        int i8 = i6 * i7;
                        int i9 = i;
                        if (i7 >= i9 - 1) {
                            i6 = i3 - ((i9 - 1) * i6);
                        }
                        long elapsedRealtime2 = SystemClock.elapsedRealtime();
                        ProcessingRange processingRange2 = new ProcessingRange(i3);
                        processingRange2.start = i8;
                        processingRange2.length = i6;
                        int i10 = WallpaperFilter.$r8$clinit;
                        Log.i("WallpaperFilter", "applyFilterOnMultiThread before [" + str2 + "] : tid " + i5 + processingRange2);
                        consumer2.accept(processingRange2);
                        synchronized (zArr) {
                            try {
                                Log.i("WallpaperFilter", "applyFilterOnMultiThread[" + str2 + "] : tid " + i5 + " finished. startDelay=" + (elapsedRealtime2 - elapsedRealtime) + ", pureJniDur=" + (SystemClock.elapsedRealtime() - elapsedRealtime2));
                                zArr[i5] = true;
                                int i11 = 0;
                                while (true) {
                                    if (i11 >= i) {
                                        zArr.notify();
                                        break;
                                    } else if (zArr[i11]) {
                                        i11++;
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                });
                thread.setPriority(10);
                thread.start();
                i5++;
                this = wallpaperFilter;
                str = str2;
                consumer = consumer2;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ImageFilterParams {
        public final float mBlurRadius;
        public final float mHighlightAmount;
        public final float mNoiseValue;

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
