package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AppControlInfo implements Parcelable {
    public static final Parcelable.Creator<AppControlInfo> CREATOR = new Parcelable.Creator<AppControlInfo>() { // from class: com.samsung.android.knox.application.AppControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AppControlInfo[] newArray(int i) {
            return new AppControlInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AppControlInfo createFromParcel(Parcel parcel) {
            return new AppControlInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final AppControlInfo[] newArray(int i) {
            return new AppControlInfo[i];
        }
    };
    public String adminPackageName;
    public List<String> entries;
    public final Object mQueueLock;

    public /* synthetic */ AppControlInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        synchronized (this.mQueueLock) {
            this.adminPackageName = parcel.readString();
            this.entries = parcel.createStringArrayList();
        }
    }

    public final String toString() {
        return "adminPackageName: " + this.adminPackageName + " ,appControlType: " + this.entries;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        synchronized (this.mQueueLock) {
            parcel.writeString(this.adminPackageName);
            parcel.writeStringList(this.entries);
        }
    }

    public AppControlInfo() {
        this.adminPackageName = null;
        this.entries = null;
        this.mQueueLock = new Object();
    }

    private AppControlInfo(Parcel parcel) {
        this.adminPackageName = null;
        this.entries = null;
        this.mQueueLock = new Object();
        readFromParcel(parcel);
    }
}
