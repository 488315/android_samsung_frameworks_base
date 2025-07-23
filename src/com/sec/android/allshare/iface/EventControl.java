package com.sec.android.allshare.iface;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class EventControl implements Parcelable {
    public static final Parcelable.Creator<EventControl> CREATOR = new Parcelable.Creator<EventControl>() { // from class: com.sec.android.allshare.iface.EventControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventControl createFromParcel(Parcel parcel) {
            EventControl eventControl = new EventControl();
            eventControl.mWhat = parcel.readInt();
            eventControl.mArg1 = parcel.readInt();
            eventControl.mArg2 = parcel.readInt();
            eventControl.mStr = parcel.readString();
            return eventControl;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventControl[] newArray(int i) {
            return new EventControl[i];
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mWhat);
        parcel.writeInt(this.mArg1);
        parcel.writeInt(this.mArg2);
        parcel.writeString(this.mStr);
    }
}
