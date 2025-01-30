package com.android.server.location.nsflp;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class NSConnectionRecord implements Parcelable {
  public static final Parcelable.Creator CREATOR =
      new Parcelable
          .Creator() { // from class: com.android.server.location.nsflp.NSConnectionRecord.1
        @Override // android.os.Parcelable.Creator
        public NSConnectionRecord createFromParcel(Parcel parcel) {
          return new NSConnectionRecord(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public NSConnectionRecord[] newArray(int i) {
          return new NSConnectionRecord[i];
        }
      };
  public String componentName;
  public long connectedTime;
  public int connectionCount;
  public long disconnectedTime;
  public int disconnectionCount;
  public final String logTag;
  public String packageName;
  public String prevComponentName;
  public String prevPackageName;
  public long serviceBindingDiedTime;

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public NSConnectionRecord(String str) {
    this.componentName = null;
    this.prevComponentName = null;
    this.packageName = null;
    this.prevPackageName = null;
    this.connectionCount = 0;
    this.disconnectionCount = 0;
    this.connectedTime = 0L;
    this.disconnectedTime = 0L;
    this.serviceBindingDiedTime = 0L;
    this.logTag = str;
  }

  public NSConnectionRecord(Parcel parcel) {
    this.componentName = null;
    this.prevComponentName = null;
    this.packageName = null;
    this.prevPackageName = null;
    this.connectionCount = 0;
    this.disconnectionCount = 0;
    this.connectedTime = 0L;
    this.disconnectedTime = 0L;
    this.serviceBindingDiedTime = 0L;
    this.logTag = parcel.readString();
    this.componentName = parcel.readString();
    this.prevComponentName = parcel.readString();
    this.packageName = parcel.readString();
    this.prevPackageName = parcel.readString();
    this.connectionCount = parcel.readInt();
    this.disconnectionCount = parcel.readInt();
    this.connectedTime = parcel.readLong();
    this.disconnectedTime = parcel.readLong();
    this.serviceBindingDiedTime = parcel.readLong();
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.logTag);
    parcel.writeString(this.componentName);
    parcel.writeString(this.prevComponentName);
    parcel.writeString(this.packageName);
    parcel.writeString(this.prevPackageName);
    parcel.writeInt(this.connectionCount);
    parcel.writeInt(this.disconnectionCount);
    parcel.writeLong(this.connectedTime);
    parcel.writeLong(this.disconnectedTime);
    parcel.writeLong(this.serviceBindingDiedTime);
  }

  public void setComponentName(String str) {
    String str2 = this.componentName;
    if (str2 != null) {
      this.prevComponentName = str2;
    }
    this.componentName = str;
  }

  public void setPackageName(String str) {
    String str2 = this.packageName;
    if (str2 != null) {
      this.prevPackageName = str2;
    }
    this.packageName = str;
  }

  public void increaseConnectionCount() {
    this.connectionCount++;
  }

  public void increaseDisconnectionCount() {
    this.disconnectionCount++;
  }

  public void setConnectedTime(long j) {
    this.connectedTime = j;
  }

  public void setDisconnectedTime(long j) {
    this.disconnectedTime = j;
  }

  public void setServiceBindingDiedTime(long j) {
    this.serviceBindingDiedTime = j;
  }

  public String toString() {
    return "tag="
        + this.logTag
        + "_prevComp="
        + this.prevComponentName
        + "_comp="
        + this.componentName
        + "_connCnt="
        + this.connectionCount
        + "_discCnt="
        + this.disconnectionCount
        + "_discTime="
        + this.disconnectedTime
        + "_discDur="
        + getDisconnectedDuration()
        + "_bindDiedTime="
        + this.serviceBindingDiedTime;
  }

  public long getDisconnectedDuration() {
    long j = this.connectedTime;
    long j2 = this.disconnectedTime;
    if (j >= j2) {
      return j - j2;
    }
    return 0L;
  }
}
