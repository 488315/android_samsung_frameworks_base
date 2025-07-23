package android.os;

import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class WakeLockStats implements Parcelable {
    public static final Parcelable.Creator<WakeLockStats> CREATOR = new Parcelable.Creator<WakeLockStats>() { // from class: android.os.WakeLockStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WakeLockStats createFromParcel(Parcel parcel) {
            return new WakeLockStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WakeLockStats[] newArray(int i) {
            return new WakeLockStats[i];
        }
    };
    private final List<WakeLock> mAggregatedWakeLocks;
    private final List<WakeLock> mWakeLocks;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class WakeLockData {
        public static final WakeLockData EMPTY = new WakeLockData(0, 0, 0);
        public final long timeHeldMs;
        public final int timesAcquired;
        public final long totalTimeHeldMs;

        public WakeLockData(int i, long j, long j2) {
            this.timesAcquired = i;
            this.totalTimeHeldMs = j;
            this.timeHeldMs = j2;
        }

        public boolean isDataValid() {
            boolean z;
            if (this.timesAcquired > 0) {
                long j = this.totalTimeHeldMs;
                if (j > 0) {
                    long j2 = this.timeHeldMs;
                    if (j2 >= 0 && j >= j2) {
                        z = true;
                        return !isEmpty() || z;
                    }
                }
            }
            z = false;
            if (isEmpty()) {
            }
        }

        private boolean isEmpty() {
            return this.timesAcquired == 0 && this.totalTimeHeldMs == 0 && this.timeHeldMs == 0;
        }

        private WakeLockData(Parcel parcel) {
            this.timesAcquired = parcel.readInt();
            this.totalTimeHeldMs = parcel.readLong();
            this.timeHeldMs = parcel.readLong();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.timesAcquired);
            parcel.writeLong(this.totalTimeHeldMs);
            parcel.writeLong(this.timeHeldMs);
        }

        public String toString() {
            return "WakeLockData{timesAcquired=" + this.timesAcquired + ", totalTimeHeldMs=" + this.totalTimeHeldMs + ", timeHeldMs=" + this.timeHeldMs + "}";
        }
    }

    public static class WakeLock {
        public static final String NAME_AGGREGATED = "wakelockstats_aggregated";
        public final WakeLockData backgroundWakeLockData;
        public final boolean isAggregated;
        public final String name;
        public final WakeLockData totalWakeLockData;
        public final int uid;

        public WakeLock(int i, String str, boolean z, WakeLockData wakeLockData, WakeLockData wakeLockData2) {
            this.uid = i;
            this.name = str;
            this.isAggregated = z;
            this.totalWakeLockData = wakeLockData;
            this.backgroundWakeLockData = wakeLockData2;
        }

        public static boolean isDataValid(WakeLockData wakeLockData, WakeLockData wakeLockData2) {
            return wakeLockData.totalTimeHeldMs > 0 && wakeLockData.isDataValid() && wakeLockData2.isDataValid() && wakeLockData.timesAcquired >= wakeLockData2.timesAcquired && wakeLockData.totalTimeHeldMs >= wakeLockData2.totalTimeHeldMs && wakeLockData.timeHeldMs >= wakeLockData2.timeHeldMs;
        }

        private WakeLock(Parcel parcel) {
            this.uid = parcel.readInt();
            this.name = parcel.readString();
            this.isAggregated = parcel.readBoolean();
            this.totalWakeLockData = new WakeLockData(parcel);
            this.backgroundWakeLockData = new WakeLockData(parcel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.uid);
            parcel.writeString(this.name);
            parcel.writeBoolean(this.isAggregated);
            this.totalWakeLockData.writeToParcel(parcel);
            this.backgroundWakeLockData.writeToParcel(parcel);
        }

        public String toString() {
            return "WakeLock{uid=" + this.uid + ", name='" + this.name + "', isAggregated=" + this.isAggregated + ", totalWakeLockData=" + this.totalWakeLockData + ", backgroundWakeLockData=" + this.backgroundWakeLockData + '}';
        }
    }

    public WakeLockStats(List<WakeLock> list, List<WakeLock> list2) {
        this.mWakeLocks = list;
        this.mAggregatedWakeLocks = list2;
    }

    public List<WakeLock> getWakeLocks() {
        return this.mWakeLocks;
    }

    public List<WakeLock> getAggregatedWakeLocks() {
        return this.mAggregatedWakeLocks;
    }

    private WakeLockStats(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mWakeLocks = new ArrayList(readInt);
        int i = 0;
        while (true) {
            if (i >= readInt) {
                break;
            }
            this.mWakeLocks.add(new WakeLock(parcel));
            i++;
        }
        int readInt2 = parcel.readInt();
        this.mAggregatedWakeLocks = new ArrayList(readInt2);
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mAggregatedWakeLocks.add(new WakeLock(parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int size = this.mWakeLocks.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.mWakeLocks.get(i2).writeToParcel(parcel);
        }
        int size2 = this.mAggregatedWakeLocks.size();
        parcel.writeInt(size2);
        for (int i3 = 0; i3 < size2; i3++) {
            this.mAggregatedWakeLocks.get(i3).writeToParcel(parcel);
        }
    }

    public String toString() {
        return "WakeLockStats{mWakeLocks: [" + this.mWakeLocks + "], mAggregatedWakeLocks: [" + this.mAggregatedWakeLocks + "]}";
    }
}
