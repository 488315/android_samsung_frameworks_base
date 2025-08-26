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
        char[] cArrObtain = TemporaryBuffer.obtain(charSequence.length());
        TextUtils.getChars(charSequence, 0, charSequence.length(), cArrObtain, 0);
        textPaint.getTextWidths(cArrObtain, 0, charSequence.length(), rawArray);
        GraphemeBreak.isGraphemeBreak(rawArray, cArrObtain, 0, charSequence.length(), zArr);
        TemporaryBuffer.recycle(cArrObtain);
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
        int iPreviousBoundary;
        if (i == 0 || (iPreviousBoundary = previousBoundary(i)) == -1 || previousBoundary(iPreviousBoundary) == -1) {
            return -1;
        }
        return iPreviousBoundary;
    }

    @Override // android.text.SegmentFinder
    public int nextStartBoundary(int i) {
        int iNextBoundary;
        if (i == this.mIsGraphemeBreak.length || (iNextBoundary = nextBoundary(i)) == -1 || nextBoundary(iNextBoundary) == -1) {
            return -1;
        }
        return iNextBoundary;
    }

    @Override // android.text.SegmentFinder
    public int nextEndBoundary(int i) {
        return nextBoundary(i);
    }
}
