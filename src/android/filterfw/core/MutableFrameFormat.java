package android.filterfw.core;

import java.util.Arrays;

/* loaded from: classes.dex */
public class MutableFrameFormat extends FrameFormat {
    public MutableFrameFormat() {
    }

    public MutableFrameFormat(int i, int i2) {
        super(i, i2);
    }

    public void setBaseType(int i) {
        this.mBaseType = i;
        this.mBytesPerSample = bytesPerSampleOf(i);
    }

    public void setTarget(int i) {
        this.mTarget = i;
    }

    public void setBytesPerSample(int i) {
        this.mBytesPerSample = i;
        this.mSize = -1;
    }

    public void setDimensions(int[] iArr) {
        this.mDimensions = iArr == null ? null : Arrays.copyOf(iArr, iArr.length);
        this.mSize = -1;
    }

    public void setDimensions(int i) {
        this.mDimensions = new int[]{i};
        this.mSize = -1;
    }

    public void setDimensions(int i, int i2) {
        this.mDimensions = new int[]{i, i2};
        this.mSize = -1;
    }

    public void setDimensions(int i, int i2, int i3) {
        this.mDimensions = new int[]{i, i2, i3};
        this.mSize = -1;
    }

    public void setDimensionCount(int i) {
        this.mDimensions = new int[i];
    }

    public void setObjectClass(Class cls) {
        this.mObjectClass = cls;
    }

    public void setMetaValue(String str, Object obj) {
        if (this.mMetaData == null) {
            this.mMetaData = new KeyValueMap();
        }
        this.mMetaData.put(str, obj);
    }
}
