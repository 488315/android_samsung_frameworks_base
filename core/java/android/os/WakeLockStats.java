package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class WakeLockStats implements Parcelable {
    public static final Parcelable.Creator<WakeLockStats> CREATOR =
            new Parcelable.Creator<WakeLockStats>() { // from class: android.os.WakeLockStats.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WakeLockStats createFromParcel(Parcel in) {
                    return new WakeLockStats(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WakeLockStats[] newArray(int size) {
                    return new WakeLockStats[size];
                }
            };
    private final List<WakeLock> mAggregatedWakeLocks;
    private final List<WakeLock> mWakeLocks;

    public static class WakeLockData {
        public static final WakeLockData EMPTY = new WakeLockData(0, 0, 0);
        public final long timeHeldMs;
        public final int timesAcquired;
        public final long totalTimeHeldMs;

        public WakeLockData(int timesAcquired, long totalTimeHeldMs, long timeHeldMs) {
            this.timesAcquired = timesAcquired;
            this.totalTimeHeldMs = totalTimeHeldMs;
            this.timeHeldMs = timeHeldMs;
        }

        public boolean isDataValid() {
            boolean isDataReasonable =
                    this.timesAcquired > 0
                            && this.totalTimeHeldMs > 0
                            && this.timeHeldMs >= 0
                            && this.totalTimeHeldMs >= this.timeHeldMs;
            return isEmpty() || isDataReasonable;
        }

        private boolean isEmpty() {
            return this.timesAcquired == 0 && this.totalTimeHeldMs == 0 && this.timeHeldMs == 0;
        }

        private WakeLockData(Parcel in) {
            this.timesAcquired = in.readInt();
            this.totalTimeHeldMs = in.readLong();
            this.timeHeldMs = in.readLong();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel out) {
            out.writeInt(this.timesAcquired);
            out.writeLong(this.totalTimeHeldMs);
            out.writeLong(this.timeHeldMs);
        }

        public String toString() {
            return "WakeLockData{timesAcquired="
                    + this.timesAcquired
                    + ", totalTimeHeldMs="
                    + this.totalTimeHeldMs
                    + ", timeHeldMs="
                    + this.timeHeldMs
                    + "}";
        }
    }

    public static class WakeLock {
        public static final String NAME_AGGREGATED = "wakelockstats_aggregated";
        public final WakeLockData backgroundWakeLockData;
        public final boolean isAggregated;
        public final String name;
        public final WakeLockData totalWakeLockData;
        public final int uid;

        public WakeLock(
                int uid,
                String name,
                boolean isAggregated,
                WakeLockData totalWakeLockData,
                WakeLockData backgroundWakeLockData) {
            this.uid = uid;
            this.name = name;
            this.isAggregated = isAggregated;
            this.totalWakeLockData = totalWakeLockData;
            this.backgroundWakeLockData = backgroundWakeLockData;
        }

        public static boolean isDataValid(
                WakeLockData totalWakeLockData, WakeLockData backgroundWakeLockData) {
            return totalWakeLockData.totalTimeHeldMs > 0
                    && totalWakeLockData.isDataValid()
                    && backgroundWakeLockData.isDataValid()
                    && totalWakeLockData.timesAcquired >= backgroundWakeLockData.timesAcquired
                    && totalWakeLockData.totalTimeHeldMs >= backgroundWakeLockData.totalTimeHeldMs
                    && totalWakeLockData.timeHeldMs >= backgroundWakeLockData.timeHeldMs;
        }

        private WakeLock(Parcel in) {
            this.uid = in.readInt();
            this.name = in.readString();
            this.isAggregated = in.readBoolean();
            this.totalWakeLockData = new WakeLockData(in);
            this.backgroundWakeLockData = new WakeLockData(in);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel out) {
            out.writeInt(this.uid);
            out.writeString(this.name);
            out.writeBoolean(this.isAggregated);
            this.totalWakeLockData.writeToParcel(out);
            this.backgroundWakeLockData.writeToParcel(out);
        }

        public String toString() {
            return "WakeLock{uid="
                    + this.uid
                    + ", name='"
                    + this.name
                    + DateFormat.QUOTE
                    + ", isAggregated="
                    + this.isAggregated
                    + ", totalWakeLockData="
                    + this.totalWakeLockData
                    + ", backgroundWakeLockData="
                    + this.backgroundWakeLockData
                    + '}';
        }
    }

    public WakeLockStats(List<WakeLock> wakeLocks, List<WakeLock> aggregatedWakeLocks) {
        this.mWakeLocks = wakeLocks;
        this.mAggregatedWakeLocks = aggregatedWakeLocks;
    }

    public List<WakeLock> getWakeLocks() {
        return this.mWakeLocks;
    }

    public List<WakeLock> getAggregatedWakeLocks() {
        return this.mAggregatedWakeLocks;
    }

    private WakeLockStats(Parcel in) {
        int wakelockSize = in.readInt();
        this.mWakeLocks = new ArrayList(wakelockSize);
        int i = 0;
        while (true) {
            if (i >= wakelockSize) {
                break;
            }
            this.mWakeLocks.add(new WakeLock(in));
            i++;
        }
        int aggregatedWakelockSize = in.readInt();
        this.mAggregatedWakeLocks = new ArrayList(aggregatedWakelockSize);
        for (int i2 = 0; i2 < aggregatedWakelockSize; i2++) {
            this.mAggregatedWakeLocks.add(new WakeLock(in));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        int wakelockSize = this.mWakeLocks.size();
        out.writeInt(wakelockSize);
        for (int i = 0; i < wakelockSize; i++) {
            WakeLock stats = this.mWakeLocks.get(i);
            stats.writeToParcel(out);
        }
        int aggregatedWakelockSize = this.mAggregatedWakeLocks.size();
        out.writeInt(aggregatedWakelockSize);
        for (int i2 = 0; i2 < aggregatedWakelockSize; i2++) {
            WakeLock stats2 = this.mAggregatedWakeLocks.get(i2);
            stats2.writeToParcel(out);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "WakeLockStats{mWakeLocks: ["
                + this.mWakeLocks
                + "], mAggregatedWakeLocks: ["
                + this.mAggregatedWakeLocks
                + NavigationBarInflaterView.SIZE_MOD_END
                + '}';
    }
}
