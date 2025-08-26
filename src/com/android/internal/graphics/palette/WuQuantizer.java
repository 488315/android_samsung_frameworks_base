package com.android.internal.graphics.palette;

import android.graphics.Color;
import android.hardware.scontext.SContextConstants;
import com.android.internal.graphics.palette.Palette;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public final class WuQuantizer implements Quantizer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BITS = 5;
    private static final int MAX_INDEX = 32;
    private static final int SIDE_LENGTH = 33;
    private static final int TOTAL_SIZE = 35937;
    private int[] mColors;
    private Box[] mCubes;
    private Map<Integer, Integer> mInputPixelToCount;
    private double[] mMoments;
    private int[] mMomentsB;
    private int[] mMomentsG;
    private int[] mMomentsR;
    private Palette mPalette;
    private int[] mWeights;

    private enum Direction {
        RED,
        GREEN,
        BLUE
    }

    private static int getIndex(int i, int i2, int i3) {
        return (i << 10) + (i << 6) + (i2 << 5) + i + i2 + i3;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mPalette.getSwatches();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        QuantizerMap quantizerMap = new QuantizerMap();
        quantizerMap.quantize(iArr, i);
        Map<Integer, Integer> colorToCount = quantizerMap.getColorToCount();
        this.mInputPixelToCount = colorToCount;
        Set<Integer> setKeySet = colorToCount.keySet();
        if (setKeySet.size() <= i) {
            this.mColors = new int[this.mInputPixelToCount.keySet().size()];
            Iterator<Integer> it = setKeySet.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                this.mColors[i2] = it.next().intValue();
                i2++;
            }
        } else {
            constructHistogram(this.mInputPixelToCount);
            createMoments();
            this.mColors = createResult(createBoxes(i).mResultCount);
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 : this.mColors) {
            arrayList.add(new Palette.Swatch(i3, 0));
        }
        this.mPalette = Palette.from(arrayList);
    }

    public int[] getColors() {
        return this.mColors;
    }

    public Map<Integer, Integer> inputPixelToCount() {
        return this.mInputPixelToCount;
    }

    private void constructHistogram(Map<Integer, Integer> map) {
        this.mWeights = new int[TOTAL_SIZE];
        this.mMomentsR = new int[TOTAL_SIZE];
        this.mMomentsG = new int[TOTAL_SIZE];
        this.mMomentsB = new int[TOTAL_SIZE];
        this.mMoments = new double[TOTAL_SIZE];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int iIntValue = entry.getKey().intValue();
            int iIntValue2 = entry.getValue().intValue();
            int iRed = Color.red(iIntValue);
            int iGreen = Color.green(iIntValue);
            int iBlue = Color.blue(iIntValue);
            int index = getIndex((iRed >> 3) + 1, (iGreen >> 3) + 1, (iBlue >> 3) + 1);
            int[] iArr = this.mWeights;
            iArr[index] = iArr[index] + iIntValue2;
            int[] iArr2 = this.mMomentsR;
            iArr2[index] = iArr2[index] + (iRed * iIntValue2);
            int[] iArr3 = this.mMomentsG;
            iArr3[index] = iArr3[index] + (iGreen * iIntValue2);
            int[] iArr4 = this.mMomentsB;
            iArr4[index] = iArr4[index] + (iBlue * iIntValue2);
            double[] dArr = this.mMoments;
            dArr[index] = dArr[index] + (iIntValue2 * ((iRed * iRed) + (iGreen * iGreen) + (iBlue * iBlue)));
        }
    }

    private void createMoments() {
        int i = 1;
        while (true) {
            int i2 = 33;
            if (i >= 33) {
                return;
            }
            int[] iArr = new int[33];
            int[] iArr2 = new int[33];
            int[] iArr3 = new int[33];
            int[] iArr4 = new int[33];
            double[] dArr = new double[33];
            int i3 = 1;
            while (i3 < i2) {
                int i4 = 0;
                int i5 = 0;
                double d = 0.0d;
                int i6 = 1;
                int i7 = 0;
                int i8 = 0;
                while (i6 < i2) {
                    int index = getIndex(i, i3, i6);
                    i4 += this.mWeights[index];
                    i7 += this.mMomentsR[index];
                    i8 += this.mMomentsG[index];
                    i5 += this.mMomentsB[index];
                    d += this.mMoments[index];
                    iArr[i6] = iArr[i6] + i4;
                    iArr2[i6] = iArr2[i6] + i7;
                    iArr3[i6] = iArr3[i6] + i8;
                    iArr4[i6] = iArr4[i6] + i5;
                    dArr[i6] = dArr[i6] + d;
                    int index2 = getIndex(i - 1, i3, i6);
                    int i9 = i6;
                    int[] iArr5 = this.mWeights;
                    iArr5[index] = iArr5[index2] + iArr[i9];
                    int[] iArr6 = this.mMomentsR;
                    iArr6[index] = iArr6[index2] + iArr2[i9];
                    int[] iArr7 = this.mMomentsG;
                    iArr7[index] = iArr7[index2] + iArr3[i9];
                    int[] iArr8 = this.mMomentsB;
                    iArr8[index] = iArr8[index2] + iArr4[i9];
                    double[] dArr2 = this.mMoments;
                    dArr2[index] = dArr2[index2] + dArr[i9];
                    i6 = i9 + 1;
                    i2 = 33;
                }
                i3++;
                i2 = 33;
            }
            i++;
        }
    }

    private CreateBoxesResult createBoxes(int i) {
        this.mCubes = new Box[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mCubes[i2] = new Box();
        }
        double[] dArr = new double[i];
        Box box = this.mCubes[0];
        box.r1 = 32;
        box.g1 = 32;
        box.b1 = 32;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        while (true) {
            if (i5 >= i) {
                break;
            }
            Box[] boxArr = this.mCubes;
            if (cut(boxArr[i3], boxArr[i5])) {
                dArr[i3] = this.mCubes[i3].vol > 1 ? variance(this.mCubes[i3]) : 0.0d;
                dArr[i5] = this.mCubes[i5].vol > 1 ? variance(this.mCubes[i5]) : 0.0d;
            } else {
                dArr[i3] = 0.0d;
                i5--;
            }
            double d = dArr[0];
            int i6 = 0;
            for (int i7 = 1; i7 <= i5; i7++) {
                double d2 = dArr[i7];
                if (d2 > d) {
                    i6 = i7;
                    d = d2;
                }
            }
            i5++;
            if (d <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                i4 = i5;
                break;
            }
            i4 = i5;
            i3 = i6;
        }
        return new CreateBoxesResult(i, i4);
    }

    private int[] createResult(int i) {
        int[] iArr = new int[i];
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Box box = this.mCubes[i3];
            int iVolume = volume(box, this.mWeights);
            if (iVolume > 0) {
                iArr[i2] = Color.rgb(volume(box, this.mMomentsR) / iVolume, volume(box, this.mMomentsG) / iVolume, volume(box, this.mMomentsB) / iVolume);
                i2++;
            }
        }
        int[] iArr2 = new int[i2];
        System.arraycopy(iArr, 0, iArr2, 0, i2);
        return iArr2;
    }

    private double variance(Box box) {
        int iVolume = volume(box, this.mMomentsR);
        int iVolume2 = volume(box, this.mMomentsG);
        int iVolume3 = volume(box, this.mMomentsB);
        return (((((((this.mMoments[getIndex(box.r1, box.g1, box.b1)] - this.mMoments[getIndex(box.r1, box.g1, box.b0)]) - this.mMoments[getIndex(box.r1, box.g0, box.b1)]) + this.mMoments[getIndex(box.r1, box.g0, box.b0)]) - this.mMoments[getIndex(box.r0, box.g1, box.b1)]) + this.mMoments[getIndex(box.r0, box.g1, box.b0)]) + this.mMoments[getIndex(box.r0, box.g0, box.b1)]) - this.mMoments[getIndex(box.r0, box.g0, box.b0)]) - ((((iVolume * iVolume) + (iVolume2 * iVolume2)) + (iVolume3 * iVolume3)) / volume(box, this.mWeights));
    }

    private boolean cut(Box box, Box box2) {
        Direction direction;
        int iVolume = volume(box, this.mMomentsR);
        int iVolume2 = volume(box, this.mMomentsG);
        int iVolume3 = volume(box, this.mMomentsB);
        int iVolume4 = volume(box, this.mWeights);
        MaximizeResult maximizeResultMaximize = maximize(box, Direction.RED, box.r0 + 1, box.r1, iVolume, iVolume2, iVolume3, iVolume4);
        MaximizeResult maximizeResultMaximize2 = maximize(box, Direction.GREEN, box.g0 + 1, box.g1, iVolume, iVolume2, iVolume3, iVolume4);
        MaximizeResult maximizeResultMaximize3 = maximize(box, Direction.BLUE, box.b0 + 1, box.b1, iVolume, iVolume2, iVolume3, iVolume4);
        double d = maximizeResultMaximize.mMaximum;
        double d2 = maximizeResultMaximize2.mMaximum;
        double d3 = maximizeResultMaximize3.mMaximum;
        if (d < d2 || d < d3) {
            if (d2 >= d && d2 >= d3) {
                direction = Direction.GREEN;
            } else {
                direction = Direction.BLUE;
            }
        } else {
            if (maximizeResultMaximize.mCutLocation < 0) {
                return false;
            }
            direction = Direction.RED;
        }
        box2.r1 = box.r1;
        box2.g1 = box.g1;
        box2.b1 = box.b1;
        int iOrdinal = direction.ordinal();
        if (iOrdinal == 0) {
            box.r1 = maximizeResultMaximize.mCutLocation;
            box2.r0 = box.r1;
            box2.g0 = box.g0;
            box2.b0 = box.b0;
        } else if (iOrdinal == 1) {
            box.g1 = maximizeResultMaximize2.mCutLocation;
            box2.r0 = box.r0;
            box2.g0 = box.g1;
            box2.b0 = box.b0;
        } else if (iOrdinal == 2) {
            box.b1 = maximizeResultMaximize3.mCutLocation;
            box2.r0 = box.r0;
            box2.g0 = box.g0;
            box2.b0 = box.b1;
        } else {
            throw new IllegalArgumentException("unexpected direction " + direction);
        }
        box.vol = (box.r1 - box.r0) * (box.g1 - box.g0) * (box.b1 - box.b0);
        box2.vol = (box2.r1 - box2.r0) * (box2.g1 - box2.g0) * (box2.b1 - box2.b0);
        return true;
    }

    private MaximizeResult maximize(Box box, Direction direction, int i, int i2, int i3, int i4, int i5, int i6) {
        WuQuantizer wuQuantizer = this;
        Box box2 = box;
        int iBottom = bottom(box2, direction, wuQuantizer.mMomentsR);
        int iBottom2 = bottom(box2, direction, wuQuantizer.mMomentsG);
        int iBottom3 = bottom(box2, direction, wuQuantizer.mMomentsB);
        int iBottom4 = bottom(box2, direction, wuQuantizer.mWeights);
        int i7 = -1;
        double d = 0.0d;
        int i8 = i;
        while (i8 < i2) {
            int pVar = top(box2, direction, i8, wuQuantizer.mMomentsR) + iBottom;
            int pVar2 = top(box2, direction, i8, wuQuantizer.mMomentsG) + iBottom2;
            int pVar3 = top(box2, direction, i8, wuQuantizer.mMomentsB) + iBottom3;
            int pVar4 = top(box2, direction, i8, wuQuantizer.mWeights) + iBottom4;
            if (pVar4 != 0) {
                double d2 = (((pVar * pVar) + (pVar2 * pVar2)) + (pVar3 * pVar3)) / pVar4;
                int i9 = i3 - pVar;
                int i10 = i4 - pVar2;
                int i11 = i5 - pVar3;
                int i12 = i6 - pVar4;
                if (i12 != 0) {
                    double d3 = d2 + ((((i9 * i9) + (i10 * i10)) + (i11 * i11)) / i12);
                    if (d3 > d) {
                        d = d3;
                        i7 = i8;
                    }
                }
            }
            i8++;
            wuQuantizer = this;
            box2 = box;
        }
        return new MaximizeResult(i7, d);
    }

    private static int volume(Box box, int[] iArr) {
        return ((((((iArr[getIndex(box.r1, box.g1, box.b1)] - iArr[getIndex(box.r1, box.g1, box.b0)]) - iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)]) - iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)]) + iArr[getIndex(box.r0, box.g0, box.b1)]) - iArr[getIndex(box.r0, box.g0, box.b0)];
    }

    private static int bottom(Box box, Direction direction, int[] iArr) {
        int i;
        int i2;
        int iOrdinal = direction.ordinal();
        if (iOrdinal == 0) {
            i = (-iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)] + iArr[getIndex(box.r0, box.g0, box.b1)];
            i2 = iArr[getIndex(box.r0, box.g0, box.b0)];
        } else if (iOrdinal == 1) {
            i = (-iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)] + iArr[getIndex(box.r0, box.g0, box.b1)];
            i2 = iArr[getIndex(box.r0, box.g0, box.b0)];
        } else if (iOrdinal == 2) {
            i = (-iArr[getIndex(box.r1, box.g1, box.b0)]) + iArr[getIndex(box.r1, box.g0, box.b0)] + iArr[getIndex(box.r0, box.g1, box.b0)];
            i2 = iArr[getIndex(box.r0, box.g0, box.b0)];
        } else {
            throw new IllegalArgumentException("unexpected direction " + direction);
        }
        return i - i2;
    }

    private static int top(Box box, Direction direction, int i, int[] iArr) {
        int i2;
        int i3;
        int iOrdinal = direction.ordinal();
        if (iOrdinal == 0) {
            i2 = (iArr[getIndex(i, box.g1, box.b1)] - iArr[getIndex(i, box.g1, box.b0)]) - iArr[getIndex(i, box.g0, box.b1)];
            i3 = iArr[getIndex(i, box.g0, box.b0)];
        } else if (iOrdinal == 1) {
            i2 = (iArr[getIndex(box.r1, i, box.b1)] - iArr[getIndex(box.r1, i, box.b0)]) - iArr[getIndex(box.r0, i, box.b1)];
            i3 = iArr[getIndex(box.r0, i, box.b0)];
        } else if (iOrdinal == 2) {
            i2 = (iArr[getIndex(box.r1, box.g1, i)] - iArr[getIndex(box.r1, box.g0, i)]) - iArr[getIndex(box.r0, box.g1, i)];
            i3 = iArr[getIndex(box.r0, box.g0, i)];
        } else {
            throw new IllegalArgumentException("unexpected direction " + direction);
        }
        return i2 + i3;
    }

    private static class MaximizeResult {
        final int mCutLocation;
        final double mMaximum;

        MaximizeResult(int i, double d) {
            this.mCutLocation = i;
            this.mMaximum = d;
        }
    }

    private static class CreateBoxesResult {
        final int mRequestedCount;
        final int mResultCount;

        CreateBoxesResult(int i, int i2) {
            this.mRequestedCount = i;
            this.mResultCount = i2;
        }
    }

    private static class Box {
        public int b0;
        public int b1;
        public int g0;
        public int g1;
        public int r0;
        public int r1;
        public int vol;

        private Box() {
            this.r0 = 0;
            this.r1 = 0;
            this.g0 = 0;
            this.g1 = 0;
            this.b0 = 0;
            this.b1 = 0;
            this.vol = 0;
        }
    }
}
