package com.samsung.android.sdhms;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SemNetworkUsageStats implements Parcelable {
  public static final Parcelable.Creator<SemNetworkUsageStats> CREATOR =
      new Parcelable.Creator<
          SemNetworkUsageStats>() { // from class: com.samsung.android.sdhms.SemNetworkUsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemNetworkUsageStats createFromParcel(Parcel in) {
          return new SemNetworkUsageStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemNetworkUsageStats[] newArray(int size) {
          return new SemNetworkUsageStats[size];
        }
      };
  private long endTime;
  private List<NetworkUsageHistoryItem> netUsageList;
  private long startTime;

  public SemNetworkUsageStats(SemNetworkUsageStats copy) {
    this.startTime = copy.getStartTimestamp();
    this.endTime = copy.getEndTimestamp();
    this.netUsageList = copy.getNetworkUsageHistoryList();
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

    public Builder startTimestamp(long value) {
      this.startTime = value;
      return this;
    }

    public Builder endTimestamp(long value) {
      this.endTime = value;
      return this;
    }

    public Builder networkUsageHistoryList(List<NetworkUsageHistoryItem> value) {
      if (this.netUsageList == null) {
        this.netUsageList = new ArrayList();
      }
      if (value == null) {
        this.netUsageList.add(
            new NetworkUsageHistoryItem.Builder().packageName("").uid(0).usage(0L).build());
        return this;
      }
      this.netUsageList.addAll(value);
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

  protected SemNetworkUsageStats(Parcel in) {
    this.startTime = in.readLong();
    this.endTime = in.readLong();
    this.netUsageList = in.createTypedArrayList(NetworkUsageHistoryItem.CREATOR);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeLong(this.startTime);
    parcel.writeLong(this.endTime);
    parcel.writeTypedList(this.netUsageList);
  }

  public static class NetworkUsageHistoryItem implements Parcelable {
    public static final Parcelable.Creator<NetworkUsageHistoryItem> CREATOR =
        new Parcelable.Creator<
            NetworkUsageHistoryItem>() { // from class:
                                         // com.samsung.android.sdhms.SemNetworkUsageStats.NetworkUsageHistoryItem.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public NetworkUsageHistoryItem createFromParcel(Parcel in) {
            return new NetworkUsageHistoryItem(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public NetworkUsageHistoryItem[] newArray(int size) {
            return new NetworkUsageHistoryItem[size];
          }
        };
    private String packageName;
    private int uid;
    private long usage;

    public NetworkUsageHistoryItem(NetworkUsageHistoryItem copy) {
      this.packageName = copy.getPackageName();
      this.uid = copy.getUid();
      this.usage = copy.getUsage();
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

      public Builder packageName(String value) {
        this.packageName = value;
        return this;
      }

      public Builder uid(int value) {
        this.uid = value;
        return this;
      }

      public Builder usage(long value) {
        this.usage = value;
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

    protected NetworkUsageHistoryItem(Parcel in) {
      this.packageName = in.readString();
      this.uid = in.readInt();
      this.usage = in.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(this.packageName);
      parcel.writeInt(this.uid);
      parcel.writeLong(this.usage);
    }
  }
}
