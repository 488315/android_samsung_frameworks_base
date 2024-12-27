package android.hardware.display;

/* loaded from: classes2.dex */
public final class DisplayedContentSample {
    private long mNumFrames;
    private long[] mSamplesComponent0;
    private long[] mSamplesComponent1;
    private long[] mSamplesComponent2;
    private long[] mSamplesComponent3;

    public enum ColorComponent {
        CHANNEL0,
        CHANNEL1,
        CHANNEL2,
        CHANNEL3
    }

    public DisplayedContentSample(
            long numFrames,
            long[] sampleComponent0,
            long[] sampleComponent1,
            long[] sampleComponent2,
            long[] sampleComponent3) {
        this.mNumFrames = numFrames;
        this.mSamplesComponent0 = sampleComponent0;
        this.mSamplesComponent1 = sampleComponent1;
        this.mSamplesComponent2 = sampleComponent2;
        this.mSamplesComponent3 = sampleComponent3;
    }

    public long[] getSampleComponent(ColorComponent component) {
        switch (component) {
            case CHANNEL0:
                return this.mSamplesComponent0;
            case CHANNEL1:
                return this.mSamplesComponent1;
            case CHANNEL2:
                return this.mSamplesComponent2;
            case CHANNEL3:
                return this.mSamplesComponent3;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }

    public long getNumFrames() {
        return this.mNumFrames;
    }
}
