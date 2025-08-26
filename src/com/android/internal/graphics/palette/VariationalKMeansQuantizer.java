package com.android.internal.graphics.palette;

import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.ml.clustering.KMeans;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes5.dex */
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

    public VariationalKMeansQuantizer(float f) {
        this(f, 1);
    }

    public VariationalKMeansQuantizer(float f, int i) {
        this.mKMeans = new KMeans(new Random(0L), 30, 0.0f);
        this.mMinClusterSqDistance = f * f;
        this.mInitializations = i;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        char c;
        float[] fArr;
        float[] fArr2 = {0.0f, 0.0f, 0.0f};
        char c2 = 2;
        float[][] fArr3 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, iArr.length, 3);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            ColorUtils.colorToHSL(iArr[i2], fArr2);
            float[] fArr4 = fArr3[i2];
            fArr4[0] = fArr2[0] / 360.0f;
            fArr4[1] = fArr2[1];
            fArr4[2] = fArr2[2];
        }
        List<KMeans.Mean> optimalKMeans = getOptimalKMeans(i, fArr3);
        int i3 = 0;
        while (i3 < optimalKMeans.size()) {
            KMeans.Mean mean = optimalKMeans.get(i3);
            float[] centroid = mean.getCentroid();
            i3++;
            int i4 = i3;
            while (i4 < optimalKMeans.size()) {
                KMeans.Mean mean2 = optimalKMeans.get(i4);
                if (KMeans.sqDistance(centroid, mean2.getCentroid()) < this.mMinClusterSqDistance) {
                    optimalKMeans.remove(mean2);
                    mean.getItems().addAll(mean2.getItems());
                    int i5 = 0;
                    while (i5 < centroid.length) {
                        float[] fArr5 = centroid;
                        fArr5[i5] = (float) (centroid[i5] + ((r12[i5] - r13) / 2.0d));
                        i5++;
                        centroid = fArr5;
                        c2 = c2;
                    }
                    c = c2;
                    fArr = centroid;
                    i4--;
                } else {
                    c = c2;
                    fArr = centroid;
                }
                i4++;
                centroid = fArr;
                c2 = c;
            }
        }
        char c3 = c2;
        this.mQuantizedColors = new ArrayList();
        for (KMeans.Mean mean3 : optimalKMeans) {
            if (mean3.getItems().size() != 0) {
                float[] centroid2 = mean3.getCentroid();
                float f = centroid2[0] * 360.0f;
                float f2 = centroid2[1];
                float f3 = centroid2[c3];
                float[] fArr6 = new float[3];
                fArr6[0] = f;
                fArr6[1] = f2;
                fArr6[c3] = f3;
                this.mQuantizedColors.add(new Palette.Swatch(ColorUtils.HSLToColor(fArr6), mean3.getItems().size()));
            }
        }
    }

    private List<KMeans.Mean> getOptimalKMeans(int i, float[][] fArr) {
        List<KMeans.Mean> list = null;
        double d = -1.7976931348623157E308d;
        for (int i2 = this.mInitializations; i2 > 0; i2--) {
            List<KMeans.Mean> listPredict = this.mKMeans.predict(i, fArr);
            double dScore = KMeans.score(listPredict);
            if (list == null || dScore > d) {
                list = listPredict;
                d = dScore;
            }
        }
        return list;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mQuantizedColors;
    }
}
