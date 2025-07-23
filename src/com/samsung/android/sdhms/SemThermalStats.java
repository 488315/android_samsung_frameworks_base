package com.samsung.android.sdhms;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemThermalStats implements Parcelable {
    public static final Parcelable.Creator<SemThermalStats> CREATOR = new Parcelable.Creator<SemThermalStats>() { // from class: com.samsung.android.sdhms.SemThermalStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemThermalStats createFromParcel(Parcel parcel) {
            return new SemThermalStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemThermalStats[] newArray(int i) {
            return new SemThermalStats[i];
        }
    };
    private List<CpuMaxFrequencyThrottledHistoryItem> cpuFreqHistoryList;
    private List<TemperatureHistoryItem> tempHistoryList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<TemperatureHistoryItem> getTemperatureHistoryList() {
        return this.tempHistoryList;
    }

    public List<CpuMaxFrequencyThrottledHistoryItem> getCpuMaxFrequencyThrottledHistoryList() {
        return this.cpuFreqHistoryList;
    }

    public static final class Builder {
        private List<CpuMaxFrequencyThrottledHistoryItem> cpuFreqHistoryList;
        private List<TemperatureHistoryItem> tempHistoryList;

        public Builder tempHistoryList(List<TemperatureHistoryItem> list) {
            if (this.tempHistoryList == null) {
                this.tempHistoryList = new ArrayList();
            }
            if (list == null) {
                this.tempHistoryList.add(new TemperatureHistoryItem.Builder().updatedTime(0L).skinTemperature(0).build());
                return this;
            }
            this.tempHistoryList.addAll(list);
            return this;
        }

        public Builder cpuFreqHistoryList(List<CpuMaxFrequencyThrottledHistoryItem> list) {
            if (this.cpuFreqHistoryList == null) {
                this.cpuFreqHistoryList = new ArrayList();
            }
            if (list == null) {
                this.cpuFreqHistoryList.add(new CpuMaxFrequencyThrottledHistoryItem.Builder().updatedTime(0L).cpuMaxFrequency(0).build());
                return this;
            }
            this.cpuFreqHistoryList.addAll(list);
            return this;
        }

        public SemThermalStats build() {
            return new SemThermalStats(this);
        }
    }

    public SemThermalStats(Builder builder) {
        this.tempHistoryList = builder.tempHistoryList;
        this.cpuFreqHistoryList = builder.cpuFreqHistoryList;
    }

    protected SemThermalStats(Parcel parcel) {
        this.tempHistoryList = parcel.createTypedArrayList(TemperatureHistoryItem.CREATOR);
        this.cpuFreqHistoryList = parcel.createTypedArrayList(CpuMaxFrequencyThrottledHistoryItem.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.tempHistoryList);
        parcel.writeTypedList(this.cpuFreqHistoryList);
    }

    public static class TemperatureHistoryItem implements Parcelable {
        public static final Parcelable.Creator<TemperatureHistoryItem> CREATOR = new Parcelable.Creator<TemperatureHistoryItem>() { // from class: com.samsung.android.sdhms.SemThermalStats.TemperatureHistoryItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TemperatureHistoryItem createFromParcel(Parcel parcel) {
                return new TemperatureHistoryItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TemperatureHistoryItem[] newArray(int i) {
                return new TemperatureHistoryItem[i];
            }
        };
        private int skinTemp;
        private long updatedTime;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public TemperatureHistoryItem(TemperatureHistoryItem temperatureHistoryItem) {
            this.updatedTime = temperatureHistoryItem.getUpdatedTimestamp();
            this.skinTemp = temperatureHistoryItem.getSkinTemperature();
        }

        public long getUpdatedTimestamp() {
            return this.updatedTime;
        }

        public int getSkinTemperature() {
            return this.skinTemp;
        }

        public static final class Builder {
            private int skinTemp;
            private long updatedTime;

            public Builder updatedTime(long j) {
                this.updatedTime = j;
                return this;
            }

            public Builder skinTemperature(int i) {
                this.skinTemp = i;
                return this;
            }

            public TemperatureHistoryItem build() {
                return new TemperatureHistoryItem(this);
            }
        }

        public TemperatureHistoryItem(Builder builder) {
            this.updatedTime = builder.updatedTime;
            this.skinTemp = builder.skinTemp;
        }

        protected TemperatureHistoryItem(Parcel parcel) {
            this.updatedTime = parcel.readLong();
            this.skinTemp = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.updatedTime);
            parcel.writeInt(this.skinTemp);
        }
    }

    public static class CpuMaxFrequencyThrottledHistoryItem implements Parcelable {
        public static final Parcelable.Creator<CpuMaxFrequencyThrottledHistoryItem> CREATOR = new Parcelable.Creator<CpuMaxFrequencyThrottledHistoryItem>() { // from class: com.samsung.android.sdhms.SemThermalStats.CpuMaxFrequencyThrottledHistoryItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CpuMaxFrequencyThrottledHistoryItem createFromParcel(Parcel parcel) {
                return new CpuMaxFrequencyThrottledHistoryItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CpuMaxFrequencyThrottledHistoryItem[] newArray(int i) {
                return new CpuMaxFrequencyThrottledHistoryItem[i];
            }
        };
        private int cpuMaxFreq;
        private long updatedTime;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public CpuMaxFrequencyThrottledHistoryItem(CpuMaxFrequencyThrottledHistoryItem cpuMaxFrequencyThrottledHistoryItem) {
            this.updatedTime = cpuMaxFrequencyThrottledHistoryItem.getUpdatedTimestamp();
            this.cpuMaxFreq = cpuMaxFrequencyThrottledHistoryItem.getCpuMaxFrequency();
        }

        public long getUpdatedTimestamp() {
            return this.updatedTime;
        }

        public int getCpuMaxFrequency() {
            return this.cpuMaxFreq;
        }

        public static final class Builder {
            private int cpuMaxFreq;
            private long updatedTime;

            public Builder updatedTime(long j) {
                this.updatedTime = j;
                return this;
            }

            public Builder cpuMaxFrequency(int i) {
                this.cpuMaxFreq = i;
                return this;
            }

            public CpuMaxFrequencyThrottledHistoryItem build() {
                return new CpuMaxFrequencyThrottledHistoryItem(this);
            }
        }

        public CpuMaxFrequencyThrottledHistoryItem(Builder builder) {
            this.updatedTime = builder.updatedTime;
            this.cpuMaxFreq = builder.cpuMaxFreq;
        }

        protected CpuMaxFrequencyThrottledHistoryItem(Parcel parcel) {
            this.updatedTime = parcel.readLong();
            this.cpuMaxFreq = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.updatedTime);
            parcel.writeInt(this.cpuMaxFreq);
        }
    }
}
