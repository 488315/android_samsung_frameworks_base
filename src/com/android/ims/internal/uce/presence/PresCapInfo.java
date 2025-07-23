package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.common.CapInfo;

/* loaded from: classes5.dex */
public class PresCapInfo implements Parcelable {
    public static final Parcelable.Creator<PresCapInfo> CREATOR = new Parcelable.Creator<PresCapInfo>() { // from class: com.android.ims.internal.uce.presence.PresCapInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresCapInfo createFromParcel(Parcel parcel) {
            return new PresCapInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresCapInfo[] newArray(int i) {
            return new PresCapInfo[i];
        }
    };
    private CapInfo mCapInfo;
    private String mContactUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    public void setCapInfo(CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    public String getContactUri() {
        return this.mContactUri;
    }

    public void setContactUri(String str) {
        this.mContactUri = str;
    }

    public PresCapInfo() {
        this.mContactUri = "";
        this.mCapInfo = new CapInfo();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mContactUri);
        parcel.writeParcelable(this.mCapInfo, i);
    }

    private PresCapInfo(Parcel parcel) {
        this.mContactUri = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mContactUri = parcel.readString();
        this.mCapInfo = (CapInfo) parcel.readParcelable(CapInfo.class.getClassLoader(), CapInfo.class);
    }
}
