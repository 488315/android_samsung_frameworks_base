package android.graphics.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.NeverInline;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class MeasuredText {
    private static final String TAG = "MeasuredText";
    private final int mBottom;
    private final char[] mChars;
    private final boolean mComputeBounds;
    private final boolean mComputeHyphenation;
    private final boolean mComputeLayout;
    private final long mNativePtr;
    private final int mTop;

    private static native void nGetBounds(long j, char[] cArr, int i, int i2, Rect rect);

    @CriticalNative
    private static native float nGetCharWidthAt(long j, int i);

    private static native long nGetExtent(long j, char[] cArr, int i, int i2);

    @CriticalNative
    private static native int nGetMemoryUsage(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nGetReleaseFunc();

    @CriticalNative
    private static native float nGetWidth(long j, int i, int i2);

    private MeasuredText(long j, char[] cArr, boolean z, boolean z2, boolean z3, int i, int i2) {
        this.mNativePtr = j;
        this.mChars = cArr;
        this.mComputeHyphenation = z;
        this.mComputeLayout = z2;
        this.mComputeBounds = z3;
        this.mTop = i;
        this.mBottom = i2;
    }

    public char[] getChars() {
        return this.mChars;
    }

    private void rangeCheck(int i, int i2) {
        if (i < 0 || i > i2 || i2 > this.mChars.length) {
            throwRangeError(i, i2);
        }
    }

    @NeverInline
    private void throwRangeError(int i, int i2) {
        throw new IllegalArgumentException(String.format(Locale.US, "start(%d) end(%d) length(%d) out of bounds", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.mChars.length)));
    }

    private void offsetCheck(int i) {
        if (i < 0 || i >= this.mChars.length) {
            throwOffsetError(i);
        }
    }

    @NeverInline
    private void throwOffsetError(int i) {
        throw new IllegalArgumentException(String.format(Locale.US, "offset (%d) length(%d) out of bounds", Integer.valueOf(i), Integer.valueOf(this.mChars.length)));
    }

    public float getWidth(int i, int i2) {
        rangeCheck(i, i2);
        return nGetWidth(this.mNativePtr, i, i2);
    }

    public int getMemoryUsage() {
        return nGetMemoryUsage(this.mNativePtr);
    }

    public void getBounds(int i, int i2, Rect rect) {
        rangeCheck(i, i2);
        Preconditions.checkNotNull(rect);
        nGetBounds(this.mNativePtr, this.mChars, i, i2, rect);
    }

    public void getFontMetricsInt(int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        rangeCheck(i, i2);
        Objects.requireNonNull(fontMetricsInt);
        long nGetExtent = nGetExtent(this.mNativePtr, this.mChars, i, i2);
        fontMetricsInt.ascent = (int) (nGetExtent >> 32);
        fontMetricsInt.descent = (int) nGetExtent;
        fontMetricsInt.top = Math.min(fontMetricsInt.ascent, this.mTop);
        fontMetricsInt.bottom = Math.max(fontMetricsInt.descent, this.mBottom);
    }

    public float getCharWidthAt(int i) {
        offsetCheck(i);
        return nGetCharWidthAt(this.mNativePtr, i);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }

    public static final class Builder {
        public static final int HYPHENATION_MODE_FAST = 2;
        public static final int HYPHENATION_MODE_NONE = 0;
        public static final int HYPHENATION_MODE_NORMAL = 1;
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(MeasuredText.class.getClassLoader(), MeasuredText.nGetReleaseFunc());
        private int mBottom;
        private Paint.FontMetricsInt mCachedMetrics;
        private boolean mComputeBounds;
        private boolean mComputeHyphenation;
        private boolean mComputeLayout;
        private int mCurrentOffset;
        private boolean mFastHyphenation;
        private MeasuredText mHintMt;
        private long mNativePtr;
        private final char[] mText;
        private int mTop;

        @Retention(RetentionPolicy.SOURCE)
        public @interface HyphenationMode {
        }

        private static native void nAddReplacementRun(long j, long j2, int i, int i2, float f);

        private static native void nAddStyleRun(long j, long j2, int i, int i2, boolean z, int i3, int i4, boolean z2);

        private static native long nBuildMeasuredText(long j, long j2, char[] cArr, boolean z, boolean z2, boolean z3, boolean z4);

        private static native void nFreeBuilder(long j);

        private static native long nInitBuilder();

        public Builder(char[] cArr) {
            this.mComputeHyphenation = false;
            this.mComputeLayout = true;
            this.mComputeBounds = true;
            this.mFastHyphenation = false;
            this.mCurrentOffset = 0;
            this.mHintMt = null;
            this.mTop = 0;
            this.mBottom = 0;
            this.mCachedMetrics = new Paint.FontMetricsInt();
            Preconditions.checkNotNull(cArr);
            this.mText = cArr;
            this.mNativePtr = nInitBuilder();
        }

        public Builder(MeasuredText measuredText) {
            this.mComputeHyphenation = false;
            this.mComputeLayout = true;
            this.mComputeBounds = true;
            this.mFastHyphenation = false;
            this.mCurrentOffset = 0;
            this.mHintMt = null;
            this.mTop = 0;
            this.mBottom = 0;
            this.mCachedMetrics = new Paint.FontMetricsInt();
            Preconditions.checkNotNull(measuredText);
            this.mText = measuredText.mChars;
            this.mNativePtr = nInitBuilder();
            if (!measuredText.mComputeLayout) {
                throw new IllegalArgumentException("The input MeasuredText must not be created with setComputeLayout(false).");
            }
            this.mComputeHyphenation = measuredText.mComputeHyphenation;
            this.mComputeLayout = measuredText.mComputeLayout;
            this.mHintMt = measuredText;
        }

        public Builder appendStyleRun(Paint paint, int i, boolean z) {
            return appendStyleRun(paint, null, i, z);
        }

        public Builder appendStyleRun(Paint paint, LineBreakConfig lineBreakConfig, int i, boolean z) {
            Preconditions.checkNotNull(paint);
            Preconditions.checkArgument(i > 0, "length can not be negative");
            int i2 = this.mCurrentOffset + i;
            Preconditions.checkArgument(i2 <= this.mText.length, "Style exceeds the text length");
            nAddStyleRun(this.mNativePtr, paint.getNativeInstance(), LineBreakConfig.getResolvedLineBreakStyle(lineBreakConfig), LineBreakConfig.getResolvedLineBreakWordStyle(lineBreakConfig), LineBreakConfig.getResolvedHyphenation(lineBreakConfig) == 1, this.mCurrentOffset, i2, z);
            this.mCurrentOffset = i2;
            paint.getFontMetricsInt(this.mCachedMetrics);
            this.mTop = Math.min(this.mTop, this.mCachedMetrics.top);
            this.mBottom = Math.max(this.mBottom, this.mCachedMetrics.bottom);
            return this;
        }

        public Builder appendReplacementRun(Paint paint, int i, float f) {
            Preconditions.checkArgument(i > 0, "length can not be negative");
            int i2 = this.mCurrentOffset + i;
            Preconditions.checkArgument(i2 <= this.mText.length, "Replacement exceeds the text length");
            nAddReplacementRun(this.mNativePtr, paint.getNativeInstance(), this.mCurrentOffset, i2, f);
            this.mCurrentOffset = i2;
            return this;
        }

        @Deprecated
        public Builder setComputeHyphenation(boolean z) {
            setComputeHyphenation(z ? 1 : 0);
            return this;
        }

        public Builder setComputeHyphenation(int i) {
            if (i == 0) {
                this.mComputeHyphenation = false;
                this.mFastHyphenation = false;
                return this;
            }
            if (i == 1) {
                this.mComputeHyphenation = true;
                this.mFastHyphenation = false;
                return this;
            }
            if (i == 2) {
                this.mComputeHyphenation = true;
                this.mFastHyphenation = true;
                return this;
            }
            Log.e(MeasuredText.TAG, "Unknown hyphenation mode: " + i);
            this.mComputeHyphenation = false;
            this.mFastHyphenation = false;
            return this;
        }

        public Builder setComputeLayout(boolean z) {
            this.mComputeLayout = z;
            return this;
        }

        public Builder setComputeBounds(boolean z) {
            this.mComputeBounds = z;
            return this;
        }

        public MeasuredText build() {
            ensureNativePtrNoReuse();
            if (this.mCurrentOffset != this.mText.length) {
                throw new IllegalStateException("Style info has not been provided for all text.");
            }
            MeasuredText measuredText = this.mHintMt;
            if (measuredText != null && measuredText.mComputeHyphenation != this.mComputeHyphenation) {
                throw new IllegalArgumentException("The hyphenation configuration is different from given hint MeasuredText");
            }
            try {
                MeasuredText measuredText2 = this.mHintMt;
                long nBuildMeasuredText = nBuildMeasuredText(this.mNativePtr, measuredText2 == null ? 0L : measuredText2.getNativePtr(), this.mText, this.mComputeHyphenation, this.mComputeLayout, this.mComputeBounds, this.mFastHyphenation);
                MeasuredText measuredText3 = new MeasuredText(nBuildMeasuredText, this.mText, this.mComputeHyphenation, this.mComputeLayout, this.mComputeBounds, this.mTop, this.mBottom);
                sRegistry.registerNativeAllocation(measuredText3, nBuildMeasuredText);
                return measuredText3;
            } finally {
                nFreeBuilder(this.mNativePtr);
                this.mNativePtr = 0L;
            }
        }

        private void ensureNativePtrNoReuse() {
            if (this.mNativePtr == 0) {
                throw new IllegalStateException("Builder can not be reused.");
            }
        }
    }
}
