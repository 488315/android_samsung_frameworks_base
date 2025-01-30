package com.samsung.android.allshare;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* compiled from: IAppControlAPI.java */
/* loaded from: classes5.dex */
class EventSync implements Parcelable {
  public static final Parcelable.Creator<EventSync> CREATOR =
      new Parcelable.Creator<EventSync>() { // from class: com.samsung.android.allshare.EventSync.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventSync createFromParcel(Parcel source) {
          EventSync eventsync = new EventSync();
          eventsync.mWhat = source.readInt();
          eventsync.mArg1 = source.readInt();
          eventsync.mArg2 = source.readInt();
          eventsync.mStr = source.readString();
          return eventsync;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventSync[] newArray(int size) {
          return new EventSync[size];
        }
      };
  public int mArg1;
  public int mArg2;
  public String mStr;
  public int mWhat;

  EventSync() {}

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mWhat);
    dest.writeInt(this.mArg1);
    dest.writeInt(this.mArg2);
    dest.writeString(this.mStr);
  }
}
