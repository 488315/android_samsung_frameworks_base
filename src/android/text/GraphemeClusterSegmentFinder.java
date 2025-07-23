package android.text;

import android.graphics.TemporaryBuffer;
import android.graphics.text.GraphemeBreak;
import android.text.AutoGrowArray;

/* loaded from: classes4.dex */
public class GraphemeClusterSegmentFinder extends SegmentFinder {
    private static AutoGrowArray.FloatArray sTempAdvances;
    private final boolean[] mIsGraphemeBreak;

    public GraphemeClusterSegmentFinder(CharSequence charSequence, TextPaint textPaint) {
        AutoGrowArray.FloatArray floatArray = sTempAdvances;
        if (floatArray == null) {
            sTempAdvances = new AutoGrowArray.FloatArray(charSequence.length());
        } else if (floatArray.size() < charSequence.length()) {
            sTempAdvances.resize(charSequence.length());
        }
        boolean[] zArr = new boolean[charSequence.length()];
        this.mIsGraphemeBreak = zArr;
        float[] rawArray = sTempAdvances.getRawArray();
        char[] obtain = TemporaryBuffer.obtain(charSequence.length());
        TextUtils.getChars(charSequence, 0, charSequence.length(), obtain, 0);
        textPaint.getTextWidths(obtain, 0, charSequence.length(), rawArray);
        GraphemeBreak.isGraphemeBreak(rawArray, obtain, 0, charSequence.length(), zArr);
        TemporaryBuffer.recycle(obtain);
    }

    private int previousBoundary(int i) {
        if (i <= 0) {
            return -1;
        }
        do {
            i--;
            if (i <= 0) {
                break;
            }
        } while (!this.mIsGraphemeBreak[i]);
        return i;
    }

    private int nextBoundary(int i) {
        boolean[] zArr;
        if (i >= this.mIsGraphemeBreak.length) {
            return -1;
        }
        do {
            i++;
            zArr = this.mIsGraphemeBreak;
            if (i >= zArr.length) {
                break;
            }
        } while (!zArr[i]);
        return i;
    }

    @Override // android.text.SegmentFinder
    public int previousStartBoundary(int i) {
        return previousBoundary(i);
    }

    @Override // android.text.SegmentFinder
    public int previousEndBoundary(int i) {
        int previousBoundary;
        if (i == 0 || (previousBoundary = previousBoundary(i)) == -1 || previousBoundary(previousBoundary) == -1) {
            return -1;
        }
        return previousBoundary;
    }

    @Override // android.text.SegmentFinder
    public int nextStartBoundary(int i) {
        int nextBoundary;
        if (i == this.mIsGraphemeBreak.length || (nextBoundary = nextBoundary(i)) == -1 || nextBoundary(nextBoundary) == -1) {
            return -1;
        }
        return nextBoundary;
    }

    @Override // android.text.SegmentFinder
    public int nextEndBoundary(int i) {
        return nextBoundary(i);
    }
}
