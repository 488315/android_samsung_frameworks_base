package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class DownloadEvent extends FilterEvent {
    private final int mDataLength;
    private final int mDownloadId;
    private final int mItemFragmentIndex;
    private final int mItemId;
    private final int mLastItemFragmentIndex;
    private final int mMpuSequenceNumber;

    private DownloadEvent(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mItemId = i;
        this.mDownloadId = i2;
        this.mMpuSequenceNumber = i3;
        this.mItemFragmentIndex = i4;
        this.mLastItemFragmentIndex = i5;
        this.mDataLength = i6;
    }

    public int getItemId() {
        return this.mItemId;
    }

    public int getDownloadId() {
        return this.mDownloadId;
    }

    public int getMpuSequenceNumber() {
        return this.mMpuSequenceNumber;
    }

    public int getItemFragmentIndex() {
        return this.mItemFragmentIndex;
    }

    public int getLastItemFragmentIndex() {
        return this.mLastItemFragmentIndex;
    }

    public int getDataLength() {
        return this.mDataLength;
    }
}
