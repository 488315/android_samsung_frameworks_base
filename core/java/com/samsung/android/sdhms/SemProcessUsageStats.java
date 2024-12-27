package com.samsung.android.sdhms;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SemProcessUsageStats implements Parcelable {
    public static final Parcelable.Creator<SemProcessUsageStats> CREATOR =
            new Parcelable.Creator<
                    SemProcessUsageStats>() { // from class:
                                              // com.samsung.android.sdhms.SemProcessUsageStats.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemProcessUsageStats createFromParcel(Parcel in) {
                    return new SemProcessUsageStats(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemProcessUsageStats[] newArray(int size) {
                    return new SemProcessUsageStats[size];
                }
            };
    private long cpuTime;
    private long endTime;
    private List<ProcessUsageHistoryItem> procUsageList;
    private long startTime;
    private long uptime;

    public SemProcessUsageStats(SemProcessUsageStats copy) {
        this.startTime = copy.getStartTimestamp();
        this.endTime = copy.getEndTimestamp();
        this.procUsageList = copy.getProcessUsageHistoryList();
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

        public Builder startTimestamp(long value) {
            this.startTime = value;
            return this;
        }

        public Builder endTimestamp(long value) {
            this.endTime = value;
            return this;
        }

        public Builder uptime(long value) {
            this.uptime = value;
            return this;
        }

        public Builder cpuTime(long value) {
            this.cpuTime = value;
            return this;
        }

        public Builder processUsageHistoryList(List<ProcessUsageHistoryItem> value) {
            if (this.procUsageList == null) {
                this.procUsageList = new ArrayList();
            }
            if (value == null) {
                this.procUsageList.add(
                        new ProcessUsageHistoryItem.Builder()
                                .processName("")
                                .uid(0)
                                .pid(0)
                                .usage(0L)
                                .build());
                return this;
            }
            this.procUsageList.addAll(value);
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

    protected SemProcessUsageStats(Parcel in) {
        this.startTime = in.readLong();
        this.endTime = in.readLong();
        this.uptime = in.readLong();
        this.cpuTime = in.readLong();
        this.procUsageList = in.createTypedArrayList(ProcessUsageHistoryItem.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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
        public static final Parcelable.Creator<ProcessUsageHistoryItem> CREATOR =
                new Parcelable.Creator<
                        ProcessUsageHistoryItem>() { // from class:
                                                     // com.samsung.android.sdhms.SemProcessUsageStats.ProcessUsageHistoryItem.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ProcessUsageHistoryItem createFromParcel(Parcel in) {
                        return new ProcessUsageHistoryItem(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ProcessUsageHistoryItem[] newArray(int size) {
                        return new ProcessUsageHistoryItem[size];
                    }
                };
        private int pid;
        private String processName;
        private int uid;
        private long usage;

        public ProcessUsageHistoryItem(ProcessUsageHistoryItem copy) {
            this.processName = copy.getProcessName();
            this.uid = copy.getUid();
            this.pid = copy.getPid();
            this.usage = copy.getUsage();
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

            public Builder processName(String value) {
                this.processName = value;
                return this;
            }

            public Builder uid(int value) {
                this.uid = value;
                return this;
            }

            public Builder pid(int value) {
                this.pid = value;
                return this;
            }

            public Builder usage(long value) {
                this.usage = value;
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

        protected ProcessUsageHistoryItem(Parcel in) {
            this.processName = in.readString();
            this.uid = in.readInt();
            this.pid = in.readInt();
            this.usage = in.readLong();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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
