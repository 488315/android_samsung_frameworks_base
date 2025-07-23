package com.samsung.android.sdhms;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemNetworkUsageStats implements Parcelable {
    public static final Parcelable.Creator<SemNetworkUsageStats> CREATOR = new Parcelable.Creator<SemNetworkUsageStats>() { // from class: com.samsung.android.sdhms.SemNetworkUsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemNetworkUsageStats createFromParcel(Parcel parcel) {
            return new SemNetworkUsageStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemNetworkUsageStats[] newArray(int i) {
            return new SemNetworkUsageStats[i];
        }
    };
    private long endTime;
    private List<NetworkUsageHistoryItem> netUsageList;
    private long startTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemNetworkUsageStats(SemNetworkUsageStats semNetworkUsageStats) {
        this.startTime = semNetworkUsageStats.getStartTimestamp();
        this.endTime = semNetworkUsageStats.getEndTimestamp();
        this.netUsageList = semNetworkUsageStats.getNetworkUsageHistoryList();
    }

    public long getStartTimestamp() {
        return this.startTime;
    }

    public long getEndTimestamp() {
        return this.endTime;
    }

    public List<NetworkUsageHistoryItem> getNetworkUsageHistoryList() {
        return this.netUsageList;
    }

    public static final class Builder {
        private long endTime;
        private List<NetworkUsageHistoryItem> netUsageList;
        private long startTime;

        public Builder startTimestamp(long j) {
            this.startTime = j;
            return this;
        }

        public Builder endTimestamp(long j) {
            this.endTime = j;
            return this;
        }

        public Builder networkUsageHistoryList(List<NetworkUsageHistoryItem> list) {
            if (this.netUsageList == null) {
                this.netUsageList = new ArrayList();
            }
            if (list == null) {
                this.netUsageList.add(new NetworkUsageHistoryItem.Builder().packageName("").uid(0).usage(0L).build());
                return this;
            }
            this.netUsageList.addAll(list);
            return this;
        }

        public SemNetworkUsageStats build() {
            return new SemNetworkUsageStats(this);
        }
    }

    public SemNetworkUsageStats(Builder builder) {
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.netUsageList = builder.netUsageList;
    }

    protected SemNetworkUsageStats(Parcel parcel) {
        this.startTime = parcel.readLong();
        this.endTime = parcel.readLong();
        this.netUsageList = parcel.createTypedArrayList(NetworkUsageHistoryItem.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.startTime);
        parcel.writeLong(this.endTime);
        parcel.writeTypedList(this.netUsageList);
    }

    public static class NetworkUsageHistoryItem implements Parcelable {
        public static final Parcelable.Creator<NetworkUsageHistoryItem> CREATOR = new Parcelable.Creator<NetworkUsageHistoryItem>() { // from class: com.samsung.android.sdhms.SemNetworkUsageStats.NetworkUsageHistoryItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NetworkUsageHistoryItem createFromParcel(Parcel parcel) {
                return new NetworkUsageHistoryItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NetworkUsageHistoryItem[] newArray(int i) {
                return new NetworkUsageHistoryItem[i];
            }
        };
        private String packageName;
        private int uid;
        private long usage;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public NetworkUsageHistoryItem(NetworkUsageHistoryItem networkUsageHistoryItem) {
            this.packageName = networkUsageHistoryItem.getPackageName();
            this.uid = networkUsageHistoryItem.getUid();
            this.usage = networkUsageHistoryItem.getUsage();
        }

        public String getPackageName() {
            return this.packageName;
        }

        public int getUid() {
            return this.uid;
        }

        public long getUsage() {
            return this.usage;
        }

        public static final class Builder {
            private String packageName;
            private int uid;
            private long usage;

            public Builder packageName(String str) {
                this.packageName = str;
                return this;
            }

            public Builder uid(int i) {
                this.uid = i;
                return this;
            }

            public Builder usage(long j) {
                this.usage = j;
                return this;
            }

            public NetworkUsageHistoryItem build() {
                return new NetworkUsageHistoryItem(this);
            }
        }

        public NetworkUsageHistoryItem(Builder builder) {
            this.packageName = builder.packageName;
            this.uid = builder.uid;
            this.usage = builder.usage;
        }

        protected NetworkUsageHistoryItem(Parcel parcel) {
            this.packageName = parcel.readString();
            this.uid = parcel.readInt();
            this.usage = parcel.readLong();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.packageName);
            parcel.writeInt(this.uid);
            parcel.writeLong(this.usage);
        }
    }
}
