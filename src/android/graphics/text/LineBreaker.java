package android.graphics.text;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class LineBreaker {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int JUSTIFICATION_MODE_INTER_CHARACTER = 2;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    private final int mBreakStrategy;
    private final int mHyphenationFrequency;
    private final int[] mIndents;
    private final int mJustificationMode;
    private final long mNativePtr;
    private final boolean mUseBoundsForWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BreakStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HyphenationFrequency {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface JustificationMode {
    }

    private static native long nComputeLineBreaks(long j, char[] cArr, long j2, int i, float f, int i2, float f2, float[] fArr, float f3, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native float nGetLineAscent(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native int nGetLineBreakOffset(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native int nGetLineCount(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native float nGetLineDescent(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native int nGetLineFlag(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native float nGetLineWidth(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nGetReleaseFunc();

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nGetReleaseResultFunc();

    @FastNative
    private static native long nInit(int i, int i2, boolean z, int[] iArr, boolean z2);

    public static final class Builder {
        private int mBreakStrategy = 0;
        private int mHyphenationFrequency = 0;
        private int mJustificationMode = 0;
        private int[] mIndents = null;
        private boolean mUseBoundsForWidth = false;

        public Builder setBreakStrategy(int i) {
            this.mBreakStrategy = i;
            return this;
        }

        public Builder setHyphenationFrequency(int i) {
            this.mHyphenationFrequency = i;
            return this;
        }

        public Builder setJustificationMode(int i) {
            this.mJustificationMode = i;
            return this;
        }

        public Builder setIndents(int[] iArr) {
            this.mIndents = iArr;
            return this;
        }

        public Builder setUseBoundsForWidth(boolean z) {
            this.mUseBoundsForWidth = z;
            return this;
        }

        public LineBreaker build() {
            return new LineBreaker(this.mBreakStrategy, this.mHyphenationFrequency, this.mJustificationMode, this.mIndents, this.mUseBoundsForWidth);
        }
    }

    public static class ParagraphConstraints {
        private float mWidth = 0.0f;
        private float mFirstWidth = 0.0f;
        private int mFirstWidthLineCount = 0;
        private float[] mVariableTabStops = null;
        private float mDefaultTabStop = 0.0f;

        public void setWidth(float f) {
            this.mWidth = f;
        }

        public void setIndent(float f, int i) {
            this.mFirstWidth = f;
            this.mFirstWidthLineCount = i;
        }

        public void setTabStops(float[] fArr, float f) {
            this.mVariableTabStops = fArr;
            this.mDefaultTabStop = f;
        }

        public float getWidth() {
            return this.mWidth;
        }

        public float getFirstWidth() {
            return this.mFirstWidth;
        }

        public int getFirstWidthLineCount() {
            return this.mFirstWidthLineCount;
        }

        public float[] getTabStops() {
            return this.mVariableTabStops;
        }

        public float getDefaultTabStop() {
            return this.mDefaultTabStop;
        }
    }

    public static class Result {
        private static final int END_HYPHEN_MASK = 7;
        private static final int HYPHEN_MASK = 255;
        private static final int START_HYPHEN_BITS_SHIFT = 3;
        private static final int START_HYPHEN_MASK = 24;
        private static final int TAB_MASK = 536870912;
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Result.class.getClassLoader(), LineBreaker.nGetReleaseResultFunc());
        private final long mPtr;

        private Result(long j) {
            this.mPtr = j;
            sRegistry.registerNativeAllocation(this, j);
        }

        public int getLineCount() {
            return LineBreaker.nGetLineCount(this.mPtr);
        }

        public int getLineBreakOffset(int i) {
            return LineBreaker.nGetLineBreakOffset(this.mPtr, i);
        }

        public float getLineWidth(int i) {
            return LineBreaker.nGetLineWidth(this.mPtr, i);
        }

        public float getLineAscent(int i) {
            return LineBreaker.nGetLineAscent(this.mPtr, i);
        }

        public float getLineDescent(int i) {
            return LineBreaker.nGetLineDescent(this.mPtr, i);
        }

        public boolean hasLineTab(int i) {
            return (LineBreaker.nGetLineFlag(this.mPtr, i) & 536870912) != 0;
        }

        public int getStartLineHyphenEdit(int i) {
            return (LineBreaker.nGetLineFlag(this.mPtr, i) & 24) >> 3;
        }

        public int getEndLineHyphenEdit(int i) {
            return LineBreaker.nGetLineFlag(this.mPtr, i) & 7;
        }
    }

    private static class NoImagePreloadHolder {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(LineBreaker.class.getClassLoader(), LineBreaker.nGetReleaseFunc());

        private NoImagePreloadHolder() {
        }
    }

    private LineBreaker(int i, int i2, int i3, int[] iArr, boolean z) {
        long jNInit = nInit(i, i2, i3 == 1, iArr, z);
        this.mNativePtr = jNInit;
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, jNInit);
        this.mBreakStrategy = i;
        this.mHyphenationFrequency = i2;
        this.mJustificationMode = i3;
        this.mIndents = iArr;
        this.mUseBoundsForWidth = z;
    }

    public int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    public int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public int getJustificationMode() {
        return this.mJustificationMode;
    }

    public int[] getIndents() {
        return this.mIndents;
    }

    public boolean getUseBoundsForWidth() {
        return this.mUseBoundsForWidth;
    }

    public Result computeLineBreaks(MeasuredText measuredText, ParagraphConstraints paragraphConstraints, int i) {
        return new Result(nComputeLineBreaks(this.mNativePtr, measuredText.getChars(), measuredText.getNativePtr(), measuredText.getChars().length, paragraphConstraints.mFirstWidth, paragraphConstraints.mFirstWidthLineCount, paragraphConstraints.mWidth, paragraphConstraints.mVariableTabStops, paragraphConstraints.mDefaultTabStop, i));
    }
}
