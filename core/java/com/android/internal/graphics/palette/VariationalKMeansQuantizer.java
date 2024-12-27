package com.android.internal.graphics.palette;

import com.android.internal.graphics.ColorUtils;
import com.android.internal.ml.clustering.KMeans;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VariationalKMeansQuantizer implements Quantizer {
    private static final boolean DEBUG = false;
    private static final String TAG = "KMeansQuantizer";
    private final int mInitializations;
    private final KMeans mKMeans;
    private final float mMinClusterSqDistance;
    private List<Palette.Swatch> mQuantizedColors;

    public VariationalKMeansQuantizer() {
        this(0.25f);
    }

    public VariationalKMeansQuantizer(float minClusterDistance) {
        this(minClusterDistance, 1);
    }

    public VariationalKMeansQuantizer(float minClusterDistance, int initializations) {
        this.mKMeans = new KMeans(new Random(0L), 30, 0.0f);
        this.mMinClusterSqDistance = minClusterDistance * minClusterDistance;
        this.mInitializations = initializations;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] pixels, int maxColors) {
        float[] hsl;
        float[] hsl2 = {0.0f, 0.0f, 0.0f};
        float[][] hslPixels =
                (float[][]) Array.newInstance((Class<?>) Float.TYPE, pixels.length, 3);
        for (int i = 0; i < pixels.length; i++) {
            ColorUtils.colorToHSL(pixels[i], hsl2);
            hslPixels[i][0] = hsl2[0] / 360.0f;
            hslPixels[i][1] = hsl2[1];
            hslPixels[i][2] = hsl2[2];
        }
        List<KMeans.Mean> optimalMeans = getOptimalKMeans(maxColors, hslPixels);
        for (int i2 = 0; i2 < optimalMeans.size(); i2++) {
            KMeans.Mean current = optimalMeans.get(i2);
            float[] currentCentroid = current.getCentroid();
            int j = i2 + 1;
            while (j < optimalMeans.size()) {
                KMeans.Mean compareTo = optimalMeans.get(j);
                float[] compareToCentroid = compareTo.getCentroid();
                float sqDistance = KMeans.sqDistance(currentCentroid, compareToCentroid);
                if (sqDistance >= this.mMinClusterSqDistance) {
                    hsl = hsl2;
                } else {
                    optimalMeans.remove(compareTo);
                    current.getItems().addAll(compareTo.getItems());
                    int k = 0;
                    while (k < currentCentroid.length) {
                        int k2 = k;
                        currentCentroid[k2] =
                                (float)
                                        (currentCentroid[k]
                                                + ((compareToCentroid[k] - currentCentroid[k])
                                                        / 2.0d));
                        k = k2 + 1;
                        hsl2 = hsl2;
                    }
                    hsl = hsl2;
                    j--;
                }
                j++;
                hsl2 = hsl;
            }
        }
        this.mQuantizedColors = new ArrayList();
        float[] mHsl = new float[3];
        for (KMeans.Mean mean : optimalMeans) {
            if (mean.getItems().size() != 0) {
                float[] centroid = mean.getCentroid();
                mHsl[0] = centroid[0] * 360.0f;
                mHsl[1] = centroid[1];
                mHsl[2] = centroid[2];
                int color = ColorUtils.HSLToColor(mHsl);
                this.mQuantizedColors.add(new Palette.Swatch(color, mean.getItems().size()));
            }
        }
    }

    private List<KMeans.Mean> getOptimalKMeans(int k, float[][] inputData) {
        List<KMeans.Mean> optimal = null;
        double optimalScore = -1.7976931348623157E308d;
        for (int runs = this.mInitializations; runs > 0; runs--) {
            List<KMeans.Mean> means = this.mKMeans.predict(k, inputData);
            double score = KMeans.score(means);
            if (optimal == null || score > optimalScore) {
                optimalScore = score;
                optimal = means;
            }
        }
        return optimal;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mQuantizedColors;
    }
}
