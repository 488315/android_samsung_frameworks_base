package android.media.tv.tuner;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public class DemuxCapabilities {
    private final int mAudioFilterCount;
    private final int mDemuxCount;
    private final int mFilterCaps;
    private final int[] mFilterCapsList;
    private final int[] mLinkCaps;
    private final int mPcrFilterCount;
    private final int mPesFilterCount;
    private final int mPlaybackCount;
    private final int mRecordCount;
    private final int mSectionFilterCount;
    private final long mSectionFilterLength;
    private final boolean mSupportTimeFilter;
    private final int mTsFilterCount;
    private final int mVideoFilterCount;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterCapabilities {
    }

    private DemuxCapabilities(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, long j, int i10, int[] iArr, int[] iArr2, boolean z) {
        this.mDemuxCount = i;
        this.mRecordCount = i2;
        this.mPlaybackCount = i3;
        this.mTsFilterCount = i4;
        this.mSectionFilterCount = i5;
        this.mAudioFilterCount = i6;
        this.mVideoFilterCount = i7;
        this.mPesFilterCount = i8;
        this.mPcrFilterCount = i9;
        this.mSectionFilterLength = j;
        this.mFilterCaps = i10;
        this.mFilterCapsList = iArr;
        this.mLinkCaps = iArr2;
        this.mSupportTimeFilter = z;
    }

    public int getDemuxCount() {
        return this.mDemuxCount;
    }

    public int getRecordCount() {
        return this.mRecordCount;
    }

    public int getPlaybackCount() {
        return this.mPlaybackCount;
    }

    public int getTsFilterCount() {
        return this.mTsFilterCount;
    }

    public int getSectionFilterCount() {
        return this.mSectionFilterCount;
    }

    public int getAudioFilterCount() {
        return this.mAudioFilterCount;
    }

    public int getVideoFilterCount() {
        return this.mVideoFilterCount;
    }

    public int getPesFilterCount() {
        return this.mPesFilterCount;
    }

    public int getPcrFilterCount() {
        return this.mPcrFilterCount;
    }

    public long getSectionFilterLength() {
        return this.mSectionFilterLength;
    }

    public int getFilterCapabilities() {
        return this.mFilterCaps;
    }

    public int[] getFilterTypeCapabilityList() {
        return this.mFilterCapsList;
    }

    public int[] getLinkCapabilities() {
        return this.mLinkCaps;
    }

    public boolean isTimeFilterSupported() {
        return this.mSupportTimeFilter;
    }
}
