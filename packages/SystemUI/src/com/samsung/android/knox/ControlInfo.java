package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes4.dex */
public class ControlInfo implements Parcelable {
    public static final Parcelable.Creator<ControlInfo> CREATOR = new Parcelable.Creator<ControlInfo>() { // from class: com.samsung.android.knox.ControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlInfo createFromParcel(Parcel parcel) {
            return new ControlInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlInfo[] newArray(int i) {
            return new ControlInfo[i];
        }
    };
    public String adminPackageName;
    public List<String> entries;

    public /* synthetic */ ControlInfo(Parcel parcel, int i) {
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
        return "adminPackageName: " + this.adminPackageName + " ,appControlType: " + this.entries;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.adminPackageName);
        parcel.writeStringList(this.entries);
    }

    public ControlInfo() {
        this.adminPackageName = null;
        this.entries = null;
    }

    private ControlInfo(Parcel parcel) {
        this.adminPackageName = null;
        this.entries = null;
        readFromParcel(parcel);
    }
}
