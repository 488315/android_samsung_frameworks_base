package android.hardware.display;

/* loaded from: classes2.dex */
public final class DisplayedContentSamplingAttributes {
    private int mComponentMask;
    private int mDataspace;
    private int mPixelFormat;

    public DisplayedContentSamplingAttributes(int i, int i2, int i3) {
        this.mPixelFormat = i;
        this.mDataspace = i2;
        this.mComponentMask = i3;
    }

    public int getPixelFormat() {
        return this.mPixelFormat;
    }

    public int getDataspace() {
        return this.mDataspace;
    }

    public int getComponentMask() {
        return this.mComponentMask;
    }
}
