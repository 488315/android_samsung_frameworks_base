package com.samsung.android.knox.net.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WifiControlInfo implements Parcelable {
    public static final Parcelable.Creator<WifiControlInfo> CREATOR = new Parcelable.Creator<WifiControlInfo>() { // from class: com.samsung.android.knox.net.wifi.WifiControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final WifiControlInfo[] newArray(int i) {
            return new WifiControlInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final WifiControlInfo createFromParcel(Parcel parcel) {
            return new WifiControlInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final WifiControlInfo[] newArray(int i) {
            return new WifiControlInfo[i];
        }
    };
    public String adminPackageName;
    public List<String> entries;

    public /* synthetic */ WifiControlInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.adminPackageName = parcel.readString();
        this.entries = parcel.createStringArrayList();
    }

    public final String toString() {
        return "adminPackageName: " + this.adminPackageName + " ,entries: " + this.entries;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
