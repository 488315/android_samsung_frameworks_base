package com.samsung.android.knox.net.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WifiControlInfo implements Parcelable {
    public static final Parcelable.Creator<WifiControlInfo> CREATOR = new Parcelable.Creator<WifiControlInfo>() { // from class: com.samsung.android.knox.net.wifi.WifiControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiControlInfo createFromParcel(Parcel parcel) {
            return new WifiControlInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiControlInfo[] newArray(int i) {
            return new WifiControlInfo[i];
        }
    };
    public String adminPackageName;
    public List<String> entries;

    public /* synthetic */ WifiControlInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.adminPackageName = parcel.readString();
        this.entries = parcel.createStringArrayList();
    }

    public String toString() {
        return "adminPackageName: " + this.adminPackageName + " ,entries: " + this.entries;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.adminPackageName);
        parcel.writeStringList(this.entries);
    }

    public WifiControlInfo() {
        this.adminPackageName = null;
        this.entries = null;
    }

    private WifiControlInfo(Parcel parcel) {
        this.adminPackageName = null;
        this.entries = null;
        readFromParcel(parcel);
    }
}
