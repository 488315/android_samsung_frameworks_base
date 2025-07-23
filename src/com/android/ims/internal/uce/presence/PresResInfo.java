package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class PresResInfo implements Parcelable {
    public static final Parcelable.Creator<PresResInfo> CREATOR = new Parcelable.Creator<PresResInfo>() { // from class: com.android.ims.internal.uce.presence.PresResInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresResInfo createFromParcel(Parcel parcel) {
            return new PresResInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresResInfo[] newArray(int i) {
            return new PresResInfo[i];
        }
    };
    private String mDisplayName;
    private PresResInstanceInfo mInstanceInfo;
    private String mResUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PresResInstanceInfo getInstanceInfo() {
        return this.mInstanceInfo;
    }

    public void setInstanceInfo(PresResInstanceInfo presResInstanceInfo) {
        this.mInstanceInfo = presResInstanceInfo;
    }

    public String getResUri() {
        return this.mResUri;
    }

    public void setResUri(String str) {
        this.mResUri = str;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public void setDisplayName(String str) {
        this.mDisplayName = str;
    }

    public PresResInfo() {
        this.mResUri = "";
        this.mDisplayName = "";
        this.mInstanceInfo = new PresResInstanceInfo();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mResUri);
        parcel.writeString(this.mDisplayName);
        parcel.writeParcelable(this.mInstanceInfo, i);
    }

    private PresResInfo(Parcel parcel) {
        this.mResUri = "";
        this.mDisplayName = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mResUri = parcel.readString();
        this.mDisplayName = parcel.readString();
        this.mInstanceInfo = (PresResInstanceInfo) parcel.readParcelable(PresResInstanceInfo.class.getClassLoader(), PresResInstanceInfo.class);
    }
}
