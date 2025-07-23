package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class ApfStats implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<ApfStats> CREATOR = new Parcelable.Creator<ApfStats>() { // from class: android.net.metrics.ApfStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApfStats createFromParcel(Parcel parcel) {
            return new ApfStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApfStats[] newArray(int i) {
            return new ApfStats[i];
        }
    };
    public final int droppedRas;
    public final long durationMs;
    public final int matchingRas;
    public final int maxProgramSize;
    public final int parseErrors;
    public final int programUpdates;
    public final int programUpdatesAll;
    public final int programUpdatesAllowingMulticast;
    public final int receivedRas;
    public final int zeroLifetimeRas;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ApfStats(Parcel parcel) {
        this.durationMs = parcel.readLong();
        this.receivedRas = parcel.readInt();
        this.matchingRas = parcel.readInt();
        this.droppedRas = parcel.readInt();
        this.zeroLifetimeRas = parcel.readInt();
        this.parseErrors = parcel.readInt();
        this.programUpdates = parcel.readInt();
        this.programUpdatesAll = parcel.readInt();
        this.programUpdatesAllowingMulticast = parcel.readInt();
        this.maxProgramSize = parcel.readInt();
    }

    private ApfStats(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.durationMs = j;
        this.receivedRas = i;
        this.matchingRas = i2;
        this.droppedRas = i3;
        this.zeroLifetimeRas = i4;
        this.parseErrors = i5;
        this.programUpdates = i6;
        this.programUpdatesAll = i7;
        this.programUpdatesAllowingMulticast = i8;
        this.maxProgramSize = i9;
    }

    @SystemApi
    public static final class Builder {
        private int mDroppedRas;
        private long mDurationMs;
        private int mMatchingRas;
        private int mMaxProgramSize;
        private int mParseErrors;
        private int mProgramUpdates;
        private int mProgramUpdatesAll;
        private int mProgramUpdatesAllowingMulticast;
        private int mReceivedRas;
        private int mZeroLifetimeRas;

        public Builder setDurationMs(long j) {
            this.mDurationMs = j;
            return this;
        }

        public Builder setReceivedRas(int i) {
            this.mReceivedRas = i;
            return this;
        }

        public Builder setMatchingRas(int i) {
            this.mMatchingRas = i;
            return this;
        }

        public Builder setDroppedRas(int i) {
            this.mDroppedRas = i;
            return this;
        }

        public Builder setZeroLifetimeRas(int i) {
            this.mZeroLifetimeRas = i;
            return this;
        }

        public Builder setParseErrors(int i) {
            this.mParseErrors = i;
            return this;
        }

        public Builder setProgramUpdates(int i) {
            this.mProgramUpdates = i;
            return this;
        }

        public Builder setProgramUpdatesAll(int i) {
            this.mProgramUpdatesAll = i;
            return this;
        }

        public Builder setProgramUpdatesAllowingMulticast(int i) {
            this.mProgramUpdatesAllowingMulticast = i;
            return this;
        }

        public Builder setMaxProgramSize(int i) {
            this.mMaxProgramSize = i;
            return this;
        }

        public ApfStats build() {
            return new ApfStats(this.mDurationMs, this.mReceivedRas, this.mMatchingRas, this.mDroppedRas, this.mZeroLifetimeRas, this.mParseErrors, this.mProgramUpdates, this.mProgramUpdatesAll, this.mProgramUpdatesAllowingMulticast, this.mMaxProgramSize);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.durationMs);
        parcel.writeInt(this.receivedRas);
        parcel.writeInt(this.matchingRas);
        parcel.writeInt(this.droppedRas);
        parcel.writeInt(this.zeroLifetimeRas);
        parcel.writeInt(this.parseErrors);
        parcel.writeInt(this.programUpdates);
        parcel.writeInt(this.programUpdatesAll);
        parcel.writeInt(this.programUpdatesAllowingMulticast);
        parcel.writeInt(this.maxProgramSize);
    }

    public String toString() {
        return "ApfStats(" + String.format("%dms ", Long.valueOf(this.durationMs)) + String.format("%dB RA: {", Integer.valueOf(this.maxProgramSize)) + String.format("%d received, ", Integer.valueOf(this.receivedRas)) + String.format("%d matching, ", Integer.valueOf(this.matchingRas)) + String.format("%d dropped, ", Integer.valueOf(this.droppedRas)) + String.format("%d zero lifetime, ", Integer.valueOf(this.zeroLifetimeRas)) + String.format("%d parse errors}, ", Integer.valueOf(this.parseErrors)) + String.format("updates: {all: %d, RAs: %d, allow multicast: %d})", Integer.valueOf(this.programUpdatesAll), Integer.valueOf(this.programUpdates), Integer.valueOf(this.programUpdatesAllowingMulticast));
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(ApfStats.class)) {
            ApfStats apfStats = (ApfStats) obj;
            if (this.durationMs == apfStats.durationMs && this.receivedRas == apfStats.receivedRas && this.matchingRas == apfStats.matchingRas && this.droppedRas == apfStats.droppedRas && this.zeroLifetimeRas == apfStats.zeroLifetimeRas && this.parseErrors == apfStats.parseErrors && this.programUpdates == apfStats.programUpdates && this.programUpdatesAll == apfStats.programUpdatesAll && this.programUpdatesAllowingMulticast == apfStats.programUpdatesAllowingMulticast && this.maxProgramSize == apfStats.maxProgramSize) {
                return true;
            }
        }
        return false;
    }
}
