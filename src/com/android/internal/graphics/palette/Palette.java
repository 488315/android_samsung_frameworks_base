package com.android.internal.graphics.palette;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.scontext.SContextConstants;
import android.os.AsyncTask;
import android.util.Log;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public final class Palette {
    static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;
    static final Filter DEFAULT_FILTER = new Filter() { // from class: com.android.internal.graphics.palette.Palette.1
        private static final float BLACK_MAX_LIGHTNESS = 0.05f;
        private static final float WHITE_MIN_LIGHTNESS = 0.95f;

        @Override // com.android.internal.graphics.palette.Palette.Filter
        public boolean isAllowed(int i, float[] fArr) {
            return (isWhite(fArr) || isBlack(fArr) || isNearRedILine(fArr)) ? false : true;
        }

        private boolean isBlack(float[] fArr) {
            return fArr[2] <= BLACK_MAX_LIGHTNESS;
        }

        private boolean isWhite(float[] fArr) {
            return fArr[2] >= WHITE_MIN_LIGHTNESS;
        }

        private boolean isNearRedILine(float[] fArr) {
            float f = fArr[0];
            return f >= 10.0f && f <= 37.0f && fArr[1] <= 0.82f;
        }
    };
    static final int DEFAULT_RESIZE_BITMAP_AREA = 12544;
    static final String LOG_TAG = "Palette";
    private final Swatch mDominantSwatch = findDominantSwatch();
    private final List<Swatch> mSwatches;

    public interface Filter {
        boolean isAllowed(int i, float[] fArr);
    }

    public interface PaletteAsyncListener {
        void onGenerated(Palette palette);
    }

    public static Builder from(Bitmap bitmap, Quantizer quantizer) {
        return new Builder(bitmap, quantizer);
    }

    public static Palette from(List<Swatch> list) {
        return new Builder(list).generate();
    }

    Palette(List<Swatch> list) {
        this.mSwatches = list;
    }

    public List<Swatch> getSwatches() {
        return Collections.unmodifiableList(this.mSwatches);
    }

    public Swatch getDominantSwatch() {
        return this.mDominantSwatch;
    }

    private Swatch findDominantSwatch() {
        int size = this.mSwatches.size();
        int i = Integer.MIN_VALUE;
        Swatch swatch = null;
        for (int i2 = 0; i2 < size; i2++) {
            Swatch swatch2 = this.mSwatches.get(i2);
            if (swatch2.getPopulation() > i) {
                i = swatch2.getPopulation();
                swatch = swatch2;
            }
        }
        return swatch;
    }

    public static class Swatch {
        private final Color mColor;
        private final int mPopulation;

        public Swatch(int i, int i2) {
            this.mColor = Color.valueOf(i);
            this.mPopulation = i2;
        }

        public int getInt() {
            return this.mColor.toArgb();
        }

        public int getPopulation() {
            return this.mPopulation;
        }

        public String toString() {
            return getClass().getSimpleName() + " [" + this.mColor + "] [Population: " + this.mPopulation + ']';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Swatch swatch = (Swatch) obj;
                if (this.mPopulation == swatch.mPopulation && this.mColor.toArgb() == swatch.mColor.toArgb()) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (this.mColor.toArgb() * 31) + this.mPopulation;
        }
    }

    public static class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Bitmap mBitmap;
        private Quantizer mQuantizer;
        private Rect mRegion;
        private final List<Swatch> mSwatches;
        private int mMaxColors = 16;
        private int mResizeArea = Palette.DEFAULT_RESIZE_BITMAP_AREA;
        private int mResizeMaxDimension = -1;

        public Builder(Bitmap bitmap, Quantizer quantizer) {
            this.mQuantizer = new ColorCutQuantizer();
            if (bitmap == null || bitmap.isRecycled()) {
                throw new IllegalArgumentException("Bitmap is not valid");
            }
            this.mSwatches = null;
            this.mBitmap = bitmap;
            this.mQuantizer = quantizer == null ? new ColorCutQuantizer() : quantizer;
        }

        public Builder(List<Swatch> list) {
            this.mQuantizer = new ColorCutQuantizer();
            if (list == null || list.isEmpty()) {
                throw new IllegalArgumentException("List of Swatches is not valid");
            }
            this.mSwatches = list;
            this.mBitmap = null;
            this.mQuantizer = null;
        }

        public Builder maximumColorCount(int i) {
            this.mMaxColors = i;
            return this;
        }

        @Deprecated
        public Builder resizeBitmapSize(int i) {
            this.mResizeMaxDimension = i;
            this.mResizeArea = -1;
            return this;
        }

        public Builder resizeBitmapArea(int i) {
            this.mResizeArea = i;
            this.mResizeMaxDimension = -1;
            return this;
        }

        public Builder setRegion(int i, int i2, int i3, int i4) {
            if (this.mBitmap != null) {
                if (this.mRegion == null) {
                    this.mRegion = new Rect();
                }
                this.mRegion.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                if (!this.mRegion.intersect(i, i2, i3, i4)) {
                    throw new IllegalArgumentException("The given region must intersect with the Bitmap's dimensions.");
                }
            }
            return this;
        }

        public Builder clearRegion() {
            this.mRegion = null;
            return this;
        }

        public Palette generate() {
            List<Swatch> list;
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                Bitmap scaleBitmapDown = scaleBitmapDown(bitmap);
                Rect rect = this.mRegion;
                if (scaleBitmapDown != this.mBitmap && rect != null) {
                    double width = scaleBitmapDown.getWidth() / this.mBitmap.getWidth();
                    rect.left = (int) Math.floor(rect.left * width);
                    rect.top = (int) Math.floor(rect.top * width);
                    rect.right = Math.min((int) Math.ceil(rect.right * width), scaleBitmapDown.getWidth());
                    rect.bottom = Math.min((int) Math.ceil(rect.bottom * width), scaleBitmapDown.getHeight());
                }
                this.mQuantizer.quantize(getPixelsFromBitmap(scaleBitmapDown), this.mMaxColors);
                if (scaleBitmapDown != this.mBitmap) {
                    scaleBitmapDown.recycle();
                }
                list = this.mQuantizer.getQuantizedColors();
            } else {
                list = this.mSwatches;
                if (list == null) {
                    throw new AssertionError();
                }
            }
            return new Palette(list);
        }

        @Deprecated
        public AsyncTask<Bitmap, Void, Palette> generate(final PaletteAsyncListener paletteAsyncListener) {
            return new AsyncTask<Bitmap, Void, Palette>() { // from class: com.android.internal.graphics.palette.Palette.Builder.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public Palette doInBackground(Bitmap... bitmapArr) {
                    try {
                        return Builder.this.generate();
                    } catch (Exception e) {
                        Log.e(Palette.LOG_TAG, "Exception thrown during async generate", e);
                        return null;
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(Palette palette) {
                    paletteAsyncListener.onGenerated(palette);
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.mBitmap);
        }

        private int[] getPixelsFromBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[width * height];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            Rect rect = this.mRegion;
            if (rect == null) {
                return iArr;
            }
            int width2 = rect.width();
            int height2 = this.mRegion.height();
            int[] iArr2 = new int[width2 * height2];
            for (int i = 0; i < height2; i++) {
                System.arraycopy(iArr, ((this.mRegion.top + i) * width) + this.mRegion.left, iArr2, i * width2, width2);
            }
            return iArr2;
        }

        private Bitmap scaleBitmapDown(Bitmap bitmap) {
            int max;
            int i;
            double d = -1.0d;
            if (this.mResizeArea > 0) {
                int width = bitmap.getWidth() * bitmap.getHeight();
                int i2 = this.mResizeArea;
                if (width > i2) {
                    d = Math.sqrt(i2 / width);
                }
            } else if (this.mResizeMaxDimension > 0 && (max = Math.max(bitmap.getWidth(), bitmap.getHeight())) > (i = this.mResizeMaxDimension)) {
                d = i / max;
            }
            return d <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? bitmap : Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(bitmap.getWidth() * d), (int) Math.ceil(bitmap.getHeight() * d), false);
        }
    }
}
