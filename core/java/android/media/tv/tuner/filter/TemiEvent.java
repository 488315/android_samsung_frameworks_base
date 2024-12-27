package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
public class TemiEvent extends FilterEvent {
    private final byte[] mDescrData;
    private final byte mDescrTag;
    private final long mPts;

    private TemiEvent(long pts, byte descrTag, byte[] descrData) {
        this.mPts = pts;
        this.mDescrTag = descrTag;
        this.mDescrData = descrData;
    }

    public long getPts() {
        return this.mPts;
    }

    public byte getDescriptorTag() {
        return this.mDescrTag;
    }

    public byte[] getDescriptorData() {
        return this.mDescrData;
    }
}
