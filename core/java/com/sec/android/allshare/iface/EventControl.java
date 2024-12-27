package com.sec.android.allshare.iface;

import android.os.Parcel;
import android.os.Parcelable;

public class EventControl implements Parcelable {
    public static final Parcelable.Creator<EventControl> CREATOR =
            new Parcelable.Creator<
                    EventControl>() { // from class: com.sec.android.allshare.iface.EventControl.1
                @Override // android.os.Parcelable.Creator
                public EventControl createFromParcel(Parcel source) {
                    EventControl eventsync = new EventControl();
                    eventsync.mWhat = source.readInt();
                    eventsync.mArg1 = source.readInt();
                    eventsync.mArg2 = source.readInt();
                    eventsync.mStr = source.readString();
                    return eventsync;
                }

                @Override // android.os.Parcelable.Creator
                public EventControl[] newArray(int size) {
                    return new EventControl[size];
                }
            };
    public int mArg1;
    public int mArg2;
    public String mStr;
    public int mWhat;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mWhat);
        dest.writeInt(this.mArg1);
        dest.writeInt(this.mArg2);
        dest.writeString(this.mStr);
    }
}
