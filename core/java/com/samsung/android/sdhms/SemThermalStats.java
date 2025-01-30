package com.samsung.android.sdhms;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SemThermalStats implements Parcelable {
  public static final Parcelable.Creator<SemThermalStats> CREATOR =
      new Parcelable.Creator<
          SemThermalStats>() { // from class: com.samsung.android.sdhms.SemThermalStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemThermalStats createFromParcel(Parcel in) {
          return new SemThermalStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemThermalStats[] newArray(int size) {
          return new SemThermalStats[size];
        }
      };
  private List<CpuMaxFrequencyThrottledHistoryItem> cpuFreqHistoryList;
  private List<TemperatureHistoryItem> tempHistoryList;

  public List<TemperatureHistoryItem> getTemperatureHistoryList() {
    return this.tempHistoryList;
  }

  public List<CpuMaxFrequencyThrottledHistoryItem> getCpuMaxFrequencyThrottledHistoryList() {
    return this.cpuFreqHistoryList;
  }

  public static final class Builder {
    private List<CpuMaxFrequencyThrottledHistoryItem> cpuFreqHistoryList;
    private List<TemperatureHistoryItem> tempHistoryList;

    public Builder tempHistoryList(List<TemperatureHistoryItem> value) {
      if (this.tempHistoryList == null) {
        this.tempHistoryList = new ArrayList();
      }
      if (value == null) {
        this.tempHistoryList.add(
            new TemperatureHistoryItem.Builder().updatedTime(0L).skinTemperature(0).build());
        return this;
      }
      this.tempHistoryList.addAll(value);
      return this;
    }

    public Builder cpuFreqHistoryList(List<CpuMaxFrequencyThrottledHistoryItem> value) {
      if (this.cpuFreqHistoryList == null) {
        this.cpuFreqHistoryList = new ArrayList();
      }
      if (value == null) {
        this.cpuFreqHistoryList.add(
            new CpuMaxFrequencyThrottledHistoryItem.Builder()
                .updatedTime(0L)
                .cpuMaxFrequency(0)
                .build());
        return this;
      }
      this.cpuFreqHistoryList.addAll(value);
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

  protected SemThermalStats(Parcel in) {
    this.tempHistoryList = in.createTypedArrayList(TemperatureHistoryItem.CREATOR);
    this.cpuFreqHistoryList = in.createTypedArrayList(CpuMaxFrequencyThrottledHistoryItem.CREATOR);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeTypedList(this.tempHistoryList);
    parcel.writeTypedList(this.cpuFreqHistoryList);
  }

  public static class TemperatureHistoryItem implements Parcelable {
    public static final Parcelable.Creator<TemperatureHistoryItem> CREATOR =
        new Parcelable.Creator<
            TemperatureHistoryItem>() { // from class:
                                        // com.samsung.android.sdhms.SemThermalStats.TemperatureHistoryItem.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public TemperatureHistoryItem createFromParcel(Parcel in) {
            return new TemperatureHistoryItem(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public TemperatureHistoryItem[] newArray(int size) {
            return new TemperatureHistoryItem[size];
          }
        };
    private int skinTemp;
    private long updatedTime;

    public TemperatureHistoryItem(TemperatureHistoryItem copy) {
      this.updatedTime = copy.getUpdatedTimestamp();
      this.skinTemp = copy.getSkinTemperature();
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

      public Builder updatedTime(long value) {
        this.updatedTime = value;
        return this;
      }

      public Builder skinTemperature(int value) {
        this.skinTemp = value;
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

    protected TemperatureHistoryItem(Parcel in) {
      this.updatedTime = in.readLong();
      this.skinTemp = in.readInt();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
      parcel.writeLong(this.updatedTime);
      parcel.writeInt(this.skinTemp);
    }
  }

  public static class CpuMaxFrequencyThrottledHistoryItem implements Parcelable {
    public static final Parcelable.Creator<CpuMaxFrequencyThrottledHistoryItem> CREATOR =
        new Parcelable.Creator<
            CpuMaxFrequencyThrottledHistoryItem>() { // from class:
                                                     // com.samsung.android.sdhms.SemThermalStats.CpuMaxFrequencyThrottledHistoryItem.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public CpuMaxFrequencyThrottledHistoryItem createFromParcel(Parcel in) {
            return new CpuMaxFrequencyThrottledHistoryItem(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public CpuMaxFrequencyThrottledHistoryItem[] newArray(int size) {
            return new CpuMaxFrequencyThrottledHistoryItem[size];
          }
        };
    private int cpuMaxFreq;
    private long updatedTime;

    public CpuMaxFrequencyThrottledHistoryItem(CpuMaxFrequencyThrottledHistoryItem copy) {
      this.updatedTime = copy.getUpdatedTimestamp();
      this.cpuMaxFreq = copy.getCpuMaxFrequency();
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

      public Builder updatedTime(long value) {
        this.updatedTime = value;
        return this;
      }

      public Builder cpuMaxFrequency(int value) {
        this.cpuMaxFreq = value;
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

    protected CpuMaxFrequencyThrottledHistoryItem(Parcel in) {
      this.updatedTime = in.readLong();
      this.cpuMaxFreq = in.readInt();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
      parcel.writeLong(this.updatedTime);
      parcel.writeInt(this.cpuMaxFreq);
    }
  }
}
