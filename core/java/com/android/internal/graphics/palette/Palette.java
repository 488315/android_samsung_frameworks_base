package com.android.internal.graphics.palette;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.scontext.SContextConstants;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Collections;
import java.util.List;

public final class Palette {
    static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;
    static final Filter DEFAULT_FILTER =
            new Filter() { // from class: com.android.internal.graphics.palette.Palette.1
                private static final float BLACK_MAX_LIGHTNESS = 0.05f;
                private static final float WHITE_MIN_LIGHTNESS = 0.95f;

                @Override // com.android.internal.graphics.palette.Palette.Filter
                public boolean isAllowed(int rgb, float[] hsl) {
                    return (isWhite(hsl) || isBlack(hsl) || isNearRedILine(hsl)) ? false : true;
                }

                private boolean isBlack(float[] hslColor) {
                    return hslColor[2] <= BLACK_MAX_LIGHTNESS;
                }

                private boolean isWhite(float[] hslColor) {
                    return hslColor[2] >= WHITE_MIN_LIGHTNESS;
                }

                private boolean isNearRedILine(float[] hslColor) {
                    return hslColor[0] >= 10.0f && hslColor[0] <= 37.0f && hslColor[1] <= 0.82f;
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

    public static Palette from(List<Swatch> swatches) {
        return new Builder(swatches).generate();
    }

    Palette(List<Swatch> swatches) {
        this.mSwatches = swatches;
    }

    public List<Swatch> getSwatches() {
        return Collections.unmodifiableList(this.mSwatches);
    }

    public Swatch getDominantSwatch() {
        return this.mDominantSwatch;
    }

    private Swatch findDominantSwatch() {
        int maxPop = Integer.MIN_VALUE;
        Swatch maxSwatch = null;
        int count = this.mSwatches.size();
        for (int i = 0; i < count; i++) {
            Swatch swatch = this.mSwatches.get(i);
            if (swatch.getPopulation() > maxPop) {
                maxSwatch = swatch;
                maxPop = swatch.getPopulation();
            }
        }
        return maxSwatch;
    }

    public static class Swatch {
        private final Color mColor;
        private final int mPopulation;

        public Swatch(int colorInt, int population) {
            this.mColor = Color.valueOf(colorInt);
            this.mPopulation = population;
        }

        public int getInt() {
            return this.mColor.toArgb();
        }

        public int getPopulation() {
            return this.mPopulation;
        }

        public String toString() {
            return getClass().getSimpleName()
                    + " ["
                    + this.mColor
                    + "] [Population: "
                    + this.mPopulation
                    + ']';
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Swatch swatch = (Swatch) o;
            if (this.mPopulation == swatch.mPopulation
                    && this.mColor.toArgb() == swatch.mColor.toArgb()) {
                return true;
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

        public Builder(List<Swatch> swatches) {
            this.mQuantizer = new ColorCutQuantizer();
            if (swatches == null || swatches.isEmpty()) {
                throw new IllegalArgumentException("List of Swatches is not valid");
            }
            this.mSwatches = swatches;
            this.mBitmap = null;
            this.mQuantizer = null;
        }

        public Builder maximumColorCount(int colors) {
            this.mMaxColors = colors;
            return this;
        }

        @Deprecated
        public Builder resizeBitmapSize(int maxDimension) {
            this.mResizeMaxDimension = maxDimension;
            this.mResizeArea = -1;
            return this;
        }

        public Builder resizeBitmapArea(int area) {
            this.mResizeArea = area;
            this.mResizeMaxDimension = -1;
            return this;
        }

        public Builder setRegion(int left, int top, int right, int bottom) {
            if (this.mBitmap != null) {
                if (this.mRegion == null) {
                    this.mRegion = new Rect();
                }
                this.mRegion.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                if (!this.mRegion.intersect(left, top, right, bottom)) {
                    throw new IllegalArgumentException(
                            "The given region must intersect with the Bitmap's dimensions.");
                }
            }
            return this;
        }

        public Builder clearRegion() {
            this.mRegion = null;
            return this;
        }

        public Palette generate() {
            List<Swatch> swatches;
            if (this.mBitmap != null) {
                Bitmap bitmap = scaleBitmapDown(this.mBitmap);
                Rect region = this.mRegion;
                if (bitmap != this.mBitmap && region != null) {
                    double scale = bitmap.getWidth() / this.mBitmap.getWidth();
                    region.left = (int) Math.floor(region.left * scale);
                    region.top = (int) Math.floor(region.top * scale);
                    region.right =
                            Math.min((int) Math.ceil(region.right * scale), bitmap.getWidth());
                    region.bottom =
                            Math.min((int) Math.ceil(region.bottom * scale), bitmap.getHeight());
                }
                this.mQuantizer.quantize(getPixelsFromBitmap(bitmap), this.mMaxColors);
                if (bitmap != this.mBitmap) {
                    bitmap.recycle();
                }
                swatches = this.mQuantizer.getQuantizedColors();
            } else {
                List<Swatch> swatches2 = this.mSwatches;
                if (swatches2 != null) {
                    swatches = this.mSwatches;
                } else {
                    throw new AssertionError();
                }
            }
            Palette p = new Palette(swatches);
            return p;
        }

        @Deprecated
        public AsyncTask<Bitmap, Void, Palette> generate(final PaletteAsyncListener listener) {
            return new AsyncTask<
                    Bitmap,
                    Void,
                    Palette>() { // from class:
                                 // com.android.internal.graphics.palette.Palette.Builder.1
                @Override // android.os.AsyncTask
                public Palette doInBackground(Bitmap... params) {
                    try {
                        return Builder.this.generate();
                    } catch (Exception e) {
                        Log.e(Palette.LOG_TAG, "Exception thrown during async generate", e);
                        return null;
                    }
                }

                @Override // android.os.AsyncTask
                public void onPostExecute(Palette colorExtractor) {
                    listener.onGenerated(colorExtractor);
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.mBitmap);
        }

        private int[] getPixelsFromBitmap(Bitmap bitmap) {
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            int[] pixels = new int[bitmapWidth * bitmapHeight];
            bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
            if (this.mRegion == null) {
                return pixels;
            }
            int regionWidth = this.mRegion.width();
            int regionHeight = this.mRegion.height();
            int[] subsetPixels = new int[regionWidth * regionHeight];
            for (int row = 0; row < regionHeight; row++) {
                System.arraycopy(
                        pixels,
                        ((this.mRegion.top + row) * bitmapWidth) + this.mRegion.left,
                        subsetPixels,
                        row * regionWidth,
                        regionWidth);
            }
            return subsetPixels;
        }

        private Bitmap scaleBitmapDown(Bitmap bitmap) {
            int maxDimension;
            double scaleRatio = -1.0d;
            if (this.mResizeArea > 0) {
                int bitmapArea = bitmap.getWidth() * bitmap.getHeight();
                if (bitmapArea > this.mResizeArea) {
                    scaleRatio = Math.sqrt(this.mResizeArea / bitmapArea);
                }
            } else if (this.mResizeMaxDimension > 0
                    && (maxDimension = Math.max(bitmap.getWidth(), bitmap.getHeight()))
                            > this.mResizeMaxDimension) {
                scaleRatio = this.mResizeMaxDimension / maxDimension;
            }
            if (scaleRatio <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                return bitmap;
            }
            return Bitmap.createScaledBitmap(
                    bitmap,
                    (int) Math.ceil(bitmap.getWidth() * scaleRatio),
                    (int) Math.ceil(bitmap.getHeight() * scaleRatio),
                    false);
        }
    }
}
