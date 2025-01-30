package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ControlInfo implements Parcelable {
    public static final Parcelable.Creator<ControlInfo> CREATOR = new Parcelable.Creator<ControlInfo>() { // from class: com.samsung.android.knox.ControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ControlInfo[] newArray(int i) {
            return new ControlInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ControlInfo createFromParcel(Parcel parcel) {
            return new ControlInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final ControlInfo[] newArray(int i) {
            return new ControlInfo[i];
        }
    };
    public String adminPackageName;
    public List<String> entries;

    public /* synthetic */ ControlInfo(Parcel parcel, int i) {
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
        return "adminPackageName: " + this.adminPackageName + " ,appControlType: " + this.entries;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
