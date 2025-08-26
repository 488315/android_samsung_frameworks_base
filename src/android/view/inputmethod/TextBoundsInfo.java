package android.view.inputmethod;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.SegmentFinder;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TextBoundsInfo implements Parcelable {
    private static final int BIDI_LEVEL_MASK = 66584576;
    private static final int BIDI_LEVEL_SHIFT = 19;
    public static final Parcelable.Creator<TextBoundsInfo> CREATOR = new Parcelable.Creator<TextBoundsInfo>() { // from class: android.view.inputmethod.TextBoundsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextBoundsInfo createFromParcel(Parcel parcel) {
            return new TextBoundsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextBoundsInfo[] newArray(int i) {
            return new TextBoundsInfo[i];
        }
    };
    public static final int FLAG_CHARACTER_LINEFEED = 2;
    public static final int FLAG_CHARACTER_PUNCTUATION = 4;
    public static final int FLAG_CHARACTER_WHITESPACE = 1;
    private static final int FLAG_GRAPHEME_SEGMENT_END = 67108864;
    private static final int FLAG_GRAPHEME_SEGMENT_START = 134217728;
    public static final int FLAG_LINE_IS_RTL = 8;
    private static final int FLAG_LINE_SEGMENT_END = 1073741824;
    private static final int FLAG_LINE_SEGMENT_START = Integer.MIN_VALUE;
    private static final int FLAG_WORD_SEGMENT_END = 268435456;
    private static final int FLAG_WORD_SEGMENT_START = 536870912;
    private static final int KNOWN_CHARACTER_FLAGS = 15;
    private static final String TEXT_BOUNDS_INFO_KEY = "android.view.inputmethod.TextBoundsInfo";
    private final float[] mCharacterBounds;
    private final int mEnd;
    private final SegmentFinder mGraphemeSegmentFinder;
    private final int[] mInternalCharacterFlags;
    private final SegmentFinder mLineSegmentFinder;
    private final float[] mMatrixValues;
    private final int mStart;
    private final SegmentFinder mWordSegmentFinder;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CharacterFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void getMatrix(Matrix matrix) {
        Objects.requireNonNull(matrix);
        matrix.setValues(this.mMatrixValues);
    }

    public int getStartIndex() {
        return this.mStart;
    }

    public int getEndIndex() {
        return this.mEnd;
    }

    public void getCharacterBounds(int i, RectF rectF) {
        int i2 = this.mStart;
        if (i < i2 || i >= this.mEnd) {
            throw new IndexOutOfBoundsException("Index is out of the bounds of [" + this.mStart + ", " + this.mEnd + ").");
        }
        int i3 = (i - i2) * 4;
        float[] fArr = this.mCharacterBounds;
        rectF.set(fArr[i3], fArr[i3 + 1], fArr[i3 + 2], fArr[i3 + 3]);
    }

    public int getCharacterFlags(int i) {
        int i2 = this.mStart;
        if (i < i2 || i >= this.mEnd) {
            throw new IndexOutOfBoundsException("Index is out of the bounds of [" + this.mStart + ", " + this.mEnd + ").");
        }
        return this.mInternalCharacterFlags[i - i2] & 15;
    }

    public int getCharacterBidiLevel(int i) {
        int i2 = this.mStart;
        if (i < i2 || i >= this.mEnd) {
            throw new IndexOutOfBoundsException("Index is out of the bounds of [" + this.mStart + ", " + this.mEnd + ").");
        }
        return (this.mInternalCharacterFlags[i - i2] & BIDI_LEVEL_MASK) >> 19;
    }

    public SegmentFinder getWordSegmentFinder() {
        return this.mWordSegmentFinder;
    }

    public SegmentFinder getGraphemeSegmentFinder() {
        return this.mGraphemeSegmentFinder;
    }

    public SegmentFinder getLineSegmentFinder() {
        return this.mLineSegmentFinder;
    }

    public int getOffsetForPosition(float f, float f2) {
        int i;
        TextBoundsInfo textBoundsInfo;
        int[] iArr = new int[2];
        RectF rectF = new RectF();
        getLineInfo(f2, iArr, rectF);
        int i2 = iArr[0];
        if (i2 == -1 || (i = iArr[1]) == -1) {
            return -1;
        }
        int i3 = (2 & getCharacterFlags(i + (-1))) != 0 ? i : i + 1;
        int iNextEndBoundary = this.mGraphemeSegmentFinder.nextEndBoundary(i2);
        if (iNextEndBoundary == -1) {
            return -1;
        }
        int i4 = -1;
        int iPreviousStartBoundary = this.mGraphemeSegmentFinder.previousStartBoundary(iNextEndBoundary);
        float f3 = Float.MAX_VALUE;
        while (iPreviousStartBoundary != -1 && iPreviousStartBoundary < i3) {
            if (iPreviousStartBoundary >= i2) {
                textBoundsInfo = this;
                float fAbs = Math.abs(textBoundsInfo.getCursorHorizontalPosition(iPreviousStartBoundary, i2, i, rectF.left, rectF.right) - f);
                if (fAbs < f3) {
                    f3 = fAbs;
                    i4 = iPreviousStartBoundary;
                }
            } else {
                textBoundsInfo = this;
            }
            iPreviousStartBoundary = textBoundsInfo.mGraphemeSegmentFinder.nextStartBoundary(iPreviousStartBoundary);
            this = textBoundsInfo;
        }
        return i4;
    }

    private boolean primaryIsTrailingPrevious(int i, int i2, int i3) {
        int characterBidiLevel;
        int characterBidiLevel2;
        if (i < i3) {
            characterBidiLevel = getCharacterBidiLevel(i);
        } else {
            characterBidiLevel = (getCharacterFlags(i + (-1)) & 8) == 8 ? 1 : 0;
        }
        if (i > i2) {
            characterBidiLevel2 = getCharacterBidiLevel(i - 1);
        } else {
            characterBidiLevel2 = (getCharacterFlags(i) & 8) == 8 ? 1 : 0;
        }
        return characterBidiLevel2 < characterBidiLevel;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0026 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private float getCursorHorizontalPosition(int i, int i2, int i3, float f, float f2) {
        boolean z;
        Preconditions.checkArgumentInRange(i, i2, i3, "index");
        boolean z2 = (getCharacterFlags(i2) & 8) != 0;
        if (primaryIsTrailingPrevious(i, i2, i3)) {
            if (i <= i2) {
                return z2 ? f2 : f;
            }
            i--;
            z = false;
            boolean z3 = (getCharacterBidiLevel(i) & 1) != 0;
            int i4 = i - this.mStart;
            float[] fArr = this.mCharacterBounds;
            int i5 = i4 * 4;
            return z3 == z ? fArr[i5] : fArr[i5 + 2];
        }
        if (i < i3) {
            z = true;
            if ((getCharacterBidiLevel(i) & 1) != 0) {
            }
            int i42 = i - this.mStart;
            float[] fArr2 = this.mCharacterBounds;
            int i52 = i42 * 4;
            if (z3 == z) {
            }
        } else if (z2) {
        }
    }

    private void getBoundsForRange(int i, int i2, RectF rectF) {
        Preconditions.checkArgumentInRange(i, this.mStart, this.mEnd - 1, "start");
        Preconditions.checkArgumentInRange(i2, i, this.mEnd, "end");
        if (i2 <= i) {
            rectF.setEmpty();
            return;
        }
        rectF.left = Float.MAX_VALUE;
        rectF.top = Float.MAX_VALUE;
        rectF.right = Float.MIN_VALUE;
        rectF.bottom = Float.MIN_VALUE;
        while (i < i2) {
            int i3 = (i - this.mStart) * 4;
            rectF.left = Math.min(rectF.left, this.mCharacterBounds[i3]);
            rectF.top = Math.min(rectF.top, this.mCharacterBounds[i3 + 1]);
            rectF.right = Math.max(rectF.right, this.mCharacterBounds[i3 + 2]);
            rectF.bottom = Math.max(rectF.bottom, this.mCharacterBounds[i3 + 3]);
            i++;
        }
    }

    private void getLineInfo(float f, int[] iArr, RectF rectF) {
        iArr[0] = -1;
        iArr[1] = -1;
        int iNextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(this.mStart);
        if (iNextEndBoundary == -1) {
            return;
        }
        int iPreviousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(iNextEndBoundary);
        RectF rectF2 = new RectF();
        float fMin = Float.MAX_VALUE;
        float fMax = Float.MIN_VALUE;
        float f2 = Float.MAX_VALUE;
        while (iPreviousStartBoundary != -1 && iPreviousStartBoundary < this.mEnd) {
            getBoundsForRange(Math.max(this.mStart, iPreviousStartBoundary), Math.min(this.mEnd, iNextEndBoundary), rectF2);
            fMin = Math.min(rectF2.top, fMin);
            fMax = Math.max(rectF2.bottom, fMax);
            float fVerticalDistance = verticalDistance(rectF2, f);
            if (fVerticalDistance == 0.0f) {
                iArr[0] = iPreviousStartBoundary;
                iArr[1] = iNextEndBoundary;
                if (rectF != null) {
                    rectF.set(rectF2);
                    return;
                }
                return;
            }
            if (fVerticalDistance < f2) {
                iArr[0] = iPreviousStartBoundary;
                iArr[1] = iNextEndBoundary;
                if (rectF != null) {
                    rectF.set(rectF2);
                }
                f2 = fVerticalDistance;
            }
            if (f < rectF.top) {
                break;
            }
            iPreviousStartBoundary = this.mLineSegmentFinder.nextStartBoundary(iPreviousStartBoundary);
            iNextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(iNextEndBoundary);
        }
        if (f < fMin || f > fMax) {
            iArr[0] = -1;
            iArr[1] = -1;
            if (rectF != null) {
                rectF.setEmpty();
            }
        }
    }

    public int[] getRangeForRect(RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        int iPreviousStartBoundary;
        int iNextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(this.mStart);
        if (iNextEndBoundary == -1) {
            return null;
        }
        int iNextEndBoundary2 = iNextEndBoundary;
        int iPreviousStartBoundary2 = this.mLineSegmentFinder.previousStartBoundary(iNextEndBoundary);
        int startForRectWithinLine = -1;
        while (iPreviousStartBoundary2 != -1 && startForRectWithinLine == -1) {
            startForRectWithinLine = getStartForRectWithinLine(iPreviousStartBoundary2, iNextEndBoundary2, rectF, segmentFinder, textInclusionStrategy);
            iPreviousStartBoundary2 = this.mLineSegmentFinder.nextStartBoundary(iPreviousStartBoundary2);
            iNextEndBoundary2 = this.mLineSegmentFinder.nextEndBoundary(iNextEndBoundary2);
        }
        if (startForRectWithinLine == -1 || (iPreviousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(this.mEnd)) == -1) {
            return null;
        }
        int iNextEndBoundary3 = this.mLineSegmentFinder.nextEndBoundary(iPreviousStartBoundary);
        int endForRectWithinLine = -1;
        while (iNextEndBoundary3 > startForRectWithinLine && endForRectWithinLine == -1) {
            endForRectWithinLine = getEndForRectWithinLine(iPreviousStartBoundary, iNextEndBoundary3, rectF, segmentFinder, textInclusionStrategy);
            iPreviousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(iPreviousStartBoundary);
            iNextEndBoundary3 = this.mLineSegmentFinder.previousEndBoundary(iNextEndBoundary3);
        }
        return new int[]{segmentFinder.previousStartBoundary(startForRectWithinLine + 1), segmentFinder.nextEndBoundary(endForRectWithinLine - 1)};
    }

    private int getStartForRectWithinLine(int i, int i2, RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        if (i >= i2) {
            return -1;
        }
        int i3 = i;
        int i4 = -1;
        while (i3 < i2) {
            int characterBidiLevel = this.getCharacterBidiLevel(i3);
            TextBoundsInfo textBoundsInfo = this;
            if (characterBidiLevel != i4) {
                int startForRectWithinRun = textBoundsInfo.getStartForRectWithinRun(i, i3, rectF, segmentFinder, textInclusionStrategy);
                if (startForRectWithinRun != -1) {
                    return startForRectWithinRun;
                }
                i = i3;
                i4 = characterBidiLevel;
            }
            i3++;
            this = textBoundsInfo;
        }
        return this.getStartForRectWithinRun(i, i2, rectF, segmentFinder, textInclusionStrategy);
    }

    private int getStartForRectWithinRun(int i, int i2, RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        int iNextEndBoundary;
        if (i >= i2 || (iNextEndBoundary = segmentFinder.nextEndBoundary(i)) == -1) {
            return -1;
        }
        int iPreviousStartBoundary = segmentFinder.previousStartBoundary(iNextEndBoundary);
        RectF rectF2 = new RectF();
        while (iPreviousStartBoundary != -1 && iPreviousStartBoundary < i2) {
            int iMax = Math.max(i, iPreviousStartBoundary);
            getBoundsForRange(iMax, Math.min(i2, iNextEndBoundary), rectF2);
            if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                return iMax;
            }
            iPreviousStartBoundary = segmentFinder.nextStartBoundary(iPreviousStartBoundary);
            iNextEndBoundary = segmentFinder.nextEndBoundary(iNextEndBoundary);
        }
        return -1;
    }

    private int getEndForRectWithinLine(int i, int i2, RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        TextBoundsInfo textBoundsInfo;
        int i3;
        if (i >= i2) {
            return -1;
        }
        int iMax = Math.max(i, this.mStart);
        int iMin = Math.min(i2, this.mEnd);
        int i4 = iMin - 1;
        int i5 = -1;
        while (i4 >= iMax) {
            int characterBidiLevel = this.getCharacterBidiLevel(i4);
            if (characterBidiLevel != i5) {
                i3 = i4 + 1;
                TextBoundsInfo textBoundsInfo2 = this;
                int endForRectWithinRun = textBoundsInfo2.getEndForRectWithinRun(i3, iMin, rectF, segmentFinder, textInclusionStrategy);
                textBoundsInfo = textBoundsInfo2;
                if (endForRectWithinRun != -1) {
                    return endForRectWithinRun;
                }
                i5 = characterBidiLevel;
            } else {
                int i6 = iMin;
                textBoundsInfo = this;
                i3 = i6;
            }
            i4--;
            this = textBoundsInfo;
            iMin = i3;
        }
        return this.getEndForRectWithinRun(iMax, iMin, rectF, segmentFinder, textInclusionStrategy);
    }

    private int getEndForRectWithinRun(int i, int i2, RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        int iPreviousStartBoundary;
        if (i >= i2 || (iPreviousStartBoundary = segmentFinder.previousStartBoundary(i2)) == -1) {
            return -1;
        }
        int iNextEndBoundary = segmentFinder.nextEndBoundary(iPreviousStartBoundary);
        RectF rectF2 = new RectF();
        while (iNextEndBoundary != -1 && iNextEndBoundary > i) {
            int iMax = Math.max(i, iPreviousStartBoundary);
            int iMin = Math.min(i2, iNextEndBoundary);
            getBoundsForRange(iMax, iMin, rectF2);
            if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                return iMin;
            }
            iPreviousStartBoundary = segmentFinder.previousStartBoundary(iPreviousStartBoundary);
            iNextEndBoundary = segmentFinder.previousEndBoundary(iNextEndBoundary);
        }
        return -1;
    }

    private static float verticalDistance(RectF rectF, float f) {
        if (rectF.top <= f && f < rectF.bottom) {
            return 0.0f;
        }
        if (f < rectF.top) {
            return rectF.top - f;
        }
        return f - rectF.bottom;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStart);
        parcel.writeInt(this.mEnd);
        parcel.writeFloatArray(this.mMatrixValues);
        parcel.writeFloatArray(this.mCharacterBounds);
        int[] iArrCopyOf = Arrays.copyOf(this.mInternalCharacterFlags, (this.mEnd - this.mStart) + 1);
        encodeSegmentFinder(iArrCopyOf, 134217728, 67108864, this.mStart, this.mEnd, this.mGraphemeSegmentFinder);
        encodeSegmentFinder(iArrCopyOf, 536870912, 268435456, this.mStart, this.mEnd, this.mWordSegmentFinder);
        encodeSegmentFinder(iArrCopyOf, Integer.MIN_VALUE, 1073741824, this.mStart, this.mEnd, this.mLineSegmentFinder);
        parcel.writeIntArray(iArrCopyOf);
    }

    private TextBoundsInfo(Parcel parcel) {
        int i = parcel.readInt();
        this.mStart = i;
        int i2 = parcel.readInt();
        this.mEnd = i2;
        this.mMatrixValues = (float[]) Objects.requireNonNull(parcel.createFloatArray());
        this.mCharacterBounds = (float[]) Objects.requireNonNull(parcel.createFloatArray());
        int[] iArr = (int[]) Objects.requireNonNull(parcel.createIntArray());
        this.mGraphemeSegmentFinder = decodeSegmentFinder(iArr, 134217728, 67108864, i, i2);
        this.mWordSegmentFinder = decodeSegmentFinder(iArr, 536870912, 268435456, i, i2);
        this.mLineSegmentFinder = decodeSegmentFinder(iArr, Integer.MIN_VALUE, 1073741824, i, i2);
        int i3 = i2 - i;
        this.mInternalCharacterFlags = new int[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            this.mInternalCharacterFlags[i4] = iArr[i4] & 66584591;
        }
    }

    private TextBoundsInfo(Builder builder) {
        int i = builder.mStart;
        this.mStart = i;
        int i2 = builder.mEnd;
        this.mEnd = i2;
        this.mMatrixValues = Arrays.copyOf(builder.mMatrixValues, 9);
        int i3 = i2 - i;
        this.mCharacterBounds = Arrays.copyOf(builder.mCharacterBounds, i3 * 4);
        this.mInternalCharacterFlags = new int[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            this.mInternalCharacterFlags[i4] = builder.mCharacterFlags[i4] | (builder.mCharacterBidiLevels[i4] << 19);
        }
        this.mGraphemeSegmentFinder = builder.mGraphemeSegmentFinder;
        this.mWordSegmentFinder = builder.mWordSegmentFinder;
        this.mLineSegmentFinder = builder.mLineSegmentFinder;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEXT_BOUNDS_INFO_KEY, this);
        return bundle;
    }

    public static TextBoundsInfo createFromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return (TextBoundsInfo) bundle.getParcelable(TEXT_BOUNDS_INFO_KEY, TextBoundsInfo.class);
    }

    public static final class Builder {
        private int[] mCharacterBidiLevels;
        private float[] mCharacterBounds;
        private int[] mCharacterFlags;
        private SegmentFinder mGraphemeSegmentFinder;
        private SegmentFinder mLineSegmentFinder;
        private boolean mMatrixInitialized;
        private SegmentFinder mWordSegmentFinder;
        private final float[] mMatrixValues = new float[9];
        private int mStart = -1;
        private int mEnd = -1;

        public Builder(int i, int i2) {
            setStartAndEnd(i, i2);
        }

        public Builder clear() {
            this.mMatrixInitialized = false;
            this.mStart = -1;
            this.mEnd = -1;
            this.mCharacterBounds = null;
            this.mCharacterFlags = null;
            this.mCharacterBidiLevels = null;
            this.mLineSegmentFinder = null;
            this.mWordSegmentFinder = null;
            this.mGraphemeSegmentFinder = null;
            return this;
        }

        public Builder setMatrix(Matrix matrix) {
            ((Matrix) Objects.requireNonNull(matrix)).getValues(this.mMatrixValues);
            this.mMatrixInitialized = true;
            return this;
        }

        public Builder setStartAndEnd(int i, int i2) {
            Preconditions.checkArgument(i >= 0);
            Preconditions.checkArgumentInRange(i, 0, i2, "start");
            this.mStart = i;
            this.mEnd = i2;
            return this;
        }

        public Builder setCharacterBounds(float[] fArr) {
            this.mCharacterBounds = (float[]) Objects.requireNonNull(fArr);
            return this;
        }

        public Builder setCharacterFlags(int[] iArr) {
            Objects.requireNonNull(iArr);
            for (int i : iArr) {
                if ((i & (-16)) != 0) {
                    throw new IllegalArgumentException("characterFlags contains invalid flags.");
                }
            }
            this.mCharacterFlags = iArr;
            return this;
        }

        public Builder setCharacterBidiLevel(int[] iArr) {
            Objects.requireNonNull(iArr);
            for (int i = 0; i < iArr.length; i++) {
                Preconditions.checkArgumentInRange(iArr[i], 0, 125, "bidiLevels[" + i + NavigationBarInflaterView.SIZE_MOD_END);
            }
            this.mCharacterBidiLevels = iArr;
            return this;
        }

        public Builder setGraphemeSegmentFinder(SegmentFinder segmentFinder) {
            this.mGraphemeSegmentFinder = (SegmentFinder) Objects.requireNonNull(segmentFinder);
            return this;
        }

        public Builder setWordSegmentFinder(SegmentFinder segmentFinder) {
            this.mWordSegmentFinder = (SegmentFinder) Objects.requireNonNull(segmentFinder);
            return this;
        }

        public Builder setLineSegmentFinder(SegmentFinder segmentFinder) {
            this.mLineSegmentFinder = (SegmentFinder) Objects.requireNonNull(segmentFinder);
            return this;
        }

        public TextBoundsInfo build() {
            int i;
            int i2 = this.mStart;
            if (i2 < 0 || (i = this.mEnd) < 0) {
                throw new IllegalStateException("Start and end must be set.");
            }
            if (!this.mMatrixInitialized) {
                throw new IllegalStateException("Matrix must be set.");
            }
            float[] fArr = this.mCharacterBounds;
            if (fArr == null) {
                throw new IllegalStateException("CharacterBounds must be set.");
            }
            int[] iArr = this.mCharacterFlags;
            if (iArr == null) {
                throw new IllegalStateException("CharacterFlags must be set.");
            }
            int[] iArr2 = this.mCharacterBidiLevels;
            if (iArr2 == null) {
                throw new IllegalStateException("CharacterBidiLevel must be set.");
            }
            if (fArr.length != (i - i2) * 4) {
                throw new IllegalStateException("The length of characterBounds doesn't match the length of the given start and end. Expected length: " + ((this.mEnd - this.mStart) * 4) + " characterBounds length: " + this.mCharacterBounds.length);
            }
            if (iArr.length != i - i2) {
                throw new IllegalStateException("The length of characterFlags doesn't match the length of the given start and end. Expected length: " + (this.mEnd - this.mStart) + " characterFlags length: " + this.mCharacterFlags.length);
            }
            if (iArr2.length != i - i2) {
                throw new IllegalStateException("The length of characterBidiLevels doesn't match the length of the given start and end. Expected length: " + (this.mEnd - this.mStart) + " characterFlags length: " + this.mCharacterBidiLevels.length);
            }
            if (this.mGraphemeSegmentFinder == null) {
                throw new IllegalStateException("GraphemeSegmentFinder must be set.");
            }
            if (this.mWordSegmentFinder == null) {
                throw new IllegalStateException("WordSegmentFinder must be set.");
            }
            SegmentFinder segmentFinder = this.mLineSegmentFinder;
            if (segmentFinder == null) {
                throw new IllegalStateException("LineSegmentFinder must be set.");
            }
            if (!TextBoundsInfo.isLineDirectionFlagConsistent(iArr, segmentFinder, i2, i)) {
                throw new IllegalStateException("characters in the same line must have the same FLAG_LINE_IS_RTL flag value.");
            }
            return new TextBoundsInfo(this);
        }
    }

    private static void encodeSegmentFinder(int[] iArr, int i, int i2, int i3, int i4, SegmentFinder segmentFinder) {
        if ((i4 - i3) + 1 != iArr.length) {
            throw new IllegalStateException("The given flags array must have the same length as the given range. flags length: " + iArr.length + " range: [" + i3 + ", " + i4 + NavigationBarInflaterView.SIZE_MOD_END);
        }
        int iNextEndBoundary = segmentFinder.nextEndBoundary(i3);
        if (iNextEndBoundary == -1) {
            return;
        }
        int iPreviousStartBoundary = segmentFinder.previousStartBoundary(iNextEndBoundary);
        while (iNextEndBoundary != -1 && iNextEndBoundary <= i4) {
            if (iPreviousStartBoundary >= i3) {
                int i5 = iPreviousStartBoundary - i3;
                iArr[i5] = iArr[i5] | i;
                int i6 = iNextEndBoundary - i3;
                iArr[i6] = iArr[i6] | i2;
            }
            iPreviousStartBoundary = segmentFinder.nextStartBoundary(iPreviousStartBoundary);
            iNextEndBoundary = segmentFinder.nextEndBoundary(iNextEndBoundary);
        }
    }

    private static SegmentFinder decodeSegmentFinder(int[] iArr, int i, int i2, int i3, int i4) {
        if ((i4 - i3) + 1 != iArr.length) {
            throw new IllegalStateException("The given flags array must have the same length as the given range. flags length: " + iArr.length + " range: [" + i3 + ", " + i4 + NavigationBarInflaterView.SIZE_MOD_END);
        }
        int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(10);
        int i5 = 0;
        for (int i6 = 0; i6 < iArr.length; i6++) {
            if ((iArr[i6] & i) == i) {
                iArrNewUnpaddedIntArray = GrowingArrayUtils.append(iArrNewUnpaddedIntArray, i5, i3 + i6);
                i5++;
            }
            if ((iArr[i6] & i2) == i2) {
                iArrNewUnpaddedIntArray = GrowingArrayUtils.append(iArrNewUnpaddedIntArray, i5, i3 + i6);
                i5++;
            }
        }
        return new SegmentFinder.PrescribedSegmentFinder(Arrays.copyOf(iArrNewUnpaddedIntArray, i5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0037, code lost:
    
        r3 = r10.nextStartBoundary(r3);
        r0 = r10.nextEndBoundary(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isLineDirectionFlagConsistent(int[] iArr, SegmentFinder segmentFinder, int i, int i2) {
        int iNextEndBoundary = segmentFinder.nextEndBoundary(i);
        if (iNextEndBoundary == -1) {
            return true;
        }
        int iPreviousStartBoundary = segmentFinder.previousStartBoundary(iNextEndBoundary);
        while (iPreviousStartBoundary != -1 && iPreviousStartBoundary < i2) {
            int iMax = Math.max(iPreviousStartBoundary, i);
            int iMin = Math.min(iNextEndBoundary, i2);
            boolean z = (iArr[iMax - i] & 8) != 0;
            do {
                iMax++;
                if (iMax < iMin) {
                }
            } while (((iArr[iMax - i] & 8) != 0) == z);
            return false;
        }
        return true;
    }
}
