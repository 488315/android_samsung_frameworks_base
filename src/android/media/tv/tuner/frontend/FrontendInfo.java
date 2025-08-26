package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import android.util.Range;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public class FrontendInfo {
    private final long mAcquireRange;
    private final int mExclusiveGroupId;
    private final Range<Long> mFrequencyRange;
    private final FrontendCapabilities mFrontendCap;
    private final int mId;
    private final int[] mStatusCaps;
    private final Range<Integer> mSymbolRateRange;
    private final int mType;

    private FrontendInfo(int i, int i2, long j, long j2, int i3, int i4, long j3, int i5, int[] iArr, FrontendCapabilities frontendCapabilities) {
        this.mId = i;
        this.mType = i2;
        this.mFrequencyRange = new Range<>(Long.valueOf(j), Long.valueOf(j2 < 0 ? 2147483647L : j2));
        this.mSymbolRateRange = new Range<>(Integer.valueOf(i3), Integer.valueOf(i4));
        this.mAcquireRange = j3;
        this.mExclusiveGroupId = i5;
        this.mStatusCaps = iArr;
        this.mFrontendCap = frontendCapabilities;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    @Deprecated
    public Range<Integer> getFrequencyRange() {
        return new Range<>(Integer.valueOf((int) ((Long) this.mFrequencyRange.getLower()).longValue()), Integer.valueOf((int) ((Long) this.mFrequencyRange.getUpper()).longValue()));
    }

    public Range<Long> getFrequencyRangeLong() {
        return this.mFrequencyRange;
    }

    public Range<Integer> getSymbolRateRange() {
        return this.mSymbolRateRange;
    }

    @Deprecated
    public int getAcquireRange() {
        return (int) getAcquireRangeLong();
    }

    public long getAcquireRangeLong() {
        return this.mAcquireRange;
    }

    public int getExclusiveGroupId() {
        return this.mExclusiveGroupId;
    }

    public int[] getStatusCapabilities() {
        return this.mStatusCaps;
    }

    public FrontendCapabilities getFrontendCapabilities() {
        return this.mFrontendCap;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof FrontendInfo)) {
            FrontendInfo frontendInfo = (FrontendInfo) obj;
            if (this.mId == frontendInfo.getId() && this.mType == frontendInfo.getType() && Objects.equals(this.mFrequencyRange, frontendInfo.getFrequencyRangeLong()) && Objects.equals(this.mSymbolRateRange, frontendInfo.getSymbolRateRange()) && this.mAcquireRange == frontendInfo.getAcquireRangeLong() && this.mExclusiveGroupId == frontendInfo.getExclusiveGroupId() && Arrays.equals(this.mStatusCaps, frontendInfo.getStatusCapabilities())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mId;
    }
}
