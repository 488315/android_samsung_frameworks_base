package com.samsung.android.allshare;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: IAppControlAPI.java */
/* loaded from: classes5.dex */
class EventTouch implements Parcelable {
  public static final Parcelable.Creator<EventTouch> CREATOR =
      new Parcelable.Creator<
          EventTouch>() { // from class: com.samsung.android.allshare.EventTouch.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventTouch createFromParcel(Parcel source) {
          EventTouch eventsync = new EventTouch();
          eventsync.f2948mX = source.readInt();
          eventsync.f2949mY = source.readInt();
          eventsync.mDX = source.readInt();
          eventsync.mDY = source.readInt();
          eventsync.mAccelLevel = source.readInt();
          eventsync.mFingerId = source.readInt();
          eventsync.mType = source.readInt();
          eventsync.mDistance = source.readInt();
          eventsync.mDegree = source.readInt();
          eventsync.mStr = source.readString();
          return eventsync;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventTouch[] newArray(int size) {
          return new EventTouch[size];
        }
      };
  public int mAccelLevel;
  public int mDX;
  public int mDY;
  public int mDegree;
  public int mDistance;
  public int mFingerId;
  public String mStr;
  public int mType;

  /* renamed from: mX */
  public int f2948mX;

  /* renamed from: mY */
  public int f2949mY;

  EventTouch() {}

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.f2948mX);
    dest.writeInt(this.f2949mY);
    dest.writeInt(this.mDX);
    dest.writeInt(this.mDY);
    dest.writeInt(this.mAccelLevel);
    dest.writeInt(this.mFingerId);
    dest.writeInt(this.mType);
    dest.writeInt(this.mDistance);
    dest.writeInt(this.mDegree);
    dest.writeString(this.mStr);
  }
}
