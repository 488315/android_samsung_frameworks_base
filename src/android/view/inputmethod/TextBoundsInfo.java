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
        int nextEndBoundary = this.mGraphemeSegmentFinder.nextEndBoundary(i2);
        if (nextEndBoundary == -1) {
            return -1;
        }
        int i4 = -1;
        int previousStartBoundary = this.mGraphemeSegmentFinder.previousStartBoundary(nextEndBoundary);
        float f3 = Float.MAX_VALUE;
        while (previousStartBoundary != -1 && previousStartBoundary < i3) {
            if (previousStartBoundary >= i2) {
                textBoundsInfo = this;
                float abs = Math.abs(textBoundsInfo.getCursorHorizontalPosition(previousStartBoundary, i2, i, rectF.left, rectF.right) - f);
                if (abs < f3) {
                    f3 = abs;
                    i4 = previousStartBoundary;
                }
            } else {
                textBoundsInfo = this;
            }
            previousStartBoundary = textBoundsInfo.mGraphemeSegmentFinder.nextStartBoundary(previousStartBoundary);
            this = textBoundsInfo;
        }
        return i4;
    }

    private boolean primaryIsTrailingPrevious(int i, int i2, int i3) {
        int i4;
        int i5;
        if (i < i3) {
            i4 = getCharacterBidiLevel(i);
        } else {
            i4 = (getCharacterFlags(i + (-1)) & 8) == 8 ? 1 : 0;
        }
        if (i > i2) {
            i5 = getCharacterBidiLevel(i - 1);
        } else {
            i5 = (getCharacterFlags(i) & 8) == 8 ? 1 : 0;
        }
        return i5 < i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float getCursorHorizontalPosition(int r5, int r6, int r7, float r8, float r9) {
        /*
            r4 = this;
            java.lang.String r0 = "index"
            com.android.internal.util.Preconditions.checkArgumentInRange(r5, r6, r7, r0)
            int r0 = r4.getCharacterFlags(r6)
            r0 = r0 & 8
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L11
            r0 = r2
            goto L12
        L11:
            r0 = r1
        L12:
            boolean r3 = r4.primaryIsTrailingPrevious(r5, r6, r7)
            if (r3 == 0) goto L21
            if (r5 > r6) goto L1d
            if (r0 == 0) goto L25
            goto L26
        L1d:
            int r5 = r5 + (-1)
            r6 = r1
            goto L28
        L21:
            if (r5 < r7) goto L27
            if (r0 == 0) goto L26
        L25:
            return r8
        L26:
            return r9
        L27:
            r6 = r2
        L28:
            int r7 = r4.getCharacterBidiLevel(r5)
            r7 = r7 & r2
            if (r7 == 0) goto L30
            r1 = r2
        L30:
            int r7 = r4.mStart
            int r5 = r5 - r7
            float[] r4 = r4.mCharacterBounds
            int r5 = r5 * 4
            if (r1 == r6) goto L3c
            r4 = r4[r5]
            return r4
        L3c:
            int r5 = r5 + 2
            r4 = r4[r5]
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.TextBoundsInfo.getCursorHorizontalPosition(int, int, int, float, float):float");
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
        int nextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(this.mStart);
        if (nextEndBoundary == -1) {
            return;
        }
        int previousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(nextEndBoundary);
        RectF rectF2 = new RectF();
        float f2 = Float.MAX_VALUE;
        float f3 = Float.MIN_VALUE;
        float f4 = Float.MAX_VALUE;
        while (previousStartBoundary != -1 && previousStartBoundary < this.mEnd) {
            getBoundsForRange(Math.max(this.mStart, previousStartBoundary), Math.min(this.mEnd, nextEndBoundary), rectF2);
            f2 = Math.min(rectF2.top, f2);
            f3 = Math.max(rectF2.bottom, f3);
            float verticalDistance = verticalDistance(rectF2, f);
            if (verticalDistance == 0.0f) {
                iArr[0] = previousStartBoundary;
                iArr[1] = nextEndBoundary;
                if (rectF != null) {
                    rectF.set(rectF2);
                    return;
                }
                return;
            }
            if (verticalDistance < f4) {
                iArr[0] = previousStartBoundary;
                iArr[1] = nextEndBoundary;
                if (rectF != null) {
                    rectF.set(rectF2);
                }
                f4 = verticalDistance;
            }
            if (f < rectF.top) {
                break;
            }
            previousStartBoundary = this.mLineSegmentFinder.nextStartBoundary(previousStartBoundary);
            nextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(nextEndBoundary);
        }
        if (f < f2 || f > f3) {
            iArr[0] = -1;
            iArr[1] = -1;
            if (rectF != null) {
                rectF.setEmpty();
            }
        }
    }

    public int[] getRangeForRect(RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        int previousStartBoundary;
        int nextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(this.mStart);
        if (nextEndBoundary == -1) {
            return null;
        }
        int i = nextEndBoundary;
        int previousStartBoundary2 = this.mLineSegmentFinder.previousStartBoundary(nextEndBoundary);
        int i2 = -1;
        while (previousStartBoundary2 != -1 && i2 == -1) {
            i2 = getStartForRectWithinLine(previousStartBoundary2, i, rectF, segmentFinder, textInclusionStrategy);
            previousStartBoundary2 = this.mLineSegmentFinder.nextStartBoundary(previousStartBoundary2);
            i = this.mLineSegmentFinder.nextEndBoundary(i);
        }
        if (i2 == -1 || (previousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(this.mEnd)) == -1) {
            return null;
        }
        int nextEndBoundary2 = this.mLineSegmentFinder.nextEndBoundary(previousStartBoundary);
        int i3 = -1;
        while (nextEndBoundary2 > i2 && i3 == -1) {
            i3 = getEndForRectWithinLine(previousStartBoundary, nextEndBoundary2, rectF, segmentFinder, textInclusionStrategy);
            previousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(previousStartBoundary);
            nextEndBoundary2 = this.mLineSegmentFinder.previousEndBoundary(nextEndBoundary2);
        }
        return new int[]{segmentFinder.previousStartBoundary(i2 + 1), segmentFinder.nextEndBoundary(i3 - 1)};
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
        int nextEndBoundary;
        if (i >= i2 || (nextEndBoundary = segmentFinder.nextEndBoundary(i)) == -1) {
            return -1;
        }
        int previousStartBoundary = segmentFinder.previousStartBoundary(nextEndBoundary);
        RectF rectF2 = new RectF();
        while (previousStartBoundary != -1 && previousStartBoundary < i2) {
            int max = Math.max(i, previousStartBoundary);
            getBoundsForRange(max, Math.min(i2, nextEndBoundary), rectF2);
            if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                return max;
            }
            previousStartBoundary = segmentFinder.nextStartBoundary(previousStartBoundary);
            nextEndBoundary = segmentFinder.nextEndBoundary(nextEndBoundary);
        }
        return -1;
    }

    private int getEndForRectWithinLine(int i, int i2, RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        TextBoundsInfo textBoundsInfo;
        int i3;
        if (i >= i2) {
            return -1;
        }
        int max = Math.max(i, this.mStart);
        int min = Math.min(i2, this.mEnd);
        int i4 = min - 1;
        int i5 = -1;
        while (i4 >= max) {
            int characterBidiLevel = this.getCharacterBidiLevel(i4);
            if (characterBidiLevel != i5) {
                i3 = i4 + 1;
                TextBoundsInfo textBoundsInfo2 = this;
                int endForRectWithinRun = textBoundsInfo2.getEndForRectWithinRun(i3, min, rectF, segmentFinder, textInclusionStrategy);
                textBoundsInfo = textBoundsInfo2;
                if (endForRectWithinRun != -1) {
                    return endForRectWithinRun;
                }
                i5 = characterBidiLevel;
            } else {
                int i6 = min;
                textBoundsInfo = this;
                i3 = i6;
            }
            i4--;
            this = textBoundsInfo;
            min = i3;
        }
        return this.getEndForRectWithinRun(max, min, rectF, segmentFinder, textInclusionStrategy);
    }

    private int getEndForRectWithinRun(int i, int i2, RectF rectF, SegmentFinder segmentFinder, Layout.TextInclusionStrategy textInclusionStrategy) {
        int previousStartBoundary;
        if (i >= i2 || (previousStartBoundary = segmentFinder.previousStartBoundary(i2)) == -1) {
            return -1;
        }
        int nextEndBoundary = segmentFinder.nextEndBoundary(previousStartBoundary);
        RectF rectF2 = new RectF();
        while (nextEndBoundary != -1 && nextEndBoundary > i) {
            int max = Math.max(i, previousStartBoundary);
            int min = Math.min(i2, nextEndBoundary);
            getBoundsForRange(max, min, rectF2);
            if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                return min;
            }
            previousStartBoundary = segmentFinder.previousStartBoundary(previousStartBoundary);
            nextEndBoundary = segmentFinder.previousEndBoundary(nextEndBoundary);
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
        int[] copyOf = Arrays.copyOf(this.mInternalCharacterFlags, (this.mEnd - this.mStart) + 1);
        encodeSegmentFinder(copyOf, 134217728, 67108864, this.mStart, this.mEnd, this.mGraphemeSegmentFinder);
        encodeSegmentFinder(copyOf, 536870912, 268435456, this.mStart, this.mEnd, this.mWordSegmentFinder);
        encodeSegmentFinder(copyOf, Integer.MIN_VALUE, 1073741824, this.mStart, this.mEnd, this.mLineSegmentFinder);
        parcel.writeIntArray(copyOf);
    }

    private TextBoundsInfo(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mStart = readInt;
        int readInt2 = parcel.readInt();
        this.mEnd = readInt2;
        this.mMatrixValues = (float[]) Objects.requireNonNull(parcel.createFloatArray());
        this.mCharacterBounds = (float[]) Objects.requireNonNull(parcel.createFloatArray());
        int[] iArr = (int[]) Objects.requireNonNull(parcel.createIntArray());
        this.mGraphemeSegmentFinder = decodeSegmentFinder(iArr, 134217728, 67108864, readInt, readInt2);
        this.mWordSegmentFinder = decodeSegmentFinder(iArr, 536870912, 268435456, readInt, readInt2);
        this.mLineSegmentFinder = decodeSegmentFinder(iArr, Integer.MIN_VALUE, 1073741824, readInt, readInt2);
        int i = readInt2 - readInt;
        this.mInternalCharacterFlags = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mInternalCharacterFlags[i2] = iArr[i2] & 66584591;
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
        int nextEndBoundary = segmentFinder.nextEndBoundary(i3);
        if (nextEndBoundary == -1) {
            return;
        }
        int previousStartBoundary = segmentFinder.previousStartBoundary(nextEndBoundary);
        while (nextEndBoundary != -1 && nextEndBoundary <= i4) {
            if (previousStartBoundary >= i3) {
                int i5 = previousStartBoundary - i3;
                iArr[i5] = iArr[i5] | i;
                int i6 = nextEndBoundary - i3;
                iArr[i6] = iArr[i6] | i2;
            }
            previousStartBoundary = segmentFinder.nextStartBoundary(previousStartBoundary);
            nextEndBoundary = segmentFinder.nextEndBoundary(nextEndBoundary);
        }
    }

    private static SegmentFinder decodeSegmentFinder(int[] iArr, int i, int i2, int i3, int i4) {
        if ((i4 - i3) + 1 != iArr.length) {
            throw new IllegalStateException("The given flags array must have the same length as the given range. flags length: " + iArr.length + " range: [" + i3 + ", " + i4 + NavigationBarInflaterView.SIZE_MOD_END);
        }
        int[] newUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(10);
        int i5 = 0;
        for (int i6 = 0; i6 < iArr.length; i6++) {
            if ((iArr[i6] & i) == i) {
                newUnpaddedIntArray = GrowingArrayUtils.append(newUnpaddedIntArray, i5, i3 + i6);
                i5++;
            }
            if ((iArr[i6] & i2) == i2) {
                newUnpaddedIntArray = GrowingArrayUtils.append(newUnpaddedIntArray, i5, i3 + i6);
                i5++;
            }
        }
        return new SegmentFinder.PrescribedSegmentFinder(Arrays.copyOf(newUnpaddedIntArray, i5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0037, code lost:
    
        r3 = r10.nextStartBoundary(r3);
        r0 = r10.nextEndBoundary(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isLineDirectionFlagConsistent(int[] r9, android.text.SegmentFinder r10, int r11, int r12) {
        /*
            int r0 = r10.nextEndBoundary(r11)
            r1 = 1
            r2 = -1
            if (r0 != r2) goto L9
            return r1
        L9:
            int r3 = r10.previousStartBoundary(r0)
        Ld:
            if (r3 == r2) goto L40
            if (r3 >= r12) goto L40
            int r4 = java.lang.Math.max(r3, r11)
            int r5 = java.lang.Math.min(r0, r12)
            int r6 = r4 - r11
            r6 = r9[r6]
            r6 = r6 & 8
            r7 = 0
            if (r6 == 0) goto L24
            r6 = r1
            goto L25
        L24:
            r6 = r7
        L25:
            int r4 = r4 + 1
            if (r4 >= r5) goto L37
            int r8 = r4 - r11
            r8 = r9[r8]
            r8 = r8 & 8
            if (r8 == 0) goto L33
            r8 = r1
            goto L34
        L33:
            r8 = r7
        L34:
            if (r8 == r6) goto L25
            return r7
        L37:
            int r3 = r10.nextStartBoundary(r3)
            int r0 = r10.nextEndBoundary(r0)
            goto Ld
        L40:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.TextBoundsInfo.isLineDirectionFlagConsistent(int[], android.text.SegmentFinder, int, int):boolean");
    }
}
