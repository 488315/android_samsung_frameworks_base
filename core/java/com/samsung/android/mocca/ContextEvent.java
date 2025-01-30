package com.samsung.android.mocca;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes5.dex */
public final class ContextEvent implements Serializable, Parcelable {
  public static final Parcelable.Creator<ContextEvent> CREATOR =
      new Parcelable.Creator<
          ContextEvent>() { // from class: com.samsung.android.mocca.ContextEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextEvent createFromParcel(Parcel in) {
          return new ContextEvent(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextEvent[] newArray(int size) {
          return new ContextEvent[size];
        }
      };
  private static final long serialVersionUID = 4417824416088197504L;
  public final byte[] data;
  public final String deviceId;
  public final long timestamp;
  public final String type;

  private ContextEvent(long timestamp, String deviceId, String type, byte[] data) {
    this.timestamp = timestamp;
    this.deviceId = deviceId;
    this.type = type;
    this.data = Arrays.copyOf(data, data.length);
  }

  protected ContextEvent(Parcel in) {
    this.timestamp = in.readLong();
    this.deviceId = in.readString();
    this.type = in.readString();
    this.data = in.createByteArray();
  }

  public static ContextEventBuilder builder() {
    return new ContextEventBuilder();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeLong(this.timestamp);
    parcel.writeString(this.deviceId);
    parcel.writeString(this.type);
    parcel.writeByteArray(this.data);
  }

  public static class ContextEventBuilder {
    private long mTimestamp = -1;
    private String mDeviceId = null;
    private String mType = null;
    private byte[] mData = null;

    protected ContextEventBuilder() {}

    public ContextEventBuilder setTimestamp(long timestamp) {
      this.mTimestamp = timestamp;
      return this;
    }

    public ContextEventBuilder setDeviceId(String deviceId) {
      this.mDeviceId = deviceId;
      return this;
    }

    public ContextEventBuilder setType(String type) {
      this.mType = type;
      return this;
    }

    public ContextEventBuilder setData(byte[] data) {
      this.mData = data;
      return this;
    }

    public ContextEvent build() {
      if (this.mTimestamp < 0
          || this.mDeviceId == null
          || this.mType == null
          || this.mData == null) {
        return null;
      }
      return new ContextEvent(this.mTimestamp, this.mDeviceId, this.mType, this.mData);
    }
  }
}
