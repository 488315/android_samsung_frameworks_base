package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.AudioPresentation;
import android.media.MediaCodec;
import java.util.Collections;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public class MediaEvent extends FilterEvent {
    private final List<AudioPresentation> mAudioPresentations;
    private final int mDataGroupId;
    private final long mDataId;
    private final long mDataLength;
    private final long mDts;
    private final AudioDescriptor mExtraMetaData;
    private final int mIndexInDataGroup;
    private final boolean mIsDtsPresent;
    private final boolean mIsPrivateData;
    private final boolean mIsPtsPresent;
    private final boolean mIsSecureMemory;
    private MediaCodec.LinearBlock mLinearBlock;
    private final int mMpuSequenceNumber;
    private long mNativeContext;
    private final int mNumDataPieces;
    private final long mOffset;
    private final long mPts;
    private final int mScIndexMask;
    private final int mStreamId;
    private boolean mReleased = false;
    private final Object mLock = new Object();

    private native void nativeFinalize();

    private native Long nativeGetAudioHandle();

    private native MediaCodec.LinearBlock nativeGetLinearBlock();

    private MediaEvent(int i, boolean z, long j, boolean z2, long j2, long j3, long j4, MediaCodec.LinearBlock linearBlock, boolean z3, long j5, int i2, boolean z4, int i3, AudioDescriptor audioDescriptor, List<AudioPresentation> list, int i4, int i5, int i6) {
        this.mStreamId = i;
        this.mIsPtsPresent = z;
        this.mPts = j;
        this.mIsDtsPresent = z2;
        this.mDts = j2;
        this.mDataLength = j3;
        this.mOffset = j4;
        this.mLinearBlock = linearBlock;
        this.mIsSecureMemory = z3;
        this.mDataId = j5;
        this.mMpuSequenceNumber = i2;
        this.mIsPrivateData = z4;
        this.mScIndexMask = i3;
        this.mExtraMetaData = audioDescriptor;
        this.mAudioPresentations = list;
        this.mNumDataPieces = i4;
        this.mIndexInDataGroup = i5;
        this.mDataGroupId = i6;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public boolean isPtsPresent() {
        return this.mIsPtsPresent;
    }

    public long getPts() {
        return this.mPts;
    }

    public boolean isDtsPresent() {
        return this.mIsDtsPresent;
    }

    public long getDts() {
        return this.mDts;
    }

    public long getDataLength() {
        return this.mDataLength;
    }

    public long getOffset() {
        return this.mOffset;
    }

    public MediaCodec.LinearBlock getLinearBlock() {
        MediaCodec.LinearBlock linearBlock;
        synchronized (this.mLock) {
            if (this.mLinearBlock == null) {
                this.mLinearBlock = nativeGetLinearBlock();
            }
            linearBlock = this.mLinearBlock;
        }
        return linearBlock;
    }

    public boolean isSecureMemory() {
        return this.mIsSecureMemory;
    }

    public long getAvDataId() {
        return this.mDataId;
    }

    public long getAudioHandle() {
        nativeGetAudioHandle();
        return this.mDataId;
    }

    public int getMpuSequenceNumber() {
        return this.mMpuSequenceNumber;
    }

    public boolean isPrivateData() {
        return this.mIsPrivateData;
    }

    public int getScIndexMask() {
        return this.mScIndexMask;
    }

    public AudioDescriptor getExtraMetaData() {
        return this.mExtraMetaData;
    }

    public List<AudioPresentation> getAudioPresentations() {
        List<AudioPresentation> list = this.mAudioPresentations;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public int getNumDataPieces() {
        return this.mNumDataPieces;
    }

    public int getIndexInDataGroup() {
        return this.mIndexInDataGroup;
    }

    public int getDataGroupId() {
        return this.mDataGroupId;
    }

    protected void finalize() {
        release();
    }

    public void release() {
        synchronized (this.mLock) {
            if (this.mReleased) {
                return;
            }
            nativeFinalize();
            this.mNativeContext = 0L;
            this.mReleased = true;
        }
    }
}
