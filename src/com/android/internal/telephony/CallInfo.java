package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class CallInfo implements Parcelable {
    public static final Parcelable.Creator<CallInfo> CREATOR = new Parcelable.Creator<CallInfo>() { // from class: com.android.internal.telephony.CallInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallInfo createFromParcel(Parcel parcel) {
            return new CallInfo(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallInfo[] newArray(int i) {
            return new CallInfo[i];
        }
    };
    private String handle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CallInfo(String str) {
        this.handle = str;
    }

    public String getHandle() {
        return this.handle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.handle);
    }
}
