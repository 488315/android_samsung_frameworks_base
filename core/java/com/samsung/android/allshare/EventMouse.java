package com.samsung.android.allshare;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* compiled from: IAppControlAPI.java */
/* loaded from: classes5.dex */
class EventMouse implements Parcelable {
  public static final Parcelable.Creator<EventMouse> CREATOR =
      new Parcelable.Creator<
          EventMouse>() { // from class: com.samsung.android.allshare.EventMouse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventMouse createFromParcel(Parcel source) {
          EventMouse eventsync = new EventMouse();
          eventsync.mType = source.readInt();
          eventsync.f2946mX = source.readInt();
          eventsync.f2947mY = source.readInt();
          eventsync.mDX = source.readInt();
          eventsync.mDY = source.readInt();
          eventsync.mButton = source.readInt();
          return eventsync;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventMouse[] newArray(int size) {
          return new EventMouse[size];
        }
      };
  public int mButton;
  public int mDX;
  public int mDY;
  public int mType;

  /* renamed from: mX */
  public int f2946mX;

  /* renamed from: mY */
  public int f2947mY;

  EventMouse() {}

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mType);
    dest.writeInt(this.f2946mX);
    dest.writeInt(this.f2947mY);
    dest.writeInt(this.mDX);
    dest.writeInt(this.mDY);
    dest.writeInt(this.mButton);
  }
}
