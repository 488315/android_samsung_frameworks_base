package com.samsung.android.sdhms;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemProcessUsageStats implements Parcelable {
    public static final Parcelable.Creator<SemProcessUsageStats> CREATOR = new Parcelable.Creator<SemProcessUsageStats>() { // from class: com.samsung.android.sdhms.SemProcessUsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemProcessUsageStats createFromParcel(Parcel parcel) {
            return new SemProcessUsageStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemProcessUsageStats[] newArray(int i) {
            return new SemProcessUsageStats[i];
        }
    };
    private long cpuTime;
    private long endTime;
    private List<ProcessUsageHistoryItem> procUsageList;
    private long startTime;
    private long uptime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemProcessUsageStats(SemProcessUsageStats semProcessUsageStats) {
        this.startTime = semProcessUsageStats.getStartTimestamp();
        this.endTime = semProcessUsageStats.getEndTimestamp();
        this.procUsageList = semProcessUsageStats.getProcessUsageHistoryList();
    }

    public long getStartTimestamp() {
        return this.startTime;
    }

    public long getEndTimestamp() {
        return this.endTime;
    }

    public long getUptime() {
        return this.uptime;
    }

    public long getCpuTime() {
        return this.cpuTime;
    }

    public List<ProcessUsageHistoryItem> getProcessUsageHistoryList() {
        return this.procUsageList;
    }

    public static final class Builder {
        private long cpuTime;
        private long endTime;
        private List<ProcessUsageHistoryItem> procUsageList;
        private long startTime;
        private long uptime;

        public Builder startTimestamp(long j) {
            this.startTime = j;
            return this;
        }

        public Builder endTimestamp(long j) {
            this.endTime = j;
            return this;
        }

        public Builder uptime(long j) {
            this.uptime = j;
            return this;
        }

        public Builder cpuTime(long j) {
            this.cpuTime = j;
            return this;
        }

        public Builder processUsageHistoryList(List<ProcessUsageHistoryItem> list) {
            if (this.procUsageList == null) {
                this.procUsageList = new ArrayList();
            }
            if (list == null) {
                this.procUsageList.add(new ProcessUsageHistoryItem.Builder().processName("").uid(0).pid(0).usage(0L).build());
                return this;
            }
            this.procUsageList.addAll(list);
            return this;
        }

        public SemProcessUsageStats build() {
            return new SemProcessUsageStats(this);
        }
    }

    public SemProcessUsageStats(Builder builder) {
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.uptime = builder.uptime;
        this.cpuTime = builder.cpuTime;
        this.procUsageList = builder.procUsageList;
    }

    protected SemProcessUsageStats(Parcel parcel) {
        this.startTime = parcel.readLong();
        this.endTime = parcel.readLong();
        this.uptime = parcel.readLong();
        this.cpuTime = parcel.readLong();
        this.procUsageList = parcel.createTypedArrayList(ProcessUsageHistoryItem.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.startTime);
        parcel.writeLong(this.endTime);
        parcel.writeLong(this.uptime);
        parcel.writeLong(this.cpuTime);
        parcel.writeTypedList(this.procUsageList);
    }

    public static class ProcessUsageHistoryItem implements Parcelable {
        public static final Parcelable.Creator<ProcessUsageHistoryItem> CREATOR = new Parcelable.Creator<ProcessUsageHistoryItem>() { // from class: com.samsung.android.sdhms.SemProcessUsageStats.ProcessUsageHistoryItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessUsageHistoryItem createFromParcel(Parcel parcel) {
                return new ProcessUsageHistoryItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ProcessUsageHistoryItem[] newArray(int i) {
                return new ProcessUsageHistoryItem[i];
            }
        };
        private int pid;
        private String processName;
        private int uid;
        private long usage;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ProcessUsageHistoryItem(ProcessUsageHistoryItem processUsageHistoryItem) {
            this.processName = processUsageHistoryItem.getProcessName();
            this.uid = processUsageHistoryItem.getUid();
            this.pid = processUsageHistoryItem.getPid();
            this.usage = processUsageHistoryItem.getUsage();
        }

        public String getProcessName() {
            return this.processName;
        }

        public int getUid() {
            return this.uid;
        }

        public int getPid() {
            return this.pid;
        }

        public long getUsage() {
            return this.usage;
        }

        public static final class Builder {
            private int pid;
            private String processName;
            private int uid;
            private long usage;

            public Builder processName(String str) {
                this.processName = str;
                return this;
            }

            public Builder uid(int i) {
                this.uid = i;
                return this;
            }

            public Builder pid(int i) {
                this.pid = i;
                return this;
            }

            public Builder usage(long j) {
                this.usage = j;
                return this;
            }

            public ProcessUsageHistoryItem build() {
                return new ProcessUsageHistoryItem(this);
            }
        }

        public ProcessUsageHistoryItem(Builder builder) {
            this.processName = builder.processName;
            this.uid = builder.uid;
            this.pid = builder.pid;
            this.usage = builder.usage;
        }

        protected ProcessUsageHistoryItem(Parcel parcel) {
            this.processName = parcel.readString();
            this.uid = parcel.readInt();
            this.pid = parcel.readInt();
            this.usage = parcel.readLong();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.processName);
            parcel.writeInt(this.uid);
            parcel.writeInt(this.pid);
            parcel.writeLong(this.usage);
        }
    }
}
