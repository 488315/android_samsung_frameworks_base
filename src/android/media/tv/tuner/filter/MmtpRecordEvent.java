package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class MmtpRecordEvent extends FilterEvent {
    private final long mDataLength;
    private final int mFirstMbInSlice;
    private final int mMpuSequenceNumber;
    private final long mPts;
    private final int mScHevcIndexMask;
    private final int mTsIndexMask;

    private MmtpRecordEvent(int i, long j, int i2, long j2, int i3, int i4) {
        this.mScHevcIndexMask = i;
        this.mDataLength = j;
        this.mMpuSequenceNumber = i2;
        this.mPts = j2;
        this.mFirstMbInSlice = i3;
        this.mTsIndexMask = i4;
    }

    public int getScHevcIndexMask() {
        return this.mScHevcIndexMask;
    }

    public long getDataLength() {
        return this.mDataLength;
    }

    public int getMpuSequenceNumber() {
        return this.mMpuSequenceNumber;
    }

    public long getPts() {
        return this.mPts;
    }

    public int getFirstMacroblockInSlice() {
        return this.mFirstMbInSlice;
    }

    public int getTsIndexMask() {
        return this.mTsIndexMask;
    }
}
