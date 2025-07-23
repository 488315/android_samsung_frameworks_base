package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class TsRecordEvent extends FilterEvent {
    private final long mDataLength;
    private final int mFirstMbInSlice;
    private final int mPid;
    private final long mPts;
    private final int mScIndexMask;
    private final int mTsIndexMask;

    private TsRecordEvent(int i, int i2, int i3, long j, long j2, int i4) {
        this.mPid = i;
        this.mTsIndexMask = i2;
        this.mScIndexMask = i3;
        this.mDataLength = j;
        this.mPts = j2;
        this.mFirstMbInSlice = i4;
    }

    public int getPacketId() {
        return this.mPid;
    }

    public int getTsIndexMask() {
        return this.mTsIndexMask;
    }

    public int getScIndexMask() {
        return this.mScIndexMask;
    }

    public long getDataLength() {
        return this.mDataLength;
    }

    public long getPts() {
        return this.mPts;
    }

    public int getFirstMacroblockInSlice() {
        return this.mFirstMbInSlice;
    }
}
