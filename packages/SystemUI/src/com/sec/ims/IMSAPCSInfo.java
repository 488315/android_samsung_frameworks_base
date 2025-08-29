package com.sec.ims;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class IMSAPCSInfo implements Parcelable, Cloneable {
    public static final Parcelable.Creator<IMSAPCSInfo> CREATOR = new Parcelable.Creator<IMSAPCSInfo>() { // from class: com.sec.ims.IMSAPCSInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSAPCSInfo createFromParcel(Parcel parcel) {
            return new IMSAPCSInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSAPCSInfo[] newArray(int i) {
            return new IMSAPCSInfo[i];
        }
    };
    private boolean mServiceStatus;

    public IMSAPCSInfo(Parcel parcel) {
        this.mServiceStatus = false;
        this.mServiceStatus = parcel.readInt() == 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mServiceStatus ? 1 : 0);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IMSAPCSInfo m3389clone() throws CloneNotSupportedException {
        return (IMSAPCSInfo) super.clone();
    }
}
