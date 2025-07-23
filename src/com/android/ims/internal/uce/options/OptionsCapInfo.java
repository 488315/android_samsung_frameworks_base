package com.android.ims.internal.uce.options;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.common.CapInfo;

/* loaded from: classes5.dex */
public class OptionsCapInfo implements Parcelable {
    public static final Parcelable.Creator<OptionsCapInfo> CREATOR = new Parcelable.Creator<OptionsCapInfo>() { // from class: com.android.ims.internal.uce.options.OptionsCapInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OptionsCapInfo createFromParcel(Parcel parcel) {
            return new OptionsCapInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OptionsCapInfo[] newArray(int i) {
            return new OptionsCapInfo[i];
        }
    };
    private CapInfo mCapInfo;
    private String mSdp;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static OptionsCapInfo getOptionsCapInfoInstance() {
        return new OptionsCapInfo();
    }

    public String getSdp() {
        return this.mSdp;
    }

    public void setSdp(String str) {
        this.mSdp = str;
    }

    public OptionsCapInfo() {
        this.mSdp = "";
        this.mCapInfo = new CapInfo();
    }

    public CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    public void setCapInfo(CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSdp);
        parcel.writeParcelable(this.mCapInfo, i);
    }

    private OptionsCapInfo(Parcel parcel) {
        this.mSdp = "";
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mSdp = parcel.readString();
        this.mCapInfo = (CapInfo) parcel.readParcelable(CapInfo.class.getClassLoader(), CapInfo.class);
    }
}
