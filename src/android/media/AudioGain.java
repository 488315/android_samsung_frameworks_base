package android.media;

/* loaded from: classes2.dex */
public class AudioGain {
    public static final int MODE_CHANNELS = 2;
    public static final int MODE_JOINT = 1;
    public static final int MODE_RAMP = 4;
    private final int mChannelMask;
    private final int mDefaultValue;
    private final int mIndex;
    private final int mMaxValue;
    private final int mMinValue;
    private final int mMode;
    private final int mRampDurationMaxMs;
    private final int mRampDurationMinMs;
    private final int mStepValue;

    AudioGain(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.mIndex = i;
        this.mMode = i2;
        this.mChannelMask = i3;
        this.mMinValue = i4;
        this.mMaxValue = i5;
        this.mDefaultValue = i6;
        this.mStepValue = i7;
        this.mRampDurationMinMs = i8;
        this.mRampDurationMaxMs = i9;
    }

    public int mode() {
        return this.mMode;
    }

    public int channelMask() {
        return this.mChannelMask;
    }

    public int minValue() {
        return this.mMinValue;
    }

    public int maxValue() {
        return this.mMaxValue;
    }

    public int defaultValue() {
        return this.mDefaultValue;
    }

    public int stepValue() {
        return this.mStepValue;
    }

    public int rampDurationMinMs() {
        return this.mRampDurationMinMs;
    }

    public int rampDurationMaxMs() {
        return this.mRampDurationMaxMs;
    }

    public AudioGainConfig buildConfig(int i, int i2, int[] iArr, int i3) {
        return new AudioGainConfig(this.mIndex, this, i, i2, iArr, i3);
    }
}
