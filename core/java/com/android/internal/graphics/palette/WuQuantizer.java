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

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mPalette.getSwatches();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] pixels, int colorCount) {
        QuantizerMap quantizerMap = new QuantizerMap();
        quantizerMap.quantize(pixels, colorCount);
        this.mInputPixelToCount = quantizerMap.getColorToCount();
        Set<Integer> uniqueColors = this.mInputPixelToCount.keySet();
        if (uniqueColors.size() <= colorCount) {
            this.mColors = new int[this.mInputPixelToCount.keySet().size()];
            int index = 0;
            Iterator<Integer> it = uniqueColors.iterator();
            while (it.hasNext()) {
                int color = it.next().intValue();
                this.mColors[index] = color;
                index++;
            }
        } else {
            constructHistogram(this.mInputPixelToCount);
            createMoments();
            CreateBoxesResult createBoxesResult = createBoxes(colorCount);
            this.mColors = createResult(createBoxesResult.mResultCount);
        }
        List<Palette.Swatch> swatches = new ArrayList<>();
        for (int color2 : this.mColors) {
            swatches.add(new Palette.Swatch(color2, 0));
        }
        this.mPalette = Palette.from(swatches);
    }

    public int[] getColors() {
        return this.mColors;
    }

    public Map<Integer, Integer> inputPixelToCount() {
        return this.mInputPixelToCount;
    }

    private static int getIndex(int r, int g, int b) {
        return (r << 10) + (r << 6) + (g << 5) + r + g + b;
    }

    private void constructHistogram(Map<Integer, Integer> pixels) {
        WuQuantizer wuQuantizer = this;
        wuQuantizer.mWeights = new int[TOTAL_SIZE];
        wuQuantizer.mMomentsR = new int[TOTAL_SIZE];
        wuQuantizer.mMomentsG = new int[TOTAL_SIZE];
        wuQuantizer.mMomentsB = new int[TOTAL_SIZE];
        wuQuantizer.mMoments = new double[TOTAL_SIZE];
        for (Iterator<Map.Entry<Integer, Integer>> it = pixels.entrySet().iterator(); it.hasNext(); it = it) {
            Map.Entry<Integer, Integer> pair = it.next();
            int pixel = pair.getKey().intValue();
            int count = pair.getValue().intValue();
            int red = Color.red(pixel);
            int green = Color.green(pixel);
            int blue = Color.blue(pixel);
            int iR = (red >> 3) + 1;
            int iG = (green >> 3) + 1;
            int iB = (blue >> 3) + 1;
            int index = getIndex(iR, iG, iB);
            int[] iArr = wuQuantizer.mWeights;
            iArr[index] = iArr[index] + count;
            int[] iArr2 = wuQuantizer.mMomentsR;
            iArr2[index] = iArr2[index] + (red * count);
            int[] iArr3 = wuQuantizer.mMomentsG;
            iArr3[index] = iArr3[index] + (green * count);
            int[] iArr4 = wuQuantizer.mMomentsB;
            iArr4[index] = iArr4[index] + (blue * count);
            double[] dArr = wuQuantizer.mMoments;
            dArr[index] = dArr[index] + (count * ((red * red) + (green * green) + (blue * blue)));
            wuQuantizer = this;
        }
    }

    private void createMoments() {
        int r = 1;
        while (true) {
            int i = 33;
            if (r < 33) {
                int[] area = new int[33];
                int[] areaR = new int[33];
                int[] areaG = new int[33];
                int[] areaB = new int[33];
                double[] area2 = new double[33];
                int g = 1;
                while (g < i) {
                    int line = 0;
                    int lineR = 0;
                    int lineG = 0;
                    int lineB = 0;
                    double line2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                    int b = 1;
                    while (b < i) {
                        int index = getIndex(r, g, b);
                        int line3 = line + this.mWeights[index];
                        int lineR2 = lineR + this.mMomentsR[index];
                        lineG += this.mMomentsG[index];
                        lineB += this.mMomentsB[index];
                        line2 += this.mMoments[index];
                        area[b] = area[b] + line3;
                        areaR[b] = areaR[b] + lineR2;
                        areaG[b] = areaG[b] + lineG;
                        areaB[b] = areaB[b] + lineB;
                        area2[b] = area2[b] + line2;
                        int previousIndex = getIndex(r - 1, g, b);
                        this.mWeights[index] = this.mWeights[previousIndex] + area[b];
                        this.mMomentsR[index] = this.mMomentsR[previousIndex] + areaR[b];
                        this.mMomentsG[index] = this.mMomentsG[previousIndex] + areaG[b];
                        this.mMomentsB[index] = this.mMomentsB[previousIndex] + areaB[b];
                        this.mMoments[index] = this.mMoments[previousIndex] + area2[b];
                        b++;
                        line = line3;
                        lineR = lineR2;
                        i = 33;
                    }
                    g++;
                    i = 33;
                }
                r++;
            } else {
                return;
            }
        }
    }

    private CreateBoxesResult createBoxes(int maxColorCount) {
        this.mCubes = new Box[maxColorCount];
        for (int i = 0; i < maxColorCount; i++) {
            this.mCubes[i] = new Box();
        }
        double[] volumeVariance = new double[maxColorCount];
        Box firstBox = this.mCubes[0];
        firstBox.r1 = 32;
        firstBox.g1 = 32;
        firstBox.b1 = 32;
        int generatedColorCount = 0;
        int next = 0;
        int i2 = 1;
        while (i2 < maxColorCount) {
            if (cut(this.mCubes[next], this.mCubes[i2])) {
                volumeVariance[next] = this.mCubes[next].vol > 1 ? variance(this.mCubes[next]) : 0.0d;
                volumeVariance[i2] = this.mCubes[i2].vol > 1 ? variance(this.mCubes[i2]) : 0.0d;
            } else {
                volumeVariance[next] = 0.0d;
                i2--;
            }
            next = 0;
            double temp = volumeVariance[0];
            for (int k = 1; k <= i2; k++) {
                if (volumeVariance[k] > temp) {
                    temp = volumeVariance[k];
                    next = k;
                }
            }
            generatedColorCount = i2 + 1;
            if (temp <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                break;
            }
            i2++;
        }
        return new CreateBoxesResult(maxColorCount, generatedColorCount);
    }

    private int[] createResult(int colorCount) {
        int[] colors = new int[colorCount];
        int nextAvailableIndex = 0;
        for (int i = 0; i < colorCount; i++) {
            Box cube = this.mCubes[i];
            int weight = volume(cube, this.mWeights);
            if (weight > 0) {
                int r = volume(cube, this.mMomentsR) / weight;
                int g = volume(cube, this.mMomentsG) / weight;
                int b = volume(cube, this.mMomentsB) / weight;
                int color = Color.rgb(r, g, b);
                colors[nextAvailableIndex] = color;
                nextAvailableIndex++;
            }
        }
        int[] resultArray = new int[nextAvailableIndex];
        System.arraycopy(colors, 0, resultArray, 0, nextAvailableIndex);
        return resultArray;
    }

    private double variance(Box cube) {
        int dr = volume(cube, this.mMomentsR);
        int dg = volume(cube, this.mMomentsG);
        int db = volume(cube, this.mMomentsB);
        double xx = ((((((this.mMoments[getIndex(cube.r1, cube.g1, cube.b1)] - this.mMoments[getIndex(cube.r1, cube.g1, cube.b0)]) - this.mMoments[getIndex(cube.r1, cube.g0, cube.b1)]) + this.mMoments[getIndex(cube.r1, cube.g0, cube.b0)]) - this.mMoments[getIndex(cube.r0, cube.g1, cube.b1)]) + this.mMoments[getIndex(cube.r0, cube.g1, cube.b0)]) + this.mMoments[getIndex(cube.r0, cube.g0, cube.b1)]) - this.mMoments[getIndex(cube.r0, cube.g0, cube.b0)];
        int hypotenuse = (dr * dr) + (dg * dg) + (db * db);
        int volume2 = volume(cube, this.mWeights);
        double variance2 = xx - (hypotenuse / volume2);
        return variance2;
    }

    private boolean cut(Box one, Box two) {
        Direction cutDirection;
        int wholeR = volume(one, this.mMomentsR);
        int wholeG = volume(one, this.mMomentsG);
        int wholeB = volume(one, this.mMomentsB);
        int wholeW = volume(one, this.mWeights);
        MaximizeResult maxRResult = maximize(one, Direction.RED, one.r0 + 1, one.r1, wholeR, wholeG, wholeB, wholeW);
        MaximizeResult maxGResult = maximize(one, Direction.GREEN, one.g0 + 1, one.g1, wholeR, wholeG, wholeB, wholeW);
        MaximizeResult maxBResult = maximize(one, Direction.BLUE, one.b0 + 1, one.b1, wholeR, wholeG, wholeB, wholeW);
        double maxR = maxRResult.mMaximum;
        double maxG = maxGResult.mMaximum;
        double maxB = maxBResult.mMaximum;
        if (maxR < maxG || maxR < maxB) {
            if (maxG >= maxR && maxG >= maxB) {
                cutDirection = Direction.GREEN;
            } else {
                cutDirection = Direction.BLUE;
            }
        } else {
            if (maxRResult.mCutLocation < 0) {
                return false;
            }
            cutDirection = Direction.RED;
        }
        two.r1 = one.r1;
        two.g1 = one.g1;
        two.b1 = one.b1;
        switch (cutDirection) {
            case RED:
                one.r1 = maxRResult.mCutLocation;
                two.r0 = one.r1;
                two.g0 = one.g0;
                two.b0 = one.b0;
                break;
            case GREEN:
                one.g1 = maxGResult.mCutLocation;
                two.r0 = one.r0;
                two.g0 = one.g1;
                two.b0 = one.b0;
                break;
            case BLUE:
                one.b1 = maxBResult.mCutLocation;
                two.r0 = one.r0;
                two.g0 = one.g0;
                two.b0 = one.b1;
                break;
            default:
                throw new IllegalArgumentException("unexpected direction " + cutDirection);
        }
        one.vol = (one.r1 - one.r0) * (one.g1 - one.g0) * (one.b1 - one.b0);
        two.vol = (two.r1 - two.r0) * (two.g1 - two.g0) * (two.b1 - two.b0);
        return true;
    }

    private MaximizeResult maximize(Box cube, Direction direction, int first, int last, int wholeR, int wholeG, int wholeB, int wholeW) {
        int baseR;
        WuQuantizer wuQuantizer = this;
        Box box = cube;
        Direction direction2 = direction;
        int baseR2 = bottom(box, direction2, wuQuantizer.mMomentsR);
        int baseG = bottom(box, direction2, wuQuantizer.mMomentsG);
        int baseB = bottom(box, direction2, wuQuantizer.mMomentsB);
        int baseW = bottom(box, direction2, wuQuantizer.mWeights);
        double max = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        int cut = -1;
        int i = first;
        while (i < last) {
            int halfR = top(box, direction2, i, wuQuantizer.mMomentsR) + baseR2;
            int halfG = top(box, direction2, i, wuQuantizer.mMomentsG) + baseG;
            int halfB = top(box, direction2, i, wuQuantizer.mMomentsB) + baseB;
            int halfW = top(box, direction2, i, wuQuantizer.mWeights) + baseW;
            if (halfW == 0) {
                baseR = baseR2;
            } else {
                double tempNumerator = (halfR * halfR) + (halfG * halfG) + (halfB * halfB);
                baseR = baseR2;
                double tempDenominator = halfW;
                double temp = tempNumerator / tempDenominator;
                int halfR2 = wholeR - halfR;
                int halfG2 = wholeG - halfG;
                int halfB2 = wholeB - halfB;
                int halfW2 = wholeW - halfW;
                if (halfW2 != 0) {
                    double tempNumerator2 = (halfR2 * halfR2) + (halfG2 * halfG2) + (halfB2 * halfB2);
                    double tempDenominator2 = halfW2;
                    double temp2 = temp + (tempNumerator2 / tempDenominator2);
                    if (temp2 > max) {
                        max = temp2;
                        cut = i;
                    }
                }
            }
            i++;
            wuQuantizer = this;
            box = cube;
            direction2 = direction;
            baseR2 = baseR;
        }
        return new MaximizeResult(cut, max);
    }

    private static int volume(Box cube, int[] moment) {
        return ((((((moment[getIndex(cube.r1, cube.g1, cube.b1)] - moment[getIndex(cube.r1, cube.g1, cube.b0)]) - moment[getIndex(cube.r1, cube.g0, cube.b1)]) + moment[getIndex(cube.r1, cube.g0, cube.b0)]) - moment[getIndex(cube.r0, cube.g1, cube.b1)]) + moment[getIndex(cube.r0, cube.g1, cube.b0)]) + moment[getIndex(cube.r0, cube.g0, cube.b1)]) - moment[getIndex(cube.r0, cube.g0, cube.b0)];
    }

    private static int bottom(Box cube, Direction direction, int[] moment) {
        switch (direction) {
            case RED:
                return (((-moment[getIndex(cube.r0, cube.g1, cube.b1)]) + moment[getIndex(cube.r0, cube.g1, cube.b0)]) + moment[getIndex(cube.r0, cube.g0, cube.b1)]) - moment[getIndex(cube.r0, cube.g0, cube.b0)];
            case GREEN:
                return (((-moment[getIndex(cube.r1, cube.g0, cube.b1)]) + moment[getIndex(cube.r1, cube.g0, cube.b0)]) + moment[getIndex(cube.r0, cube.g0, cube.b1)]) - moment[getIndex(cube.r0, cube.g0, cube.b0)];
            case BLUE:
                return (((-moment[getIndex(cube.r1, cube.g1, cube.b0)]) + moment[getIndex(cube.r1, cube.g0, cube.b0)]) + moment[getIndex(cube.r0, cube.g1, cube.b0)]) - moment[getIndex(cube.r0, cube.g0, cube.b0)];
            default:
                throw new IllegalArgumentException("unexpected direction " + direction);
        }
    }

    private static int top(Box cube, Direction direction, int position, int[] moment) {
        switch (direction) {
            case RED:
                return ((moment[getIndex(position, cube.g1, cube.b1)] - moment[getIndex(position, cube.g1, cube.b0)]) - moment[getIndex(position, cube.g0, cube.b1)]) + moment[getIndex(position, cube.g0, cube.b0)];
            case GREEN:
                return ((moment[getIndex(cube.r1, position, cube.b1)] - moment[getIndex(cube.r1, position, cube.b0)]) - moment[getIndex(cube.r0, position, cube.b1)]) + moment[getIndex(cube.r0, position, cube.b0)];
            case BLUE:
                return ((moment[getIndex(cube.r1, cube.g1, position)] - moment[getIndex(cube.r1, cube.g0, position)]) - moment[getIndex(cube.r0, cube.g1, position)]) + moment[getIndex(cube.r0, cube.g0, position)];
            default:
                throw new IllegalArgumentException("unexpected direction " + direction);
        }
    }

    private static class MaximizeResult {
        final int mCutLocation;
        final double mMaximum;

        MaximizeResult(int cut, double max) {
            this.mCutLocation = cut;
            this.mMaximum = max;
        }
    }

    private static class CreateBoxesResult {
        final int mRequestedCount;
        final int mResultCount;

        CreateBoxesResult(int requestedCount, int resultCount) {
            this.mRequestedCount = requestedCount;
            this.mResultCount = resultCount;
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
