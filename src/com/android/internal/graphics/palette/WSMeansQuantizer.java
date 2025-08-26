package com.android.internal.graphics.palette;

import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.WSMeansQuantizer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/* loaded from: classes5.dex */
public final class WSMeansQuantizer implements Quantizer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final int MAX_ITERATIONS = 10;
    private static final float MIN_MOVEMENT_DISTANCE = 3.0f;
    private static final String TAG = "QuantizerWsmeans";
    private int[] mClusterIndices;
    private int[] mClusterPopulations;
    private float[][] mClusters;
    private Map<Integer, Integer> mInputPixelToCount;
    private Palette mPalette;
    private int[] mPixels;
    private final PointProvider mPointProvider;
    private float[][] mPoints;
    private int[][] mIndexMatrix = new int[0][];
    private float[][] mDistanceMatrix = new float[0][];

    public WSMeansQuantizer(int[] iArr, PointProvider pointProvider, Map<Integer, Integer> map) {
        int i = 0;
        this.mPointProvider = pointProvider;
        this.mClusters = (float[][]) Array.newInstance((Class<?>) Float.TYPE, iArr.length, 3);
        int length = iArr.length;
        int i2 = 0;
        while (i < length) {
            this.mClusters[i2] = pointProvider.fromInt(iArr[i]);
            i++;
            i2++;
        }
        this.mInputPixelToCount = map;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mPalette.getSwatches();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        if (this.mInputPixelToCount == null) {
            QuantizerMap quantizerMap = new QuantizerMap();
            quantizerMap.quantize(iArr, i);
            this.mInputPixelToCount = quantizerMap.getColorToCount();
        }
        this.mPoints = (float[][]) Array.newInstance((Class<?>) Float.TYPE, this.mInputPixelToCount.size(), 3);
        this.mPixels = new int[this.mInputPixelToCount.size()];
        Iterator<Integer> it = this.mInputPixelToCount.keySet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            this.mPixels[i2] = iIntValue;
            this.mPoints[i2] = this.mPointProvider.fromInt(iIntValue);
            i2++;
        }
        float[][] fArr = this.mClusters;
        if (fArr.length > 0) {
            i = Math.min(i, fArr.length);
        }
        int iMin = Math.min(i, this.mPoints.length);
        initializeClusters(iMin);
        for (int i3 = 0; i3 < 10; i3++) {
            calculateClusterDistances(iMin);
            if (!reassignPoints(iMin)) {
                break;
            }
            recalculateClusterCenters(iMin);
        }
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < iMin; i4++) {
            arrayList.add(new Palette.Swatch(this.mPointProvider.toInt(this.mClusters[i4]), this.mClusterPopulations[i4]));
        }
        this.mPalette = Palette.from(arrayList);
    }

    private void initializeClusters(int i) {
        float[][] fArr = this.mClusters;
        if (fArr.length <= 0) {
            int length = i - fArr.length;
            Random random = new Random(272008L);
            ArrayList arrayList = new ArrayList(length);
            HashSet hashSet = new HashSet();
            for (int i2 = 0; i2 < length; i2++) {
                int iNextInt = random.nextInt(this.mPoints.length);
                while (hashSet.contains(Integer.valueOf(iNextInt))) {
                    int size = hashSet.size();
                    float[][] fArr2 = this.mPoints;
                    if (size < fArr2.length) {
                        iNextInt = random.nextInt(fArr2.length);
                    }
                }
                hashSet.add(Integer.valueOf(iNextInt));
                arrayList.add(this.mPoints[iNextInt]);
            }
            float[][] fArr3 = (float[][]) arrayList.toArray();
            float[][] fArr4 = (float[][]) Arrays.copyOf(this.mClusters, i);
            System.arraycopy(fArr3, 0, fArr4, fArr4.length, fArr3.length);
            this.mClusters = fArr4;
        }
        int[] iArr = this.mPixels;
        this.mClusterIndices = new int[iArr.length];
        this.mClusterPopulations = new int[iArr.length];
        Random random2 = new Random(272008L);
        for (int i3 = 0; i3 < this.mPixels.length; i3++) {
            this.mClusterIndices[i3] = random2.nextInt(i);
            this.mClusterPopulations[i3] = this.mInputPixelToCount.get(Integer.valueOf(this.mPixels[i3])).intValue();
        }
    }

    void calculateClusterDistances(int i) {
        if (this.mDistanceMatrix.length != i) {
            this.mDistanceMatrix = (float[][]) Array.newInstance((Class<?>) Float.TYPE, i, i);
        }
        int i2 = 0;
        while (i2 <= i) {
            int i3 = i2 + 1;
            for (int i4 = i3; i4 < i; i4++) {
                PointProvider pointProvider = this.mPointProvider;
                float[][] fArr = this.mClusters;
                float fDistance = pointProvider.distance(fArr[i2], fArr[i4]);
                float[][] fArr2 = this.mDistanceMatrix;
                fArr2[i4][i2] = fDistance;
                fArr2[i2][i4] = fDistance;
            }
            i2 = i3;
        }
        if (this.mIndexMatrix.length != i) {
            this.mIndexMatrix = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i, i);
        }
        for (int i5 = 0; i5 < i; i5++) {
            ArrayList arrayList = new ArrayList(i);
            for (int i6 = 0; i6 < i; i6++) {
                arrayList.add(new Distance(i6, this.mDistanceMatrix[i5][i6]));
            }
            arrayList.sort(new Comparator() { // from class: com.android.internal.graphics.palette.WSMeansQuantizer$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Float.compare(((WSMeansQuantizer.Distance) obj).getDistance(), ((WSMeansQuantizer.Distance) obj2).getDistance());
                }
            });
            for (int i7 = 0; i7 < i; i7++) {
                this.mIndexMatrix[i5][i7] = ((Distance) arrayList.get(i7)).getIndex();
            }
        }
    }

    boolean reassignPoints(int i) {
        int i2 = 0;
        boolean z = false;
        while (true) {
            float[][] fArr = this.mPoints;
            if (i2 >= fArr.length) {
                return z;
            }
            float[] fArr2 = fArr[i2];
            int i3 = this.mClusterIndices[i2];
            float fDistance = this.mPointProvider.distance(fArr2, this.mClusters[i3]);
            float f = fDistance;
            int i4 = -1;
            for (int i5 = 1; i5 < i; i5++) {
                int i6 = this.mIndexMatrix[i3][i5];
                if (this.mDistanceMatrix[i3][i6] >= 4.0f * fDistance) {
                    break;
                }
                float fDistance2 = this.mPointProvider.distance(fArr2, this.mClusters[i6]);
                if (fDistance2 < f) {
                    i4 = i6;
                    f = fDistance2;
                }
            }
            if (i4 != -1 && ((float) Math.abs(Math.sqrt(f) - Math.sqrt(fDistance))) > 3.0f) {
                this.mClusterIndices[i2] = i4;
                z = true;
            }
            i2++;
        }
    }

    void recalculateClusterCenters(int i) {
        this.mClusterPopulations = new int[i];
        float[] fArr = new float[i];
        float[] fArr2 = new float[i];
        float[] fArr3 = new float[i];
        int i2 = 0;
        while (true) {
            float[][] fArr4 = this.mPoints;
            if (i2 >= fArr4.length) {
                break;
            }
            int i3 = this.mClusterIndices[i2];
            float[] fArr5 = fArr4[i2];
            int iIntValue = this.mInputPixelToCount.get(Integer.valueOf(this.mPixels[i2])).intValue();
            int[] iArr = this.mClusterPopulations;
            iArr[i3] = iArr[i3] + iIntValue;
            float f = iIntValue;
            fArr[i3] = fArr[i3] + (fArr5[0] * f);
            fArr2[i3] = fArr2[i3] + (fArr5[1] * f);
            fArr3[i3] = fArr3[i3] + (fArr5[2] * f);
            i2++;
        }
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = this.mClusterPopulations[i4];
            float f2 = fArr[i4];
            float f3 = fArr2[i4];
            float f4 = fArr3[i4];
            float[] fArr6 = this.mClusters[i4];
            float f5 = i5;
            fArr6[0] = f2 / f5;
            fArr6[1] = f3 / f5;
            fArr6[2] = f4 / f5;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Distance {
        private final float mDistance;
        private final int mIndex;

        int getIndex() {
            return this.mIndex;
        }

        float getDistance() {
            return this.mDistance;
        }

        Distance(int i, float f) {
            this.mIndex = i;
            this.mDistance = f;
        }
    }
}
